package com.basic.mywwweather.ui.rengwuxian

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.basic.mywwweather.R
import com.ww.study.PieData
import com.ww.study.dp
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*
import kotlin.collections.ArrayList

class TestAct : AppCompatActivity() {

    private var timer: Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

//        etFloat()
    }

    override fun onResume() {
        super.onResume()
    }
    /**简单时间*/
    fun time(){
        timeView.visibility = View.VISIBLE
        timeView.updateTime()
        timer = Timer()
        timer?.schedule(object : TimerTask(){
            override fun run() {
                timeView.updateTime()
            }
        }, 1000, 1000)
    }

    /**简单饼图*/
    fun pie(){
        pieView.visibility = View.VISIBLE
        val pieData1 = PieData(216f, Color.BLUE)
        val pieData2 = PieData(33f, Color.GRAY)
        val pieData3 = PieData(150f, Color.BLACK)
        val pieData4 = PieData(66f, Color.GREEN)
        val pieData5 = PieData(110f, Color.CYAN)
        val pieData6 = PieData(40f, Color.RED)
        val pieDataList = ArrayList<PieData>()
        pieDataList.add(pieData1)
        pieDataList.add(pieData2)
        pieDataList.add(pieData3)
        pieDataList.add(pieData4)
        pieDataList.add(pieData5)
        pieDataList.add(pieData6)
        pieView.setPieList(pieDataList)
    }

    /**圆形图片*/
    fun circle(){
        ivCircle.visibility = View.VISIBLE
    }

    /**文字环绕图片*/
    fun multiText(){
        tvMulti.visibility = View.VISIBLE
    }

    /**范围裁切和几何变换*/
    fun cameraView(){
        cameraView.visibility = View.VISIBLE
        cameraView.setOnClickListener {
            val top = ObjectAnimator.ofFloat(cameraView, "topFlip", 0f, 60f, 0f)
            top.duration = 1500
            val bottom = ObjectAnimator.ofFloat(cameraView, "bottomFlip", 30f, -60f, 30f)
            bottom.duration = 1500
            val flipRotation = ObjectAnimator.ofFloat(cameraView, "flipRotation", 25f, 60f, 25f)
            flipRotation.duration = 1500
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(top, bottom, flipRotation)
            animatorSet.interpolator = AccelerateDecelerateInterpolator()
            animatorSet.start()
        }
    }

    /**动画*/
    fun animator(){
        //圆小变大，点位移，头像动画，文字动画
        time()
        timeView.setOnClickListener {
            timeView.animate()
                    .scaleX(0f)
                    .scaleY(0f)
                    .translationY(-timeView.height.toFloat())
                    .setDuration(2000)
                    .setListener(object : Animator.AnimatorListener{
                        override fun onAnimationStart(animation: Animator?) {

                        }

                        override fun onAnimationEnd(animation: Animator?) {
                            cameraView()
                        }

                        override fun onAnimationCancel(animation: Animator?) {

                        }

                        override fun onAnimationRepeat(animation: Animator?) {

                        }
                    })
                    .start()
        }
    }

    fun etFloat(){
        etFloat.visibility = View.VISIBLE
    }
    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}