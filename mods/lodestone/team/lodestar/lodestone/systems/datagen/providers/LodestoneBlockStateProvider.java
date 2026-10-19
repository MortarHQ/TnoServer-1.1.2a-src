package team.lodestar.lodestone.systems.datagen.providers;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel.Builder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import team.lodestar.lodestone.systems.datagen.statesmith.ModularBlockStateSmith;

public abstract class LodestoneBlockStateProvider extends BlockStateProvider {
   public final Set<ResourceLocation> staticTextures = new HashSet<>();
   private final LodestoneBlockModelProvider blockModels;
   public final LodestoneItemModelProvider itemModelProvider;
   private static String texturePath = "";

   public LodestoneBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper, LodestoneItemModelProvider itemModelProvider) {
      super(output, modid, exFileHelper);
      this.itemModelProvider = itemModelProvider;
      this.blockModels = new LodestoneBlockModelProvider(this, output, modid, exFileHelper);
   }

   public LodestoneBlockModelProvider models() {
      return this.blockModels;
   }

   public LodestoneItemModelProvider itemModels() {
      return this.itemModelProvider;
   }

   public void setTexturePath(String texturePath) {
      if (!texturePath.isEmpty() && !texturePath.endsWith("/")) {
         texturePath = texturePath + "/";
      }

      LodestoneBlockStateProvider.texturePath = texturePath;
   }

   public static String getTexturePath() {
      return texturePath;
   }

   public ModularBlockStateSmith.ModelFileSupplier fromFunction(BiFunction<String, ResourceLocation, ModelFile> modelFileFunction) {
      return b -> {
         String name = this.getBlockName(b);
         return modelFileFunction.apply(name, this.getBlockTexture(name));
      };
   }

   public void varyingRotationBlock(Block block, ModelFile model) {
      Builder<VariantBlockStateBuilder> builder = this.getVariantBuilder(block)
         .partialState()
         .modelForState()
         .modelFile(model)
         .nextModel()
         .modelFile(model)
         .rotationY(90)
         .nextModel()
         .modelFile(model)
         .rotationY(180)
         .nextModel()
         .modelFile(model)
         .rotationY(270);
      this.simpleBlock(block, builder.build());
   }

   public ModelFile predefinedModel(Block block) {
      return this.models().getExistingFile(BuiltInRegistries.BLOCK.getKey(block));
   }

   public ModelFile predefinedModel(Block block, String extension) {
      return this.models().getExistingFile(this.extend(BuiltInRegistries.BLOCK.getKey(block), extension));
   }

   public ModelFile grassBlockModel(Block block) {
      String name = this.getBlockName(block);
      ResourceLocation side = this.getBlockTexture(name);
      ResourceLocation dirt = ResourceLocation.withDefaultNamespace("block/dirt");
      ResourceLocation top = this.getBlockTexture(name + "_top");
      return this.models().cubeBottomTop(name, side, dirt, top);
   }

   public ModelFile leavesBlockModel(Block block) {
      String name = this.getBlockName(block);
      return ((BlockModelBuilder)this.models().withExistingParent(name, ResourceLocation.withDefaultNamespace("block/leaves")))
         .texture("all", this.getBlockTexture(name));
   }

   public ModelFile airModel(Block block) {
      String name = this.getBlockName(block);
      return this.models().withExistingParent(name, ResourceLocation.withDefaultNamespace("block/air"));
   }

   public ModelFile cubeModelAirTexture(Block block) {
      String name = this.getBlockName(block);
      return this.models().cubeAll(name, ResourceLocation.withDefaultNamespace("block/air"));
   }

   public String getBlockName(Block block) {
      return BuiltInRegistries.BLOCK.getKey(block).getPath();
   }

   public ResourceLocation getBlockTexture(String path) {
      return this.modLoc("block/" + path);
   }

   public ResourceLocation getStaticBlockTexture(String path) {
      return this.markTextureAsStatic(this.getBlockTexture(path));
   }

   public ResourceLocation markTextureAsStatic(ResourceLocation texture) {
      this.staticTextures.add(texture);
      return texture;
   }

   public ResourceLocation extend(ResourceLocation resourceLocation, String suffix) {
      return ResourceLocation.fromNamespaceAndPath(resourceLocation.getNamespace(), resourceLocation.getPath() + suffix);
   }
}
