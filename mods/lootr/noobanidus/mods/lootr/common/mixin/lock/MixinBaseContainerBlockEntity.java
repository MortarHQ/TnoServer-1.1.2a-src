package noobanidus.mods.lootr.common.mixin.lock;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import noobanidus.mods.lootr.common.api.data.blockentity.LockMessageSuppression;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BaseContainerBlockEntity.class)
public class MixinBaseContainerBlockEntity {
   @WrapOperation(
      method = "canUnlock",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;displayClientMessage(Lnet/minecraft/network/chat/Component;Z)V")
   )
   private static void lootr$suppressLockMessage(Player instance, Component chatComponent, boolean actionBar, Operation<Void> original) {
      if (!LockMessageSuppression.isSuppressed()) {
         original.call(new Object[]{instance, chatComponent, actionBar});
      }
   }

   @WrapOperation(
      method = "canUnlock",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/player/Player;playNotifySound(Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"
      )
   )
   private static void lootr$suppressLockSound(
      Player instance, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch, Operation<Void> original
   ) {
      if (!LockMessageSuppression.isSuppressed()) {
         original.call(new Object[]{instance, soundEvent, soundSource, volume, pitch});
      }
   }
}
