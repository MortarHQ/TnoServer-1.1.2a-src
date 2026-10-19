package com.benji.netherman.common.block;

import com.benji.netherman.common.network.TotemAnimationPayload;
import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModItems;
import com.mojang.serialization.MapCodec;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;

public class LockerNetherBlock extends HorizontalDirectionalBlock {
   public static final MapCodec<VoidMidBlock> CODEC = simpleCodec(VoidMidBlock::new);
   private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

   public LockerNetherBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
      if (!level.isClientSide()) {
         CompoundTag playerData = player.getPersistentData();
         int brokenCount = playerData.getInt("LockerQuestProgress") + 1;
         Item animationItem = (Item)ModItems.QUEST_ICON_1.get();
         if (brokenCount == 2) {
            animationItem = (Item)ModItems.QUEST_ICON_2.get();
         }

         if (brokenCount >= 3) {
            animationItem = (Item)ModItems.QUEST_ICON_3.get();
         }

         level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.MASTER, 2.0F, 0.5F);
         if (player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new TotemAnimationPayload(new ItemStack(animationItem)), new CustomPacketPayload[0]);
         }

         if (brokenCount >= 3) {
            player.getInventory().add(new ItemStack((ItemLike)ModItems.ALTAR_COMPASS_KEY.get()));
            playerData.putInt("LockerQuestProgress", 0);
         } else {
            playerData.putInt("LockerQuestProgress", brokenCount);
         }

         this.triggerChainReactionAndPillars((ServerLevel)level, pos);
      }

      return super.playerWillDestroy(level, pos, state, player);
   }

   private void triggerChainReactionAndPillars(ServerLevel level, BlockPos startPos) {
      Set<BlockPos> lockersToDestroy = new HashSet<>();

      for (int x = -10; x <= 10; x++) {
         for (int y = -10; y <= 10; y++) {
            for (int z = -10; z <= 10; z++) {
               BlockPos checkPos = startPos.offset(x, y, z);
               if (level.getBlockState(checkPos).is(this)) {
                  lockersToDestroy.add(checkPos);
               }
            }
         }
      }

      for (BlockPos lockerPos : lockersToDestroy) {
         level.setBlock(lockerPos, ((Block)ModBlocks.BLACKSTONE_COLUMN.get()).defaultBlockState(), 3);
         level.scheduleTick(lockerPos, (Block)ModBlocks.BLACKSTONE_COLUMN.get(), 2);
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return Shapes.empty();
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }
}
