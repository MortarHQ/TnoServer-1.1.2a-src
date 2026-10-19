package io.github.Memoires.mysticism.ability.skill.intrinsic;

import io.github.Memoires.mysticism.config.ability.skill.IntrinsicMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.sound.MysticismSoundEvents;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ClangorousSoulSkill extends Skill {
   protected static final IntrinsicMysticismSkillConfig.ClangorousSoul CONFIG = ((IntrinsicMysticismSkillConfig)ConfigRegistry.getConfig(
         IntrinsicMysticismSkillConfig.class
      ))
      .ClangorousSoul;

   public ClangorousSoulSkill() {
      super(SkillType.INTRINSIC);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/intrinsic/clangorous_soul.png");
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      List<LivingEntity> list = entity.level()
         .getEntitiesOfClass(
            LivingEntity.class,
            entity.getBoundingBox().inflate(instance.isMastered(entity) ? CONFIG.tauntRadiusMastered : CONFIG.tauntRadius),
            living -> !living.is(entity) && living.isAlive() && !living.isAlliedTo(entity) && living.canAttack(entity)
         );
      entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), MysticismSoundEvents.CLANGOROUS_SOUL, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
      if (!list.isEmpty()) {
         for (LivingEntity target : list) {
            if (target instanceof Mob mob) {
               mob.setTarget(entity);
               mob.setAggressive(true);
               if (entity instanceof Player p) {
                  mob.setLastHurtByPlayer(p);
               } else {
                  mob.setLastHurtByMob(entity);
               }

               if (mob instanceof NeutralMob neutral) {
                  neutral.setPersistentAngerTarget(entity.getUUID());
                  neutral.startPersistentAngerTimer();
               }
            }
         }

         instance.addMasteryPoint(entity);
         int cooldown = instance.isMastered(entity) ? CONFIG.cooldownMastered : CONFIG.cooldown;
         instance.setCoolDown(cooldown, 0);
      }
   }
}
