package io.github.Memoires.mysticism.ability.skill.intrinsic;

import io.github.Memoires.mysticism.config.ability.skill.IntrinsicMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.sound.MysticismSoundEvents;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class BulletPunchSkill extends Skill {
   private static final Set<UUID> BULLET_PUNCH_REPEATING = new HashSet<>();
   protected static final IntrinsicMysticismSkillConfig.BulletPunch CONFIG = ((IntrinsicMysticismSkillConfig)ConfigRegistry.getConfig(
         IntrinsicMysticismSkillConfig.class
      ))
      .BulletPunch;

   public BulletPunchSkill() {
      super(SkillType.INTRINSIC);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/intrinsic/bullet_punch.png");
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isMastered(entity);
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (BULLET_PUNCH_REPEATING.contains(target.getUUID())) {
         return true;
      }

      if (TensuraDamageHelper.isPhysicalAttack(source)) {
         if (source.is(DamageTypeTags.IS_PROJECTILE)) {
            return true;
         }

         if (!instance.onCoolDown(0) && (this.isInSlot(owner) || instance.isToggled())) {
            float damage = (Float)amount.get();
            owner.level().playSound(null, owner.blockPosition(), (SoundEvent)MysticismSoundEvents.BULLET_PUNCH.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);

            for (int i = 1; i < CONFIG.amount; i++) {
               owner.getCommandSenderWorld().getServer().submit(() -> {
                  if (target.isAlive()) {
                     BULLET_PUNCH_REPEATING.add(target.getUUID());

                     try {
                        target.invulnerableTime = 0;
                        target.hurt(source, damage);
                        target.level().broadcastEntityEvent(target, (byte)2);
                     } finally {
                        BULLET_PUNCH_REPEATING.remove(target.getUUID());
                     }
                  }
               });
            }

            instance.addMasteryPoint(owner);
            int cooldown = instance.isMastered(owner) ? CONFIG.cooldownMastered : CONFIG.cooldown;
            instance.setCoolDown(cooldown, 0);
         }
      }

      return true;
   }
}
