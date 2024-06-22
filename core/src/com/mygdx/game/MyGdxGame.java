package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.api.collision.ShapeCollisionDetector;
import com.mygdx.game.api.shape.*;

import java.util.ArrayList;
import java.util.List;


public class MyGdxGame extends ApplicationAdapter {

	Line l;
	Line line;
	Circle c1;
	FreePolygon poly;
	float scl = 0f;
	float rot = 0f;
	float lrot = 0f;

	@Override
	public void create () {
		poly = new FreePolygon();

		Vector2 v1 = new Vector2(0, 0);
		Vector2 v2 = new Vector2(50, 0);
		Vector2 v3 = new Vector2(50, 60);
		Vector2 v4 = new Vector2(25, 90);
		Vector2 v5 = new Vector2(0, 60);

		poly.setPoints(v1, v2, v3, v4, v5);
		poly.setPos(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

		c1 = new Circle(200);
		c1.setPos(900, 800);

		l = new Line(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		line = new Line(100, 100, 200, 200);
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

		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			rot -= 0.04f;
		} else if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			rot += 0.04f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			inputAcc.add(0f, 0.5f);
		}
		inputAcc.rotateRad(rot);

		poly.setScale(scl);
		poly.setRotation(rot);
		poly.accelerate(inputAcc);
		poly.update();
		poly.render();

		lrot += 0.01f;
		l.setRotation(lrot);
		l.render();
		c1.render();

		ShapeCollisionDetector collision = new ShapeCollisionDetector(c1, l, poly);
		if(collision.isColliding()) {
			for(Vector2 collisionPoint : collision.getCollisionPoints()) {
				Circle hitPoint = new Circle(4);
				hitPoint.setShapeType(ShapeRenderer.ShapeType.Filled);
				hitPoint.setColor(new Color(1, 0, 1, 1));
				hitPoint.setPos(collisionPoint);
				hitPoint.render();
			}
		}
	}
	
	@Override
	public void dispose () {

	}
}
