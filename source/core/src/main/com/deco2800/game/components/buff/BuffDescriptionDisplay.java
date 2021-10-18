package com.deco2800.game.components.buff;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.concurrency.AsyncTaskQueue;
import com.deco2800.game.entities.configs.buff.BuffDescriptionConfig;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

/**
 * A UI component to display buff description cards and labels for corresponding descriptions
 */
public class BuffDescriptionDisplay extends UIComponent {
    public static boolean showFlag = false;
    private static final int RENDER_DURATION = 500;
    private Table table;
    private Label buffDescriptionLabel;
    @Override
    public void create() {
        super.create();
        AsyncTaskQueue.newQueue();
        loadAssets();
        addActors();
        entity.getEvents().addListener("updateBuffDescription", this::updateBuffDescriptionUI);
        entity.getEvents().addListener("clearBuffDescription", this::clear);
        entity.getEvents().addListener("displayBuffDescription", this::display);
    }

    /**
     * Load all buff' assets
     */
    private void loadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadAll();
    }

    /**
     * Unload all buff' assets
     */
    private void unloadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
    }

    /**
     * Adds a new table as an actor to the stage
     */
    private void addActors() {
        table = new Table();
        table.top();
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void draw(SpriteBatch batch) {

    }


    private void updateBuffDescriptionUI(BuffDescriptionConfig buffDescriptionConfig) {
        /* Queue expensive task to run on a separate
         * single thread to run asynchronously. */
        AsyncTaskQueue.enqueueTask(() -> {
            try {
                entity.getEvents().trigger("displayBuffDescription", buffDescriptionConfig);
                /* Wait for some time */
                Thread.sleep(RENDER_DURATION);
                this.clear();
            } catch (InterruptedException ignored) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private synchronized void display(BuffDescriptionConfig buffDescriptionConfig){
        /* Render buff description  */
        renderBuffDescription(buffDescriptionConfig);
    }


    private synchronized void clear(){
        /* Remove card from screen */
        showFlag = false;
        table.clear();
    }



    /**
     * Renders the current buff description on the table
     *
     */
    private void renderBuffDescription(BuffDescriptionConfig buffDescriptionConfig) {
        CharSequence text = buffDescriptionConfig.description;
        buffDescriptionLabel = new Label(text, skin, "large");
        table.clear();
        table.row();
        table.add(buffDescriptionLabel).padLeft(600);
        showFlag = true;
    }


    @Override
    public void dispose() {
        super.dispose();
        /* If the in game screen is out of focus, cancel all future tasks. This has to be
         * done in order to prevent memory leaks, unforeseen exceptions and
         * other nasty bugs.*/
        AsyncTaskQueue.cancelFutureTasksNow();

        if (buffDescriptionLabel != null) {
            buffDescriptionLabel.remove();
        }
        unloadAssets();
    }
}
