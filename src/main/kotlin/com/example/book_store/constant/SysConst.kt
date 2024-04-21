package com.example.book_store.constant

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

typealias RequestId = String

object SysConst {
    const val VERSION_1_0_0 = "1.0.0"
    const val CRLF = "\n"
    const val NOT_DEFINED = "?"
    const val UNKNOWN = "UNKNOWN"
    const val NOT_ASSIGNED = "NOT ASSIGNED"
    const val NULL = "null"
    const val ZERO_STRING = "0"
    const val EMPTY_STRING = ""
    const val BLANK_SYMBOL = " "
    const val DATETIME_MS_FORMAT = "dd.MM.yyyy HH:mm:ss.SSS"
    const val DATETIME_FORMAT = "dd.MM.yyyy HH:mm:ss"
    const val DATE_FORMAT = "dd.MM.yyyy"
    const val TIME_FORMAT = "HH:mm:ss"
    const val TIME_FORMAT_MS = "HH:mm:ss.SSS"
    const val APP_LOCALE = "ru"
    const val LOG_PKG_NAME = "root-service"
    val FORMAT_dd_MM_yyyy__HH_mm_ss = DateTimeFormatter.ofPattern(DATETIME_FORMAT)
    val FORMAT_dd_MM_yyyy = DateTimeFormatter.ofPattern(DATE_FORMAT)

    const val SSL = true
    const val NO_SSL = !SSL
    const val K9S_MODE = "server.k9sMode"
    val ONE_SECOND = 1.seconds.toJavaDuration()
    val TEN_SECONDS = 10.seconds.toJavaDuration()
    const val MILLIS_1000 = 1000L
    const val TIMEOUT_10000_MILLIS = 10000
    const val TIMEOUT_10000_MILLIS_LONG = 10000L
    val TIMEOUT_30_SEC = 30.seconds.toJavaDuration()
    val TIMEOUT_60_SEC = 60.seconds.toJavaDuration()
    val TIMEOUT_120_SEC = 120.seconds.toJavaDuration()
    const val ONE_ATTEMPT = 1
    const val TWO_ATTEMPTS = 2
    const val FIVE_ATTEMPTS = 5
    const val RESOURCE_FLD = "src/main/resources"
    const val DOCKER_FOLDER = "/opt/"
    val KOTLIN_VERSION = "KotlinVersion = ${KotlinVersion.CURRENT}, JavaVersion = ${Runtime.version()}"

    //==========================================================================
    const val LONG_ZERO = 0L
    const val LONG_ONE = 1L
    const val SERVICE_USER_ID = LONG_ONE
    val INT_NULL: Int? = null
    val LONG_NULL: Long? = null
    const val INT_10POW9 = 1000000000
    const val INTEGER_ZERO = 0
    const val INTEGER_ONE = 1
    val BIGDECIMAL_ZERO = BigDecimal.ZERO
    const val DOUBLE_ZERO: Double = 0.0
    val INTEGER_NULL: Int? = null
    val LOCALDATE_NULL: LocalDate? = null

    val LOCALDATETIME_NULL: LocalDateTime? = null

    val STRING_NULL: String? = null
    const val STRING_ONE: String = "1"
    const val STRING_ZERO: String = "0"
    const val STRING_TEN: String = "10"
    const val STRING_TRUE = "true"
    const val STRING_FALSE = "false"
    val BYTES_NULL: ByteArray? = null



    const val OC_OK = "OC_OK"
    const val INVALID_ENTITY_ATTR = "INVALID ENTITY ATTR"

    //==========================================================================
    val VOID_CLASS = Void::class.java
    val BIGDECIMAL_CLASS = BigDecimal::class.java
    val LOCALDATE_CLASS = LocalDate::class.java
    val LOCALDATETIME_CLASS = LocalDateTime::class.java
    val STRING_CLASS = String::class.java
    val BOOLEAN_CLASS = Boolean::class.java
    val INTEGER_CLASS = Int::class.java
    val LONG_CLASS = Long::class.java
}
