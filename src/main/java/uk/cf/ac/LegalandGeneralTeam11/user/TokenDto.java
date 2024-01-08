package uk.cf.ac.LegalandGeneralTeam11.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

/**
 * This class is used to create a token
 * it contains the token id, the user email, the token, the expiry date, the type of token and whether it has been used or not
 * type of token can be either "registration" or "reset"
 */

public class TokenDto {
    private Long tokenId;
    private String userEmail;
    private String token;
    private LocalDateTime expiry;

    boolean is_used = false;

    String type;

    public TokenDto(String token) {
        this.token = token;
    }
}
