package twilightforest.item.recipe.travellers;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import twilightforest.data.helpers.TFLangProvider;
import twilightforest.init.custom.TravellersModifiersManager;
import twilightforest.item.travellers_gear.modifiers.TravellersModifiable;
import twilightforest.item.travellers_gear.modifiers.TravellersModifier;

public abstract class TravellersGearModifierRecipe extends CustomRecipe {
   protected final ResourceKey<TravellersModifier> travellersModifierKey;

   public TravellersGearModifierRecipe(ResourceKey<TravellersModifier> travellersModifier) {
      super(CraftingBookCategory.EQUIPMENT);
      this.travellersModifierKey = travellersModifier;
   }

   public boolean matches(@NotNull CraftingInput input, @NotNull Level level) {
      ItemStack stack = getModifiableArmor(input);
      if (stack == null) {
         return false;
      }

      int slots = 0;
      if (stack.getItem() instanceof TravellersModifiable travellersModifiableItem) {
         slots = travellersModifiableItem.getModifierSlots();
      }

      return TravellersModifiersManager.countInsertableModifiers(level.registryAccess(), stack) < slots
         && !TravellersModifiersManager.hasTravellersModifier(level.registryAccess(), stack, this.travellersModifierKey)
         && TravellersModifiersManager.getModifierDataComponentProviders(
               level.registryAccess(), input.items().stream().map(xva$0 -> Ingredient.of(new ItemStack[]{xva$0})).toList(), this.travellersModifierKey
            )
            <= 1;
   }

   @NotNull
   public ItemStack assemble(@NotNull CraftingInput input, @NotNull Provider registries) {
      ItemStack travellerArmorStack = getModifiableArmor(input);
      if (travellerArmorStack == null) {
         return ItemStack.EMPTY;
      }

      ItemStack stack = travellerArmorStack.copy();
      return this.applyModifier(registries, stack, input.items().stream().map(xva$0 -> Ingredient.of(new ItemStack[]{xva$0})).toList());
   }

   public ItemStack applyModifier(Provider registries, ItemStack stack, List<Ingredient> inputs) {
      if (TravellersModifiersManager.transferModifier(registries, stack, inputs, this.travellersModifierKey)) {
         return stack;
      }

      boolean modifierAdded = TravellersModifiersManager.addModifier(registries, stack, this.travellersModifierKey);
      return modifierAdded ? stack : ItemStack.EMPTY;
   }

   public abstract boolean isShapeless();

   public abstract int getWidth();

   public abstract int getHeight();

   @Nullable
   protected static ItemStack getModifiableArmor(CraftingInput input) {
      return getModifiableArmor(input.items());
   }

   @Nullable
   protected static ItemStack getModifiableArmor(Iterable<ItemStack> items) {
      return StreamSupport.stream(items.spliterator(), false)
         .filter(stack -> stack.getItem() instanceof TravellersModifiable modifiable && modifiable.getModifierSlots() > 0)
         .findFirst()
         .orElse(null);
   }

   public static ItemStack getModifiableArmorFromIngredients(Iterable<Ingredient> ingredients) {
      return StreamSupport.stream(ingredients.spliterator(), false)
         .flatMap(ingredient -> Arrays.stream(ingredient.getItems()))
         .filter(stack -> stack.getItem() instanceof TravellersModifiable)
         .findFirst()
         .orElseThrow();
   }

   public ResourceLocation getId() {
      return this.travellersModifierKey
         .location()
         .withPrefix(StringUtils.substringAfterLast(getModifiableArmorFromIngredients(this.getIngredients()).getDescriptionId(), 46) + "/")
         .withPrefix("add_modifier_to_travellers_gear/")
         .withSuffix("_modifier");
   }

   public ResourceKey<TravellersModifier> getTravellersModifierKey() {
      return this.travellersModifierKey;
   }

   public static class AbstractModifierRecipeSerializer<T extends TravellersGearModifierRecipe> implements RecipeSerializer<T> {
      protected final MapCodec<T> codec;

      protected AbstractModifierRecipeSerializer(MapCodec<T> codec) {
         this.codec = codec;
      }

      @NotNull
      public MapCodec<T> codec() {
         return this.codec;
      }

      @NotNull
      public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
         return StreamCodec.of(this::toNetwork, this::fromNetwork);
      }

      public T fromNetwork(RegistryFriendlyByteBuf buf) {
         RegistryOps<JsonElement> registryops = buf.registryAccess().createSerializationContext(JsonOps.INSTANCE);
         JsonElement jsonelementDeserialized = (JsonElement)GsonHelper.fromJson(TFLangProvider.GSON, buf.readUtf(), JsonElement.class);
         return (T)((Pair)this.codec.codec().decode(registryops, jsonelementDeserialized).getOrThrow()).getFirst();
      }

      public void toNetwork(RegistryFriendlyByteBuf buf, T recipe) {
         RegistryOps<JsonElement> registryops = buf.registryAccess().createSerializationContext(JsonOps.INSTANCE);
         JsonElement jsonelement = (JsonElement)this.codec.codec().encodeStart(registryops, recipe).getOrThrow();
         buf.writeUtf(jsonelement.toString());
      }
   }
}
