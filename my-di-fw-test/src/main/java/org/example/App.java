package org.example;

import org.example.di.ContainerService;

public class App {
    public static void main(String[] args) {
        AccountService accountService = ContainerService.getObject(AccountService.class);
        accountService.join();
    }
}
