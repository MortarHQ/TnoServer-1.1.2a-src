package twilightforest.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger.SimpleInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.init.TFAdvancements;

public class KillBugTrigger extends SimpleCriterionTrigger<KillBugTrigger.TriggerInstance> {
   public Codec<KillBugTrigger.TriggerInstance> codec() {
      return KillBugTrigger.TriggerInstance.CODEC;
   }

   public void trigger(ServerPlayer player, BlockState bug) {
      this.trigger(player, instance -> instance.matches(bug));
   }

   public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<Block> bugType) implements SimpleInstance {
      public static final Codec<KillBugTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(KillBugTrigger.TriggerInstance::player),
               BuiltInRegistries.BLOCK.byNameCodec().optionalFieldOf("bug").forGetter(KillBugTrigger.TriggerInstance::bugType)
            )
            .apply(instance, KillBugTrigger.TriggerInstance::new)
      );

      public static Criterion<KillBugTrigger.TriggerInstance> killBug(Block bug) {
         return ((KillBugTrigger)TFAdvancements.KILL_BUG.get()).createCriterion(new KillBugTrigger.TriggerInstance(Optional.empty(), Optional.of(bug)));
      }

      public boolean matches(BlockState bug) {
         return this.bugType.isEmpty() || bug.is(this.bugType.get());
      }
   }
}
