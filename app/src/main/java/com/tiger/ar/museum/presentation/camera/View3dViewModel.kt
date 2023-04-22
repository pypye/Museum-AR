package com.tiger.ar.museum.presentation.camera

import androidx.lifecycle.viewModelScope
import com.tiger.ar.museum.common.BaseUseCase
import com.tiger.ar.museum.common.BaseViewModel
import com.tiger.ar.museum.common.extension.failure
import com.tiger.ar.museum.common.extension.loading
import com.tiger.ar.museum.common.extension.onException
import com.tiger.ar.museum.common.extension.success
import com.tiger.ar.museum.common.usecase.FlowResult
import com.tiger.ar.museum.domain.model.Model3d
import com.tiger.ar.museum.domain.usecase.GetAllModelUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class View3dViewModel : BaseViewModel() {

    private var _get3dModelListState = MutableStateFlow(FlowResult.newInstance<List<Model3d>>())
    val get3dModelListState = _get3dModelListState.asStateFlow()

    fun get3dModelList() {
        viewModelScope.launch(Dispatchers.IO) {
            GetAllModelUseCase().invoke(BaseUseCase.VoidRequest())
                .onStart { _get3dModelListState.loading() }
                .onException { _get3dModelListState.failure(it) }
                .collect { _get3dModelListState.success(it) }
        }
    }
}