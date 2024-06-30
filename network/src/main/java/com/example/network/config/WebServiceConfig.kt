package com.example.network.config

object WebServiceConfig {
    object Url {
        const val REMOTE_PROD_HOST = "http://<your-prod-domain>"
        const val REMOTE_DEV_HOST = "http://10.0.2.2" // ip local del emulador
        const val REMOTE_STAG_HOST = "http://<your-staging-domain>"
        const val LOCAL_HOST = "http://mock.api/"
    }

    object Timeout {
        const val CONNECT: Long = 120
    }
}