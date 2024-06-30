package com.example.uicomponents.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import com.example.uicomponents.R

class ExampleText @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        initAttributes()
    }

    private fun initAttributes() {
        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ExampleText)

        try {
            when (styledAttributes.getInt(R.styleable.ExampleText_ui_text_type, HEADER)) {
                HEADER -> TextViewCompat.setTextAppearance(
                    this,
                    R.style.UiFontHeader
                )
                SUBTITLE -> TextViewCompat.setTextAppearance(
                    this,
                    R.style.UiFontSubtitle
                )
                SECONDARY_SUBTITLE -> TextViewCompat.setTextAppearance(
                    this,
                    R.style.UiFontSecondarySubtitle
                )
                INFO -> TextViewCompat.setTextAppearance(
                    this,
                    R.style.UiFontInfo
                )
                BODY -> TextViewCompat.setTextAppearance(
                    this,
                    R.style.UiFontBody
                )
                ADDITIONAL_INFO -> TextViewCompat.setTextAppearance(
                    this,
                    R.style.UiFontAdditionalInfo
                )
                BIG_HEADER -> TextViewCompat.setTextAppearance(
                    this,
                    R.style.UiFontBigHeader
                )
                BODY_18 -> TextViewCompat.setTextAppearance(
                    this,
                    R.style.UiFontBody18
                )
                LABEL -> TextViewCompat.setTextAppearance(
                    this,
                    R.style.UiFontLabel
                )
                SECONDARY_INFO_12 -> TextViewCompat.setTextAppearance(
                    this,
                    R.style.UiFontSecondaryInfo12
                )
                SECONDARY_BODY_14 -> TextViewCompat.setTextAppearance(
                    this,
                    R.style.UiFontBody14
                )
                INSTRUCTION -> TextViewCompat.setTextAppearance(
                    this,
                    R.style.UiFontInstruction16
                )
            }
        } finally {
            styledAttributes.recycle()
        }
    }

    companion object {
        const val HEADER = 1
        const val SUBTITLE = 2
        const val SECONDARY_SUBTITLE = 3
        const val INFO = 4
        const val BODY = 5
        const val ADDITIONAL_INFO = 6
        const val BIG_HEADER = 7
        const val BODY_18 = 8
        const val LABEL = 9
        const val SECONDARY_INFO_12 = 10
        const val SECONDARY_BODY_14 = 11
        const val INSTRUCTION = 12
    }
}