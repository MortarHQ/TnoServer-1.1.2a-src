package it.hurts.sskirillss.relics.client.screen.description.general.widgets.base;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.hurts.sskirillss.relics.badges.base.AbstractBadge;
import it.hurts.sskirillss.relics.client.screen.base.IHoverableWidget;
import it.hurts.sskirillss.relics.client.screen.base.IRelicScreenProvider;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.sounds.SoundManager;

public abstract class AbstractBadgeWidget extends AbstractDescriptionWidget implements IHoverableWidget {
   private IRelicScreenProvider provider;

   public AbstractBadgeWidget(int x, int y, IRelicScreenProvider provider, AbstractBadge badge) {
      super(x, y, 13, 10);
      this.provider = provider;
   }

   public abstract AbstractBadge getBadge();

   public void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      if (this.minecraft.player != null) {
         PoseStack poseStack = guiGraphics.pose();
         poseStack.pushPose();
         float color = (float)(1.05F + Math.sin((this.minecraft.player.tickCount + this.getBadge().getId().length() * 10) * 0.2F) * 0.1F);
         RenderSystem.setShaderColor(color, color, color, 1.0F);
         poseStack.translate(this.getX(), this.getY(), 0.0F);
         if (this.isHovered) {
            poseStack.translate(this.width / 2.0F, this.height / 2.0F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees((float)Math.cos((this.minecraft.player.tickCount + pPartialTick) * 0.35F) * 7.5F));
            poseStack.translate(-(this.width / 2.0F), -(this.height / 2.0F), 0.0F);
         }

         guiGraphics.blit(this.getBadge().getIconTexture(), 0, 0, 0.0F, 0.0F, this.width, this.height, this.width, this.height);
         if (this.isHovered) {
            guiGraphics.blit(this.getBadge().getOutlineTexture(), -1, -1, 0.0F, 0.0F, this.width + 2, this.height + 2, this.width + 2, this.height + 2);
         }

         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         poseStack.popPose();
      }
   }

   @Override
   public void playDownSound(SoundManager handler) {
   }

   public IRelicScreenProvider getProvider() {
      return this.provider;
   }
}
