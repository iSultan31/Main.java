package com.nexaus;

import city.cs.engine.StepEvent;
import city.cs.engine.World;
import com.nexaus.helpers.StepAdapter;
import com.nexaus.levels.Stages;
import com.nexaus.objects.Player;
import org.jbox2d.common.Vec2;
import com.nexaus.points.PointsHandler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Handle all key events in the game.
 */
public class GameKeyDispatcher extends KeyAdapter {
	private final Player player;
	private final World world;

	private int horizontal;
	private boolean cheat = false;

	private static final LinkedList<Integer> konamiKeys = new LinkedList<>(
			Arrays.asList(38, 38, 40, 40, 37, 39, 37, 39, 66, 65)
	);
	private final LinkedList<Integer> lastKeys = new LinkedList<>();

	/**
	 * Set up the event handler.
	 *
	 * @param player	The main character.
	 * @param world     The world.
	 * @param stages    Levels object.
	 */
	public GameKeyDispatcher(Player player, World world, final Stages stages) {
		this.player = player;
		this.world = world;

		// Reapply force on ever step to simulate a continuous force.
		world.addStepListener(new StepAdapter() {
			@Override
			public void preStep(StepEvent stepEvent) {
				if (stages.getInactive()) {
					return;
				}

				GameKeyDispatcher.this.player.applyForce(new Vec2(horizontal, 0));

				// If player is off screen, put them back to their startPosition.
				Vec2 position = GameKeyDispatcher.this.player.getPosition();

			 	if (Math.abs(position.x) > 300 || position.y < -350) {
					stages.getCurrentLevel().restorePlayer();
					PointsHandler.removePoints(10);
				}

			}
		});
	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);

		konamiHandler(e.getKeyCode());

		switch (e.getKeyCode()) {

			/*// Throw a bullet
			case KeyEvent.VK_SPACE:
				Vec2 playerVelocity = player.getLinearVelocity();
				float xVelocity = playerVelocity.x + 12 * (playerVelocity.x >= 0 ? 1 : -1);

				Vec2 position = player.getPosition();
				position.x += 0.3f * (playerVelocity.x >= 0 ? 1 : -1) + xVelocity / 20;
				position.y += 0.5f;

				final DynamicBody bullet = new Bullet(world, cheat);
				bullet.setPosition(position);
				bullet.setLinearVelocity(new Vec2(xVelocity, 0));

				if (!cheat) {
					PointsHandler.removePoints(0.5f);
				}
				break; */

			// Make the main character jump
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				Vec2 currentVelocity = player.getLinearVelocity();

				// For some reason, this takes a while to get to 0
				if (Math.abs(currentVelocity.y) < 0.001) {
					player.setLinearVelocity(new Vec2(currentVelocity.x, 25));
				}

				break;

			// Apply a horizontal force
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				this.horizontal = -50;
				break;

			// Apply a horizontal force
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				this.horizontal = 50;
				break;

			// Add points
			case KeyEvent.VK_P:
				if (cheat) {
					PointsHandler.addPoints(10);
				}
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);

		switch (e.getKeyCode()) {

			// Cancel the force when the left or right key is released.
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_A:
			case KeyEvent.VK_D:
				this.horizontal = 0;
				break;
		}
	}

	private void konamiHandler(int keyCode) {
		lastKeys.add(keyCode);

		// No point in checking again
		if (cheat) {
			return;
		}

		if (lastKeys.size() > konamiKeys.size()) {
			lastKeys.remove();
		}

		if (lastKeys.equals(konamiKeys)) {
			cheat = true;
			player.ninja();
			world.setGravity(50);
		}
	}
}