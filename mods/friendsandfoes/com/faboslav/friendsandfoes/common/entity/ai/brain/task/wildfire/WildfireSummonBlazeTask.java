package com.faboslav.friendsandfoes.common.entity.ai.brain.task.wildfire;

import com.faboslav.friendsandfoes.common.entity.BlazeEntityAccess;
import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.WildfireBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;

public final class WildfireSummonBlazeTask extends Behavior<WildfireEntity> {
   private LivingEntity attackTarget;
   private int summonedBlazesCount;
   private static final int SUMMON_BLAZES_DURATION = 20;
   public static final int MIN_BLAZES_TO_BE_SUMMONED = 1;
   private static final int MAX_BLAZES_TO_BE_SUMMONED = 2;

   public WildfireSummonBlazeTask() {
      super(
         ImmutableMap.of(
            MemoryModuleType.ATTACK_TARGET,
            MemoryStatus.VALUE_PRESENT,
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.WILDFIRE_SUMMON_BLAZE_COOLDOWN.get(),
            MemoryStatus.VALUE_ABSENT
         ),
         20
      );
   }

   protected boolean checkExtraStartConditions(ServerLevel world, WildfireEntity wildfire) {
      LivingEntity attackTarget = (LivingEntity)wildfire.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
      if (attackTarget != null
         && attackTarget.isAlive()
         && (!(attackTarget instanceof Player) || !attackTarget.isSpectator() && !((Player)attackTarget).isCreative())
         && wildfire.getSummonedBlazesCount() != 2) {
         this.attackTarget = attackTarget;
         return true;
      } else {
         WildfireBrain.setSummonBlazeCooldown(wildfire);
         return false;
      }
   }

   protected void start(ServerLevel world, WildfireEntity wildfire, long time) {
      wildfire.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      wildfire.getNavigation().stop();
      BehaviorUtils.lookAtEntity(wildfire, this.attackTarget);
      wildfire.getLookControl().setLookAt(this.attackTarget);
      WildfireBrain.setAttackTarget(wildfire, this.attackTarget);
      this.summonedBlazesCount = 0;
   }

   protected boolean canStillUse(ServerLevel world, WildfireEntity wildfire, long time) {
      return this.summonedBlazesCount == 0;
   }

   protected void tick(ServerLevel world, WildfireEntity wildfire, long time) {
      BehaviorUtils.lookAtEntity(wildfire, this.attackTarget);
      ServerLevel serverWorld = (ServerLevel)wildfire.level();
      int blazesToBeSummoned = Math.max(0, wildfire.getRandom().nextIntBetweenInclusive(1, 2) - wildfire.getSummonedBlazesCount());
      if (blazesToBeSummoned > 0) {
         wildfire.playSummonBlazeSound();
      }

      for (int i = 0; i < blazesToBeSummoned; i++) {
         BlockPos blockPos = wildfire.blockPosition().offset(-2 + wildfire.getRandom().nextInt(5), 1, -2 + wildfire.getRandom().nextInt(5));
         Blaze blaze = (Blaze)EntityType.BLAZE.create(serverWorld);
         VersionedEntity.moveTo(blaze, blockPos, 0.0F, 0.0F);
         blaze.setTarget(this.attackTarget);
         ((BlazeEntityAccess)blaze).friendsandfoes_setWildfire(wildfire);
         blaze.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(blockPos), VersionedEntitySpawnReason.MOB_SUMMONED, null);
         serverWorld.addFreshEntityWithPassengers(blaze);
         this.summonedBlazesCount++;
         wildfire.setSummonedBlazesCount(wildfire.getSummonedBlazesCount() + 1);
      }
   }

   protected void stop(ServerLevel world, WildfireEntity wildfire, long time) {
      WildfireBrain.setSummonBlazeCooldown(wildfire);
   }
}
