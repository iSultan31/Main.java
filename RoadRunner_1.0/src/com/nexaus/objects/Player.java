package com.nexaus.objects;

import city.cs.engine.*;
import com.nexaus.helpers.StepAdapter;
import com.nexaus.levels.Stages;
import org.jbox2d.common.Vec2;
import com.nexaus.points.PointsHandler;

/**
 * Create a new DynamicBody for our character.
 */
public class Player extends DynamicBody {
	private static final Shape shape = new BoxShape(0.5f, 0.5f, new Vec2(-0.1f, 0));
	private static final Shape fingerShape = new BoxShape(0.2f, 0.2f, new Vec2(0.5f, 0.1f));
	private static final BodyImage walkingRightImage = new BodyImage("resources/PlayerImage/walking_right.gif");
	private static final BodyImage walkingLeftImage = new BodyImage("resources/PlayerImage/walking_left.gif");
	private static final BodyImage idleImage = new BodyImage("resources/PlayerImage/idle.gif");
	private static final BodyImage kisiLeft =new BodyImage("resources/PlayerImage/walking_left.gif");
	private static final BodyImage kisiRight =new BodyImage("resources/PlayerImage/walking_right.gif");

	private Stages stages;
	private boolean ninja = false;

	/**
	 * Create a new DynamicBody. Hulk!
	 *
	 * @param world The world that the Player should be added to.
	 */
	public Player(World world) {
		super(world);
		new SolidFixture(this, shape);
		new SolidFixture(this, fingerShape);

		this.addImage(idleImage);
		//this.set(true);

		// Deduct points when the Player hits a bad guy
		this.addCollisionListener(collisionEvent -> {
			if (collisionEvent.getOtherBody() instanceof Enemy) {
				if (ninja) {
					return;
				}

				if (stages != null) {
					stages.getCurrentLevel().restorePlayer();
					stages.getCurrentLevel().moveRoad(world);
				} else {
					System.out.println("ERROR: Levels data not specified.");
				}
				stages.getCurrentLevel().moveRoad(world);
				Enemy enemy = (Enemy) collisionEvent.getOtherBody();
				enemy.setLives(enemy.getLives() + 1);

				//PointsHandler.removePoints(10);
			}
		});

		// Make sure the correct image is being used
		world.addStepListener(new StepAdapter() {
			@Override
			public void preStep(StepEvent stepEvent) {
				super.preStep(stepEvent);

				Vec2 velocity = Player.this.getLinearVelocity();
				BodyImage currentImage = Player.this.getImages().get(0).getBodyImage();

				if (velocity.x > 0.1 && currentImage != walkingRightImage) {
					Player.this.addImage(ninja ? kisiRight : walkingRightImage);
				} else if (velocity.x < -0.1 && currentImage != walkingLeftImage) {
					Player.this.addImage(ninja ? kisiLeft : walkingLeftImage);
				} else if (Math.abs(velocity.x) < 0.1 && currentImage != idleImage) {
					Player.this.addImage(ninja ? kisiRight : idleImage);
				}
			}
		});
	}

	/**
	 * Add levels data: as Levels wants access to Player and Player wants
	 * access to Levels, this cannot be done in the constructor.
	 *
	 * @param stages The Levels object.
	 */
	public void addLevelsData(Stages stages) {
		this.stages = stages;
	}

	/**
	 * Turns the player into a ninja.
	 */
	public void ninja() {
		ninja = true;
	}

	/**
	 * Returns whether the player is a ninja or not.
	 *
	 * @return True if player is a ninja.
	 */
	public boolean isNinja() {
		return ninja;
	}
}
