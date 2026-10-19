package it.hurts.octostudios.rarcompat.items.charm;

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
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

public class ObsidianSkullItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("hell")
                     .stat(
                        StatData.builder("duration")
                           .initialValue(40.0, 60.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.3)
                           .formatValue(value -> MathUtils.round(value / 20.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 8, 25)
                           .star(1, 3, 16)
                           .star(2, 6, 8)
                           .star(3, 12, 4)
                           .star(4, 17, 7)
                           .star(5, 19, 13)
                           .star(6, 14, 22)
                           .star(7, 11, 15)
                           .link(0, 1)
                           .link(0, 7)
                           .link(2, 3)
                           .link(3, 4)
                           .link(4, 5)
                           .link(5, 7)
                           .link(5, 6)
                           .link(7, 6)
                           .link(1, 2)
                           .link(1, 7)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-12704166).borderBottom(-15398100).build())
               .beams(BeamsData.builder().startColor(-6006785).endColor(395873).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("hell").initialValue(1).gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.NETHER_LIKE, LootEntries.THE_NETHER}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && !player.getCommandSenderWorld().isClientSide()) {
         if (this.getCooldown(stack) >= 60) {
            this.addTime(stack, -1);
         } else if (this.getTime(stack) > 0) {
            this.addCooldown(stack, 1);
         }

         if (player.isOnFire() && this.getTime(stack) < this.getMaxTime(stack)) {
            if (player.tickCount % 20 == 0) {
               this.spreadRelicExperience(player, stack, 1);
            }

            this.addTime(stack, 1);
         }
      }
   }

   public int getBarWidth(ItemStack stack) {
      return Math.max(0, Math.round(13.0F * (this.getMaxTime(stack) - this.getTime(stack)) / this.getMaxTime(stack)));
   }

   public boolean isBarVisible(@NotNull ItemStack stack) {
      return this.getTime(stack) != 0 && this.hasUnlockedAbility(stack);
   }

   public int getBarColor(@NotNull ItemStack stack) {
      return Mth.hsvToRgb(Math.max(0.0F, Math.min(1.0F, 1.0F - Math.max(0.0F, (float)this.getTime(stack) / this.getMaxTime(stack))) / 3.0F), 1.0F, 1.0F);
   }

   public int getMaxTime(ItemStack stack) {
      return (int)MathUtils.round(this.getStatValue(stack, "hell", "duration"), 0);
   }

   public void addTime(ItemStack stack, int time) {
      this.setTime(stack, this.getTime(stack) + time);
   }

   public int getTime(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
   }

   public void setTime(ItemStack stack, int val) {
      stack.set(DataComponentRegistry.TIME, Math.max(val, 0));
   }

   public void addCooldown(ItemStack stack, int time) {
      this.setCooldown(stack, this.getCooldown(stack) + time);
   }

   public int getCooldown(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.COOLDOWN, 0);
   }

   public void setCooldown(ItemStack stack, int val) {
      stack.set(DataComponentRegistry.COOLDOWN, Math.max(val, 0));
   }

   @EventBusSubscriber
   public static class ObsidianSkull {
      @SubscribeEvent
      public static void onIncomingDamage(LivingIncomingDamageEvent event) {
         Level level = event.getEntity().getCommandSenderWorld();
         if (event.getEntity() instanceof Player player && event.getSource().is(DamageTypeTags.IS_FIRE) && !level.isClientSide()) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.OBSIDIAN_SKULL.value());
            if (stack.getItem() instanceof ObsidianSkullItem relic && relic.isAbilityUnlocked(stack, "hell")) {
               if (!player.isOnFire()) {
                  relic.addTime(stack, 1);
               }

               relic.setCooldown(stack, 0);
               if (relic.getTime(stack) >= relic.getMaxTime(stack)) {
                  relic.setCooldown(stack, 0);
               } else {
                  event.setCanceled(true);
                  relic.setCooldown(stack, 0);
                  RandomSource random = level.getRandom();
                  ((ServerLevel)level)
                     .sendParticles(
                        ParticleUtils.constructSimpleSpark(new Color(64 + random.nextInt(64), random.nextInt(50), 200 + random.nextInt(55)), 0.35F, 10, 0.9F),
                        player.getX(),
                        player.getY() + player.getBbHeight() / 2.0F,
                        player.getZ(),
                        1,
                        player.getBbWidth() / 2.0F,
                        player.getBbHeight() / 2.0F,
                        player.getBbWidth() / 2.0F,
                        0.025F
                     );
               }
            }
         }
      }
   }
}
