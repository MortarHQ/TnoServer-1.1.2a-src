package mezz.jei.library.plugins.debug;

import com.mojang.blaze3d.platform.InputConstants.Key;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.inputs.IJeiInputHandler;
import mezz.jei.api.gui.inputs.IJeiUserInput;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.runtime.IBookmarkOverlay;
import mezz.jei.api.runtime.IIngredientFilter;
import mezz.jei.api.runtime.IIngredientListOverlay;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.common.Internal;
import mezz.jei.common.gui.textures.Textures;
import mezz.jei.library.plugins.debug.ingredients.DebugIngredient;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class DebugRecipeCategory<F> implements IRecipeCategory<DebugRecipe> {
   public static final RecipeType<DebugRecipe> TYPE = RecipeType.create("jei", "debug", DebugRecipe.class);
   public static final int RECIPE_WIDTH = 160;
   public static final int RECIPE_HEIGHT = 60;
   private final IPlatformFluidHelper<F> platformFluidHelper;
   private final IIngredientManager ingredientManager;
   private final Component localizedName;
   private final IDrawable tankBackground;
   private final IDrawable tankOverlay;
   private final IDrawable item;
   @Nullable
   private IJeiRuntime runtime;
   private boolean hiddenRecipes;

   public DebugRecipeCategory(IGuiHelper guiHelper, IPlatformFluidHelper<F> platformFluidHelper, IIngredientManager ingredientManager) {
      this.platformFluidHelper = platformFluidHelper;
      this.ingredientManager = ingredientManager;
      this.localizedName = Component.literal("debug");
      ResourceLocation backgroundTexture = ResourceLocation.fromNamespaceAndPath("jei", "textures/jei/gui/debug.png");
      this.tankBackground = guiHelper.createDrawable(backgroundTexture, 220, 196, 18, 60);
      this.tankOverlay = guiHelper.createDrawable(backgroundTexture, 238, 196, 18, 60);
      this.item = guiHelper.createDrawableItemStack(new ItemStack(Items.ACACIA_LEAVES));
   }

   public void setRuntime(IJeiRuntime runtime) {
      this.runtime = runtime;
   }

   @Override
   public RecipeType<DebugRecipe> getRecipeType() {
      return TYPE;
   }

   @Override
   public Component getTitle() {
      return this.localizedName;
   }

   @Override
   public int getWidth() {
      return 160;
   }

   @Override
   public int getHeight() {
      return 60;
   }

   @Override
   public IDrawable getIcon() {
      Textures textures = Internal.getTextures();
      return textures.getConfigButtonIcon();
   }

   public void draw(DebugRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
      if (this.runtime != null) {
         this.item.draw(guiGraphics, 50, 20);
         IIngredientFilter ingredientFilter = this.runtime.getIngredientFilter();
         Minecraft minecraft = Minecraft.getInstance();
         guiGraphics.drawString(minecraft.font, ingredientFilter.getFilterText(), 20, 52, 0, false);
         IIngredientListOverlay ingredientListOverlay = this.runtime.getIngredientListOverlay();
         Optional<ITypedIngredient<?>> ingredientUnderMouse = getIngredientUnderMouse(ingredientListOverlay, this.runtime.getBookmarkOverlay());
         ingredientUnderMouse.ifPresent(typedIngredient -> this.drawIngredientName(minecraft, guiGraphics, (ITypedIngredient<?>)typedIngredient));
      }

      Button button = recipe.getButton();
      button.render(guiGraphics, (int)mouseX, (int)mouseY, 0.0F);
   }

   private static Optional<ITypedIngredient<?>> getIngredientUnderMouse(IIngredientListOverlay ingredientListOverlay, IBookmarkOverlay bookmarkOverlay) {
      return ingredientListOverlay.getIngredientUnderMouse().or(bookmarkOverlay::getIngredientUnderMouse);
   }

   private <T> void drawIngredientName(Minecraft minecraft, GuiGraphics guiGraphics, ITypedIngredient<T> ingredient) {
      IIngredientHelper<T> ingredientHelper = this.ingredientManager.getIngredientHelper(ingredient.getType());
      String serialized = ingredientHelper.getResourceLocation(ingredient.getIngredient()).toString();
      guiGraphics.drawString(minecraft.font, serialized, 50, 52, 0, false);
   }

   public void setRecipe(IRecipeLayoutBuilder builder, DebugRecipe recipe, IFocusGroup focuses) {
      builder.addOutputSlot(70, 0).addItemStack(new ItemStack(Items.FARMLAND)).addItemStack(new ItemStack(Items.BUNDLE));
      builder.addInputSlot(110, 0).addIngredientsUnsafe(Arrays.asList(new ItemStack(Items.RABBIT), null));
      long bucketVolume = this.platformFluidHelper.bucketVolume();
      IIngredientType<F> fluidType = this.platformFluidHelper.getFluidIngredientType();
      long capacity = 10L * bucketVolume;
      long amount = capacity / 2L + (int)(Math.random() * capacity / 2.0);
      builder.addOutputSlot(90, 0)
         .setFluidRenderer(capacity, false, 16, 58)
         .setOverlay(this.tankOverlay, -1, -1)
         .setBackground(this.tankBackground, -1, -1)
         .addFluidStack(Fluids.WATER, amount);
      capacity = 2L * bucketVolume;
      amount = capacity / 2L + (int)(Math.random() * capacity / 2.0);
      builder.addInputSlot(24, 0)
         .setFluidRenderer(capacity, true, 12, 47)
         .addIngredient(fluidType, this.platformFluidHelper.create(Fluids.LAVA.defaultFluidState().holder(), amount));
      builder.addInputSlot(40, 0).addIngredients(DebugIngredient.TYPE, List.of(new DebugIngredient(0), new DebugIngredient(1)));
      builder.addOutputSlot(40, 16).addIngredient(DebugIngredient.TYPE, new DebugIngredient(2));
      builder.addInputSlot(40, 32)
         .addIngredient(DebugIngredient.TYPE, new DebugIngredient(3))
         .addIngredientsUnsafe(
            List.of(
               this.platformFluidHelper.create(Fluids.LAVA.defaultFluidState().holder(), (int)((1.0 + Math.random()) * bucketVolume)),
               (F)(new ItemStack(Items.ACACIA_LEAVES))
            )
         )
         .addRichTooltipCallback((recipeSlotView, tooltip) -> {
            switch (recipeSlotView.getRole()) {
               case INPUT:
                  tooltip.add(Component.literal("Input DebugIngredient"));
                  break;
               case OUTPUT:
                  tooltip.add(Component.literal("Output DebugIngredient"));
                  break;
               case CATALYST:
                  tooltip.add(Component.literal("Catalyst DebugIngredient"));
            }
         });
   }

   public void createRecipeExtras(IRecipeExtrasBuilder builder, DebugRecipe recipe, IFocusGroup focuses) {
      builder.addInputHandler(new DebugRecipeCategory.JeiInputHandler(recipe, new ScreenRectangle(0, 0, 160, 60)));
   }

   public void getTooltip(ITooltipBuilder tooltip, DebugRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
      tooltip.add(Component.literal("Debug Recipe Category Tooltip is very long and going to wrap").withStyle(ChatFormatting.GOLD));
      if (recipe.checkHover(mouseX, mouseY)) {
         tooltip.add(Component.literal("button tooltip!"));
      } else {
         MutableComponent debug = Component.literal("tooltip debug");
         tooltip.add(debug.withStyle(ChatFormatting.BOLD));
      }

      tooltip.add(Component.literal(mouseX + ", " + mouseY));
   }

   @Nullable
   public ResourceLocation getRegistryName(DebugRecipe recipe) {
      return recipe.getRegistryName();
   }

   @Override
   public boolean needsRecipeBorder() {
      return false;
   }

   public class JeiInputHandler implements IJeiInputHandler {
      private final DebugRecipe recipe;
      private final ScreenRectangle area;

      public JeiInputHandler(DebugRecipe recipe, ScreenRectangle area) {
         this.recipe = recipe;
         this.area = area;
      }

      @Override
      public ScreenRectangle getArea() {
         return this.area;
      }

      @Override
      public boolean handleInput(double mouseX, double mouseY, IJeiUserInput userInput) {
         if (!userInput.is(Internal.getKeyMappings().getLeftClick())) {
            return false;
         }

         Key key = userInput.getKey();
         Button button = this.recipe.getButton();
         int mouseButton = key.getValue();
         if (mouseButton == 0 && button.mouseClicked(mouseX, mouseY, mouseButton)) {
            if (!userInput.isSimulate()) {
               Minecraft minecraft = Minecraft.getInstance();
               LocalPlayer player = minecraft.player;
               if (player != null) {
                  Screen screen = new InventoryScreen(player);
                  minecraft.setScreen(screen);
               }

               if (DebugRecipeCategory.this.runtime != null) {
                  IIngredientFilter ingredientFilter = DebugRecipeCategory.this.runtime.getIngredientFilter();
                  String filterText = ingredientFilter.getFilterText();
                  ingredientFilter.setFilterText(filterText + " test");
                  IRecipeManager recipeManager = DebugRecipeCategory.this.runtime.getRecipeManager();
                  if (!DebugRecipeCategory.this.hiddenRecipes) {
                     recipeManager.hideRecipeCategory(RecipeTypes.CRAFTING);
                     DebugRecipeCategory.this.hiddenRecipes = true;
                  } else {
                     recipeManager.unhideRecipeCategory(RecipeTypes.CRAFTING);
                     DebugRecipeCategory.this.hiddenRecipes = false;
                  }
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }
}
