package team.lodestar.lodestone.systems.datagen.providers;

import java.util.function.Function;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class LodestoneItemModelProvider extends ItemModelProvider {
   private String texturePath = "";
   private Function<String, String> modelNameModifier;
   private Function<String, String> textureNameModifier;
   private Function<String, String> genericLocationModifier;

   public LodestoneItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
      super(output, modid, existingFileHelper);
   }

   public ItemModelBuilder getBuilder(String path) {
      if (this.modelNameModifier != null) {
         path = this.modelNameModifier.apply(path);
         this.modelNameModifier = null;
      }

      return (ItemModelBuilder)super.getBuilder(path);
   }

   public void setModelNameModifier(Function<String, String> modelNameModifier) {
      this.modelNameModifier = modelNameModifier;
   }

   public void setTextureNameModifier(Function<String, String> textureNameModifier) {
      this.textureNameModifier = textureNameModifier;
   }

   public void setGenericLocationModifier(Function<String, String> genericLocationModifier) {
      this.genericLocationModifier = genericLocationModifier;
   }

   public void setTexturePath(String texturePath) {
      if (!texturePath.isEmpty() && !texturePath.endsWith("/")) {
         texturePath = texturePath + "/";
      }

      this.texturePath = texturePath;
   }

   public String getTexturePath() {
      return this.texturePath;
   }

   public String getItemName(Item item) {
      return BuiltInRegistries.ITEM.getKey(item).getPath();
   }

   public ResourceLocation getItemTexture(String path) {
      String texture = path;
      if (this.textureNameModifier != null) {
         texture = this.textureNameModifier.apply(texture);
         this.textureNameModifier = null;
      }

      return this.modLoc("item/" + this.getTexturePath() + texture);
   }

   public ResourceLocation getBlockTexture(String path) {
      String texture = path;
      if (this.textureNameModifier != null) {
         texture = this.textureNameModifier.apply(texture);
         this.textureNameModifier = null;
      }

      return this.modLoc("block/" + LodestoneBlockStateProvider.getTexturePath() + texture);
   }

   public ResourceLocation modLoc(String name) {
      String path = name;
      if (this.textureNameModifier != null) {
         path = this.textureNameModifier.apply(path);
         this.textureNameModifier = null;
      }

      return super.modLoc(path);
   }

   public ItemModelBuilder createParentedModel(Item item, ResourceLocation modelParent) {
      return (ItemModelBuilder)this.getBuilder(this.getItemName(item)).parent(new UncheckedModelFile(modelParent));
   }

   public ItemModelBuilder createGenericModel(Item item, ResourceLocation modelParent, ResourceLocation... textures) {
      ItemModelBuilder itemModelBuilder = this.createParentedModel(item, modelParent);

      for (int i = 0; i < textures.length; i++) {
         itemModelBuilder.texture("layer" + i, textures[i]);
      }

      return itemModelBuilder;
   }

   public ResourceLocation getBlockTextureFromCache(String key) {
      return LodestoneBlockModelProvider.BLOCK_TEXTURE_CACHE.get(key);
   }
}
