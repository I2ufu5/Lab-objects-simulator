package com.rbelcyr.kia.sol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.rbelcyr.kia.sol.Enitities.Blocker;
import com.rbelcyr.kia.sol.Enitities.Sensor;

public class ballSorting extends AbstractBallMachineScene {

	@Override
	public void create () {
		super.create();
		super.sceneTex = new Texture("textures/ballSortingScene.png");
		spawnBalls();
	}

	protected void createScene(){
		BodyEditorLoader scene = new BodyEditorLoader(Gdx.files.internal("bodies/sortownica.json"));

		BodyDef bd = new BodyDef();
		bd.type = BodyDef.BodyType.StaticBody;
		bd.position.set(new Vector2(0,0));

		Body body = world.createBody(bd);

		FixtureDef fd = new FixtureDef();
		fd.density = 1;
		fd.friction = 0.5f;
		fd.restitution = 0.3f;

		scene.attachFixture(body, "ballSortingScene", fd, 800);

	}

	////////////////////////////////
	///SENSORS..................
	//////////////////////////////

	protected void createSensors(){
		colorSensor = Sensor.createSensor(world,new Vector2(420,370.0f));
		ballSensor = Sensor.createSensor(world,new Vector2(420, 340.0f));
	}

	////////////////////////////////
	///BLOCKERS..................
	//////////////////////////////

	protected void createBlockers(){
		S1 = new Blocker(world,blockerTex,new Vector2(550,260),new Vector2(510,210),60.0f+180);
		S2 = new Blocker(world,blockerTex,new Vector2(265,260),new Vector2(300,210),-60.0f-180);
		S3 = new Blocker(world,blockerTex,new Vector2(477.5f,295),new Vector2(425,255),35.0f);
        S4 = new Blocker(world,blockerTex,new Vector2(365,360),new Vector2(405,360),0);
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
