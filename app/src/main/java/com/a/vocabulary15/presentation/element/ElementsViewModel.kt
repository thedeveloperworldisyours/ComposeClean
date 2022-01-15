package com.a.vocabulary15.presentation.element

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElementsViewModel @Inject constructor(
    var getElements: GetElements,
    var setElement: SetElement,
    var deleteElement: DeleteElement,
    var editElement: EditElement,
    var delete: DeleteGroupWithElements
) : ViewModel() {

    private val _state = mutableStateOf(ElementState())
    val state: State<ElementState> = _state
    private var getElementsJob: Job? = null

    //States
    var isDeleteAllOpen by mutableStateOf(false)
    var isDeleteElementOpen by mutableStateOf(false)
    var isAddElementOpen by mutableStateOf(false)
    var isDetailElementOpen by mutableStateOf(false)
    var isEditElementOpen by mutableStateOf(false)
    var item = Element(1,1,"","", "")

    fun getElements(idGroup :Int) = viewModelScope.launch(Dispatchers.IO) {
        getElementsJob?.cancel()
        getElementsJob = getElements.invoke(idGroup).onEach { elements ->
            _state.value = state.value.copy(elements = elements)
        }.launchIn(viewModelScope)
    }

    fun deleteGroupWithElements(idGroup: Int) = viewModelScope.launch(Dispatchers.IO) {
        delete.invoke(idGroup)
    }

    fun setElement(element: Element) = viewModelScope.launch(Dispatchers.IO) {
        setElement.invoke(element)
    }

    fun editElement(element: Element) = viewModelScope.launch (Dispatchers.IO){
        editElement.invoke(element)
    }

    fun deleteElement(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        deleteElement.invoke(id)
    }

}