package com.mortal_cultivation.client.renderer;

import com.mortal_cultivation.client.model.RuneBurstModel;
import com.mortal_cultivation.entity.RuneBurstEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.util.Mth;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.Color;

public final class RuneBurstRenderer extends GeoEntityRenderer<RuneBurstEntity> {
   private static final float FADE_TICKS = 4.0F;

   public RuneBurstRenderer(Context context) {
      super(context, new RuneBurstModel());
      this.shadowRadius = 0.0F;
   }

   public Color getRenderColor(RuneBurstEntity burst, float partialTick, int packedLight) {
      float remainingTicks = 13 - burst.tickCount - partialTick;
      float alpha = Mth.clamp(remainingTicks / 4.0F, 0.0F, 1.0F);
      return Color.ofRGBA(1.0F, 0.84F, 0.2F, alpha);
   }
}
