package com.teamresourceful.resourcefullib.client.components.selection;

import com.teamresourceful.resourcefullib.client.scissor.ScissorBoxStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.jetbrains.annotations.NotNull;

public abstract class ListEntry implements GuiEventListener {
   protected abstract void render(
      @NotNull GuiGraphics var1,
      @NotNull ScissorBoxStack var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      boolean var10,
      float var11,
      boolean var12
   );
}
