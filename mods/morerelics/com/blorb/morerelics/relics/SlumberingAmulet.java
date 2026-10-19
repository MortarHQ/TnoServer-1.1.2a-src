package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.dataComponents.IntegerCounter;
import com.blorb.morerelics.dataComponents.StarCounter;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import top.theillusivec4.curios.api.SlotContext;

public class SlumberingAmulet extends MoreRelicBase {
   public final int starEatTimer = 60;
   public final int expTimer = 10;

   public SlumberingAmulet(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(AbilityData.builder("odd_aura").maxLevel(1).requiredLevel(0).build())
               .ability(AbilityData.builder("awakening_potential").requiredLevel(10).maxLevel(1).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(10)
               .initialCost(75)
               .step(50)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("odd_aura").initialValue(1).build()).build())
               .build()
         )
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player) {
         if (!player.level().isClientSide) {
            IntegerCounter counter = DataComponentHandler.getIntegerCounter(stack);
            int expProgress = counter.current();
            if (expProgress < 10) {
               expProgress++;
            } else {
               this.addRelicExperience(slotContext.entity(), stack, 1);
               expProgress = 0;
            }

            stack.set(DataComponentHandler.INT_COUNTER.get(), counter.getUpdatedCounter(expProgress));
            if (this.isAbilityUnlocked(stack, "awakening_potential")) {
               ItemStack mainHandItem = player.getMainHandItem();
               StarCounter starCounter = DataComponentHandler.getStarCounter(stack);
               int starEatProgress = starCounter.timerCurrent();
               int netherStartEaten = starCounter.count();
               if (mainHandItem.is(Items.NETHER_STAR)) {
                  if (starEatProgress == 0) {
                     Component message = Component.translatable("amulet.morerelics.absorbing_star")
                        .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(16737894)));
                     player.sendSystemMessage(message);
                     starEatProgress++;
                  } else if (starEatProgress < 60) {
                     starEatProgress++;
                  } else {
                     mainHandItem.consume(1, player);
                     if (++netherStartEaten < 2) {
                        Component message = Component.translatable("amulet.morerelics.craves_more")
                           .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(14067711)));
                        player.sendSystemMessage(message);
                        starEatProgress = 0;
                     } else {
                        Component message = Component.translatable("amulet.morerelics.amulet_transforms")
                           .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(16766720)));
                        player.sendSystemMessage(message);
                        EntityUtils.addItem(player, MoreRelics.WHISPERING_AMULET.toStack());
                        stack.setCount(0);
                     }
                  }
               } else {
                  starEatProgress = 0;
               }

               stack.set(DataComponentHandler.STAR_COUNTER.get(), starCounter.getUpdatedCounter(netherStartEaten, starEatProgress));
            }
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      StarCounter starCounter = DataComponentHandler.getStarCounter(stack);
      stack.set(DataComponentHandler.STAR_COUNTER.get(), starCounter.getUpdatedCounter(starCounter.count(), 0));
      super.onUnequip(slotContext, newStack, stack);
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
      ArrayList<Component> list = new ArrayList<>();
      list.add(Component.translatable("tooltip.morerelics.slumbering_amulet.source").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
      return list;
   }
}
