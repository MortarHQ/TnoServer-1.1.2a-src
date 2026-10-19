package dev.foxboss.entity;

import dev.foxboss.FoxBossConfig;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

final class MilimSatanaelCombat {
   private final MilimEntity milim;
   private long lastContact = -4611686018427387904L;

   MilimSatanaelCombat(MilimEntity milim) {
      this.milim = milim;
   }

   void reset() {
      this.lastContact = -4611686018427387904L;
   }

   private boolean enabled() {
      return !this.milim.level().isClientSide
         && this.milim.isAlive()
         && !this.milim.isNoAi()
         && !this.milim.isDemo()
         && !this.milim.isDebugAnimation()
         && !this.milim.isOrderedToSit();
   }

   void contact(LivingEntity opponent) {
      if (this.enabled()
         && opponent != null
         && opponent.level() == this.milim.level()
         && opponent.isAlive()
         && !opponent.isSpectator()
         && this.milim.canAttack(opponent)) {
         ManasSkillInstance skill = (ManasSkillInstance)SkillAPI.getSkillsFrom(this.milim).getSkill(MilimSatanaelCompat.SATANAEL).orElse(null);
         if (skill != null && skill.canInteractSkill(this.milim)) {
            this.lastContact = this.milim.level().getGameTime();
            CompoundTag tag = skill.getOrCreateTag();
            tag.putInt("SatanaelLastCombatTick", this.milim.tickCount);
            tag.putInt("SatanaelLastDecayTick", this.milim.tickCount);
            SkillAPI.getSkillsFrom(this.milim).checkAndMarkDirty(skill);
         }
      }
   }

   void tick(SkillStorage skills, ManasSkillInstance skill, boolean fighting) {
      if (this.enabled() && fighting) {
         long now = this.milim.level().getGameTime();
         if (now >= this.lastContact && now - this.lastContact <= 40L && this.milim.tickCount % 20 == 0) {
            CompoundTag tag = skill.getOrCreateTag();
            if (!skill.onCoolDown(1) && !tag.contains("SatanaelStampedeEnd") && tag.getLong("SatanaelWeaknessEnd") <= now) {
               double gain = this.milim.getHealth() < this.milim.getMaxHealth() * 0.5F
                  ? (Double)FoxBossConfig.SATANAEL_INJURED_WRATH.get()
                  : (Double)FoxBossConfig.SATANAEL_COMBAT_WRATH.get();
               double previous = tag.getDouble("SatanaelWrathValue");
               tag.putDouble("SatanaelWrathValue", Math.min(100.0, Math.max(0.0, previous) + gain));
               skills.checkAndMarkDirty(skill);
            }

            if (skill.isToggled()) {
               double naturalMaximum = this.milim.getAttributeValue(TensuraAttributes.MAX_MAGICULE);
               double limit = this.milim.getAttributeValue(TensuraAttributes.LIMITED_SPIRITUAL_MAX_MAGICULE);
               double maximum = limit > 0.0 ? Math.min(naturalMaximum, limit) : naturalMaximum;
               IExistence existence = TensuraStorages.getExistenceFrom(this.milim);
               double current = existence.getMagicule();
               double recovery = naturalMaximum * MilimSatanaelCompat.combatRecoveryFraction();
               if (recovery > 0.0 && current < maximum) {
                  existence.setMagicule(Math.min(maximum, current + recovery));
                  existence.markDirty();
               }
            }
         }
      } else {
         this.reset();
      }
   }
}
