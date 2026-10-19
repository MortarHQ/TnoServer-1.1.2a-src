package nadiendev.constructionwand;

import nadiendev.constructionwand.basics.ConfigClient;
import nadiendev.constructionwand.basics.ConfigServer;
import nadiendev.constructionwand.basics.ModStats;
import nadiendev.constructionwand.client.ClientEvents;
import nadiendev.constructionwand.client.RenderBlockPreview;
import nadiendev.constructionwand.client.property.SelectWandCore;
import nadiendev.constructionwand.component.ModDataComponents;
import nadiendev.constructionwand.containers.ContainerManager;
import nadiendev.constructionwand.containers.ContainerRegistrar;
import nadiendev.constructionwand.crafting.ModRecipes;
import nadiendev.constructionwand.creative.ModCreativeTabs;
import nadiendev.constructionwand.events.VoidSackDropHandler;
import nadiendev.constructionwand.events.VoidSackPickupHandler;
import nadiendev.constructionwand.items.ModItems;
import nadiendev.constructionwand.network.ModMessages;
import nadiendev.constructionwand.registry.ModMenuTypes;
import nadiendev.constructionwand.wand.undo.UndoHistory;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("constructionwand")
public class ConstructionWand {
   public static final String MODID = "constructionwand";
   public static final String MODNAME = "ConstructionWandRevived";
   public static ConstructionWand instance;
   public static final Logger LOGGER = LogManager.getLogger();
   public ContainerManager containerManager;
   public UndoHistory undoHistory;
   public RenderBlockPreview renderBlockPreview;

   public ConstructionWand(IEventBus eventBus, ModContainer container, Dist dist) {
      instance = this;
      this.containerManager = new ContainerManager();
      this.undoHistory = new UndoHistory();
      eventBus.addListener(this::commonSetup);
      eventBus.addListener(this::clientSetup);
      eventBus.addListener(ModMessages::registerPayloads);
      ModDataComponents.DATA_COMPONENT_TYPES.register(eventBus);
      ModItems.ITEMS.register(eventBus);
      ModRecipes.RECIPE_SERIALIZERS.register(eventBus);
      ModCreativeTabs.register(eventBus);
      ModMenuTypes.register(eventBus);
      ModStats.CUSTOM_STATS.register(eventBus);
      container.registerConfig(Type.SERVER, ConfigServer.SPEC);
      container.registerConfig(Type.CLIENT, ConfigClient.SPEC);
      NeoForge.EVENT_BUS.register(new VoidSackDropHandler());
      NeoForge.EVENT_BUS.register(new VoidSackPickupHandler());
   }

   private void commonSetup(FMLCommonSetupEvent event) {
      LOGGER.info("ConstructionWand says hello - may the odds be ever in your favor.");
      ContainerRegistrar.register();
   }

   private void clientSetup(FMLClientSetupEvent event) {
      this.renderBlockPreview = new RenderBlockPreview();
      NeoForge.EVENT_BUS.register(this.renderBlockPreview);
      NeoForge.EVENT_BUS.register(new ClientEvents());
      SelectWandCore.onClientSetup(event);
   }

   public static ResourceLocation loc(String name) {
      return ResourceLocation.fromNamespaceAndPath("constructionwand", name);
   }
}
