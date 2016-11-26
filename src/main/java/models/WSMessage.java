package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WSMessage {
    private String type;

    private int id;
    private int post_id;
    private String text;

    private int user_id;
    private String from_firstName;
    private String from_lastName;

}
