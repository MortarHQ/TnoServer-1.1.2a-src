package fuzs.illagerinvasion.mixin;

import fuzs.illagerinvasion.handler.PlatinumTrimHandler;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
abstract class FoodDataMixin {
   @Shadow
   private float saturationLevel;
   @Nullable
   private Player illagerinvasion$lastPlayer;

   @Inject(method = "tick", at = @At("HEAD"))
   public void tick(Player player, CallbackInfo callback) {
      this.illagerinvasion$lastPlayer = player;
   }

   @ModifyVariable(method = "addExhaustion", at = @At("HEAD"), argsOnly = true)
   public float addExhaustion(float exhaustion) {
      if (exhaustion != 6.0F
         && exhaustion != this.saturationLevel
         && this.illagerinvasion$lastPlayer != null
         && PlatinumTrimHandler.hasPlatinumTrim(this.illagerinvasion$lastPlayer, EquipmentSlot.LEGS)) {
         if (exhaustion > 0.0F) {
            exhaustion /= 2.0F;
         } else if (exhaustion < 0.0F) {
            exhaustion *= 2.0F;
         }

         return exhaustion;
      } else {
         return exhaustion;
      }
   }
}
