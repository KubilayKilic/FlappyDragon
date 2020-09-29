package com.atilsamancioglu.survivorbirdstarter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture bg;
	Texture dragon;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;


	float dragonX = 0;
	float dragonY = 0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 14f;
	float enemyVelocity = 13;
	Random random;
	int score = 0;
	int scoreEnemy = 0;
	BitmapFont font;
	BitmapFont go;

	Circle dragonCircle;
	ShapeRenderer shapeRenderer;

	int numOfEnemies = 4;
	float [] enemyX = new float[numOfEnemies];
	float [] enemyOffset = new float[numOfEnemies];
	float [] enemyOffset2 = new float[numOfEnemies];
	float [] enemyOffset3 = new float[numOfEnemies];
	float distance = 0;

	Circle[] enemyCircle;
	Circle[] enemyCircle2;
	Circle[] enemyCircle3;


	@Override
	public void create () {
		batch = new SpriteBatch();
		bg = new Texture("bg.png");
		dragon = new Texture("dragon.png");
		enemy1 = new Texture ("enemy.png");
		enemy2 = new Texture("enemy.png");
		enemy3 = new Texture("enemy.png");

		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();


		dragonX = Gdx.graphics.getWidth() / 2 - dragon.getHeight() / 2;
		dragonY = Gdx.graphics.getHeight() / 2;

		shapeRenderer = new ShapeRenderer();

		dragonCircle = new Circle();
		enemyCircle = new Circle[numOfEnemies];
		enemyCircle2 = new Circle[numOfEnemies];
		enemyCircle3 = new Circle[numOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.BLACK);
		font.getData().setScale(4);

		go = new BitmapFont();
		go.setColor(Color.BLACK);
		go.getData().setScale(5);

		for (int i = 0; i < numOfEnemies; i++) {

			enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyX[i] = Gdx.graphics.getWidth() - dragon.getWidth() / 2 + i * distance;


			enemyCircle[i] = new Circle();
			enemyCircle2[i] = new Circle();
			enemyCircle3[i] = new Circle();
		}


	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(bg,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameState == 1) {

			if (enemyX[scoreEnemy] < Gdx.graphics.getWidth() / 2 - dragon.getHeight() / 2) {
				score++;

				if (scoreEnemy < numOfEnemies - 1) {
					scoreEnemy++;
				} else {
					scoreEnemy = 0;
				}
			}

			if (Gdx.input.justTouched()) {

				velocity = -80;

			}

			for (int i = 0; i < numOfEnemies; i++) {

				if (enemyX[i] < Gdx.graphics.getWidth()/14) {
					enemyX[i] = enemyX[i] + numOfEnemies * distance;

					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

				} else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}

				enemyX[i] = enemyX[i] - enemyVelocity;

				batch.draw(enemy1,enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset[i], Gdx.graphics.getWidth()/14, Gdx.graphics.getHeight()/11);
				batch.draw(enemy2,enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset2[i], Gdx.graphics.getWidth()/14, Gdx.graphics.getHeight()/11);
				batch.draw(enemy3,enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset3[i], Gdx.graphics.getWidth()/14, Gdx.graphics.getHeight()/11);

				enemyCircle[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/28, + Gdx.graphics.getHeight() / 2 + enemyOffset[i] + Gdx.graphics.getHeight()/22,Gdx.graphics.getWidth()/28);
				enemyCircle2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/28, + Gdx.graphics.getHeight() / 2 + enemyOffset2[i] + Gdx.graphics.getHeight()/22,Gdx.graphics.getWidth()/28);
				enemyCircle3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/28, + Gdx.graphics.getHeight() / 2 + enemyOffset3[i] + Gdx.graphics.getHeight()/22,Gdx.graphics.getWidth()/28);

			}


			if (dragonY > 0 ) {
				velocity = velocity + gravity;
				dragonY = dragonY - velocity;
			} else {
				gameState = 2;

			}

		} else if (gameState == 0) {
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if (gameState == 2) {

			go.draw(batch,"GAME OVER, TAP TO PLAY AGAIN", 100, Gdx.graphics.getHeight()/2);

			if (Gdx.input.justTouched()) {
				gameState = 1;

				dragonY = Gdx.graphics.getHeight() / 2;

				for (int i = 0; i < numOfEnemies; i++) {

					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyX[i] = Gdx.graphics.getWidth() - dragon.getWidth() / 2 + i * distance;


					enemyCircle[i] = new Circle();
					enemyCircle2[i] = new Circle();
					enemyCircle3[i] = new Circle();
				}

				velocity = 0;
				scoreEnemy = 0;
				score = 0;
			}
		}

		batch.draw(dragon,dragonX, dragonY, Gdx.graphics.getWidth()/14, Gdx.graphics.getHeight()/11);

		font.draw(batch, String.valueOf(score), 100, 200);

		batch.end();

		dragonCircle.set(dragonX + Gdx.graphics.getWidth()/28 , dragonY + Gdx.graphics.getHeight()/22 ,Gdx.graphics.getWidth()/28);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(dragonCircle.x, dragonCircle.y, dragonCircle.radius);

		for (int i = 0; i < numOfEnemies; i++) {
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/28, + Gdx.graphics.getHeight() / 2 + enemyOffset[i] + Gdx.graphics.getHeight()/22,Gdx.graphics.getWidth()/28);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/28, + Gdx.graphics.getHeight() / 2 + enemyOffset2[i] + Gdx.graphics.getHeight()/22,Gdx.graphics.getWidth()/28);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/28, + Gdx.graphics.getHeight() / 2 + enemyOffset3[i] + Gdx.graphics.getHeight()/22,Gdx.graphics.getWidth()/28);

			if (Intersector.overlaps(dragonCircle, enemyCircle[i]) || Intersector.overlaps(dragonCircle,enemyCircle2[i]) || Intersector.overlaps(dragonCircle, enemyCircle3[i])) {
				gameState = 2;
			}
		}
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
	}
}