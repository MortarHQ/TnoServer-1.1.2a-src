package net.unusual.block_factorys_bosses.mixins.server;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.unusual.block_factorys_bosses.util.BossHandling;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(Warden.class)
public abstract class WardenMixin extends Monster implements VibrationSystem {
   protected WardenMixin(EntityType<? extends Monster> entityType, Level level) {
      super(entityType, level);
   }

   @Inject(method = "<init>", at = @At("TAIL"))
   private void onInit(EntityType<? extends Monster> entityType, Level level, CallbackInfo ci) {
      if (!level.isClientSide) {
         BossHandling.CommonHandler.getOrCreate((Warden)this);
      }
   }

   @Inject(method = "customServerAiStep", at = @At("TAIL"))
   private void onTick(CallbackInfo ci) {
      if (!this.level().isClientSide) {
         BossHandling.CommonHandler.getOrCreate((Warden)this).setProgress(this.getHealth() / this.getMaxHealth());
      }
   }
}
