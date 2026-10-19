package com.mortal_cultivation.event;

import com.mojang.logging.LogUtils;
import com.mortal_cultivation.race.demonic_cultivator.DemonicFoundationRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicGoldenCoreRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicGreatAscensionRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicNascentSoulRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicSpiritTransformationRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicTribulationTranscendenceRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicTrueImmortalRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicUnityRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicVoidRefinementRace;
import com.mortal_cultivation.race.demonic_cultivator.DemonicVoidReturnRace;
import com.mortal_cultivation.race.literal_translation.SigilImmortalRealmRace;
import com.mortal_cultivation.race.literal_translation.SigilMasterRace;
import com.mortal_cultivation.race.literal_translation.SigilSaintRace;
import com.mortal_cultivation.race.literal_translation.TalismanApprenticeRace;
import com.mortal_cultivation.race.literal_translation.TalismanMasterRace;
import com.mortal_cultivation.race.right_way.DaoBreakingRace;
import com.mortal_cultivation.race.right_way.FoundationEstablishmentRace;
import com.mortal_cultivation.race.right_way.GoldenCoreRace;
import com.mortal_cultivation.race.right_way.GreatAscensionRace;
import com.mortal_cultivation.race.right_way.HeavenlyDaoRace;
import com.mortal_cultivation.race.right_way.NascentSoulRace;
import com.mortal_cultivation.race.right_way.SpiritTransformationRace;
import com.mortal_cultivation.race.right_way.TribulationTranscendenceRace;
import com.mortal_cultivation.race.right_way.TrueImmortalRace;
import com.mortal_cultivation.race.right_way.UnityRace;
import com.mortal_cultivation.race.right_way.VoidRefinementRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;
import org.slf4j.Logger;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class DoubleJumpEventHandler {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final Map<UUID, Boolean> canDoubleJump = new HashMap<>();
   private static boolean wasSpaceDown = false;
   private static boolean wasOnGround = true;
   private static boolean canAcceptSpaceInput = true;
   private static final Map<UUID, Long> lastDoubleJumpTime = new HashMap<>();
   private static final Map<UUID, Boolean> isInCooldown = new HashMap<>();
   private static final Map<UUID, Boolean> needReleaseAndPress = new HashMap<>();
   private static final float DOUBLE_JUMP_STRENGTH = 0.6F;
   private static final float HORIZONTAL_BOOST = 0.4F;
   private static final int FALL_DAMAGE_IMMUNITY = 8;
   private static final long DOUBLE_JUMP_COOLDOWN = 3000L;

   private static Object getPlayerRace(Player player) {
      Optional<ManasRaceInstance> race = RaceAPI.getRaceFrom(player).getRace();
      return race.isEmpty() ? null : race.get().getRace();
   }

   private static boolean supportsDoubleJump(Object race) {
      return RaceFlightAbilityHandler.supportsRaceFlight(race)
         ? false
         : race instanceof FoundationEstablishmentRace
            || race instanceof GoldenCoreRace
            || race instanceof NascentSoulRace
            || race instanceof SpiritTransformationRace
            || race instanceof VoidRefinementRace
            || race instanceof UnityRace
            || race instanceof GreatAscensionRace
            || race instanceof TribulationTranscendenceRace
            || race instanceof TrueImmortalRace
            || race instanceof HeavenlyDaoRace
            || race instanceof DaoBreakingRace
            || race instanceof TalismanMasterRace
            || race instanceof TalismanApprenticeRace
            || race instanceof SigilMasterRace
            || race instanceof SigilSaintRace
            || race instanceof SigilImmortalRealmRace
            || race instanceof DemonicFoundationRace
            || race instanceof DemonicGoldenCoreRace
            || race instanceof DemonicNascentSoulRace
            || race instanceof DemonicSpiritTransformationRace
            || race instanceof DemonicVoidRefinementRace
            || race instanceof DemonicUnityRace
            || race instanceof DemonicGreatAscensionRace
            || race instanceof DemonicTribulationTranscendenceRace
            || race instanceof DemonicTrueImmortalRace
            || race instanceof DemonicVoidReturnRace;
   }

   private static boolean isDoubleJumpEnabled(Object race, UUID playerId) {
      if (race instanceof FoundationEstablishmentRace) {
         return ((FoundationEstablishmentRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof GoldenCoreRace) {
         return ((GoldenCoreRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof NascentSoulRace) {
         return ((NascentSoulRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof SpiritTransformationRace) {
         return ((SpiritTransformationRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof VoidRefinementRace) {
         return ((VoidRefinementRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof UnityRace) {
         return ((UnityRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof GreatAscensionRace) {
         return ((GreatAscensionRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof TribulationTranscendenceRace) {
         return ((TribulationTranscendenceRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof TrueImmortalRace) {
         return ((TrueImmortalRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof HeavenlyDaoRace) {
         return ((HeavenlyDaoRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof DaoBreakingRace) {
         return ((DaoBreakingRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof TalismanMasterRace) {
         return ((TalismanMasterRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof TalismanApprenticeRace) {
         return ((TalismanApprenticeRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof SigilMasterRace) {
         return ((SigilMasterRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof SigilSaintRace) {
         return ((SigilSaintRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof SigilImmortalRealmRace) {
         return ((SigilImmortalRealmRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof DemonicFoundationRace) {
         return ((DemonicFoundationRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof DemonicGoldenCoreRace) {
         return ((DemonicGoldenCoreRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof DemonicNascentSoulRace) {
         return ((DemonicNascentSoulRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof DemonicSpiritTransformationRace) {
         return ((DemonicSpiritTransformationRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof DemonicVoidRefinementRace) {
         return ((DemonicVoidRefinementRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof DemonicUnityRace) {
         return ((DemonicUnityRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof DemonicGreatAscensionRace) {
         return ((DemonicGreatAscensionRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof DemonicTribulationTranscendenceRace) {
         return ((DemonicTribulationTranscendenceRace)race).isDoubleJumpEnabled(playerId);
      } else if (race instanceof DemonicTrueImmortalRace) {
         return ((DemonicTrueImmortalRace)race).isDoubleJumpEnabled(playerId);
      } else {
         return race instanceof DemonicVoidReturnRace ? ((DemonicVoidReturnRace)race).isDoubleJumpEnabled(playerId) : false;
      }
   }

   private static void resetDoubleJump(Object race, Player player) {
      if (race instanceof FoundationEstablishmentRace) {
         ((FoundationEstablishmentRace)race).resetDoubleJump(player);
      } else if (race instanceof GoldenCoreRace) {
         ((GoldenCoreRace)race).resetDoubleJump(player);
      } else if (race instanceof NascentSoulRace) {
         ((NascentSoulRace)race).resetDoubleJump(player);
      } else if (race instanceof SpiritTransformationRace) {
         ((SpiritTransformationRace)race).resetDoubleJump(player);
      } else if (race instanceof VoidRefinementRace) {
         ((VoidRefinementRace)race).resetDoubleJump(player);
      } else if (race instanceof UnityRace) {
         ((UnityRace)race).resetDoubleJump(player);
      } else if (race instanceof GreatAscensionRace) {
         ((GreatAscensionRace)race).resetDoubleJump(player);
      } else if (race instanceof TribulationTranscendenceRace) {
         ((TribulationTranscendenceRace)race).resetDoubleJump(player);
      } else if (race instanceof TrueImmortalRace) {
         ((TrueImmortalRace)race).resetDoubleJump(player);
      } else if (race instanceof HeavenlyDaoRace) {
         ((HeavenlyDaoRace)race).resetDoubleJump(player);
      } else if (race instanceof DaoBreakingRace) {
         ((DaoBreakingRace)race).resetDoubleJump(player);
      } else if (race instanceof TalismanMasterRace) {
         ((TalismanMasterRace)race).resetDoubleJump(player);
      } else if (race instanceof TalismanApprenticeRace) {
         ((TalismanApprenticeRace)race).resetDoubleJump(player);
      } else if (race instanceof SigilMasterRace) {
         ((SigilMasterRace)race).resetDoubleJump(player);
      } else if (race instanceof SigilSaintRace) {
         ((SigilSaintRace)race).resetDoubleJump(player);
      } else if (race instanceof SigilImmortalRealmRace) {
         ((SigilImmortalRealmRace)race).resetDoubleJump(player);
      } else if (race instanceof DemonicFoundationRace) {
         ((DemonicFoundationRace)race).resetDoubleJump(player);
      } else if (race instanceof DemonicGoldenCoreRace) {
         ((DemonicGoldenCoreRace)race).resetDoubleJump(player);
      } else if (race instanceof DemonicNascentSoulRace) {
         ((DemonicNascentSoulRace)race).resetDoubleJump(player);
      } else if (race instanceof DemonicSpiritTransformationRace) {
         ((DemonicSpiritTransformationRace)race).resetDoubleJump(player);
      } else if (race instanceof DemonicVoidRefinementRace) {
         ((DemonicVoidRefinementRace)race).resetDoubleJump(player);
      } else if (race instanceof DemonicUnityRace) {
         ((DemonicUnityRace)race).resetDoubleJump(player);
      } else if (race instanceof DemonicGreatAscensionRace) {
         ((DemonicGreatAscensionRace)race).resetDoubleJump(player);
      } else if (race instanceof DemonicTribulationTranscendenceRace) {
         ((DemonicTribulationTranscendenceRace)race).resetDoubleJump(player);
      } else if (race instanceof DemonicTrueImmortalRace) {
         ((DemonicTrueImmortalRace)race).resetDoubleJump(player);
      } else if (race instanceof DemonicVoidReturnRace) {
         ((DemonicVoidReturnRace)race).resetDoubleJump(player);
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide()) {
         handleServerTick(player);
      } else {
         handleClientTick(player);
      }
   }

   private static void handleServerTick(Player player) {
      UUID playerId = player.getUUID();
      Object race = getPlayerRace(player);
      if (supportsDoubleJump(race)) {
         if (player.onGround()) {
            canDoubleJump.put(playerId, true);
            resetDoubleJump(race, player);
         }
      }
   }

   private static void handleClientTick(Player player) {
      Minecraft mc = Minecraft.getInstance();
      if (player == mc.player) {
         UUID playerId = player.getUUID();
         Object race = getPlayerRace(player);
         if (supportsDoubleJump(race)) {
            boolean isAbilityEnabled = isDoubleJumpEnabled(race, playerId);
            boolean isOnGround = player.onGround();
            if (wasOnGround && !isOnGround) {
               canAcceptSpaceInput = false;
            }

            if (!wasOnGround && isOnGround) {
               canDoubleJump.put(playerId, true);
               resetDoubleJump(race, player);
               canAcceptSpaceInput = true;
               needReleaseAndPress.put(playerId, false);
            }

            boolean isSpaceDown = mc.options.keyJump.isDown();
            long currentTime = System.currentTimeMillis();
            long lastJumpTime = lastDoubleJumpTime.getOrDefault(playerId, 0L);
            long timeSinceLastJump = currentTime - lastJumpTime;
            boolean isCurrentlyInCooldown = timeSinceLastJump < 3000L;
            boolean wasInCooldown = isInCooldown.getOrDefault(playerId, false);
            if (wasInCooldown && !isCurrentlyInCooldown) {
               needReleaseAndPress.put(playerId, true);
            }

            isInCooldown.put(playerId, isCurrentlyInCooldown);
            if (isAbilityEnabled && isCurrentlyInCooldown) {
               float remainingCooldown = (float)(3000L - timeSinceLastJump) / 1000.0F;
               if (remainingCooldown >= 0.1F) {
                  player.displayClientMessage(
                     Component.translatable("mortal_cultivation.ability.double_jump.cooldown", new Object[]{String.format("%.1f", remainingCooldown)}), true
                  );
               } else {
                  player.displayClientMessage(Component.empty(), true);
               }
            } else if (wasInCooldown) {
               player.displayClientMessage(Component.empty(), true);
            }

            if (!isOnGround && !isSpaceDown) {
               canAcceptSpaceInput = true;
               if (needReleaseAndPress.getOrDefault(playerId, false)) {
                  needReleaseAndPress.put(playerId, false);
               }
            }

            if (isSpaceDown && !wasSpaceDown && canAcceptSpaceInput) {
               boolean requireRelease = needReleaseAndPress.getOrDefault(playerId, false);
               if (requireRelease) {
                  wasSpaceDown = isSpaceDown;
                  wasOnGround = isOnGround;
                  return;
               }

               boolean canJump = canDoubleJump.getOrDefault(playerId, false);
               if (!isAbilityEnabled) {
                  wasSpaceDown = isSpaceDown;
                  wasOnGround = isOnGround;
                  return;
               }

               if (isCurrentlyInCooldown) {
                  needReleaseAndPress.put(playerId, true);
                  wasSpaceDown = isSpaceDown;
                  wasOnGround = isOnGround;
                  return;
               }

               if (canJump && !isOnGround) {
                  Vec3 motion = player.getDeltaMovement();
                  float forward = 0.0F;
                  float strafe = 0.0F;
                  if (mc.options.keyUp.isDown()) {
                     forward++;
                  }

                  if (mc.options.keyDown.isDown()) {
                     forward--;
                  }

                  if (mc.options.keyLeft.isDown()) {
                     strafe++;
                  }

                  if (mc.options.keyRight.isDown()) {
                     strafe--;
                  }

                  double yaw = Math.toRadians(player.getYRot());
                  double motionX = motion.x;
                  double motionZ = motion.z;
                  if (forward != 0.0F || strafe != 0.0F) {
                     double newMotionX = -Math.sin(yaw) * forward + Math.cos(yaw) * strafe;
                     double newMotionZ = Math.cos(yaw) * forward + Math.sin(yaw) * strafe;
                     double length = Math.sqrt(newMotionX * newMotionX + newMotionZ * newMotionZ);
                     if (length > 0.0) {
                        newMotionX = newMotionX / length * 0.4F;
                        newMotionZ = newMotionZ / length * 0.4F;
                        motionX += newMotionX;
                        motionZ += newMotionZ;
                     }
                  }

                  player.setDeltaMovement(motionX, 0.6F, motionZ);
                  player.hasImpulse = true;

                  for (int i = 0; i < 10; i++) {
                     double offsetX = (player.getRandom().nextDouble() - 0.5) * 0.5;
                     double offsetY = player.getRandom().nextDouble() * 0.1;
                     double offsetZ = (player.getRandom().nextDouble() - 0.5) * 0.5;
                     player.level().addParticle(ParticleTypes.CLOUD, player.getX() + offsetX, player.getY(), player.getZ() + offsetZ, 0.0, 0.0, 0.0);
                  }

                  player.level().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 0.2F, 1.0F);
                  player.displayClientMessage(Component.translatable("mortal_cultivation.ability.double_jump.used"), true);
                  canDoubleJump.put(playerId, false);
                  lastDoubleJumpTime.put(playerId, currentTime);
                  isInCooldown.put(playerId, true);
               }
            }

            wasSpaceDown = isSpaceDown;
            wasOnGround = isOnGround;
         }
      }
   }

   @SubscribeEvent
   public static void onLivingFall(LivingFallEvent event) {
      if (event.getEntity() instanceof Player) {
         Player player = (Player)event.getEntity();
         Object race = getPlayerRace(player);
         if (supportsDoubleJump(race)) {
            if (event.getDistance() <= 8.0F) {
               event.setCanceled(true);
            } else {
               float newDistance = event.getDistance() - 8.0F;
               event.setDistance(newDistance);
            }
         }
      }
   }

   public static void cleanupPlayerData(UUID playerId) {
      canDoubleJump.remove(playerId);
      lastDoubleJumpTime.remove(playerId);
      isInCooldown.remove(playerId);
      needReleaseAndPress.remove(playerId);
   }
}
