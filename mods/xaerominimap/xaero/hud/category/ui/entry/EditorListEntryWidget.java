package xaero.hud.category.ui.entry;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import org.joml.Vector4f;
import xaero.hud.category.ui.GuiCategoryEditor;
import xaero.hud.category.ui.entry.widget.EditorTextField;
import xaero.lib.client.gui.widget.Tooltip;

public class EditorListEntryWidget extends EditorListEntryWithRootReference {
   protected AbstractWidget widget;
   private boolean widgetPressed;

   public EditorListEntryWidget(
      int entryX,
      int entryY,
      int entryW,
      int entryH,
      int index,
      GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList rowList,
      EditorListRootEntry root,
      AbstractWidget widget,
      Supplier<Tooltip> tooltipSupplier
   ) {
      super(entryX, entryY, entryW, entryH, index, rowList, root, tooltipSupplier);
      this.widget = widget;
   }

   @Override
   public boolean mouseClicked(GuiCategoryEditor<?, ?, ?, ?, ?, ?>.SettingRowList.Entry entry, double relativeMouseX, double relativeMouseY, int i) {
      boolean result = super.mouseClicked(entry, relativeMouseX, relativeMouseY, i);
      if (result) {
         return true;
      }

      if (this.widget instanceof AbstractButton) {
         return false;
      }

      if (!this.widget.isMouseOver(relativeMouseX, relativeMouseY)) {
         return false;
      }

      this.widgetPressed = true;
      return this.widget.mouseClicked(relativeMouseX, relativeMouseY, i);
   }

   @Override
   public boolean mouseReleased(double relativeMouseX, double relativeMouseY, int i) {
      if (this.widgetPressed) {
         this.widget.mouseReleased(relativeMouseX, relativeMouseY, i);
      }

      this.widgetPressed = false;
      super.mouseReleased(relativeMouseX, relativeMouseY, i);
      return false;
   }

   @Override
   public boolean mouseDragged(double relativeMouseX, double relativeMouseY, int i, double f, double g) {
      return this.widgetPressed && this.widget.mouseDragged(relativeMouseX, relativeMouseY, i, f, g)
         ? true
         : super.mouseDragged(relativeMouseX, relativeMouseY, i, f, g);
   }

   @Override
   public boolean mouseScrolled(double relativeMouseX, double relativeMouseY, double f, double g) {
      return this.widget.isMouseOver(relativeMouseX, relativeMouseY) && this.widget.mouseScrolled(relativeMouseX, relativeMouseY, f, g)
         ? true
         : super.mouseScrolled(relativeMouseX, relativeMouseY, f, g);
   }

   @Override
   public void mouseMoved(double relativeMouseX, double relativeMouseY) {
      this.widget.mouseMoved(relativeMouseX, relativeMouseY);
      super.mouseMoved(relativeMouseX, relativeMouseY);
   }

   @Override
   public boolean keyPressed(int i, int j, int k, boolean isRoot) {
      return this.widget.keyPressed(i, j, k) ? true : super.keyPressed(i, j, k, isRoot);
   }

   @Override
   public boolean keyReleased(int i, int j, int k) {
      return this.widget.keyReleased(i, j, k) ? true : super.keyReleased(i, j, k);
   }

   @Override
   public boolean charTyped(char c, int i) {
      return this.widget.charTyped(c, i) ? true : super.charTyped(c, i);
   }

   @Override
   public void tick() {
      super.tick();
   }

   @Override
   public String getNarration() {
      return super.getNarration();
   }

   @Override
   public String getHoverNarration() {
      return this.getNarration();
   }

   @Override
   public Component getMessage() {
      return this.widget.getMessage();
   }

   @Override
   public Component getNarrationMessage() {
      if (this.widget instanceof EditorTextField) {
         return ((EditorTextField)this.widget).createNarrationMessage();
      } else {
         return this.widget instanceof AbstractSliderButton
            ? Component.translatable("gui.narrate.slider", new Object[]{this.getMessage()})
            : Component.translatable("gui.narrate.button", new Object[]{this.getMessage()});
      }
   }

   @Override
   public EditorListEntry render(
      GuiGraphics guiGraphics,
      int index,
      int rowWidth,
      int rowHeight,
      int relativeMouseX,
      int relativeMouseY,
      boolean isMouseOver,
      float partialTicks,
      Font font,
      int globalMouseX,
      int globalMouseY,
      boolean includesSelected,
      boolean isRoot
   ) {
      PoseStack poseStack = guiGraphics.pose();
      EditorListEntry result = super.render(
         guiGraphics,
         index,
         rowWidth,
         rowHeight,
         relativeMouseX,
         relativeMouseY,
         isMouseOver,
         partialTicks,
         font,
         globalMouseX,
         globalMouseY,
         includesSelected,
         isRoot
      );
      Vector4f widgetPos = new Vector4f(this.widget.getX(), this.widget.getY(), 0.0F, 1.0F);
      widgetPos.mul(poseStack.last().pose());
      int xBU = this.widget.getX();
      int yBU = this.widget.getY();
      this.widget.setX((int)widgetPos.x());
      this.widget.setY((int)widgetPos.y());
      poseStack.pushPose();
      poseStack.setIdentity();
      this.widget.render(guiGraphics, globalMouseX, globalMouseY, partialTicks);
      poseStack.popPose();
      this.widget.setX(xBU);
      this.widget.setY(yBU);
      return this.widgetPressed ? null : result;
   }

   @Override
   public void setFocused(boolean bl) {
      if (this.widget.active && this.widget.visible && this.widget.isFocused() != bl) {
         this.widget.setFocused(bl);
      }

      super.setFocused(bl);
   }

   @Override
   protected boolean selectAction() {
      if (this.widget instanceof AbstractButton && this.widget.active) {
         ((AbstractButton)this.widget).onPress();
         Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         return false;
      } else {
         return false;
      }
   }
}
