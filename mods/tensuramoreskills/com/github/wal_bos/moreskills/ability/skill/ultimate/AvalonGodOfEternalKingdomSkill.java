package com.github.wal_bos.moreskills.ability.skill.ultimate;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.arthur.ArthurRuntimeHandler;
import com.github.wal_bos.moreskills.handler.avalon.AvalonActions;
import com.github.wal_bos.moreskills.handler.avalon.AvalonKingdomHandler;
import com.github.wal_bos.moreskills.handler.avalon.AvalonRoundTableHandler;
import com.github.wal_bos.moreskills.registry.TenaddexItems;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.manascore.skill.api.EntityEvents.LivingEffectAddedEvent;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillDamageCalculationEvent;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.LivingPostDamageEvent;
import io.github.manasmods.tensura.event.TensuraEntityEvents.SpiritualHurtEvent;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class AvalonGodOfEternalKingdomSkill extends Skill {
   private static final String WEAPON_STACKS = "AvalonDivineWeaponStacks";
   private static final double REQUIRED_EP = 1.0E8;
   private static final ResourceLocation AUTHORITY_DODGE = RoyalSkillSupport.id("avalon_authority_dodge");
   private static final ResourceLocation AUTHORITY_RESIST = RoyalSkillSupport.id("avalon_authority_resistance");
   private static final ResourceLocation AUTHORITY_PHYSICAL = RoyalSkillSupport.id("avalon_authority_physical");
   private static final ResourceLocation AUTHORITY_LIGHT = RoyalSkillSupport.id("avalon_authority_light");
   private static final ResourceLocation AUTHORITY_LAW = RoyalSkillSupport.id("avalon_authority_law");
   private static final ResourceLocation SWORD_DAMAGE = RoyalSkillSupport.id("avalon_sword_damage");
   private static final ResourceLocation SWORD_SPEED = RoyalSkillSupport.id("avalon_sword_speed");
   private static final ResourceLocation SWORD_MOVE = RoyalSkillSupport.id("avalon_sword_move");
   private static final ResourceLocation SHIELD_ARMOR = RoyalSkillSupport.id("avalon_shield_armor");
   private static final ResourceLocation PICKAXE_HASTE = RoyalSkillSupport.id("avalon_pickaxe_haste");
   private static final ResourceLocation HIT_DAMAGE = RoyalSkillSupport.id("avalon_hit_damage");
   private static final ResourceLocation HIT_SPEED = RoyalSkillSupport.id("avalon_hit_speed");
   private static final ResourceLocation ENDURANCE_MELEE = RoyalSkillSupport.id("avalon_endurance_melee");
   private static final ResourceLocation ENDURANCE_PROJECTILE = RoyalSkillSupport.id("avalon_endurance_projectile");
   private static final ThreadLocal<Boolean> ADDITIONAL_DAMAGE = ThreadLocal.withInitial(() -> false);
   private static boolean initialized;

   public AvalonGodOfEternalKingdomSkill() {
      super(SkillType.ULTIMATE);
      init();
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         AvalonActions.init();
         TensuraSkillEvents.SKILL_PLUNDER
            .register(
               (SkillPlunderEvent)(target, owner, steal, skill) -> skill.get() instanceof AvalonGodOfEternalKingdomSkill
                  ? EventResult.interruptFalse()
                  : EventResult.pass()
            );
         EntityEvents.LIVING_EFFECT_ADDED
            .register(
               (LivingEffectAddedEvent)(entity, source, changeableInstance) -> {
                  MobEffectInstance effect = (MobEffectInstance)changeableInstance.get();
                  if (effect == null || ((MobEffect)effect.getEffect().value()).getCategory() != MobEffectCategory.HARMFUL) {
                     return EventResult.pass();
                  } else if (!RoyalSkillSupport.inCombat(entity)) {
                     return EventResult.pass();
                  } else {
                     return SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)AllSkills.AVALON_GOD_OF_ETERNAL_KINGDOM.get()).isPresent()
                        ? EventResult.interruptFalse()
                        : EventResult.pass();
                  }
               }
            );
         SkillEvents.SKILL_DAMAGE_PRE_CALCULATION.register((SkillDamageCalculationEvent)(storage, target, source, amount) -> {
            if (source.getEntity() instanceof LivingEntity attacker) {
               RoyalSkillSupport.markCombat(attacker, target);
               if (ADDITIONAL_DAMAGE.get()) {
                  return EventResult.pass();
               }

               Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(attacker).getSkill((ManasSkill)AllSkills.AVALON_GOD_OF_ETERNAL_KINGDOM.get());
               if (!optional.isEmpty() && RoyalSkillSupport.inCombat(attacker)) {
                  boolean mastered = optional.get().isMastered(attacker);
                  if (isBattlewill(source, attacker) || TensuraDamageHelper.isPhysicalAttack(source)) {
                     amount.set((Float)amount.get() * 5.0F);
                  } else if (source.is(TensuraDamageTypes.HOLY_DAMAGE) || TensuraDamageHelper.isLightDamage(source)) {
                     amount.set((Float)amount.get() * (mastered ? 4.0F : 3.0F));
                  }

                  return EventResult.pass();
               } else {
                  return EventResult.pass();
               }
            } else {
               return EventResult.pass();
            }
         });
         TensuraEntityEvents.LIVING_POST_DAMAGE
            .register(
               (LivingPostDamageEvent)(target, source, amount) -> {
                  if (source.getEntity() instanceof LivingEntity attacker) {
                     Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(attacker)
                        .getSkill((ManasSkill)AllSkills.AVALON_GOD_OF_ETERNAL_KINGDOM.get());
                     if (!optional.isEmpty()) {
                        ManasSkillInstance instance = optional.get();
                        if (source.getDirectEntity() == attacker && TensuraDamageHelper.isPhysicalAttack(source)) {
                           int stacks = Math.min(100, instance.getOrCreateTag().getInt("AvalonDivineWeaponStacks") + 1);
                           instance.getOrCreateTag().putInt("AvalonDivineWeaponStacks", stacks);
                           instance.markDirty();
                        }

                        if (!ADDITIONAL_DAMAGE.get()) {
                           int mode = 0;

                           try {
                              ADDITIONAL_DAMAGE.set(true);
                              if (isBattlewill(source, attacker)) {
                                 target.invulnerableTime = 0;
                                 target.hurt(RoyalSkillSupport.source(attacker, instance, mode, TensuraDamageTypes.HOLY_DAMAGE), amount);
                              } else if (source.is(TensuraDamageTypes.HOLY_DAMAGE)) {
                                 target.invulnerableTime = 0;
                                 target.hurt(RoyalSkillSupport.physicalSource(attacker, instance, mode), amount);
                              } else if (source.getDirectEntity() == attacker
                                 && TensuraDamageHelper.isPhysicalAttack(source)
                                 && RoyalSkillSupport.isSword(attacker.getMainHandItem())) {
                                 target.invulnerableTime = 0;
                                 target.hurt(RoyalSkillSupport.source(attacker, instance, mode, TensuraDamageTypes.HOLY_DAMAGE), amount);
                              }
                           } finally {
                              ADDITIONAL_DAMAGE.set(false);
                           }
                        }
                     }
                  }
               }
            );
         TensuraEntityEvents.SPIRITUAL_HURT_EVENT
            .register(
               (SpiritualHurtEvent)(target, attacker, originalAmount, resistPercentage, amount, source) -> RoyalSkillSupport.isToggled(
                     target, (ManasSkill)AllSkills.AVALON_GOD_OF_ETERNAL_KINGDOM.get()
                  )
                  ? EventResult.interruptFalse()
                  : EventResult.pass()
            );
      }
   }

   private static boolean isBattlewill(DamageSource source, LivingEntity attacker) {
      return TensuraDamageHelper.isBattlewill(source, attacker);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/avalon_god_of_eternal_kingdom.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)MoreSkillsConfigs.GRAND.avalon.acquirementMagiculeCost.get();
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return 0;
   }

   public int getMaxMastery() {
      return (Integer)MoreSkillsConfigs.GRAND.avalon.maxMastery.get();
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      if (entity instanceof ServerPlayer serverPlayer) {
         if (!SkillObtainmentHelper.canAcquire(
            entity,
            (Double)MoreSkillsConfigs.GRAND.avalon.acquirementMagiculeCost.get(),
            ((ArthurOnceAndFutureKingSkill)AllSkills.ARTHUR_ONCE_AND_FUTURE_KING.get()).getRegistryName().toString(),
            true
         )) {
            return false;
         } else if (Math.max(newEP, EnergyHelper.getMaxEP(entity)) < 1.0E8) {
            return false;
         } else if (!TensuraStorages.getExistenceFrom(entity).isTrueHero()) {
            return false;
         } else if (!entity.getInventory().contains(new ItemStack((ItemLike)TenaddexItems.TRUE_EXCALIBUR.get()))
            && !entity.getMainHandItem().is((Item)TenaddexItems.TRUE_EXCALIBUR.get())
            && !entity.getOffhandItem().is((Item)TenaddexItems.TRUE_EXCALIBUR.get())) {
            return false;
         } else if (serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.RAID_WIN)) < (Integer)MoreSkillsConfigs.GRAND.avalon.requiredRaidWins.get()) {
            return false;
         } else if (TensuraStorages.getSpiritFrom(entity).getSpiritLevelId(Element.LIGHT) < SpiritLevel.GREATER.getId()) {
            return false;
         } else {
            return !hasMasteredRequiredLightMagic(entity) ? false : hasMasteredEveryBaseBattlewill(entity);
         }
      } else {
         return false;
      }
   }

   private static boolean hasMasteredRequiredLightMagic(Player entity) {
      Skills skills = SkillAPI.getSkillsFrom(entity);
      boolean found = false;

      for (Entry<ResourceKey<ManasSkill>, ManasSkill> entry : SkillAPI.getSkillRegistry().entrySet()) {
         ManasSkill skill = entry.getValue();
         ResourceLocation id = skill.getRegistryName();
         if (id.getNamespace().equals("tensura") && skill instanceof SpiritualMagic spiritualMagic && spiritualMagic.getElemental() == Element.LIGHT) {
            found = true;
            Optional<ManasSkillInstance> learned = skills.getSkill(skill);
            if (learned.isEmpty() || !learned.get().isMastered(entity)) {
               return false;
            }
         }
      }

      return found;
   }

   private static boolean hasMasteredEveryBaseBattlewill(Player entity) {
      Skills skills = SkillAPI.getSkillsFrom(entity);
      boolean found = false;

      for (Entry<ResourceKey<ManasSkill>, ManasSkill> entry : SkillAPI.getSkillRegistry().entrySet()) {
         ManasSkill skill = entry.getValue();
         ResourceLocation id = skill.getRegistryName();
         if (id.getNamespace().equals("tensura") && SkillAPI.getSkillRegistry().delegate(id).is(TensuraSkillTags.BATTLEWILL)) {
            found = true;
            Optional<ManasSkillInstance> learned = skills.getSkill(skill);
            if (learned.isEmpty() || !learned.get().isMastered(entity)) {
               return false;
            }
         }
      }

      return found;
   }

   public int getModes(ManasSkillInstance instance) {
      return AvalonActions.getModes(instance);
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return AvalonActions.nextMode(entity, instance, mode, reverse);
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return AvalonActions.getModeId(instance, mode, super.getModeId(instance, mode));
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return AvalonActions.getMagiculeCost(entity, instance, mode);
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      AvalonActions.onPressed(instance, entity, keyNumber, mode);
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      return AvalonActions.onHeld(instance, entity, heldTicks, mode);
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      AvalonActions.onRelease(instance, entity, heldTicks, keyNumber, mode);
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      super.onTick(instance, entity);
      if (!(entity instanceof ServerPlayer)) {
         ArthurRuntimeHandler.tickAvalon(instance, entity);
      }
   }

   private static void tickEternalKingdom(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.tickCount % 20 == 0) {
         RoyalSkillSupport.removeHarmfulEffects(entity);
      }

      if (entity.tickCount % 80 == 0) {
         for (LivingEntity ally : RoyalSkillSupport.alliesInRange(entity, 48.0)) {
            RoyalSkillSupport.applyEffect(ally, entity, TensuraMobEffects.getReference(TensuraMobEffects.INSPIRATION), 100, 2);
         }
      }

      if (entity.tickCount % 200 == 0) {
         RoyalSkillSupport.restoreEnergyPercent(entity, 0.05, 0.05);
      }
   }

   private static void applyAuthority(LivingEntity entity) {
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, AUTHORITY_DODGE, 100.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, AUTHORITY_RESIST, 1.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.PHYSICAL_RESIST_DEGRADATION, AUTHORITY_PHYSICAL, 1.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.LIGHT_RESIST_DEGRADATION, AUTHORITY_LIGHT, 1.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.LAW_DEGRADATION, AUTHORITY_LAW, 1.0, Operation.ADD_VALUE);
   }

   private static void applyWeaponMaster(ManasSkillInstance instance, LivingEntity entity) {
      boolean mastered = instance.isMastered(entity);
      boolean sword = RoyalSkillSupport.isSword(entity.getMainHandItem());
      boolean pickaxe = RoyalSkillSupport.isPickaxe(entity.getMainHandItem());
      boolean shieldMain = RoyalSkillSupport.isShield(entity.getMainHandItem());
      boolean shieldOff = RoyalSkillSupport.isShield(entity.getOffhandItem());
      if (sword) {
         AttributeHelper.addPermanentAttribute(entity, Attributes.ATTACK_DAMAGE, SWORD_DAMAGE, mastered ? 30.0 : 24.0, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(entity, Attributes.ATTACK_SPEED, SWORD_SPEED, 0.5, Operation.ADD_MULTIPLIED_TOTAL);
         AttributeHelper.addPermanentAttribute(entity, Attributes.MOVEMENT_SPEED, SWORD_MOVE, 0.8, Operation.ADD_MULTIPLIED_TOTAL);
      } else {
         AttributeHelper.removeAttribute(entity, Attributes.ATTACK_DAMAGE, SWORD_DAMAGE);
         AttributeHelper.removeAttribute(entity, Attributes.ATTACK_SPEED, SWORD_SPEED);
         AttributeHelper.removeAttribute(entity, Attributes.MOVEMENT_SPEED, SWORD_MOVE);
      }

      if (!shieldMain && !shieldOff) {
         AttributeHelper.removeAttribute(entity, Attributes.ARMOR, SHIELD_ARMOR);
      } else {
         AttributeHelper.addPermanentAttribute(entity, Attributes.ARMOR, SHIELD_ARMOR, shieldMain ? 24.0 : 16.0, Operation.ADD_VALUE);
      }

      if (pickaxe) {
         AttributeHelper.addPermanentAttribute(entity, Attributes.BLOCK_BREAK_SPEED, PICKAXE_HASTE, mastered ? 1.6 : 1.2, Operation.ADD_MULTIPLIED_TOTAL);
      } else {
         AttributeHelper.removeAttribute(entity, Attributes.BLOCK_BREAK_SPEED, PICKAXE_HASTE);
      }

      int stacks = Math.max(0, Math.min(100, instance.getOrCreateTag().getInt("AvalonDivineWeaponStacks")));
      if (stacks > 0) {
         AttributeHelper.addPermanentAttribute(entity, Attributes.ATTACK_DAMAGE, HIT_DAMAGE, stacks * 0.01, Operation.ADD_MULTIPLIED_TOTAL);
         AttributeHelper.addPermanentAttribute(entity, Attributes.ATTACK_SPEED, HIT_SPEED, stacks * 0.01, Operation.ADD_MULTIPLIED_TOTAL);
      } else {
         AttributeHelper.removeAttribute(entity, Attributes.ATTACK_DAMAGE, HIT_DAMAGE);
         AttributeHelper.removeAttribute(entity, Attributes.ATTACK_SPEED, HIT_SPEED);
      }
   }

   private static void applyEndurance(ManasSkillInstance instance, LivingEntity entity) {
      RoyalSkillSupport.infiniteRegeneration(entity, instance, 0, 0.2);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, ENDURANCE_MELEE, 50.0, Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, ENDURANCE_PROJECTILE, 100.0, Operation.ADD_VALUE);
      RoyalSkillSupport.applyEffect(entity, entity, TensuraMobEffects.getReference(TensuraMobEffects.AURA_SWORD), 40, 1);
   }

   private static void removeEndurance(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, ENDURANCE_MELEE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, ENDURANCE_PROJECTILE);
      RoyalSkillSupport.removeInfiniteRegeneration(entity);
   }

   public boolean onEffectAdded(ManasSkillInstance instance, LivingEntity entity, @Nullable Entity source, Changeable<MobEffectInstance> effect) {
      MobEffectInstance effectInstance = (MobEffectInstance)effect.get();
      if (effectInstance != null && instance.isToggled()) {
         Holder<MobEffect> holder = effectInstance.getEffect();
         if (holder.equals(TensuraMobEffects.getReference(TensuraMobEffects.FEAR))) {
            return false;
         } else if (holder.equals(TensuraMobEffects.getReference(TensuraMobEffects.HYPNOSIS))) {
            return false;
         } else {
            return holder.equals(TensuraMobEffects.getReference(TensuraMobEffects.MIND_CONTROL))
               ? false
               : !holder.equals(TensuraMobEffects.getReference(TensuraMobEffects.LUST_EMBRACEMENT));
         }
      } else {
         return true;
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOn(instance, entity);
      ArthurRuntimeHandler.onAvalonToggleOn(instance, entity);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOff(instance, entity);
      ArthurRuntimeHandler.onAvalonToggleOff(entity);
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      ArthurRuntimeHandler.applyAvalonImmediately(instance, entity);
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      AvalonActions.cleanup(entity);
      AvalonKingdomHandler.releaseOwnedBy(entity);
      AvalonRoundTableHandler.removeOwned(entity);
      cleanupAttributes(entity);
      ArthurRuntimeHandler.cleanupAvalon(entity);
   }

   private static void cleanupAttributes(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, AUTHORITY_DODGE);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, AUTHORITY_RESIST);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.PHYSICAL_RESIST_DEGRADATION, AUTHORITY_PHYSICAL);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.LIGHT_RESIST_DEGRADATION, AUTHORITY_LIGHT);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.LAW_DEGRADATION, AUTHORITY_LAW);
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_DAMAGE, SWORD_DAMAGE);
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_SPEED, SWORD_SPEED);
      AttributeHelper.removeAttribute(entity, Attributes.MOVEMENT_SPEED, SWORD_MOVE);
      AttributeHelper.removeAttribute(entity, Attributes.ARMOR, SHIELD_ARMOR);
      AttributeHelper.removeAttribute(entity, Attributes.BLOCK_BREAK_SPEED, PICKAXE_HASTE);
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_DAMAGE, HIT_DAMAGE);
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_SPEED, HIT_SPEED);
      removeEndurance(entity);
   }
}
