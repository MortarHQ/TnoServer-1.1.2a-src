package net.crypticmc.tr_unique_monsters.mixin.tensura.network.c2s;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.network.c2s.RequestNamingKeyPacket;
import net.crypticmc.tr_unique_monsters.config.configs.BaseConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = RequestNamingKeyPacket.class, remap = false)
public class RequestNamingKeyPacketMixin {
   @Inject(
      method = {
            "canName(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/LivingEntity;)Z",
            "canName(Lnet/minecraft/class_1657;Lnet/minecraft/class_1309;)Z"
      },
      at = @At("HEAD"),
      cancellable = true,
      remap = false,
      require = 0
   )
   private static void canName(Player player, LivingEntity sub, CallbackInfoReturnable<Boolean> cir) {
      BaseConfig config = (BaseConfig)ConfigRegistry.getConfig(BaseConfig.class);
      if (sub.getTags().contains("tr_unique_monster") && !config.uniqueMonsterTameable) {
         player.displayClientMessage(Component.translatable("tr_unique_monsters.deny.unique.tameable"), true);
         cir.setReturnValue(false);
      }
   }
}
