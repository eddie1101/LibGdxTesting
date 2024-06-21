package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.api.collision.ShapeCollision;
import com.mygdx.game.api.shape.Circle;
import com.mygdx.game.api.shape.FreePolygon;
import com.mygdx.game.api.shape.Rectangle;
import com.mygdx.game.api.shape.Triangle;


public class MyGdxGame extends ApplicationAdapter {

	Circle c1;

	FreePolygon poly;
	float scl = 0f;
	float rot = 0f;

	@Override
	public void create () {
		poly = new FreePolygon();

		Vector2 v1 = new Vector2(0, 0);
		Vector2 v2 = new Vector2(60, 0);
		Vector2 v3 = new Vector2(90, 25);
		Vector2 v4 = new Vector2(60, 50);
		Vector2 v5 = new Vector2(0, 50);

		poly.setPoints(v1, v2, v3, v4, v5);
		poly.setPos(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

		c1 = new Circle(20);

		c1.setPos(300, 500);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		Vector2 inputAcc = new Vector2();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			scl += 0.025f;
		} else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			scl -= 0.025f;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			rot -= 0.025f;
		} else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			rot += 0.025f;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			inputAcc.add(-0.5f, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			inputAcc.add(0.5f, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			inputAcc.add(0f, 0.5f);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			inputAcc.add(0f, -0.5f);
		}

		poly.setScale(scl);
		poly.setRotation(rot);

		poly.accelerate(inputAcc);
		poly.update();
		poly.render();

		ShapeCollision collision = new ShapeCollision(c1, poly);
		if(collision.isColliding()) {
			c1.setColor(new Color(1, 0, 0, 1));
			poly.setColor(new Color(1, 0, 0, 1));

			Circle hitPoint = new Circle(3);
			hitPoint.setColor(new Color(0, 0, 1, 1));
			hitPoint.setPos(collision.getCollisionPoint());
			hitPoint.render();

		} else {
			c1.setColor(new Color(1, 1, 1, 1));
			poly.setColor(new Color(1, 1, 1, 1));
		}

		c1.render();




	}
	
	@Override
	public void dispose () {

	}
}
