package me.jarvis;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new Engine(1920, 1080)
            .run()
            .dispose();
    }
}
