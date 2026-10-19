package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimatedObject;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.ModelSystem;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.entity_model_system.EntityModelSystem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public abstract class FDMob extends Mob implements AnimatedObject {
   private EntityModelSystem<?> modelSystem = EntityModelSystem.create(this);

   public FDMob(EntityType<? extends Mob> type, Level level) {
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
}
