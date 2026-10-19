package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.stextras.SkillUnlockEpCost;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "org.crypticdev.stextras.client.screen.skilllock.STSkillLockTooltips", remap = false)
public class SkillLockTooltipLocalizationMixin {
   private static final String KEY_PREFIX = "tensura_tno.skill_lock.";
   private static final String REQUIREMENTS = "Requirements";
   private static final String SOUL_GRADE_COST = "Soul Grade Cost: ";
   private static final String MP_COST = "MP Cost: ";
   private static final String EP_NEEDED = "EP Needed: ";
   private static final String EP_COST = "EP Cost: 1%";

   @Inject(method = "unlockTooltip", at = @At("RETURN"), cancellable = true, require = 0, remap = false)
   private static void localizeUnlockTooltip(ManasSkillInstance selectedAbility, IExistence existence, CallbackInfoReturnable<List<Component>> cir) {
      List<Component> tooltip = (List<Component>)cir.getReturnValue();
      if (tooltip != null && !tooltip.isEmpty()) {
         List<Component> localized = new ArrayList<>(tooltip.size());
         boolean showCost = selectedAbility != null;
         boolean affordable = !showCost || canAfford(existence);
         boolean costAdded = false;

         for (Component component : tooltip) {
            String text = component.getString();
            if (!isOldFixedEpFailure(component)
               && (affordable || !isTranslation(component, "stextras.skill_lock.cannot_unlock.generic"))
               && !text.startsWith("Soul Grade Cost: ")
               && !text.startsWith("MP Cost: ")
               && !text.startsWith("EP Needed: ")
               && !text.isEmpty()) {
               localized.add(localizeComponent(component));
               if ("Requirements".equals(text) && showCost) {
                  localized.add(Component.literal(tr("tooltip.ep_cost", "EP Cost: 1%")).withStyle(affordable ? ChatFormatting.GRAY : ChatFormatting.RED));
                  costAdded = true;
               }
            }
         }

         if (showCost && !costAdded) {
            localized.add(0, Component.literal(tr("tooltip.ep_cost", "EP Cost: 1%")).withStyle(affordable ? ChatFormatting.GRAY : ChatFormatting.RED));
         }

         if (showCost && !affordable) {
            localized.add(Component.translatable("tensura_tno.skill_lock.not_enough_ep_cost").withStyle(ChatFormatting.RED));
         }

         cir.setReturnValue(localized);
      }
   }

   private static boolean isOldFixedEpFailure(Component component) {
      return isTranslation(component, "stextras.skill_lock.not_enough_ep");
   }

   private static boolean isTranslation(Component component, String key) {
      return component.getContents() instanceof TranslatableContents contents && key.equals(contents.getKey());
   }

   private static boolean canAfford(IExistence existence) {
      LocalPlayer player = Minecraft.getInstance().player;
      return player == null || SkillUnlockEpCost.canAfford(player, existence);
   }

   private static Component localizeComponent(Component component) {
      String text = component.getString();
      return (Component)("Requirements".equals(text) ? copyLiteral(component, tr("tooltip.requirements", "Requirements")) : component);
   }

   private static MutableComponent copyLiteral(Component original, String text) {
      MutableComponent replacement = Component.literal(text).setStyle(original.getStyle());

      for (Component sibling : original.getSiblings()) {
         replacement.append(sibling.copy());
      }

      return replacement;
   }

   private static String tr(String suffix, String fallback) {
      Language language = Language.getInstance();
      String key = "tensura_tno.skill_lock." + suffix;
      return language.has(key) ? language.getOrDefault(key) : fallback;
   }
}
