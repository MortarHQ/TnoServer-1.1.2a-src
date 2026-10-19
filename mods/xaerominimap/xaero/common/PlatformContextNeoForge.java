package xaero.common;

import java.io.IOException;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import xaero.common.cache.BlockStateShortShapeCache;
import xaero.common.events.ClientEvents;
import xaero.common.events.ClientEventsNeoForge;
import xaero.common.events.CommonEvents;
import xaero.common.events.CommonEventsNeoForge;
import xaero.common.events.ModClientEvents;
import xaero.common.events.ModClientEventsNeoForge;
import xaero.common.events.ModCommonEvents;
import xaero.common.events.ModCommonEventsNeoForge;
import xaero.common.minimap.highlight.HighlighterRegistry;
import xaero.common.minimap.write.MinimapWriter;
import xaero.common.minimap.write.MinimapWriterNeoForge;
import xaero.common.mods.SupportMods;
import xaero.common.mods.SupportModsNeoForge;
import xaero.hud.minimap.module.MinimapSession;

public class PlatformContextNeoForge extends PlatformContext {
   private PlatformContextLoaderClientOnlyNeoForge loaderClientOnly;
   private PlatformContextLoaderCommonNeoForge loaderCommon;
   private final HudMod modMain;
   private IEventBus modEventBus;

   public PlatformContextNeoForge(HudMod modMain) {
      this.modMain = modMain;
   }

   public void registerEvents(IEventBus modEventBus) {
      this.modEventBus = modEventBus;
      modEventBus.addListener(this::loadCommonNeoForge);
      modEventBus.addListener(this::loadServerNeoForge);
      modEventBus.addListener(this::loadClientNeoForge);
      NeoForge.EVENT_BUS.register(this.modMain.getCommonEvents());
      modEventBus.register(this.modMain.getModCommonEvents());
      if (FMLLoader.getDist() == Dist.CLIENT) {
         this.registerClientEvents(modEventBus);
      }
   }

   private void registerClientEvents(IEventBus modEventBus) {
      modEventBus.register(this.modMain.getModClientEvents());
      NeoForge.EVENT_BUS.register(this.modMain.getEvents());
   }

   private void loadClientNeoForge(FMLClientSetupEvent event) {
      try {
         this.modMain.loadClient();
         event.enqueueWork(this.modMain::loadLater);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private void loadCommonNeoForge(FMLCommonSetupEvent event) {
      this.modMain.loadCommon();
   }

   private void loadServerNeoForge(FMLDedicatedServerSetupEvent event) {
      this.modMain.loadServer();
      event.enqueueWork(this.modMain::loadLaterServer);
   }

   @Override
   public ClientEvents createClientEvents(HudMod modMain) {
      return new ClientEventsNeoForge(modMain);
   }

   @Override
   public CommonEvents createCommonEvents(HudMod modMain) {
      return new CommonEventsNeoForge(modMain);
   }

   public PlatformContextLoaderClientOnlyNeoForge getLoaderClientOnly() {
      if (this.loaderClientOnly == null) {
         this.loaderClientOnly = new PlatformContextLoaderClientOnlyNeoForge();
      }

      return this.loaderClientOnly;
   }

   public PlatformContextLoaderCommonNeoForge getLoaderCommon() {
      if (this.loaderCommon == null) {
         this.loaderCommon = new PlatformContextLoaderCommonNeoForge();
      }

      return this.loaderCommon;
   }

   @Override
   public ModClientEvents createModClientEvents(IXaeroMinimap modMain) {
      return new ModClientEventsNeoForge(modMain);
   }

   @Override
   public SupportMods createSupportMods(IXaeroMinimap modMain) {
      return new SupportModsNeoForge(modMain);
   }

   @Override
   public ModCommonEvents createModCommonEvents(IXaeroMinimap modMain) {
      return new ModCommonEventsNeoForge(modMain);
   }

   @Override
   public MinimapWriter createMinimapWriter(
      IXaeroMinimap modMain, MinimapSession xaeroMinimapSession, BlockStateShortShapeCache blockStateShortShapeCache, HighlighterRegistry highlighterRegistry
   ) {
      return new MinimapWriterNeoForge(modMain, xaeroMinimapSession, blockStateShortShapeCache, highlighterRegistry);
   }

   @Override
   public String getModInfoVersion() {
      ModContainer modContainer = (ModContainer)ModList.get().getModContainerById(this.modMain.getModId()).get();
      return modContainer.getModInfo().getVersion().toString() + "_neoforge";
   }

   public IEventBus getModEventBus() {
      return this.modEventBus;
   }
}
