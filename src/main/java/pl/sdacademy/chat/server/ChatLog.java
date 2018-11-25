package pl.sdacademy.chat.server;

import pl.sdacademy.chat.model.ChatMessage;
import pl.sdacademy.chat.model.DatedChatMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatLog {
    private Map<Socket, ObjectOutputStream> registeredClients;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public ChatLog() {
        registeredClients = new ConcurrentHashMap<>();
    }

    public boolean register(Socket client) {
        try {
            ObjectOutputStream streamToClient = new ObjectOutputStream(client.getOutputStream());
            registeredClients.put(client, streamToClient);
            return true;
        } catch (IOException e) {
            System.out.println("### Someone tried to connect, but was rejected ###");
            return false;
        }
        //zapisac clienta w kolekcji wzytkich klientow
    }

    public boolean unRegister(Socket client) {
        ObjectOutputStream oos = registeredClients.get(client);
        if (oos != null) {
            try {
                //zamknij strumien
                registeredClients.get(client).close();
            } catch (IOException e) {}
            registeredClients.remove(client);
            return true;
        }
        return false;
    }

    public void acceptMessage(ChatMessage message) {
        //przekonwertowac chat message na Dated chat message
        DatedChatMessage datedChatMessage = new DatedChatMessage(message);
        //wypisz wiadomosc w formacie <Data> <Author> : <Message>
        printMessage(datedChatMessage);
        //wyslac DatedChatM do wszytskich uzytkownikow
       updateClients(datedChatMessage);
    }

    private void updateClients(DatedChatMessage datedChatMessage) {
        for (Map.Entry<Socket, ObjectOutputStream> entry : registeredClients.entrySet()) {
            try {
                entry.getValue().writeObject(datedChatMessage);
                registeredClients.get(entry.getValue()).flush();
            } catch (IOException e) {
                try {
                    //jak sie nie udalo do ktoregos, to wyrejestruj tego klienta
                    registeredClients.get(entry.getValue()).close();
                } catch (IOException e1) {}
                registeredClients.remove(entry.getKey());
            }
        }
    }

    private void printMessage(DatedChatMessage datedChatMessage) {
        System.out.println("<" + dateTimeFormatter.format(datedChatMessage.getReciveDate())
                + "> " + "<" + datedChatMessage.getAuthor() + ">: " + datedChatMessage.getMessage());

    }


}
