package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.entity.projectile.KunaiProjectile;
import io.github.manasmods.tensura.entity.projectile.SpearProjectile;
import io.github.manasmods.tensura.entity.projectile.ThrownItemProjectile;
import io.github.manasmods.tensura.entity.projectile.WebBulletProjectile;
import io.github.manasmods.tensura.item.weapon.SimpleSpearItem;
import io.github.manasmods.tensura.item.weapon.ranged.KunaiItem;
import io.github.manasmods.tensura.item.weapon.ranged.WebCartridgeItem;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.ExperienceBottleItem;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class MelancholySkill extends Skill {
   protected static final UniqueMysticismSkillConfig.Melancholy CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class))
      .Melancholy;

   public MelancholySkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/melancholy.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return CONFIG.mpAcquirement;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return SkillUtils.isSkillMastered(entity, (ManasSkill)UniqueSkills.THROWER.get())
         && SkillUtils.isSkillMastered(entity, (ManasSkill)ExtraSkills.MOLECULAR_MANIPULATION.get());
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return true;
   }

   public int getModes(ManasSkillInstance instance) {
      return 3;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "melancholy.gust";
         case 1 -> "melancholy.turbulent_force";
         case 2 -> "melancholy.grief";
         default -> super.getModeId(instance, mode);
      };
   }

   public int getMaxMastery() {
      return 1500;
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return -1;
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      instance.setCoolDownList(NonNullList.withSize(this.getModes(instance) + 1, 0));
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode == 0 ? 2 : mode - 1;
      } else {
         return (mode + 1) % this.getModes(instance);
      }
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled();
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      List<LivingEntity> list = entity.level()
         .getEntitiesOfClass(
            LivingEntity.class,
            entity.getBoundingBox().inflate(instance.isMastered(entity) ? CONFIG.ishaRangeMastered : CONFIG.ishaRange),
            living -> !living.is(entity) && living.isAlive()
         );
      if (!list.isEmpty()) {
         int level = CONFIG.ishaLevel - 1;

         for (LivingEntity target : list) {
            TensuraMobEffect.addEffect(target, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, level, false, false, false), entity, this);
         }
      }

      int level = CONFIG.ishaLevelUser - 1;
      int resistLevel = CONFIG.ishaResistLevel - 1;
      TensuraMobEffect.addEffect(entity, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, level, false, false, false), entity, this);
      TensuraMobEffect.addEffect(entity, new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, resistLevel, false, false, false), entity, this);
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      if (instance.onCoolDown(this.getModes(instance) + 1) && entity.getHealth() <= entity.getMaxHealth() * 0.25) {
         entity.level().playSound(null, entity.blockPosition(), SoundEvents.RAVAGER_ROAR, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
         double knockbackRadius = CONFIG.veilKnockbackRadius;
         Vec3 knockbackPosition = entity.position();

         for (LivingEntity target : entity.level()
            .getEntitiesOfClass(
               LivingEntity.class,
               new AABB(
                  knockbackPosition.subtract(knockbackRadius, knockbackRadius, knockbackRadius),
                  knockbackPosition.add(knockbackRadius, knockbackRadius, knockbackRadius)
               ),
               e -> e != entity && e.isAlive()
            )) {
            Vec3 knockback = target.position().subtract(entity.position()).normalize().scale(5.0);
            target.setDeltaMovement(knockback);
            target.hasImpulse = true;
            int burdenLevel = CONFIG.veilBurdenLevel - 1;
            int slownessLevel = CONFIG.veilSlownessLevel - 1;
            TensuraMobEffect.addEffect(target, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, slownessLevel), entity, this);
            TensuraMobEffect.addEffect(
               target, new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN), 600, burdenLevel, false, false, false), entity, this
            );
         }

         instance.setCoolDown(300, this.getModes(instance) + 1);
      }

      return true;
   }

   public boolean canScroll(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return mode == 1;
   }

   public void onScroll(ManasSkillInstance instance, LivingEntity entity, double delta, int mode) {
      CompoundTag tag = instance.getOrCreateTag();
      double newRange = tag.getDouble("range") + delta;
      double maxRange = instance.isMastered(entity) ? CONFIG.maxRange : CONFIG.defaultRange;
      if (newRange > maxRange) {
         newRange = maxRange;
      } else if (newRange < 3.0) {
         newRange = 3.0;
      }

      if (tag.getDouble("range") != newRange) {
         tag.putDouble("range", newRange);
         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensura.skill.range", new Object[]{newRange}).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_AQUA)), true
            );
         }

         instance.markDirty();
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (mode == 1) {
         if (entity.level() instanceof ServerLevel level) {
            CompoundTag tag = instance.getOrCreateTag();
            double range = tag.getDouble("range");
            if (entity instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("tensura.skill.range", new Object[]{range}).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_AQUA)), true
               );
            }

            if (tag.contains("target")) {
               Entity target = level.getEntity(tag.getUUID("target"));
               if (target != null) {
                  Vec3 viewVector = entity.getViewVector(1.0F).scale(range);
                  double x = entity.getX() + viewVector.x;
                  double y = entity.getEyeY() + viewVector.y;
                  double z = entity.getZ() + viewVector.z;
                  Vec3 targetPos = new Vec3(x, y, z);
                  if (targetPos.distanceTo(target.position()) <= 6.0) {
                     if (heldTicks % 60 == 0 && heldTicks > 0) {
                        this.addMasteryPoint(instance, entity);
                     }

                     TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.SWEEP_ATTACK, 1.0);
                     Vec3 vec3 = targetPos.subtract(target.position()).normalize().scale(0.5);
                     if (vec3.length() > 0.2) {
                        target.setDeltaMovement(vec3);
                     }

                     target.hurtMarked = true;
                     target.resetFallDistance();
                  }
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void onNumberKeyPress(ManasSkillInstance instance, Player player, int keyNumber) {
      double delta = 0.0;
      if (keyNumber == 7) {
         delta = -1.0;
      }

      if (keyNumber == 8) {
         delta = 1.0;
      }

      if (delta != 0.0) {
         double r = CONFIG.defaultRange + delta;
         if (r > 50.0) {
            r = 50.0;
         } else if (r < 0.0) {
            r = 0.0;
         }

         instance.getOrCreateTag().putDouble("range", r);
         instance.markDirty();
         if (player != null) {
            player.displayClientMessage(Component.translatable("mysticism.skill.melancholy.range", new Object[]{String.format("%.1f", r)}), true);
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         instance.addMasteryPoint(entity);
         Level level = entity.level();
         double range = instance.getOrCreateTag().getDouble("range");
         if (range <= 0.0) {
            range = 15.0;
         }

         if (mode == 0) {
            LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, range, false);
            if (target != null) {
               boolean pull = entity.isShiftKeyDown();
               double scale = pull ? CONFIG.pullEntity : CONFIG.pushEntity;
               if (SkillUtils.isSkillToggled(entity, (ManasSkill)ExtraSkills.GRAVITY_DOMINATION.get())) {
                  scale += pull ? CONFIG.pullEntityDomination : CONFIG.pushEntityDomination;
               } else if (SkillUtils.isSkillToggled(entity, (ManasSkill)ExtraSkills.GRAVITY_MANIPULATION.get())) {
                  scale += pull ? CONFIG.pullEntityManipulation : CONFIG.pushEntityManipulation;
               }

               entity.swing(InteractionHand.MAIN_HAND, true);
               Vec3 dir = pull
                  ? new Vec3(entity.getX() - target.getX(), entity.getY() - target.getY() + 0.5, entity.getZ() - target.getZ())
                  : new Vec3(target.getX() - entity.getX(), target.getY() - entity.getY() + 0.5, target.getZ() - entity.getZ());
               double len = Math.max(dir.length(), 1.0E-4);
               target.setDeltaMovement(dir.scale(1.0 / len).normalize().scale(scale));
               target.hasImpulse = true;
               target.hurtMarked = true;
               level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               return;
            }

            this.fireSingleProjectile(instance, entity, level, instance.isMastered(entity));
            entity.swing(InteractionHand.MAIN_HAND, true);
         } else if (mode == 1) {
            Entity target = ObjectSelectionHelper.getTargetingEntity(entity, 15.0, false, true);
            if (target == null) {
               return;
            }

            if (target.getType().is(TensuraEntityTags.FULL_GRAVITY_CONTROL)) {
               return;
            }

            if (!(entity instanceof Player player && player.getAbilities().invulnerable)
               && target instanceof Player player
               && player.getAbilities().invulnerable) {
               return;
            }

            double maxSize = instance.isMastered(entity) ? 15.0 : 10.0;
            if (target.getBoundingBox().getSize() > maxSize) {
               return;
            }

            CompoundTag tag = instance.getOrCreateTag();
            tag.putUUID("target", target.getUUID());
            double rangeT = Math.min(50.0, target.position().subtract(entity.getEyePosition()).length());
            tag.putDouble("range", rangeT);
         } else if (mode == 2) {
            entity.level().playSound(null, entity.blockPosition(), SoundEvents.RAVAGER_ROAR, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            double knockbackRadius = CONFIG.griefKnockbackRadius;
            Vec3 knockbackPosition = entity.position();

            for (LivingEntity target : entity.level()
               .getEntitiesOfClass(
                  LivingEntity.class,
                  new AABB(
                     knockbackPosition.subtract(knockbackRadius, knockbackRadius, knockbackRadius),
                     knockbackPosition.add(knockbackRadius, knockbackRadius, knockbackRadius)
                  ),
                  e -> e != entity && e.isAlive()
               )) {
               Vec3 knockback = target.position().subtract(entity.position()).normalize().scale(5.0);
               target.setDeltaMovement(knockback);
               target.hasImpulse = true;
               int levelEffect = CONFIG.griefEffectLevel - 1;
               TensuraMobEffect.addEffect(target, new MobEffectInstance(TensuraMobEffects.TRUE_BLINDNESS, 200, levelEffect), entity, this, mode);
               TensuraMobEffect.addEffect(target, new MobEffectInstance(MobEffects.WEAKNESS, 200, levelEffect), entity, this, mode);
            }

            instance.setCoolDown(10, 2);
         }
      }
   }

   private void fireSingleProjectile(ManasSkillInstance instance, LivingEntity entity, Level level, boolean mastered) {
      ItemStack hand = entity.getMainHandItem();
      ItemStack thrownStack = hand.copy();
      thrownStack.setCount(1);
      Projectile proj = getProjectile(level, entity, thrownStack, instance);
      Vec3 look = entity.getLookAngle();
      float speed = mastered ? 3.25F : 2.5F;
      proj.shoot(look.x, look.y, look.z, speed, 0.0F);
      level.addFreshEntity(proj);
      if (!entity.hasInfiniteMaterials() && !hand.isEmpty()) {
         hand.shrink(1);
      }
   }

   public static Projectile getProjectile(Level level, LivingEntity entity, ItemStack stack, @Nullable ManasSkillInstance instance) {
      Item item = stack.getItem();
      if (item instanceof ArrowItem arrowItem) {
         AbstractArrow arrow = arrowItem.createArrow(level, stack, entity, null);
         double base = arrow.getBaseDamage() + (instance != null && instance.isMastered(entity) ? CONFIG.itemThrowDamageMastered : CONFIG.itemThrowDamage);
         if (SkillUtils.isSkillToggled(entity, (ManasSkill)ExtraSkills.GRAVITY_DOMINATION.get())) {
            base *= CONFIG.itemThrowDomination;
         } else if (SkillUtils.isSkillToggled(entity, (ManasSkill)ExtraSkills.GRAVITY_MANIPULATION.get())) {
            base *= CONFIG.itemThrowManipulation;
         }

         arrow.pickup = entity.hasInfiniteMaterials() ? Pickup.CREATIVE_ONLY : Pickup.ALLOWED;
         arrow.setBaseDamage(base);
         return arrow;
      } else {
         if (item instanceof ExperienceBottleItem) {
            return new ThrownExperienceBottle(level, entity);
         }

         if (item instanceof EggItem) {
            return new ThrownEgg(level, entity);
         }

         if (item instanceof EnderpearlItem) {
            return new ThrownEnderpearl(level, entity);
         }

         if (item instanceof SnowballItem) {
            return new Snowball(level, entity);
         }

         if (item instanceof FireworkRocketItem) {
            return new FireworkRocketEntity(level, stack, entity, entity.getX(), entity.getY(), entity.getZ(), true);
         }

         if (item instanceof ThrowablePotionItem) {
            return new ThrownPotion(level, entity);
         }

         if (item instanceof WebCartridgeItem) {
            return new WebBulletProjectile(level, entity, true, stack, stack);
         }

         if (item instanceof TridentItem) {
            ThrownTrident trident = new ThrownTrident(level, entity, stack);
            double base = instance != null && instance.isMastered(entity) ? CONFIG.itemThrowDamageMastered : CONFIG.itemThrowDamage;
            trident.setBaseDamage(base);
            return trident;
         }

         if (item instanceof SimpleSpearItem) {
            SpearProjectile spear = new SpearProjectile(level, entity, stack, true);
            double base = instance != null && instance.isMastered(entity) ? CONFIG.itemThrowDamageMastered : CONFIG.itemThrowDamage;
            spear.setBaseDamage(base);
            if (instance != null) {
               spear.setLoyaltyLevel(instance.isMastered(entity) ? spear.getLoyaltyLevel() + 3 : spear.getLoyaltyLevel() + 1);
            }

            return spear;
         } else if (item instanceof KunaiItem) {
            ItemStack one = stack.copy();
            one.setCount(1);
            KunaiProjectile kunai = new KunaiProjectile(level, entity, one, true);
            double base = instance != null && instance.isMastered(entity) ? CONFIG.itemThrowDamageMastered : CONFIG.itemThrowDamage;
            kunai.setBaseDamage(base);
            if (instance != null) {
               kunai.setLoyaltyLevel(instance.isMastered(entity) ? kunai.getLoyaltyLevel() + 3 : kunai.getLoyaltyLevel() + 1);
            }

            return kunai;
         } else {
            float base = stack.isEmpty()
               ? (instance != null && instance.isMastered(entity) ? CONFIG.airThrowDamageMastered : CONFIG.airThrowDamage)
               : (instance != null && instance.isMastered(entity) ? CONFIG.itemThrowDamageMastered : CONFIG.itemThrowDamage);
            float mult = 1.0F;
            if (SkillUtils.isSkillToggled(entity, (ManasSkill)ExtraSkills.GRAVITY_DOMINATION.get())) {
               mult = (float)CONFIG.itemThrowDomination;
            } else if (SkillUtils.isSkillToggled(entity, (ManasSkill)ExtraSkills.GRAVITY_MANIPULATION.get())) {
               mult = (float)CONFIG.itemThrowManipulation;
            }

            base *= mult;
            ThrownItemProjectile proj = new ThrownItemProjectile(level, entity, stack, true, base);
            proj.getSourceItem().setCount(1);
            if (instance != null) {
               proj.setSkill(instance);
               proj.setLoyaltyLevel((int)mult);
            }

            return proj;
         }
      }
   }
}
