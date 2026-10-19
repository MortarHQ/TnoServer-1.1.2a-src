package it.hurts.octostudios.rarcompat.items.hands;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
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
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import top.theillusivec4.curios.api.SlotContext;

public class PickaxeHeaterItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("heater")
                     .active(CastData.builder().type(CastType.TOGGLEABLE).build())
                     .stat(
                        StatData.builder("capacity")
                           .initialValue(20.0, 25.0)
                           .upgradeModifier(UpgradeOperation.ADD, 5.0)
                           .formatValue(value -> (int)MathUtils.round(value, 0))
                           .build()
                     )
                     .stat(
                        StatData.builder("duration")
                           .initialValue(140.0, 120.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, -0.083)
                           .formatValue(value -> MathUtils.round(value / 20.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 11, 28)
                           .star(1, 11, 18)
                           .star(2, 19, 24)
                           .star(3, 11, 6)
                           .star(4, 4, 9)
                           .star(5, 18, 9)
                           .link(0, 1)
                           .link(1, 2)
                           .link(3, 1)
                           .link(3, 4)
                           .link(3, 5)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-7103326).borderBottom(-9868420).build())
               .beams(BeamsData.builder().startColor(-7103326).endColor(6908796).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("heater").initialValue(1).gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.CAVE, LootEntries.MINESHAFT}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player
         && player.tickCount % tnoSafeDuration(this.getStatValue(stack, "heater", "duration")) == 0L
         && getCharges(stack) < Math.round(this.getStatValue(stack, "heater", "capacity"))) {
         addCharges(stack, 1);
      }
   }

   public static void addCharges(ItemStack stack, int amount) {
      stack.set(DataComponentRegistry.CHARGE, getCharges(stack) + amount);
   }

   public static int getCharges(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0);
   }

   public static ItemStack getSmeltingResult(ItemStack stack, ServerLevel level) {
      Optional<RecipeHolder<SmeltingRecipe>> entry = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(stack), level);
      if (entry.isPresent()) {
         ItemStack result = ((SmeltingRecipe)entry.get().value()).getResultItem(level.registryAccess());
         if (!result.isEmpty()) {
            return result.copyWithCount(stack.getCount() * result.getCount());
         }
      }

      return stack;
   }

   public static long tnoSafeDuration(double var0) {
      long var2 = Math.round(var0);
      return var2 - 1L >> 63 & (var2 ^ 1L) ^ var2;
   }

   @EventBusSubscriber
   public static class PickaxeHeaterEvent {
      @SubscribeEvent
      public static void onPlayerAttack(BlockDropsEvent event) {
         if (event.getBreaker() instanceof Player player) {
            ItemStack var11 = EntityUtils.findEquippedCurio(player, (Item)ModItems.PICKAXE_HEATER.value());
            Level level = player.getCommandSenderWorld();
            if (!level.isClientSide()
               && var11.getItem() instanceof PickaxeHeaterItem relic
               && relic.isAbilityTicking(var11, "heater")
               && PickaxeHeaterItem.getCharges(var11) > 1) {
               ServerLevel serverLevel = (ServerLevel)level;
               boolean smelted = false;

               for (ItemEntity drop : event.getDrops()) {
                  ItemStack source = drop.getItem();
                  ItemStack result = PickaxeHeaterItem.getSmeltingResult(source, serverLevel);
                  if (!smelted && source != result) {
                     smelted = true;
                  }

                  drop.setItem(result);
               }

               if (smelted) {
                  Vec3 center = event.getPos().getCenter();
                  RandomSource random = level.getRandom();
                  serverLevel.sendParticles(
                     ParticleUtils.constructSimpleSpark(new Color(150 + random.nextInt(106), random.nextInt(50), 50 + random.nextInt(51), 255), 0.6F, 20, 0.85F),
                     center.x(),
                     center.y(),
                     center.z(),
                     25,
                     0.3,
                     0.3,
                     0.3,
                     0.01
                  );
                  PickaxeHeaterItem.addCharges(var11, -1);
                  relic.spreadRelicExperience(player, var11, 1);
               }
            }
         }
      }
   }
}
