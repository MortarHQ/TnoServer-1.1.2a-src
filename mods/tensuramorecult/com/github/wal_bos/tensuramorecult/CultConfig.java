package com.github.wal_bos.tensuramorecult;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;

public final class CultConfig {
   public static final ModConfigSpec SPEC;
   public static final DoubleValue AZAZEL_HEALTH;
   public static final DoubleValue HUMAN_HEALTH;
   public static final DoubleValue AZAZEL_STATS;
   public static final DoubleValue HUMAN_STATS;
   public static final DoubleValue AZAZEL_DAMAGE;
   public static final DoubleValue HUMAN_DAMAGE;
   public static final DoubleValue AZAZEL_EP;
   public static final DoubleValue HUMAN_EP;
   public static final DoubleValue AZAZEL_SPIRITUAL_HEALTH;
   public static final DoubleValue HUMAN_SPIRITUAL_HEALTH;
   public static final BooleanValue AZAZEL_GREED;
   public static final BooleanValue HUMAN_GREED;
   public static final BooleanValue AZAZEL_PRIDE;
   public static final BooleanValue HUMAN_PRIDE;
   public static final BooleanValue AZAZEL_RESISTANCES;
   public static final BooleanValue HUMAN_RESISTANCES;
   public static final BooleanValue TOGGLE_BOSS_SKILLS;
   public static final ConfigValue<String> GREED_SKILL_ID;
   public static final ConfigValue<String> PRIDE_SKILL_ID;
   public static final ConfigValue<String> BOSS_RESISTANCE_SKILL_IDS;
   public static final DoubleValue MOB_HEALTH_SCALE;
   public static final DoubleValue MOB_DAMAGE_SCALE;
   public static final DoubleValue MOB_EP_SCALE;
   public static final DoubleValue MOB_SPIRITUAL_HEALTH_SCALE;
   public static final BooleanValue DEBUFFS;
   public static final BooleanValue PRAYER;
   public static final BooleanValue MATCH_HIHIIROKANE_ATTACK;
   public static final BooleanValue MATCH_HIHIIROKANE_DURABILITY;
   public static final DoubleValue WEAPON_ATTACK_FLOOR;
   public static final DoubleValue HELMET_ARMOR;
   public static final DoubleValue CHEST_ARMOR;
   public static final DoubleValue LEGS_ARMOR;
   public static final DoubleValue BOOTS_ARMOR;
   public static final DoubleValue ARMOR_TOUGHNESS;
   public static final DoubleValue ARMOR_KNOCKBACK_RESISTANCE;
   public static final DoubleValue ARMOR_AURA;
   public static final DoubleValue ARMOR_MAGICULE;
   public static final DoubleValue ARMOR_SPIRITUAL_HEALTH;
   public static final DoubleValue ARMOR_FLAME_RESISTANCE;
   public static final DoubleValue ARMOR_DARKNESS_RESISTANCE;
   public static final DoubleValue WEAPON_AURA;
   public static final DoubleValue WEAPON_MAGICULE;
   public static final DoubleValue SHIELD_AURA;
   public static final DoubleValue SHIELD_MAGICULE;
   public static final DoubleValue SHIELD_SPIRITUAL_HEALTH;
   public static final DoubleValue SHIELD_MAGIC_RESISTANCE;
   public static final DoubleValue WEAPON_ITEM_EP;
   public static final DoubleValue ARMOR_ITEM_EP;
   public static final DoubleValue SHIELD_ITEM_EP;

   private CultConfig() {
   }

