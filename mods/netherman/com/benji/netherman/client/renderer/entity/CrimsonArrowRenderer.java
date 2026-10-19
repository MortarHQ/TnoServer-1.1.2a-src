package com.benji.netherman.client.renderer.entity;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.CrimsonArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class CrimsonArrowRenderer extends ArrowRenderer<CrimsonArrowEntity> {
   private static final ResourceLocation TEXTURE = NetherExp.location("textures/entity/crimson_arrow.png");

   public CrimsonArrowRenderer(Context context) {
      super(context);
   }

   public ResourceLocation getTextureLocation(CrimsonArrowEntity entity) {
      return TEXTURE;
   }
}
