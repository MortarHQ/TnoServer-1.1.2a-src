package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.damage.MysticismDamageTypes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class TheBalanceSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.TheBalance CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class))
      .TheBalance;

   public TheBalanceSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/the_balance.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return CONFIG.mpAcquirement;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return instance.getMastery() >= 0.0;
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      instance.getOrCreateTag().putInt("MisfortunePoint", 0);
   }

   public int getModes(ManasSkillInstance instance) {
      return 1;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return "mysticism.balance.misfortune_release";
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return 0;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         instance.addMasteryPoint(entity);
         Level level = entity.level();
         LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, CONFIG.range, false);
         if (target == null) {
            level.playSound(
               null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)SoundEvents.NOTE_BLOCK_BELL.value(), TensuraSkill.ABILITY_SOUND, 0.7F, 1.2F
            );
         } else {
            int points = instance.getOrCreateTag().getInt("MisfortunePoint");
            DamageSource src = this.createSource(instance, entity, MysticismDamageTypes.BALANCE, mode);
            if (points > CONFIG.thresholdPoints) {
               target.hurt(src, (float)(CONFIG.perPointDamage * points));
               instance.getOrCreateTag().putInt("MisfortunePoint", 0);
            } else {
               target.hurt(src, (float)CONFIG.heavyDamage);
               instance.getOrCreateTag().putInt("MisfortunePoint", Math.max(0, points - CONFIG.thresholdPoints));
            }

            entity.swing(InteractionHand.MAIN_HAND, true);
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
         }
      }
   }
}
