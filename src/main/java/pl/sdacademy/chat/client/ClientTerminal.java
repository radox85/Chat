package pl.sdacademy.chat.client;

import pl.sdacademy.chat.model.ChatMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTerminal implements Runnable {

    private final Socket connectionToServer;

    public ClientTerminal() throws IOException {
        connectionToServer = new Socket("192.168.8.4", 5567);
    }

    @Override
    public void run() {
        String message = null;
        //w bloku try with resources try()
        try (OutputStream streamToServer = connectionToServer.getOutputStream()) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(streamToServer);
            //pytamy raz uzytkownika jaki ma nickname
            //w petli pobieramy tekst do wyslania
            System.out.print("Give a nickname: ");
            String nickName = new Scanner(System.in).nextLine();

            do {
                System.out.print("> ");
                message = new Scanner(System.in).nextLine();
                //tworzymy nowy objekt chatmessage
                ChatMessage messageToSend = new ChatMessage(nickName, message);
                //wysylamy go do serwera - wpisujemy do OOS
                objectOutputStream.writeObject(messageToSend);
                objectOutputStream.flush();
            } while (!message.equalsIgnoreCase("exit"));


        } catch (IOException e) {
            System.out.println("Server not response");
            e.printStackTrace();
        }

    }
}
