package com.example.uicomponents.ui.groupcomponent.photopicker

import android.app.Activity
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.res.ColorStateList
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager.findFragment
import com.example.uicomponents.R
import com.example.uicomponents.databinding.UiComponentPhotoPickerBinding
import com.github.dhaval2404.imagepicker.ImagePicker

class PhotoPickerComponent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: UiComponentPhotoPickerBinding? = null
    private var selectedImage: Uri? = null

    private val activityResultLauncher =
        (context as? FragmentActivity)?.activityResultRegistry?.register(
            "photoPicker",
            StartActivityForResult()
        ) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> setImage(result)
                ImagePicker.RESULT_ERROR -> showErrorToast(result)
                else -> showCancelledToast()
            }
        }

    init {
        if (binding == null) {
            val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiComponentPhotoPickerBinding.inflate(inflater, this)
        }
        setupButton()
        setPickPhotoListener()
    }

    fun getSelectedImage() = selectedImage

    fun setSelectedImage(url: Uri) = binding?.apply {
        selectedImage = url
        hideHints()
        photoPickerComponentImage.setImageURI(url)
    }

    private fun setImage(result: ActivityResult) = binding?.apply {
        hideHints()
        val uri = result.data?.data
        selectedImage = uri
        photoPickerComponentImage.setImageURI(uri)
    }

    private fun hideHints() = binding?.apply {
        photoPickerComponentHint.isVisible = false
        photoPickerComponentIcon.isVisible = false
    }

    private fun showErrorToast(result: ActivityResult) {
        Toast.makeText(context, ImagePicker.getError(result.data), Toast.LENGTH_SHORT)
            .show()
    }

    private fun showCancelledToast() {
        Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
    }

    private fun setupButton() = binding?.apply {
        photoPickerComponentFloatingButton.imageTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ui_white))
        photoPickerComponentFloatingButton.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(context, R.color.ui_brand_primary)
        )
    }

    private fun setPickPhotoListener() = binding?.apply {
        photoPickerComponentFloatingButton.setOnClickListener {
            pickPhoto(it)
        }
        photoPickerComponentImage.setOnClickListener {
            pickPhoto(it)
        }
    }

    private fun pickPhoto(view: View) {
        ImagePicker.with(findFragment<Fragment>(view))
            .crop()
            .compress(PHOTO_MAX_SIZE)
            .maxResultSize(PHOTO_WIDTH, PHOTO_HEIGHT)
            .createIntent { intent ->
                activityResultLauncher?.launch(intent)
            }
    }

    companion object {
        const val PHOTO_MAX_SIZE = 1024
        const val PHOTO_WIDTH = 1080
        const val PHOTO_HEIGHT = 1080
    }
}
