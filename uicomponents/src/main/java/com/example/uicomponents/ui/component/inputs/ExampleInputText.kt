package com.example.uicomponents.ui.component.inputs

import android.content.Context
import android.content.res.ColorStateList
import android.text.InputFilter
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.example.uicomponents.R
import com.example.uicomponents.databinding.UiComponentInputTextBinding
import com.example.utils.validator.EmailValidator.emailValidation
import com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE

data class AttrsInputText(
    val hint: String = "",
    val helperText: String = "",
    val maxLength: Int? = null,
    val onTextChangeEvent: (String) -> Unit = { _ -> },
    val errorHandler: (String) -> String = { _ -> "" },
    @DrawableRes val icon: Int? = null
)

data class TypedError(
    val isError: Boolean = false,
    val message: String = ""
)

class ExampleInputText @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var binding: UiComponentInputTextBinding? = null
    private var typedError: TypedError? = null
    private var finalText: String? = null
    private var watcherOnText: TextWatcher? = null
    private var watcherAfterText: TextWatcher? = null
    private var watcherAfterText2: TextWatcher? = null

    init {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = UiComponentInputTextBinding.inflate(inflater, this)

        initAttributes()
    }

    private fun initAttributes() {
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.ExampleInputText)

        try {
            when (styledAttrs.getInt(R.styleable.ExampleInputText_ui_input_type, EMAIL)) {
                EMAIL -> setEmailInput()
                PASSWORD -> setPasswordInput()
                TEXT -> setTextInput()
                LONG_TEXT -> setLongTextInput()
                NUMBER -> setNumberInput()
                PICKER -> setPicker()
                else -> setTextInput()
            }
        } finally {
            styledAttrs.recycle()
        }

        initEvents()
    }

    fun setAttributes(attrs: AttrsInputText) {
        setTexts(attrs)
        setColors()
        setEvents(attrs)
        setConfig(attrs)
        setIcon(attrs.icon)
    }

    fun getText() = finalText.orEmpty()

    fun hasTypedError() = typedError?.isError == true

    fun hasError() = binding?.inputTextComponentLayout?.isErrorEnabled ?: false

    fun setError(message: String) = binding?.apply {
        inputTextComponentLayout.isErrorEnabled = true
        inputTextComponentLayout.error = message
    }

    fun setText(text: String?) = binding?.apply {
        inputTextComponentInput.setText(text)
    }

    fun blockInput() = binding?.apply {
        inputTextComponentInput.isFocusable = false
        inputTextComponentInput.inputType = AUTOFILL_TYPE_NONE
    }

    fun setHint(hint: String) = binding?.apply {
        inputTextComponentInput.hint = hint
    }

    fun setOnInputListener(event: () -> Unit) = binding?.apply {
        inputTextComponentInput.setOnClickListener(null)
        inputTextComponentInput.setOnClickListener {
            event()
        }
    }

    fun setIcon(icon: Int?) = binding?.apply {
        if (icon != null) {
            inputTextComponentLayout.startIconDrawable = context.getDrawable(icon)
            val colorStateList =
                ColorStateList.valueOf(resources.getColor(R.color.ui_brand_primary))
            inputTextComponentLayout.setStartIconTintList(colorStateList)
        }
    }

    private fun setPicker() = binding?.apply {
        inputTextComponentInput.isFocusable = false
        inputTextComponentInput.inputType = AUTOFILL_TYPE_NONE
    }

    private fun setEmailInput() = binding?.apply {
        inputTextComponentInput.inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        inputTextComponentLayout.hint = context.getString(R.string.email)
        watcherOnText = inputTextComponentInput.doOnTextChanged { text, _, _, _ ->
            setEmailMatchError(text.toString())
        }
    }

    private fun setEmailMatchError(email: String) = binding?.apply {
        typedError = TypedError(
            isError = !emailValidation(email),
            message = context.getString(R.string.invalid_email)
        )
    }

    private fun setPasswordInput() = binding?.apply {
        inputTextComponentInput.inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
        inputTextComponentLayout.endIconMode = END_ICON_PASSWORD_TOGGLE
        inputTextComponentLayout.hint = context.getString(R.string.password)
    }

    private fun setTextInput() = binding?.apply {
        inputTextComponentInput.inputType = TYPE_CLASS_TEXT
        inputTextComponentInput.isSingleLine = true
    }

    private fun setLongTextInput() = binding?.apply {
        inputTextComponentInput.inputType = TYPE_CLASS_TEXT
        inputTextComponentInput.isSingleLine = false
        inputTextComponentInput.gravity = Gravity.TOP or Gravity.START
        inputTextComponentInput.setLines(15)
    }

    private fun setNumberInput() = binding?.apply {
        inputTextComponentInput.inputType = TYPE_CLASS_NUMBER
    }

    private fun initEvents() = binding?.apply {
        watcherAfterText2 = inputTextComponentInput.doAfterTextChanged {
            finalText = it.toString()
            manageErrors(finalText.orEmpty())
        }
    }

    private fun setTexts(attrs: AttrsInputText) = binding?.apply {
        if (attrs.hint.isNotEmpty()) inputTextComponentLayout.hint = attrs.hint
        if (attrs.helperText.isNotEmpty()) inputTextComponentLayout.helperText = attrs.helperText
    }


    private fun setColors() = binding?.apply {
        val colorInt: Int = resources.getColor(R.color.ui_brand_primary)
        val colorStateList = ColorStateList.valueOf(colorInt)
        inputTextComponentLayout.setHelperTextColor(colorStateList)
    }

    private fun setEvents(attrs: AttrsInputText) = binding?.apply {
        watcherAfterText = inputTextComponentInput.doAfterTextChanged {
            finalText = it.toString()
            attrs.onTextChangeEvent(finalText.orEmpty())
            manageErrors(finalText.orEmpty(), attrs)
        }
    }

    private fun setConfig(attrs: AttrsInputText) = binding?.apply {
        inputTextComponentInput.filters = arrayOf(InputFilter.LengthFilter(attrs.maxLength ?: 120))
    }

    private fun manageErrors(text: String, attrs: AttrsInputText? = null) = binding?.apply {
        val customErrorMessage = attrs?.errorHandler?.invoke(text) ?: ""
        val customError = customErrorMessage.isNotEmpty()
        val errorByType = typedError?.isError == true

        inputTextComponentLayout.isErrorEnabled = customError || errorByType
        inputTextComponentLayout.error = when {
            errorByType -> typedError?.message
            customError -> customErrorMessage
            else -> null
        }
    }

    fun dealloc() {
        finalText = null
        binding?.inputTextComponentInput?.clearFocus()
        binding?.inputTextComponentInput?.text?.clear()
        binding?.inputTextComponentInput?.setOnClickListener(null)
        binding?.inputTextComponentInput?.removeTextChangedListener(watcherOnText)
        binding?.inputTextComponentInput?.removeTextChangedListener(watcherAfterText)
        binding?.inputTextComponentInput?.removeTextChangedListener(watcherAfterText2)
        watcherOnText = null
        watcherAfterText = null
        watcherAfterText2 = null
        binding = null
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        dealloc()
    }

    companion object {
        const val EMAIL = 1
        const val PASSWORD = 2
        const val TEXT = 3
        const val LONG_TEXT = 4
        const val NUMBER = 5
        const val PICKER = 6
    }
}