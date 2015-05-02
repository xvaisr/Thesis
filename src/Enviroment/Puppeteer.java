/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Enviroment;

import Agents.Team.Team;
import Enviroment.EnvObjects.Agents.Agent;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author lennylinux
 */
public class Puppeteer implements Runnable {

    private static final int ms2sec = 1000;
    private static final int SPEED = 30;
    
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
        int duration;
        double speed;
        
        synchronized(this) {
            this.running = true;
        } 
        
        now = System.currentTimeMillis();
        while (run) {
            begining = now;
            // get current unified speed, jut so speed won't change mid-step
            speed = this.unifiedSpeed; // volatile - should be safe
            
            // move everyone
            ArrayList<Team> teamList = Model.getTeamList();
            for (Team t : teamList) {
                for (Agent ag : t.getMemberList()) {
                    if (ag.getPosition().equals(ag.getDestinattion())) {
                        if (ag.getPosition().equals(new Point())) {
                            ag.setDestination(ag.getTeam().getHill().getPosition());
                        }
                        else {
                            ag.setDestination(new Point());
                        }
                    }
                    ag.move(speed);
                    // System.out.println(ag.getName() + " Position : " + ag.getPosition());
                }
            }
            
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
