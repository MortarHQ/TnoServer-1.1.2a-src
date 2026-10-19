package artifacts.integration.trinkets;

import artifacts.equipment.EquipmentSlotManager;
import artifacts.event.ArtifactHooks;
import artifacts.item.WearableArtifactItem;
import artifacts.platform.PlatformServices;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.event.TrinketEquipCallback;
import dev.emi.trinkets.api.event.TrinketUnequipCallback;
import net.minecraft.world.item.ItemStack;

public class TrinketsCompat {
   public static void setup() {
      if (!PlatformServices.platformHelper.isModLoaded("tclayer")) {
         EquipmentSlotManager.register(new TrinketsSlotProvider());
      }

      PlatformServices.platformHelper.addItemRegistryCallback(item -> {
         if (item instanceof WearableArtifactItem wearableArtifactItem) {
            TrinketsApi.registerTrinket(item, new WearableArtifactTrinket(wearableArtifactItem));
         }
      });
      TrinketEquipCallback.EVENT.register((TrinketEquipCallback)(stack, slot, entity) -> ArtifactHooks.onItemChanged(entity, ItemStack.EMPTY, stack));
      TrinketUnequipCallback.EVENT.register((TrinketUnequipCallback)(stack, slot, entity) -> ArtifactHooks.onItemChanged(entity, stack, ItemStack.EMPTY));
   }
}
