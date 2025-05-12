package org.mecipe.server.model.request.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class UserLogoutDTO implements Serializable {

    private String username;

}
