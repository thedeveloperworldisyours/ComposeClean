package com.a.vocabulary15.presentation.element

import com.a.vocabulary15.domain.model.Element

sealed class ElementEvent{
    class OpenAddElementDialog(val open: Boolean) : ElementEvent()
    class OpenDetailElementDialog(val open: Boolean) : ElementEvent()
    class OpenEditElementDialog(val open: Boolean) : ElementEvent()
    class OpenDeleteAllDialog(val open: Boolean) : ElementEvent()
    class OpenDeleteElementDialog(val open: Boolean) : ElementEvent()
    class SetInputValue(val input: String): ElementEvent()
    class SetInputValueLink(val input: String): ElementEvent()
    class SetInputValueLinkImage(val input: String): ElementEvent()
    class SetElement(val element: Element): ElementEvent()
    object FetchElements: ElementEvent()
    class SetErrorLinkImage(val error: Boolean) : ElementEvent()
    class SetErrorLinkWord(val error: Boolean) : ElementEvent()
}
