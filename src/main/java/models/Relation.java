package models;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Immutable class representing Relation between two users of social network.
 * Field 'status' means : block(1) - user (user_id) can't follow another user (friend_id (authorized user))
 *                        follow(2) - user (user_id (authorized user)) follow another user
 */

@Value
@AllArgsConstructor
public class Relation {
    int user_id;
    int friend_id;
    int status;
}
