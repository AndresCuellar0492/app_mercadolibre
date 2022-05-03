package com.example.appmeli.util

class Constants {

    companion object {
        const val BASE_URL = "https://api.mercadolibre.com/sites/MLA/"
        const val SEARCH_URL = "search"
        const val MESSAGE_ERROR_REQUEST =
            "Ocurrió un error al procesar la solicitud, intenta nuevamente por favor..."
        const val MESSAGE_ERROR_NETWORK =
            "Revisa tu conexion a internet e intenta nuevamente por favor..."
    }
}

class Verify {
    companion object {
        var ENABLE = true
    }
}