package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RMIClient {

    public static List<String> inputReader(File file) throws IOException {
        List<String> listOfStrings
                = new ArrayList<String>();
            BufferedReader bf = new BufferedReader(
                    new FileReader(file.getAbsolutePath()));

            String line = bf.readLine();

            while (line != null) {
                listOfStrings.add(line);
                line = bf.readLine();
            }
            bf.close();
            return listOfStrings;
    }

    public static long primes(List<String> primes) throws IOException {
        int limBegin = 0;
        int limEnd = 100;
        int currPrime;
        long total = 0;
        for (int i = 0; i < primes.size(); i++) {
            currPrime = Integer.parseInt(primes.get(i));
            if (currPrime < limEnd && currPrime > limBegin){
                if(isPrime(i)) total+= currPrime;
            }
        }
        return total;
    }


    public static boolean isPrime(int n)
    {
        if (n<=1) return false;
        for (int i=2; i<=Math.sqrt(n); i++)
            if (n%i == 0) return false ;
        return true;
    }


    public static void main(String[] args) {
        String hostName = "localhost";
        int port = 8080;
        String RMI_HOSTNAME = "java.rmi.server.hostname";
        String SERVICE_PATH = "//" + hostName + ":" + port + "/CountPrimes";

        try {
            System.setProperty(RMI_HOSTNAME, hostName);
            Service service = (Service) Naming.lookup(SERVICE_PATH);
            long startTime = System.nanoTime();

            while(true){
                String str = service.poll();
                if (str == null) {
                    System.out.println("Received none!");
                    break;
                } else {

                    try {
                        int i = Integer.parseInt(str);
                        List<String> nums = inputReader(new File("src/main/java/org/example/input/data" + i + ".txt"));
                        String res = String.valueOf(primes(nums));
                        System.out.println("Received top number in queue: " + str);
                        service.result(res);
                        service.getRes();
                        Thread.sleep(1000);
                        System.out.println("Completed: " + i);
                        System.out.println("Sum of primes equal to: " + res);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            long elapsedTime = System.nanoTime() - startTime;
            System.out.println("Total execution time: "
                    + elapsedTime/1000000);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}