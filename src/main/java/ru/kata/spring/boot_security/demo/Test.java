package ru.kata.spring.boot_security.demo;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Test {
    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("100", BCrypt.gensalt()));
    }
}
