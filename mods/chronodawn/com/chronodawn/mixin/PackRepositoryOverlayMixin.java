package com.chronodawn.mixin;

import com.chronodawn.ChronoDawn;
import com.chronodawn.worldgen.runtime.OverlayPackBootstrap;
import com.chronodawn.worldgen.runtime.OverlayPackInjection;
import java.nio.file.Path;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources.PathResourcesSupplier;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.repository.Pack.Position;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PackRepository.class)
public abstract class PackRepositoryOverlayMixin {
   @ModifyVariable(method = "<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V", at = @At("HEAD"), argsOnly = true)
   private static RepositorySource[] chronodawn$injectOverlaySource(RepositorySource[] sources) {
      if (!OverlayPackInjection.isServerDataRepository(sources)) {
         return sources;
      }

      Path overlayPath = OverlayPackBootstrap.getOverlayPath();
      if (overlayPath == null) {
         return sources;
      }

      RepositorySource overlaySource = consumer -> {
         PackLocationInfo info = new PackLocationInfo(
            "chronodawn-runtime-overlay", Component.literal("ChronoDawn config overlay"), PackSource.BUILT_IN, Optional.empty()
         );
         PackSelectionConfig selection = new PackSelectionConfig(true, Position.TOP, false);
         Pack pack = Pack.readMetaAndCreate(info, new PathResourcesSupplier(overlayPath), PackType.SERVER_DATA, selection);
         if (pack != null) {
            consumer.accept(pack);
         } else {
            ChronoDawn.LOGGER.warn("Runtime overlay pack at {} failed metadata validation; not registering", overlayPath);
         }
      };
      RepositorySource[] extended = new RepositorySource[sources.length + 1];
      System.arraycopy(sources, 0, extended, 0, sources.length);
      extended[sources.length] = overlaySource;
      ChronoDawn.LOGGER.info("Registered runtime config overlay datapack with Fabric (PackRepository mixin)");
      return extended;
   }
}
