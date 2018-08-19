package com.brainasaservice.reviewbrowser.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.brainasaservice.reviewbrowser.util.TextViewCompoundDrawableMatcher.Companion.TOP
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

@Suppress("ReturnCount")
class TextViewCompoundDrawableMatcher internal constructor(
        private val expectedId: Int,
        private val index: Int
) : TypeSafeMatcher<View>(View::class.java) {
    private var resourceName: String? = null

    override fun matchesSafely(imageView: View): Boolean {
        if (imageView !is TextView) {
            return false
        }

        if (expectedId == EMPTY) {
            return imageView.compoundDrawables[index] == null
        }

        if (expectedId == ANY) {
            return imageView.compoundDrawables[index] != null
        }

        val resources = imageView.context.resources
        val expectedDrawable = ContextCompat.getDrawable(imageView.context, expectedId)
        resourceName = resources.getResourceEntryName(expectedId)

        if (expectedDrawable == null) {
            return false
        }

        val bitmap = getBitmap(imageView.compoundDrawables[index])
        val otherBitmap = getBitmap(expectedDrawable)
        return bitmap.sameAs(otherBitmap)
    }

    private fun getBitmap(drawable: Drawable?): Bitmap {
        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    override fun describeTo(description: Description) {
        description.appendText("with drawable from resource id: ")
        description.appendValue(expectedId)
        if (resourceName != null) {
            description.appendText("[")
            description.appendText(resourceName)
            description.appendText("]")
        }
    }

    companion object {
        internal const val EMPTY = -1
        internal const val ANY = -2


        internal const val START = 0
        internal const val TOP = 1
        internal const val END = 2
        internal const val BOTTOM = 3
    }
}

fun withTopDrawable(resourceId: Int): Matcher<View> {
    return TextViewCompoundDrawableMatcher(resourceId, TOP)
}
