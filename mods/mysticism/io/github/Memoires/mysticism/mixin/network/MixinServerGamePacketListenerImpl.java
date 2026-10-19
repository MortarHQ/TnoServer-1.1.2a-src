package io.github.Memoires.mysticism.mixin.network;

import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket.Action;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class MixinServerGamePacketListenerImpl {
   @Shadow
   public ServerPlayer player;

   @Inject(method = "handleClientCommand(Lnet/minecraft/network/protocol/game/ServerboundClientCommandPacket;)V", at = @At("HEAD"), cancellable = true)
   private void myst$allowHardcoreRespawn(ServerboundClientCommandPacket packet, CallbackInfo ci) {
      if (packet.getAction() == Action.PERFORM_RESPAWN) {
         this.player.resetLastActionTime();
         MinecraftServer server = this.player.server;
         if (server.isHardcore()) {
            if (!this.player.isAlive()) {
               ManasSkillInstance skill = SkillUtils.getSkillOrNull(this.player, (ManasSkill)UniqueMysticismSkills.SCHRODINGER.get());
               if (skill != null) {
                  int lives = skill.getOrCreateTag().getInt("Lives");
                  if (lives >= 0) {
                     server.execute(() -> {
                        ServerPlayer oldPlayer = this.player;
                        GameType oldGamemode = oldPlayer.gameMode.getGameModeForPlayer();
                        ServerPlayer newPlayer = server.getPlayerList().respawn(oldPlayer, false, RemovalReason.KILLED);
                        this.player = newPlayer;
                        newPlayer.setGameMode(oldGamemode);
                        newPlayer.onUpdateAbilities();
                        server.getPlayerList().sendPlayerPermissionLevel(newPlayer);
                        server.getCommands().sendCommands(newPlayer);
                        newPlayer.invulnerableTime = 60;
                     });
                     ci.cancel();
                  }
               }
            }
         }
      }
   }
}
