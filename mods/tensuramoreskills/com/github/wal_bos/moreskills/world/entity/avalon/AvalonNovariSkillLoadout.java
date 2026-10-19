package com.github.wal_bos.moreskills.world.entity.avalon;

import com.github.wal_bos.moreskills.world.entity.AvalonNovariEntity;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.magic.AspectualMagics;
import io.github.manasmods.tensura.registry.magic.SpiritualMagics;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.util.AttributeHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class AvalonNovariSkillLoadout {
   private static final ResourceLocation ARMOR = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_armor");
   private static final ResourceLocation TOUGHNESS = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_toughness");
   private static final ResourceLocation KNOCKBACK = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_knockback");
   private static final ResourceLocation ATTACK = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_attack");
   private static final ResourceLocation ATTACK_SPEED = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_attack_speed");
   private static final ResourceLocation MOVEMENT = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_movement");
   private static final ResourceLocation PRESENCE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_presence");
   private static final ResourceLocation PRESENCE_RADIUS = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_presence_radius");
   private static final ResourceLocation CHANT = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_chant_annulment_attribute");
   private static final ResourceLocation RESISTANCE_PIERCE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_resistance_degradation");
   private static final ResourceLocation DODGE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_auto_projectile_dodge");
   private static final ResourceLocation DODGE_NEGATE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_dodge_negate");
   private static final ResourceLocation BARRIER = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_multilayer_barrier");
   private static final ResourceLocation REGEN = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_regen");
   private static final ResourceLocation AURA_REGEN = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_aura_regen");
   private static final ResourceLocation MAGICULE_REGEN = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "avalon_novari_magicule_regen");

   private AvalonNovariSkillLoadout() {
   }

   public static void apply(AvalonNovariEntity boss) {
      if (boss != null && !boss.level().isClientSide()) {
         AttributeHelper.addPermanentAttribute(boss, Attributes.ARMOR, ARMOR, 0.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(boss, Attributes.ARMOR_TOUGHNESS, TOUGHNESS, 100.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(boss, Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK, 1.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(boss, Attributes.ATTACK_DAMAGE, ATTACK, 0.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(boss, Attributes.ATTACK_SPEED, ATTACK_SPEED, 1024.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(boss, Attributes.MOVEMENT_SPEED, MOVEMENT, 0.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(boss, TensuraAttributes.PRESENCE_SENSE, PRESENCE, 10.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(boss, TensuraAttributes.PRESENCE_SENSE_RADIUS, PRESENCE_RADIUS, 160.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(boss, TensuraAttributes.CHANT_SPEED, CHANT, 1000000.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(boss, TensuraAttributes.RESISTANCE_DEGRADATION, RESISTANCE_PIERCE, 1.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(boss, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, DODGE, 100.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(boss, TensuraAttributes.DODGE_NEGATE_CHANCE, DODGE_NEGATE, 100.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(boss, TensuraAttributes.MULTILAYER_BARRIER, BARRIER, 1000000.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(
            boss, TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, REGEN, boss.getPhase() == 2 ? 500.0 : 1000.0, Operation.ADD_VALUE
         );
         AttributeHelper.addPermanentAttribute(boss, TensuraAttributes.AURA_REGENERATION_MULTIPLIER, AURA_REGEN, 100.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(boss, TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, MAGICULE_REGEN, 100.0, Operation.ADD_VALUE);
         learnCoreSkills(boss);
         if (boss.getPhase() <= 2) {
            learnMagic(boss);
         }

         learnResistances(boss);
      }
   }

   private static void learnCoreSkills(AvalonNovariEntity boss) {
      learnAndToggle(boss, (ManasSkill)UniqueSkills.GREAT_SAGE.get());
      learnAndToggle(boss, (ManasSkill)UniqueSkills.SEEKER.get());
      learnAndToggle(boss, (ManasSkill)UniqueSkills.OBSERVER.get());
      learnAndToggle(boss, (ManasSkill)UniqueSkills.USURPER.get());
      learnAndToggle(boss, (ManasSkill)UniqueSkills.REFLECTOR.get());
      learnAndToggle(boss, (ManasSkill)ExtraSkills.BLACK_FLAME.get());
      learnAndToggle(boss, (ManasSkill)ExtraSkills.CHANT_ANNULMENT.get());
      learnByName(boss, "io.github.manasmods.tensura.registry.skill.ExtraSkills", "BLACK_LIGHTNING");
      learnByName(boss, "io.github.manasmods.tensura.registry.skill.ExtraSkills", "LAW_MANIPULATION");
      learnByName(boss, "io.github.manasmods.tensura.registry.skill.ExtraSkills", "SACRED_HAKI");
      learnByName(boss, "io.github.manasmods.tensura.registry.skill.ExtraSkills", "UNIVERSAL_PERCEPTION");
      learnByName(boss, "io.github.manasmods.tensura.registry.skill.ExtraSkills", "MULTILAYER_BARRIER");
      learnByName(boss, "io.github.manasmods.tensura.registry.skill.ExtraSkills", "INFINITE_REGENERATION");
      learnByName(boss, "io.github.manasmods.tensura.registry.skill.ExtraSkills", "ULTRASPEED_REGENERATION");
      learnByName(boss, "io.github.manasmods.tensura.registry.skill.ExtraSkills", "BLACK_FLAME");
      learnByName(boss, "io.github.manasmods.tensura.registry.skill.ExtraSkills", "CHANT_ANNULMENT");
   }

   private static void learnMagic(AvalonNovariEntity boss) {
      learnAndToggle(boss, (ManasSkill)AspectualMagics.FIRE_BALL.get());
      learnAndToggle(boss, (ManasSkill)AspectualMagics.FIRE_STORM.get());
      learnAndToggle(boss, (ManasSkill)AspectualMagics.ACID_SHELL.get());
      learnAndToggle(boss, (ManasSkill)AspectualMagics.ICE_BREAKER.get());
      learnAndToggle(boss, (ManasSkill)AspectualMagics.ICE_BLIZZARD.get());
      learnAndToggle(boss, (ManasSkill)AspectualMagics.TORNADO_BLADE.get());
      learnAndToggle(boss, (ManasSkill)AspectualMagics.WATER_JAIL.get());
      learnAndToggle(boss, (ManasSkill)AspectualMagics.MUD_HAND.get());
      learnAndToggle(boss, (ManasSkill)AspectualMagics.MUD_SPEARS.get());
      learnAndToggle(boss, (ManasSkill)AspectualMagics.STONE_SHOT.get());
      learnAndToggle(boss, (ManasSkill)AspectualMagics.THUNDER_ORB.get());
      learnAndToggle(boss, (ManasSkill)AspectualMagics.THUNDER_RAIN.get());
      learnAndToggle(boss, (ManasSkill)AspectualMagics.THUNDER_LANCE.get());
      learnAndToggle(boss, (ManasSkill)AspectualMagics.REINFORCED_BARRIER.get());
      learnAndToggle(boss, (ManasSkill)AspectualMagics.MENTAL_CRUSH.get());
      learnAndToggle(boss, (ManasSkill)AspectualMagics.HYPNOS.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.DARKNESS.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.TRUE_DARKNESS.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.DARK_CUBE.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.DARKNESS_CANNON.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.SHADOW_BIND.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.FIRE_BOLT.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.HELLFIRE.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.BLIZZARD.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.ACID_RAIN.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.MEGIDDO.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.LIGHTNING_LANCE.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.ELECTRO_BLAST.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.SOLAR_BEAM.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.SOLAR_RAIN.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.SPACE.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.SWIPE.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.SHRINK.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.CURSE.get());
      learnAndToggle(boss, (ManasSkill)SpiritualMagics.CURSE_BIND.get());
   }

   private static void learnResistances(AvalonNovariEntity boss) {
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.ABNORMAL_CONDITION_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.COLD_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.CORROSION_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.DARKNESS_ATTACK_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.EARTH_ATTACK_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.ELECTRICITY_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.FLAME_ATTACK_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.GRAVITY_ATTACK_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.HEAT_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.HOLY_ATTACK_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.LIGHT_ATTACK_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.MAGIC_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.PAIN_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.PARALYSIS_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.PIERCE_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.POISON_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.SPATIAL_ATTACK_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.THERMAL_FLUCTUATION_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.WATER_ATTACK_NULLIFICATION.get());
      learnAndToggle(boss, (ManasSkill)ResistanceSkills.WIND_ATTACK_NULLIFICATION.get());
   }

   private static void learnAndToggle(AvalonNovariEntity boss, ManasSkill skill) {
      if (boss != null && skill != null) {
         try {
            SkillHelper.learnSkill(boss, skill);
         } catch (Throwable var5) {
         }

         try {
            Skills skills = SkillAPI.getSkillsFrom(boss);
            ManasSkillInstance instance = (ManasSkillInstance)skills.getSkill(skill).orElse(null);
            if (instance == null) {
               return;
            }

            if (instance.getMastery() < 0.0) {
               instance.setMastery(0.0);
            }

            if (!instance.isToggled()) {
               instance.setToggled(true);
            }

            instance.markDirty();
            skills.markDirty();
         } catch (Throwable var4) {
         }
      }
   }

   private static void learnByName(AvalonNovariEntity boss, String className, String fieldName) {
      try {
         Object holder = Class.forName(className).getField(fieldName).get(null);
         if (holder.getClass().getMethod("get").invoke(holder) instanceof ManasSkill skill) {
            learnAndToggle(boss, skill);
         }
      } catch (Throwable var6) {
      }
   }
}
