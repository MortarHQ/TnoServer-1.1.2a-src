package net.jadenxgamer.netherexp.core.misc;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.util.CompatUtil;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources.PathResourcesSupplier;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.Pack.Metadata;
import net.minecraft.server.packs.repository.Pack.Position;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;

public class JNEBuiltinPacks {
   public static void rpJNERetextures(AddPackFindersEvent event) {
      Path path = ModList.get().getModFileById("netherexp").getFile().findResource(new String[]{"resourcepacks/jne_retextures"});
      PackMetadataSection metadata = new PackMetadataSection(
         Component.literal("Improves various vanilla textures to fit with JNE"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES)
      );
      event.addRepositorySource(
         source -> source.accept(
            new Pack(
               new PackLocationInfo("netherexp:jne_retextures", Component.literal("JNE Retextures"), PackSource.BUILT_IN, Optional.empty()),
               new PathResourcesSupplier(path),
               new Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
               new PackSelectionConfig(true, Position.TOP, false)
            )
         )
      );
   }

   public static void dpNetherWorldgenOverhaul(AddPackFindersEvent event) {
      if (!CompatUtil.AMPLIFIED_NETHER && (Boolean)JNEConfigs.NETHER_WORLDGEN_OVERHAUL.get()) {
         Path path = ModList.get().getModFileById("netherexp").getFile().findResource(new String[]{"resourcepacks/nether_worldgen_overhaul"});
         PackMetadataSection metadata = new PackMetadataSection(
            Component.literal("Overhauls the nether world generation"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA)
         );
         event.addRepositorySource(
            source -> source.accept(
               new Pack(
                  new PackLocationInfo(
                     "netherexp:nether_worldgen_overhaul", Component.literal("JNE Nether Worldgen Overhaul"), PackSource.BUILT_IN, Optional.empty()
                  ),
                  new PathResourcesSupplier(path),
                  new Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                  new PackSelectionConfig(true, Position.TOP, false)
               )
            )
         );
      }
   }

   public static void dpNetherMosaicBiomeSource(AddPackFindersEvent event) {
      if ((Boolean)JNEConfigs.IMPROVED_NETHER_BIOME_SOURCE.get()) {
         Path path = ModList.get().getModFileById("netherexp").getFile().findResource(new String[]{"resourcepacks/nether_mosaic_biome_source"});
         PackMetadataSection metadata = new PackMetadataSection(
            Component.literal("Overrides the nether multi-noise biome source"), SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA)
         );
         event.addRepositorySource(
            source -> source.accept(
               new Pack(
                  new PackLocationInfo(
                     "netherexp:nether_mosaic_biome_source", Component.literal("JNE Nether Mosaic Biome Source"), PackSource.BUILT_IN, Optional.empty()
                  ),
                  new PathResourcesSupplier(path),
                  new Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                  new PackSelectionConfig(true, Position.TOP, false)
               )
            )
         );
      }
   }
}
