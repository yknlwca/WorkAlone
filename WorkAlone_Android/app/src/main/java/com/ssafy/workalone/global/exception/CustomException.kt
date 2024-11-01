package com.ssafy.workalone.global.exception

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
