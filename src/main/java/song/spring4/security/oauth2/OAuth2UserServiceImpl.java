package song.spring4.security.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.spring4.domain.oauth2.entity.Sns;
import song.spring4.security.pricipal.UserPrincipal;
import song.spring4.domain.oauth2.service.ProviderService;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final ProviderService providerService;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        for (String s : attributes.keySet()) {
            System.out.println(attributes.get(s));
        }

//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
//                .getUserInfoEndpoint().getUserNameAttributeName();
//        ClientRegistration clientRegistration = userRequest.getClientRegistration();
//        String clientName = clientRegistration.getClientName();
//        String clientId = clientRegistration.getClientId();
//        String clientSecret = clientRegistration.getClientSecret();
//        String tokenValue = userRequest.getAccessToken().getTokenValue();
//        Map<String, Object> additionalParameters = userRequest.getAdditionalParameters();
//        for (String key : additionalParameters.keySet()) {
//            log.info("additionalParameters: key = {} , value = {}", key, additionalParameters.get(key));
//        }
//        log.info("registrationId = {}", registrationId);
//        log.info("userNameAttributeName = {}", userNameAttributeName);
//        log.info("clientName = {}", clientName);
//        log.info("clientId = {}", clientId);
//        log.info("clientSecret = {}", clientSecret);
//        log.info("tokenValue = {}", tokenValue);
//        log.info("attributes ");
//        for (String s : attributes.keySet()) {
//            log.info("{}: {}", s, attributes.get(s).toString());
//        }

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        String snsId = (String) response.get("id");
        String snsName = (String) response.get("name");
        String snsEmail = (String) response.get("email");

        Sns saveSns = providerService.findOrCreate(snsId, snsName, snsEmail);

        UserPrincipal userPrincipal = UserPrincipal.of(saveSns);

        return userPrincipal;
    }
}
