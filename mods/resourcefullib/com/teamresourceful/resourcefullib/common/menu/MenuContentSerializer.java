package com.teamresourceful.resourcefullib.common.menu;

import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.Nullable;

public interface MenuContentSerializer<T extends MenuContent<T>> {
   @Nullable
   T from(FriendlyByteBuf var1);

   void to(FriendlyByteBuf var1, T var2);
}
