package uk.cf.ac.LegalandGeneralTeam11.user;

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

    private String username;

    private String email;
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
        this(0L, "hilkia", "hhh", "hhhhhhhh", 2L);
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
