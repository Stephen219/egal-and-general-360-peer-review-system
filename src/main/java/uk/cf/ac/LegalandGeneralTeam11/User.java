package uk.cf.ac.LegalandGeneralTeam11;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;
    private String password;
}


    // Validate method to check username and password
   /* public boolean validate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}*/
