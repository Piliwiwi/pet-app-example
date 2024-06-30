package com.example.uicomponents.ui.loadscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uicomponents.databinding.UiActivityLoadScreenBinding

class LoadScreen : AppCompatActivity() {
    private var binding: UiActivityLoadScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (binding == null) binding = UiActivityLoadScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onStart() {
        super.onStart()
        setAttributes()
    }

    private fun setAttributes() = binding?.apply {
        intent.getParcelableExtra<AttrsLoadScreen>(ATTRS)?.let {
            screen.activityEvent = { finishWithResultOk() }
            screen.setAttributes(it)
        }
    }

    private fun finishWithResultOk() {
        setResult(RESULT_OK)
        finish()
    }

    companion object {
        const val ATTRS = "attrs"

        fun makeIntent(context: Context, attrs: AttrsLoadScreen) =
            Intent(context, LoadScreen::class.java).also {
                it.putExtra(ATTRS, attrs)
            }
    }
}