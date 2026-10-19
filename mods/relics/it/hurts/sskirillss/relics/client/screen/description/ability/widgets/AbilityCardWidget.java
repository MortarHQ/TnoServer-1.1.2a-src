package it.hurts.sskirillss.relics.client.screen.description.ability.widgets;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.hurts.sskirillss.relics.client.screen.base.IHoverableWidget;
import it.hurts.sskirillss.relics.client.screen.base.ITickingWidget;
import it.hurts.sskirillss.relics.client.screen.description.ability.AbilityDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.base.AbstractDescriptionWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.client.screen.description.relic.particles.ChainParticleData;
import it.hurts.sskirillss.relics.client.screen.description.relic.particles.ExperienceParticleData;
import it.hurts.sskirillss.relics.client.screen.description.relic.particles.SparkParticleData;
import it.hurts.sskirillss.relics.client.screen.description.research.AbilityResearchScreen;
import it.hurts.sskirillss.relics.client.screen.utils.ParticleStorage;
import it.hurts.sskirillss.relics.client.screen.utils.ScreenUtils;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.network.packets.lock.PacketAbilityUnlock;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.RenderUtils;
import it.hurts.sskirillss.relics.utils.data.AnimationData;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;

public class AbilityCardWidget extends AbstractDescriptionWidget implements IHoverableWidget, ITickingWidget {
   private final AbilityDescriptionScreen screen;
   private final String ability;
   private float scale = 1.0F;
   private float scaleOld = 1.0F;
   private int shakeDelta = 0;
   private int colorDelta = 0;

   public AbilityCardWidget(int x, int y, AbilityDescriptionScreen screen, String ability) {
      super(x, y, 32, 47);
      this.screen = screen;
      this.ability = ability;
   }

   @Override
   public void onPress() {
      if (this.screen.getStack().getItem() instanceof IRelicItem relic) {
         ItemStack var13 = this.screen.getStack();
         boolean isEnoughLevel = relic.isEnoughLevel(var13, this.ability);
         boolean isLockUnlocked = relic.isLockUnlocked(var13, this.ability);
         boolean isAbilityResearched = relic.isAbilityResearched(var13, this.ability);
         SoundManager soundManager = this.minecraft.getSoundManager();
         if (isEnoughLevel) {
            if (isLockUnlocked) {
               if (isAbilityResearched) {
                  if (!this.screen.getSelectedAbility().equals(this.ability)) {
                     this.screen.setSelectedAbility(this.ability);
                     this.screen.rebuildWidgets();

                     for (Renderable entry : this.screen.renderables) {
                        if (entry instanceof AbilityCardWidget card && card.ability.equals(this.ability)) {
                           card.scale = this.scale;
                           card.scaleOld = this.scaleOld;
                           card.shakeDelta = this.shakeDelta;
                           card.colorDelta = this.colorDelta;
                        }
                     }
                  }
               } else {
                  this.minecraft
                     .setScreen(new AbilityResearchScreen(this.minecraft.player, this.screen.container, this.screen.slot, this.screen, this.ability));
               }
            } else {
               int unlocks = relic.getLockUnlocks(var13, this.ability) + 1;
               RandomSource random = this.minecraft.player.getRandom();

               for (int i = 0; i < unlocks * 50; i++) {
                  Vec2 center = new Vec2(this.width / 2.0F, this.height / 2.0F);
                  Vec2 margin = new Vec2(center.x + MathUtils.randomFloat(random) * 7.0F, center.y + MathUtils.randomFloat(random) * 8.5F);
                  Vec2 motion = new Vec2(margin.x - center.x, margin.y - center.y).normalized().scale(5.0F + unlocks);
                  ParticleStorage.addParticle(
                     this.screen,
                     new SparkParticleData(
                           new Color(150 + random.nextInt(100), 100 + random.nextInt(50), 0),
                           this.getX() + margin.x,
                           this.getY() + margin.y,
                           1.0F + random.nextFloat() * 0.5F,
                           20 + random.nextInt(100)
                        )
                        .setDeltaX(random.nextFloat() * motion.x)
                        .setDeltaY(random.nextFloat() * motion.y)
                  );
               }

               NetworkHandler.sendToServer(new PacketAbilityUnlock(this.screen.container, this.screen.slot, this.ability, unlocks));
               this.shakeDelta = Math.min(20, this.shakeDelta + 5 + random.nextInt(5));
               this.scale += 0.05F;
               soundManager.play(SimpleSoundInstance.forUI(SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, 1.0F));
               if (unlocks >= relic.getMaxLockUnlocks()) {
                  for (int i = 0; i < 25; i++) {
                     Vec2 center = new Vec2(this.width / 2.0F, this.height / 2.0F);
                     Vec2 margin = new Vec2(center.x + MathUtils.randomFloat(random) * 7.0F, center.y + MathUtils.randomFloat(random) * 8.5F);
                     Vec2 motion = new Vec2(margin.x - center.x, margin.y - center.y).normalized().scale(7.5F);
                     ParticleStorage.addParticle(
                        this.screen,
                        new ChainParticleData(
                              new Color(255, 255, 255),
                              this.getX() + margin.x,
                              this.getY() + margin.y,
                              1.0F + random.nextFloat() * 0.5F,
                              50 + random.nextInt(20)
                           )
                           .setDeltaX(random.nextFloat() * motion.x)
                           .setDeltaY(random.nextFloat() * motion.y)
                     );
                  }

                  soundManager.play(SimpleSoundInstance.forUI(SoundEvents.WITHER_BREAK_BLOCK, 1.0F));
                  soundManager.play(SimpleSoundInstance.forUI(SoundEvents.GENERIC_EXPLODE, 1.0F));
               }
            }
         } else {
            this.shakeDelta = Math.min(20, this.shakeDelta + 10);
            this.colorDelta = Math.min(20, this.colorDelta + 10);
            soundManager.play(SimpleSoundInstance.forUI(SoundEvents.CHAIN_BREAK, 1.0F));
         }
      }
   }

