package com.mmrbd.barikoitask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmrbd.barikoitask.data.BariKoiRepository
import com.mmrbd.barikoitask.data.model.NearByResponse
import com.mmrbd.barikoitask.utils.network.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainViewModel(
    private val repository: BariKoiRepository
) : ViewModel() {

    private val _nearByState = MutableStateFlow<ApiResult<NearByResponse>>(ApiResult.Loading())

    val nearByState: StateFlow<ApiResult<NearByResponse>> = _nearByState

    fun getNearByData(apiKey: String, lat: String, long: String) =
        viewModelScope.launch {
            repository.getNearByData(apiKey, lat, long).collect {
                _nearByState.emit(it)
            }
        }

}