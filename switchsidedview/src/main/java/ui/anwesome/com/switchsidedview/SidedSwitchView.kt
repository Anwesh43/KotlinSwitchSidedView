package ui.anwesome.com.switchsidedview

/**
 * Created by anweshmishra on 27/03/18.
 */
import android.graphics.*
import android.content.*
import android.view.*
class SidedSwitchView(ctx : Context) : View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas : Canvas) {

    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}