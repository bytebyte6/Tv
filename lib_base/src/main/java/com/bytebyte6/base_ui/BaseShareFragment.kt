package com.bytebyte6.base_ui

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import com.bytebyte6.base.logd
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform

const val KEY_TRANS_NAME = "KEY_TRANS_NAME"

abstract class BaseShareFragment/*<Binding : ViewBinding>*/(layoutId: Int) : BaseFragment(layoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd("onCreate")
        exitTransition = Hold()
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = arguments?.getString(KEY_TRANS_NAME)
        logd("onViewCreated name= $name")
        view.transitionName = name
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }
}
