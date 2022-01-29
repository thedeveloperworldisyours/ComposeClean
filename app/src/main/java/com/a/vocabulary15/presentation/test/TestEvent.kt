package com.a.vocabulary15.presentation.test

import com.a.vocabulary15.domain.model.Element

sealed class TestEvent {
    class ChangeMode(val mode: Int) : TestEvent()
    class ChangeText(val text: String) : TestEvent()
    class UpdateListItems(val newList: List<Element>) : TestEvent()
    class ChangeRight(val newValue: Int) : TestEvent()
    class ChangeWrong(val newWrongValue: Int) : TestEvent()
    class ChangeRandomNumber(val newNumber: Int) : TestEvent()
    class TestFinish(val newBoolean: Boolean) : TestEvent()
    class OpenChooseMode(val open: Boolean): TestEvent()
    object FetchElement: TestEvent()
}