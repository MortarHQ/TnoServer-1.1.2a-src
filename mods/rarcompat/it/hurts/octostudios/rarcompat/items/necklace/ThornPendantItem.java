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
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;

public class ThornPendantItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("poison")
                     .stat(
                        StatData.builder("multiplier")
                           .initialValue(0.05, 0.15)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("time")
                           .initialValue(2.0, 4.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.3)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("chance")
                           .initialValue(0.1, 0.2)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.15)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 0))
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
               .tooltip(TooltipData.builder().borderTop(-16733141).borderBottom(-16759255).build())
               .beams(BeamsData.builder().startColor(-16733141).endColor(17961).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("poison").initialValue(1).gem(GemShape.SQUARE, GemColor.GREEN).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.TROPIC}).build())
         .build();
   }

   @EventBusSubscriber
   public static class ThornPendantEvent {
      @SubscribeEvent
      public static void onReceivingDamage(Post event) {
         if (event.getEntity() instanceof Player player
            && event.getSource().getEntity() instanceof LivingEntity attacker
            && !attacker.getStringUUID().equals(player.getStringUUID())) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.THORN_PENDANT.value());
            RandomSource random = player.getRandom();
            Level level = player.getCommandSenderWorld();
            if (!level.isClientSide()
               && stack.getItem() instanceof ThornPendantItem relic
               && relic.canPlayerUseAbility(player, stack, "poison")
               && !(random.nextDouble() > relic.getStatValue(stack, "poison", "chance"))) {
               relic.spreadRelicExperience(player, stack, 1);
               attacker.invulnerableTime = 0;
               attacker.hurt(level.damageSources().thorns(player), (float)(event.getNewDamage() * relic.getStatValue(stack, "poison", "multiplier")));
               attacker.addEffect(new MobEffectInstance(MobEffects.POISON, (int)(relic.getStatValue(stack, "poison", "time") * 20.0), 1));
               ((ServerLevel)level)
                  .sendParticles(
                     ParticleUtils.constructSimpleSpark(new Color(50 + random.nextInt(50), 200 + random.nextInt(55), 50 + random.nextInt(50)), 0.4F, 30, 0.95F),
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
