package com.deco2800.game.components.ItemBar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

import java.util.ArrayList;

public class ItemBarDisplay extends UIComponent {

    private static Table itembartable;
    private static Table counttable;
    private static Label countlabel;
    private static final ArrayList<Image> itemImage = new ArrayList<>();
    private static final newItembar bar = new newItembar();
    private static final String counts = bar.getcounts();

    /**
     * Creates reusable ui styles and adds actors to the stage.
     */
    @Override
    public void create() {
        super.create();
        addActors();
        entity.getEvents().addListener("updateitembar", this::updateItemBarUI);
    }

    private void addActors() {

        itembartable = new Table();
        itembartable.bottom();
        itembartable.setFillParent(true);
        itembartable.padBottom(30).padRight(60);

        counttable = new Table();
        counttable.bottom();
        counttable.setFillParent(true);
        counttable.padBottom(40).padRight(20);

        countlabel = new Label(bar.getcounts(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        countlabel.setFontScale(2f);

        float itemLength = 60f;

        itemImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/itembar/item/itembar-blood.png", Texture.class)));
        itemImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/itembar/item/itembar-water.png", Texture.class)));
        itemImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/itembar/item/itembar-leg.png", Texture.class)));
        itemImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/pao.png", Texture.class)));

        itembartable.add(itemImage.get(0)).size(itemLength).pad(16);
        itembartable.add(itemImage.get(1)).size(itemLength).pad(16);
        itembartable.add(itemImage.get(2)).size(itemLength).pad(16);
        itembartable.add(itemImage.get(3)).size(itemLength).pad(16);

        counttable.add(countlabel);
        stage.addActor(itembartable);
        stage.addActor(counttable);
    }

    /**
     * @param item the item should be added or removed in item bar
     * @param flag int 1 represents add, int -1 represents remove
     */
    public static void addorremove(String item, int flag) {
        if(flag == 1){
            switch (item) {
                case "kit" :
                    bar.addkit();
                    break;
                case "water" :
                    bar.addwater();
                    break;
                case "food" :
                    bar.addfood();
                    break;
                default:
            }
        }
        else if(flag == -1){
            switch (item) {
                case "kit" :
                    bar.usekit();
                    break;
                case "water" :
                    bar.usewater();
                    break;
                case "food" :
                    bar.usefood();
                    break;
                default:
            }
        }
        counttable.reset();
        counttable.bottom().right();
        counttable.setFillParent(true);
        counttable.padBottom(60).padRight(490);
        countlabel = new Label((CharSequence) bar.getcounts()+" ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        countlabel.setFontScale(2f);
        counttable.add(countlabel);
    }

    @Override
    public void draw(SpriteBatch batch) {

    }


    @Override
    public void update() {
        super.update();
        entity.getEvents().trigger("updateitembar");
    }


    public void updateItemBarUI() {
        CharSequence text = bar.getcounts();
        countlabel.setText(text);
    }


    @Override
    public void dispose() {
        super.dispose();
        countlabel.remove();
        counttable.remove();
    }

}
