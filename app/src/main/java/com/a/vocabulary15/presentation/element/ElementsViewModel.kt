package com.a.vocabulary15.presentation.element

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.domain.usecases.*
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
class ElementsViewModel @Inject constructor(
    var getElements: GetElements,
    var setElement: SetElement,
    var deleteElement: DeleteElement,
    var editElement: EditElement,
    var delete: DeleteGroupWithElements,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val viewStateMutable: MutableStateFlow<ViewState<*>> = MutableStateFlow(ViewState.Loading)
    val viewState = viewStateMutable.asStateFlow()

    private var getElementsJob: Job? = null

    //States
    var idGroup by mutableStateOf(-1)
    var elementName by mutableStateOf("")
    var isDeleteAllOpen by mutableStateOf(false)
    var isDeleteElementOpen by mutableStateOf(false)
    var isAddElementOpen by mutableStateOf(false)
    var isDetailElementOpen by mutableStateOf(false)
    var isEditElementOpen by mutableStateOf(false)
    var item = Element(1,1,"","", "")

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

    fun getElements(idGroup :Int) = viewModelScope.launch(Dispatchers.IO) {
        notifyLoading()
        getElementsJob?.cancel()
        getElementsJob = getElements.invoke(idGroup).onEach { elements ->
            notifyPostState(elements)
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

    private fun notifyLoading() {
        viewStateMutable.value = ViewState.Loading
    }

    private fun notifyPostState(list: List<Element>) {
        viewStateMutable.value = ViewState.Success(list)
    }
}