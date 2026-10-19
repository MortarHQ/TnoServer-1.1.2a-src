package team.lodestar.lodestone.systems.blockentity;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.items.IItemHandler;

public interface IItemHandlerSupplier {
   IItemHandler getInventory(Direction var1);
}
