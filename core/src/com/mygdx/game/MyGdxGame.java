package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.api.collision.ShapeCollisionDetector;
import com.mygdx.game.api.collision.ShapeCollisionHandler;
import com.mygdx.game.api.collision.WeightlessCollisionHandler;
import com.mygdx.game.api.shape.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MyGdxGame extends ApplicationAdapter {

	static int width, height;

	Rectangle boundaries;
	Circle c;
	Line l;

	@Override
	public void create () {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		boundaries = new Rectangle(width, height);
		c = new Circle(50);
		c.setPos(width / 2f, 500);
		c.setVel(0, 10);

		l = new Line(400, 400, 2160, 1040);

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		WeightlessCollisionHandler handler = new WeightlessCollisionHandler(boundaries, c, l);
		handler.handle();

		c.update();

		boundaries.render();
		c.render();
		l.render();

	}
	
	@Override
	public void dispose () {

	}
}
