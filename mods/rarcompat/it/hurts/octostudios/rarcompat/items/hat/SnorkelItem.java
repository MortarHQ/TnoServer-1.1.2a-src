package it.hurts.octostudios.rarcompat.items.hat;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
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
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;
import top.theillusivec4.curios.api.SlotContext;

public class SnorkelItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(AbilityData.builder("passive").maxLevel(0).build())
               .ability(
                  AbilityData.builder("diving")
                     .stat(
                        StatData.builder("duration")
                           .initialValue(5.0, 10.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> (int)MathUtils.round(value, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 5, 11)
                           .star(1, 12, 11)
                           .star(2, 16, 15)
                           .star(3, 12, 20)
                           .star(4, 7, 20)
                           .star(5, 4, 16)
                           .star(6, 11, 25)
                           .star(7, 16, 24)
                           .star(8, 20, 9)
                           .link(0, 1)
                           .link(1, 2)
                           .link(2, 3)
                           .link(3, 4)
                           .link(4, 5)
                           .link(2, 8)
                           .link(2, 7)
                           .link(6, 7)
                           .link(4, 6)
                           .link(0, 5)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-14501864).borderBottom(-16742756).build())
               .beams(BeamsData.builder().startColor(-16734539).endColor(1215255).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("diving").initialValue(1).gem(GemShape.SQUARE, GemColor.CYAN).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.AQUATIC}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && player.tickCount % 10 == 0 && this.canPlayerUseAbility(player, stack, "diving")) {
         Boolean toggled = (Boolean)stack.getOrDefault(DataComponentRegistry.TOGGLED, false);
         if (player.isUnderWater()) {
            if (!toggled) {
               stack.set(DataComponentRegistry.TOGGLED, true);
               MobEffectInstance effect = player.getEffect(MobEffects.WATER_BREATHING);
               int currentDuration = effect != null ? effect.getDuration() : 0;
               int resultDuration = (int)this.getStatValue(stack, "diving", "duration");
               if (resultDuration * 20 > currentDuration) {
                  this.spreadRelicExperience(player, stack, (int)Math.abs(Math.ceil((resultDuration - currentDuration) / 20.0F)));
                  player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, resultDuration * 20, 0, true, true));
               }
            }
         } else if (toggled) {
            stack.set(DataComponentRegistry.TOGGLED, false);
         }
      }
   }

   @EventBusSubscriber(Dist.CLIENT)
   public static class SnorkelEvent {
      @SubscribeEvent
      public static void onFogRender(RenderFog event) {
         Player player = Minecraft.getInstance().player;
         if (player != null) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.SNORKEL.value());
            player.getCommandSenderWorld().getFluidState(BlockPos.containing(player.getEyePosition()));
            if (stack.getItem() instanceof SnorkelItem && player.isInLiquid()) {
               event.scaleFarPlaneDistance(150.0F);
               event.setCanceled(true);
            }
         }
      }
   }
}
