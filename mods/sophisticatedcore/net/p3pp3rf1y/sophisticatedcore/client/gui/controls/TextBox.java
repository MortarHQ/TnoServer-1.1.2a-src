package net.p3pp3rf1y.sophisticatedcore.client.gui.controls;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;

public class TextBox extends WidgetBase {
   private final EditBox editBox;
   @Nullable
   private String unfocusedEmptyHint = null;

   public TextBox(Position position, Dimension dimension) {
      super(position, dimension);
      this.editBox = new EditBox(this.minecraft.font, position.x(), position.y(), dimension.width(), dimension.height(), Component.empty());
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, Minecraft minecraft, int mouseX, int mouseY) {
   }

   @Override
   protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(0.0F, 0.0F, 100.0F);
      this.editBox.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
      if (this.editBox.getValue().isEmpty() && this.unfocusedEmptyHint != null && !this.editBox.isFocused()) {
         int x = this.editBox.getX() + this.editBox.getWidth() / 2 + 2;
         int y = this.editBox.isBordered() ? this.editBox.getY() + (this.editBox.getHeight() - 8) / 2 : this.editBox.getY();
         guiGraphics.drawCenteredString(this.font, this.unfocusedEmptyHint, x, y, this.editBox.textColor);
      }

      poseStack.popPose();
   }

   @Override
   public void setFocused(boolean focused) {
      if (this.editBox.isFocused() != focused) {
         this.editBox.setFocused(focused);
      }

      super.setFocused(focused);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (!this.editBox.isFocused()) {
         return false;
      }

      this.editBox.keyPressed(keyCode, scanCode, modifiers);
      if (keyCode == 257) {
         this.onEnterPressed();
      }

      return keyCode != 256;
   }

   protected void onEnterPressed() {
   }

   public String getValue() {
      return this.editBox.getValue();
   }

   public boolean charTyped(char codePoint, int modifiers) {
      return this.editBox.charTyped(codePoint, modifiers);
   }

   @Override
   public void updateNarration(NarrationElementOutput narrationElementOutput) {
      this.editBox.updateNarration(narrationElementOutput);
   }

   public void setValue(String value) {
      this.editBox.setValue(value);
   }

   public void setValueWithoutNotification(String value) {
      Consumer<String> temp = this.editBox.responder;
      this.editBox.setResponder(null);
      this.editBox.setValue(value);
      this.editBox.setResponder(temp);
   }

   public void setTextColor(int color) {
      this.editBox.setTextColor(color);
   }

   public void setTextColorUneditable(int color) {
      this.editBox.setTextColorUneditable(color);
   }

   public void setBordered(boolean bordered) {
      this.editBox.setBordered(bordered);
      if (!bordered) {
         this.editBox.setX(this.x + 1);
         this.editBox.setY(this.y + 1);
         this.editBox.setWidth(this.getWidth() - 6);
      } else {
         this.editBox.setX(this.x);
         this.editBox.setY(this.y);
         this.editBox.setWidth(this.getWidth());
      }
   }

   public void setMaxLength(int maxLength) {
      this.editBox.setMaxLength(maxLength);
   }

   public void setResponder(Consumer<String> responder) {
      this.editBox.setResponder(responder);
   }

   public void setEditable(boolean editable) {
      this.editBox.setEditable(editable);
   }

   public boolean isEditable() {
      return this.editBox.isEditable();
   }

   public void setUnfocusedEmptyHint(String hint) {
      this.unfocusedEmptyHint = hint;
   }

   @Override
   public void setPosition(Position position) {
      super.setPosition(position);
      this.editBox.setX(position.x());
      this.editBox.setY(position.y());
      this.setBordered(this.editBox.isBordered());
   }

   @Override
   protected void updateDimensions(int width, int height) {
      super.updateDimensions(width, height);
      this.editBox.setWidth(width);
      this.setBordered(this.editBox.isBordered());
   }
}
