package team.creative.creativecore.common.gui.creator;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import team.creative.creativecore.common.gui.GuiLayer;

public interface BlockGuiCreator {
   GuiLayer create(CompoundTag var1, Level var2, BlockPos var3, BlockState var4, Player var5);
}
