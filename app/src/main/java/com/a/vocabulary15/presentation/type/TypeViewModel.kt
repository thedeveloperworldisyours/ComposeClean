package com.a.vocabulary15.presentation.type

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.a.vocabulary15.domain.usecases.GetElements
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TypeViewModel @Inject constructor(
    var getElements: GetElements
) : ViewModel() {

    //state
    var text by mutableStateOf("")

    //events
    fun onTextChanged(newString: String) {
        text = newString
    }


}