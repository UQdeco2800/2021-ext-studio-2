package com.deco2800.game.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.deco2800.game.extensions.GameExtension;
import com.deco2800.game.rendering.AnimationRenderComponent;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
class PlayerAnimationAtlasTest {


    @Test
    void shouldLoadTextureAtlases() {
        String asset1 = "test/files/mpcAnimation.atlas";
        String asset2 = "test/files/test2.atlas";
        String[] textures = {asset1, asset2};


        AssetManager assetManager = new AssetManager();
        ResourceService resourceService = new ResourceService(assetManager);
        resourceService.loadTextureAtlases(textures);

        assetManager.load(asset1, TextureAtlas.class);
        assetManager.load(asset2, TextureAtlas.class);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(asset1));
        AnimationRenderComponent testAnimator = new AnimationRenderComponent(atlas);

        ObjectSet<Texture> texture = atlas.getTextures();
        String tex = texture.toString();
        assertEquals("{test/files/mpcAnimation.png}",tex);

        testAnimator.addAnimation("main_player_run", 1f);
        assertTrue(testAnimator.hasAnimation("main_player_run"));;

        testAnimator.addAnimation("main_player_walk", 1f);
        assertTrue(testAnimator.hasAnimation("main_player_walk"));

        testAnimator.addAnimation("main_player_front", 1f);
        assertTrue(testAnimator.hasAnimation("main_player_front"));

        testAnimator.addAnimation("main_player_jump", 1f);
        assertTrue(testAnimator.hasAnimation("main_player_jump"));

        testAnimator.addAnimation("main_player_attack", 1f);
        assertTrue(testAnimator.hasAnimation("main_player_attack"));

        testAnimator.addAnimation("main_player_crouch", 1f);
        assertTrue(testAnimator.hasAnimation("main_player_crouch"));

        testAnimator.addAnimation("main_player_pickup", 1f);
        assertTrue(testAnimator.hasAnimation("main_player_pickup"));

        testAnimator.addAnimation("main_player_right", 1f);
        assertTrue(testAnimator.hasAnimation("main_player_right"));

        testAnimator.addAnimation("main_player_hurt", 1f);
        assertTrue(testAnimator.hasAnimation("main_player_hurt"));

        testAnimator.addAnimation("main_player_burn", 1f);
        assertTrue(testAnimator.hasAnimation("main_player_burn"));

        assertNotNull(atlas.findRegion("main_player_run"));
        assertTrue(testAnimator.hasAnimation("main_player_run"));;

        assertNotNull(atlas.findRegion("main_player_walk"));
        assertTrue(testAnimator.hasAnimation("main_player_walk"));

        assertNotNull(atlas.findRegion("main_player_front"));
        assertTrue(testAnimator.hasAnimation("main_player_front"));

        assertNotNull(atlas.findRegion("main_player_jump"));
        assertTrue(testAnimator.hasAnimation("main_player_jump"));

        assertNotNull(atlas.findRegion("main_player_attack"));
        assertTrue(testAnimator.hasAnimation("main_player_attack"));

        assertNotNull(atlas.findRegion("main_player_crouch"));
        assertTrue(testAnimator.hasAnimation("main_player_crouch"));

        assertNotNull(atlas.findRegion("main_player_pickup"));
        assertTrue(testAnimator.hasAnimation("main_player_pickup"));

        assertNotNull(atlas.findRegion("main_player_right"));
        assertTrue(testAnimator.hasAnimation("main_player_right"));

        assertNotNull(atlas.findRegion("main_player_hurt"));
        assertTrue(testAnimator.hasAnimation("main_player_hurt"));

        assertNotNull(atlas.findRegion("main_player_burn"));
        assertTrue(testAnimator.hasAnimation("main_player_burn"));


    }
}