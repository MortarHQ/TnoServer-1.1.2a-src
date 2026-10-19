package nadiendev.constructionwand.data;

import javax.annotation.Nonnull;
import nadiendev.constructionwand.items.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ItemModelGenerator extends ItemModelProvider {
   public ItemModelGenerator(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
      super(packOutput, "constructionwand", existingFileHelper);
   }

   protected void registerModels() {
      for (DeferredHolder<Item, ? extends Item> itemObject : ModItems.ITEMS.getEntries()) {
         Item item = (Item)itemObject.get();
         String name = itemObject.getId().getPath();
         if (item instanceof ICustomItemModel) {
            ((ICustomItemModel)item).generateCustomItemModel(this, name);
         } else if (item instanceof BlockItem) {
            this.withExistingParent(name, this.modLoc("block/" + name));
         } else {
            ((ItemModelBuilder)this.withExistingParent(name, "item/generated")).texture("layer0", "item/" + name);
         }
      }
   }

   @Nonnull
   public String getName() {
      return "ConstructionWandRevived item models";
   }
}
