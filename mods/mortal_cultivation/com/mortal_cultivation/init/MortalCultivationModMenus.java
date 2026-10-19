package com.mortal_cultivation.init;

import com.mortal_cultivation.gui.menu.AlchemyFurnaceMenu;
import com.mortal_cultivation.gui.menu.AncientScholarTableMenu;
import com.mortal_cultivation.gui.menu.RuneTableMenu;
import com.mortal_cultivation.gui.menu.TalismanMakingMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MortalCultivationModMenus {
   public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, "mortal_cultivation");
   public static final DeferredHolder<MenuType<?>, MenuType<TalismanMakingMenu>> TALISMAN_MAKING = REGISTRY.register(
      "talisman_making", () -> IMenuTypeExtension.create(TalismanMakingMenu::new)
   );
   public static final DeferredHolder<MenuType<?>, MenuType<AlchemyFurnaceMenu>> ALCHEMY_FURNACE = REGISTRY.register(
      "alchemy_furnace", () -> IMenuTypeExtension.create(AlchemyFurnaceMenu::new)
   );
   public static final DeferredHolder<MenuType<?>, MenuType<AncientScholarTableMenu>> ANCIENT_SCHOLAR_TABLE = REGISTRY.register(
      "ancient_scholar_table", () -> IMenuTypeExtension.create(AncientScholarTableMenu::new)
   );
   public static final DeferredHolder<MenuType<?>, MenuType<RuneTableMenu>> RUNE_TABLE = REGISTRY.register(
      "rune_table", () -> IMenuTypeExtension.create(RuneTableMenu::new)
   );

   public static void register(IEventBus modEventBus) {
      REGISTRY.register(modEventBus);
   }
}
