package twilightforest.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStructures;
import twilightforest.util.landmarks.LegacyLandmarkPlacements;

public class HedgeSpider extends Spider {
   public HedgeSpider(EntityType<? extends HedgeSpider> type, Level world) {
      super(type, world);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.availableGoals.removeIf(t -> t.getGoal() instanceof MeleeAttackGoal);
      this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, true));
      this.targetSelector.availableGoals.removeIf(t -> t.getPriority() == 2 && t.getGoal() instanceof NearestAttackableTargetGoal);
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static boolean isValidLightLevel(ServerLevelAccessor accessor, BlockPos pos, RandomSource random) {
      int chunkX = Mth.floor(pos.getX()) >> 4;
      int chunkZ = Mth.floor(pos.getZ()) >> 4;
      return LegacyLandmarkPlacements.pickLandmarkForChunk(chunkX, chunkZ, accessor) == TFStructures.HEDGE_MAZE
         || Monster.isDarkEnoughToSpawn(accessor, pos, random);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.HEDGE_SPIDER_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.HEDGE_SPIDER_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.HEDGE_SPIDER_DEATH.get();
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound((SoundEvent)TFSounds.HEDGE_SPIDER_STEP.get(), 0.15F, 1.0F);
   }

   public static boolean canSpawn(EntityType<HedgeSpider> type, ServerLevelAccessor accessor, MobSpawnType reason, BlockPos pos, RandomSource random) {
      return accessor.getDifficulty() != Difficulty.PEACEFUL && isValidLightLevel(accessor, pos, random);
   }
}
