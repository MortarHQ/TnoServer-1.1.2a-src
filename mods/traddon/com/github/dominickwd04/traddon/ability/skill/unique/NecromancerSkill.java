package com.github.dominickwd04.traddon.ability.skill.unique;

import com.github.dominickwd04.traddon.config.ability.skill.TrAddonSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.entity.monster.BarghestEntity;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.entity.MonsterEntityTypes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class NecromancerSkill extends Skill {
   public static final TrAddonSkillConfig.Necromancer CONFIG = ((TrAddonSkillConfig)ConfigRegistry.getConfig(TrAddonSkillConfig.class)).necromancer;
   protected static final ResourceLocation NECROMANCER = ResourceLocation.fromNamespaceAndPath("traddon", "necromancer");

   public NecromancerSkill() {
      super(SkillType.UNIQUE);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return CONFIG.mpAcquirement;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return mode == 0 ? CONFIG.summonCost : CONFIG.summonCost * 3;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "necromancer.summon";
         case 1 -> "necromancer.summon_3";
         default -> super.getModeId(instance, mode);
      };
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled() && entity instanceof Player;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.getCommandSenderWorld().getEntitiesOfClass(Skeleton.class, entity.getBoundingBox().inflate(50.0)).size() >= CONFIG.maxMinions
         && entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable("traddon.skill.necromancer.too_many_undead", new Object[0]).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE)),
            false
         );
      } else {
         if (this.getSouls(entity) >= CONFIG.summonCost) {
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               this.summonUndead(instance, entity, mode == 0 ? 1 : 3);
            }
         } else if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("traddon.skill.necromancer.no_souls", new Object[0]).setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE)), false
            );
         }
      }
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public Component getModeName(int mode) {
      return switch (mode) {
         case 1 -> Component.translatable("traddon.skill.mode.necromancer.summon");
         case 2 -> Component.translatable("traddon.skill.mode.necromancer.summon_3");
         default -> Component.empty();
      };
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      double souls = this.getSouls(entity);
      AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
      if (armor != null) {
         AttributeModifier armorModifier = new AttributeModifier(NECROMANCER, getArmor(souls), Operation.ADD_VALUE);
         armor.addOrReplacePermanentModifier(armorModifier);
      }

      AttributeInstance damage = entity.getAttribute(Attributes.ATTACK_DAMAGE);
      if (damage != null) {
         damage.addOrReplacePermanentModifier(
            new AttributeModifier(NECROMANCER, instance.isMastered(entity) ? getAttack(souls) * 2.0 : getAttack(souls), Operation.ADD_VALUE)
         );
      }

      AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         speed.addOrReplacePermanentModifier(new AttributeModifier(NECROMANCER, getSpeed(souls) / 100.0, Operation.ADD_VALUE));
      }

      AttributeInstance shp = entity.getAttribute(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
      if (shp != null) {
         shp.addOrReplacePermanentModifier(new AttributeModifier(NECROMANCER, getShp(souls), Operation.ADD_VALUE));
      }
   }

   public int getSouls(LivingEntity entity) {
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      return existence != null ? existence.getSoulPoints() / 100 : 0;
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
      if (armor != null) {
         armor.removeModifier(NECROMANCER);
      }

      AttributeInstance damage = entity.getAttribute(Attributes.ATTACK_DAMAGE);
      if (damage != null) {
         damage.removeModifier(NECROMANCER);
      }

      AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         speed.removeModifier(NECROMANCER);
      }

      AttributeInstance shp = entity.getAttribute(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
      if (shp != null) {
         shp.removeModifier(NECROMANCER);
      }
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (instance.isToggled() && entity instanceof Player) {
         AttributeInstance shp = entity.getAttribute(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
         if (shp.getModifier(NECROMANCER) != null && shp.getModifier(NECROMANCER).amount() != getShp(this.getSouls(entity))) {
            instance.onToggleOff(entity);
            instance.onToggleOn(entity);
         }

         for (LivingEntity livingEntity : entity.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(15.0))) {
            if (livingEntity instanceof BarghestEntity undead
               && undead.getOwnerUUID().equals(entity.getUUID())
               && livingEntity.getHealth() < livingEntity.getMaxHealth()) {
               entity.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION), 240, instance.isMastered(entity) ? 1 : 0, false, false, false
                  )
               );
            }
         }
      }
   }

   public void summonUndead(ManasSkillInstance instance, LivingEntity entity, int amount) {
      Level level = entity.level();
      EntityType<BarghestEntity> skeletonType = (EntityType<BarghestEntity>)MonsterEntityTypes.BARGHEST.get();

      for (int i = 0; i < amount; i++) {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         if (existence != null) {
            existence.setSoulPoints(existence.getSoulPoints() - this.getSoulCost());
         }

         BarghestEntity skeleton = new BarghestEntity(skeletonType, level);
         skeleton.moveTo(entity.getX() + (Math.random() - 0.5) * 10.0, entity.getY(), entity.getZ() + (Math.random() - 0.5) * 10.0, entity.getYHeadRot(), 0.0F);
         level.addFreshEntity(skeleton);
         skeleton.tame((Player)entity);
         skeleton.setOwnerUUID(entity.getUUID());
         if (instance.isMastered(entity)) {
            TensuraStorages.getExistenceFrom(skeleton).setEP(8000.0);
            AttributeInstance damage = skeleton.getAttribute(Attributes.ATTACK_DAMAGE);
            if (damage != null) {
               damage.addOrReplacePermanentModifier(new AttributeModifier(NECROMANCER, CONFIG.minionAttackDamage, Operation.ADD_VALUE));
            }

            AttributeInstance health = skeleton.getAttribute(Attributes.MAX_HEALTH);
            if (health != null) {
               health.addOrReplacePermanentModifier(new AttributeModifier(NECROMANCER, CONFIG.minionHealth, Operation.ADD_VALUE));
            }

            AttributeInstance speed = skeleton.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speed != null) {
               speed.addOrReplacePermanentModifier(new AttributeModifier(NECROMANCER, CONFIG.minionSpeed, Operation.ADD_VALUE));
            }

            AttributeInstance armor = skeleton.getAttribute(Attributes.ARMOR);
            if (armor != null) {
               armor.addOrReplacePermanentModifier(new AttributeModifier(NECROMANCER, CONFIG.minionArmor, Operation.ADD_VALUE));
            }
         }

         this.addMasteryPoint(instance, entity);
      }

      if (!instance.isMastered(entity)) {
         instance.setCoolDown(instance.getCoolDown(1), 1);
      } else {
         instance.setCoolDown(instance.getCoolDown(1), 1);
      }
   }

   public void onSubordinateDeath(ManasSkillInstance instance, LivingEntity owner, LivingEntity subordinate, DamageSource source) {
      if (instance.isToggled() && instance.isMastered(owner) && subordinate instanceof BarghestEntity) {
         IExistence existence = TensuraStorages.getExistenceFrom((Player)owner);
         if (existence != null) {
            existence.setSoulPoints(existence.getSoulPoints() + (int)(this.getSoulCost() * CONFIG.summonedDeathSoulReturn));
         }

         instance.setCoolDown(instance.getCoolDown(1) - 10, 1);
      }
   }

   public void onScroll(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      int summonAmount = tag.getInt("summonAmount");
      if (summonAmount < 5) {
         tag.putInt("summonAmount", summonAmount + 1);
      } else {
         tag.putInt("summonAmount", 1);
      }

      if (entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable("traddon.skill.summon_amount", new Object[]{summonAmount + 1, new Object[0]})
               .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_AQUA)),
            true
         );
      }

      instance.markDirty();
   }

   public int getSoulCost() {
      return 1000 * CONFIG.summonCost;
   }

   public static double getArmor(double souls) {
      return souls >= 5000.0 ? 25.0 : souls / 200.0;
   }

   public static double getAttack(double souls) {
      return souls >= 5000.0 ? 20.0 : souls / 250.0;
   }

   public static double getSpeed(double souls) {
      return (souls >= 5000.0 ? 10.0 : souls / 500.0) / 20.0;
   }

   public static double getShp(double souls) {
      return souls >= 5000.0 ? 2500.0 : souls / 2.0;
   }
}
