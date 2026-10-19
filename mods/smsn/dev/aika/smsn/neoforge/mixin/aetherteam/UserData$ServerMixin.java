package dev.aika.smsn.neoforge.mixin.aetherteam;

import com.aetherteam.nitrogen.api.users.UserData.Server;
import dev.aika.smsn.SMSN;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Server.class, remap = false)
public abstract class UserData$ServerMixin {
   @Inject(
      method = "sendUserRequest(Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;Ljava/util/UUID;)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void sendUserRequest(MinecraftServer server, ServerPlayer serverPlayer, UUID uuid, CallbackInfo ci) {
      if (!SMSN.CONFIG.isAetherMoaSkinsFeature()) {
         ci.cancel();
      }
   }
}
