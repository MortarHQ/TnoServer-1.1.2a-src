package fuzs.puzzleslib.api.client.key.v1;

import com.mojang.blaze3d.platform.InputConstants;
import fuzs.puzzleslib.impl.client.core.ClientFactories;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;

public interface KeyMappingHelper {
   KeyMappingHelper INSTANCE = ClientFactories.INSTANCE.getKeyMappingActivationHelper();

   KeyActivationContext getKeyActivationContext(KeyMapping var1);

   default boolean isConflictingWith(KeyMapping keyMapping, KeyMapping otherKeyMapping) {
      return this.getKeyActivationContext(keyMapping).isConflictingWith(this.getKeyActivationContext(otherKeyMapping));
   }

   static KeyMapping registerUnboundKeyMapping(ResourceLocation resourceLocation) {
      return registerKeyMapping(resourceLocation, InputConstants.UNKNOWN.getValue());
   }

   static KeyMapping registerKeyMapping(ResourceLocation resourceLocation, int keyCode) {
      return new KeyMapping("key." + resourceLocation.getPath(), keyCode, "key.categories." + resourceLocation.getNamespace());
   }
}
