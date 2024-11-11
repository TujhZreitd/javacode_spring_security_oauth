/*package javacode_spring_security_oauth.service;

import javacode_spring_security_oauth.model.User;
import javacode_spring_security_oauth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public String addUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oAuthToken) {
            OAuth2User oAuth2User = oAuthToken.getPrincipal();
            if (oAuth2User != null) {
                Map<String, Object> attributes = oAuth2User.getAttributes();
                Optional<User> optionalUser = userRepository.findByEmail((String) attributes.get("email"));
                if (optionalUser.isEmpty()) {
                    User user = new User();
                    user.setUserId((String) attributes.get("sub"));
                    user.setName((String) attributes.get("name"));
                    user.setGivenName((String) attributes.get("given_name"));
                    user.setFamilyName((String) attributes.get("family_name"));
                    user.setEmail((String) attributes.get("email"));
                    userRepository.save(user);
                }
                return "Hello " + attributes.get("name");
            }
        }
        return "No OAuth2AuthenticationToken found";
    }
}*/
