    package com.ssafy.project.common.security.authentication;

    import com.ssafy.project.common.db.entity.base.RoleEnum;
    import com.ssafy.project.common.db.entity.common.User;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.Setter;
    import lombok.ToString;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.oauth2.core.user.OAuth2User;

    import java.util.Collection;
    import java.util.Collections;
    import java.util.List;
    import java.util.Map;

    @ToString
    @Getter
    @AllArgsConstructor
    public class UserPrincipal implements OAuth2User, UserDetails {

        private long id;
        private String email;
        private Collection<? extends GrantedAuthority> authorities;
        @Setter
        private Map<String, Object> attributes;

        public static UserPrincipal create(User user) {
            List<GrantedAuthority> authorities =
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + RoleEnum.CLIENT));

            return new UserPrincipal(
                    user.getId(),
                    user.getEmail(),
                    authorities,
                    null
            );
        }

        public static UserPrincipal create(User user, Map<String, Object> attributes) {
            UserPrincipal userPrincipal = UserPrincipal.create(user);
            userPrincipal.setAttributes(attributes);
            return userPrincipal;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public String getName() { return String.valueOf(id); }

        @Override
        public String getUsername() { return String.valueOf(id); }

        @Override
        public String getPassword() { return null; }

    }