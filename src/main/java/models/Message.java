package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Class representing Message.
 * The message is post if post_id = -1, else it comment
 * Instance of class can be create by nested MessageBuilder class.
 */
@Data
@Builder
@AllArgsConstructor
public class Message {
    private int id;
    private int user_id;
    private int post_id;
    private String text;
    private LocalDateTime time;
}
