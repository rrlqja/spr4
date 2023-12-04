package song.spring4.security.pricipal;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import song.spring4.domain.user.User;
import song.spring4.entity.oauth2.Sns;

import java.util.*;

public class UserPrincipal implements UserDetails, OAuth2User {
    private Long id;
    private List<SimpleGrantedAuthority> roleList = new ArrayList<>();
    private String username;
    private String name;
    private String password;
    private String email;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    private Map<String, Object> attributes;

    private UserPrincipal(User user) {
        this.id = user.getId();
        this.roleList = user.getRoleList().stream().map(userRole ->
                new SimpleGrantedAuthority(userRole.getRole().getRoleName().toString())).toList();
        this.username = user.getUsername();
        this.name = user.getName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.isAccountNonExpired = user.isAccountNonExpired();
        this.isAccountNonLocked = user.isAccountNonLocked();
        this.isCredentialsNonExpired = user.isCredentialsNonExpired();
        this.isEnabled = user.isEnabled();

        this.attributes = new HashMap<>();
    }

    private UserPrincipal(Sns sns) {
        User user = sns.getUser();

        this.id = user.getId();
        this.roleList = user.getRoleList().stream().map(userRole ->
                new SimpleGrantedAuthority(userRole.getRole().getRoleName().toString())).toList();
        this.isAccountNonExpired = user.isAccountNonExpired();
        this.isAccountNonLocked = user.isAccountNonLocked();
        this.isCredentialsNonExpired = user.isCredentialsNonExpired();
        this.isEnabled = user.isEnabled();

        Map<String, Object> providerAttributes = sns.getAttributes();
//        this.username = (String) providerAttributes.get("snsName");
        this.username = user.getUsername();
        this.email = (String) providerAttributes.get("snsEmail");
        this.attributes = Map.copyOf(providerAttributes);
    }

    public static UserPrincipal create(Sns SNS) {
        return new UserPrincipal(SNS);
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(user);
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roleList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
