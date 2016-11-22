package websockets;

import Dao.MessageDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import models.Message;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static servlets.ServletConst.MESSAGE_DAO;

@Log
@SuppressWarnings("unused")
@ServerEndpoint(value = "/Profile/{id}", configurator = HttpSessionConfig.class)
public class Post {

    private static Map<Integer, Set<Session>> sessionMap = new ConcurrentHashMap<>();
    private static MessageDao messageDao;
    private Integer id;
    @OnOpen
    public void onOpen(@PathParam("id") String id, Session session, EndpointConfig config) {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        messageDao = (MessageDao) httpSession.getServletContext().getAttribute(MESSAGE_DAO);
        Set<Session> sessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
        this.id = Integer.parseInt(id);
        log.info("person id - " + id + " session id - " + session.getId() + "session size - " + sessions.size());
        sessionMap.putIfAbsent(this.id, sessions);
        sessionMap.get(this.id).add(session);
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Message> list = messageDao.getAll(Integer.parseInt(id));
            for (Message msg : list) {
                String json = mapper.writeValueAsString(msg);
                session.getBasicRemote().sendText(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessionMap.get(id).remove(session);
        log.info("session " + session.getId() + " closed");
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Message msg = mapper.readValue(message, Message.class);
            messageDao.add(msg);
            log.info("send msg to" + sessionMap.get(id).size() + " users");
            sessionMap.get(id).stream()
                    .map(Session::getAsyncRemote)
                    .forEach(s -> s.sendText(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
