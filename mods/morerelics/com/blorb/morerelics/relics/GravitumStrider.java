package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.dataComponents.IntegerCounter;
import com.blorb.morerelics.helper.DelayedTaskManager;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent.LivingJumpEvent;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class GravitumStrider extends MoreRelicBase {
   private static final int leapTime = 20;

   public GravitumStrider(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("elevated_steps")
                     .stat(
                        StatData.builder("gravity_reduction")
                           .initialValue(30.0, 40.0)
                           .upgradeModifier(UpgradeOperation.ADD, 4.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredPoints(1)
                     .requiredLevel(0)
                     .build()
               )
               .ability(
                  AbilityData.builder("leap")
                     .active(CastData.builder().type(CastType.TOGGLEABLE).build())
                     .stat(
                        StatData.builder("jump_force")
                           .initialValue(0.2, 0.28)
                           .upgradeModifier(UpgradeOperation.ADD, 0.04)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(3)
                     .requiredPoints(1)
                     .requiredLevel(5)
                     .build()
               )
               .build()
         )
         .leveling(LevelingData.builder().maxLevel(8).initialCost(120).step(40).build())
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.END_LIKE, LootEntries.THE_END, LootEntries.SCULK}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      super.curioTick(slotContext, stack);
      LivingEntity livingEntity = slotContext.entity();
      if (!livingEntity.level().isClientSide) {
         if (this.isAbilityUnlocked(stack, "leap") && this.isAbilityTicking(stack, "leap")) {
            IntegerCounter crouchCounter = DataComponentHandler.getIntegerCounter(stack);
            IntegerCounter leapFallImmunityCounter = DataComponentHandler.getAltIntegerCounter(stack);
            int leapFallImmunity = leapFallImmunityCounter.current();
            int crouchState = crouchCounter.current();
            if (!livingEntity.isCrouching() || !livingEntity.onGround()) {
               crouchState = 0;
            } else if (crouchState < 20) {
               if (crouchState == 19) {
                  livingEntity.level()
                     .playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), MoreRelics.G_STRIDER_READY, SoundSource.MASTER, 1.5F, 1.0F);
               }

               crouchState++;
            } else if (livingEntity.level().getGameTime() % 5L == 0L) {
               this.spawnJumpReadEffect(livingEntity.level(), livingEntity.position());
            }

            if (leapFallImmunity > 0) {
               if (leapFallImmunity == 1) {
                  livingEntity.resetFallDistance();
               }

               leapFallImmunity--;
            }

            stack.set(DataComponentHandler.ALT_INT_COUNTER, leapFallImmunityCounter.getUpdatedCounter(leapFallImmunity));
            stack.set(DataComponentHandler.INT_COUNTER, crouchCounter.getUpdatedCounter(crouchState));
         }

         float multiplier = (float)this.getStatValue(stack, "elevated_steps", "gravity_reduction") / 100.0F;
         EntityUtils.resetAttribute(livingEntity, stack, Attributes.GRAVITY, -multiplier, Operation.ADD_MULTIPLIED_TOTAL);
      }
   }

   @SubscribeEvent
   public static void onJump(LivingJumpEvent event) {
      if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
         OptionalDouble jumpForce = EntityUtils.findEquippedCurios(player, (Item)MoreRelics.GRAVITUM_STRIDER.get()).stream().mapToDouble(stack -> {
            GravitumStrider item = (GravitumStrider)stack.getItem();
            if (item.isAbilityUnlocked(stack, "leap") && item.isAbilityTicking(stack, "leap")) {
               IntegerCounter crouchCounter = DataComponentHandler.getIntegerCounter(stack);
               int crouchState = crouchCounter.current();
               item.spreadRelicExperience(player, stack, 1);
               if (crouchState == 20) {
                  stack.set(DataComponentHandler.ALT_INT_COUNTER, DataComponentHandler.getAltIntegerCounter(stack).getUpdatedCounter(60));
                  return item.getStatValue(stack, "leap", "jump_force");
               } else {
                  return 0.0;
               }
            } else {
               return 0.0;
            }
         }).max();
         if (jumpForce.isPresent() && jumpForce.getAsDouble() > 0.0) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), MoreRelics.G_STRIDER_LEAP, SoundSource.MASTER, 1.0F, 1.0F);
            ServerPlayer serverPlayer = (ServerPlayer)player;
            serverPlayer.addDeltaMovement(new Vec3(0.0, jumpForce.getAsDouble(), 0.0));
            serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
            DelayedTaskManager.addTask(() -> delayedCloudTask(player, 0.18F), 1);
            DelayedTaskManager.addTask(() -> delayedCloudTask(player, 0.12F), 2);
            DelayedTaskManager.addTask(() -> delayedCloudTask(player, 0.06F), 5);
         }
      }
   }

   @SubscribeEvent
   public static void onFall(LivingFallEvent event) {
      if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
         OptionalDouble gravityReduction = EntityUtils.findEquippedCurios(player, (Item)MoreRelics.GRAVITUM_STRIDER.get()).stream().mapToDouble(stack -> {
            GravitumStrider item = (GravitumStrider)stack.getItem();
            if (!item.isAbilityUnlocked(stack, "elevated_steps")) {
               return 0.0;
            }

            IntegerCounter leapCounter = DataComponentHandler.getAltIntegerCounter(stack);
            int leapImmunity = leapCounter.current();
            stack.set(DataComponentHandler.ALT_INT_COUNTER, leapCounter.getUpdatedCounter(0));
            return item.isAbilityUnlocked(stack, "leap") && leapImmunity != 0 ? 1.0 : item.getStatValue(stack, "elevated_steps", "gravity_reduction") / 100.0;
         }).max();
         if (gravityReduction.isPresent()) {
            MoreRelics.LOGGER.debug("se: " + gravityReduction.getAsDouble());
            double gravityRed = gravityReduction.getAsDouble();
            double reducedBlocks = event.getDistance() - 4.0 * gravityRed;
            if (gravityRed > 1.0 || reducedBlocks <= 3.0) {
               MoreRelics.LOGGER.debug("canceled: " + gravityReduction.getAsDouble());
               event.setCanceled(true);
            } else if (gravityRed > 0.0) {
               event.setDamageMultiplier((float)(event.getDamageMultiplier() * (1.0 - gravityReduction.getAsDouble())));
               event.setDistance((float)reducedBlocks);
            }
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (stack.getItem() != newStack.getItem()) {
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.GRAVITY, Operation.ADD_MULTIPLIED_TOTAL);
         stack.set(DataComponentHandler.ALT_INT_COUNTER, DataComponentHandler.getAltIntegerCounter(stack).getUpdatedCounter(0));
      }
   }

   private void spawnJumpReadEffect(Level level, Vec3 position) {
      int particleCount = 5;
      ServerLevel sevLevel = (ServerLevel)level;
      List<ServerPlayer> players = level.players();
      Random rand = new Random();

      for (ServerPlayer player : players) {
         for (int i = 0; i < particleCount; i++) {
            float randAngle = rand.nextFloat(360.0F);
            sevLevel.sendParticles(
               player, ParticleTypes.CLOUD, true, position.x, position.y + 0.2F, position.z, 0, Math.cos(randAngle), 0.0, Math.sin(randAngle), 0.1F
            );
         }
      }
   }

   private static void delayedCloudTask(LivingEntity entity, float size) {
      if (entity != null) {
         spawnCloudLoop(entity.level(), entity.position(), size);
      }
   }

   private static void spawnCloudLoop(Level level, Vec3 position, float size) {
      ServerLevel sevLevel = (ServerLevel)level;
      List<ServerPlayer> players = level.players();
      new Random();

      for (ServerPlayer player : players) {
         for (int i = 0; i < 360; i++) {
            sevLevel.sendParticles(player, ParticleTypes.CLOUD, true, position.x, position.y, position.z, 0, Math.cos(i), 0.0, Math.sin(i), size);
         }
      }
   }
}
