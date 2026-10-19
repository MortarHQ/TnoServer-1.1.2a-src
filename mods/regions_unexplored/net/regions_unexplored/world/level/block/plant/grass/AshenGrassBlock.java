package net.regions_unexplored.world.level.block.plant.grass;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.registry.tag.RUBlockTags;
import net.regions_unexplored.world.level.block.state.properties.RuBlockStateProperties;

public class AshenGrassBlock extends BushBlock {
   public static final MapCodec<? extends AshenGrassBlock> CODEC = simpleCodec(AshenGrassBlock::new);
   public static final BooleanProperty SMOULDERING = RuBlockStateProperties.SMOULDERING;
   protected static final float AABB_OFFSET = 6.0F;
   protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 10.0, 14.0);

   public AshenGrassBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(SMOULDERING, false));
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> stateBuilder) {
      stateBuilder.add(new Property[]{SMOULDERING});
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      super.animateTick(state, level, pos, random);
      if ((Boolean)state.getValue(SMOULDERING) && random.nextInt(20) == 0) {
         level.addParticle(ParticleTypes.FLAME, pos.getX() + random.nextDouble(), pos.getY() + 0.25, pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
         level.addParticle(ParticleTypes.SMOKE, pos.getX() + random.nextDouble(), pos.getY() + 0.25, pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
      }
   }

   public static boolean isSmouldering(BlockState state) {
      return (Boolean)state.getValue(SMOULDERING);
   }

   public boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos pos) {
      return state.is(RUBlockTags.SUPPORTS_ASHEN_GRASS);
   }
}
