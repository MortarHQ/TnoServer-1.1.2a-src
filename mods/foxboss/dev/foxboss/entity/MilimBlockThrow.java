package dev.foxboss.entity;

import dev.foxboss.registry.FoxEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

final class MilimBlockThrow {
   private final MilimEntity milim;
   private int cooldown = 80;
   private BlockPos source;
   private BlockState selected;
   private ThrownBlockEntity held;

   MilimBlockThrow(MilimEntity milim) {
      this.milim = milim;
   }

   void tick() {
      if (this.cooldown > 0) {
         this.cooldown--;
      }

      if ((!this.milim.isAlive() || this.milim.action() != 17) && this.held != null) {
         this.cancel();
      }
   }

   boolean tryStart(LivingEntity target) {
      if (this.cooldown <= 0
         && !this.milim.isNoAi()
         && !this.milim.isOrderedToSit()
         && this.milim.onGround()
         && !this.milim.isDemo()
         && !this.milim.isDebugAnimation()
         && this.milim.hasLineOfSight(target)) {
         double distance = this.milim.distanceToSqr(target);
         if (!(distance < 64.0) && !(distance > 4096.0)) {
            Vec3 direction = target.position().subtract(this.milim.position()).multiply(1.0, 0.0, 1.0).normalize();
            BlockPos pos = BlockPos.containing(this.milim.position().add(direction.scale(1.0)).add(0.0, -0.2, 0.0));
            BlockState state = this.milim.level().getBlockState(pos);
            if (!canPickUp(this.milim, pos, state)) {
               this.cooldown = 40;
               return false;
            } else {
               this.source = pos;
               this.selected = state;
               this.cooldown = 160;
               float yaw = (float)(Math.atan2(direction.z, direction.x) * 180.0 / Math.PI) - 90.0F;
               this.milim.setYRot(yaw);
               this.milim.setYHeadRot(yaw);
               this.milim.setYBodyRot(yaw);
               this.milim.startAction(17);
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   static boolean canPickUp(MilimEntity milim, BlockPos pos, BlockState state) {
      Level level = milim.level();
      float hardness = state.getDestroySpeed(level, pos);
      return !state.isAir()
         && !state.hasBlockEntity()
         && state.getFluidState().isEmpty()
         && !state.getCollisionShape(level, pos).isEmpty()
         && Float.isFinite(hardness)
         && hardness >= 0.0F
         && level.hasChunkAt(pos)
         && level.getWorldBorder().isWithinBounds(pos)
         && EventHooks.canEntityGrief(level, milim)
         && state.canEntityDestroy(level, pos, milim);
   }

   void animateTick(int tick) {
      LivingEntity target = this.milim.getTarget();
      if (this.milim.isAlive() && !this.milim.isNoAi() && !this.milim.isOrderedToSit() && target != null && target.isAlive() && this.milim.canAttack(target)) {
         if (tick == 8 && this.source != null) {
            ServerLevel level = (ServerLevel)this.milim.level();
            if (this.milim.position().distanceToSqr(Vec3.atCenterOf(this.source)) > 6.25
               || !level.getBlockState(this.source).equals(this.selected)
               || !canPickUp(this.milim, this.source, this.selected)
               || !EventHooks.onEntityDestroyBlock(this.milim, this.source, this.selected)) {
               return;
            }

            ThrownBlockEntity prepared = ThrownBlockEntity.prepare(
               level, this.milim, this.selected, this.source, this.releasePoint(), target.getBoundingBox().getCenter()
            );
            if (prepared != null && level.removeBlock(this.source, false)) {
               this.held = prepared;
               this.milim.setCarriedBlockState(this.selected);
            }
         }

         if (tick == 20 && this.held != null) {
            Vec3 from = this.releasePoint();
            if (this.milim.distanceToSqr(target) > 4096.0
               || !this.held.launchFrom(from, target.getBoundingBox().getCenter())
               || !this.milim.level().addFreshEntity(this.held)) {
               this.held.breakAt(from);
            }

            this.held = null;
            this.milim.setCarriedBlockState(Blocks.AIR.defaultBlockState());
         }
      } else {
         this.cancel();
      }
   }

   private Vec3 releasePoint() {
      return this.milim.position().add(0.0, 1.7 * this.milim.getScale(), 0.0).add(this.milim.forward().scale(this.milim.getScale()));
   }

   void cancel() {
      if (this.held != null) {
         this.held.breakAt(this.milim.position().add(0.0, 1.0, 0.0));
         this.held = null;
      }

      this.source = null;
      this.selected = null;
      this.milim.setCarriedBlockState(Blocks.AIR.defaultBlockState());
   }

   void save(CompoundTag tag) {
      tag.putInt("BlockThrowCooldown", this.cooldown);
      if (this.held != null) {
         tag.put("HeldThrowBlock", this.held.saveWithoutId(new CompoundTag()));
      }
   }

   void load(CompoundTag tag) {
      this.cooldown = tag.contains("BlockThrowCooldown") ? Math.max(0, tag.getInt("BlockThrowCooldown")) : 80;
      this.held = null;
      this.source = null;
      this.selected = null;
      if (tag.contains("HeldThrowBlock")) {
         this.held = (ThrownBlockEntity)((EntityType)FoxEntities.THROWN_BLOCK.get()).create(this.milim.level());
         if (this.held != null) {
            this.held.load(tag.getCompound("HeldThrowBlock"));
            this.held.setOwner(this.milim);
         }
      }
   }
}
