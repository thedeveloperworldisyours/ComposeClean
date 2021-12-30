package com.a.vocabulary15.presentation.test

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.domain.usecases.GetElements
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    var getElements: GetElements
) : ViewModel() {

    companion object {
        const val LIST_MODE = 0
        const val TEXT_MODE = 1
    }

    private val mutable = MutableLiveData<GroupElementStates<*>>()
    val genericLiveData: LiveData<GroupElementStates<*>>
        get() = mutable

    private var elementsAsked = mutableListOf<Boolean>()

    //state
    private val _mode = mutableStateOf(-1)
    val mode: State<Int> = _mode

    private val _text = mutableStateOf("")
    val text: State<String> = _text

    private var _listItems = mutableStateOf(listOf<Element>())
    private val listItems: State<List<Element>> = _listItems

    private val _right = mutableStateOf(0)
    val right: State<Int> = _right

    private val _wrong = mutableStateOf(0)
    val wrong: State<Int> = _wrong

    private val _randomNumber = mutableStateOf(-1)
    val randomNumber:State<Int>  = _randomNumber

    private val _isTestFinishOpen = mutableStateOf(false)
    val isTestFinishOpen: State<Boolean> = _isTestFinishOpen

    private val _isChooseLevelOpen = mutableStateOf(false)
    val isChooseLevelOpen: State<Boolean> = _isChooseLevelOpen

    //events
    fun onEvent(event: TestEvent) {
        when(event) {
            is TestEvent.ChangeMode -> {
                _mode.value = event.mode
            }
            is TestEvent.ChangeText -> {
                _text.value = event.text
            }
            is TestEvent.UpdateListItems -> {
                _listItems.value = event.newList
            }
            is TestEvent.ChangeRight -> {
                _right.value = event.newValue
            }
            is TestEvent.ChangeWrong -> {
                _wrong.value = event.newWrongValue
            }
            is TestEvent.ChangeRandomNumber -> {
                _randomNumber.value = event.newNumber
            }
            is TestEvent.TestFinish -> {
                _isTestFinishOpen.value = event.newBoolean
            }
            is TestEvent.OpenChooseMode -> {
                _isChooseLevelOpen.value = event.open
            }
        }
    }

    fun getElements(idGroup: Int) = viewModelScope.launch(Dispatchers.IO) {
        when (val groupElementStates = getElements.invoke(idGroup)) {
            is GroupElementStates.Loading -> notifyLoadingStates()
            is GroupElementStates.Data<List<Element>> -> initTest(groupElementStates.data)
            is GroupElementStates.Error -> notifyErrorState(groupElementStates.error)
        }
    }

    private fun initTest(elementList: List<Element>) {
        onEvent(TestEvent.UpdateListItems(elementList))
        if (randomNumber.value == -1) {
            getNumber()
        }
        setCompletedElement(randomNumber.value)
        notifyGroupState(listItems.value)
    }

    private fun getNumber() {
        onEvent(TestEvent.ChangeRandomNumber(listItems.value.indices.random()))
        elementsAsked = MutableList(listItems.value.size) { false }
    }

    private fun setCompletedElement(elementCompleted: Int) {
        elementsAsked[elementCompleted] = true
    }

    private fun isTestFinished(): Boolean {
        for (item in elementsAsked) {
            if (!item) {
                return false
            }
        }
        return true
    }

    fun nextElement() {
        if (isTestFinished()) {
            onEvent(TestEvent.TestFinish(true))
            elementsAsked = MutableList(listItems.value.size) { false }
        } else {
            val possibleElement = (listItems.value.indices).random()
            if (elementsAsked[possibleElement]) {
                nextElement()
            } else {
                onEvent(TestEvent.ChangeRight(right.value + 1))
                setCompletedElement(possibleElement)
                onEvent(TestEvent.ChangeRandomNumber(possibleElement))
            }
        }
    }

    fun startAgain() {
        getNumber()
        setCompletedElement(randomNumber.value)
        onEvent(TestEvent.ChangeRight(0))
        onEvent(TestEvent.ChangeRight(0))
        onEvent(TestEvent.TestFinish(false))
    }

    private fun notifyLoadingStates() {
        mutable.postValue(GroupElementStates.Loading)
    }

    private fun notifyGroupState(elementList: List<Element>) {
        mutable.postValue(GroupElementStates.Data(elementList))
    }

    private fun notifyErrorState(error: Throwable) {
        mutable.postValue(GroupElementStates.Error(error))
    }
}