package it.hurts.octostudios.reliquified_lenders_cataclysm.items.relics.charm;

import it.hurts.octostudios.reliquified_lenders_cataclysm.entities.VoidVortexModifiedEntity;
import it.hurts.octostudios.reliquified_lenders_cataclysm.init.ItemRegistry;
import it.hurts.octostudios.reliquified_lenders_cataclysm.init.RECDataComponentRegistry;
import it.hurts.octostudios.reliquified_lenders_cataclysm.items.base.RECItem;
import it.hurts.octostudios.reliquified_lenders_cataclysm.items.base.data.RECLootEntries;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.ItemUtils;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.RECMathUtils;
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
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class VoidVortexInBottleItem extends RECItem {
   private static final String ABILITY_ID = "spawn_vortex";

   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("spawn_vortex")
                     .stat(
                        StatData.builder("height")
                           .initialValue(3.0, 4.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.075)
                           .formatValue(RECMathUtils::roundInt)
                           .build()
                     )
                     .stat(
                        StatData.builder("damage")
                           .initialValue(6.0, 8.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.15)
                           .formatValue(RECMathUtils::roundDamage)
                           .build()
                     )
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(30.0, 25.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, -0.06)
                           .formatValue(RECMathUtils::roundOneDigit)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .step(100)
               .maxLevel(10)
               .sources(
                  LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("spawn_vortex").gem(GemShape.SQUARE, GemColor.PURPLE).build()).build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{RECLootEntries.FROSTED_PRISON, LootEntries.THE_END}).build())
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-6922832).borderBottom(-8960882).textured(true).build())
               .beams(BeamsData.builder().startColor(-9624915).endColor(10704581).build())
               .build()
         )
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LivingEntity entity = slotContext.entity();
      if (!stack.isEmpty()) {
         Level level = entity.getCommandSenderWorld();
         if (!level.isClientSide) {
            int cooldownCurrent = this.getVortexCooldown(stack);
            if (cooldownCurrent > 0) {
               if (cooldownCurrent == 1) {
                  ItemUtils.playCooldownSound(level, entity);
               }

               this.reduceVortexCooldown(stack);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onEntityDeath(LivingDeathEvent event) {
      Level level = event.getEntity().level();
      if (!level.isClientSide && event.getSource().getEntity() instanceof Player player) {
         for (ItemStack stack : EntityUtils.findEquippedCurios(player, (Item)ItemRegistry.VOID_VORTEX_IN_BOTTLE.get())) {
            LivingEntity target = event.getEntity();
            if (stack.getItem() instanceof VoidVortexInBottleItem relic && relic.getVortexCooldown(stack) <= 0) {
               VoidVortexModifiedEntity voidVortexEntity = new VoidVortexModifiedEntity(
                  level,
                  target.getX(),
                  target.getY(),
                  target.getZ(),
                  player.getYRot(),
                  player,
                  100,
                  ItemUtils.getIntStat(stack, "spawn_vortex", "height"),
                  relic.getDamageStat(stack)
               );
               voidVortexEntity.setOwner(player);
               level.addFreshEntity(voidVortexEntity);
               relic.spreadRelicExperience(player, stack, 3);
               relic.setVortexCooldown(stack);
            }
         }
      }
   }

   private float getDamageStat(ItemStack stack) {
      return (float)this.getStatValue(stack, "spawn_vortex", "damage");
   }

   private int getVortexCooldown(ItemStack stack) {
      return (Integer)stack.getOrDefault(RECDataComponentRegistry.COOLDOWN, 0);
   }

   private void reduceVortexCooldown(ItemStack stack) {
      stack.set(RECDataComponentRegistry.COOLDOWN, this.getVortexCooldown(stack) - 1);
   }

   private void setVortexCooldown(ItemStack stack) {
      stack.set(RECDataComponentRegistry.COOLDOWN, ItemUtils.getCooldownStat(stack, "spawn_vortex"));
   }
}
