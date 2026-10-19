package it.hurts.octostudios.rarcompat.items.necklace;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.init.EffectRegistry;
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import top.theillusivec4.curios.api.SlotContext;

public class ScarfOfInvisibilityItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("invisible")
                     .stat(
                        StatData.builder("time")
                           .initialValue(140.0, 100.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, -0.05)
                           .formatValue(value -> MathUtils.round(value / 20.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 11, 27)
                           .star(1, 11, 12)
                           .star(2, 11, 8)
                           .star(3, 17, 12)
                           .star(4, 6, 12)
                           .star(5, 6, 17)
                           .link(0, 1)
                           .link(1, 2)
                           .link(1, 3)
                           .link(1, 4)
                           .link(4, 5)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-11426593).borderBottom(-13878416).build())
               .beams(BeamsData.builder().startColor(-11426593).endColor(2898800).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("invisible").initialValue(1).gem(GemShape.SQUARE, GemColor.BLUE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.END_LIKE, LootEntries.THE_END}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && !player.getCommandSenderWorld().isClientSide() && this.isAbilityUnlocked(stack, "invisible")) {
         int time = this.getTime(stack);
         if (time == 0
            && player.getCommandSenderWorld()
               .getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(15.0))
               .stream()
               .noneMatch(mob -> mob.getTarget() == player)) {
            if (!player.hasEffect(EffectRegistry.VANISHING)) {
               this.spreadRelicExperience(player, stack, 1);
            }

            player.addEffect(new MobEffectInstance(EffectRegistry.VANISHING, 4, 0, true, false));
         } else if (time > 0) {
            this.addTime(stack, -1);
         }
      }
   }

   public void addTime(ItemStack stack, int time) {
      this.setTime(stack, this.getTime(stack) + time);
   }

   public void setTime(ItemStack stack, int time) {
      stack.set(DataComponentRegistry.TIME, Math.max(time, 0));
   }

   public int getTime(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
   }

   @EventBusSubscriber
   public static class ScarfOfInvisibilityEvent {
      @SubscribeEvent
      public static void onRightClickBlock(RightClickBlock event) {
         onInteract(event.getEntity());
      }

      @SubscribeEvent
      public static void onRightClickItem(RightClickItem event) {
         onInteract(event.getEntity());
      }

      @SubscribeEvent
      public static void onHarvestCheck(BreakSpeed event) {
         onInteract(event.getEntity());
      }

      @SubscribeEvent
      public static void onAttacking(AttackEntityEvent event) {
         onInteract(event.getEntity());
      }

      private static void onInteract(Player player) {
         if (!player.getCommandSenderWorld().isClientSide()) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.SCARF_OF_INVISIBILITY.value());
            if (stack.getItem() instanceof ScarfOfInvisibilityItem relic) {
               relic.setTime(stack, (int)relic.getStatValue(stack, "invisible", "time"));
            }
         }
      }
   }
}
