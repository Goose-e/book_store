package com.example.book_store.config

import com.example.book_store.models.enum.converter.*
import org.springframework.context.annotation.Configuration
import org.springframework.format.support.FormattingConversionService
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class EnumConfig : WebMvcConfigurationSupport() {
    override fun mvcConversionService(): FormattingConversionService {
        val f = super.mvcConversionService()
        f.addConverter(StatusRefConverter())
        f.addConverter(StatusRefEnumConverter())
        f.addConverter(GenreRefConverter())
        f.addConverter(GenreRefEnumConverter())
        f.addConverter(RoleRefConverter())
        f.addConverter(RoleRefEnumConverter())
        return f
    }
}