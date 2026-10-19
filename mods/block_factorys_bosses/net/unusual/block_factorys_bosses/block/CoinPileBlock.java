package net.unusual.block_factorys_bosses.block;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.unusual.block_factorys_bosses.block.abstracts.AbstractStackableBlock;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CoinPileBlock extends AbstractStackableBlock {
   public static final int MIN_STACK = 1;
   public static final int MAX_STACK = 2;
   public static final IntegerProperty COINS = IntegerProperty.create("coins", 1, 2);
   private static final VoxelShape SHAPE = box(1.0, 0.0, 1.0, 15.0, 3.0, 15.0);

   public CoinPileBlock() {
      super(
         Properties.of().mapColor(MapColor.GOLD).sound(SoundType.METAL).strength(0.5F, 5.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false),
         COINS,
         1,
         2
      );
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(COINS, 1));
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{COINS});
   }

   public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return Shapes.empty();
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return 0;
   }
}
