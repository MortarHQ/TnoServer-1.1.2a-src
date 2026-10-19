package it.hurts.octostudios.reliquified_twilight_forest.item.relic;

import it.hurts.octostudios.reliquified_twilight_forest.data.loot.LootEntries;
import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.util.MathButCool;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
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
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;
import twilightforest.entity.projectile.ChainBlock;
import twilightforest.init.TFEntities;

@EventBusSubscriber
public class SteelCapeItem extends RelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("iron_guard")
                     .stat(
                        StatData.builder("flat_armor")
                           .initialValue(0.2, 0.4)
                           .upgradeModifier(UpgradeOperation.ADD, 0.16)
                           .formatValue(MathButCool::roundSingleDigit)
                           .build()
                     )
                     .stat(
                        StatData.builder("chance")
                           .initialValue(0.1, 0.25)
                           .upgradeModifier(UpgradeOperation.ADD, 0.025)
                           .formatValue(MathButCool::percentage)
                           .build()
                     )
                     .stat(
                        StatData.builder("damage")
                           .initialValue(4.0, 6.0)
                           .upgradeModifier(UpgradeOperation.ADD, 0.6)
                           .formatValue(MathButCool::roundSingleDigit)
                           .build()
                     )
                     .stat(
                        StatData.builder("stun_duration")
                           .initialValue(10.0, 20.0)
                           .upgradeModifier(UpgradeOperation.ADD, 8.0)
                           .formatValue(MathButCool::ticksToSecondsAndRoundSingleDigit)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(StyleData.builder().beams(BeamsData.builder().startColor(-3092272).endColor(0).build()).build())
         .leveling(
            LevelingData.builder()
               .sources(
                  LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("iron_guard").gem(GemShape.SQUARE, GemColor.CYAN).build()).build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.STRONGHOLD}).build())
         .build();
   }

   @SubscribeEvent
   public static void onLivingDamage(Pre e) {
      Entity entity = e.getSource().getEntity();
      LivingEntity victim = e.getEntity();
      ItemStack stack = EntityUtils.findEquippedCurio(victim, (Item)ItemRegistry.STEEL_CAPE.get());
      if (!victim.level().isClientSide && !e.getSource().is(DamageTypeTags.BYPASSES_ARMOR) && stack.getItem() instanceof SteelCapeItem relic) {
         float var9 = e.getNewDamage() - (float)relic.getStatValue(stack, "iron_guard", "flat_armor");
         e.setNewDamage(Math.max(var9, 0.001F));
         if (!(var9 <= 0.0F) && !(victim.getRandom().nextDouble() > relic.getStatValue(stack, "iron_guard", "chance"))) {
            if (entity instanceof LivingEntity source) {
               ChainBlock chain = new ChainBlock((EntityType)TFEntities.CHAIN_BLOCK.get(), victim.level(), victim, null, stack);
               chain.setPos(victim.position().add(0.0, victim.getBbHeight() / 2.0F, 0.0));
               Vec3 direction = source.position().add(0.0, source.getBbHeight() / 2.0F, 0.0).subtract(chain.position()).normalize();
               chain.shoot(direction.x, direction.y, direction.z, 1.5F, 1.0F);
               victim.level().addFreshEntity(chain);
               relic.spreadRelicExperience(victim, stack, 1);
            }
         }
      }
   }

   public String getConfigRoute() {
      return "reliquified_twilight_forest";
   }
}
