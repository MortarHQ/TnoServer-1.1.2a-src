package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastStage;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber
public class ThermoseismicHeart extends CyberwareBase {
   public ThermoseismicHeart(Properties properties) {
      super(properties);
   }

   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("pyromania")
                     .stat(
                        StatData.builder("duration")
                           .initialValue(20.0, 30.0)
                           .upgradeModifier(UpgradeOperation.ADD, 5.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(3)
                     .requiredLevel(0)
                     .build()
               )
               .ability(
                  AbilityData.builder("thermal_discharge")
                     .stat(
                        StatData.builder("radius")
                           .initialValue(1.0, 2.0)
                           .upgradeModifier(UpgradeOperation.ADD, 0.25)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("damage")
                           .initialValue(3.0, 5.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.25)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(4)
                     .active(CastData.builder().type(CastType.INSTANTANEOUS).build())
                     .requiredLevel(3)
                     .build()
               )
               .ability(
                  AbilityData.builder("cyberpsychosis")
                     .stat(
                        StatData.builder("chance")
                           .initialValue(3.0, 5.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("duration")
                           .initialValue(4.0, 7.0)
                           .upgradeModifier(UpgradeOperation.ADD, 2.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(3)
                     .requiredLevel(7)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(10)
               .initialCost(100)
               .step(25)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("thermal_discharge").build())
                     .source(LevelingSourceData.abilityBuilder("pyromania").build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.DESERT}).build())
         .build();
   }

   @SubscribeEvent
   public static void onDamageTaken(LivingIncomingDamageEvent event) {
      LivingEntity player = event.getEntity();
      if (!player.level().isClientSide) {
         if (event.getSource().typeHolder().getKey() == DamageTypes.EXPLOSION || event.getSource().typeHolder().getKey() == DamageTypes.PLAYER_EXPLOSION) {
            double overloadTime = EntityUtils.findEquippedCurios(player, (Item)MoreRelics.THERMOSEISMIC_HEART.get()).stream().mapToDouble(stack -> {
               ThermoseismicHeart item = (ThermoseismicHeart)stack.getItem();
               if (!item.isAbilityUnlocked(stack, "pyromania")) {
                  return 0.0;
               }

               item.spreadRelicExperience(player, stack, 20);
               return item.getStatValue(stack, "pyromania", "duration");
            }).sum();
            if (overloadTime >= 0.5) {
               player.invulnerableTime = 20;
               player.hurtDuration = 10;
               player.hurtTime = player.hurtDuration;
               MobEffectInstance current = player.getEffect(MoreRelics.OVERLOAD.getDelegate());
               if (current == null) {
                  player.addEffect(new MobEffectInstance(MoreRelics.OVERLOAD.getDelegate(), (int)Math.round(overloadTime * 20.0), 0));
               } else {
                  int currentAmplifier = current.getAmplifier();
                  if (currentAmplifier < 3) {
                     player.addEffect(new MobEffectInstance(MoreRelics.OVERLOAD.getDelegate(), (int)Math.round(overloadTime * 20.0), currentAmplifier + 1));
                  } else {
                     player.addEffect(new MobEffectInstance(MoreRelics.OVERLOAD.getDelegate(), (int)Math.round(overloadTime * 20.0), 3));
                  }
               }

               event.setCanceled(true);
            }
         }
      }
   }

   public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
      if (ability.equals("thermal_discharge")) {
         if (player.level().isClientSide) {
            return;
         }

         MobEffectInstance current = player.getEffect(MoreRelics.OVERLOAD.getDelegate());
         int dischargeLevel = current == null ? 0 : current.getAmplifier() + 1;
         boolean playerHasPsychosis = player.hasEffect(MoreRelics.CYBERPSYCHOSIS.getDelegate());
         Level level = player.level();
         if (dischargeLevel < 2 && playerHasPsychosis) {
            dischargeLevel = 2;
         }

         Vec3 center = player.position();
         double radius = this.getStatValue(stack, "thermal_discharge", "radius") * (dischargeLevel * 0.5F + 1.0F);
         float damage = (float)this.getStatValue(stack, "thermal_discharge", "damage") * (dischargeLevel * 0.5F + 1.0F);
         AABB area = new AABB(center.x - radius, center.y - radius, center.z - radius, center.x + radius, center.y + radius, center.z + radius);
         List<Entity> detectedEntities = level.getEntities(player, area);
         this.spawnFireParticles(level, center, dischargeLevel, (float)radius);

         for (Entity detected : detectedEntities) {
            if (detected instanceof LivingEntity livingEntity && player != detected) {
               Vec3 direction = livingEntity.position().subtract(center).normalize();
               double pushStrength = (dischargeLevel + 1) / 4.0F;
               livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(direction.scale(pushStrength)));
               livingEntity.hurt(new DamageSource(livingEntity.damageSources().generic().typeHolder(), player, player), damage);
               livingEntity.setRemainingFireTicks(20 * (int)(6.0F * ((dischargeLevel + 1) / 2.0F)));
            }
         }

         this.setAbilityCooldown(stack, "thermal_discharge", 300);
         this.spreadRelicExperience(player, stack, 10);
         if (!playerHasPsychosis) {
            player.removeEffect(MoreRelics.OVERLOAD.getDelegate());
         }
      }
   }

   private void spawnFireParticles(Level level, Vec3 position, int amplifier, float radius) {
      int particleCount = 30 + amplifier * 60;
      if (particleCount > 400) {
         particleCount = 400;
      }

      level.playSound(null, position.x, position.y, position.z, SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.MASTER, 0.6F, 1.0F);
      ServerLevel sevLevel = (ServerLevel)level;

      for (ServerPlayer player : level.players()) {
         sevLevel.sendParticles(player, ParticleTypes.FLAME, true, position.x, position.y + 1.0, position.z, particleCount, 0.0, 0.0, 0.0, 0.025 * radius);
      }
   }
}
