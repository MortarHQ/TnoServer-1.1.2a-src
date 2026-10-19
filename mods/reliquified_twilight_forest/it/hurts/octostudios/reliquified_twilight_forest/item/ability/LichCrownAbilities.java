package it.hurts.octostudios.reliquified_twilight_forest.item.ability;

import com.google.common.collect.Lists;
import it.hurts.octostudios.reliquified_twilight_forest.init.DataComponentRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.item.GemItem;
import it.hurts.octostudios.reliquified_twilight_forest.item.relic.LichCrownItem;
import it.hurts.octostudios.reliquified_twilight_forest.network.LaunchTwilightBoltPacket;
import it.hurts.octostudios.reliquified_twilight_forest.network.LifedrainParticlePacket;
import it.hurts.octostudios.reliquified_twilight_forest.util.EntitiesButCool;
import it.hurts.octostudios.reliquified_twilight_forest.util.MathButCool;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent.Post;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import twilightforest.components.entity.FortificationShieldAttachment;
import twilightforest.entity.monster.LoyalZombie;
import twilightforest.entity.projectile.TwilightWandBolt;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFDataAttachments;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;
import twilightforest.item.LifedrainScepterItem;

public class LichCrownAbilities {
   public static final Map<String, AbilityData> ABILITIES = new HashMap<>();
   public static final Map<String, DeferredHolder<Item, GemItem>> GEMS = new HashMap<>();
   public static final int MAX_LIFEDRAIN_TIME = 100;
   public static final int MAX_TWILIGHT_TIME = 50;
   public static final String VENDETTA_ID = "reliquified_twilight_forest:vendetta";
   public static final AbilityData FORTIFICATION = register(
      AbilityData.builder("fortification")
         .stat(StatData.builder("max_shields").initialValue(3.0, 5.0).upgradeModifier(UpgradeOperation.ADD, 1.0).formatValue(Math::round).build())
         .stat(
            StatData.builder("interval")
               .initialValue(200.0, 160.0)
               .upgradeModifier(UpgradeOperation.MULTIPLY_TOTAL, -0.125)
               .formatValue(MathButCool::ticksToSecondsAndRoundSingleDigit)
               .build()
         )
         .build(),
      ItemRegistry.SHIELDING_GEM
   );
   public static final AbilityData ZOMBIE = register(
      AbilityData.builder("zombie")
         .stat(StatData.builder("max_zombies").initialValue(1.0, 2.0).upgradeModifier(UpgradeOperation.ADD, 1.0).formatValue(Math::round).build())
         .stat(StatData.builder("damage").initialValue(2.0, 3.0).upgradeModifier(UpgradeOperation.ADD, 0.5).formatValue(MathButCool::roundSingleDigit).build())
         .stat(
            StatData.builder("interval")
               .initialValue(400.0, 320.0)
               .upgradeModifier(UpgradeOperation.MULTIPLY_TOTAL, -0.075F)
               .formatValue(MathButCool::ticksToSecondsAndRoundSingleDigit)
               .build()
         )
         .build(),
      ItemRegistry.NECROMANCY_GEM
   );
   public static final AbilityData TWILIGHT = register(
      AbilityData.builder("twilight")
         .stat(StatData.builder("damage").initialValue(3.0, 4.0).upgradeModifier(UpgradeOperation.ADD, 0.5).formatValue(MathButCool::roundSingleDigit).build())
         .stat(
            StatData.builder("speed_scale")
               .initialValue(0.9, 1.2)
               .upgradeModifier(UpgradeOperation.ADD, 0.1)
               .formatValue(MathButCool::roundSingleDigit)
               .build()
         )
         .build(),
      ItemRegistry.TWILIGHT_GEM
   );
   public static final AbilityData LIFEDRAIN = register(
      AbilityData.builder("lifedrain")
         .stat(
            StatData.builder("heal_percentage")
               .initialValue(0.0075, 0.015)
               .upgradeModifier(UpgradeOperation.ADD, 0.0035)
               .formatValue(value -> MathButCool.roundSingleDigit(value * 400.0))
               .build()
         )
         .stat(StatData.builder("radius").initialValue(2.0, 5.0).upgradeModifier(UpgradeOperation.ADD, 0.5).formatValue(MathButCool::roundSingleDigit).build())
         .stat(
            StatData.builder("interval")
               .initialValue(300.0, 260.0)
               .upgradeModifier(UpgradeOperation.MULTIPLY_TOTAL, -0.1F)
               .formatValue(MathButCool::ticksToSecondsAndRoundSingleDigit)
               .build()
         )
         .build(),
      ItemRegistry.ABSORPTION_GEM
   );
   public static final AbilityData FROSTBITE = register(
      AbilityData.builder("frostbite")
         .stat(
            StatData.builder("duration")
               .initialValue(80.0, 100.0)
               .upgradeModifier(UpgradeOperation.ADD, 10.0)
               .formatValue(MathButCool::ticksToSecondsAndRoundSingleDigit)
               .build()
         )
         .build(),
      ItemRegistry.FROST_GEM
   );
   public static final AbilityData BIOME_BURN = register(
      AbilityData.builder("biome_burn")
         .stat(
            StatData.builder("multiplier")
               .initialValue(0.02, 0.05)
               .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
               .formatValue(MathButCool::percentageAndRoundSingleDigit)
               .build()
         )
         .build(),
      ItemRegistry.FIRE_GEM
   );
   public static final AbilityData VENDETTA = register(
      AbilityData.builder("vendetta")
         .stat(
            StatData.builder("lifetime")
               .initialValue(6.0, 10.0)
               .upgradeModifier(UpgradeOperation.ADD, 1.6666666666666667)
               .formatValue(MathButCool::ticksToSecondsAndRoundSingleDigit)
               .build()
         )
         .stat(
            StatData.builder("multiplier")
               .initialValue(0.25, 0.5)
               .upgradeModifier(UpgradeOperation.ADD, 0.08333333333333333)
               .formatValue(MathButCool::percentage)
               .build()
         )
         .build(),
      ItemRegistry.VENGEFUL_GEM
   );
   public static final AbilityData ETHEREAL_GUARD = register(
      AbilityData.builder("ethereal_guard")
         .stat(
            StatData.builder("max_projectiles")
               .initialValue(3.0, 6.0)
               .upgradeModifier(UpgradeOperation.ADD, 1.5555555555555556)
               .formatValue(Math::round)
               .build()
         )
         .stat(
            StatData.builder("cooldown")
               .initialValue(300.0, 200.0)
               .upgradeModifier(UpgradeOperation.ADD, -5.555555555555555)
               .formatValue(MathButCool::ticksToSecondsAndRoundSingleDigit)
               .build()
         )
         .build(),
      ItemRegistry.ETHER_GEM
   );
   public static final AbilityData MIRROR_LEECH = register(
      AbilityData.builder("mirror_leech")
         .stat(
            StatData.builder("chance")
               .initialValue(0.05, 0.1)
               .upgradeModifier(UpgradeOperation.ADD, 0.022222222222222223)
               .formatValue(MathButCool::percentageAndRoundSingleDigit)
               .build()
         )
         .build(),
      ItemRegistry.CARMINITE_GEM
   );
   public static final AbilityData FRENZY = register(
      AbilityData.builder("frenzy")
         .stat(
            StatData.builder("additive_multiplier")
               .initialValue(0.025, 0.05)
               .upgradeModifier(UpgradeOperation.ADD, 0.016666666666666666)
               .formatValue(MathButCool::percentageAndRoundSingleDigit)
               .build()
         )
         .build(),
      ItemRegistry.FRENZY_GEM
   );

