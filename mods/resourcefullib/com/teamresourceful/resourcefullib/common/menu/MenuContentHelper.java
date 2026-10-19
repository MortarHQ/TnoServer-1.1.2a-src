package com.teamresourceful.resourcefullib.common.menu;

import com.teamresourceful.resourcefullib.common.menu.neoforge.MenuContentHelperImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public final class MenuContentHelper {
   @ExpectPlatform
   @Transformed
   public static <T extends AbstractContainerMenu, C extends MenuContent<C>> MenuType<T> create(
      MenuContentHelper.MenuFactory<T, C> factory, MenuContentSerializer<C> serializer
   ) {
      return MenuContentHelperImpl.create(factory, serializer);
   }

   @ExpectPlatform
   @Transformed
   public static <C extends MenuContent<C>> void open(ServerPlayer player, ContentMenuProvider<C> provider) {
      MenuContentHelperImpl.open(player, provider);
   }

   public interface MenuFactory<T extends AbstractContainerMenu, C extends MenuContent<C>> {
      @NotNull
      T create(int var1, Inventory var2, Optional<C> var3);

      @NotNull
      default T create(int id, @NotNull Inventory inventory) {
         return this.create(id, inventory, Optional.empty());
      }
   }
}
