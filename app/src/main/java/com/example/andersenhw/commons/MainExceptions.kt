package com.example.andersenhw.commons

sealed class MainExceptions {
    class GeneralError : Exception()
    class NetworkConnection : Exception()
    class ServerError : Exception()
}