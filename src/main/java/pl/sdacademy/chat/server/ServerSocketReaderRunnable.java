package pl.sdacademy.chat.server;

import pl.sdacademy.chat.model.ChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerSocketReaderRunnable implements Runnable {
    private final Socket client;
    private final ChatLog chatLog;

    public ServerSocketReaderRunnable(Socket client, ChatLog chatLog) {
        this.client = client;
        this.chatLog = chatLog;
    }

    @Override
    public void run() {
        ChatMessage message = null;
        if (chatLog.register(client)) {
            try (ObjectInputStream input = new ObjectInputStream(client.getInputStream())) {
                do {
                    try {
                        message = (ChatMessage) input.readObject();
                        chatLog.acceptMessage(message);
                    } catch (ClassNotFoundException e) {
                        break;
                    }
                } while (message.getMessage().equalsIgnoreCase("exit"));
            } catch (IOException e) {
                System.out.println("### Can not accept message on chat log. Server error ####");
            }
            chatLog.unRegister(client);
        }
    }
}
