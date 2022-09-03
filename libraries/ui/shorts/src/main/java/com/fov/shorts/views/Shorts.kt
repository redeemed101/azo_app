package com.fov.shorts.views

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.VideoView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.fov.shorts.R
import com.fov.shorts.databinding.ProgressShortViewBinding
import com.fov.shorts.models.ShortsView
import com.fov.shorts.utils.ProgressTimeWatcher
import com.fov.shorts.utils.ShortsCallback
import java.lang.Exception

class Shorts : ConstraintLayout {
    private var currentlyShownIndex = 0
    private lateinit var currentView: View
    private var storiesViewList: List<ShortsView>
    private var libSliderViewList = mutableListOf<MyProgressBar>()
    private var storiesCallback : ShortsCallback
    private lateinit var view: View
    private val passedInContainerView: ViewGroup
    private var mProgressDrawable : Int = R.drawable.green_lightgray_drawable
    private var pausedState : Boolean = false
    lateinit var gestureDetector: GestureDetector
    lateinit var progressStoryViewBinding : ProgressShortViewBinding;

    constructor(
        context: Context,
        storiesViewList: List<ShortsView>,
        passedInContainerView: ViewGroup,
        storiesCallback: ShortsCallback,
        @DrawableRes mProgressDrawable : Int = R.drawable.green_lightgray_drawable
    ) : super(context) {
        this.storiesViewList = storiesViewList
        this.storiesCallback = storiesCallback
        this.passedInContainerView = passedInContainerView
        this.mProgressDrawable = mProgressDrawable
        initView()
        init()
    }

    private fun init() {
        storiesViewList.forEachIndexed { index, sliderView ->
            val myProgressBar = MyProgressBar(
                context,
                index,
                sliderView.durationInSeconds,
                object : ProgressTimeWatcher {
                    override fun onEnd(indexFinished: Int) {
                        currentlyShownIndex = indexFinished + 1
                        next()
                    }
                },
                mProgressDrawable)
            libSliderViewList.add(myProgressBar)
            //view.linearProgressIndicatorLay.addView(myProgressBar)
            progressStoryViewBinding.linearProgressIndicatorLay.addView(myProgressBar)
        }
        //start()
    }

    fun callPause(pause : Boolean){
        try {
            if(pause){
                if(!pausedState){
                    this.pausedState = !pausedState
                    pause(false)
                }
            } else {
                if(pausedState){
                    this.pausedState = !pausedState
                    resume()
                }
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun initView() {
        view = View.inflate(context, R.layout.progress_short_view, this)
        progressStoryViewBinding = ProgressShortViewBinding.bind(view)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        gestureDetector = GestureDetector(context, SingleTapConfirm())

        val touchListener = object  : OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                //Log.e("BackClicked","Back clicked ${v?.id}")
                if (gestureDetector.onTouchEvent(event)) {
                    // single tap
                    if(v?.id == progressStoryViewBinding.rightLay.id){
                        next()
                    } else if(v?.id == progressStoryViewBinding.leftLay.id){
                        prev()
                    }
                    return true
                } else {
                    // your code for move and drag
                    when(event?.action){
                        MotionEvent.ACTION_DOWN -> {
                            callPause(true)
                            return true
                        }

                        MotionEvent.ACTION_UP -> {
                            callPause(false)
                            return true
                        }
                        else -> return false
                    }
                }
            }
        }
        progressStoryViewBinding.backArrow.setOnClickListener{
            Log.e("BackClicked","tapped here2")
            finish()
        }
//        view.leftLay.setOnClickListener { prev() }
//        view.rightLay.setOnClickListener { next() }

        progressStoryViewBinding.leftLay.setOnTouchListener(touchListener)
        progressStoryViewBinding.rightLay.setOnTouchListener(touchListener)

        val extraTouchListener = OnTouchListener { v, _ ->
            Log.e("BackClicked","tapped here")
            finish()
            true
        }
        progressStoryViewBinding.extraView.setOnTouchListener(extraTouchListener)
        //view.container.setOnTouchListener(touchListener)

        this.layoutParams = params
        passedInContainerView.addView(this)
    }

    fun show() {
        progressStoryViewBinding.loaderProgressbar.visibility = View.GONE
        if (currentlyShownIndex != 0) {
            for (i in 0..Math.max(0, currentlyShownIndex - 1)) {
                libSliderViewList[i].progress = 100
                libSliderViewList[i].cancelProgress()
            }
        }

        if (currentlyShownIndex != libSliderViewList.size - 1) {
            for (i in currentlyShownIndex + 1..libSliderViewList.size - 1) {
                libSliderViewList[i].progress = 0
                libSliderViewList[i].cancelProgress()
            }
        }

        currentView = storiesViewList[currentlyShownIndex].view

        libSliderViewList[currentlyShownIndex].startProgress()

        storiesCallback.onNextCalled(currentView, this, currentlyShownIndex)

        progressStoryViewBinding.currentlyDisplayedView.removeAllViews()
        progressStoryViewBinding.currentlyDisplayedView.addView(currentView)
        val params = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT, 1f
        )
        //params.gravity = Gravity.CENTER_VERTICAL
        if(currentView is ImageView) {
            (currentView as ImageView).scaleType = ImageView.ScaleType.FIT_CENTER
            (currentView as ImageView).adjustViewBounds = true
        }
        currentView.layoutParams = params
    }

    fun start() {
//            Handler().postDelayed({
//                show()
//            }, 2000)
        show()
    }

    fun editDurationAndResume(index: Int, newDurationInSecons : Int){
        progressStoryViewBinding.loaderProgressbar.visibility = View.GONE
        libSliderViewList[index].editDurationAndResume(newDurationInSecons)
    }

    fun pause(withLoader : Boolean) {
        if(withLoader){
            progressStoryViewBinding.loaderProgressbar.visibility = View.VISIBLE
        }
        libSliderViewList[currentlyShownIndex].pauseProgress()
        if(storiesViewList[currentlyShownIndex].view is VideoView){
            (storiesViewList[currentlyShownIndex].view as VideoView).pause()
        }
    }

    fun resume() {
        progressStoryViewBinding.loaderProgressbar.visibility = View.GONE
        libSliderViewList[currentlyShownIndex].resumeProgress()
        if(storiesViewList[currentlyShownIndex].view is VideoView){
            (storiesViewList[currentlyShownIndex].view as VideoView).start()
        }
    }

    private fun stop() {

    }

    fun next() {
        try {
            if (currentView == storiesViewList[currentlyShownIndex].view) {
                currentlyShownIndex++
                if (storiesViewList.size <= currentlyShownIndex) {
                    finish()
                    return
                }
            }
            show()
        } catch (e: IndexOutOfBoundsException) {
            finish()
        }
    }

    private fun finish() {
        storiesCallback.done()
        for (progressBar in libSliderViewList) {
            progressBar.cancelProgress()
            progressBar.progress = 100
        }
    }

    fun prev() {
        try {
            if (currentView == storiesViewList[currentlyShownIndex].view) {
                currentlyShownIndex--
                if (0 > currentlyShownIndex) {
                    currentlyShownIndex = 0
                }
            }
        } catch (e: IndexOutOfBoundsException) {
            currentlyShownIndex -= 2
        } finally {
            show()
        }
    }

    private inner class SingleTapConfirm : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(event: MotionEvent): Boolean {
            return true
        }
    }


}