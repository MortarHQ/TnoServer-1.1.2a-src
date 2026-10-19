package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.handler.ESCommonHandler;
import cn.leolezury.eternalstarlight.common.network.SimpleActionPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateWeatherPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {
   @Inject(method = "sendLevelInfo(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/server/level/ServerLevel;)V", at = @At("RETURN"))
   private void sendLevelInfo(ServerPlayer serverPlayer, ServerLevel serverLevel, CallbackInfo ci) {
      if (serverLevel.dimension() == ESDimensions.STARLIGHT_KEY) {
         ESCommonHandler.getActiveWeather()
            .ifPresentOrElse(
               weatherInstance -> ESPlatform.INSTANCE.sendToClient(serverPlayer, new UpdateWeatherPacket(weatherInstance.getWeather())),
               () -> ESPlatform.INSTANCE.sendToClient(serverPlayer, new SimpleActionPacket("clear_weather"))
            );
      }
   }

   @ModifyVariable(
      method = "placeNewPlayer(Lnet/minecraft/network/Connection;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/server/network/CommonListenerCookie;)V",
      at = @At("STORE"),
      ordinal = 0
   )
   private ResourceKey<Level> modifySpawnDimension(
      ResourceKey<Level> original, Connection connection, ServerPlayer player, @Local Optional<CompoundTag> playerTag
   ) {
      return playerTag.isEmpty() && ESConfig.INSTANCE.spawnInEternalStarlight ? ESDimensions.STARLIGHT_KEY : original;
   }

   @WrapOperation(
      method = "placeNewPlayer(Lnet/minecraft/network/Connection;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/server/network/CommonListenerCookie;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;overworld()Lnet/minecraft/server/level/ServerLevel;")
   )
   private ServerLevel modifyDefaultSpawnDimension(MinecraftServer instance, Operation<ServerLevel> original) {
      return ESConfig.INSTANCE.spawnInEternalStarlight ? instance.getLevel(ESDimensions.STARLIGHT_KEY) : (ServerLevel)original.call(new Object[]{instance});
   }

   @WrapOperation(
      method = "getPlayerForLogin(Lcom/mojang/authlib/GameProfile;Lnet/minecraft/server/level/ClientInformation;)Lnet/minecraft/server/level/ServerPlayer;",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;overworld()Lnet/minecraft/server/level/ServerLevel;")
   )
   private ServerLevel modifyLoginDimension(MinecraftServer instance, Operation<ServerLevel> original) {
      return ESConfig.INSTANCE.spawnInEternalStarlight ? instance.getLevel(ESDimensions.STARLIGHT_KEY) : (ServerLevel)original.call(new Object[]{instance});
   }
}
