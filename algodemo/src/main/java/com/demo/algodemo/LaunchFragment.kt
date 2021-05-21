package com.demo.algodemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class LaunchFragment(var context:Context) {

    fun callFragment(){
        val frag=BottomFragment()
        frag.show((context as AppCompatActivity).supportFragmentManager,"")
    }
}