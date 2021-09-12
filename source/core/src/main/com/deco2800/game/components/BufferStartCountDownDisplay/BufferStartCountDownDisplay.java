package com.deco2800.game.components.BufferStartCountDownDisplay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.gameover.GameOverDisplay;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.rendering.Renderer;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.GdxGame;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.ScreenAdapter;
import com.deco2800.game.rendering.RenderService;
import com.deco2800.game.rendering.Renderer;


import static java.util.concurrent.TimeUnit.SECONDS;



public abstract class BufferStartCountDownDisplay extends UIComponent{
    private static final Logger logger = LoggerFactory.getLogger(MainGameScreen.class);

    private final GdxGame game;

    public BufferStartCountDownDisplay(GdxGame game) {
        super();
        this.game = game;
    }


    public static void main(String[] args) {

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Runnable runnable = new Runnable() {
            int countdownStarter = 5;

            public void run() {

                System.out.println(countdownStarter);
                countdownStarter--;

                if (countdownStarter < 0) {
                    System.out.println("Start the Game!");
                    scheduler.shutdown();
                }
            }
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);

    }
    private void OpenMainGame(){
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }




    }

