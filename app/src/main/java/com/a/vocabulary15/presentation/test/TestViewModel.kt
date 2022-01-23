package com.a.vocabulary15.presentation.test

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.usecases.GetElements
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    var getElements: GetElements,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val LIST_MODE = 0
        const val TEXT_MODE = 1
    }

    private val _state = mutableStateOf(TestState())
    val state: State<TestState> = _state
    private var getElementsJob: Job? = null

    private var elementsAsked = mutableListOf<Boolean>()

    //state
    var idGroup by mutableStateOf(-1)
    var elementName by mutableStateOf("")
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

    init {
        savedStateHandle.get<Int>("idGroup")?.let { currentID ->
            if (currentID != -1) {
                idGroup = currentID
                getElements(currentID)
            }
        }
        savedStateHandle.get<String>("elementName")?.let { name ->
            if (name != "") {
                elementName = name
            }
        }
    }

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
        getElementsJob?.cancel()
        getElementsJob = getElements.invoke(idGroup).onEach { elements ->
            initTest(elements)
            _state.value = state.value.copy(elements = elements)
        }.launchIn(viewModelScope)
    }

    private fun initTest(elementList: List<Element>) {
        onEvent(TestEvent.UpdateListItems(elementList))
        if (randomNumber.value == -1) {
            getNumber()
        }
        setCompletedElement(randomNumber.value)
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
        onEvent(TestEvent.ChangeWrong(0))
        onEvent(TestEvent.TestFinish(false))
    }
}