package net.unusual.block_factorys_bosses.mixins.client;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.ClientHooks;
import net.unusual.block_factorys_bosses.client.CameraInject;
import net.unusual.block_factorys_bosses.client.camera.renderer.CameraRenderer;
import net.unusual.block_factorys_bosses.entity.decoration.CannonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(Camera.class)
public abstract class CameraMixin implements CameraInject {
   @Shadow
   private float eyeHeight;
   @Unique
   private boolean isCinematic = false;
   @Unique
   private Vec3 bosses_rise_java$pos = Vec3.ZERO;

   @Shadow
   public abstract void setPosition(Vec3 var1);

   @Shadow
   @Deprecated
   protected abstract void setRotation(float var1, float var2);

   @Shadow
   protected abstract float getMaxZoom(float var1);

   @Shadow
   protected abstract void move(float var1, float var2, float var3);

   @Shadow
   public abstract Vec3 getPosition();

   @Inject(method = "getPosition", at = @At("RETURN"), cancellable = true)
   private void getPosition(CallbackInfoReturnable<Vec3> cir) {
      if (this.isCinematic) {
         cir.setReturnValue(this.bosses_rise_java$pos);
      }
   }

   @Override
   public boolean isCinematic() {
      return this.isCinematic;
   }

   @Override
   public void setCinematic(boolean isCinematic) {
      if (this.isCinematic != isCinematic) {
         Minecraft.getInstance().options.hideGui = isCinematic;
      }

      this.isCinematic = isCinematic;
      if (isCinematic) {
         CameraRenderer.viewBobCancel = 3;
      }
   }

   @Override
   public void setCinematicPosition(Vec3 pos) {
      this.bosses_rise_java$pos = pos;
   }

   @Inject(method = "setup", at = @At("TAIL"))
   private void setupMixin(BlockGetter level, Entity entity, boolean detached, boolean thirdPersonReverse, float partialTick, CallbackInfo ci) {
      if (entity.getControlledVehicle() instanceof CannonEntity cannon && cannon.bonePos != null) {
         Vec3 camPos = new Vec3(cannon.bonePos.x, cannon.bonePos.y, cannon.bonePos.z);
         Vec3 eyePos = new Vec3(0.0, entity.getEyeHeight() * (detached ? 1.0 : 0.5), 0.0);
         this.setPosition(
            cannon.getPosition(partialTick)
               .add(camPos)
               .add(
                  eyePos.xRot(cannon.getViewXRot(partialTick) * (float) (Math.PI / 180.0))
                     .yRot((-cannon.getViewYRot(partialTick) - 180.0F) * (float) (Math.PI / 180.0))
               )
         );
         if (detached) {
            if (thirdPersonReverse) {
               this.setRotation(entity.getViewYRot(partialTick) + 180.0F, -(entity.getViewXRot(partialTick) + cannon.getViewXRot(partialTick)));
            }

            float f = entity instanceof LivingEntity living ? living.getScale() : 1.0F;
            this.move(-this.getMaxZoom(ClientHooks.getDetachedCameraDistance((Camera)this, thirdPersonReverse, f, 4.0F) * f), 0.0F, 0.0F);
         }
      }
   }
}
