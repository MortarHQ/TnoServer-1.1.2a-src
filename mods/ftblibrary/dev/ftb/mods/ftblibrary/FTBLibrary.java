package dev.ftb.mods.ftblibrary;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import dev.ftb.mods.ftblibrary.api.color.RegisterCustomColorEvent;
import dev.ftb.mods.ftblibrary.config.FTBLibraryClientConfig;
import dev.ftb.mods.ftblibrary.config.FTBLibraryServerConfig;
import dev.ftb.mods.ftblibrary.config.FTBLibraryStartupConfig;
import dev.ftb.mods.ftblibrary.config.manager.ConfigManager;
import dev.ftb.mods.ftblibrary.items.ModItems;
import dev.ftb.mods.ftblibrary.nbtedit.NBTEditResponseHandlers;
import dev.ftb.mods.ftblibrary.net.FTBLibraryNet;
import dev.ftb.mods.ftblibrary.net.SyncKnownServerRegistriesPacket;
import dev.ftb.mods.ftblibrary.util.KnownServerRegistries;
import dev.ftb.mods.ftblibrary.util.ModUtils;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import dev.ftb.mods.ftblibrary.util.text.ExtendableTextColor;
import dev.ftb.mods.ftblibrary.util.text.RainbowTextColor;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTab;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FTBLibrary {
   public static final String MOD_ID = "ftblibrary";
   public static final String MOD_NAME = "FTB Library";
   public static final Logger LOGGER = LogManager.getLogger("FTB Library");

   public FTBLibrary() {
      ConfigManager cfgMgr = ConfigManager.getInstance();
      cfgMgr.init();
      cfgMgr.registerClientConfig(FTBLibraryClientConfig.CONFIG, "ftblibrary.client_settings");
      if (ModUtils.isDevMode()) {
         cfgMgr.registerStartupConfig(FTBLibraryStartupConfig.CONFIG, "ftblibrary.startup_settings");
         cfgMgr.registerServerConfig(FTBLibraryServerConfig.CONFIG, "ftblibrary.server_settings", true, FTBLibraryServerConfig::onChanged);
      }

      CommandRegistrationEvent.EVENT.register(FTBLibraryCommands::registerCommands);
      FTBLibraryNet.register();
      LifecycleEvent.SERVER_STARTED.register(this::serverStarted);
      LifecycleEvent.SERVER_STOPPED.register(this::serverStopped);
      PlayerEvent.PLAYER_JOIN.register(this::playerJoined);
      ModItems.init();
      EnvExecutor.runInEnv(Env.CLIENT, () -> FTBLibraryClient::init);
      RegisterCustomColorEvent.EVENT.register((Consumer<RegisterCustomColorEvent>)event -> event.register("ftb:rainbow", RainbowTextColor.INSTANCE));
      LifecycleEvent.SETUP.register(this::onSetup);
   }

   private void onSetup() {
      Map<String, TextColor> customColors = new HashMap<>();
      ((Consumer)RegisterCustomColorEvent.EVENT.invoker()).accept(new RegisterCustomColorEvent(customColors));
      customColors.forEach(ExtendableTextColor::addCustomColor);
   }

   public static ResourceLocation rl(String path) {
      return ResourceLocation.fromNamespaceAndPath("ftblibrary", path);
   }

   public static DeferredSupplier<CreativeModeTab> getCreativeModeTab() {
      return ModItems.FTB_LIBRARY_TAB;
   }

   private void serverStarted(MinecraftServer server) {
      KnownServerRegistries.server = KnownServerRegistries.create(server);
      NBTEditResponseHandlers.registerBuiltinHandlers();
   }

   private void serverStopped(MinecraftServer server) {
      KnownServerRegistries.server = null;
   }

   private void playerJoined(ServerPlayer player) {
      if (Platform.isDevelopmentEnvironment()) {
         player.sendSystemMessage(Component.literal("Hello from FTB Library!").withStyle(Style.EMPTY.withColor(RainbowTextColor.INSTANCE)));
      }

      if (KnownServerRegistries.server != null) {
         NetworkHelper.sendTo(player, new SyncKnownServerRegistriesPacket(KnownServerRegistries.server));
      }
   }
}
