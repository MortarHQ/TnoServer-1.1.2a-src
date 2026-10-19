package twilightforest.data;

import java.util.concurrent.CompletableFuture;
import net.minecraft.advancements.critereon.ItemPredicate.Builder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import twilightforest.init.TFItems;
import twilightforest.loot.conditions.GiantPickUsedCondition;
import twilightforest.loot.modifiers.FieryToolSmeltingModifier;
import twilightforest.loot.modifiers.GiantToolGroupingModifier;

public class LootModifierGenerator extends GlobalLootModifierProvider {
   public LootModifierGenerator(PackOutput output, CompletableFuture<Provider> provider) {
      super(output, provider, "twilightforest");
   }

   protected void start() {
      this.add(
         "fiery_pick_smelting",
         new FieryToolSmeltingModifier(
            new LootItemCondition[]{MatchTool.toolMatches(Builder.item().of(new ItemLike[]{(ItemLike)TFItems.FIERY_PICKAXE.get()})).build()}
         ),
         new ICondition[0]
      );
      this.add(
         "giant_pick_grouping",
         new GiantToolGroupingModifier(new LootItemCondition[]{GiantPickUsedCondition.builder(EntityTarget.THIS).build()}),
         new ICondition[0]
      );
   }
}
