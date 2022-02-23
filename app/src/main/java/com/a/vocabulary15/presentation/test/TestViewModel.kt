package com.a.vocabulary15.presentation.test

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.Statistics
import com.a.vocabulary15.domain.usecases.GetElements
import com.a.vocabulary15.domain.usecases.SetStatistics
import com.a.vocabulary15.presentation.common.ViewState
import com.a.vocabulary15.presentation.util.findFinalScoreColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    var getElements: GetElements,
    var setStatistics: SetStatistics,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val LIST_MODE = 0
        const val TEXT_MODE = 1
        const val HANGMAN_MODE = 2
    }

    private val viewStateMutable: MutableStateFlow<ViewState<*>> =
        MutableStateFlow(ViewState.Loading)
    val viewState = viewStateMutable.asStateFlow()
    private var getElementsJob: Job? = null

    //state
    private val _state = mutableStateOf(TestState())
    val state: State<TestState> = _state

    init {
        savedStateHandle.get<Int>("idGroup")?.let { currentID ->
            if (currentID != -1) {
                _state.value = state.value.copy(
                    idGroup = currentID
                )
            }
        }
        savedStateHandle.get<String>("elementName")?.let { name ->
            if (name != "") {
                _state.value = state.value.copy(
                    elementName = name
                )
            }
        }
    }

    //events
    fun onEvent(event: TestEvent) {
        when (event) {
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
            is TestEvent.FetchElement -> {
                getElements(state.value.idGroup)
            }
            is TestEvent.NextInTextMode -> {
                nextInTextMode()
            }
            is TestEvent.GenerateUnderscores -> {
                saveWordShowed(generateUnderscores(word = event.word))
            }
            is TestEvent.CheckLetter -> {
                checkLetter(event.letter)
            }
        }
    }

    fun getElements(idGroup: Int) = viewModelScope.launch(Dispatchers.IO) {
        getElementsJob?.cancel()
        getElementsJob = getElements.invoke(idGroup).onEach { elements ->
            initTest(elements)
            notifyPostState(elements)
        }.launchIn(viewModelScope)
    }

    private fun initTest(elementList: List<Element>) {
        if (elementList.isNotEmpty()) {
            onEvent(TestEvent.UpdateListItems(elementList))
            if (state.value.randomNumber == -1) {
                getNumber()
            }
            setCompletedElement(state.value.randomNumber)
        }
    }

    private fun getNumber() {
        onEvent(TestEvent.ChangeRandomNumber(state.value.elements.indices.random()))
        _state.value = state.value.copy(
            asked = MutableList(state.value.elements.size) { false }
        )
    }

    private fun setCompletedElement(elementCompleted: Int) {
        val elementsAsked = MutableList(state.value.elements.size) { false }
        for (i in state.value.asked.indices) {
            elementsAsked[i] = state.value.asked[i]
        }
        elementsAsked[elementCompleted] = true
        _state.value = state.value.copy(
            asked = elementsAsked
        )
    }

    private fun isTestFinished(): Boolean {
        for (item in state.value.asked) {
            if (!item) {
                return false
            }
        }
        return true
    }

    fun nextElement() {
        if (isTestFinished()) {
            onEvent(TestEvent.ChangeRight(state.value.right + 1))
            insertStatistics(generateStatistics())
            saveFinalScoreColor(findFinalScoreColor(state.value.right - state.value.wrong))
            onEvent(TestEvent.TestFinish(true))
            _state.value = state.value.copy(
                asked = MutableList(state.value.elements.size) { false }
            )
        } else {
            val possibleElement = (state.value.elements.indices).random()
            if (state.value.asked[possibleElement]) {
                nextElement()
            } else {
                onEvent(TestEvent.ChangeRight(state.value.right + 1))
                setCompletedElement(possibleElement)
                onEvent(TestEvent.ChangeRandomNumber(possibleElement))
                restartAgainHangman(possibleElement)
            }
        }
    }

    fun startAgain() {
        getNumber()
        setCompletedElement(state.value.randomNumber)
        restartAgainHangman(state.value.randomNumber)
        onEvent(TestEvent.ChangeRight(0))
        onEvent(TestEvent.ChangeWrong(0))
        onEvent(TestEvent.TestFinish(false))
    }

    private fun saveFinalScoreColor(color: Color) {
        _state.value = state.value.copy(
            finalScoreColor = color
        )
    }

    private fun generateStatistics(): Statistics {
        val calendar: Calendar = Calendar.getInstance()
        return Statistics(
            0,
            calendar.timeInMillis,
            state.value.right - state.value.wrong,
            state.value.idGroup
        )
    }

    fun insertStatistics(statistics: Statistics) = viewModelScope.launch(Dispatchers.IO) {
        setStatistics.invoke(statistics)
    }

    private fun notifyPostState(list: List<Element>) {
        viewStateMutable.value = ViewState.Success(list)
    }

    //Text mode
    private fun nextInTextMode() {
        if (state.value.elements[state.value.randomNumber].value == state.value.text) {
            onEvent(TestEvent.ChangeText(""))
            nextElement()
        } else {
            onEvent(TestEvent.ChangeWrong(state.value.wrong + 1))
        }
    }

    //Hangman mode
    fun generateUnderscores(word: String): List<String> {
        val wordShowed = mutableListOf<String>()
        word.forEach { char ->
            if (char != '/') {
                wordShowed.add("_")
            }
        }
        return wordShowed
    }

    private fun checkLetter(letter: String) {
        val word = state.value.elements[state.value.randomNumber].value
        val wordShowed = mutableListOf<String>()
        var success = false

        word.forEachIndexed { index, char ->
            if (char.toString() == letter) {
                wordShowed.add(letter)
                success = true
            } else {
                wordShowed.add(state.value.wordShowed[index])
            }
        }
        resultHangman(success, letter, wordShowed)
    }

    private fun resultHangman(success: Boolean, letter: String, wordShowed: List<String>) {
        if (isHangmanCompleted(wordShowed)) {
            saveWrongLetters(emptyList())
            nextElement()
        } else {
            if (success) {
                saveWordShowed(wordShowed)
            } else {
                var sameFail = false
                val newWrongLetters = mutableListOf<String>()
                for (index in state.value.wrongLetters.indices) {
                    if (letter == state.value.wrongLetters[index]) {
                        sameFail = true
                    }
                    newWrongLetters.add(state.value.wrongLetters[index])
                }
                if (!sameFail) {
                    saveHangmanStep(setHangmanImage(newWrongLetters.size))
                    newWrongLetters.add(letter)
                    saveWrongLetters(newWrongLetters)
                    onEvent(TestEvent.ChangeWrong(state.value.wrong + 1))
                    if (newWrongLetters.size == 7) {
                        saveWrongLetters(emptyList())
                        nextElement()
                        if (state.value.right > 0) {
                            onEvent(TestEvent.ChangeRight(state.value.right - 1))
                        }
                    }
                }
            }
        }
    }

    private fun isHangmanCompleted(wordShowed: List<String>): Boolean {
        for (letter in wordShowed) {
            if (letter == "_") {
                return false
            }
        }
        return true
    }

    private fun saveWordShowed(wordShowed: List<String>) {
        _state.value = state.value.copy(
            wordShowed = wordShowed
        )
    }

    private fun saveWrongLetters(newWrongLetters: List<String>) {
        _state.value = state.value.copy(
            wrongLetters = newWrongLetters
        )
    }

    private fun saveHangmanStep(step: Int) {
        _state.value = state.value.copy(
            hangmanStep = step
        )
    }

    private fun restartAgainHangman(possibleElement: Int) {
        if (state.value.levelMode == HANGMAN_MODE) {
            saveWordShowed(generateUnderscores(state.value.elements[possibleElement].value))
            saveHangmanStep(R.drawable.ic_hangman_start)
        }
    }

    fun setHangmanImage(step: Int): Int {
        var resource = 0
        when (step) {
            0 -> {
                resource = R.drawable.ic_hangman_2
            }
            1 -> {
                resource = R.drawable.ic_hangman_3
            }
            2 -> {
                resource = R.drawable.ic_hangman_4
            }
            3 -> {
                resource = R.drawable.ic_hangman_5
            }
            4 -> {
                resource = R.drawable.ic_hangman_6
            }
            5 -> {
                resource = R.drawable.ic_hangman_7
            }
            6 -> {
                resource = R.drawable.ic_hangman_final
            }
        }
        return resource
    }
}