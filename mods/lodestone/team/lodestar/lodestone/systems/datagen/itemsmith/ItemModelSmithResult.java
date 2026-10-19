package team.lodestar.lodestone.systems.datagen.itemsmith;

import java.util.function.Consumer;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.loaders.ItemLayerModelBuilder;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneItemModelProvider;

public class ItemModelSmithResult {
   private final LodestoneItemModelProvider provider;
   private final Item item;
   private final ItemModelBuilder builder;

   public ItemModelSmithResult(LodestoneItemModelProvider provider, Item item, ItemModelBuilder builder) {
      this.provider = provider;
      this.item = item;
      this.builder = builder;
   }

   public LodestoneItemModelProvider getProvider() {
      return this.provider;
   }

   public Item getItem() {
      return this.item;
   }

   public ItemModelBuilder getBuilder() {
      return this.builder;
   }

   public ItemModelBuilder parentedToThis(ExistingFileHelper existingFileHelper) {
      return (ItemModelBuilder)new ItemModelBuilder(this.builder.getLocation(), existingFileHelper).parent(this.builder);
   }

   public ItemModelBuilder parentedToThis(ExistingFileHelper existingFileHelper, String childName) {
      return (ItemModelBuilder)new ItemModelBuilder(this.builder.getLocation().withSuffix("_" + childName), existingFileHelper).parent(this.builder);
   }

   public ItemLayerModelBuilder<ItemModelBuilder> addModelLayerData() {
      return (ItemLayerModelBuilder<ItemModelBuilder>)this.builder.customLoader(ItemLayerModelBuilder::begin);
   }

   public SeparateTransformsModelBuilder<ItemModelBuilder> addSeparateTransformData() {
      return (SeparateTransformsModelBuilder<ItemModelBuilder>)this.builder.customLoader(SeparateTransformsModelBuilder::begin);
   }

   public ItemModelSmithResult applyModifier(Consumer<ItemModelSmithResult> modifier) {
      modifier.accept(this);
      return this;
   }
}
