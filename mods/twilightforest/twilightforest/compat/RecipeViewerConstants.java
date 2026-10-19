package twilightforest.compat;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mezz.jei.library.plugins.vanilla.crafting.JeiShapedRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import twilightforest.config.TFConfig;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFDataMaps;
import twilightforest.init.TFItems;
import twilightforest.init.TFRecipes;
import twilightforest.inventory.UncraftingMenu;
import twilightforest.util.datamaps.CrumbledBlock;
import twilightforest.util.datamaps.EntityTransformation;

public class RecipeViewerConstants {
   public static final int GENERIC_RECIPE_WIDTH = 116;
   public static final int GENERIC_RECIPE_HEIGHT = 54;
   public static final Component MOONWORM_QUEEN_TOOLTIP = Component.translatable("item.twilightforest.moonworm_queen.jei_info_message")
      .withStyle(ChatFormatting.GREEN);
   public static final ItemStack DAMAGED_MOONWORM_QUEEN = (ItemStack)Util.make(
      new ItemStack((ItemLike)TFItems.MOONWORM_QUEEN.get()), stack -> stack.setDamageValue(256)
   );
   public static final List<ItemStack> BERRY_2_LIST = List.of(
      ItemStack.EMPTY,
      new ItemStack((ItemLike)TFItems.TORCHBERRIES.get()),
      new ItemStack((ItemLike)TFItems.TORCHBERRIES.get()),
      new ItemStack((ItemLike)TFItems.TORCHBERRIES.get())
   );
   public static final List<ItemStack> BERRY_3_LIST = List.of(
      ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack((ItemLike)TFItems.TORCHBERRIES.get()), new ItemStack((ItemLike)TFItems.TORCHBERRIES.get())
   );
   public static final List<ItemStack> BERRY_4_LIST = List.of(
      ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack((ItemLike)TFItems.TORCHBERRIES.get())
   );
   public static final List<ItemStack> MOONWORM_QUEEN_LIST = List.of(
      (ItemStack)Util.make(new ItemStack((ItemLike)TFItems.MOONWORM_QUEEN.get()), stack -> stack.setDamageValue(192)),
      (ItemStack)Util.make(new ItemStack((ItemLike)TFItems.MOONWORM_QUEEN.get()), stack -> stack.setDamageValue(128)),
      (ItemStack)Util.make(new ItemStack((ItemLike)TFItems.MOONWORM_QUEEN.get()), stack -> stack.setDamageValue(64)),
      new ItemStack((ItemLike)TFItems.MOONWORM_QUEEN.get())
   );

   public static List<RecipeHolder<? extends CraftingRecipe>> getAllUncraftingRecipes(RecipeManager manager) {
      if (!TFConfig.disableUncraftingOnly) {
         List<RecipeHolder<? extends CraftingRecipe>> recipes = new ArrayList<>(manager.getAllRecipesFor(RecipeType.CRAFTING));
         recipes = recipes.stream()
            .filter(
               recipe -> !((CraftingRecipe)recipe.value()).getResultItem(Minecraft.getInstance().level.registryAccess()).isEmpty()
                  && !((CraftingRecipe)recipe.value()).getResultItem(Minecraft.getInstance().level.registryAccess()).is(ItemTagGenerator.BANNED_UNCRAFTABLES)
                  && TFConfig.reverseRecipeBlacklist == TFConfig.disableUncraftingRecipes.contains(recipe.id().toString())
                  && TFConfig.flipUncraftingModIdList == TFConfig.blacklistedUncraftingModIds.contains(recipe.id().getNamespace())
            )
            .collect(Collectors.toList());
         recipes.removeIf(recipe -> recipe.value() instanceof ShapelessRecipe && !TFConfig.allowShapelessUncrafting);
         recipes.addAll(manager.getAllRecipesFor((RecipeType)TFRecipes.UNCRAFTING_RECIPE.get()));
         return recipes;
      } else {
         return new ArrayList<>(manager.getAllRecipesFor((RecipeType)TFRecipes.UNCRAFTING_RECIPE.get()));
      }
   }

   public static List<RecipeViewerConstants.TransformationPowderInfo> getTransformationPowderRecipes() {
      List<EntityType<?>> inputs = new ArrayList<>();
      List<RecipeViewerConstants.TransformationPowderInfo> info = new ArrayList<>();

      for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {
         if (type.builtInRegistryHolder().getData(TFDataMaps.TRANSFORMATION_POWDER) != null) {
            inputs.add(type);
         }
      }

      for (EntityType<?> input : new ArrayList<>(inputs)) {
         EntityTransformation output = (EntityTransformation)input.builtInRegistryHolder().getData(TFDataMaps.TRANSFORMATION_POWDER);
         if (output != null) {
            RecipeViewerConstants.TransformationPowderInfo dummy = new RecipeViewerConstants.TransformationPowderInfo(output.result(), input, true);
            if (!info.contains(dummy)) {
               if (inputs.contains(output.result())) {
                  info.add(new RecipeViewerConstants.TransformationPowderInfo(input, output.result(), true));
               } else {
                  info.add(new RecipeViewerConstants.TransformationPowderInfo(input, output.result(), false));
               }
            }
         }
      }

      return info;
   }

