package com.rbelcyr.kia.sol.Enitities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.lang.reflect.Array;

public class Blocker extends Sprite{
    private Vector2 positionOpen;
    private Vector2 positionClose;
    public Body body;

    final float PIXELS_TO_METERS = 100f;

    public Blocker(World world, Texture texture, Vector2 positionOpen, Vector2 positionClose, float angle){
        super(texture);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(positionOpen.x/PIXELS_TO_METERS,positionOpen.y/PIXELS_TO_METERS);
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(35.0f/PIXELS_TO_METERS, 5.0f/PIXELS_TO_METERS);
        body.createFixture(shape, 0.0f);
        body.setTransform(body.getPosition(),(float) Math.toRadians(angle));

        this.setScale(1/PIXELS_TO_METERS,1/PIXELS_TO_METERS);
        this.setRotation(angle);
        this.body = body;
        this.positionOpen = new Vector2(positionOpen.x/PIXELS_TO_METERS,positionOpen.y/PIXELS_TO_METERS);
        this.positionClose = new Vector2(positionClose.x/PIXELS_TO_METERS,positionClose.y/PIXELS_TO_METERS);

        shape.dispose();
    }

    public Vector2 getPositionOpen() {
        return positionOpen;
    }

    public Vector2 getPositionClose() {
        return positionClose;
    }

    private void updatePosition(){
        super.setPosition(this.body.getPosition().x-this.getTexture().getWidth()/2,this.body.getPosition().y-this.getTexture().getHeight()/2);
        //super.setRotation(this.body.getPosition().angle());
    }

    /////////////
    ////static///
    /////////////

    public static void draw(Blocker blocker, SpriteBatch batch){
        blocker.updatePosition();
        blocker.draw(batch);
    }

}
