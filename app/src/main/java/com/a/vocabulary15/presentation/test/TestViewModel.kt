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
import com.a.vocabulary15.presentation.common.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val viewStateMutable: MutableStateFlow<ViewState<*>> = MutableStateFlow(ViewState.Loading)
    val viewState = viewStateMutable.asStateFlow()
    private var getElementsJob: Job? = null

    private var elementsAsked = mutableListOf<Boolean>()

    //state
    var idGroup by mutableStateOf(-1)
    var elementName by mutableStateOf("")
    private val _state = mutableStateOf(TestState())
    val state: State<TestState> = _state

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
                _state.value = state.value.copy(
                    levelMode = event.mode
                )
            }
            is TestEvent.ChangeText -> {
                _state.value = state.value.copy(
                    text = event.text
                )
            }
            is TestEvent.UpdateListItems -> {
                _state.value = state.value.copy(
                    elements = event.newList
                )
            }
            is TestEvent.ChangeRight -> {
                _state.value = state.value.copy(
                    right = event.newValue
                )
            }
            is TestEvent.ChangeWrong -> {
                _state.value = state.value.copy(
                    wrong = event.newWrongValue
                )
            }
            is TestEvent.ChangeRandomNumber -> {
                _state.value = state.value.copy(
                    randomNumber = event.newNumber
                )
            }
            is TestEvent.TestFinish -> {
                _state.value = state.value.copy(
                    isTestFinishOpen = event.newBoolean
                )
            }
            is TestEvent.OpenChooseMode -> {
                _state.value = state.value.copy(
                    isChooseLevelOpen = event.open
                )
            }
        }
    }

    fun getElements(idGroup: Int) = viewModelScope.launch(Dispatchers.IO) {
        notifyLoading()
        getElementsJob?.cancel()
        getElementsJob = getElements.invoke(idGroup).onEach { elements ->
            initTest(elements)
            notifyPostState(elements)
        }.launchIn(viewModelScope)
    }

    private fun initTest(elementList: List<Element>) {
        elementList
        onEvent(TestEvent.UpdateListItems(elementList))
        if (state.value.randomNumber == -1) {
            getNumber()
        }
        setCompletedElement(state.value.randomNumber)
    }

    private fun getNumber() {
        onEvent(TestEvent.ChangeRandomNumber(state.value.elements.indices.random()))
        elementsAsked = MutableList(state.value.elements.size) { false }
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
            elementsAsked = MutableList(state.value.elements.size) { false }
        } else {
            val possibleElement = (state.value.elements.indices).random()
            if (elementsAsked[possibleElement]) {
                nextElement()
            } else {
                onEvent(TestEvent.ChangeRight(state.value.right + 1))
                setCompletedElement(possibleElement)
                onEvent(TestEvent.ChangeRandomNumber(possibleElement))
            }
        }
    }

    fun startAgain() {
        getNumber()
        setCompletedElement(state.value.randomNumber)
        onEvent(TestEvent.ChangeRight(0))
        onEvent(TestEvent.ChangeWrong(0))
        onEvent(TestEvent.TestFinish(false))
    }

    private fun notifyLoading() {
        viewStateMutable.value = ViewState.Loading
    }

    private fun notifyPostState(list: List<Element>) {
        viewStateMutable.value = ViewState.Success(list)
    }
}