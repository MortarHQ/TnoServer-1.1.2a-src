package com.benji.netherman.common.block;

import com.benji.netherman.common.block.entity.AltarBlockEntity;
import com.benji.netherman.init.ModBlockEntities;
import com.benji.netherman.init.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AltarBlock extends HorizontalDirectionalBlock implements EntityBlock {
   public static final MapCodec<AltarBlock> CODEC = simpleCodec(AltarBlock::new);
   public static final BooleanProperty LIT = BooleanProperty.create("lit");
   public static final IntegerProperty LETTER = IntegerProperty.create("letter", 0, 26);
   public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
   public static final BooleanProperty GUESSED = BooleanProperty.create("guessed");
   private static final VoxelShape SHAPE = Shapes.or(
      Block.box(1.0, 0.0, 1.0, 15.0, 5.0, 15.0), new VoxelShape[]{Block.box(3.0, 5.0, 3.0, 13.0, 14.0, 13.0), Block.box(2.0, 14.0, 2.0, 14.0, 18.0, 14.0)}
   );

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   public AltarBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH))
                     .setValue(LIT, false))
                  .setValue(LETTER, 0))
               .setValue(ACTIVE, false))
            .setValue(GUESSED, false)
      );
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{LIT, LETTER, ACTIVE, GUESSED, FACING});
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit
   ) {
      if ((Boolean)state.getValue(GUESSED)) {
         return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
      }

      if (!(Boolean)state.getValue(LIT)) {
         if (stack.is(Items.FLINT_AND_STEEL)) {
            if (!level.isClientSide()) {
               level.setBlock(pos, (BlockState)state.setValue(LIT, true), 3);
               stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
               level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide());
         }
      } else if ((Boolean)state.getValue(ACTIVE) && this.isCorrectPuzzleItem((Integer)state.getValue(LETTER), stack.getItem())) {
         if (!level.isClientSide()) {
            if (!player.isCreative()) {
               stack.shrink(1);
            }

            level.setBlock(pos, (BlockState)((BlockState)((BlockState)state.setValue(GUESSED, true)).setValue(ACTIVE, false)).setValue(LIT, false), 3);
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.5F, 1.0F);
            level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.0F, 1.2F);
            ((ServerLevel)level).sendParticles(ParticleTypes.END_ROD, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 25, 0.3, 0.3, 0.3, 0.05);
            ((ServerLevel)level).sendParticles(ParticleTypes.GLOW, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 15, 0.4, 0.4, 0.4, 0.1);
         }

         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      }

      return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      if ((Boolean)state.getValue(GUESSED)) {
         return InteractionResult.PASS;
      }

      if (!(Boolean)state.getValue(LIT)) {
         return InteractionResult.PASS;
      }

      if (!level.isClientSide()) {
         int currentLetter = (Integer)state.getValue(LETTER);
         int nextLetter = currentLetter >= 26 ? 1 : currentLetter + 1;
         BlockState newState = (BlockState)state.setValue(LETTER, nextLetter);
         level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, 1.5F);
         newState = this.checkAzazelLogic(level, pos, newState);
         level.setBlock(pos, newState, 3);
         if ((Boolean)newState.getValue(ACTIVE) && !(Boolean)state.getValue(ACTIVE)) {
            level.playSound(null, pos, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 2.0F, 1.0F);
            if (level.getBlockEntity(pos) instanceof AltarBlockEntity altar) {
               altar.triggerPuzzleSearch(nextLetter);
               altar.performSearch(level, pos);
               if (altar.getTargetPuzzlePos() != null) {
                  player.lookAt(Anchor.EYES, Vec3.atCenterOf(altar.getTargetPuzzlePos()));
               }
            }
         }
      }

      return InteractionResult.sidedSuccess(level.isClientSide());
   }

   private boolean isCorrectPuzzleItem(int letter, Item item) {
      if (letter == 1 && item == ModBlocks.A_PUZZLE.asItem()) {
         return true;
      } else if (letter == 5 && item == ModBlocks.E_PUZZLE.asItem()) {
         return true;
      } else {
         return letter == 12 && item == ModBlocks.L_PUZZLE.asItem() ? true : letter == 26 && item == ModBlocks.Z_PUZZLE.asItem();
      }
   }

   public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      super.neighborChanged(state, level, pos, block, fromPos, isMoving);
      if (!level.isClientSide() && (Boolean)state.getValue(LIT) && !(Boolean)state.getValue(GUESSED)) {
         BlockState reCheckedState = this.checkAzazelLogic(level, pos, state);
         if (reCheckedState != state) {
            level.setBlock(pos, reCheckedState, 3);
            if ((Boolean)reCheckedState.getValue(ACTIVE) && !(Boolean)state.getValue(ACTIVE)) {
               level.playSound(null, pos, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 2.0F, 1.0F);
               if (level.getBlockEntity(pos) instanceof AltarBlockEntity altar) {
                  altar.triggerPuzzleSearch((Integer)reCheckedState.getValue(LETTER));
               }
            }
         }
      }
   }

   private BlockState checkAzazelLogic(Level level, BlockPos pos, BlockState currentState) {
      int letter = (Integer)currentState.getValue(LETTER);
      boolean hasZ = this.hasAdjacent(level, pos, 26, false) || this.hasAdjacent(level, pos, 26, true);
      boolean hasE = this.hasAdjacent(level, pos, 5, false) || this.hasAdjacent(level, pos, 5, true);
      boolean hasL = this.hasAdjacent(level, pos, 12, false) || this.hasAdjacent(level, pos, 12, true);
      boolean hasAActive = this.hasAdjacent(level, pos, 1, true);
      boolean hasEActive = this.hasAdjacent(level, pos, 5, true);
      if (letter != 26 || !hasEActive && !hasAActive) {
         if (letter == 1 && hasZ) {
            return (BlockState)currentState.setValue(ACTIVE, true);
         } else if (letter != 5 || !hasZ && !hasL) {
            return letter == 12 && hasE ? (BlockState)currentState.setValue(ACTIVE, true) : (BlockState)currentState.setValue(ACTIVE, false);
         } else {
            return (BlockState)currentState.setValue(ACTIVE, true);
         }
      } else {
         return (BlockState)currentState.setValue(ACTIVE, true);
      }
   }

   private boolean hasAdjacent(Level level, BlockPos pos, int targetLetter, boolean mustBeActive) {
      for (Direction dir : Direction.values()) {
         BlockState neighbor = level.getBlockState(pos.relative(dir));
         if (neighbor.getBlock() instanceof AltarBlock
            && (Integer)neighbor.getValue(LETTER) == targetLetter
            && (!mustBeActive || (Boolean)neighbor.getValue(ACTIVE) || (Boolean)neighbor.getValue(GUESSED))) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new AltarBlockEntity(pos, state);
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return type == ModBlockEntities.ALTAR.get() ? (lvl, p, st, be) -> AltarBlockEntity.tick(lvl, p, st, (AltarBlockEntity)be) : null;
   }
}
