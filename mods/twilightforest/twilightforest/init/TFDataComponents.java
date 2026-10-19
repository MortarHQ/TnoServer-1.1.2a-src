package twilightforest.init;

import com.mojang.serialization.Codec;
import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Unit;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import twilightforest.components.item.CandelabraData;
import twilightforest.components.item.ItemDisplayContents;
import twilightforest.components.item.JarLid;
import twilightforest.components.item.OreScannerComponent;
import twilightforest.components.item.OreScannerData;
import twilightforest.components.item.PotionFlaskComponent;
import twilightforest.components.item.SkullCandles;
import twilightforest.entity.MagicPaintingVariant;
import twilightforest.init.custom.MagicPaintingVariants;

public class TFDataComponents {
   public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.createDataComponents(
      Registries.DATA_COMPONENT_TYPE, "twilightforest"
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> EMPERORS_CLOTH = COMPONENTS.register(
      "emperors_cloth", () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<PotionFlaskComponent>> POTION_FLASK_CONTENTS = COMPONENTS.register(
      "flask_contents", () -> DataComponentType.builder().persistent(PotionFlaskComponent.CODEC).networkSynchronized(PotionFlaskComponent.STREAM_CODEC).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> INFINITE_GLASS_SWORD = COMPONENTS.register(
      "infinite_glass_sword",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> THROWN_PROJECTILE = COMPONENTS.register(
      "thrown_projectile", () -> DataComponentType.builder().persistent(UUIDUtil.CODEC).networkSynchronized(UUIDUtil.STREAM_CODEC).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> EXPERIMENT_115_VARIANTS = COMPONENTS.register(
      "e115_variant", () -> DataComponentType.builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<SkullCandles>> SKULL_CANDLES = COMPONENTS.register(
      "skull_candles", () -> DataComponentType.builder().persistent(SkullCandles.CODEC).networkSynchronized(SkullCandles.STREAM_CODEC).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<CandelabraData>> CANDELABRA_DATA = COMPONENTS.register(
      "candelabra_data", () -> DataComponentType.builder().persistent(CandelabraData.CODEC).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Holder<MagicPaintingVariant>>> MAGIC_PAINTING_VARIANT = COMPONENTS.register(
      "magic_painting_variant",
      () -> DataComponentType.builder().persistent(MagicPaintingVariants.CODEC).networkSynchronized(MagicPaintingVariants.STREAM_CODEC).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> TRANSLATABLE_BOOK = COMPONENTS.register(
      "translatable_book", () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<JarLid>> JAR_LID = register("jar_lid", JarLid.CODEC);
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CASKET_DAMAGE = COMPONENTS.register(
      "casket_damage", () -> DataComponentType.builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<OreScannerComponent>> ORE_SCANNING = register(
      "ore_scanner", OreScannerComponent.CODEC
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<OreScannerData>> ORE_DATA = register(
      "ore_data", OreScannerData.CODEC, OreScannerData.STREAM_CODEC
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ORE_LOADING = COMPONENTS.register(
      "ore_loading",
      () -> DataComponentType.builder().persistent(ExtraCodecs.NON_NEGATIVE_INT.orElse(0)).networkSynchronized(ByteBufCodecs.VAR_INT).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ORE_RANGE = COMPONENTS.register(
      "ore_range",
      () -> DataComponentType.builder().persistent(ExtraCodecs.NON_NEGATIVE_INT.orElse(1)).networkSynchronized(ByteBufCodecs.VAR_INT).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Block>> ORE_FILTER = COMPONENTS.register(
      "ore_filter",
      () -> DataComponentType.builder()
         .persistent(BuiltInRegistries.BLOCK.byNameCodec().orElse(Blocks.AIR))
         .networkSynchronized(ByteBufCodecs.registry(Registries.BLOCK))
         .cacheEncoding()
         .build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> IS_TRAVELLERS_GEAR = COMPONENTS.register(
      "travellers_armor",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemAttributeModifiers>> STORED_BROKEN_ATTRIBUTES = COMPONENTS.register(
      "stored_broken_attributes",
      () -> DataComponentType.builder()
         .persistent(ItemAttributeModifiers.CODEC)
         .networkSynchronized(ItemAttributeModifiers.STREAM_CODEC)
         .cacheEncoding()
         .build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> TRAVELLERS_HAS_CHESTPLATE = COMPONENTS.register(
      "has_travellers_chestplate",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> TRAVELLERS_HAS_GLOVES = COMPONENTS.register(
      "has_travellers_gloves",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> TRAVELLERS_HAS_BELT = COMPONENTS.register(
      "has_travellers_belt",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> TRAVELLERS_HAS_WINGS = COMPONENTS.register(
      "has_travellers_wings",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> TRAVELLERS_HAS_BOOTS = COMPONENTS.register(
      "has_travellers_boots",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> AUTO_REPAIR_PROBABILITY = COMPONENTS.register(
      "auto_repair_probability",
      () -> DataComponentType.builder().persistent(ExtraCodecs.POSITIVE_FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> ZOOM_ABILITY_MODIFIER = COMPONENTS.register(
      "zoom_ability_modifier",
      () -> DataComponentType.builder().persistent(ExtraCodecs.POSITIVE_FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> RED_THREAD_VISION = COMPONENTS.register(
      "red_thread_vision",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> STEALTH_CROUCHING = COMPONENTS.register(
      "stealth_crouching",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> ARROW_MAGNETISM = COMPONENTS.register(
      "arrow_magnetism",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> EFFICIENT_EATER = COMPONENTS.register(
      "efficient_eater", () -> DataComponentType.builder().persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> PERFECT_DODGE_PROBABILITY = COMPONENTS.register(
      "perfect_dodge_probability",
      () -> DataComponentType.builder().persistent(ExtraCodecs.POSITIVE_FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> HASTE_AMPLIFIER = COMPONENTS.register(
      "haste_amplifier", () -> DataComponentType.builder().persistent(ExtraCodecs.UNSIGNED_BYTE).networkSynchronized(ByteBufCodecs.INT).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> SWAP_HOTBAR_ABILITY = COMPONENTS.register(
      "swap_hotbar_ability",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> SWAP_HOTBAR_MODIFIER = COMPONENTS.register(
      "swap_hotbar_modifier",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> HIGH_JUMP_AMPLIFIER = COMPONENTS.register(
      "high_jump_amplifier",
      () -> DataComponentType.builder().persistent(ExtraCodecs.UNSIGNED_BYTE).networkSynchronized(ByteBufCodecs.INT).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> GRADUALLY_GLIDING_MULTIPLIER = COMPONENTS.register(
      "gradually_gliding_multiplier",
      () -> DataComponentType.builder().persistent(ExtraCodecs.POSITIVE_FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> AGILE_RANGER_MODIFIER = COMPONENTS.register(
      "agile_ranger_modifier",
      () -> DataComponentType.builder().persistent(ExtraCodecs.POSITIVE_FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> DOUBLE_JUMP = COMPONENTS.register(
      "double_jump",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Long>> SIDESTEP_COOLDOWN = COMPONENTS.register(
      "sidestep_cooldown", () -> DataComponentType.builder().persistent(Codec.LONG).networkSynchronized(ByteBufCodecs.VAR_LONG).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> STRAIGHT_AHEAD_MULTIPLIER = COMPONENTS.register(
      "straight_ahead_multiplier", () -> DataComponentType.builder().persistent(Codec.DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> SLIMY_SOLES_COEFFICIENT = COMPONENTS.register(
      "slimy_soles_coefficient",
      () -> DataComponentType.builder().persistent(ExtraCodecs.POSITIVE_FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> WATER_WALK = COMPONENTS.register(
      "water_walk",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> ALL_NIGHT_GOGGLES = COMPONENTS.register(
      "all_night_goggles",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemDisplayContents>> ITEM_DISPLAY = COMPONENTS.register(
      "item_display",
      () -> DataComponentType.builder().persistent(ItemDisplayContents.CODEC).networkSynchronized(ItemDisplayContents.STREAM_CODEC).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> UNRESTRAINED = COMPONENTS.register(
      "unrestrained",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> SWIFT_SWIM = COMPONENTS.register(
      "swift_swim",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> HIGH_STEP = COMPONENTS.register(
      "high_step",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );
   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> AQUATIC_AGILITY = COMPONENTS.register(
      "aquatic_agility",
      () -> DataComponentType.builder().persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).cacheEncoding().build()
   );

   @NotNull
   private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, Codec<T> codec) {
      return register(name, codec, null);
   }

   @NotNull
   private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(
      String name, Codec<T> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec
   ) {
      return streamCodec == null
         ? COMPONENTS.register(name, () -> DataComponentType.builder().persistent(codec).build())
         : COMPONENTS.register(name, () -> DataComponentType.builder().persistent(codec).networkSynchronized(streamCodec).build());
   }
}
