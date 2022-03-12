package com.nexaus.objects;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * A DynamicBody which gets in the way. Hint: shoot it to knock it down!
 */
public class Gate extends DynamicBody {
	private static final Shape pivotShape = new CircleShape(0.1f, new Vec2(0, -0.4f));
	private static final Shape gateShape = new BoxShape(0.05f, 0.5f, new Vec2(0, 0.05f));

	/**
	 * Creates a new Gate.
	 *
	 * @param world The world to insert the Gate into.
	 */
	public Gate(World world) {
		super(world);

		new SolidFixture(this, pivotShape, 200);
		new SolidFixture(this, gateShape, 1);
	}
}
