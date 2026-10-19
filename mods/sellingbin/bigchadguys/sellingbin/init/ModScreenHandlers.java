package bigchadguys.sellingbin.init;

import bigchadguys.sellingbin.screen.handler.SellingBinScreenHandler;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.inventory.MenuType;

public class ModScreenHandlers extends ModRegistries {
   public static RegistrySupplier<MenuType<SellingBinScreenHandler>> SELLING_BIN;

   public static void register() {
      SELLING_BIN = register(SCREEN_HANDLERS, "selling_bin", () -> MenuRegistry.ofExtended(SellingBinScreenHandler::new));
   }
}
