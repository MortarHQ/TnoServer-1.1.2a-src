package team.lodestar.lodestone.systems.texture;

import javax.annotation.Nullable;

public enum InternalTextureFormat {
   RGBA8(32856, 5121, 4, 6408),
   RGBA16F(34842, 5131, 4, 6408),
   RGBA32F(34836, 5126, 4, 6408),
   RGBA16(32859, 5123, 4, 6408),
   RGBA16_SNORM(36763, 5122, 4, 6408),
   RGBA8_SNORM(36759, 5120, 4, 6408);

   private final int glFormat;
   private final int glType;
   private final int channels;
   private final int texelFormat;

   InternalTextureFormat(int glFormat, int glType, int channels, int texelFormat) {
      this.glFormat = glFormat;
      this.glType = glType;
      this.channels = channels;
      this.texelFormat = texelFormat;
   }

   public int getGlFormat() {
      return this.glFormat;
   }

   public int getGlType() {
      return this.glType;
   }

   public int getChannels() {
      return this.channels;
   }

   public int getTexelFormat() {
      return this.texelFormat;
   }

   @Nullable
   public static InternalTextureFormat fromString(String format) {
      for (InternalTextureFormat value : values()) {
         if (value.name().equalsIgnoreCase(format)) {
            return value;
         }
      }

      return null;
   }
}
