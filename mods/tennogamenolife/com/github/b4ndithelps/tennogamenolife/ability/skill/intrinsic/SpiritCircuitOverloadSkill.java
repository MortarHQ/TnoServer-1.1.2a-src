package com.github.b4ndithelps.tennogamenolife.ability.skill.intrinsic;

import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class SpiritCircuitOverloadSkill extends Skill {
   private static final ResourceLocation OUTPUT_MODIFIER = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "spirit_circuit_output");
   private static final ResourceLocation CRUISE_MODIFIER = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "spirit_circuit_cruise");
   private static final ResourceLocation DEFENSE_MODIFIER = ResourceLocation.fromNamespaceAndPath("tennogamenolife", "spirit_circuit_defense");
   private static final String HEAT = "SpiritCircuitHeat";
   private static final String BURNOUT_UNTIL = "SpiritCircuitBurnoutUntil";
   private static final String ORIGINAL_FLY_SPEED = "SpiritCircuitOriginalFlySpeed";
   private static final String BOOSTED_FLY_SPEED = "SpiritCircuitBoostedFlySpeed";
   private static final int DISCHARGE_MODE = 0;
   private static final double OUTPUT_COST_PER_SECOND = 300.0;
   private static final double CRUISE_COST_PER_SECOND = 200.0;
   private static final double DEFENSE_COST_PER_SECOND = 150.0;
   private static final double PASSIVE_COST_PER_SECOND = 650.0;
   private static final double DISCHARGE_COST = 2000.0;
   private static final int BURNOUT_TICKS = 300;

   public SpiritCircuitOverloadSkill() {
      super(SkillType.INTRINSIC);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/skill/intrinsic/spirit_circuit_overload.png");
   }

   public int getModes(ManasSkillInstance instance) {
      return 1;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return mode == 0 ? "spirit_circuit_overload.discharge" : super.getModeId(instance, mode);
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return mode == 0 ? 2000.0 : 0.0;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getTag();
      return tag == null || entity.level().getGameTime() >= tag.getLong("SpiritCircuitBurnoutUntil");
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (mode == 0) {
            this.releaseOverload(instance, entity);
         }
      }
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getTag();
      return instance.isToggled() || tag != null && tag.getInt("SpiritCircuitHeat") > 0;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         CompoundTag tag = instance.getOrCreateTag();
         int heat = tag.getInt("SpiritCircuitHeat");
         if (!instance.isToggled()) {
            if (heat > 0) {
               tag.putInt("SpiritCircuitHeat", Math.max(0, heat - 10));
               instance.markDirty();
            }
         } else if (entity.level().getGameTime() < tag.getLong("SpiritCircuitBurnoutUntil")) {
            this.disablePassive(instance, entity, false);
         } else if (!this.hasModifier(entity, TensuraAttributes.MULTILAYER_BARRIER, DEFENSE_MODIFIER)) {
            this.triggerBurnout(instance, entity, 100);
            this.displayActionBar(entity, Component.translatable("tennogamenolife.skill.spirit_circuit_overload.barrier_broken").withStyle(ChatFormatting.RED));
         } else if (this.isOutOfEnergy(entity, instance, 0.0, 3250.0)) {
            this.disablePassive(instance, entity, false);
         } else {
            heat = Math.min(100, heat + 15);
            tag.putInt("SpiritCircuitHeat", heat);
            instance.addMasteryPoint(entity);
            if (heat >= 100) {
               this.triggerBurnout(instance, entity, 300);
            } else {
               instance.markDirty();
            }
         }
      }
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (instance.isToggled() && ((TensuraDamageSource)source).tensura$getAbilityInstance() != null) {
         amount.set((Float)amount.get() * 1.2F);
      }

      return true;
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity owner, DamageSource source, Changeable<Float> amount) {
      CompoundTag tag = instance.getTag();
      if (tag != null && instance.isToggled() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         tag.putInt("SpiritCircuitHeat", Math.min(100, tag.getInt("SpiritCircuitHeat") + 5));
         instance.markDirty();
      }

      return true;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      long burnoutUntil = tag.getLong("SpiritCircuitBurnoutUntil");
      if (entity.level().getGameTime() < burnoutUntil) {
         long seconds = Math.max(1L, (burnoutUntil - entity.level().getGameTime() + 19L) / 20L);
         instance.setToggled(false);
         this.displayActionBar(
            entity, Component.translatable("tennogamenolife.skill.spirit_circuit_overload.burnout", new Object[]{seconds}).withStyle(ChatFormatting.RED)
         );
         instance.markDirty();
      } else {
         this.removeAllModifiers(entity, tag);
         this.applyOutputModifiers(entity);
         this.applyCruiseModifiers(entity, tag);
         this.applyDefenseModifier(entity);
         instance.markDirty();
         this.displayActionBar(entity, Component.translatable("tennogamenolife.skill.spirit_circuit_overload.activated_all").withStyle(ChatFormatting.GOLD));
         entity.level().playSound(null, entity.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 0.9F, 1.2F);
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      this.disablePassive(instance, entity, false);
   }

   private void disablePassive(ManasSkillInstance instance, LivingEntity entity, boolean clearHeat) {
      this.removeAllModifiers(entity, instance.getOrCreateTag());
      CompoundTag tag = instance.getOrCreateTag();
      if (clearHeat) {
         tag.putInt("SpiritCircuitHeat", 0);
      }

      instance.setToggled(false);
      instance.markDirty();
   }

   private void applyOutputModifiers(LivingEntity entity) {
      this.addModifier(entity, TensuraAttributes.CHANT_SPEED, OUTPUT_MODIFIER, 0.25, Operation.ADD_VALUE);
      this.addModifier(entity, TensuraAttributes.MAGIC_COST_MULTIPLIER, OUTPUT_MODIFIER, 0.2, Operation.ADD_VALUE);
   }

   private void applyCruiseModifiers(LivingEntity entity, CompoundTag tag) {
      this.addModifier(entity, Attributes.MOVEMENT_SPEED, CRUISE_MODIFIER, 0.2, Operation.ADD_MULTIPLIED_TOTAL);
      this.addModifier(entity, Attributes.FLYING_SPEED, CRUISE_MODIFIER, 0.2, Operation.ADD_MULTIPLIED_TOTAL);
      if (entity instanceof Player player) {
         float original = player.getAbilities().getFlyingSpeed();
         float boosted = original * 1.35F;
         tag.putFloat("SpiritCircuitOriginalFlySpeed", original);
         tag.putFloat("SpiritCircuitBoostedFlySpeed", boosted);
         player.getAbilities().setFlyingSpeed(boosted);
         player.onUpdateAbilities();
      }
   }

   private void applyDefenseModifier(LivingEntity entity) {
      this.addModifier(entity, TensuraAttributes.MULTILAYER_BARRIER, DEFENSE_MODIFIER, Math.max(20.0, entity.getMaxHealth() * 2.0), Operation.ADD_VALUE);
   }

   private void removeAllModifiers(LivingEntity entity, CompoundTag tag) {
      this.removeModifier(entity, TensuraAttributes.CHANT_SPEED, OUTPUT_MODIFIER);
      this.removeModifier(entity, TensuraAttributes.MAGIC_COST_MULTIPLIER, OUTPUT_MODIFIER);
      this.removeModifier(entity, Attributes.MOVEMENT_SPEED, CRUISE_MODIFIER);
      this.removeModifier(entity, Attributes.FLYING_SPEED, CRUISE_MODIFIER);
      this.removeModifier(entity, TensuraAttributes.MULTILAYER_BARRIER, DEFENSE_MODIFIER);
      if (entity instanceof Player player && tag.contains("SpiritCircuitBoostedFlySpeed")) {
         float boosted = tag.getFloat("SpiritCircuitBoostedFlySpeed");
         if (Math.abs(player.getAbilities().getFlyingSpeed() - boosted) < 1.0E-4F) {
            player.getAbilities().setFlyingSpeed(tag.getFloat("SpiritCircuitOriginalFlySpeed"));
            player.onUpdateAbilities();
         }
      }

      tag.remove("SpiritCircuitOriginalFlySpeed");
      tag.remove("SpiritCircuitBoostedFlySpeed");
   }

   private void releaseOverload(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      int heat = tag.getInt("SpiritCircuitHeat");
      if (heat <= 0) {
         this.displayActionBar(entity, Component.translatable("tennogamenolife.skill.spirit_circuit_overload.no_heat").withStyle(ChatFormatting.RED));
      } else if (!this.isOutOfEnergy(entity, instance, 0.0, 2000.0)) {
         this.disablePassive(instance, entity, false);
         double radius = 4.0 + heat / 10.0;
         float damage = 4.0F + heat * 0.4F;
         DamageSource source = this.createSource(instance, entity, TensuraDamageTypes.MAGIC_GENERIC, 0);

         for (LivingEntity target : entity.level()
            .getEntitiesOfClass(
               LivingEntity.class, entity.getBoundingBox().inflate(radius), targetx -> targetx.isAlive() && targetx != entity && !targetx.isAlliedTo(entity)
            )) {
            if (target.hurt(source, damage)) {
               Vec3 push = target.position().subtract(entity.position());
               if (push.lengthSqr() > 0.001) {
                  target.push(push.x / push.length(), 0.35, push.z / push.length());
                  target.hurtMarked = true;
               }
            }
         }

         if (entity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
               ParticleTypes.END_ROD, entity.getX(), entity.getY() + entity.getBbHeight() / 2.0, entity.getZ(), 80, radius / 4.0, 1.0, radius / 4.0, 0.15
            );
            serverLevel.sendParticles(
               ParticleTypes.ELECTRIC_SPARK,
               entity.getX(),
               entity.getY() + entity.getBbHeight() / 2.0,
               entity.getZ(),
               60,
               radius / 3.0,
               0.8,
               radius / 3.0,
               0.25
            );
         }

         entity.level().playSound(null, entity.blockPosition(), (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 1.5F, 1.2F);
         tag.putInt("SpiritCircuitHeat", 0);
         instance.setCoolDown(200, 0);
         instance.addMasteryPoint(entity);
         instance.markDirty();
      }
   }

   private void triggerBurnout(ManasSkillInstance instance, LivingEntity entity, int duration) {
      this.disablePassive(instance, entity, false);
      CompoundTag tag = instance.getOrCreateTag();
      tag.putLong("SpiritCircuitBurnoutUntil", entity.level().getGameTime() + duration);
      tag.putInt("SpiritCircuitHeat", 100);
      instance.setCoolDown(duration, 0);
      entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration, 1, false, true, true));
      entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, 0, false, true, true));
      this.displayActionBar(entity, Component.translatable("tennogamenolife.skill.spirit_circuit_overload.overheated").withStyle(ChatFormatting.RED));
      entity.level().playSound(null, entity.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 0.7F);
      instance.markDirty();
   }

   private void addModifier(LivingEntity entity, Holder<Attribute> attribute, ResourceLocation id, double amount, Operation operation) {
      AttributeInstance instance = entity.getAttribute(attribute);
      if (instance != null) {
         instance.addOrReplacePermanentModifier(new AttributeModifier(id, amount, operation));
      }
   }

   private void removeModifier(LivingEntity entity, Holder<Attribute> attribute, ResourceLocation id) {
      AttributeInstance instance = entity.getAttribute(attribute);
      if (instance != null) {
         instance.removeModifier(id);
      }
   }

   private boolean hasModifier(LivingEntity entity, Holder<Attribute> attribute, ResourceLocation id) {
      AttributeInstance instance = entity.getAttribute(attribute);
      return instance != null && instance.hasModifier(id);
   }

   private void displayActionBar(LivingEntity entity, Component message) {
      if (entity instanceof Player player) {
         player.displayClientMessage(message, true);
      } else {
         entity.sendSystemMessage(message);
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity owner, DamageSource source) {
      this.disablePassive(instance, owner, true);
      return true;
   }

   public void onRespawn(ManasSkillInstance instance, ServerPlayer owner, boolean conqueredEnd) {
      this.disablePassive(instance, owner, true);
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      this.disablePassive(instance, entity, true);
      super.onForgetSkill(instance, entity);
   }
}
