package artifacts.component.ability;

import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public record EnchantmentLevelModifiers(List<EnchantmentLevelModifiers.Entry> entries) implements CompositeAbility<EnchantmentLevelModifiers.Entry> {
   public static final List<ResourceKey<Enchantment>> ALLOWED_ENCHANTMENTS = List.of(
      Enchantments.FORTUNE, Enchantments.LOOTING, Enchantments.LURE, Enchantments.LUCK_OF_THE_SEA
   );
   public static final Codec<EnchantmentLevelModifiers> CODEC = CompositeAbility.codec(
      EnchantmentLevelModifiers.Entry.CODEC, EnchantmentLevelModifiers::new, EnchantmentLevelModifiers::entries
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, EnchantmentLevelModifiers> STREAM_CODEC = CompositeAbility.streamCodec(
      EnchantmentLevelModifiers.Entry.STREAM_CODEC, EnchantmentLevelModifiers::new, EnchantmentLevelModifiers::entries
   );

   public record Entry(ResourceKey<Enchantment> enchantment, Value<Integer> amount) implements EquipmentAbility {
      public static final Codec<EnchantmentLevelModifiers.Entry> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ResourceKey.codec(Registries.ENCHANTMENT)
                  .validate(
                     enchantment -> EnchantmentLevelModifiers.ALLOWED_ENCHANTMENTS.contains(enchantment)
                        ? DataResult.success(enchantment)
                        : DataResult.error(() -> "Unsupported enchantment: %s".formatted(enchantment.location()))
                  )
                  .fieldOf("enchantment")
                  .forGetter(EnchantmentLevelModifiers.Entry::enchantment),
               ValueTypes.ENCHANTMENT_LEVEL.codec().optionalFieldOf("level", Value.of(1)).forGetter(EnchantmentLevelModifiers.Entry::amount)
            )
            .apply(instance, EnchantmentLevelModifiers.Entry::new)
      );
      public static final StreamCodec<RegistryFriendlyByteBuf, EnchantmentLevelModifiers.Entry> STREAM_CODEC = StreamCodec.composite(
         ResourceKey.streamCodec(Registries.ENCHANTMENT),
         EnchantmentLevelModifiers.Entry::enchantment,
         ValueTypes.ENCHANTMENT_LEVEL.streamCodec(),
         EnchantmentLevelModifiers.Entry::amount,
         EnchantmentLevelModifiers.Entry::new
      );

      @Override
      public boolean isNonCosmetic() {
         return this.amount.get() > 0;
      }

      @Override
      public void addToTooltip(EquipmentAbility.TooltipWriter writer) {
         String enchantmentName = this.enchantment().location().getPath();
         if (this.amount().get() == 1) {
            writer.add("%s.single_level".formatted(enchantmentName));
         } else {
            writer.add("%s.multiple_levels".formatted(enchantmentName), this.amount().get());
         }
      }
   }
}
