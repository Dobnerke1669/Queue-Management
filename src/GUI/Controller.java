package GUI;

import Business_Logic.SimulationManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Controller {
    private SimulationFrame frame;

    public Controller(SimulationFrame view) {
        frame = view;
        view.addStartListener(new StartListener());
    }

    public class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int minProcess,maxProcess,minArrival,maxArrival,timeLimit,nrClients,nrServers;
            minProcess=Integer.parseInt(frame.minProcessingInput.getText());
            maxProcess=Integer.parseInt(frame.maxProcessingInput.getText());
            minArrival=Integer.parseInt(frame.minArrivalInput.getText());
            maxArrival=Integer.parseInt(frame.maxArrivalInput.getText());
            timeLimit=Integer.parseInt(frame.timeSimulationInput.getText());
            nrClients=Integer.parseInt(frame.nrOfTasksInput.getText());
            nrServers=Integer.parseInt(frame.nrOfServersInput.getText());
            SimulationManager manager=new SimulationManager(minProcess,maxProcess,nrClients,nrServers,minArrival,maxArrival,timeLimit);
            Thread thread=new Thread(manager);
            thread.start();
        }

    }
}