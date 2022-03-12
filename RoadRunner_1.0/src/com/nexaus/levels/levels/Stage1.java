package com.nexaus.levels.levels;

import com.nexaus.levels.Stage;
import com.nexaus.levels.LevelEventListener;
import com.nexaus.objects.Player;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;

/**
 * A level. Would be better in Levels.java, but requirements.
 */
public class Stage1 extends Stage {
	private final static float[] platforms = {4, 4};
	private final static Vec2[] enemies = {
			new Vec2(5, -4.5f)
	};

	public Stage1(Player player, final JLabel levelLabel) {
		super(platforms, enemies, player, new Vec2(0, -9));

		// Display instructions
		this.addEventListener(new LevelEventListener() {
			Font oldFont;
			String oldText;

			@Override
			public void levelStart() {
				oldFont = levelLabel.getFont();
				oldText = levelLabel.getText();

				levelLabel.setFont(UIManager.getDefaults().getFont("TabbedPane.font"));
				levelLabel.setText("<html><center>" +
						"Use the arrow keys to move </center></html>");
				levelLabel.setVisible(true);
			}

			@Override
			public void levelComplete() {
				levelLabel.setFont(oldFont);
				levelLabel.setText(oldText);
			}
		});
	}
}
