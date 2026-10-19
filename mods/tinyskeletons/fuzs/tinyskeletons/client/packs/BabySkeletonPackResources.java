package fuzs.tinyskeletons.client.packs;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mojang.blaze3d.platform.NativeImage;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.resources.v1.AbstractModPackResources;
import fuzs.tinyskeletons.client.renderer.entity.BabyBoggedRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabySkeletonRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabyStrayRenderer;
import fuzs.tinyskeletons.client.renderer.entity.BabyWitherSkeletonRenderer;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.Nullable;

public class BabySkeletonPackResources extends AbstractModPackResources {
   private static final ResourceLocation SKELETON_LOCATION = ResourceLocationHelper.withDefaultNamespace("textures/entity/skeleton/skeleton.png");
   private static final ResourceLocation STRAY_LOCATION = ResourceLocationHelper.withDefaultNamespace("textures/entity/skeleton/stray.png");
   private static final ResourceLocation WITHER_SKELETON_LOCATION = ResourceLocationHelper.withDefaultNamespace("textures/entity/skeleton/wither_skeleton.png");
   private static final ResourceLocation BOGGED_LOCATION = ResourceLocationHelper.withDefaultNamespace("textures/entity/skeleton/bogged.png");
   private static final BiMap<ResourceLocation, ResourceLocation> BABY_SKELETON_LOCATIONS = ImmutableBiMap.of(
      BabySkeletonRenderer.BABY_SKELETON_LOCATION,
      SKELETON_LOCATION,
      BabyStrayRenderer.BABY_STRAY_SKELETON_LOCATION,
      STRAY_LOCATION,
      BabyWitherSkeletonRenderer.BABY_WITHER_SKELETON_LOCATION,
      WITHER_SKELETON_LOCATION,
      BabyBoggedRenderer.BABY_BOGGED_SKELETON_LOCATION,
      BOGGED_LOCATION
   );
   private static final int VANILLA_SKELETON_TEXTURE_WIDTH = 64;
   private static final int VANILLA_SKELETON_TEXTURE_HEIGHT = 32;
   private final ResourceManager resourceManager;
   private final VanillaPackResources vanillaPackResources;

   public BabySkeletonPackResources() {
      Minecraft minecraft = Minecraft.getInstance();
      this.resourceManager = minecraft.getResourceManager();
      this.vanillaPackResources = minecraft.getVanillaPackResources();
   }

   @Nullable
   public IoSupplier<InputStream> getResource(PackType packType, ResourceLocation location) {
      ResourceLocation vanillaSkeletonLocation = (ResourceLocation)BABY_SKELETON_LOCATIONS.get(location);
      if (vanillaSkeletonLocation == null) {
         return null;
      }

      Optional<Resource> resource = this.resourceManager.getResource(vanillaSkeletonLocation);
      if (resource.isPresent()) {
         try {
            NativeImage image = NativeImage.read(resource.get().open());

            IoSupplier var6;
            label60: {
               try {
                  if (image.getWidth() / image.getHeight() != 2) {
                     var6 = this.vanillaPackResources.getResource(packType, vanillaSkeletonLocation);
                     break label60;
                  }
               } catch (Throwable var9) {
                  if (image != null) {
                     try {
                        image.close();
                     } catch (Throwable var8) {
                        var9.addSuppressed(var8);
                     }
                  }

                  throw var9;
               }

               if (image != null) {
                  image.close();
               }

               return resource.get()::open;
            }

            if (image != null) {
               image.close();
            }

            return var6;
         } catch (IOException var10) {
            return resource.get()::open;
         }
      } else {
         return null;
      }
   }

   public Set<String> getNamespaces(PackType type) {
      return BABY_SKELETON_LOCATIONS.keySet().stream().<String>map(ResourceLocation::getNamespace).collect(Collectors.toSet());
   }
}
