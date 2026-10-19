package bigchadguys.sellingbin.init;

import bigchadguys.sellingbin.screen.SellingBinScreen;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent.ClientState;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;

public class ModScreens extends ModRegistries {
   public static void register() {
      ClientLifecycleEvent.CLIENT_SETUP
         .register((ClientState)minecraft -> MenuScreens.register((MenuType)ModScreenHandlers.SELLING_BIN.get(), SellingBinScreen::new));
   }
}
