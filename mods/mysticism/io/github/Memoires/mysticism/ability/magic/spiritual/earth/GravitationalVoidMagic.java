package io.github.Memoires.mysticism.ability.magic.spiritual.earth;

import com.mojang.datafixers.util.Pair;
import io.github.Memoires.mysticism.config.ability.magic.SpiritualMysticismMagicConfig;
import io.github.Memoires.mysticism.entity.magic.misc.BlackHoleEntity;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class GravitationalVoidMagic extends SpiritualMagic {
   private static final SpiritualMysticismMagicConfig.GravitationalVoid CONFIG = ((SpiritualMysticismMagicConfig)ConfigRegistry.getConfig(
         SpiritualMysticismMagicConfig.class
      ))
      .GravitationalVoid;

   public GravitationalVoidMagic() {
      super(Element.EARTH, SpiritLevel.LORD);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/magic/spiritual/gravitational_void.png");
   }

   public int getDefaultCastTime() {
      return CONFIG.castTime;
   }

   public int getMasteryCastTime() {
      return CONFIG.castTimeMastered;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   protected void applyCastingVisual(ManasSkillInstance instance, Player entity, int heldTicks, int mode, int castTime) {
      super.applyCastingVisual(instance, entity, heldTicks, mode, castTime);
      if (castTime > 1) {
         MagicCircle.castMagicCircle(
            3.0F,
            25,
            MagicCircleVariant.GRAVITY,
            entity,
            instance.getOrCreateTag(),
            0.75F,
            Vec3.ZERO,
            instance,
            mode,
            Pair.of(0.0, this.getMagiculeCost(entity, instance, mode))
         );
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (heldTicks >= this.getCastingTime(instance, entity) && !EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         entity.swing(InteractionHand.MAIN_HAND, true);
         instance.addMasteryPoint(entity);
         instance.setCoolDown(instance.isMastered(entity) ? CONFIG.cooldownMastered : CONFIG.cooldown, mode);
         this.placeblackHole(instance, entity, mode);
      }
   }

   private void placeblackHole(ManasSkillInstance instance, LivingEntity entity, int mode) {
      BlackHoleEntity blackHole = new BlackHoleEntity(entity.level(), entity);
      blackHole.setMaxSize(CONFIG.maxSize);
      blackHole.setMaxLifeTime(CONFIG.lifespan);
      blackHole.setDamage(CONFIG.baseGravityDamage);
      blackHole.setSecondaryDamage(CONFIG.baseMagicDamage);
      blackHole.setMaxDamage(CONFIG.maxGravityDamage);
      blackHole.setMaxSecondaryDamage(CONFIG.maxMagicDamage);
      if (instance.isMastered(entity)) {
         blackHole.setMaxLifeTime(CONFIG.lifespanMastered);
         instance.setCoolDown(CONFIG.cooldownMastered, mode);
      } else {
         instance.setCoolDown(CONFIG.cooldown, mode);
      }

      blackHole.setSkill(entity, instance, this, mode);
      blackHole.setPos(
         entity.position().add(0.0, entity.getEyeHeight() - blackHole.getBoundingBox().getYsize() * 0.5, 0.0).add(entity.getLookAngle().normalize())
      );
      entity.level().addFreshEntity(blackHole);
      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_SPACE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_EARTH.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
   }
}
