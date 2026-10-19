package fuzs.illagerinvasion.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.init.ClientModRegistry;
import fuzs.illagerinvasion.client.model.BrimmedHatIllagerEntityModel;
import fuzs.illagerinvasion.world.entity.monster.Alchemist;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class AlchemistRender extends MobRenderer<Alchemist, BrimmedHatIllagerEntityModel<Alchemist>> {
   private static final ResourceLocation ALCHEMIST_LOCATION = IllagerInvasion.id("textures/entity/alchemist.png");

   public AlchemistRender(Context context) {
      super(context, new BrimmedHatIllagerEntityModel(context.bakeLayer(ClientModRegistry.BRIM_HAT_ILLAGER)), 0.5F);
      this.addLayer(
         new ItemInHandLayer<Alchemist, BrimmedHatIllagerEntityModel<Alchemist>>(this, context.getItemInHandRenderer()) {
            public void render(
               PoseStack matrixStack,
               MultiBufferSource vertexConsumerProvider,
               int i,
               Alchemist provokerEntity,
               float f,
               float g,
               float h,
               float j,
               float k,
               float l
            ) {
               if (provokerEntity.isAggressive()) {
                  super.render(matrixStack, vertexConsumerProvider, i, provokerEntity, f, g, h, j, k, l);
               }
            }
         }
      );
      ((BrimmedHatIllagerEntityModel)this.model).getHat().visible = true;
   }

   protected void scale(Alchemist Alchemist, PoseStack matrixStack, float f) {
      matrixStack.scale(0.95F, 0.95F, 0.95F);
   }

   public ResourceLocation getTextureLocation(Alchemist entity) {
      return ALCHEMIST_LOCATION;
   }
}
