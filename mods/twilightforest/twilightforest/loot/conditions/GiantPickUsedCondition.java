package twilightforest.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Set;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;
import twilightforest.components.entity.GiantPickaxeMiningAttachment;
import twilightforest.init.TFDataAttachments;
import twilightforest.init.TFLoot;

public record GiantPickUsedCondition(EntityTarget target) implements LootItemCondition {
   public static final MapCodec<GiantPickUsedCondition> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(EntityTarget.CODEC.fieldOf("entity").forGetter(o -> o.target)).apply(instance, GiantPickUsedCondition::new)
   );

   public LootItemConditionType getType() {
      return (LootItemConditionType)TFLoot.GIANT_PICK_USED_CONDITION.get();
   }

   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(this.target.getParam());
   }

   public boolean test(LootContext context) {
      if (!(context.getParamOrNull(this.target.getParam()) instanceof Player player)) {
         return false;
      } else {
         GiantPickaxeMiningAttachment attachment = (GiantPickaxeMiningAttachment)player.getData(TFDataAttachments.GIANT_PICKAXE_MINING);
         return player.level().getGameTime() == attachment.getMining() && attachment.canMakeGiantBlock();
      }
   }

   public static Builder builder(EntityTarget target) {
      return () -> new GiantPickUsedCondition(target);
   }
}
