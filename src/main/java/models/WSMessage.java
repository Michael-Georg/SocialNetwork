package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Class WSMessage for JSON messages sends and receives in WebSocket chanel
 * The message is post if post_id = -1, else it comment
 * Instance of class can be create by nested WSMessageBuilder class.
 */
@Data
@Builder
@AllArgsConstructor
public class WSMessage {
    /**
     * Action field
     */
    private String type;

    /**
     * Information about message
     */
    private int id;
    private int post_id;
    private String text;
    private String time;
    /**
     * Some information about the sender
     */
    private int user_id;
    private String from_firstName;
    private String from_lastName;

}
