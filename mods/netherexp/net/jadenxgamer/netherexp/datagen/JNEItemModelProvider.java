package net.jadenxgamer.netherexp.datagen;

import java.util.Objects;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class JNEItemModelProvider extends ItemModelProvider {
   public JNEItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
      super(output, "netherexp", existingFileHelper);
   }

   protected void registerModels() {
      this.basicItemWithBlockPath(JNEBlocks.DAMP_SILTMARRAM.get().asItem());
      this.basicItemWithBlockPath(JNEBlocks.MOIST_SILTMARRAM.get().asItem());
      this.basicItemWithBlockPath(JNEBlocks.DRY_SILTMARRAM.get().asItem());
   }

   public ItemModelBuilder basicItemWithBlockPath(Item item) {
      return this.basicItemWithBlockPath(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)));
   }

   public ItemModelBuilder basicItemWithBlockPath(ResourceLocation item) {
      return (ItemModelBuilder)((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(item.toString())).parent(new UncheckedModelFile("item/generated")))
         .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "block/" + item.getPath()));
   }
}
