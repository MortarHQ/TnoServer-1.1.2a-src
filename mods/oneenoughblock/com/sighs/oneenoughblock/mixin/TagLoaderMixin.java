package com.sighs.oneenoughblock.mixin;

import com.mafuyu404.oneenoughitem.data.Replacements.ProcessingMode;
import com.mafuyu404.oneenoughitem.data.Replacements.Rules;
import com.mafuyu404.oneenoughitem.util.MixinUtils.ReplacementLoader;
import com.mafuyu404.oneenoughitem.util.MixinUtils.ReplacementLoader.CurrentSnapshot;
import com.sighs.oneenoughblock.init.BlockReplacementCache;
import com.sighs.oneenoughblock.init.OEBConfig;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagLoader;
import net.minecraft.tags.TagLoader.EntryWithSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TagLoader.class)
public abstract class TagLoaderMixin<T> {
   @Shadow
   @Final
   private String directory;
   private static final String BLOCKS_TAG_DIR = "tags/blocks";

   @Inject(method = "load(Lnet/minecraft/server/packs/resources/ResourceManager;)Ljava/util/Map;", at = @At("RETURN"))
   private void oneenoughblock$processTags(ResourceManager resourceManager, CallbackInfoReturnable<Map<ResourceLocation, List<EntryWithSource>>> cir) {
      if ("tags/blocks".equals(this.directory)) {
         Map<ResourceLocation, List<EntryWithSource>> tags = (Map<ResourceLocation, List<EntryWithSource>>)cir.getReturnValue();
         if (tags != null && !tags.isEmpty()) {
            CurrentSnapshot snap = ReplacementLoader.loadCurrentSnapshot(resourceManager);
            Map<String, String> currentMap = snap.dataMap();
            Map<String, Rules> currentRules = snap.dataRules();
            boolean fallbackEnabled = currentMap.isEmpty();
            Rules defaultRules = null;

            try {
               OEBConfig dr = OEBConfig.get();
               defaultRules = dr.defaultRules().toRules();
            } catch (Exception var20) {
            }

            for (Entry<ResourceLocation, List<EntryWithSource>> tagEntry : tags.entrySet()) {
               List<EntryWithSource> entries = tagEntry.getValue();
               if (entries != null && !entries.isEmpty()) {
                  Iterator<EntryWithSource> it = entries.iterator();

                  while (it.hasNext()) {
                     EntryWithSource tracked = it.next();
                     TagEntry e = tracked.entry();
                     if (!e.isTag()) {
                        ResourceLocation fromId = e.getId();
                        String fromStr = fromId.toString();
                        String mapped = currentMap.get(fromStr);
                        if (mapped == null && fallbackEnabled) {
                           mapped = BlockReplacementCache.matchBlock(fromStr);
                        }

                        if (mapped != null) {
                           Rules rules = currentRules.getOrDefault(fromStr, defaultRules);
                           boolean shouldReplace = rules != null
                              && rules.tag()
                                 .map(m -> (ProcessingMode)m.get("blocks"))
                                 .map(mode -> mode == ProcessingMode.REPLACE)
                                 .orElse(fallbackEnabled && BlockReplacementCache.shouldReplaceInTagType(fromStr, "blocks"));
                           if (shouldReplace) {
                              it.remove();
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
