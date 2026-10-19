package it.hurts.sskirillss.relics.client.screen.description.experience.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.screen.base.IHoverableWidget;
import it.hurts.sskirillss.relics.client.screen.base.ITickingWidget;
import it.hurts.sskirillss.relics.client.screen.description.experience.ExperienceDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.base.AbstractDescriptionWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.client.screen.description.relic.particles.ExperienceParticleData;
import it.hurts.sskirillss.relics.client.screen.utils.ParticleStorage;
import it.hurts.sskirillss.relics.client.screen.utils.ScreenUtils;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class ExperienceGemWidget extends AbstractDescriptionWidget implements ITickingWidget, IHoverableWidget {
   private final ExperienceDescriptionScreen screen;
   private final String source;
   private float scale = 1.0F;
   private float scaleOld = 1.0F;

   public ExperienceGemWidget(int x, int y, ExperienceDescriptionScreen screen, String source) {
      super(x, y, 32, 47);
      this.screen = screen;
      this.source = source;
   }

   @Override
   public void onPress() {
      if (!this.screen.getSelectedSource().equals(this.source)) {
         this.screen.setSelectedSource(this.source);
         this.screen.rebuildWidgets();

         for (Renderable entry : this.screen.renderables) {
            if (entry instanceof ExperienceGemWidget gem && gem.source.equals(this.source)) {
               gem.scale = this.scale;
               gem.scaleOld = this.scaleOld;
            }
         }
      }
   }

   public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && this.screen.stack.getItem() instanceof IRelicItem relic) {
         ItemStack var17 = this.screen.getStack();
         PoseStack poseStack = guiGraphics.pose();
         LevelingSourceData sourceData = relic.getLevelingSourcesData().getSources().get(this.source);
         boolean isUnlocked = relic.isLevelingSourceUnlocked(var17, this.source);
         poseStack.pushPose();
         RenderSystem.enableBlend();
         float partialTicks = this.minecraft.getTimer().getGameTimeDeltaPartialTick(false);
         float lerpedScale = Mth.lerp(partialTicks, this.scaleOld, this.scale);
         poseStack.scale(lerpedScale, lerpedScale, lerpedScale);
         poseStack.translate((this.getX() + this.width / 2.0F) / lerpedScale, (this.getY() + this.height / 2.0F) / lerpedScale, 0.0F);
         String shape = sourceData.getShape().name().toLowerCase(Locale.ROOT);
         float color = (float)(1.05F + Math.sin((player.tickCount + this.source.length() * 10) * 0.2F) * 0.1F);
         if (isUnlocked) {
            GUIRenderer.begin(sourceData.getIcon().apply(var17), poseStack).pos(0.0F, -1.0F).color(color, color, color, 1.0F).end();
         } else {
            GUIRenderer.begin(DescriptionTextures.SMALL_CARD_LOCK_BACKGROUND, poseStack).pos(0.0F, -1.0F).end();
         }

         GUIRenderer.begin(
               ResourceLocation.fromNamespaceAndPath(
                  "relics", "textures/gui/description/experience/gems/" + shape + "/" + sourceData.getColor().name().toLowerCase(Locale.ROOT) + ".png"
               ),
               poseStack
            )
            .end();
         GUIRenderer.begin(
               ResourceLocation.fromNamespaceAndPath(
                  "relics", "textures/gui/description/experience/gems/" + shape + "/frame_" + (isUnlocked ? "unlocked" : "locked") + ".png"
               ),
               poseStack
            )
            .end();
         RenderSystem.disableBlend();
         MutableComponent title = Component.literal(isUnlocked ? String.valueOf(relic.getLevelingSourceLevel(var17, this.source)) : "?")
            .withStyle(ChatFormatting.BOLD);
         float textScale = 0.5F;
         poseStack.scale(textScale, textScale, textScale);
         guiGraphics.drawString(
            this.minecraft.font,
            title,
            -((this.width + 1) / 2) - this.minecraft.font.width(title) / 2 + 16,
            -(this.height / 2) + 40,
            isUnlocked ? 16769656 : 12037849,
            true
         );
         poseStack.popPose();
      }
   }

   @Override
   public void onTick() {
      if (this.screen.stack.getItem() instanceof IRelicItem relic) {
         float var5 = 1.15F;
         float minScale = 1.0F;
         RandomSource random = this.minecraft.player.getRandom();
         this.scaleOld = this.scale;
         if (this.scale > var5) {
            this.scale = Math.max(minScale, this.scale - 0.01F);
         }

         if (this.isHovered()) {
            if (this.minecraft.player.tickCount % 3 == 0) {
               ParticleStorage.addParticle(
                  this.screen,
                  new ExperienceParticleData(
                     new Color(200 + random.nextInt(50), 150 + random.nextInt(100), 0),
                     this.getX() + random.nextInt(this.width),
                     this.getY() - 1,
                     1.0F + random.nextFloat() * 0.5F,
                     100 + random.nextInt(50)
                  )
               );
            }

            if (this.scale < var5) {
               this.scale = Math.min(var5, this.scale + 0.04F);
            }
         } else if (this.scale > minScale) {
            this.scale = Math.max(minScale, this.scale - 0.03F);
         }
      }
   }

   @Override
   public boolean isLocked() {
      return this.screen.getSelectedSource().equals(this.source);
   }

   @Override
   public void onHovered(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      ItemStack stack = this.screen.stack;
      if (stack.getItem() instanceof IRelicItem relic) {
         LevelingSourceData data = relic.getLevelingSourceData(this.source);
         if (data != null) {
            PoseStack poseStack = guiGraphics.pose();
            List<FormattedCharSequence> tooltip = Lists.newArrayList();
            MutableComponent title = Component.translatableWithFallback(data.getTranslationPath().apply(stack) + ".title", this.source);
            int maxWidth = 110;
            int renderWidth = Math.min(this.minecraft.font.width(title.withStyle(ChatFormatting.BOLD)) / 2 + 4, maxWidth);
            List<MutableComponent> entries = new ArrayList<>();
            entries.add(Component.literal(" "));
            int requiredLevel = data.getRequiredLevel();
            String requiredAbility = data.getRequiredAbility();
            if (relic.getRelicLevel(this.screen.stack) < requiredLevel) {
               entries.add(Component.literal(" "));
               entries.add(
                  Component.literal("")
                     .append(
                        Component.translatable(
                           "tooltip.relics.researching.relic.gem.low_level",
                           new Object[]{Component.literal(String.valueOf(requiredLevel)).withStyle(ChatFormatting.BOLD)}
                        )
                     )
               );
            } else if (!requiredAbility.isEmpty() && !relic.isAbilityUnlocked(stack, requiredAbility)) {
               entries.add(Component.literal(" "));
               entries.add(Component.literal("").append(Component.translatable("tooltip.relics.researching.relic.gem.locked_ability")));
            }

            for (MutableComponent entry : entries) {
               int entryWidth = this.minecraft.font.width(entry) / 2;
               if (entryWidth > renderWidth) {
                  renderWidth = Math.min(entryWidth + 4, maxWidth);
               }

               tooltip.addAll(this.minecraft.font.split(entry, maxWidth * 2));
            }

            int height = tooltip.size() * 5;
            int y = this.getHeight() / 2;
            float partialTicks = this.minecraft.getTimer().getGameTimeDeltaPartialTick(false);
            float lerpedScale = Mth.lerp(partialTicks, this.scaleOld, this.scale);
            poseStack.scale(lerpedScale, lerpedScale, lerpedScale);
            poseStack.translate((this.getX() + this.getWidth() / 2.0F) / lerpedScale, (this.getY() + this.getHeight() / 2.0F) / lerpedScale, 0.0F);
            DescriptionUtils.drawTooltipBackground(guiGraphics, renderWidth, height, -((renderWidth + 19) / 2), y);
            int yOff = 0;
            poseStack.pushPose();
            poseStack.scale(0.5F, 0.5F, 0.5F);
            if (!relic.isLevelingSourceUnlocked(stack, this.source)) {
               title = ScreenUtils.stylizeWithReplacement(
                  title, 1.0, Style.EMPTY.withFont(ScreenUtils.ILLAGER_ALT_FONT).withColor(10354864), this.source.length()
               );
               RandomSource random = this.minecraft.player.getRandom();
               float shakeX = MathUtils.randomFloat(random) * 0.5F;
               float shakeY = MathUtils.randomFloat(random) * 0.5F;
               poseStack.translate(shakeX, shakeY, 0.0F);
            } else {
               title.withStyle(ChatFormatting.BOLD);
            }

            guiGraphics.drawString(this.minecraft.font, title, -(this.minecraft.font.width(title) / 2), (y + yOff + 9) * 2, 6696723, false);
            poseStack.popPose();

            for (FormattedCharSequence entry : tooltip) {
               poseStack.pushPose();
               poseStack.scale(0.5F, 0.5F, 0.5F);
               guiGraphics.drawString(this.minecraft.font, entry, -(this.minecraft.font.width(entry) / 2), (y + yOff + 9) * 2, 6696723, false);
               yOff += 5;
               poseStack.popPose();
            }
         }
      }
   }

   @Override
   public void playDownSound(SoundManager handler) {
      if (!this.isLocked()) {
         super.playDownSound(handler);
      }
   }
}
