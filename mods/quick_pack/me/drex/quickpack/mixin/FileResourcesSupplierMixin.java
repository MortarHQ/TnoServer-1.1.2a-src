package me.drex.quickpack.mixin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipFile;
import me.drex.quickpack.packs.FastFilePackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.FilePackResources.FileResourcesSupplier;
import net.minecraft.server.packs.repository.Pack.Metadata;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FileResourcesSupplier.class)
public abstract class FileResourcesSupplierMixin {
   @Shadow
   @Final
   private File content;

   @Overwrite
   public PackResources openFull(PackLocationInfo packLocationInfo, Metadata metadata) {
      ZipFile zipFile = null;

      try {
         zipFile = new ZipFile(this.content);
      } catch (IOException e) {
         FastFilePackResources.LOGGER.error("Failed to open pack {}", this.content, e);
      }

      List<String> overlays = metadata.overlays();
      return new FastFilePackResources(packLocationInfo, zipFile, overlays);
   }
}
