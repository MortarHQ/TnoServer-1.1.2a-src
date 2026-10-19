package com.tensura_tno.client.race;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.component.DyedItemColor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.object.GeoCube;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;

final class PlayerOrcArmorLayer extends GeoRenderLayer<PlayerOrcAnimatable> {
   private static final HumanoidModel<LivingEntity> INNER_ARMOR_MODEL = new HumanoidModel(
      Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)
   );
   private static final HumanoidModel<LivingEntity> OUTER_ARMOR_MODEL = new HumanoidModel(
      Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)
   );
   private static final String LEFT_BOOT = "LeftBootArmor";
   private static final String RIGHT_BOOT = "RightBootArmor";
   private static final String LEFT_LEG = "LeftLegArmor";
   private static final String RIGHT_LEG = "RightLegArmor";
   private static final String BODY = "ChestArmor";
   private static final String RIGHT_ARM = "RightArmArmor";
   private static final String LEFT_ARM = "LeftArmArmor";
   private static final String HEAD = "HeadArmor";
   @Nullable
   private ItemStack helmetStack;
   @Nullable
   private ItemStack chestplateStack;
   @Nullable
   private ItemStack leggingsStack;
   @Nullable
   private ItemStack bootsStack;

   PlayerOrcArmorLayer(GeoRenderer<PlayerOrcAnimatable> renderer) {
      super(renderer);
   }

   public void preRender(
      PoseStack poseStack,
      PlayerOrcAnimatable animatable,
      BakedGeoModel bakedModel,
      @Nullable RenderType renderType,
      MultiBufferSource bufferSource,
      @Nullable VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      Player player = animatable.getPlayer();
      this.helmetStack = getEquipped(player, EquipmentSlot.HEAD);
      this.chestplateStack = getEquipped(player, EquipmentSlot.CHEST);
      this.leggingsStack = getEquipped(player, EquipmentSlot.LEGS);
      this.bootsStack = getEquipped(player, EquipmentSlot.FEET);
   }

   public void renderForBone(
      PoseStack poseStack,
      PlayerOrcAnimatable animatable,
      GeoBone bone,
      RenderType renderType,
      MultiBufferSource bufferSource,
      VertexConsumer buffer,
      float partialTick,
      int packedLight,
      int packedOverlay
   ) {
      ItemStack armorStack = this.getArmorItemForBone(bone);
      if (armorStack != null && armorStack.getItem() instanceof ArmorItem) {
         if (!bone.getCubes().isEmpty()) {
            EquipmentSlot slot = getEquipmentSlotForBone(bone);
            HumanoidModel<?> model = slot == EquipmentSlot.LEGS ? INNER_ARMOR_MODEL : OUTER_ARMOR_MODEL;
            ModelPart modelPart = getModelPartForBone(bone, model);
            poseStack.pushPose();
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            prepModelPartForRender(poseStack, bone, modelPart);
            modelPart.visible = true;
            renderVanillaArmorPiece(poseStack, bone, slot, armorStack, modelPart, bufferSource, packedLight, packedOverlay);
            poseStack.popPose();
         }
      }
   }

   @Nullable
   private ItemStack getArmorItemForBone(GeoBone bone) {
      ItemStack stack = switch (bone.getName()) {
         case "LeftBootArmor", "RightBootArmor" -> this.bootsStack;
         case "LeftLegArmor", "RightLegArmor" -> this.leggingsStack;
         case "ChestArmor", "RightArmArmor", "LeftArmArmor" -> this.chestplateStack;
         case "HeadArmor" -> this.helmetStack;
         default -> null;
      };
      return stack != null && !stack.isEmpty() ? stack : null;
   }

   private static EquipmentSlot getEquipmentSlotForBone(GeoBone bone) {
      return switch (bone.getName()) {
         case "LeftBootArmor", "RightBootArmor" -> EquipmentSlot.FEET;
         case "LeftLegArmor", "RightLegArmor" -> EquipmentSlot.LEGS;
         case "HeadArmor" -> EquipmentSlot.HEAD;
         default -> EquipmentSlot.CHEST;
      };
   }

   private static ModelPart getModelPartForBone(GeoBone bone, HumanoidModel<?> baseModel) {
      return switch (bone.getName()) {
         case "LeftBootArmor", "LeftLegArmor" -> baseModel.leftLeg;
         case "RightBootArmor", "RightLegArmor" -> baseModel.rightLeg;
         case "RightArmArmor" -> baseModel.rightArm;
         case "LeftArmArmor" -> baseModel.leftArm;
         case "HeadArmor" -> baseModel.head;
         default -> baseModel.body;
      };
   }

   private static void renderVanillaArmorPiece(
      PoseStack poseStack,
      GeoBone bone,
      EquipmentSlot slot,
      ItemStack armorStack,
      ModelPart modelPart,
      MultiBufferSource bufferSource,
      int packedLight,
      int packedOverlay
   ) {
      Holder<ArmorMaterial> material = ((ArmorItem)armorStack.getItem()).getMaterial();

      for (Layer layer : ((ArmorMaterial)material.value()).layers()) {
         int color = armorStack.is(ItemTags.DYEABLE) ? DyedItemColor.getOrDefault(armorStack, -6265536) : -1;
         VertexConsumer armorBuffer = getVanillaArmorBuffer(bufferSource, armorStack, slot, bone, layer, false);
         modelPart.render(poseStack, armorBuffer, packedLight, packedOverlay, color);
      }

      ArmorTrim trim = (ArmorTrim)armorStack.get(DataComponents.TRIM);
      if (trim != null) {
         TextureAtlasSprite sprite = Minecraft.getInstance()
            .getModelManager()
            .getAtlas(Sheets.ARMOR_TRIMS_SHEET)
            .getSprite(slot == EquipmentSlot.LEGS ? trim.innerTexture(material) : trim.outerTexture(material));
         VertexConsumer trimBuffer = sprite.wrap(bufferSource.getBuffer(Sheets.armorTrimsSheet(((TrimPattern)trim.pattern().value()).decal())));
         modelPart.render(poseStack, trimBuffer, packedLight, packedOverlay);
      }

      if (armorStack.hasFoil()) {
         modelPart.render(poseStack, getVanillaArmorBuffer(bufferSource, armorStack, slot, bone, null, true), packedLight, packedOverlay, Color.WHITE.argbInt());
      }
   }

   private static VertexConsumer getVanillaArmorBuffer(
      MultiBufferSource bufferSource, ItemStack stack, EquipmentSlot slot, GeoBone bone, @Nullable Layer layer, boolean forGlint
   ) {
      return forGlint
         ? bufferSource.getBuffer(RenderType.armorEntityGlint())
         : bufferSource.getBuffer(RenderType.armorCutoutNoCull(layer.texture(slot == EquipmentSlot.LEGS)));
   }

   private static void prepModelPartForRender(PoseStack poseStack, GeoBone bone, ModelPart sourcePart) {
      GeoCube firstCube = (GeoCube)bone.getCubes().getFirst();
      PlayerOrcArmorLayer.ReferenceSize armorCube = getReferenceSizeForBone(bone);
      double armorBoneSizeX = firstCube.size().x();
      double armorBoneSizeY = firstCube.size().y();
      double armorBoneSizeZ = firstCube.size().z();
      double actualArmorSizeX = armorCube.x();
      double actualArmorSizeY = armorCube.y();
      double actualArmorSizeZ = armorCube.z();
      float scaleX = (float)(armorBoneSizeX / actualArmorSizeX);
      float scaleY = (float)(armorBoneSizeY / actualArmorSizeY);
      float scaleZ = (float)(armorBoneSizeZ / actualArmorSizeZ);
      sourcePart.setPos(
         -(bone.getPivotX() - (bone.getPivotX() * scaleX - bone.getPivotX()) / scaleX),
         -(bone.getPivotY() - (bone.getPivotY() * scaleY - bone.getPivotY()) / scaleY),
         bone.getPivotZ() - (bone.getPivotZ() * scaleZ - bone.getPivotZ()) / scaleZ
      );
      sourcePart.xRot = -bone.getRotX();
      sourcePart.yRot = -bone.getRotY();
      sourcePart.zRot = bone.getRotZ();
      poseStack.scale(scaleX, scaleY, scaleZ);
   }

   private static PlayerOrcArmorLayer.ReferenceSize getReferenceSizeForBone(GeoBone bone) {
      return switch (bone.getName()) {
         case "HeadArmor" -> new PlayerOrcArmorLayer.ReferenceSize(8.0, 8.0, 8.0);
         case "ChestArmor" -> new PlayerOrcArmorLayer.ReferenceSize(8.0, 12.0, 4.0);
         default -> new PlayerOrcArmorLayer.ReferenceSize(4.0, 12.0, 4.0);
      };
   }

   private static ItemStack getEquipped(@Nullable Player player, EquipmentSlot slot) {
      return PlayerRaceArmorRenderCompatibility.getRenderableArmor(player, slot);
   }

   private record ReferenceSize(double x, double y, double z) {
   }
}
