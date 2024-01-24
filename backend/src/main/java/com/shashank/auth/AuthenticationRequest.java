package com.shashank.auth;

public record AuthenticationRequest(
        String username,
        String password

) {
}
