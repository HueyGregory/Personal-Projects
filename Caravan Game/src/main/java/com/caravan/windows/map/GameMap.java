package com.caravan.windows.map;

import com.caravan.entities.Entity;
import com.caravan.entities.dynamic.individuals.IndividualBuilder;
import com.caravan.play.NestedMap;
import com.caravan.windows.Screen;

import java.util.List;

public enum GameMap {
    INSTANCE;

    // where each entity is located in the entire game at a point in time.
    private static final NestedMap mapCoordinateEntityList = new NestedMap();
    private final int widthTile = Screen.getWidthTile(), heightTile = Screen.getHeightTile();	// tile-precision
    private final int radius = Math.min(widthTile, heightTile);
    private int startingX, startingY, width, height;

    private static final Entity mainEntity = IndividualBuilder.builder().build();

    public List<Entity> getEntities(int x, int y) {
        return mapCoordinateEntityList.getEntities(x, y);
    }

    public List<Entity> getEntitiesToRender() {
        return mapCoordinateEntityList.getEntitiesInCircularRange(mainEntity.getXCoordinate(), mainEntity.getYCoordinate(), radius, mainEntity.getSight());
    }

    public Entity getMainEntity() {
        return mainEntity;
    }

}
