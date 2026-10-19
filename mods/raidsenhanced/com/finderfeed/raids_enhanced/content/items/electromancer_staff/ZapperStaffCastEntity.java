package com.finderfeed.raids_enhanced.content.items.electromancer_staff;

import com.finderfeed.raids_enhanced.content.entities.vertical_lightning_strike.VerticalLightningStrikeAttack;
import com.finderfeed.raids_enhanced.content.util.HorizontalCircleRandomDirections;
import com.finderfeed.raids_enhanced.init.REEntities;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ZapperStaffCastEntity extends Entity {
   public UUID owner;
   public float ownerRotation;

   public static void summon(LivingEntity owner, Vec3 pos) {
      ZapperStaffCastEntity staff = new ZapperStaffCastEntity(REEntities.ENGINEER_STAFF_CAST_ENTTITY.get(), owner.level());
      staff.setPos(pos);
      staff.owner = owner.getUUID();
      staff.ownerRotation = (float)Math.toRadians(-owner.getYRot() + 180.0F);
      owner.level().addFreshEntity(staff);
   }

   public ZapperStaffCastEntity(EntityType<?> type, Level level) {
      super(type, level);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (this.owner == null) {
            return;
         }

         if (((ServerLevel)this.level()).getEntity(this.owner) instanceof LivingEntity livingEntity) {
            if (this.tickCount == 1) {
               this.spawnLightningAttacksAround(livingEntity, 4.0F, 4, this.ownerRotation);
            } else if (this.tickCount == 6) {
               this.spawnLightningAttacksAround(livingEntity, 8.0F, 8, this.ownerRotation + (float) (Math.PI / 2));
            } else if (this.tickCount == 11) {
               this.spawnLightningAttacksAround(livingEntity, 12.0F, 12, this.ownerRotation);
            } else if (this.tickCount == 16) {
               this.spawnLightningAttacksAround(livingEntity, 16.0F, 16, this.ownerRotation + (float) (Math.PI / 2));
            }
         } else {
            this.remove(RemovalReason.DISCARDED);
         }

         if (this.tickCount > 17) {
            this.remove(RemovalReason.DISCARDED);
         }
      }
   }

   public void spawnLightningAttacksAround(LivingEntity owner, float distance, int lightningsCount, float angle) {
      for (Vec3 dir : new HorizontalCircleRandomDirections(this.random, lightningsCount, 0.0F)) {
         Vec3 realDir = dir.yRot(angle).scale(distance);
         BlockPos spawnPosCandidate = BlockPos.containing(this.position().add(realDir));
         this.trySpawnLightning(owner, spawnPosCandidate);
      }
   }

   private void trySpawnLightning(LivingEntity owner, BlockPos candidate) {
      if (this.isPosValidForLightning(owner, candidate)) {
         this.spawnLighting(owner, candidate);
      } else {
         for (int i = 1; i < 10; i++) {
            if (this.isPosValidForLightning(owner, candidate.below(i))) {
               this.spawnLighting(owner, candidate.below(i));
               return;
            }
         }

         for (int i = 1; i < 10; i++) {
            if (this.isPosValidForLightning(owner, candidate.above(i))) {
               this.spawnLighting(owner, candidate.above(i));
               return;
            }
         }
      }
   }

   private void spawnLighting(LivingEntity owner, BlockPos blockPos) {
      Vec3 pos = blockPos.getCenter();
      VerticalLightningStrikeAttack.summon(owner, new Vec3(pos.x, Math.floor(pos.y), pos.z));
   }

   private boolean isPosValidForLightning(LivingEntity owner, BlockPos blockPos) {
      BlockState state = this.level().getBlockState(blockPos);
      BlockState stateBelow = this.level().getBlockState(blockPos.below());
      return state.getCollisionShape(this.level(), blockPos).isEmpty() && !stateBelow.getCollisionShape(this.level(), blockPos.below()).isEmpty();
   }

   protected void defineSynchedData(Builder p_326003_) {
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      if (tag.contains("owner")) {
         this.owner = tag.getUUID("owner");
      }
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      if (this.owner != null) {
         tag.putUUID("owner", this.owner);
      }
   }
}
