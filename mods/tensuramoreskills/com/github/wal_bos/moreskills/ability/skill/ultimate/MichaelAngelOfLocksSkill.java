package com.github.wal_bos.moreskills.ability.skill.ultimate;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.michael.MichaelActions;
import com.github.wal_bos.moreskills.handler.michael.MichaelPassives;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.subclass.ISpatialStorage;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class MichaelAngelOfLocksSkill extends Skill implements ISpatialStorage {
   private static final String STORAGE_TAG = "SpatialStorage";
   private static final MoreSkillsConfigs.MichaelAngelOfLocksConfig CONFIG = MoreSkillsConfigs.GRAND.michaelAngelOfLocks;

   public MichaelAngelOfLocksSkill() {
      super(SkillType.ULTIMATE);
      MichaelPassives.init();
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/michael_angel_of_locks.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)CONFIG.acquirementMagiculeCost.get();
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return (Integer)CONFIG.acquirementMastery.get();
   }

   public int getMaxMastery() {
      return (Integer)CONFIG.maxMastery.get();
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return SkillObtainmentHelper.canAcquire(
         entity, (Double)CONFIG.acquirementMagiculeCost.get(), (String)CONFIG.requiredSkillId.get(), (Boolean)CONFIG.requiredSkillMustBeMastered.get()
      );
   }

   public boolean canBeToggled(@NotNull ManasSkillInstance instance, @NotNull LivingEntity living) {
      return true;
   }

   public int getModes(ManasSkillInstance instance) {
      return MichaelActions.getModes(instance);
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return MichaelActions.nextMode(entity, instance, mode, reverse);
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return MichaelActions.getModeId(instance, mode, super.getModeId(instance, mode));
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return MichaelActions.getMagiculeCost(entity, instance, mode);
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled() || MichaelPassives.hasActiveState(entity) || MichaelActions.hasActiveState(entity);
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      MichaelPassives.onTick(instance, entity);
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      MichaelPassives.onToggleOn(instance, entity);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      MichaelPassives.onToggleOff(instance, entity);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      MichaelActions.onPressed(instance, entity, keyNumber, mode);
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      return MichaelActions.onHeld(instance, entity, heldTicks, mode);
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      MichaelActions.onRelease(instance, entity, heldTicks, keyNumber, mode);
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      MichaelPassives.onDamageEntity(instance, attacker, target, source, amount);
      return true;
   }

   @NotNull
   public SpatialStorageContainer getSpatialStorage(ManasSkillInstance instance, Provider provider) {
      SpatialStorageContainer container = new SpatialStorageContainer((Integer)CONFIG.spatialStorageSlots.get(), (Integer)CONFIG.spatialStorageStack.get());
      container.fromTag(instance.getOrCreateTag().getList("SpatialStorage", 10), provider);
      return container;
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      MichaelActions.cleanup(entity);
      MichaelPassives.onToggleOff(instance, entity);
      super.onForgetSkill(instance, entity);
   }
}
