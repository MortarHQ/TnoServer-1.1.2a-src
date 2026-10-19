package fuzs.puzzleslib.api.client.init.v1;

import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import net.minecraft.client.model.geom.ModelLayerLocation;

@FunctionalInterface
public interface ModelLayerFactory {
   static ModelLayerFactory from(String modId) {
      return () -> modId;
   }

   String modId();

   default ModelLayerLocation register(String path, String layer) {
      return new ModelLayerLocation(ResourceLocationHelper.fromNamespaceAndPath(this.modId(), path), layer);
   }

   default ModelLayerLocation register(String path) {
      return this.register(path, "main");
   }

   default ModelLayerLocation registerInnerArmor(String path) {
      return this.register(path, "inner_armor");
   }

   default ModelLayerLocation registerOuterArmor(String path) {
      return this.register(path, "outer_armor");
   }
}
