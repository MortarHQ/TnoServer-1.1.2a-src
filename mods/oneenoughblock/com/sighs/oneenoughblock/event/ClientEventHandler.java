package com.sighs.oneenoughblock.event;

import cc.sighs.oelib.neoforge.data.DataManager;
import cc.sighs.oelib.neoforge.event.DataReloadEvent;
import com.mafuyu404.oneenoughitem.data.Replacements;
import com.mafuyu404.oneenoughitem.event.base.AbstractReplacementEventHandler;
import com.sighs.oneenoughblock.client.gui.cache.GlobalBlockReplacementCache;
import com.sighs.oneenoughblock.init.BlockReplacementCache;
import com.sighs.oneenoughblock.init.OEBConfig;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = "oneenoughblock", value = Dist.CLIENT)
public class ClientEventHandler {
   private static final ClientEventHandler.Handler HANDLER = new ClientEventHandler.Handler();

   @SubscribeEvent
   public static void onDataReload(DataReloadEvent event) {
      if (event.isDataType(Replacements.class)) {
         RegistryLookup<Block> registryLookup = Minecraft.getInstance().level != null
            ? Minecraft.getInstance().level.registryAccess().lookupOrThrow(Registries.BLOCK)
            : null;
         Minecraft.getInstance().execute(() -> {
            HANDLER.rebuildReplacementCache("oeb-client-data-reload", DataManager.get(Replacements.class), registryLookup);
            GlobalBlockReplacementCache.get().rebuild();
            BlockReplacementCache.endReloadOverride();
         });
      }
   }

   private static class Handler extends AbstractReplacementEventHandler<Block> {
      protected void clearModuleCache() {
         BlockReplacementCache.clearCache();
      }

      protected void putToModuleCache(Replacements r, RegistryLookup<Block> registryLookup) {
         BlockReplacementCache.putReplacement(this.buildReplacements(r));
      }

      protected boolean tryResolveData(String id, RegistryLookup<Block> registryLookup) {
         BuiltInRegistries.BLOCK.get(ResourceLocation.parse(id));
         return true;
      }

      protected boolean tryResolveTag(String tagId, RegistryLookup<Block> registryLookup) {
         TagKey<Block> tag = TagKey.create(Registries.BLOCK, ResourceLocation.parse(tagId));

         for (Block b : BuiltInRegistries.BLOCK) {
            if (b.builtInRegistryHolder().is(tag)) {
               return true;
            }
         }

         return false;
      }

      protected Replacements buildReplacements(Replacements r) {
         OEBConfig dr = OEBConfig.get();
         return r.rules().isEmpty() && dr != null ? new Replacements(r.match(), r.result(), Optional.of(dr.defaultRules().toRules())) : r;
      }
   }
}
