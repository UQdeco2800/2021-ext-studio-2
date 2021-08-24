package com.deco2800.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.entities.EntityService;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.input.InputService;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.services.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deco2800.game.GdxGame;
import com.deco2800.game.entities.factories.RenderFactory;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.components.gameover.GameOverDisplay;

public class GameOverScreen extends ScreenAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);
    private static final String[] mainGameTextures = {"images/gobg.jpg"};
    private final Renderer renderer;
    private final GameOverDisplay gomd;


    public GameOverScreen(GdxGame game) {
        logger.debug("drawing game over ui");
        ServiceLocator.registerInputService(new InputService());
        ServiceLocator.registerResourceService(new ResourceService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());
        renderer = RenderFactory.createRenderer();
        renderer.getCamera().getEntity().setPosition(2f, 1f);

        Stage stage = ServiceLocator.getRenderService().getStage();
        Entity ui = new Entity();
        gomd = new GameOverDisplay(game);
        ui.addComponent(gomd).addComponent(new InputDecorator(stage, 10));
        ServiceLocator.getEntityService().register(ui);

    }

    public void setPoints(double points){
        this.gomd.setPoints(points);
    }

    @Override
    public void render(float delta) {
        ServiceLocator.getEntityService().update();
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
        logger.info("Resized renderer: ({} x {})", width, height);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        ServiceLocator.getRenderService().dispose();
        ServiceLocator.getEntityService().dispose();
        ServiceLocator.clear();
    }


}