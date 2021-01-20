package com.caravan.entities.dynamic.individuals;

import com.caravan.Role;
import com.caravan.entities.Entity;

public class IndividualBuilder {

    protected Role role;
    protected int maxCapacity, velocity;
    protected boolean inGroup;
    protected double xCoordinate, yCoordinate;

    public static IndividualBuilder builder() {
        return new IndividualBuilder();
    }

    public IndividualBuilder role(Role role) {
        this.role = role;
        return this;
    }

    public IndividualBuilder maxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        return this;
    }

    public IndividualBuilder velocity(int velocity) {
        this.velocity = velocity;
        return this;
    }

    public IndividualBuilder inGroup(boolean inGroup) {
        this.inGroup = inGroup;
        return this;
    }

    public IndividualBuilder xCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
        return this;
    }

    public IndividualBuilder yCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
        return this;
    }

    public Entity build() {
        return new IndividualImpl(this);
    }



}
