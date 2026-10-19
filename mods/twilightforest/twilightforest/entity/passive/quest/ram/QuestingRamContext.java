package twilightforest.entity.passive.quest.ram;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootTable;
import twilightforest.loot.TFLootTables;

public record QuestingRamContext(Map<DyeColor, Ingredient> questItems, ResourceKey<LootTable> lootTable) {
   public static final QuestingRamContext FALLBACK = new QuestingRamContext(
      ImmutableMap.builder()
         .put(DyeColor.WHITE, Ingredient.of(new ItemLike[]{Items.WHITE_WOOL}))
         .put(DyeColor.LIGHT_GRAY, Ingredient.of(new ItemLike[]{Items.LIGHT_GRAY_WOOL}))
         .put(DyeColor.GRAY, Ingredient.of(new ItemLike[]{Items.GRAY_WOOL}))
         .put(DyeColor.BLACK, Ingredient.of(new ItemLike[]{Items.BLACK_WOOL}))
         .put(DyeColor.RED, Ingredient.of(new ItemLike[]{Items.RED_WOOL}))
         .put(DyeColor.ORANGE, Ingredient.of(new ItemLike[]{Items.ORANGE_WOOL}))
         .put(DyeColor.YELLOW, Ingredient.of(new ItemLike[]{Items.YELLOW_WOOL}))
         .put(DyeColor.GREEN, Ingredient.of(new ItemLike[]{Items.GREEN_WOOL}))
         .put(DyeColor.LIME, Ingredient.of(new ItemLike[]{Items.LIME_WOOL}))
         .put(DyeColor.BLUE, Ingredient.of(new ItemLike[]{Items.BLUE_WOOL}))
         .put(DyeColor.CYAN, Ingredient.of(new ItemLike[]{Items.CYAN_WOOL}))
         .put(DyeColor.LIGHT_BLUE, Ingredient.of(new ItemLike[]{Items.LIGHT_BLUE_WOOL}))
         .put(DyeColor.PURPLE, Ingredient.of(new ItemLike[]{Items.PURPLE_WOOL}))
         .put(DyeColor.MAGENTA, Ingredient.of(new ItemLike[]{Items.MAGENTA_WOOL}))
         .put(DyeColor.PINK, Ingredient.of(new ItemLike[]{Items.PINK_WOOL}))
         .put(DyeColor.BROWN, Ingredient.of(new ItemLike[]{Items.BROWN_WOOL}))
         .build(),
      TFLootTables.QUESTING_RAM_REWARDS
   );
   public static final Codec<QuestingRamContext> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.unboundedMap(DyeColor.CODEC, Ingredient.CODEC_NONEMPTY)
               .validate(QuestingRamContext::validate)
               .fieldOf("items")
               .forGetter(QuestingRamContext::questItems),
            ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("reward").forGetter(QuestingRamContext::lootTable)
         )
         .apply(instance, QuestingRamContext::new)
   );

   private static DataResult<Map<DyeColor, Ingredient>> validate(Map<DyeColor, Ingredient> map) {
      int colorFlags = 0;

      for (DyeColor color : map.keySet()) {
         colorFlags |= 1 << color.getId();
      }

      return Integer.bitCount(colorFlags) == 16 ? DataResult.success(map) : DataResult.error(() -> "Questing Ram quest must contain all 16 dye colors");
   }
}
