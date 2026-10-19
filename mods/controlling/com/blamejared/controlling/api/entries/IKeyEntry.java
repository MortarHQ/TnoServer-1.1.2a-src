package com.blamejared.controlling.api.entries;

import java.util.List;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;

public interface IKeyEntry {
   Component categoryName();

   KeyMapping getKey();

   Component getKeyDesc();

   Button getBtnResetKeyBinding();

   Button getBtnChangeKeyBinding();

   List<GuiEventListener> children();

   boolean mouseClicked(double var1, double var3, int var5);

   boolean mouseReleased(double var1, double var3, int var5);

   void render(GuiGraphics var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10);
}
