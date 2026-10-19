package fzzyhmstrs.emi_loot.util;

import com.google.common.base.Suppliers;
import fzzyhmstrs.emi_loot.EMILoot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public record TextKey(int index, List<Component> args) {
   static final Component missing = LText.literal("Missing");
   static final Map<String, Integer> keyMap = new HashMap<>();
   static final Map<Integer, String> keyReverseMap = new HashMap<>();
   static final Map<Integer, Function<TextKey, Component>> keyTextBuilderMap = new HashMap<>();
   static final Map<Integer, BiFunction<ItemStack, Level, List<ItemStack>>> processorMap = new HashMap<>();
   static final Map<Integer, ResourceLocation> keySpriteIdMap = new HashMap<>();
   static final Function<TextKey, Component> DEFAULT_FUNCTION = key -> LText.translatable("emi_loot.missing_key");
   static final BiFunction<ItemStack, Level, List<ItemStack>> DEFAULT_PROCESSOR = (stack, world) -> List.of(stack);
   static final ResourceLocation EMPTY = ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/empty.png");
   static int curDynamicIndex = 1000;
   public static final Supplier<List<Tuple<Integer, Component>>> noConditionsList = Suppliers.memoize(() -> {
      List<Tuple<Integer, Component>> list = new ArrayList<>();
      list.add(new Tuple(getIndex("emi_loot.no_conditions"), LText.translatable("emi_loot.no_conditions")));
      return list;
   });
   public static final Set<String> defaultSkips = Set.of(
      "emi_loot.function.set_count_add",
      "emi_loot.function.set_count_set",
      "emi_loot.function.fill_player_head",
      "emi_loot.function.limit_count",
      "emi_loot.no_conditions"
   );

   private static void mapBuilder(int index, String key, Function<TextKey, Component> function, ResourceLocation spriteId) {
      keyMap.put(key, index);
      keyReverseMap.put(index, key);
      keyTextBuilderMap.put(index, function);
      keySpriteIdMap.put(index, spriteId);
   }

   private static void mapBuilder(
      int index, String key, Function<TextKey, Component> function, ResourceLocation spriteId, BiFunction<ItemStack, Level, List<ItemStack>> processor
   ) {
      keyMap.put(key, index);
      keyReverseMap.put(index, key);
      keyTextBuilderMap.put(index, function);
      keySpriteIdMap.put(index, spriteId);
      processorMap.put(index, processor);
   }

   public static void register(String key, int args, ResourceLocation sprite, BiFunction<ItemStack, Level, List<ItemStack>> processor) {
      if (keyMap.containsKey(key)) {
         throw new IllegalArgumentException("Text key [" + key + "] already registered!");
      }

      if (!sprite.toString().contains(".png")) {
         throw new IllegalArgumentException("Text key [" + key + "] registered with sprite identifier [" + sprite + "] that isn't a png!)");
      }

      int index = curDynamicIndex++;
      switch (args) {
         case 0:
            mapBuilder(index, key, tk -> getBasicText(index), sprite, processor);
            break;
         case 1:
            mapBuilder(index, key, tk -> getOneArgText(index, tk), sprite, processor);
            break;
         case 2:
            mapBuilder(index, key, tk -> getTwoArgText(index, tk), sprite, processor);
            break;
         default:
            mapBuilder(index, key, TextKey::getAnyOfText, sprite, processor);
      }
   }

   public static void register(String key, int args, ResourceLocation sprite) {
      if (keyMap.containsKey(key)) {
         throw new IllegalArgumentException("Text key [" + key + "] already registered!");
      }

      if (!sprite.toString().contains(".png")) {
         throw new IllegalArgumentException("Text key [" + key + "] registered with sprite identifier [" + sprite + "] that isn't a png!)");
      }

      int index = curDynamicIndex++;
      switch (args) {
         case 0:
            mapBuilder(index, key, tk -> getBasicText(index), sprite);
            break;
         case 1:
            mapBuilder(index, key, tk -> getOneArgText(index, tk), sprite);
            break;
         case 2:
            mapBuilder(index, key, tk -> getTwoArgText(index, tk), sprite);
            break;
         default:
            mapBuilder(index, key, TextKey::getAnyOfText, sprite);
      }
   }

   private static Component getBasicText(int index) {
      String translationKey = keyReverseMap.getOrDefault(index, "emi_loot.missing_key");
      return LText.translatable(translationKey);
   }

   private static Component getOneArgText(int index, TextKey key) {
      String translationKey = keyReverseMap.getOrDefault(index, "emi_loot.missing_key");

      Component arg;
      try {
         arg = key.args.get(0);
      } catch (Exception e) {
         EMILoot.LOGGER.error("Couldn't get one-arg text");
         e.printStackTrace();
         arg = missing.copy();
      }

      return LText.translatable(translationKey, arg);
   }

   private static Component getTwoArgText(int index, TextKey key) {
      String translationKey = keyReverseMap.getOrDefault(index, "emi_loot.missing_key");

      Component arg1;
      try {
         arg1 = key.args.get(0);
      } catch (Exception e) {
         EMILoot.LOGGER.error("Couldn't get first arg of two-arg text");
         e.printStackTrace();
         arg1 = missing.copy();
      }

      Component arg2;
      try {
         arg2 = key.args.get(1);
      } catch (Exception e) {
         EMILoot.LOGGER.error("Couldn't get second arg of two-arg text");
         e.printStackTrace();
         arg2 = missing.copy();
      }

      return LText.translatable(translationKey, arg1, arg2);
   }

   private static Component getThreeArgText(int index, TextKey key) {
      String translationKey = keyReverseMap.getOrDefault(index, "emi_loot.missing_key");

      Component arg1;
      try {
         arg1 = key.args.get(0);
      } catch (Exception e) {
         EMILoot.LOGGER.error("Couldn't get first arg of three-arg text");
         e.printStackTrace();
         arg1 = LText.literal("Missing");
      }

      Component arg2;
      try {
         arg2 = key.args.get(1);
      } catch (Exception e) {
         EMILoot.LOGGER.error("Couldn't get second arg of three-arg text");
         e.printStackTrace();
         arg2 = LText.literal("Missing");
      }

      Component arg3;
      try {
         arg3 = key.args.get(2);
      } catch (Exception e) {
         EMILoot.LOGGER.error("Couldn't get third arg of three-arg text");
         e.printStackTrace();
         arg3 = LText.literal("Missing");
      }

      return LText.translatable(translationKey, arg1, arg2, arg3);
   }

   private static Component getAnyOfText(TextKey key) {
      List<Component> args = key.args;
      int size = args.size();
      if (size == 1) {
         return LText.translatable("emi_loot.condition.any_of", args.get(0));
      }

      if (size == 2) {
         return LText.translatable("emi_loot.condition.any_of_2", args.get(0), args.get(1));
      }

      MutableComponent finalText = LText.empty();

      for (int i = 0; i < size; i++) {
         Component arg = args.get(i);
         if (i == size - 2) {
            finalText.append(LText.translatable("emi_loot.condition.any_of_3a", arg));
         } else if (i == size - 1) {
            finalText.append(LText.translatable("emi_loot.condition.any_of", arg));
         } else {
            finalText.append(LText.translatable("emi_loot.condition.any_of_3", arg));
         }
      }

      return finalText;
   }

   private static Component getAllOfText(TextKey key) {
      List<Component> args = key.args;
      int size = args.size();
      if (size == 1) {
         return LText.translatable("emi_loot.condition.all_of", args.get(0));
      }

      if (size == 2) {
         return LText.translatable("emi_loot.condition.all_of_2", args.get(0), args.get(1));
      }

      MutableComponent finalText = LText.empty();

      for (int i = 0; i < size; i++) {
         Component arg = args.get(i);
         if (i == size - 2) {
            finalText.append(LText.translatable("emi_loot.condition.all_of_3a", arg));
         } else if (i == size - 1) {
            finalText.append(LText.translatable("emi_loot.condition.all_of", arg));
         } else {
            finalText.append(LText.translatable("emi_loot.condition.all_of_3", arg));
         }
      }

      return finalText;
   }

   private static Component getInvertedText(int index, TextKey key) {
      String translationKey = keyReverseMap.getOrDefault(index, "emi_loot.missing_key");

      Component arg;
      try {
         arg = key.args.get(0);
      } catch (Exception e) {
         EMILoot.LOGGER.error("Couldn't get inverted text");
         e.printStackTrace();
         arg = missing.copy();
      }

      return LText.translatable(translationKey, arg).withStyle(ChatFormatting.RED);
   }

   public boolean isNotEmpty() {
      return this.index != 0;
   }

   public static Set<String> keys() {
      return keyMap.keySet();
   }

   public static int getIndex(String key) {
      return keyMap.getOrDefault(key, -1);
   }

   public static String getKey(int index) {
      return keyReverseMap.getOrDefault(index, "emi_loot.function.empty");
   }

   public static ResourceLocation getSpriteId(int index) {
      return keySpriteIdMap.getOrDefault(index, EMPTY);
   }

   public static TextKey empty() {
      return new TextKey(0, new LinkedList<>());
   }

   public static TextKey of(String key, Component... args) {
      return keyMap.containsKey(key) ? new TextKey(keyMap.get(key), Arrays.stream(args).toList()) : new TextKey(0, new LinkedList<>());
   }

   public static TextKey of(String key, List<Component> args) {
      if (keyMap.containsKey(key)) {
         return new TextKey(keyMap.get(key), args);
      }

      EMILoot.LOGGER.error("Couldn't parse TextKey with key: {}and args: {}", key, args);
      return new TextKey(0, new LinkedList<>());
   }

   public static TextKey of(String key) {
      return of(key, new LinkedList<>());
   }

   public static TextKey of(String key, Component arg) {
      return of(key, Collections.singletonList(arg));
   }

   public static String symbolKey(int index) {
      return String.valueOf((char)(59136 + index));
   }

   public Component processText() {
      return keyTextBuilderMap.getOrDefault(this.index, DEFAULT_FUNCTION).apply(this);
   }

   public List<ItemStack> processStack(ItemStack stack, @Nullable Level world) {
      BiFunction<ItemStack, Level, List<ItemStack>> processor = processorMap.getOrDefault(this.index, DEFAULT_PROCESSOR);
      return processor.apply(stack, world);
   }

   public TextKey.TextKeyResult process(ItemStack stack, @Nullable Level world) {
      BiFunction<ItemStack, Level, List<ItemStack>> processor = processorMap.getOrDefault(this.index, DEFAULT_PROCESSOR);
      List<ItemStack> finalStacks = processor.apply(stack, world);
      Component text = keyTextBuilderMap.getOrDefault(this.index, DEFAULT_FUNCTION).apply(this);
      return new TextKey.TextKeyResult(text, finalStacks);
   }

   public Component asText() {
      return keyTextBuilderMap.getOrDefault(this.index, DEFAULT_FUNCTION).apply(this);
   }

   public boolean skip() {
      return EMILoot.config.skippedKeys.contains(getKey(this.index));
   }

   public static TextKey fromBuf(FriendlyByteBuf buf) {
      int key = buf.readVarInt();
      int size = buf.readByte();
      List<Component> args = new LinkedList<>();
      if (size > 0) {
         for (int i = 0; i < size; i++) {
            try {
               Component arg = (Component)ComponentSerialization.TRUSTED_CONTEXT_FREE_STREAM_CODEC.decode(buf);
               args.add(arg);
            } catch (Throwable e) {
               args.add(LText.empty());
               String err = "Problem while decoding TextKey with index " + key;
               EMILoot.LOGGER.error(err, e);
            }
         }
      }

      return new TextKey(key, args);
   }

   public void toBuf(FriendlyByteBuf buf) {
      buf.writeVarInt(this.index);
      if (this.args.isEmpty()) {
         buf.writeByte(0);
      } else {
         int argSize = Math.min(127, this.args.size());
         buf.writeByte(this.args.size());

         for (int i = 0; i < argSize; i++) {
            try {
               Component text = this.args.get(i);
               ComponentSerialization.TRUSTED_CONTEXT_FREE_STREAM_CODEC.encode(buf, text);
            } catch (Throwable e) {
               ComponentSerialization.TRUSTED_CONTEXT_FREE_STREAM_CODEC.encode(buf, LText.empty());
               String err = "Problem while encoding TextKey with index " + this.index;
               EMILoot.LOGGER.error(err, e);
            }
         }
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         TextKey textKey = (TextKey)o;
         return this.index == textKey.index && this.args.equals(textKey.args);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.index, this.args);
   }

   static {
      BiFunction<ItemStack, Level, List<ItemStack>> smeltProcessor = (stack, world) -> {
         List<ItemStack> finalStacks = new LinkedList<>();
         if (world != null) {
            Optional<RecipeHolder<SmeltingRecipe>> opt = world.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(stack), world);
            if (opt.isPresent()) {
               ItemStack tempStack = ((SmeltingRecipe)opt.get().value()).getResultItem(world.registryAccess());
               if (!tempStack.isEmpty()) {
                  finalStacks.add(tempStack.copy());
               }
            } else {
               finalStacks.add(stack);
            }
         } else {
            finalStacks.add(stack);
         }

         return finalStacks;
      };
      BiFunction<ItemStack, Level, List<ItemStack>> ominousProcessor = (stack, world) -> List.of(
         Raid.getLeaderBannerInstance(world.registryAccess().lookupOrThrow(Registries.BANNER_PATTERN))
      );
      BiFunction<ItemStack, Level, List<ItemStack>> glintProcessor = (stack, world) -> {
         stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
         return List.of(stack);
      };
      mapBuilder(0, "emi_loot.function.empty", key -> LText.empty(), EMPTY);
      mapBuilder(1, "emi_loot.function.bonus", key -> getOneArgText(1, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/bonus.png"));
      mapBuilder(2, "emi_loot.function.potion", key -> getOneArgText(2, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/potion.png"));
      mapBuilder(
         3, "emi_loot.function.set_count_add", key -> getBasicText(3), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/set_count_add.png")
      );
      mapBuilder(
         4, "emi_loot.function.set_count_set", key -> getBasicText(4), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/set_count_add.png")
      );
      mapBuilder(
         5,
         "emi_loot.function.randomly_enchanted_book",
         key -> getBasicText(5),
         ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/random_book.png"),
         glintProcessor
      );
      mapBuilder(
         6,
         "emi_loot.function.randomly_enchanted_item",
         key -> getBasicText(6),
         ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/random_item.png"),
         glintProcessor
      );
      mapBuilder(
         7, "emi_loot.function.set_enchant_book", key -> getBasicText(7), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/set_book.png")
      );
      mapBuilder(
         8, "emi_loot.function.set_enchant_item", key -> getBasicText(8), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/set_item.png")
      );
      mapBuilder(
         9, "emi_loot.function.smelt", key -> getBasicText(9), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/smelt.png"), smeltProcessor
      );
      mapBuilder(10, "emi_loot.function.looting", key -> getBasicText(10), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/looting.png"));
      mapBuilder(11, "emi_loot.function.map", key -> getOneArgText(11, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/map.png"));
      mapBuilder(
         12, "emi_loot.function.set_contents", key -> getBasicText(12), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/set_contents.png")
      );
      mapBuilder(13, "emi_loot.function.damage", key -> getOneArgText(13, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/damage.png"));
      mapBuilder(14, "emi_loot.function.copy_state", key -> getBasicText(14), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/copy.png"));
      mapBuilder(15, "emi_loot.function.copy_name", key -> getOneArgText(15, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/copy.png"));
      mapBuilder(16, "emi_loot.function.copy_nbt", key -> getBasicText(16), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/nbt.png"));
      mapBuilder(17, "emi_loot.function.decay", key -> getBasicText(17), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/tnt.png"));
      mapBuilder(18, "emi_loot.function.fill_player_head", key -> getBasicText(18), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/fzzy.png"));
      mapBuilder(
         19, "emi_loot.function.limit_count", key -> getOneArgText(19, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/limit_count.png")
      );
      mapBuilder(
         20,
         "emi_loot.function.set_attributes",
         key -> getOneArgText(20, key),
         ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/set_attributes.png")
      );
      mapBuilder(21, "emi_loot.function.banner", key -> getBasicText(21), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/banner.png"));
      mapBuilder(22, "emi_loot.function.lore", key -> getBasicText(22), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/lore.png"));
      mapBuilder(23, "emi_loot.function.set_stew", key -> getOneArgText(23, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/stew.png"));
      mapBuilder(24, "emi_loot.function.set_nbt", key -> getBasicText(24), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/nbt.png"));
      mapBuilder(
         25, "emi_loot.function.set_loot_table", key -> getOneArgText(25, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/chest.png")
      );
      mapBuilder(
         26, "emi_loot.function.reference", key -> getOneArgText(26, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/reference.png")
      );
      mapBuilder(
         27, "emi_loot.function.sequence", key -> getOneArgText(27, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/sequence.png")
      );
      mapBuilder(
         28, "emi_loot.function.set_item", key -> getOneArgText(28, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/set_item.png")
      );
      mapBuilder(
         29,
         "emi_loot.function.modify_contents",
         key -> getOneArgText(29, key),
         ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/modify_contents.png")
      );
      mapBuilder(
         30, "emi_loot.function.filtered", key -> getTwoArgText(30, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/filtered.png")
      );
      mapBuilder(31, "emi_loot.function.fireworks", key -> getBasicText(31), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/firework.png"));
      mapBuilder(
         32,
         "emi_loot.function.firework_explosion",
         key -> getOneArgText(32, key),
         ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/firework.png")
      );
      mapBuilder(
         33, "emi_loot.function.set_book_cover", key -> getOneArgText(33, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/set_book.png")
      );
      mapBuilder(
         34, "emi_loot.condition.survives_explosion", key -> getBasicText(34), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/tnt.png")
      );
      mapBuilder(
         35, "emi_loot.condition.blockstate", key -> getOneArgText(35, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/blockstate.png")
      );
      mapBuilder(
         36, "emi_loot.condition.table_bonus", key -> getOneArgText(36, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/percent.png")
      );
      mapBuilder(37, "emi_loot.condition.invert", key -> getInvertedText(37, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/invert.png"));
      mapBuilder(39, "emi_loot.condition.any_of", TextKey::getAnyOfText, ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/or.png"));
      mapBuilder(40, "emi_loot.condition.all_of", TextKey::getAllOfText, ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/or.png"));
      mapBuilder(41, "emi_loot.condition.killed_player", key -> getBasicText(41), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/steve.png"));
      mapBuilder(42, "emi_loot.condition.chance", key -> getOneArgText(42, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/percent.png"));
      mapBuilder(
         43,
         "emi_loot.condition.chance_looting",
         key -> getTwoArgText(43, key),
         ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/chance_looting.png")
      );
      mapBuilder(
         44,
         "emi_loot.condition.damage_source",
         key -> getOneArgText(44, key),
         ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/tiny_cactus.png")
      );
      mapBuilder(
         45, "emi_loot.condition.location", key -> getOneArgText(45, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/location.png")
      );
      mapBuilder(
         46,
         "emi_loot.condition.entity_props",
         key -> getOneArgText(46, key),
         ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/entity_props.png")
      );
      mapBuilder(
         47, "emi_loot.condition.match_tool", key -> getOneArgText(47, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/match_tool.png")
      );
      mapBuilder(48, "emi_loot.condition.entity_scores", key -> getBasicText(48), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/score.png"));
      mapBuilder(
         49, "emi_loot.condition.reference", key -> getOneArgText(49, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/reference.png")
      );
      mapBuilder(50, "emi_loot.condition.time_check", key -> getOneArgText(50, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/time.png"));
      mapBuilder(
         51, "emi_loot.condition.value_check", key -> getTwoArgText(51, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/value.png")
      );
      mapBuilder(52, "emi_loot.condition.raining_true", key -> getBasicText(52), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/raining.png"));
      mapBuilder(53, "emi_loot.condition.raining_false", key -> getBasicText(53), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/sunny.png"));
      mapBuilder(
         54, "emi_loot.condition.thundering_true", key -> getBasicText(54), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/thundering.png")
      );
      mapBuilder(
         55,
         "emi_loot.condition.thundering_false",
         key -> getBasicText(55),
         ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/not_thundering.png")
      );
      mapBuilder(62, "emi_loot.pickaxe.wood", key -> getBasicText(62), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/pickaxe_wood.png"));
      mapBuilder(63, "emi_loot.pickaxe.stone", key -> getBasicText(63), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/pickaxe_stone.png"));
      mapBuilder(64, "emi_loot.pickaxe.iron", key -> getBasicText(64), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/pickaxe_iron.png"));
      mapBuilder(65, "emi_loot.pickaxe.diamond", key -> getBasicText(65), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/pickaxe_diamond.png"));
      mapBuilder(
         66, "emi_loot.pickaxe.netherite", key -> getBasicText(66), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/pickaxe_netherite.png")
      );
      mapBuilder(77, "emi_loot.axe.wood", key -> getBasicText(77), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/axe_wood.png"));
      mapBuilder(78, "emi_loot.axe.stone", key -> getBasicText(78), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/axe_stone.png"));
      mapBuilder(79, "emi_loot.axe.iron", key -> getBasicText(79), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/axe_iron.png"));
      mapBuilder(80, "emi_loot.axe.diamond", key -> getBasicText(80), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/axe_diamond.png"));
      mapBuilder(81, "emi_loot.axe.netherite", key -> getBasicText(81), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/axe_netherite.png"));
      mapBuilder(92, "emi_loot.shovel.wood", key -> getBasicText(92), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/shovel_wood.png"));
      mapBuilder(93, "emi_loot.shovel.stone", key -> getBasicText(93), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/shovel_stone.png"));
      mapBuilder(94, "emi_loot.shovel.iron", key -> getBasicText(94), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/shovel_iron.png"));
      mapBuilder(95, "emi_loot.shovel.diamond", key -> getBasicText(95), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/shovel_diamond.png"));
      mapBuilder(
         96, "emi_loot.shovel.netherite", key -> getBasicText(96), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/shovel_netherite.png")
      );
      mapBuilder(107, "emi_loot.hoe.wood", key -> getBasicText(107), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/hoe_wood.png"));
      mapBuilder(108, "emi_loot.hoe.stone", key -> getBasicText(108), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/hoe_stone.png"));
      mapBuilder(109, "emi_loot.hoe.iron", key -> getBasicText(109), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/hoe_iron.png"));
      mapBuilder(110, "emi_loot.hoe.diamond", key -> getBasicText(110), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/hoe_diamond.png"));
      mapBuilder(111, "emi_loot.hoe.netherite", key -> getBasicText(111), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/hoe_netherite.png"));
      mapBuilder(124, "emi_loot.condition.sequence", key -> getBasicText(124), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/sequence.png"));
      mapBuilder(
         125, "emi_loot.condition.direct_drop", key -> getBasicText(125), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/direct_drops.png")
      );
      mapBuilder(
         126, "emi_loot.condition.spawns_with", key -> getBasicText(126), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/spawns_with.png")
      );
      mapBuilder(127, "emi_loot.condition.creeper", key -> getBasicText(127), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/creeper.png"));
      mapBuilder(128, "emi_loot.condition.wither_kill", key -> getBasicText(128), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/wither.png"));
      mapBuilder(
         129, "emi_loot.function.set_any_damage", key -> getBasicText(129), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/damage.png")
      );
      mapBuilder(
         130,
         "emi_loot.function.ominous_banner",
         key -> getBasicText(130),
         ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/ominous.png"),
         ominousProcessor
      );
      mapBuilder(131, "emi_loot.function.set_pages", key -> getBasicText(131), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/set_book.png"));
      mapBuilder(
         132, "emi_loot.function.ominous_bottle", key -> getOneArgText(132, key), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/potion.png")
      );
      mapBuilder(
         133,
         "emi_loot.function.custom_model_data",
         key -> getBasicText(133),
         ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/random_item.png")
      );
      mapBuilder(
         134, "emi_loot.function.toggle_tooltip", key -> getBasicText(134), ResourceLocation.fromNamespaceAndPath("emi_loot", "textures/gui/tooltip.png")
      );
      mapBuilder(150, "emi_loot.no_conditions", key -> getBasicText(150), EMPTY);
   }

   public record TextKeyResult(Component text, List<ItemStack> stacks) {
   }
}
