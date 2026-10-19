package org.crypticdev.stextras.client.loadingScreen.texture;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.SimpleTexture.TextureImage;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

public class FileBasedTexture extends SimpleTexture {
   public FileBasedTexture(ResourceLocation location) {
      super(location);
   }

   @NotNull
   protected TextureImage getTextureImage(ResourceManager resourceManager) {
      try {
         Resource res = (Resource)resourceManager.getResource(this.location).orElseThrow(() -> new IOException("Missing texture resource: " + this.location));

         try (InputStream is = res.open()) {
            return new TextureImage(new TextureMetadataSection(true, true), NativeImage.read(is));
         }
      } catch (IOException e) {
         return new TextureImage(e);
      }
   }
}
