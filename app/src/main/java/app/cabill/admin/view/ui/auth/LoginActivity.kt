package app.cabill.agent.view.auth

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import app.cabill.admin.cache.PrefUtils
import app.cabill.admin.constants.Constants
import app.cabill.admin.databinding.ActivityLogin2Binding
import app.cabill.admin.model.Event
import app.cabill.admin.util.Utils
import app.cabill.admin.view.Dashboard

import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks

import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.EventBus.TAG
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.TimeUnit


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LoginActivity : FragmentActivity() {

    private var code: String? = ""
    private var bypass: Boolean = false
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var binding: ActivityLogin2Binding
    public lateinit var viewModel: LoginViewModel
    var verificationID: String? = null
    var mCallBacks: OnVerificationStateChangedCallbacks? = null
    var mResendToken: PhoneAuthProvider.ForceResendingToken? = null
    var verifyToken: String? = ""

    private fun stopCounter() {
        countDownTimer.onFinish()
    }

    fun verifyCode(code: String?) {
        val credential1 = PhoneAuthProvider.getCredential(
            verificationID!!,
            code!!
        )
        signInWithPhoneAuthCredential(credential1)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Utils.getInstance().showLoader(this, "Verifying. Please wait...")
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Utils.getInstance().dismissLoader()
                    viewModel.login(verifyToken, this)
                } else {
                    Utils.getInstance().dismissLoader()
                    // stopCounter();
                    // Sign in failed, display a message and update the UI
                    // Log.w(TAG, "signInWithCredential:failure", task.getException());
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(
                            this,
                            "Auth failed. Make sure you are using credentials right!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Suspecious activity detected. Firebase has blocked this number!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Log.e("error", task.exception!!.message!!)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pager.adapter = DividerPagerAdapter(this)
        binding.pager.isUserInputEnabled = false
        binding.fb1.visibility = View.VISIBLE
        binding.fb2.visibility = View.GONE

        viewModel = ViewModelProvider(this)
            .get(LoginViewModel::class.java)

        initFirebaseCallback()
        observeViewModel()
        registerPager()
    }

    private fun registerPager() {
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == 0) {
                    binding.fb1.visibility = View.VISIBLE
                    binding.fb2.visibility = View.GONE

                } else {
                    binding.fb1.visibility = View.GONE
                    binding.fb2.visibility = View.VISIBLE
                    startTimer(1)
                }
            }
        })
    }

    private fun initFirebaseCallback() {
        mCallBacks = object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Utils.getInstance().dismissLoader()
                code = credential.smsCode;
                if (!code.isNullOrEmpty())
                    verifyCode(code);
                else signInWithPhoneAuthCredential(credential)

                bypass = true
            }

            override fun onVerificationFailed(e: FirebaseException) {
                bypass = false
                Utils.getInstance().dismissLoader()
                Utils.getInstance().showAlertDialog(
                    this@LoginActivity,
                    """
                ${e.message}
                """, "Firebase Error"
                )
                stopCounter()
                if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                } else {
                    Log.e("error", e.localizedMessage)
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                verificationID = verificationId
                Log.e("verifyID", verificationID!!)
                Utils.getInstance().dismissLoader()
                mResendToken = token
            }
        }
    }

    private fun observeViewModel() {

        viewModel.getTokenObserver().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                PrefUtils.putString(Constants.TOKEN, it, this)
                stopCounter()
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                //stopCounter()
                countDownTimer.cancel()
            }
        })

        viewModel.loadingObserver().observe(this, Observer {
            if (it)
                Utils.getInstance().showLoader(this, "Verifying...")
            else Utils.getInstance().dismissLoader()

        })


        viewModel.getOtpValue().observe(this, Observer {
            if (it.length == 6) {
                Log.e("otp", it.toString())
                verifyCode(it.toString())
                // stopTimer()
            }
        })
        viewModel.getMobileObserver().observe(this, {
            CoroutineScope(Dispatchers.Main).launch {
//                Utils.getInstance().showLoader(this@LoginActivity, "Please wait...")
//                delay(3000)
//                Utils.getInstance().dismissLoader()
                Log.e("mobile", it)
                var formattedMobile: String = it.replace("+92", "0").replace(" ", "")
                viewModel.verify(formattedMobile, this@LoginActivity)
                Log.e("mobile", formattedMobile)

            }
        })
        viewModel.authObserver().observe(this, Observer {
            if (it.data?.verified!!) {
                binding.pager.setCurrentItem(1, true)
                val phoneNo: String = viewModel.getMobileObserver().value.toString()
                Log.e("phone", phoneNo)
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNo,  // Phone number to verify
                    40,  // Timeout duration
                    TimeUnit.SECONDS,  // Unit of timeout
                    this,  // Activity (for callback binding)
                    mCallBacks!!
                )
                verifyToken = it.data?.code
                Utils.getInstance().showLoader(this, "Please wait...")
            } else Toast.makeText(this, "Phone number not verified", Toast.LENGTH_SHORT).show()
        })
    }

    private fun stopTimer() {
        countDownTimer.onFinish()
    }

    private fun startTimer(minuti: Int) {
        countDownTimer = object : CountDownTimer((40 * minuti * 1000).toLong(), 500) {
            // 500 means, onTick function will be called at every 500 milliseconds
            override fun onTick(leftTimeInMilliseconds: Long) {
                val seconds = leftTimeInMilliseconds / 1000
                binding.barTimer.progress = seconds.toInt()
                binding.counterTv.text = String.format(
                    "%02d second(s)",
                    seconds % 60
                )
                // format the textview to show the easily readable format
            }

            override fun onFinish() {
                countDownTimer.cancel()
                binding.counterTv.text = "0 second"
                binding.barTimer.visibility = View.INVISIBLE
                if (bypass) {
                    startActivity(Intent(this@LoginActivity, Dashboard::class.java))
                    PrefUtils.putString("logged_in", "true", this@LoginActivity)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }.start()
    }


    class DividerPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            lateinit var fragment: Fragment

            if (position == 0) {
                fragment = FragmentOne()
            } else {
                fragment = FragmentTwo()
            }
            return fragment
        }

    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Event) {
        Log.e(TAG, event.value)
        if (event.code == 0)
            viewModel.setOtpValue(event.value)
        else viewModel.setMobileValue(event.value)
        /* Do something */
    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onMessageMobileEvent(event: String?) {
//        Log.e(TAG, event!!)
//        viewModel.setMobileValue(event!!)
//        /* Do something */
//    }
}