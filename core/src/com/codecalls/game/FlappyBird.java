package com.codecalls.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	int state = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");

	}

	@Override
	public void render () {
        if(state == 0){
            state = 1;
        }else{
            state = 0;
        }
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(birds[state], Gdx.graphics.getWidth()/2 - birds[state].getWidth()/2, Gdx.graphics.getHeight()/2 - birds[state].getHeight()/2);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
