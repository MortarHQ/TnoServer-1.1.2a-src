package de.cristelknight.cristellib.neoforge.client;

import com.mojang.datafixers.util.Pair;
import de.cristelknight.cristellib.autoconfig.ACConfig;
import de.cristelknight.cristellib.config.client.ScreenBuilder;
import de.cristelknight.cristellib.config.simple.ConfigRegistry;
import java.util.Optional;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class NeoForgeClient {
   public static void registerMainConfigScreen() {
      ModLoadingContext.get()
         .registerExtensionPoint(IConfigScreenFactory.class, () -> (mc, screenFactory) -> new ScreenBuilder("cristellib").create(screenFactory, true, true));
   }

   public static void addOtherConfigScreens() {
      ACConfig acConfig = ConfigRegistry.get(ACConfig.class);
      boolean structureMain = !acConfig.disableAutoConfig() && !acConfig.disableAutoConfigScreens();

      for (String modID : ScreenBuilder.allConfigMods(structureMain)) {
         Pair<Boolean, Boolean> structureSimple = ScreenBuilder.shouldCreateScreen(modID, structureMain);
         boolean structure = (Boolean)structureSimple.getFirst();
         boolean simple = (Boolean)structureSimple.getSecond();
         if (structure || simple) {
            Optional<? extends ModContainer> container = ModList.get().getModContainerById(modID);
            if (!container.isEmpty() && !container.get().getCustomExtension(IConfigScreenFactory.class).isPresent()) {
               container.get()
                  .registerExtensionPoint(
                     IConfigScreenFactory.class, (IConfigScreenFactory)(mc, screenFactory) -> new ScreenBuilder(modID).create(screenFactory, structure, simple)
                  );
            }
         }
      }
   }

   public static void onLoadComplete(FMLLoadCompleteEvent event) {
      event.enqueueWork(NeoForgeClient::addOtherConfigScreens);
   }
}
