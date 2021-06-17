package app.cabill.admin.remote

import android.content.Context
import android.util.Log
import app.cabill.admin.cache.PrefUtils
import app.cabill.admin.constants.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {
        fun client(context: Context): API {
            val builder = OkHttpClient.Builder()

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            builder.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(object : KotlinDemoInterceptor() {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {

//
//                    val accessToken = SharedPreferenceUtils.getFCMToken(applicationContext)
//                    val accessToken2 = PreferenceManager.getDefaultSharedPreferences(this@Slic)
//                        .getString(Constants.NEW_ACCESS_TOKEN, "")
//                    val device_id = SystemUtility.getDeviceId(applicationContext)
//                    val model_name = SystemUtility.getModelName(applicationContext)
//                    val device_type = "android"
                        var request = chain.request()
                        //alternative

                        request = request.newBuilder()
                            //.headers(moreHeaders)
//                        .addHeader(Constants.TOKEN, accessToken)
//                        //   .addHeader(Constants.TOKEN, accessToken)
//                        .addHeader(Constants.DEVICE_ID, device_id)
//                        .addHeader(Constants.DEVICE_OS, device_type)
//                        .addHeader(Constants.DEVICE_MODEL, model_name)
//                        .addHeader(Constants.OS_VERSION, BuildConfig.VERSION_CODE.toString())
//                        .addHeader(Constants.CONTENT_TYPE, "application/json")
//                        .addHeader(Constants.ACCEPT_VERSION, BuildConfig.VERSION_NAME.toString())
//                        .addHeader(Constants.ACCEPT_LANGUAGE, "en")
//                        .addHeader(Constants.AUTHORIZATION, "Bearer " + accessToken2)
                            .addHeader(
                                "Authorization",
                                "Bearer " + PrefUtils.getString(Constants.TOKEN, context)
                            )
                            .build()
//
//                    Log.e("model_name", model_name)
                    Log.e("headers", request.headers.toString())
                        return chain.proceed(request)
                    }
                })


            val okHttpClient = builder.build()

            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://recovery.hte-solutions.com/api/admin/")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

            return retrofit.create(API::class.java)
        }

        open class KotlinDemoInterceptor : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                //alternative
                val moreHeaders = request.headers.newBuilder()
                    .add("Cache-control", "no-cache")
                    .build()

                request = request.newBuilder().headers(moreHeaders).build()

                /* ##### List of headers ##### */
                // headerKey0: HeaderVal0
                // headerKey0: HeaderVal0--NotReplaced/NorUpdated
                // headerKey1: HeaderVal1
                // headerKey2: HeaderVal2--UpdatedHere
                // headerLine4: headerLine4Val

                return chain.proceed(request)
            }
        }
    }
}