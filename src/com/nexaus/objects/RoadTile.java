package com.nexaus.objects;

import city.cs.engine.*;
import com.nexaus.helpers.StepAdapter;
import com.nexaus.levels.Stages;
import com.nexaus.points.PointsHandler;
import org.jbox2d.common.Vec2;

public class RoadTile extends DynamicBody {

        private static final Shape shape = new BoxShape(0.5f, 0.5f, new Vec2(-0.1f, 0));
        private static final Shape fingerShape = new BoxShape(0.2f, 0.2f, new Vec2(0.5f, 0.1f));
        private static final BodyImage roadTile = new BodyImage("resources/footpath_images/Road.png");

        private Stages stages;

        /**
         * Create a new DynamicBody. Hulk!
         *
         * @param world The world that the Player should be added to.
         */
	public RoadTile(World world) {
        super(world);
        new SolidFixture(this, shape);
        new SolidFixture(this, fingerShape);

        this.addImage(roadTile);
        //this.set(true);

        // Deduct points when the Player hits a bad guy
        this.addCollisionListener(collisionEvent -> {
            if (collisionEvent.getOtherBody() instanceof Enemy) {

                if (stages != null) {
                    stages.getCurrentLevel().restorePlayer();
                } else {
                    System.out.println("ERROR: Levels data not specified.");
                }

                Enemy enemy = (Enemy) collisionEvent.getOtherBody();
                enemy.setLives(enemy.getLives() + 1);

                PointsHandler.removePoints(10);
            }
        });

        // Make sure the correct image is being used
        world.addStepListener(new StepAdapter() {
            @Override
            public void preStep(StepEvent stepEvent) {
                super.preStep(stepEvent);

                Vec2 velocity = RoadTile.this.getLinearVelocity();
                BodyImage currentImage = RoadTile.this.getImages().get(0).getBodyImage();
                /*
                if (velocity.x > 0.1 && currentImage != walkingRightImage) {
                    RoadTile.this.addImage(ninja ? kisiRight : walkingRightImage);
                } else if (velocity.x < -0.1 && currentImage != walkingLeftImage) {
                    RoadTile.this.addImage(ninja ? kisiLeft : walkingLeftImage);
                } else if (Math.abs(velocity.x) < 0.1 && currentImage != idleImage) {
                    RoadTile.this.addImage(ninja ? kisiRight : idleImage);
                }
                *
                 */
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





}
