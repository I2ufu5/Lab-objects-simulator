package com.rbelcyr.kia.sol;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.rbelcyr.kia.sol.Enitities.Ball;
import com.rbelcyr.kia.sol.Enitities.Blocker;
import com.rbelcyr.kia.sol.Enitities.Sensor;



public abstract class AbstractBallMachineScene extends ApplicationAdapter {

    SpriteBatch batch;
    Texture ballBlack,ballWhite;
    Texture sceneTex;
    Texture blockerTex,blockerTex2;
    World world;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera camera;

    Array<Body> bodies = new Array<Body>();
    Blocker S1,S2,S3,S4;
    Sensor colorSensor,ballSensor;



    @Override
    public void create () {

        batch = new SpriteBatch();

        ballBlack = new Texture("textures/ballBlack.png");
        ballWhite = new Texture("textures/ballWhite.png");
        blockerTex = new Texture("textures/blocker.png");



        Box2D.init();

        world = new World(new Vector2(0,-9.80f),false);
        world.setContinuousPhysics(false);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if(contact.getFixtureA().getBody().getUserData() == colorSensor
                        &
                        contact.getFixtureB().getBody().getUserData() instanceof Ball){
                    colorSensor.setDetectedColor(((Ball) contact.getFixtureB().getBody().getUserData()).color);
                }

                if(contact.getFixtureA().getBody().getUserData() == ballSensor
                        &
                        contact.getFixtureB().getBody().getUserData() instanceof Ball){
                    ballSensor.setDetectedColor(((Ball) contact.getFixtureB().getBody().getUserData()).color);
                }
            }

            @Override
            public void endContact(Contact contact) {
                if(contact.getFixtureA().getBody().getUserData() == ballSensor
                        &
                        contact.getFixtureB().getBody().getUserData() instanceof Ball){
                    ballSensor.setDetectedColor(Color.GRAY);
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        camera = new OrthographicCamera();
        camera.setToOrtho(false,800,800);

        createScene();
        createBlockers();
        createSensors();

        debugRenderer = new Box2DDebugRenderer();

    }

    abstract void createScene();

    ////////////////////////////////
    ///SENSORS..................
    //////////////////////////////

    protected abstract void createSensors();

    public boolean getBallSensorValue(){
        return ballSensor.getDetectedColor() == Color.BLACK | ballSensor.getDetectedColor() == Color.WHITE;
    }

    public boolean getColorSensorValue(){
        return colorSensor.getDetectedColor() == Color.BLACK;
    }

    ////////////////////////////////
    ///BLOCKERS..................
    //////////////////////////////

    protected abstract void createBlockers();

    public void openS4(){
        S4.body.setTransform(S4.getPositionOpen(),S4.body.getAngle());
    }

    public void closeS4(){
        S4.body.setTransform(S4.getPositionClose(),S4.body.getAngle());
    }

    public void openS3(){
        S3.body.setTransform(S3.getPositionOpen(),S3.body.getAngle());
    }

    public void closeS3(){
        S3.body.setTransform(S3.getPositionClose(),S3.body.getAngle());
    }

    public void openS2(){
        S2.body.setTransform(S2.getPositionOpen(),S2.body.getAngle());
    }

    public void closeS2(){
        S2.body.setTransform(S2.getPositionClose(),S2.body.getAngle());
    }

    public void openS1(){
        S1.body.setTransform(S1.getPositionOpen(),S1.body.getAngle());
    }

    public void closeS1(){
        S1.body.setTransform(S1.getPositionClose(),S1.body.getAngle());
    }

    ////////////////////////////////
    ///BALLS..................
    //////////////////////////////

    public void createBall(float x, float y){
        Ball ball = new Ball();
        ball.setColor(Ball.randomizeColor(),ballBlack,ballWhite);
        ball.setPosition(x,y);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(ball.getX(),ball.getY());

        Body body = world.createBody(bodyDef);
        ball.body = body;

        body.setUserData(ball);

        CircleShape circle = new CircleShape();
        circle.setRadius(ball.radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 10.0f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.1f;

        body.setGravityScale(1.0f);
        Fixture fixture = body.createFixture(fixtureDef);

        circle.dispose();
    }

    public void createBall(float x,float y,Color color){
        Ball ball = new Ball();
        ball.setColor(color,ballBlack,ballWhite);
        ball.setPosition(x,y);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(ball.getX(),ball.getY());

        Body body = world.createBody(bodyDef);
        ball.body = body;

        body.setUserData(ball);

        CircleShape circle = new CircleShape();
        circle.setRadius(ball.radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 10.0f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.1f;

        body.setGravityScale(10.0f);
        Fixture fixture = body.createFixture(fixtureDef);

        circle.dispose();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isTouched())
            world.setGravity(new Vector2(0,9.80f));
        else
            world.setGravity(new Vector2(0,-9.80f));

        camera.update();

        world.getBodies(bodies);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        Ball.draw(bodies,batch);
        Blocker.draw(S4,batch);
        Blocker.draw(S3,batch);
        Blocker.draw(S2,batch);
        Blocker.draw(S1,batch);

        batch.draw(sceneTex,0,0);
        batch.end();

        //debugRenderer.render(world, camera.combined);

        world.step(1/60f, 6, 2);
    }

    @Override
    public void dispose () {
        batch.dispose();
        ballBlack.dispose();
        ballWhite.dispose();
        sceneTex.dispose();
    }


}
