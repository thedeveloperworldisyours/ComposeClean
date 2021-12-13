package com.a.vocabulary15.presentation.test

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.domain.usecases.GetElements
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestViewModel constructor(
    var getElements: GetElements
) : ViewModel() {

    private val mutable = MutableLiveData<GroupElementStates<*>>()
    val genericLiveData: LiveData<GroupElementStates<*>>
        get() = mutable

    var isTestFinishOpen = mutableStateOf(false)
    lateinit var listItems: List<Element>
    var elementsAsked = mutableListOf<Boolean>()

    fun getElements(idGroup: Int) = viewModelScope.launch(Dispatchers.IO) {
        when (val groupElementStates = getElements.invoke(idGroup)) {
            is GroupElementStates.Loading -> notifyLoadingStates()
            is GroupElementStates.Data<List<Element>> -> initTest(groupElementStates.data)
            is GroupElementStates.Error -> notifyErrorState(groupElementStates.error)
        }
    }

    private fun initTest(elementList: List<Element>) {
        listItems = elementList
        elementsAsked = MutableList(listItems.size) { false }
        notifyGroupState(listItems)
    }

    fun setFirstRandomNumber(firstRandomNumber: Int) {
        setCompletedElement(firstRandomNumber)
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
            isTestFinishOpen.value = true
            elementsAsked = MutableList(listItems.size) { false }
            notifyTestFinished()
        } else {
            val possibleElement = (listItems.indices).random()
            if (elementsAsked[possibleElement]) {
                nextElement()
            } else {
                setCompletedElement(possibleElement)
                notifyRandomNumber(possibleElement)
            }
        }
    }

    private fun notifyLoadingStates() {
        mutable.postValue(GroupElementStates.Loading)
    }

    private fun notifyTestFinished() {
        mutable.postValue(GroupElementStates.Data(true))
    }

    private fun notifyRandomNumber(randomNumber: Int) {
        mutable.postValue(GroupElementStates.Data(randomNumber))
    }

    private fun notifyGroupState(elementList: List<Element>) {
        mutable.postValue(GroupElementStates.Data(elementList))
    }

    private fun notifyErrorState(error: Throwable) {
        mutable.postValue(GroupElementStates.Error(error))
    }
}