package com.a.vocabulary15.presentation.element

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.model.GroupElementStates
import com.a.vocabulary15.domain.usecases.DeleteElement
import com.a.vocabulary15.domain.usecases.DeleteGroupWithElements
import com.a.vocabulary15.domain.usecases.GetElement
import com.a.vocabulary15.domain.usecases.SetElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ElementsViewModel constructor(
    var getElement: GetElement,
    var setElement: SetElement,
    var deleteElement: DeleteElement,
    var delete: DeleteGroupWithElements
) : ViewModel() {
    private val mutable = MutableLiveData<GroupElementStates<*>>()
    val genericLiveData: LiveData<GroupElementStates<*>>
        get() = mutable

    var isDeleteAllOpen = mutableStateOf(false)
    var isDeleteElementOpen = mutableStateOf(false)
    private val expandedListMutable = MutableStateFlow(listOf<Int>())
    val expandedList: StateFlow<List<Int>> get() = expandedListMutable

    init {
        getElement()
    }

    private fun getElement() = viewModelScope.launch(Dispatchers.IO) {
        when (val groupElementStates = getElement.invoke()) {
            is GroupElementStates.Loading -> notifyLoadingStates()
            is GroupElementStates.Data<List<Element>> -> notifyGroupState(groupElementStates.data)
            is GroupElementStates.Error -> notifyErrorState(groupElementStates.error)
        }
    }

    fun deleteGroupWithElements(idGroup: Int) = viewModelScope.launch(Dispatchers.IO) {
        delete.invoke(idGroup)
    }

    fun setElement(element: Element) = viewModelScope.launch(Dispatchers.IO) {
        when (val groupElementStates = setElement.invoke(element)) {
            is GroupElementStates.Loading -> notifyLoadingStates()
            is GroupElementStates.Data<List<Element>> -> notifyGroupState(groupElementStates.data)
            is GroupElementStates.Error -> notifyErrorState(groupElementStates.error)
        }
    }

    fun deleteElement(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        when (val groupElementStates = deleteElement.invoke(id)) {
            is GroupElementStates.Loading -> notifyLoadingStates()
            is GroupElementStates.Data<List<Element>> -> notifyGroupState(groupElementStates.data)
            is GroupElementStates.Error -> notifyErrorState(groupElementStates.error)
        }
    }

    fun cardArrowClick(cardId: Int) {
        expandedListMutable.value = expandedListMutable.value.toMutableList().also { list ->
            if (list.contains(cardId)) {
                list.remove(cardId)
            } else {
                list.add(cardId)
            }
        }
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