package team.lodestar.lodestone.handlers;

import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;
import team.lodestar.lodestone.helpers.DamageTypeHelper;
import team.lodestar.lodestone.registry.common.LodestoneAttributes;
import team.lodestar.lodestone.registry.common.tag.LodestoneDamageTypeTags;

public class LodestoneAttributeEventHandler {
   public static void processAttributes(Pre event) {
      if (!(event.getOriginalDamage() <= 0.0F)) {
         DamageSource source = event.getSource();
         LivingEntity target = event.getEntity();
         float amount = event.getOriginalDamage();
         Holder<DamageType> damageType = source.typeHolder();
         if (damageType.is(LodestoneDamageTypeTags.IS_MAGIC)) {
            AttributeInstance magicResistance = target.getAttribute(LodestoneAttributes.MAGIC_RESISTANCE);
            if (magicResistance != null) {
               amount /= (float)Math.max(magicResistance.getValue(), 0.01F);
            }
         }

         if (source.getEntity() instanceof LivingEntity attacker && damageType.is(LodestoneDamageTypeTags.IS_MAGIC)) {
            AttributeInstance magicProficiency = attacker.getAttribute(LodestoneAttributes.MAGIC_PROFICIENCY);
            if (magicProficiency != null) {
               amount *= (float)magicProficiency.getValue();
            }

            event.setNewDamage(amount);
         }
      }
   }

   public static void triggerMagicDamage(Post event) {
      if (!(event.getOriginalDamage() <= 0.0F)) {
         DamageSource source = event.getSource();
         LivingEntity target = event.getEntity();
         Holder<DamageType> damageType = source.typeHolder();
         if (source.getEntity() instanceof LivingEntity attacker && damageType.is(LodestoneDamageTypeTags.CAN_TRIGGER_MAGIC)) {
            AttributeInstance magicDamage = attacker.getAttribute(LodestoneAttributes.MAGIC_DAMAGE);
            if (magicDamage != null && magicDamage.getValue() > 0.0 && !target.isDeadOrDying()) {
               float magicDamageAmount = (float)magicDamage.getValue();
               if (attacker instanceof Player player && !damageType.is(LodestoneDamageTypeTags.IGNORES_MAGIC_ATTACK_COOLDOWN_SCALAR)) {
                  magicDamageAmount *= player.getAttackStrengthScale(0.0F);
               }

               DamageSource magic = DamageTypeHelper.create(DamageTypes.MAGIC, attacker);
               target.invulnerableTime = 0;
               target.hurt(magic, magicDamageAmount);
            }
         }
      }
   }
}
