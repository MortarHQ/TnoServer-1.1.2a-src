package com.foxablazeultimate.ability.skill.beelzebub;

import com.foxablazeultimate.config.BeelzebubConfig;
import com.foxablazeultimate.item.beelzebub.CapturedEntityItem;
import com.foxablazeultimate.registry.FoxAblazeUltimateItems;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.subclass.ISpatialStorage;
import io.github.manasmods.tensura.entity.template.subclass.ILivingPartEntity;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Optional;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class BeelzebubCaptureHandler {
   private static final WeakHashMap<UUID, Long> LAST_CAPTURE_TICK = new WeakHashMap<>();

   private BeelzebubCaptureHandler() {
   }

   @SubscribeEvent
   public static void onEntityInteract(EntityInteract event) {
      Player p = event.getEntity();
      if (!p.level().isClientSide()) {
         if (p instanceof ServerPlayer player) {
            if (player.isShiftKeyDown()) {
               if (event.getTarget() instanceof LivingEntity raw && raw.isAlive()) {
                  if (!raw.is(player)) {
                     LivingEntity target = ILivingPartEntity.checkForHead(raw);
                     if (target.isAlive()) {
                        if (!target.is(player)) {
                           if (!isSpiritSummonBeingCreated(target)) {
                              Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)FoxAblazeUltimateSkills.BEELZEBUB.get());
                              if (!opt.isEmpty() && !(opt.get().getMastery() < 0.0)) {
                                 ManasSkillInstance instance = opt.get();
                                 if (instance.getSkill() instanceof ISpatialStorage spatial) {
                                    if (instance.getSkill() instanceof Skill tSkill) {
                                       if (tSkill.isInSlot(player, instance, 1)) {
                                          if (!player.level().getGameRules().getBoolean(FoxAblazeGameRules.BEELZEBUB_DISABLE_CAPTURE)) {
                                             BeelzebubConfig.BeelzebubSettings cfg = BeelzebubConfig.get().Beelzebub;
                                             if (checkCaptureForbidden(target, cfg) != null) {
                                                event.setCancellationResult(InteractionResult.FAIL);
                                                event.setCanceled(true);
                                             } else {
                                                double targetEP = EnergyHelper.getMaxEP(target);
                                                double selfEP = EnergyHelper.getMaxEP(player);
                                                if (targetEP > selfEP) {
                                                   event.setCancellationResult(InteractionResult.FAIL);
                                                   event.setCanceled(true);
                                                } else {
                                                   long now = player.level().getGameTime();
                                                   Long last = LAST_CAPTURE_TICK.get(player.getUUID());
                                                   if (last == null || now - last >= cfg.captureCooldownSeconds * 20L) {
                                                      SpatialStorageContainer container = spatial.getSpatialStorage(instance, player.registryAccess());
                                                      if (!hasFreeSlot(container)) {
                                                         event.setCancellationResult(InteractionResult.FAIL);
                                                         event.setCanceled(true);
                                                      } else {
                                                         ItemStack template = new ItemStack((ItemLike)FoxAblazeUltimateItems.CAPTURED_ENTITY.get());
                                                         ItemStack stack = CapturedEntityItem.captureToStack(target, targetEP, template);
                                                         if (!spatial.addItemToSpatialStorage(instance, player, stack)) {
                                                            event.setCancellationResult(InteractionResult.FAIL);
                                                            event.setCanceled(true);
                                                         } else {
                                                            ServerLevel sl = player.serverLevel();
                                                            sl.sendParticles(
                                                               ParticleTypes.PORTAL,
                                                               target.getX(),
                                                               target.getY() + target.getBbHeight() / 2.0,
                                                               target.getZ(),
                                                               30,
                                                               0.4,
                                                               target.getBbHeight() * 0.5,
                                                               0.4,
                                                               0.05
                                                            );
                                                            sl.playSound(
                                                               null,
                                                               target.getX(),
                                                               target.getY(),
                                                               target.getZ(),
                                                               SoundEvents.ENDERMAN_TELEPORT,
                                                               SoundSource.PLAYERS,
                                                               0.7F,
                                                               1.6F
                                                            );
                                                            ResourceLocation typeId = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
                                                            Component name = (Component)(target.getDisplayName() != null
                                                               ? target.getDisplayName()
                                                               : Component.literal(typeId == null ? "?" : typeId.getPath()));
                                                            player.sendSystemMessage(
                                                               Component.translatable(
                                                                     "foxablazeultimate.beelzebub.capture.success",
                                                                     new Object[]{Component.empty().append(name).withStyle(ChatFormatting.AQUA)}
                                                                  )
                                                                  .withStyle(ChatFormatting.LIGHT_PURPLE)
                                                            );
                                                            target.discard();
                                                            discardAttachedBodies(sl, target);
                                                            LAST_CAPTURE_TICK.put(player.getUUID(), now);
                                                            event.setCancellationResult(InteractionResult.SUCCESS);
                                                            event.setCanceled(true);
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static void discardAttachedBodies(ServerLevel level, LivingEntity head) {
      UUID headId = head.getUUID();
      AABB sweep = head.getBoundingBox().inflate(64.0);

      for (Entity nearby : level.getEntities(head, sweep)) {
         if (nearby != head && nearby instanceof ILivingPartEntity part) {
            UUID linkedHead = part.getHeadId();
            if (linkedHead != null && linkedHead.equals(headId)) {
               nearby.discard();
            }
         }
      }
   }

   private static String checkCaptureForbidden(LivingEntity target, BeelzebubConfig.BeelzebubSettings cfg) {
      if (target instanceof Player) {
         return cfg.captureAllowPlayers ? null : "foxablazeultimate.beelzebub.capture.fail.is_player";
      }

      if (!cfg.captureAllowBosses && isBoss(target)) {
         return "foxablazeultimate.beelzebub.capture.fail.is_boss";
      }

      ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
      return id != null && cfg.captureBlacklist.contains(id.toString()) ? "foxablazeultimate.beelzebub.capture.fail.blacklisted" : null;
   }

   private static boolean isBoss(LivingEntity entity) {
      return entity instanceof WitherBoss || entity instanceof EnderDragon;
   }

   private static boolean hasFreeSlot(SpatialStorageContainer container) {
      for (int i = 0; i < container.getContainerSize(); i++) {
         if (container.getItem(i).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   private static boolean isSpiritSummonBeingCreated(LivingEntity target) {
      return target.getPersistentData().getBoolean("tensura_tno_spirit_summoning");
   }
}
