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

    public Blocker(Vector2 positionOpen, Vector2 positionClose) {
        this.positionOpen = positionOpen;
        this.positionClose = positionClose;
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

    public Blocker(World world, Texture texture, Vector2 positionOpen, Vector2 positionClose, float angle){
        super(texture);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(positionOpen));
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(35.0f, 5.0f);
        body.createFixture(shape, 0.0f);
        body.setTransform(body.getPosition(),(float) Math.toRadians(angle));

        this.setRotation(angle);
        this.body = body;
        this.positionOpen = positionOpen;
        this.positionClose = positionClose;

    }

}
