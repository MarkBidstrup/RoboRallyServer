package com.Gruppe10.RoboRallyServer.Model;

public class Gear extends FieldAction{ // @author Xiao Chen
    private Heading heading;

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    public Gear(Heading heading) {
        this.heading = heading;
    }

    @Override
    public boolean doAction() {
        return false;
    }
}
