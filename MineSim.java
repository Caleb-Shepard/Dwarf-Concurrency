// package DwarfMine;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MineSim {

    private int dimes;
    private ExecutorService executorService;
    private Random rand;
    private boolean leftover;
    private boolean running;
    private Dwarf dwarf1;
    private Dwarf dwarf2;
    private Dwarf dwarf3;
    private Dwarf dwarf4;
    private Dwarf dwarf5;
    private Dwarf dwarf6;
    private Dwarf dwarf7;

    public boolean isLeftover(){
        return leftover;
    }

    public MineSim(int d){
        dimes = d;

        rand = new Random();
        leftover = true;
        running = true;
        runThreads();
    }

    public void runThreads(){
        dwarf1 = new Dwarf(this, "Dopey");
        dwarf2 = new Dwarf(this, "Sneezy");
        dwarf3 = new Dwarf(this, "Bashful");
        dwarf4 = new Dwarf(this, "Doc");
        dwarf5 = new Dwarf(this, "Grumpy");
        dwarf6 = new Dwarf(this, "Happy");
        dwarf7 = new Dwarf(this, "Sleepy");

        executorService = Executors.newCachedThreadPool();

        executorService.execute(dwarf1);
        executorService.execute(dwarf2);
        executorService.execute(dwarf3);
        executorService.execute(dwarf4);
        executorService.execute(dwarf5);
        executorService.execute(dwarf6);
        executorService.execute(dwarf7);
    }

    // synchronized shared var
    public synchronized int diamond_collection(String s){
        System.out.println(s + " is going in the mine.");
        int diamondsCollected = 0;
        if(dimes > 0 && leftover){
            if(dimes > 50){
                diamondsCollected = rand.nextInt(41) + 10;
                dimes -= diamondsCollected;
                System.out.printf("%s got %d diamonds. There are %d left.%n", s, diamondsCollected, dimes);
            } else if (dimes > 10) {
                diamondsCollected = rand.nextInt(dimes - 9) + 10;
                dimes -= diamondsCollected;
                System.out.printf("%s got %d diamonds. There are %d left.%n", s, diamondsCollected, dimes);
            } else {
                diamondsCollected = rand.nextInt(dimes) + 1;
                dimes -= diamondsCollected;
                System.out.printf("%s got %d diamonds. There are %d left.%n", s, diamondsCollected, dimes);
            }
        } else {
            System.out.printf("%s left. There are no diamonds left to mine.%n", s);
            leftover = false;
        }
        return diamondsCollected;
    }

    public void kill(){
        running = false;
        executorService.shutdown();
        try{
            boolean tasksEnded = executorService.awaitTermination(1, TimeUnit.SECONDS);

            if(tasksEnded){
                System.out.println("Mine closed.");
            } else {
                System.out.println("Still talking to dwarves");
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isRunning(){
        return running;
    }

}
