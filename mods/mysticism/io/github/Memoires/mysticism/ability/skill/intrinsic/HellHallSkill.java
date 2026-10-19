package io.github.Memoires.mysticism.ability.skill.intrinsic;

import io.github.Memoires.mysticism.config.ability.skill.IntrinsicMysticismSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillGriefEvent;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.world.TensuraGameRules;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class HellHallSkill extends Skill {
   protected static final IntrinsicMysticismSkillConfig.HellHall CONFIG = ((IntrinsicMysticismSkillConfig)ConfigRegistry.getConfig(
         IntrinsicMysticismSkillConfig.class
      ))
      .HellHall;

   public HellHallSkill() {
      super(SkillType.INTRINSIC);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/intrinsic/hell_hall.png");
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      Level level = entity.level();
      if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         if (TensuraGameRules.canSkillGrief(level)) {
            BlockPos playerPos = entity.blockPosition();
            int radius = instance.isMastered(entity) ? CONFIG.radiusMastered : CONFIG.radius;
            boolean waterNearby = false;

            label94:
            for (int dx = -radius; dx <= radius; dx++) {
               for (int dz = -radius; dz <= radius; dz++) {
                  BlockPos checkPos = playerPos.offset(dx, -1, dz);
                  if (level.getBlockState(checkPos).is(Blocks.WATER)) {
                     waterNearby = true;
                     break label94;
                  }
               }
            }

            if (waterNearby) {
               if (level instanceof ServerLevel serverLevel) {
                  for (int dx = -radius; dx <= radius; dx++) {
                     for (int dz = -radius; dz <= radius; dz++) {
                        BlockPos particlePos = playerPos.offset(dx, -1, dz);
                        serverLevel.sendParticles(
                           ParticleTypes.SMOKE, particlePos.getX() + 0.5, particlePos.getY() + 0.5, particlePos.getZ() + 0.5, 1, 0.1, 0.1, 0.1, 0.01
                        );
                     }
                  }
               }
            } else {
               BlockState lava = (BlockState)Blocks.LAVA.defaultBlockState().setValue(LiquidBlock.LEVEL, 12);
               boolean changed = false;

               for (int dx = -radius; dx <= radius; dx++) {
                  for (int dz = -radius; dz <= radius; dz++) {
                     BlockPos targetPos = playerPos.offset(dx, -1, dz);
                     BlockState currentState = level.getBlockState(targetPos);
                     if (!currentState.is(Blocks.BEDROCK)
                        && !(currentState.getBlock().defaultDestroyTime() < 0.0F)
                        && !((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_PRE.invoker())
                           .grief(instance, level, entity, targetPos.getX(), targetPos.getY(), targetPos.getZ())
                           .isFalse()) {
                        changed |= level.setBlockAndUpdate(targetPos, lava);
                        ((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_POS.invoker())
                           .grief(instance, level, entity, targetPos.getX(), targetPos.getY(), targetPos.getZ());
                     }
                  }
               }

               if (changed) {
                  this.addMasteryPoint(instance, entity);
                  entity.swing(InteractionHand.MAIN_HAND, true);
                  instance.setCoolDown(instance.isMastered(entity) ? CONFIG.cooldownMastered : CONFIG.cooldown, 0);
               }
            }
         }
      }
   }
}
