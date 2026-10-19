package org.crypticdev.stextras.config.ability.skill;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import io.github.manasmods.manascore.config.api.SyncToClient;
import java.util.List;

@SyncToClient
public class STExtrasExtraSkillConfig extends ManasConfig {
   public STExtrasExtraSkillConfig.KeenNose KeenNose = new STExtrasExtraSkillConfig.KeenNose();

   public String getFileName() {
      return "stextras/ability/skill/extra_config";
   }

   public static class KeenNose extends ManasSubConfig {
      @Comment("The duration that you can hold the skill in seconds.")
      public int hold_duration = 10;
      @Comment("The duration that you can hold the skill in seconds if mastered.")
      public int hold_duration_mastered = 15;
      @Comment("The cooldown in seconds.")
      public int cooldown = 60;
      @Comment("The cooldown in seconds when the skill is mastered.")
      public int cooldownMastered = 30;
      @Comment("The magicule cost per second.")
      public double magicule_cost = 15.0;
      @Comment("The block xray radius used by Keen Nose.")
      public int xray_radius = 25;
      @Comment("How much each Keen Nose effect amplifier increases the block xray radius. Mastered Keen Nose applies amplifier 1.")
      public double xray_radius_amplifier_multiplier = 0.2;
      @Comment(
         "Blocks Keen Nose outlines. Format: 'block_id[,block_id...]|#RRGGBB' or 'block_id[,block_id...]|#RRGGBB|alpha'. The first 256 entries are used."
      )
      public List<String> block_outlines = List.of(
         "minecraft:coal_ore,minecraft:deepslate_coal_ore|#404040",
         "minecraft:iron_ore,minecraft:deepslate_iron_ore|#D9D9D9",
         "minecraft:copper_ore,minecraft:deepslate_copper_ore|#FF8C33",
         "minecraft:gold_ore,minecraft:deepslate_gold_ore,minecraft:nether_gold_ore|#FFD940",
         "minecraft:redstone_ore,minecraft:deepslate_redstone_ore|#FF4040",
         "minecraft:emerald_ore,minecraft:deepslate_emerald_ore|#33F266",
         "minecraft:lapis_ore,minecraft:deepslate_lapis_ore,iceandfire:sapphire_ore|#4073FF",
         "minecraft:diamond_ore,minecraft:deepslate_diamond_ore|#26F2F2",
         "minecraft:nether_quartz_ore,tensura:silver_ore,tensura:deepslate_silver_ore|#FFFFFF",
         "minecraft:ancient_debris|#B37340",
         "tensura:magic_ore,tensura:deepslate_magic_ore|#FF66CC"
      );
   }
}
