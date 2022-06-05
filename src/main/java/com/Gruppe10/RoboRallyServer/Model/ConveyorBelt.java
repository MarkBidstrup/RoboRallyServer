package com.Gruppe10.RoboRallyServer.Model;


// @author Golbas Haidari
public class ConveyorBelt extends FieldAction {

    private Heading heading;
    private String color;
    public void setColor(String color){
        this.color=color;
    }

    public String getColor(){
        return color;
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    public ConveyorBelt(String color, Heading heading) {
        this.color = color;
        this.heading = heading;
    }

    @Override
    public boolean doAction() {
        return false;
    }
}