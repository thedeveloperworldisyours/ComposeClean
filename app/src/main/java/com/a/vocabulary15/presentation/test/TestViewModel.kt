package com.a.vocabulary15.presentation.test

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    var mode by mutableStateOf(-1)
    var text by mutableStateOf("")
    private var listItems by mutableStateOf(listOf<Element>())
    var right by mutableStateOf(0)
    var wrong by mutableStateOf(0)
    var randomNumber by mutableStateOf(-1)
    var isTestFinishOpen by mutableStateOf(false)
    var isChooseLevelOpen by mutableStateOf(false)

    //events
    fun onModeChange(newMode: Int) {
        mode = newMode
    }

    fun onTextChanged(newString: String) {
        text = newString
    }

    private fun onListItemsChange(newList: List<Element>) {
        listItems = newList
    }

    private fun onRightChange(newRight: Int) {
        right = newRight
    }

    fun onWrongChange(newWrong: Int) {
        wrong = newWrong
    }

    private fun onRandomNumber(newNumber: Int) {
        randomNumber = newNumber
    }

    private fun onTestFinishOpen(open: Boolean) {
        isTestFinishOpen = open
    }

    fun getElements(idGroup: Int) = viewModelScope.launch(Dispatchers.IO) {
        when (val groupElementStates = getElements.invoke(idGroup)) {
            is GroupElementStates.Loading -> notifyLoadingStates()
            is GroupElementStates.Data<List<Element>> -> initTest(groupElementStates.data)
            is GroupElementStates.Error -> notifyErrorState(groupElementStates.error)
        }
    }

    private fun initTest(elementList: List<Element>) {
        onListItemsChange(elementList)
        if (randomNumber == -1) {
            getNumber()
        }
        setCompletedElement(randomNumber)
        notifyGroupState(listItems)
    }

    private fun getNumber() {
        onRandomNumber((listItems.indices).random())
        elementsAsked = MutableList(listItems.size) { false }
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
            onTestFinishOpen(true)
            elementsAsked = MutableList(listItems.size) { false }
        } else {
            val possibleElement = (listItems.indices).random()
            if (elementsAsked[possibleElement]) {
                nextElement()
            } else {
                onRightChange(right + 1)
                setCompletedElement(possibleElement)
                onRandomNumber(possibleElement)
            }
        }
    }

    fun startAgain() {
        getNumber()
        setCompletedElement(randomNumber)
        onRightChange(0)
        onWrongChange(0)
        onTestFinishOpen(false)
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