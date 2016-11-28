package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Message {
    private int id;
    private int user_id;
    private int post_id; // The Message is comment if this param != -1 else it Post
    private String text;
}
