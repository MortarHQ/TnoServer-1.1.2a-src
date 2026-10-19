package com.mongoose.royalvariations.common.events;

import com.mongoose.royalvariations.client.particles.DTParticleOption;
import com.mongoose.royalvariations.client.particles.MarkParticleOption;
import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.client.particles.RoyalExplosionParticleOption;
import com.mongoose.royalvariations.client.particles.RoyalGlowParticleOption;
import com.mongoose.royalvariations.common.attachments.RVCapHelper;
import com.mongoose.royalvariations.common.effects.RVEffects;
import com.mongoose.royalvariations.common.entities.ai.ChosenVictimGoal;
import com.mongoose.royalvariations.common.entities.ai.GazeGoal;
import com.mongoose.royalvariations.common.entities.hostile.RoyalCreeper;
import com.mongoose.royalvariations.common.entities.neutral.RoyalEnderman;
import com.mongoose.royalvariations.common.entities.projectile.SpectralAnvil;
import com.mongoose.royalvariations.common.items.RVItems;
import com.mongoose.royalvariations.common.items.armor.RVArmorMaterials;
import com.mongoose.royalvariations.common.network.server.SRemoveEffectPacket;
import com.mongoose.royalvariations.config.RVConfig;
import com.mongoose.royalvariations.utils.CuriosFinder;
import com.mongoose.royalvariations.utils.CustomExplosion;
import com.mongoose.royalvariations.utils.EventTask;
import com.mongoose.royalvariations.utils.ItemHelper;
import com.mongoose.royalvariations.utils.MobUtil;
import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent.PositionCheck;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent.PositionCheck.Result;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Pre;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = "royalvariations")
public class RVEvents {
   @SubscribeEvent
   public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
      Entity entity = event.getEntity();
      Level world = event.getLevel();
      if (entity instanceof LivingEntity && !world.isClientSide() && entity instanceof Mob mob) {
         if (mob instanceof EnderMan enderMan) {
            enderMan.targetSelector.addGoal(0, new GazeGoal<LivingEntity>(mob, LivingEntity.class));
         }

         if (mob.getType().is(EntityTypeTags.UNDEAD) && !(mob instanceof OwnableEntity ownable && ownable.getOwner() != null)) {
            mob.targetSelector.addGoal(0, new ChosenVictimGoal<LivingEntity>(mob, LivingEntity.class));
         }
      }
   }

   @SubscribeEvent
   public static void LivingEffects(Pre event) {
      if (event.getEntity() instanceof LivingEntity livingEntity && !livingEntity.level().isClientSide) {
         if (RVCapHelper.isFlashing(livingEntity)) {
            RVCapHelper.decreaseFlashTick(livingEntity);
         }

         if (livingEntity.hasEffect(RVEffects.MARKED)) {
            RVCapHelper.setMarkedTick(livingEntity, 5);
         } else if (RVCapHelper.isMarked(livingEntity)) {
            RVCapHelper.setMarkedTick(livingEntity, 0);
         }

         if (livingEntity.hasEffect(RVEffects.PRESSING_GAZE)) {
            RVCapHelper.setGazeTick(livingEntity, 5);
         } else if (RVCapHelper.isGazed(livingEntity)) {
            RVCapHelper.setGazeTick(livingEntity, 0);
         }

         if (livingEntity instanceof AbstractSkeleton skeleton && skeleton.getTarget() != null && skeleton.getTarget().isDeadOrDying()) {
            skeleton.setTarget(null);
         }
      }
   }

   @SubscribeEvent
   public static void LivingTargetEvent(LivingChangeTargetEvent event) {
      LivingEntity attacker = event.getEntity();
      LivingEntity target = event.getOriginalAboutToBeSetTarget();
      if (attacker instanceof IronGolem && target instanceof RoyalCreeper) {
         event.setNewAboutToBeSetTarget(null);
      }
   }

   @SubscribeEvent
   public static void LivingAttackEffects(LivingIncomingDamageEvent event) {
      LivingEntity livingEntity = event.getEntity();
      Level level = event.getEntity().level();
      if (livingEntity.isAlive() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
         if (ItemHelper.armorSet(livingEntity, (ArmorMaterial)RVArmorMaterials.ROYAL_KNIGHT.value())
            && livingEntity.getHealth() <= livingEntity.getMaxHealth() * 0.4
            && !livingEntity.hasEffect(RVEffects.KNIGHTLY_FORTITUDE)) {
            if (level instanceof ServerLevel serverLevel) {
               livingEntity.addEffect(new MobEffectInstance(RVEffects.KNIGHTLY_FORTITUDE, 400, 0));
               serverLevel.sendParticles(
                  (SimpleParticleType)RVParticleTypes.AURA_OF_PROTECTION.get(),
                  livingEntity.getX(),
                  livingEntity.getY() + 1.4F,
                  livingEntity.getZ(),
                  Mth.nextInt(livingEntity.getRandom(), 3, 6),
                  0.3,
                  0.35,
                  0.3,
                  0.1
               );
            }

            for (Entity entity : level.getEntitiesOfClass(Entity.class, new AABB(livingEntity.position(), livingEntity.position()).inflate(5.5))) {
               if (entity instanceof LivingEntity living
                  && living instanceof OwnableEntity ownable
                  && ownable.getOwner() == livingEntity
                  && level instanceof ServerLevel serverLevel) {
                  living.addEffect(new MobEffectInstance(RVEffects.KNIGHTLY_FORTITUDE, 400, 1));
                  serverLevel.sendParticles(
                     (SimpleParticleType)RVParticleTypes.AURA_OF_PROTECTION.get(),
                     entity.getX(),
                     entity.getY() + 1.0,
                     entity.getZ(),
                     Mth.nextInt(living.getRandom(), 3, 5),
                     0.2,
                     0.3,
                     0.2,
                     0.1
                  );
               }
            }

            if (!level.isClientSide()) {
               level.playSound(null, livingEntity.blockPosition(), SoundEvents.ANVIL_PLACE, SoundSource.NEUTRAL, 0.6F, 0.9F);
            } else {
               level.playLocalSound(
                  livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.ANVIL_PLACE, SoundSource.NEUTRAL, 0.6F, 0.9F, false
               );
            }
         }

         if (event.getSource().getEntity() instanceof Player player
            && CuriosFinder.hasCurio(player, (Item)RVItems.SPECTRAL_GAUNTLET.get())
            && !MobUtil.areAllies(player, livingEntity)
            && !player.getCooldowns().isOnCooldown((Item)RVItems.SPECTRAL_GAUNTLET.get())
            && level.getRandom().nextFloat() <= 0.15F) {
            SpectralAnvil anvil = new SpectralAnvil(level, player);
            float height = Math.max(player.getBbHeight(), livingEntity.getBbHeight());
            anvil.setPos(livingEntity.position().add(0.0, height + 1.0F, 0.0));
            if (level.addFreshEntity(anvil)) {
               player.getCooldowns().addCooldown((Item)RVItems.SPECTRAL_GAUNTLET.get(), 140);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onLivingFall(LivingFallEvent event) {
      if (!event.getEntity().level().isClientSide && event.getEntity().hasEffect(RVEffects.HEAVINESS_OF_THE_END)) {
         event.setDamageMultiplier(event.getDamageMultiplier() + 0.2F);
      }
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      if (!event.getEntity().level().isClientSide && event.getEntity().hasEffect(RVEffects.TRAPPED)) {
         event.getEntity().removeEffect(RVEffects.TRAPPED);
         PacketDistributor.sendToPlayersTrackingEntityAndSelf(
            event.getEntity(), new SRemoveEffectPacket(event.getEntity().getId(), RVEffects.TRAPPED), new CustomPacketPayload[0]
         );
      }
   }

   @SubscribeEvent
   public static void DropEvents(LivingDropsEvent event) {
      Entity source = event.getSource().getEntity();
      int loot = 0;
      if (source instanceof LivingEntity livingEntity) {
         Holder<Enchantment> looting = livingEntity.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.LOOTING);
         loot = EnchantmentHelper.getEnchantmentLevel(looting, livingEntity);
      }

      if (event.getEntity() instanceof Zombie zombie && zombie.getItemBySlot(EquipmentSlot.HEAD).is((Item)RVItems.ROYAL_KNIGHT_HELMET.get())) {
         float chance = 0.1F;
         chance += loot / 100.0F;
         if (zombie.level().random.nextFloat() <= chance) {
            event.getDrops().add(ItemHelper.itemEntityDrop(zombie, new ItemStack((ItemLike)RVItems.ROYAL_ARMOR_FRAGMENTS.get())));
         }
      }

      if (event.getSource().getEntity() instanceof RoyalCreeper creeper && creeper.canDropMobsSkull()) {
         if (event.getEntity() instanceof Creeper creeper1) {
            creeper.increaseDroppedSkulls();
            creeper1.spawnAtLocation(Items.CREEPER_HEAD);
         } else if (event.getEntity() instanceof Skeleton skeleton) {
            creeper.increaseDroppedSkulls();
            skeleton.spawnAtLocation(Items.SKELETON_SKULL);
         } else if (event.getEntity() instanceof Zombie zombie) {
            creeper.increaseDroppedSkulls();
            zombie.spawnAtLocation(Items.ZOMBIE_HEAD);
         } else if (event.getEntity() instanceof WitherSkeleton skeleton) {
            creeper.increaseDroppedSkulls();
            skeleton.spawnAtLocation(Items.WITHER_SKELETON_SKULL);
         } else if (event.getEntity() instanceof Piglin piglin) {
            creeper.increaseDroppedSkulls();
            piglin.spawnAtLocation(Items.PIGLIN_HEAD);
         }
      }
   }

   @SubscribeEvent
   public static void onKnockback(LivingKnockBackEvent event) {
      if (event.getEntity().hasEffect(RVEffects.TRAPPED)) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onMobSpawn(PositionCheck event) {
      if (event.getEntity() instanceof RoyalEnderman
         && event.getLevel().getLevel().dimension() == Level.END
         && (event.getSpawnType() == MobSpawnType.NATURAL || event.getSpawnType() == MobSpawnType.CHUNK_GENERATION)
         && event.getLevel().getRandom().nextFloat() > ((Integer)RVConfig.RoyalEndermanEndSpawnChance.get()).intValue() * 0.01F) {
         event.setResult(Result.FAIL);
      }
   }

   @SubscribeEvent
   public static void PotionApplyEvents(Applicable event) {
      LivingEntity livingEntity = event.getEntity();
      MobEffectInstance instance = event.getEffectInstance();
      if (instance != null) {
         if (instance.getEffect() == RVEffects.UNDEAD_RUSH && !livingEntity.getType().is(EntityTypeTags.UNDEAD)) {
            event.setResult(net.neoforged.neoforge.event.entity.living.MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
         }

         if (instance.getEffect() == RVEffects.DAZED && livingEntity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
               (SimpleParticleType)RVParticleTypes.DAZED_STAR.get(),
               livingEntity.getX(),
               livingEntity.getY() + 1.6,
               livingEntity.getZ(),
               serverLevel.getRandom().nextIntBetweenInclusive(4, 6),
               0.35,
               0.2,
               0.35,
               0.2
            );
         }

         if (instance.getEffect() == RVEffects.MARKED
            && livingEntity.level() instanceof ServerLevel serverLevel
            && livingEntity.isAlive()
            && !livingEntity.hasEffect(RVEffects.MARKED)) {
            serverLevel.sendParticles(
               new MarkParticleOption(livingEntity.getId()),
               livingEntity.getX(),
               livingEntity.getY() + livingEntity.getBbHeight() + 1.0,
               livingEntity.getZ(),
               1,
               0.0,
               0.0,
               0.0,
               1.0
            );
            serverLevel.playSound(
               null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.ARROW_HIT_PLAYER, livingEntity.getSoundSource(), 1.0F, 0.8F
            );
         }

         if (instance.getEffect() == RVEffects.PRESSING_GAZE
            && livingEntity.level() instanceof ServerLevel
            && livingEntity.isAlive()
            && !livingEntity.hasEffect(RVEffects.PRESSING_GAZE)) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 80, 0, false, false));
         }

         if (instance.getEffect() == RVEffects.TIME_BOMB
            && livingEntity.level() instanceof ServerLevel serverLevel
            && livingEntity.isAlive()
            && !livingEntity.hasEffect(RVEffects.TIME_BOMB)) {
            serverLevel.sendParticles(
               new DTParticleOption(livingEntity.getId()),
               livingEntity.getX(),
               livingEntity.getY() + livingEntity.getBbHeight() + 1.0,
               livingEntity.getZ(),
               1,
               0.0,
               0.0,
               0.0,
               1.0
            );
            TimedEvents.submitTask("royalvariations:time_bomb", new RVEvents.TimeBombTask(livingEntity.getUUID(), serverLevel));
         }
      }
   }

   public static class TimeBombTask implements EventTask {
      public static int TIME = 83;
      public UUID entity;
      public ServerLevel level;
      public int tick;

      public TimeBombTask(UUID entity, ServerLevel level) {
         this.entity = entity;
         this.level = level;
      }

      @Override
      public void startTask() {
         this.tick = 0;
      }

      @Override
      public void tickTask() {
         this.tick++;
         if (this.level.getEntity(this.entity) instanceof LivingEntity livingEntity && this.tick == TIME - 10) {
            RVCapHelper.setFlashTick(livingEntity, 10);
         }
      }

      @Override
      public boolean getAsBoolean() {
         boolean flag = false;
         if (this.level.getEntity(this.entity) instanceof LivingEntity livingEntity && livingEntity.hasEffect(RVEffects.TIME_BOMB)) {
            if (this.tick >= TIME) {
               flag = true;
            }

            if (flag) {
               this.level
                  .sendParticles(
                     new RoyalExplosionParticleOption(3.0F),
                     livingEntity.getX(),
                     livingEntity.getY() + livingEntity.getBbHeight() / 2.0,
                     livingEntity.getZ(),
                     1,
                     0.0,
                     0.0,
                     0.0,
                     0.0
                  );
               this.level
                  .sendParticles(
                     new RoyalGlowParticleOption(1.0F, 1.0F, 1.0F, 6.0F, 2),
                     livingEntity.getX(),
                     livingEntity.getY() + livingEntity.getBbHeight() / 2.0,
                     livingEntity.getZ(),
                     1,
                     0.0,
                     0.0,
                     0.0,
                     0.0
                  );
               this.level
                  .playSound(
                     null,
                     livingEntity.getX(),
                     livingEntity.getY(),
                     livingEntity.getZ(),
                     SoundEvents.GENERIC_EXPLODE,
                     livingEntity.getSoundSource(),
                     4.0F,
                     (1.0F + (this.level.getRandom().nextFloat() - this.level.getRandom().nextFloat()) * 0.2F) * 0.7F
                  );
               new CustomExplosion(
                  this.level,
                  livingEntity,
                  this.level.damageSources().explosion(null),
                  livingEntity.getX(),
                  livingEntity.getY(),
                  livingEntity.getZ(),
                  6.0F,
                  9.0F
               ) {
                  @Override
                  public void explodeHurt(Entity target, DamageSource damageSource, double x, double y, double z, double seen, float actualDamage) {
                     if (EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target) && !(target instanceof RoyalCreeper)) {
                        super.explodeHurt(target, damageSource, x, y, z, seen, actualDamage);
                        if (MobUtil.notMonsterOrAnimal(target) && target instanceof LivingEntity living) {
                           living.addEffect(new MobEffectInstance(RVEffects.DAZED, 260));
                        }
                     }
                  }
               };
               livingEntity.removeEffect(RVEffects.TIME_BOMB);
            }
         } else {
            flag = true;
         }

         return flag;
      }
   }
}
