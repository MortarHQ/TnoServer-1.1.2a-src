package io.github.Memoires.mysticism.registry.menu;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public class MysticismMenuTypes {
   private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create("mysticism", Registries.MENU);

   public static void init() {
      MENUS.register();
   }
}
