package com.example.uicomponents.ui.dialogs.selector

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.example.uicomponents.R
import com.example.uicomponents.databinding.UiDialogSelectorBinding
import com.example.uicomponents.ui.component.AttrsOptionComponent
import com.example.uicomponents.ui.component.buttons.AttrsTitledButton
import com.example.uicomponents.ui.component.inputs.AttrsInputText
import com.example.uicomponents.ui.component.inputs.model.AttrsPicker
import com.example.uicomponents.ui.dialogs.selector.adapter.DialogSelectorAdapter

data class AttrsDialogSelector(
    var itemList: List<AttrsOptionComponent>,
    val title: String,
    val subtitle: String? = null,
    val buttonText: String,
    val inputLabelText: String,
    val buttonAction: () -> Unit,
    val onSelectItem: (String, Any) -> Unit = { _, _ -> }
) : AttrsPicker

class DialogSelectorComponent(
    private val context: Context,
    private val attrs: AttrsDialogSelector,
    private val onResultEvent: (String, Any) -> Unit
) : Dialog(context, R.style.DialogSelectorStyle) {
    private var binding: UiDialogSelectorBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (binding == null) binding = UiDialogSelectorBinding.inflate(LayoutInflater.from(context))
        binding?.root?.let { setContentView(it) }
    }

    override fun onStart() {
        super.onStart()
        setDialogSize()
        render()
    }

    private fun setDialogSize() {
        val displayMetrics = DisplayMetrics()
        window?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        val screenWidth = displayMetrics.widthPixels

        val dialogPercentage = SCREEN_PERCENTAGE
        val dialogHeight = (screenHeight * dialogPercentage).toInt()
        val dialogWidth = (screenWidth * dialogPercentage).toInt()

        window?.setLayout(dialogWidth, dialogHeight)
    }

    private fun render() = binding?.apply {
        setTexts()
        setInputs()
        setAdapter()
        setButtons()
    }

    private fun setTexts() {
        binding?.apply {
            dialogSelectorTitle.text = attrs.title
            if (attrs.subtitle != null) {
                dialogSelectorSubtitle.isVisible = false
            } else {
                dialogSelectorSubtitle.text = attrs.subtitle
            }
        }
    }

    private fun setInputs() = binding?.apply {
        dialogSelectorInput.setAttributes(
            AttrsInputText(
                hint = attrs.inputLabelText,
                onTextChangeEvent = { text ->
                    filterList(text)
                }
            )
        )
    }

    private fun filterList(text: String) = binding?.apply {
        (dialogSelectorList.adapter as? DialogSelectorAdapter)?.filterList(text)
    }

    private fun setAdapter() = binding?.apply {
        val adapter = DialogSelectorAdapter(attrs.itemList) { text, data ->
            attrs.onSelectItem(text, data)
            onResultEvent(text, data)
            dismiss()
        }
        dialogSelectorList.adapter = adapter
    }

    private fun setButtons() = binding?.apply {
        dialogSelectorButton.setAttributes(
            AttrsTitledButton(
                buttonText = attrs.buttonText,
                onClick = {
                    attrs.buttonAction()
                    dismiss()
                },
            )
        )
    }

    companion object {
        const val SCREEN_PERCENTAGE = 0.7
    }
}
