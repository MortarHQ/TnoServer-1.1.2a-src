package de.keksuccino.konkrete.resources;

import com.mojang.blaze3d.platform.NativeImage;
import de.keksuccino.konkrete.input.CharacterFilter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Deprecated(forRemoval = true)
public class WebTextureResourceLocation implements ITextureResourceLocation {
   private static final Logger LOGGER = LogManager.getLogger();
   protected String url;
   protected ResourceLocation location;
   protected boolean loaded = false;
   protected int width = 0;
   protected int height = 0;

   public WebTextureResourceLocation(String url) {
      this.url = url;
   }

   @Override
   public void loadTexture() {
      if (!this.loaded) {
         try {
            if (Minecraft.getInstance().getTextureManager() == null) {
               LOGGER.error(
                  "[KONKRETE] Can't load texture '" + this.url + "'! Minecraft TextureManager instance not ready yet!",
                  new IllegalStateException("TextureManager not initialized yet.")
               );
               return;
            }

            URL u = new URL(this.url);
            HttpURLConnection httpcon = (HttpURLConnection)u.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");
            InputStream httpIn = httpcon.getInputStream();
            if (httpIn == null) {
               return;
            }

            boolean isDirectJpegLink = this.url.toLowerCase().endsWith(".jpeg") || this.url.toLowerCase().endsWith(".jpg");
            NativeImage i = null;

            try {
               i = isDirectJpegLink ? convertJpegToPng(httpIn) : NativeImage.read(httpIn);
            } catch (Exception ex) {
               ex.printStackTrace();
            }

            if (i == null && !isDirectJpegLink) {
               IOUtils.closeQuietly(httpIn);
               httpIn = httpcon.getInputStream();
               if (httpIn == null) {
                  return;
               }

               i = convertJpegToPng(httpIn);
            }

            if (i == null) {
               IOUtils.closeQuietly(httpIn);
               throw new NullPointerException("Unable to load web image! NativeImage was NULL!");
            }

            this.width = i.getWidth();
            this.height = i.getHeight();
            this.location = Minecraft.getInstance().getTextureManager().register(this.filterUrl(this.url), new DynamicTexture(i));
            httpIn.close();
            this.loaded = true;
         } catch (Exception ex) {
            LOGGER.error("[KONKRETE] ERROR: Can't load texture '" + this.url + "'! Invalid URL!", ex);
            this.loaded = false;
         }
      }
   }

   @Nullable
   protected static NativeImage convertJpegToPng(@NotNull InputStream in) {
      NativeImage nativeImage = null;
      ByteArrayOutputStream byteArrayOut = null;

      try {
         BufferedImage bufferedImage = ImageIO.read(in);
         byteArrayOut = new ByteArrayOutputStream();
         ImageIO.write(bufferedImage, "png", byteArrayOut);
         nativeImage = NativeImage.read(new ByteArrayInputStream(byteArrayOut.toByteArray()));
      } catch (Exception ex) {
         LOGGER.error("[KONKRETE] Failed to convert web JPEG image to PNG!", ex);
      }

      IOUtils.closeQuietly(in);
      IOUtils.closeQuietly(byteArrayOut);
      return nativeImage;
   }

   @Override
   public ResourceLocation getResourceLocation() {
      return this.location;
   }

   public String getURL() {
      return this.url;
   }

   @Override
   public boolean isReady() {
      return this.loaded;
   }

   @Override
   public int getHeight() {
      return this.height;
   }

   @Override
   public int getWidth() {
      return this.width;
   }

   private String filterUrl(String url) {
      CharacterFilter c = new CharacterFilter();
      c.addAllowedCharacters(
         "a",
         "b",
         "c",
         "d",
         "e",
         "f",
         "g",
         "h",
         "i",
         "j",
         "k",
         "l",
         "m",
         "n",
         "o",
         "p",
         "q",
         "r",
         "s",
         "t",
         "u",
         "v",
         "w",
         "x",
         "y",
         "z",
         "0",
         "1",
         "2",
         "3",
         "4",
         "5",
         "6",
         "7",
         "8",
         "9",
         "."
      );
      return c.filterForAllowedChars(url.toLowerCase());
   }
}
