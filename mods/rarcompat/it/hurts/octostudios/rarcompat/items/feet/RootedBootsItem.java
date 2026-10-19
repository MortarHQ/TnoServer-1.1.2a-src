package it.hurts.octostudios.rarcompat.items.feet;

import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
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
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import top.theillusivec4.curios.api.SlotContext;

public class RootedBootsItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("devouring")
                     .active(CastData.builder().type(CastType.TOGGLEABLE).build())
                     .stat(
                        StatData.builder("frequency")
                           .initialValue(140.0, 120.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, -0.071)
                           .formatValue(value -> MathUtils.round(value / 20.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 4, 25)
                           .star(1, 9, 17)
                           .star(2, 5, 12)
                           .star(3, 15, 6)
                           .star(4, 16, 15)
                           .star(5, 18, 23)
                           .link(0, 1)
                           .link(1, 2)
                           .link(2, 3)
                           .link(3, 4)
                           .link(4, 5)
                           .link(5, 0)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-11325407).borderBottom(-7683840).build())
               .beams(BeamsData.builder().startColor(-7157751).endColor(4941828).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("devouring").initialValue(1).gem(GemShape.SQUARE, GemColor.YELLOW).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.OVERWORLD}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      Level level = slotContext.entity().getCommandSenderWorld();
      if (slotContext.entity() instanceof Player player && !level.isClientSide() && this.isAbilityTicking(stack, "devouring")) {
         BlockPos blockPos = player.blockPosition().below();
         FoodData footData = player.getFoodData();
         if (player.tickCount % Math.round(this.getStatValue(stack, "devouring", "frequency")) == 0L
            && footData.needsFood()
            && level.getBlockState(blockPos).is(Blocks.GRASS_BLOCK)) {
            footData.add(1, 1.0F);
            this.spreadRelicExperience(player, stack, 1);
            level.setBlock(blockPos, Blocks.DIRT.defaultBlockState(), 3);
            RandomSource random = player.getRandom();
            level.playSound(null, player, SoundEvents.GRASS_BREAK, SoundSource.PLAYERS, 1.0F, 0.9F + random.nextFloat() * 0.2F);
            ((ServerLevel)level)
               .sendParticles(
                  ParticleUtils.constructSimpleSpark(new Color(random.nextInt(50), 100 + random.nextInt(155), random.nextInt(50)), 0.3F, 40, 0.9F),
                  player.getX(),
                  player.getY() + 0.2,
                  player.getZ(),
                  20,
                  0.25,
                  0.0,
                  0.25,
                  0.05
               );
         }
      }
   }
}
