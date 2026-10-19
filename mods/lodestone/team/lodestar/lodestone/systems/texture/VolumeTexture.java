package team.lodestar.lodestone.systems.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.ByteBuffer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL42;

public class VolumeTexture extends AbstractTexture {
   private int width;
   private int height;
   private int depth;
   private boolean shouldReload = true;
   private boolean initialized = false;

   public VolumeTexture(int size) {
      this(size, size, size);
   }

   public VolumeTexture(int size, boolean shouldReload) {
      this(size);
      this.shouldReload = shouldReload;
   }

   public VolumeTexture(int width, int height, int depth) {
      this.width = width;
      this.height = height;
      this.depth = depth;
   }

   public VolumeTexture(int width, int height, int depth, boolean shouldReload) {
      this(width, height, depth);
      this.shouldReload = shouldReload;
   }

   public void load(@NotNull ResourceManager resourceManager) {
      if (!this.initialized) {
         this.createEmpty(true, InternalTextureFormat.RGBA16F);
         this.initialized = true;
      } else if (this.shouldReload) {
         this.createEmpty(true, InternalTextureFormat.RGBA16F);
      }
   }

   public void createEmpty(boolean linear, InternalTextureFormat format) {
      this.id = GL12.glGenTextures();
      GL12.glBindTexture(32879, this.id);
      GL12.glPixelStorei(3317, 1);
      GL12.glTexImage3D(32879, 0, format.getGlFormat(), this.width, this.height, this.depth, 0, 6408, format.getGlType(), (ByteBuffer)null);
      GL12.glTexParameteri(32879, 10241, linear ? 9729 : 9728);
      GL12.glTexParameteri(32879, 10240, linear ? 9729 : 9728);
      GL12.glTexParameteri(32879, 10242, 33071);
      GL12.glTexParameteri(32879, 10243, 33071);
      GL12.glTexParameteri(32879, 32882, 33071);
      GL30.glGenerateMipmap(32879);
      GL12.glBindTexture(32879, 0);
      GL42.glBindImageTexture(0, this.id, 0, true, 0, 35000, 34842);
   }

   public void bind() {
      RenderSystem.assertOnRenderThreadOrInit();
      GL12.glBindTexture(32879, this.id);
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
}
