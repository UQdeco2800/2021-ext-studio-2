package com.deco2800.game.components.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.deco2800.game.entities.configs.propStore.PropItemConfig;
import com.deco2800.game.files.PropStoreRecord;
import com.deco2800.game.ui.UIComponent;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

public class PropStoreItemDisplay extends UIComponent {
    private Dialog dialog;

    @Override
    public void create() {
        super.create();

        entity.getEvents().addListener("openItem", this::openItem);
    }


    private void openItem(PropItemConfig item){

        dialog = new Dialog("", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        Image img = new Image(new Texture(item.path));
        // Image background = new Image(new Texture("images/story/chapterDialog.png"));
        //background.setScaling(Scaling.fit);
        //dialog.setBackground(background.getDrawable());
        dialog.pad(50).padTop(120);
        Label desc = new Label(item.desc, new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        desc.setFontScale(1.1f);
        desc.setWrap(true);
        desc.setAlignment(Align.center);
        Label price = new Label("Gold $: " + item.price, new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        dialog.row();
        price.setFontScale(1.5f);
        price.setWrap(true);
        price.setAlignment(Align.center);

       // buyButton.setDisabled(true);
        dialog.getContentTable().add(img).height(122).width(240).row();
        dialog.getContentTable().add(desc).width(600).row();
        dialog.getContentTable().add(price).width(600).row();
        dialog.getButtonTable().add(renderButton(item)).size(600,100).row();
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();
        dialog.show(stage);

    }

    private ImageButton renderCloseButton() {
        Image crossButtonImg = new Image(new Texture("images/achievements/crossButton.png"));

        ImageButton closeButton = new ImageButton(crossButtonImg.getDrawable());

        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });

        return closeButton;
    }

    private TextButton renderButton(PropItemConfig item){
        if(PropStoreRecord.isItemBought(item)){
            TextButton buyButton = new TextButton("Bought!", skin);
            buyButton.setColor(Color.LIME);
            buyButton.setDisabled(true);
            return buyButton;
        }
        if(PropStoreRecord.hasEnoughGold(item.price)) {
            TextButton buyButton = new TextButton("Buy for " + item.price + " gold", skin);
            buyButton.setColor(Color.ROYAL);
            buyButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    PropStoreRecord.buyItem(item);
                    entity.getEvents().trigger("GoldUpdate");
                    buyButton.setText("Bought!");
                    buyButton.setColor(Color.LIME);
                    buyButton.setDisabled(true);
                }
            });
            return buyButton;
        }else{
            TextButton buyButton = new TextButton("You do not have enough Gold", skin);
            buyButton.setColor(Color.BLACK);
            buyButton.setDisabled(true);
            return buyButton;
        }
    }
    @Override
    protected void draw(SpriteBatch batch) {

    }
}
