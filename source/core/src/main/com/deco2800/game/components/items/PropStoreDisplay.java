package com.deco2800.game.components.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.deco2800.game.GdxGame;
import com.deco2800.game.entities.factories.PropStoreFactory;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A ui component for displaying the props shop.
 */
public class PropStoreDisplay extends UIComponent {
    private final GdxGame game;
    private static final int ITEM_COLUMN_COUNT = 3;




    public PropStoreDisplay(GdxGame game){
        PropStoreFactory.getPropStoreItems().forEach(item -> System.out.println(item.name));
        this.game = game;
    }


    @Override
    public void create() {
        super.create();
        addActors();
    }

    private void addActors() {
        stage.addActor(createBackground());
        stage.addActor(createExitButton());
        stage.addActor(createItemTable());
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    private Table createExitButton(){
        Image exitImg = new Image(ServiceLocator.getResourceService().getAsset("images/achievements/crossButton.png", Texture.class));
        ImageButton exitImageButton = new ImageButton(exitImg.getDrawable());
        exitImageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(GdxGame.ScreenType.MAIN_MENU);
            }
        });
        Table table = new Table();
        table.setFillParent(true);
        table.top().right();

        table.add(exitImageButton);
        return table;
    }

    private Table createItemTable(){
        AtomicInteger i = new AtomicInteger();
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.padTop(110f);
        PropStoreFactory.getPropStoreItems().forEach(item -> {
            Label price = new Label("Gold $: " + item.price, new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
            price.setFontScale(2f);
            TextButton button = new TextButton("View",skin);
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    entity.getEvents().trigger("openItem", item);
                }
            });
            button.setColor(Color.RED);
            Image img = new Image(ServiceLocator.getResourceService().getAsset(item.path, Texture.class));
            img.setScale(2,2);
            Table itemTable = new Table();
            itemTable.add(img).center().padRight(35f).padTop(50f);
            itemTable.row();
            itemTable.add(price).center();
            itemTable.padLeft(20f).padRight(20f).padTop(50f);
            itemTable.row();
            itemTable.add(button).center();
            table.add(itemTable);
            i.getAndIncrement();
           if(i.get() % (ITEM_COLUMN_COUNT) == 0){
               table.row();

           }


        });


        return table;

    }
    private Table createBackground(){
        Table background = new Table();
        background.setFillParent(true);
        Image bg = new Image(new Texture("images/Items/props/background_new.png"));
        bg.setScaling(Scaling.fit);
        background.background(bg.getDrawable());
        return background;
    }

}
