package me.wesley1808.servercore.mixin.optimizations.players;

import com.llamalad7.mixinextras.sugar.Local;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {
   @Inject(
      method = "placeNewPlayer(Lnet/minecraft/network/Connection;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/server/network/CommonListenerCookie;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/server/level/ServerPlayer;setServerLevel(Lnet/minecraft/server/level/ServerLevel;)V",
         shift = Shift.BEFORE
      )
   )
   private void servercore$moveToSpawn(
      Connection connection,
      ServerPlayer player,
      CommonListenerCookie cookie,
      CallbackInfo ci,
      @Local(ordinal = 0) Optional<CompoundTag> playerData,
      @Local(ordinal = 1) ServerLevel level
   ) {
      if (playerData.isEmpty()) {
         player.moveTo(player.adjustSpawnLocation(level, level.getSharedSpawnPos()), 0.0F, 0.0F);
      }
   }
}
