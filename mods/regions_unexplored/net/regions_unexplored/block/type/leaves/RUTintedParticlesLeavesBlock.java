package net.regions_unexplored.block.type.leaves;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.regions_unexplored.block.BlockFactory;
import net.regions_unexplored.config.RUConfigHandler;
import net.regions_unexplored.registry.RUParticleTypes;

public class RUTintedParticlesLeavesBlock extends LeavesBlock {
   public static final float DEFAULT_PARTICLE_CHANCE = 0.025F;
   private final Supplier<ParticleType<ColorParticleOption>> particle;
   private final RUTintedParticlesLeavesBlock.TintGetter tintGetter;
   private final float particleChance;

   public RUTintedParticlesLeavesBlock(
      Properties properties, Supplier<ParticleType<ColorParticleOption>> particle, RUTintedParticlesLeavesBlock.TintGetter getter, float particleChance
   ) {
      super(properties);
      this.particle = particle;
      this.tintGetter = getter;
      this.particleChance = particleChance;
   }

   public static BlockFactory<RUTintedParticlesLeavesBlock> small(RUTintedParticlesLeavesBlock.TintGetter tint) {
      return p -> new RUTintedParticlesLeavesBlock(p, RUParticleTypes.SMALL_LEAVES, tint, 0.025F);
   }

   public static BlockFactory<RUTintedParticlesLeavesBlock> large(RUTintedParticlesLeavesBlock.TintGetter tint) {
      return p -> new RUTintedParticlesLeavesBlock(p, RUParticleTypes.LARGE_LEAVES, tint, 0.01F);
   }

   public static BlockFactory<RUTintedParticlesLeavesBlock> pine(int tint) {
      return p -> new RUTintedParticlesLeavesBlock(p, RUParticleTypes.PINE_LEAVES, RUTintedParticlesLeavesBlock.TintGetter.constant(tint), 0.01F);
   }

   public static BlockFactory<RUTintedParticlesLeavesBlock> standard() {
      return standard(RUTintedParticlesLeavesBlock.TintGetter.DEFAULT);
   }

   public static BlockFactory<RUTintedParticlesLeavesBlock> standard(RUTintedParticlesLeavesBlock.TintGetter tint) {
      return standard(RUParticleTypes.STANDARD_LEAVES, tint);
   }

   public static BlockFactory<RUTintedParticlesLeavesBlock> standard(
      Supplier<ParticleType<ColorParticleOption>> type, RUTintedParticlesLeavesBlock.TintGetter tint
   ) {
      return standard(type, tint, 0.025F);
   }

   public static BlockFactory<RUTintedParticlesLeavesBlock> standard(
      Supplier<ParticleType<ColorParticleOption>> type, RUTintedParticlesLeavesBlock.TintGetter tint, float particleChance
   ) {
      return p -> new RUTintedParticlesLeavesBlock(p, type, tint, particleChance);
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      super.animateTick(state, level, pos, random);
      BlockPos below = pos.below();
      BlockState belowState = level.getBlockState(below);
      if (level.isClientSide()) {
         if (!(random.nextFloat() >= this.particleChance * RUConfigHandler.CLIENT.particleRates.leaves)) {
            if (!isFaceFull(belowState.getCollisionShape(level, below), Direction.UP)) {
               this.spawnLeavesParticle(level, pos, random);
            }
         }
      }
   }

   protected void spawnLeavesParticle(Level level, BlockPos pos, RandomSource random) {
      ColorParticleOption particle = ColorParticleOption.create(this.particle.get(), this.tintGetter.apply(level, pos));
      ParticleUtils.spawnParticleBelow(level, pos, random, particle);
   }

   public interface TintGetter extends BiFunction<Level, BlockPos, Integer> {
      RUTintedParticlesLeavesBlock.TintGetter DEFAULT = (level, pos) -> Minecraft.getInstance()
         .getBlockColors()
         .getColor(level.getBlockState(pos), level, pos, 0);

      static RUTintedParticlesLeavesBlock.TintGetter defaultDarken(float amount) {
         return (level, pos) -> {
            int base = DEFAULT.apply(level, pos);
            int r = (int)((base >> 16 & 0xFF) * amount) << 16;
            int g = (int)((base >> 8 & 0xFF) * amount) << 8;
            int b = (int)((base & 0xFF) * amount);
            return r | g | b;
         };
      }

      static RUTintedParticlesLeavesBlock.TintGetter constant(int tint) {
         return (level, pos) -> tint;
      }
   }
}
