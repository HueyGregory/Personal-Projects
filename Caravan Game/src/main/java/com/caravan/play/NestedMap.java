package com.caravan.play;

import com.caravan.entities.Entity;

import java.util.*;
import java.util.stream.Collectors;

// Writing is synchronized but not reading from a list.
// Locking granularity is on the list level (i.e. when entities have same coordinates),
// but not on entire map level.
// Although this may result in corrupted data, speed is more important.
public class NestedMap {

    private final Map<Coordinate, List<Entity>> coordinateEntityList;

    public NestedMap() {
        coordinateEntityList = new LinkedHashMap<>();
    }

    public void removeEntity(int x, int y, Entity entity) {
        List<Entity> entities = getEntities(x, y);
        entities.remove(entity);
        if (entities.isEmpty()) {
            coordinateEntityList.remove(new Coordinate(x, y), entities);
        }
    }

    public void addEntity(int x, int y, Entity entity) {
        List<Entity> entities = getEntities(x, y);
        if (entities == null) {
            entities = Collections.synchronizedList(new ArrayList<>());
        }
        coordinateEntityList.put(new Coordinate(x, y), entities);
        entities.add(entity);
    }

    public void updateEntity(int previousX, int previousY, int x, int y, Entity entity) {
        removeEntity(previousX, previousY, entity);
        addEntity(x, y, entity);
    }

    public List<Entity> getEntities(int x, int y) {
        return coordinateEntityList.get(new Coordinate(x, y));
    }

    // used by render with user's x, y position as starting point
    public List<Entity> getEntitiesInCircularRange(double x, double y, int maxRange, int sight) {
        List<Entity> entitiesInRange = new ArrayList<>();
        for (double direction = 0; direction < 360; direction += 0.5) {
            for (int radius = 1; radius < maxRange; radius++) {
                List<Entity> entities = getEntities((int) (x + (radius * Math.cos(direction))), (int) (y + (radius * Math.sin(direction))));
                if (entities != null) {
                    int currRadius = radius;
                    entitiesInRange.addAll(entities.stream().filter(entity -> entity.getVisibility() * (1/currRadius) < sight).collect(Collectors.toList()));
                }
            }
        }
        return entitiesInRange;
    }

    private static class Coordinate {
        int x, y;
        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Coordinate)) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
