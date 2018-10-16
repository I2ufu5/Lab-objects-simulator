package com.rbelcyr.kia.sol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.rbelcyr.kia.sol.Enitities.Blocker;
import com.rbelcyr.kia.sol.Enitities.Scene;
import com.rbelcyr.kia.sol.Enitities.Sensor;

public class ballSorting extends AbstractBallMachineScene {

	@Override
	public void create () {
		super.create();
		spawnBalls();
	}

	protected void createScene(){
		super.sceneTex = new Texture("textures/ballSortingScene.png");
		super.scene = new Scene(sceneTex);

		BodyEditorLoader sceneBody = new BodyEditorLoader(Gdx.files.internal("bodies/sortownica.json"));

		BodyDef bd = new BodyDef();
		bd.type = BodyDef.BodyType.StaticBody;
		bd.position.set(new Vector2(0,0));

		Body body = world.createBody(bd);
		scene.body = body;

		FixtureDef fd = new FixtureDef();
		fd.density = 1;
		fd.friction = 0.1f;
		fd.restitution = 0.3f;
		sceneBody.attachFixture(body, "ballSortingScene", fd,800/PIXELS_TO_METERS);
	}

	////////////////////////////////
	///SENSORS..................
	//////////////////////////////

	protected void createSensors(){
		colorSensor = new Sensor(world,new Vector2(420/PIXELS_TO_METERS,370.0f/PIXELS_TO_METERS));
		ballSensor = new Sensor(world,new Vector2(420/PIXELS_TO_METERS, 335.0f/PIXELS_TO_METERS));
	}

	////////////////////////////////
	///BLOCKERS..................
	//////////////////////////////

	protected void createBlockers(){
		S1 = new Blocker(world,blockerTexLeft,new Vector2(590,193),new Vector2(550,193),0,true);
		S2 = new Blocker(world,blockerTexRight,new Vector2(210,193),new Vector2(255,193),0,false);
		S3 = new Blocker(world,blockerTexRight,new Vector2(500,275),new Vector2(450,275),180,false);
        S4 = new Blocker(world,blockerTexLeft,new Vector2(355,350),new Vector2(390,355),0,true);
	}

	////////////////////////////////
	///BALLS..................
	//////////////////////////////

	private void spawnBalls(){
	    Vector2 startPos = new Vector2(100,750);
        for(int i=0;i<15;i++){
            super.createBall(startPos.x+i*35,startPos.y-i*5);
        }

        startPos = new Vector2(650,600);
        for(int i=0;i<15;i++){
            super.createBall(startPos.x-i*35,startPos.y-i*5);
        }
	}

}
