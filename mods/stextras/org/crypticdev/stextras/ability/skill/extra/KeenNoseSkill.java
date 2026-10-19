package org.crypticdev.stextras.ability.skill.extra;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillClientUtils;
import io.github.manasmods.tensura.ability.SkillClientUtils.XrayType;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.EnergyHelper.DrainType;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.crypticdev.stextras.Stextras;
import org.crypticdev.stextras.config.ability.skill.STExtrasExtraSkillConfig;
import org.crypticdev.stextras.registary.effects.STExtrasMobEffects;
import org.jetbrains.annotations.Nullable;

public class KeenNoseSkill extends Skill {
   public static final STExtrasExtraSkillConfig.KeenNose CONFIG = ((STExtrasExtraSkillConfig)ConfigRegistry.getConfig(STExtrasExtraSkillConfig.class)).KeenNose;
   private static final int MAX_CONFIGURED_XRAY_TYPES = 256;
   private static final Map<String, Optional<KeenNoseSkill.KeenNoseXrayOutline>> XRAY_OUTLINE_CACHE = new ConcurrentHashMap<>();
   private static boolean xrayTypesRegistered;

   public KeenNoseSkill() {
      super(SkillType.EXTRA);
      registerKeenNoseXrayTypes();
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("stextras", "textures/skill/extra/keen_nose_skill.png");
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(entity, instance, 0)) {
         this.HandleDeactivate(instance, entity);
         return false;
      }

      boolean mastered = instance.isMastered(entity);
      int duration = mastered ? CONFIG.hold_duration_mastered : CONFIG.hold_duration;
      int amplifier = mastered ? 1 : 0;
      if (heldTicks >= duration * 20) {
         entity.sendSystemMessage(Component.translatable("stextras.skill.keen_nose.duration_exceeded"));
         this.HandleDeactivate(instance, entity);
         return false;
      }

      entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(STExtrasMobEffects.EARTHEN_SENSE), duration, amplifier, false, false, false));
      if (heldTicks > 0 && heldTicks % 60 == 0) {
         this.addMasteryPoint(instance, entity);
         EnergyHelper.drainEnergy(entity, null, CONFIG.magicule_cost, false, DrainType.MAGICULE, GainType.NONE);
      }

      return true;
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      boolean mastered = instance.isMastered(entity);
      int duration = mastered ? CONFIG.hold_duration_mastered : CONFIG.hold_duration;
      if (heldTicks < duration * 20) {
         this.HandleDeactivate(instance, entity);
      }
   }

   private void HandleDeactivate(ManasSkillInstance instance, LivingEntity entity) {
      entity.removeEffect(STExtrasMobEffects.EARTHEN_SENSE);
      if (instance.isMastered(entity)) {
         instance.setCoolDown(CONFIG.cooldownMastered, 0);
      } else {
         instance.setCoolDown(CONFIG.cooldown, 0);
      }
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return false;
   }

   private static void registerKeenNoseXrayTypes() {
      if (!xrayTypesRegistered) {
         xrayTypesRegistered = true;

         for (int i = 0; i < 256; i++) {
            int configIndex = i;
            SkillClientUtils.XRAY_TYPES.add(player -> createXrayType(player, configIndex));
         }
      }
   }

   @Nullable
   private static XrayType createXrayType(Player player, int configIndex) {
      MobEffectInstance effect = player.getEffect(TensuraMobEffects.getReference(STExtrasMobEffects.EARTHEN_SENSE));
      if (effect == null) {
         return null;
      }

      List<String> entries = CONFIG.block_outlines;
      if (entries != null && configIndex < entries.size()) {
         String raw = entries.get(configIndex);
         if (raw != null && !raw.isBlank()) {
            Optional<KeenNoseSkill.KeenNoseXrayOutline> outline = XRAY_OUTLINE_CACHE.computeIfAbsent(raw, KeenNoseSkill::parseBlockOutline);
            if (outline.isEmpty()) {
               return null;
            }

            KeenNoseSkill.KeenNoseXrayOutline value = outline.get();
            return new XrayType(value::matches, xrayRadius(effect), value.red, value.green, value.blue, value.alpha);
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static Optional<KeenNoseSkill.KeenNoseXrayOutline> parseBlockOutline(String raw) {
      String[] parts = raw.split("\\|");
      if (parts.length < 2) {
         Stextras.LOGGER.warn("Skipping Keen Nose block outline '{}': expected 'block_id[,block_id...]|#RRGGBB'.", raw);
         return Optional.empty();
      }

      int color;
      try {
         color = parseRgbColor(parts[1]);
      } catch (IllegalArgumentException e) {
         Stextras.LOGGER.warn("Skipping Keen Nose block outline '{}': {}.", raw, e.getMessage());
         return Optional.empty();
      }

      float alpha = 1.0F;
      if (parts.length >= 3) {
         try {
            alpha = clamp(Float.parseFloat(parts[2].trim()), 0.0F, 1.0F);
         } catch (NumberFormatException e) {
            Stextras.LOGGER.warn("Skipping Keen Nose block outline '{}': alpha must be a decimal between 0 and 1.", raw);
            return Optional.empty();
         }
      }

      Set<Block> blocks = new HashSet<>();

      for (String rawId : parts[0].split(",")) {
         ResourceLocation id = ResourceLocation.tryParse(rawId.trim());
         if (id == null) {
            Stextras.LOGGER.warn("Skipping invalid Keen Nose block id '{}' in '{}'.", rawId.trim(), raw);
         } else {
            Block block = (Block)BuiltInRegistries.BLOCK.get(id);
            if (block != Blocks.AIR) {
               blocks.add(block);
            }
         }
      }

      return blocks.isEmpty()
         ? Optional.empty()
         : Optional.of(
            new KeenNoseSkill.KeenNoseXrayOutline(Set.copyOf(blocks), colorComponent(color, 16), colorComponent(color, 8), colorComponent(color, 0), alpha)
         );
   }

   private static int parseRgbColor(String raw) {
      String color = raw.trim();
      if (color.startsWith("#")) {
         color = color.substring(1);
      } else if (color.startsWith("0x") || color.startsWith("0X")) {
         color = color.substring(2);
      }

      if (color.length() != 6) {
         throw new IllegalArgumentException("color must be #RRGGBB");
      }

      try {
         return Integer.parseInt(color, 16);
      } catch (NumberFormatException e) {
         throw new IllegalArgumentException("color must be #RRGGBB", e);
      }
   }

   private static int xrayRadius(MobEffectInstance effect) {
      double multiplier = Math.max(0.0, CONFIG.xray_radius_amplifier_multiplier);
      double amplifiedRadius = Math.max(1, CONFIG.xray_radius) * (1.0 + Math.max(0, effect.getAmplifier()) * multiplier);
      return Math.max(1, Math.min(64, (int)Math.ceil(amplifiedRadius)));
   }

   private static float colorComponent(int color, int shift) {
      return (color >> shift & 0xFF) / 255.0F;
   }

   private static float clamp(float value, float min, float max) {
      return Math.max(min, Math.min(max, value));
   }

   private record KeenNoseXrayOutline(Set<Block> blocks, float red, float green, float blue, float alpha) {
      private boolean matches(BlockState state) {
         return this.blocks.contains(state.getBlock());
      }
   }
}
