package com.gustavogoma.android.userstatusimageview

import android.content.Context
import android.graphics.*
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView

class UserStatusImageView : ConstraintLayout {

    enum class Status {
        OFFLINE, ONLINE
    }

    private lateinit var imageView: CuttableImageView
    private lateinit var statusContainer: FrameLayout
    private lateinit var statusView: ImageView

    var status: Status = Status.OFFLINE
        set(value) {
            field = value
            update()
        }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        View.inflate(context, R.layout.view_user_status_image, this)

        imageView = findViewById(R.id.image)
        statusView = findViewById(R.id.status)
        statusContainer = findViewById(R.id.status_container)

        imageView.childView = statusContainer

        if (attrs == null) {
            return
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserStatusImageView, 0, 0)

        status = Status.values()[typedArray.getInteger(R.styleable.UserStatusImageView_status, 0)]

        typedArray.getDrawable(R.styleable.UserStatusImageView_image_background)?.let { drawable ->
            imageView.setImageDrawable(drawable)
        }

        typedArray.recycle()
    }

    private fun update() {
        imageView.shouldCut = status != Status.OFFLINE

        statusContainer.visibility = when (status) {
            Status.OFFLINE -> View.GONE
            Status.ONLINE -> View.VISIBLE
        }
    }

    fun setImageBitmap(bitmap: Bitmap?) {
        imageView.setImageBitmap(bitmap)
    }

}