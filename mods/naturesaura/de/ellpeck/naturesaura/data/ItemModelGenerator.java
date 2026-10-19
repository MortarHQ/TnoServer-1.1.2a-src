package de.ellpeck.naturesaura.data;

import de.ellpeck.naturesaura.reg.ICustomItemModel;
import de.ellpeck.naturesaura.reg.INoItemBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelGenerator extends ItemModelProvider {
   public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
      super(output, "naturesaura", existingFileHelper);
   }

   protected void registerModels() {
      ModData.getAllModItems().forEach(i -> {
         String name = i.getBaseName();
         if (i instanceof ICustomItemModel) {
            ((ICustomItemModel)i).generateCustomItemModel(this);
         } else if (i instanceof Item) {
            ((ItemModelBuilder)this.withExistingParent(name, "item/generated")).texture("layer0", "item/" + name);
         } else if (i instanceof Block && !(i instanceof INoItemBlock)) {
            this.withExistingParent(name, this.modLoc("block/" + name));
         }
      });
   }

   public String getName() {
      return "Nature's Aura Item Models";
   }
}
