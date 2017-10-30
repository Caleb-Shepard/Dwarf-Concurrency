// package Athreads;

import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dwarf implements Runnable {
    private final SecureRandom generator = new SecureRandom();
    private int sleepTime;
    private String name;
    private int numMined;
    private MineSim mine;

    public Dwarf(MineSim m, String n) {
        mine = m;
        name = n;
        numMined = 0;
    }

    @Override
    public void run(){
        while(mine.isLeftover()){
            try{
                System.out.printf("%s is going into the mine%n", name);
                Thread.sleep(generator.nextInt(1)+5);
                System.out.printf("%s has arrived at the mine%n", name);
                numMined += mine.diamond_collection(name);
                System.out.printf("%s is going to sleep%n", name);
                Thread.sleep(generator.nextInt(1)+10);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
                Thread.currentThread().interrupt();
            }

            System.out.printf("%s done mining xd %n", name);
        }

        if(mine.isRunning()){
            mine.kill();
        }
    }

}
