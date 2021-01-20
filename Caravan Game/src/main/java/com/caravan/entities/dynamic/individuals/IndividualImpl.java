package com.caravan.entities.dynamic.individuals;

import com.caravan.Role;
import com.caravan.entities.dynamic.DynamicImpl;

public class IndividualImpl extends DynamicImpl implements Individual {

    private boolean existent;
    private Role role;
    private int maxCapacity, velocity;
    private boolean inGroup;
    private double xCoordinate, yCoordinate;
    private byte[] battleSprite, caravanSprite;


    IndividualImpl(IndividualBuilder individualBuilder) {
        this.existent = true;
        this.role = individualBuilder.role;
        this.maxCapacity = individualBuilder.maxCapacity;
        this.velocity = individualBuilder.velocity;
        this.inGroup = individualBuilder.inGroup;
        this.xCoordinate = individualBuilder.xCoordinate;
        this.yCoordinate = individualBuilder.yCoordinate;

    }

    public void tick() {

    }

    @Override
    public void renderBattle() {

    }

    @Override
    public void renderCaravan() {

    }

    @Override
    public void renderMap() {
        // this method is only relevant for groups because individuals will always be in a group when traveling.
        if (!inGroup) {
            throw new IllegalStateException("Individual must be in group when traveling/map mode");
        }
    }

    @Override
    public boolean existent() {
        return existent;
    }

    @Override
    public Role getRole() {
        return role;
    }
}
