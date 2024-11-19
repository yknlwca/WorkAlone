package com.ssafy.workalone.global.exception

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow

sealed class CustomException(message: String) : Exception(message) {
    class NetworkException(message: String) : CustomException(message)
    class ServerException(message: String) : CustomException(message)
    class ClientException(message: String) : CustomException(message)
    class UnknownException(message: String) : CustomException(message)
}

fun handleApiError(code: Int, message: String?): Nothing {
    throw when (code) {
        in 400..499 -> CustomException.ClientException("Client Error $code: ${message ?: "Unknown Error"}")
        in 500..599 -> CustomException.ServerException("Server Error $code: ${message ?: "Unknown Error"}")
        else -> CustomException.UnknownException("Unknown Error $code: ${message ?: "No Detail"}")
    }
}


fun handleException(exception: Throwable, errorMessageFlow: MutableStateFlow<String?>) {
    val message = when (exception) {
        is CustomException.NetworkException -> {
            Log.d("Network", "Network 에러: ${exception.message}")
            exception.message
        }
        is CustomException.ServerException -> {
            Log.d("Server", "Server 에러: ${exception.message}")
            exception.message
        }
        is CustomException.ClientException -> {
            Log.d("Client", "Client 에러: ${exception.message}")
            exception.message
        }
        is CustomException.UnknownException -> {
            Log.d("Unknown", "Unknown 에러: ${exception.message}")
            exception.message
        }
        else -> {
            Log.d("Unknown", "Unhandled error: ${exception.message}")
            "An unexpected error occurred."
        }
    }
    errorMessageFlow.value = message
}

