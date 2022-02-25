package com.a.vocabulary15.presentation.element

import com.a.vocabulary15.domain.model.Element

data class ElementState(
    val idGroup: Int = -1,
    val elementName: String = "",
    val isDeleteAllOpen: Boolean = false,
    val isDeleteElementOpen: Boolean = false,
    val isAddElementOpen: Boolean = false,
    val isDetailElementOpen: Boolean = false,
    val isEditElementOpen: Boolean = false,
    val inputValue :String = "",
    val inputValueLink :String = "",
    val inputValueLinkImage :String = "",
    val element: Element = Element(1,1,"","", ""),
    val isErrorLinkImage: Boolean = false,
    val isErrorLinkWord: Boolean = false
)
