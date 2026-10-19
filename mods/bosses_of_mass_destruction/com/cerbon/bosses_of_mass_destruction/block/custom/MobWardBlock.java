package com.cerbon.bosses_of_mass_destruction.block.custom;

import com.cerbon.bosses_of_mass_destruction.block.BMDBlockEntities;
import com.cerbon.bosses_of_mass_destruction.block.BMDBlocks;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.entity.custom.lich.LichUtils;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.cerbons_api.api.general.particle.ClientParticleBuilder;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import com.mojang.serialization.MapCodec;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class MobWardBlock extends BaseEntityBlock {
   public static final MapCodec<MobWardBlock> CODEC = simpleCodec(MobWardBlock::new);
   public static final VoxelShape blockShape = box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
   public static final VoxelShape thinBlockShape = box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
   public static final EnumProperty<TripleBlockPart> tripleBlockPart = EnumProperty.create("triple_part", TripleBlockPart.class);

   public MobWardBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH));
   }

   @NotNull
   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   @Nullable
   public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
      return new MobWardBlockEntity(pos, state);
   }

   @NotNull
   public RenderShape getRenderShape(@NotNull BlockState state) {
      return RenderShape.MODEL;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
      return createTickerHelper(blockEntityType, (BlockEntityType)BMDBlockEntities.MOB_WARD.get(), ChunkCacheBlockEntity::tick);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      tooltipComponents.add(Component.translatable("item.bosses_of_mass_destruction.mob_ward.tooltip").withStyle(ChatFormatting.DARK_GRAY));
   }

   @NotNull
   public BlockState updateShape(
      BlockState state,
      @NotNull Direction direction,
      @NotNull BlockState newState,
      @NotNull LevelAccessor level,
      @NotNull BlockPos pos,
      @NotNull BlockPos posFrom
   ) {
      TripleBlockPart thisState = (TripleBlockPart)state.getValue(tripleBlockPart);
      BlockState superState = super.updateShape(state, direction, newState, level, pos, posFrom);
      BlockState air = Blocks.AIR.defaultBlockState();
      boolean otherState = newState.is(this);
      BlockState facingState = otherState
         ? (BlockState)state.setValue(HorizontalDirectionalBlock.FACING, (Direction)newState.getValue(HorizontalDirectionalBlock.FACING))
         : air;

      return switch (thisState) {
         case BOTTOM -> {
            switch (direction) {
               case UP:
                  yield otherState && newState.getValue(tripleBlockPart) == TripleBlockPart.MIDDLE ? facingState : air;
               default:
                  yield superState;
            }
         }
         case MIDDLE -> {
            switch (direction) {
               case UP:
                  yield otherState && newState.getValue(tripleBlockPart) == TripleBlockPart.TOP ? facingState : air;
               case DOWN:
                  yield otherState && newState.getValue(tripleBlockPart) == TripleBlockPart.BOTTOM ? facingState : air;
               default:
                  yield superState;
            }
         }
         case TOP -> {
            switch (direction) {
               case DOWN:
                  yield otherState && newState.getValue(tripleBlockPart) == TripleBlockPart.MIDDLE ? facingState : air;
               default:
                  yield superState;
            }
         }
      };
   }

   @NotNull
   public BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
      if (!level.isClientSide && player.isCreative()) {
         TripleBlockPart part = (TripleBlockPart)state.getValue(tripleBlockPart);
         if (part == TripleBlockPart.MIDDLE) {
            this.checkBreakPart(pos.below(), level, state, player, TripleBlockPart.BOTTOM);
            this.checkBreakPart(pos.above(), level, state, player, TripleBlockPart.TOP);
         } else if (part == TripleBlockPart.TOP) {
            this.checkBreakPart(pos.below(2), level, state, player, TripleBlockPart.BOTTOM);
            this.checkBreakPart(pos.below(), level, state, player, TripleBlockPart.MIDDLE);
         }
      }

      return super.playerWillDestroy(level, pos, state, player);
   }

   private void checkBreakPart(BlockPos pos, Level level, BlockState state, Player player, TripleBlockPart part) {
      BlockState blockState = level.getBlockState(pos);
      if (blockState.getBlock() == state.getBlock() && blockState.getValue(tripleBlockPart) == part) {
         level.setBlock(pos, Blocks.AIR.defaultBlockState(), 35);
         level.levelEvent(player, 2001, pos, getId(blockState));
      }
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext ctx) {
      BlockPos blockPos = ctx.getClickedPos();
      return blockPos.getY() < ctx.getLevel().getMaxBuildHeight() - 2
            && ctx.getLevel().getBlockState(blockPos.above()).canBeReplaced(ctx)
            && ctx.getLevel().getBlockState(blockPos.above(2)).canBeReplaced(ctx)
         ? (BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(DoorBlock.FACING, ctx.getHorizontalDirection()))
            .setValue(tripleBlockPart, TripleBlockPart.BOTTOM)
         : null;
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
      level.setBlock(pos.above(), (BlockState)state.setValue(tripleBlockPart, TripleBlockPart.MIDDLE), 3);
      level.setBlock(pos.above(2), (BlockState)state.setValue(tripleBlockPart, TripleBlockPart.TOP), 3);
   }

   public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
      BlockPos blockPos = pos.below();
      BlockState blockState = level.getBlockState(blockPos);
      return state.getValue(tripleBlockPart) == TripleBlockPart.BOTTOM ? blockState.isFaceSturdy(level, blockPos, Direction.UP) : blockState.is(this);
   }

   @NotNull
   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate((Direction)state.getValue(HorizontalDirectionalBlock.FACING)));
   }

   @NotNull
   public BlockState mirror(@NotNull BlockState state, @NotNull Mirror mirror) {
      return mirror == Mirror.NONE ? state : state.rotate(mirror.getRotation((Direction)state.getValue(DoorBlock.FACING)));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{HorizontalDirectionalBlock.FACING, tripleBlockPart});
   }

   @NotNull
   public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return state.getValue(tripleBlockPart) == TripleBlockPart.TOP ? blockShape : thinBlockShape;
   }

   public void animateTick(BlockState state, @NotNull Level lvel, @NotNull BlockPos pos, @NotNull RandomSource random) {
      if (state.getValue(tripleBlockPart) == TripleBlockPart.TOP && random.nextInt(3) == 0) {
         Vec3 vecPos = VecUtils.asVec3(pos).add(VecUtils.unit.scale(0.5));
         double randomHeight = RandomUtils.randDouble(0.25) + 0.25;
         double randomRadius = RandomUtils.randDouble(0.1) + 0.3;
         double randomOffset = RandomUtils.randDouble(Math.PI);
         MobWardBlock.Particles.blueFireParticleFactory
            .continuousPosition(simpleParticle -> this.calcParticlePos(vecPos, randomOffset, randomRadius, randomHeight, simpleParticle.getAge() * 0.1))
            .build(this.calcParticlePos(vecPos, randomOffset, randomRadius, randomHeight, 0.0), Vec3.ZERO);
      }
   }

   private Vec3 calcParticlePos(Vec3 vecPos, double randomOffset, double randomRadius, double randomHeight, double age) {
      return vecPos.add(new Vec3(Math.sin(age + randomOffset) * randomRadius, randomHeight + age * 0.3, Math.cos(age + randomOffset) * randomRadius));
   }

   public static void canSpawn(ServerLevel serverLevel, MutableBlockPos pos, CallbackInfoReturnable<Boolean> cir) {
      if ((Boolean)cir.getReturnValue()) {
         ChunkPos chunkPos = new ChunkPos(pos);
         BMDCapabilities.getChunkBlockCache(serverLevel)
            .ifPresent(
               capability -> {
                  for (int x = chunkPos.x - 4; x <= chunkPos.x + 4; x++) {
                     for (int z = chunkPos.z - 4; z <= chunkPos.z + 4; z++) {
                        List<BlockPos> blocks = capability.getBlocksFromChunk(new ChunkPos(x, z), (Block)BMDBlocks.MOB_WARD.get());
                        if (blocks == null) {
                           return;
                        }

                        for (BlockPos blockPos : blocks) {
                           if (Math.abs(blockPos.getX() - pos.getX()) < 64
                              && Math.abs(blockPos.getY() - pos.getY()) < 64
                              && Math.abs(blockPos.getZ() - pos.getZ()) < 64) {
                              cir.setReturnValue(false);
                              break;
                           }
                        }
                     }
                  }
               }
            );
      }
   }

   private static class Particles {
      private static final ClientParticleBuilder blueFireParticleFactory = new ClientParticleBuilder((ParticleOptions)BMDParticles.SOUL_FLAME.get())
         .color(LichUtils.blueColorFade)
         .age(30, 40)
         .colorVariation(0.5)
         .scale(f -> 0.15F - f * 0.1F);
   }
}
