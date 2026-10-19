package net.p3pp3rf1y.sophisticatedcore.init;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentType.Builder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.p3pp3rf1y.sophisticatedcore.common.gui.SortBy;
import net.p3pp3rf1y.sophisticatedcore.upgrades.EntityMatch;
import net.p3pp3rf1y.sophisticatedcore.upgrades.FilterAttributes;
import net.p3pp3rf1y.sophisticatedcore.upgrades.alchemy.AlchemyFilterAttribute;
import net.p3pp3rf1y.sophisticatedcore.upgrades.cooking.CookingLogic;
import net.p3pp3rf1y.sophisticatedcore.upgrades.feeding.HungerLevel;
import net.p3pp3rf1y.sophisticatedcore.upgrades.filter.Direction;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.RepeatMode;
import net.p3pp3rf1y.sophisticatedcore.upgrades.voiding.VoidType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.xppump.AutomationDirection;
import net.p3pp3rf1y.sophisticatedcore.util.CodecHelper;
import net.p3pp3rf1y.sophisticatedcore.util.SimpleItemContent;

public class ModCoreDataComponents {
   private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(
      BuiltInRegistries.DATA_COMPONENT_TYPE, "sophisticatedcore"
   );
   public static final Supplier<DataComponentType<Integer>> NUMBER_OF_INVENTORY_SLOTS = DATA_COMPONENT_TYPES.register(
      "number_of_inventory_slots", () -> new Builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build()
   );
   public static final Supplier<DataComponentType<Integer>> NUMBER_OF_UPGRADE_SLOTS = DATA_COMPONENT_TYPES.register(
      "number_of_upgrade_slots", () -> new Builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build()
   );
   public static final Supplier<DataComponentType<Integer>> MAIN_COLOR = DATA_COMPONENT_TYPES.register(
      "main_color", () -> new Builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build()
   );
   public static final Supplier<DataComponentType<Integer>> ACCENT_COLOR = DATA_COMPONENT_TYPES.register(
      "accent_color", () -> new Builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build()
   );
   public static final Supplier<DataComponentType<UUID>> STORAGE_UUID = DATA_COMPONENT_TYPES.register(
      "storage_uuid", () -> new Builder().persistent(UUIDUtil.CODEC).networkSynchronized(UUIDUtil.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<Integer>> OPEN_TAB_ID = DATA_COMPONENT_TYPES.register(
      "open_tab_id", () -> new Builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build()
   );
   public static final Supplier<DataComponentType<SortBy>> SORT_BY = DATA_COMPONENT_TYPES.register(
      "sort_by", () -> new Builder().persistent(SortBy.CODEC).networkSynchronized(SortBy.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<CustomData>> RENDER_INFO_TAG = DATA_COMPONENT_TYPES.register(
      "render_info_tag", () -> new Builder().persistent(CustomData.CODEC).networkSynchronized(CustomData.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<Boolean>> SHIFT_CLICK_INTO_STORAGE = DATA_COMPONENT_TYPES.register(
      "shift_click_into_storage", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<Boolean>> REFILL_CRAFTING_GRID = DATA_COMPONENT_TYPES.register(
      "refill_crafting_grid", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<SimpleItemContent>> INPUT_ITEM = DATA_COMPONENT_TYPES.register(
      "input_item", () -> new Builder().persistent(SimpleItemContent.CODEC).networkSynchronized(SimpleItemContent.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<SimpleItemContent>> RESULT_ITEM = DATA_COMPONENT_TYPES.register(
      "result_item", () -> new Builder().persistent(SimpleItemContent.CODEC).networkSynchronized(SimpleItemContent.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<Integer>> ENERGY_STORED = DATA_COMPONENT_TYPES.register(
      "energy_stored", () -> new Builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build()
   );
   public static final Supplier<DataComponentType<Boolean>> COMPACT_NON_UNCRAFTABLE = DATA_COMPONENT_TYPES.register(
      "compact_non_uncraftable", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<Boolean>> SHOULD_WORK_IN_GUI = DATA_COMPONENT_TYPES.register(
      "should_work_in_gui", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<ItemContainerContents>> COOKING_INVENTORY = DATA_COMPONENT_TYPES.register(
      "cooking_inventory",
      () -> new Builder()
         .persistent(CodecHelper.LENIENT_ITEM_CONTAINER_CONTENTS_CODEC)
         .networkSynchronized(ItemContainerContents.STREAM_CODEC)
         .cacheEncoding()
         .build()
   );
   public static final Supplier<DataComponentType<Long>> BURN_TIME_FINISH = DATA_COMPONENT_TYPES.register(
      "burn_time_finish", () -> new Builder().persistent(Codec.LONG).networkSynchronized(ByteBufCodecs.VAR_LONG).build()
   );
   public static final Supplier<DataComponentType<Integer>> BURN_TIME_TOTAL = DATA_COMPONENT_TYPES.register(
      "burn_time_total", () -> new Builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build()
   );
   public static final Supplier<DataComponentType<Long>> COOK_TIME_FINISH = DATA_COMPONENT_TYPES.register(
      "cook_time_finish", () -> new Builder().persistent(Codec.LONG).networkSynchronized(ByteBufCodecs.VAR_LONG).build()
   );
   public static final Supplier<DataComponentType<Integer>> COOK_TIME_TOTAL = DATA_COMPONENT_TYPES.register(
      "cook_time_total", () -> new Builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build()
   );
   public static final Supplier<DataComponentType<Boolean>> IS_COOKING = DATA_COMPONENT_TYPES.register(
      "is_cooking", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<HungerLevel>> FEED_AT_HUNGER_LEVEL = DATA_COMPONENT_TYPES.register(
      "feed_at_hunger_level", () -> new Builder().persistent(HungerLevel.CODEC).networkSynchronized(HungerLevel.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<Boolean>> FEED_IMMEDIATELY_WHEN_HURT = DATA_COMPONENT_TYPES.register(
      "feed_immediately_when_hurt", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<Direction>> DIRECTION = DATA_COMPONENT_TYPES.register(
      "direction", () -> new Builder().persistent(Direction.CODEC).networkSynchronized(Direction.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<Boolean>> IS_PLAYING = DATA_COMPONENT_TYPES.register(
      "is_playing", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<Boolean>> PICKUP_ITEMS = DATA_COMPONENT_TYPES.register(
      "pickup_items", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<Boolean>> PICKUP_XP = DATA_COMPONENT_TYPES.register(
      "pickup_xp", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<List<SimpleFluidContent>>> FLUID_FILTERS = DATA_COMPONENT_TYPES.register(
      "fluid_filters",
      () -> new Builder()
         .persistent(Codec.list(SimpleFluidContent.CODEC))
         .networkSynchronized(SimpleFluidContent.STREAM_CODEC.apply(ByteBufCodecs.list()))
         .build()
   );
   public static final Supplier<DataComponentType<Boolean>> IS_INPUT = DATA_COMPONENT_TYPES.register(
      "is_input", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<Boolean>> INTERACT_WITH_HAND = DATA_COMPONENT_TYPES.register(
      "interact_with_hand", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<Boolean>> INTERACT_WITH_WORLD = DATA_COMPONENT_TYPES.register(
      "interact_with_world", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<Boolean>> INTERACT_WITH_FLUID_HANDLERS = DATA_COMPONENT_TYPES.register(
      "interact_with_fluid_handler", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<ResourceLocation>> RECIPE_ID = DATA_COMPONENT_TYPES.register(
      "recipe_id", () -> new Builder().persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<SimpleFluidContent>> FLUID_CONTENTS = DATA_COMPONENT_TYPES.register(
      "fluid_contents", () -> new Builder().persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC).build()
   );
   @Deprecated
   public static final Supplier<DataComponentType<Boolean>> LEGACY_SHOULD_VOID_OVERFLOW = DATA_COMPONENT_TYPES.register(
      "should_void_overflow", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<VoidType>> VOID_TYPE = DATA_COMPONENT_TYPES.register(
      "void_type", () -> new Builder().persistent(VoidType.CODEC).networkSynchronized(VoidType.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<AutomationDirection>> AUTOMATION_DIRECTION = DATA_COMPONENT_TYPES.register(
      "automation_direction", () -> new Builder().persistent(AutomationDirection.CODEC).networkSynchronized(AutomationDirection.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<Integer>> LEVEL = DATA_COMPONENT_TYPES.register(
      "level", () -> new Builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build()
   );
   public static final Supplier<DataComponentType<Integer>> LEVELS_TO_STORE = DATA_COMPONENT_TYPES.register(
      "levels_to_store", () -> new Builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build()
   );
   public static final Supplier<DataComponentType<Integer>> LEVELS_TO_TAKE = DATA_COMPONENT_TYPES.register(
      "levels_to_take", () -> new Builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build()
   );
   public static final Supplier<DataComponentType<Boolean>> MEND_ITEMS = DATA_COMPONENT_TYPES.register(
      "mend_items", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<FilterAttributes>> FILTER_ATTRIBUTES = DATA_COMPONENT_TYPES.register(
      "filter_attributes", () -> new Builder().persistent(FilterAttributes.CODEC).networkSynchronized(FilterAttributes.STREAM_CODEC).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<FilterAttributes>> INPUT_FILTER_ATTRIBUTES = DATA_COMPONENT_TYPES.register(
      "input_filter_attributes", () -> new Builder().persistent(FilterAttributes.CODEC).networkSynchronized(FilterAttributes.STREAM_CODEC).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<FilterAttributes>> FUEL_FILTER_ATTRIBUTES = DATA_COMPONENT_TYPES.register(
      "fuel_filter_attributes", () -> new Builder().persistent(FilterAttributes.CODEC).networkSynchronized(FilterAttributes.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<List<AlchemyFilterAttribute>>> ALCHEMY_FILTER_ATTRIBUTES = DATA_COMPONENT_TYPES.register(
      "alchemy_filter_attributes",
      () -> new Builder()
         .persistent(Codec.list(AlchemyFilterAttribute.CODEC))
         .networkSynchronized(AlchemyFilterAttribute.STREAM_CODEC.apply(ByteBufCodecs.list()))
         .build()
   );
   public static final Supplier<DataComponentType<Boolean>> ENABLED = DATA_COMPONENT_TYPES.register(
      "enabled", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<RepeatMode>> REPEAT_MODE = DATA_COMPONENT_TYPES.register(
      "repeat_mode", () -> new Builder().persistent(RepeatMode.CODEC).networkSynchronized(RepeatMode.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<Boolean>> SHUFFLE = DATA_COMPONENT_TYPES.register(
      "shuffle", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<Integer>> DISC_SLOT_ACTIVE = DATA_COMPONENT_TYPES.register(
      "disc_slot_active", () -> new Builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build()
   );
   public static final Supplier<DataComponentType<Long>> DISC_FINISH_TIME = DATA_COMPONENT_TYPES.register(
      "disc_finish_time", () -> new Builder().persistent(Codec.LONG).networkSynchronized(ByteBufCodecs.VAR_LONG).build()
   );
   public static final Supplier<DataComponentType<Boolean>> MATCH_ALL_EFFECTS = DATA_COMPONENT_TYPES.register(
      "match_all_effects", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<Boolean>> MATCH_EFFECT_DURATION = DATA_COMPONENT_TYPES.register(
      "match_effect_duration", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<Boolean>> MATCH_EFFECT_AMPLIFIER = DATA_COMPONENT_TYPES.register(
      "match_effect_amplifier", () -> new Builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build()
   );
   public static final Supplier<DataComponentType<EntityMatch>> ENTITY_MATCH = DATA_COMPONENT_TYPES.register(
      "entity_match", () -> new Builder().persistent(EntityMatch.CODEC).networkSynchronized(EntityMatch.STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<Map<ResourceLocation, Integer>>> RECIPES_USED = DATA_COMPONENT_TYPES.register(
      "recipes_used", () -> new Builder().persistent(CookingLogic.RECIPES_USED_CODEC).networkSynchronized(CookingLogic.RECIPES_USED_STREAM_CODEC).build()
   );
   public static final Supplier<DataComponentType<Float>> STORED_XP = DATA_COMPONENT_TYPES.register(
      "stored_xp", () -> new Builder().persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).build()
   );
   public static final Supplier<DataComponentType<ItemContainerContents>> LENIENT_CONTAINER = DATA_COMPONENT_TYPES.register(
      "lenient_container",
      () -> new Builder()
         .persistent(CodecHelper.LENIENT_ITEM_CONTAINER_CONTENTS_CODEC)
         .networkSynchronized(ItemContainerContents.STREAM_CODEC)
         .cacheEncoding()
         .build()
   );

   public static void register(IEventBus modBus) {
      DATA_COMPONENT_TYPES.register(modBus);
   }
}
