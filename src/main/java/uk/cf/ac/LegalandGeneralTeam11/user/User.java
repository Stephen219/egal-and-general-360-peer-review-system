package uk.cf.ac.LegalandGeneralTeam11.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor



public class User implements UserDetails {
    private Long id;

    @NotEmpty(message = "Please provide a username")
    private String username;

    @NotEmpty(message = "Please provide a full name")
    @Email(message = "Please provide a valid email address")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long, contain at least one digit, one uppercase letter, one lowercase letter and one special character")
    private String password;
    private  Long roleId;

//    public User(long id, String username, String email, String password, long roleId) {
//        this.email = email;
//        this.id = id;
//        this.roleId = roleId;
//        this.password = password;
//        this.username = username;
//    }


    public User() {
        this(0L, " ", " ", " ", 2L);
    }

    public User(String testOwner, String s) {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }



    public String getEmail(){
        return email;
    }
}
