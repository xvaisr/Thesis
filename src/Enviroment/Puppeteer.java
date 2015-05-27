/**
 * Thesis project, BP, anthill strategy game refactored
 *
 * @author  Roman Vais, xvaisr00
 * @date    2015/05/27
 */
package Enviroment;

import Agents.Team.Team;
import Enviroment.EnvObjFeatures.Senses.Sense;
import Enviroment.EnvObjects.Agents.Agent;
import jason.asSyntax.Literal;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author lennylinux
 */
public class Puppeteer implements Runnable {

    private static final int ms2sec = 1000;
    private static final int SPEED = 20;
    
    private volatile boolean running;
    private volatile double unifiedSpeed;            // pixels distance per step
    private volatile int stepLenghtMs;               // how long to wait before next step 

    public Puppeteer() {
        this.running = false;
        this.unifiedSpeed = SPEED / 60.0;
        this.stepLenghtMs = ms2sec / 60;
    }

    public void setStepsPerSec(int stepCount) {
        // at least 1 step per second
        if (stepCount <= 0) {
            stepCount = 1;
        }
        
        this.stepLenghtMs = ms2sec / stepCount;
    }
    
    public void setBaseSpeed(int pxPerSec) {
        // at least one pixel per second
        if (pxPerSec <= 0) {
            pxPerSec = 1;
        }

        this.unifiedSpeed = pxPerSec / ((double) this.stepLenghtMs);
    }
    
    private void stop() {
        synchronized(this) {
            this.running = false;
        }
    }
    
    @Override
    public void run() {
        long begining, now, delta;
        boolean run = true;
        int duration, counter = 0;
        double speed;
        
        synchronized(this) {
            this.running = true;
        } 
        ArrayList<Team> teamList = Model.getTeamList();
        boolean g = Model.getInstance().getEnviromentalControler()
                        .getEnvironmentInfraTier().isRunning();
        System.err.println(g);
        for (Team t : teamList) {
            for (Agent ag : t.getMemberList()) {
                System.err.println(ag.getName());
                Model.getInstance().getEnviromentalControler()
                        .getEnvironmentInfraTier().getRuntimeServices().startAgent(ag.getName());
            }
        }
        now = System.currentTimeMillis();
        while (run) {
            if (counter > SPEED) {
                counter = 0;
            }
            begining = now;
            // get current unified speed, jut so speed won't change mid-step
            speed = this.unifiedSpeed; // volatile - should be safe
            
            // move everyone
            teamList = Model.getTeamList();
            for (Team t : teamList) {
                for (Agent ag : t.getMemberList()) {
                    LinkedList<String> percepts = new LinkedList();
                    if (ag.getPosition().equals(ag.getDestinattion())) {
                        percepts.add("finishedMovement");
                    }
                    else {
                        ag.move(speed);
                    }
                    for(Sense s : ag.getSenseList()) {
                        if (counter == SPEED) {
                            s.updatePreception();
                        }
                        percepts.addAll(s.getPercepts());
                    }
                    EnvirometControler env = Model.getInstance().getEnviromentalControler();
                    for(String str : percepts) {
                        env.addPercept(ag.getName(), Literal.parseLiteral(str));
                    }
                }
            }
            
            counter++;
            now = System.currentTimeMillis();
            
            
            delta = now - begining;
            duration = this.stepLenghtMs; // volatile - should be save
            
            if (delta < duration) {
                try {
                    Thread.sleep(duration - delta);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            synchronized(this) { // synchronized jutt to be 100% sure
                run = this.running;
            }
        }
    }
    
}
