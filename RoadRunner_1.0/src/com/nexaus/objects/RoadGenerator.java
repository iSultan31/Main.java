package com.nexaus.objects;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

/**
 * As you can only have one image per Body, this is hacky.
 */
public class RoadGenerator {
	private RoadGenerator() {}

	private static final BodyImage road = new BodyImage("resources/footpath_images/Road.png");

	private static final Shape shape = new BoxShape(0.5f, 0.5f);

	/**
	 * Generates a vertical wall out of tiles.
	 *
	 * @param world The world to create the wall on.
	 * @param x     The x position of the wall.
	 * @return      An array of Tiles that make the wall.
	 */
	public static Tile[] generateWall(World world, float x) {
		Tile[] tiles = new Tile[18];

		for (int i = 0; i < 18; i++) {
			Tile tile = new Tile(world, shape);
			tile.setKillBullets(true);
			tile.setPosition(new Vec2(x, 9 - i));
			tile.addImage(road);


			tiles[i] = tile;
		}

		return tiles;
	}
}
