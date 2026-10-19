package com.mafuyu404.oneenoughitem.event;

import cc.sighs.oelib.neoforge.data.DataManager;
import cc.sighs.oelib.neoforge.event.DataReloadEvent;
import com.mafuyu404.oneenoughitem.Oneenoughitem;
import com.mafuyu404.oneenoughitem.client.ModKeyMappings;
import com.mafuyu404.oneenoughitem.client.gui.ReplacementEditorScreen;
import com.mafuyu404.oneenoughitem.client.gui.cache.ItemGlobalReplacementCache;
import com.mafuyu404.oneenoughitem.data.Replacements;
import com.mafuyu404.oneenoughitem.event.base.AbstractReplacementEventHandler;
import com.mafuyu404.oneenoughitem.init.ItemReplacementCache;
import com.mafuyu404.oneenoughitem.init.access.CreativeModeTabIconRefresher;
import com.mafuyu404.oneenoughitem.init.config.OEIConfig;
import com.mafuyu404.oneenoughitem.util.Utils;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = "oneenoughitem", value = Dist.CLIENT)
public class ClientEventHandler {
   private static final ClientEventHandler.Handler HANDLER = new ClientEventHandler.Handler();

   @SubscribeEvent
   public static void onClientTick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (ModKeyMappings.OPEN_EDITOR.consumeClick() && mc.screen == null && hasCtrlDown()) {
         mc.setScreen(new ReplacementEditorScreen());
      }
   }

   @SubscribeEvent
   public static void onDataReload(DataReloadEvent event) {
      if (event.isDataType(Replacements.class)) {
         Minecraft.getInstance()
            .execute(
               () -> {
                  RegistryLookup<Item> registryLookup = Minecraft.getInstance().level != null
                     ? Minecraft.getInstance().level.registryAccess().lookupOrThrow(Registries.ITEM)
                     : null;
                  HANDLER.rebuildReplacementCache("client-data-reload", DataManager.get(Replacements.class), registryLookup);
                  ItemGlobalReplacementCache.get().rebuild();
                  refreshAllCreativeModeTabIcons();
                  Oneenoughitem.LOGGER
                     .info("Replacement cache rebuilt due to data reload: {} entries loaded, {} invalid", event.getLoadedCount(), event.getInvalidCount());
                  ItemReplacementCache.endReloadOverride();
               }
            );
      }
   }

   private static boolean hasCtrlDown() {
      long window = Minecraft.getInstance().getWindow().getWindow();
      return GLFW.glfwGetKey(window, 341) == 1 || GLFW.glfwGetKey(window, 345) == 1;
   }

   private static void refreshAllCreativeModeTabIcons() {
      try {
         for (CreativeModeTab tab : CreativeModeTabs.tabs()) {
            if (tab instanceof CreativeModeTabIconRefresher refresher) {
               refresher.oei$refreshIconCache();
            }
         }

         Oneenoughitem.LOGGER.info("Refreshed creative mode tab icons after replacement reload");
      } catch (Exception e) {
         Oneenoughitem.LOGGER.warn("Failed to refresh creative mode tab icons", e);
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
