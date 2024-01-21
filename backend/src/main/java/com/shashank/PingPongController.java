package com.shashank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {

    private static int COUNTER=0;
   public record PingPong(String result){}


    @GetMapping("/ping")
    public PingPong getPingPong(){
        return new PingPong("Pong"+ ++COUNTER);
    }
    @GetMapping("/check")
    public String checkEC2() {
        // Add logic to check the status of your application or perform any other checks
        return "EC2 is up and runs on Som Server!";
    }
}
