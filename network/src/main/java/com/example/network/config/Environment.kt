package com.example.network.config

import com.example.network.config.WebServiceConfig.Url.REMOTE_DEV_HOST
import com.example.network.config.WebServiceConfig.Url.REMOTE_PROD_HOST
import com.example.network.config.WebServiceConfig.Url.REMOTE_STAG_HOST

sealed class Environment(val name: String, val url: String) {
    object Prod : Environment(name = "prod", url = REMOTE_PROD_HOST)
    object Dev : Environment(name = "dev", url = REMOTE_DEV_HOST)
    object Staging : Environment(name = "stag", url = REMOTE_STAG_HOST)
    object Dummy : Environment(name = "dummy", url = "http://mock.api/")
}
