package com.ms.itec.presentation.mapper

import com.ms.itec.application.dto.request.ContentDto
import com.ms.itec.application.dto.request.ProspectModelDto
import com.ms.itec.application.dto.request.ProspectModelWithIdDto
import com.ms.itec.application.enums.Polos
import com.ms.itec.application.enums.Tag
import com.ms.itec.entity.Content
import com.ms.itec.domain.entity.ProspectModel
import jakarta.validation.Valid
import java.time.Instant
import java.util.*

class FromDto {
    fun toEntity(@Valid dto: ContentDto): Content{

        return Content(
            id = UUID.randomUUID().toString(),
            title = dto.title,
            description = dto.description,
            content = dto.content,
            background = dto.background,
            tag = Tag.valueOf(dto.tag.trim()),
            avgSalary = dto.avgSalary
        )
    }

    fun toEntity(@Valid dto: ProspectModelDto): ProspectModel {

        return ProspectModel(
            id = UUID.randomUUID().toString(),
            name = dto.name.trim(),
            email = dto.email.trim(),
            phone = formatBrazilianPhoneNumber(dto.phone),
            polo = if (dto.polo != null ) Polos.valueOf(dto.polo!!.trim()) else Polos.UNDECIDED,
            course = dto.course.trim(),
            cupom = dto.cupom,
            emailMarketing = dto.emailMarketing,
            contacted = false,
            ownerId = "",
            createdAt = Date.from(Instant.now()),
            updatedAt = Instant.now()
        )
    }

    fun toEntity(@Valid dto: ProspectModelWithIdDto): ProspectModel {

        return ProspectModel(
            id = dto.id,
            name = dto.name.trim(),
            email = dto.email.trim(),
            phone = formatBrazilianPhoneNumber(dto.phone),
            polo = if (dto.polo != null ) Polos.valueOf(dto.polo!!.trim()) else Polos.UNDECIDED,
            course = dto.course.trim(),
            cupom = dto.cupom,
            emailMarketing = dto.emailMarketing,
            contacted = false,
            ownerId = "",
            createdAt = Date.from(Instant.now()),
            updatedAt = Instant.now()
        )
    }


    fun formatBrazilianPhoneNumber(input: String): String {
        // Remove todos os caracteres não numéricos
        val cleanedInput = input.replace("\\D".toRegex(), "")

        // Verifica se o número tem o formato esperado (DDD + 9 dígitos)
        return if (cleanedInput.length >= 10 && cleanedInput.length <= 11) {
            // Extrai o DDD e o número de telefone
            val ddd = cleanedInput.substring(0, 2)
            val phoneNumber = cleanedInput.substring(2)

            // Formata o número
            return when {
                cleanedInput.length == 10 -> "(0$ddd) $phoneNumber" // Sem o nono dígito
                cleanedInput.length == 11 -> "($ddd) $phoneNumber" // Com o nono dígito
                else -> input
            }
        } else {
            input
        }
    }
}

