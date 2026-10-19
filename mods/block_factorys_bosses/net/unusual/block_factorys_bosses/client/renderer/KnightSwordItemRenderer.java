package net.unusual.block_factorys_bosses.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.AnimationTickHolder;
import net.unusual.block_factorys_bosses.client.model.Modelknight_sword;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.item.KnightSwordItem;

@OnlyIn(Dist.CLIENT)
public class KnightSwordItemRenderer extends BlockEntityWithoutLevelRenderer {
   private final EntityModelSet entityModelSet;
   private final ItemStack transformSource;

   public KnightSwordItemRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
      super(blockEntityRenderDispatcher, entityModelSet);
      this.entityModelSet = entityModelSet;
      this.transformSource = new ItemStack((ItemLike)BossesRiseItems.KNIGHT_SWORD.get());
   }

   public void renderByItem(
      ItemStack itemstack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay
   ) {
      Modelknight_sword model = new Modelknight_sword(this.entityModelSet.bakeLayer(Modelknight_sword.LAYER_LOCATION));
      if (model != null) {
         if (displayContext != ItemDisplayContext.GUI) {
            float tick = AnimationTickHolder.getRenderTime();
            poseStack.pushPose();
            Minecraft.getInstance()
               .getItemRenderer()
               .getModel(this.transformSource, null, null, 0)
               .applyTransform(displayContext, poseStack, isLeftHand(displayContext));
            poseStack.translate(0.5, 1.55, 0.6);
            poseStack.scale(1.05F, 1.05F, 1.05F);
            poseStack.scale(1.0F, -1.0F, displayContext == ItemDisplayContext.GUI ? -1.0F : 1.0F);
            if (displayContext.firstPerson()) {
               poseStack.scale(1.1F, 1.1F, 1.1F);
               poseStack.mulPose(Axis.XP.rotationDegrees(-22.5F));
               if (displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
                  poseStack.translate(0.5, 0.4, -0.2);
               }

               if (displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
                  poseStack.translate(-0.5, 0.4, -0.2);
               }
            }

            String key = ((CustomData)itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag().getString("itemKey");
            AnimationState idleState = KnightSwordItem.IDLE_STATES.computeIfAbsent(key, s -> new AnimationState());
            AnimationState swingState = KnightSwordItem.SWING_STATES.computeIfAbsent(key, s -> new AnimationState());
            double swing_animtime = ((CustomData)itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag().getDouble("swing_animtime");
            idleState.animateWhen(swing_animtime == 0.0, Math.round(tick));
            swingState.animateWhen(swing_animtime > 0.0, Math.round(tick));
            int firstPersonContext = 0;
            if (displayContext.firstPerson()) {
               if (displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
                  firstPersonContext = 1;
               }

               if (displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
                  firstPersonContext = 2;
               }
            }

            model.setupItemAnim(key, firstPersonContext, tick);
            int index = Mth.floor(Math.round(AnimationTickHolder.getRenderTime()) % 100 * 0.1F);
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(
               bufferSource, model.renderType(BossesRise.prefix("textures/entities/knight_sword/knight_sword_" + index + ".png")), false, itemstack.hasFoil()
            );
            model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
            poseStack.popPose();
         }
      }
   }

   private static boolean isLeftHand(ItemDisplayContext type) {
      return type == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || type == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
   }

   private static boolean isInventory(ItemDisplayContext type) {
      return type == ItemDisplayContext.GUI || type == ItemDisplayContext.FIXED;
   }
}
