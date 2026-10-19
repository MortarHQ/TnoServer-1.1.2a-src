package mcjty.incontrol.tools.varia;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import mcjty.incontrol.ErrorHandler;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

public class Tools {
   public static ResourceKey<Level> getDimensionKey(LevelAccessor world) {
      if (world instanceof Level) {
         return ((Level)world).dimension();
      } else if (world instanceof ServerLevelAccessor) {
         return ((ServerLevelAccessor)world).getLevel().dimension();
      } else {
         throw new IllegalStateException("Not possible to get a dimension key here!");
      }
   }

   public static String getBiomeId(Holder<Biome> biomeHolder) {
      return (String)biomeHolder.unwrap().map(key -> key.location().toString(), key -> "[unregistered " + key + "]");
   }

   public static org.apache.commons.lang3.tuple.Pair<Float, ItemStack> parseStackWithFactor(String name) {
      int i = 0;

      while (i < name.length() && (Character.isDigit(name.charAt(i)) || name.charAt(i) == '.')) {
         i++;
      }

      if (i < name.length() && name.charAt(i) == '=') {
         String f = name.substring(0, i);

         float v;
         try {
            v = Float.parseFloat(f);
         } catch (NumberFormatException e) {
            v = 1.0F;
         }

         return org.apache.commons.lang3.tuple.Pair.of(v, parseStack(name.substring(i + 1)));
      } else {
         return org.apache.commons.lang3.tuple.Pair.of(1.0F, parseStack(name));
      }
   }

   public static org.apache.commons.lang3.tuple.Pair<Float, ItemStack> parseStackWithFactor(JsonObject obj) {
      float factor = 1.0F;
      if (obj.has("factor")) {
         factor = obj.get("factor").getAsFloat();
      }

      ItemStack stack = parseStack(obj);
      return stack == null ? null : org.apache.commons.lang3.tuple.Pair.of(factor, stack);
   }

   @Nonnull
   public static ItemStack parseStack(String name) {
      if (name.contains("{")) {
         int idx = name.indexOf(123);
         ItemStack stack = parseStackNoNBT(name.substring(0, idx));
         if (stack.isEmpty()) {
            return stack;
         }

         CompoundTag nbt;
         try {
            nbt = TagParser.parseTag(name.substring(idx));
         } catch (CommandSyntaxException e) {
            ErrorHandler.error("Error parsing NBT in '" + name + "'!");
            return ItemStack.EMPTY;
         }

         DataResult<Pair<DataComponentPatch, Tag>> decoded = DataComponentPatch.CODEC.decode(NbtOps.INSTANCE, nbt);
         DataComponentPatch patch = (DataComponentPatch)((Pair)decoded.getOrThrow()).getFirst();
         stack.applyComponents(patch);
         return stack;
      } else if (name.contains("/")) {
         int idx = name.indexOf(47);
         ItemStack stack = parseStackNoNBT(name.substring(0, idx));
         if (stack.isEmpty()) {
            return stack;
         }

         CompoundTag nbt;
         try {
            nbt = TagParser.parseTag(name.substring(idx + 1));
         } catch (CommandSyntaxException e) {
            ErrorHandler.error("Error parsing NBT in '" + name + "'!");
            return ItemStack.EMPTY;
         }

         DataResult<Pair<DataComponentPatch, Tag>> decoded = DataComponentPatch.CODEC.decode(NbtOps.INSTANCE, nbt);
         DataComponentPatch patch = (DataComponentPatch)((Pair)decoded.getOrThrow()).getFirst();
         stack.applyComponents(patch);
         return stack;
      } else {
         return parseStackNoNBT(name);
      }
   }

