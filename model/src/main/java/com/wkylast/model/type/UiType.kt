package com.wkylast.model.type

enum class UiType {
    HORIZONTAL,
    VERTICAL,
    GRID;

    companion object {
        fun from(type: String?): UiType {
            return entries.first { it.name == type }
        }
    }
}