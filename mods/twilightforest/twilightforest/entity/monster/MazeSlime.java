package twilightforest.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.TwilightForestMod;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFSounds;

public class MazeSlime extends Slime {
   private static final AttributeModifier DOUBLE_HEALTH = new AttributeModifier(TwilightForestMod.prefix("double_health"), 2.0, Operation.ADD_MULTIPLIED_BASE);

   public MazeSlime(EntityType<? extends MazeSlime> type, Level world) {
      super(type, world);
   }

   public void setSize(int size, boolean resetHealth) {
      super.setSize(size, resetHealth);
      this.getAttribute(Attributes.MAX_HEALTH).addOrReplacePermanentModifier(DOUBLE_HEALTH);
      this.setHealth(this.getMaxHealth());
      this.xpReward = size + 3;
   }

   public static boolean getCanSpawnHere(EntityType<MazeSlime> entity, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource random) {
      return world.getDifficulty() != Difficulty.PEACEFUL
         && checkMobSpawnRules(entity, world, reason, pos, random)
         && Monster.isDarkEnoughToSpawn(world, pos, random);
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return this.isTiny() ? (SoundEvent)TFSounds.MAZE_SLIME_HURT_SMALL.get() : (SoundEvent)TFSounds.MAZE_SLIME_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return this.isTiny() ? (SoundEvent)TFSounds.MAZE_SLIME_DEATH_SMALL.get() : (SoundEvent)TFSounds.MAZE_SLIME_DEATH.get();
   }

   protected SoundEvent getSquishSound() {
      return this.isTiny() ? (SoundEvent)TFSounds.MAZE_SLIME_SQUISH_SMALL.get() : (SoundEvent)TFSounds.MAZE_SLIME_SQUISH.get();
   }

   protected SoundEvent getJumpSound() {
      return this.isTiny() ? (SoundEvent)TFSounds.MAZE_SLIME_SQUISH_SMALL.get() : (SoundEvent)TFSounds.MAZE_SLIME_SQUISH.get();
   }

   protected boolean isDealsDamage() {
      return true;
   }

   protected boolean spawnCustomParticles() {
      int i = this.getSize();

      for (int j = 0; j < i * 8; j++) {
         float f = this.getRandom().nextFloat() * (float) (Math.PI * 2);
         float f1 = this.getRandom().nextFloat() * 0.5F + 0.5F;
         float f2 = Mth.sin(f) * i * 0.5F * f1;
         float f3 = Mth.cos(f) * i * 0.5F * f1;
         double d0 = this.getX() + f2;
         double d1 = this.getZ() + f3;
         BlockState state = ((Block)TFBlocks.MAZESTONE_BRICK.get()).defaultBlockState();
         this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), d0, this.getBoundingBox().minY, d1, 0.0, 0.0, 0.0);
      }

      return true;
   }

   protected float getSoundVolume() {
      return 0.1F * this.getSize();
   }
}