   public static void fortificationUnequip(SlotContext slotContext, ItemStack stack) {
      ((FortificationShieldAttachment)slotContext.entity().getData(TFDataAttachments.FORTIFICATION_SHIELDS)).setShields(slotContext.entity(), 0, false);
   }

   public static void zombieUnequip(SlotContext slotContext, ItemStack stack) {
      ArrayList<UUID> uuids = Lists.newArrayList((Iterable)stack.getOrDefault(DataComponentRegistry.ZOMBIES, List.of()));
      uuids.forEach(uuid -> {
         Entity entity = ((ServerLevel)slotContext.entity().level()).getEntity(uuid);
         if (entity != null) {
            entity.discard();
         }
      });
      uuids.clear();
      stack.set(DataComponentRegistry.ZOMBIES, List.of());
   }

   public static void fortificationTick(LivingEntity entity, ItemStack stack) {
      if (!entity.isSpectator() && stack.getItem() instanceof LichCrownItem relic) {
         FortificationShieldAttachment var6 = (FortificationShieldAttachment)entity.getData(TFDataAttachments.FORTIFICATION_SHIELDS);
         int maxTime = (int)Math.round(relic.getStatValue(stack, "fortification", "interval"));
         int time = (Integer)stack.getOrDefault(DataComponentRegistry.FORTIFICATION_TIME, 0);
         if (var6.permanentShieldsLeft() < relic.getStatValue(stack, "fortification", "max_shields")) {
            if (time <= 0) {
               var6.addShields(entity, 1, false);
               time = maxTime;
               relic.spreadRelicExperience(entity, stack, 1);
            } else {
               time--;
            }
         }

         stack.set(DataComponentRegistry.FORTIFICATION_TIME, time);
      }
   }