   public static List<RecipeViewerConstants.OminousFireInfo> getOminousFireRecipes() {
      List<EntityType<?>> inputs = new ArrayList<>();
      List<RecipeViewerConstants.OminousFireInfo> info = new ArrayList<>();

      for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {
         if (type.builtInRegistryHolder().getData(TFDataMaps.OMINOUS_FIRE) != null) {
            inputs.add(type);
         }
      }

      for (EntityType<?> input : new ArrayList<>(inputs)) {
         EntityTransformation output = (EntityTransformation)input.builtInRegistryHolder().getData(TFDataMaps.OMINOUS_FIRE);
         if (output != null) {
            RecipeViewerConstants.OminousFireInfo dummy = new RecipeViewerConstants.OminousFireInfo(output.result(), input);
            if (!info.contains(dummy)) {
               info.add(new RecipeViewerConstants.OminousFireInfo(input, output.result()));
            }
         }
      }

      return info;
   }

   public static List<Pair<Block, Block>> getCrumbleHornRecipes() {
      List<Pair<Block, Block>> info = new ArrayList<>();

      for (Block input : BuiltInRegistries.BLOCK) {
         CrumbledBlock output = (CrumbledBlock)input.builtInRegistryHolder().getData(TFDataMaps.CRUMBLE_HORN);
         if (output != null) {
            info.add(Pair.of(input, output.result()));
         }
      }

      return info;
   }

   public static int getRecipeCost(List<ItemStack> inputs) {
      int cost = 0;

      for (ItemStack stack : inputs) {
         if (UncraftingMenu.isDamageableComponent(stack) && !UncraftingMenu.isIngredientProblematic(stack) && !UncraftingMenu.isMarked(stack)) {
            cost++;
         }
      }

      return cost;
   }

   public static int getXPColor(int cost) {
      return Minecraft.getInstance().player.experienceLevel < cost && !Minecraft.getInstance().player.getAbilities().instabuild ? 10485760 : 8453920;
   }

   public static Component getDryingTime(int dryingTicks) {
      int dryingMinutes = dryingTicks / 60 / 20;
      int dryingSeconds = dryingTicks / 20 % 60;
      Component time;
      if (dryingMinutes > 0) {
         if (dryingSeconds > 0) {
            time = Component.translatable("gui.twilightforest.drying_time", new Object[]{dryingMinutes, dryingSeconds});
         } else if (dryingMinutes == 1) {
            time = Component.translatable("gui.twilightforest.drying_minute", new Object[]{dryingMinutes});
         } else {
            time = Component.translatable("gui.twilightforest.drying_minutes", new Object[]{dryingMinutes});
         }
      } else if (dryingSeconds == 1) {
         time = Component.translatable("gui.twilightforest.drying_second", new Object[]{dryingSeconds});
      } else {
         time = Component.translatable("gui.twilightforest.drying_seconds", new Object[]{dryingSeconds});
      }

      return time;
   }

   public static void renderFlatBlock(PoseStack stack, BlockState state, Vec3 location, float scale) {
      Minecraft minecraft = Minecraft.getInstance();
      BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
      stack.pushPose();
      Lighting.setupForFlatItems();
      stack.translate(location.x(), location.y(), location.z());
      stack.scale(scale, -scale, scale);
      minecraft.getBlockRenderer().renderSingleBlock(state, stack, bufferSource, 15728880, OverlayTexture.NO_OVERLAY);
      stack.popPose();
      bufferSource.endBatch();
      Lighting.setupFor3DItems();
   }

   public static int getCraftingIndex(CraftingRecipe recipe, int i) {
      int width = getRecipeWidth(recipe);
      int height = getRecipeHeight(recipe);
      int index;
      if (width == 1) {
         if (height == 3) {
            index = i * 3 + 1;
         } else if (height == 2) {
            index = i * 3 + 1;
         } else {
            index = 4;
         }
      } else if (height == 1) {
         index = i + 3;
      } else if (width == 2) {
         index = i;
         if (i > 1) {
            index++;
            if (i > 3) {
               index++;
            }
         }
      } else if (height == 2) {
         index = i + 3;
      } else {
         index = i;
      }

      return index;
   }

   public static int getRecipeWidth(CraftingRecipe recipe) {
      if (recipe instanceof ShapedRecipe shapedRecipe) {
         return shapedRecipe.getWidth();
      } else {
         return recipe instanceof JeiShapedRecipe shapedRecipe ? shapedRecipe.getWidth() : 0;
      }
   }

   public static int getRecipeHeight(CraftingRecipe recipe) {
      if (recipe instanceof ShapedRecipe shapedRecipe) {
         return shapedRecipe.getHeight();
      } else {
         return recipe instanceof JeiShapedRecipe shapedRecipe ? shapedRecipe.getHeight() : 0;
      }
   }

   public record OminousFireInfo(EntityType<?> input, EntityType<?> output) {
   }

   public record TransformationPowderInfo(EntityType<?> input, EntityType<?> output, boolean reversible) {
   }
}
