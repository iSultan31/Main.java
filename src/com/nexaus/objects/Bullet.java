package com.nexaus.objects;

import city.cs.engine.*;

/**
 * Create a DynamicBody in the shape of an image. Handles appearance and
 * collisions, but does not handle position and velocity.
 */
public class Bullet extends DynamicBody {
	private static final Shape bullet = new CircleShape(0.1f);

	/**
	 * Creates the bullet.
	 *
	 * @param world The world to add the bullet to.
	 * @param cheat If true, bullet will bounce off walls.
	 */
	public Bullet(World world, final boolean cheat) {
		super(world);

		this.addCollisionListener(collisionEvent -> {
			Body otherBody = collisionEvent.getOtherBody();

			if (otherBody instanceof Player) {
				this.destroy();
			}

			if (otherBody instanceof Tile && ((Tile) otherBody).getKillBullets() && !cheat) {
				this.destroy();
			}
		});

		SolidFixture fixture = new SolidFixture(this, bullet, 20);
		fixture.setRestitution(1); // Bouncy bullets!
		fixture.setFriction(0);
	}
}
