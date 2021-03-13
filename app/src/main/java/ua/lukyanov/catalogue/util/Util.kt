package ua.lukyanov.catalogue.util

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import ua.lukyanov.catalogue.R

fun View?.hideKeyboard(){
    val manager = this?.context?.getSystemService(Context.INPUT_METHOD_SERVICE)
    val viewBinder = this?.windowToken
    if (manager != null && viewBinder != null){
        val inputManager = manager as InputMethodManager
        inputManager.hideSoftInputFromWindow(viewBinder, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun setColorFilter(drawable: Drawable, color: Int) {
    drawable.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            colorFilter = BlendModeColorFilter(color, BlendMode.SRC_IN)
        } else {
            setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }
}

fun inflateImage(context: Context, imgUrl: String, imageView: ImageView){
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 4f
    circularProgressDrawable.centerRadius = 20f
    val progressColor = ContextCompat.getColor(context, R.color.colorPrimary)
    setColorFilter(circularProgressDrawable, progressColor)
    circularProgressDrawable.start()
    val options = RequestOptions()
        .placeholder(circularProgressDrawable)

    Glide.with(context)
        .load(imgUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(options)
        .error(R.drawable.ic_image_placeholder)
        .into(imageView)
}