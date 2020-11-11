package com.example.mygw.test

import com.example.mygw.activity.TestActivity
import kotlin.reflect.KProperty

class Delegate {
    operator fun getValue(testActivity: TestActivity, property: KProperty<*>): String {
        TODO("Not yet implemented")
    }

    operator fun setValue(testActivity: TestActivity, property: KProperty<*>, s: String) {
        TODO("Not yet implemented")
    }


}