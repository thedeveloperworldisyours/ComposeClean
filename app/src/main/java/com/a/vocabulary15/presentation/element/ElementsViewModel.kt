package com.a.vocabulary15.presentation.element

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    //State
    private val _state = mutableStateOf(ElementState())
    val state: State<ElementState> = _state

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
    fun onEvent(event: ElementEvent) {
        when(event) {
            is ElementEvent.OpenAddElementDialog -> {
                _state.value = state.value.copy(
                    isAddElementOpen = event.open
                )
            }
            is ElementEvent.OpenEditElementDialog -> {
                _state.value = state.value.copy(
                    isEditElementOpen = event.open
                )
            }
            is ElementEvent.OpenDeleteAllDialog -> {
                _state.value = state.value.copy(
                    isDeleteAllOpen = event.open
                )
            }
            is ElementEvent.OpenDeleteElementDialog -> {
                _state.value = state.value.copy(
                    isDeleteElementOpen = event.open
                )
            }
            is ElementEvent.OpenDetailElementDialog -> {
                _state.value = state.value.copy(
                    isDetailElementOpen = event.open
                )
            }
            is ElementEvent.SetInputValue -> {
                _state.value = state.value.copy(
                    inputValue = event.input
                )
            }
            is ElementEvent.SetInputValueLink -> {
                _state.value = state.value.copy(
                    inputValueLink = event.input
                )
            }
            is ElementEvent.SetInputValueLinkImage -> {
                _state.value = state.value.copy(
                    inputValueLinkImage = event.input
                )
            }
            is ElementEvent.SetElement -> {
                _state.value = state.value.copy(
                    element = event.element
                )
            }
            is ElementEvent.FetchElements -> {
                getElements(state.value.idGroup)
            }
        }
    }
    fun getElements(idGroup: Int) = viewModelScope.launch(Dispatchers.IO) {
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

    private fun notifyPostState(list: List<Element>) {
        viewStateMutable.value = ViewState.Success(list)
    }
}