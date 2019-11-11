package goodguygregory.app.sunset

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sceneView: View
    private lateinit var sunView: View
    private lateinit var skyView: View

    private val blueSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.blue_sky)
    }
    private val sunsetSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.sunset_sky)
    }
    private val nightSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.night_sky)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sceneView = findViewById(R.id.scene)
        sunView = findViewById(R.id.sun)
        skyView = findViewById(R.id.sky)

//        Makes call to start animation with onClickListener

        sceneView.setOnClickListener {
            startAnimation()
        }
    }

//    Creates Animation Function
    private fun startAnimation() {
        val sunYStart = sunView.top.toFloat()
        val sunYEnd = skyView.height.toFloat()

        val heightAnimator = ObjectAnimator
            .ofFloat(sunView, "y", sunYStart, sunYEnd)
            .setDuration(3000)

//      Adds acceleration to the animation
        heightAnimator.interpolator = AccelerateInterpolator()

//      Adds color animation to the sunset
        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(skyView, "backgroundColor", blueSkyColor, sunsetSkyColor)
            .setDuration(3000)

//        Scales the Object Animators values for the transition on colors with a TypeEvaluator
            sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

//    Builds nightSky animatior
        val nightSkyAnimator = ObjectAnimator
            .ofInt(skyView, "backgroundColor", sunsetSkyColor, nightSkyColor)
            .setDuration(1500)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())

//     Builds and Animator set: the AnimatorSet object will allow the specifcation of animation order

        val animatorSet = AnimatorSet()
        animatorSet.play(heightAnimator)
            .with(sunsetSkyAnimator)
            .before(nightSkyAnimator)
        animatorSet.start()
    }
}
