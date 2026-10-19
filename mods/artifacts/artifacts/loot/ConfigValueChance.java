package artifacts.loot;

import artifacts.Artifacts;
import artifacts.registry.ModLootConditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;

public record ConfigValueChance(ConfigValueChance.ChanceConfig chanceConfig) implements LootItemCondition {
   public static final MapCodec<ConfigValueChance> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(ConfigValueChance.ChanceConfig.CODEC.fieldOf("config").forGetter(c -> c.chanceConfig)).apply(instance, ConfigValueChance::new)
   );

   public LootItemConditionType getType() {
      return (LootItemConditionType)ModLootConditions.CONFIG_VALUE_CHANCE.value();
   }

   public boolean test(LootContext context) {
      return context.getRandom().nextDouble() < this.chanceConfig.value.get();
   }

   public static Builder archaeologyChance() {
      return () -> new ConfigValueChance(ConfigValueChance.ChanceConfig.ARCHAEOLOGY);
   }

   public static Builder entityEquipmentChance() {
      return () -> new ConfigValueChance(ConfigValueChance.ChanceConfig.ENTITY_EQUIPMENT);
   }

   public static Builder everlastingBeefChance() {
      return () -> new ConfigValueChance(ConfigValueChance.ChanceConfig.EVERLASTING_BEEF);
   }

   private enum ChanceConfig implements StringRepresentable {
      ARCHAEOLOGY("archaeology", Artifacts.CONFIG.general.archaeologyChance),
      ENTITY_EQUIPMENT("entity_equipment", Artifacts.CONFIG.general.entityEquipmentChance),
      EVERLASTING_BEEF("everlasting_beef", Artifacts.CONFIG.items.everlastingBeefDropRate);

      private static final Codec<ConfigValueChance.ChanceConfig> CODEC = StringRepresentable.fromEnum(ConfigValueChance.ChanceConfig::values);
      final String name;
      final Supplier<Double> value;

      ChanceConfig(String name, Supplier<Double> value) {
         this.name = name;
         this.value = value;
      }

      public String getSerializedName() {
         return this.name;
      }
   }
}
