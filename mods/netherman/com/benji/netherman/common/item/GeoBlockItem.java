package com.benji.netherman.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GeoBlockItem extends BlockItem implements GeoItem {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   public final ResourceLocation modelLocation;
   public final ResourceLocation textureLocation;
   public final ResourceLocation animationLocation;
   public final ResourceLocation emissiveTextureLocation;

   public GeoBlockItem(
      Block block,
      Properties properties,
      ResourceLocation modelLocation,
      ResourceLocation textureLocation,
      ResourceLocation animationLocation,
      ResourceLocation emissiveTextureLocation
   ) {
      super(block, properties);
      this.modelLocation = modelLocation;
      this.textureLocation = textureLocation;
      this.animationLocation = animationLocation;
      this.emissiveTextureLocation = emissiveTextureLocation;
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
