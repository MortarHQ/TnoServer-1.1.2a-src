package it.hurts.octostudios.rarcompat.items.hat;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
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
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;

public class AnglersHatItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("catch")
                     .stat(
                        StatData.builder("chance")
                           .initialValue(0.1, 0.2)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.25)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 16, 2)
                           .star(1, 12, 5)
                           .star(2, 5, 4)
                           .star(3, 10, 10)
                           .star(4, 6, 15)
                           .star(5, 5, 24)
                           .star(6, 16, 15)
                           .star(7, 18, 21)
                           .link(0, 1)
                           .link(1, 2)
                           .link(2, 3)
                           .link(3, 4)
                           .link(4, 5)
                           .link(3, 6)
                           .link(6, 7)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-6254456).borderBottom(-11385022).build())
               .beams(BeamsData.builder().startColor(-3657969).endColor(4405814).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("catch").initialValue(1).gem(GemShape.SQUARE, GemColor.BLUE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.AQUATIC, LootEntries.VILLAGE}).build())
         .build();
   }

   @EventBusSubscriber
   public static class AnglersHatEvent {
      @SubscribeEvent
      public static void onItemFished(ItemFishedEvent event) {
         Player player = event.getEntity();
         Level level = player.getCommandSenderWorld();
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.ANGLERS_HAT.value());
         if (!level.isClientSide() && stack.getItem() instanceof AnglersHatItem relic && relic.canPlayerUseAbility(player, stack, "catch")) {
            ServerLevel serverLevel = (ServerLevel)level;
            RandomSource random = serverLevel.getRandom();
            int rolls = MathUtils.multicast(random, relic.getStatValue(stack, "catch", "chance"));
            if (rolls > 0) {
               relic.spreadRelicExperience(player, stack, random.nextInt(rolls) + 1);
            }

            LootTable loottable = serverLevel.getServer().reloadableRegistries().getLootTable(BuiltInLootTables.FISHING);
            LootParams lootparams = new Builder(serverLevel)
               .withParameter(LootContextParams.ORIGIN, player.position())
               .withParameter(LootContextParams.TOOL, stack)
               .withParameter(LootContextParams.THIS_ENTITY, player)
               .create(LootContextParamSets.FISHING);
            FishingHook fishingHook = event.getHookEntity();

            for (int i = 0; i < rolls; i++) {
               ObjectListIterator var12 = loottable.getRandomItems(lootparams).iterator();

               while (var12.hasNext()) {
                  ItemStack itemstack = (ItemStack)var12.next();
                  ItemEntity itementity = new ItemEntity(serverLevel, fishingHook.getX(), fishingHook.getY(), fishingHook.getZ(), itemstack);
                  double x = player.getX() - fishingHook.getX();
                  double y = player.getY() - fishingHook.getY();
                  double z = player.getZ() - fishingHook.getZ();
                  itementity.setDeltaMovement(x * 0.1, y * 0.1 + Math.sqrt(Math.sqrt(x * x + y * y + z * z)) * 0.08, z * 0.1);
                  serverLevel.addFreshEntity(itementity);
                  serverLevel.addFreshEntity(new ExperienceOrb(serverLevel, player.getX(), player.getY() + 0.5, player.getZ() + 0.5, random.nextInt(6) + 1));
               }
            }
         }
      }
   }
}
