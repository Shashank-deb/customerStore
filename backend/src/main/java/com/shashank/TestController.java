package com.shashank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @GetMapping("/shank")
  public String getName(){
      return "som jaxen";
  }
}
