package dev.latvian.mods.kubejs.plugin.builtin.wrapper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JavaOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.DataResult.Error;
import dev.latvian.mods.kubejs.component.DataComponentWrapper;
import dev.latvian.mods.kubejs.core.IngredientSupplierKJS;
import dev.latvian.mods.kubejs.core.ItemStackKJS;
import dev.latvian.mods.kubejs.error.KubeRuntimeException;
import dev.latvian.mods.kubejs.ingredient.CreativeTabIngredient;
import dev.latvian.mods.kubejs.ingredient.NamespaceIngredient;
import dev.latvian.mods.kubejs.ingredient.RegExIngredient;
import dev.latvian.mods.kubejs.ingredient.WildcardIngredient;
import dev.latvian.mods.kubejs.script.SourceLine;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ID;
import dev.latvian.mods.kubejs.util.ListJS;
import dev.latvian.mods.kubejs.util.RegExpKJS;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.Wrapper;
import dev.latvian.mods.rhino.regexp.NativeRegExp;
import dev.latvian.mods.rhino.type.TypeInfo;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Ingredient.TagValue;
import net.minecraft.world.item.crafting.Ingredient.Value;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.CompoundIngredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.Nullable;

@Info("Various Ingredient related helper methods")
public interface IngredientWrapper {
   TypeInfo TYPE_INFO = TypeInfo.of(Ingredient.class);
   @Info("A completely empty ingredient that will only match air")
   Ingredient none = Ingredient.EMPTY;
   @Info("An ingredient that matches everything")
   Ingredient all = WildcardIngredient.INSTANCE.toVanilla();

   @Info("Returns an ingredient of the input")
   static Ingredient of(Ingredient ingredient) {
      return ingredient;
   }

   @Info("Returns an ingredient of the input, with the specified count")
   static SizedIngredient of(Ingredient ingredient, int count) {
      return ingredient.kjs$withCount(count);
   }

   @Info("Returns an ingredient that accepts the given set of items under the given component filter.")
   static Ingredient withData(HolderSet<Item> base, DataComponentMap data) {
      return withData(base, data, false);
   }

   @Info("Returns an ingredient that accepts the given set of items under the given (optionally strict) component filter.")
   static Ingredient withData(HolderSet<Item> base, DataComponentMap data, boolean strict) {
      return DataComponentIngredient.of(strict, data, base);
   }

   @HideFromJS
   private static Ingredient wrapTrivial(@Nullable Object from) {
      while (from instanceof Wrapper) {
         Wrapper w = (Wrapper)from;
         from = w.unwrap();
      }
      return switch (from) {
         case null -> Ingredient.EMPTY;
         case Ingredient id -> id;
         case ItemStack s when s.isEmpty() -> Ingredient.EMPTY;
         case ItemLike i when i.asItem() == Items.AIR -> Ingredient.EMPTY;
         case IngredientSupplierKJS ingr -> ingr.kjs$asIngredient();
         case ItemLike i -> Ingredient.of(new ItemLike[]{i});
         case TagKey var8 -> {
            TagKey var15 = var8;

            try {
               var15.registry();
            } catch (Throwable var13) {
               throw new MatchException(var13.toString(), var13);
            }

            var15 = var8;

            try {
               var17 = var15.location();
            } catch (Throwable var12) {
               throw new MatchException(var12.toString(), var12);
            }

            ResourceLocation var11 = var17;
            ResourceLocation location = var11;
            yield Ingredient.of(ItemTags.create(location));
         }
         default -> null;
      };
   }

