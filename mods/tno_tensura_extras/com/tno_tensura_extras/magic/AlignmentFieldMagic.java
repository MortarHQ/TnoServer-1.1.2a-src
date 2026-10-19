package com.tno_tensura_extras.magic;

import com.mojang.datafixers.util.Pair;
import com.tno_tensura_extras.entity.AlignmentFieldEntity;
import com.tno_tensura_extras.registry.ExtrasEntityTypes;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.magic.aspectual.AspectualMagic;
import io.github.manasmods.tensura.ability.magic.aspectual.AspectualMagic.AspectualType;
import io.github.manasmods.tensura.ability.magic.aspectual.barrier.AntiMagicAreaMagic;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.registry.magic.AspectualMagics;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AlignmentFieldMagic extends AspectualMagic {
   private static final int CAST_TICKS = 100;
   private static final int GROWTH_TICKS = 200;
   private static final float MIN_RADIUS = 4.0F;
   private static final float MAX_RADIUS = 60.0F;
   private static final double MAGICULE_COST_PER_SECOND = 1000.0;
   private static final String FIELD_ID_TAG = "AlignmentFieldID";
   private static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "textures/magic/alignment_field.png");

   public AlignmentFieldMagic() {
      super(AspectualType.BARRIER);
   }

   public ResourceLocation getSkillIcon() {
      return ICON;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return "default";
   }

   public int getDefaultCastTime() {
      return 100;
   }

   public boolean isInstantCast(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return 1000.0;
   }

   public boolean canLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (!super.canLearnSkill(instance, entity)) {
         return false;
      }

      if (SkillUtils.isSkillMastered(entity, (ManasSkill)AspectualMagics.ANTI_MAGIC_AREA.get())) {
         return true;
      }

      instance.setCoolDowns(TensuraSkill.BASE_CONFIG.Learning.learningFailCooldown);
      if (entity instanceof Player player) {
         player.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
         player.displayClientMessage(
            Component.translatable(
                  "tensura.skill.learn_points.failed_mastery",
                  new Object[]{instance.getChatDisplayName(false), ((AntiMagicAreaMagic)AspectualMagics.ANTI_MAGIC_AREA.get()).getChatDisplayName(false)}
               )
               .withStyle(ChatFormatting.RED),
            true
         );
      }

      return false;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      instance.getOrCreateTag().putInt("AlignmentFieldID", 0);
      instance.markDirty();
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (instance.onCoolDown(mode) && !instance.canIgnoreCoolDown(entity, mode)) {
         return false;
      } else if (heldTicks == 0 && this.isCastingBlocked(instance, entity)) {
         return false;
      } else {
         int castTime = this.getCastingTime(instance, entity);
         if (heldTicks < castTime) {
            MagicCircle.castMagicCircle(
               2.0F, 25, MagicCircleVariant.BARRIER, entity, instance.getOrCreateTag(), 0.0F, Vec3.ZERO, instance, mode, Pair.of(0.0, 1000.0)
            );
            this.applyCastingVisual(instance, entity, heldTicks, mode);
            return true;
         } else {
            int grownTicks = heldTicks - castTime;
            AlignmentFieldEntity field = this.getOrSummonField(instance, entity, mode, grownTicks);
            if (field == null) {
               return false;
            } else if (grownTicks > 0 && grownTicks % 20 == 0 && this.isOutOfEnergy(entity, instance, 0.0, 1000.0)) {
               field.startFading();
               return false;
            } else {
               field.keepAlive();
               field.setRadius(radiusFor(grownTicks));
               return true;
            }
         }
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      AlignmentFieldEntity field = this.getField(instance, entity.level());
      if (field != null) {
         field.startFading();
      }

      instance.getOrCreateTag().putInt("AlignmentFieldID", 0);
      instance.markDirty();
   }

   private static float radiusFor(int grownTicks) {
      float progress = Math.min(1.0F, grownTicks / 200.0F);
      return 4.0F + 56.0F * progress;
   }

   private AlignmentFieldEntity getOrSummonField(ManasSkillInstance instance, LivingEntity entity, int mode, int grownTicks) {
      AlignmentFieldEntity existing = this.getField(instance, entity.level());
      if (existing != null) {
         return existing;
      }

      if (grownTicks > 0) {
         return null;
      }

      if (this.isOutOfEnergy(entity, instance, 0.0, 1000.0)) {
         return null;
      }

      Level level = entity.level();
      AlignmentFieldEntity field = (AlignmentFieldEntity)ExtrasEntityTypes.ALIGNMENT_FIELD.get().create(level);
      if (field == null) {
         return null;
      }

      field.setOwner(entity);
      field.setRadius(radiusFor(0));
      field.keepAlive();
      field.setPos(entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ());
      level.addFreshEntity(field);
      instance.getOrCreateTag().putInt("AlignmentFieldID", field.getId());
      instance.markDirty();
      instance.addMasteryPoint(entity);
      level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.PLAYERS, 1.0F, 0.6F);
      return field;
   }

   private AlignmentFieldEntity getField(ManasSkillInstance instance, Level level) {
      CompoundTag tag = instance.getTag();
      if (tag == null) {
         return null;
      } else {
         int id = tag.getInt("AlignmentFieldID");
         if (id == 0) {
            return null;
         } else {
            return level.getEntity(id) instanceof AlignmentFieldEntity field && !field.isRemoved() && !field.isFading() ? field : null;
         }
      }
   }
}
