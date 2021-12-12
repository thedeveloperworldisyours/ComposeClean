package com.a.vocabulary15.presentation.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.domain.usecases.GetElements
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TestViewModel constructor(
    var getElements: GetElements): ViewModel () {

    private val mutable = MutableLiveData<GroupElementStates<*>>()
    val genericLiveData: LiveData<GroupElementStates<*>>
        get() = mutable
    lateinit var listItems: List<Element>

    fun getElements(idGroup :Int) = viewModelScope.launch(Dispatchers.IO) {
        when (val groupElementStates = getElements.invoke(idGroup)) {
            is GroupElementStates.Loading -> notifyLoadingStates()
            is GroupElementStates.Data<List<Element>> -> {
                listItems = groupElementStates.data
                notifyGroupState(listItems)}
            is GroupElementStates.Error -> notifyErrorState(groupElementStates.error)
        }
    }

    fun nextElement() {
        notifyRandomNumber((listItems.indices).random())
    }

    private fun notifyLoadingStates() {
        mutable.postValue(GroupElementStates.Loading)
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