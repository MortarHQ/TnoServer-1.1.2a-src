package com.teamresourceful.resourcefullib.common.menu.neoforge;

import com.teamresourceful.resourcefullib.common.menu.ContentMenuProvider;
import com.teamresourceful.resourcefullib.common.menu.MenuContent;
import com.teamresourceful.resourcefullib.common.menu.MenuContentHelper;
import com.teamresourceful.resourcefullib.common.menu.MenuContentSerializer;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MenuContentHelperImpl {
   public static <T extends AbstractContainerMenu, C extends MenuContent<C>> MenuType<T> create(
      MenuContentHelper.MenuFactory<T, C> factory, MenuContentSerializer<C> serializer
   ) {
      return IMenuTypeExtension.create(
         (id, inventory, data) -> serializer != null
            ? factory.create(id, inventory, Optional.ofNullable(serializer.from(data)))
            : factory.create(id, inventory, Optional.empty())
      );
   }

   public static <C extends MenuContent<C>> void open(ServerPlayer player, ContentMenuProvider<C> provider) {
      player.openMenu(new MenuContentHelperImpl.ScreenFactory<>(provider), buf -> {
         C content = provider.createContent(player);
         if (content != null) {
            content.serializer().to(buf, content);
         }
      });
   }

   private record ScreenFactory<C extends MenuContent<C>>(ContentMenuProvider<C> provider) implements MenuProvider {
      @NotNull
      public Component getDisplayName() {
         return this.provider.getDisplayName();
      }

      @Nullable
      public AbstractContainerMenu createMenu(int i, @NotNull Inventory arg, @NotNull Player arg2) {
         return this.provider.createMenu(i, arg, arg2);
      }

      public boolean shouldTriggerClientSideContainerClosingOnOpen() {
         return this.provider.resetMouseOnOpen();
      }
   }
}
