package com.caravan.entities;

public abstract class EntityImpl implements Entity {
    private int currentCapacity;
    private int maxCapacity;

    private long money;
    private double xCoordinate;
    private double yCoordinate;
    private int visibility;
    private int sight;


    @Override
    public int getCurrentCapacity() {
        return currentCapacity;
    }

    @Override
    public boolean addCapacity(int capacityAdded) {
        if (currentCapacity + capacityAdded > maxCapacity) {
            return false;
        }
        currentCapacity += capacityAdded;
        return true;
    }

    @Override
    public void subtractCapacity(int capacitySubtracted) {
        if (capacitySubtracted > currentCapacity) {
            throw new IllegalStateException("Capacity will be less than 0");
        }
        currentCapacity -= capacitySubtracted;
    }

    @Override
    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public void addMaxCapacity(int maxCapacityAdded) {
        maxCapacity += maxCapacityAdded;
    }

    @Override
    public void subtractMaxCapacity(int maxCapacitySubtracted) {
        if (maxCapacitySubtracted > maxCapacity) {
            throw new IllegalStateException("maxCapacitySubtracted is greater than maxCapacity");
        }
        maxCapacity -= maxCapacitySubtracted;
    }

    @Override
    public long getMoney() {
        return money;
    }

    @Override
    public void addMoney(long moneyAdded) {
        money += moneyAdded;
    }

    @Override
    public boolean subtractMoney(long moneySubtracted) {
        if (moneySubtracted > money) {
            return false;
        }
        money -= moneySubtracted;
        return true;
    }

    @Override
    public double getXCoordinate() {
        return xCoordinate;
    }

    @Override
    public double getYCoordinate() {
        return yCoordinate;
    }

    public void setXCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setYCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getVisibility() {
        return visibility;
    }

    public int getSight() {
        return sight;
    }
}
