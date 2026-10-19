package com.mortal_cultivation.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3f;

public class ImmortalHerbBlock extends BushBlock {
   public static final MapCodec<ImmortalHerbBlock> CODEC = simpleCodec(ImmortalHerbBlock::new);
   private static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 10.0, 14.0);
   private static final DustParticleOptions ROOT_GLOW = new DustParticleOptions(new Vector3f(1.0F, 0.86F, 0.48F), 0.45F);

   public ImmortalHerbBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<ImmortalHerbBlock> codec() {
      return CODEC;
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      Vec3 offset = state.getOffset(level, pos);
      return SHAPE.move(offset.x, offset.y, offset.z);
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      boolean rootGlow = random.nextInt(4) == 0;
      boolean leafSparkle = random.nextInt(8) == 0;
      if (rootGlow || leafSparkle) {
         Vec3 offset = state.getOffset(level, pos);
         double centerX = pos.getX() + 0.5 + offset.x;
         double baseY = pos.getY() + offset.y;
         double centerZ = pos.getZ() + 0.5 + offset.z;
         if (rootGlow) {
            level.addParticle(
               ROOT_GLOW,
               centerX + (random.nextDouble() - 0.5) * 0.45,
               baseY + 0.04 + random.nextDouble() * 0.06,
               centerZ + (random.nextDouble() - 0.5) * 0.45,
               0.0,
               0.012,
               0.0
            );
         }

         if (leafSparkle) {
            level.addParticle(
               ParticleTypes.HAPPY_VILLAGER,
               centerX + (random.nextDouble() - 0.5) * 0.8,
               baseY + 0.18 + random.nextDouble() * 0.48,
               centerZ + (random.nextDouble() - 0.5) * 0.8,
               0.0,
               0.015,
               0.0
            );
         }
      }
   }
}
