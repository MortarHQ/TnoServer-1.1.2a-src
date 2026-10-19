package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.dataComponents.IntegerCounter;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastStage;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.PredicateType;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class MadeInHeaven extends MoreRelicBase {
   private final int experienceTimer = 10;

   public MadeInHeaven(Properties properties) {
      super(new Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant());
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("as_one")
                     .stat(
                        StatData.builder("initial_boost")
                           .initialValue(0.03, 0.06)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .upgradeModifier(UpgradeOperation.ADD, 0.005)
                           .build()
                     )
                     .stat(
                        StatData.builder("accel_cooldown")
                           .initialValue(48.0, 36.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .upgradeModifier(UpgradeOperation.ADD, -2.0)
                           .build()
                     )
                     .maxLevel(8)
                     .requiredLevel(0)
                     .build()
               )
               .ability(
                  AbilityData.builder("zenith")
                     .stat(
                        StatData.builder("revive_cooldown")
                           .initialValue(1200.0, 860.0)
                           .upgradeModifier(UpgradeOperation.ADD, -40.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("stat_boost")
                           .initialValue(0.08, 0.14)
                           .upgradeModifier(UpgradeOperation.ADD, 0.02)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .requiredLevel(5)
                     .maxLevel(3)
                     .build()
               )
               .ability(
                  AbilityData.builder("condemn")
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(60.0, 45.0)
                           .upgradeModifier(UpgradeOperation.ADD, -5.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("damage")
                           .initialValue(3.0, 5.0)
                           .upgradeModifier(UpgradeOperation.ADD, 0.5)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .requiredLevel(8)
                     .active(CastData.builder().type(CastType.INSTANTANEOUS).build())
                     .maxLevel(4)
                     .build()
               )
               .ability(
                  AbilityData.builder("divine_judgement")
                     .requiredLevel(12)
                     .maxLevel(3)
                     .icon(
                        (player, stack, ability) -> ability + "_" + (DataComponentHandler.getAltAltAltIntegerCounter(stack).current() > 0 ? "cancel" : "cast")
                     )
                     .active(
                        CastData.builder()
                           .type(CastType.INTERRUPTIBLE)
                           .predicate(
                              "stack_count",
                              PredicateType.CAST,
                              (player, itemStack) -> getCurrentAccelAmplifier(player) == 9
                                 || DataComponentHandler.getAltAltAltIntegerCounter(itemStack).current() > 0
                           )
                           .build()
                     )
                     .stat(
                        StatData.builder("damage")
                           .initialValue(35.0, 50.0)
                           .upgradeModifier(UpgradeOperation.ADD, 10.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(1260.0, 960.0)
                           .upgradeModifier(UpgradeOperation.ADD, -60.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(18)
               .initialCost(200)
               .step(70)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("as_one").initialValue(1).build()).build())
               .build()
         )
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      IntegerCounter judgementCounter = DataComponentHandler.getAltAltAltIntegerCounter(stack);
      int judgementState = judgementCounter.current();
      if (livingEntity.level().isClientSide) {
         if (judgementState > 0) {
            if (judgementState > 100) {
               livingEntity.setDeltaMovement(0.0, 0.051000000000000004, 0.0);
            } else {
               livingEntity.setDeltaMovement(0.0, 0.001, 0.0);
            }
         }
      } else {
         IntegerCounter reviveCounter = DataComponentHandler.getAltIntegerCounter(stack);
         int reviveCooldown = reviveCounter.current();
         if (reviveCooldown > 1) {
            reviveCooldown--;
         } else if (reviveCooldown == 1) {
            reviveCooldown--;
            Component message = Component.translatable("tooltip.morerelics.made_in_heaven.zenith_up")
               .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(16766720)));
            livingEntity.sendSystemMessage(message);
         }

         stack.set(DataComponentHandler.ALT_INT_COUNTER.get(), reviveCounter.getUpdatedCounter(reviveCooldown));
         float multiplier = (float)this.getStatValue(stack, "as_one", "initial_boost");
         int currentStacks = getCurrentAccelAmplifier(slotContext.entity()) + 1;
         float zenithMult = 0.0F;
         if (currentStacks == 10 && this.isAbilityUnlocked(stack, "zenith")) {
            zenithMult = (float)this.getStatValue(stack, "zenith", "stat_boost");
         }

         float accelBoost = currentStacks * 0.03F + zenithMult;
         IntegerCounter counter = DataComponentHandler.getIntegerCounter(stack);
         int experienceProgress = counter.current();
         if (10 > experienceProgress) {
            experienceProgress++;
         } else {
            this.addRelicExperience(livingEntity, stack, 1);
            experienceProgress = 0;
         }

         stack.set(DataComponentHandler.INT_COUNTER.get(), counter.getUpdatedCounter(experienceProgress));
         IntegerCounter accelCounter = DataComponentHandler.getAltAltIntegerCounter(stack);
         int accelTimer = accelCounter.current();
         if (accelTimer > 0) {
            stack.set(DataComponentHandler.ALT_ALT_INT_COUNTER, accelCounter.getUpdatedCounter(accelTimer - 1));
         }

         if (judgementState > 0 && livingEntity instanceof ServerPlayer player) {
            if (judgementState == 160) {
               player.level()
                  .playSound(null, player.position().x, player.position().y, player.position().z, MoreRelics.JUDGEMENT_BELL, SoundSource.MASTER, 0.7F, 1.0F);
            } else if (judgementState <= 100) {
               this.spawnSpinningInferno((ServerLevel)player.level(), player, judgementState);
               float totalDamage = (float)this.getStatValue(stack, "divine_judgement", "damage");
               if (judgementState == 100) {
                  this.damageAndCharEnemiesInSphere(player, totalDamage / 15.0F, false);
                  player.level()
                     .playSound(null, player.position().x, player.position().y, player.position().z, MoreRelics.JUDGEMENT_STORM, SoundSource.MASTER, 1.5F, 1.0F);
               } else if (judgementState == 80) {
                  this.damageAndCharEnemiesInSphere(player, 2.0F * totalDamage / 15.0F, false);
               } else if (judgementState == 60) {
                  this.damageAndCharEnemiesInSphere(player, 3.0F * totalDamage / 15.0F, true);
               } else if (judgementState == 40) {
                  this.damageAndCharEnemiesInSphere(player, 4.0F * totalDamage / 15.0F, true);
               } else if (judgementState == 20) {
                  this.damageAndCharEnemiesInSphere(player, 5.0F * totalDamage / 15.0F, true);
               }

               if (judgementState == 1) {
                  this.setAbilityCooldown(stack, "divine_judgement", (int)this.getStatValue(stack, "divine_judgement", "cooldown") * 20);
                  player.setNoGravity(false);
               }
            }

            stack.set(DataComponentHandler.ALT_ALT_ALT_INT_COUNTER, judgementCounter.getUpdatedCounter(judgementState - 1));
         }

         EntityUtils.resetAttribute(livingEntity, stack, Attributes.ATTACK_DAMAGE, multiplier + accelBoost, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.resetAttribute(livingEntity, stack, Attributes.ATTACK_SPEED, multiplier + accelBoost, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.resetAttribute(livingEntity, stack, Attributes.MAX_HEALTH, multiplier, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.resetAttribute(livingEntity, stack, Attributes.MINING_EFFICIENCY, multiplier + accelBoost, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.resetAttribute(livingEntity, stack, Attributes.OXYGEN_BONUS, multiplier + accelBoost, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.resetAttribute(livingEntity, stack, Attributes.SNEAKING_SPEED, multiplier + accelBoost, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.resetAttribute(livingEntity, stack, Attributes.MOVEMENT_SPEED, multiplier + accelBoost, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.resetAttribute(livingEntity, stack, Attributes.SWEEPING_DAMAGE_RATIO, multiplier + accelBoost, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.resetAttribute(livingEntity, stack, Attributes.LUCK, multiplier + accelBoost, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.resetAttribute(livingEntity, stack, Attributes.KNOCKBACK_RESISTANCE, multiplier + accelBoost, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.resetAttribute(livingEntity, stack, Attributes.SUBMERGED_MINING_SPEED, multiplier + accelBoost, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.resetAttribute(livingEntity, stack, Attributes.BLOCK_BREAK_SPEED, multiplier + accelBoost, Operation.ADD_MULTIPLIED_TOTAL);
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (stack.getItem() != newStack.getItem()) {
         slotContext.entity().removeEffect(MoreRelics.ACCELERATING);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.ATTACK_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.MAX_HEALTH, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.MINING_EFFICIENCY, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.OXYGEN_BONUS, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.SNEAKING_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.SWEEPING_DAMAGE_RATIO, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.KNOCKBACK_RESISTANCE, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.SUBMERGED_MINING_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.LUCK, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.BLOCK_BREAK_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
      }
   }

   @SubscribeEvent
   public static void onPlayerAttack(LivingIncomingDamageEvent event) {
      if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide) {
         EntityUtils.findEquippedCurios(player, (Item)MoreRelics.MADE_IN_HEAVEN.get())
            .forEach(
               stack -> {
                  MadeInHeaven item = (MadeInHeaven)stack.getItem();
                  if (item.isAbilityUnlocked(stack, "as_one")) {
                     int currentAmplifier = getCurrentAccelAmplifier(player);
                     IntegerCounter accelCounter = DataComponentHandler.getAltAltIntegerCounter(stack);
                     int accelCooldown = accelCounter.current();
                     if (accelCooldown == 0 && currentAmplifier != 9) {
                        player.addEffect(new MobEffectInstance(MoreRelics.ACCELERATING, 400, currentAmplifier + 1));
                        stack.set(
                           DataComponentHandler.ALT_ALT_INT_COUNTER,
                           accelCounter.getUpdatedCounter((int)(item.getStatValue(stack, "as_one", "accel_cooldown") * 20.0))
                        );
                     } else {
                        player.addEffect(new MobEffectInstance(MoreRelics.ACCELERATING, 400, currentAmplifier));
                     }
                  }
               }
            );
      }
   }

   private static int getCurrentAccelAmplifier(LivingEntity player) {
      int currentStacks = -1;
      MobEffectInstance accelInstance = player.getEffect(MoreRelics.ACCELERATING);
      if (accelInstance != null) {
         currentStacks = accelInstance.getAmplifier();
      }

      return currentStacks;
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onPlayerDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
         int shouldPrevent = EntityUtils.findEquippedCurios(player, (Item)MoreRelics.MADE_IN_HEAVEN.get())
            .stream()
            .mapToInt(
               stack -> {
                  MadeInHeaven item = (MadeInHeaven)stack.getItem();
                  IntegerCounter counter = DataComponentHandler.getAltIntegerCounter(stack);
                  int currentAmplifier = getCurrentAccelAmplifier(player);
                  boolean result = item.isAbilityUnlocked(stack, "zenith") && counter.current() <= 0 && currentAmplifier == 9;
                  if (result) {
                     stack.set(
                        DataComponentHandler.ALT_INT_COUNTER.get(), counter.getUpdatedCounter((int)item.getStatValue(stack, "zenith", "revive_cooldown") * 20)
                     );
                     return 1;
                  } else {
                     return 0;
                  }
               }
            )
            .sum();
         if (shouldPrevent == 1) {
            player.removeEffect(MoreRelics.ACCELERATING);
            event.setCanceled(true);
            player.setHealth(player.getMaxHealth() / 2.0F);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GLASS_BREAK, SoundSource.MASTER, 1.2F, 0.4F);
            Component message = Component.translatable("tooltip.morerelics.made_in_heaven.zenith_protect")
               .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(16766720)));
            player.sendSystemMessage(message);
         }
      }
   }

   @SubscribeEvent
   public static void onDamageTaken(LivingIncomingDamageEvent event) {
      if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
         double shouldReduce = EntityUtils.findEquippedCurios(player, (Item)MoreRelics.MADE_IN_HEAVEN.get()).stream().mapToDouble(stack -> {
            MadeInHeaven item = (MadeInHeaven)stack.getItem();
            return DataComponentHandler.getAltAltAltIntegerCounter(stack).current() > 0 ? 1.0 : 0.0;
         }).sum();
         if (shouldReduce > 0.0) {
            event.setAmount(event.getAmount() * 0.4F);
         }
      }
   }

   public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
      if (!player.level().isClientSide) {
         if (ability.equals("condemn")) {
            double range = 4.5;
            double rangeSqr = range * range;
            double coneAngle = Math.toRadians(60.0);
            double damage = this.getStatValue(stack, "condemn", "damage");
            Vec3 origin = player.position().add(0.0, player.getEyeHeight(), 0.0);
            Vec3 forward = player.getLookAngle().normalize();

            for (LivingEntity target : player.level()
               .getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(range), e -> e != player && !e.isSpectator() && e.isAlive())) {
               AABB box = target.getHitbox();
               Vec3 closest = new Vec3(
                  Math.clamp(origin.x, box.minX, box.maxX), Math.clamp(origin.y, box.minY, box.maxY), Math.clamp(origin.z, box.minZ, box.maxZ)
               );
               Vec3 toClosest = closest.subtract(origin);
               double distance = toClosest.lengthSqr();
               if (!(distance >= rangeSqr)) {
                  Vec3 dir = toClosest.normalize();
                  double cosHalfCone = Math.cos(coneAngle * 0.5);
                  if (distance < 0.1F || forward.dot(dir) >= cosHalfCone) {
                     target.setRemainingFireTicks(60);
                     target.hurt(target.damageSources().source(MoreRelics.JUDGEMENT_BURN, player, player), (float)damage);
                     target.addEffect(new MobEffectInstance(MoreRelics.CHARRED, 200, 0));
                  }
               }
            }

            spawnHolyFireCone(player.level(), origin, forward, 120, coneAngle, range);
            this.setAbilityCooldown(stack, "condemn", (int)this.getStatValue(stack, "condemn", "cooldown") * 20);
         } else if (ability.equals("divine_judgement")) {
            IntegerCounter judgementCounter = DataComponentHandler.getAltAltAltIntegerCounter(stack);
            if (judgementCounter.current() == 0) {
               stack.set(DataComponentHandler.ALT_ALT_ALT_INT_COUNTER, DataComponentHandler.getAltAltAltIntegerCounter(stack).getUpdatedCounter(160));
            } else {
               stack.set(DataComponentHandler.ALT_ALT_ALT_INT_COUNTER, DataComponentHandler.getAltAltAltIntegerCounter(stack).getUpdatedCounter(0));
               this.setAbilityCooldown(stack, "divine_judgement", (int)this.getStatValue(stack, "divine_judgement", "cooldown") * 20);
            }
         }
      }
   }

   public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
      return super.canUnequip(slotContext, stack) && DataComponentHandler.getAltAltAltIntegerCounter(stack).current() == 0;
   }

   @Override
   public boolean canEquipMultipleCopies() {
      return false;
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
      ArrayList<Component> list = new ArrayList<>();
      list.add(Component.translatable("tooltip.morerelics.made_in_heaven.source").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
      return list;
   }

   private static void spawnHolyFireParticles(Level level, Vec3 position) {
      int particleCount = 40;
      level.playSound(null, position.x, position.y, position.z, MoreRelics.JUDGEMENT, SoundSource.MASTER, 0.4F, 1.0F);
      ServerLevel sevLevel = (ServerLevel)level;

      for (ServerPlayer player : level.players()) {
         sevLevel.sendParticles(player, MoreRelics.HOLY_FIRE.get(), true, position.x, position.y + 1.0, position.z, particleCount, 0.0, 0.0, 0.0, 0.05);
      }
   }

   private static void spawnHolyFireCone(Level level, Vec3 origin, Vec3 look, int particleCount, double coneAngle, double range) {
      if (level instanceof ServerLevel sevLevel) {
         List players = level.players();
         Random random = new Random();
         Vec3 forward = look.normalize();
         Vec3 up = new Vec3(0.0, 1.0, 0.0);
         if (Math.abs(forward.dot(up)) > 0.99) {
            up = new Vec3(1.0, 0.0, 0.0);
         }

         Vec3 right = forward.cross(up).normalize();
         Vec3 newUp = right.cross(forward).normalize();
         double cosThetaMax = Math.cos(coneAngle / 2.0);
         double speed = range / 2.0;

         for (int i = 0; i < particleCount; i++) {
            double cosTheta = cosThetaMax + (1.0 - cosThetaMax) * random.nextDouble();
            double theta = Math.acos(cosTheta);
            double phi = (Math.PI * 2) * random.nextDouble();
            Vec3 dir = forward.scale(Math.cos(theta))
               .add(right.scale(Math.sin(theta) * Math.cos(phi)))
               .add(newUp.scale(Math.sin(theta) * Math.sin(phi)))
               .normalize();
            Vec3 velocity = dir.scale(speed * (0.5 + 0.5 * random.nextDouble()));

            for (ServerPlayer player : players) {
               sevLevel.sendParticles(player, MoreRelics.HOLY_FIRE.get(), true, origin.x, origin.y, origin.z, 0, velocity.x, velocity.y, velocity.z, 1.0);
            }
         }

         level.playSound(null, origin.x, origin.y, origin.z, MoreRelics.JUDGEMENT, SoundSource.MASTER, 0.4F, 1.0F);
      }
   }

   private void damageAndCharEnemiesInSphere(LivingEntity player, float damage, boolean doChar) {
      for (LivingEntity target : player.level()
         .getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(7.0), e -> e != player && !e.isSpectator() && e.isAlive())) {
         target.setRemainingFireTicks(100);
         Holder<DamageType> typeHolder = target.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(MoreRelics.JUDGEMENT_BURN);
         target.hurt(new DamageSource(typeHolder, player, player), damage);
         if (doChar) {
            MobEffectInstance instance = target.getEffect(MoreRelics.CHARRED);
            if (instance != null) {
               target.addEffect(new MobEffectInstance(MoreRelics.CHARRED, 600, Math.clamp(instance.getAmplifier() + 1, 0, 2)));
            } else {
               target.addEffect(new MobEffectInstance(MoreRelics.CHARRED, 600, 0));
            }
         }
      }
   }

   private void spawnSpinningInferno(ServerLevel level, ServerPlayer player, int judgementState) {
      Random random = new Random();
      double cx = player.getX();
      double cy = player.getY();
      double cz = player.getZ();
      int particleCount = 64;
      double maxRadius = 14.0;
      double spinSpeed = 0.25;

      for (int i = 0; i < particleCount; i++) {
         double u = random.nextDouble();
         double v = random.nextDouble();
         double w = random.nextDouble();
         double theta = (Math.PI * 2) * u;
         double phi = Math.acos(2.0 * v - 1.0);
         double r = maxRadius * Math.cbrt(w);
         double offsetX = r * Math.sin(phi) * Math.cos(theta);
         double offsetY = r * Math.cos(phi);
         double offsetZ = r * Math.sin(phi) * Math.sin(theta);
         double radiusXZ = Math.sqrt(offsetX * offsetX + offsetZ * offsetZ);
         double particleY = cy + offsetY;
         double initialAngle = random.nextDouble() * 2.0 * Math.PI;
         double angularSpeed = spinSpeed * (0.8 + random.nextDouble() * 0.4);
         level.sendParticles(MoreRelics.ORBITING_HOLY_FIRE.get(), cx, particleY, cz, 0, radiusXZ, initialAngle, angularSpeed, 1.0);
         level.sendParticles(ParticleTypes.LARGE_SMOKE, cx + offsetX, cy + offsetY, cz + offsetZ, 1, 0.0, 0.0, 0.0, 0.0);
      }
   }
}
