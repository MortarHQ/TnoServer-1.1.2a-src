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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class DischargeSkill extends Skill {
   protected static final IntrinsicMysticismSkillConfig.Discharge CONFIG = ((IntrinsicMysticismSkillConfig)ConfigRegistry.getConfig(
         IntrinsicMysticismSkillConfig.class
      ))
      .Discharge;

   public DischargeSkill() {
      super(SkillType.INTRINSIC);
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/intrinsic/discharge.png");
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      Level level = entity.level();
      if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         instance.addMasteryPoint(entity);
         Entity target = ObjectSelectionHelper.getTargetingEntity(entity, CONFIG.selectionRange, false, false);
         Vec3 pos;
         if (target != null) {
            pos = target.position();
         } else {
            BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(level, entity, Fluid.NONE, 30.0);
            pos = result.getLocation();
         }

         LightningBolt bolt = new LightningBolt(level, entity);
         ServerPlayer cause = entity instanceof ServerPlayer ? (ServerPlayer)entity : null;
         bolt.setCause(cause);
         bolt.setSkill(entity, instance, this, mode);
         bolt.setTensuraDamage(CONFIG.boltDamage);
         bolt.setAdditionalVisual(2);
         bolt.setRadius(CONFIG.boltRange);
         bolt.setSkill(instance);
         bolt.setPos(pos);
         level.addFreshEntity(bolt);
         entity.swing(InteractionHand.MAIN_HAND, true);
         entity.playSound((SoundEvent)TensuraSoundEvents.CAST_LIGHTNING.get());
         instance.setCoolDown(CONFIG.cooldown, 0);
      }
   }
}
