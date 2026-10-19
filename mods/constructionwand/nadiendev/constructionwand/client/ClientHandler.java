package nadiendev.constructionwand.client;

import nadiendev.constructionwand.client.screen.ScreenVoidSack;
import nadiendev.constructionwand.registry.ModMenuTypes;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;

@EventBusSubscriber(modid = "constructionwand", bus = Bus.MOD, value = Dist.CLIENT)
public class ClientHandler {
   public static RenderBlockPreview renderBlockPreview;

   @SubscribeEvent
   public static void onClientSetup(FMLClientSetupEvent event) {
      renderBlockPreview = new RenderBlockPreview();
      NeoForge.EVENT_BUS.register(renderBlockPreview);
      NeoForge.EVENT_BUS.register(new KeybindHandler());
   }

   @SubscribeEvent
   public static void registerKeymapping(RegisterKeyMappingsEvent event) {
      event.register(KeybindHandler.KEY_VOID_SACK_TOGGLE);
      event.register(KeybindHandler.KEY_WAND_UNDO);
   }

   @SubscribeEvent
   public static void registerMenuScreens(RegisterMenuScreensEvent event) {
      event.register((MenuType)ModMenuTypes.VOID_SACK.get(), ScreenVoidSack::new);
   }
}
