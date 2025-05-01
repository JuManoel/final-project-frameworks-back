package edu.ucaldas.back.models.chat;

public record MessageData(
    long chatId,
    String content,
    long senderId
) {

}
