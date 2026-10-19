package com.natamus.collective_common_neoforge.functions;

import com.natamus.collective_common_neoforge.data.Constants;
import com.natamus.collective_common_neoforge.data.GlobalVariables;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class BlockFunctions {
   public static List<ItemStack> getBlockDrops(Level level, BlockState blockState, boolean silkTouched) {
      return getBlockDrops(level, blockState, silkTouched ? Constants.silkPickaxeStack : Constants.normalPickaxeStack, null, new Vec3(0.0, 0.0, 0.0));
   }

   public static List<ItemStack> getBlockDrops(Level level, BlockState blockState, ItemStack itemStack, Player player) {
      return getBlockDrops(level, blockState, itemStack, player, player.position());
   }

   public static List<ItemStack> getBlockDrops(Level level, BlockState blockState, ItemStack itemStack, Player player, Vec3 vec) {
      Builder lootParamsBuilder = new Builder((ServerLevel)level)
         .withParameter(LootContextParams.BLOCK_STATE, blockState)
         .withParameter(LootContextParams.TOOL, itemStack)
         .withParameter(LootContextParams.ORIGIN, vec)
         .withOptionalParameter(LootContextParams.THIS_ENTITY, player);
      return blockState.getDrops(lootParamsBuilder);
   }

   public static Boolean isSpecificBlock(Block specificblock, Block tocheckblock) {
      return specificblock != null && tocheckblock != null ? specificblock.equals(tocheckblock) : false;
   }

   public static Boolean isSpecificBlock(Block specificblock, ItemStack tocheckitemstack) {
      if (tocheckitemstack == null) {
         return false;
      }

      Item tocheckitem = tocheckitemstack.getItem();
      if (tocheckitem == null) {
         return false;
      }

      Block tocheckblock = Block.byItem(tocheckitem);
      return isSpecificBlock(specificblock, tocheckblock);
   }

   public static Boolean isSpecificBlock(Block specificblock, Level world, BlockPos pos) {
      Block tocheckblock = world.getBlockState(pos).getBlock();
      return isSpecificBlock(specificblock, tocheckblock);
   }

   public static void dropBlock(Level world, BlockPos pos) {
      BlockState blockstate = world.getBlockState(pos);
      BlockEntity tileentity = world.getBlockEntity(pos);
      Block.dropResources(blockstate, world, pos, tileentity);
      world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
   }

   public static void dropBlock(Level world, BlockPos pos, @Nullable Entity entity, ItemStack itemStack) {
      BlockState blockstate = world.getBlockState(pos);
      BlockEntity tileentity = world.getBlockEntity(pos);
      Block.dropResources(blockstate, world, pos, tileentity, entity, itemStack);
      world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
   }

   public static Boolean isOneOfBlocks(List<Block> blocks, Block tocheckblock) {
      if (blocks.size() < 1) {
         return false;
      }

      for (Block specificblock : blocks) {
         if (isSpecificBlock(specificblock, tocheckblock)) {
            return true;
         }
      }

      return false;
   }

   public static Boolean isOneOfBlocks(List<Block> blocks, ItemStack tocheckitemstack) {
      if (tocheckitemstack == null) {
         return false;
      }

      Item tocheckitem = tocheckitemstack.getItem();
      if (tocheckitem == null) {
         return false;
      }

      Block tocheckblock = Block.byItem(tocheckitem);
      return isOneOfBlocks(blocks, tocheckblock);
   }

   public static Boolean isOneOfBlocks(List<Block> blocks, Level world, BlockPos pos) {
      Block tocheckblock = world.getBlockState(pos).getBlock();
      return isOneOfBlocks(blocks, tocheckblock);
   }

   public static boolean isGrowBlock(Block block) {
      return GlobalVariables.growblocks.contains(block);
   }

   public static boolean isStoneTypeBlock(Block block) {
      return GlobalVariables.stoneblocks.contains(block);
   }

   public static Boolean isFilledPortalFrame(BlockState blockstate) {
      Block block = blockstate.getBlock();
      return !block.equals(Blocks.END_PORTAL_FRAME) ? false : (Boolean)blockstate.getValue(EndPortalFrameBlock.HAS_EYE);
   }

   public static boolean canOpenByHand(BlockState blockState) {
      return canOpenByHand(blockState, true);
   }

   public static boolean canOpenByHand(BlockState blockState, boolean defaultReturn) {
      Block block = blockState.getBlock();
      if (block instanceof DoorBlock doorBlock) {
         return doorBlock.type().canOpenByHand();
      } else {
         return block instanceof TrapDoorBlock trapDoorBlock ? trapDoorBlock.getType().canOpenByHand() : defaultReturn;
      }
   }

   public static String blockToReadableString(Block block, int amount) {
      String[] blockspl = block.getDescriptionId().replace("block.", "").split("\\.");
      String blockstring;
      if (blockspl.length > 1) {
         blockstring = blockspl[1];
      } else {
         blockstring = blockspl[0];
      }

      blockstring = blockstring.replace("_", " ");
      if (amount > 1) {
         blockstring = blockstring + "s";
      }

      return blockstring;
   }

   public static String blockToReadableString(Block block) {
      return blockToReadableString(block, 1);
   }
}