   public static void lifedrainTick(LivingEntity entity, ItemStack stack) {
      if (!entity.isSpectator() && stack.getItem() instanceof LichCrownItem relic && entity.isAlive()) {
         int maxTime = (int)Math.round(relic.getStatValue(stack, "lifedrain", "interval")) + 100;
         int time = (Integer)stack.getOrDefault(DataComponentRegistry.LIFEDRAIN_TIME, 0);
         float healAmount = (float)(entity.getMaxHealth() * relic.getStatValue(stack, "lifedrain", "heal_percentage"));
         DamageSource dmg = TFDamageTypes.getEntityDamageSource(entity.level(), TFDamageTypes.LIFEDRAIN, entity, new EntityType[0]);
         if (time > maxTime - 100 && entity.getHealth() < entity.getMaxHealth() && entity.tickCount % 5 == 0) {
            List<LivingEntity> toAbsorb = EntitiesButCool.findEligibleEntities(
               entity, relic.getStatValue(stack, "lifedrain", "radius"), e -> !EntityUtils.isAlliedTo(entity, e) && e.isAlive() && entity.hasLineOfSight(e)
            );
            toAbsorb.forEach(
               toHurt -> {
                  PacketDistributor.sendToPlayersTrackingEntityAndSelf(
                     entity, new LifedrainParticlePacket(entity.getId(), toHurt.getEyePosition()), new CustomPacketPayload[0]
                  );
                  if (toHurt.getHealth() <= healAmount / toAbsorb.size() && !toHurt.getType().is(EntityTypes.BOSSES)) {
                     LichCrownItem.explodeEntity(entity, toHurt, dmg);
                  } else {
                     toHurt.invulnerableTime = 0;
                     if (toHurt.hurt(dmg, healAmount / toAbsorb.size())) {
                        if (toHurt.getMaxHealth() <= entity.getMaxHealth()) {
                           toHurt.setDeltaMovement(0.0, 0.15, 0.0);
                        } else {
                           toHurt.setDeltaMovement(0.0, 0.0, 0.0);
                        }
                     }
                  }
               }
            );
            entity.heal(healAmount);
            if (!toAbsorb.isEmpty()) {
               entity.level().playSound(null, entity.blockPosition(), (SoundEvent)TFSounds.LIFE_SCEPTER_DRAIN.get(), SoundSource.PLAYERS);
               if (entity.tickCount % 10 == 0) {
                  relic.spreadRelicExperience(entity, stack, 1);
               }
            }
         }

         if (time <= 0) {
            if (entity.getHealth() / entity.getMaxHealth() <= 0.2F) {
               time = maxTime;
            }
         } else {
            time--;
         }

         stack.set(DataComponentRegistry.LIFEDRAIN_TIME, time);
      }
   }

   public static void twilightTick(LivingEntity entity, ItemStack stack) {
      if (!entity.isSpectator() && stack.getItem() instanceof LichCrownItem relic) {
         int time = (Integer)stack.getOrDefault(DataComponentRegistry.TWILIGHT_TIME, 0);
         if (time > 0) {
            time--;
         }

         stack.set(DataComponentRegistry.TWILIGHT_TIME, time);
      }
   }

