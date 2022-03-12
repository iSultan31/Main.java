package com.nexaus.points;

public interface PointsChangeListener {
	/**
	 * Fired when the number of points has changed.
	 *
	 * @param pointsChangeEvent The event.
	 * @see points.PointsChangeEvent
	 */
	public void changed(PointsChangeEvent pointsChangeEvent);
}
