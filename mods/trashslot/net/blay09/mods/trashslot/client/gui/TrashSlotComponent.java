package net.blay09.mods.trashslot.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.blay09.mods.balm.mixin.AbstractContainerScreenAccessor;
import net.blay09.mods.balm.mixin.SlotAccessor;
import net.blay09.mods.trashslot.TrashSlotSaveState;
import net.blay09.mods.trashslot.api.IGuiContainerLayout;
import net.blay09.mods.trashslot.api.SlotRenderStyle;
import net.blay09.mods.trashslot.api.Snap;
import net.blay09.mods.trashslot.client.ContainerSettings;
import net.blay09.mods.trashslot.client.TrashSlotGuiHandler;
import net.blay09.mods.trashslot.client.TrashSlotSlot;
import net.blay09.mods.trashslot.client.deletion.DeletionProvider;
import net.blay09.mods.trashslot.config.TrashSlotConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class TrashSlotComponent {
   private static final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("trashslot", "textures/gui/slot.png");
   private static final int SNAP_SIZE = 7;
   private final AbstractContainerScreen<?> screen;
   private final IGuiContainerLayout layout;
   private final ContainerSettings settings;
   private final TrashSlotSlot trashSlot;
   private SlotRenderStyle renderStyle = SlotRenderStyle.LONE;
   private boolean wasMouseDown;
   private boolean isDragging;
   private int dragStartX;
   private int dragStartY;

   public TrashSlotComponent(AbstractContainerScreen<?> screen, IGuiContainerLayout layout, ContainerSettings settings, TrashSlotSlot trashSlot) {
      this.screen = screen;
      this.layout = layout;
      this.settings = settings;
      this.trashSlot = trashSlot;
   }

   public boolean isInside(int mouseX, int mouseY) {
      int anchoredX = this.getAnchoredX();
      int anchoredY = this.getAnchoredY();
      int renderX = anchoredX + this.renderStyle.getRenderOffsetX() + this.layout.getSlotOffsetX(this.screen, this.renderStyle);
      int renderY = anchoredY + this.renderStyle.getRenderOffsetY() + this.layout.getSlotOffsetY(this.screen, this.renderStyle);
      return mouseX >= renderX
         && mouseY >= renderY
         && mouseX < renderX + this.renderStyle.getRenderWidth()
         && mouseY < renderY + this.renderStyle.getRenderHeight();
   }

   public void update(int mouseX, int mouseY) {
      int anchoredX = this.getAnchoredX();
      int anchoredY = this.getAnchoredY();
      int renderX = anchoredX + this.renderStyle.getRenderOffsetX() + this.layout.getSlotOffsetX(this.screen, this.renderStyle);
      int renderY = anchoredY + this.renderStyle.getRenderOffsetY() + this.layout.getSlotOffsetY(this.screen, this.renderStyle);
      boolean isMouseOver = mouseX >= renderX
         && mouseY >= renderY
         && mouseX < renderX + this.renderStyle.getRenderWidth()
         && mouseY < renderY + this.renderStyle.getRenderHeight();
      if (TrashSlotGuiHandler.isLeftMouseDown()) {
         if (!this.isDragging
            && isMouseOver
            && !this.wasMouseDown
            && !this.settings.isLocked()
            && Minecraft.getInstance().player.containerMenu.getCarried().isEmpty()
            && (!this.trashSlot.hasItem() || !((AbstractContainerScreenAccessor)this.screen).callIsHovering(this.trashSlot, mouseX, mouseY))) {
            this.dragStartX = renderX - mouseX;
            this.dragStartY = renderY - mouseY;
            this.isDragging = true;
         }

         this.wasMouseDown = true;
      } else {
         if (this.isDragging) {
            TrashSlotSaveState.save();
            this.isDragging = false;
         }

         this.wasMouseDown = false;
      }

      if (this.isDragging) {
         int targetX = mouseX + this.dragStartX;
         int targetY = mouseY + this.dragStartY;

         for (Rect2i collisionArea : this.layout.getCollisionAreas(this.screen)) {
            int targetRight = targetX + this.renderStyle.getWidth();
            int targetBottom = targetY + this.renderStyle.getHeight();
            int rectRight = collisionArea.getX() + collisionArea.getWidth();
            int rectBottom = collisionArea.getY() + collisionArea.getHeight();
            if (targetRight >= collisionArea.getX() && targetX < rectRight && targetBottom >= collisionArea.getY() && targetY < rectBottom) {
               int distLeft = targetRight - collisionArea.getX();
               int distRight = rectRight - targetX;
               int distTop = targetBottom - collisionArea.getY();
               int distBottom = rectBottom - targetY;
               if (anchoredX >= collisionArea.getX() && anchoredX < collisionArea.getX() + collisionArea.getWidth()) {
                  targetY = distTop < distBottom ? collisionArea.getY() - this.renderStyle.getHeight() : collisionArea.getY() + collisionArea.getHeight();
               } else {
                  targetX = distLeft < distRight ? collisionArea.getX() - this.renderStyle.getWidth() : collisionArea.getX() + collisionArea.getWidth();
               }
            }
         }

         if (!Screen.hasShiftDown()) {
            int bestSnapDist = Integer.MAX_VALUE;
            Snap bestSnap = null;

            for (Snap snap : this.layout.getSnaps(this.screen, this.renderStyle)) {
               int dist = Integer.MAX_VALUE;
               switch (snap.getType()) {
                  case HORIZONTAL:
                     dist = Math.abs(snap.getY() - targetY);
                     break;
                  case VERTICAL:
                     dist = Math.abs(snap.getX() - targetX);
                     break;
                  case FIXED:
                     int distX = snap.getX() - targetX;
                     int distY = snap.getY() - targetY;
                     dist = (int)Math.sqrt(distX * distX + distY * distY);
               }

               if (dist < 7 && dist < bestSnapDist) {
                  bestSnap = snap;
                  bestSnapDist = dist;
               }
            }

            if (bestSnap != null) {
               if (bestSnap.getType() == Snap.Type.VERTICAL || bestSnap.getType() == Snap.Type.FIXED) {
                  targetX = bestSnap.getX();
               }

               if (bestSnap.getType() == Snap.Type.HORIZONTAL || bestSnap.getType() == Snap.Type.FIXED) {
                  targetY = bestSnap.getY();
               }
            }
         }

         targetX = Mth.clamp(targetX, 0, this.screen.width - this.renderStyle.getRenderWidth());
         targetY = Mth.clamp(targetY, 0, this.screen.height - this.renderStyle.getRenderHeight());
         this.settings.setSlotX(this.getUnanchoredX(targetX));
         this.settings.setSlotY(this.getUnanchoredY(targetY));
      }
   }

   public void drawBackground(GuiGraphics guiGraphics) {
      int renderX = this.getAnchoredX();
      int renderY = this.getAnchoredY();
      this.renderStyle = this.layout.getSlotRenderStyle(this.screen, renderX, renderY);
      AbstractContainerScreenAccessor screenAccessor = (AbstractContainerScreenAccessor)this.screen;
      ((SlotAccessor)this.trashSlot)
         .setX(renderX - screenAccessor.getLeftPos() + this.renderStyle.getSlotOffsetX() + this.layout.getSlotOffsetX(this.screen, this.renderStyle));
      ((SlotAccessor)this.trashSlot)
         .setY(renderY - screenAccessor.getTopPos() + this.renderStyle.getSlotOffsetY() + this.layout.getSlotOffsetY(this.screen, this.renderStyle));
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(0.0F, 0.0F, 1.0F);
      guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      renderX += this.renderStyle.getRenderOffsetX() + this.layout.getSlotOffsetX(this.screen, this.renderStyle);
      renderY += this.renderStyle.getRenderOffsetY() + this.layout.getSlotOffsetY(this.screen, this.renderStyle);
      DeletionProvider deletionProvider = TrashSlotConfig.getDeletionProvider();
      int texOffsetX = 0;
      if (deletionProvider == null || !deletionProvider.canUndeleteLast()) {
         texOffsetX = 64;
      }

      switch (this.renderStyle) {
         case LONE:
            guiGraphics.blit(texture, renderX, renderY, texOffsetX, 56, this.renderStyle.getRenderWidth(), this.renderStyle.getRenderHeight());
            break;
         case ATTACH_BOTTOM_CENTER:
            guiGraphics.blit(texture, renderX, renderY, texOffsetX, 0, this.renderStyle.getRenderWidth(), this.renderStyle.getRenderHeight());
            guiGraphics.blit(texture, renderX, renderY, texOffsetX + 50, 29, 4, 4);
            guiGraphics.blit(texture, renderX + this.renderStyle.getRenderWidth() - 4, renderY, texOffsetX + 54, 29, 4, 4);
            break;
         case ATTACH_BOTTOM_LEFT:
            guiGraphics.blit(texture, renderX, renderY, texOffsetX, 0, this.renderStyle.getRenderWidth(), this.renderStyle.getRenderHeight());
            guiGraphics.blit(texture, renderX + this.renderStyle.getRenderWidth() - 4, renderY, texOffsetX + 54, 29, 4, 4);
            break;
         case ATTACH_BOTTOM_RIGHT:
            guiGraphics.blit(texture, renderX, renderY, texOffsetX, 0, this.renderStyle.getRenderWidth(), this.renderStyle.getRenderHeight());
            guiGraphics.blit(texture, renderX, renderY, texOffsetX + 50, 29, 4, 4);
            break;
         case ATTACH_TOP_CENTER:
            guiGraphics.blit(texture, renderX, renderY, texOffsetX + 32, 0, this.renderStyle.getRenderWidth(), this.renderStyle.getRenderHeight());
            guiGraphics.blit(texture, renderX, renderY + this.renderStyle.getRenderHeight() - 4, texOffsetX + 50, 25, 4, 4);
            guiGraphics.blit(
               texture, renderX + this.renderStyle.getRenderWidth() - 4, renderY + this.renderStyle.getRenderHeight() - 4, texOffsetX + 54, 25, 4, 4
            );
            break;
         case ATTACH_TOP_LEFT:
            guiGraphics.blit(texture, renderX, renderY, texOffsetX + 32, 0, this.renderStyle.getRenderWidth(), this.renderStyle.getRenderHeight());
            guiGraphics.blit(
               texture, renderX + this.renderStyle.getRenderWidth() - 4, renderY + this.renderStyle.getRenderHeight() - 4, texOffsetX + 54, 25, 4, 4
            );
            break;
         case ATTACH_TOP_RIGHT:
            guiGraphics.blit(texture, renderX, renderY, texOffsetX + 32, 0, this.renderStyle.getRenderWidth(), this.renderStyle.getRenderHeight());
            guiGraphics.blit(texture, renderX, renderY + this.renderStyle.getRenderHeight() - 4, texOffsetX + 50, 25, 4, 4);
            break;
         case ATTACH_LEFT_CENTER:
            guiGraphics.blit(texture, renderX, renderY, texOffsetX + 25, 25, this.renderStyle.getRenderWidth(), this.renderStyle.getRenderHeight());
            guiGraphics.blit(texture, renderX + this.renderStyle.getRenderWidth() - 4, renderY, texOffsetX + 50, 33, 4, 4);
            guiGraphics.blit(
               texture, renderX + this.renderStyle.getRenderWidth() - 4, renderY + this.renderStyle.getRenderHeight() - 4, texOffsetX + 50, 37, 4, 4
            );
            break;
         case ATTACH_LEFT_TOP:
            guiGraphics.blit(texture, renderX, renderY, texOffsetX + 25, 25, this.renderStyle.getRenderWidth(), this.renderStyle.getRenderHeight());
            guiGraphics.blit(
               texture, renderX + this.renderStyle.getRenderWidth() - 4, renderY + this.renderStyle.getRenderHeight() - 4, texOffsetX + 50, 37, 4, 4
            );
            break;
         case ATTACH_LEFT_BOTTOM:
            guiGraphics.blit(texture, renderX, renderY, texOffsetX + 25, 25, this.renderStyle.getRenderWidth(), this.renderStyle.getRenderHeight());
            guiGraphics.blit(texture, renderX + this.renderStyle.getRenderWidth() - 4, renderY, texOffsetX + 50, 33, 4, 4);
            break;
         case ATTACH_RIGHT_CENTER:
            guiGraphics.blit(texture, renderX, renderY, texOffsetX, 25, this.renderStyle.getRenderWidth(), this.renderStyle.getRenderHeight());
            guiGraphics.blit(texture, renderX, renderY, texOffsetX + 54, 33, 4, 4);
            guiGraphics.blit(texture, renderX, renderY + this.renderStyle.getRenderHeight() - 4, texOffsetX + 54, 37, 4, 4);
            break;
         case ATTACH_RIGHT_TOP:
            guiGraphics.blit(texture, renderX, renderY, texOffsetX, 25, this.renderStyle.getRenderWidth(), this.renderStyle.getRenderHeight());
            guiGraphics.blit(texture, renderX, renderY + this.renderStyle.getRenderHeight() - 4, texOffsetX + 54, 37, 4, 4);
            break;
         case ATTACH_RIGHT_BOTTOM:
            guiGraphics.blit(texture, renderX, renderY, texOffsetX, 25, this.renderStyle.getRenderWidth(), this.renderStyle.getRenderHeight());
            guiGraphics.blit(texture, renderX, renderY, texOffsetX + 54, 33, 4, 4);
      }

      poseStack.popPose();
   }

   private int getAnchoredX() {
      AbstractContainerScreenAccessor screenAccessor = (AbstractContainerScreenAccessor)this.screen;
      return Mth.clamp(
         this.settings.getSlotX() + screenAccessor.getLeftPos() + (int)(screenAccessor.getImageWidth() * this.settings.getAnchorX()),
         0,
         this.screen.width - this.renderStyle.getRenderWidth()
      );
   }

   private int getUnanchoredX(int x) {
      AbstractContainerScreenAccessor screenAccessor = (AbstractContainerScreenAccessor)this.screen;
      return x - screenAccessor.getLeftPos() - (int)(screenAccessor.getImageWidth() * this.settings.getAnchorX());
   }

   private int getAnchoredY() {
      AbstractContainerScreenAccessor screenAccessor = (AbstractContainerScreenAccessor)this.screen;
      return Mth.clamp(
         this.settings.getSlotY() + screenAccessor.getTopPos() + (int)(screenAccessor.getImageHeight() * this.settings.getAnchorY()),
         0,
         this.screen.width - this.renderStyle.getRenderWidth()
      );
   }

   private int getUnanchoredY(int y) {
      AbstractContainerScreenAccessor screenAccessor = (AbstractContainerScreenAccessor)this.screen;
      return y - screenAccessor.getTopPos() - (int)(screenAccessor.getImageHeight() * this.settings.getAnchorY());
   }

   public boolean isVisible() {
      return this.settings.isEnabled();
   }

   public boolean isDragging() {
      return this.isDragging;
   }

   public Rect2i getRectangle() {
      int anchoredX = this.getAnchoredX();
      int anchoredY = this.getAnchoredY();
      int renderX = anchoredX + this.renderStyle.getRenderOffsetX() + this.layout.getSlotOffsetX(this.screen, this.renderStyle);
      int renderY = anchoredY + this.renderStyle.getRenderOffsetY() + this.layout.getSlotOffsetY(this.screen, this.renderStyle);
      return new Rect2i(renderX, renderY, this.renderStyle.getRenderWidth(), this.renderStyle.getRenderHeight());
   }
}
