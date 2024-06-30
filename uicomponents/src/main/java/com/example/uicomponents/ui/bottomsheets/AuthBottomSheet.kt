package com.example.uicomponents.ui.bottomsheets

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.uicomponents.R
import com.example.uicomponents.databinding.UiGroupComponentBottomSheetAuthBinding
import com.example.uicomponents.ui.component.buttons.AttrsTitledButton
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


data class AttrsAuthBottomSheet(
    @StringRes val title: Int = R.string.you_log_in,
    @StringRes val forgotText: Int = R.string.forgot_my_password,
    @StringRes val buttonText: Int = R.string.log_in,
    @StringRes val forgotPassButtonText: Int = R.string.recover,
    val onForgotClick: () -> Unit,
    val onButtonClick: (String, String) -> Unit,
    val topView: View? = null
)

class AuthBottomSheet(private val attrs: AttrsAuthBottomSheet) : BottomSheetDialogFragment() {
    private var binding: UiGroupComponentBottomSheetAuthBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (binding == null) binding =
            UiGroupComponentBottomSheetAuthBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun getTheme() = R.style.CustomBottomSheetDialog

    override fun onStart() {
        super.onStart()
        render()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val array = intArrayOf(1,2)
        attrs.topView?.getLocationOnScreen(array)

        val params = ((view?.parent as View).layoutParams as CoordinatorLayout.LayoutParams)
        val behavior = params.behavior



        if (behavior is BottomSheetBehavior) {
            attrs.topView?.viewTreeObserver?.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    attrs.topView.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                    val stepReferenceGlobalRect = Rect()
                    attrs.topView.getGlobalVisibleRect(stepReferenceGlobalRect)
                    val dp = (26 * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
                    behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels - stepReferenceGlobalRect.bottom - dp
                }
            })
            //behavior.setPeekHeight(stepReferenceGlobalRect.height(), true)
            //behavior.isFitToContents = true
            //behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun render() = binding?.apply {
        authComponentTitle.text = context?.getString(attrs.title).orEmpty()
        authComponentForgotPassword.text = context?.getString(attrs.forgotText).orEmpty()

        authComponentLoginButton.isClickable = !authComponentEmail.hasTypedError()

        authComponentForgotPasswordButton.setAttributes(
            AttrsTitledButton(
                buttonText = context?.getString(attrs.forgotPassButtonText).orEmpty(),
                onClick = {
                    attrs.onForgotClick()
                    dismiss()
                }
            )
        )
        authComponentLoginButton.setAttributes(
            AttrsTitledButton(
                buttonText = context?.getString(attrs.buttonText).orEmpty(),
                onClick = {
                    if (authComponentEmail.hasTypedError().not()) {
                        attrs.onButtonClick(
                            authComponentEmail.getText(),
                            authComponentPassword.getText()
                        )
                        dismiss()
                    }
                }
            )
        )

        authComponentGoogleButton.setAttributes(
            AttrsTitledButton(
                buttonText = "Entra con Google",
                onClick = {
                    Toast.makeText(context, "Entrando con Google", Toast.LENGTH_SHORT).show()
                }
            )
        )

        authComponentFacebookButton.setAttributes(
            AttrsTitledButton(
                buttonText = "Entra con Facebook",
                onClick = {
                    Toast.makeText(context, "Entrando con Facebook", Toast.LENGTH_SHORT).show()
                }
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}