package xaero.common.graphics;

import com.mojang.blaze3d.platform.TextureUtil;
import java.io.IOException;
import java.nio.ByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.lwjgl.BufferUtils;

public class MinimapTexture extends SimpleTexture {
   public ByteBuffer buffer = BufferUtils.createByteBuffer(786432);
   boolean loaded = false;

   public void loadIfNeeded() throws IOException {
      if (!this.loaded) {
         this.load(Minecraft.getInstance().getResourceManager());
         this.loaded = true;
      }
   }

   public MinimapTexture(ResourceLocation location) throws IOException {
      super(location);
   }

   public void load(ResourceManager resourceManager_1) throws IOException {
      TextureUtil.prepareImage(this.getId(), 0, 512, 512);
   }
}
