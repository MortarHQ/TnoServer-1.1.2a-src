package gardensofthedead.loot;

import com.mojang.serialization.MapCodec;
import gardensofthedead.platform.PlatformServices;
import gardensofthedead.registry.ModLootConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder;

public class MatchShears implements LootItemCondition {
   public static final MapCodec<MatchShears> CODEC = MapCodec.unit(new MatchShears());

   public LootItemConditionType getType() {
      return (LootItemConditionType)ModLootConditions.MATCH_SHEARS.get();
   }

   public static Builder matchShears() {
      return MatchShears::new;
   }

   public boolean test(LootContext context) {
      return context.hasParam(LootContextParams.TOOL) ? test((ItemStack)context.getParam(LootContextParams.TOOL)) : false;
   }

   public static boolean test(ItemStack tool) {
      return PlatformServices.platformHelper.isShears(tool);
   }
}
