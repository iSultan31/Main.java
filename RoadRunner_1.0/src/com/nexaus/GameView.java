package com.nexaus;

import city.cs.engine.UserView;
import city.cs.engine.World;
import com.nexaus.objects.Player;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameView extends UserView {
	private final Player player;

	private static final Image backgroundImage = Toolkit.getDefaultToolkit().getImage("resources/game_background.jpg");
	private static final Image closeButton = Toolkit.getDefaultToolkit().getImage("resources/close.png");

	public GameView(World world, int i, int i2, Player player) {
		super(world, i, i2);

		this.player = player;

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				if (e.getButton() != 1) {
					return;
				}

				// Close button
				if (Math.abs(e.getX() - 15) < 10 && Math.abs(e.getY() - 15) < 10) {
					System.exit(0);
				}
			}
		});
	}

	@Override
	protected void paintBackground(Graphics2D graphics2D) {
		Vec2 playerPosition = player.getPosition();

		// Calls to Math.max and Math.min to ensure that the image doesn't go off screen
		int backgroundX = Math.max(-400, Math.min(0, -100 - (int) (playerPosition.x * 10)));
		int backgroundY = Math.max(-260, Math.min(0, -100 + (int) (playerPosition.y * 10)));

		graphics2D.drawImage(backgroundImage, backgroundX, backgroundY, this);
	}

	@Override
	protected void paintForeground(Graphics2D graphics2D) {
		graphics2D.drawImage(closeButton, 5, 5, 20, 20, this);
	}
}
