package io.github.Memoires.mysticism.neoforge.data;

import io.github.Memoires.mysticism.registry.block.MysticismBlocks;
import io.github.Memoires.mysticism.registry.item.MysticismMaterialItems;
import io.github.Memoires.mysticism.registry.item.MysticismMobDropItems;
import io.github.Memoires.mysticism.registry.item.MysticismSpawnEggs;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MysticismItemModelProvider extends ItemModelProvider {
   public MysticismItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
      super(output, "mysticism", existingFileHelper);
   }

   protected void registerModels() {
      this.generateMiscItems();
      this.generateSpawnEggs();
   }

   private void generateMiscItems() {
      this.basicItem((Item)MysticismMaterialItems.DARKNESS_CORE.get());
      this.basicItem((Item)MysticismMobDropItems.ICE_ESSENCE.get());
      this.basicItem((Item)MysticismMobDropItems.LIGHTNING_ESSENCE.get());
      this.basicItem((Item)MysticismMobDropItems.CRYPTID_ESSENCE.get());
      this.basicItem((Item)MysticismMobDropItems.FLAME_ESSENCE.get());
      this.basicItem((Item)MysticismBlocks.Items.ICE_ORE.get());
   }

   private void generateSpawnEggs() {
      this.spawnEggItem((Item)MysticismSpawnEggs.OKAMI.get());
      this.spawnEggItem((Item)MysticismSpawnEggs.MEMOIRES.get());
      this.spawnEggItem((Item)MysticismSpawnEggs.SHADOW_IMP.get());
   }
}
