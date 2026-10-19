package com.foxablazeultimate.client.render;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import com.foxablazeultimate.item.beelzebub.CapturedEntityData;
import com.foxablazeultimate.registry.FoxAblazeUltimateDataComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class CapturedEntityRenderer extends BlockEntityWithoutLevelRenderer {
   private final Map<EntityType<?>, Entity> dummyCache = new HashMap<>();

   public CapturedEntityRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet models) {
      super(dispatcher, models);
   }

   public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack pose, MultiBufferSource buffer, int packedLight, int packedOverlay) {
      CapturedEntityData data = (CapturedEntityData)stack.get((DataComponentType)FoxAblazeUltimateDataComponents.CAPTURED_ENTITY.get());
      if (data != null) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.level != null) {
            Optional<EntityType<?>> typeOpt = EntityType.byString(data.entityType().toString());
            if (!typeOpt.isEmpty()) {
               Entity dummy = this.dummyCache
                  .computeIfAbsent(
                     typeOpt.get(),
                     t -> {
                        try {
                           Entity e = t.create(mc.level);
                           if (e == null) {
                              return null;
                           }

                           e.setYRot(0.0F);
                           e.setXRot(0.0F);
                           if (e instanceof LivingEntity le) {
                              le.yHeadRot = 0.0F;
                              le.yBodyRot = 0.0F;
                           }

                           return e;
                        } catch (Throwable err) {
                           FoxAblazeUltimateMod.LOGGER
                              .debug("[Beelzebub BEWLR] 无法为 {} 创建 dummy 实体（mod 缺失或构造异常），跳过 3D 渲染", BuiltInRegistries.ENTITY_TYPE.getKey(t), err);
                           return null;
                        }
                     }
                  );
               if (dummy != null) {
                  EntityRenderDispatcher dispatcher = mc.getEntityRenderDispatcher();
                  pose.pushPose();
                  pose.translate(0.5, 0.5, 0.5);
                  float bbMax = Math.max(dummy.getBbHeight(), dummy.getBbWidth());
                  if (bbMax <= 0.0F) {
                     bbMax = 1.0F;
                  }

                  float scale = 0.55F / Math.max(0.6F, bbMax);
                  if (scale > 0.75F) {
                     scale = 0.75F;
                  }

                  pose.scale(scale, scale, scale);
                  switch (context) {
                     case GUI:
                        pose.translate(0.0, -dummy.getBbHeight() / 2.0, 0.0);
                        pose.mulPose(Axis.YP.rotationDegrees(180.0F + getGuiSpinAngle()));
                        break;
                     case GROUND:
                     case FIXED:
                        pose.translate(0.0, -dummy.getBbHeight() / 2.0, 0.0);
                        break;
                     case FIRST_PERSON_LEFT_HAND:
                     case FIRST_PERSON_RIGHT_HAND:
                     case THIRD_PERSON_LEFT_HAND:
                     case THIRD_PERSON_RIGHT_HAND:
                        pose.translate(0.0, -dummy.getBbHeight() / 2.0, 0.0);
                        break;
                     default:
                        pose.translate(0.0, -dummy.getBbHeight() / 2.0, 0.0);
                  }

                  try {
                     dispatcher.render(dummy, 0.0, 0.0, 0.0, 0.0F, 0.0F, pose, buffer, packedLight);
                  } catch (Throwable err) {
                     FoxAblazeUltimateMod.LOGGER.debug("[Beelzebub BEWLR] 渲染异常 {}，跳过", err.toString());
                  }

                  pose.popPose();
               }
            }
         }
      }
   }

   private static float getGuiSpinAngle() {
      long ms = System.currentTimeMillis();
      return (float)(ms % 4000L) / 4000.0F * 360.0F;
   }

   public static BakedModel emptyBakedModel() {
      return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getMissingModel();
   }
}
