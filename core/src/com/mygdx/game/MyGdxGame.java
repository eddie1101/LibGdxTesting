package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {

	final float ACC = 0.8f;

	Circle player, circle;
	Rectangle rect;
	Triangle tri;
	Line line;
	IRenderableShape[] renderQueue;
	RenderableMovableShape[] collisionObjects;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		player = new Circle(30);
		circle = new Circle(200);
		circle.setPos(1800, 1000);
		rect = new Rectangle(200, 100);
		rect.setPos(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
		tri = new Triangle();
		tri.setPos(500, 500);

		line = new Line(0, 0, 0, 0);

		renderQueue = new IRenderableShape[5];
		renderQueue[0] = player;
		renderQueue[1] = rect;
		renderQueue[2] = line;
		renderQueue[3] = tri;
		renderQueue[4] = circle;

		collisionObjects = new RenderableMovableShape[3];
		collisionObjects[0] = rect;
		collisionObjects[1] = tri;
		collisionObjects[2] = circle;
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		Vector2 inputVector = new Vector2();
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			inputVector.add(new Vector2(-ACC, 0));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			inputVector.add(new Vector2(ACC, 0));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			inputVector.add(new Vector2(0, ACC));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			inputVector.add(new Vector2(0, -ACC));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			float a = 0;
		}
		player.accelerate(inputVector);

		player.update();

		Vector2 cursor = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

		Vector2 directionToCursor = new Vector2(cursor).sub(player.getPos());
		Vector2 radialPointToCursor = directionToCursor.nor().scl(player.getRadius());
		Vector2 lineStartPoint = new Vector2(player.getPos()).add(radialPointToCursor);

		line.setP1(lineStartPoint);
		line.setP2(cursor);

		LineIntersection intersection = new LineIntersection(line, collisionObjects);

		if(intersection.intersects()) {
			line.setP2(intersection.getIntersectionPoint());
		}

		for(IRenderableShape shape : renderQueue) {
			shape.render();
		}
	}
	
	@Override
	public void dispose () {
		for(IRenderableShape shape : renderQueue) {
			shape.dispose();
		}
	}
}
