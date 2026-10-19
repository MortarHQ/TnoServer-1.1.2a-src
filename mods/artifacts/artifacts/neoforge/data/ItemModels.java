package artifacts.neoforge.data;

import artifacts.Artifacts;
import artifacts.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProvider {
   public ItemModels(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
      super(packOutput, "artifacts", existingFileHelper);
   }

   protected void registerModels() {
      BuiltInRegistries.ITEM
         .stream()
         .filter(item -> BuiltInRegistries.ITEM.getKey(item).getNamespace().equals("artifacts"))
         .filter(item -> item != ModItems.MIMIC_SPAWN_EGG.value())
         .filter(item -> item != ModItems.UMBRELLA.value())
         .forEach(this::addGeneratedModel);
   }

   private void addGeneratedModel(Item item) {
      String name = BuiltInRegistries.ITEM.getKey(item).getPath();
      ((ItemModelBuilder)this.withExistingParent("item/" + name, "item/generated")).texture("layer0", Artifacts.id("item/%s", name));
   }
}
