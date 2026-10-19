package team.lodestar.lodestone.systems.texture;

import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Optional;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class CubeMapTexture extends AbstractTexture {
   protected final ResourceLocation location;
   protected final boolean alpha;
   private int width;
   private int height;
   private static final String[] CUBE_MAP_SIDES = new String[]{"right", "left", "top", "bottom", "front", "back"};

   public CubeMapTexture(ResourceLocation location, boolean hasAlphaChannel) {
      this.location = location;
      this.alpha = hasAlphaChannel;
   }

   public void load(@NotNull ResourceManager resourceManager) {
      this.id = GL30.glGenTextures();
      GL30.glBindTexture(34067, this.id);

      for (int i = 0; i < CUBE_MAP_SIDES.length; i++) {
         ResourceLocation sideLocation = ResourceLocation.fromNamespaceAndPath(
            this.location.getNamespace(), this.location.getPath() + "_" + CUBE_MAP_SIDES[i] + (this.alpha ? ".png" : ".jpg")
         );
         Optional<Resource> resource = resourceManager.getResource(sideLocation);
         if (resource.isPresent()) {
            try {
               ByteBuffer textureData = TextureUtil.readResource(resource.get().open());
               textureData.rewind();
               MemoryStack stack = MemoryStack.stackPush();

               try {
                  IntBuffer width = stack.mallocInt(1);
                  IntBuffer height = stack.mallocInt(1);
                  IntBuffer channels = stack.mallocInt(1);
                  ByteBuffer image = STBImage.stbi_load_from_memory(textureData, width, height, channels, this.alpha ? 4 : 3);
                  if (image == null) {
                     throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
                  }

                  int texFormat = this.alpha ? 6408 : 6407;
                  GL30.glTexImage2D(34069 + i, 0, texFormat, width.get(), height.get(), 0, texFormat, 5121, image);
                  this.width = width.get(0);
                  this.height = height.get(0);
                  STBImage.stbi_image_free(image);
               } catch (Throwable var13) {
                  if (stack != null) {
                     try {
                        stack.close();
                     } catch (Throwable var12) {
                        var13.addSuppressed(var12);
                     }
                  }

                  throw var13;
               }

               if (stack != null) {
                  stack.close();
               }
            } catch (Exception e) {
               throw new RuntimeException("Failed to load cubemap side: " + sideLocation, e);
            }
         }
      }

      GL30.glTexParameteri(34067, 10241, 9729);
      GL30.glTexParameteri(34067, 10240, 9729);
      GL30.glTexParameteri(34067, 10242, 33071);
      GL30.glTexParameteri(34067, 10243, 33071);
      GL30.glTexParameteri(34067, 32882, 33071);
   }

   public void bind() {
      RenderSystem.assertOnRenderThreadOrInit();
      GL30.glBindTexture(34067, this.id);
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }
}
