package websockets;

import Dao.MessageDao;
import Dao.PersonDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import models.Message;
import models.Person;
import models.WSMessage;

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
import static servlets.ServletConst.PERSON;
import static servlets.ServletConst.PERSON_DAO;

@Log
@SuppressWarnings("unused")
@ServerEndpoint(value = "/Profile/{id}", configurator = HttpSessionConfig.class)
public class UserWall {

    private static Map<Integer, Set<Session>> sessionMap = new ConcurrentHashMap<>();
    private static MessageDao messageDao;
    private static PersonDao personDao;
    private Person person;
    private Integer id;

    @OnOpen
    public void onOpen(@PathParam("id") String id, Session session, EndpointConfig config) {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        messageDao = (MessageDao) httpSession.getServletContext().getAttribute(MESSAGE_DAO);
        personDao = (PersonDao) httpSession.getServletContext().getAttribute(PERSON_DAO);
        person = (Person) httpSession.getAttribute(PERSON);
        Set<Session> sessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
        this.id = Integer.parseInt(id);
        log.info("person id - " + id + " session id - " + session.getId() + "session size - " + sessions.size());
        sessionMap.putIfAbsent(this.id, sessions);
        sessionMap.get(this.id).add(session);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Set<Message> list = messageDao.getAllPosts(this.id);
            for (Message msg : list) {
                session.getBasicRemote().sendText(createJSON(msg, "post"));
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
    public void onMessage(String json, Session session) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            WSMessage msg = mapper.readValue(json, WSMessage.class);
            switch (msg.getType()) {
                case "add": {
//                log.info("send msg to" + sessionMap.get(id).size() + " users");
                    Message message = messageDao.add(msg);
                    sessionMap.get(id).stream()
                            .map(Session::getAsyncRemote)
                            .forEach(s -> s.sendText(createJSON(message, "msg")));
                    break;
                }
                case "comments": {
                    List<Message> list = messageDao.getAllComments(msg.getPost_id());
                    for (Message message : list)
                        session.getBasicRemote().sendText(createJSON(message, "post"));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private String createJSON(Message msg, String type) {
        ObjectMapper mapper = new ObjectMapper();
        Person user = personDao.getEntity(msg.getUser_id()).get();
        return mapper.writeValueAsString(WSMessage.builder()
                .id(msg.getId())
                .text(msg.getText())
                .user_id(msg.getUser_id())
                .post_id(msg.getPost_id())
                .from_firstName(user.getFirstName())
                .from_lastName(user.getLastName())
                .type(type)
                .build());
    }
}
