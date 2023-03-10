package Business_Logic;

import GUI.SimulationFrame;
import Model.Server;
import Model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers=new ArrayList<>();
    SimulationFrame frame;
    private int finalTime;
    private int nrTasks;
    private int currentTime;
    private int[] waitingTimes=new int[1000];
    private int[] peakNumberClients=new int[1000];
    private int serviceTimes=0;
    BufferedWriter writer;
    {
        try {
            writer = new BufferedWriter(new FileWriter("log.txt",false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Scheduler(SimulationFrame frame, int maxNoServers,int finalTime,int nrTasks)
    {
        for (int i=0;i<maxNoServers;i++)
        {
            Server server=new Server(finalTime);
            Thread thread=new Thread(server);
            thread.start();
            servers.add(server);
        }
        currentTime=0;
        this.finalTime=finalTime;
        this.frame=frame;
        this.nrTasks=nrTasks;
    }
    public void dispatchTask(Task t)
    {
        int indexServer=0,minTime=10000;
        for (int i=0;i<servers.size();i++)
        {
            if (minTime>servers.get(i).getWaitingPeriod())
            {
                minTime=servers.get(i).getWaitingPeriod();
                indexServer=i;
            }
        }
        t.setServiceTime(t.getServiceTime()+1);
        servers.get(indexServer).addTask(t);
        int value= servers.get(indexServer).getWaitingPeriod();
        servers.get(indexServer).setWaitingPeriod(value+t.getServiceTime());
    }
    public void calculateServiceTime(List<Task> generatedTasks)
    {
        for (int i=0;i<generatedTasks.size();i++)
        {
            serviceTimes+=generatedTasks.get(i).getServiceTime();
        }
    }
    public void displayTasksServer()
    {
        for (int i=0;i<servers.size();i++)
        {
            System.out.print("Queue "+(i+1)+":");
            ArrayList<Task> tasks=servers.get(i).getTasks();
            for(int j=0;j<tasks.size();j++)
            {
                System.out.print("("+tasks.get(j).getID()+","+tasks.get(j).getArrivalTime()
                        +","+tasks.get(j).getServiceTime()+")  ");
            }
            System.out.println();
        }
    }
    public void displayTasksServerLog(List<Task> generatedTasks)
    {
        try {
            writer.append("Time "+currentTime);
            writer.newLine();
            writer.append("Waiting tasks:");
            for (int i=0;i<generatedTasks.size();i++)
            {
                writer.append("("+generatedTasks.get(i).getID()+","+generatedTasks.get(i).getArrivalTime()
                        +","+generatedTasks.get(i).getServiceTime()+")  ");
            }
            writer.newLine();
            peakNumberClients[currentTime]=0;
            for (int i=0;i<servers.size();i++)
            {
                writer.append("Queue "+(i+1)+":");
                ArrayList<Task> tasks=servers.get(i).getTasks();
                peakNumberClients[currentTime]+=tasks.size();
                for(int j=0;j<tasks.size();j++)
                {
                    writer.append("("+tasks.get(j).getID()+","+tasks.get(j).getArrivalTime()
                            +","+tasks.get(j).getServiceTime()+")  ");
                }
                writer.newLine();
                waitingTimes[i]=0;
                if (tasks.size()!=0 )
                {
                    waitingTimes[currentTime]=waitingTimes[currentTime]+tasks.get(0).getServiceTime();
                }

            }
            if (currentTime==finalTime)
            {
                float sum=0;
                for (int i=0;i<finalTime;i++)
                {
                    sum+=waitingTimes[i];
                }
                sum/=finalTime;
                writer.append("Average waiting time:"+sum);
                writer.close();
            }
            currentTime++;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void displayAvgWaitingTime()
    {
        float sum=0;
        for (int i=0;i<finalTime;i++)
        {
            sum+=waitingTimes[i];
        }
        sum/=finalTime;
        frame.setAverageWaitValue(sum);
    }
    public void displayAvgServiceTime()
    {
        float avg=serviceTimes;
        avg/=nrTasks;
        frame.setAverageServiceTime(avg);
    }
    public void displayPeakHour()
    {
        int highest=0;
        int highestTime=-1;
        for (int i=0;i<finalTime;i++)
        {
            if (peakNumberClients[i]>highest)
            {
                highest=peakNumberClients[i];
                highestTime=i;
            }
        }
        frame.setPeakHourValue(highestTime);
    }
    public void displayTasksOnGUI()
    {
        for (int i=0;i<servers.size();i++)
        {
            ArrayList<Task> tasks=servers.get(i).getTasks();
            frame.setQueue(i,tasks);
        }
    }
    public List<Server> getServers()
    {
        return servers;
    }
}
