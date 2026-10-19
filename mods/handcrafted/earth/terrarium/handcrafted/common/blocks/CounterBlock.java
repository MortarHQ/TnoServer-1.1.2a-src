package earth.terrarium.handcrafted.common.blocks;

import com.mojang.serialization.MapCodec;
import earth.terrarium.handcrafted.common.blockentities.ContainerBlockEntity;
import earth.terrarium.handcrafted.common.blocks.base.Hammerable;
import earth.terrarium.handcrafted.common.blocks.base.properties.CounterProperty;
import earth.terrarium.handcrafted.common.constants.ConstantComponents;
import earth.terrarium.handcrafted.common.registry.ModItems;
import earth.terrarium.handcrafted.common.registry.ModSoundEvents;
import earth.terrarium.handcrafted.common.utils.InteractionUtils;
import earth.terrarium.handcrafted.common.utils.TooltipUtils;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class CounterBlock extends HorizontalDirectionalBlock implements Hammerable, EntityBlock {
   public static final MapCodec<CounterBlock> CODEC = simpleCodec(CounterBlock::new);
   public static final IntegerProperty TYPE = IntegerProperty.create("type", 1, 3);
   public static final EnumProperty<CounterProperty> COUNTER = EnumProperty.create("counter", CounterProperty.class);

   public CounterBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, Direction.NORTH)).setValue(TYPE, 1))
            .setValue(COUNTER, CounterProperty.CALCITE)
      );
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
      if (!level.isClientSide() && state.getBlock() != newState.getBlock() && !((CounterProperty)state.getValue(COUNTER)).toBlock().is(Items.CALCITE)) {
         Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), ((CounterProperty)state.getValue(COUNTER)).toBlock());
      }

      if (state.getBlock() != newState.getBlock()) {
         if (level.getBlockEntity(pos) instanceof ContainerBlockEntity container && container.getContainerSize() > 0) {
            Containers.dropContents(level, pos, container);
            level.updateNeighbourForOutputSignal(pos, this);
         }

         super.onRemove(state, level, pos, newState, moved);
      }
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      ItemInteractionResult result = InteractionUtils.interactCounter(state, level, pos, player, stack, COUNTER);
      if (!(result == ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION && level.getBlockEntity(pos) instanceof ContainerBlockEntity container)) {
         return result;
      }

      if (stack.is((Item)ModItems.HAMMER.get())) {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }

      player.openMenu(container);
      return ItemInteractionResult.SUCCESS;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, TYPE, COUNTER});
   }

   public BlockState getStateForPlacement(BlockPlaceContext ctx) {
      return (BlockState)this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
   }

   @Override
   public void onHammer(Level level, BlockPos pos, BlockState state, Direction side, Player user, Vec3 hitPos) {
      if (!level.isClientSide()) {
         level.setBlockAndUpdate(pos, (BlockState)state.cycle(TYPE));
         level.playSound(null, pos, (SoundEvent)ModSoundEvents.HAMMER_WOOD.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
      }
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new ContainerBlockEntity(pos, state);
   }

   protected boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
      return AbstractContainerMenu.getRedstoneSignalFromContainer((Container)level.getBlockEntity(pos));
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.COUNTER, ConstantComponents.HAMMER_USE_LOOK);
   }
}
