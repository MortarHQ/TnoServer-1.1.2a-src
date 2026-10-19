package net.p3pp3rf1y.sophisticatedbackpacks.api;

import javax.annotation.Nullable;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageFluidHandler;

public interface IFluidHandlerWrapperUpgrade {
   @Nullable
   IStorageFluidHandler wrapHandler(@Nullable IStorageFluidHandler var1, ItemStack var2);
}
