package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StarfireBirdAviaryBlock extends StarfireBirdNestBlock {
   public static final MapCodec<StarfireBirdAviaryBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(BlockSetType.CODEC.fieldOf("block_set_type").forGetter(block -> block.type), propertiesCodec())
         .apply(instance, StarfireBirdAviaryBlock::new)
   );
   public static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 15.75, 16.0);
   public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
   private final BlockSetType type;

   public StarfireBirdAviaryBlock(BlockSetType type, Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(OPEN, false));
      this.type = type;
   }

   @Override
   protected MapCodec<StarfireBirdAviaryBlock> codec() {
      return CODEC;
   }

   @Override
   public float getSeedsRenderOffset() {
      return 0.1875F;
   }

   @Override
   public boolean canAccessNestContent(BlockState state) {
      return (Boolean)state.getValue(OPEN);
   }

   @Override
   protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
      return SHAPE;
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if (!level.isClientSide) {
         boolean open = (Boolean)state.getValue(OPEN);
         level.setBlockAndUpdate(pos, (BlockState)state.setValue(OPEN, !open));
         level.playSound(
            null, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, open ? this.type.trapdoorClose() : this.type.trapdoorOpen(), SoundSource.BLOCKS, 1.0F, 1.0F
         );
      }

      return InteractionResult.sidedSuccess(level.isClientSide);
   }

   @Override
   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return true;
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{OPEN});
   }
}
