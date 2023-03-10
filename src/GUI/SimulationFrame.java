package GUI;

import Model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SimulationFrame extends JFrame {
    private JLabel title;
    private JLabel currentTime;
    private JLabel currentTimeVal;
    public JLabel[][] queues=new JLabel[15][15];
    private JLabel averageWait;
    private JLabel averageWaitValue;
    private JLabel averageService;
    private JLabel averageServiceValue;
    private JLabel peakHour;
    private JLabel peakHourValue;
    private JLabel waitingList;
    private JLabel nrOfTasks;
    private JButton startButton;
    public JTextField nrOfTasksInput;
    private JLabel nrOfServers;
    public JTextField nrOfServersInput;
    private JLabel timeSimulation;
    public JTextField timeSimulationInput;
    private JLabel minArrival;
    public JTextField minArrivalInput;
    private JLabel maxArrival;
    public JTextField maxArrivalInput;
    private JLabel minProcessing;
    public JTextField minProcessingInput;
    private JLabel maxProcessing;
    public JTextField maxProcessingInput;
    private static int width;
    private static int height;
    Font myFont = new Font("Times New Roman", Font.BOLD, 24);
    Font resultFont = new Font("Times New Roman", Font.BOLD, 15);
    public SimulationFrame()
    {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screenSize.getWidth();
        height = (int) screenSize.getHeight();
        this.setSize(width,height);
        setupTitlePart();
        this.setLayout(null);
    }
    public void setupTitlePart()
    {
        title=new JLabel("Queue simulator");
        title.setFont(myFont);
        currentTime=new JLabel("Current time:");
        currentTime.setFont(resultFont);
        currentTimeVal=new JLabel();
        currentTimeVal.setFont(resultFont);
        currentTime.setBounds((width/2)-100,100,150,100);
        currentTimeVal.setBounds((width/2),100,150,100);
        title.setBounds((width/2)-100,10,200,100);
        getContentPane().add(currentTime);
        getContentPane().add(title);

        nrOfTasks=new JLabel("Number of Tasks:");
        nrOfTasks.setBounds(100,100,100,50);
        nrOfTasksInput=new JTextField(5);
        nrOfTasksInput.setBounds(100,150,100,20);
        getContentPane().add(nrOfTasks);
        getContentPane().add(nrOfTasksInput);
        nrOfServers=new JLabel("Number of Serv:");
        nrOfServersInput=new JTextField(5);
        nrOfServers.setBounds(100,170,100,50);
        nrOfServersInput.setBounds(100,220,100,20);
        getContentPane().add(nrOfServers);
        getContentPane().add(nrOfServersInput);
        timeSimulation=new JLabel("Simulation time:");
        timeSimulationInput=new JTextField(5);
        timeSimulation.setBounds(100,240,100,50);
        timeSimulationInput.setBounds(100,290,100,20);
        getContentPane().add(timeSimulation);
        getContentPane().add(timeSimulationInput);
        minArrival=new JLabel("Min arrival time:");
        minArrivalInput=new JTextField(5);
        minArrival.setBounds(100,310,100,50);
        minArrivalInput.setBounds(100,360,100,20);
        getContentPane().add(minArrival);
        getContentPane().add(minArrivalInput);
        maxArrival=new JLabel("Max arrival time:");
        maxArrivalInput=new JTextField(5);
        maxArrival.setBounds(100,380,100,50);
        maxArrivalInput.setBounds(100,430,100,20);
        getContentPane().add(maxArrival);
        getContentPane().add(maxArrivalInput);
        minProcessing=new JLabel("Min processing:");
        minProcessingInput=new JTextField(5);
        minProcessing.setBounds(100,450,100,50);
        minProcessingInput.setBounds(100,500,100,20);
        getContentPane().add(minProcessing);
        getContentPane().add(minProcessingInput);
        maxProcessing=new JLabel("Min processing:");
        maxProcessingInput=new JTextField(5);
        maxProcessing.setBounds(100,520,100,50);
        maxProcessingInput.setBounds(100,570,100,20);
        getContentPane().add(maxProcessing);
        getContentPane().add(maxProcessingInput);
        startButton = new JButton("Start Simulation");
        startButton.setBounds(75,620,150,50);
        getContentPane().add(startButton);
        getContentPane().add(currentTimeVal);
        setupQueues();
    }
    public void setupQueues()
    {
        for (int i=0;i<10;i++)
        {
            for (int j=0;j<15;j++)
            {
                if (j==0)
                {
                    queues[i][j]=new JLabel("Queue");
                }
                else
                {
                    queues[i][j]=new JLabel("");
                }
                queues[i][j].setBounds(300+70*j,200+50*i,70,25);
                getContentPane().add(queues[i][j]);
            }
        }
        setupResults();
    }

    // innen alol nem tudom mik ezek
    public void setupResults()
    {
        averageWait=new JLabel("Average Waiting Period");
        averageWaitValue=new JLabel();
        peakHour=new JLabel("Peak Moment");
        peakHourValue=new JLabel();
        averageService=new JLabel("Average Service time");
        averageServiceValue=new JLabel();
        averageService.setFont(resultFont);
        averageServiceValue.setFont(resultFont);
        averageWait.setFont(resultFont);
        peakHour.setFont(resultFont);
        averageWaitValue.setFont(resultFont);
        peakHourValue.setFont(resultFont);
        averageWait.setBounds(1175,150,200,50);
        averageWaitValue.setBounds(1175,200,100,50);
        averageService.setBounds(1175,300,200,50);
        averageServiceValue.setBounds(1175,400,100,50);
        peakHour.setBounds(1175,500,100,50);
        peakHourValue.setBounds(1175,600,100,50);
        getContentPane().add(averageWait);
        getContentPane().add(averageWaitValue);
        getContentPane().add(averageService);
        getContentPane().add(averageServiceValue);
        getContentPane().add(peakHour);
        getContentPane().add(peakHourValue);
    }
    public String createWaiting(List<Task> generatedTasks)
    {
        StringBuilder sb=new StringBuilder("Waiting: ");
        for (int i=0;i<generatedTasks.size();i++)
        {
            sb.append("(");
            sb.append(generatedTasks.get(i).getID());
            sb.append(",");
            sb.append(generatedTasks.get(i).getArrivalTime());
            sb.append(",");
            sb.append(generatedTasks.get(i).getServiceTime());
            sb.append("), ");
        }
        return sb.toString();
    }
    public void setupWaiting(List<Task> generatedTasks)
    {
        String list=new String();
        list=createWaiting(generatedTasks);
        waitingList=new JLabel(list);
        waitingList.setFont(resultFont);
        waitingList.setBounds(100,450,700,300);
        getContentPane().add(waitingList);
    }
    public void setCurrentTime(int time)
    {
        String times= Integer.toString(time);
        currentTimeVal.setText(times);
    }
    public void setQueue(int row,ArrayList<Task> tasks)
    {
        for (int j=0;j<tasks.size();j++)
        {
            StringBuilder builder=new StringBuilder();
            builder.append("(");
            builder.append(tasks.get(j).getID());
            builder.append(",");
            builder.append(tasks.get(j).getArrivalTime());
            builder.append(",");
            builder.append(tasks.get(j).getServiceTime());
            builder.append(")");
            String string=builder.toString();
            queues[row][j+1].setText(string);
        }
        for (int j=tasks.size();j<14;j++)
        {
            queues[row][j+1].setText("");
        }
    }
    public void setAverageWaitValue(float value)
    {
        String times= Float.toString(value);
        averageWaitValue.setText(times);
    }
    public void setPeakHourValue(int value)
    {
        String times= Integer.toString(value);
        peakHourValue.setText(times);
    }
    public void setAverageServiceTime(float value)
    {
        String times= Float.toString(value);
        averageServiceValue.setText(times);
    }
    public void addStartListener(ActionListener mal) {
        startButton.addActionListener(mal);
    }
}
