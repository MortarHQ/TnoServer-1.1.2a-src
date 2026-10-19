package com.mortal_cultivation.ability.skill.intrinsic;

import com.mortal_cultivation.ability.skill.base.LegacySkill;
import com.mortal_cultivation.entity.breath.AlchemistFireBreathEntity;
import com.mortal_cultivation.registry.CultivationEntities;
import com.mortal_cultivation.spiritroot.SpiritRootData;
import com.mortal_cultivation.spiritroot.SpiritRootType;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class AlchemistFireSkill extends LegacySkill {
   private static final double MAGICULE_COST_PER_SECOND = 200.0;
   private static final double MAGICULE_COST_HEAVEN_ROOT = 100.0;
   private static final float DAMAGE_PER_SECOND = 10.0F;
   private static final float FIRE_ROOT_DAMAGE_MULTIPLIER = 1.5F;
   private static final float HEAVEN_ROOT_DAMAGE_MULTIPLIER = 1.6F;

   public AlchemistFireSkill() {
      super(SkillType.INTRINSIC);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/skill/intrinsic/alchemist_fire.png");
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return entity instanceof Player player && SpiritRootData.getPrimaryRoot(player).orElse(null) == SpiritRootType.HEAVEN ? 100.0 : 200.0;
   }

   @Override
   public void onPressed(ManasSkillInstance instance, LivingEntity entity) {
      instance.getOrCreateTag().putInt("BreathEntity", 0);
      instance.markDirty();
   }

   @Override
   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
      if (entity instanceof Player player) {
         Level level = player.level();
         if (level.isClientSide) {
            return true;
         }

         if (heldTicks % 10 == 0) {
            double magiculeCost = this.magiculeCost(entity, instance);
            if (EnergyHelper.isOutOfMagiculeConsuming(player, magiculeCost) > 0.0) {
               return false;
            }
         }

         if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
            this.addMasteryPoint(instance, player);
         }

         float damage = this.computeDamage(player);
         CompoundTag tag = instance.getOrCreateTag();
         AlchemistFireBreathEntity.spawnOrMaintain(
            (EntityType<? extends AlchemistFireBreathEntity>)CultivationEntities.ALCHEMIST_FIRE_BREATH.get(),
            player,
            tag,
            damage,
            this.magiculeCost(entity, instance)
         );
         instance.markDirty();
         if (heldTicks % 20 == 0) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRE_AMBIENT, SoundSource.PLAYERS, 0.8F, 1.2F);
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
      CompoundTag tag = instance.getOrCreateTag();
      int breathId = tag.getInt("BreathEntity");
      if (breathId != 0) {
         if (entity.level().getEntity(breathId) instanceof AlchemistFireBreathEntity breath) {
            breath.discard();
         }

         tag.putInt("BreathEntity", 0);
         instance.markDirty();
      }
   }

   private float computeDamage(Player player) {
      float damage = 10.0F;
      if (SpiritRootData.getPrimaryRoot(player).orElse(null) == SpiritRootType.HEAVEN) {
         damage *= 1.6F;
      } else if (SpiritRootData.hasPrimaryRoot(player, SpiritRootType.FIRE)) {
         damage *= 1.5F;
      }

      return damage;
   }
}
