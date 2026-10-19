package de.ellpeck.naturesaura.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.render.ITrinketItem;
import de.ellpeck.naturesaura.compat.Compat;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@OnlyIn(Dist.CLIENT)
public class PlayerLayerTrinkets extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   private final Set<Item> alreadyRendered = new HashSet<>();

   public PlayerLayerTrinkets(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> p_117346_) {
      super(p_117346_);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      AbstractClientPlayer player,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if ((Boolean)ModConfig.instance.renderItemsOnPlayer.get()) {
         if (player.getEffect(MobEffects.INVISIBILITY) == null) {
            ItemStack main = player.getMainHandItem();
            ItemStack second = player.getOffhandItem();
            this.alreadyRendered.clear();
            matrixStackIn.pushPose();
            this.render(player, ITrinketItem.RenderType.BODY, main, second, matrixStackIn, bufferIn, packedLightIn);
            float yaw = player.yHeadRotO + (player.yHeadRot - player.yHeadRotO) * partialTicks;
            float yawOffset = player.yBodyRotO + (player.yBodyRot - player.yBodyRotO) * partialTicks;
            float pitch = player.xRotO + (player.getXRot() - player.xRotO) * partialTicks;
            matrixStackIn.mulPose(Axis.YN.rotationDegrees(yawOffset));
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(yaw - 270.0F));
            matrixStackIn.mulPose(Axis.ZP.rotationDegrees(pitch));
            this.render(player, ITrinketItem.RenderType.HEAD, main, second, matrixStackIn, bufferIn, packedLightIn);
            matrixStackIn.popPose();
         }
      }
   }

   private void render(
      Player player, ITrinketItem.RenderType type, ItemStack main, ItemStack second, PoseStack matrices, MultiBufferSource buffer, int packedLight
   ) {
      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         this.renderStack(player.getInventory().getItem(i), player, type, main, second, matrices, buffer, packedLight);
      }

      if (Compat.hasCompat("curios")) {
         ICuriosItemHandler inventory = (ICuriosItemHandler)CuriosApi.getCuriosInventory(player).orElse(null);
         if (inventory != null) {
            IItemHandlerModifiable handler = inventory.getEquippedCurios();

            for (int i = 0; i < handler.getSlots(); i++) {
               this.renderStack(handler.getStackInSlot(i), player, type, main, second, matrices, buffer, packedLight);
            }
         }
      }
   }

   private void renderStack(
      ItemStack stack,
      Player player,
      ITrinketItem.RenderType type,
      ItemStack main,
      ItemStack second,
      PoseStack matrices,
      MultiBufferSource buffer,
      int packedLight
   ) {
      if (!stack.isEmpty()) {
         Item item = stack.getItem();
         if (item instanceof ITrinketItem && !this.alreadyRendered.contains(item)) {
            matrices.pushPose();
            if (type == ITrinketItem.RenderType.BODY && player.getPose() == Pose.CROUCHING) {
               matrices.translate(0.0F, 0.2F, 0.0F);
               matrices.mulPose(Axis.XP.rotationDegrees(28.647888F));
            }

            ((ITrinketItem)item).render(stack, player, type, matrices, buffer, packedLight, stack == main || stack == second);
            matrices.popPose();
            this.alreadyRendered.add(item);
         }
      }
   }
}
