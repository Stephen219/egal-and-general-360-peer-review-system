package uk.cf.ac.LegalandGeneralTeam11.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TokenDto {
    private Long tokenId;
    private String userEmail;
    private String token;
    private LocalDateTime expiry;

    boolean is_used = false;

    String type;

}