   @HideFromJS
   static DataResult<Ingredient> wrapResult(Context cx, @Nullable Object from) {
      while (from instanceof Wrapper) {
         Wrapper w = (Wrapper)from;
         from = w.unwrap();
      }

      Ingredient trivial = wrapTrivial(from);
      if (trivial != null) {
         return DataResult.success(trivial);
      } else if (from instanceof Pattern || from instanceof NativeRegExp) {
         String str = String.valueOf(from);
         return Optional.ofNullable(RegExpKJS.wrap(from))
            .<DataResult>map(DataResult::success)
            .orElseGet(() -> DataResult.error(() -> "Invalid regex " + str))
            .map(RegExIngredient::new)
            .map(ICustomIngredient::toVanilla);
      } else if (from instanceof JsonElement json) {
         return parseJson(cx, json);
      } else {
         if (from instanceof CharSequence) {
            return parseString(cx, from.toString());
         }

         List<?> list = ListJS.of(from);
         if (list != null) {
            List<Ingredient> results = new ArrayList<>(list.size());
            boolean failed = false;
            Builder<String> errors = Stream.builder();

            for (Object o1 : list) {
               DataResult<Ingredient> ingredient = wrapResult(cx, o1);
               ingredient.resultOrPartial().filter(ingr -> ingr != Ingredient.EMPTY).ifPresent(results::add);
               if (ingredient.isError()) {
                  failed = true;
                  errors.add(o1 + ": " + ((Error)ingredient.error().orElseThrow()).message());
               }
            }

            if (failed) {
               String msg = errors.build().collect(Collectors.joining("; "));
               return DataResult.error(() -> "Failed to parse ingredient list: " + msg);
            }

            return DataResult.success(switch (results.size()) {
               case 0 -> Ingredient.EMPTY;
               case 1 -> (Ingredient)results.getFirst();
               default -> new CompoundIngredient(results).toVanilla();
            });
         } else {
            Map<String, Object> map = cx.optionalMapOf(from);
            return map != null ? Ingredient.CODEC.parse(JavaOps.INSTANCE, map) : ItemWrapper.wrapResult(cx, from).map(ItemStackKJS::kjs$asIngredient);
         }
      }
   }

   @HideFromJS
   static Ingredient wrap(Context cx, @Nullable Object from) {
      Ingredient trivial = wrapTrivial(from);
      return trivial != null
         ? trivial
         : (Ingredient)wrapResult(cx, from)
            .getOrThrow(error -> new KubeRuntimeException("Failed to read ingredient from %s: %s".formatted(from, error)).source(SourceLine.of(cx)));
   }

   static boolean isIngredientLike(Object from) {
      return from instanceof Ingredient || from instanceof SizedIngredient || from instanceof ItemStack;
   }

   static DataResult<Ingredient> parseJson(Context cx, JsonElement json) {
      return switch (json) {
         case null -> DataResult.success(Ingredient.EMPTY);
         case JsonNull jsonNull -> DataResult.success(Ingredient.EMPTY);
         case JsonArray arr when arr.isEmpty() -> DataResult.success(Ingredient.EMPTY);
         case JsonPrimitive primitive -> wrapResult(cx, json.getAsString());
         default -> Ingredient.CODEC.decode(JsonOps.INSTANCE, json).map(Pair::getFirst);
      };
   }

   static DataResult<Ingredient> parseString(Context cx, String s) {
      return switch (s) {
         case "", "-", "air", "minecraft:air" -> DataResult.success(Ingredient.EMPTY);
         case "*" -> DataResult.success(all);
         default -> read(cx, new StringReader(s));
      };
   }

