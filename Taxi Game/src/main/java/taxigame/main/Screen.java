/*package main;

import entities.ObjectList;
import entities.Tile;

public class Screen {
	
	public static int TILESIZE = 16;	// one dimension of tile, meaning how many pixels in one tile in one dimension, so a two-dimensional tile would be == TILESIZE * TILESIZE
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
		for (int i = this.xPixel; i < (this.widthPixel + this.xPixel); i+=TILESIZE - i%16) {	// pixel-precision
			int yy = this.yTile;
			for (int j = this.yPixel; j < (this.heightPixel + this.yPixel); j+=TILESIZE - j%16) {
				if(xx < 0 || xx >= GameLoop.map.length || yy < 0 || yy >= GameLoop.map[xx].length) { 
					Tile.outsideVoidTile.render(i, j, this); 
					yy++;
					continue; 
				}
			 try {
					switch(GameLoop.map[xx][yy++]) {
					case -1: 	Tile.insideVoidTile.render(i, j, this); 	break;
					case 0: 	 	break;
					//case 1: 	Tile.tileRoadLane.render(i, j, this); 				break;
					//case 2: 	Tile.tileRoadWithSideWalkUp.render(i, j, this);		break;
					//case 3: 	Tile.tileRoadWithSideWalkDown.render(i, j, this);		break;
					default:	Tile.insideVoidTile.render(i, j, this);					break;
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
		
	}
}*/
