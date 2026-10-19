package com.blackgear.platform.core.events.neoforge;

import com.blackgear.platform.core.events.ResourcePackManager;
import com.blackgear.platform.core.util.EventBus;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.DetectedVersion;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.Pack.Metadata;
import net.minecraft.server.packs.repository.Pack.Position;
import net.minecraft.server.packs.repository.Pack.ResourcesSupplier;
import net.minecraft.server.packs.resources.IoSupplier;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.locating.IModFile;
import org.jetbrains.annotations.Nullable;

public class ResourcePackManagerImpl {
   public static void registerPack(Consumer<ResourcePackManager.Event> listener) {
      EventBus.get(EventBus.MOD).addListener(event -> listener.accept((type, source) -> {
         if (event.getPackType() == type) {
            event.addRepositorySource(source);
         }
      }));
   }

   public static void registerBuiltResourcePack(ResourceLocation packId, String modId, String packName) {
      IModFile file = ModList.get().getModFileById(modId).getFile();
      registerPack(
         event -> event.register(
            PackType.CLIENT_RESOURCES,
            () -> Pack.readMetaAndCreate(
               new PackLocationInfo(
                  packId.toString(),
                  Component.literal(packName),
                  PackSource.DEFAULT,
                  Optional.of(new KnownPack(modId, packName, SharedConstants.getCurrentVersion().getId()))
               ),
               new ResourcePackManagerImpl.ModFilePackResources.ModPathResourcesSupplier(file, Path.of("resourcepacks/" + packId.getPath())),
               PackType.CLIENT_RESOURCES,
               new PackSelectionConfig(false, Position.TOP, false)
            )
         )
      );
   }

   private static class ModFilePackResources extends PathPackResources {
      private final PackMetadataSection section;

      public ModFilePackResources(PackLocationInfo packId, IModFile modFile, String sourcePath) {
         super(packId, modFile.findResource(new String[]{sourcePath}));
         this.section = new PackMetadataSection(packId.title(), DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES), Optional.empty());
      }

      @Nullable
      public <T> T getMetadataSection(MetadataSectionSerializer<T> deserializer) throws IOException {
         IoSupplier<InputStream> supplier = this.getRootResource(new String[]{"pack.mcmeta"});
         if (supplier == null) {
            return (T)(deserializer == PackMetadataSection.TYPE ? this.section : null);
         }

         try (InputStream stream = (InputStream)supplier.get()) {
            return (T)getMetadataFromStream(deserializer, stream);
         }
      }

      public static class ModPathResourcesSupplier implements ResourcesSupplier {
         private final IModFile modFile;
         private final Path content;

         public ModPathResourcesSupplier(IModFile modFile, Path content) {
            this.modFile = modFile;
            this.content = content;
         }

         public PackResources openPrimary(PackLocationInfo location) {
            return new ResourcePackManagerImpl.ModFilePackResources(location, this.modFile, this.content.toString());
         }

         public PackResources openFull(PackLocationInfo location, Metadata metadata) {
            PackResources resources = this.openPrimary(location);
            List<String> overlays = metadata.overlays();
            return overlays.isEmpty() ? resources : null;
         }
      }
   }
}
