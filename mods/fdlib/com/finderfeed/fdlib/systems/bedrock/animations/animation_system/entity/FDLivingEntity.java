package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimatedObject;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.ModelSystem;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.entity_model_system.EntityModelSystem;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class FDLivingEntity extends LivingEntity implements AnimatedObject {
   private static final List<ItemStack> DUMMY_LIST = List.of();
   public EntityModelSystem<?> modelSystem = EntityModelSystem.create(this);

   protected FDLivingEntity(EntityType<? extends LivingEntity> type, Level level) {
      super(type, level);
   }

   public void tick() {
      super.tick();
      this.tickModelSystem();
   }

   @Override
   public ModelSystem getModelSystem() {
      return this.modelSystem;
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.modelSystem.asServerside().syncToPlayer(player);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      this.modelSystem.saveAttachments(this.level().registryAccess(), tag);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.modelSystem.loadAttachments(this.level().registryAccess(), tag);
   }

   public Iterable<ItemStack> getArmorSlots() {
      return DUMMY_LIST;
   }

   public ItemStack getItemBySlot(EquipmentSlot p_21127_) {
      return ItemStack.EMPTY;
   }

   public void setItemSlot(EquipmentSlot p_21036_, ItemStack p_21037_) {
   }

   public HumanoidArm getMainArm() {
      return HumanoidArm.LEFT;
   }
}
