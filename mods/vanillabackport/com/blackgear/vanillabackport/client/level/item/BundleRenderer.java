package com.blackgear.vanillabackport.client.level.item;

import com.blackgear.platform.client.v2.render.ItemRendererRegistry.Renderer;
import com.blackgear.platform.core.util.event.ResultHolder;
import com.blackgear.vanillabackport.common.registries.ModItems;
import com.blackgear.vanillabackport.core.VanillaBackport;
import com.google.common.collect.ImmutableSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class BundleRenderer implements Renderer {
   public static final Set<ItemLike> BUNDLES = Set.of(
      Items.BUNDLE,
      (ItemLike)ModItems.WHITE_BUNDLE.get(),
      (ItemLike)ModItems.ORANGE_BUNDLE.get(),
      (ItemLike)ModItems.MAGENTA_BUNDLE.get(),
      (ItemLike)ModItems.LIGHT_BLUE_BUNDLE.get(),
      (ItemLike)ModItems.YELLOW_BUNDLE.get(),
      (ItemLike)ModItems.LIME_BUNDLE.get(),
      (ItemLike)ModItems.PINK_BUNDLE.get(),
      (ItemLike)ModItems.GRAY_BUNDLE.get(),
      (ItemLike)ModItems.LIGHT_GRAY_BUNDLE.get(),
      (ItemLike)ModItems.CYAN_BUNDLE.get(),
      (ItemLike)ModItems.BLUE_BUNDLE.get(),
      (ItemLike)ModItems.BROWN_BUNDLE.get(),
      (ItemLike)ModItems.GREEN_BUNDLE.get(),
      (ItemLike)ModItems.RED_BUNDLE.get(),
      (ItemLike)ModItems.BLACK_BUNDLE.get(),
      (ItemLike)ModItems.PURPLE_BUNDLE.get()
   );
   private static final Map<ItemLike, ModelResourceLocation> BUNDLE_MODELS = buildModels();

   private static Map<ItemLike, ModelResourceLocation> buildModels() {
      Map<ItemLike, ModelResourceLocation> models = new HashMap<>();

      for (ItemLike item : BUNDLES) {
         models.put(item, create(item.asItem()));
      }

      return models;
   }

   private static ModelResourceLocation create(Item item) {
      return new ModelResourceLocation(VanillaBackport.resource(BuiltInRegistries.ITEM.getKey(item).getPath()), "inventory");
   }

   public boolean shouldUse() {
      return (Boolean)VanillaBackport.COMMON_CONFIG.hasUpdatedBundles.get();
   }

   public ResultHolder<BakedModel> renderFirstPerson(ItemStack stack, ItemDisplayContext context, ItemModelShaper shaper) {
      return ResultHolder.submit(shaper.getModelManager().getModel(BUNDLE_MODELS.get(stack.getItem())));
   }

   public ResultHolder<BakedModel> renderThirdPerson(ItemStack stack, ItemModelShaper shaper) {
      return ResultHolder.submit(shaper.getModelManager().getModel(BUNDLE_MODELS.get(stack.getItem())));
   }

   public Set<ModelResourceLocation> registerModels() {
      Set<ModelResourceLocation> models = ImmutableSet.of();
      return ImmutableSet.builder().addAll(models).addAll(BUNDLE_MODELS.values()).build();
   }
}
