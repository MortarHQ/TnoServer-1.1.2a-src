package it.hurts.octostudios.rarcompat.items.feet;

import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
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
import it.hurts.sskirillss.relics.utils.WorldUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class SnowshoesItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(AbilityData.builder("passive").maxLevel(0).build())
               .ability(
                  AbilityData.builder("speed")
                     .stat(
                        StatData.builder("amount")
                           .initialValue(0.2, 0.4)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 6, 7)
                           .star(1, 11, 3)
                           .star(2, 16, 8)
                           .star(3, 15, 16)
                           .star(4, 14, 25)
                           .star(5, 8, 25)
                           .star(6, 9, 17)
                           .link(0, 1)
                           .link(1, 2)
                           .link(2, 3)
                           .link(3, 4)
                           .link(4, 5)
                           .link(5, 6)
                           .link(6, 0)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-5338802).borderBottom(-10403546).build())
               .beams(BeamsData.builder().startColor(-5338802).endColor(6373670).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("speed").initialValue(1).gem(GemShape.SQUARE, GemColor.CYAN).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.FROST, LootEntries.TAIGA, LootEntries.MOUNTAIN}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && !player.getCommandSenderWorld().isClientSide() && this.canPlayerUseAbility(player, stack, "speed")) {
         double step = 0.01;
         double modifier = this.isStandingOnSnow(player) ? step : -step;
         if (modifier != 0.0) {
            this.addSpeed(stack, modifier);
         }

         EntityUtils.resetAttribute(player, stack, Attributes.MOVEMENT_SPEED, (float)this.getSpeed(stack), Operation.ADD_MULTIPLIED_BASE);
         if (this.isStandingOnSnow(player) && player.tickCount % 60 == 0 && (player.getKnownMovement().x != 0.0 || player.getKnownMovement().z != 0.0)) {
            this.spreadRelicExperience(player, stack, 1);
         }
      }
   }

   public void addSpeed(ItemStack stack, double val) {
      if (this.getSpeed(stack) <= this.getStatValue(stack, "speed", "amount") || val < 0.0) {
         this.setSpeed(stack, this.getSpeed(stack) + val);
      }
   }

   public double getSpeed(ItemStack stack) {
      return (Double)stack.getOrDefault(DataComponentRegistry.SPEED, 0.0);
   }

   public void setSpeed(ItemStack stack, double val) {
      stack.set(DataComponentRegistry.SPEED, Math.max(val, 0.0));
   }

   private boolean isStandingOnSnow(Player player) {
      for (int i = 0; i < 8; i++) {
         if (player.getCommandSenderWorld()
            .getBlockState(player.blockPosition().atY((int)Math.floor(WorldUtils.getGroundHeight(player, player.position().add(0.0, 0.1, 0.0), 8))).below(i))
            .is(BlockTags.SNOW)) {
            return true;
         }
      }

      return false;
   }

   public boolean canWalkOnPowderedSnow(SlotContext slotContext, ItemStack stack) {
      return slotContext.entity() instanceof Player player
         ? this.canPlayerUseAbility(player, stack, "passive")
         : super.canWalkOnPowderedSnow(slotContext, stack);
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      if (newStack.getItem() != stack.getItem()) {
         EntityUtils.removeAttribute(slotContext.entity(), stack, Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE);
      }
   }
}
