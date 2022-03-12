package com.nexaus;

import javax.swing.*;

import city.cs.engine.*;
import city.cs.engine.World;
import com.nexaus.levels.Stages;
import com.nexaus.objects.Player;
import com.nexaus.objects.RoadTile;
import com.nexaus.points.HighScore;
import com.nexaus.points.PointsHandler;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        World world = new World();
        world.setGravity(100);
        Player player = new Player(world);
        RoadTile roadTile= new RoadTile(world);
        // Create the view
        UserView view = new GameView(world, 600, 700, player);
        view.setView(new Vec2(0, 0), 32);
        view.setLayout(new BorderLayout());


        // Display points
        final String pointsText = "Points: %s,Life: %s High Score: %s";
        final float highScore = HighScore.getHighScore();
        String highScoreString = PointsHandler.pointsToString(highScore);

        String text = String.format(pointsText, 100,100, highScoreString);
        final JLabel pointsLabel = new JLabel(text);

        PointsHandler.addChangeListener(pointsChangeEvent -> {
            float points = PointsHandler.getPoints();
            if (points > highScore) {
                HighScore.setHighScore(points);
            }

            String pointsStr = PointsHandler.pointsToString();
            float high = Math.max(highScore, points);
            String highStr = PointsHandler.pointsToString(high);

            if (pointsStr.equals(highStr)) {
                highStr += "!";
            }

            String newText = String.format(pointsText, pointsStr,100, highStr);
            pointsLabel.setText(newText);
        });
        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        view.add(pointsLabel, BorderLayout.NORTH);

        // Label for "Level Complete", "Game Complete" and start instructions.
        JLabel levelLabel = new JLabel("Level Complete!");
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelLabel.setVisible(false);

        Font defaultFont = UIManager.getDefaults().getFont("TabbedPane.font");
        levelLabel.setFont(new Font(defaultFont.getFontName(), defaultFont.getStyle(), 48));

        view.add(levelLabel, BorderLayout.WEST);


        // Start the game
        Stages stages = new Stages(player, view, levelLabel);
        stages.start();
        player.addLevelsData(stages);


        // Set up the the Frame
        JFrame frame = new JFrame("Road Runner");
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.add(view);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        // Position window in middle of screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = frame.getSize();
        Point frameLocation = new Point(dim.width / 2 - windowSize.width / 2,
                dim.height / 2 - windowSize.height / 2);
        frame.setLocation(frameLocation);

        // Set up Draggable
        Draggable.makeDraggable(frame, view, frameLocation);


        frame.addKeyListener(new GameKeyDispatcher(player, world, stages));

//		view.setGridResolution(1);
//		JFrame debugView = new DebugViewer(world, 500, 500);

        world.start();
    }
}
