package org.crypticdev.ancient_artifacts.artifact;

import io.github.manasmods.tensura.registry.item.misc.TensuraDataComponents;
import java.util.Optional;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.ancient_artifacts.AncientArtifacts;
import org.crypticdev.ancient_artifacts.component.RefinementComponent;
import org.crypticdev.ancient_artifacts.registry.ArtifactDataComponents;

public final class ArtifactRefinement {
   public static final int ANVIL_LEVEL_COST = 1;
   public static final int ANVIL_MATERIAL_COST = 1;

   private ArtifactRefinement() {
   }

   public static RefinementComponent get(ItemStack stack) {
      return (RefinementComponent)stack.getOrDefault(ArtifactDataComponents.REFINEMENT.get(), RefinementComponent.DEFAULT);
   }

   public static Optional<ItemStack> tryCombine(ItemStack base, ItemStack addition) {
      if (!base.isEmpty() && !addition.isEmpty()) {
         if (base.getItem() != addition.getItem()) {
            return Optional.empty();
         }

         if (!base.has(ArtifactDataComponents.REFINEMENT.get()) || !addition.has(ArtifactDataComponents.REFINEMENT.get())) {
            return Optional.empty();
         }

         if (base.has((DataComponentType)TensuraDataComponents.EP.get()) && base.get((DataComponentType)TensuraDataComponents.EP.get()) != null) {
            RefinementComponent baseRefinement = get(base);
            RefinementComponent additionRefinement = get(addition);
            double ep = (Double)base.get((DataComponentType)TensuraDataComponents.EP.get());
            switch (baseRefinement.grade()) {
               case 1:
                  if (ep < AncientArtifacts.CONFIG.getGradeConfig(1).EffectUnlockEp.get(2)) {
                     return Optional.empty();
                  }
               case 2:
                  if (ep < AncientArtifacts.CONFIG.getGradeConfig(2).EffectUnlockEp.get(1)) {
                     return Optional.empty();
                  }
               case 3:
                  if (ep < AncientArtifacts.CONFIG.getGradeConfig(3).EffectUnlockEp.get(0)) {
                     return Optional.empty();
                  }
               default:
                  if (baseRefinement.refinement() + additionRefinement.refinement() + 1 > RefinementComponent.GRADES.get(baseRefinement.grade())) {
                     return Optional.empty();
                  } else {
                     System.out.println("working");
                     ItemStack result = base.copy();
                     RefinementComponent newRefinement = baseRefinement.addRefinement(base, additionRefinement);
                     if (newRefinement == baseRefinement) {
                        System.out.println(newRefinement.refinement());
                        System.out.println(baseRefinement.refinement());
                        return Optional.empty();
                     } else {
                        result.set(ArtifactDataComponents.REFINEMENT.get(), newRefinement);
                        return Optional.of(result);
                     }
                  }
            }
         } else {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }
}
