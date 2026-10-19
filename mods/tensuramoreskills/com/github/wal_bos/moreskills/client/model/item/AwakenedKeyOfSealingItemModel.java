package com.github.wal_bos.moreskills.client.model.item;

import com.github.wal_bos.moreskills.item.AwakenedKeyOfSealingItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AwakenedKeyOfSealingItemModel extends GeoModel<AwakenedKeyOfSealingItem> {
   public ResourceLocation getModelResource(AwakenedKeyOfSealingItem animatable) {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "geo/item/awakened_key_of_sealing.geo.json");
   }

   public ResourceLocation getTextureResource(AwakenedKeyOfSealingItem animatable) {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/item/awakened_key_of_sealing.png");
   }

   public ResourceLocation getAnimationResource(AwakenedKeyOfSealingItem animatable) {
      return null;
   }
}
