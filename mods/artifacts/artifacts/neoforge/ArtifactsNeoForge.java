package artifacts.neoforge;

import artifacts.Artifacts;
import artifacts.config.screen.ArtifactsConfigScreen;
import artifacts.integration.ModCompat;
import artifacts.neoforge.event.ArtifactHooksNeoForge;
import artifacts.neoforge.integration.curios.CuriosCompat;
import artifacts.neoforge.network.NeoForgeNetworkHandler;
import artifacts.neoforge.registry.ModAttachmentTypes;
import artifacts.neoforge.registry.ModConditions;
import artifacts.neoforge.registry.ModLootModifiers;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModEntityTypes;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod("artifacts")
public class ArtifactsNeoForge {
   private static IEventBus modBus;

   public ArtifactsNeoForge(IEventBus modBus) {
      ArtifactsNeoForge.modBus = modBus;
      Artifacts.setup();
      if (FMLEnvironment.dist == Dist.CLIENT) {
         new ArtifactsNeoForgeClient(modBus);
      }

      ModConditions.CONDITIONS.register(modBus);
      ModLootModifiers.LOOT_MODIFIERS.register(modBus);
      ModAttachmentTypes.ATTACHMENT_TYPES.register(modBus);
      modBus.addListener(ArtifactsData::gatherData);
      modBus.addListener(NeoForgeNetworkHandler::registerPayloadHandlers);
      NeoForge.EVENT_BUS.addListener(event -> Artifacts.onServerStarting(event.getServer()));
      NeoForge.EVENT_BUS.addListener(event -> Artifacts.onServerStopping());
      NeoForge.EVENT_BUS.addListener(this::onPlayerLoggedIn);
      modBus.addListener(event -> ModEntityTypes.registerMobAttributes(event::put));
      this.registerConfigScreen();
      ArtifactHooksNeoForge.register();
      if (PlatformServices.platformHelper.isModLoaded("curios")) {
         CuriosCompat.setup();
      }

      ArtifactsNeoForge.modBus = null;
   }

   private void registerConfigScreen() {
      if (PlatformServices.platformHelper.isModLoaded(ModCompat.CLOTH_CONFIG)) {
         ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (client, parent) -> new ArtifactsConfigScreen(parent).build());
      }
   }

   private void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         Artifacts.onPlayerJoin(player);
      }
   }

   public static void addDeferredRegister(DeferredRegister<?> register) {
      register.register(modBus);
   }
}
