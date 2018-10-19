package com.rbelcyr.kia.sol.Enitities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.rbelcyr.kia.sol.AbstractBallMachineScene;

import java.util.Random;


public class Ball extends Sprite {
    final float PIXELS_TO_METERS = 100f;

    public Color color;
    public float radius;
    public Body body;



    public Ball(Vector2 position, Texture texture, Color color, World world) {
        super(texture);
        this.radius = texture.getHeight()/2/PIXELS_TO_METERS;
        this.color = color;
        this.setPosition(position.x/PIXELS_TO_METERS, position.y/PIXELS_TO_METERS);
        this.setScale(1/PIXELS_TO_METERS,1/PIXELS_TO_METERS);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(this.getX(),this.getY());
        Body body = world.createBody(bodyDef);
        this.body = body;
        body.setUserData(this);

        CircleShape circle = new CircleShape();
        circle.setRadius(this.radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 5.0f;
        fixtureDef.friction = 0.01f;
        fixtureDef.restitution = 0.1f;
        Fixture fixture = body.createFixture(fixtureDef);

        circle.dispose();
    }

    public void updatePosition(){
        super.setPosition(this.body.getPosition().x-this.getWidth()/2,this.body.getPosition().y-this.getHeight()/2);
    }

    /////////////////////////////////
    ///////STATIC FUNCTIONS//////////
    /////////////////////////////////

    public static void applyImpulse(Array<Body> bodies){
        for(int i=0;i<bodies.size;i++){
            if(bodies.get(i).getUserData() instanceof Ball){
                bodies.get(i).applyLinearImpulse(new Vector2(0,0.1f),bodies.get(i).getPosition(),true);
            }

        }
    }

    public static Color randomizeColor(){
        Random r = new Random();

        int Result = r.nextInt();

        if(Result%2==0)
            return Color.BLACK;
        else
            return Color.WHITE;
    }

    public static void draw(Array<Body> bodies, SpriteBatch batch){

        for (int i=0;bodies.size>i;i++) {
            if(bodies.get(i).getUserData() instanceof Ball) {
                Ball ball = (Ball) bodies.get(i).getUserData();
                ball.updatePosition();
                ball.draw(batch);
                //batch.draw(ball.getTexture(), ball.body.getPosition().x- 16, ball.body.getPosition().y- 16);
            }

        }
    }
}
