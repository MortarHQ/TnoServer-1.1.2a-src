package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.entity.ModelWroughtnaut;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.ItemLayer;
import com.bobmowzie.mowziesmobs.client.render.entity.layer.WroughtnautEyesLayer;
import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import com.ilexiconn.llibrary.client.util.ClientUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class RenderWroughtnaut extends MobRenderer<EntityWroughtnaut, ModelWroughtnaut<EntityWroughtnaut>> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "textures/entity/wroughtnaut.png");

   public RenderWroughtnaut(Context mgr) {
      super(mgr, new ModelWroughtnaut(), 1.0F);
      this.addLayer(new WroughtnautEyesLayer(this));
      this.addLayer(new ItemLayer(this, ((ModelWroughtnaut)this.getModel()).sword, Items.DIAMOND_SWORD.getDefaultInstance(), ItemDisplayContext.GROUND));
   }

   public void render(EntityWroughtnaut p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) {
      super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
      if (this.entityRenderDispatcher.shouldRenderHitBoxes() && !p_115455_.isInvisible() && !Minecraft.getInstance().showOnlyReducedInfo()) {
         Vec3 forward = p_115455_.getForward();
         Vec3 bodyFacing = Vec3.directionFromRotation(0.0F, p_115455_.yBodyRot);
         Matrix4f matrix4f = p_115458_.last().pose();
         Matrix3f matrix3f = p_115458_.last().normal();
         VertexConsumer consumer = p_115459_.getBuffer(RenderType.lines());
         consumer.addVertex(matrix4f, 0.0F, p_115455_.getEyeHeight() + 0.1F, 0.0F).setColor(0, 255, 255, 255);
         ClientUtils.transformNormals(consumer, matrix3f, (float)forward.x(), (float)forward.y(), (float)forward.z());
         consumer.addVertex(matrix4f, (float)(forward.x * 2.0), (float)(p_115455_.getEyeHeight() + 0.1F + forward.y * 2.0), (float)(forward.z * 2.0))
            .setColor(0, 255, 255, 255);
         ClientUtils.transformNormals(consumer, matrix3f, (float)forward.x(), (float)forward.y(), (float)forward.z());
         consumer.addVertex(matrix4f, 0.0F, p_115455_.getEyeHeight() + 0.2F, 0.0F).setColor(255, 0, 255, 255);
         ClientUtils.transformNormals(consumer, matrix3f, (float)bodyFacing.x(), (float)bodyFacing.y(), (float)bodyFacing.z());
         consumer.addVertex(matrix4f, (float)(bodyFacing.x * 2.0), (float)(p_115455_.getEyeHeight() + 0.2F + bodyFacing.y * 2.0), (float)(bodyFacing.z * 2.0))
            .setColor(255, 0, 255, 255);
         ClientUtils.transformNormals(consumer, matrix3f, (float)bodyFacing.x(), (float)bodyFacing.y(), (float)bodyFacing.z());
      }
   }

   protected float getFlipDegrees(EntityWroughtnaut entity) {
      return 0.0F;
   }

   public ResourceLocation getTextureLocation(EntityWroughtnaut entity) {
      return TEXTURE;
   }
}
