package org.dbs.validator


data class ErrorInfo(val error: String, val errorMsg: String) {

    companion object {
        fun create(error: String, errorMsg: String): ErrorInfo = ErrorInfo(error, errorMsg)

        @Deprecated(" use 'responseBody.errors.whenNoErrors' expression ")
        val noErrors = MutableCollection<ErrorInfo>::isEmpty
        val noCollectionsErrors = Collection<ErrorInfo>::isEmpty
    }
}
