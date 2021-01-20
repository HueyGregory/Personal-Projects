package com.caravan.play;

import com.caravan.entities.Entity;
import com.caravan.windows.Screen;
import com.caravan.windows.State;
import com.caravan.windows.map.GameMap;

import java.util.List;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public enum Render {
    INSTANCE;

    //private static final Map<Integer, LinkedHashMap<Integer, BasicObject>> battlefieldCoordinateBasicObjectList = new LinkedHashMap<>();

    private Screen screen;
    private BufferStrategy bufferStrategy;
    private final int widthPixel = Screen.getWidthPixel(), heightPixel = Screen.getHeightPixel(), scale = Screen.getScale();

    private final BufferedImage bufferedImage = new BufferedImage(widthPixel, heightPixel, BufferedImage.TYPE_INT_RGB);
    private final int[] imagePixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();

    void setScreen(Screen screen) {
        this.screen = screen;
        this.screen.renderBackground(imagePixels);
    }

    void setBufferStrategy(BufferStrategy bufferStrategy) {
        this.bufferStrategy = bufferStrategy;
    }

    List<Entity> render(List<Entity> oldEntities, Graphics2D graphics2D) {
        if (GameLoop.getCurrentState() == State.TRAVELING) {
            return renderMap(oldEntities, graphics2D);
        }
        else if (GameLoop.getCurrentState() == State.BATTLE) {
            return renderBattleField(oldEntities);
        }
        else if (GameLoop.getCurrentState() == State.CARAVAN) {
            return renderMenu(oldEntities);
        }
        return null;
    }

    List<Entity> renderMap(List<Entity> oldEntities, Graphics2D graphics2D) {
        if (oldEntities != null) {
            // this means that there has been no update to the location of the entities
            screen.clearMapEntities(imagePixels, oldEntities, graphics2D);
            oldEntities = screen.renderMapEntities(imagePixels, graphics2D);
        } else {
            oldEntities = GameMap.INSTANCE.getEntitiesToRender();
        }
        graphics2D.drawImage(bufferedImage, 0, 0, widthPixel *scale, heightPixel *scale, null);
        //graphics2D.dispose();
        bufferStrategy.show();
        return oldEntities;
    }

    List<Entity> renderBattleField(List<Entity> oldEntities) {
        return null;
    }

    List<Entity> renderMenu(List<Entity> oldEntities) {
        return null;
    }

    void updateCoordinate(int previousX, int previousY, int x, int y, Entity entity) {
        //mapCoordinateEntityList.removeEntity(previousX, previousY, entity);
    }

    void removeEntity() {

    }
}
