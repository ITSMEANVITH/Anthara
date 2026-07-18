package com.eventbooking.util;

public class GenerateAdminPassword {

    public static void main(String[] args) {

        String hash = PasswordUtil.hashPassword("admin123");

        System.out.println(hash);

    }

}