package com.sjyueluo.tensura_skill_books.client.icon;

import net.minecraft.resources.ResourceLocation;

public record IconData(ResourceLocation texture, int pixelWidth, int pixelHeight, float u0, float v0, float u1, float v1) {
   public static IconData fullTexture(ResourceLocation texture) {
      return fullTexture(texture, 16, 16);
   }

   public static IconData fullTexture(ResourceLocation texture, int pixelWidth, int pixelHeight) {
      return new IconData(texture, pixelWidth, pixelHeight, 0.0F, 0.0F, 1.0F, 1.0F);
   }
}
