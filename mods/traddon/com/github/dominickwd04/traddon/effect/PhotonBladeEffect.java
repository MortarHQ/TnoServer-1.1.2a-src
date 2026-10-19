package com.github.dominickwd04.traddon.effect;

import com.github.dominickwd04.traddon.ability.skill.intrinsic.PhotonBladeSkill;
import com.github.dominickwd04.traddon.registry.effect.TrAddonMobEffects;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.effect.template.DamageAction;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;

public class PhotonBladeEffect extends TensuraMobEffect implements DamageAction {
   public static final ResourceLocation PHOTON_BLADE = ResourceLocation.fromNamespaceAndPath("tensura", "photon_blade");

   public PhotonBladeEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(229, 180, 9).getRGB());
      if (PhotonBladeSkill.CONFIG.physicalResist) {
         this.addAttributeModifier(TensuraAttributes.PHYSICAL_RESIST_DEGRADATION, PHOTON_BLADE, 1.0, Operation.ADD_VALUE);
      }
   }

   public boolean onPlayerAttack(Player attacker, Entity entity) {
      MobEffectInstance photonBlade = attacker.getEffect(TensuraMobEffects.getReference(TrAddonMobEffects.PHOTON_BLADE));
      if (photonBlade == null) {
         return true;
      }

      AbilitySlot abilitySlot = photonBlade.tensura$getSourceAbility();
      if (abilitySlot != null && !abilitySlot.isEmpty()) {
         ManasSkillInstance skillInstance = (ManasSkillInstance)SkillAPI.getSkillsFrom(attacker).getSkill(abilitySlot.getSkill()).orElse(null);
         if (skillInstance != null && !skillInstance.onCoolDown(0)) {
            DamageSource source = TensuraDamageHelper.getAbilityDamageSource(TensuraDamageTypes.LIGHT_ELEMENTAL, attacker, abilitySlot);
            entity.hurt(
               source,
               (float)(
                  SkillUtils.isSkillMastered(attacker, skillInstance.getSkill())
                     ? PhotonBladeSkill.CONFIG.photonBladeMasteredExtraDamage
                     : PhotonBladeSkill.CONFIG.photonBladeBaseDamage
               )
            );
            entity.invulnerableTime = 0;
            entity.level()
               .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_LIGHT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            return true;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }
}
