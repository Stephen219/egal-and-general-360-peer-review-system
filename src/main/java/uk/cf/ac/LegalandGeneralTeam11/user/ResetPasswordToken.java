package uk.cf.ac.LegalandGeneralTeam11.user;

import java.time.LocalDateTime;

public class ResetPasswordToken {


    Long tokenId;
    String userEmail;
    String token;
    LocalDateTime expiry;
    boolean is_used = false;



}
