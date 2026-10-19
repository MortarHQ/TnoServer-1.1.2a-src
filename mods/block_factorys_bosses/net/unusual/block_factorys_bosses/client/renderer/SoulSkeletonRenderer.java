package net.unusual.block_factorys_bosses.client.renderer;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.model.Modelsoul_skeleton;
import net.unusual.block_factorys_bosses.entity.monster.SoulSkeletonEntity;

@ParametersAreNonnullByDefault
@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SoulSkeletonRenderer extends MobRenderer<SoulSkeletonEntity, Modelsoul_skeleton<SoulSkeletonEntity>> {
   private static final ResourceLocation TEXTURE_LOCATION = BossesRise.prefix("textures/entities/soul_skeleton.png");
   private static final ResourceLocation GLOW_TEXTURE_LOCATION = BossesRise.prefix("textures/entities/soul_skeleton_glow.png");

   public SoulSkeletonRenderer(Context context) {
      super(context, new Modelsoul_skeleton(context.bakeLayer(Modelsoul_skeleton.LAYER_LOCATION)), 0.5F);
      this.addLayer(new GlowRenderLayer(this, RenderType.eyes(GLOW_TEXTURE_LOCATION)));
   }

   public ResourceLocation getTextureLocation(SoulSkeletonEntity entity) {
      return TEXTURE_LOCATION;
   }
}
