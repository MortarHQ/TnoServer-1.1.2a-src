package io.github.manasmods.tensura_neb.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.manasmods.tensura.item.tool.SimpleShieldItem;
import io.github.manasmods.tensura_neb.entity.CarrionEntity;
import io.github.manasmods.tensura_neb.registry.NebItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class CarrionRenderer<E extends CarrionEntity> extends GeoEntityRenderer<E> {
   private static final String LEFT_HAND = "leftItem";
   private static final String RIGHT_HAND = "rightItem";
   protected ItemStack mainHandItem;
   protected ItemStack offhandItem;

   public CarrionRenderer(Context renderManager) {
      super(renderManager, new CarrionModel());
      this.addRenderLayer(
         new BlockAndItemGeoLayer<E>(this) {
            @Nullable
            protected ItemStack getStackForBone(GeoBone bone, E animatable) {
               return switch (bone.getName()) {
                  case "leftItem" -> animatable.isLeftHanded() ? CarrionRenderer.this.mainHandItem : CarrionRenderer.this.offhandItem;
                  case "rightItem" -> animatable.isLeftHanded() ? CarrionRenderer.this.offhandItem : CarrionRenderer.this.mainHandItem;
                  default -> null;
               };
            }

            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, E animatable) {
               return switch (bone.getName()) {
                  case "leftItem", "rightItem" -> ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                  default -> ItemDisplayContext.NONE;
               };
            }

            protected void renderStackForBone(
               PoseStack poseStack,
               GeoBone bone,
               ItemStack stack,
               E animatable,
               MultiBufferSource bufferSource,
               float partialTick,
               int packedLight,
               int packedOverlay
            ) {
               if (stack == CarrionRenderer.this.mainHandItem) {
                  poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                  if (stack.getItem() instanceof ShieldItem || stack.getItem() instanceof SimpleShieldItem) {
                     poseStack.translate(0.0, 0.125, -0.25);
                  }
               } else if (stack == CarrionRenderer.this.offhandItem) {
                  poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                  if (stack.getItem() instanceof ShieldItem || stack.getItem() instanceof SimpleShieldItem) {
                     poseStack.translate(0.0, 0.125, 0.25);
                     poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                  }
               }

               super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }
         }
      );
   }

   protected float getShadowRadius(E entity) {
      return 0.2F;
   }

   public void preRender(
      PoseStack poseStack,
      E carrion,
      BakedGeoModel model,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      this.mainHandItem = carrion.getMainHandItem().is((Item)NebItems.WHITE_TIGER_AZURE_DRAGON_HALBERD.get()) ? ItemStack.EMPTY : carrion.getMainHandItem();
      this.offhandItem = carrion.getOffhandItem();
      this.entityRenderTranslations = new Matrix4f(poseStack.last().pose());
      float scale = carrion.isBaby() ? 0.5F : 1.0F;
      this.scaleModelForRender(
         this.scaleWidth * scale, this.scaleHeight * scale, poseStack, carrion, model, isReRender, partialTick, packedLight, packedOverlay
      );
   }
}
