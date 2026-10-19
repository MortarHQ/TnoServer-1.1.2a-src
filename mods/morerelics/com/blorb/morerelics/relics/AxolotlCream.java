package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

@EventBusSubscriber
public class AxolotlCream extends MoreRelicBase {
   public AxolotlCream(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("mend_moisturize")
                     .stat(
                        StatData.builder("multip")
                           .initialValue(0.6, 1.0)
                           .upgradeModifier(UpgradeOperation.ADD, 0.1)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(3)
                     .requiredLevel(0)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(3)
               .initialCost(120)
               .step(20)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("mend_moisturize").build()).build())
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.AQUATIC, LootEntries.TROPIC}).build())
         .build();
   }

   @SubscribeEvent
   public static void onDamageTaken(LivingHealEvent event) {
      LivingEntity entity = event.getEntity();
      if (!entity.level().isClientSide) {
         double multiplier = EntityUtils.findEquippedCurios(entity, (Item)MoreRelics.AXOLOTL_CREAM.get()).stream().mapToDouble(stack -> {
            AxolotlCream item = (AxolotlCream)stack.getItem();
            if (!item.isAbilityUnlocked(stack, "mend_moisturize")) {
               return 0.0;
            }

            double mult = item.getStatValue(stack, "mend_moisturize", "multip");
            if (mult > 0.0) {
               item.spreadRelicExperience(entity, stack, 2);
            }

            return mult;
         }).sum();
         event.setAmount((float)(event.getAmount() * (1.0 + multiplier)));
      }
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
      ArrayList<Component> list = new ArrayList<>();
      list.add(Component.translatable("tooltip.morerelics.axolotl_cream.source").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
      return list;
   }
}
