package twilightforest.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.EventHooks;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFSounds;
import twilightforest.util.ColorUtil;

public class UnstableIceCore extends BaseIceMob {
   private static final float EXPLOSION_RADIUS = 1.0F;

   public UnstableIceCore(EntityType<? extends UnstableIceCore> type, Level world) {
      super(type, world);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static Builder registerAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.23).add(Attributes.ATTACK_DAMAGE, 3.0);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.ICE_CORE_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.ICE_CORE_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.ICE_CORE_DEATH.get();
   }

   protected void tickDeath() {
      this.deathTime++;
      if (this.deathTime == 60) {
         if (!this.level().isClientSide()) {
            boolean mobGriefing = EventHooks.canEntityGrief(this.level(), this);
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, ExplosionInteraction.MOB);
            if (mobGriefing) {
               this.transformBlocks();
            }
         }

         this.deathTime = 19;
         super.tickDeath();
         this.deathTime = 60;
      }
   }

   private void transformBlocks() {
      int range = 4;

      for (int dx = -range; dx <= range; dx++) {
         for (int dy = -range; dy <= range; dy++) {
            for (int dz = -range; dz <= range; dz++) {
               double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
               float randRange = range + (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 2.0F;
               if (distance < randRange) {
                  this.transformBlock(this.blockPosition().offset(dx, dy, dz));
               }
            }
         }
      }
   }

   private void transformBlock(BlockPos pos) {
      BlockState state = this.level().getBlockState(pos);
      Block block = state.getBlock();
      if (block.getExplosionResistance() < 8.0F && state.getDestroySpeed(this.level(), pos) >= 0.0F) {
         int blockColor = state.getMapColor(this.level(), pos).col;
         if (this.shouldTransformGlass(state, pos)) {
            this.level().setBlockAndUpdate(pos, ColorUtil.STAINED_GLASS.getColor(getClosestDyeColor(blockColor)));
         } else if (this.shouldTransformClay(state, pos)) {
            this.level().setBlockAndUpdate(pos, ColorUtil.TERRACOTTA.getColor(getClosestDyeColor(blockColor)));
         }
      }
   }

   private boolean shouldTransformClay(BlockState state, BlockPos pos) {
      return !state.isAir() && state.isRedstoneConductor(this.level(), pos);
   }

   private boolean shouldTransformGlass(BlockState state, BlockPos pos) {
      return !state.isAir()
         && this.isBlockNormalBounds(state, pos)
         && (!state.isSolid() || state.is(BlockTags.LEAVES) || state.is(Blocks.ICE) || state.is(TFBlocks.AURORA_BLOCK));
   }

   private boolean isBlockNormalBounds(BlockState state, BlockPos pos) {
      return Block.isShapeFullBlock(state.getShape(this.level(), pos));
   }

   private static DyeColor getClosestDyeColor(int blockColor) {
      int red = blockColor >> 16 & 0xFF;
      int green = blockColor >> 8 & 0xFF;
      int blue = blockColor & 0xFF;
      DyeColor bestColor = DyeColor.WHITE;
      int bestDifference = 1024;

      for (DyeColor color : DyeColor.values()) {
         int iColor = color.getTextureDiffuseColor();
         int iRed = ARGB32.red(iColor);
         int iGreen = ARGB32.green(iColor);
         int iBlue = ARGB32.blue(iColor);
         int difference = Math.abs(red - iRed) + Math.abs(green - iGreen) + Math.abs(blue - iBlue);
         if (difference < bestDifference) {
            bestColor = color;
            bestDifference = difference;
         }
      }

      return bestColor;
   }

   public int getMaxSpawnClusterSize() {
      return 2;
   }
}
