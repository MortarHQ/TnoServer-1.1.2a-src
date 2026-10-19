package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.AltarBlock;
import com.benji.netherman.common.block.entity.AltarBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.model.GeoModel;

public class AltarModel extends GeoModel<AltarBlockEntity> {
   private static final ResourceLocation DEFAULT_MODEL = NetherExp.location("geo/altar.geo.json");
   private static final ResourceLocation TEXTURE = NetherExp.location("textures/block/altar.png");
   private static final ResourceLocation ANIMATION = NetherExp.location("animations/altar.animation.json");

   public ResourceLocation getModelResource(AltarBlockEntity animatable) {
      BlockState state = animatable.getBlockState();
      if (state.hasProperty(AltarBlock.GUESSED) && (Boolean)state.getValue(AltarBlock.GUESSED)) {
         return DEFAULT_MODEL;
      }

      if (state.hasProperty(AltarBlock.LIT) && (Boolean)state.getValue(AltarBlock.LIT)) {
         int letterValue = (Integer)state.getValue(AltarBlock.LETTER);
         if (letterValue > 0) {
            char letterChar = (char)(97 + letterValue - 1);
            boolean isActive = (Boolean)state.getValue(AltarBlock.ACTIVE);
            if (isActive) {
               return NetherExp.location("geo/altar_" + letterChar + "_active.geo.json");
            }

            return NetherExp.location("geo/altar_" + letterChar + ".geo.json");
         }
      }

      return DEFAULT_MODEL;
   }

   public ResourceLocation getTextureResource(AltarBlockEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(AltarBlockEntity animatable) {
      return ANIMATION;
   }
}
