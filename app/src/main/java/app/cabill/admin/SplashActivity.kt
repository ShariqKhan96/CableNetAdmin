package app.cabill.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.cabill.admin.cache.PrefUtils
import app.cabill.admin.view.Dashboard
import app.cabill.agent.view.auth.LoginActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        FirebaseDatabase.getInstance().reference.child("ACCESS_APPROVAL")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value.toString() != "true") {
                        this@SplashActivity.finishAffinity()
                    } else {
                        if (PrefUtils.getString("logged_in", this@SplashActivity).equals("true")){
                            startActivity(Intent(this@SplashActivity, Dashboard::class.java))
                            finish()
                        }else{
                            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                            finish()
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}