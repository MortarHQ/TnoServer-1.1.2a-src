package io.github.Memoires.mysticism.handler.skill;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.entity.IMysticismEntity;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.entity.magic.field.MagicExplosion;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector3f;

public class ButcherHandler {
   public static void init() {
      EntityEvent.LIVING_DEATH
         .register(
            (LivingDeath)(livingEntity, damageSource) -> {
               IMysticismEntity storage = MysticismStorages.getEntityDataFrom(livingEntity);
               if (storage.getButcherMarks() >= 5) {
                  DustParticleOptions bloodDust = new DustParticleOptions(new Vector3f(0.6F, 0.0F, 0.0F), 1.2F);
                  List<LivingEntity> list = livingEntity.level()
                     .getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(5.0), living -> !living.is(livingEntity));
                  UUID attackerUUID = storage.getLastMarkApplier();
                  LivingEntity markApplier = null;
                  if (attackerUUID != null) {
                     markApplier = livingEntity.level().getPlayerByUUID(attackerUUID);
                  }

                  if (markApplier != null) {
                     Optional<ManasSkillInstance> butcherInstance = SkillAPI.getSkillsFrom(markApplier)
                        .getSkill((ManasSkill)UniqueMysticismSkills.BUTCHER.get());
                     if (butcherInstance.isEmpty() || !butcherInstance.get().isToggled()) {
                        return EventResult.pass();
                     }

                     MagicExplosion explosion = new MagicExplosion(livingEntity.level(), markApplier);
                     explosion.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                     explosion.setTickEachHit(1);
                     explosion.setLife(50);
                     explosion.setDamage(50.0F);
                     explosion.setSize(5.0F);
                     explosion.setSkill(damageSource.tensura$getAbilityInstance());
                     butcherInstance.ifPresent(explosion::setSkill);
                     explosion.setMode(damageSource.tensura$getAbilityMode());
                     explosion.setApCost(damageSource.tensura$getAuraCost());
                     explosion.setMpCost(damageSource.tensura$getMagiculeCost());
                     explosion.setLimitedGriefing(true);
                     livingEntity.level().addFreshEntity(explosion);
                  }

                  TensuraParticleHelper.addServerParticlesAroundSelf(livingEntity, bloodDust, 2.0, 200);

                  for (LivingEntity nearby : list) {
                     if (!(nearby instanceof Player p && p.isCreative()) && !nearby.equals(livingEntity.level().getPlayerByUUID(attackerUUID))) {
                        nearby.setRemainingFireTicks(200);
                     }
                  }

                  storage.setButcherMarks(0);
                  storage.markDirty();
               }

               return EventResult.pass();
            }
         );
   }
}
