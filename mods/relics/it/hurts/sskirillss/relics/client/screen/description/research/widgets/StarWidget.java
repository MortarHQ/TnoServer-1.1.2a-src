package it.hurts.sskirillss.relics.client.screen.description.research.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.hurts.sskirillss.relics.client.screen.base.ITickingWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.base.AbstractDescriptionWidget;
import it.hurts.sskirillss.relics.client.screen.description.research.AbilityResearchScreen;
import it.hurts.sskirillss.relics.client.screen.description.research.particles.ResearchParticleData;
import it.hurts.sskirillss.relics.client.screen.utils.ParticleStorage;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.research.StarData;
import it.hurts.sskirillss.relics.utils.data.AnimationData;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

public class StarWidget extends AbstractDescriptionWidget implements ITickingWidget {
   private AbilityResearchScreen screen;
   private StarData star;

   public StarWidget(int x, int y, AbilityResearchScreen screen, StarData star) {
      super(x, y, 17, 17);
      this.screen = screen;
      this.star = star;
   }

   @Override
   public boolean isLocked() {
      return this.screen.stack.getItem() instanceof IRelicItem relic && relic.isAbilityResearched(this.screen.stack, this.screen.ability);
   }

   @Override
   public void onPress() {
      if (!this.isLocked() && this.screen.getOccupiedConnectionsCount(this.star) < this.screen.getTotalConnectionsCount(this.star)) {
         this.screen.selectedStar = this.star;
      }
   }

   public void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      this.isHovered = guiGraphics.containsPointInScissor(pMouseX, pMouseY)
         && pMouseX >= this.getX()
         && pMouseY >= this.getY()
         && pMouseX < this.getX() + this.width
         && pMouseY < this.getY() + this.height;
      int index = this.star.getIndex();
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null) {
         PoseStack poseStack = guiGraphics.pose();
         poseStack.pushPose();
         float time = player.tickCount + pPartialTick + index * 100.0F;
         poseStack.pushPose();
         RenderSystem.enableBlend();
         poseStack.translate(this.getX() + this.width / 2.0F, this.getY() + this.height / 2.0F, 0.0F);
         int totalCount = this.screen.getTotalConnectionsCount(this.star);
         int connectedCount = this.screen.getOccupiedConnectionsCount(this.star);
         float angle = time * 0.05F;
         float radius = (float)(8.0 + Math.sin(time * 0.1F));
         float angleStep = (float)((Math.PI * 2) / totalCount);

         for (int i = 0; i < totalCount; i++) {
            float color = (float)(0.25 + Math.sin((time + i * 10) * 0.25F) * 0.5);
            if (i < connectedCount) {
               RenderSystem.setShaderColor(0.25F, 1.0F + color, 1.0F, 1.0F);
            } else {
               RenderSystem.setShaderColor(1.0F + color, 1.0F + color, 1.0F + color, 1.0F);
            }

            float currentAngle = angle + i * angleStep;
            float x = (float)(radius * Math.cos(currentAngle));
            float y = (float)(radius * Math.sin(currentAngle));
            GUIRenderer.begin(ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/research/dot.png"), poseStack).pos(x, y).end();
         }

         RenderSystem.disableBlend();
         poseStack.popPose();
         poseStack.pushPose();
         float color = (float)(1.1F + Math.sin(time) * 0.2F);
         RenderSystem.setShaderColor(color, color, color, 1.0F);
         RenderSystem.enableBlend();
         poseStack.translate(this.getX() + 0.5F + this.width / 2.0F, this.getY() + 0.5F + this.height / 2.0F, 0.0F);
         poseStack.mulPose(Axis.ZN.rotationDegrees(time * 0.75F * (index % 2 == 0 ? 1 : -1)));
         GUIRenderer.begin(ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/research/star.png"), poseStack)
            .texSize(17, 136)
            .patternSize(17, 17)
            .scale((float)(1.0 + Math.sin(time * 0.5F) * 0.1F))
            .animation(AnimationData.builder().frame(0, 2).frame(1, 2).frame(2, 2).frame(3, 2).frame(4, 2).frame(5, 2).frame(6, 2).frame(7, 2))
            .end();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableBlend();
         poseStack.popPose();
         poseStack.popPose();
      }
   }

   @Override
   public void onTick() {
      RandomSource random = this.minecraft.player.getRandom();
      if (this.minecraft.player.tickCount % 5 == 0) {
         ParticleStorage.addParticle(
            this.screen,
            new ResearchParticleData(
               new Color(100 + random.nextInt(150), random.nextInt(25), 200 + random.nextInt(50)),
               this.getX() + random.nextFloat() * this.width,
               this.getY() + random.nextFloat() * this.height,
               1.0F + random.nextFloat() * 0.25F,
               10 + random.nextInt(30),
               0.01F
            )
         );
      }
   }

   @Override
   public void playDownSound(SoundManager handler) {
   }

   public StarData getStar() {
      return this.star;
   }
}
