package team.creative.creativecore.common.gui.creator;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import team.creative.creativecore.common.gui.GuiLayer;

public interface ItemGuiCreator {
   GuiLayer create(CompoundTag var1, Player var2);
}
