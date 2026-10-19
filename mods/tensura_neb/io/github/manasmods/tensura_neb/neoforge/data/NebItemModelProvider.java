package io.github.manasmods.tensura_neb.neoforge.data;

import io.github.manasmods.tensura_neb.registry.NebItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class NebItemModelProvider extends ItemModelProvider {
   public NebItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
      super(output, "tensura_neb", existingFileHelper);
   }

   protected void registerModels() {
      this.spawnEggItem((Item)NebItems.RIMURU_SPAWN_EGG.get());
      this.spawnEggItem((Item)NebItems.CARRION_SPAWN_EGG.get());
      this.spawnEggItem((Item)NebItems.LUMINOUS_SPAWN_EGG.get());
   }
}
