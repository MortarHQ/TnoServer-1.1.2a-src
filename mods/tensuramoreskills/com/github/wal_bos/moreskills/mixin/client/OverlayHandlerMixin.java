package com.github.wal_bos.moreskills.mixin.client;

import com.github.wal_bos.moreskills.client.BloodfiendClientData;
import com.github.wal_bos.moreskills.registry.skill.ShadeSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.client.screen.widgets.OverlayElement;
import io.github.manasmods.tensura.handler.client.OverlayHandler;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = OverlayHandler.class, remap = false)
public abstract class OverlayHandlerMixin {
   @Shadow
   private static LocalPlayer player;
   @Shadow
   private static Font font;
   @Shadow
   private static GuiGraphics graphics;
   @Shadow
   private static float foodScale;
   @Shadow
   private static float[] foodPositions;
   @Shadow
   public static OverlayElement FOOD_DECORATION;
   @Unique
   private static final ResourceLocation MORESKILLS$PHAINON_MAIN_HUD = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "textures/player_hud/phainon_main_hud.png"
   );
   @Unique
   private static final ResourceLocation MORESKILLS$PHAINON_MAIN_HUD_BARS = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "textures/player_hud/phainon_main_hud_bars.png"
   );
   @Unique
   private static final ResourceLocation MORESKILLS$PHAINON_MANA_BAR = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "textures/player_hud/phainon_mana_bar.png"
   );
   @Unique
   private static final ResourceLocation MORESKILLS$PHAINON_AURA_BAR = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "textures/player_hud/phainon_aura_bar.png"
   );
   @Unique
   private static final ResourceLocation MORESKILLS$PHAINON_ABILITY_OVERLAY = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "textures/player_hud/abilities/phainon_ability_overlay.png"
   );
   @Unique
   private static final ResourceLocation MORESKILLS$PHAINON_ABILITY_OVERLAY_TEXT_BARS = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "textures/player_hud/abilities/phainon_ability_overlay_text_bars.png"
   );
   @Unique
   private static final ResourceLocation MORESKILLS$PHAINON_MASTERY_BAR = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "textures/player_hud/abilities/phainon_mastery_bar.png"
   );
   @Unique
   private static final ResourceLocation MORESKILLS$PHAINON_LEARNING_BAR = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "textures/player_hud/abilities/phainon_mastery_bar.png"
   );
   @Unique
   private static final ResourceLocation MORESKILLS$BLOOD_0 = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "textures/player_hud/decorations/blood_0.png"
   );
   @Unique
   private static final ResourceLocation MORESKILLS$BLOOD_25 = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "textures/player_hud/decorations/blood_25.png"
   );
   @Unique
   private static final ResourceLocation MORESKILLS$BLOOD_50 = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "textures/player_hud/decorations/blood_50.png"
   );
   @Unique
   private static final ResourceLocation MORESKILLS$BLOOD_75 = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "textures/player_hud/decorations/blood_75.png"
   );
   @Unique
   private static final ResourceLocation MORESKILLS$BLOOD_100 = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "textures/player_hud/decorations/blood_100.png"
   );

   @Shadow
   public static boolean isLeftSide(int element) {
      throw new AssertionError();
   }

   @Shadow
   private static void renderWithFlipping(ResourceLocation texture, float x, float y, int width, int height, boolean isLeftSide) {
      throw new AssertionError();
   }

   @ModifyArg(
      method = "renderStatus",
      at = @At(
         value = "INVOKE",
         target = "Lio/github/manasmods/tensura/handler/client/OverlayHandler;renderWithFlipping(Lnet/minecraft/resources/ResourceLocation;FFIIZ)V"
      ),
      index = 0
   )
   private static ResourceLocation moreskills$replacePhainonMainHud(ResourceLocation texture) {
      if (moreskills$hasPhainon() && !player.isSpectator()) {
         String path = texture.getPath();
         return !path.equals("textures/player_hud/main_hud.png")
               && !path.equals("textures/player_hud/hardcore_hud.png")
               && !path.equals("textures/player_hud/creative_hud.png")
            ? texture
            : MORESKILLS$PHAINON_MAIN_HUD;
      } else {
         return texture;
      }
   }

   @ModifyArg(
      method = "renderStatusBars",
      at = @At(
         value = "INVOKE",
         target = "Lio/github/manasmods/tensura/handler/client/OverlayHandler;renderWithFlipping(Lnet/minecraft/resources/ResourceLocation;FFIIZ)V"
      ),
      index = 0
   )
   private static ResourceLocation moreskills$replacePhainonStatusBars(ResourceLocation texture) {
      if (moreskills$hasPhainon() && !player.isSpectator()) {
         String path = texture.getPath();
         if (path.equals("textures/player_hud/main_hud_bars.png")
            || path.equals("textures/player_hud/hardcore_hud_bars.png")
            || path.equals("textures/player_hud/creative_hud_bars.png")) {
            return MORESKILLS$PHAINON_MAIN_HUD_BARS;
         } else if (path.equals("textures/player_hud/mana_bar.png")) {
            return MORESKILLS$PHAINON_MANA_BAR;
         } else {
            return path.equals("textures/player_hud/aura_bar.png") ? MORESKILLS$PHAINON_AURA_BAR : texture;
         }
      } else {
         return texture;
      }
   }

   @ModifyArg(
      method = "renderAbilities",
      at = @At(
         value = "INVOKE",
         target = "Lio/github/manasmods/tensura/handler/client/OverlayHandler;renderWithFlipping(Lnet/minecraft/resources/ResourceLocation;FFIIZ)V"
      ),
      index = 0
   )
   private static ResourceLocation moreskills$replacePhainonAbilityHud(ResourceLocation texture) {
      if (moreskills$hasPhainon() && !player.isSpectator()) {
         String path = texture.getPath();
         if (path.equals("textures/player_hud/abilities/ability_overlay.png")) {
            return MORESKILLS$PHAINON_ABILITY_OVERLAY;
         } else if (path.equals("textures/player_hud/abilities/ability_overlay_text_bars.png")) {
            return MORESKILLS$PHAINON_ABILITY_OVERLAY_TEXT_BARS;
         } else if (path.equals("textures/player_hud/abilities/mastery_bar.png")) {
            return MORESKILLS$PHAINON_MASTERY_BAR;
         } else {
            return path.equals("textures/player_hud/abilities/learning_bar.png") ? MORESKILLS$PHAINON_LEARNING_BAR : texture;
         }
      } else {
         return texture;
      }
   }

   @Inject(method = "renderFoodDecoration", at = @At("HEAD"), cancellable = true)
   private static void moreskills$renderBloodDecoration(CallbackInfo ci) {
      if (player != null && !player.isSpectator() && BloodfiendClientData.isBloodfiend()) {
         ci.cancel();
         FOOD_DECORATION.setScale(foodScale);
         FOOD_DECORATION.setWidth(9.0F * foodScale);
         FOOD_DECORATION.setHeight(9.0F * foodScale);
         FOOD_DECORATION.setPosX(foodPositions[0]);
         FOOD_DECORATION.setPosY(foodPositions[1]);
         boolean leftSide = isLeftSide(7);
         int blood = Math.max(0, BloodfiendClientData.getBlood());
         int maxBlood = Math.max(0, BloodfiendClientData.getMaxBlood());
         int generation = Math.max(0, BloodfiendClientData.getGeneration());
         ResourceLocation icon = moreskills$getBloodIcon(blood, maxBlood);
         renderWithFlipping(icon, 0.0F, 0.0F, 9, 9, leftSide);
         String bloodText = maxBlood > 0 ? String.valueOf(blood) : "0";
         int bloodWidth = font.width(bloodText);
         int bloodX = leftSide ? -3 - bloodWidth : 11;
         int bloodColor = moreskills$getBloodTextColor(blood, maxBlood);
         graphics.drawString(font, bloodText, bloodX, 1, bloodColor, true);
         if (generation > 0) {
            String generationText = String.valueOf(generation);
            int generationColor = moreskills$getGenerationTextColor(generation);
            float scale = 0.32F;
            float generationX = leftSide ? 7.0F : 0.5F;
            float generationY = 6.0F;
            graphics.pose().pushPose();
            graphics.pose().scale(scale, scale, 1.0F);
            graphics.drawString(font, generationText, (int)(generationX / scale), (int)(generationY / scale), generationColor, true);
            graphics.pose().popPose();
         }
      }
   }

   @Unique
   private static int moreskills$getGenerationTextColor(int generation) {
      if (generation <= 1) {
         return 16733525;
      } else if (generation <= 3) {
         return 14496563;
      } else {
         return generation <= 6 ? 11149858 : 7804181;
      }
   }

   @Unique
   private static ResourceLocation moreskills$getBloodIcon(int blood, int maxBlood) {
      if (maxBlood > 0 && blood > 0) {
         float percent = (float)blood / maxBlood;
         if (percent >= 0.875F) {
            return MORESKILLS$BLOOD_100;
         } else if (percent >= 0.625F) {
            return MORESKILLS$BLOOD_75;
         } else if (percent >= 0.375F) {
            return MORESKILLS$BLOOD_50;
         } else {
            return percent >= 0.125F ? MORESKILLS$BLOOD_25 : MORESKILLS$BLOOD_0;
         }
      } else {
         return MORESKILLS$BLOOD_0;
      }
   }

   @Unique
   private static int moreskills$getBloodTextColor(int blood, int maxBlood) {
      if (maxBlood > 0 && blood > 0) {
         float percent = (float)blood / maxBlood;
         if (percent >= 0.75F) {
            return 16730714;
         } else {
            return percent >= 0.35F ? 14886459 : 11013922;
         }
      } else {
         return 7019034;
      }
   }

   @Unique
   private static boolean moreskills$hasPhainon() {
      return player != null && SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)ShadeSkills.PHAINON.get()).isPresent();
   }
}
