package net.unusual.block_factorys_bosses.mixins.client;

import java.util.Collections;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.unusual.block_factorys_bosses.attachment.entity.RollAttachment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(ElytraModel.class)
public abstract class ElytraLayerMixin<T extends LivingEntity> {
   @Shadow
   @Final
   private ModelPart leftWing;
   @Shadow
   @Final
   private ModelPart rightWing;

   @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
   private void preSetup(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
      if (entity instanceof AbstractClientPlayer) {
         this.leftWing.resetPose();
         this.rightWing.resetPose();
      }
   }

   @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
   private void postSetup(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
      if (entity instanceof AbstractClientPlayer player) {
         RollAttachment attachment = RollAttachment.fromPlayer(player);
         if (attachment.isRolling() && Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player) instanceof PlayerRenderer renderer) {
            ModelPart cloak = ((PlayerModel)renderer.getModel()).body;
            this.leftWing.resetPose();
            ModelPart left = new ModelPart(Collections.emptyList(), Collections.emptyMap());
            left.copyFrom(this.leftWing);
            this.leftWing.copyFrom(cloak);
            this.leftWing.x = this.leftWing.x + left.x;
            this.leftWing.z = this.leftWing.z + left.z;
            this.leftWing.y = this.leftWing.y + left.y;
            this.leftWing.xRot = this.leftWing.xRot + left.xRot;
            this.leftWing.yRot = this.leftWing.yRot + left.yRot;
            this.leftWing.zRot = this.leftWing.zRot + left.zRot;
            this.rightWing.resetPose();
            ModelPart right = new ModelPart(Collections.emptyList(), Collections.emptyMap());
            right.copyFrom(this.rightWing);
            this.rightWing.copyFrom(cloak);
            this.rightWing.x = this.rightWing.x + right.x;
            this.rightWing.z = this.rightWing.z + right.z;
            this.rightWing.y = this.rightWing.y + right.y;
            this.rightWing.xRot = this.rightWing.xRot + right.xRot;
            this.rightWing.yRot = this.rightWing.yRot + right.yRot;
            this.rightWing.zRot = this.rightWing.zRot + right.zRot;
         }
      }
   }
}
