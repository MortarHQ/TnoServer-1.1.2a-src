package artifacts.mixin.item.pocketpiston.client;

import artifacts.extensions.pocketpiston.LivingEntityExtensions;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements LivingEntityExtensions {
   @Unique
   private static final int RETRACTION_DURATION = 2;
   @Unique
   private static final int RETRACTION_DELAY = 4;
   @Shadow
   public int swingTime;
   @Unique
   private float artifacts$pocketPistonLength;
   @Unique
   private int artifacts$pocketPistonTimeRemaining;

   @Inject(method = "tick()V", at = @At("HEAD"))
   private void updatePocketPistonLength(CallbackInfo ci) {
      float d = (this.artifacts$pocketPistonTimeRemaining < 2 ? -1.0F : 1.0F) / 2.0F;
      this.artifacts$pocketPistonLength = Math.max(0.0F, Math.min(1.0F, this.artifacts$pocketPistonLength + d));
      if (this.swingTime != 0) {
         this.artifacts$pocketPistonTimeRemaining = 6;
      }

      if (this.artifacts$pocketPistonTimeRemaining > 0) {
         this.artifacts$pocketPistonTimeRemaining--;
      }
   }

   @Unique
   @Override
   public float artifacts$getPocketPistonLength() {
      Minecraft minecraft = Minecraft.getInstance();
      float partialTicks = minecraft.getTimer().getGameTimeDeltaPartialTick(true);
      float d = (this.artifacts$pocketPistonTimeRemaining + partialTicks < 2.0F ? -1.0F : 1.0F) / 2.0F;
      return Math.max(0.0F, Math.min(1.0F, this.artifacts$pocketPistonLength + d * partialTicks));
   }
}
