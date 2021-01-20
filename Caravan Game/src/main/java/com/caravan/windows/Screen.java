package com.caravan.windows;

import com.caravan.entities.Entity;
import com.caravan.windows.map.GameMap;

import java.awt.*;
import java.util.List;

public class Screen {

    private static final int TILESIZE = 32;	// one dimension of tile, meaning how many pixels in one tile in one dimension, so a two-dimensional tile would be == TILESIZE * TILESIZE
    private final int ENTITY_MAP_RADIUS = 16;
    private int xPixel, yPixel;
    private static final int widthPixel = 300, heightPixel = 50, scale = 3;	// pixel-precision
    private int xTile, yTile;
    private static final int widthTile = widthPixel/TILESIZE, heightTile = heightPixel/TILESIZE;	// tile-precision
    private final int radius = Math.min(widthTile, heightTile);

    public static int getScale() {
        return scale;
    }

    public void renderBackground(int[] imagePixels) {
        double playerX = GameMap.INSTANCE.getMainEntity().getXCoordinate();
        double playerY = GameMap.INSTANCE.getMainEntity().getYCoordinate();
        int xx = xTile;	// tile-precision; gets the pixel in the map[][] array, which represents each tile in the game
        // for each tile, get the entity
        for (int x = xTile; x < xTile + widthTile; x++) {
            for (int y = yTile; y < yTile + heightTile; y++) {
                int i = 0;
                for (int pixelX = x * TILESIZE; pixelX < (x + 1) * TILESIZE && i <= TILESIZE; pixelX++) {
                    int j = 0;
                    for (int pixelY = y * TILESIZE; pixelY < (y + 1) * TILESIZE && j <= TILESIZE; pixelY++) {
                        imagePixels[pixelX + pixelY * widthPixel] = //entity.render(i, j);

                        j++;
                    }
                    i++;
                }
            }
        }
            // render each pixel of that entity
        for (int i = this.xPixel; i < (this.widthPixel + this.xPixel); i+=TILESIZE - i%TILESIZE) {	// pixel-precision
            int yy = this.yTile;
//            for (int j = this.yPixel; j < (this.heightPixel + this.yPixel); j+=TILESIZE - j%TILESIZE) {
//                if(xx < 0 || xx >= GameLoop.map.length || yy < 0 || yy >= GameLoop.map[xx].length) {
//                    Tile.outsideVoidTile.render(i, j, this);
//                    yy++;
//                    continue;
//                }
//                try {
//                    switch(GameLoop.map[xx][yy++]) {
//                        case RoadLeftRight: 	Tile.RoadLeftRight.render(i, j, this);		break;
//                        case RoadUpDown: 		Tile.RoadUpDown.render(i, j, this);			break;
//                        case RoadUpRight: 		Tile.RoadUpRight.render(i, j, this);		break;
//                        case RoadDownRight: 	Tile.RoadDownRight.render(i, j, this);		break;
//                        case RoadUpLeft: 		Tile.RoadUpLeft.render(i, j, this);			break;
//                        case RoadDownLeft: 		Tile.RoadDownLeft.render(i, j, this);		break;
//
//                        case RoadUp: 			Tile.RoadUp.render(i, j, this);				break;
//                        case RoadDown: 			Tile.RoadDown.render(i, j, this);			break;
//                        case RoadRight: 		Tile.RoadRight.render(i, j, this);			break;
//                        case RoadLeft: 			Tile.RoadLeft.render(i, j, this);			break;
//
//                        case RoadUpDownRight: 	Tile.RoadUpDownRight.render(i, j, this);	break;
//                        case RoadUpDownLeft: 	Tile.RoadUpDownLeft.render(i, j, this);		break;
//                        case RoadUpLeftRight: 	Tile.RoadUpLeftRight.render(i, j, this);	break;
//                        case RoadDownLeftRight: Tile.RoadDownLeftRight.render(i, j, this);	break;
//
//                        case BuildingFront: 	Tile.BuildingFront.render(i, j, this); 		break;
//                        case BuildingBack: 		Tile.BuildingBack.render(i, j, this);		break;
//                        case InsideVoidTile: 	Tile.insideVoidTile.render(i, j, this); 	break;
//
//                        default:				Tile.insideVoidTile.render(i, j, this);		break;
//                    }
//                } catch (Exception e) {
//                    Tile.outsideVoidTile.render(i, j, this);
//                }
//            }
            xx++;
        }

    }

    public void clear() {
    }

    public static int getWidthTile() {
        return widthTile;
    }

    public static int getHeightTile() {
        return heightTile;
    }

    public int getXTile() {
        return this.xTile;
    }
    public void setCoordinatesTile(int x, int y) {
        this.xTile = x;
        this.yTile = y;
        this.xPixel = this.xTile*TILESIZE;
        this.yPixel = this.yTile*TILESIZE;
    }
    public int getYTile() {
        return this.yTile;
    }

    public static int getWidthPixel() {
        return widthPixel;
    }

    public static int getHeightPixel() {
        return heightPixel;
    }

    public int getXPixel() {
        return this.xPixel;
    }
    public void setCoordinatesPixel(double x, double y) {
        this.xPixel = (int) x;
        this.yPixel = (int) y;
        this.xTile = this.xPixel/TILESIZE;
        this.yTile = this.yPixel/TILESIZE;
    }
    public int getYPixel() {
        return this.yPixel;
    }

    public List<Entity> renderMapEntities(int[] imagePixels, Graphics2D g) {
        List<Entity> oldEntities = GameMap.INSTANCE.getEntitiesToRender();
        oldEntities.forEach(entity -> g.drawOval((int) (entity.getXCoordinate() * TILESIZE), (int) (entity.getYCoordinate() * TILESIZE), ENTITY_MAP_RADIUS, ENTITY_MAP_RADIUS));
        return oldEntities;
    }

    public void clearMapEntities(int[] imagePixels, List<Entity> oldEntities, Graphics2D g) {
        if (oldEntities != null) {
            oldEntities.forEach(entity -> {

//                entity.
            });
            return;
        }

    }
}
