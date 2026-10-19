package it.hurts.octostudios.rarcompat.items.necklace;

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
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;

public class ShockPendantItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("passive")
                     .maxLevel(0)
                     .research(
                        ResearchData.builder()
                           .star(0, 6, 29)
                           .star(1, 10, 25)
                           .star(2, 12, 29)
                           .star(3, 15, 20)
                           .star(4, 18, 27)
                           .star(5, 8, 17)
                           .star(7, 5, 9)
                           .star(8, 13, 2)
                           .star(9, 16, 5)
                           .star(10, 20, 12)
                           .star(11, 13, 9)
                           .link(1, 0)
                           .link(1, 2)
                           .link(1, 3)
                           .link(3, 4)
                           .link(5, 7)
                           .link(7, 8)
                           .link(9, 10)
                           .link(3, 5)
                           .link(8, 9)
                           .link(10, 3)
                           .link(1, 3)
                           .link(9, 11)
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("lightning")
                     .stat(
                        StatData.builder("damage")
                           .initialValue(1.0, 3.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("chance")
                           .initialValue(0.1, 0.2)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 10, 18)
                           .star(1, 4, 14)
                           .star(2, 11, 13)
                           .star(3, 16, 16)
                           .star(4, 12, 29)
                           .link(1, 0)
                           .link(2, 0)
                           .link(3, 0)
                           .link(4, 0)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-16740147).borderBottom(-15846034).build())
               .beams(BeamsData.builder().startColor(-16740147).endColor(931182).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("lightning").initialValue(1).gem(GemShape.SQUARE, GemColor.CYAN).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.MOUNTAIN}).build())
         .build();
   }

   @EventBusSubscriber
   public static class ShockPendantEvent {
      @SubscribeEvent
      public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
         if (event.getEntity() instanceof Player player && event.getSource().is(DamageTypeTags.IS_LIGHTNING)) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.SHOCK_PENDANT.value());
            if (stack.getItem() instanceof ShockPendantItem relic && relic.canPlayerUseAbility(player, stack, "passive")) {
               event.setCanceled(true);
            }
         }
      }

      @SubscribeEvent
      public static void onReceivingDamage(Post event) {
         if (event.getEntity() instanceof Player player) {
            ItemStack var8 = EntityUtils.findEquippedCurio(player, (Item)ModItems.SHOCK_PENDANT.value());
            Level level = player.getCommandSenderWorld();
            if (!level.isClientSide() && var8.getItem() instanceof ShockPendantItem relic && relic.canPlayerUseAbility(player, var8, "lightning")) {
               RandomSource random = level.getRandom();
               Entity attacker = event.getSource().getEntity();
               if (attacker != null && !(random.nextDouble() > relic.getStatValue(var8, "lightning", "chance"))) {
                  relic.spreadRelicExperience(player, var8, 1);
                  LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                  bolt.setCause((ServerPlayer)player);
                  bolt.setPos(attacker.position());
                  bolt.setVisualOnly(true);
                  level.addFreshEntity(bolt);
                  attacker.invulnerableTime = 0;
                  attacker.hurt(bolt.damageSources().lightningBolt(), (float)relic.getStatValue(var8, "lightning", "damage"));
                  ((ServerLevel)level)
                     .sendParticles(
                        ParticleUtils.constructSimpleSpark(new Color(random.nextInt(50), random.nextInt(50), 50 + random.nextInt(55)), 0.4F, 30, 0.95F),
                        attacker.getX(),
                        attacker.getY() + attacker.getBbHeight() / 2.0F,
                        attacker.getZ(),
                        10,
                        attacker.getBbWidth() / 2.0F,
                        attacker.getBbHeight() / 2.0F,
                        attacker.getBbWidth() / 2.0F,
                        0.025F
                     );
               }
            }
         }
      }
   }
}
