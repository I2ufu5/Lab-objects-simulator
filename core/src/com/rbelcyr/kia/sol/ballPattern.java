package com.rbelcyr.kia.sol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.rbelcyr.kia.sol.Enitities.Blocker;
import com.rbelcyr.kia.sol.Enitities.Scene;
import com.rbelcyr.kia.sol.Enitities.Sensor;

public class ballPattern extends AbstractBallMachineScene {


    @Override
    public void create () {
        super.create();
        spawnBalls();
    }

    protected void createScene(){
        super.sceneTex = new Texture("textures/ballPatternScene.png");
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
        sceneBody.attachFixture(body, "ballPatternScene", fd,800/PIXELS_TO_METERS);
    }

    ////////////////////////////////
    ///SENSORS..................
    //////////////////////////////

    protected void createSensors(){
        colorSensor = new Sensor(world,new Vector2(382.5f/PIXELS_TO_METERS,450.0f/PIXELS_TO_METERS));
        ballSensor = new Sensor(world,new Vector2(382.5f/PIXELS_TO_METERS, 420.0f/PIXELS_TO_METERS));
    }

    ////////////////////////////////
    ///BLOCKERS..................
    //////////////////////////////

    protected void createBlockers(){
        S1 = new Blocker(world,blockerTexRight,new Vector2(595,610),new Vector2(555,610),180,false);
        S2 = new Blocker(world,blockerTexLeft,new Vector2(205,607),new Vector2(250,607),180,true);
        S3 = new Blocker(world,blockerTexRight,new Vector2(295f,525),new Vector2(350,525),0,false);
        S4 = new Blocker(world,blockerTexRight,new Vector2(445f,437),new Vector2(410f,437),0,false);
    }

    ////////////////////////////////
    ///BALLS..................
    //////////////////////////////

    private void spawnBalls(){
        Vector2 startPos = new Vector2(100,750);

        for(int j=0;j<3;j++)
            for(int i=0;i<6;i++)
             createBall(startPos.x+32*i,startPos.y-35*j, Color.WHITE);

        startPos = new Vector2(520,750);

        for(int j=0;j<3;j++)
            for(int i=0;i<6;i++)
             createBall((startPos.x+32*i),(startPos.y-35*j), Color.BLACK);
    }

}