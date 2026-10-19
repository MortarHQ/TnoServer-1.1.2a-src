package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.BrushableBlockEntityAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BuryLootGoal extends Goal {
   private static final double MAX_ALLOWED_SQR_DISTANCE_TO_TARGET = 100.0;
   private final SandSnapperEntity sandSnapper;
   @Nullable
   private final PathNavigation pathNav;
   private Vec3 targetPos;
   private final int buryingDuration;
   private int buryingTimer;
   private final int xzRange;
   private final int yRange;
   private boolean isDone;

   public BuryLootGoal(SandSnapperEntity sandSnapper, int xzRange, int yRange, int buryingDuration) {
      this.sandSnapper = sandSnapper;
      this.pathNav = sandSnapper.getNavigation();
      this.buryingDuration = buryingDuration;
      this.xzRange = xzRange;
      this.yRange = yRange;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      if (!this.sandSnapper.carryingItem.isEmpty() && !this.isDone) {
         this.targetPos = this.findTargetPos();
         return this.targetPos != null;
      } else {
         return false;
      }
   }

   public boolean canContinueToUse() {
      if (this.isDone) {
         return false;
      } else if (!this.isBlockAValidTarget(BlockPos.containing(this.targetPos).below())) {
         this.popItem();
         this.isDone = true;
         return false;
      } else {
         return this.buryingTimer > 0 || this.sandSnapper.distanceToSqr(this.targetPos) < 100.0;
      }
   }

   public void start() {
      this.isDone = false;
   }

   public void stop() {
      this.sandSnapper.setForceSpawnDigParticles(false);
      this.buryingTimer = 0;
      this.isDone = false;
   }

   public void tick() {
      if (!this.isDone) {
         if (this.buryingTimer > 0) {
            this.buryingTimer--;
            if (this.buryingTimer == 0) {
               BlockState blockStateOn = this.sandSnapper.getBlockStateOn();
               if (blockStateOn.is(Blocks.SAND)) {
                  this.sandSnapper.level().broadcastEntityEvent(this.sandSnapper, (byte)8);
                  this.sandSnapper.level().setBlock(this.sandSnapper.getOnPos(), Blocks.SUSPICIOUS_SAND.defaultBlockState(), 3);
                  this.sandSnapper.level().getBlockEntity(this.sandSnapper.getOnPos(), BlockEntityType.BRUSHABLE_BLOCK).ifPresentOrElse(blockEntity -> {
                     ((BrushableBlockEntityAccessor)blockEntity).setItem(this.sandSnapper.carryingItem);
                     this.sandSnapper.carryingItem = ItemStack.EMPTY;
                  }, this::popItem);
               } else if (blockStateOn.is((Block)BlockModule.ANCIENT_SAND.get())) {
                  this.sandSnapper.level().broadcastEntityEvent(this.sandSnapper, (byte)9);
                  this.sandSnapper.level().setBlock(this.sandSnapper.getOnPos(), ((Block)BlockModule.SUSPICIOUS_ANCIENT_SAND.get()).defaultBlockState(), 3);
                  this.sandSnapper
                     .level()
                     .getBlockEntity(this.sandSnapper.getOnPos(), (BlockEntityType)EntityTypeModule.SUSPICIOUS_ANCIENT_SAND.get())
                     .ifPresentOrElse(blockEntity -> {
                        blockEntity.setItem(this.sandSnapper.carryingItem);
                        this.sandSnapper.carryingItem = ItemStack.EMPTY;
                     }, this::popItem);
               } else if (blockStateOn.is(Blocks.GRAVEL)) {
                  this.sandSnapper.level().broadcastEntityEvent(this.sandSnapper, (byte)10);
                  this.sandSnapper.level().setBlock(this.sandSnapper.getOnPos(), Blocks.SUSPICIOUS_GRAVEL.defaultBlockState(), 3);
                  this.sandSnapper.level().getBlockEntity(this.sandSnapper.getOnPos(), BlockEntityType.BRUSHABLE_BLOCK).ifPresentOrElse(blockEntity -> {
                     ((BrushableBlockEntityAccessor)blockEntity).setItem(this.sandSnapper.carryingItem);
                     this.sandSnapper.carryingItem = ItemStack.EMPTY;
                  }, this::popItem);
               } else {
                  this.popItem();
               }

               this.isDone = true;
            } else if (this.buryingTimer == this.buryingDuration / 2) {
               this.sandSnapper.setForceSpawnDigParticles(true);
            }
         } else if (!this.sandSnapper.blockPosition().equals(BlockPos.containing(this.targetPos))) {
            double sqrDist = this.sandSnapper.position().distanceToSqr(this.targetPos);
            if (sqrDist > 4.0 && this.pathNav != null) {
               this.pathNav.moveTo(this.targetPos.x(), this.targetPos.y(), this.targetPos.z(), 1.0);
            } else {
               this.sandSnapper.getMoveControl().setWantedPosition(this.targetPos.x(), this.targetPos.y(), this.targetPos.z(), 1.0);
            }
         } else {
            this.buryingTimer = this.buryingDuration;
            if (this.pathNav != null) {
               this.pathNav.stop();
            }

            if (!this.sandSnapper.isSubmerged() && !this.sandSnapper.isDiggingDown() && !this.sandSnapper.isDiving() && this.sandSnapper.canSubmerge(true)) {
               this.sandSnapper.setSubmerged(true);
            }
         }
      }
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   public boolean isInterruptable() {
      return false;
   }

   @Nullable
   private Vec3 findTargetPos() {
      return BlockPos.findClosestMatch(this.sandSnapper.getOnPos(), this.xzRange, this.yRange, this::isBlockAValidTarget)
         .<BlockPos>map(BlockPos::above)
         .map(pos -> new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5))
         .orElse(null);
   }

   private boolean isBlockAValidTarget(BlockPos blockPos) {
      Block block = this.sandSnapper.level().getBlockState(blockPos).getBlock();
      return (block == Blocks.SAND || block == BlockModule.ANCIENT_SAND.get() || block == Blocks.GRAVEL)
         && this.sandSnapper.level().getBlockState(blockPos.above()).isAir();
   }

   private void popItem() {
      if (!this.sandSnapper.carryingItem.isEmpty()) {
         BlockPos spawnPos = this.sandSnapper.blockPosition();
         double x = spawnPos.getX() + 0.5;
         double y = spawnPos.getY() + 0.5 - EntityType.ITEM.getHeight() / 2.0F;
         double z = spawnPos.getZ() + 0.5;
         RandomSource random = this.sandSnapper.getRandom();
         double xOffset = Mth.nextDouble(random, 0.0, 0.2);
         double yOffset = 0.2;
         double zOffset = Mth.nextDouble(random, 0.0, 0.2);
         double dx = Mth.nextDouble(random, 0.0, 0.05);
         double dy = 0.2;
         double dz = Mth.nextDouble(random, 0.0, 0.05);
         if (random.nextBoolean()) {
            xOffset *= -1.0;
            dx *= -1.0;
         }

         if (random.nextBoolean()) {
            zOffset *= -1.0;
            dz *= -1.0;
         }

         x += xOffset;
         y += yOffset;
         z += zOffset;
         ItemEntity itemEntity = new ItemEntity(this.sandSnapper.level(), x, y, z, this.sandSnapper.carryingItem, dx, dy, dz);
         itemEntity.setDefaultPickUpDelay();
         this.sandSnapper.level().addFreshEntity(itemEntity);
         this.sandSnapper.carryingItem = ItemStack.EMPTY;
      }
   }
}
