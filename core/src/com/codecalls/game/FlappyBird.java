package com.codecalls.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bottomtube;
	Texture toptube;
	Texture[] birds;

	int state = 0;
	float birdY = 0;
	float velocity = 0;
	float gravity = 2;
	int gamestate = 0;
	float gap = 400;
	float maxOffset;
	float tubevelocity =4;
	Random random;
	int numberofTube =4;
    int Score = 0;

	float distanceBetweenTubes;
    float[] tubeX = new float[numberofTube];
    float tubeOffset[] = new float[numberofTube];

    Circle birdCircle;
    Rectangle[] tubesTop;
    Rectangle[] tubesBottom;



	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");
        birdY = Gdx.graphics.getHeight()/2 - birds[state].getHeight()/2;
        bottomtube = new Texture("bottomtube.png");
        toptube = new Texture("toptube.png");
        random = new Random();
        maxOffset = Gdx.graphics.getHeight()/2 - gap/2 -100;
        distanceBetweenTubes = Gdx.graphics.getWidth()*2/3;
        birdCircle =  new Circle();
        tubesTop = new Rectangle[numberofTube];
        tubesBottom = new Rectangle[numberofTube];

        for (int i =0; i< numberofTube; i++){
            tubeOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap -200);
            tubeX[i] = Gdx.graphics.getWidth()/2 - toptube.getWidth()/2 + Gdx.graphics.getWidth() + (i* distanceBetweenTubes);
            tubesTop[i] = new Rectangle();
            tubesBottom[i] = new Rectangle();

        }



	}

	@Override
	public void render () {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (Gdx.input.justTouched()) {
            gamestate = 1;

        }

        if (gamestate != 0) {

            if (Gdx.input.justTouched()) {
                velocity = -30;



            }
            for (int i =0; i< numberofTube; i++) {

                if(tubeX[i] < - toptube.getWidth()){
                    tubeX[i] += numberofTube * distanceBetweenTubes;
                     Score++;
                    Gdx.app.log("Score :",Integer.toString(Score));
                    tubeOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap -200);
                }else {

                    tubeX[i] = tubeX[i] - tubevelocity;
                }
                batch.draw(toptube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
                tubesTop[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], toptube.getWidth(), toptube.getHeight());
                batch.draw(bottomtube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomtube.getHeight() + tubeOffset[i]);
                tubesBottom[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap/2 - bottomtube.getHeight() + tubeOffset[i], bottomtube.getWidth(), bottomtube.getHeight());

            }




            if(birdY > Gdx.graphics.getHeight()){
                velocity = 0;
                birdY -= Gdx.graphics.getHeight() - birds[state].getHeight();
            }
            if(birdY > 0 || velocity < 0) {

                velocity = velocity + gravity;
                birdY -= velocity;
            }

        }else{

            if (Gdx.input.justTouched()) {
                gamestate = 1;

            }
        }

        if (state == 0) {
            state = 1;
        } else {
            state = 0;
        }

        batch.draw(birds[state], Gdx.graphics.getWidth() / 2 - birds[state].getWidth() / 2, birdY);
        batch.end();
        birdCircle.set(Gdx.graphics.getWidth() /2, birdY + birds[state].getHeight()/2, birds[state].getWidth()/2);


        for (int i = 0 ; i < numberofTube ; i ++){

            if(Intersector.overlaps(birdCircle, tubesTop[i]) || Intersector.overlaps(birdCircle, tubesBottom[i])){
               Score = 0;
               Gdx.app.log("Score :",Integer.toString(Score));
          }
        }


    }
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
