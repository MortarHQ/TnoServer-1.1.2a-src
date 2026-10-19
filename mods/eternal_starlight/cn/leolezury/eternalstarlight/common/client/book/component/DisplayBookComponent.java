package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookContext;
import cn.leolezury.eternalstarlight.common.client.book.text.BookContent;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.StreamSupport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class DisplayBookComponent extends BookComponent<DisplayBookComponent.Config> {
   public DisplayBookComponent() {
      super(DisplayBookComponent.Config.CODEC);
   }

   public int getTotalHeight(DisplayBookComponent.Config config, BookContext context) {
      int totalHeight = config.totalHeight();

      for (DisplayBookComponent.TextDisplay display : config.textDisplays()) {
         totalHeight = Math.max(totalHeight, display.getLeastComponentHeight(context));
      }

      return totalHeight;
   }

   public void render(DisplayBookComponent.Config config, BookContext context, GuiGraphics graphics, int x, int y) {
      for (DisplayBookComponent.ImageDisplay display : config.imageDisplays()) {
         graphics.blit(display.location(), x + display.x(), y + display.y(), 0.0F, 0.0F, display.width(), display.height(), display.width(), display.height());
      }

      for (DisplayBookComponent.EntityDisplay display : config.entityDisplays()) {
         LivingEntity entity = display.getEntity();
         if (entity != null) {
            InventoryScreen.renderEntityInInventory(graphics, x + display.x, y + display.y, display.scale, new Vector3f(), display.rotation, null, entity);
         }
      }

      for (DisplayBookComponent.ItemDisplay display : config.itemDisplays()) {
         ItemStack stack = display.getItemStack();
         graphics.renderItem(stack, x + display.x, y + display.y);
      }

      for (DisplayBookComponent.ItemTagDisplay display : config.itemTagDisplays()) {
         List<Item> items = StreamSupport.stream(BuiltInRegistries.ITEM.getTagOrEmpty(display.tag()).spliterator(), false).<Item>map(Holder::value).toList();
         if (!items.isEmpty()) {
            ItemStack stack = items.get(Mth.floor(context.getTickCount() / 30.0) % items.size()).getDefaultInstance();
            graphics.renderItem(stack, x + display.x(), y + display.y());
         }
      }

      for (DisplayBookComponent.CraftingRecipeDisplay display : config.craftingRecipeDisplays) {
         Ingredient[][] ingredients = display.getIngredients();

         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               Ingredient ingredient = ingredients[i][j];
               if (ingredient != null) {
                  ItemStack[] items = ingredient.getItems();
                  ItemStack stack = items.length == 0 ? ItemStack.EMPTY : items[Mth.floor(context.getTickCount() / 30.0) % items.length];
                  graphics.renderItem(
                     stack,
                     x + display.x + i * display.slotWidth + (display.slotWidth - 16) / 2,
                     y + display.y + j * display.slotHeight + (display.slotHeight - 16) / 2
                  );
               }
            }
         }
      }

      for (DisplayBookComponent.TextDisplay display : config.textDisplays()) {
         graphics.pose().pushPose();
         graphics.pose().translate(x + display.x, y + display.y, 0.0F);
         List<FormattedCharSequence> lines = display.getLines(context);
         graphics.pose().scale(display.scale, display.scale, 1.0F);

         for (int i = 0; i < lines.size(); i++) {
            FormattedCharSequence text = lines.get(i);
            graphics.drawString(context.getFont(), text, display.centered ? -context.getFont().width(text) / 2 : 0, i * display.lineHeight, 0, true);
         }

         graphics.pose().popPose();
      }
   }

   public void renderDelayed(DisplayBookComponent.Config config, BookContext context, GuiGraphics graphics, int x, int y) {
      for (DisplayBookComponent.ItemDisplay display : config.itemDisplays()) {
         ItemStack stack = display.getItemStack();
         if (context.getMouseX() >= x + display.x
            && context.getMouseX() <= x + display.x + 16
            && context.getMouseY() >= Math.max(y + display.y, context.getContentY())
            && context.getMouseY()
               <= Math.min(y + display.y + 16, context.getContentY() + context.getBookDefinition().height() - 2 * context.getBookDefinition().frameWidth())) {
            graphics.pose().pushPose();
            graphics.pose().translate(0.0, 0.0, 790.0);
            graphics.renderTooltip(
               context.getFont(), Screen.getTooltipFromItem(Minecraft.getInstance(), stack), stack.getTooltipImage(), context.getMouseX(), context.getMouseY()
            );
            graphics.pose().popPose();
         }
      }

      for (DisplayBookComponent.ItemTagDisplay display : config.itemTagDisplays()) {
         List<Item> items = StreamSupport.stream(BuiltInRegistries.ITEM.getTagOrEmpty(display.tag()).spliterator(), false).<Item>map(Holder::value).toList();
         if (!items.isEmpty()) {
            ItemStack stack = items.get(Mth.floor(context.getTickCount() / 30.0) % items.size()).getDefaultInstance();
            if (context.getMouseX() >= x + display.x()
               && context.getMouseX() <= x + display.x() + 16
               && context.getMouseY() >= Math.max(y + display.y(), context.getContentY())
               && context.getMouseY()
                  <= Math.min(y + display.y() + 16, context.getContentY() + context.getBookDefinition().height() - 2 * context.getBookDefinition().frameWidth())
               )
             {
               graphics.pose().pushPose();
               graphics.pose().translate(0.0, 0.0, 790.0);
               graphics.renderTooltip(
                  context.getFont(),
                  Screen.getTooltipFromItem(Minecraft.getInstance(), stack),
                  stack.getTooltipImage(),
                  context.getMouseX(),
                  context.getMouseY()
               );
               graphics.pose().popPose();
            }
         }
      }

      for (DisplayBookComponent.CraftingRecipeDisplay display : config.craftingRecipeDisplays) {
         Ingredient[][] ingredients = display.getIngredients();

         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               Ingredient ingredient = ingredients[i][j];
               if (ingredient != null) {
                  ItemStack[] items = ingredient.getItems();
                  ItemStack stack = items.length == 0 ? ItemStack.EMPTY : items[Mth.floor(context.getTickCount() / 30.0) % items.length];
                  if (!stack.isEmpty()
                     && context.getMouseX() > x + display.x + i * display.slotWidth + (display.slotWidth - 16) / 2
                     && context.getMouseX() < x + display.x + i * display.slotWidth + (display.slotWidth - 16) / 2 + 16
                     && context.getMouseY() > Math.max(y + display.y + j * display.slotHeight + (display.slotHeight - 16) / 2, context.getContentY())
                     && context.getMouseY()
                        < Math.min(
                           y + display.y + j * display.slotHeight + (display.slotHeight - 16) / 2 + 16,
                           context.getContentY() + context.getBookDefinition().height() - 2 * context.getBookDefinition().frameWidth()
                        )) {
                     graphics.pose().pushPose();
                     graphics.pose().translate(0.0, 0.0, 790.0);
                     graphics.renderTooltip(
                        context.getFont(),
                        Screen.getTooltipFromItem(Minecraft.getInstance(), stack),
                        stack.getTooltipImage(),
                        context.getMouseX(),
                        context.getMouseY()
                     );
                     graphics.pose().popPose();
                  }
               }
            }
         }
      }
   }

   public void onClick(DisplayBookComponent.Config config, BookContext context, int x, int y) {
      for (DisplayBookComponent.TextDisplay display : config.textDisplays()) {
         int trueLineHeight = (int)(display.lineHeight * display.scale);
         List<FormattedCharSequence> list = display.getLines(context);
         int line = (context.getMouseY() - (y + display.y)) / trueLineHeight;
         if (context.getMouseY() >= y + display.y && line >= 0 && line < list.size()) {
            FormattedCharSequence charSequence = list.get(line);
            int width = (int)(context.getFont().width(charSequence) * display.scale);
            int startX = x + display.x - (display.centered ? width / 2 : 0);
            int endX = x + display.x + (display.centered ? width / 2 : width);
            if (context.getMouseX() >= startX && context.getMouseX() <= endX) {
               Style style = context.getFont().getSplitter().componentStyleAtWidth(charSequence, (int)((context.getMouseX() - startX) / display.scale));
               if (style != null) {
                  ClickEvent event = style.getClickEvent();
                  if (event != null && event.getAction() == Action.CHANGE_PAGE) {
                     context.jumpToComponent(ResourceLocation.parse(event.getValue()));
                  }
               }
            }
         }
      }
   }

   public record Config(
      ResourceLocation id,
      HashSet<HashSet<ResourceLocation>> unlockConditions,
      int totalHeight,
      List<DisplayBookComponent.TextDisplay> textDisplays,
      List<DisplayBookComponent.EntityDisplay> entityDisplays,
      List<DisplayBookComponent.ItemDisplay> itemDisplays,
      List<DisplayBookComponent.ItemTagDisplay> itemTagDisplays,
      List<DisplayBookComponent.CraftingRecipeDisplay> craftingRecipeDisplays,
      List<DisplayBookComponent.ImageDisplay> imageDisplays
   ) implements BookComponentConfig {
      public static final Codec<DisplayBookComponent.Config> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ResourceLocation.CODEC.fieldOf("id").forGetter(DisplayBookComponent.Config::id),
               ResourceLocation.CODEC
                  .listOf()
                  .xmap(Sets::newHashSet, Lists::newArrayList)
                  .listOf()
                  .xmap(Sets::newHashSet, Lists::newArrayList)
                  .fieldOf("unlock_conditions")
                  .forGetter(DisplayBookComponent.Config::unlockConditions),
               Codec.INT.fieldOf("total_height").forGetter(DisplayBookComponent.Config::totalHeight),
               DisplayBookComponent.TextDisplay.CODEC.listOf().fieldOf("text_displays").forGetter(DisplayBookComponent.Config::textDisplays),
               DisplayBookComponent.EntityDisplay.CODEC.listOf().fieldOf("entity_displays").forGetter(DisplayBookComponent.Config::entityDisplays),
               DisplayBookComponent.ItemDisplay.CODEC.listOf().fieldOf("item_displays").forGetter(DisplayBookComponent.Config::itemDisplays),
               DisplayBookComponent.ItemTagDisplay.CODEC.listOf().fieldOf("item_tag_displays").forGetter(DisplayBookComponent.Config::itemTagDisplays),
               DisplayBookComponent.CraftingRecipeDisplay.CODEC
                  .listOf()
                  .fieldOf("crafting_recipe_displays")
                  .forGetter(DisplayBookComponent.Config::craftingRecipeDisplays),
               DisplayBookComponent.ImageDisplay.CODEC.listOf().fieldOf("image_displays").forGetter(DisplayBookComponent.Config::imageDisplays)
            )
            .apply(instance, DisplayBookComponent.Config::new)
      );

      public Config(ResourceLocation id, HashSet<HashSet<ResourceLocation>> unlockConditions, int totalHeight) {
         this(
            id, unlockConditions, totalHeight, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
         );
      }

      public DisplayBookComponent.Config textDisplay(
         BookContent text, boolean centered, int x, int y, int width, int lineHeight, int minDistanceToBottom, float scale
      ) {
         this.textDisplays.add(new DisplayBookComponent.TextDisplay(text, centered, x, y, width, lineHeight, minDistanceToBottom, scale));
         return this;
      }

      public DisplayBookComponent.Config entityDisplay(CompoundTag tag, int x, int y, float xRot, float yRot, float scale, Quaternionf rotation) {
         this.entityDisplays.add(new DisplayBookComponent.EntityDisplay(tag, x, y, xRot, yRot, scale, rotation));
         return this;
      }

      public DisplayBookComponent.Config itemDisplay(CompoundTag tag, int x, int y) {
         this.itemDisplays.add(new DisplayBookComponent.ItemDisplay(tag, x, y));
         return this;
      }

      public DisplayBookComponent.Config itemTagDisplay(TagKey<Item> tag, int x, int y) {
         this.itemTagDisplays.add(new DisplayBookComponent.ItemTagDisplay(tag, x, y));
         return this;
      }

      public DisplayBookComponent.Config craftingRecipeDisplay(ResourceLocation recipe, int x, int y, int slotWidth, int slotHeight) {
         this.craftingRecipeDisplays.add(new DisplayBookComponent.CraftingRecipeDisplay(recipe, x, y, slotWidth, slotHeight));
         return this;
      }

      public DisplayBookComponent.Config imageDisplay(ResourceLocation location, int x, int y, int width, int height) {
         this.imageDisplays.add(new DisplayBookComponent.ImageDisplay(location, x, y, width, height));
         return this;
      }
   }

   private static class CraftingRecipeDisplay {
      public static final Codec<DisplayBookComponent.CraftingRecipeDisplay> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ResourceLocation.CODEC.fieldOf("recipe").forGetter(o -> o.recipeId),
               Codec.INT.fieldOf("x").forGetter(o -> o.x),
               Codec.INT.fieldOf("y").forGetter(o -> o.y),
               Codec.INT.fieldOf("slot_width").forGetter(o -> o.slotWidth),
               Codec.INT.fieldOf("slot_height").forGetter(o -> o.slotHeight)
            )
            .apply(instance, DisplayBookComponent.CraftingRecipeDisplay::new)
      );
      private final Ingredient[][] ingredients = new Ingredient[3][3];
      private final ResourceLocation recipeId;
      private RecipeHolder<?> recipe;
      private final int x;
      private final int y;
      private final int slotWidth;
      private final int slotHeight;
      private boolean recipePlaced = false;

      public CraftingRecipeDisplay(ResourceLocation recipe, int x, int y, int slotWidth, int slotHeight) {
         this.recipeId = recipe;
         this.x = x;
         this.y = y;
         this.slotWidth = slotWidth;
         this.slotHeight = slotHeight;
      }

      public Ingredient[][] getIngredients() {
         if (this.recipe == null && Minecraft.getInstance().level != null) {
            this.recipe = (RecipeHolder<?>)Minecraft.getInstance().level.getRecipeManager().byKey(this.recipeId).orElse(null);
         }

         if (!this.recipePlaced && this.recipe != null && this.recipe.value().getType() == RecipeType.CRAFTING) {
            this.recipePlaced = true;
            PlaceRecipe<Ingredient> placeRecipe = (ingredient, slot, maxAmount, x, y) -> this.ingredients[x][y] = ingredient;
            placeRecipe.placeRecipe(3, 3, -1, this.recipe, this.recipe.value().getIngredients().iterator(), 0);
         }

         return this.ingredients;
      }
   }

   private static class EntityDisplay {
      public static final Codec<DisplayBookComponent.EntityDisplay> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               CompoundTag.CODEC.fieldOf("entity").forGetter(o -> o.entityTag),
               Codec.INT.fieldOf("x").forGetter(o -> o.x),
               Codec.INT.fieldOf("y").forGetter(o -> o.y),
               Codec.FLOAT.fieldOf("x_rot").forGetter(o -> o.xRot),
               Codec.FLOAT.fieldOf("y_rot").forGetter(o -> o.yRot),
               Codec.FLOAT.fieldOf("scale").forGetter(o -> o.scale),
               ExtraCodecs.QUATERNIONF.fieldOf("rotation").forGetter(o -> o.rotation)
            )
            .apply(instance, DisplayBookComponent.EntityDisplay::new)
      );
      private LivingEntity cachedEntity;
      private final CompoundTag entityTag;
      private final int x;
      private final int y;
      private final float xRot;
      private final float yRot;
      private final float scale;
      private final Quaternionf rotation;

      public EntityDisplay(CompoundTag entity, int x, int y, float xRot, float yRot, float scale, Quaternionf rotation) {
         this.entityTag = entity;
         this.x = x;
         this.y = y;
         this.xRot = xRot;
         this.yRot = yRot;
         this.scale = scale;
         this.rotation = rotation;
      }

      public LivingEntity getEntity() {
         if (this.cachedEntity == null
            && Minecraft.getInstance().level != null
            && EntityType.loadEntityRecursive(this.entityTag, Minecraft.getInstance().level, e -> e) instanceof LivingEntity entity) {
            this.cachedEntity = entity;
            this.cachedEntity.yBodyRot = this.yRot;
            this.cachedEntity.setXRot(this.xRot);
            this.cachedEntity.setYRot(this.yRot);
            this.cachedEntity.yHeadRot = this.cachedEntity.getYRot();
            this.cachedEntity.yHeadRotO = this.cachedEntity.getYRot();
         }

         return this.cachedEntity;
      }
   }

   private record ImageDisplay(ResourceLocation location, int x, int y, int width, int height) {
      public static final Codec<DisplayBookComponent.ImageDisplay> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ResourceLocation.CODEC.fieldOf("location").forGetter(DisplayBookComponent.ImageDisplay::location),
               Codec.INT.fieldOf("x").forGetter(DisplayBookComponent.ImageDisplay::x),
               Codec.INT.fieldOf("y").forGetter(DisplayBookComponent.ImageDisplay::y),
               Codec.INT.fieldOf("width").forGetter(DisplayBookComponent.ImageDisplay::width),
               Codec.INT.fieldOf("height").forGetter(DisplayBookComponent.ImageDisplay::height)
            )
            .apply(instance, DisplayBookComponent.ImageDisplay::new)
      );
   }

   private static class ItemDisplay {
      public static final Codec<DisplayBookComponent.ItemDisplay> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               CompoundTag.CODEC.fieldOf("item").forGetter(o -> o.itemStackTag),
               Codec.INT.fieldOf("x").forGetter(o -> o.x),
               Codec.INT.fieldOf("y").forGetter(o -> o.y)
            )
            .apply(instance, DisplayBookComponent.ItemDisplay::new)
      );
      private ItemStack cachedStack = null;
      private final CompoundTag itemStackTag;
      private final int x;
      private final int y;

      public ItemDisplay(CompoundTag itemStack, int x, int y) {
         this.itemStackTag = itemStack;
         this.x = x;
         this.y = y;
      }

      public ItemStack getItemStack() {
         if (this.cachedStack == null && Minecraft.getInstance().level != null) {
            this.cachedStack = ItemStack.parseOptional(Minecraft.getInstance().level.registryAccess(), this.itemStackTag);
         }

         return this.cachedStack == null ? ItemStack.EMPTY : this.cachedStack;
      }
   }

   private record ItemTagDisplay(TagKey<Item> tag, int x, int y) {
      public static final Codec<DisplayBookComponent.ItemTagDisplay> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               TagKey.codec(Registries.ITEM).fieldOf("item_tag").forGetter(DisplayBookComponent.ItemTagDisplay::tag),
               Codec.INT.fieldOf("x").forGetter(DisplayBookComponent.ItemTagDisplay::x),
               Codec.INT.fieldOf("y").forGetter(DisplayBookComponent.ItemTagDisplay::y)
            )
            .apply(instance, DisplayBookComponent.ItemTagDisplay::new)
      );
   }

   private static class TextDisplay {
      public static final Codec<DisplayBookComponent.TextDisplay> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               BookContent.CODEC.fieldOf("text").forGetter(o -> o.text),
               Codec.BOOL.fieldOf("centered").forGetter(o -> o.centered),
               Codec.INT.fieldOf("x").forGetter(o -> o.x),
               Codec.INT.fieldOf("y").forGetter(o -> o.y),
               Codec.INT.fieldOf("width").forGetter(o -> o.width),
               Codec.INT.fieldOf("line_height").forGetter(o -> o.lineHeight),
               Codec.INT.fieldOf("min_distance_to_bottom").forGetter(o -> o.minDistanceToBottom),
               Codec.FLOAT.fieldOf("scale").forGetter(o -> o.scale)
            )
            .apply(instance, DisplayBookComponent.TextDisplay::new)
      );
      private final BookContent text;
      private final List<FormattedCharSequence> cachedText = new ArrayList<>();
      private final boolean centered;
      private final int x;
      private final int y;
      private final int width;
      private final int lineHeight;
      private final int minDistanceToBottom;
      private final float scale;

      public TextDisplay(BookContent text, boolean centered, int x, int y, int width, int lineHeight, int minDistanceToBottom, float scale) {
         this.text = text;
         this.centered = centered;
         this.x = x;
         this.y = y;
         this.width = width;
         this.lineHeight = lineHeight;
         this.minDistanceToBottom = minDistanceToBottom;
         this.scale = scale;
      }

      public List<FormattedCharSequence> getLines(BookContext context) {
         if (this.cachedText.isEmpty()) {
            this.cachedText.addAll(context.getFont().split(this.text.toComponent(), (int)(this.width / this.scale)));
         }

         return this.cachedText;
      }

      public int getLeastComponentHeight(BookContext context) {
         return (int)(this.y + this.getLines(context).size() * this.lineHeight * this.scale + this.minDistanceToBottom);
      }
   }
}
