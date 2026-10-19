package it.hurts.octostudios.rarcompat.items.charm;

import artifacts.registry.ModItems;
import be.florens.expandability.api.EventResult;
import be.florens.expandability.api.forge.PlayerSwimEvent;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import it.hurts.octostudios.rarcompat.network.packets.FlamingoSwimPacket;
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
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent.Key;
import net.neoforged.neoforge.common.NeoForgeMod;
import top.theillusivec4.curios.api.SlotContext;

public class HeliumFlamingoItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("flying")
                     .stat(
                        StatData.builder("time")
                           .initialValue(3.0, 5.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("speed")
                           .initialValue(0.2, 0.3)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 2, 6)
                           .star(1, 8, 10)
                           .star(2, 6, 19)
                           .star(3, 15, 21)
                           .star(4, 20, 11)
                           .link(0, 2)
                           .link(1, 2)
                           .link(2, 3)
                           .link(3, 4)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-754286).borderBottom(-4967837).build())
               .beams(BeamsData.builder().startColor(-219985).endColor(11809379).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("flying").initialValue(1).gem(GemShape.SQUARE, GemColor.PURPLE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.AQUATIC, LootEntries.THE_END, LootEntries.END_LIKE}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && this.isAbilityUnlocked(stack, "flying")) {
         if (player.getCommandSenderWorld().isClientSide() && HeliumFlamingoItem.HeliumFlamingoClientEvent.onDoubleJump) {
            HeliumFlamingoItem.HeliumFlamingoClientEvent.ticKCount++;
            if (HeliumFlamingoItem.HeliumFlamingoClientEvent.ticKCount % 10 == 0) {
               HeliumFlamingoItem.HeliumFlamingoClientEvent.onDoubleJump = false;
               HeliumFlamingoItem.HeliumFlamingoClientEvent.ticKCount = 0;
            }
         }

         if (player.onGround() || player.isInLiquid()) {
            this.setTime(stack, 0);
         }

         if (player.tickCount % 20 == 0 && this.getToggled(stack)) {
            this.addTime(stack, 1);
            this.spreadRelicExperience(player, stack, 1);
         }

         if (this.getTime(stack) >= (int)MathUtils.round(this.getStatValue(stack, "flying", "time"), 0) && !player.isInLiquid()) {
            player.setDeltaMovement(player.getDeltaMovement().x, -0.25, player.getKnownMovement().z);
            player.fallDistance = 0.0F;
            this.setToggled(stack, false);
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && newStack != stack) {
         EntityUtils.removeAttribute(player, stack, NeoForgeMod.SWIM_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         this.setToggled(stack, false);
         this.setTime(stack, 0);
      }
   }

   public boolean getToggled(ItemStack stack) {
      return (Boolean)stack.getOrDefault(DataComponentRegistry.TOGGLED, false);
   }

   public void setToggled(ItemStack stack, boolean val) {
      stack.set(DataComponentRegistry.TOGGLED, val);
   }

   public void addTime(ItemStack stack, int time) {
      this.setTime(stack, this.getTime(stack) + time);
   }

   public int getTime(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
   }

   public void setTime(ItemStack stack, int val) {
      stack.set(DataComponentRegistry.TIME, Math.max(val, 0));
   }

   @EventBusSubscriber(Dist.CLIENT)
   public static class HeliumFlamingoClientEvent {
      private static boolean onDoubleJump = false;
      private static int ticKCount;

      @SubscribeEvent
      public static void onClientTick(Key event) {
         Minecraft minecraft = Minecraft.getInstance();
         LocalPlayer player = minecraft.player;
         if (player != null) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.HELIUM_FLAMINGO.value());
            if (minecraft.screen == null
               && event.getAction() == 1
               && stack.getItem() instanceof HeliumFlamingoItem relic
               && relic.canPlayerUseAbility(player, stack, "flying")
               && event.getKey() == minecraft.options.keyJump.getKey().getValue()) {
               if (!onDoubleJump) {
                  onDoubleJump = true;
               } else if (!player.mayFly()) {
                  int statValue = (int)MathUtils.round(relic.getStatValue(stack, "flying", "time"), 0);
                  int time = relic.getTime(stack);
                  if (time >= statValue) {
                     return;
                  }

                  NetworkHandler.sendToServer(new FlamingoSwimPacket(!relic.getToggled(stack)));
                  if (!relic.getToggled(stack) && !player.isInLiquid()) {
                     player.setDeltaMovement(player.getDeltaMovement().add(player.getLookAngle().scale(0.6F)));
                  }
               } else if (relic.getToggled(stack)) {
                  NetworkHandler.sendToServer(new FlamingoSwimPacket(false));
               }
            }
         }
      }
   }

   @EventBusSubscriber
   public static class HeliumFlamingoEvent {
      @SubscribeEvent
      public static void onSwimAir(PlayerSwimEvent event) {
         Player player = event.getEntity();
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.HELIUM_FLAMINGO.value());
         if (stack.getItem() instanceof HeliumFlamingoItem relic && relic.isAbilityUnlocked(stack, "flying")) {
            if (relic.getToggled(stack)) {
               event.setResult(EventResult.SUCCESS);
               player.setSprinting(true);
               EntityUtils.applyAttribute(
                  player, stack, NeoForgeMod.SWIM_SPEED, (float)relic.getStatValue(stack, "flying", "speed"), Operation.ADD_MULTIPLIED_TOTAL
               );
            } else {
               event.setResult(EventResult.PASS);
               EntityUtils.removeAttribute(player, stack, NeoForgeMod.SWIM_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
            }
         }
      }
   }
}
