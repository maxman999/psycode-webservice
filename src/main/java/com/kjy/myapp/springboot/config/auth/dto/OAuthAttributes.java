package com.kjy.myapp.springboot.config.auth.dto;

import com.kjy.myapp.springboot.domain.user.Role;
import com.kjy.myapp.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        System.out.println(registrationId);
        if("naver".equals(registrationId)){
            return ofNaver("id", attributes);
        }else if("github".equals(registrationId)){
            return ofGithub(userNameAttributeName, attributes);
        }else if ("kakao".equals(registrationId)){
            return ofKakao("id",attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private  static OAuthAttributes ofGithub(String userNameAttributeName, Map<String,Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("avatar_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String,Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String,Object> attributes){
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String,Object> attributes){
        Map<String, Object> profile = (Map<String, Object>) attributes.get("properties");
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        return OAuthAttributes.builder()
                .name((String)profile.get("nickname"))
                .email((String)kakao_account.get("email"))
                .picture((String)profile.get("profile_image"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
    }

}
