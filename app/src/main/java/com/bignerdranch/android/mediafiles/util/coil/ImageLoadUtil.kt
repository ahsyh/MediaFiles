package com.bignerdranch.android.mediafiles.util.coil

import android.widget.ImageView
import coil.api.load
import java.io.File

fun loadImage(imageView : ImageView, filePath : String) : Unit {
    imageView.load(File(filePath))
}
