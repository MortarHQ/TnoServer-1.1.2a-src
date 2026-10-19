package noobanidus.mods.lootr.common.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.StringRepresentable.EnumCodec;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;

public record LootCount(List<LootCount.Operation> operations) implements LootItemCondition {
   public static final MapCodec<LootCount> CODEC = RecordCodecBuilder.mapCodec(
      builder -> builder.group(
            LootCount.Operation.CODEC
               .listOf()
               .xmap(operationList -> operationList.stream().sorted(Comparator.comparingInt(LootCount.Operation::getPrecedence)).toList(), Function.identity())
               .fieldOf("operations")
               .forGetter(LootCount::operations)
         )
         .apply(builder, LootCount::new)
   );

   public LootItemConditionType getType() {
      return LootrRegistry.getLootCount();
   }

   public boolean test(LootContext lootContext) {
      Vec3 incomingPos = (Vec3)lootContext.getParam(LootContextParams.ORIGIN);
      BlockPos position = new BlockPos((int)incomingPos.x, (int)incomingPos.y, (int)incomingPos.z);
      BlockEntity blockEntity = lootContext.getLevel().getBlockEntity(position);
      ILootrBlockEntity ibe = LootrAPI.resolveBlockEntity(blockEntity);
      if (ibe != null && ibe.hasLootTable()) {
         Set<UUID> actualOpeners = ibe.getActualOpeners();
         if (actualOpeners == null) {
            return false;
         }

         int count = ibe.getActualOpeners().size() + 1;

         for (LootCount.Operation op : this.operations) {
            if (!op.test(count)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(LootContextParams.ORIGIN);
   }

   public enum Operand implements BiPredicate<Integer, Integer>, StringRepresentable {
      EQUALS(Integer::equals, 0),
      NOT_EQUALS((a, b) -> !a.equals(b), 0),
      LESS_THAN((a, b) -> a < b, 1),
      GREATER_THAN((a, b) -> a > b, 1),
      LESS_THAN_EQUALS((a, b) -> a <= b, 1),
      GREATER_THAN_EQUALS((a, b) -> a >= b, 1);

      public static final EnumCodec<LootCount.Operand> CODEC = StringRepresentable.fromEnum(LootCount.Operand::values);
      private final BiPredicate<Integer, Integer> predicate;
      private final int precedence;

      Operand(BiPredicate<Integer, Integer> predicate, int precedence) {
         this.predicate = predicate;
         this.precedence = precedence;
      }

      public boolean test(Integer integer, Integer integer2) {
         return this.predicate.test(integer, integer2);
      }

      public int getPrecedence() {
         return this.precedence;
      }

      public String getSerializedName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }

   public record Operation(LootCount.Operand operand, int value) implements Predicate<Integer> {
      public static final Codec<LootCount.Operation> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               LootCount.Operand.CODEC.fieldOf("type").forGetter(LootCount.Operation::operand),
               PrimitiveCodec.INT.fieldOf("value").forGetter(LootCount.Operation::value)
            )
            .apply(instance, LootCount.Operation::new)
      );

      public int getPrecedence() {
         return this.operand.getPrecedence();
      }

      public boolean test(Integer integer) {
         return this.operand.test(integer, this.value);
      }
   }
}
