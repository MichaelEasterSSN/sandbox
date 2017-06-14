
package com.screenscape.example;

import java.util.Date;

public class Runner {
    private static void log(String s) {
        System.out.println("TRACER " + new Date().toString() + " " + s);
    }

    public static void main(String[] args) {
        try {
            NetworkSource networkSource = new NetworkSource();

            long wait = 1000 * 12;
            try { System.out.println("sleeping ..."); Thread.sleep(wait); } catch (Exception ex) {}

            int count = 0;
            int max = 50;

            while (count < max) {
                log(networkSource.getNetworkInfo(NetworkSource.NETWORK_TYPE_GOOGLE).toString());
                log(networkSource.getNetworkInfo(NetworkSource.NETWORK_TYPE_YOUTUBE).toString());
                log(networkSource.getNetworkInfo(NetworkSource.NETWORK_TYPE_SSN).toString());

                wait = 1000 * 12;
                try { System.out.println("sleeping ..."); Thread.sleep(wait); } catch (Exception ex) {}
                count++;
            }

            System.out.println("Ready.");
        } catch (Exception ex) {
            System.err.println("caught exception : " + ex.getMessage());
        }
    }
}



