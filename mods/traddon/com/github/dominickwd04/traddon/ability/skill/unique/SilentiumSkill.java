package com.github.dominickwd04.traddon.ability.skill.unique;

import com.github.dominickwd04.traddon.config.ability.skill.TrAddonSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill.ResistType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public class SilentiumSkill extends ResistSkill {
   public static final TrAddonSkillConfig.Silentium CONFIG = ((TrAddonSkillConfig)ConfigRegistry.getConfig(TrAddonSkillConfig.class)).silentium;
   public static final ResourceLocation NULL_FIELD = ResourceLocation.fromNamespaceAndPath("traddon", "null_field");

   public SilentiumSkill() {
      super(SkillType.UNIQUE, ResistType.NULLIFICATION);
   }

   public int getModes(ManasSkillInstance instance) {
      return 3;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canBeSlotted(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return true;
   }

   public boolean isDamageResisted(LivingEntity entity, DamageSource damageSource, ManasSkillInstance instance) {
      return TensuraDamageHelper.isSoundDamage(damageSource);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 0) {
         int radius = CONFIG.acousticNullRadius > 0 ? CONFIG.acousticNullRadius : 100;
         if (CONFIG.acousticNullDuration <= 0) {
            int duration = 200;
         }

         AABB area = entity.getBoundingBox().inflate(radius);

         for (LivingEntity var10 : entity.level().getEntitiesOfClass(LivingEntity.class, area, e -> e != entity)) {
            ;
         }

         int cooldown = CONFIG.acousticNullCooldown > 0 ? CONFIG.acousticNullCooldown : 400;
         instance.setCoolDown(cooldown, mode);
      }
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity entity, DamageSource damageSource, Changeable<Float> amount) {
      if (!damageSource.getMsgId().contains("acid") && !damageSource.getMsgId().contains("liquid")) {
         return super.onTakenDamage(instance, entity, damageSource, amount);
      }

      amount.set(0.0F);
      return true;
   }
}
