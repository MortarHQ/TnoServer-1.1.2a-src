package com.chronodawn.neoforge.event;

import com.chronodawn.ChronoDawn;
import com.chronodawn.worldgen.runtime.OverlayPackBootstrap;
import java.nio.file.Path;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources.PathResourcesSupplier;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.Pack.Position;
import net.neoforged.neoforge.event.AddPackFindersEvent;

public final class OverlayPackFinder {
   public static final String PACK_DISPLAY_NAME = "ChronoDawn config overlay";

   private OverlayPackFinder() {
   }

   public static void onAddPackFinders(AddPackFindersEvent event) {
      if (event.getPackType() == PackType.SERVER_DATA) {
         Path overlayPath = OverlayPackBootstrap.getOverlayPath();
         if (overlayPath != null) {
            PackLocationInfo info = new PackLocationInfo(
               "chronodawn-runtime-overlay", Component.literal("ChronoDawn config overlay"), PackSource.BUILT_IN, Optional.empty()
            );
            PackSelectionConfig selection = new PackSelectionConfig(true, Position.TOP, false);
            Pack pack = Pack.readMetaAndCreate(info, new PathResourcesSupplier(overlayPath), PackType.SERVER_DATA, selection);
            if (pack == null) {
               ChronoDawn.LOGGER.warn("Runtime overlay pack at {} failed metadata validation; not registering", overlayPath);
            } else {
               event.addRepositorySource(consumer -> consumer.accept(pack));
               ChronoDawn.LOGGER.info("Registered runtime config overlay datapack with NeoForge");
            }
         }
      }
   }
}
