package com.example.oca42.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    public String accessToken; //1 min //401 id/username + roles[]
    public String refreshToken; //1 day //401 id/username +expire time is more than access token
}
