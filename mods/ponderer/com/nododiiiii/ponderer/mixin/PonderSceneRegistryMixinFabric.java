package com.nododiiiii.ponderer.mixin;

import com.mojang.logging.LogUtils;
import com.nododiiiii.ponderer.ponder.SceneStore;
import com.nododiiiii.ponderer.util.SafePaths;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import net.createmod.ponder.foundation.registration.PonderSceneRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.storage.LevelResource;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PonderSceneRegistry.class)
public class PonderSceneRegistryMixinFabric {
   private static final Logger LOGGER = LogUtils.getLogger();

   @Inject(
      method = "loadSchematic(Lnet/minecraft/class_3300;Lnet/minecraft/class_2960;)Lnet/minecraft/class_3499;",
      at = @At("HEAD"),
      cancellable = true,
      remap = false,
      require = 0
   )
   private static void ponderer$loadLocalSchematicFabric(
      ResourceManager resourceManager, ResourceLocation location, CallbackInfoReturnable<StructureTemplate> cir
   ) {
      if ("ponderer".equals(location.getNamespace())) {
         Path path = SceneStore.resolveStructurePath(location.getPath(), null);
         if (path != null && Files.exists(path)) {
            try (InputStream stream = Files.newInputStream(path)) {
               cir.setReturnValue(PonderSceneRegistry.loadSchematic(stream));
            } catch (Exception e) {
               LOGGER.error("Failed to read ponderer schematic: {}", path, e);
            }
         } else {
            if (SceneStore.ensureBuiltinStructure(location.getPath())) {
               Path localPath = SceneStore.getStructurePath(location);
               if (localPath != null && Files.exists(localPath)) {
                  try (InputStream stream = Files.newInputStream(localPath)) {
                     cir.setReturnValue(PonderSceneRegistry.loadSchematic(stream));
                  } catch (Exception e) {
                     LOGGER.error("Failed to read ponderer schematic after copy: {}", localPath, e);
                  }

                  return;
               }
            }

            InputStream builtinStream = SceneStore.openBuiltinStructure(location.getPath());
            if (builtinStream != null) {
               try (builtinStream) {
                  cir.setReturnValue(PonderSceneRegistry.loadSchematic(builtinStream));
               } catch (Exception e) {
                  LOGGER.error("Failed to read built-in ponderer schematic: {}", location, e);
               }
            } else {
               LOGGER.warn("Ponderer schematic missing: {}", location);
            }
         }
      } else {
         IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
         if (server != null) {
            Path root = server.getWorldPath(LevelResource.ROOT);
            Path generatedPath = SafePaths.resolveRelativePath(
               root.resolve("generated"), location.getNamespace() + "/structures/" + location.getPath() + ".nbt"
            );
            if (generatedPath != null && Files.exists(generatedPath)) {
               try (InputStream stream = Files.newInputStream(generatedPath)) {
                  cir.setReturnValue(PonderSceneRegistry.loadSchematic(stream));
               } catch (Exception e) {
                  LOGGER.error("Failed to read generated schematic: {}", generatedPath, e);
               }
            }
         }
      }
   }
}
