package net.p3pp3rf1y.sophisticatedbackpacks.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.util.PlayerInventoryProvider;
import net.p3pp3rf1y.sophisticatedcore.api.IUpgradeRenderer;
import net.p3pp3rf1y.sophisticatedcore.client.render.UpgradeRenderRegistry;
import net.p3pp3rf1y.sophisticatedcore.renderdata.IUpgradeRenderData;
import net.p3pp3rf1y.sophisticatedcore.renderdata.RenderInfo;
import net.p3pp3rf1y.sophisticatedcore.renderdata.UpgradeRenderDataType;
import org.joml.Vector3f;

public class BackpackLayerRenderer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   public static final float CHILD_Y_OFFSET = 0.3F;
   public static final float CHILD_Z_OFFSET = 0.1F;
   public static final float CHILD_SCALE = 0.55F;
   private static ItemRenderer itemRenderer;

   public BackpackLayerRenderer(RenderLayerParent<T, M> entityRendererIn) {
      super(entityRendererIn);
      itemRenderer = Minecraft.getInstance().getItemRenderer();
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLight,
      T entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (entity instanceof AbstractClientPlayer player) {
         PlayerInventoryProvider.get().getBackpackFromRendered(player).ifPresent(backpackRenderInfo -> {
            poseStack.pushPose();
            ItemStack backpack = backpackRenderInfo.getBackpack();
            boolean wearsArmor = !backpackRenderInfo.isArmorSlot() && !((ItemStack)player.getInventory().armor.get(EquipmentSlot.CHEST.getIndex())).isEmpty();
            renderBackpack((M)this.getParentModel(), player, poseStack, buffer, packedLight, backpack, wearsArmor);
            poseStack.popPose();
         });
      } else {
         poseStack.pushPose();
         ItemStack chestStack = entity.getItemBySlot(EquipmentSlot.CHEST);
         if (chestStack.getItem() instanceof BackpackItem) {
            renderBackpack((M)this.getParentModel(), entity, poseStack, buffer, packedLight, chestStack, false);
         }

         poseStack.popPose();
      }
   }

   public static <T extends LivingEntity, M extends EntityModel<T>> void renderBackpack(
      M parentModel, LivingEntity livingEntity, PoseStack poseStack, MultiBufferSource buffer, int packedLight, ItemStack backpack, boolean wearsArmor
   ) {
      translateRotateAndScale(parentModel, livingEntity, poseStack, wearsArmor);
      IBackpackWrapper wrapper = BackpackWrapper.fromStack(backpack);
      itemRenderer.renderStatic(backpack, BackpackDynamicModel.WORN, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntity.level(), 0);
      renderUpgrades(livingEntity, wrapper.getRenderInfo());
   }

   private static <L extends LivingEntity, M extends EntityModel<L>> void translateRotateAndScale(
      M parentModel, LivingEntity livingEntity, PoseStack poseStack, boolean wearsArmor
   ) {
      if (parentModel instanceof HumanoidModel<?> humanoidModel) {
         humanoidModel.body.translateAndRotate(poseStack);
      }

      poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
      poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
      float zOffset = wearsArmor ? -0.35F : -0.3F;
      float yOffset = -0.25F;
      if (livingEntity.isBaby()) {
         zOffset += 0.1F;
         yOffset = 0.3F;
      }

      poseStack.translate(0.0F, yOffset, zOffset);
      if (!(livingEntity instanceof Player)) {
         if (livingEntity.isBaby()) {
            poseStack.scale(0.55F, 0.55F, 0.55F);
         }
      }
   }

   private static void renderUpgrades(LivingEntity livingEntity, RenderInfo renderInfo) {
      if (!Minecraft.getInstance().isPaused() && livingEntity.level().random.nextInt(32) == 0) {
         renderInfo.getUpgradeRenderData()
            .forEach(
               (type, data) -> UpgradeRenderRegistry.getUpgradeRenderer(type)
                  .ifPresent(renderer -> renderUpgrade((IUpgradeRenderer<T>)renderer, livingEntity, (UpgradeRenderDataType<?>)type, data))
            );
      }
   }

   private static Vector3f getBackpackMiddleFacePoint(LivingEntity livingEntity, Vector3f vector) {
      Vector3f point = new Vector3f(vector);
      point.rotate(Axis.XP.rotationDegrees(livingEntity.isCrouching() ? 25.0F : 0.0F));
      point.add(0.0F, 0.8F, livingEntity.isCrouching() ? 0.9F : 0.7F);
      point.rotate(Axis.YN.rotationDegrees(livingEntity.yBodyRot - 180.0F));
      point.add(livingEntity.position().toVector3f());
      return point;
   }

   private static <T extends IUpgradeRenderData> void renderUpgrade(
      IUpgradeRenderer<T> renderer, LivingEntity livingEntity, UpgradeRenderDataType<?> type, IUpgradeRenderData data
   ) {
      type.cast(data)
         .ifPresent(
            renderData -> renderer.render(
               livingEntity.level(), livingEntity.level().random, vector3d -> getBackpackMiddleFacePoint(livingEntity, vector3d), renderData
            )
         );
   }
}
