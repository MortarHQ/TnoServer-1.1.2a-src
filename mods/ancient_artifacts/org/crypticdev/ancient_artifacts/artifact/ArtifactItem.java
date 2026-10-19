package org.crypticdev.ancient_artifacts.artifact;

import io.github.manasmods.tensura.registry.item.misc.TensuraDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.ancient_artifacts.AncientArtifacts;

public interface ArtifactItem {
   default ArtifactDefinition getArtifact() {
      throw new AssertionError();
   }

   default int getEvolution(ItemStack stack) {
      switch (this.getArtifact().defaultRefinement().grade()) {
         case 1:
            if (!stack.has((DataComponentType)TensuraDataComponents.EP.get())) {
               return 0;
            } else if ((Double)stack.get((DataComponentType)TensuraDataComponents.EP.get()) < AncientArtifacts.CONFIG.getGradeConfig(1).EffectUnlockEp.get(1)) {
               return 1;
            } else {
               if ((Double)stack.get((DataComponentType)TensuraDataComponents.EP.get()) < AncientArtifacts.CONFIG.getGradeConfig(1).EffectUnlockEp.get(2)) {
                  return 2;
               }

               return 3;
            }
         case 2:
            if (!stack.has((DataComponentType)TensuraDataComponents.EP.get())) {
               return 0;
            } else {
               if ((Double)stack.get((DataComponentType)TensuraDataComponents.EP.get()) < AncientArtifacts.CONFIG.getGradeConfig(2).EffectUnlockEp.get(1)) {
                  return 1;
               }

               return 2;
            }
         case 3:
            return 1;
         default:
            return 0;
      }
   }
}
