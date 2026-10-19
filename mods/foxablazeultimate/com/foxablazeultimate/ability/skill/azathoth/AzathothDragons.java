package com.foxablazeultimate.ability.skill.azathoth;

import com.foxablazeultimate.config.AzathothConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateDataComponents;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.effect.debuff.SleepEffect;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.entity.human.CloneEntity.CopySkill;
import io.github.manasmods.tensura.entity.projectile.TensuraFlyingProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.FireBoltProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.IceLanceProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.WindBladeProjectile;
import io.github.manasmods.tensura.registry.entity.HumanEntityTypes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.ExistenceStorage;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class AzathothDragons {
   private static final String TYPE = "AzathothDragonType";
   private static final String SUBMODE = "AzathothDragonSubmode";
   private static final String BORN = "AzathothDragonBorn";
   private static final String EXPIRES = "AzathothDragonExpires";
   private static final String ENERGY_INITIALIZED = "AzathothDragonEnergyInitialized";
   private static final ResourceLocation BONUS = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "azathoth_dragon");
   private static final ResourceLocation SLEEP_MODE = ResourceLocation.fromNamespaceAndPath("tensura", "sleep_mode");

   private AzathothDragons() {
   }

   public static void onDragonPressed(ManasSkillInstance instance, LivingEntity owner, int key, int mode) {
      if (owner instanceof Player player && owner.level() instanceof ServerLevel level) {
         CompoundTag tag = instance.getOrCreateTag();
         int type = Math.floorMod(tag.getInt("AzathothDragonSubmode"), 3);
         if (owner.isShiftKeyDown()) {
            type = (type + 1) % 3;
            tag.putInt("AzathothDragonSubmode", type);
            instance.markDirty();
            AzathothSpatial.announceSubMode(
               player, Component.translatable("foxablazeultimate.skill.azathoth.dragon." + new String[]{"scorching", "storm", "frost"}[type])
            );
         } else if (!instance.onCoolDown(mode)) {
            Vec3 position = AzathothSpatial.safePosition(owner, level, owner.position().add(owner.getLookAngle().multiply(2.5, 0.0, 2.5)));
            if (position != null && !EnergyHelper.isOutOfEnergy(owner, instance, mode)) {
               AzathothConfig.Settings c = AzathothConfig.settings();
               List<CloneEntity> existing = AzathothSpatial.ownedClones(owner)
                  .stream()
                  .filter(AzathothDragons::isDragon)
                  .sorted(Comparator.comparingLong(clonex -> clonex.getPersistentData().getLong("AzathothDragonBorn")))
                  .toList();
               int limit = Math.clamp(c.maxDragonAvatars, 1, 16);

               for (int i = 0; i <= existing.size() - limit; i++) {
                  existing.get(i).discard();
               }

               CloneEntity clone = new CloneEntity((EntityType)HumanEntityTypes.CLONE.get(), level);
               clone.tame(player);
               ManasSkillInstance reference = new TensuraSkillInstance(instance.getSkill());
               reference.setMastery(instance.getMastery());
               clone.setSkill(reference);
               clone.copyStatsAndSkills(owner, CopySkill.NONE, true);
               clone.setHealth(clone.getMaxHealth());
               clone.setLife(Math.clamp(c.dragonDurationSeconds, 1, 86400) * 20);
               clone.getPersistentData().putInt("AzathothDragonType", type);
               clone.getPersistentData().putLong("AzathothDragonBorn", level.getServer().overworld().getGameTime());
               clone.getPersistentData().putLong("AzathothDragonExpires", level.getServer().overworld().getGameTime() + clone.getLife());
               IExistence existence = TensuraStorages.getExistenceFrom(clone);
               existence.setSkippingEPDrop(true);
               EnergyHelper.setMaxMagicule(clone, EnergyHelper.getBaseMaxMagicule(owner));
               EnergyHelper.setMaxAura(clone, EnergyHelper.getBaseMaxAura(owner));
               initializeEnergy(clone, owner, false);

               for (EquipmentSlot slot : EquipmentSlot.values()) {
                  ItemStack copy = owner.getItemBySlot(slot).copy();
                  copy.remove((DataComponentType)FoxAblazeUltimateDataComponents.AZATHOTH_CHARGE.get());
                  copy.remove((DataComponentType)FoxAblazeUltimateDataComponents.VELGRYND_CHARGE.get());
                  clone.addFakeItem(slot, copy);
               }

               if (type == 0 && clone.getAttribute(Attributes.ATTACK_SPEED) != null) {
                  clone.getAttribute(Attributes.ATTACK_SPEED)
                     .addOrReplacePermanentModifier(new AttributeModifier(BONUS, Math.max(0.0, c.scorchingAttackSpeedBonus), Operation.ADD_VALUE));
               } else if (type == 1 && clone.getAttribute(Attributes.MOVEMENT_SPEED) != null) {
                  clone.getAttribute(Attributes.MOVEMENT_SPEED)
                     .addOrReplacePermanentModifier(new AttributeModifier(BONUS, Math.max(0.0, c.stormMovementBonus), Operation.ADD_MULTIPLIED_BASE));
               } else if (type == 2 && clone.getAttribute(Attributes.ARMOR) != null) {
                  clone.getAttribute(Attributes.ARMOR)
                     .addOrReplacePermanentModifier(new AttributeModifier(BONUS, Math.max(0.0, c.frostArmorBonus), Operation.ADD_VALUE));
               }

               clone.setPos(position);
               CloneEntity.copyRotation(owner, clone);
               if (level.addFreshEntity(clone)) {
                  instance.setCoolDown(Math.max(0, c.dragonCooldownSeconds), mode);
                  instance.addMasteryPoint(owner);
                  level.sendParticles(
                     type == 0 ? ParticleTypes.FLAME : (type == 1 ? ParticleTypes.CLOUD : ParticleTypes.SNOWFLAKE),
                     position.x,
                     position.y + 1.0,
                     position.z,
                     20,
                     0.4,
                     0.7,
                     0.4,
                     0.02
                  );
               }
            }
         }
      }
   }

   public static boolean isDragon(CloneEntity clone) {
      return clone.getPersistentData().contains("AzathothDragonType");
   }

   public static void onCloneTick(CloneEntity clone, LivingEntity owner, ManasSkillInstance instance) {
      if (isDragon(clone) && clone.level() instanceof ServerLevel level) {
         if (owner.isAlive() && level.getServer().overworld().getGameTime() < clone.getPersistentData().getLong("AzathothDragonExpires")) {
            if (!clone.getPersistentData().getBoolean("AzathothDragonEnergyInitialized")) {
               initializeEnergy(clone, owner, true);
            }

            if (clone.getPose() == Pose.SLEEPING
               && !owner.isSleeping()
               && clone.getSleepingPos().isEmpty()
               && !ExistenceStorage.isInSleepMode(TensuraStorages.getExistenceFrom(clone))
               && !SleepEffect.isSleeping(clone)) {
               clone.setPose(Pose.STANDING);
            }

            if (clone.tickCount % 10 == 0) {
               AzathothConfig.Settings c = AzathothConfig.settings();
               double range = Math.clamp(c.dragonAttackRange, 1.0, 96.0);
               LivingEntity target = owner.getLastHurtByMob();
               if (!validEnemy(owner, clone, target, range)) {
                  target = owner.getLastHurtMob();
               }

               if (!validEnemy(owner, clone, target, range)) {
                  target = clone.getTarget();
               }

               if (!validEnemy(owner, clone, target, range)) {
                  target = level.getEntitiesOfClass(
                        LivingEntity.class,
                        clone.getBoundingBox().inflate(range),
                        enemy -> validEnemy(owner, clone, enemy, range)
                           && (enemy instanceof Enemy || enemy instanceof Mob mob && (mob.getTarget() == owner || mob.getTarget() == clone))
                     )
                     .stream()
                     .min(Comparator.comparingDouble(clone::distanceToSqr))
                     .orElse(null);
               }

               clone.setTarget(target);
               if (target != null && clone.hasLineOfSight(target)) {
                  int type = Math.floorMod(clone.getPersistentData().getInt("AzathothDragonType"), 3);
                  int interval = Math.max(10, c.dragonAttackIntervalTicks);
                  if (type == 0) {
                     interval = Math.max(10, (int)(interval / (1.0 + Math.max(0.0, c.scorchingAttackSpeedBonus))));
                  }

                  long now = level.getGameTime();
                  if (now >= clone.getPersistentData().getLong("AzathothNextAttack")) {
                     clone.getPersistentData().putLong("AzathothNextAttack", now + interval);
                     clone.getLookControl().setLookAt(target, 180.0F, 180.0F);
                     clone.swing(InteractionHand.MAIN_HAND, true);

                     TensuraFlyingProjectile projectile = (TensuraFlyingProjectile)(switch (type) {
                        case 0 -> new FireBoltProjectile(level, clone);
                        case 1 -> new WindBladeProjectile(level, clone);
                        default -> new IceLanceProjectile(level, clone);
                     });
                     float damage = (float)Math.clamp(c.dragonElementalDamage * (type == 0 ? Math.max(0.0, c.scorchingDamageMultiplier) : 1.0), 0.0, 1000000.0);
                     projectile.setDamage(damage);
                     projectile.setSpeed(1.5F);
                     projectile.setNoGravity(true);
                     TensuraSkillInstance attackInstance = new TensuraSkillInstance(instance.getSkill());
                     attackInstance.setMastery(instance.getMastery());
                     projectile.setSkill(clone, attackInstance, (TensuraSkill)FoxAblazeUltimateSkills.AZATHOTH.get(), 4);
                     Vec3 direction = target.getEyePosition().subtract(clone.getEyePosition()).normalize();
                     projectile.setPos(clone.getEyePosition().add(direction.scale(0.7)));
                     projectile.shootFromRot(direction);
                     level.addFreshEntity(projectile);
                     if (type == 1 || type == 2) {
                        double radius = type == 1 ? Math.clamp(c.stormAreaRadius, 0.0, 16.0) : 3.0;

                        for (LivingEntity victim : level.getEntitiesOfClass(
                           LivingEntity.class,
                           target.getBoundingBox().inflate(radius),
                           enemy -> validEnemy(owner, clone, enemy, range) && clone.hasLineOfSight(enemy)
                        )) {
                           if (type == 1) {
                              DamageSource source = ((AzathothSkill)FoxAblazeUltimateSkills.AZATHOTH.get())
                                 .createSource(instance, clone, TensuraDamageTypes.LIGHTNING_ELEMENTAL, 4);
                              victim.hurt(source, damage * 0.35F);
                              level.sendParticles(ParticleTypes.ELECTRIC_SPARK, victim.getX(), victim.getY() + 1.0, victim.getZ(), 6, 0.3, 0.5, 0.3, 0.1);
                           } else {
                              victim.addEffect(
                                 new MobEffectInstance(
                                    MobEffects.MOVEMENT_SLOWDOWN, Math.clamp(c.frostSlowSeconds, 1, 600) * 20, Math.clamp(c.frostSlowAmplifier, 0, 10)
                                 ),
                                 clone
                              );
                           }
                        }
                     }
                  }
               }
            }
         } else {
            clone.discard();
         }
      }
   }

   private static void initializeEnergy(CloneEntity clone, LivingEntity owner, boolean legacy) {
      IExistence existence = TensuraStorages.getExistenceFrom(clone);
      IExistence ownerExistence = TensuraStorages.getExistenceFrom(owner);
      double maximum = EnergyHelper.getMaxMagicule(clone);
      double available = ownerExistence.getMagicule();
      double initial = Double.isFinite(maximum) && maximum > 0.0 ? Math.min(maximum, Math.max(1.0, Double.isFinite(available) ? available : 1.0)) : 0.0;
      if (!legacy || !Double.isFinite(existence.getMagicule()) || existence.getMagicule() <= 0.0) {
         existence.setMagicule(initial);
      }

      if (!legacy) {
         double maxAura = EnergyHelper.getMaxAura(clone);
         double ownerAura = ownerExistence.getAura();
         existence.setAura(Double.isFinite(maxAura) && Double.isFinite(ownerAura) ? Math.max(0.0, Math.min(maxAura, ownerAura)) : 0.0);
      }

      if (legacy && existence.getMagicule() > 0.0 && existence.getHarvestTick() <= 0 && existence.getHarvestGiftTick() <= 0) {
         existence.setSleepModeTime(-1);
         AttributeHelper.removeSleepModeAttribute(clone, SLEEP_MODE);
      }

      clone.getPersistentData().putBoolean("AzathothDragonEnergyInitialized", true);
      existence.markDirty();
   }

   private static boolean validEnemy(LivingEntity owner, CloneEntity clone, LivingEntity target, double range) {
      return target != null
         && target.isAlive()
         && !target.isSpectator()
         && target != owner
         && target != clone
         && target.level() == clone.level()
         && clone.distanceToSqr(target) <= range * range
         && !target.isAlliedTo(owner)
         && !owner.isAlliedTo(target)
         && !(target instanceof TamableAnimal tame && owner.getUUID().equals(tame.getOwnerUUID()))
         && !(target instanceof Player p && p.getAbilities().invulnerable);
   }

   @SubscribeEvent
   public static void onFrostDamage(LivingIncomingDamageEvent event) {
      if (event.getEntity() instanceof CloneEntity clone && isDragon(clone) && clone.getPersistentData().getInt("AzathothDragonType") == 2) {
         event.setAmount(event.getAmount() * (float)(1.0 - Math.clamp(AzathothConfig.settings().frostDamageReduction, 0.0, 1.0)));
      }
   }

   public static void recallAll(ManasSkillInstance instance, LivingEntity owner) {
      for (CloneEntity clone : AzathothSpatial.ownedClones(owner)) {
         if (isDragon(clone)) {
            clone.discard();
         }
      }
   }
}
