package gardensofthedead.block;

import gardensofthedead.GardensOfTheDead;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SoulblightFungusBlock extends FungusBlock {
   protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 13.0, 12.0);
   public static final ResourceKey<ConfiguredFeature<?, ?>> SOULBLIGHT_FUNGUS_PLANTED = ResourceKey.create(
      Registries.CONFIGURED_FEATURE, GardensOfTheDead.id("soulblight_fungus_planted")
   );
   public static final Block REQUIRED_BLOCK = Blocks.SOUL_SOIL;

   public SoulblightFungusBlock(Properties properties) {
      super(SOULBLIGHT_FUNGUS_PLANTED, REQUIRED_BLOCK, properties);
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return state.is(BlockTags.WART_BLOCKS) || state.is(Blocks.SOUL_SAND) || super.mayPlaceOn(state, level, pos);
   }
}
