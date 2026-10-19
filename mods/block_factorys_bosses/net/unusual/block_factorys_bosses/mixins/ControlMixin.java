package net.unusual.block_factorys_bosses.mixins;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.unusual.block_factorys_bosses.attachment.entity.RollAttachment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(Player.class)
public abstract class ControlMixin extends LivingEntity {
   protected ControlMixin(EntityType<? extends LivingEntity> entityType, Level level) {
      super(entityType, level);
   }

   @Inject(method = "isImmobile", at = @At("HEAD"), cancellable = true)
   private void isImmobileMixin(CallbackInfoReturnable<Boolean> cir) {
      if (RollAttachment.fromPlayer((Player)this).isRolling()) {
         cir.setReturnValue(true);
      }
   }
}
