package de.keksuccino.konkrete.resources;

import com.mojang.blaze3d.platform.NativeImage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Deprecated(forRemoval = true)
public class ExternalTextureResourceLocation implements ITextureResourceLocation {
   private static final Logger LOGGER = LogManager.getLogger();
   protected InputStream in;
   protected boolean isJpeg;
   protected String path;
   protected ResourceLocation location;
   protected boolean loaded = false;
   protected int width = 0;
   protected int height = 0;

   public ExternalTextureResourceLocation(String path) {
      this.path = path;
   }

   public ExternalTextureResourceLocation(InputStream in) {
      this.in = in;
   }

   public ExternalTextureResourceLocation(InputStream in, boolean isJpeg) {
      this.in = in;
      this.isJpeg = isJpeg;
   }

   @Override
   public void loadTexture() {
      if (!this.loaded) {
         try {
            if (Minecraft.getInstance().getTextureManager() == null) {
               LOGGER.error("Minecraft TextureManager instance not ready yet! Unable to load texture: " + this.path);
               return;
            }

            boolean isImageJpeg = this.isJpeg;
            if (this.in == null) {
               File f = new File(this.path);
               isImageJpeg = f.getAbsolutePath().toLowerCase().endsWith(".jpeg") || f.getAbsolutePath().toLowerCase().endsWith(".jpg");
               this.in = new FileInputStream(f);
            }

            if (isImageJpeg) {
               LOGGER.warn(
                  "[KONKRETE] Loading JPEG image! JPEGs are not natively supported by Minecraft anymore and cause freezes on load! Consider replacing JPEG with PNG image: "
                     + this.path
               );
            }

            NativeImage i = isImageJpeg ? convertJpegToPng(this.in) : NativeImage.read(this.in);
            if (i == null) {
               throw new NullPointerException("Failed to load image! NativeImage was null!");
            }

            this.width = i.getWidth();
            this.height = i.getHeight();
            this.location = Minecraft.getInstance().getTextureManager().register("konkrete_external_texture", new SelfcleaningDynamicTexture(i));
            this.in.close();
            this.loaded = true;
         } catch (Exception ex) {
            LOGGER.error("[KONKRETE] Failed to load image!", ex);
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
         LOGGER.error("[KONKRETE] Failed to convert JPEG image to PNG!", ex);
      }

      IOUtils.closeQuietly(in);
      IOUtils.closeQuietly(byteArrayOut);
      return nativeImage;
   }

   @Override
   public ResourceLocation getResourceLocation() {
      return this.location;
   }

   public String getPath() {
      return this.path;
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
}
