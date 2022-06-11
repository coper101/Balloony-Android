package com.darealreally.balloony.data

enum class Size {
    FiveInch {
        override val number = 5
    },
    NineInch {
        override val number = 9
    },
    ElevenInch {
        override val number = 11
    };
    abstract val number: Int
}