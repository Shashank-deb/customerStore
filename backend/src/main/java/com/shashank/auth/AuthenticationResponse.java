package com.shashank.auth;

import com.shashank.customer.CustomerDTO;

public record AuthenticationResponse(
        String token,
        CustomerDTO customerDTO
) {


}
