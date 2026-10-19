package fuzs.illagerinvasion.mixin.client;

import fuzs.illagerinvasion.world.entity.monster.Basher;
import fuzs.illagerinvasion.world.entity.monster.Marauder;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.AbstractIllager.IllagerArmPose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IllagerModel.class)
abstract class IllagerModelMixin<T extends AbstractIllager> extends HierarchicalModel<T> {
   @Shadow
   @Final
   private ModelPart rightArm;
   @Shadow
   @Final
   private ModelPart head;
   @Shadow
   @Final
   private ModelPart leftArm;

   @Inject(method = "setupAnim(Lnet/minecraft/world/entity/Entity;FFFFF)V", at = @At("TAIL"), cancellable = true)
   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callback) {
      AbstractIllager illagerEntity = (AbstractIllager)entity;
      ItemStack item = illagerEntity.getMainHandItem();
      HumanoidArm mainArm = illagerEntity.getMainArm();
      if (illagerEntity instanceof Basher basher) {
         IllagerArmPose state = basher.getArmPose();
         if (state == IllagerArmPose.ATTACKING && item.is(Items.SHIELD) && !basher.getStunnedState() && mainArm == HumanoidArm.RIGHT) {
            this.rightArm.xRot = this.rightArm.xRot * 0.5F + 0.05424779F;
            this.rightArm.yRot = (float) (-Math.PI / 6);
         }

         if (state == IllagerArmPose.ATTACKING && item.is(Items.SHIELD) && !basher.getStunnedState() && mainArm == HumanoidArm.LEFT) {
            this.leftArm.xRot = this.leftArm.xRot * 0.5F - 0.9424779F;
            this.leftArm.yRot = (float) (Math.PI / 6);
         }

         if (basher.getStunnedState()) {
            this.head.xRot = 20.35F;
            this.head.yRot = Mth.cos(ageInTicks * 0.8F) * 0.3F;
            this.rightArm.xRot = -0.25F;
            this.leftArm.xRot = -0.25F;
         }
      }

      if (illagerEntity instanceof Marauder marauder) {
         if (mainArm == HumanoidArm.RIGHT) {
            if (marauder.isCharging()) {
               this.rightArm.xRot = 3.7699115F;
            }

            if (!marauder.isAggressive()) {
               this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
            }
         } else {
            if (marauder.isCharging()) {
               this.leftArm.xRot = 3.7699115F;
            }

            if (!marauder.isAggressive()) {
               this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
            }
         }
      }
   }
}
