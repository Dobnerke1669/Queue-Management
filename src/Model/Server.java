package Model;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks= new ArrayBlockingQueue<Task>( 10 ) ;
    private int waitingPeriod;
    private boolean isStopped=false;
    private int finalTime;

    public Server(int finalTime)
    {
        waitingPeriod=0;
        this.finalTime=finalTime;
    }
    public void addTask(Task newTask)
    {
        tasks.add(newTask);
        return;
    }

    public void stopServer(){
        isStopped=true;
    }

    @Override
    public void run() {
        int currentTime=0;
        while (!isStopped)
        {
            Task task=tasks.peek();
            if (task==null) continue;
            int serviceTime=task.getServiceTime();
            if (serviceTime!=1)
            {
                task.setServiceTime(serviceTime-1);
                setWaitingPeriod(getWaitingPeriod()-1);
            }
            else
            {
                try {
                    tasks.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (currentTime==finalTime)
            {
                stopServer();
            }
            currentTime++;
        }
    }
    public ArrayList<Task> getTasks()
    {
        return new ArrayList<Task>(tasks);
    }
    public int getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(int waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }
}
