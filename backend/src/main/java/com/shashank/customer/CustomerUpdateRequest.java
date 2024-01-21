package com.shashank.customer;

public record CustomerUpdateRequest (
        String name,
        String email,
        Integer age
){

 }
