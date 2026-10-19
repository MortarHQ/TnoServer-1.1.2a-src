package com.cerbon.cerbons_api.platform.services;

import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public interface IMenuTypeHelper {
   <T extends AbstractContainerMenu> MenuType<T> registerSimpleMenuType(IMenuTypeHelper.SimpleMenuSupplier<T> var1);

   <T extends AbstractContainerMenu, D> MenuType<T> registerExtendedMenuType(
      IMenuTypeHelper.ExtendedMenuSupplier<T, D> var1, StreamCodec<? super RegistryFriendlyByteBuf, D> var2
   );

   <D> void openExtendedMenu(Player var1, MenuProvider var2, Function<ServerPlayer, D> var3, Consumer<FriendlyByteBuf> var4);

   @FunctionalInterface
   interface ExtendedMenuSupplier<T extends AbstractContainerMenu, D> {
      T create(int var1, Inventory var2, @Nullable D var3, @Nullable FriendlyByteBuf var4);
   }

   @FunctionalInterface
   interface SimpleMenuSupplier<T extends AbstractContainerMenu> {
      T create(int var1, Inventory var2);
   }
}
