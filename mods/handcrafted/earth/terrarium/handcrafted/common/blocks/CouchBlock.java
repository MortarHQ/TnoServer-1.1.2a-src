package earth.terrarium.handcrafted.common.blocks;

import com.mojang.serialization.MapCodec;
import earth.terrarium.handcrafted.common.blocks.base.ModularSeatBlock;
import earth.terrarium.handcrafted.common.blocks.base.properties.ColorProperty;
import earth.terrarium.handcrafted.common.blocks.base.properties.ModularSeatProperty;
import earth.terrarium.handcrafted.common.constants.ConstantComponents;
import earth.terrarium.handcrafted.common.utils.InteractionUtils;
import earth.terrarium.handcrafted.common.utils.TooltipUtils;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CouchBlock extends ModularSeatBlock {
   public static final MapCodec<CouchBlock> CODEC = simpleCodec(CouchBlock::new);
   public static final EnumProperty<ColorProperty> COLOR = EnumProperty.create("color", ColorProperty.class);
   public static final AABB SEAT = new AABB(0.0, 0.0, 0.0, 1.0, 0.125, 1.0);
   public static final VoxelShape VOXEL_SHAPE = box(0.0, 0.0, 0.0, 16.0, 5.0, 16.0);

   public CouchBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(SHAPE, ModularSeatProperty.SINGLE))
                  .setValue(COLOR, ColorProperty.WHITE))
               .setValue(FACING, Direction.NORTH))
            .setValue(WATERLOGGED, false)
      );
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
      if (!level.isClientSide() && state.getBlock() != newState.getBlock() && state.getValue(COLOR) != ColorProperty.WHITE) {
         Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), ((ColorProperty)state.getValue(COLOR)).toCushion());
      }

      super.onRemove(state, level, pos, newState, moved);
   }

   @NotNull
   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return VOXEL_SHAPE;
   }

   @Override
   public AABB getSeatSize(BlockState state) {
      return SEAT;
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{COLOR});
   }

   @Override
   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      ItemInteractionResult result = InteractionUtils.interactCushion(state, level, pos, player, stack, COLOR);
      return result != ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION ? result : super.useItemOn(stack, state, level, pos, player, hand, hitResult);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.CUSHION);
   }
}
