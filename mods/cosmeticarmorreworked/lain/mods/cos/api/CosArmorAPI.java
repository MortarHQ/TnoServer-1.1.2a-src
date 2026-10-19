package lain.mods.cos.api;

import java.util.UUID;
import lain.mods.cos.api.inventory.CAStacksBase;
import lain.mods.cos.impl.ModObjects;

public class CosArmorAPI {
   public static CAStacksBase getCAStacks(UUID uuid) {
      return ModObjects.invMan.getCosArmorInventory(uuid);
   }

   public static CAStacksBase getCAStacksClient(UUID uuid) {
      return ModObjects.invMan.getCosArmorInventoryClient(uuid);
   }
}
