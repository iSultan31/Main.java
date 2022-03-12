package com.nexaus.levels;

import city.cs.engine.Body;
import city.cs.engine.World;
import com.nexaus.objects.*;
import org.jbox2d.common.Vec2;
import com.nexaus.points.PointsHandler;

import java.util.ArrayList;
import java.util.Arrays;

public class Stage {
	private final float[] platformLengths;
	private final Vec2[] enemiesStart;
	private final Player player;
	private final Vec2 startPosition;

	private int remainingEnemies;
	private final ArrayList<LevelEventListener> listeners = new ArrayList<>();

	private final ArrayList<Body> bodyArray = new ArrayList<>();

	/**
	 * Create level with initial level data.
	 * @param platformLengths Array of platform length floats.
	 * @param enemiesStart    Array of Vec2 objects for enemy start positions.
	 * @param player          The player.
	 * @param startPosition   The start position of the player.
	 */
	public Stage(float[] platformLengths, Vec2[] enemiesStart, Player player, Vec2 startPosition) {
		this.platformLengths = platformLengths;
		this.enemiesStart = enemiesStart;
		this.player = player;
		this.startPosition = startPosition;

		remainingEnemies = enemiesStart.length;
	}

	/**
	 * Draw the level to the world.
	 *
	 * @param world The world to draw the level to.
	 */
	public void drawTo(World world) {
		// Build the walls
		bodyArray.addAll(Arrays.asList(
				FootpathGenerator.generateWall(world, 3f)
		));
		bodyArray.addAll(Arrays.asList(
						RoadGenerator.generateRoadLeft(world, 2f)));
		bodyArray.addAll(Arrays.asList(
				DividerGenerator.generateDividerLeft(world, 1f)));
		bodyArray.addAll(Arrays.asList(
				RoadGenerator.generateRoadCenter(world, 0f)));
		bodyArray.addAll(Arrays.asList(
				DividerGenerator.generateDividerRight(world, -1f)));
		bodyArray.addAll(Arrays.asList(
				RoadGenerator.generateRoadRight(world, -2f)));
		bodyArray.addAll(Arrays.asList(
				FootpathGenerator.generateWall(world, -3f)
		));

		// Create platforms
		for (int i = 0; i < platformLengths.length; i++) {
			Tile[] tiles = BottomGenerator.generate(world, platformLengths[i], 2.5f * i - 13.5f);
			bodyArray.addAll(Arrays.asList(tiles));
		}

		// Draw enemies
		Arrays.stream(enemiesStart).forEach(enemyPosition -> {
			Enemy enemy = new Enemy(world, player);
			//enemy.setPosition(enemyPosition);

			enemy.addCollisionListener(collisionEvent -> {
				Body otherBody = collisionEvent.getOtherBody();

				if (otherBody instanceof Bullet || (otherBody instanceof Player && ((Player) otherBody).isNinja())) {
					if (enemy.getLives() == 1) {
						enemy.destroy();
						remainingEnemies--;

						PointsHandler.addPoints(5);
					} else {
						enemy.setLives(enemy.getLives() - 1);
					}

					if (otherBody instanceof Bullet) {
						otherBody.destroy();
					}

					if (remainingEnemies == 0) {
						listeners.forEach(LevelEventListener::levelComplete);
					}
				}
			});

			bodyArray.add(enemy);
		});

		// Position Player
		restorePlayer();

		listeners.forEach(LevelEventListener::levelStart);
	}

	/**
	 * Remove the level from the world.
	 */
	public void destroy() {
		bodyArray.forEach(Body::destroy);
	}

	/**
	 * Moves the player back to the start point.
	 */
	public void restorePlayer() {
		player.setLinearVelocity(new Vec2(0, 0));
		player.setPosition(startPosition);
	}

	/**
	 * Adds an event listener.
	 *
	 * @param levelEventListener The listener to add.
	 */
	public void addEventListener(LevelEventListener levelEventListener) {
		listeners.add(levelEventListener);
	}

	/**
	 * Removes an event listener.
	 *
	 * @param levelEventListener The exact listener to remove.
	 */
	public void removeEventListener(LevelEventListener levelEventListener) {
		listeners.remove(levelEventListener);
	}

	public void moveRoad(World world) {
		System.out.println("The move road mthod is called.");
		bodyArray.addAll(Arrays.asList(
				RoadGenerator.generateRoadLeft(world, 2f)));
		bodyArray.addAll(Arrays.asList(
				DividerGenerator.generateDividerLeft(world, 1f)));
		bodyArray.addAll(Arrays.asList(
				RoadGenerator.generateRoadCenter(world, 0f)));
		bodyArray.addAll(Arrays.asList(
				DividerGenerator.generateDividerRight(world, -1f)));
		bodyArray.addAll(Arrays.asList(
				RoadGenerator.generateRoadRight(world, -2f)));
	}
}