   public static void zombieTick(LivingEntity entity, ItemStack stack) {
      if (!entity.isSpectator() && stack.getItem() instanceof LichCrownItem relic) {
         int var7 = (Integer)stack.getOrDefault(DataComponentRegistry.ZOMBIE_TIME, 0);
         ArrayList uuids = Lists.newArrayList((Iterable)stack.getOrDefault(DataComponentRegistry.ZOMBIES, List.of()));
         int maxZombies = (int)Math.round(relic.getStatValue(stack, "zombie", "max_zombies"));
         if (var7 <= 0 && uuids.size() < maxZombies) {
            var7 = (int)Math.round(relic.getStatValue(stack, "zombie", "interval"));
            LoyalZombie zombie = spawnZombie(entity, (float)relic.getStatValue(stack, "zombie", "damage"), entity.blockPosition());
            if (zombie != null) {
               relic.spreadRelicExperience(entity, stack, 1);
               uuids.add(zombie.getUUID());
            }
         }

         if (var7 > 0) {
            var7--;
         }

         stack.set(DataComponentRegistry.ZOMBIE_TIME, var7);
         if (entity.tickCount % 15 == 0) {
            uuids.removeIf(uuid -> {
               Entity e = ((ServerLevel)entity.level()).getEntity(uuid);
               if (e == null) {
                  return true;
               }

               boolean flag = !e.isAlive() || !((ServerLevel)entity.level()).isPositionEntityTicking(e.blockPosition());
               if (flag) {
                  e.discard();
               }

               return flag;
            });
         }

         stack.set(DataComponentRegistry.ZOMBIES, uuids);
      }
   }

