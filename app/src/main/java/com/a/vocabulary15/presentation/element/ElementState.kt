package com.a.vocabulary15.presentation.element

data class ElementState(
    var isDeleteAllOpen: Boolean = false,
    var isDeleteElementOpen: Boolean = false,
    var isAddElementOpen: Boolean = false,
    var isDetailElementOpen: Boolean = false,
    var isEditElementOpen: Boolean = false,
    val inputValue :String = "",
    val inputValueLink :String = "",
    val inputValueLinkImage :String = "",
)
