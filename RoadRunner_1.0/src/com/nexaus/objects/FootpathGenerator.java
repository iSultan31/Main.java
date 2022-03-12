package com.nexaus.objects;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

/**
 * As you can only have one image per Body, this is hacky.
 */
public class FootpathGenerator {
	private FootpathGenerator() {}

	private static final BodyImage wallTop = new BodyImage("resources/footpath_images/footpath_top.png");
	private static final BodyImage wallMiddle = new BodyImage("resources/footpath_images/footpath_middle.png");
	private static final BodyImage wallBottomLeft = new BodyImage("resources/footpath_images/footpath_corner_left.png");
	private static final BodyImage wallBottomRight = new BodyImage("resources/footpath_images/footpath_right.png");

	private static final Shape shape = new BoxShape(0.5f, 0.5f);

	/**
	 * Generates a vertical wall out of tiles.
	 *
	 * @param world The world to create the wall on.
	 * @param x     The x position of the wall.
	 * @return      An array of Tiles that make the wall.
	 */
	public static Tile[] generateWall(World world, float x) {
		Tile[] tiles = new Tile[20];

		for (int i = 0; i < 20; i++) {
			Tile tile = new Tile(world, shape);
			tile.setKillBullets(true);
			tile.setPosition(new Vec2(x, 9 - i));

			if (i == 0) {
				tile.addImage(wallTop);
			} else if (i == 19) {
				tile.addImage(x < 0 ? wallBottomLeft : wallBottomRight);
			} else {
				tile.addImage(wallMiddle);
			}

			tiles[i] = tile;
		}

		return tiles;
	}
}
