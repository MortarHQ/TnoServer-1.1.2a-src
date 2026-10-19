package com.benji.netherman.events;

import com.benji.netherman.common.compat.CuriosCompat;
import com.benji.netherman.common.network.TotemAnimationPayload;
import com.benji.netherman.init.ModItems;
import com.benji.netherman.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = "netherman")
public class ChanceTotemEvents {
   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onPlayerDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         ItemStack mainHand = player.getMainHandItem();
         ItemStack offHand = player.getOffhandItem();
         ItemStack totem = null;
         if (mainHand.is((Item)ModItems.CHANCE_TOTEM.get())) {
            totem = mainHand;
         } else if (offHand.is((Item)ModItems.CHANCE_TOTEM.get())) {
            totem = offHand;
         } else if (ModList.get().isLoaded("curios")) {
            totem = CuriosCompat.getTotemFromCurios(player);
         }

         if (totem != null) {
            event.setCanceled(true);
            player.setHealth(1.0F);
            player.removeAllEffects();
            totem.shrink(1);
            PacketDistributor.sendToPlayer(player, new TotemAnimationPayload(new ItemStack((ItemLike)ModItems.CHANCE_TOTEM.get())), new CustomPacketPayload[0]);
            ServerLevel currentLevel = (ServerLevel)player.level();
            currentLevel.playSound(null, player.blockPosition(), (SoundEvent)ModSounds.RESPAWN_TOTEM.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            currentLevel.sendParticles(ParticleTypes.LARGE_SMOKE, player.getX(), player.getY() + 1.0, player.getZ(), 50, 0.5, 0.5, 0.5, 0.05);
            ServerLevel respawnLevel = currentLevel.getServer().getLevel(player.getRespawnDimension());
            if (respawnLevel == null) {
               respawnLevel = currentLevel.getServer().overworld();
            }

            BlockPos respawnPos = player.getRespawnPosition();
            float respawnAngle = player.getRespawnAngle();
            if (respawnPos != null) {
               player.teleportTo(respawnLevel, respawnPos.getX() + 0.5, respawnPos.getY() + 1.0, respawnPos.getZ() + 0.5, respawnAngle, 0.0F);
            } else {
               BlockPos sharedSpawn = respawnLevel.getSharedSpawnPos();
               player.teleportTo(respawnLevel, sharedSpawn.getX() + 0.5, sharedSpawn.getY() + 1.0, sharedSpawn.getZ() + 0.5, respawnAngle, 0.0F);
            }

            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 4));
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 600, 0));
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 600, 1));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 2));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 4));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 0));
            respawnLevel.playSound(null, player.blockPosition(), (SoundEvent)ModSounds.RESPAWN_TOTEM.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            respawnLevel.sendParticles(ParticleTypes.LARGE_SMOKE, player.getX(), player.getY() + 1.0, player.getZ(), 50, 0.5, 0.5, 0.5, 0.05);
         }
      }
   }
}
