package com.finderfeed.fdlib.systems.simple_screen;

import com.mojang.blaze3d.platform.Window;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.joml.Vector2f;

public abstract class SimpleFDScreen extends Screen {
   public float relX;
   public float relY;
   private List<FDWidget> tickableWidgets;

   public SimpleFDScreen() {
      super(Component.empty());
   }

   protected void init() {
      super.init();
      Window window = Minecraft.getInstance().getWindow();
      float width = window.getGuiScaledWidth();
      float height = window.getGuiScaledHeight();
      this.relX = width / 2.0F - this.getScreenWidth() / 2.0F;
      this.relY = height / 2.0F - this.getScreenHeight() / 2.0F;
   }

   public void tick() {
      super.tick();

      for (GuiEventListener widget : this.children()) {
         if (widget instanceof FDWidget fdWidget) {
            fdWidget.tick();
         }
      }
   }

   public Vector2f getAnchor(float wMod, float hMod) {
      Window window = Minecraft.getInstance().getWindow();
      float height = window.getGuiScaledHeight();
      float width = window.getGuiScaledWidth();
      return new Vector2f(width * wMod, height * hMod);
   }

   public Vector2f getCenterAnchor() {
      return this.getAnchor(0.5F, 0.5F);
   }

   public Vector2f getUpRightAnchor() {
      return this.getAnchor(1.0F, 0.0F);
   }

   public Vector2f getDownRightAnchor() {
      return this.getAnchor(1.0F, 1.0F);
   }

   public Vector2f getDownLeftAnchor() {
      return this.getAnchor(0.0F, 1.0F);
   }

   public Vector2f getScreenRelativeAnchor() {
      return new Vector2f(this.relX, this.relY);
   }

   public abstract float getScreenWidth();

   public abstract float getScreenHeight();
}
