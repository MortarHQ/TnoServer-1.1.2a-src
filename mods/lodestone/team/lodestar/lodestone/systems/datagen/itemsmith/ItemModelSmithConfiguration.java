package team.lodestar.lodestone.systems.datagen.itemsmith;

import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.world.item.Item;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneItemModelProvider;

public class ItemModelSmithConfiguration extends ItemModelSmith {
   private Consumer<ItemModelSmithResult> modifier;
   private Function<String, String> modelNameModifier;
   private Function<String, String> textureNameModifier;

   public ItemModelSmithConfiguration(ItemModelSmith.ItemModelSupplier modelSupplier) {
      super(modelSupplier);
   }

   @Override
   public ItemModelSmithConfiguration modifyResult(Consumer<ItemModelSmithResult> modifier) {
      this.modifier = modifier;
      return this;
   }

   @Override
   public ItemModelSmithConfiguration addModelNameAffix(String affix) {
      return this.modifyModelName(s -> s + affix);
   }

   @Override
   public ItemModelSmithConfiguration modifyModelName(Function<String, String> modelNameModifier) {
      this.modelNameModifier = modelNameModifier;
      return this;
   }

   @Override
   public ItemModelSmithConfiguration addTextureNameAffix(String affix) {
      return this.modifyTextureName(s -> s + affix);
   }

   @Override
   public ItemModelSmithConfiguration modifyTextureName(Function<String, String> textureNameModifier) {
      this.textureNameModifier = textureNameModifier;
      return this;
   }

   @Override
   protected void preDatagen(LodestoneItemModelProvider provider, Item item) {
      provider.setModelNameModifier(this.modelNameModifier);
      provider.setTextureNameModifier(this.textureNameModifier);
   }

   @Override
   protected void postDatagen(ItemModelSmithResult result) {
      if (this.modifier != null) {
         result.applyModifier(this.modifier);
      }

      LodestoneItemModelProvider provider = result.getProvider();
      provider.setTextureNameModifier(null);
      provider.setModelNameModifier(null);
   }
}