   public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && this.screen.stack.getItem() instanceof IRelicItem relic) {
         ItemStack var26 = this.screen.getStack();
         TextureManager manager = this.minecraft.getTextureManager();
         PoseStack poseStack = guiGraphics.pose();
         int unlocks = relic.getLockUnlocks(var26, this.ability);
         boolean isEnoughLevel = relic.isEnoughLevel(var26, this.ability);
         boolean isLockUnlocked = relic.isLockUnlocked(var26, this.ability);
         boolean isAbilityResearched = relic.isAbilityResearched(var26, this.ability);
         boolean canUse = isEnoughLevel && isLockUnlocked && isAbilityResearched;
         boolean canUpgrade = relic.mayPlayerUpgrade(this.minecraft.player, var26, this.ability);
         boolean canResearch = relic.mayResearch(var26, this.ability);
         boolean canBeUpgraded = relic.canBeUpgraded(var26, this.ability);
         if (!canUpgrade && !canResearch) {
            boolean hasAction = false;
         } else {
            boolean hasAction = true;
         }

         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.enableBlend();
         poseStack.pushPose();
         float partialTicks = this.minecraft.getTimer().getGameTimeDeltaPartialTick(false);
         float lerpedScale = Mth.lerp(partialTicks, this.scaleOld, this.scale);
         poseStack.scale(lerpedScale, lerpedScale, lerpedScale);
         poseStack.translate((this.getX() + this.width / 2.0F) / lerpedScale, (this.getY() + this.height / 2.0F) / lerpedScale, 0.0F);
         float color = (float)((canUpgrade ? 0.75F : 1.05F) + Math.sin((player.tickCount + this.ability.length() * 10) * 0.2F) * 0.1F);
         if (isLockUnlocked) {
            GUIRenderer.begin(DescriptionTextures.getAbilityCardTexture(var26, this.ability), poseStack)
               .color(color, color, color, 1.0F)
               .texSize(22, 31)
               .scale(1.01F)
               .end();
         }

         if (!canUse) {
            GUIRenderer.begin(isLockUnlocked ? DescriptionTextures.SMALL_CARD_RESEARCH_BACKGROUND : DescriptionTextures.SMALL_CARD_LOCK_BACKGROUND, poseStack)
               .scale(1.01F)
               .end();
         }

         GUIRenderer.begin(
               canBeUpgraded
                  ? (canUse ? DescriptionTextures.SMALL_CARD_FRAME_UNLOCKED_ACTIVE : DescriptionTextures.SMALL_CARD_FRAME_UNLOCKED_INACTIVE)
                  : (canUse ? DescriptionTextures.SMALL_CARD_FRAME_LOCKED_ACTIVE : DescriptionTextures.SMALL_CARD_FRAME_LOCKED_INACTIVE),
               poseStack
            )
            .end();
         if (this.isHovered()) {
            GUIRenderer.begin(DescriptionTextures.SMALL_CARD_FRAME_OUTLINE, poseStack).pos(0.0F, 0.5F).end();
         }

         if (isLockUnlocked) {
            if (!isAbilityResearched) {
               float time = this.minecraft.player.tickCount + this.ability.length() * 10.0F + partialTick;
               GUIRenderer.begin(DescriptionTextures.RESEARCH, poseStack)
                  .pos((float)Math.sin(time * 0.25F), (float)Math.cos(time * 0.25F) + 0.5F)
                  .patternSize(16, 16)
                  .animation(
                     AnimationData.builder()
                        .frame(0, 2)
                        .frame(1, 2)
                        .frame(2, 2)
                        .frame(3, 2)
                        .frame(4, 2)
                        .frame(5, 2)
                        .frame(6, 2)
                        .frame(7, 2)
                        .frame(8, 2)
                        .frame(9, 2)
                        .frame(10, 2)
                        .frame(11, 40)
                  )
                  .end();
            }
         } else {
            GUIRenderer.begin(
                  isEnoughLevel
                     ? ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/relic/chains_active_" + unlocks + ".png")
                     : DescriptionTextures.CHAINS_INACTIVE,
                  poseStack
               )
               .pos(0.0F, -1.0F)
               .end();
            MutableComponent level = Component.literal(String.valueOf(relic.getAbilityData(this.ability).getRequiredLevel())).withStyle(ChatFormatting.BOLD);
            color = Math.min(0.75F, this.colorDelta * 0.04F);
            RenderSystem.setShaderColor(1.0F, 1.0F - color, 1.0F - color, 1.0F);
            poseStack.pushPose();
            if (this.shakeDelta > 0) {
               poseStack.mulPose(Axis.ZP.rotation((float)Math.sin((player.tickCount + partialTick) * 0.75F) * (this.shakeDelta / 30.0F * 0.75F)));
            }

            GUIRenderer.begin(
                  isEnoughLevel
                     ? ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/relic/icons/lock_active_" + unlocks + ".png")
                     : DescriptionTextures.LOCK_INACTIVE,
                  poseStack
               )
               .end();
            poseStack.scale(0.5F, 0.5F, 0.5F);
            guiGraphics.drawString(
               this.minecraft.font,
               level,
               (-(this.width / 2) + 16) * 2 - this.minecraft.font.width(level) / 2,
               (-(this.height / 2) + 24) * 2,
               isEnoughLevel ? 16769656 : 12037849,
               true
            );
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
         }

         if (canUse && canUpgrade) {
            RenderSystem.setShaderTexture(0, DescriptionTextures.UPGRADE);
            manager.bindForSetup(DescriptionTextures.UPGRADE);
            RenderSystem.enableBlend();
            RenderUtils.renderAnimatedTextureFromCenter(
               poseStack,
               0.0F,
               -1.0F,
               20.0F,
               400.0F,
               20.0F,
               20.0F,
               0.9F + (float)(Math.sin((player.tickCount + partialTick) * 0.25F) * 0.025F),
               AnimationData.builder()
                  .frame(0, 2)
                  .frame(1, 2)
                  .frame(2, 2)
                  .frame(3, 2)
                  .frame(4, 2)
                  .frame(5, 2)
                  .frame(6, 2)
                  .frame(7, 2)
                  .frame(8, 2)
                  .frame(9, 2)
                  .frame(10, 2)
                  .frame(11, 2)
                  .frame(12, 2)
                  .frame(13, 2)
                  .frame(14, 2)
                  .frame(15, 2)
                  .frame(16, 2)
                  .frame(17, 2)
                  .frame(18, 2)
                  .frame(19, 2)
            );
            RenderSystem.disableBlend();
         }

         if (canBeUpgraded && canUse) {
            int xOff = 0;

            for (int i = 0; i < 5; i++) {
               guiGraphics.blit(DescriptionTextures.SMALL_STAR_HOLE, -(this.width / 2) + xOff + 4, -(this.height / 2) + 40, 0.0F, 0.0F, 4, 4, 4, 4);
               xOff += 5;
            }

            int var30 = 0;
            int quality = relic.getAbilityQuality(this.screen.stack, this.ability);
            boolean isAliquot = quality % 2 == 1;

            for (int i = 0; i < Math.floor(quality / 2.0); i++) {
               guiGraphics.blit(DescriptionTextures.SMALL_STAR_ACTIVE, -(this.width / 2) + var30 + 4, -(this.height / 2) + 40, 0.0F, 0.0F, 4, 4, 4, 4);
               var30 += 5;
            }

            if (isAliquot) {
               guiGraphics.blit(DescriptionTextures.SMALL_STAR_ACTIVE, -(this.width / 2) + var30 + 4, -(this.height / 2) + 40, 0.0F, 0.0F, 2, 4, 4, 4);
            }
         }

         if (canBeUpgraded) {
            MutableComponent title = Component.literal(canUse ? String.valueOf(relic.getAbilityLevel(this.screen.stack, this.ability)) : "?")
               .withStyle(ChatFormatting.BOLD);
            float textScale = 0.5F;
            poseStack.scale(textScale, textScale, textScale);
            guiGraphics.drawString(
               this.minecraft.font,
               title,
               -((this.width + 1) / 2) - this.minecraft.font.width(title) / 2 + 16,
               -(this.height / 2) - 19,
               canUse ? 16769656 : 12037849,
               true
            );
         }

         RenderSystem.disableBlend();
         poseStack.popPose();
      }
   }

   @Override
   public void onTick() {
      if (this.screen.stack.getItem() instanceof IRelicItem relic) {
         float var7 = 1.15F;
         float minScale = 1.0F;
         RandomSource random = this.minecraft.player.getRandom();
         boolean canUpgrade = relic.mayPlayerUpgrade(this.minecraft.player, this.screen.stack, this.ability);
         boolean canResearch = relic.mayResearch(this.screen.stack, this.ability);
         if ((canUpgrade || canResearch) && this.minecraft.player.tickCount % 7 == 0) {
            ParticleStorage.addParticle(
               this.screen,
               new ExperienceParticleData(
                  new Color(200 + random.nextInt(50), 150 + random.nextInt(100), 0),
                  this.getX() + 5 + random.nextInt(18),
                  this.getY() + 18,
                  1.0F + random.nextFloat() * 0.5F,
                  100 + random.nextInt(50)
               )
            );
         }

         this.scaleOld = this.scale;
         if (this.scale > var7) {
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

            if (this.scale < var7) {
               this.scale = Math.min(var7, this.scale + 0.04F);
            }
         } else if (this.scale > minScale) {
            this.scale = Math.max(minScale, this.scale - 0.03F);
         }

         if (this.shakeDelta > 0) {
            this.shakeDelta--;
         }

         if (this.colorDelta > 0) {
            this.colorDelta--;
         }
      }
   }

   @Override
   public boolean isLocked() {
      return this.screen.getSelectedAbility().equals(this.ability);
   }

   @Override
   public void onHovered(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      ItemStack stack = this.screen.stack;
      if (stack.getItem() instanceof IRelicItem relic) {
         AbilityData data = relic.getAbilityData(this.ability);
         if (data != null) {
            PoseStack poseStack = guiGraphics.pose();
            List<FormattedCharSequence> tooltip = Lists.newArrayList();
            MutableComponent title = Component.translatableWithFallback(
               "tooltip.relics." + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + ".ability." + this.ability, this.ability
            );
            int maxWidth = 110;
            int renderWidth = Math.min(this.minecraft.font.width(title.withStyle(ChatFormatting.BOLD)) / 2 + 4, maxWidth);
            List<MutableComponent> entries = new ArrayList<>();
            entries.add(Component.literal(" "));
            int level = relic.getRelicLevel(this.screen.stack);
            int requiredLevel = data.getRequiredLevel();
            if (level < requiredLevel) {
               entries.add(Component.literal(" "));
               entries.add(
                  Component.literal("")
                     .append(
                        Component.translatable(
                           "tooltip.relics.researching.relic.card.low_level",
                           new Object[]{Component.literal(String.valueOf(requiredLevel)).withStyle(ChatFormatting.BOLD)}
                        )
                     )
               );
            } else if (!relic.isLockUnlocked(this.screen.stack, this.ability)) {
               entries.add(Component.literal(" "));
               entries.add(
                  Component.literal("")
                     .append(
                        Component.translatable(
                           "tooltip.relics.researching.relic.card.ready_to_unlock",
                           new Object[]{
                              Component.literal(String.valueOf(relic.getMaxLockUnlocks() - relic.getLockUnlocks(this.screen.stack, this.ability)))
                                 .withStyle(ChatFormatting.BOLD)
                           }
                        )
                     )
               );
            } else if (!relic.isAbilityResearched(this.screen.stack, this.ability)) {
               entries.add(Component.literal(" "));
               entries.add(Component.literal("").append(Component.translatable("tooltip.relics.researching.relic.card.unresearched")));
            } else if (relic.mayPlayerUpgrade(this.minecraft.player, this.screen.stack, this.ability)) {
               entries.add(Component.literal(" "));
               entries.add(Component.literal("").append(Component.translatable("tooltip.relics.researching.relic.card.ready_to_upgrade")));
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
            if (!relic.isAbilityUnlocked(stack, this.ability)) {
               title = ScreenUtils.stylizeWithReplacement(
                  title, 1.0, Style.EMPTY.withFont(ScreenUtils.ILLAGER_ALT_FONT).withColor(10354864), this.ability.length()
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
      if (!this.isLocked() && this.screen.getStack().getItem() instanceof IRelicItem relic && relic.isAbilityUnlocked(this.screen.stack, this.ability)) {
         super.playDownSound(handler);
      }
   }
}
