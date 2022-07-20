package com.keremmuhcu.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import sun.jvm.hotspot.utilities.BitMap;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture skull1;
	Texture skull2;
	Texture skull3;
	float birdX = 0;
	float birdY = 0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 1f;

	int numberOfSkulls = 4;
	float[] skullX = new float[numberOfSkulls];
	float[] skullOffSet1 = new float[numberOfSkulls];
	float[] skullOffSet2 = new float[numberOfSkulls];
	float[] skullOffSet3 = new float[numberOfSkulls];
	Circle[] skullCircle1;
	Circle[] skullCircle2;
	Circle[] skullCircle3;

 	float distance = 0;
	float skullVelocity = 12;
	Random random;

	Circle birdCircle;
	ShapeRenderer shapeRenderer;
	int score = 0;
	int scoredSkull = 0;
	BitmapFont font;
	BitmapFont font2;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		skull1 = new Texture("skull.png");
		skull2 = new Texture("skull.png");
		skull3 = new Texture("skull.png");

		birdX = Gdx.graphics.getWidth() / 7;
		birdY = Gdx.graphics.getHeight() / 3;
		shapeRenderer = new ShapeRenderer();

		birdCircle = new Circle();
		skullCircle1 = new Circle[numberOfSkulls];
		skullCircle2 = new Circle[numberOfSkulls];
		skullCircle3 = new Circle[numberOfSkulls];

		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);

		for (int i = 0; i < numberOfSkulls; i++) {
			skullX[i] = Gdx.graphics.getWidth() + i * distance;

			skullOffSet1[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
			skullOffSet2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
			skullOffSet3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());

			skullCircle1[i] = new Circle();
			skullCircle2[i] = new Circle();
			skullCircle3[i] = new Circle();

		}

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1) {

			if (skullX[scoredSkull] < Gdx.graphics.getWidth() / 7) {
				score++;

				if (scoredSkull < numberOfSkulls - 1) {
					scoredSkull++;
				} else {
					scoredSkull = 0;
				}
			}

			if (Gdx.input.justTouched()) {
				velocity = -23;
			}

			for (int i = 0; i < numberOfSkulls; i++) {
				if (skullX[i] < -Gdx.graphics.getWidth() / 15) {
					skullX[i] = skullX[i] + numberOfSkulls * distance;

					skullOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					skullOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					skullOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				} else {
					skullX[i] = skullX[i] - skullVelocity;
				}

				batch.draw(skull1, skullX[i], Gdx.graphics.getHeight() / 2 + skullOffSet1[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(skull2, skullX[i], Gdx.graphics.getHeight() / 2 + skullOffSet2[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(skull3, skullX[i], Gdx.graphics.getHeight() / 2 + skullOffSet3[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

				skullCircle1[i] = new Circle(skullX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + skullOffSet1[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getHeight() / 30);
				skullCircle2[i] = new Circle(skullX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + skullOffSet2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getHeight() / 30);
				skullCircle3[i] = new Circle(skullX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + skullOffSet3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getHeight() / 30);
			}


			if (birdY > 0) {
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			} else {
				gameState = 2;
			}

		} else if (gameState == 0) {
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if (gameState == 2) {

			font2.draw(batch, "Game Over! Tap to Play Again!", 300, Gdx.graphics.getHeight() / 2);

			if (Gdx.input.justTouched()) {
				gameState = 1;

				birdY = Gdx.graphics.getHeight() / 3;

				for (int i = 0; i < numberOfSkulls; i++) {
					skullX[i] = Gdx.graphics.getWidth() + i * distance;

					skullOffSet1[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
					skullOffSet2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
					skullOffSet3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());

					skullCircle1[i] = new Circle();
					skullCircle2[i] = new Circle();
					skullCircle3[i] = new Circle();

				}

				velocity = 0;
				score = 0;
				scoredSkull = 0;

			}
		}


		batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
		font.draw(batch, String.valueOf(score), 100, 200);
		batch.end();

		birdCircle.set(birdX + Gdx.graphics.getWidth() / 30, birdY + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

		for (int i = 0; i < numberOfSkulls; i++) {
			//shapeRenderer.circle(skullX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + skullOffSet1[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getHeight() / 15);
			//shapeRenderer.circle(skullX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + skullOffSet2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getHeight() / 15);
			//shapeRenderer.circle(skullX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + skullOffSet3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getHeight() / 15);

			if (Intersector.overlaps(birdCircle, skullCircle1[i]) || Intersector.overlaps(birdCircle, skullCircle2[i]) || Intersector.overlaps(birdCircle, skullCircle3[i])) {
				gameState = 2;
			}
		}

		//shapeRenderer.end();

	}
	
	@Override
	public void dispose () {

	}
}
