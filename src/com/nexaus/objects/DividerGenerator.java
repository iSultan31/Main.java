package com.nexaus.objects;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

/**
 * As you can only have one image per Body, this is hacky.
 */
public class DividerGenerator {
	private DividerGenerator() {}
	private static int left_offset=0;
	private static int right_offset=0;
	private static final BodyImage road = new BodyImage("resources/footpath_images/Road.png");
	private static final BodyImage divider = new BodyImage("resources/footpath_images/Divider.png");
	private static final Shape shape = new BoxShape(0.5f, 0.5f);

	/**
	 * Generates a vertical wall out of tiles.
	 *
	 * @param world The world to create the wall on.
	 * @param x     The x position of the wall.
	 * @return      An array of Tiles that make the wall.
	 */
	public static TileDynamic[] generateDividerLeft(World world, float x) {
		TileDynamic[] tiles = new TileDynamic[18];

		for (int i = 0; i < 18; i++) {
			TileDynamic tile = new TileDynamic(world, shape);
			tile.setKillBullets(true);
			tile.setPosition(new Vec2(x, 9 - i));
			tile.addImage(road);
			if ((i+left_offset)%5 == 0) {
				tile.addImage(road);
			}else {
				tile.addImage(divider);
			}


			tiles[i] = tile;
		}
		left_offset=left_offset+1;
		return tiles;
	}
	public static TileDynamic[] generateDividerRight(World world, float x) {
		TileDynamic[] tiles = new TileDynamic[18];

		for (int i = 0; i < 18; i++) {
			TileDynamic tile = new TileDynamic(world, shape);
			tile.setKillBullets(true);
			tile.setPosition(new Vec2(x, 9 - i));
			tile.addImage(road);
			if ((i+right_offset)%5 == 0) {
				tile.addImage(road);
			}else {
				tile.addImage(divider);
			}


			tiles[i] = tile;
		}
		right_offset=right_offset+1;
		return tiles;
	}
}