   public static void frenzyTick(LivingEntity entity, ItemStack stack, LichCrownItem instance) {
      List<UUID> entities = (List<UUID>)stack.getOrDefault(DataComponentRegistry.ENTITIES, List.of());
      ServerLevel serverLevel = (ServerLevel)entity.level();
      if (!entities.isEmpty() || ((Number)stack.getOrDefault(DataComponentRegistry.MULTIPLIER, 0)).floatValue() != 0.0F) {
         int time = (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
         if (time >= 100) {
            resetFrenzy(entity, stack, null);
         }

         stack.set(DataComponentRegistry.TIME, ++time);
      }
   }

   public static void resetFrenzy(LivingEntity entity, ItemStack stack, @Nullable LivingEntity newTarget) {
      List<UUID> uuids = List.of();
      if (newTarget != null) {
         uuids = List.of(newTarget.getUUID());
      }

      stack.set(DataComponentRegistry.ENTITIES, uuids);
      stack.set(DataComponentRegistry.MULTIPLIER, 0.0F);
      stack.set(DataComponentRegistry.TIME, 0);
   }

   public static LoyalZombie spawnZombie(LivingEntity entity, final float damage, BlockPos position) {
      final Level level = entity.level();
      LoyalZombie zombie = new LoyalZombie((EntityType)TFEntities.LOYAL_ZOMBIE.get(), level) {
         public boolean doHurtTarget(Entity entity) {
            if (entity.hurt(this.damageSources().mobAttack(this), damage)) {
               entity.push(0.0, 0.2, 0.0);
               return true;
            } else {
               return false;
            }
         }

         public InteractionResult interactAt(Player player, Vec3 vec3, InteractionHand hand) {
            return InteractionResult.PASS;
         }

         public void aiStep() {
            if (!this.hasEffect(MobEffects.DAMAGE_BOOST)) {
               if (!level.isClientSide) {
                  LifedrainScepterItem.animateTargetShatter((ServerLevel)level, this);
               }

               this.hurt(TFDamageTypes.getDamageSource(this.level(), TFDamageTypes.EXPIRED, new EntityType[0]), Float.MAX_VALUE);
               this.discard();
            }

            super.aiStep();
         }
      };
      zombie.moveTo(findSafeSpawn(position, (ServerLevel)level, 3).getBottomCenter());
      if (!level.noCollision(zombie, zombie.getBoundingBox())) {
         return null;
      }

      zombie.spawnAnim();
      zombie.setTame(true, false);
      zombie.setOwnerUUID(entity.getUUID());
      zombie.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Integer.MAX_VALUE, 0, true, false, false));
      zombie.setBaby(true);
      level.addFreshEntity(zombie);
      level.gameEvent(entity, GameEvent.ENTITY_PLACE, position);
      zombie.playSound((SoundEvent)TFSounds.ZOMBIE_SCEPTER_USE.get(), 1.0F, 1.0F);
      zombie.setSilent(true);
      return zombie;
   }

   public static BlockPos findSafeSpawn(BlockPos position, ServerLevel world, int radius) {
      Random random = new Random();

      for (int attempts = 0; attempts < 10; attempts++) {
         int xOffset = random.nextInt(radius * 2) - radius;
         int zOffset = random.nextInt(radius * 2) - radius;
         BlockPos potentialPos = position.offset(xOffset, 0, zOffset);
         BlockPos spawnPos = world.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, potentialPos);
         if (isSafe(spawnPos, world)) {
            return spawnPos;
         }
      }

      return position;
   }

   public static boolean isSafe(BlockPos pos, ServerLevel world) {
      return world.getBlockState(pos).isAir() && world.getBlockState(pos.below()).isSolid() && world.getBlockState(pos.below()).getBlock() != Blocks.LAVA;
   }

   private static AbilityData register(AbilityData data, DeferredHolder<Item, GemItem> gem) {
      ABILITIES.put(data.getId(), data);
      GEMS.put(data.getId(), gem);
      return data;
   }

   @EventBusSubscriber(Dist.CLIENT)
   public static class ClientEvents {
      @SubscribeEvent
      public static void renderRevengeData(Post e) {
      }

      @SubscribeEvent
      public static void swingEvent(LeftClickEmpty e) {
         Player player = e.getEntity();
         if (!player.isSpectator()) {
            double maxDistance = 64.0;
            EntityHitResult target = getEntityLookingAt(player, maxDistance);
            if (target != null && target.getEntity() instanceof LivingEntity) {
               PacketDistributor.sendToServer(new LaunchTwilightBoltPacket(), new CustomPacketPayload[0]);
            }
         }
      }

      public static EntityHitResult getEntityLookingAt(Player player, double maxDistance) {
         Vec3 eyePosition = player.getEyePosition(1.0F);
         Vec3 lookVector = player.getViewVector(1.0F);
         Vec3 reachEnd = eyePosition.add(lookVector.scale(maxDistance));
         return ProjectileUtil.getEntityHitResult(
            player,
            eyePosition,
            reachEnd,
            player.getBoundingBox().expandTowards(lookVector.scale(maxDistance)).inflate(1.0),
            entity -> entity instanceof LivingEntity
               && entity.isAlive()
               && player.hasLineOfSight(entity)
               && entity != player
               && !EntityUtils.isAlliedTo(player, entity),
            maxDistance * maxDistance
         );
      }
   }

   @EventBusSubscriber
   public static class CommonEvents {
      @SubscribeEvent
      public static void frenzyDeath(LivingDeathEvent e) {
         ItemStack stack = EntityUtils.findEquippedCurio(e.getSource().getEntity(), (Item)ItemRegistry.LICH_CROWN.get());
         if (!e.getEntity().level().isClientSide
            && e.getSource().getEntity() instanceof LivingEntity entity
            && stack.getItem() instanceof LichCrownItem relic
            && relic.isAbilityUnlocked(stack, "frenzy")) {
            stack.set(DataComponentRegistry.ENTITIES, List.of());
            stack.set(DataComponentRegistry.TIME, 0);
         }
      }

      @SubscribeEvent
      public static void processFrenzy(Pre e) {
         ItemStack stack = EntityUtils.findEquippedCurio(e.getSource().getEntity(), (Item)ItemRegistry.LICH_CROWN.get());
         if (!e.getEntity().level().isClientSide && stack.getItem() instanceof LichCrownItem relic && relic.isAbilityUnlocked(stack, "frenzy")) {
            List<UUID> entities = (List<UUID>)stack.getOrDefault(DataComponentRegistry.ENTITIES, List.of());
            if (entities.contains(e.getEntity().getUUID())) {
               LichCrownAbilities.resetFrenzy((LivingEntity)e.getSource().getEntity(), stack, e.getEntity());
            } else {
               ArrayList<UUID> mutable = Lists.newArrayList(entities);
               mutable.add(e.getEntity().getUUID());
               float multiplier = (Float)stack.getOrDefault(DataComponentRegistry.MULTIPLIER, 0.0F);
               e.setNewDamage(e.getNewDamage() * (multiplier + 1.0F));
               multiplier += (float)relic.getStatValue(stack, "frenzy", "additive_multiplier");
               stack.set(DataComponentRegistry.ENTITIES, mutable);
               stack.set(DataComponentRegistry.MULTIPLIER, multiplier);
               stack.set(DataComponentRegistry.TIME, 0);
            }
         }
      }

      @SubscribeEvent
      public static void processMirrorLeech(Pre e) {
         ItemStack stack = EntityUtils.findEquippedCurio(e.getEntity(), (Item)ItemRegistry.LICH_CROWN.get());
         if (e.getSource().getEntity() != e.getEntity()
            && !e.getEntity().level().isClientSide
            && stack.getItem() instanceof LichCrownItem relic
            && relic.isAbilityUnlocked(stack, "mirror_leech")
            && !(e.getEntity().getRandom().nextFloat() > relic.getStatValue(stack, "mirror_leech", "chance"))) {
            if (e.getSource().getEntity() instanceof LivingEntity source) {
               source.hurt(e.getSource(), e.getOriginalDamage());
               e.getEntity().heal(e.getNewDamage());
               relic.spreadRelicExperience(e.getEntity(), stack, Mth.floor(e.getNewDamage()));
               e.getEntity().level().playSound(null, e.getEntity(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 1.0F, 2.0F);
               e.setNewDamage(0.0F);
            }
         }
      }

      @SubscribeEvent
      public static void tickEther(net.neoforged.neoforge.event.tick.EntityTickEvent.Post e) {
         if (!e.getEntity().level().isClientSide) {
            ItemStack stack = EntityUtils.findEquippedCurio(e.getEntity(), (Item)ItemRegistry.LICH_CROWN.get());
            int count = (Integer)stack.getOrDefault(it.hurts.sskirillss.relics.init.DataComponentRegistry.COUNT, 0);
            int time = (Integer)stack.getOrDefault(it.hurts.sskirillss.relics.init.DataComponentRegistry.TIME, 0);
            if (stack.getItem() instanceof LichCrownItem relic
               && relic.isAbilityUnlocked(stack, "ethereal_guard")
               && !(count < relic.getStatValue(stack, "ethereal_guard", "max_projectiles"))) {
               if (time < relic.getStatValue(stack, "ethereal_guard", "cooldown")) {
                  time++;
               } else {
                  count = 0;
                  time = 0;
               }

               stack.set(it.hurts.sskirillss.relics.init.DataComponentRegistry.COUNT, count);
               stack.set(it.hurts.sskirillss.relics.init.DataComponentRegistry.TIME, time);
            }
         }
      }

      @SubscribeEvent
      public static void phaseProjectiles(ProjectileImpactEvent e) {
         if (e.getRayTraceResult() instanceof EntityHitResult hitResult && hitResult.getEntity() instanceof LivingEntity entity) {
            ItemStack stack = EntityUtils.findEquippedCurio(entity, (Item)ItemRegistry.LICH_CROWN.get());
            int count = (Integer)stack.getOrDefault(it.hurts.sskirillss.relics.init.DataComponentRegistry.COUNT, 0);
            if (stack.getItem() instanceof LichCrownItem relic
               && relic.isAbilityUnlocked(stack, "ethereal_guard")
               && !(count >= relic.getStatValue(stack, "ethereal_guard", "max_projectiles"))) {
               if (!e.getEntity().level().isClientSide) {
                  entity.level().playSound(null, entity, SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.NEUTRAL, 1.0F, 2.0F);
                  relic.spreadRelicExperience(entity, stack, 1);
                  stack.set(it.hurts.sskirillss.relics.init.DataComponentRegistry.COUNT, count + 1);
               }

               e.setCanceled(true);
            }
         }
      }

      @SubscribeEvent
      public static void storeRevengeData(net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post e) {
         LivingEntity victim = e.getEntity();
         ItemStack stack = EntityUtils.findEquippedCurio(victim, (Item)ItemRegistry.LICH_CROWN.get());
         if (e.getSource().getEntity() instanceof LivingEntity source
            && source != e.getEntity()
            && !victim.level().isClientSide
            && stack.getItem() instanceof LichCrownItem relic
            && relic.isAbilityUnlocked(stack, "vendetta")) {
            CompoundTag tag = source.getPersistentData().getCompound("reliquified_twilight_forest:vendetta");
            tag.putInt(victim.getStringUUID(), (int)Math.round(relic.getStatValue(stack, "vendetta", "lifetime")));
            source.getPersistentData().put("reliquified_twilight_forest:vendetta", tag);
         }
      }

      @SubscribeEvent
      public static void tickRevenge(net.neoforged.neoforge.event.tick.EntityTickEvent.Post e) {
         if (e.getEntity().getPersistentData().contains("reliquified_twilight_forest:vendetta")) {
            CompoundTag tag = e.getEntity().getPersistentData().getCompound("reliquified_twilight_forest:vendetta");
            Set<String> keys = tag.getAllKeys();
            if (keys.isEmpty()) {
               e.getEntity().getPersistentData().remove("reliquified_twilight_forest:vendetta");
            } else {
               CompoundTag newTag = new CompoundTag();
               tag.getAllKeys().forEach(key -> {
                  int ticks = tag.getInt(key);
                  if (--ticks > 0) {
                     newTag.putInt(key, ticks);
                  }
               });
               e.getEntity().getPersistentData().put("reliquified_twilight_forest:vendetta", newTag);
            }
         }
      }

      @SubscribeEvent
      public static void multiplyRevengeDamage(Pre e) {
         ItemStack stack = EntityUtils.findEquippedCurio(e.getSource().getEntity(), (Item)ItemRegistry.LICH_CROWN.get());
         if (e.getSource().getEntity() instanceof LivingEntity source
            && source != e.getEntity()
            && !source.level().isClientSide
            && stack.getItem() instanceof LichCrownItem relic
            && relic.isAbilityUnlocked(stack, "vendetta")
            && e.getEntity().getPersistentData().getCompound("reliquified_twilight_forest:vendetta").contains(source.getStringUUID())) {
            float multiplier = (float)relic.getStatValue(stack, "vendetta", "multiplier");
            e.setNewDamage(e.getNewDamage() * (multiplier + 1.0F));
            relic.spreadRelicExperience(source, stack, 1);
            e.getEntity().getPersistentData().remove("reliquified_twilight_forest:vendetta");
         }
      }

      @SubscribeEvent
      public static void multiplyBiomeDamage(Pre e) {
         ItemStack stack = EntityUtils.findEquippedCurio(e.getSource().getEntity(), (Item)ItemRegistry.LICH_CROWN.get());
         if (e.getSource().getEntity() instanceof LivingEntity livingEntity
            && livingEntity != e.getEntity()
            && !livingEntity.level().isClientSide
            && stack.getItem() instanceof LichCrownItem relic
            && relic.isAbilityUnlocked(stack, "biome_burn")) {
            float multiplier = (float)relic.getStatValue(stack, "biome_burn", "multiplier");
            float temperature = ((Biome)livingEntity.level().getBiome(livingEntity.blockPosition()).value()).getBaseTemperature();
            if (!(temperature <= 0.5F)) {
               e.setNewDamage(e.getNewDamage() * (1.0F + multiplier * (temperature * 10.0F - 5.0F)));
               relic.spreadRelicExperience(livingEntity, stack, 1);
            }
         }
      }

      @SubscribeEvent
      public static void applyFrostbite(net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post e) {
         ItemStack stack = EntityUtils.findEquippedCurio(e.getSource().getEntity(), (Item)ItemRegistry.LICH_CROWN.get());
         if (e.getSource().getEntity() instanceof Player player
            && player != e.getEntity()
            && !player.level().isClientSide
            && stack.getItem() instanceof LichCrownItem relic
            && relic.isAbilityUnlocked(stack, "frostbite")) {
            e.getEntity().setTicksFrozen(e.getEntity().getTicksFrozen() + (int)Math.round(relic.getStatValue(stack, "frostbite", "duration")));
            relic.spreadRelicExperience(player, stack, 1);
         }
      }

      @SubscribeEvent
      public static void onTwilightBoltHit(net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post e) {
         ItemStack stack = EntityUtils.findEquippedCurio(e.getSource().getEntity(), (Item)ItemRegistry.LICH_CROWN.get());
         if (e.getSource().getDirectEntity() instanceof TwilightWandBolt bolt
            && e.getSource().getEntity() instanceof Player player
            && stack.getItem() instanceof LichCrownItem relic
            && bolt.getPersistentData().contains("reliquified_twilight_forest:isCustom")) {
            relic.spreadRelicExperience(player, stack, 1);
         }
      }
   }
}
