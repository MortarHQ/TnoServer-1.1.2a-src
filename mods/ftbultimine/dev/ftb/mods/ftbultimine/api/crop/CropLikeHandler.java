package dev.ftb.mods.ftbultimine.api.crop;

import dev.ftb.mods.ftbultimine.api.util.ItemCollector;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface CropLikeHandler {
   boolean isApplicable(Level var1, BlockPos var2, BlockState var3);

   boolean doHarvesting(Player var1, BlockPos var2, BlockState var3, ItemCollector var4);

   boolean isEquivalent(BlockState var1, BlockState var2);
}
