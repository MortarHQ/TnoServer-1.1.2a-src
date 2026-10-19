package xaero.map;

import java.io.IOException;
import java.nio.file.Path;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import xaero.map.biome.BiomeGetter;
import xaero.map.cache.BlockStateShortShapeCache;
import xaero.map.events.ClientEventsNeoForge;
import xaero.map.events.CommonEventsNeoForge;
import xaero.map.events.ModClientEventsNeoForge;
import xaero.map.events.ModCommonEventsNeoForge;
import xaero.map.mods.SupportMods;
import xaero.map.mods.SupportModsNeoForge;
import xaero.map.region.OverlayManager;
import xaero.map.server.WorldMapServer;
import xaero.map.server.WorldMapServerNeoForge;

@Mod("xaeroworldmap")
public class WorldMapNeoForge extends WorldMap {
   private String fileLayoutID = "worldmap_neoforge";

   public WorldMapNeoForge(IEventBus fmlEventBus) {
      fmlEventBus.addListener(this::loadCommonNeoForge);
      fmlEventBus.addListener(this::loadClientNeoForge);
      fmlEventBus.addListener(this::loadServerNeoForge);
      this.registerCommonEvents(fmlEventBus);
      if (FMLLoader.getDist() == Dist.CLIENT) {
         this.registerClientEvents(fmlEventBus);
      }
   }

   protected void registerClientEvents(IEventBus fmlEventBus) {
      events = new ClientEventsNeoForge();
      modEvents = new ModClientEventsNeoForge();
      NeoForge.EVENT_BUS.register(events);
      fmlEventBus.register(modEvents);
   }

   protected void registerCommonEvents(IEventBus fmlEventBus) {
      commonEvents = new CommonEventsNeoForge();
      NeoForge.EVENT_BUS.register(commonEvents);
      fmlEventBus.register(new ModCommonEventsNeoForge(fmlEventBus));
   }

   private void loadClientNeoForge(FMLClientSetupEvent event) {
      try {
         this.loadClient();
         event.enqueueWork(this::loadLater);
      } catch (IOException | ClassNotFoundException | NoSuchMethodException | SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
         throw new RuntimeException(e);
      }
   }

   private void loadCommonNeoForge(FMLCommonSetupEvent event) {
      this.loadCommon();
   }

   private void loadServerNeoForge(FMLDedicatedServerSetupEvent event) {
      this.loadServer();
      event.enqueueWork(this::loadLaterServer);
   }

   @Override
   protected Path fetchModFile() {
      return FMLLoader.getLoadingModList().getModFileById("xaeroworldmap").getFile().getFilePath();
   }

   @Override
   protected String getFileLayoutID() {
      return this.fileLayoutID;
   }

   @Override
   protected SupportMods createSupportMods() {
      return new SupportModsNeoForge();
   }

   @Override
   protected WorldMapClientOnly createClientLoad() {
      return new WorldMapClientOnlyNeoForge();
   }

   @Override
   protected WorldMapServer createServerLoad() {
      return new WorldMapServerNeoForge();
   }

   @Override
   public MapWriter createWriter(OverlayManager overlayManager, BlockStateShortShapeCache blockStateShortShapeCache, BiomeGetter biomeGetter) {
      return new MapWriterNeoForge(overlayManager, blockStateShortShapeCache, biomeGetter);
   }

   @Override
   public String getModInfoVersion() {
      ModContainer modContainer = (ModContainer)ModList.get().getModContainerById("xaeroworldmap").get();
      return modContainer.getModInfo().getVersion().toString() + "_neoforge";
   }
}
