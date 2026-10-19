package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.model.TotemusPuzzleModel;
import com.benji.netherman.common.entity.TotemusPuzzleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TotemusPuzzleRenderer extends GeoEntityRenderer<TotemusPuzzleEntity> {
   private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
      NetherExp.location("textures/entity/totemus_puzzle_red.png"),
      NetherExp.location("textures/entity/totemus_puzzle_orange.png"),
      NetherExp.location("textures/entity/totemus_puzzle_yellow.png"),
      NetherExp.location("textures/entity/totemus_puzzle_green.png"),
      NetherExp.location("textures/entity/totemus_puzzle_blue.png"),
      NetherExp.location("textures/entity/totemus_puzzle_purple.png")
   };

   public TotemusPuzzleRenderer(Context renderManager) {
      super(renderManager, new TotemusPuzzleModel());
      this.shadowRadius = 0.4F;
   }

   public ResourceLocation getTextureLocation(TotemusPuzzleEntity animatable) {
      int colorIndex = animatable.getColor();
      if (colorIndex < 0 || colorIndex > 5) {
         colorIndex = 0;
      }

      return TEXTURES[colorIndex];
   }
}
