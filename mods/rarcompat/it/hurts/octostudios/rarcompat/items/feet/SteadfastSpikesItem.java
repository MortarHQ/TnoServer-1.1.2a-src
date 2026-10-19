package it.hurts.octostudios.rarcompat.items.feet;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import it.hurts.octostudios.rarcompat.network.packets.SteadfastSpikesPacket;
import it.hurts.sskirillss.relics.api.events.common.LivingSlippingEvent;
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
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import top.theillusivec4.curios.api.SlotContext;

public class SteadfastSpikesItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(AbilityData.builder("passive").maxLevel(0).build())
               .ability(
                  AbilityData.builder("resistance")
                     .stat(
                        StatData.builder("modifier")
                           .initialValue(0.2, 0.3)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.15)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-11260640).borderBottom(-11183260).build())
               .beams(BeamsData.builder().startColor(-11784409).endColor(8077056).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("resistance_1", "resistance").initialValue(1).gem(GemShape.SQUARE, GemColor.YELLOW).build())
                     .source(LevelingSourceData.abilityBuilder("resistance_2", "resistance").initialValue(1).gem(GemShape.SQUARE, GemColor.YELLOW).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.CAVE, LootEntries.MINESHAFT, LootEntries.MOUNTAIN}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player
         && player.getCommandSenderWorld().isClientSide()
         && this.isAbilityUnlocked(stack, "passive")
         && !player.onGround()
         && player.horizontalCollision
         && !(player.getDeltaMovement().y >= -0.1)) {
         NetworkHandler.sendToServer(new SteadfastSpikesPacket());
         player.setDeltaMovement(player.getDeltaMovement().x, -0.05, player.getDeltaMovement().z);
         player.getCommandSenderWorld()
            .addParticle(
               ParticleUtils.constructSimpleSpark(new Color(50, 20 + player.getRandom().nextInt(50), 0), 0.5F, 50, 0.9F),
               player.getX(),
               player.getY(),
               player.getZ(),
               0.0,
               0.0,
               0.0
            );
      }
   }

   @EventBusSubscriber
   public static class SteadfastSpikesEvent {
      @SubscribeEvent
      public static void onLivingKnockBack(LivingKnockBackEvent event) {
         if (event.getEntity() instanceof Player player) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.STEADFAST_SPIKES.value());
            if (stack.getItem() instanceof SteadfastSpikesItem relic) {
               event.setStrength((float)(event.getStrength() * (1.0 - relic.getStatValue(stack, "resistance", "modifier"))));
               relic.spreadRelicExperience(player, stack, 1);
            }
         }
      }

      @SubscribeEvent
      public static void onLivingSlipping(LivingSlippingEvent event) {
         if (!(event.getFriction() <= 0.6F) && event.getEntity() instanceof Player player && !player.isInWater() && !player.isInLava()) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.STEADFAST_SPIKES.value());
            if (stack.getItem() instanceof SteadfastSpikesItem relic) {
               if (player.tickCount % 60 == 0 && player.onGround() && (player.getKnownMovement().x != 0.0 || player.getKnownMovement().z != 0.0)) {
                  relic.spreadRelicExperience(player, stack, 1);
               }

               event.setFriction((float)(event.getFriction() * (1.0 - relic.getStatValue(stack, "resistance", "modifier") / 3.0)));
            }
         }
      }
   }
}
