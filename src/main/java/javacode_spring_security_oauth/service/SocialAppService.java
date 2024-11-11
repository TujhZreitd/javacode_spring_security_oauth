package javacode_spring_security_oauth.service;

import jakarta.transaction.Transactional;
import javacode_spring_security_oauth.model.ERole;
import javacode_spring_security_oauth.model.Role;
import javacode_spring_security_oauth.model.User;
import javacode_spring_security_oauth.repository.RoleRepository;
import javacode_spring_security_oauth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SocialAppService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(SocialAppService.class);

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        if (oAuth2User != null) {
            Map<String, Object> attributes = oAuth2User.getAttributes();
            String email = (String) attributes.get("email");
            logger.info("Attempting to load user with email: {}", email);
            Optional<User> optionalUser = userRepository.findByEmail(email);
            Set<Role> roles = checkRolesUser(oAuth2User);
            if (optionalUser.isEmpty()) {
                logger.info("User with email: {} not found, creating new user", email);
                createAndSaveUser(attributes, roles);
            } else {
                logger.info("User with email: {} already exists", email);
            }
        } else {
            logger.warn("OAuth2User returned null for request: {}", userRequest);
        }
        return oAuth2User;
    }

    private void createAndSaveUser(Map<String, Object> attributes, Set<Role> roles) {
        User user = new User();
        user.setUserId((String) attributes.get("sub"));
        user.setName((String) attributes.get("name"));
        user.setGivenName((String) attributes.get("given_name"));
        user.setFamilyName((String) attributes.get("family_name"));
        user.setEmail((String) attributes.get("email"));
        user.setRoles(roles);
        userRepository.save(user);
    }

    private Set<Role> checkRolesUser(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        Set<Role> result = new HashSet<>();
        if (email != null && email.startsWith("admin")) {
            result.addAll(roleRepository.findAll());
        } else {
            result.add(roleRepository.findByName(ERole.ROLE_USER).orElseThrow());
        }
        return result;
    }
}
