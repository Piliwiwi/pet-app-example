package com.example.uicomponents.ui.dialogs.info

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import com.example.uicomponents.R
import com.example.uicomponents.databinding.UiComponentDialogInfoComponentBinding
import com.example.uicomponents.ui.component.buttons.AttrsTitledButton

data class AttrsDialogInfoComponent(
    val title: String,
    val content: String,
    val additionalInfo: String? = null,
    val buttonText: String
)

class DialogInfoComponent(
    private val context: Context,
    private val attrs: AttrsDialogInfoComponent
) : Dialog(context, R.style.DialogSelectorStyle) {
    private var binding: UiComponentDialogInfoComponentBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (binding == null) binding =
            UiComponentDialogInfoComponentBinding.inflate(LayoutInflater.from(context))
        binding?.root?.let { setContentView(it) }
    }

    override fun onStart() {
        super.onStart()
        setDialogSize()
        setTexts(attrs)
        setButton(attrs)
    }

    private fun setDialogSize() {
        val displayMetrics = DisplayMetrics()
        window?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        val screenWidth = displayMetrics.widthPixels

        val dialogPercentageVertical = INFO_PERCENTAGE_SCREEN_VERTICAL
        val dialogPercentageHorizontal = INFO_PERCENTAGE_SCREEN_HORIZONTAL
        val dialogHeight = (screenHeight * dialogPercentageVertical).toInt()
        val dialogWidth = (screenWidth * dialogPercentageHorizontal).toInt()

        window?.setLayout(dialogWidth, dialogHeight)
    }

    private fun setTexts(attrs: AttrsDialogInfoComponent) = binding?.apply {
        dialogComponentInfoTitle.text = attrs.title
        dialogComponentInfoContent.text = attrs.content
        if (attrs.additionalInfo != null) dialogComponentInfoAdditionalInfo.text =
            attrs.additionalInfo
    }

    private fun setButton(attrs: AttrsDialogInfoComponent) = binding?.apply {
        dialogComponentInfoButton.setAttributes(
            AttrsTitledButton(
                buttonText = attrs.buttonText,
                onClick = { dismiss() })
        )
    }

    companion object {
        const val INFO_PERCENTAGE_SCREEN_HORIZONTAL = 0.8
        const val INFO_PERCENTAGE_SCREEN_VERTICAL = 0.37
    }
}
