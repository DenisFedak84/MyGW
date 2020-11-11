package com.example.mygw.test

interface Box {
    fun print()
}

class PaperBox(val x: Int) : Box {
    override fun print() {
        print("paper $x")
    }
}

class IronBox(val x: Int, val y: Int) : Box {
    override fun print() {
        val c = x + y
        print("iron $c")
    }
}

class Derived(b: Box) : Box by b {

}

class Delivery(b: Box) {

}
