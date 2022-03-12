package com.nexaus.points;

import java.io.*;

/**
 * A class to handle high scores.
 */
public class HighScore {
	private static final String file = "highscore";

	public static float getHighScore() {
		float highScore = 0;

		// Autocloses in Java 7 (both resources)
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			highScore = Float.parseFloat(reader.readLine());
		} catch (IOException e) {
			if (e instanceof FileNotFoundException) {
				System.out.println("High score file not found. Probably not a problem.");
			} else {
				e.printStackTrace();
			}
		}

		return highScore;
	}

	public static void setHighScore(float highScore) {
		try (FileWriter fw = new FileWriter(file)) {
			fw.write(highScore + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
