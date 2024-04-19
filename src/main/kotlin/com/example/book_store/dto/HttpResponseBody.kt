package com.example.book_store.dto

import com.example.book_store.constant.RequestId
import com.example.book_store.constant.SysConst.INVALID_ENTITY_ATTR
import com.example.book_store.constant.SysConst.OC_OK
import com.example.book_store.constant.SysConst.STRING_NULL
import com.fasterxml.jackson.annotation.JsonIgnore
import org.dbs.validator.ErrorInfo
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import java.io.Serializable

interface ResponseDto : Dto
sealed interface Dto : Serializable

abstract class HttpResponseBody<T : ResponseDto>(private val requestId: RequestId) : Serializable {
    lateinit var responseCode: String
    lateinit var message: String
    var error = STRING_NULL
    var responseEntity: T? = null
    val errors: MutableCollection<ErrorInfo> = mutableListOf()

    @JsonIgnore
    val contentType: MediaType = APPLICATION_JSON

    //==================================================================================================================
    @JsonIgnore
    fun haveErrors() =
        (responseCode != if (::responseCode.isInitialized) responseCode else OC_OK) || (errors.isNotEmpty())

    @JsonIgnore
    fun haveNoErrors() = !haveErrors()

    private fun addErrorInfo(errorInfo: ErrorInfo) = errors.run {
        add(errorInfo)


        if (!::message.isInitialized) {
            message = errorInfo.errorMsg
        }

        error?.let {
            // message is not empty
        } ?: {
            error = errorInfo.errorMsg
            responseEntity = null
        }
        size
    }

    fun addErrorInfo(errorCode: String, error: String, errorMsg: String) =
        addErrorInfo(ErrorInfo.create(error, errorMsg)).also {
            if (!::responseCode.isInitialized) {
                responseCode = errorCode
            } else if (responseCode == OC_OK) {
                responseCode = errorCode
                message = errorMsg
            }
        }

    private fun setErrorMessage(errorMsg: String) = error?.let {} ?: { error = errorMsg }

    fun assignErrors(errors: Collection<ErrorInfo>) {
        this.errors.addAll(errors)
        errors.let { e ->
            if (!e.isEmpty()) {
                responseCode = INVALID_ENTITY_ATTR

                if (!::message.isInitialized) {
                    message = e.stream().findFirst().orElseThrow().errorMsg.uppercase()
                }

                setErrorMessage(message)


            }
        }
    }

    fun toString2() =
        "code='${responseCode}', message='$message', error='$error', errors=$errors, " +
                "${javaClass.simpleName}($responseEntity)"

    companion object {
        @java.io.Serial
        private const val serialVersionUID: Long = 1000L
    }
}
