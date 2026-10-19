package team.creative.creativecore.client;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens.ScreenConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;
import net.neoforged.neoforge.client.event.ModelEvent.RegisterGeometryLoaders;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.Side;
import team.creative.creativecore.client.render.model.CreativeBlockModel;
import team.creative.creativecore.client.render.model.CreativeItemModel;
import team.creative.creativecore.client.render.model.CreativeModelLoader;
import team.creative.creativecore.common.config.gui.ConfigGuiLayer;
import team.creative.creativecore.common.config.holder.CreativeConfigRegistry;
import team.creative.creativecore.common.config.holder.ICreativeConfigHolder;
import team.creative.creativecore.common.gui.IScaleableGuiScreen;
import team.creative.creativecore.common.gui.integration.ContainerIntegration;
import team.creative.creativecore.common.gui.integration.ContainerScreenIntegration;
import team.creative.creativecore.common.gui.integration.GuiEventHandler;
import team.creative.creativecore.common.gui.integration.GuiScreenIntegration;
import team.creative.creativecore.common.gui.style.GuiStyle;
import team.creative.creativecore.common.util.registry.LocatedHandlerRegistry;

public class CreativeCoreClient {
   private static final Minecraft mc = Minecraft.getInstance();
   public static final LocatedHandlerRegistry<CreativeBlockModel> BLOCK_MODEL_TYPES = new LocatedHandlerRegistry<>(null).allowOverwrite();
   public static final LocatedHandlerRegistry<CreativeItemModel> ITEM_MODEL_TYPES = new LocatedHandlerRegistry<>(null).allowOverwrite();
   private static final ItemColor ITEM_COLOR = (stack, tint) -> tint;

   public static void load(IEventBus bus) {
      bus.addListener(CreativeCoreClient::init);
      bus.addListener(CreativeCoreClient::modelEvent);
      bus.addListener(CreativeCoreClient::screenEvent);
   }

   public static void registerClientConfig(String modid) {
      ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (a, b) -> {
         ICreativeConfigHolder holder = CreativeConfigRegistry.ROOT.followPath(modid);
         return holder != null && !holder.isEmpty(Side.CLIENT) ? new GuiScreenIntegration(new ConfigGuiLayer(holder, Side.CLIENT)) : null;
      });
   }

   public static void registerBlockModel(ResourceLocation location, CreativeBlockModel renderer) {
      BLOCK_MODEL_TYPES.register(location, renderer);
   }

   public static void registerItemModel(ResourceLocation location, CreativeItemModel renderer) {
      ITEM_MODEL_TYPES.register(location, renderer);
   }

   public static void registerItemColor(ItemColors colors, Item item) {
      colors.register(ITEM_COLOR, new ItemLike[]{item});
   }

   public static float getFrameTime() {
      return mc.isPaused() ? 1.0F : mc.getTimer().getGameTimeDeltaPartialTick(false);
   }

   @SubscribeEvent
   public static void commands(RegisterClientCommandsEvent event) {
      event.getDispatcher().register((LiteralArgumentBuilder)LiteralArgumentBuilder.literal("cmdclientconfig").executes(x -> {
         try {
            GuiEventHandler.queueScreen(new GuiScreenIntegration(new ConfigGuiLayer(CreativeConfigRegistry.ROOT, Side.CLIENT)));
         } catch (Exception e) {
            CreativeCore.LOGGER.error(e);
         }

         return 1;
      }));
   }

   public static void init(FMLClientSetupEvent event) {
      NeoForge.EVENT_BUS.register(CreativeCoreClient.class);
      NeoForge.EVENT_BUS.register(GuiEventHandler.class);
      GuiStyle.reload();
      Minecraft minecraft = Minecraft.getInstance();
      ReloadableResourceManager reloadableResourceManager = (ReloadableResourceManager)minecraft.getResourceManager();
      reloadableResourceManager.registerReloadListener(new SimplePreparableReloadListener() {
         protected Object prepare(ResourceManager p_10796_, ProfilerFiller p_10797_) {
            return GuiStyle.class;
         }

         protected void apply(Object p_10793_, ResourceManager p_10794_, ProfilerFiller p_10795_) {
            GuiStyle.reload();
         }
      });
   }

   public static void modelEvent(RegisterGeometryLoaders event) {
      event.register(ResourceLocation.tryBuild("creativecore", "rendered"), new CreativeModelLoader());
   }

   public static void screenEvent(RegisterMenuScreensEvent event) {
      event.register(CreativeCore.GUI_CONTAINER, new ScreenConstructor<ContainerIntegration, ContainerScreenIntegration>() {
         public ContainerScreenIntegration create(ContainerIntegration container, Inventory inventory, Component p_create_3_) {
            return new ContainerScreenIntegration(container, inventory);
         }
      });
   }

   @SubscribeEvent
   public static void clientTick(Pre event) {
      if (Minecraft.getInstance().screen instanceof IScaleableGuiScreen gui) {
         gui.clientTick();
      }
   }
}
