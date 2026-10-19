package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.client.model.animation.PlayerAnimator;
import cn.leolezury.eternalstarlight.common.item.combat.SeedsLauncherItem;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESAccessoryUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemInHandRenderer.HandRenderSelection;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
   @Shadow
   @Final
   private Minecraft minecraft;
   @Shadow
   @Final
   private EntityRenderDispatcher entityRenderDispatcher;
   @Shadow
   private ItemStack mainHandItem;
   @Shadow
   private ItemStack offHandItem;

   @Shadow
   public abstract void renderItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, boolean var4, PoseStack var5, MultiBufferSource var6, int var7);

   @Inject(
      method = "evaluateWhichHandsToRender(Lnet/minecraft/client/player/LocalPlayer;)Lnet/minecraft/client/renderer/ItemInHandRenderer$HandRenderSelection;",
      at = @At("RETURN"),
      cancellable = true
   )
   private static void evaluateWhichHandsToRender(LocalPlayer player, CallbackInfoReturnable<HandRenderSelection> cir) {
      ItemStack mainhand = player.getMainHandItem();
      ItemStack offhand = player.getOffhandItem();
      boolean flag = BuiltInRegistries.ITEM.getKey(mainhand.getItem()).getNamespace().equals("eternal_starlight")
         && (mainhand.getItem() instanceof BowItem || mainhand.getItem() instanceof CrossbowItem);
      boolean flag1 = BuiltInRegistries.ITEM.getKey(offhand.getItem()).getNamespace().equals("eternal_starlight")
         && (offhand.getItem() instanceof BowItem || offhand.getItem() instanceof CrossbowItem);
      if (flag || flag1) {
         if (player.isUsingItem()) {
            ItemStack useItem = player.getUseItem();
            InteractionHand hand = player.getUsedItemHand();
            if (BuiltInRegistries.ITEM.getKey(useItem.getItem()).getNamespace().equals("eternal_starlight")
               && (useItem.getItem() instanceof BowItem || useItem.getItem() instanceof CrossbowItem)) {
               cir.setReturnValue(HandRenderSelection.onlyForHand(hand));
            }
         } else {
            cir.setReturnValue(
               BuiltInRegistries.ITEM.getKey(mainhand.getItem()).getNamespace().equals("eternal_starlight")
                     && mainhand.getItem() instanceof CrossbowItem
                     && CrossbowItem.isCharged(mainhand)
                  ? HandRenderSelection.RENDER_MAIN_HAND_ONLY
                  : HandRenderSelection.RENDER_BOTH_HANDS
            );
         }
      }
   }

   @Inject(method = "applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V", at = @At("RETURN"))
   private void applyItemArmTransform(PoseStack stack, HumanoidArm arm, float equipProgress, CallbackInfo ci) {
      if (ESClientHandler.oldSeedsLauncherAnimTicks != 0 || ESClientHandler.seedsLauncherAnimTicks != 0) {
         float anim = Mth.lerp(
            Minecraft.getInstance()
               .getTimer()
               .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()),
            ESClientHandler.oldSeedsLauncherAnimTicks,
            ESClientHandler.seedsLauncherAnimTicks
         );
         stack.translate(0.0, 0.0, Mth.sin(anim / 5.0F * (float) Math.PI) * 0.05);
      }
   }

   @WrapOperation(
      method = "tick()V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/item/ItemStack;matches(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z",
         ordinal = 0
      )
   )
   private boolean mainHandMatch(ItemStack stack1, ItemStack stack2, Operation<Boolean> original) {
      if (stack1.getItem() instanceof SeedsLauncherItem && stack2.getItem() instanceof SeedsLauncherItem) {
         ItemStack copied = stack1.copy();
         copied.set(DataComponents.DAMAGE, (Integer)stack2.get(DataComponents.DAMAGE));
         return (Boolean)original.call(new Object[]{copied, stack2});
      } else {
         return (Boolean)original.call(new Object[]{stack1, stack2});
      }
   }

   @WrapOperation(
      method = "tick()V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/item/ItemStack;matches(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z",
         ordinal = 1
      )
   )
   private boolean offhandMatch(ItemStack stack1, ItemStack stack2, Operation<Boolean> original) {
      if (stack1.getItem() instanceof SeedsLauncherItem && stack2.getItem() instanceof SeedsLauncherItem) {
         ItemStack copied = stack1.copy();
         copied.set(DataComponents.DAMAGE, (Integer)stack2.get(DataComponents.DAMAGE));
         return (Boolean)original.call(new Object[]{copied, stack2});
      } else {
         return (Boolean)original.call(new Object[]{stack1, stack2});
      }
   }

   @WrapOperation(
      method = "renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;")
   )
   private UseAnim getUseAnimation(ItemStack instance, Operation<UseAnim> original, @Local(argsOnly = true) AbstractClientPlayer player) {
      return ESAccessoryUtil.getActiveAccessoriesOnArmors(player).contains(ESItems.FUNGUS_AMULET.get())
            && instance.is(ESTags.Items.CONSUMABLE_WHEN_WEARING_FUNGUS_AMULET)
         ? UseAnim.EAT
         : (UseAnim)original.call(new Object[]{instance});
   }

   @Inject(
      method = "renderArmWithItem(Lnet/minecraft/client/player/AbstractClientPlayer;FFLnet/minecraft/world/InteractionHand;FLnet/minecraft/world/item/ItemStack;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private void renderArmWithItemForAnimation(
      AbstractClientPlayer abstractClientPlayer,
      float f,
      float g,
      InteractionHand interactionHand,
      float h,
      ItemStack itemStack,
      float i,
      PoseStack poseStack,
      MultiBufferSource multiBufferSource,
      int j,
      CallbackInfo ci
   ) {
      PlayerAnimator.renderingFirstPersonPlayer = true;
      boolean renderLeft = false;
      boolean renderRight = false;

      for (Entry<PlayerAnimator.AnimationTrigger, PlayerAnimator.AnimationStateFunction> entry : PlayerAnimator.ANIMATIONS.entrySet()) {
         if (entry.getKey().shouldPlay(abstractClientPlayer)) {
            PlayerAnimator.PlayerAnimationState state = entry.getValue().get(abstractClientPlayer);
            renderLeft = renderLeft || state.renderLeftArm();
            renderRight = renderRight || state.renderRightArm();
            ci.cancel();
         }
      }

      if (renderLeft || renderRight) {
         boolean mainHand = interactionHand == InteractionHand.MAIN_HAND;
         boolean rightMain = abstractClientPlayer.getMainArm() == HumanoidArm.RIGHT;
         poseStack.pushPose();
         boolean mainArm = renderRight && (mainHand && rightMain || !mainHand && !rightMain);
         boolean oppositeArm = renderLeft && (mainHand && !rightMain || !mainHand && rightMain);
         float n = Mth.sqrt(h);
         float k = -0.2F * Mth.sin(h * (float) Math.PI);
         float l = -0.4F * Mth.sin(n * (float) Math.PI);
         poseStack.translate(0.0F, -k / 2.0F, l);
         poseStack.translate(0.0F, 0.04F + i * -1.2F, -0.72F);
         poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
         if (mainArm) {
            HumanoidArm humanoidArm = abstractClientPlayer.getMainArm();
            this.renderPlayerArm(poseStack, multiBufferSource, j, mainHand, humanoidArm);
         }

         if (oppositeArm) {
            HumanoidArm humanoidArm = abstractClientPlayer.getMainArm().getOpposite();
            this.renderPlayerArm(poseStack, multiBufferSource, j, mainHand, humanoidArm);
         }

         poseStack.popPose();
      }

      PlayerAnimator.renderingFirstPersonPlayer = false;
   }

   @Unique
   private void renderPlayerArm(PoseStack poseStack, MultiBufferSource buffer, int light, boolean mainHand, HumanoidArm humanoidArm) {
      poseStack.pushPose();
      boolean rightArm = humanoidArm != HumanoidArm.LEFT;
      float leftRight = rightArm ? 1.0F : -1.0F;
      AbstractClientPlayer player = this.minecraft.player;
      poseStack.mulPose(Axis.YP.rotationDegrees(92.0F));
      poseStack.mulPose(Axis.XP.rotationDegrees(88.0F));
      poseStack.translate(leftRight * -0.01F, -0.4, 0.45F);
      if (rightArm) {
         this.renderRightHand(poseStack, buffer, light, mainHand, player);
      } else {
         this.renderLeftHand(poseStack, buffer, light, mainHand, player);
      }

      poseStack.popPose();
   }

   @Unique
   public void renderRightHand(PoseStack poseStack, MultiBufferSource buffer, int light, boolean mainHand, AbstractClientPlayer abstractClientPlayer) {
      PlayerRenderer playerRenderer = (PlayerRenderer)this.entityRenderDispatcher.getRenderer(abstractClientPlayer);
      PlayerModel<AbstractClientPlayer> playerModel = (PlayerModel<AbstractClientPlayer>)playerRenderer.getModel();
      this.renderHand(poseStack, buffer, light, mainHand, false, abstractClientPlayer, playerModel.rightArm, playerModel.rightSleeve);
   }

   @Unique
   public void renderLeftHand(PoseStack poseStack, MultiBufferSource buffer, int light, boolean mainHand, AbstractClientPlayer abstractClientPlayer) {
      PlayerRenderer playerRenderer = (PlayerRenderer)this.entityRenderDispatcher.getRenderer(abstractClientPlayer);
      PlayerModel<AbstractClientPlayer> playerModel = (PlayerModel<AbstractClientPlayer>)playerRenderer.getModel();
      this.renderHand(poseStack, buffer, light, mainHand, true, abstractClientPlayer, playerModel.leftArm, playerModel.leftSleeve);
   }

   @Unique
   private void renderHand(
      PoseStack poseStack,
      MultiBufferSource buffer,
      int light,
      boolean mainHand,
      boolean leftHand,
      AbstractClientPlayer abstractClientPlayer,
      ModelPart modelPart,
      ModelPart modelPart2
   ) {
      PlayerRenderer playerRenderer = (PlayerRenderer)this.entityRenderDispatcher.getRenderer(abstractClientPlayer);
      PlayerModel<AbstractClientPlayer> playerModel = (PlayerModel<AbstractClientPlayer>)playerRenderer.getModel();
      playerRenderer.setModelProperties(abstractClientPlayer);
      playerModel.attackTime = 0.0F;
      playerModel.crouching = false;
      playerModel.swimAmount = 0.0F;
      playerModel.setupAnim(abstractClientPlayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      ResourceLocation resourceLocation = abstractClientPlayer.getSkin().texture();
      modelPart.render(poseStack, buffer.getBuffer(RenderType.entitySolid(resourceLocation)), light, OverlayTexture.NO_OVERLAY);
      modelPart2.render(poseStack, buffer.getBuffer(RenderType.entityTranslucent(resourceLocation)), light, OverlayTexture.NO_OVERLAY);
      poseStack.pushPose();
      playerModel.translateToHand(leftHand ? HumanoidArm.LEFT : HumanoidArm.RIGHT, poseStack);
      poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
      poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
      poseStack.translate((leftHand ? -1 : 1) / 16.0F, 0.125F, -0.625F);
      ItemStack var10002 = mainHand ? this.mainHandItem : this.offHandItem;
      ItemDisplayContext var10003 = leftHand ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
      this.renderItem(Minecraft.getInstance().player, var10002, var10003, leftHand, poseStack, buffer, light);
      poseStack.popPose();
   }
}
