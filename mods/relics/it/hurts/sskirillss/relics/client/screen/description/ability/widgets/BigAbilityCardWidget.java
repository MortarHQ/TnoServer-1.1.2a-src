package it.hurts.sskirillss.relics.client.screen.description.ability.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.screen.base.IHoverableWidget;
import it.hurts.sskirillss.relics.client.screen.base.ITickingWidget;
import it.hurts.sskirillss.relics.client.screen.description.ability.AbilityDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.base.AbstractDescriptionWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.client.screen.description.research.particles.SmokeParticleData;
import it.hurts.sskirillss.relics.client.screen.utils.ParticleStorage;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class BigAbilityCardWidget extends AbstractDescriptionWidget implements IHoverableWidget, ITickingWidget {
   private AbilityDescriptionScreen screen;

   public BigAbilityCardWidget(int x, int y, AbilityDescriptionScreen screen) {
      super(x, y, 48, 74);
      this.screen = screen;
   }

   public void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      ItemStack stack = this.screen.getStack();
      if (stack.getItem() instanceof IRelicItem relic) {
         LocalPlayer var17 = this.minecraft.player;
         PoseStack poseStack = guiGraphics.pose();
         String ability = this.screen.getSelectedAbility();
         boolean isUnlocked = relic.isAbilityUnlocked(stack, ability);
         boolean canBeUpgraded = relic.canBeUpgraded(stack, ability);
         poseStack.pushPose();
         float color = (float)(1.05F + Math.sin((var17.tickCount + ability.length() * 10) * 0.2F) * 0.1F);
         if (isUnlocked) {
            GUIRenderer.begin(DescriptionTextures.getAbilityCardTexture(stack, ability), poseStack)
               .anchor(SpriteAnchor.TOP_LEFT)
               .color(color, color, color, 1.0F)
               .pos(this.getX() + 7, this.getY() + 10)
               .texSize(34, 49)
               .end();
         } else {
            GUIRenderer.begin(DescriptionTextures.BIG_CARD_BACKGROUND, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(this.getX() + 7, this.getY() + 10).end();
         }

         GUIRenderer.begin(
               canBeUpgraded
                  ? (isUnlocked ? DescriptionTextures.BIG_CARD_FRAME_UNLOCKED_ACTIVE : DescriptionTextures.BIG_CARD_FRAME_UNLOCKED_INACTIVE)
                  : (isUnlocked ? DescriptionTextures.BIG_CARD_FRAME_LOCKED_ACTIVE : DescriptionTextures.BIG_CARD_FRAME_LOCKED_INACTIVE),
               poseStack
            )
            .anchor(SpriteAnchor.TOP_LEFT)
            .pos(this.getX(), this.getY())
            .end();
         int xOff = 0;
         if (isUnlocked && canBeUpgraded) {
            for (int i = 0; i < 5; i++) {
               GUIRenderer.begin(DescriptionTextures.BIG_STAR_HOLE, poseStack)
                  .anchor(SpriteAnchor.TOP_LEFT)
                  .pos(this.getX() + xOff + 4, this.getY() + 63)
                  .end();
               xOff += 8;
            }

            int var18 = 0;
            int quality = relic.getAbilityQuality(stack, ability);
            boolean isAliquot = quality % 2 == 1;

            for (int i = 0; i < Math.floor(quality / 2.0); i++) {
               GUIRenderer.begin(DescriptionTextures.BIG_STAR_ACTIVE, poseStack)
                  .anchor(SpriteAnchor.TOP_LEFT)
                  .pos(this.getX() + var18 + 4, this.getY() + 63)
                  .end();
               var18 += 8;
            }

            if (isAliquot) {
               GUIRenderer.begin(DescriptionTextures.BIG_STAR_ACTIVE, poseStack)
                  .anchor(SpriteAnchor.TOP_LEFT)
                  .pos(this.getX() + var18 + 4, this.getY() + 63)
                  .patternSize(4, 7)
                  .texSize(8, 7)
                  .end();
            }
         }

         if (canBeUpgraded) {
            poseStack.pushPose();
            MutableComponent pointsComponent = Component.literal(isUnlocked ? String.valueOf(relic.getAbilityLevel(stack, ability)) : "?")
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
         }

         if (isUnlocked && canBeUpgraded && this.isHovered()) {
            GUIRenderer.begin(DescriptionTextures.BIG_CARD_FRAME_OUTLINE, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(this.getX() - 1, this.getY() - 1).end();
         }

         poseStack.popPose();
      }
   }

   @Override
   public void onHovered(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      ItemStack stack = this.screen.getStack();
      String ability = this.screen.getSelectedAbility();
      if (stack.getItem() instanceof IRelicItem relic && relic.isAbilityUnlocked(stack, ability) && relic.canBeUpgraded(stack, ability)) {
         PoseStack poseStack = guiGraphics.pose();
         List<FormattedCharSequence> tooltip = Lists.newArrayList();
         int maxWidth = 150;
         int renderWidth = 0;
         List<MutableComponent> entries = Lists.newArrayList(
            new MutableComponent[]{
               Component.literal("")
                  .append(
                     Component.translatable("tooltip.relics.researching.ability.info.level").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.UNDERLINE)
                  )
                  .append(" " + relic.getAbilityLevel(stack, ability) + "/" + relic.getAbilityMaxLevel(stack, ability)),
               Component.literal("")
                  .append(
                     Component.translatable("tooltip.relics.researching.ability.info.quality")
                        .withStyle(ChatFormatting.BOLD)
                        .withStyle(ChatFormatting.UNDERLINE)
                  )
                  .append(" " + MathUtils.round(relic.getAbilityQuality(stack, ability) / 2.0F, 1) + "/" + relic.getMaxQuality() / 2),
               Component.literal(" ")
            }
         );
         if (Screen.hasShiftDown()) {
            entries.add(Component.translatable("tooltip.relics.researching.ability.info.extra_info").withStyle(ChatFormatting.ITALIC));
         } else {
            entries.add(Component.translatable("tooltip.relics.researching.general.extra_info"));
         }

         for (MutableComponent entry : entries) {
            int entryWidth = this.minecraft.font.width(entry) / 2;
            if (entryWidth > renderWidth) {
               renderWidth = Math.min(entryWidth + 2, maxWidth);
            }

            tooltip.addAll(this.minecraft.font.split(entry, maxWidth * 2));
         }

         poseStack.pushPose();
         poseStack.translate(0.0F, 0.0F, 400.0F);
         DescriptionUtils.drawTooltipBackground(guiGraphics, renderWidth, tooltip.size() * 5, mouseX - 9 - renderWidth / 2, mouseY);
         poseStack.scale(0.5F, 0.5F, 0.5F);
         int yOff = 0;

         for (FormattedCharSequence entry : tooltip) {
            guiGraphics.drawString(this.minecraft.font, entry, (mouseX - renderWidth / 2 + 1) * 2, (mouseY + yOff + 9) * 2, 6696723, false);
            yOff += 5;
         }

         poseStack.popPose();
      }
   }

   @Override
   public void onTick() {
      ItemStack stack = this.screen.getStack();
      String ability = this.screen.getSelectedAbility();
      if (stack.getItem() instanceof IRelicItem relic) {
         boolean isUnlocked = relic.isAbilityUnlocked(stack, ability);
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
