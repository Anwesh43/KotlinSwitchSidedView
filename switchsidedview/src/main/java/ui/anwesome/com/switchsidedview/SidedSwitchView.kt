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
    data class State(var prevScale : Float = 0f, var dir : Int = 0, var j : Int = 0) {
        val scales : Array<Float> = arrayOf(0f, 0f, 0f)
        fun update(stopcb : (Float) -> Unit) {
            scales[j] += 0.1f * dir
            if (Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += dir
                if (j == scales.size || j == -1) {
                    j -= dir
                    dir = 0
                    prevScale = scales[j]
                    stopcb(prevScale)
                }
            }
        }
        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0) {
                dir = 1 - 2 * prevScale.toInt()
                startcb()
            }
        }
    }
    data class Animator(var view : View, var animated : Boolean = false) {
        fun animate(updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex : Exception) {

                }
            }
        }
        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }
    data class SidedSwitch (var i : Int, val state : State = State()) {
        fun draw(canvas : Canvas, paint : Paint) {
            val w = canvas.width.toFloat()
            val h = canvas.height.toFloat()
            val edgeSide = Math.min(w, h) / 3
            paint.strokeWidth = edgeSide / 20
            canvas.save()
            canvas.translate(w / 2, h / 2)
            for (i in 0..5) {
                canvas.save()
                canvas.rotate(60f)
                paint.color = Color.WHITE
                canvas.drawLine(0f, 0f, edgeSide * state.scales[0], 0f, paint)
                paint.color = Color.parseColor("#3498db")
                canvas.drawLine(0f, 0f, edgeSide * state.scales[2], 0f, paint)
                canvas.drawCircle(0f, edgeSide * state.scales[2], (edgeSide/6) * state.scales[1], paint)
                canvas.restore()
            }
            canvas.restore()
        }
        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }
    data class Renderer(var view : SidedSwitchView) {
        val sidedSwitch : SidedSwitch = SidedSwitch(0)
        val animator : Animator = Animator(view)
        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            sidedSwitch.draw(canvas, paint)
            animator.animate {
                sidedSwitch.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            sidedSwitch.startUpdating {
                animator.start()
            }
        }
    }
}