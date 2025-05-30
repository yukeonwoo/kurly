package com.wkylast.model.type

enum class UiType(val value: String) {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical"),
    GRID("grid");

    companion object {
        fun from(type: String?): UiType {
            return entries.first { it.value == type }
        }
    }
}