package com.rbelcyr.kia.sol.Enitities;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Sensor{
    Color detectedColor;
    Body body;

    public Sensor() {
    }

    public Color getDetectedColor() {
        return detectedColor;
    }

    public void setDetectedColor(Color detectedColor) {
        this.detectedColor = detectedColor;
    }

    ////////Statyczne
    public static Sensor createSensor(World world, Vector2 position){
        Sensor sensor = new Sensor();
        sensor.setDetectedColor(Color.GRAY);
        BodyDef sensorDef = new BodyDef();
        sensorDef.type = BodyDef.BodyType.StaticBody;
        sensorDef.position.set(position);
        Body sensorBody = world.createBody(sensorDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(15,2);
        FixtureDef sensorFix = new FixtureDef();
        sensorFix.shape = shape;
        sensorFix.isSensor = true;
        sensorBody.createFixture(sensorFix);
        shape.dispose();
        sensor.body = sensorBody;
        sensorBody.setUserData(sensor);
        return sensor;
    }
}
