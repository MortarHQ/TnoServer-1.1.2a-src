package it.hurts.octostudios.rarcompat.items.charm;

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
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent.Added;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

public class AntidoteVesselItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("antidote")
                     .stat(
                        StatData.builder("amount")
                           .initialValue(0.2, 0.4)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder().star(0, 4, 7).star(1, 12, 18).star(2, 5, 26).star(3, 15, 26).link(0, 1).link(1, 2).link(1, 2).link(1, 3).build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("devourer")
                     .requiredLevel(5)
                     .stat(
                        StatData.builder("duration")
                           .initialValue(1.0, 1.5)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.4)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-1991387).borderBottom(-8634333).build())
               .beams(BeamsData.builder().startColor(-203175).endColor(6102291).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(15)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("antidote").initialValue(1).gem(GemShape.SQUARE, GemColor.YELLOW).build())
                     .source(LevelingSourceData.abilityBuilder("devourer").initialValue(1).gem(GemShape.SQUARE, GemColor.BLUE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.TROPIC}).build())
         .build();
   }

   @EventBusSubscriber
   public static class AntidoteVeselEvents {
      @SubscribeEvent
      public static void onIncomingDamage(AttackEntityEvent event) {
         if (event.getTarget() instanceof LivingEntity target && !target.getCommandSenderWorld().isClientSide() && !target.getActiveEffects().isEmpty()) {
            Player player = event.getEntity();
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.ANTIDOTE_VESSEL.value());
            if (stack.getItem() instanceof AntidoteVesselItem relic
               && relic.canPlayerUseAbility(player, stack, "devourer")
               && !(player.getAttackStrengthScale(0.0F) <= 0.9F)) {
               for (MobEffectInstance activeEffect : target.getActiveEffects()
                  .stream()
                  .filter(effect -> ((MobEffect)effect.getEffect().value()).isBeneficial())
                  .toList()) {
                  int transferDuration = (int)(activeEffect.getDuration() - relic.getStatValue(stack, "devourer", "duration") * 20.0);
                  target.removeEffect(activeEffect.getEffect());
                  target.addEffect(new MobEffectInstance(activeEffect.getEffect(), transferDuration, activeEffect.getAmplifier()));
                  MobEffectInstance existingEffect = player.getEffect(activeEffect.getEffect());
                  int amplifier = existingEffect == null ? 1 : activeEffect.getAmplifier();
                  int transferDuration1 = (int)relic.getStatValue(stack, "devourer", "duration") * 20;
                  if (existingEffect != null) {
                     transferDuration1 += existingEffect.getDuration();
                     player.removeEffect(activeEffect.getEffect());
                  }

                  player.addEffect(new MobEffectInstance(activeEffect.getEffect(), transferDuration1, amplifier));
                  relic.spreadRelicExperience(player, stack, 1);
               }
            }
         }
      }

      @SubscribeEvent
      public static void onAddedEffect(Added event) {
         MobEffectInstance effectDuration = event.getEffectInstance();
         if (event.getEntity() instanceof Player player
            && !player.getCommandSenderWorld().isClientSide()
            && !((MobEffect)effectDuration.getEffect().value()).isBeneficial()) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.ANTIDOTE_VESSEL.value());
            if (stack.getItem() instanceof AntidoteVesselItem relic && relic.canPlayerUseAbility(player, stack, "antidote")) {
               effectDuration.duration = (int)(effectDuration.getDuration() * (1.0 - relic.getStatValue(stack, "antidote", "amount")));
               relic.spreadRelicExperience(player, stack, 1);
            }
         }
      }
   }
}
