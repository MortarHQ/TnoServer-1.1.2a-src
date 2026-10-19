package com.mortal_cultivation.block;

import com.mojang.serialization.MapCodec;
import com.mortal_cultivation.block.entity.AlchemyFurnaceBlockEntity;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.IItemHandler;
import org.joml.Vector3f;

public class AlchemyFurnaceBlock extends HorizontalDirectionalBlock implements EntityBlock {
   public static final MapCodec<AlchemyFurnaceBlock> CODEC = simpleCodec(properties -> new AlchemyFurnaceBlock());
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final EnumProperty<AlchemyFurnaceBlock.AlchemyFurnacePart> PART = EnumProperty.create("part", AlchemyFurnaceBlock.AlchemyFurnacePart.class);
   public static final BooleanProperty LIT = BooleanProperty.create("lit");
   private static final VoxelShape BASE_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape TOP_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 13.0, 16.0);
   private static final ResourceLocation DESTROY_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "mortal_cultivation", "textures/block/alchemy_furnace/destroy_stage/alchemy_furnace_fragments.png"
   );

   public AlchemyFurnaceBlock() {
      super(
         Properties.of()
            .mapColor(MapColor.METAL)
            .strength(3.0F, 5.0F)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .isRedstoneConductor((state, world, pos) -> false)
            .dynamicShape()
            .lightLevel(state -> state.getValue(LIT) ? 12 : 0)
      );
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH))
               .setValue(PART, AlchemyFurnaceBlock.AlchemyFurnacePart.BASE))
            .setValue(LIT, false)
      );
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, PART, LIT});
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return state.getValue(PART) == AlchemyFurnaceBlock.AlchemyFurnacePart.BASE ? new AlchemyFurnaceBlockEntity(pos, state) : null;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      if (level.isClientSide()) {
         return null;
      } else {
         return state.getValue(PART) != AlchemyFurnaceBlock.AlchemyFurnacePart.BASE ? null : (lvl, pos, blockState, blockEntity) -> {
            if (blockEntity instanceof AlchemyFurnaceBlockEntity alchemyFurnace) {
               AlchemyFurnaceBlockEntity.tick(lvl, pos, blockState, alchemyFurnace);
            }
         };
      }
   }

   public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
      return player.hasCorrectToolForDrops(state);
   }

   public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
      ItemStack itemStack = player.getInventory().getSelected();
      if (itemStack.getItem() instanceof PickaxeItem) {
         return itemStack.isCorrectToolForDrops(state) ? 0.2F : 0.02F;
      } else {
         return 0.002F;
      }
   }

   public List<ItemStack> getDrops(BlockState state, net.minecraft.world.level.storage.loot.LootParams.Builder builder) {
      List<ItemStack> dropsOriginal = super.getDrops(state, builder);
      return !dropsOriginal.isEmpty() ? dropsOriginal : Collections.singletonList(new ItemStack(this, 1));
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
      super.setPlacedBy(level, pos, state, placer, stack);
      if (!level.isClientSide()) {
         BlockPos topPos = pos.above();
         level.setBlock(topPos, (BlockState)state.setValue(PART, AlchemyFurnaceBlock.AlchemyFurnacePart.TOP), 3);
         level.updateNeighborsAt(pos, Blocks.AIR);
         state.updateNeighbourShapes(level, pos, 3);
      }
   }

   private BlockPos getOtherPartPosition(BlockPos pos, AlchemyFurnaceBlock.AlchemyFurnacePart part) {
      return part == AlchemyFurnaceBlock.AlchemyFurnacePart.BASE ? pos.above() : pos.below();
   }

   public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
      if (!level.isClientSide()) {
         BlockPos otherPos = this.getOtherPartPosition(pos, (AlchemyFurnaceBlock.AlchemyFurnacePart)state.getValue(PART));
         level.removeBlock(otherPos, false);
         if (state.getValue(PART) == AlchemyFurnaceBlock.AlchemyFurnacePart.BASE) {
            if (level.getBlockEntity(pos) instanceof AlchemyFurnaceBlockEntity furnace) {
               furnace.dropItems(level, pos);
            }
         } else if (level.getBlockEntity(pos.below()) instanceof AlchemyFurnaceBlockEntity furnace) {
            furnace.dropItems(level, pos.below());
         }
      }

      super.playerWillDestroy(level, pos, state, player);
      return state;
   }

   public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
      if (!level.isClientSide()) {
         BlockPos otherPos = this.getOtherPartPosition(pos, (AlchemyFurnaceBlock.AlchemyFurnacePart)state.getValue(PART));
         level.removeBlock(otherPos, false);
      }

      super.onBlockExploded(state, level, pos, explosion);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos pos = context.getClickedPos();
      Level level = context.getLevel();
      return pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(context)
         ? (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
         : null;
   }

   public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      AlchemyFurnaceBlock.AlchemyFurnacePart part = (AlchemyFurnaceBlock.AlchemyFurnacePart)state.getValue(PART);
      return part == AlchemyFurnaceBlock.AlchemyFurnacePart.BASE ? BASE_SHAPE : TOP_SHAPE;
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return this.getShape(state, world, pos, context);
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter getter, BlockPos pos) {
      return true;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
      return 1.0F;
   }

   public boolean useShapeForLightOcclusion(BlockState state) {
      return true;
   }

   public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
      return false;
   }

   public VoxelShape getInteractionShape(BlockState state, BlockGetter world, BlockPos pos) {
      return this.getShape(state, world, pos, null);
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
      return false;
   }

   public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
      return false;
   }

   public VoxelShape getBlockSupportShape(BlockState state, BlockGetter reader, BlockPos pos) {
      return this.getCollisionShape(state, reader, pos, null);
   }

   public float getMaxHorizontalOffset() {
      return 0.0F;
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   @OnlyIn(Dist.CLIENT)
   public ResourceLocation getParticleTexture() {
      return DESTROY_TEXTURE;
   }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource randomSource) {
      if ((Boolean)state.getValue(LIT)) {
         Vector3f BLACK_COLOR = new Vector3f(0.1F, 0.1F, 0.1F);
         float PARTICLE_SIZE = 0.8F;
         boolean isBase = state.getValue(PART) == AlchemyFurnaceBlock.AlchemyFurnacePart.BASE;
         BlockPos particlePos = isBase ? pos : pos.below();
         if (randomSource.nextFloat() < 0.3F) {
            double x = particlePos.getX() + 0.5 + (randomSource.nextDouble() - 0.5) * 0.5;
            double y = particlePos.getY() + (isBase ? 1.0 : 0.2);
            double z = particlePos.getZ() + 0.5 + (randomSource.nextDouble() - 0.5) * 0.5;
            level.addParticle(new DustParticleOptions(BLACK_COLOR, PARTICLE_SIZE), x, y, z, 0.0, 0.05, 0.0);
            if (randomSource.nextFloat() < 0.3F) {
               level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.05, 0.0);
            }
         }
      }
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      if (level.isClientSide()) {
         return InteractionResult.sidedSuccess(true);
      }

      BlockPos basePos = state.getValue(PART) == AlchemyFurnaceBlock.AlchemyFurnacePart.BASE ? pos : pos.below();
      if (level.getBlockEntity(basePos) instanceof AlchemyFurnaceBlockEntity furnace) {
         player.openMenu(furnace);
      }

      return InteractionResult.CONSUME;
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit
   ) {
      if (level.isClientSide()) {
         return ItemInteractionResult.SUCCESS;
      } else {
         BlockPos basePos = state.getValue(PART) == AlchemyFurnaceBlock.AlchemyFurnacePart.BASE ? pos : pos.below();
         if (!(level.getBlockEntity(basePos) instanceof AlchemyFurnaceBlockEntity furnace)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
         } else if (player.isShiftKeyDown() && !stack.isEmpty() && stack.getBurnTime(null) > 0) {
            IItemHandler handler = furnace.getItemHandler();
            ItemStack fuelSlot = handler.getStackInSlot(1);
            if (!fuelSlot.isEmpty() && (!ItemStack.isSameItemSameComponents(fuelSlot, stack) || fuelSlot.getCount() >= fuelSlot.getMaxStackSize())) {
               return ItemInteractionResult.CONSUME;
            }

            this.insertItemIntoHandler(handler, stack, 1);
            furnace.setChanged();
            return ItemInteractionResult.SUCCESS;
         } else {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
         }
      }
   }

   private void insertItemIntoHandler(IItemHandler handler, ItemStack handItem, int slot) {
      ItemStack slotItem = handler.getStackInSlot(slot);
      if (slotItem.isEmpty()) {
         ItemStack toInsert = handItem.copy();
         toInsert.setCount(1);
         handler.insertItem(slot, toInsert, false);
         handItem.shrink(1);
      } else if (ItemStack.isSameItemSameComponents(slotItem, handItem) && slotItem.getCount() < slotItem.getMaxStackSize()) {
         int insertAmount = Math.min(handItem.getCount(), slotItem.getMaxStackSize() - slotItem.getCount());
         ItemStack toInsert = handItem.copy();
         toInsert.setCount(insertAmount);
         handler.insertItem(slot, toInsert, false);
         handItem.shrink(insertAmount);
      }
   }

   public enum AlchemyFurnacePart implements StringRepresentable {
      BASE("base"),
      TOP("top");

      private final String name;

      AlchemyFurnacePart(String name) {
         this.name = name;
      }

      public String getSerializedName() {
         return this.name;
      }
   }
}
