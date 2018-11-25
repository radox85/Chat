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
            try (ObjectInputStream clientInput = new ObjectInputStream(client.getInputStream())) {
                processClientInput(clientInput);
            } catch (IOException e) {
                System.out.println("### Can not create input object. Server error ####");
            }
            chatLog.unRegister(client);
        }
    }

    private void processClientInput(ObjectInputStream clientInput) throws IOException {
        ChatMessage message;
        do {
            try {
                message = (ChatMessage) clientInput.readObject();
                if (!message.getMessage().equals("exit") || message.getMessage() != null) {
                    chatLog.acceptMessage(message);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("### Can not accept message. Server error ####");
                break;
            }
        } while (message.getMessage().equalsIgnoreCase("exit"));
    }
}
