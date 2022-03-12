package com.nexaus.objects;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;


public class BottomGenerator {
	private BottomGenerator() {}

	private static final BodyImage bottomLeft = new BodyImage("resources/footpath_images/bottom_left.png");
	private static final BodyImage bottomCentre = new BodyImage("resources/footpath_images/bottom_centre.png");
	private static final BodyImage bottomRight = new BodyImage("resources/footpath_images/bottom_right.png");

	private static final Shape bottom_shape = new BoxShape(0.5f, 0.5f);


	public static Tile[] generate(World world, float length, float y) {
		Tile[] tiles = new Tile[(int) Math.floor(Math.abs(length) * 2 + 1)];

		for (int i = -1; i < Math.abs(length) * 2; i++) {
			Tile tile = new Tile(world, bottom_shape);
			tile.setPosition(new Vec2((3f - (i == -1 ? -0.5f : i)) * (length > 0 ? 1 : -1), y));

			if (i == length * 2 - 1 && length != 6) {
				tile.addImage(bottomLeft);
			} else if (-i == length * 2 + 1) {
				tile.addImage(bottomRight);
			} else {
				tile.addImage(bottomCentre);
			}

			tiles[i + 1] = tile;
		}

		return tiles;
	}
}
