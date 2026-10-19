package it.hurts.octostudios.rarcompat.items.charm;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import it.hurts.octostudios.rarcompat.network.packets.DoubleJumpPacket;
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
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent.Key;
import top.theillusivec4.curios.api.SlotContext;

public class CloudInBottleItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("jump")
                     .requiredPoints(2)
                     .stat(
                        StatData.builder("count")
                           .initialValue(1.0, 3.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.0)
                           .formatValue(value -> (int)MathUtils.round(value, 0))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 15, 3)
                           .star(1, 19, 5)
                           .star(2, 9, 8)
                           .star(3, 14, 9)
                           .star(4, 17, 11)
                           .star(5, 9, 15)
                           .star(6, 9, 20)
                           .star(7, 3, 24)
                           .star(8, 15, 24)
                           .star(9, 9, 27)
                           .link(0, 2)
                           .link(0, 3)
                           .link(1, 3)
                           .link(3, 4)
                           .link(3, 5)
                           .link(5, 6)
                           .link(6, 7)
                           .link(6, 8)
                           .link(9, 7)
                           .link(9, 8)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-3085591).borderBottom(-10711616).build())
               .beams(BeamsData.builder().startColor(-12417035).endColor(9680621).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("jump").initialValue(1).gem(GemShape.SQUARE, GemColor.BLUE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.MOUNTAIN, LootEntries.THE_END, LootEntries.END_LIKE}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && this.canPlayerUseAbility(player, stack, "jump") && player.onGround() && this.getCount(stack) > 0) {
         this.setCount(stack, 0);
      }
   }

   public void addCount(ItemStack stack, int amount) {
      this.setCount(stack, this.getCount(stack) + amount);
   }

   public int getCount(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.COUNT, 0);
   }

   public void setCount(ItemStack stack, int val) {
      stack.set(DataComponentRegistry.COUNT, Math.max(val, 0));
   }

   @EventBusSubscriber(Dist.CLIENT)
   public static class CloudInBottleEvent {
      @SubscribeEvent
      public static void onMouseInput(Key event) {
         Minecraft minecraft = Minecraft.getInstance();
         LocalPlayer player = minecraft.player;
         if (player != null) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.CLOUD_IN_A_BOTTLE.value());
            if (minecraft.screen == null
               && event.getAction() == 1
               && stack.getItem() instanceof CloudInBottleItem relic
               && relic.canPlayerUseAbility(player, stack, "jump")
               && event.getKey() == minecraft.options.keyJump.getKey().getValue()
               && relic.getCount(stack) < Math.round(relic.getStatValue(stack, "jump", "count"))
               && !player.isFallFlying()
               && !player.onGround()) {
               NetworkHandler.sendToServer(new DoubleJumpPacket());
               player.jumpFromGround();
            }
         }
      }
   }
}
