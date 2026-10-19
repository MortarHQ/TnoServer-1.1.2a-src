package io.github.Memoires.mysticism.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.github.Memoires.mysticism.client.model.OkamiModel;
import io.github.Memoires.mysticism.entity.monster.OkamiEntity;
import io.github.manasmods.tensura.item.tool.SimpleShieldItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;

public class OkamiRenderer extends GeoEntityRenderer<OkamiEntity> {
   protected ItemStack mainHandItem;
   protected ItemStack offhandItem;

   public OkamiRenderer(Context renderManager) {
      super(renderManager, new OkamiModel());
      this.addRenderLayer(new ItemArmorGeoLayer<OkamiEntity>(this) {
         @Nullable
         protected ItemStack getArmorItemForBone(GeoBone bone, OkamiEntity animatable) {
            return switch (bone.getName()) {
               case "LeftBootArmor", "RightBootArmor" -> this.bootsStack;
               case "LeftLeg", "RightLeg" -> this.leggingsStack;
               case "Body", "RightArmcoat", "LeftArmcoat" -> this.chestplateStack;
               case "Head" -> this.helmetStack;
               default -> null;
            };
         }

         @NotNull
         protected EquipmentSlot getEquipmentSlotForBone(GeoBone bone, ItemStack stack, OkamiEntity animatable) {
            return switch (bone.getName()) {
               case "LeftBootArmor", "RightBootArmor" -> EquipmentSlot.FEET;
               case "LeftLeg", "RightLeg" -> EquipmentSlot.LEGS;
               case "RightArmcoat" -> !animatable.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
               case "LeftArmcoat" -> animatable.isLeftHanded() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
               case "Body" -> EquipmentSlot.CHEST;
               case "Head" -> EquipmentSlot.HEAD;
               default -> super.getEquipmentSlotForBone(bone, stack, animatable);
            };
         }

         @NotNull
         protected ModelPart getModelPartForBone(GeoBone bone, EquipmentSlot slot, ItemStack stack, OkamiEntity animatable, HumanoidModel<?> baseModel) {
            return switch (bone.getName()) {
               case "LeftBootArmor", "LeftLeg" -> baseModel.leftLeg;
               case "RightBootArmor", "RightLeg" -> baseModel.rightLeg;
               case "RightArmcoat" -> baseModel.rightArm;
               case "LeftArmcoat" -> baseModel.leftArm;
               case "Body" -> baseModel.body;
               case "Head" -> baseModel.head;
               default -> super.getModelPartForBone(bone, slot, stack, animatable, baseModel);
            };
         }
      });
      this.addRenderLayer(
         new BlockAndItemGeoLayer<OkamiEntity>(this) {
            @Nullable
            protected ItemStack getStackForBone(GeoBone bone, OkamiEntity animatable) {
               return switch (bone.getName()) {
                  case "leftItem" -> animatable.isLeftHanded() ? OkamiRenderer.this.mainHandItem : OkamiRenderer.this.offhandItem;
                  case "rightItem" -> animatable.isLeftHanded() ? OkamiRenderer.this.offhandItem : OkamiRenderer.this.mainHandItem;
                  default -> null;
               };
            }

            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, OkamiEntity animatable) {
               return switch (bone.getName()) {
                  case "leftItem", "rightItem" -> ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                  default -> ItemDisplayContext.NONE;
               };
            }

            protected void renderStackForBone(
               PoseStack poseStack,
               GeoBone bone,
               ItemStack stack,
               OkamiEntity animatable,
               MultiBufferSource bufferSource,
               float partialTick,
               int packedLight,
               int packedOverlay
            ) {
               if (stack == OkamiRenderer.this.mainHandItem) {
                  poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                  if (stack.getItem() instanceof ShieldItem || stack.getItem() instanceof SimpleShieldItem) {
                     poseStack.translate(0.0, 0.125, -0.25);
                  }
               } else if (stack == OkamiRenderer.this.offhandItem) {
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

   protected float getShadowRadius(OkamiEntity entity) {
      return entity.isBaby() ? 0.25F : 0.5F;
   }

   public void preRender(
      PoseStack poseStack,
      OkamiEntity okami,
      BakedGeoModel model,
      @Nullable MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      boolean isReRender,
      float partialTick,
      int packedLight,
      int packedOverlay,
      int colour
   ) {
      this.mainHandItem = okami.getMainHandItem();
      this.offhandItem = okami.getOffhandItem();
      this.entityRenderTranslations = new Matrix4f(poseStack.last().pose());
      float scale = okami.isBaby() ? 0.5F : 1.0F;
      this.scaleModelForRender(this.scaleWidth * scale, this.scaleHeight * scale, poseStack, okami, model, isReRender, partialTick, packedLight, packedOverlay);
      boolean hasLegArmor = okami.getItemBySlot(EquipmentSlot.LEGS) != null && !okami.getItemBySlot(EquipmentSlot.LEGS).isEmpty();
      GeoBone rightLegCoat2 = (GeoBone)model.getBone("RightLegCoat2").orElse(null);
      GeoBone leftLegCoat2 = (GeoBone)model.getBone("LeftLegCoat2").orElse(null);
      boolean hasHelmet = okami.getItemBySlot(EquipmentSlot.HEAD) != null && !okami.getItemBySlot(EquipmentSlot.HEAD).isEmpty();
      GeoBone ear1 = (GeoBone)model.getBone("bone10").orElse(null);
      GeoBone ear2 = (GeoBone)model.getBone("bone11").orElse(null);
      GeoBone hair1 = (GeoBone)model.getBone("bone9").orElse(null);
      GeoBone hair2 = (GeoBone)model.getBone("bone").orElse(null);
      if (rightLegCoat2 != null) {
         rightLegCoat2.setHidden(hasLegArmor);
      }

      if (leftLegCoat2 != null) {
         leftLegCoat2.setHidden(hasLegArmor);
      }

      if (ear1 != null) {
         ear1.setHidden(hasHelmet);
      }

      if (ear2 != null) {
         ear2.setHidden(hasHelmet);
      }

      if (hair1 != null) {
         hair1.setHidden(hasHelmet);
      }

      if (hair2 != null) {
         hair2.setHidden(hasHelmet);
      }
   }
}
