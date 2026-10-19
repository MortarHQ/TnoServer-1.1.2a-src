package it.hurts.octostudios.rarcompat.items.charm;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicAttributeModifier;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicAttributeModifier.Modifier;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemColor;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemShape;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.items.relics.base.data.research.ResearchData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import top.theillusivec4.curios.api.SlotContext;

public class CrystalHeartItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("heart")
                     .stat(
                        StatData.builder("amount")
                           .initialValue(2.0, 6.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> (int)MathUtils.round(value, 1))
                           .build()
                     )
                     .research(ResearchData.builder().star(0, 6, 12).star(1, 16, 12).star(2, 11, 22).link(0, 1).link(1, 2).link(2, 0).link(1, 0).build())
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-1435881).borderBottom(-8585216).build())
               .beams(BeamsData.builder().startColor(-2751484).endColor(15888239).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("heart").initialValue(1).gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.CAVE, LootEntries.MINESHAFT}).build())
         .build();
   }

   public RelicAttributeModifier getRelicAttributeModifiers(ItemStack stack) {
      return !this.isAbilityUnlocked(stack, "heart")
         ? super.getRelicAttributeModifiers(stack)
         : RelicAttributeModifier.builder()
            .attribute(new Modifier(Attributes.MAX_HEALTH, (float)this.getStatValue(stack, "heart", "amount"), Operation.ADD_VALUE))
            .build();
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && newStack.getItem() != stack.getItem()) {
         EntityUtils.removeAttribute(player, stack, Attributes.MAX_HEALTH, Operation.ADD_VALUE);
      }
   }

   @EventBusSubscriber
   public static class CrystalHeartEvent {
      @SubscribeEvent
      public static void onLivingHealEvent(LivingHealEvent event) {
         if (event.getEntity() instanceof Player player) {
            ItemStack stack = EntityUtils.findEquippedCurio(event.getEntity(), (Item)ModItems.CRYSTAL_HEART.value());
            if (stack.getItem() instanceof CrystalHeartItem relic && relic.isAbilityUnlocked(stack, "heart")) {
               float maxHealth = player.getMaxHealth();
               if (player.getRandom().nextFloat() <= Math.max(0.1, (maxHealth - player.getHealth()) / maxHealth)) {
                  relic.spreadRelicExperience(player, stack, 1);
               }
            }
         }
      }
   }
}
