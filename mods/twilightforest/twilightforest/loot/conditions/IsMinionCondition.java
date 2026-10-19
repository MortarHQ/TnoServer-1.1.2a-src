package twilightforest.loot.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import javax.annotation.Nonnull;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;
import twilightforest.entity.monster.CarminiteGhastling;
import twilightforest.init.TFLoot;

public record IsMinionCondition(boolean inverse) implements LootItemCondition {
   public static final MapCodec<IsMinionCondition> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.BOOL.fieldOf("inverse").forGetter(o -> o.inverse)).apply(instance, IsMinionCondition::new)
   );

   public LootItemConditionType getType() {
      return (LootItemConditionType)TFLoot.IS_MINION.get();
   }

   public boolean test(@Nonnull LootContext context) {
      return context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof CarminiteGhastling ghastling && ghastling.isMinion() == !this.inverse;
   }

   public static Builder builder(boolean inverse) {
      return () -> new IsMinionCondition(inverse);
   }
}
