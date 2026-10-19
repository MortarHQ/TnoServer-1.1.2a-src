package io.github.Memoires.mysticism.ability.skill.intrinsic;

import io.github.Memoires.mysticism.config.ability.skill.IntrinsicMysticismSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.entity.magic.lightning.LightningBolt;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class DissonanceSkill extends Skill {
   protected static final IntrinsicMysticismSkillConfig.Dissonance CONFIG = ((IntrinsicMysticismSkillConfig)ConfigRegistry.getConfig(
         IntrinsicMysticismSkillConfig.class
      ))
      .Dissonance;

   public DissonanceSkill() {
      super(SkillType.INTRINSIC);
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/intrinsic/dissonance.png");
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      Level level = entity.level();
      if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         instance.addMasteryPoint(entity);
         AABB area = entity.getBoundingBox().inflate(CONFIG.radius);
         List<LivingEntity> nearby = level.getEntitiesOfClass(LivingEntity.class, area, e -> e != entity && e.isAlive());
         if (!nearby.isEmpty()) {
            for (LivingEntity target : nearby) {
               LightningBolt bolt = new LightningBolt(level, entity);
               ServerPlayer cause = entity instanceof ServerPlayer ? (ServerPlayer)entity : null;
               bolt.setCause(cause);
               bolt.setSkill(entity, instance, this, mode);
               bolt.setTensuraDamage(CONFIG.boltDamage);
               bolt.setAdditionalVisual(2);
               bolt.setRadius(CONFIG.boltRange);
               bolt.setSkill(instance);
               bolt.setPos(target.position());
               level.addFreshEntity(bolt);
            }
         } else {
            BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(level, entity, Fluid.NONE, CONFIG.selectionRange);
            LightningBolt bolt = new LightningBolt(level, entity);
            ServerPlayer cause = entity instanceof ServerPlayer ? (ServerPlayer)entity : null;
            bolt.setCause(cause);
            bolt.setSkill(entity, instance, this, mode);
            bolt.setTensuraDamage(CONFIG.boltDamage);
            bolt.setAdditionalVisual(2);
            bolt.setRadius(CONFIG.boltRange);
            bolt.setSkill(instance);
            bolt.setPos(result.getLocation());
            level.addFreshEntity(bolt);
         }

         entity.swing(InteractionHand.MAIN_HAND, true);
         entity.playSound((SoundEvent)TensuraSoundEvents.CAST_LIGHTNING.get());
         instance.setCoolDown(CONFIG.cooldown, 0);
      }
   }
}
