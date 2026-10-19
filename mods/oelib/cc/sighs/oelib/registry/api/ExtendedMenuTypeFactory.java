package cc.sighs.oelib.registry.api;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

@FunctionalInterface
public interface ExtendedMenuTypeFactory<T extends AbstractContainerMenu> {
   T create(int var1, Inventory var2, FriendlyByteBuf var3);
}
