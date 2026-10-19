package com.tno_tensura_extras.magic;

import com.tno_tensura_extras.registry.ExtrasSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.magic.Magic;
import io.github.manasmods.tensura.ability.magic.Magic.MagicType;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class DayNightInversionMagic extends Magic {
   private static final int COOLDOWN_SECONDS = 1800;
   private static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "textures/magic/day_night_inversion.png");

   public DayNightInversionMagic() {
      super(MagicType.MISC);
   }

   public ResourceLocation getSkillIcon() {
      return ICON;
   }

   protected int getInversionCooldown() {
      return 1800;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return "default";
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      resetAlignment(entity);
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return !entity.level().isClientSide() && entity instanceof Player;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (isReverserMastered(entity)) {
         Skills skills = SkillAPI.getSkillsFrom(entity);
         if (!skills.getSkill((ManasSkill)ExtrasSkills.HOLY_DEMONIC_INVERSION.get()).isPresent()) {
            skills.forgetSkill((ManasSkill)ExtrasSkills.DAY_NIGHT_INVERSION.get());
            SkillHelper.learnSkill(entity, (ManasSkill)ExtrasSkills.HOLY_DEMONIC_INVERSION.get());
         }
      }
   }

   private static boolean isReverserMastered(LivingEntity entity) {
      return SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)UniqueSkills.REVERSER.get()).map(reverser -> reverser.isMastered(entity)).orElse(false);
   }

   protected static void resetAlignment(LivingEntity entity) {
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      existence.setAlignment(Alignment.DEFAULT);
      existence.setOriginalAlignment(Alignment.DEFAULT);
      existence.markDirty();
   }

   public int getDefaultCastTime() {
      return 1;
   }

   public boolean isInstantCast(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide() && !instance.onCoolDown(mode)) {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         Alignment inverted = invert(existence.getAlignment());
         if (inverted != null) {
            existence.setAlignment(inverted);
            existence.setOriginalAlignment(inverted);
            existence.markDirty();
            instance.addMasteryPoint(entity);
            instance.setCoolDown(this.getInversionCooldown(), mode);
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), getInversionSound(inverted), SoundSource.PLAYERS, 1.0F, 1.0F);
            TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.END_ROD, 1.0);
         }
      }
   }

   private static Alignment invert(Alignment alignment) {
      if (alignment == Alignment.MAJIN) {
         return Alignment.HOLY;
      } else {
         return alignment == Alignment.HOLY ? Alignment.MAJIN : null;
      }
   }

   private static SoundEvent getInversionSound(Alignment inverted) {
      return inverted == Alignment.HOLY ? (SoundEvent)TensuraSoundEvents.CAST_LIGHT.get() : (SoundEvent)TensuraSoundEvents.CAST_DARK.get();
   }
}
