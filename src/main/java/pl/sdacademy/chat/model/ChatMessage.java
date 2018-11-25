package pl.sdacademy.chat.model;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    static final long serialVersionUID = 1L;
    private final String author;
    private final String message;

    public ChatMessage(String author, String message) {
        this.author = author;
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }
}
