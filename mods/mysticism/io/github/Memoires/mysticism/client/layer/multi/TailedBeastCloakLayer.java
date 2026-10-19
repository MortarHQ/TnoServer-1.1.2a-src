package io.github.Memoires.mysticism.client.layer.multi;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.Memoires.mysticism.entity.skill.UncontrolledJinchurikiEntity;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.tensura.client.layer.template.MultiRenderLayer;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.util.Color;

public class TailedBeastCloakLayer<T extends Entity, M extends EntityModel<T>> extends MultiRenderLayer<T, M> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("tensura", "textures/models/layer/full_color.png");

   public TailedBeastCloakLayer(RenderLayerParent<T, M> renderLayerParent) {
      super(renderLayerParent);
   }

   public <E extends Entity & GeoEntity> TailedBeastCloakLayer(GeoRenderer<E> entityRenderer) {
      super(entityRenderer);
   }

   private RenderType getRenderType(float f) {
      return RenderType.energySwirl(TEXTURE, f * 0.02F % 1.0F, f * 0.01F % 1.0F);
   }

   public <E extends Entity & GeoEntity> void renderGeo(
      PoseStack poseStack,
      E animatable,
      BakedGeoModel bakedModel,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      if (animatable instanceof LivingEntity entity
         && (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.TAILED_BEAST_CLOAK)) || entity instanceof UncontrolledJinchurikiEntity)) {
         float f = animatable.tickCount + partialTick;
         poseStack.pushPose();
         poseStack.scale(1.0F, 1.0F, 1.0F);
         this.getGeoRenderer()
            .reRender(
               bakedModel,
               poseStack,
               bufferSource,
               (GeoAnimatable)animatable,
               this.getRenderType(f),
               bufferSource.getBuffer(this.getRenderType(f)),
               partialTick,
               packedLight,
               OverlayTexture.NO_OVERLAY,
               Color.ofARGB(255, 200, 60, 10).argbInt()
            );
         poseStack.popPose();
      }
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource multiBufferSource,
      int packedLight,
      T entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (entity instanceof LivingEntity living
         && (living.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.TAILED_BEAST_CLOAK)) || entity instanceof UncontrolledJinchurikiEntity)) {
         float f = entity.tickCount + partialTicks;
         poseStack.pushPose();
         poseStack.scale(1.0F, 1.0F, 1.0F);
         this.getParentModel()
            .renderToBuffer(
               poseStack, multiBufferSource.getBuffer(this.getRenderType(f)), packedLight, OverlayTexture.NO_OVERLAY, Color.ofARGB(255, 200, 60, 10).argbInt()
            );
         poseStack.popPose();
      }
   }
}
