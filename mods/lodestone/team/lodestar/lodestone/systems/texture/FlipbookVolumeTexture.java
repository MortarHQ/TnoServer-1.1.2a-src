package team.lodestar.lodestone.systems.texture;

import com.mojang.blaze3d.platform.GlStateManager;
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
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import team.lodestar.lodestone.helpers.TextureHelper;

public class FlipbookVolumeTexture extends AbstractTexture {
   protected final ResourceLocation location;
   private final int xSlices;
   private final int ySlices;
   private int width;
   private int height;
   private int depth;
   private boolean linear;

   public FlipbookVolumeTexture(ResourceLocation location, int xSlices, int ySlices) {
      this(location, xSlices, ySlices, false);
   }

   public FlipbookVolumeTexture(ResourceLocation location, int xSlices, int ySlices, boolean linear) {
      this.location = location;
      this.xSlices = xSlices;
      this.ySlices = ySlices;
      this.linear = linear;
   }

   public void load(ResourceManager resourceManager) throws IOException {
      Optional<Resource> resource = resourceManager.getResource(this.location);
      if (resource.isPresent()) {
         ByteBuffer textureData = TextureUtil.readResource(resource.get().open());
         textureData.rewind();
         MemoryStack stack = MemoryStack.stackPush();

         try {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            ByteBuffer image = STBImage.stbi_load_from_memory(textureData, width, height, channels, 4);
            if (image == null) {
               throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
            }

            int[] resultSize = new int[3];
            ByteBuffer volume = TextureHelper.convertTextureAtlasToVolume(image, width.get(), height.get(), this.xSlices, this.ySlices, 4, resultSize);
            this.width = resultSize[0];
            this.height = resultSize[1];
            this.depth = resultSize[2];
            this.id = GL30.glGenTextures();
            GL30.glBindTexture(32879, this.id);
            GL30.glPixelStorei(3317, 1);
            GL30.glTexImage3D(32879, 0, 6408, this.width, this.height, this.depth, 0, 6408, 5121, volume);
            GL30.glTexParameteri(32879, 10241, this.linear ? 9729 : 9728);
            GL30.glTexParameteri(32879, 10240, this.linear ? 9729 : 9728);
            GL30.glTexParameteri(32879, 10242, 33069);
            GL30.glTexParameteri(32879, 10243, 33069);
            GL30.glTexParameteri(32879, 32882, 33069);
            GL30.glGenerateMipmap(32879);
            GL30.glBindTexture(32879, 0);
            STBImage.stbi_image_free(image);
         } catch (Throwable var12) {
            if (stack != null) {
               try {
                  stack.close();
               } catch (Throwable var11) {
                  var12.addSuppressed(var11);
               }
            }

            throw var12;
         }

         if (stack != null) {
            stack.close();
         }
      }
   }

   public void bind() {
      RenderSystem.assertOnRenderThreadOrInit();
      GL30.glBindTexture(32879, this.id);
   }

   public void setFilter(boolean blur, boolean mipmap) {
      RenderSystem.assertOnRenderThreadOrInit();
      this.blur = blur;
      this.mipmap = mipmap;
      int i;
      short j;
      if (blur) {
         i = mipmap ? 9987 : 9729;
         j = 9729;
      } else {
         i = mipmap ? 9986 : 9728;
         j = 9728;
      }

      this.bind();
      GlStateManager._texParameter(32879, 10241, i);
      GlStateManager._texParameter(32879, 10240, j);
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public int getDepth() {
      return this.depth;
   }

   public int getXSlices() {
      return this.xSlices;
   }

   public int getYSlices() {
      return this.ySlices;
   }
}
