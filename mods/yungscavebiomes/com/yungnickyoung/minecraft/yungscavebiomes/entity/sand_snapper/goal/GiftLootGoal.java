package com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.goal;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.LootTableModule;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class GiftLootGoal extends Goal {
   private static final double MAX_ALLOWED_SQR_DISTANCE_TO_TARGET = 100.0;
   private final SandSnapperEntity sandSnapper;
   @Nullable
   private final PathNavigation pathNav;
   private Vec3 targetPos;
   private final int diggingDuration;
   private int diggingTimer;
   private final int xzRange;
   private final int yRange;
   private boolean isDone;

   public GiftLootGoal(SandSnapperEntity sandSnapper, int xzRange, int yRange, int diggingDuration) {
      this.sandSnapper = sandSnapper;
      this.pathNav = sandSnapper.getNavigation();
      this.diggingDuration = diggingDuration;
      this.xzRange = xzRange;
      this.yRange = yRange;
      this.setFlags(EnumSet.of(Flag.MOVE));
   }

   public boolean canUse() {
      if (this.sandSnapper.searchingForGift && !this.isDone) {
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
         this.isDone = true;
         return false;
      } else {
         return this.diggingTimer > 0 || this.sandSnapper.distanceToSqr(this.targetPos) < 100.0;
      }
   }

   public void start() {
      this.isDone = false;
   }

   public void stop() {
      this.sandSnapper.setForceSpawnDigParticles(false);
      this.diggingTimer = 0;
      this.sandSnapper.searchingForGift = false;
      this.isDone = false;
   }

   public void tick() {
      if (!this.isDone) {
         if (this.diggingTimer > 0) {
            this.diggingTimer--;
            if (this.diggingTimer == 0) {
               LootTable lootTable = this.sandSnapper.getServer().reloadableRegistries().getLootTable(LootTableModule.SAND_SNAPPER_LOOT);
               LootParams lootParams = new Builder((ServerLevel)this.sandSnapper.level())
                  .withParameter(LootContextParams.ORIGIN, this.sandSnapper.position())
                  .withParameter(LootContextParams.THIS_ENTITY, this.sandSnapper)
                  .create(LootContextParamSets.GIFT);
               ObjectArrayList<ItemStack> lootChoices = lootTable.getRandomItems(lootParams, new Random().nextLong());
               ItemStack loot = lootChoices.isEmpty() ? ItemStack.EMPTY : (ItemStack)lootChoices.getFirst();
               this.spawnLoot(loot);
               this.isDone = true;
            } else if (this.diggingTimer == this.diggingDuration / 2) {
               this.sandSnapper.setForceSpawnDigParticles(true);
            }
         } else {
            double sqrDist = this.sandSnapper.position().distanceToSqr(this.targetPos);
            if (!this.sandSnapper.blockPosition().equals(BlockPos.containing(this.targetPos))) {
               if (sqrDist > 4.0 && this.pathNav != null) {
                  this.pathNav.moveTo(this.targetPos.x(), this.targetPos.y(), this.targetPos.z(), 1.0);
               } else {
                  this.sandSnapper.getMoveControl().setWantedPosition(this.targetPos.x(), this.targetPos.y(), this.targetPos.z(), 1.0);
               }
            } else {
               this.diggingTimer = this.diggingDuration;
               if (this.pathNav != null) {
                  this.pathNav.stop();
               }

               if (!this.sandSnapper.isSubmerged() && !this.sandSnapper.isDiggingDown() && !this.sandSnapper.isDiving() && this.sandSnapper.canSubmerge(true)) {
                  this.sandSnapper.setSubmerged(true);
               }
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
      BlockState blockState = this.sandSnapper.level().getBlockState(blockPos);
      return blockState.is(BlockModule.SAND_SNAPPER_BLOCKS) && this.sandSnapper.level().getBlockState(blockPos.above()).isAir();
   }

   private void spawnLoot(ItemStack loot) {
      if (!loot.isEmpty()) {
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
         ItemEntity itemEntity = new ItemEntity(this.sandSnapper.level(), x, y, z, loot, dx, dy, dz);
         itemEntity.setDefaultPickUpDelay();
         this.sandSnapper.level().addFreshEntity(itemEntity);
      }
   }
}
