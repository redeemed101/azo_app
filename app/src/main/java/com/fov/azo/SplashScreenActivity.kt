package com.fov.azo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.constraintlayout.widget.ConstraintLayout
import com.fov.core.di.Preferences
import com.fov.core.utils.Validation
import com.fov.domain.database.daos.UserDao
import com.fov.main.utils.extensions.isDarkThemeOn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPrefs : Preferences

    @Inject
    lateinit var userDao : UserDao


    private var TIME_OUT:Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val layout = findViewById<ConstraintLayout>(R.id.splashLayout)
        val text = findViewById<TextView>(R.id.textViewId)
        text.setTextColor(Color(0xFF044B95).toArgb())
        var  logo = com.fov.common_ui.R.drawable.fov_logo
        var backgroundColor = Color.White.toArgb()
        if (applicationContext.isDarkThemeOn()){
           logo = com.fov.common_ui.R.drawable.fov_logo
           backgroundColor = Color(0xFFFFFF).toArgb()
        }
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(logo)
        layout.setBackgroundColor(backgroundColor)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = backgroundColor
        }

        // HERE WE ARE TAKING THE REFERENCE OF OUR IMAGE
        // SO THAT WE CAN PERFORM ANIMATION USING THAT IMAGE

        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        imageView.startAnimation(slideAnimation)


        val slideAnimationText = AnimationUtils.loadAnimation(this, R.anim.side_slide2)
        text.startAnimation(slideAnimationText)





        supportActionBar?.hide()
        CoroutineScope(Dispatchers.Main).launch {
            loadSplashScreen()
        }
    }
    private suspend fun loadSplashScreen(){
        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                // You can declare your desire activity here to open after finishing splash screen. Like MainActivity
                CoroutineScope(Dispatchers.Main).launch {
                    var intent = Intent(this@SplashScreenActivity, AuthenticationActivity::class.java)
                   sharedPrefs.accessToken.collectLatest{ token ->
                       sharedPrefs.isVerified.collectLatest { verified ->
                           if(token != null && token.isNotEmpty()){

                               var user = userDao.getUsers().first()

                               val notEmpty = user.isNotEmpty()

                               if (Validation.isTokenValid(token!!) && verified == true && notEmpty) {
                                   intent = Intent(this@SplashScreenActivity, MainActivity::class.java)


                               }
                           }

                           intent = Intent(this@SplashScreenActivity, MainActivity::class.java)

                           startActivity(intent)

                           overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                           finish()

                           this.cancel()

                       }

                   }

                }

            },TIME_OUT)
        }
    }
}