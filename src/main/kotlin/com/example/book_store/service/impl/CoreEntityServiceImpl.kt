package com.example.book_store.service.impl

import com.example.book_store.constant.SysConst.LOCALDATETIME_NULL
import com.example.book_store.models.CoreEntity
import com.example.book_store.models.enum.StatusEnum
import com.example.book_store.service.CoreEntityService
import com.example.book_store.service.GenerationService.Companion.generateEntityId
import org.springframework.stereotype.Service
import java.time.LocalDateTime.now

@Service
class CoreEntityServiceImpl(): CoreEntityService {
    override fun createCoreEntity(entityStatus: StatusEnum): CoreEntity = CoreEntity(
        coreEntityId = generateEntityId(),
        createDate = now(),
        deleteDate = LOCALDATETIME_NULL,
        status = entityStatus
    )
}