package com.example.network.interceptor

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.net.URI
import java.util.Locale

class LocalInterceptor(private val context: Context?) : Interceptor {
    private val tag = this.javaClass.simpleName

    override fun intercept(chain: Interceptor.Chain): Response {
        Thread.sleep(DEFAULT_SLEEP_TIME)
        val uri = chain.request().url.toUri()
        val httpMethod = chain.request().method.lowercase(Locale.getDefault())
        logHttpMethodAndUri(httpMethod, uri)
        logRequestBody(chain)
        val fileName = getFileNameWithHttpMethodAndExtension(chain, httpMethod)
        val filePath = getFilePathWithHost(uri, fileName)
        logFilePath(filePath)

        val jsonBodyText = getJsonBodyText(filePath)
        logResponseBody(jsonBodyText)

        return makeResponseBuilder(jsonBodyText, chain).build()
    }

    private fun logHttpMethodAndUri(method: String, uri: URI) {
        Log.d(tag, "--> Request url: [${method.lowercase(Locale.getDefault())}]$uri")
    }

    private fun logRequestBody(chain: Interceptor.Chain) {
        Log.d(tag, "Request Body: ${bodyToString(chain.request())}")
    }

    private fun bodyToString(request: Request): String {
        val requestBuilder = request.newBuilder().build()
        val buffer = Buffer()
        requestBuilder.body?.writeTo(buffer)
        return buffer.readUtf8()
    }

    private fun getFileNameWithHttpMethodAndExtension(
        chain: Interceptor.Chain,
        httpMethod: String
    ): String {
        val fileName = chain.request().url.pathSegments.last()
        return fileName + "_$httpMethod" + FILE_EXTENSION
    }

    private fun getFilePathWithHost(uri: URI, fileName: String): String {
        val path = uri.path.substring(0, uri.path.lastIndexOf('/') + 1)
        return uri.host + path.lowercase(Locale.getDefault()) + fileName
    }

    private fun logFilePath(filePath: String) {
        Log.d(tag, "Read data from file : $filePath")
    }

    private fun getJsonBodyText(filePath: String): String = context
        ?.assets
        ?.open(filePath)
        ?.bufferedReader()
        ?.use { it.readText() }.orEmpty()

    private fun logResponseBody(jsonBodyText: String) {
        Log.d(tag, "Response: $jsonBodyText")
    }

    private fun makeResponseBuilder(
        jsonBodyText: String,
        chain: Interceptor.Chain
    ) = Response.Builder()
        .code(SUCCESS_CODE)
        .message(jsonBodyText)
        .request(chain.request())
        .protocol(Protocol.HTTP_1_0)
        .body(
            jsonBodyText
                .toByteArray()
                .toResponseBody(CONTENT_TYPE_APPLICATION_JSON.toMediaTypeOrNull())
        )
        .addHeader(CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON)

    companion object {
        private const val DEFAULT_SLEEP_TIME = 1000L

        private const val FILE_EXTENSION = ".json"
        private const val CONTENT_TYPE = "content-type"
        private const val CONTENT_TYPE_APPLICATION_JSON = "application/json;charset=UTF-8"
        private const val SUCCESS_CODE = 200
    }
}