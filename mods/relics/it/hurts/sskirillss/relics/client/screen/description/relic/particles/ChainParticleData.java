package it.hurts.sskirillss.relics.client.screen.description.relic.particles;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.hurts.sskirillss.relics.client.screen.description.general.particles.base.ParticleData;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import java.awt.Color;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ChainParticleData extends ParticleData {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/relic/particles/chain.png");

   public ChainParticleData(Color color, float xStart, float yStart, float scale, int lifeTime) {
      super(TEXTURE, color, xStart, yStart, scale, lifeTime);
   }

   @Override
   public void tick(Screen screen) {
      super.tick(screen);
      LocalPlayer player = screen.getMinecraft().player;
      if (player != null) {
         float lifePercentage = (float)this.getLifeTime() / this.getMaxLifeTime();
         this.setX(this.getX() + this.getDeltaX() * lifePercentage);
         this.setY(this.getY() + this.getDeltaY() * lifePercentage + (this.getMaxLifeTime() - this.getLifeTime()));
      }
   }

   @Override
   public void render(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      PoseStack poseStack = guiGraphics.pose();
      float lifePercentage = (float)this.getLifeTime() / this.getMaxLifeTime();
      poseStack.pushPose();
      RenderSystem.setShaderColor(this.getColor().getRed() / 255.0F, this.getColor().getGreen() / 255.0F, this.getColor().getBlue() / 255.0F, 1.0F);
      RenderSystem.enableBlend();
      poseStack.translate(Mth.lerp(partialTick, this.getXO(), this.getX()), Mth.lerp(partialTick, this.getYO(), this.getY()), 0.0F);
      poseStack.mulPose(Axis.ZP.rotationDegrees((this.getLifeTime() + partialTick) * this.getDeltaX()));
      GUIRenderer.begin(TEXTURE, guiGraphics.pose()).scale(this.getScale() * lifePercentage).anchor(SpriteAnchor.CENTER).end();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableBlend();
      poseStack.popPose();
   }
}
