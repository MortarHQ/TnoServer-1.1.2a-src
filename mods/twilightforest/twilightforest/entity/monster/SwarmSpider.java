package twilightforest.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStructures;
import twilightforest.util.landmarks.LegacyLandmarkPlacements;

public class SwarmSpider extends Spider {
   public SwarmSpider(EntityType<? extends SwarmSpider> type, Level world) {
      super(type, world);
      this.xpReward = 2;
   }

   public static Builder registerAttributes() {
      return Spider.createAttributes().add(Attributes.MAX_HEALTH, 3.0).add(Attributes.ATTACK_DAMAGE, 1.0);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.availableGoals.removeIf(t -> t.getGoal() instanceof MeleeAttackGoal);
      this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, true));
      this.targetSelector.availableGoals.removeIf(t -> t.getPriority() == 2 && t.getGoal() instanceof NearestAttackableTargetGoal);
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.SWARM_SPIDER_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)TFSounds.SWARM_SPIDER_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.SWARM_SPIDER_DEATH.get();
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound((SoundEvent)TFSounds.SWARM_SPIDER_STEP.get(), 0.15F, 1.0F);
   }

   public boolean doHurtTarget(Entity entity) {
      return this.getRandom().nextInt(4) == 0 && super.doHurtTarget(entity);
   }

   public EntityType<? extends SwarmSpider> getReinforcementType() {
      return (EntityType<? extends SwarmSpider>)TFEntities.SWARM_SPIDER.get();
   }

   public static boolean getCanSpawnHere(
      EntityType<? extends SwarmSpider> entity, ServerLevelAccessor accessor, MobSpawnType reason, BlockPos pos, RandomSource random
   ) {
      return accessor.getDifficulty() != Difficulty.PEACEFUL
         && isValidLightLevel(accessor, pos, random)
         && checkMobSpawnRules(entity, accessor, reason, pos, random);
   }

   public static boolean isValidLightLevel(ServerLevelAccessor accessor, BlockPos pos, RandomSource random) {
      int chunkX = Mth.floor(pos.getX()) >> 4;
      int chunkZ = Mth.floor(pos.getZ()) >> 4;
      return LegacyLandmarkPlacements.pickLandmarkForChunk(chunkX, chunkZ, accessor) == TFStructures.HEDGE_MAZE
         || Monster.isDarkEnoughToSpawn(accessor, pos, random);
   }

   public float getVoicePitch() {
      return (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.5F;
   }

   public int getMaxSpawnClusterSize() {
      return 6;
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData data) {
      data = super.finalizeSpawn(accessor, difficulty, reason, data);
      this.summonJockey(accessor, difficulty);
      return data;
   }

   public void summonJockey(ServerLevelAccessor accessor, DifficultyInstance difficulty) {
      if (this.getFirstPassenger() != null || accessor.getRandom().nextInt(200) == 0) {
         SkeletonDruid druid = (SkeletonDruid)((EntityType)TFEntities.SKELETON_DRUID.get()).create(this.level());
         if (druid != null) {
            druid.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
            druid.setBaby(true);
            EventHooks.finalizeMobSpawn(druid, accessor, difficulty, MobSpawnType.JOCKEY, null);
            if (this.hasPassenger(e -> true)) {
               this.ejectPassengers();
            }

            druid.startRiding(this);
         }
      }
   }
}
