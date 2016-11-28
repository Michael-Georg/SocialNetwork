package models;

import lombok.AllArgsConstructor;
import lombok.Value;

/*
    @param status ignore  - user (user_id) can't follow smbdy (friend_id current user)
           status follow - user (user_id current user) follow smbdy
 */

@Value
@AllArgsConstructor
public class Relation {
    int user_id;
    int friend_id;
    Status status;
}
