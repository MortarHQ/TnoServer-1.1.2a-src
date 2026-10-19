package team.lodestar.lodestone.systems.texture;

import java.io.IOException;
import java.nio.ByteBuffer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL42;

public class BlankTexture extends AbstractTexture {
   private int width;
   private int height;

   public void load(@NotNull ResourceManager resourceManager) throws IOException {
      int size = 200;
      this.width = size;
      this.height = size;
      this.createEmpty(size, size, true, InternalTextureFormat.RGBA8);
   }

   public void createEmpty(int width, int height, boolean linear, InternalTextureFormat format) {
      this.id = GL42.glGenTextures();
      GL42.glBindTexture(3553, this.id);
      GL42.glPixelStorei(3317, 1);
      GL42.glTexImage2D(3553, 0, format.getGlFormat(), this.width, this.height, 0, format.getTexelFormat(), format.getGlType(), (ByteBuffer)null);
      GL42.glTexParameteri(3553, 10241, linear ? 9729 : 9728);
      GL42.glTexParameteri(3553, 10240, linear ? 9729 : 9728);
      GL42.glTexParameteri(3553, 10242, 33069);
      GL42.glTexParameteri(3553, 10243, 33069);
      GL42.glGenerateMipmap(3553);
      GL42.glBindTexture(3553, 0);
      GL42.glBindImageTexture(0, this.id, 0, false, 0, 35000, format.getGlFormat());
   }

   public void bind() {
      super.bind();
   }
}
