package id.indocyber.common.binding_adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation


object ImageViewBindingAdapter {
    @BindingAdapter("custom:loadImage")
    @JvmStatic
    fun loadImage(imageView: ImageView, link: String?) {
        link?.let {
            Glide.with(imageView)
                .load(link)
                .into(imageView)
        }
    }

    @BindingAdapter("custom:loadImageCircled")
    @JvmStatic
    fun loadImageCircled(imageView: ImageView, link: String?) {
        link?.let {
            Glide.with(imageView)
                .load(link)
                .circleCrop()
                .into(imageView)
        }
    }

    @BindingAdapter("custom:blurImage")
    @JvmStatic
    fun blurImage(imageView: ImageView, link: String?) {
        link?.let {
            Glide.with(imageView).load(link)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(10, 3)))
                .into(imageView)
        }
    }

    @BindingAdapter("custom:roundedCornerImage")
    @JvmStatic
    fun roundedCornerImage(imageView: ImageView, link: String?) {
        link?.let {
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(16))
            Glide.with(imageView).load(link)
                .apply(requestOptions)
                .into(imageView)
        }
    }
}