package edu.ucaldas.back.models.chat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MessageData(
        long chatId,
        String content,
        @NotBlank(message = "Falto Escribir el email") @NotNull(message = "El email no puede ser null") @Email String sender) {

}
