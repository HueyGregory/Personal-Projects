package com.caravan.entities;

import com.caravan.BasicObject;

// consists of health, money, supplies, and movement information
public interface Entity extends BasicObject {

    int getCurrentCapacity();
    boolean addCapacity(int capacityAdded);
    void subtractCapacity(int capacitySubtracted);

    int getMaxCapacity();
    void addMaxCapacity(int maxCapacityAdded);
    void subtractMaxCapacity(int maxCapacitySubtracted);

    long getMoney();
    void addMoney(long moneyAdded);
    boolean subtractMoney(long moneySubtracted);

    void tick();
    void renderBattle();
    void renderCaravan();
    void renderMap();

    boolean existent();

    int getVisibility();
    int getSight();
}
