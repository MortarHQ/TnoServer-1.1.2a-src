package xaero.map.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import xaero.lib.client.gui.IScreenBase;
import xaero.lib.client.gui.widget.dropdown.DropDownWidget;

public class ConfirmScreenBase extends ConfirmScreen implements IScreenBase {
   public Screen parent;
   public Screen escape;
   private boolean renderEscapeInBackground;
   protected boolean canSkipWorldRender;

   public ConfirmScreenBase(
      Screen parent, Screen escape, boolean renderEscapeInBackground, BooleanConsumer _callbackFunction, Component _title, Component _messageLine2
   ) {
      super(_callbackFunction, _title, _messageLine2);
      this.parent = parent;
      this.escape = escape;
      this.renderEscapeInBackground = renderEscapeInBackground;
      this.canSkipWorldRender = true;
   }

   public ConfirmScreenBase(
      Screen parent,
      Screen escape,
      boolean renderEscapeInBackground,
      BooleanConsumer p_i232270_1_,
      Component p_i232270_2_,
      Component p_i232270_3_,
      Component p_i232270_4_,
      Component p_i232270_5_
   ) {
      super(p_i232270_1_, p_i232270_2_, p_i232270_3_, p_i232270_4_, p_i232270_5_);
      this.parent = parent;
      this.escape = escape;
      this.renderEscapeInBackground = renderEscapeInBackground;
      this.canSkipWorldRender = true;
   }

   protected void onExit(Screen screen) {
      this.minecraft.setScreen(screen);
   }

   protected void goBack() {
      this.onExit(this.parent);
   }

   public void onClose() {
      this.onExit(this.escape);
   }

   public void renderEscapeScreen(GuiGraphics guiGraphics, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      if (this.escape != null) {
         this.escape.render(guiGraphics, p_230430_2_, p_230430_3_, p_230430_4_);
         guiGraphics.flush();
      }

      GlStateManager._clear(256, Minecraft.ON_OSX);
   }

   public void render(GuiGraphics guiGraphics, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
      if (this.renderEscapeInBackground) {
         this.renderEscapeScreen(guiGraphics, p_230430_2_, p_230430_3_, p_230430_4_);
      }

      super.render(guiGraphics, p_230430_2_, p_230430_3_, p_230430_4_);
   }

   public void init() {
      super.init();
      if (this.escape != null) {
         this.escape.init(this.minecraft, this.width, this.height);
      }
   }

   public boolean shouldSkipWorldRender() {
      return this.canSkipWorldRender
         && this.renderEscapeInBackground
         && this.escape instanceof IScreenBase
         && ((IScreenBase)this.escape).shouldSkipWorldRender();
   }

   public void onDropdownOpen(DropDownWidget menu) {
   }

   public void onDropdownClosed(DropDownWidget menu) {
   }

   public Screen getEscape() {
      return this.escape;
   }
}
