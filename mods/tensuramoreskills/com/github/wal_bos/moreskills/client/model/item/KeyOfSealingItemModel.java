package com.github.wal_bos.moreskills.client.model.item;

import com.github.wal_bos.moreskills.item.KeyOfSealingItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class KeyOfSealingItemModel extends GeoModel<KeyOfSealingItem> {
   public ResourceLocation getModelResource(KeyOfSealingItem animatable) {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "geo/item/key_of_sealing.geo.json");
   }

   public ResourceLocation getTextureResource(KeyOfSealingItem animatable) {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/item/key_of_sealing.png");
   }

   public ResourceLocation getAnimationResource(KeyOfSealingItem animatable) {
      return null;
   }
}
