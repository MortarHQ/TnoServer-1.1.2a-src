package org.crypticdev.stextras.registary.menu;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import org.crypticdev.stextras.menu.ImitatorMenu;

public class STExtrasMenuTypes {
   private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create("stextras", Registries.MENU);
   public static final RegistrySupplier<MenuType<ImitatorMenu>> IMITATOR_SKILL = MENUS.register(
      "imitator_menu", () -> MenuRegistry.ofExtended(ImitatorMenu::new)
   );

   public static void init() {
      MENUS.register();
   }
}