   static {
      Builder builder = new Builder();
      builder.push("bosses");
      AZAZEL_HEALTH = builder.defineInRange("azazelHealth", 5000.0, 1.0, 1000000.0);
      HUMAN_HEALTH = builder.defineInRange("humanHealth", 10000.0, 1.0, 1000000.0);
      AZAZEL_STATS = builder.defineInRange("azazelStatsMultiplier", 3.0, 0.1, 100.0);
      HUMAN_STATS = builder.defineInRange("humanStatsMultiplier", 5.0, 0.1, 100.0);
      AZAZEL_DAMAGE = builder.defineInRange("azazelDamageMultiplier", 2.0, 0.1, 100.0);
      HUMAN_DAMAGE = builder.defineInRange("humanDamageMultiplier", 3.0, 0.1, 100.0);
      AZAZEL_EP = builder.defineInRange("azazelEP", 1000000.0, 0.0, 1.0E8);
      HUMAN_EP = builder.defineInRange("humanEP", 2000000.0, 0.0, 1.0E8);
      AZAZEL_SPIRITUAL_HEALTH = builder.defineInRange("azazelSpiritualHealth", 10800.0, 0.0, 1000000.0);
      HUMAN_SPIRITUAL_HEALTH = builder.defineInRange("humanSpiritualHealth", 18000.0, 0.0, 1000000.0);
      builder.push("skills");
      AZAZEL_GREED = builder.define("azazelGreed", true);
      HUMAN_GREED = builder.define("humanGreed", true);
      AZAZEL_PRIDE = builder.define("azazelPride", true);
      HUMAN_PRIDE = builder.define("humanPride", true);
      AZAZEL_RESISTANCES = builder.define("azazelResistances", true);
      HUMAN_RESISTANCES = builder.define("humanResistances", true);
      TOGGLE_BOSS_SKILLS = builder.define("toggleBossSkills", true);
      GREED_SKILL_ID = builder.define("greedSkillId", "tensura:greed");
      PRIDE_SKILL_ID = builder.define("prideSkillId", "tensura:pride");
      BOSS_RESISTANCE_SKILL_IDS = builder.define(
         "resistanceSkillIds",
         "tensura:abnormal_condition_resistance,tensura:cold_resistance,tensura:corrosion_resistance,tensura:darkness_attack_resistance,tensura:earth_attack_resistance,tensura:electricity_resistance,tensura:flame_attack_resistance,tensura:gravity_attack_resistance,tensura:heat_resistance,tensura:holy_attack_resistance,tensura:light_attack_resistance,tensura:magic_resistance,tensura:physical_attack_resistance,tensura:pain_resistance,tensura:paralysis_resistance,tensura:pierce_resistance,tensura:poison_resistance,tensura:spatial_attack_resistance,tensura:spiritual_attack_resistance,tensura:thermal_fluctuation_resistance,tensura:water_attack_resistance,tensura:wind_attack_resistance"
      );
      builder.pop();
      builder.pop();
      builder.push("mobs");
      MOB_HEALTH_SCALE = builder.defineInRange("healthScale", 1.0, 0.0, 100.0);
      MOB_DAMAGE_SCALE = builder.defineInRange("damageScale", 1.0, 0.0, 100.0);
      MOB_EP_SCALE = builder.defineInRange("epScale", 1.0, 0.0, 100.0);
      MOB_SPIRITUAL_HEALTH_SCALE = builder.defineInRange("spiritualHealthScale", 1.0, 0.0, 100.0);
      builder.pop();
      builder.push("gear");
      MATCH_HIHIIROKANE_ATTACK = builder.define("matchHihiirokaneAttack", true);
      MATCH_HIHIIROKANE_DURABILITY = builder.define("matchHihiirokaneDurability", true);
      WEAPON_ATTACK_FLOOR = builder.defineInRange("weaponAttackFloor", 80.0, 0.0, 10000.0);
      HELMET_ARMOR = builder.defineInRange("helmetArmor", 8.0, 0.0, 100.0);
      CHEST_ARMOR = builder.defineInRange("chestArmor", 15.0, 0.0, 100.0);
      LEGS_ARMOR = builder.defineInRange("legsArmor", 12.0, 0.0, 100.0);
      BOOTS_ARMOR = builder.defineInRange("bootsArmor", 8.0, 0.0, 100.0);
      ARMOR_TOUGHNESS = builder.defineInRange("armorToughness", 6.0, 0.0, 100.0);
      ARMOR_KNOCKBACK_RESISTANCE = builder.defineInRange("armorKnockbackResistance", 0.5, 0.0, 1.0);
      ARMOR_AURA = builder.defineInRange("armorAuraPerPiece", 750.0, 0.0, 1000000.0);
      ARMOR_MAGICULE = builder.defineInRange("armorMagiculePerPiece", 750.0, 0.0, 1000000.0);
      ARMOR_SPIRITUAL_HEALTH = builder.defineInRange("armorSpiritualHealthPerPiece", 50.0, 0.0, 1000000.0);
      ARMOR_FLAME_RESISTANCE = builder.defineInRange("armorFlameResistancePerPiece", 0.05, 0.0, 1.0);
      ARMOR_DARKNESS_RESISTANCE = builder.defineInRange("armorDarknessResistancePerPiece", 0.05, 0.0, 1.0);
      WEAPON_AURA = builder.defineInRange("weaponAura", 1000.0, 0.0, 1000000.0);
      WEAPON_MAGICULE = builder.defineInRange("weaponMagicule", 1000.0, 0.0, 1000000.0);
      SHIELD_AURA = builder.defineInRange("shieldAura", 1000.0, 0.0, 1000000.0);
      SHIELD_MAGICULE = builder.defineInRange("shieldMagicule", 1000.0, 0.0, 1000000.0);
      SHIELD_SPIRITUAL_HEALTH = builder.defineInRange("shieldSpiritualHealth", 75.0, 0.0, 1000000.0);
      SHIELD_MAGIC_RESISTANCE = builder.defineInRange("shieldMagicResistance", 0.1, 0.0, 1.0);
      WEAPON_ITEM_EP = builder.defineInRange("weaponItemEP", 5000.0, 0.0, 1.0E8);
      ARMOR_ITEM_EP = builder.defineInRange("armorItemEP", 5000.0, 0.0, 1.0E8);
      SHIELD_ITEM_EP = builder.defineInRange("shieldItemEP", 5000.0, 0.0, 1.0E8);
      builder.pop();
      builder.push("mechanics");
      DEBUFFS = builder.define("tensuraDebuffs", true);
      PRAYER = builder.define("believerInfiniteRegeneration", true);
      builder.pop();
      SPEC = builder.build();
   }
}
