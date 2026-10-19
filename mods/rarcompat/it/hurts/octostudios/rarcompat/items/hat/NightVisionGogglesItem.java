package it.hurts.octostudios.rarcompat.items.hat;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.init.SoundRegistry;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastStage;
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
import java.util.Collection;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
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

public class NightVisionGogglesItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("vision")
                     .active(CastData.builder().type(CastType.TOGGLEABLE).build())
                     .stat(
                        StatData.builder("amount")
                           .initialValue(0.1, 0.15)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.5)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 16, 7)
                           .star(1, 19, 16)
                           .star(2, 17, 21)
                           .star(3, 8, 26)
                           .star(4, 3, 16)
                           .star(5, 6, 7)
                           .star(6, 9, 16)
                           .star(7, 11, 11)
                           .star(8, 13, 16)
                           .link(0, 1)
                           .link(1, 2)
                           .link(2, 3)
                           .link(3, 4)
                           .link(4, 5)
                           .link(5, 6)
                           .link(6, 7)
                           .link(7, 8)
                           .link(0, 8)
                           .link(4, 6)
                           .link(8, 1)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-8061888).borderBottom(-16719810).build())
               .beams(BeamsData.builder().startColor(-8061888).endColor(729645).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("vision").initialValue(1).gem(GemShape.SQUARE, GemColor.CYAN).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.CAVE, LootEntries.MINESHAFT, LootEntries.SCULK}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player) {
         if (this.isAbilityTicking(stack, "vision")) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 10, 0, false, false));
            float percent = Math.abs(1.0F - player.getCommandSenderWorld().getMaxLocalRawBrightness(player.blockPosition()) / 15.0F);
            if (player.getRandom().nextFloat() <= percent
               && player.tickCount % 60 == 0
               && !(Math.abs(player.getKnownMovement().x) <= 0.01)
               && !(Math.abs(player.getKnownMovement().z) <= 0.01)) {
               this.spreadRelicExperience(player, stack, 1);
            }
         } else if (this.isNightVision(player.getActiveEffects())) {
            player.removeEffect(MobEffects.NIGHT_VISION);
         }
      }
   }

   public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
      if (ability.equals("vision") && player.getCommandSenderWorld().isClientSide && stage == CastStage.START) {
         player.playSound((SoundEvent)SoundRegistry.NIGHT_VISION_TOGGLE.get(), 1.0F, 0.75F + player.getRandom().nextFloat() * 0.5F);
      }
   }

   public boolean isNightVision(Collection<MobEffectInstance> activeEffects) {
      return !activeEffects.isEmpty()
         && activeEffects.stream().anyMatch(mobEffectInstance -> mobEffectInstance.is(MobEffects.NIGHT_VISION) && mobEffectInstance.getDuration() <= 10);
   }

   @EventBusSubscriber(Dist.CLIENT)
   public static class NightVisionGogglesEvent {
      @SubscribeEvent
      public static void onFogRender(RenderFog event) {
         Player player = Minecraft.getInstance().player;
         if (player != null) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.NIGHT_VISION_GOGGLES.value());
            if (stack.getItem() instanceof NightVisionGogglesItem relic
               && relic.isAbilityTicking(stack, "vision")
               && (player.hasEffect(MobEffects.BLINDNESS) || player.hasEffect(MobEffects.DARKNESS))) {
               double statValue = relic.getStatValue(stack, "vision", "amount") * (player.hasEffect(MobEffects.BLINDNESS) ? 9 : 1);
               event.scaleFarPlaneDistance((float)(event.getFarPlaneDistance() * statValue));
               event.setCanceled(true);
            }
         }
      }
   }
}
