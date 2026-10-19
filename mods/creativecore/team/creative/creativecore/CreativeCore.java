package team.creative.creativecore;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.function.Supplier;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.MenuType.MenuSupplier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.creative.creativecore.client.CreativeCoreClient;
import team.creative.creativecore.common.config.event.ConfigEventHandler;
import team.creative.creativecore.common.config.gui.ClientSyncGuiLayer;
import team.creative.creativecore.common.config.gui.ConfigGuiLayer;
import team.creative.creativecore.common.config.holder.CreativeConfigRegistry;
import team.creative.creativecore.common.config.sync.ConfigurationChangePacket;
import team.creative.creativecore.common.config.sync.ConfigurationClientPacket;
import team.creative.creativecore.common.config.sync.ConfigurationPacket;
import team.creative.creativecore.common.gui.creator.GuiCreator;
import team.creative.creativecore.common.gui.integration.ContainerIntegration;
import team.creative.creativecore.common.gui.packet.ControlSyncPacket;
import team.creative.creativecore.common.gui.packet.ImmediateItemStackPacket;
import team.creative.creativecore.common.gui.packet.LayerClosePacket;
import team.creative.creativecore.common.gui.packet.OpenGuiPacket;
import team.creative.creativecore.common.gui.packet.SyncPacket;
import team.creative.creativecore.common.loader.ForgeLoaderUtils;
import team.creative.creativecore.common.loader.ILoaderUtils;
import team.creative.creativecore.common.network.CreativeNetwork;
import team.creative.creativecore.common.util.argument.StringArrayArgumentType;

@Mod("creativecore")
public class CreativeCore {
   private static final ICreativeLoader LOADER = new CreativeForgeLoader();
   private static final ILoaderUtils UTILS = new ForgeLoaderUtils();
   public static final String MODID = "creativecore";
   public static final Logger LOGGER = LogManager.getLogger("creativecore");
   public static final CreativeCoreConfig CONFIG = new CreativeCoreConfig();
   public static final CreativeNetwork NETWORK = new CreativeNetwork(1, LOGGER, ResourceLocation.tryBuild("creativecore", "main"));
   public static ConfigEventHandler CONFIG_HANDLER;
   public static MenuType<ContainerIntegration> GUI_CONTAINER;
   public static final GuiCreator.GuiCreatorBasic CONFIG_OPEN = GuiCreator.register(
      "config", new GuiCreator.GuiCreatorBasic((player, nbt) -> new ConfigGuiLayer(CreativeConfigRegistry.ROOT, Side.SERVER))
   );
   public static final GuiCreator.GuiCreatorBasic CONFIG_CLIENT_SYNC_OPEN = GuiCreator.register(
      "clientconfig", new GuiCreator.GuiCreatorBasic((player, nbt) -> new ClientSyncGuiLayer(CreativeConfigRegistry.ROOT))
   );
   public static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES = DeferredRegister.create(
      Registries.COMMAND_ARGUMENT_TYPE, "creativecore"
   );
   public static final Supplier<SingletonArgumentInfo<StringArrayArgumentType>> STRING_ARRAY_ARGUMENT_TYPE = COMMAND_ARGUMENT_TYPES.register(
      "string_array",
      () -> (SingletonArgumentInfo)ArgumentTypeInfos.registerByClass(
         StringArrayArgumentType.class, SingletonArgumentInfo.contextFree(StringArrayArgumentType::stringArray)
      )
   );

   public CreativeCore() {
      ModLoadingContext.get().getActiveContainer().getEventBus().addListener(this::init);
      ModLoadingContext.get().getActiveContainer().getEventBus().addListener(this::registerMenus);
      COMMAND_ARGUMENT_TYPES.register(ModLoadingContext.get().getActiveContainer().getEventBus());
      NeoForge.EVENT_BUS.addListener(this::server);
      if (FMLLoader.getDist() == Dist.CLIENT) {
         CreativeCoreClient.load(ModLoadingContext.get().getActiveContainer().getEventBus());
      }

      GUI_CONTAINER = new MenuType<ContainerIntegration>(null, FeatureFlags.VANILLA_SET) {
         public ContainerIntegration create(int windowId, Inventory playerInv, RegistryFriendlyByteBuf extraData) {
            return new ContainerIntegration(this, windowId, playerInv.player);
         }

         public ContainerIntegration create(int windowId, Inventory playerInv) {
            return new ContainerIntegration(this, windowId, playerInv.player);
         }
      };
   }

   public void registerMenus(RegisterEvent event) {
      event.register(Registries.MENU, x -> x.register(ResourceLocation.tryBuild("creativecore", "container"), GUI_CONTAINER));
   }

   private void server(ServerStartingEvent event) {
      event.getServer().getCommands().getDispatcher().register((LiteralArgumentBuilder)Commands.literal("cmdconfig").executes(x -> {
         CONFIG_OPEN.open(new CompoundTag(), ((CommandSourceStack)x.getSource()).getPlayerOrException());
         return 0;
      }));
   }

   private void init(FMLCommonSetupEvent event) {
      NETWORK.registerType(ConfigurationChangePacket.class, ConfigurationChangePacket::new);
      NETWORK.registerType(ConfigurationClientPacket.class, ConfigurationClientPacket::new);
      NETWORK.registerType(ConfigurationPacket.class, ConfigurationPacket::new);
      NETWORK.registerType(LayerClosePacket.class, LayerClosePacket::new);
      NETWORK.registerType(OpenGuiPacket.class, OpenGuiPacket::new);
      NETWORK.registerType(ControlSyncPacket.class, ControlSyncPacket::new);
      NETWORK.registerType(SyncPacket.class, SyncPacket::new);
      NETWORK.registerType(ImmediateItemStackPacket.class, ImmediateItemStackPacket::new);
      CONFIG_HANDLER = new ConfigEventHandler(FMLPaths.CONFIGDIR.get().toFile(), LOGGER);
      CreativeConfigRegistry.ROOT.registerValue("creativecore", CONFIG);
      LOADER.loadCommon();
   }

   public static ICreativeLoader loader() {
      return LOADER;
   }

   public static ILoaderUtils utils() {
      return UTILS;
   }
}
