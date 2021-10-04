package com.deco2800.game.components.ItemBar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GameExtension.class)
class newItembarTest {

    @Test
    void addwater() {
        newItembar bar = spy(newItembar.class);
        for (int i = 0; i < 8; i++) {
            bar.addwater();
        }
        assertEquals(10,bar.getwater());
    }

    @Test
    void addfood() {
        newItembar bar = spy(newItembar.class);
        for (int i = 0; i < 8; i++) {
            bar.addfood();
        }
        assertEquals(10,bar.getfood());
    }

    @Test
    void addkit() {
        newItembar bar = spy(newItembar.class);
        for (int i = 0; i < 8; i++) {
            bar.addkit();
        }
        assertEquals(10,bar.getkit());
    }

}