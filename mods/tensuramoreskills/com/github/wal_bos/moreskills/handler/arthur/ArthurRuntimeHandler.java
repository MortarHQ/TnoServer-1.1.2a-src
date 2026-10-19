package com.github.wal_bos.moreskills.handler.arthur;

import com.github.wal_bos.moreskills.ability.skill.ultimate.ArthurOnceAndFutureKingSkill;
import com.github.wal_bos.moreskills.ability.skill.ultimate.AvalonGodOfEternalKingdomSkill;
import com.github.wal_bos.moreskills.handler.avalon.AvalonActions;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.AttributeHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class ArthurRuntimeHandler {
   private static final String CROWN_STACKS = "ArthurCrownStacks";
   private static final String CROWN_TICKS = "ArthurCrownTicks";
   private static final String AVALON_WEAPON_STACKS = "AvalonDivineWeaponStacks";
   private static final ResourceLocation ARTHUR_AUTHORITY_DODGE = RoyalSkillSupport.id("arthur_authority_dodge");
   private static final ResourceLocation ARTHUR_AUTHORITY_RESIST = RoyalSkillSupport.id("arthur_authority_resistance");
   private static final ResourceLocation ARTHUR_AUTHORITY_PHYSICAL = RoyalSkillSupport.id("arthur_authority_physical");
   private static final ResourceLocation ARTHUR_AUTHORITY_LIGHT = RoyalSkillSupport.id("arthur_authority_light");
   private static final ResourceLocation ARTHUR_SWORD_DAMAGE = RoyalSkillSupport.id("arthur_sword_damage");
   private static final ResourceLocation ARTHUR_SWORD_SPEED = RoyalSkillSupport.id("arthur_sword_speed");
   private static final ResourceLocation ARTHUR_SWORD_MOVE = RoyalSkillSupport.id("arthur_sword_move");
   private static final ResourceLocation ARTHUR_SHIELD_ARMOR = RoyalSkillSupport.id("arthur_shield_armor");
   private static final ResourceLocation ARTHUR_PICKAXE_HASTE = RoyalSkillSupport.id("arthur_pickaxe_haste");
   private static final ResourceLocation AVALON_AUTHORITY_DODGE = RoyalSkillSupport.id("avalon_authority_dodge");
   private static final ResourceLocation AVALON_AUTHORITY_RESIST = RoyalSkillSupport.id("avalon_authority_resistance");
   private static final ResourceLocation AVALON_AUTHORITY_PHYSICAL = RoyalSkillSupport.id("avalon_authority_physical");
   private static final ResourceLocation AVALON_AUTHORITY_LIGHT = RoyalSkillSupport.id("avalon_authority_light");
   private static final ResourceLocation AVALON_AUTHORITY_LAW = RoyalSkillSupport.id("avalon_authority_law");
   private static final ResourceLocation AVALON_SWORD_DAMAGE = RoyalSkillSupport.id("avalon_sword_damage");
   private static final ResourceLocation AVALON_SWORD_SPEED = RoyalSkillSupport.id("avalon_sword_speed");
   private static final ResourceLocation AVALON_SWORD_MOVE = RoyalSkillSupport.id("avalon_sword_move");
   private static final ResourceLocation AVALON_SHIELD_ARMOR = RoyalSkillSupport.id("avalon_shield_armor");
   private static final ResourceLocation AVALON_PICKAXE_HASTE = RoyalSkillSupport.id("avalon_pickaxe_haste");
   private static final ResourceLocation AVALON_HIT_DAMAGE = RoyalSkillSupport.id("avalon_hit_damage");
   private static final ResourceLocation AVALON_HIT_SPEED = RoyalSkillSupport.id("avalon_hit_speed");
   private static final ResourceLocation AVALON_ENDURANCE_MELEE = RoyalSkillSupport.id("avalon_endurance_melee");
   private static final ResourceLocation AVALON_ENDURANCE_PROJECTILE = RoyalSkillSupport.id("avalon_endurance_projectile");
   private static final int PASSIVE_REFRESH_TICKS = 20;
   private static final int PASSIVE_EFFECT_TICKS = 30;
   private static final int ENDURANCE_REFRESH_TICKS = 10;

   private ArthurRuntimeHandler() {
   }

   public static void init() {
   }

   @SubscribeEvent
   public static void onLevelTick(Post event) {
      if (event.getLevel() instanceof ServerLevel level) {
         for (ServerPlayer player : level.players()) {
            tickPlayer(player);
         }
      }
   }

   private static void tickPlayer(ServerPlayer player) {
      ManasSkillInstance arthur = findArthur(player);
      ManasSkillInstance avalon = findAvalon(player);
      if (arthur != null) {
         ArthurActions.tick(arthur, player);
      }

      if (avalon != null) {
         AvalonActions.tick(avalon, player);
      }

      if (avalon != null) {
         tickAvalonPassive(avalon, player);
      } else if (arthur != null) {
         tickArthurPassive(arthur, player);
      } else {
         removeAllEndurance(player);
      }
   }

   private static ManasSkillInstance findArthur(LivingEntity entity) {
      ManasSkillInstance direct = (ManasSkillInstance)SkillAPI.getSkillsFrom(entity)
         .getSkill((ManasSkill)AllSkills.ARTHUR_ONCE_AND_FUTURE_KING.get())
         .orElse(null);
      if (direct != null) {
         return direct;
      }

      for (ManasSkillInstance learned : SkillAPI.getSkillsFrom(entity).getLearnedSkills()) {
         if (learned != null && learned.getSkill() instanceof ArthurOnceAndFutureKingSkill) {
            return learned;
         }
      }

      return null;
   }

   private static ManasSkillInstance findAvalon(LivingEntity entity) {
      ManasSkillInstance direct = (ManasSkillInstance)SkillAPI.getSkillsFrom(entity)
         .getSkill((ManasSkill)AllSkills.AVALON_GOD_OF_ETERNAL_KINGDOM.get())
         .orElse(null);
      if (direct != null) {
         return direct;
      }

      for (ManasSkillInstance learned : SkillAPI.getSkillsFrom(entity).getLearnedSkills()) {
         if (learned != null && learned.getSkill() instanceof AvalonGodOfEternalKingdomSkill) {
            return learned;
         }
      }

      return null;
   }

   public static void tickArthur(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         ArthurActions.tick(instance, entity);
         if (findAvalon(entity) == null) {
            tickArthurPassive(instance, entity);
         }
      }
   }

   public static void tickAvalon(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         AvalonActions.tick(instance, entity);
         tickAvalonPassive(instance, entity);
      }
   }

   private static void tickArthurPassive(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.tickCount % 20 == 0) {
         cleanupAvalonPassiveAttributes(entity);
         applyArthurAuthority(entity);
         applyArthurWeaponMaster(instance, entity);
      }

      if (instance.isToggled()) {
         if (entity.tickCount % 10 == 0) {
            applyArthurEndurance(instance, entity);
         } else {
            removeArthurControlEffects(entity);
         }
      } else if (entity.tickCount % 20 == 0) {
         removeArthurEndurance(entity);
      }

      if (entity.tickCount % 20 == 0) {
         tickCrown(instance, entity);
      }
   }

   private static void tickAvalonPassive(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.tickCount % 20 == 0) {
         cleanupArthurPassiveAttributes(entity);
         applyAvalonAuthority(entity);
         applyAvalonWeaponMaster(instance, entity);
         if (RoyalSkillSupport.inCombat(entity)) {
            tickEternalKingdom(entity);
         }
      }

      if (instance.isToggled()) {
         if (entity.tickCount % 10 == 0) {
            applyAvalonEndurance(instance, entity);
         }
      } else if (entity.tickCount % 20 == 0) {
         removeAvalonEndurance(entity);
      }
   }

   public static void applyArthurImmediately(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide() && findAvalon(entity) == null) {
         applyArthurAuthority(entity);
         applyArthurWeaponMaster(instance, entity);
         if (instance.isToggled()) {
            applyArthurEndurance(instance, entity);
         }
      }
   }

   public static void applyAvalonImmediately(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         cleanupArthurPassiveAttributes(entity);
         applyAvalonAuthority(entity);
         applyAvalonWeaponMaster(instance, entity);
         if (instance.isToggled()) {
            applyAvalonEndurance(instance, entity);
         }
      }
   }

   public static void onArthurToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide() && findAvalon(entity) == null) {
         applyArthurEndurance(instance, entity);
      }
   }

   public static void onArthurToggleOff(LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         if (findAvalon(entity) == null) {
            removeArthurEndurance(entity);
         }
      }
   }

   public static void onAvalonToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         removeArthurEndurance(entity);
         applyAvalonEndurance(instance, entity);
      }
   }

   public static void onAvalonToggleOff(LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         removeAvalonEndurance(entity);
      }
   }

   public static int crownStacks(ManasSkillInstance instance) {
      return Math.max(0, Math.min(20, instance.getOrCreateTag().getInt("ArthurCrownStacks")));
   }

   private static void tickCrown(ManasSkillInstance instance, LivingEntity entity) {
      if (!RoyalSkillSupport.inCombat(entity)) {
         int ticks = instance.getOrCreateTag().getInt("ArthurCrownTicks");
         int stacks = instance.getOrCreateTag().getInt("ArthurCrownStacks");
         if (ticks != 0 || stacks != 0) {
            instance.getOrCreateTag().putInt("ArthurCrownTicks", 0);
            instance.getOrCreateTag().putInt("ArthurCrownStacks", 0);
            instance.markDirty();
         }
      } else {
         int ticks = instance.getOrCreateTag().getInt("ArthurCrownTicks") + 20;
         boolean changed = false;

         while (ticks >= 600) {
            ticks -= 600;
            int current = instance.getOrCreateTag().getInt("ArthurCrownStacks");
            int stacks = Math.min(20, current + 1);
            if (stacks != current) {
               instance.getOrCreateTag().putInt("ArthurCrownStacks", stacks);
               instance.addMasteryPoint(entity);
               changed = true;
            }

            if (stacks >= 20) {
               ticks = 0;
               break;
            }
         }

         instance.getOrCreateTag().putInt("ArthurCrownTicks", ticks);
         if (changed || entity.tickCount % 100 == 0) {
            instance.markDirty();
         }
      }
   }

   private static void applyArthurAuthority(LivingEntity entity) {
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, ARTHUR_AUTHORITY_DODGE, 100.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, ARTHUR_AUTHORITY_RESIST, 1.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.PHYSICAL_RESIST_DEGRADATION, ARTHUR_AUTHORITY_PHYSICAL, 1.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.LIGHT_RESIST_DEGRADATION, ARTHUR_AUTHORITY_LIGHT, 1.0, Operation.ADD_VALUE);
   }

   private static void applyArthurWeaponMaster(ManasSkillInstance instance, LivingEntity entity) {
      boolean mastered = instance.isMastered(entity);
      boolean sword = RoyalSkillSupport.isSword(entity.getMainHandItem()) || RoyalSkillSupport.isSword(entity.getOffhandItem());
      boolean pickaxe = RoyalSkillSupport.isPickaxe(entity.getMainHandItem()) || RoyalSkillSupport.isPickaxe(entity.getOffhandItem());
      boolean shieldMain = RoyalSkillSupport.isShield(entity.getMainHandItem());
      boolean shieldOff = RoyalSkillSupport.isShield(entity.getOffhandItem());
      if (sword) {
         AttributeHelper.addPermanentAttribute(entity, Attributes.ATTACK_DAMAGE, ARTHUR_SWORD_DAMAGE, mastered ? 18.0 : 15.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(entity, Attributes.ATTACK_SPEED, ARTHUR_SWORD_SPEED, mastered ? 0.42 : 0.32, Operation.ADD_MULTIPLIED_TOTAL);
         AttributeHelper.addPermanentAttribute(entity, Attributes.MOVEMENT_SPEED, ARTHUR_SWORD_MOVE, 0.2, Operation.ADD_MULTIPLIED_TOTAL);
         RoyalSkillSupport.applyEffect(entity, entity, MobEffects.DAMAGE_BOOST, 30, mastered ? 5 : 4);
         RoyalSkillSupport.applyEffect(entity, entity, MobEffects.DIG_SPEED, 30, 2);
         RoyalSkillSupport.applyEffect(entity, entity, MobEffects.MOVEMENT_SPEED, 30, 1);
      } else {
         AttributeHelper.removeAttribute(entity, Attributes.ATTACK_DAMAGE, ARTHUR_SWORD_DAMAGE);
         AttributeHelper.removeAttribute(entity, Attributes.ATTACK_SPEED, ARTHUR_SWORD_SPEED);
         AttributeHelper.removeAttribute(entity, Attributes.MOVEMENT_SPEED, ARTHUR_SWORD_MOVE);
      }

      if (!shieldMain && !shieldOff) {
         AttributeHelper.removeAttribute(entity, Attributes.ARMOR, ARTHUR_SHIELD_ARMOR);
      } else {
         AttributeHelper.addPermanentAttribute(entity, Attributes.ARMOR, ARTHUR_SHIELD_ARMOR, shieldMain ? 12.0 : 8.0, Operation.ADD_VALUE);
         RoyalSkillSupport.applyEffect(entity, entity, MobEffects.DAMAGE_RESISTANCE, 30, shieldMain ? 2 : 1);
      }

      if (pickaxe) {
         AttributeHelper.addPermanentAttribute(entity, Attributes.BLOCK_BREAK_SPEED, ARTHUR_PICKAXE_HASTE, mastered ? 0.8 : 0.6, Operation.ADD_MULTIPLIED_TOTAL);
         RoyalSkillSupport.applyEffect(entity, entity, MobEffects.DIG_SPEED, 30, mastered ? 3 : 2);
      } else {
         AttributeHelper.removeAttribute(entity, Attributes.BLOCK_BREAK_SPEED, ARTHUR_PICKAXE_HASTE);
      }
   }

   private static void applyArthurEndurance(ManasSkillInstance instance, LivingEntity entity) {
      removeArthurControlEffects(entity);
      RoyalSkillSupport.infiniteRegeneration(entity, instance, 0, 0.5);
   }

   private static void removeArthurControlEffects(LivingEntity entity) {
      entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.FEAR));
      entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.HYPNOSIS));
      entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MIND_CONTROL));
      entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.LUST_EMBRACEMENT));
   }

   private static void removeArthurEndurance(LivingEntity entity) {
      RoyalSkillSupport.removeInfiniteRegeneration(entity);
   }

   private static void applyAvalonAuthority(LivingEntity entity) {
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, AVALON_AUTHORITY_DODGE, 100.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, AVALON_AUTHORITY_RESIST, 1.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.PHYSICAL_RESIST_DEGRADATION, AVALON_AUTHORITY_PHYSICAL, 1.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.LIGHT_RESIST_DEGRADATION, AVALON_AUTHORITY_LIGHT, 1.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.LAW_DEGRADATION, AVALON_AUTHORITY_LAW, 1.0, Operation.ADD_VALUE);
   }

   private static void applyAvalonWeaponMaster(ManasSkillInstance instance, LivingEntity entity) {
      boolean mastered = instance.isMastered(entity);
      boolean sword = RoyalSkillSupport.isSword(entity.getMainHandItem()) || RoyalSkillSupport.isSword(entity.getOffhandItem());
      boolean pickaxe = RoyalSkillSupport.isPickaxe(entity.getMainHandItem()) || RoyalSkillSupport.isPickaxe(entity.getOffhandItem());
      boolean shieldMain = RoyalSkillSupport.isShield(entity.getMainHandItem());
      boolean shieldOff = RoyalSkillSupport.isShield(entity.getOffhandItem());
      if (sword) {
         AttributeHelper.addPermanentAttribute(entity, Attributes.ATTACK_DAMAGE, AVALON_SWORD_DAMAGE, mastered ? 30.0 : 24.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(entity, Attributes.ATTACK_SPEED, AVALON_SWORD_SPEED, 0.6, Operation.ADD_MULTIPLIED_TOTAL);
         AttributeHelper.addPermanentAttribute(entity, Attributes.MOVEMENT_SPEED, AVALON_SWORD_MOVE, 0.32, Operation.ADD_MULTIPLIED_TOTAL);
         RoyalSkillSupport.applyEffect(entity, entity, MobEffects.DAMAGE_BOOST, 30, mastered ? 9 : 7);
         RoyalSkillSupport.applyEffect(entity, entity, MobEffects.DIG_SPEED, 30, 4);
         RoyalSkillSupport.applyEffect(entity, entity, MobEffects.MOVEMENT_SPEED, 30, 3);
      } else {
         AttributeHelper.removeAttribute(entity, Attributes.ATTACK_DAMAGE, AVALON_SWORD_DAMAGE);
         AttributeHelper.removeAttribute(entity, Attributes.ATTACK_SPEED, AVALON_SWORD_SPEED);
         AttributeHelper.removeAttribute(entity, Attributes.MOVEMENT_SPEED, AVALON_SWORD_MOVE);
      }

      if (!shieldMain && !shieldOff) {
         AttributeHelper.removeAttribute(entity, Attributes.ARMOR, AVALON_SHIELD_ARMOR);
      } else {
         AttributeHelper.addPermanentAttribute(entity, Attributes.ARMOR, AVALON_SHIELD_ARMOR, shieldMain ? 24.0 : 16.0, Operation.ADD_VALUE);
         RoyalSkillSupport.applyEffect(entity, entity, MobEffects.DAMAGE_RESISTANCE, 30, shieldMain ? 5 : 3);
      }

      if (pickaxe) {
         AttributeHelper.addPermanentAttribute(entity, Attributes.BLOCK_BREAK_SPEED, AVALON_PICKAXE_HASTE, mastered ? 1.6 : 1.2, Operation.ADD_MULTIPLIED_TOTAL);
         RoyalSkillSupport.applyEffect(entity, entity, MobEffects.DIG_SPEED, 30, mastered ? 7 : 5);
      } else {
         AttributeHelper.removeAttribute(entity, Attributes.BLOCK_BREAK_SPEED, AVALON_PICKAXE_HASTE);
      }

      int stacks = Math.max(0, Math.min(100, instance.getOrCreateTag().getInt("AvalonDivineWeaponStacks")));
      if (stacks > 0) {
         AttributeHelper.addPermanentAttribute(entity, Attributes.ATTACK_DAMAGE, AVALON_HIT_DAMAGE, stacks * 0.01, Operation.ADD_MULTIPLIED_TOTAL);
         AttributeHelper.addPermanentAttribute(entity, Attributes.ATTACK_SPEED, AVALON_HIT_SPEED, stacks * 0.01, Operation.ADD_MULTIPLIED_TOTAL);
      } else {
         AttributeHelper.removeAttribute(entity, Attributes.ATTACK_DAMAGE, AVALON_HIT_DAMAGE);
         AttributeHelper.removeAttribute(entity, Attributes.ATTACK_SPEED, AVALON_HIT_SPEED);
      }
   }

   private static void removeAvalonWeaponAttributes(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_DAMAGE, AVALON_SWORD_DAMAGE);
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_SPEED, AVALON_SWORD_SPEED);
      AttributeHelper.removeAttribute(entity, Attributes.MOVEMENT_SPEED, AVALON_SWORD_MOVE);
      AttributeHelper.removeAttribute(entity, Attributes.ARMOR, AVALON_SHIELD_ARMOR);
      AttributeHelper.removeAttribute(entity, Attributes.BLOCK_BREAK_SPEED, AVALON_PICKAXE_HASTE);
   }

   private static void tickEternalKingdom(LivingEntity entity) {
      RoyalSkillSupport.removeHarmfulEffects(entity);
      if (entity.tickCount % 80 == 0) {
         for (LivingEntity ally : RoyalSkillSupport.alliesInRange(entity, 48.0)) {
            RoyalSkillSupport.applyEffect(ally, entity, TensuraMobEffects.getReference(TensuraMobEffects.INSPIRATION), 100, 2);
         }
      }

      if (entity.tickCount % 200 == 0) {
         RoyalSkillSupport.restoreEnergyPercent(entity, 0.05, 0.05);
      }
   }

   private static void applyAvalonEndurance(ManasSkillInstance instance, LivingEntity entity) {
      RoyalSkillSupport.infiniteRegeneration(entity, instance, 0, 0.2);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, AVALON_ENDURANCE_MELEE, 50.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, AVALON_ENDURANCE_PROJECTILE, 100.0, Operation.ADD_VALUE);
      RoyalSkillSupport.applyEffect(entity, entity, TensuraMobEffects.getReference(TensuraMobEffects.AURA_SWORD), 30, 1);
   }

   private static void removeAvalonEndurance(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, AVALON_ENDURANCE_MELEE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, AVALON_ENDURANCE_PROJECTILE);
      RoyalSkillSupport.removeInfiniteRegeneration(entity);
   }

   private static void removeAllEndurance(LivingEntity entity) {
      removeAvalonEndurance(entity);
      removeArthurEndurance(entity);
   }

   public static void cleanupArthur(LivingEntity entity) {
      cleanupArthurPassiveAttributes(entity);
      if (findAvalon(entity) == null) {
         removeArthurEndurance(entity);
      }
   }

   private static void cleanupArthurPassiveAttributes(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, ARTHUR_AUTHORITY_DODGE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, ARTHUR_AUTHORITY_RESIST);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.PHYSICAL_RESIST_DEGRADATION, ARTHUR_AUTHORITY_PHYSICAL);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.LIGHT_RESIST_DEGRADATION, ARTHUR_AUTHORITY_LIGHT);
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_DAMAGE, ARTHUR_SWORD_DAMAGE);
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_SPEED, ARTHUR_SWORD_SPEED);
      AttributeHelper.removeAttribute(entity, Attributes.MOVEMENT_SPEED, ARTHUR_SWORD_MOVE);
      AttributeHelper.removeAttribute(entity, Attributes.ARMOR, ARTHUR_SHIELD_ARMOR);
      AttributeHelper.removeAttribute(entity, Attributes.BLOCK_BREAK_SPEED, ARTHUR_PICKAXE_HASTE);
   }

   private static void cleanupAvalonPassiveAttributes(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, AVALON_AUTHORITY_DODGE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, AVALON_AUTHORITY_RESIST);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.PHYSICAL_RESIST_DEGRADATION, AVALON_AUTHORITY_PHYSICAL);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.LIGHT_RESIST_DEGRADATION, AVALON_AUTHORITY_LIGHT);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.LAW_DEGRADATION, AVALON_AUTHORITY_LAW);
      removeAvalonWeaponAttributes(entity);
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_DAMAGE, AVALON_HIT_DAMAGE);
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_SPEED, AVALON_HIT_SPEED);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, AVALON_ENDURANCE_MELEE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, AVALON_ENDURANCE_PROJECTILE);
   }

   public static void cleanupAvalon(LivingEntity entity) {
      cleanupAvalonPassiveAttributes(entity);
      removeAvalonEndurance(entity);
      ManasSkillInstance arthur = findArthur(entity);
      if (arthur != null) {
         applyArthurImmediately(arthur, entity);
      }
   }
}
