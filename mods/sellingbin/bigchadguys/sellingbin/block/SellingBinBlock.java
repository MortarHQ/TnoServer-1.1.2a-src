package bigchadguys.sellingbin.block;

import bigchadguys.sellingbin.block.entity.SellingBinBlockEntity;
import bigchadguys.sellingbin.init.ModBlocks;
import bigchadguys.sellingbin.item.SellingBinItem;
import com.mojang.serialization.MapCodec;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SellingBinBlock extends BaseEntityBlock implements WorldlyContainerHolder {
   public static final MapCodec<SellingBinBlock> CODEC = simpleCodec(SellingBinBlock::new);
   public static final EnumProperty<BinMaterial> MATERIAL = EnumProperty.create("material", BinMaterial.class);
   public static final EnumProperty<BinMode> MODE = EnumProperty.create("mode", BinMode.class);
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   private static final VoxelShape[] SHAPES = new VoxelShape[]{
      Shapes.joinUnoptimized(
         Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
         Shapes.or(
            Block.box(2.0, 2.0, 2.0, 14.0, 16.0, 14.0),
            new VoxelShape[]{
               Block.box(2.0, 2.0, 0.0, 14.0, 14.0, 1.0),
               Block.box(0.0, 2.0, 2.0, 1.0, 14.0, 14.0),
               Block.box(2.0, 2.0, 15.0, 14.0, 14.0, 16.0),
               Block.box(15.0, 2.0, 2.0, 16.0, 14.0, 14.0),
               Block.box(1.0, 10.0, 6.0, 2.0, 12.0, 10.0),
               Block.box(14.0, 10.0, 6.0, 15.0, 12.0, 10.0)
            }
         ),
         BooleanOp.ONLY_FIRST
      ),
      Shapes.joinUnoptimized(
         Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
         Shapes.or(
            Block.box(2.0, 2.0, 2.0, 14.0, 16.0, 14.0),
            new VoxelShape[]{
               Block.box(2.0, 2.0, 0.0, 14.0, 14.0, 1.0),
               Block.box(0.0, 2.0, 2.0, 1.0, 14.0, 14.0),
               Block.box(2.0, 2.0, 15.0, 14.0, 14.0, 16.0),
               Block.box(15.0, 2.0, 2.0, 16.0, 14.0, 14.0),
               Block.box(6.0, 10.0, 1.0, 10.0, 12.0, 2.0),
               Block.box(6.0, 10.0, 14.0, 10.0, 12.0, 15.0)
            }
         ),
         BooleanOp.ONLY_FIRST
      )
   };

   public SellingBinBlock(Properties settings) {
      super(settings);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(MATERIAL, BinMaterial.WOOD))
               .setValue(MODE, BinMode.BLOCK_BOUND))
            .setValue(FACING, Direction.NORTH)
      );
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return switch ((Direction)state.getValue(FACING)) {
         case NORTH, SOUTH -> SHAPES[0];
         case EAST, WEST -> SHAPES[1];
         default -> throw new IllegalStateException("Unexpected value: " + state.getValue(FACING));
      };
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)((BlockState)((BlockState)this.defaultBlockState()
               .setValue(MATERIAL, SellingBinItem.getMaterial(context.getItemInHand()).orElse(BinMaterial.WOOD)))
            .setValue(MODE, SellingBinItem.getMode(context.getItemInHand()).orElse(BinMode.BLOCK_BOUND)))
         .setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
      ItemStack stack = super.getCloneItemStack(world, pos, state);
      SellingBinItem.setMaterial(stack, (BinMaterial)state.getValue(MATERIAL));
      SellingBinItem.setMode(stack, (BinMode)state.getValue(MODE));
      return stack;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{MATERIAL}).add(new Property[]{MODE}).add(new Property[]{FACING});
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new SellingBinBlockEntity(pos, state);
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, (BlockEntityType)ModBlocks.Entities.SELLING_BIN.get(), SellingBinBlockEntity::tick);
   }

   public WorldlyContainer getContainer(BlockState state, LevelAccessor world, BlockPos pos) {
      return world.getBlockEntity(pos) instanceof SellingBinBlockEntity bin ? bin.getInventory() : null;
   }

   public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
      if (!world.isClientSide() && player instanceof ServerPlayer serverPlayer) {
         if (world.getBlockEntity(pos) instanceof ExtendedMenuProvider menu) {
            MenuRegistry.openExtendedMenu(serverPlayer, menu);
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
      if (!world.isClientSide && world.getBlockEntity(pos) instanceof SellingBinBlockEntity bin) {
         ItemStack stack = SellingBinItem.create((BinMaterial)state.getValue(MATERIAL), (BinMode)state.getValue(MODE));
         bin.saveToItem(stack, world.registryAccess());
         if (player.isCreative() && stack.get(DataComponents.BLOCK_ENTITY_DATA) != null) {
            ItemEntity item = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
            item.setDefaultPickUpDelay();
            world.addFreshEntity(item);
         }
      }

      return super.playerWillDestroy(world, pos, state, player);
   }

   public List<ItemStack> getDrops(BlockState state, net.minecraft.world.level.storage.loot.LootParams.Builder builder) {
      List<ItemStack> drops = super.getDrops(state, builder);
      ItemStack stack = SellingBinItem.create((BinMaterial)state.getValue(MATERIAL), (BinMode)state.getValue(MODE));
      if (builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof SellingBinBlockEntity bin) {
         bin.saveToItem(stack, bin.getLevel().registryAccess());
      }

      drops.add(stack);
      return drops;
   }
}
