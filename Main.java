package pr4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {
        String input = "Hello, CompletableFuture!";

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            System.out.println("Running an asynchronous task...");
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            String result = input.toUpperCase();
            System.out.println("Converted to upper case: " + result);
            return result;
        });

        CompletableFuture<Integer> future3 = future2.thenApplyAsync(result -> {
            int length = result.length();
            System.out.println("Length of the string: " + length);
            return length;
        });

        future3.thenAcceptAsync(length -> {
            System.out.println("The length of the string was written in file");
            writeLengthToFile(length);
        });

        future3.thenRunAsync(() -> {
            System.out.println("All tasks completed.");
        });

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future3);
        allFutures.join();
    }

    private static void writeLengthToFile(int length) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true))) {
            writer.write("The length of the string is: " + length);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}