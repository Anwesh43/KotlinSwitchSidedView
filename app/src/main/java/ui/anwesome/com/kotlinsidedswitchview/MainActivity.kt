package ui.anwesome.com.kotlinsidedswitchview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.switchsidedview.SidedSwitchView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SidedSwitchView.create(this)
    }
}
