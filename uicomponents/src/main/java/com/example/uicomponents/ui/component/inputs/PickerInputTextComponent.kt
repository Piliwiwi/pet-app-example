package com.example.uicomponents.ui.component.inputs

import android.app.DatePickerDialog
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.example.uicomponents.databinding.UiComponentPickerInputTextBinding
import com.example.uicomponents.ui.component.AttrsOptionComponent
import com.example.uicomponents.ui.component.inputs.PickerType.DATE
import com.example.uicomponents.ui.component.inputs.PickerType.SELECTOR
import com.example.uicomponents.ui.component.inputs.model.AttrsPicker
import com.example.uicomponents.ui.dialogs.selector.AttrsDialogSelector
import com.example.uicomponents.ui.dialogs.selector.DialogSelectorComponent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class AttrsPickerInputTextComponent(
    val type: PickerType,
    val data: AttrsPicker? = null,
    val labelText: String? = null,
    val onResultEvent: (String, Any) -> Unit = { _, _ -> },
    @DrawableRes val icon: Int? = null
)

data class AttrsPickerInputTextData(
    val name: String,
    val data: Any
)

enum class PickerType {
    SELECTOR, DATE
}

class PickerInputTextComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var binding: UiComponentPickerInputTextBinding? = null
    private var attributes: AttrsPickerInputTextComponent? = null
    private var selectedData: AttrsPickerInputTextData? = null

    init {
        val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = UiComponentPickerInputTextBinding.inflate(inflater, this)
    }

    fun setAttributes(attrs: AttrsPickerInputTextComponent) {
        attributes = attrs
        when (attrs.type) {
            SELECTOR -> setOptionSelector(attrs)
            DATE -> setDatePicker(attrs)
        }
    }

    fun updateSelector(options: List<AttrsOptionComponent>) {
        (attributes?.data as? AttrsDialogSelector)?.apply {
            itemList = options
            attributes?.let { setOptionSelector(it) }
        }
    }

    fun getData() = selectedData
    fun setData(data: AttrsPickerInputTextData?) {
        selectedData = data
        setText(data?.name.orEmpty())
    }

    fun setText(text: String) = binding?.apply {
        pickerComponentInputText.setText(text)
    }

    fun setLabelText(text: String) = binding?.apply {
        pickerComponentInputText.setAttributes(attrs = AttrsInputText(hint = text))
    }

    fun setError(message: String) = binding?.apply {
        pickerComponentInputText.setError(message)
    }

    private fun setOptionSelector(attrs: AttrsPickerInputTextComponent) = binding?.apply {
        (attrs.data as? AttrsDialogSelector)?.let { data ->
            pickerComponentInputText.setOnInputListener {
                val selectorDialog = DialogSelectorComponent(context, data) { text, key ->
                    attrs.onResultEvent(text, key)
                   setData(AttrsPickerInputTextData(name = text, data = key))
                }
                selectorDialog.show()
            }
        }
        setIcon(attrs)
    }

    private fun setDatePicker(attrs: AttrsPickerInputTextComponent) = binding?.apply {
        pickerComponentInputText.setOnInputListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                context,
                { _, dateYear, monthOfYear, dayOfMonth ->
                    setCalendarDate(
                        calendar = calendar,
                        year = dateYear,
                        month = monthOfYear,
                        day = dayOfMonth,
                        attrs = attrs
                    )
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        setIcon(attrs)
    }

    private fun setCalendarDate(
        attrs: AttrsPickerInputTextComponent,
        calendar: Calendar,
        year: Int,
        month: Int,
        day: Int
    ) {
        calendar.set(year, month, day)
        val dateInMillis = calendar.timeInMillis
        val selectedDate = convertMillisToDateString(dateInMillis, "dd/MM/yyyy")

        attrs.onResultEvent(selectedDate, dateInMillis)
        binding?.pickerComponentInputText?.setText(selectedDate)
        selectedData = AttrsPickerInputTextData(name = selectedDate, data = dateInMillis)
    }

    private fun convertMillisToDateString(millis: Long, dateFormat: String): String {
        val date = Date(millis)
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        return formatter.format(date)
    }

    private fun setIcon(attrs: AttrsPickerInputTextComponent) = binding?.apply {
        pickerComponentInputText.setIcon(attrs.icon)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        selectedData = null
        binding = null
    }
}
