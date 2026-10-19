package org.crypticdev.ancient_artifacts.platform.neoforge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.ancient_artifacts.artifact.ArtifactDefinition;
import org.crypticdev.ancient_artifacts.artifact.ArtifactItem;
import org.crypticdev.ancient_artifacts.artifact.ArtifactSlot;
import org.crypticdev.ancient_artifacts.registry.ArtifactItems;
import org.crypticdev.ancient_artifacts.registry.Artifacts;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public class PlatformEquipmentImpl {
   public static boolean hasArtifact(LivingEntity livingEntity, ArtifactDefinition definition) {
      return !Artifacts.ALL.contains(definition)
         ? false
         : CuriosApi.getCuriosInventory(livingEntity).map(inventory -> inventory.isEquipped(ArtifactItems.get(definition))).orElse(false);
   }

   public static int amountOfGradeEquipped(LivingEntity entity, int grade) {
      AtomicInteger i = new AtomicInteger();
      ((ICuriosItemHandler)CuriosApi.getCuriosInventory(entity).get())
         .getCurios()
         .values()
         .forEach(
            iCurioStacksHandler -> {
               for (int j = 0; j < iCurioStacksHandler.getStacks().getSlots(); j++) {
                  if (iCurioStacksHandler.getStacks().getStackInSlot(j).getItem() instanceof ArtifactItem artifactItem
                     && artifactItem.getArtifact().defaultRefinement().grade() == grade) {
                     i.getAndIncrement();
                  }
               }
            }
         );
      return i.get();
   }

   public static ItemStack getArtifactStack(LivingEntity livingEntity, ArtifactDefinition definition) {
      return CuriosApi.getCuriosInventory(livingEntity)
         .flatMap(inventory -> inventory.findFirstCurio(ArtifactItems.get(definition)))
         .map(slotResult -> slotResult.stack())
         .orElse(ItemStack.EMPTY);
   }

   public static Map<Integer, ItemStack> getArtifactStacksinSlot(LivingEntity entity, ArtifactSlot artifactSlot) {
      Optional<ICuriosItemHandler> iCuriosItemHandlerOptional = CuriosApi.getCuriosInventory(entity);
      if (iCuriosItemHandlerOptional.isPresent()) {
         ICuriosItemHandler iCuriosItemHandler = iCuriosItemHandlerOptional.get();
         List<SlotResult> slotResults = iCuriosItemHandler.findCurios(new String[]{artifactSlot.curiosSlot()});
         Map<Integer, ItemStack> items = new HashMap<>(Map.of());
         slotResults.forEach(slotResult -> items.put(slotResult.slotContext().index(), slotResult.stack()));
         return items;
      } else {
         return new HashMap<>();
      }
   }

   public static ItemStack getArtifactStackinSlot(LivingEntity entity, int index, ArtifactSlot artifactSlot) {
      Optional<ICuriosItemHandler> iCuriosItemHandlerOptional = CuriosApi.getCuriosInventory(entity);
      if (iCuriosItemHandlerOptional.isPresent()) {
         ICuriosItemHandler iCuriosItemHandler = (ICuriosItemHandler)CuriosApi.getCuriosInventory(entity).get();
         SlotResult slotResult = (SlotResult)iCuriosItemHandler.findCurio(artifactSlot.curiosSlot(), index).get();
         return slotResult.stack();
      } else {
         return ItemStack.EMPTY;
      }
   }

   public static void setArtifactInSlot(LivingEntity entity, int index, ArtifactSlot artifactSlot, ItemStack itemStack) {
      Optional<ICuriosItemHandler> iCuriosItemHandlerOptional = CuriosApi.getCuriosInventory(entity);
      if (iCuriosItemHandlerOptional.isPresent()) {
         ICuriosItemHandler iCuriosItemHandler = (ICuriosItemHandler)CuriosApi.getCuriosInventory(entity).get();
         iCuriosItemHandler.setEquippedCurio(artifactSlot.curiosSlot(), index, itemStack);
      }
   }
}
