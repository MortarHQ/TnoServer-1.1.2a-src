package org.crypticdev.ancient_artifacts.mixin.tensura.ability.skill.unique;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.unique.CommanderSkill;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.crypticdev.ancient_artifacts.artifact.ArtifactItem;
import org.crypticdev.ancient_artifacts.platform.PlatformEquipment;
import org.crypticdev.ancient_artifacts.registry.Artifacts;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CommanderSkill.class)
public class CommanderSkillMixin {
   @ModifyVariable(method = "onTick", at = @At("STORE"), name = "level")
   private int increaseLevel(int level, ManasSkillInstance instance, LivingEntity entity) {
      if (!(entity instanceof Player player)) {
         return level;
      } else {
         return PlatformEquipment.hasArtifact(entity, Artifacts.LionEmblem)
               && ((ArtifactItem)PlatformEquipment.getArtifactStack(entity, Artifacts.LionEmblem).getItem())
                  .getArtifact()
                  .artifactEffects()
                  .get(2)
                  .unlockCondition()
                  .apply(PlatformEquipment.getArtifactStack(entity, Artifacts.LionEmblem), player)
            ? level + 1
            : level;
      }
   }
}
