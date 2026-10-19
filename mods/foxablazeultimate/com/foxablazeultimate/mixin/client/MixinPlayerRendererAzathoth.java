package com.foxablazeultimate.mixin.client;

import com.foxablazeultimate.client.AzathothChargeVisuals;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRendererAzathoth {
   @Inject(method = "renderHand", at = @At("TAIL"))
   private void foxablazeultimate$coilAroundBareArm(
      PoseStack pose, MultiBufferSource buffers, int light, AbstractClientPlayer player, ModelPart arm, ModelPart sleeve, CallbackInfo callback
   ) {
      PlayerModel<AbstractClientPlayer> model = (PlayerModel<AbstractClientPlayer>)((PlayerRenderer)this).getModel();
      ModelPart mainArm = player.getMainArm() == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
      if (arm == mainArm) {
         AzathothChargeVisuals.renderArm(player, arm, pose, buffers, Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false));
      }
   }
}
