package net.unusual.block_factorys_bosses.geckolib.util;

import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.loading.object.BakedAnimations;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface CustomDefaultedGeoModel<T extends GeoAnimatable> {
   static ResourceLocation buildFormattedAnimationPathImpl(String modelSubtype, ResourceLocation basePath) {
      return ResourceLocation.fromNamespaceAndPath(basePath.getNamespace(), "anim_bosses_rise/" + modelSubtype + "/" + basePath.getPath() + ".animation.json");
   }

   @Nullable
   static Animation getAnimationImpl(Supplier<ResourceLocation> modelGetAnimationResource, String name, ResourceLocation[] fallbacks) {
      ResourceLocation location = modelGetAnimationResource.get();
      BakedAnimations bakedAnimations = BossesRiseAnimationCache.getBakedAnimations().get(location);
      Animation animation = bakedAnimations.getAnimation(name);

      for (ResourceLocation fallbackLocation : fallbacks) {
         Map var10000 = GeckoLibCache.getBakedAnimations();
         location = fallbackLocation;
         bakedAnimations = (BakedAnimations)var10000.get(fallbackLocation);
         animation = bakedAnimations != null ? bakedAnimations.getAnimation(name) : null;
         if (animation != null) {
            return animation;
         }
      }

      if (animation != null) {
         return animation;
      } else if (bakedAnimations != null) {
         return null;
      } else if (!location.getPath().contains("anim_bosses_rise/")) {
         throw GeckoLibConstants.exception(
            location, "Invalid animation resource path provided - Bosses'Rise animations must be placed in assets/<modid>/anim_bosses_rise/"
         );
      } else {
         throw GeckoLibConstants.exception(location, "Unable to find animation file.");
      }
   }
}
