package it.hurts.sskirillss.relics.client.screen.description.experience.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.screen.base.ITickingWidget;
import it.hurts.sskirillss.relics.client.screen.description.experience.ExperienceDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.base.AbstractDescriptionWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.client.screen.description.research.particles.SmokeParticleData;
import it.hurts.sskirillss.relics.client.screen.utils.ParticleStorage;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class BigExperienceCardWidget extends AbstractDescriptionWidget implements ITickingWidget {
   private ExperienceDescriptionScreen screen;

   public BigExperienceCardWidget(int x, int y, ExperienceDescriptionScreen screen) {
      super(x, y, 48, 74);
      this.screen = screen;
   }

   public void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      ItemStack stack = this.screen.getStack();
      if (stack.getItem() instanceof IRelicItem relic) {
         LocalPlayer var14 = this.minecraft.player;
         PoseStack poseStack = guiGraphics.pose();
         String source = this.screen.getSelectedSource();
         LevelingSourceData sourceData = relic.getLevelingSourcesData().getSources().get(source);
         boolean isUnlocked = relic.isLevelingSourceUnlocked(stack, source);
         float color = (float)(1.05F + Math.sin((var14.tickCount + sourceData.getId().length() * 10) * 0.2F) * 0.1F);
         poseStack.pushPose();
         if (isUnlocked) {
            GUIRenderer.begin(sourceData.getIcon().apply(stack), poseStack)
               .anchor(SpriteAnchor.TOP_LEFT)
               .color(color, color, color, 1.0F)
               .pos(this.getX() + 7, this.getY() + 10)
               .texSize(34, 49)
               .end();
            RenderSystem.enableBlend();
            GUIRenderer.begin(
                  ResourceLocation.fromNamespaceAndPath(
                     "relics", "textures/gui/description/experience/filters/" + sourceData.getColor().name().toLowerCase(Locale.ROOT) + ".png"
                  ),
                  poseStack
               )
               .anchor(SpriteAnchor.TOP_LEFT)
               .pos(this.getX() + 7, this.getY() + 10)
               .texSize(34, 49)
               .end();
            RenderSystem.disableBlend();
         } else {
            GUIRenderer.begin(DescriptionTextures.BIG_CARD_BACKGROUND, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(this.getX() + 7, this.getY() + 10).end();
         }

         GUIRenderer.begin(isUnlocked ? DescriptionTextures.BIG_CARD_FRAME_UNLOCKED_ACTIVE : DescriptionTextures.BIG_CARD_FRAME_UNLOCKED_INACTIVE, poseStack)
            .anchor(SpriteAnchor.TOP_LEFT)
            .pos(this.getX(), this.getY())
            .end();
         poseStack.pushPose();
         MutableComponent pointsComponent = Component.literal(isUnlocked ? String.valueOf(relic.getLevelingSourceLevel(stack, source)) : "?")
            .withStyle(ChatFormatting.BOLD);
         poseStack.scale(0.75F, 0.75F, 1.0F);
         guiGraphics.drawString(
            this.minecraft.font,
            pointsComponent,
            (int)((this.getX() + 25.5F) * 1.33F - this.minecraft.font.width(pointsComponent) / 2.0F),
            (int)((this.getY() + 4) * 1.33F),
            isUnlocked ? 16769656 : 12037849,
            true
         );
         poseStack.popPose();
         poseStack.popPose();
      }
   }

   @Override
   public void onTick() {
      ItemStack stack = this.screen.getStack();
      String source = this.screen.getSelectedSource();
      if (stack.getItem() instanceof IRelicItem relic) {
         boolean isUnlocked = relic.isLevelingSourceUnlocked(stack, source);
         if (!isUnlocked) {
            RandomSource random = this.minecraft.player.getRandom();
            ParticleStorage.addParticle(
               this.screen,
               new SmokeParticleData(
                     this.getX() + 11 + random.nextInt(27),
                     this.getY() + 15 + random.nextInt(43),
                     0.75F + random.nextFloat() * 0.25F,
                     20 + random.nextInt(40),
                     0.5F
                  )
                  .setDeltaX(MathUtils.randomFloat(random) * 0.1F)
                  .setDeltaY(MathUtils.randomFloat(random) * 0.1F)
            );
         }
      }
   }

   @Override
   public void playDownSound(SoundManager handler) {
   }
}
