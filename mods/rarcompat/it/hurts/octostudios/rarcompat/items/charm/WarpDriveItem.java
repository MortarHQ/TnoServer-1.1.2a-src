package it.hurts.octostudios.rarcompat.items.charm;

import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.CastData;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastStage;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.PredicateType;
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
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.Vec3;

public class WarpDriveItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("teleport")
                     .active(CastData.builder().type(CastType.INSTANTANEOUS).predicate("teleport", PredicateType.CAST, (player, stack) -> {
                        BlockPos pos = this.getHitResult(player, stack);
                        return pos != null && player.position().distanceTo(pos.getCenter()) <= this.getStatValue(stack, "teleport", "distance");
                     }).build())
                     .stat(
                        StatData.builder("distance")
                           .initialValue(5.0, 15.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.565)
                           .formatValue(value -> (int)MathUtils.round(value, 0))
                           .build()
                     )
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(100.0, 80.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, -0.075)
                           .formatValue(value -> MathUtils.round(value / 20.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 11, 28)
                           .star(1, 6, 24)
                           .star(2, 2, 14)
                           .star(3, 6, 5)
                           .star(4, 11, 2)
                           .star(5, 16, 5)
                           .star(6, 20, 14)
                           .star(7, 16, 24)
                           .star(8, 11, 14)
                           .star(9, 7, 20)
                           .star(10, 15, 20)
                           .link(0, 1)
                           .link(1, 2)
                           .link(2, 3)
                           .link(3, 4)
                           .link(4, 5)
                           .link(5, 6)
                           .link(6, 7)
                           .link(7, 0)
                           .link(0, 8)
                           .link(9, 8)
                           .link(10, 8)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-13313829).borderBottom(-15303231).build())
               .beams(BeamsData.builder().startColor(-16282701).endColor(224396).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("teleport").initialValue(1).gem(GemShape.SQUARE, GemColor.CYAN).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.END_LIKE, LootEntries.THE_END}).build())
         .build();
   }

   public void castActiveAbility(ItemStack stack, Player player, String ability, CastType type, CastStage stage) {
      Level level = player.getCommandSenderWorld();
      if (ability.equals("teleport") && !level.isClientSide()) {
         BlockPos blockPos = this.getHitResult(player, stack);
         if (blockPos != null) {
            this.spawnCuboidOutlineParticles(level, player.position(), player.getBbHeight(), player.getBbWidth(), (int)player.getBbHeight() * 20);

            for (int i = 1; i <= player.position().distanceTo(blockPos.getCenter()) + 9.0; i++) {
               if (i % 10 == 0) {
                  this.spreadRelicExperience(player, stack, 1);
               }
            }

            player.teleportTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
            player.fallDistance = 0.0F;
            level.playSound(null, player, SoundEvents.ENDER_EYE_DEATH, SoundSource.PLAYERS, 1.0F, 0.9F + player.getRandom().nextFloat() * 0.2F);
            this.setAbilityCooldown(stack, "teleport", (int)this.getStatValue(stack, "teleport", "cooldown"));
         }
      }
   }

   public BlockPos getHitResult(Player player, ItemStack stack) {
      Level level = player.getCommandSenderWorld();
      Vec3 viewVec = player.getViewVector(0.0F);
      Vec3 eyeVec = player.getEyePosition(0.0F);
      double distance = this.getStatValue(stack, "teleport", "distance");
      BlockPos blockPos = level.clip(
            new ClipContext(eyeVec, eyeVec.add(viewVec.x * distance, viewVec.y * distance, viewVec.z * distance), Block.COLLIDER, Fluid.NONE, player)
         )
         .getBlockPos();
      if (this.hasCollision(level, blockPos) && !level.getBlockState(blockPos).isAir()) {
         blockPos = blockPos.above();

         for (int i = 0; i < (int)Math.round(distance / 3.0); i++) {
            if (!this.hasCollision(level, blockPos) && !this.hasCollision(level, blockPos.above())) {
               return blockPos;
            }

            blockPos = blockPos.above();
         }

         return null;
      } else {
         return null;
      }
   }

   private boolean hasCollision(Level level, BlockPos pos) {
      return level.getBlockState(pos).getCollisionShape(level, pos).max(Axis.Y) >= 0.25;
   }

   public void spawnCuboidOutlineParticles(Level level, Vec3 position, double height, double halfWidth, int density) {
      double half = halfWidth / 2.0;
      Vec3[] corners = new Vec3[]{
         position.add(-half, 0.0, -half),
         position.add(half, 0.0, -half),
         position.add(half, 0.0, half),
         position.add(-half, 0.0, half),
         position.add(-half, height, -half),
         position.add(half, height, -half),
         position.add(half, height, half),
         position.add(-half, height, half)
      };
      int[][] edges = new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 0}, {4, 5}, {5, 6}, {6, 7}, {7, 4}, {0, 4}, {1, 5}, {2, 6}, {3, 7}};
      density = height < 1.0 ? Math.max(density, 5) : density;
      RandomSource random = level.getRandom();

      for (int[] edge : edges) {
         Vec3 start = corners[edge[0]];
         Vec3 end = corners[edge[1]];
         if (!(start.distanceTo(end) < 0.01)) {
            for (int j = 0; j <= density; j++) {
               double t = (double)j / density;
               double x = start.x + t * (end.x - start.x);
               double y = start.y + t * (end.y - start.y);
               double z = start.z + t * (end.z - start.z);
               ((ServerLevel)level)
                  .sendParticles(
                     ParticleUtils.constructSimpleSpark(
                        new Color(random.nextInt(50), random.nextInt(50), 50 + random.nextInt(55)), (float)(0.2F + height / 80.0), 100, 0.9F
                     ),
                     x,
                     y,
                     z,
                     0,
                     0.0,
                     -0.1,
                     0.0,
                     0.1
                  );
            }
         }
      }
   }
}
