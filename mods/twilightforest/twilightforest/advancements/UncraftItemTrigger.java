package twilightforest.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.ItemPredicate.Builder;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger.SimpleInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import twilightforest.init.TFAdvancements;

public class UncraftItemTrigger extends SimpleCriterionTrigger<UncraftItemTrigger.TriggerInstance> {
   public Codec<UncraftItemTrigger.TriggerInstance> codec() {
      return UncraftItemTrigger.TriggerInstance.CODEC;
   }

   public void trigger(ServerPlayer player, ItemStack stack) {
      this.trigger(player, instance -> instance.matches(stack));
   }

   public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item) implements SimpleInstance {
      public static final Codec<UncraftItemTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(UncraftItemTrigger.TriggerInstance::player),
               ItemPredicate.CODEC.optionalFieldOf("item").forGetter(UncraftItemTrigger.TriggerInstance::item)
            )
            .apply(instance, UncraftItemTrigger.TriggerInstance::new)
      );

      public static Criterion<UncraftItemTrigger.TriggerInstance> uncraftedItem(ItemPredicate predicate) {
         return ((UncraftItemTrigger)TFAdvancements.UNCRAFT_ITEM.get())
            .createCriterion(new UncraftItemTrigger.TriggerInstance(Optional.empty(), Optional.of(predicate)));
      }

      public static Criterion<UncraftItemTrigger.TriggerInstance> uncraftedItem(ItemLike item) {
         return uncraftedItem(Builder.item().of(new ItemLike[]{item}).build());
      }

      public boolean matches(ItemStack item) {
         return this.item.isEmpty() || this.item.get().test(item);
      }
   }
}
