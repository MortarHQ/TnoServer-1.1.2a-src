package net.jadenxgamer.netherexp.client.rendering.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Blaze;
import org.jetbrains.annotations.Nullable;

public class JNEBlazeRenderer extends BlazeRenderer {
   private static final ResourceLocation LIT = NetherExp.netherexpPath("textures/entity/blaze/lit.png");
   private static final ResourceLocation DIM = NetherExp.netherexpPath("textures/entity/blaze/dim.png");
   private static final ResourceLocation DULL = NetherExp.netherexpPath("textures/entity/blaze/dull.png");
   private static final ResourceLocation FADING = NetherExp.netherexpPath("textures/entity/blaze/fading.png");

   public JNEBlazeRenderer(Context context) {
      super(context);
   }

   public ResourceLocation getTextureLocation(Blaze entity) {
      if ((Boolean)JNEConfigs.DIMINISHING_BLAZES.get()) {
         float health = entity.getHealth();
         if (health >= 20.0F) {
            return LIT;
         }

         if (health >= 15.0F) {
            return DIM;
         }

         if (health >= 6.0F) {
            return DULL;
         }

         if (health >= 0.0F) {
            return FADING;
         }
      }

      return super.getTextureLocation(entity);
   }

   protected int getBlockLightLevel(Blaze entity, BlockPos pos) {
      if ((Boolean)JNEConfigs.DIMINISHING_BLAZES.get()) {
         float health = entity.getHealth();
         if (health >= 20.0F) {
            return 15;
         }

         if (health >= 15.0F) {
            return 10;
         }

         if (health >= 6.0F) {
            return 5;
         }

         if (health >= 0.0F) {
            return 0;
         }
      }

      return super.getBlockLightLevel(entity, pos);
   }

   @Nullable
   protected RenderType getRenderType(Blaze entity, boolean bodyVisible, boolean translucent, boolean glowing) {
      return JNERenderType.noShadeEntityCutoutNoCull(this.getTextureLocation(entity));
   }
}
