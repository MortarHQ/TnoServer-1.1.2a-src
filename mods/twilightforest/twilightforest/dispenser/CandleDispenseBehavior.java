package twilightforest.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.SkullBlock.Types;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import twilightforest.block.AbstractSkullCandleBlock;
import twilightforest.block.CandelabraBlock;
import twilightforest.block.LightableBlock;
import twilightforest.block.SkullCandleBlock;
import twilightforest.block.WallSkullCandleBlock;
import twilightforest.block.entity.CandelabraBlockEntity;
import twilightforest.block.entity.SkullCandleBlockEntity;
import twilightforest.init.TFBlocks;

public class CandleDispenseBehavior extends OptionalDispenseItemBehavior {
   protected ItemStack execute(BlockSource source, ItemStack stack) {
      ServerLevel level = source.level();
      if (!level.isClientSide()) {
         BlockPos blockpos = source.pos().relative((Direction)source.state().getValue(DispenserBlock.FACING));
         this.setSuccess(tryAddCandle(level, blockpos, stack.getItem()) || tryCreateSkullCandle(level, blockpos, stack.getItem()));
         if (this.isSuccess()) {
            stack.shrink(1);
         }
      }

      return stack;
   }

   private static boolean tryAddCandle(ServerLevel level, BlockPos pos, Item candle) {
      if (level.getBlockEntity(pos) instanceof SkullCandleBlockEntity sc) {
         if (candle == AbstractSkullCandleBlock.candleColorToCandle(AbstractSkullCandleBlock.CandleColors.colorFromInt(sc.getCandleColor())).asItem()) {
            BlockState state = level.getBlockState(pos);
            int candles = (Integer)state.getValue(BlockStateProperties.CANDLES);
            if (candles < 4) {
               level.setBlockAndUpdate(pos, (BlockState)state.setValue(BlockStateProperties.CANDLES, candles + 1));
               level.playSound(null, pos, SoundEvents.CANDLE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
               level.getLightEngine().checkBlock(pos);
               level.sendBlockUpdated(pos, state, state, 1);
               return true;
            }
         }
      } else if (level.getBlockEntity(pos) instanceof CandelabraBlockEntity candelabra) {
         if (!(candle instanceof BlockItem block)) {
            return false;
         }

         BlockState var12 = level.getBlockState(pos);

         for (int i = 0; i < 3; i++) {
            Block storedCandle = candelabra.getCandle(i);
            if (storedCandle == Blocks.AIR) {
               level.setBlockAndUpdate(pos, (BlockState)var12.setValue((Property)CandelabraBlock.CANDLES.get(i), true));
               candelabra.setCandle(i, block.getBlock());
               level.playSound(null, pos, SoundEvents.CANDLE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
               level.getLightEngine().checkBlock(pos);
               level.sendBlockUpdated(pos, var12, var12, 1);
               return true;
            }
         }
      }

      return false;
   }

   private static boolean tryCreateSkullCandle(ServerLevel level, BlockPos pos, Item candle) {
      BlockState blockstate = level.getBlockState(pos);
      if (blockstate.getBlock() instanceof AbstractSkullBlock skull) {
         Types type = (Types)skull.getType();
         boolean wall = blockstate.getBlock() instanceof WallSkullBlock;
         switch (type) {
            case SKELETON:
               if (wall) {
                  makeWallSkull(level, pos, (Block)TFBlocks.SKELETON_WALL_SKULL_CANDLE.get(), candle);
               } else {
                  makeFloorSkull(level, pos, (Block)TFBlocks.SKELETON_SKULL_CANDLE.get(), candle);
               }
               break;
            case WITHER_SKELETON:
               if (wall) {
                  makeWallSkull(level, pos, (Block)TFBlocks.WITHER_SKELE_WALL_SKULL_CANDLE.get(), candle);
               } else {
                  makeFloorSkull(level, pos, (Block)TFBlocks.WITHER_SKELE_SKULL_CANDLE.get(), candle);
               }
               break;
            case PLAYER:
               if (wall) {
                  makeWallSkull(level, pos, (Block)TFBlocks.PLAYER_WALL_SKULL_CANDLE.get(), candle);
               } else {
                  makeFloorSkull(level, pos, (Block)TFBlocks.PLAYER_SKULL_CANDLE.get(), candle);
               }
               break;
            case ZOMBIE:
               if (wall) {
                  makeWallSkull(level, pos, (Block)TFBlocks.ZOMBIE_WALL_SKULL_CANDLE.get(), candle);
               } else {
                  makeFloorSkull(level, pos, (Block)TFBlocks.ZOMBIE_SKULL_CANDLE.get(), candle);
               }
               break;
            case CREEPER:
               if (wall) {
                  makeWallSkull(level, pos, (Block)TFBlocks.CREEPER_WALL_SKULL_CANDLE.get(), candle);
               } else {
                  makeFloorSkull(level, pos, (Block)TFBlocks.CREEPER_SKULL_CANDLE.get(), candle);
               }
               break;
            default:
               return false;
         }

         return true;
      } else {
         return false;
      }
   }

   private static void makeFloorSkull(Level level, BlockPos pos, Block newBlock, Item candle) {
      ResolvableProfile profile = null;
      if (level.getBlockEntity(pos) instanceof SkullBlockEntity skull) {
         profile = skull.getOwnerProfile();
      }

      level.setBlockAndUpdate(
         pos,
         (BlockState)((BlockState)newBlock.defaultBlockState().setValue(AbstractSkullCandleBlock.LIGHTING, LightableBlock.Lighting.NONE))
            .setValue(SkullCandleBlock.ROTATION, (Integer)level.getBlockState(pos).getValue(SkullBlock.ROTATION))
      );
      level.setBlockEntity(
         new SkullCandleBlockEntity(
            pos,
            (BlockState)((BlockState)newBlock.defaultBlockState().setValue(AbstractSkullCandleBlock.LIGHTING, LightableBlock.Lighting.NONE))
               .setValue(SkullCandleBlock.ROTATION, (Integer)level.getBlockState(pos).getValue(SkullBlock.ROTATION)),
            AbstractSkullCandleBlock.candleToCandleColor(candle).getValue()
         )
      );
      if (level.getBlockEntity(pos) instanceof SkullCandleBlockEntity sc) {
         sc.setOwner(profile);
      }
   }

   private static void makeWallSkull(Level level, BlockPos pos, Block newBlock, Item candle) {
      ResolvableProfile profile = null;
      if (level.getBlockEntity(pos) instanceof SkullBlockEntity skull) {
         profile = skull.getOwnerProfile();
      }

      level.setBlockAndUpdate(
         pos,
         (BlockState)((BlockState)newBlock.defaultBlockState().setValue(AbstractSkullCandleBlock.LIGHTING, LightableBlock.Lighting.NONE))
            .setValue(WallSkullCandleBlock.FACING, (Direction)level.getBlockState(pos).getValue(WallSkullBlock.FACING))
      );
      level.setBlockEntity(
         new SkullCandleBlockEntity(
            pos,
            (BlockState)((BlockState)newBlock.defaultBlockState().setValue(AbstractSkullCandleBlock.LIGHTING, LightableBlock.Lighting.NONE))
               .setValue(WallSkullCandleBlock.FACING, (Direction)level.getBlockState(pos).getValue(WallSkullBlock.FACING)),
            AbstractSkullCandleBlock.candleToCandleColor(candle).getValue()
         )
      );
      if (level.getBlockEntity(pos) instanceof SkullCandleBlockEntity sc) {
         sc.setOwner(profile);
      }
   }
}
