package de.keksuccino.konkrete.resources;

import com.mojang.blaze3d.platform.NativeImage;
import de.keksuccino.konkrete.mixin.mixins.client.IMixinDynamicTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;

@Deprecated(forRemoval = true)
public class SelfcleaningDynamicTexture extends DynamicTexture {
   public SelfcleaningDynamicTexture(NativeImage nativeImageIn) {
      super(nativeImageIn);
   }

   public void upload() {
      super.upload();
      clearTextureData(this);
   }

   private static void clearTextureData(DynamicTexture texture) {
      try {
         ((IMixinDynamicTexture)texture).setPixelsKonkrete(new NativeImage(1, 1, true));
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
