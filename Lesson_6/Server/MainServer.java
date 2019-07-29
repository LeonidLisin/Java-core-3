package Lesson_6.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.*;

public class MainServer {

    private Vector<ClientHandler> clients;
    Logger logger;

    public MainServer() throws SQLException, IOException {
        ServerSocket server = null;
        Socket socket = null;
        clients = new Vector<>();
        logger = Logger.getLogger("");
        logger.getHandlers()[0].setLevel(Level.ALL);
        Handler fileLogHandler = new FileHandler("server.log", true);
        fileLogHandler.setLevel(Level.ALL);
        fileLogHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileLogHandler);

        try {
            AuthRegService.connect();

            server = new ServerSocket(8189);
            //System.out.println("Сервер запущен");
            logger.log(Level.SEVERE, "Сервер запущен");

            while (true) {
                socket = server.accept();
                //System.out.println("Клиент подключился");
                logger.log(Level.SEVERE, "Клиент подключился");
                new ClientHandler(socket, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthRegService.disconnect();
        }
    }

    // подписываем клиента на рассылку
    public void subscribe(ClientHandler client) {
        clients.add(client);
        broadcastClientList();
    }

    // отписываем клиента от рассылки сообщений
    public void unsubscribe(ClientHandler client){
        clients.remove(client);
    }

    public void broadcastMsg(String nick, String msg) {
        if (!msg.startsWith("/w"))
            AuthRegService.saveToDB(nick, msg);
        boolean notBanned = true;
        for (ClientHandler o: clients) {
            // проверяем нет ли написавшего ника в блэклисте у всех клиентов
                if (o.bannedList.contains(nick))
                    notBanned = false;
            if(notBanned) o.sendMsg(nick + " " + msg);
        }
    }

    public void broadcastClientList(){
        StringBuilder clientList = new StringBuilder();
        for (ClientHandler o: clients)
            clientList.append(o.getNick() + " ");
        for (ClientHandler o: clients)
            o.sendMsg("/clients " + clientList.toString());

    }

    public boolean checkNick(String nick){
        for (ClientHandler o: clients) {
            if (o.getNick().equals(nick))
                return false;
        }
        return true;
    }

}
