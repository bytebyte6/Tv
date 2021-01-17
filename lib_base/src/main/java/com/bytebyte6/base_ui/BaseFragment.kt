package com.bytebyte6.base_ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bytebyte6.base.logd
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform


abstract class BaseFragment/*<Binding : ViewBinding>*/(layoutId: Int) : Fragment(layoutId) {

    var viewBinding: ViewBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        logd("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logd("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        logd("onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logd("onViewCreated")
        viewBinding = initBinding(view)
    }

    override fun onStart() {
        super.onStart()
        logd("onStart")
    }

    override fun onResume() {
        super.onResume()
        logd("onResume")
    }

    override fun onStop() {
        super.onStop()
        logd("onStop")
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
        logd("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        logd("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        logd("onDetach")
    }

    inline fun <reified T : ViewBinding> binding(): T? {
        return viewBinding as T?
    }

    abstract fun initBinding(view: View): ViewBinding?
}
