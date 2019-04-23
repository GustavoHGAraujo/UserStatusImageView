package com.gustavogoma.android.userstatusimageview

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

class CuttableImageView : ImageView {
    private val childRect = RectF()
    private val radius: Float = context.resources.getDimension(R.dimen.default_margin)

    lateinit var childView: View
    lateinit var eraser: Paint

    var shouldCut = false

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        setupEraser()
    }

    private fun setupEraser() {
        eraser = Paint()
        eraser.color = ContextCompat.getColor(context, android.R.color.transparent)
        eraser.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        eraser.isAntiAlias = true
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!shouldCut) {
            return
        }

        childRect.set(
            childView.left.toFloat() - 1,
            childView.top.toFloat() - 1,
            childView.right.toFloat() + 1,
            childView.bottom.toFloat() + 1
        )

        canvas.drawRoundRect(childRect, radius, radius, eraser)
    }
}