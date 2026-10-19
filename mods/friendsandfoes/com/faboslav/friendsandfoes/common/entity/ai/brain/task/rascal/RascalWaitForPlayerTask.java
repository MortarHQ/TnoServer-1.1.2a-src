package com.faboslav.friendsandfoes.common.entity.ai.brain.task.rascal;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.advancements.criterion.CompleteHideAndSeekGameCriterion;
import com.faboslav.friendsandfoes.common.entity.RascalEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.RascalBrain;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesCriterias;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.util.MovementUtil;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.BundleContents.Mutable;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public final class RascalWaitForPlayerTask extends Behavior<RascalEntity> {
   private static final int NOD_DURATION = 90;
   public static final float NOD_RANGE = 5.0F;
   private int nodTicks;
   private LivingEntity nearestTarget;

   public RascalWaitForPlayerTask() {
      super(
         ImmutableMap.of(
            MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
            MemoryStatus.REGISTERED,
            MemoryModuleType.INTERACTION_TARGET,
            MemoryStatus.VALUE_PRESENT,
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.RASCAL_NOD_COOLDOWN.get(),
            MemoryStatus.VALUE_ABSENT
         ),
         90
      );
   }

   protected boolean checkExtraStartConditions(ServerLevel world, RascalEntity rascal) {
      if (rascal.hasCustomName()) {
         return false;
      }

      LivingEntity nearestTarget = (LivingEntity)rascal.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER).orElse(null);
      if (nearestTarget == null) {
         nearestTarget = (LivingEntity)rascal.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).orElse(null);
      }

      if (nearestTarget != null
         && !(rascal.distanceTo(nearestTarget) > 5.0F)
         && nearestTarget.isAlive()
         && (!(nearestTarget instanceof Player) || !nearestTarget.isSpectator() && !((Player)nearestTarget).isCreative())) {
         this.nearestTarget = nearestTarget;
         return true;
      } else {
         return false;
      }
   }

   protected void start(ServerLevel world, RascalEntity rascal, long time) {
      MovementUtil.stopMovement(rascal);
      BehaviorUtils.lookAtEntity(rascal, this.nearestTarget);
      rascal.getLookControl().setLookAt(this.nearestTarget);
      rascal.getLookControl().tick();
      this.nodTicks = 0;
      rascal.addToCaughtCount();
      rascal.disableAmbientSounds();
   }

   protected boolean canStillUse(ServerLevel world, RascalEntity rascal, long time) {
      return this.nodTicks <= 90;
   }

   protected void tick(ServerLevel world, RascalEntity rascal, long time) {
      if (this.nodTicks == 20) {
         rascal.spawnHappyParticles();
         rascal.startNodAnimation();
         rascal.getLookControl().setLookAt(this.nearestTarget);
      }

      if (this.nodTicks == 40 && rascal.shouldGiveReward()) {
         rascal.startGiveRewardAnimation();
      }

      if (this.nodTicks == 62 && rascal.shouldGiveReward()) {
         Vec3 targetPos = this.nearestTarget.position().add(0.0, 1.0, 0.0);
         LootTable rascalGoodItemsLootTable = world.getServer()
            .reloadableRegistries()
            .getLootTable(ResourceKey.create(Registries.LOOT_TABLE, FriendsAndFoes.makeID("rewards/rascal_good_reward")));
         LootParams lootContextParameterSet = new Builder(world)
            .withParameter(LootContextParams.ORIGIN, targetPos)
            .withParameter(LootContextParams.THIS_ENTITY, this.nearestTarget)
            .create(LootContextParamSets.GIFT);
         ObjectArrayList<ItemStack> rascalGoodRewards = rascalGoodItemsLootTable.getRandomItems(lootContextParameterSet);
         ObjectListIterator var9 = rascalGoodRewards.iterator();

         while (var9.hasNext()) {
            ItemStack rascalReward = (ItemStack)var9.next();
            ItemStack thrownStack;
            if (FriendsAndFoes.getConfig().rascalGiveRewardInBundle) {
               ItemStack bundleItemStack = Items.BUNDLE.getDefaultInstance();
               BundleContents bundleContentsComponent = (BundleContents)bundleItemStack.get(DataComponents.BUNDLE_CONTENTS);
               if (bundleContentsComponent == null) {
                  break;
               }

               Mutable builder = new Mutable(bundleContentsComponent);
               builder.tryInsert(rascalReward);
               bundleItemStack.set(DataComponents.BUNDLE_CONTENTS, builder.toImmutable());
               thrownStack = bundleItemStack;
            } else {
               thrownStack = rascalReward;
            }

            BehaviorUtils.throwItem(rascal, thrownStack, this.nearestTarget.position().add(0.0, 1.0, 0.0));
            ((CompleteHideAndSeekGameCriterion)FriendsAndFoesCriterias.COMPLETE_HIDE_AND_SEEK_GAME.get())
               .trigger((ServerPlayer)this.nearestTarget, rascal, thrownStack);
         }
      }

      this.nodTicks++;
   }

   protected void stop(ServerLevel world, RascalEntity rascal, long time) {
      if (!rascal.hasCustomName()) {
         rascal.spawnCloudParticles();
         rascal.playDisappearSound();
         if (rascal.shouldGiveReward()) {
            rascal.discard();
            return;
         }

         rascal.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 200));
         this.tryToTeleport(world, rascal);
      }

      rascal.setEntityPose(FriendsAndFoesEntityPose.IDLE);
      RascalBrain.setNodCooldown(rascal);
      rascal.enableAmbientSounds();
   }

   private void tryToTeleport(ServerLevel world, RascalEntity rascal) {
      StructureManager structureAccessor = world.structureManager();
      int worldBottomY = world.getMinBuildHeight();
      int logicalHeight = world.getLogicalHeight();

      for (int i = 0; i < 64; i++) {
         double x = rascal.getX() + (rascal.getRandom().nextDouble() - 0.5) * 16.0;
         double y = Mth.clamp(rascal.getY() + (rascal.getRandom().nextInt(8) - 4), worldBottomY, worldBottomY + logicalHeight - 1);
         double z = rascal.getZ() + (rascal.getRandom().nextDouble() - 0.5) * 16.0;
         if (structureAccessor.getStructureWithPieceAt(new BlockPos((int)x, (int)y, (int)z), StructureTags.MINESHAFT).isValid()) {
            if (rascal.isPassenger()) {
               rascal.stopRiding();
            }

            boolean teleportResult = rascal.randomTeleport(x, y, z, false);
            if (teleportResult && rascal.distanceTo(this.nearestTarget) > 10.0) {
               return;
            }
         }
      }
   }
}
