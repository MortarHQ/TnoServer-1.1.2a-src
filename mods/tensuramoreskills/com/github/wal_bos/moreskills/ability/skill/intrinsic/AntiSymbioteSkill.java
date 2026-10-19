package com.github.wal_bos.moreskills.ability.skill.intrinsic;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.ParasiteData;
import com.github.wal_bos.moreskills.handler.ParasiteHandler;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class AntiSymbioteSkill extends Skill {
   public AntiSymbioteSkill() {
      super(SkillType.INTRINSIC);
      ParasiteHandler.init();
   }

   private static MoreSkillsConfigs.ParasiteConfig cfg() {
      return MoreSkillsConfigs.GRAND.parasite;
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/intrinsic/anti_symbiote.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 0.0;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (!instance.onCoolDown(mode)) {
            int rel = Math.min((Integer)cfg().antiMaxRelationship.get(), Math.max(0, ParasiteData.getRelationship(entity)));
            ParasiteData.setRelationship(entity, rel);
            ParasiteHandler.applyFormAttributes(entity, rel, true, (Integer)cfg().antiDurationTicks.get());
            int cooldownSeconds = Math.max(0, (int)Math.ceil(((Integer)cfg().antiCooldownTicks.get()).intValue() / 20.0));
            instance.setCoolDown(cooldownSeconds, mode);
            instance.addMasteryPoint(entity);
            entity.level()
               .playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.4F
               );
         }
      }
   }
}
