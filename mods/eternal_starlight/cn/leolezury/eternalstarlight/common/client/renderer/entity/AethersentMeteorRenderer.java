package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.entity.AethersentMeteorModel;
import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentMeteor;
import cn.leolezury.eternalstarlight.common.util.ModelPartPose;
import cn.leolezury.eternalstarlight.common.util.ModelSnapshot;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class AethersentMeteorRenderer extends EntityRenderer<AethersentMeteor> {
   private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/aethersent_meteor.png");
   private static final int SNAPSHOT_LIFESPAN = 5;
   private final AethersentMeteorModel<AethersentMeteor> model;

   public AethersentMeteorRenderer(Context context) {
      super(context);
      this.model = new AethersentMeteorModel<>(context.bakeLayer(AethersentMeteorModel.LAYER_LOCATION));
   }

   public void render(AethersentMeteor entity, float yaw, float partialTicks, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
      stack.pushPose();
      float yRot = -Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
      float xRot = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) - 90.0F;
      float bob = entity.tickCount + partialTicks;
      stack.scale(-1.0F, -1.0F, 1.0F);
      stack.translate(0.0F, -1.5F, 0.0F);
      this.model.prepareMobModel(entity, 0.0F, 0.0F, partialTicks);
      this.model.setupAnim(entity, 0.0F, 0.0F, bob, yRot, xRot);
      RenderType renderType = this.model.renderType(this.getTextureLocation(entity));
      VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
      this.model.renderToBuffer(stack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
      stack.popPose();
      if (!entity.isRemoved() && entity.getSize() >= 10) {
         Vec3 currentPos = new Vec3(
            Mth.lerp(partialTicks, entity.xo, entity.getX()),
            Mth.lerp(partialTicks, entity.yo, entity.getY()),
            Mth.lerp(partialTicks, entity.zo, entity.getZ())
         );
         float currentTick = entity.tickCount + partialTicks;
         if (entity.trailSnapshots.isEmpty() || currentTick - entity.lastTrailTick > 2.0F) {
            Map<String, ModelPartPose> snapshot = ESModelUtil.saveModelSnapshot(this.model.allPartNames, this.model::getAnyDescendantWithName);
            entity.trailSnapshots.addFirst(Pair.of(currentPos, new ModelSnapshot(0.0F, 0.0F, currentTick, snapshot)));
            entity.lastTrailTick = currentTick;
         }

         entity.trailSnapshots.removeIf(p -> currentTick - ((ModelSnapshot)p.getSecond()).timestamp() > 5.0F);

         while (entity.trailSnapshots.size() > 32) {
            entity.trailSnapshots.removeLast();
         }

         this.model.root.getAllParts().forEach(ModelPart::resetPose);

         for (int i = 0; i < entity.trailSnapshots.size(); i++) {
            stack.pushPose();
            Vec3 trailPos = (Vec3)entity.trailSnapshots.get(i).getFirst();
            ModelSnapshot snapshot = (ModelSnapshot)entity.trailSnapshots.get(i).getSecond();
            ESModelUtil.loadPoseFromSnapshot(snapshot.poses(), this.model::getAnyDescendantWithName);
            stack.translate(trailPos.x - currentPos.x, trailPos.y - currentPos.y, trailPos.z - currentPos.z);
            this.model.alphaFactor = (1.0F - Mth.clamp(currentTick - snapshot.timestamp(), 0.0F, 5.0F) / 5.0F) * 0.3F;
            stack.scale(-1.0F, -1.0F, 1.0F);
            stack.translate(0.0F, -1.5F, 0.0F);
            renderType = ESRenderType.entityTranslucentGlow(this.getTextureLocation(entity));
            vertexConsumer = bufferSource.getBuffer(renderType);
            this.model.renderToBuffer(stack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
            stack.popPose();
         }

         this.model.alphaFactor = 1.0F;
      }

      super.render(entity, yaw, partialTicks, stack, bufferSource, packedLight);
   }

   public ResourceLocation getTextureLocation(AethersentMeteor meteor) {
      return ENTITY_TEXTURE;
   }
}
