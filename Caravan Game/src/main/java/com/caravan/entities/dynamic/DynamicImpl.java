package com.caravan.entities.dynamic;

import com.caravan.entities.EntityImpl;

public abstract class DynamicImpl extends EntityImpl implements Dynamic {

    private int velocity; // radius
    private double direction; // angle

    @Override
    public int getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }


    @Override
    public double getDirection() {
        return direction;
    }

    @Override
    public void setDirection(double direction) {
        if (direction > 360.0 || direction < 0) {
            throw new IllegalArgumentException();
        }
        this.direction = direction;
    }

    @Override
    public void moveOne() {
        setXCoordinate(getXCoordinate() + (velocity * Math.cos(direction)));
        setYCoordinate(getYCoordinate() + (velocity * Math.sin(direction)));
    }


}
