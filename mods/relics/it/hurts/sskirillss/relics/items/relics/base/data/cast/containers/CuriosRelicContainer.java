package it.hurts.sskirillss.relics.items.relics.base.data.cast.containers;

import it.hurts.sskirillss.relics.init.RelicContainerRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.containers.base.RelicContainer;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.system.casts.abilities.AbilityReference;
import it.hurts.sskirillss.relics.system.casts.slots.CurioSlotReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public class CuriosRelicContainer extends RelicContainer {
   @Override
   public Function<LivingEntity, List<ItemStack>> gatherRelics() {
      return entity -> {
         List<ItemStack> relics = new ArrayList<>();
         CuriosApi.getCuriosInventory(entity).ifPresent(itemHandler -> {
            for (Entry<String, ICurioStacksHandler> entry : itemHandler.getCurios().entrySet()) {
               ICurioStacksHandler stacksHandler = entry.getValue();

               for (int slot = 0; slot < stacksHandler.getSlots(); slot++) {
                  ItemStack stack = stacksHandler.getStacks().getStackInSlot(slot);
                  if (stack.getItem() instanceof IRelicItem) {
                     relics.add(stack);
                  }
               }
            }
         });
         return relics;
      };
   }

   @Override
   public Function<LivingEntity, List<AbilityReference>> gatherAbilities() {
      return entity -> {
         List<AbilityReference> references = new ArrayList<>();
         if (entity instanceof Player player) {
            CuriosApi.getCuriosInventory(entity).ifPresent(itemHandler -> {
               for (Entry<String, ICurioStacksHandler> entry : itemHandler.getCurios().entrySet()) {
                  ICurioStacksHandler stacksHandler = entry.getValue();

                  for (int slot = 0; slot < stacksHandler.getSlots(); slot++) {
                     ItemStack stack = stacksHandler.getStacks().getStackInSlot(slot);
                     if (stack.getItem() instanceof IRelicItem relic) {
                        for (AbilityData abilityData : relic.getRelicData().getAbilities().getAbilities().values()) {
                           String id = abilityData.getId();
                           if (relic.isAbilityUnlocked(stack, id) && relic.canPlayerSeeAbility(player, stack, id)) {
                              CastData castData = abilityData.getCastData();
                              if (castData.getType() != CastType.NONE && castData.getContainers().contains(RelicContainerRegistry.CURIOS.get())) {
                                 references.add(new AbilityReference(abilityData.getId(), new CurioSlotReference(slot, entry.getKey())));
                              }
                           }
                        }
                     }
                  }
               }
            });
            return references;
         } else {
            return references;
         }
      };
   }
}
