package net.blay09.mods.waystones.block;

import com.mojang.serialization.MapCodec;
import java.util.Objects;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.block.entity.WaystoneBlockEntity;
import net.blay09.mods.waystones.block.entity.WaystoneBlockEntityBase;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WaystoneSyncManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WaystoneBlock extends WaystoneBlockBase {
   public static final MapCodec<WaystoneBlock> CODEC = simpleCodec(WaystoneBlock::new);
   private static final VoxelShape LOWER_SHAPE = Shapes.or(
         box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
         new VoxelShape[]{box(1.0, 3.0, 1.0, 15.0, 7.0, 15.0), box(2.0, 7.0, 2.0, 14.0, 9.0, 14.0), box(3.0, 9.0, 3.0, 13.0, 16.0, 13.0)}
      )
      .optimize();
   private static final VoxelShape UPPER_SHAPE = Shapes.or(
         box(3.0, 0.0, 3.0, 13.0, 8.0, 13.0),
         new VoxelShape[]{
            box(2.0, 8.0, 2.0, 14.0, 10.0, 14.0),
            box(1.0, 10.0, 1.0, 15.0, 12.0, 15.0),
            box(3.0, 12.0, 3.0, 13.0, 14.0, 13.0),
            box(4.0, 14.0, 4.0, 12.0, 16.0, 12.0)
         }
      )
      .optimize();

   public WaystoneBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(HALF, DoubleBlockHalf.LOWER)).setValue(WATERLOGGED, false))
            .setValue(FACING, Direction.NORTH)
      );
   }

   @Override
   protected boolean canSilkTouch() {
      return true;
   }

   public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
      return state.getValue(HALF) == DoubleBlockHalf.UPPER ? UPPER_SHAPE : LOWER_SHAPE;
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new WaystoneBlockEntity(pos, state);
   }

   @Override
   protected InteractionResult handleActivation(Level world, BlockPos pos, Player player, WaystoneBlockEntityBase blockEntity, Waystone waystone) {
      boolean isActivated = PlayerWaystoneManager.isWaystoneActivated(player, waystone);
      if (isActivated) {
         if (!world.isClientSide) {
            blockEntity.getSelectionMenuProvider().ifPresent(menuProvider -> Balm.getNetworking().openGui(player, menuProvider));
         }
      } else {
         PlayerWaystoneManager.activateWaystone(player, waystone);
         if (!world.isClientSide) {
            MutableComponent nameComponent = waystone.getName().copy().withStyle(ChatFormatting.WHITE);
            MutableComponent chatComponent = Component.translatable("chat.waystones.waystone_activated", new Object[]{nameComponent})
               .withStyle(ChatFormatting.YELLOW);
            player.sendSystemMessage(chatComponent);
            WaystoneSyncManager.sendActivatedWaystones(player);
            world.playSound(null, pos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 0.2F, 1.0F);
         }

         this.notifyObserversOfAction(world, pos);
         if (world.isClientSide) {
            for (int i = 0; i < 32; i++) {
               world.addParticle(
                  ParticleTypes.ENCHANT,
                  pos.getX() + 0.5 + (world.random.nextDouble() - 0.5) * 2.0,
                  pos.getY() + 3,
                  pos.getZ() + 0.5 + (world.random.nextDouble() - 0.5) * 2.0,
                  0.0,
                  -5.0,
                  0.0
               );
               world.addParticle(
                  ParticleTypes.ENCHANT,
                  pos.getX() + 0.5 + (world.random.nextDouble() - 0.5) * 2.0,
                  pos.getY() + 4,
                  pos.getZ() + 0.5 + (world.random.nextDouble() - 0.5) * 2.0,
                  0.0,
                  -5.0,
                  0.0
               );
            }
         }
      }

      return InteractionResult.SUCCESS;
   }

   public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
      if (random.nextFloat() < 0.75F) {
         BlockEntity blockEntity = world.getBlockEntity(pos);
         Player player = Minecraft.getInstance().player;
         if (blockEntity instanceof WaystoneBlockEntity
            && PlayerWaystoneManager.isWaystoneActivated(Objects.requireNonNull(player), ((WaystoneBlockEntity)blockEntity).getWaystone())) {
            world.addParticle(
               ParticleTypes.PORTAL,
               pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 1.5,
               pos.getY() + 0.5,
               pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 1.5,
               0.0,
               0.0,
               0.0
            );
            world.addParticle(
               ParticleTypes.ENCHANT,
               pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 1.5,
               pos.getY() + 0.5,
               pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 1.5,
               0.0,
               0.0,
               0.0
            );
         }
      }
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{HALF});
   }

   protected boolean isPathfindable(BlockState state, PathComputationType type) {
      return false;
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }
}
