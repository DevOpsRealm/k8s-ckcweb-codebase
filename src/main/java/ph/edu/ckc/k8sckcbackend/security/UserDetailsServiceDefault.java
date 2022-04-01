package ph.edu.ckc.k8sckcbackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ph.edu.ckc.k8sckcbackend.entities.Users;
import ph.edu.ckc.k8sckcbackend.repository.UsersRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceDefault implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Users users = usersRepository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(users.getUsername(), users.getPassword(), true, true, true, true, getAuthorities(users.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(roles.split(","))
                .forEach(s -> {
                    authorities.add(new SimpleGrantedAuthority(s));
                });
        return authorities;
    }
}
