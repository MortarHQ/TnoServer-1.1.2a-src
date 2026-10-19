package it.hurts.sskirillss.relics.client.screen.description.research.particles;

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

public class SmokeParticleData extends ParticleData {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/relic/particles/smoke.png");
   private final float fadeInPercentage;

   public SmokeParticleData(float xStart, float yStart, float scale, int lifeTime, float fadeInPercentage) {
      super(TEXTURE, new Color(1.0F, 1.0F, 1.0F), xStart, yStart, scale, lifeTime);
      this.fadeInPercentage = fadeInPercentage;
   }

   @Override
   public void tick(Screen screen) {
      super.tick(screen);
      LocalPlayer player = screen.getMinecraft().player;
      if (player != null) {
         float lifePercentage = (float)this.getLifeTime() / this.getMaxLifeTime();
         this.setX(this.getX() + this.getDeltaX() * lifePercentage);
         this.setY(this.getY() + this.getDeltaY() * lifePercentage);
      }
   }

   @Override
   public void render(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      PoseStack poseStack = guiGraphics.pose();
      float lifePercentage = (float)this.getLifeTime() / this.getMaxLifeTime();
      float maxScale = this.getScale();
      float ratio = 1.0F - lifePercentage;
      float scale = ratio <= this.fadeInPercentage ? maxScale * (ratio / this.fadeInPercentage) : maxScale;
      poseStack.pushPose();
      RenderSystem.setShaderColor(
         this.getColor().getRed() / 255.0F, this.getColor().getGreen() / 255.0F, this.getColor().getBlue() / 255.0F, lifePercentage * 0.75F
      );
      RenderSystem.enableBlend();
      poseStack.translate(Mth.lerp(partialTick, this.getXO(), this.getX()), Mth.lerp(partialTick, this.getYO(), this.getY()), 0.0F);
      poseStack.mulPose(Axis.ZP.rotationDegrees((this.getLifeTime() + partialTick) * lifePercentage));
      GUIRenderer.begin(TEXTURE, guiGraphics.pose()).scale(scale).anchor(SpriteAnchor.CENTER).end();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableBlend();
      poseStack.popPose();
   }
}
