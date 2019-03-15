package main.render;

import main.GameLoop;
import main.entities.ObjectList;
import main.entities.Tile;

public class Screen {
	
	public static int TILESIZE = 32;	// one dimension of tile, meaning how many pixels in one tile in one dimension, so a two-dimensional tile would be == TILESIZE * TILESIZE
	
	private int[][] pixels;	// gathers all the colors of the pixels on the screen; in pixel-precision
	private int xTile, yTile, widthTile, heightTile;	// tile-precision
	private int xPixel, yPixel, widthPixel, heightPixel;	// pixel-precision 
//	private int xOffset, yOffset;	// pixel-precision
	
	public Screen(int widthPixel, int heightPixel) {
		this.widthTile = widthPixel/TILESIZE;
		this.heightTile = heightPixel/TILESIZE;
		this.widthPixel = widthPixel;
		this.heightPixel = heightPixel;
		this.pixels = new int[widthPixel][heightPixel];	// pixel-precision
	}

	public void renderTiles() {
		int xx = this.xTile;	// tile-precision; gets the pixel in the map[][] array, which represents each tile in the game
		for (int i = this.xPixel; i < (this.widthPixel + this.xPixel); i+=TILESIZE - i%TILESIZE) {	// pixel-precision
			int yy = this.yTile;
			for (int j = this.yPixel; j < (this.heightPixel + this.yPixel); j+=TILESIZE - j%TILESIZE) {
				if(xx < 0 || xx >= GameLoop.map.length || yy < 0 || yy >= GameLoop.map[xx].length) { 
					Tile.outsideVoidTile.render(i, j, this); 
					yy++;
					continue; 
				}
			 try {
					switch(GameLoop.map[xx][yy++]) {
					case RoadLeftRight: 	Tile.RoadLeftRight.render(i, j, this);		break;
					case RoadUpDown: 		Tile.RoadUpDown.render(i, j, this);			break;
					case RoadUpRight: 		Tile.RoadUpRight.render(i, j, this);		break;
					case RoadDownRight: 	Tile.RoadDownRight.render(i, j, this);		break;
					case RoadUpLeft: 		Tile.RoadUpLeft.render(i, j, this);			break;
					case RoadDownLeft: 		Tile.RoadDownLeft.render(i, j, this);		break;
					
					case RoadUp: 			Tile.RoadUp.render(i, j, this);				break;
					case RoadDown: 			Tile.RoadDown.render(i, j, this);			break;
					case RoadRight: 		Tile.RoadRight.render(i, j, this);			break;
					case RoadLeft: 			Tile.RoadLeft.render(i, j, this);			break;
					
					case RoadUpDownRight: 	Tile.RoadUpDownRight.render(i, j, this);	break;
					case RoadUpDownLeft: 	Tile.RoadUpDownLeft.render(i, j, this);		break;
					case RoadUpLeftRight: 	Tile.RoadUpLeftRight.render(i, j, this);	break;
					case RoadDownLeftRight: Tile.RoadDownLeftRight.render(i, j, this);	break;
					
					case BuildingFront: 	Tile.BuildingFront.render(i, j, this); 		break;
					case BuildingBack: 		Tile.BuildingBack.render(i, j, this);		break;
					case InsideVoidTile: 	Tile.insideVoidTile.render(i, j, this); 	break;
					
					default:				Tile.insideVoidTile.render(i, j, this);		break;
					}
				} catch (Exception e) {
					Tile.outsideVoidTile.render(i, j, this);
				}
			}
			xx++;
		}
		
	}

	public void clear() {
		for (int x = 0; x < this.widthPixel; x++) {
			for (int y = 0; y < this.heightPixel; y++) {
				this.pixels[x][y] = 0;
			}
		}
	}
	
	public int[][] getPixels() {	// pixel-precision
		return pixels;
	}

	public int getWidthTile() {
		return this.widthTile;
	}

	public int getHeightTile() {
		return this.heightTile;
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

	public int getWidthPixel() {
		return this.widthPixel;
	}

	public int getHeightPixel() {
		return this.heightPixel;
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

	public void renderEntities() {
	//	ObjectList.players.render(this);
		ObjectList.dynamicEntities.render(this);
		ObjectList.players.render(this);
		
	}
}
