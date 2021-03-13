package ua.lukyanov.catalogue.util

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration (context: Context, @DimenRes paddingLeft: Int) : RecyclerView.ItemDecoration() {

    private val attrs = intArrayOf(R.attr.listDivider)

    private var mDivider: Drawable? = null
    private var leftPadding: Int = 0

    init {
        leftPadding = context.resources.getDimensionPixelSize(paddingLeft)

        val styledAttributes: TypedArray = context.obtainStyledAttributes(attrs)
        mDivider = styledAttributes.getDrawable(0)
        styledAttributes.recycle()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        mDivider?.let{ divider ->
            val left = leftPadding
            val right = parent.width - parent.paddingRight
            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child: View = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top: Int = child.bottom + params.bottomMargin
                val bottom = top + divider.intrinsicHeight
                divider.setBounds(left, top, right, bottom)
                divider.draw(c)
            }
        }

    }

}