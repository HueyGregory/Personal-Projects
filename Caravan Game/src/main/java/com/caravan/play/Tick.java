package com.caravan.play;

import com.caravan.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public enum Tick {
    INSTANCE;

    List<Entity> entities = new ArrayList<>();

    void tick() {
        entities.forEach(entity -> {
            if (!entity.existent()) {
                entities.remove(entity);
            }
            else {
                entity.tick();
            }
        });
    }

}
