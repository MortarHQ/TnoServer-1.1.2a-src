package com.blackgear.vanillabackport.client.util;

import net.minecraft.client.renderer.block.model.BakedQuad;

public class EmissiveQuad extends BakedQuad {
   private static final int FULL_BRIGHTNESS = 15728880;

   public EmissiveQuad(BakedQuad original) {
      super(makeEmissive((int[])original.getVertices().clone()), original.getTintIndex(), original.getDirection(), original.getSprite(), original.isShade());
   }

   private static int[] makeEmissive(int[] vertexData) {
      int vertexSize = 8;
      int lightsOffset = 6;

      for (int i = 0; i < vertexData.length; i += vertexSize) {
         vertexData[i + lightsOffset] = 15728880;
      }

      return vertexData;
   }
}
