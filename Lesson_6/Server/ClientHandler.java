package Lesson_6.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;

public class ClientHandler {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private MainServer server;
    private String nick;
    public ArrayList<String> bannedList;

    public ClientHandler(Socket socket, MainServer server) {
        try {
            this.socket = socket;
            this.server = server;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // цикл для авторизации или регистрации
                        while (true) {
                            String str = in.readUTF();
                            // если сообщение начинается с /auth
                            if(str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                // Вытаскиваем данные из БД
                                String newNick = AuthRegService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (newNick != null) {
                                    // отправляем сообщение об успешной авторизации
                                    if (server.checkNick(newNick)) {
                                        sendMsg("/authok " + newNick);
                                        nick = newNick;
                                        server.subscribe(ClientHandler.this);
                                        break;
                                    }
                                    else{
                                        sendMsg("Пользователь с таким ником уже в чате");
                                        server.logger.log(Level.INFO, "Попытка подключения с тем же ником/паролем");
                                    }
                                } else {
                                    sendMsg("Неверный логин/пароль!");
                                    server.logger.log(Level.INFO, "Неверный ввод логина/пароля");

                                }
                            }
                            // регистрация нового пользователя
                            if(str.startsWith("/reg")) {
                                String[] tokens = str.split(" ");
                                // Вытаскиваем данные из БД
                                if(AuthRegService.isNickAndLoginFree(tokens[1], tokens[3])){
                                    AuthRegService.regNewUser(tokens[1], tokens[2], tokens[3]);
                                    server.logger.log(Level.SEVERE, "Новый пользователь зарегистрирован");
                                    nick = tokens[3];
                                    server.subscribe(ClientHandler.this);
                                    sendMsg("/authok " + nick);
                                    break;
                                }
                                else {
                                    sendMsg("Пользователь с таким логином/ником уже заререстрирован");
                                    server.logger.log(Level.INFO, "Попытка регистрации с использованным ником/паролем");

                                }
                            }

                        }

                        // отправляем историю подключившемуся клиенту
                        ArrayList<String[]> history = AuthRegService.readHistoryFromDB();
                        for(String[] h: history){
                            if (!h[1].equals(""))
                            sendMsg("/hist " + h[0] + " " + h[1]);}

                        // получаем список забаненных клиентов
                        bannedList = AuthRegService.getBannedList(nick);

                        // блок для отправки сообщений
                        while (true) {
                            String str = in.readUTF();
                            String[] tokens = str.split(" ");

                            // баним юзера
                            if(str.startsWith("/ban")) {
                                AuthRegService.addToBlackList(nick, tokens[1]);
                                bannedList.add(tokens[1]);
                                server.logger.log(Level.SEVERE, nick + " добавил " + tokens[1] + " в банлист");

                            }

                            if(str.equals("/end")) {
                                out.writeUTF("/serverClosed");
                                break;
                            }

                            if(str.startsWith("/w")){
                                if (tokens.length >= 3){
                                    if (server.checkNick(tokens[1])){
                                        out.writeUTF("/err wrongNick");
                                    }else server.broadcastMsg(nick, str);
                                } else out.writeUTF("/err wrongW");
                            } else if (!str.startsWith("/?")) server.broadcastMsg(nick, str);

                        // шлем список клиентов постоянно
                            server.broadcastClientList();
                        }

                    }  catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        server.unsubscribe(ClientHandler.this);
                        server.logger.log(Level.INFO, "Клиент отключился");

                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick(){
        return nick;
    }
}
