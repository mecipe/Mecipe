package org.mecipe.server.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginToken {

    private String tokenName;

    private String tokenValue;

}