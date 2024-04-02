package com.andreiz0r.breddit.integration;

import org.junit.jupiter.api.BeforeEach;

public class AuthIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void before() {
        super.setUp();
    }

    void register(){
        //        RegisterRequest request = RegisterRequest.builder()
//                .username(Randoms.alphabetic())
//                .password(Randoms.alphabetic())
//                .email(Randoms.randomEmail())
//                .country(Randoms.alphabetic())
//                .birthDate(AppUtils.sqlDateNow())
//                .role(UserRole.User)
//                .build();
//
//        assertThatRespondedWithSuccess(AppHttpMethod.POST, "/api/auth/register", request, "UserUti");
    }
}
