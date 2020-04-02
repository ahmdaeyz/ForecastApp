package dev.ahmdaeyz.forecastmvvm.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import dev.ahmdaeyz.forecastmvvm.R

@BindingAdapter("weatherCode")
fun getImage(img: ImageView,code: String?){
    code?.let {
        Glide.with(img.context)
            .load("http://openweathermap.org/img/wn/$code@2x.png")
            .fitCenter()
            .into(img)
    }

}
