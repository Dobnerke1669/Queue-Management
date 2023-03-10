package Business_Logic;

import GUI.Controller;
import GUI.SimulationFrame;
import Model.Task;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.System.exit;


public class SimulationManager implements Runnable{
    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int numberOfServers;
    public int numberOfClients;
    public int minArrivalTime;
    public int maxArrivalTime;
    private Scheduler scheduler;
    private List<Task> generatedTasks=new ArrayList<>();
    static SimulationFrame frame=new SimulationFrame();
    public SimulationManager(int minProcessingTime,int maxProcessingTime,int numberOfClients,int numberOfServers,
                             int minArrivalTime,int maxArrivalTime,int timeLimit)
    {
        this.maxProcessingTime=maxProcessingTime;
        this.minProcessingTime=minProcessingTime;
        this.minArrivalTime=minArrivalTime;
        this.maxArrivalTime=maxArrivalTime;
        this.numberOfServers=numberOfServers;
        this.numberOfClients=numberOfClients;
        this.timeLimit=timeLimit;
        this.scheduler=new Scheduler(frame,numberOfServers,timeLimit,numberOfClients);
    }
    private void generateRandomTasks(int n)
    {
        Random random = new Random();
        for(int i=0;i<n;i++)
        {
            Task newTask=new Task(i+1,random.nextInt(maxArrivalTime-minArrivalTime)+minArrivalTime,random.nextInt(maxProcessingTime-minProcessingTime)+minProcessingTime);
            generatedTasks.add(newTask);
        }
    }
    @Override
    public void run() {
        int currentTime=0;
        generateRandomTasks(numberOfClients);
        scheduler.calculateServiceTime(generatedTasks);
        while (currentTime <= timeLimit){
            frame.setCurrentTime(currentTime);
            int k=0;
            while(k<generatedTasks.size())
            {
                if (currentTime==generatedTasks.get(k).getArrivalTime())
                {
                    scheduler.dispatchTask(generatedTasks.get(k));
                    generatedTasks.remove(k);
                }
                else k++;
            }
            scheduler.displayTasksOnGUI();
            //scheduler.displayTasksServer();
            scheduler.displayTasksServerLog(generatedTasks);
            currentTime++;
            try {
                Thread.sleep(1000); // varj 1mp-t
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        scheduler.displayAvgWaitingTime();
        scheduler.displayPeakHour();
        scheduler.displayAvgServiceTime();
    }

    public static void main(String[] args) {

        Controller controller=new Controller(frame);
        frame.setVisible(true);
    }
}
