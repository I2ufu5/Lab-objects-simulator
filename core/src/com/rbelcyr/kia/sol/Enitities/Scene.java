package com.rbelcyr.kia.sol.Enitities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.rbelcyr.kia.sol.BodyEditorLoader;

public class Scene extends Sprite {
    public Body body;

    final float PIXELS_TO_METERS = 100f;

    public Scene(Texture texture) {
        super(texture);
        this.setOrigin(0,0);
        this.setScale(1/PIXELS_TO_METERS,1/PIXELS_TO_METERS);

    }
}
