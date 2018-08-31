package com.rbelcyr.kia.sol.Enitities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import javax.xml.soap.Text;

public class Ball extends Sprite {
    public Color color;
    public float radius = 16;
    public Body body;

    public Ball() {
    }

    private void choseTexture(Texture textureBlack, Texture textureWhite){
        if(color == Color.BLACK)
            super.setTexture(textureBlack);
        if(color == Color.WHITE)
            super.setTexture(textureWhite);
    }

    public void setColor(Color tint,Texture textureBlack, Texture textureWhite) {
        this.color = tint;
        this.choseTexture(textureBlack,textureWhite);
    }

    /////////////////////////////////
    ///////STATIC FUNCTIONS//////////
    /////////////////////////////////


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
                batch.draw(ball.getTexture(), ball.body.getPosition().x- 16, ball.body.getPosition().y- 16);
            }

        }
    }
}
