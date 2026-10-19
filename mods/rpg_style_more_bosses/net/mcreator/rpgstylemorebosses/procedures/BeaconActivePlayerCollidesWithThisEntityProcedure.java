package net.mcreator.rpgstylemorebosses.procedures;

import net.mcreator.rpgstylemorebosses.RpgStyleMoreBossesMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class BeaconActivePlayerCollidesWithThisEntityProcedure {
   public static void execute(LevelAccessor world, Entity sourceentity) {
      if (sourceentity != null) {
         if (sourceentity.level().dimension() == Level.OVERWORLD) {
            if (sourceentity instanceof Player _player && !_player.level().isClientSide()) {
               _player.displayClientMessage(Component.literal("Teleportation in progress. Please wait a moment."), true);
            }

            RpgStyleMoreBossesMod.queueServerWork(
               20,
               () -> {
                  if (sourceentity instanceof ServerPlayer _playerx && !_playerx.level().isClientSide()) {
                     ResourceKey<Level> destinationType = ResourceKey.create(
                        Registries.DIMENSION, ResourceLocation.parse("rpg_style_more_bosses:second_dimention")
                     );
                     if (_playerx.level().dimension() == destinationType) {
                        return;
                     }

                     ServerLevel nextLevel = _playerx.server.getLevel(destinationType);
                     if (nextLevel != null) {
                        _playerx.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0.0F));
                        _playerx.teleportTo(nextLevel, _playerx.getX(), _playerx.getY(), _playerx.getZ(), _playerx.getYRot(), _playerx.getXRot());
                        _playerx.connection.send(new ClientboundPlayerAbilitiesPacket(_playerx.getAbilities()));

                        for (MobEffectInstance _effectinstance : _playerx.getActiveEffects()) {
                           _playerx.connection.send(new ClientboundUpdateMobEffectPacket(_playerx.getId(), _effectinstance, false));
                        }

                        _playerx.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
                     }
                  }
               }
            );
         }
      }
   }
}
