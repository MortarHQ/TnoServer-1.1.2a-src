package xaero.map.cache;

import java.util.HashMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xaero.map.region.state.UnknownBlockState;

public class UnknownBlockStateCache {
   private HashMap<String, BlockState> unknownBlockStates = new HashMap<>();

   public BlockState getBlockStateFromNBT(HolderLookup<Block> blockLookup, CompoundTag nbt) {
      BlockState blockState;
      try {
         blockState = NbtUtils.readBlockState(blockLookup, nbt);
      } catch (IllegalArgumentException iae) {
         blockState = Blocks.AIR.defaultBlockState();
      }

      if (!nbt.getString("Name").equals("minecraft:air") && blockState.getBlock() == Blocks.AIR) {
         String nbtString = nbt.toString();
         blockState = this.unknownBlockStates.get(nbtString);
         if (blockState == null) {
            blockState = new UnknownBlockState(nbt);
            this.unknownBlockStates.put(nbtString, blockState);
         }
      }

      return blockState;
   }
}
