package com.rbelcyr.kia.sol.Enitities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.rbelcyr.kia.sol.BodyEditorLoader;

import java.lang.reflect.Array;

public class Blocker extends Sprite{
    private Vector2 positionOpen;
    private Vector2 positionClose;
    public Body body;

    final float PIXELS_TO_METERS = 100f;

    public Blocker(World world, Texture texture, Vector2 positionOpen, Vector2 positionClose, float angle,boolean left){
        super(texture);

        BodyEditorLoader blockerBody = new BodyEditorLoader(Gdx.files.internal("bodies/sortownica.json"));

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.KinematicBody;
        bd.position.set(positionOpen);
        bd.angle = (float)Math.toRadians(angle);
        Body body = world.createBody(bd);

        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.1f;
        fd.restitution = 0.3f;

        if(left)
            blockerBody.attachFixture(body, "blockerLeft", fd, 1);
        else
            blockerBody.attachFixture(body, "blockerRight", fd, 1);

        this.setScale(1 / PIXELS_TO_METERS, 1 / PIXELS_TO_METERS);
        this.setRotation(angle);
        this.body = body;
        this.positionOpen = new Vector2(positionOpen.x/PIXELS_TO_METERS,positionOpen.y/PIXELS_TO_METERS);
        this.positionClose = new Vector2(positionClose.x/PIXELS_TO_METERS,positionClose.y/PIXELS_TO_METERS);

        }

    public Vector2 getPositionOpen() {
        return positionOpen;
    }

    public Vector2 getPositionClose() {
        return positionClose;
    }

    private void updatePosition(){
        super.setPosition(this.body.getPosition().x-this.getWidth()/2,this.body.getPosition().y-this.getHeight()/2);
    }

    /////////////
    ////static///
    /////////////

    public static void draw(Blocker blocker, SpriteBatch batch){
        blocker.updatePosition();
        blocker.draw(batch);
    }

}
