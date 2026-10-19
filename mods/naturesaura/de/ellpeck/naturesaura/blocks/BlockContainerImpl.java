package de.ellpeck.naturesaura.blocks;

import com.mojang.serialization.MapCodec;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityImpl;
import de.ellpeck.naturesaura.blocks.tiles.ITickableBlockEntity;
import de.ellpeck.naturesaura.reg.IModItem;
import de.ellpeck.naturesaura.reg.ModRegistry;
import de.ellpeck.naturesaura.reg.ModTileType;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class BlockContainerImpl extends BaseEntityBlock implements IModItem {
   private final String baseName;
   private final Class<? extends BlockEntity> tileClass;
   private final ModTileType<? extends BlockEntity> tileType;

   public BlockContainerImpl(String baseName, Class<? extends BlockEntity> tileClass, Properties properties) {
      super(properties);
      this.baseName = baseName;
      this.tileClass = tileClass;
      this.tileType = new ModTileType<>(this::createBlockEntity, this);
      ModRegistry.ALL_ITEMS.add(this);
      ModRegistry.ALL_ITEMS.add(this.tileType);
      if (this.hasWaterlogging()) {
         this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(BlockStateProperties.WATERLOGGED, false));
      }
   }

   protected boolean hasWaterlogging() {
      return false;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      if (this.hasWaterlogging()) {
         builder.add(new Property[]{BlockStateProperties.WATERLOGGED});
      }
   }

   public FluidState getFluidState(BlockState state) {
      return this.hasWaterlogging() && state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor levelIn, BlockPos currentPos, BlockPos facingPos) {
      if (this.hasWaterlogging() && (Boolean)stateIn.getValue(BlockStateProperties.WATERLOGGED)) {
         levelIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelIn));
      }

      return super.updateShape(stateIn, facing, facingState, levelIn, currentPos, facingPos);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      if (!this.hasWaterlogging()) {
         return super.getStateForPlacement(context);
      }

      FluidState state = context.getLevel().getFluidState(context.getClickedPos());
      return (BlockState)this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, state.is(FluidTags.WATER) && state.getAmount() == 8);
   }

   @org.jetbrains.annotations.Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return this.createBlockEntity(pos, state);
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return ITickableBlockEntity.class.isAssignableFrom(this.tileClass) ? ITickableBlockEntity.createTickerHelper(type, this.tileType.type) : null;
   }

   @Override
   public String getBaseName() {
      return this.baseName;
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return null;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   public List<ItemStack> getDrops(BlockState state, net.minecraft.world.level.storage.loot.LootParams.Builder builder) {
      List<ItemStack> drops = super.getDrops(state, builder);
      BlockEntity tile = (BlockEntity)builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
      if (tile instanceof BlockEntityImpl) {
         for (ItemStack stack : drops) {
            if (stack.getItem() == this.asItem()) {
               ((BlockEntityImpl)tile).modifyDrop(stack);
               break;
            }
         }
      }

      return drops;
   }

   public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
      if (level.getBlockEntity(pos) instanceof BlockEntityImpl impl) {
         impl.dropInventory();
      }

      return super.playerWillDestroy(level, pos, state, player);
   }

   public void setPlacedBy(Level levelIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
      BlockEntity tile = levelIn.getBlockEntity(pos);
      if (tile instanceof BlockEntityImpl) {
         ((BlockEntityImpl)tile).loadDataOnPlace(stack);
      }
   }

   public void neighborChanged(BlockState state, Level levelIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      this.updateRedstoneState(levelIn, pos);
   }

   private void updateRedstoneState(Level level, BlockPos pos) {
      if (!level.isClientSide && level.getBlockEntity(pos) instanceof BlockEntityImpl impl) {
         int newPower = level.getBestNeighborSignal(pos);
         if (impl.redstonePower != newPower) {
            level.scheduleTick(pos, this, 4);
         }
      }
   }

   public void tick(BlockState state, ServerLevel levelIn, BlockPos pos, RandomSource random) {
      if (!levelIn.isClientSide && levelIn.getBlockEntity(pos) instanceof BlockEntityImpl impl) {
         int newPower = levelIn.getBestNeighborSignal(pos);
         if (impl.redstonePower != newPower) {
            impl.onRedstonePowerChange(newPower);
         }
      }
   }

   private BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
      try {
         return this.tileClass.getConstructor(BlockPos.class, BlockState.class).newInstance(pos, state);
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
         throw new IllegalStateException("Cannot construct block entity from class " + this.tileClass, e);
      }
   }
}
