package fuzs.illagerinvasion.neoforge;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.data.ModRecipeProvider;
import fuzs.illagerinvasion.data.ModTrimMaterialDataProvider;
import fuzs.illagerinvasion.data.loot.ModBlockLootProvider;
import fuzs.illagerinvasion.data.loot.ModChestLootProvider;
import fuzs.illagerinvasion.data.loot.ModEntityInjectLootProvider;
import fuzs.illagerinvasion.data.loot.ModEntityTypeLootProvider;
import fuzs.illagerinvasion.data.tags.ModBiomeTagProvider;
import fuzs.illagerinvasion.data.tags.ModBlockTagProvider;
import fuzs.illagerinvasion.data.tags.ModEnchantmentTagProvider;
import fuzs.illagerinvasion.data.tags.ModEntityTypeTagProvider;
import fuzs.illagerinvasion.data.tags.ModInstrumentTagProvider;
import fuzs.illagerinvasion.data.tags.ModItemTagProvider;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import fuzs.puzzleslib.neoforge.api.data.v2.core.NeoForgeDataProviderContext.Factory;
import net.neoforged.fml.common.Mod;

@Mod("illagerinvasion")
public class IllagerInvasionNeoForge {
   public IllagerInvasionNeoForge() {
      ModConstructor.construct("illagerinvasion", IllagerInvasion::new);
      DataProviderHelper.registerDataProviders(
         "illagerinvasion",
         new Factory[]{
            ModBiomeTagProvider::new,
            ModBlockTagProvider::new,
            ModEnchantmentTagProvider::new,
            ModEntityTypeTagProvider::new,
            ModInstrumentTagProvider::new,
            ModItemTagProvider::new,
            ModBlockLootProvider::new,
            ModChestLootProvider::new,
            ModEntityInjectLootProvider::new,
            ModEntityTypeLootProvider::new,
            ModRecipeProvider::new,
            ModTrimMaterialDataProvider::new
         }
      );
   }
}
