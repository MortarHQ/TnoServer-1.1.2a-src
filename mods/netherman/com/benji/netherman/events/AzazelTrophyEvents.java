package com.benji.netherman.events;

import com.benji.netherman.common.network.TotemAnimationPayload;
import com.benji.netherman.config.AzazelConfig;
import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModItems;
import com.benji.netherman.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = "netherman")
public class AzazelTrophyEvents {
   @SubscribeEvent
   public static void onLivingAttack(LivingIncomingDamageEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
         if ((head.is(ModBlocks.AZAZEL_TROPHY.asItem()) || head.is(ModBlocks.AZAZEL_TROPHY_STAGE2.asItem()) || head.is(ModBlocks.AZAZEL_TROPHY_STAGE3.asItem()))
            && (Boolean)AzazelConfig.MASK_FIRE_IMMUNITY.get()) {
            DamageSource source = event.getSource();
            if (source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypes.LAVA) || source.is(DamageTypes.HOT_FLOOR)) {
               event.setCanceled(true);
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onPlayerDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         ItemStack mask = player.getItemBySlot(EquipmentSlot.HEAD);
         if (mask.is(ModBlocks.AZAZEL_TROPHY.asItem()) || mask.is(ModBlocks.AZAZEL_TROPHY_STAGE2.asItem()) || mask.is(ModBlocks.AZAZEL_TROPHY_STAGE3.asItem())) {
            int stage = 0;
            if (mask.is(ModBlocks.AZAZEL_TROPHY_STAGE2.asItem())) {
               stage = 1;
            }

            if (mask.is(ModBlocks.AZAZEL_TROPHY_STAGE3.asItem())) {
               stage = 2;
            }

            if (stage < 2) {
               event.setCanceled(true);
               Item nextItem = stage == 0 ? ModBlocks.AZAZEL_TROPHY_STAGE2.asItem() : ModBlocks.AZAZEL_TROPHY_STAGE3.asItem();
               ItemStack newMask = new ItemStack(nextItem);
               CompoundTag tag = new CompoundTag();
               tag.putLong("LastRegenTick", player.level().getGameTime());
               newMask.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
               player.setItemSlot(EquipmentSlot.HEAD, newMask);
               player.setHealth(2.0F);
               player.removeAllEffects();
               player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
               player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
               player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
               ServerLevel level = (ServerLevel)player.level();
               PacketDistributor.sendToPlayer(
                  player, new TotemAnimationPayload(new ItemStack((ItemLike)ModItems.CHANCE_TOTEM.get())), new CustomPacketPayload[0]
               );
               level.playSound(null, player.getX(), player.getY(), player.getZ(), (SoundEvent)ModSounds.AZAZEL_DAMAGE_1.get(), SoundSource.PLAYERS, 1.2F, 1.0F);
               level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
               level.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, player.getX(), player.getY() + 1.0, player.getZ(), 64, 0.3, 0.3, 0.3, 0.5);
            } else if (stage == 2) {
               event.setCanceled(true);
               mask.shrink(1);
               player.setHealth(1.0F);
               player.removeAllEffects();
               ServerLevel currentLevel = (ServerLevel)player.level();
               currentLevel.playSound(
                  null, player.getX(), player.getY(), player.getZ(), (SoundEvent)SoundEvents.ARMOR_EQUIP_GENERIC.value(), SoundSource.PLAYERS, 1.0F, 0.5F
               );
               triggerChanceTotemTeleport(player, currentLevel);
            }
         }
      }
   }

   private static void triggerChanceTotemTeleport(ServerPlayer player, ServerLevel currentLevel) {
      PacketDistributor.sendToPlayer(player, new TotemAnimationPayload(new ItemStack((ItemLike)ModItems.CHANCE_TOTEM.get())), new CustomPacketPayload[0]);
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

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (!event.getEntity().level().isClientSide()) {
         ServerPlayer player = (ServerPlayer)event.getEntity();
         ItemStack mask = player.getItemBySlot(EquipmentSlot.HEAD);
         if (mask.is(ModBlocks.AZAZEL_TROPHY_STAGE2.asItem()) || mask.is(ModBlocks.AZAZEL_TROPHY_STAGE3.asItem())) {
            CustomData data = (CustomData)mask.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag tag = data.copyTag();
            long currentTick = player.level().getGameTime();
            long lastRegenTick = tag.getLong("LastRegenTick");
            if (currentTick - lastRegenTick >= ((Integer)AzazelConfig.MASK_REGEN_COOLDOWN.get()).intValue()) {
               Item prevItem = mask.is(ModBlocks.AZAZEL_TROPHY_STAGE3.asItem()) ? ModBlocks.AZAZEL_TROPHY_STAGE2.asItem() : ModBlocks.AZAZEL_TROPHY.asItem();
               ItemStack newMask = new ItemStack(prevItem);
               tag.putLong("LastRegenTick", currentTick);
               newMask.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
               player.setItemSlot(EquipmentSlot.HEAD, newMask);
               player.playSound(SoundEvents.AMETHYST_BLOCK_CHIME, 1.0F, 1.2F);
               ServerLevel level = (ServerLevel)player.level();
               level.sendParticles(ParticleTypes.HAPPY_VILLAGER, player.getX(), player.getY() + 1.7, player.getZ(), 15, 0.25, 0.25, 0.25, 0.05);
            }
         }
      }
   }
}
