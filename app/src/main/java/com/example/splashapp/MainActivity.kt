package com.example.splashapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.FragmentActivity
import com.example.splashapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    //매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 자동 생성된 뷰 바인딩 클래스에서의 inflate라는 메서드를 활용해서
        // 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActivity()
    }

    private fun initActivity() {
        initSplashStart()
    }

    private fun initSplashStart() = with(binding) {
        imageView.setOnClickListener {
            container.transitionToEnd {
                startSingleTopActivity(SubActivity::class.java)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
        }
        container.transitionToEnd {

        }
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}

fun <T : Activity> Activity.startSingleTopActivity(clazz: Class<T>, action: Intent.() -> Unit = {}) {
    val intent = Intent(this, clazz).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        action.invoke(this)
    }

    startActivity(intent)
}

fun MotionLayout.setTransitionListener(started: () -> Unit = {}, completed: (Int) -> Unit = {}) = setTransitionListener(object : MotionLayout.TransitionListener {
    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) = started()
    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) = Unit
    override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) = completed(currentId)
    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) = Unit
})