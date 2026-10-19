package com.mortal_cultivation.block;

import com.mojang.serialization.MapCodec;
import com.mortal_cultivation.block.entity.MillenniumGinsengBlockEntity;
import com.mortal_cultivation.entity.MillenniumGinsengEntity;
import com.mortal_cultivation.init.BlockEntityRegistry;
import com.mortal_cultivation.registry.CultivationEntities;
import com.mortal_cultivation.registry.item.CultivationItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MillenniumGinsengBlock extends BushBlock implements EntityBlock {
   public static final MapCodec<MillenniumGinsengBlock> CODEC = simpleCodec(MillenniumGinsengBlock::new);
   private static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 8.0, 14.0);

   public MillenniumGinsengBlock(Properties properties) {
      super(properties);
   }

   public MapCodec<MillenniumGinsengBlock> codec() {
      return CODEC;
   }

   public static boolean canHarvestQuietly(Player player) {
      return player.isShiftKeyDown() || player.isInvisible();
   }

   public static boolean isThreat(Player player) {
      return player.isAlive() && !player.isSpectator() && !canHarvestQuietly(player);
   }

   public static boolean startle(ServerLevel level, BlockPos pos, Player player) {
      if (level.hasChunkAt(pos) && isThreat(player)) {
         BlockState plant = level.getBlockState(pos);
         if (!(plant.getBlock() instanceof MillenniumGinsengBlock)) {
            return false;
         }

         MillenniumGinsengEntity root = (MillenniumGinsengEntity)((EntityType)CultivationEntities.MILLENNIUM_GINSENG.get()).create(level);
         if (root == null) {
            return false;
         }

         root.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0F, 0.0F);
         root.startEscape(player);
         if (!level.addFreshEntity(root)) {
            return false;
         }

         if (level.getBlockState(pos).equals(plant) && level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3)) {
            return true;
         }

         root.discard();
         return false;
      } else {
         return false;
      }
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new MillenniumGinsengBlockEntity(pos, state);
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return !level.isClientSide && type == BlockEntityRegistry.MILLENNIUM_GINSENG.get() ? (tickLevel, tickPos, tickState, blockEntity) -> {
         if (blockEntity instanceof MillenniumGinsengBlockEntity ginseng) {
            MillenniumGinsengBlockEntity.serverTick(tickLevel, tickPos, tickState, ginseng);
         }
      } : null;
   }

   public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
      return new ItemStack((ItemLike)CultivationItems.MILLENNIUM_GINSENG.get());
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      Vec3 offset = state.getOffset(level, pos);
      return SHAPE.move(offset.x, offset.y, offset.z);
   }
}