   @Nullable
   public static ItemStack parseStack(JsonObject obj) {
      if (obj.has("empty")) {
         return ItemStack.EMPTY;
      }

      String name = obj.get("item").getAsString();
      Item item = (Item)BuiltInRegistries.ITEM.get(ResourceLocation.parse(name));
      if (item == null) {
         ErrorHandler.error("Unknown item '" + name + "'!");
         return null;
      }

      ItemStack stack = new ItemStack(item);
      if (obj.has("damage")) {
         stack.setDamageValue(obj.get("damage").getAsInt());
      }

      if (obj.has("count")) {
         stack.setCount(obj.get("count").getAsInt());
      }

      if (obj.has("nbt")) {
         String nbt = obj.get("nbt").toString();
         CompoundTag tag = null;

         try {
            tag = TagParser.parseTag(nbt);
         } catch (CommandSyntaxException e) {
            ErrorHandler.error("Error parsing json '" + nbt + "'!");
            return ItemStack.EMPTY;
         }

         DataResult<Pair<DataComponentPatch, Tag>> decoded = DataComponentPatch.CODEC.decode(NbtOps.INSTANCE, tag);
         DataComponentPatch patch = (DataComponentPatch)((Pair)decoded.getOrThrow()).getFirst();
         stack.applyComponents(patch);
      }

      return stack;
   }

   private static ItemStack parseStackNoNBT(String name) {
      Item item = (Item)BuiltInRegistries.ITEM.get(ResourceLocation.parse(name));
      return item == null ? ItemStack.EMPTY : new ItemStack(item);
   }

   public static ServerLevel getServerWorld(LevelAccessor world) {
      if (!(world instanceof ServerLevel sw)) {
         if (!(world instanceof ServerLevelAccessor)) {
            throw new IllegalStateException("No world found!");
         }

         sw = ((ServerLevelAccessor)world).getLevel();
      }

      return sw;
   }

   public static Predicate<Integer> parseExpression(String input) {
      String i = input.toLowerCase();
      if (i.startsWith("greater(") || i.startsWith("gt(")) {
         int number = Integer.parseInt(i.substring(i.indexOf(40) + 1, i.indexOf(41)).trim());
         return value -> value > number;
      } else if (i.startsWith("greaterorequal(") || i.startsWith("ge(")) {
         int number = Integer.parseInt(i.substring(i.indexOf(40) + 1, i.indexOf(41)).trim());
         return value -> value >= number;
      } else if (i.startsWith("smaller(") || i.startsWith("lt(")) {
         int number = Integer.parseInt(i.substring(i.indexOf(40) + 1, i.indexOf(41)).trim());
         return value -> value < number;
      } else if (i.startsWith("smallerorequal(") || i.startsWith("le(")) {
         int number = Integer.parseInt(i.substring(i.indexOf(40) + 1, i.indexOf(41)).trim());
         return value -> value <= number;
      } else if (i.startsWith("equal(") || i.startsWith("eq(")) {
         int number = Integer.parseInt(i.substring(i.indexOf(40) + 1, i.indexOf(41)).trim());
         return value -> value == number;
      } else if (i.startsWith("notequal(") || i.startsWith("ne(")) {
         int number = Integer.parseInt(i.substring(i.indexOf(40) + 1, i.indexOf(41)).trim());
         return value -> value != number;
      } else if (i.startsWith("range(")) {
         int min = Integer.parseInt(i.substring(i.indexOf(40) + 1, i.indexOf(44)));
         int max = Integer.parseInt(i.substring(i.indexOf(44) + 1, i.indexOf(41)));
         return value -> value >= min && value <= max;
      } else if (i.startsWith("outsiderange(")) {
         int min = Integer.parseInt(i.substring(i.indexOf(40) + 1, i.indexOf(44)));
         int max = Integer.parseInt(i.substring(i.indexOf(44) + 1, i.indexOf(41)));
         return value -> value < min || value > max;
      } else if (i.startsWith("repeat(")) {
         int cycle = Integer.parseInt(i.substring(i.indexOf(40) + 1, i.indexOf(44)));
         int min = Integer.parseInt(i.substring(i.indexOf(44) + 1, i.lastIndexOf(44)));
         int max = Integer.parseInt(i.substring(i.lastIndexOf(44) + 1, i.indexOf(41)));
         return value -> {
            int v = value % cycle;
            return v >= min && v <= max;
         };
      } else {
         ErrorHandler.error("Unknown expression '" + input + "'!");
         return null;
      }
   }
}
