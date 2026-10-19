package de.keksuccino.konkrete.resources;

import net.minecraft.resources.ResourceLocation;

@Deprecated(forRemoval = true)
public interface ITextureResourceLocation {
   void loadTexture();

   ResourceLocation getResourceLocation();

   boolean isReady();

   int getHeight();

   int getWidth();
}
