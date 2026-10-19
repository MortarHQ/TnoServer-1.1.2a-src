package com.benji.netherman;

import com.benji.netherman.client.renderer.AzazelWingTrails;
import com.benji.netherman.common.entity.AzazelHumanEntity;
import com.benji.netherman.common.entity.BelieverEntity;
import com.benji.netherman.common.entity.BelieverVillagerEntity;
import com.benji.netherman.common.network.AzazelBoostPacket;
import com.benji.netherman.config.AzazelConfig;
import com.benji.netherman.init.ModEffects;
import com.benji.netherman.init.ModItems;
import com.benji.netherman.init.ModSounds;
import java.util.Collection;
import java.util.WeakHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Pre;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = "netherman")
public class ModGameEvents {
   private static final WeakHashMap<Player, Integer> BOOST_TRAILS = new WeakHashMap<>();
   public static final WeakHashMap<Player, ModGameEvents.DrillData> ACTIVE_DRILLS = new WeakHashMap<>();

   @SubscribeEvent
   public static void onLivingHurt(LivingIncomingDamageEvent event) {
      if (event.getEntity() instanceof Player player) {
         if (event.getSource().is(DamageTypes.FALL)) {
            boolean hasFullSet = player.getItemBySlot(EquipmentSlot.HEAD).is((Item)ModItems.AZAZEL_HELMET.get())
               && player.getItemBySlot(EquipmentSlot.CHEST).is((Item)ModItems.AZAZEL_CHESTPLATE.get())
               && player.getItemBySlot(EquipmentSlot.LEGS).is((Item)ModItems.AZAZEL_LEGGINGS.get())
               && player.getItemBySlot(EquipmentSlot.FEET).is((Item)ModItems.AZAZEL_BOOTS.get());
            if (hasFullSet) {
               float originalAmount = event.getAmount();
               event.setAmount(originalAmount * 0.2F);
            }
         }

         if (event.getSource().is(DamageTypes.FLY_INTO_WALL) && player.getItemBySlot(EquipmentSlot.CHEST).is((Item)ModItems.AZAZEL_CHESTPLATE.get())) {
            event.setCanceled(true);
            if ((Boolean)AzazelConfig.AZAZEL_ARMOR_BLOCK_BREAKING.get()) {
               Vec3 look = player.getLookAngle().normalize();
               ACTIVE_DRILLS.put(player, new ModGameEvents.DrillData(12, look));
               player.level().playSound(null, player.blockPosition(), (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 1.5F, 1.2F);
               if (player.level().isClientSide()) {
                  AzazelWingTrails.startDrillingSparks(player, 12, look);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onEntityTick(Post event) {
      if (event.getEntity() instanceof Player player) {
         if (!player.level().isClientSide()) {
            boolean hasFullSet = player.getItemBySlot(EquipmentSlot.HEAD).is((Item)ModItems.AZAZEL_HELMET.get())
               && player.getItemBySlot(EquipmentSlot.CHEST).is((Item)ModItems.AZAZEL_CHESTPLATE.get())
               && player.getItemBySlot(EquipmentSlot.LEGS).is((Item)ModItems.AZAZEL_LEGGINGS.get())
               && player.getItemBySlot(EquipmentSlot.FEET).is((Item)ModItems.AZAZEL_BOOTS.get());
            if (hasFullSet && player.tickCount % 20 == 0) {
               player.addEffect(new MobEffectInstance(ModEffects.DESTINY, 220, 0, false, false, true));
            }
         }
      }
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof Player deadPlayer && event.getSource().getEntity() instanceof AzazelHumanEntity) {
         deadPlayer.displayClientMessage(Component.literal("You can change his stats in mod config").withStyle(ChatFormatting.AQUA), true);
      }

      if (event.getSource().getEntity() instanceof Player player) {
         LivingEntity victim = event.getEntity();
         if (victim instanceof BelieverEntity || victim instanceof BelieverVillagerEntity) {
            QuotaManager.failQuota(player);
            return;
         }

         QuotaManager.addProgress(player, 2, 1);
         if (victim instanceof Villager || victim instanceof IronGolem) {
            QuotaManager.addProgress(player, 1, 1);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerTickQuota(net.neoforged.neoforge.event.tick.PlayerTickEvent.Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide()) {
         CompoundTag data = player.getPersistentData();
         if (data.getBoolean("AzazelCultist") && data.contains("QuotaTimeLeft")) {
            int timeLeft = data.getInt("QuotaTimeLeft");
            if (timeLeft > 0) {
               data.putInt("QuotaTimeLeft", timeLeft - 1);
            } else {
               QuotaManager.failQuota(player);
            }
         }

         if (data.contains("AzazelPenaltyTime")) {
            long penaltyTime = data.getLong("AzazelPenaltyTime");
            if (player.level().getGameTime() >= penaltyTime) {
               QuotaManager.restoreHealth(player);
               data.remove("AzazelPenaltyTime");
            }
         }
      }
   }

   @SubscribeEvent
   public static void onBlockPlace(EntityPlaceEvent event) {
      if (!event.getLevel().isClientSide() && event.getEntity() instanceof Player player) {
         BlockState state = event.getState();
         if ((state.is(Blocks.GOLD_BLOCK) || state.is(Blocks.BELL)) && QuotaManager.checkAltarStructure((Level)event.getLevel(), event.getPos())) {
            QuotaManager.addProgress(player, 0, 1);
         }
      }
   }

   @SubscribeEvent
   public static void onLivingDrops(LivingDropsEvent event) {
      if (event.getSource().getEntity() instanceof Player player) {
         boolean hasFullSet = player.getItemBySlot(EquipmentSlot.HEAD).is((Item)ModItems.AZAZEL_HELMET.get())
            && player.getItemBySlot(EquipmentSlot.CHEST).is((Item)ModItems.AZAZEL_CHESTPLATE.get())
            && player.getItemBySlot(EquipmentSlot.LEGS).is((Item)ModItems.AZAZEL_LEGGINGS.get())
            && player.getItemBySlot(EquipmentSlot.FEET).is((Item)ModItems.AZAZEL_BOOTS.get());
         if (hasFullSet) {
            LivingEntity target = event.getEntity();
            RandomSource rand = target.getRandom();
            Item dropItem = (Item)ModItems.FAITH_PART.get();
            if (target.getMaxHealth() >= 100.0F) {
               int amount = 5 + rand.nextInt(8);
               addDrop(target, event.getDrops(), dropItem, amount);
            } else if (rand.nextFloat() < 0.5F) {
               int amount = 1 + rand.nextInt(3);
               addDrop(target, event.getDrops(), dropItem, amount);
            }
         }
      }
   }

   private static void addDrop(LivingEntity entity, Collection<ItemEntity> drops, Item item, int count) {
      ItemStack stack = new ItemStack(item, count);
      drops.add(new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack));
   }

   @SubscribeEvent
   public static void onPlayerTick(Pre event) {
      Player player = event.getEntity();
      ModGameEvents.DrillData drill = ACTIVE_DRILLS.get(player);
      if (drill != null && drill.ticks > 0) {
         drill.ticks--;
         player.setDeltaMovement(drill.direction.scale(0.85));
         player.hurtMarked = true;
         if (!player.level().isClientSide() && player.level() instanceof ServerLevel serverLevel) {
            if ((Boolean)AzazelConfig.AZAZEL_ARMOR_BLOCK_BREAKING.get()) {
               Vec3 targetPos = player.position().add(drill.direction.scale(1.2));
               BlockPos centerBlock = BlockPos.containing(targetPos.x, targetPos.y + 0.6, targetPos.z);
               int r = 1;

               for (int x = -r; x <= r; x++) {
                  for (int y = -r; y <= r + 1; y++) {
                     for (int z = -r; z <= r; z++) {
                        BlockPos targetBlock = centerBlock.offset(x, y, z);
                        BlockState state = serverLevel.getBlockState(targetBlock);
                        if (!state.isAir() && state.getDestroySpeed(serverLevel, targetBlock) >= 0.0F) {
                           serverLevel.destroyBlock(targetBlock, true, player);
                        }
                     }
                  }
               }
            }

            serverLevel.sendParticles(ParticleTypes.FLAME, player.getX(), player.getY() + 0.8, player.getZ(), 6, 0.4, 0.4, 0.4, 0.1);
            serverLevel.sendParticles(ParticleTypes.LAVA, player.getX(), player.getY() + 0.8, player.getZ(), 3, 0.3, 0.3, 0.3, 0.1);
         }

         if (drill.ticks <= 0) {
            ACTIVE_DRILLS.remove(player);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerTakeDamage(LivingIncomingDamageEvent event) {
      if (event.getEntity() instanceof Player player) {
         boolean hasShieldInMainHand = player.getMainHandItem().is((Item)ModItems.AZAZEL_SHIELD.get());
         boolean hasShieldInOffHand = player.getOffhandItem().is((Item)ModItems.AZAZEL_SHIELD.get());
         if ((hasShieldInMainHand || hasShieldInOffHand)
            && !event.getSource().is(DamageTypes.STARVE)
            && !event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD)
            && !event.getSource().is(DamageTypes.MAGIC)
            && event.getSource().getSourcePosition() != null) {
            Vec3 damagePos = event.getSource().getSourcePosition();
            Vec3 playerLook = player.getLookAngle();
            Vec3 toDamage = damagePos.subtract(player.position()).normalize();
            if (playerLook.dot(toDamage) > 0.0) {
               event.setCanceled(true);
               player.level().playSound(null, player.blockPosition(), (SoundEvent)ModSounds.DODGE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
               ItemStack shieldStack = hasShieldInMainHand ? player.getMainHandItem() : player.getOffhandItem();
               EquipmentSlot slot = hasShieldInMainHand ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
               int durabilityToConsume = Math.max(1, (int)event.getAmount());
               shieldStack.hurtAndBreak(durabilityToConsume, player, slot);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerRightClickEmpty(RightClickEmpty event) {
      Player player = event.getEntity();
      if (player.isFallFlying()) {
         ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
         if (chestStack.is((Item)ModItems.AZAZEL_CHESTPLATE.get()) && player.getItemInHand(event.getHand()).isEmpty()) {
            Vec3 look = player.getLookAngle();
            player.setDeltaMovement(player.getDeltaMovement().add(look.scale(0.85)));
            player.level().playSound(player, player.blockPosition(), SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 0.8F, 1.0F);
            player.level().playSound(player, player.blockPosition(), SoundEvents.PHANTOM_FLAP, SoundSource.PLAYERS, 0.5F, 1.3F);
            player.level().playSound(player, player.blockPosition(), (SoundEvent)ModSounds.SHOCK.get(), SoundSource.PLAYERS, 5.0F, 1.0F);
            BOOST_TRAILS.put(player, 35);
            AzazelWingTrails.spawnShockwave(player);
            AzazelWingTrails.startBoost(player, 160);
            PacketDistributor.sendToServer(new AzazelBoostPacket(), new CustomPacketPayload[0]);
         }
      }
   }

   public static class DrillData {
      public int ticks;
      public Vec3 direction;

      public DrillData(int ticks, Vec3 direction) {
         this.ticks = ticks;
         this.direction = direction;
      }
   }
}
