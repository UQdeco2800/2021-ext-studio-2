package com.deco2800.game.screens;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import com.badlogic.gdx.ScreenAdapter;

import com.deco2800.game.GdxGame;
import com.deco2800.game.components.BufferStartCountDownDisplay.BufferStartCountDownDisplay;
import com.deco2800.game.components.gameover.GameOverDisplay;
import com.deco2800.game.input.InputDecorator;
import com.deco2800.game.ui.UIComponent;

import com.deco2800.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BufferStartCountDown extends ScreenAdapter {

    private final BufferStartCountDownDisplay gomd;
    private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);

    public BufferStartCountDown(GdxGame game) {
        gomd = new BufferStartCountDownDisplay(game);
        ui.addComponent(gomd).addComponent(new InputDecorator(stage, 10));

    }
}



