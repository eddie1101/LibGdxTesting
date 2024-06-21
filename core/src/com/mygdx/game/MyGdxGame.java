package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {

	final float ACC = 0.8f;
	float radians = 0f;

	Circle player, circle;
	Rectangle rect;
	Triangle tri;
	Line line;
	ArrayList<IRenderableShape> renderQueue;
	RenderableMovableShape[] collisionObjects;
	ArrayList<IRenderableShape> hits;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		player = new Circle(30);
		player.setPos(Gdx.graphics.getWidth() / 2f, 100);
		circle = new Circle(200);
		circle.setPos(1800, 1000);
		rect = new Rectangle(200, 100);
		rect.setPos(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
		tri = new Triangle();
		tri.setPos(500, 500);

		line = new Line(0, 0, 0, 0);

		renderQueue = new ArrayList<>();
		renderQueue.add(player);
		renderQueue.add(rect);
		renderQueue.add(tri);
		renderQueue.add(line);
		renderQueue.add(circle);

		collisionObjects = new RenderableMovableShape[3];
		collisionObjects[0] = rect;
		collisionObjects[1] = tri;
		collisionObjects[2] = circle;

		hits = new ArrayList<>();
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
			float a = 0; //debugging button lmao
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			radians -= 0.05f;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			radians += 0.05f;
		}
		player.accelerate(inputVector);
		player.update();

		rect.setRotation(radians);
		tri.setRotation(radians);
		Gdx.app.debug("Main", rect.getVertexPositions().toString());

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

		if(intersection.intersects()) {
			Circle circle = new Circle(3);
			circle.setPos(intersection.getIntersectionPoint());
			hits.add(circle);
		}

		if(hits.size() > 100){
			hits.remove(0);
		}

		for(IRenderableShape shape: hits) {
			shape.render();
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
