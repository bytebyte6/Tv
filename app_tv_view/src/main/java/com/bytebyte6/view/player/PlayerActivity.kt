package com.bytebyte6.view.player

import android.os.Bundle
import com.bytebyte6.base_ui.BaseActivity
import com.bytebyte6.view.R
import com.bytebyte6.view.replaceNotAddToBackStack

class PlayerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            replaceNotAddToBackStack(
                PlayerFragment().apply { arguments = intent.extras },
                PlayerFragment.TAG
            )
        }
    }
}