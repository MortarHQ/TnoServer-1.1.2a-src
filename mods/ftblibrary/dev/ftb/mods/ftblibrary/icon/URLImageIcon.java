package dev.ftb.mods.ftblibrary.icon;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.util.UndashedUuid;
import dev.ftb.mods.ftblibrary.math.PixelBuffer;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class URLImageIcon extends ImageIcon {
   public final URI uri;
   private final String url;

   public URLImageIcon(ResourceLocation tex, URI _uri) {
      super(tex);
      this.uri = _uri;
      this.url = this.uri.toString();
   }

   public URLImageIcon(URI uri) {
      this(ResourceLocation.parse("remote_image:" + UndashedUuid.toString(UUID.nameUUIDFromBytes(uri.toString().getBytes(StandardCharsets.UTF_8)))), uri);
   }

   public URLImageIcon copy() {
      URLImageIcon icon = new URLImageIcon(this.texture, this.uri);
      icon.minU = this.minU;
      icon.minV = this.minV;
      icon.maxU = this.maxU;
      icon.maxV = this.maxV;
      icon.tileSize = this.tileSize;
      return icon;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void bindTexture() {
      TextureManager manager = Minecraft.getInstance().getTextureManager();
      AbstractTexture img = manager.getTexture(this.texture);
      if (img == null) {
         if (!this.uri.getScheme().equals("http") && !this.uri.getScheme().equals("https")) {
            File file = null;
            if (this.uri.getScheme().equals("file")) {
               try {
                  file = new File(this.uri.getPath());
               } catch (Exception ex) {
                  ex.printStackTrace();
               }
            }

            if (file == null) {
               file = new File(this.uri);
            }

            img = new HttpTexture(file, this.url, MISSING_IMAGE, false, null);
         } else {
            img = new HttpTexture(null, this.url, MISSING_IMAGE, false, null);
         }

         manager.register(this.texture, img);
      }

      RenderSystem.bindTexture(img.getId());
   }

   @Override
   public String toString() {
      return this.url;
   }

   @Override
   public PixelBuffer createPixelBuffer() {
      try (InputStream stream = this.uri.toURL().openConnection(Minecraft.getInstance().getProxy()).getInputStream()) {
         return PixelBuffer.from(ImageIO.read(stream));
      } catch (Exception ex) {
         return null;
      }
   }
}