   static DataResult<Ingredient> read(Context cx, StringReader reader) {
      RegistryAccessContainer registries = RegistryAccessContainer.of(cx);
      reader.skipWhitespace();
      if (!reader.canRead()) {
         return DataResult.success(Ingredient.EMPTY);
      }

      return switch (reader.peek()) {
         case '#' -> {
            reader.skip();
            DataResult var19 = ID.read(reader).map(ItemTags::create).map(Ingredient::of);
            yield var19;
         }
         case '%' -> {
            reader.skip();
            DataResult var18 = ID.read(reader).flatMap(input -> {
               CreativeModeTab tab = UtilsJS.findCreativeTab(input);
               return tab != null ? DataResult.success(tab) : DataResult.error(() -> "Creative tab " + input + " does not exist!");
            }).map(group -> new CreativeTabIngredient(group).toVanilla());
            yield var18;
         }
         case '*' -> {
            reader.skip();
            DataResult var17 = DataResult.success(all);
            yield var17;
         }
         case '-' -> {
            reader.skip();
            DataResult var16 = DataResult.success(Ingredient.EMPTY);
            yield var16;
         }
         case '/' -> {
            DataResult var15 = RegExpKJS.tryRead(reader).map(RegExIngredient::new).map(ICustomIngredient::toVanilla);
            yield var15;
         }
         case '@' -> {
            reader.skip();
            DataResult var14 = DataResult.success(new NamespaceIngredient(reader.readUnquotedString()).toVanilla());
            yield var14;
         }
         case '[' -> {
            reader.skip();
            reader.skipWhitespace();
            if (reader.canRead() && reader.peek() != ']') {
               ArrayList<Ingredient> ingredients = new ArrayList<>(2);

               while (true) {
                  DataResult<Ingredient> ingredient = read(cx, reader);
                  if (!ingredient.isSuccess()) {
                     DataResult var13 = DataResult.error(() -> "Invalid ingredient in list: " + ((Error)ingredient.error().orElseThrow()).message());
                     yield var13;
                  }

                  ingredients.add((Ingredient)ingredient.getOrThrow());
                  reader.skipWhitespace();
                  if (!reader.canRead() || reader.peek() != ',') {
                     if (!reader.canRead() || reader.peek() == ']') {
                        if (reader.canRead() && reader.peek() == ']') {
                           reader.skip();
                           reader.skipWhitespace();
                           DataResult var12 = DataResult.success(new CompoundIngredient(ingredients).toVanilla());
                           yield var12;
                        } else {
                           DataResult var11 = DataResult.error(() -> "Unterminated compound ingredient");
                           yield var11;
                        }
                     }
                  } else {
                     reader.skip();
                     reader.skipWhitespace();
                  }
               }
            } else {
               DataResult var10 = DataResult.success(Ingredient.EMPTY);
               yield var10;
            }
         }
         default -> {
            label87: {
               DataResult var9;
               label93: {
                  DataResult<Holder<Item>> item = ID.read(reader).flatMap(ItemWrapper::findItem);
                  char next = reader.canRead() ? reader.peek() : '\u0000';
                  if (next == '[' || next == '{') {
                     try {
                        DataComponentPredicate components = DataComponentWrapper.readPredicate(registries.nbt(), reader);
                        if (components != DataComponentPredicate.EMPTY) {
                           var9 = item.map(holder -> DataComponentIngredient.of(false, components, new Holder[]{holder}));
                           break label93;
                        }
                     } catch (CommandSyntaxException e) {
                        var9 = DataResult.error(e::getMessage);
                        yield var9;
                        break label87;
                     }
                  }

                  var9 = item.map(Holder::value).map(xva$0 -> Ingredient.of(new ItemLike[]{xva$0}));
                  yield var9;
                  break label87;
               }

               yield var9;
            }
         }
      };
   }

   @Info(
      "Checks if the passed in object is an Ingredient.\nNote that this does not mean it will not function as an Ingredient if passed to something that requests one.\n"
   )
   static boolean isIngredient(@Nullable Object o) {
      return o instanceof Ingredient;
   }

   static ItemStack first(Ingredient ingredient) {
      return ingredient.kjs$getFirst();
   }

   @Nullable
   static TagKey<Item> tagKeyOf(Ingredient in) {
      if (!in.isCustom() && in.getValues().length == 1 && in.getValues()[0] instanceof TagValue var1) {
         TagValue var10000 = var1;

         try {
            var6 = var10000.tag();
         } catch (Throwable var5) {
            throw new MatchException(var5.toString(), var5);
         }

         return var6;
      } else {
         return null;
      }
   }

   static boolean containsAnyTag(Ingredient in) {
      if (in.isCustom()) {
         return false;
      }

      for (Value value : in.getValues()) {
         if (value instanceof TagValue) {
            return true;
         }
      }

      return false;
   }
}
