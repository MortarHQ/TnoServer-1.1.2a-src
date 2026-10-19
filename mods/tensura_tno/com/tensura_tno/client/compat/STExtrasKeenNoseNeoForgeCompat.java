package com.tensura_tno.client.compat;

import io.github.manasmods.tensura.ability.SkillClientUtils;
import io.github.manasmods.tensura.ability.SkillClientUtils.XrayType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public final class STExtrasKeenNoseNeoForgeCompat {
   private static final ResourceLocation EARTH_SENSE = ResourceLocation.fromNamespaceAndPath("stextras", "earth_sense");
   private static boolean registered;

   public static synchronized void registerIfNeeded() {
      if (!registered && !hasNativeXrayRegistration()) {
         addOutline(4210752, "minecraft:coal_ore", "minecraft:deepslate_coal_ore");
         addOutline(14277081, "minecraft:iron_ore", "minecraft:deepslate_iron_ore");
         addOutline(16747571, "minecraft:copper_ore", "minecraft:deepslate_copper_ore");
         addOutline(16767296, "minecraft:gold_ore", "minecraft:deepslate_gold_ore", "minecraft:nether_gold_ore");
         addOutline(16728128, "minecraft:redstone_ore", "minecraft:deepslate_redstone_ore");
         addOutline(3404390, "minecraft:emerald_ore", "minecraft:deepslate_emerald_ore");
         addOutline(4223999, "minecraft:lapis_ore", "minecraft:deepslate_lapis_ore", "iceandfire:sapphire_ore");
         addOutline(2552562, "minecraft:diamond_ore", "minecraft:deepslate_diamond_ore");
         addOutline(16777215, "minecraft:nether_quartz_ore", "tensura:silver_ore", "tensura:deepslate_silver_ore");
         addOutline(11760448, "minecraft:ancient_debris");
         addOutline(16737996, "tensura:magic_ore", "tensura:deepslate_magic_ore");
         registered = true;
      } else {
         registered = true;
      }
   }

   private static void addOutline(int rgb, String... blockIds) {
      Set<ResourceLocation> ids = new HashSet<>();
      Arrays.stream(blockIds).<ResourceLocation>map(ResourceLocation::tryParse).filter(id -> id != null).forEach(ids::add);
      Set<ResourceLocation> immutableIds = Set.copyOf(ids);
      float red = color(rgb, 16);
      float green = color(rgb, 8);
      float blue = color(rgb, 0);
      SkillClientUtils.XRAY_TYPES.add(player -> {
         MobEffectInstance effect = earthSense(player);
         if (effect == null) {
            return null;
         }

         int radius = Math.max(1, Math.min(64, (int)Math.ceil(25.0 * (1.0 + Math.max(0, effect.getAmplifier()) * 0.2))));
         return new XrayType(state -> immutableIds.contains(BuiltInRegistries.BLOCK.getKey(state.getBlock())), radius, red, green, blue, 1.0F);
      });
   }

   private static MobEffectInstance earthSense(Player player) {
      MobEffect effect = (MobEffect)BuiltInRegistries.MOB_EFFECT.get(EARTH_SENSE);
      return effect == null ? null : player.getEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect));
   }

   private static boolean hasNativeXrayRegistration() {
      try {
         Class<?> config = Class.forName(
            "org.crypticdev.stextras.config.ability.skill.STExtrasExtraSkillConfig$KeenNose", false, STExtrasKeenNoseNeoForgeCompat.class.getClassLoader()
         );
         config.getField("block_outlines");
         return true;
      } catch (ReflectiveOperationException | LinkageError ignored) {
         return false;
      }
   }

   private static float color(int rgb, int shift) {
      return (rgb >> shift & 0xFF) / 255.0F;
   }

   private STExtrasKeenNoseNeoForgeCompat() {
   }
}
