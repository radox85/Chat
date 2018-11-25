package pl.sdacademy.chat.model;

import java.time.LocalDateTime;

//tez implementuje Serializable, bo ChatMessage go implementuje
public class DatedChatMessage extends ChatMessage {
    private final LocalDateTime receiveDate;

    public DatedChatMessage(ChatMessage chatMessage) {
        super(chatMessage.getAuthor(),chatMessage.getMessage());
        this.receiveDate = LocalDateTime.now();
    }

    public LocalDateTime getReciveDate() {
        return receiveDate;
    }
}
