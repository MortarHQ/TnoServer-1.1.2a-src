package com.github.L_Ender.cataclysm.entity.Pet;

import com.github.L_Ender.cataclysm.entity.etc.IFollower;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AnimationPet extends TamableAnimal implements IFollower {
   private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(AnimationPet.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(AnimationPet.class, EntityDataSerializers.INT);
   private static final ResourceLocation MOB_HEALTH_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("cataclysm", "pet_health");
   private static final ResourceLocation MOB_DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("cataclysm", "pet_damage");

   public AnimationPet(EntityType entity, Level world) {
      super(entity, world);
   }

   protected void defineSynchedData(Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(COMMAND, 0);
      p_326229_.define(SITTING, false);
   }

   public int getCommand() {
      return (Integer)this.entityData.get(COMMAND);
   }

   public void setCommand(int command) {
      this.entityData.set(COMMAND, command);
   }

   public boolean isSitting() {
      return (Boolean)this.entityData.get(SITTING);
   }

   public void setOrderedToSit(boolean sit) {
      this.entityData.set(SITTING, sit);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("CmPetSitting", this.isSitting());
      compound.putInt("Command", this.getCommand());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setOrderedToSit(compound.getBoolean("CmPetSitting"));
      this.setCommand(compound.getInt("Command"));
   }

   public void setConfigattribute(LivingEntity entity, double hpconfig, double dmgconfig) {
      AttributeInstance maxHealthAttr = entity.getAttribute(Attributes.MAX_HEALTH);
      if (maxHealthAttr != null) {
         double difference = maxHealthAttr.getBaseValue() * hpconfig - maxHealthAttr.getBaseValue();
         maxHealthAttr.addTransientModifier(new AttributeModifier(MOB_HEALTH_MODIFIER_ID, difference, Operation.ADD_VALUE));
         entity.setHealth(entity.getMaxHealth());
      }

      AttributeInstance attackDamageAttr = entity.getAttribute(Attributes.ATTACK_DAMAGE);
      if (attackDamageAttr != null) {
         double difference = attackDamageAttr.getBaseValue() * dmgconfig - attackDamageAttr.getBaseValue();
         attackDamageAttr.addTransientModifier(new AttributeModifier(MOB_DAMAGE_MODIFIER_ID, difference, Operation.ADD_VALUE));
      }
   }

   public boolean removeWhenFarAway(double p_21542_) {
      return false;
   }

   public boolean isFood(ItemStack p_27600_) {
      return false;
   }

   public void circleEntity(LivingEntity target, float radius, float speed, boolean direction, int circleFrame, float offset, float moveSpeedMultiplier) {
      int directionInt = direction ? 1 : -1;
      double t = directionInt * circleFrame * 0.5 * speed / radius + offset;
      Vec3 movePos = target.position().add(radius * Math.cos(t), 0.0, radius * Math.sin(t));
      this.getNavigation().moveTo(movePos.x, movePos.y, movePos.z, speed * moveSpeedMultiplier);
   }

   @Override
   public boolean shouldFollow() {
      return false;
   }

   public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
      return null;
   }
}
