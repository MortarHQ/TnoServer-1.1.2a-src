package org.crypticdev.ancient_artifacts.artifact;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.function.ToBooleanBiFunction;
import org.crypticdev.ancient_artifacts.AncientArtifacts;

public class ArtifactUnlockCondition {
   private final List<ToBooleanBiFunction<ItemStack, Player>> unlock_conditions = new ArrayList<>();

   public ArtifactUnlockCondition addEPCondition(int grade, int ability) {
      return this.addCondition((stack, player) -> ArtifactEffects.EpUnlock(stack, AncientArtifacts.CONFIG.getGradeConfig(grade).EffectUnlockEp.get(ability)));
   }

   public ArtifactUnlockCondition addCondition(ToBooleanBiFunction<ItemStack, Player> condition) {
      this.unlock_conditions.add(condition);
      return this;
   }

   public boolean apply(ItemStack stack, Player player) {
      for (ToBooleanBiFunction<ItemStack, Player> condition : this.unlock_conditions) {
         if (!condition.applyAsBoolean(stack, player)) {
            return false;
         }
      }

      return true;
   }
}
