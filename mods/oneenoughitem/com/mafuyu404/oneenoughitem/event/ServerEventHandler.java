package com.mafuyu404.oneenoughitem.event;

import cc.sighs.oelib.neoforge.data.DataManager;
import cc.sighs.oelib.neoforge.event.DataReloadEvent;
import com.mafuyu404.oneenoughitem.Oneenoughitem;
import com.mafuyu404.oneenoughitem.data.Replacements;
import com.mafuyu404.oneenoughitem.event.base.AbstractReplacementEventHandler;
import com.mafuyu404.oneenoughitem.init.ItemReplacementCache;
import com.mafuyu404.oneenoughitem.init.config.OEIConfig;
import com.mafuyu404.oneenoughitem.util.Utils;
import java.util.Optional;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@EventBusSubscriber(modid = "oneenoughitem")
public class ServerEventHandler {
   private static final ServerEventHandler.Handler HANDLER = new ServerEventHandler.Handler();

   @SubscribeEvent
   public static void onServerStarted(ServerStartedEvent event) {
      MinecraftServer server = event.getServer();
      RegistryLookup<Item> registryLookup = server.registryAccess().lookupOrThrow(Registries.ITEM);
      HANDLER.rebuildReplacementCache("oei-server-start", DataManager.get(Replacements.class), registryLookup);
   }

   @SubscribeEvent
   public static void onDataReload(DataReloadEvent event) {
      if (event.isDataType(Replacements.class)) {
         MinecraftServer server = DataManager.getCurrentServer();
         RegistryLookup<Item> registryLookup = server != null ? server.registryAccess().lookupOrThrow(Registries.ITEM) : null;
         HANDLER.rebuildReplacementCache("server-data-reload", DataManager.get(Replacements.class), registryLookup);
         Oneenoughitem.LOGGER
            .info("Server replacement cache rebuilt due to data reload: {} entries loaded, {} invalid", event.getLoadedCount(), event.getInvalidCount());
         ItemReplacementCache.endReloadOverride();
      }
   }

   private static class Handler extends AbstractReplacementEventHandler<Item> {
      @Override
      protected void clearModuleCache() {
         ItemReplacementCache.clearCache();
      }

      @Override
      protected void putToModuleCache(Replacements r, RegistryLookup<Item> registryLookup) {
         ItemReplacementCache.putReplacement(this.buildReplacements(r), registryLookup);
      }

      @Override
      protected boolean tryResolveData(String id, RegistryLookup<Item> registryLookup) {
         return Utils.getItemById(id) != null;
      }

      @Override
      protected boolean tryResolveTag(String tagId, RegistryLookup<Item> registryLookup) {
         return Utils.isTagExists(ResourceLocation.parse(tagId), registryLookup);
      }

      @Override
      protected Replacements buildReplacements(Replacements r) {
         OEIConfig dr = OEIConfig.get();
         return r.rules().isEmpty() && dr != null ? new Replacements(r.match(), r.result(), Optional.of(dr.defaultRules().toRules())) : r;
      }

      @Override
      protected boolean acceptLocation(ResourceLocation location) {
         return "oei".equals(location.getNamespace());
      }
   }
}
