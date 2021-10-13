package com.deco2800.game.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.deco2800.game.services.ServiceLocator;

public class ParticleRenderComponent extends RenderComponent{
    private Texture texture;
    private String texturePath;
    private ParticleEffect pe;
    private boolean EffectStart = false;


    public ParticleRenderComponent(String texturePath) {
        this.texturePath = texturePath;
    }

    @Override
    public void create() {
        super.create();
//        texture = ServiceLocator.getResourceService().getAsset(texturePath, Texture.class);
        Vector2 pos = entity.getPosition();
        pe = new ParticleEffect();
        pe.load(Gdx.files.internal(texturePath),Gdx.files.internal("images/particle"));
        //     pe.setPosition(pos.x,pos.y);
        pe.start();

    }

    public void startEffect(){

        EffectStart=true;
    }
    @Override
    protected void draw(SpriteBatch batch) {
        if (EffectStart){
            Vector2 pos = entity.getPosition();
            pe.update(Gdx.graphics.getDeltaTime());
            pe.setPosition(pos.x,pos.y);
            pe.draw(batch);
            if (pe.isComplete()){
                pe.reset();
            }

        }

    }



}
