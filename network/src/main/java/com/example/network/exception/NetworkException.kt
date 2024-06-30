package com.example.network.exception

import java.io.IOException

sealed class NetworkException : IOException() {
    object Error403Exception : NetworkException()
    object GeneralNetworkException : NetworkException()
    object NoInternetConnectionException : NetworkException()
}