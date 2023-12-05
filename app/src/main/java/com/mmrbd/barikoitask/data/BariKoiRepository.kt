package com.mmrbd.barikoitask.data

import com.mmrbd.barikoitask.data.model.NearByResponse
import com.mmrbd.barikoitask.data.remote.BariKoiApi
import com.mmrbd.barikoitask.utils.network.ApiResult
import com.mmrbd.barikoitask.utils.network.Failure
import com.mmrbd.barikoitask.utils.network.getErrorTypeByHTTPCode
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.net.UnknownHostException

class BariKoiRepository(
    private val apiService: BariKoiApi
) {
    fun getNearByData(apiKey: String, lat: String, lon: String): Flow<ApiResult<NearByResponse>> =
        callbackFlow {
            try {
                val response = apiService.getNearByData(apiKey, lat, lon)
                if (response.body() != null && response.isSuccessful) {
                    trySend(ApiResult.Success(response.body()!!))
                } else {
                    trySend(
                        ApiResult.Error(
                            getErrorTypeByHTTPCode(response.code()), null
                        )
                    )
                }

            } catch (exception: Exception) {
                when (exception) {
                    is UnknownHostException -> {
                        trySend(ApiResult.Error((Failure.HTTP.NetworkConnection)))
                    }

                    else -> {
                        trySend(ApiResult.Error(Failure.Exception(exception), null))
                    }
                }
            }

            awaitClose()
        }
}