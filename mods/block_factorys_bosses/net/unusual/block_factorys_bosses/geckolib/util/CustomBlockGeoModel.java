package net.unusual.block_factorys_bosses.geckolib.util;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CustomBlockGeoModel<T extends GeoAnimatable> extends DefaultedBlockGeoModel<T> implements CustomDefaultedGeoModel<T> {
   public CustomBlockGeoModel(ResourceLocation assetSubpath) {
      super(assetSubpath);
   }

   public ResourceLocation buildFormattedAnimationPath(ResourceLocation basePath) {
      return CustomDefaultedGeoModel.buildFormattedAnimationPathImpl(this.subtype(), basePath);
   }

   @Nullable
   public Animation getAnimation(T animatable, String name) {
      return CustomDefaultedGeoModel.getAnimationImpl(() -> this.getAnimationResource(animatable), name, this.getAnimationResourceFallbacks(animatable));
   }
}
