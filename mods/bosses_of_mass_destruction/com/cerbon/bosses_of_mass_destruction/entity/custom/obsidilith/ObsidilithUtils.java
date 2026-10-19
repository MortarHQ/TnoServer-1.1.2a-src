package com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith;

import com.cerbon.bosses_of_mass_destruction.client.render.NodeBossBarRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class ObsidilithUtils {
   private static final int textureSize = 256;
   private static final ResourceLocation bossBarDividerTexture = ResourceLocation.fromNamespaceAndPath(
      "bosses_of_mass_destruction", "textures/gui/obsidilith_boss_bar_dividers.png"
   );
   public static final List<Float> hpPillarShieldMilestones = List.of(0.0F, 0.25F, 0.5F, 0.75F, 1.0F);
   public static final NodeBossBarRenderer obsidilithBossBarRenderer = new NodeBossBarRenderer(
      ((EntityType)BMDEntities.OBSIDILITH.get()).getDescriptionId(), hpPillarShieldMilestones, bossBarDividerTexture, 256
   );
   public static final List<Vec3> circlePos = MathUtils.buildBlockCircle(Math.sqrt(Math.pow(2.0, 2.0) + Math.pow(1.0, 2.0)));
   public static final byte deathStatus = 3;
   public static final byte burstAttackStatus = 5;
   public static final byte waveAttackStatus = 6;
   public static final byte spikeAttackStatus = 7;
   public static final byte anvilAttackStatus = 8;
   public static final byte pillarDefenseStatus = 9;
   public static final int deathPillarHeight = 15;
   public static final int ticksBetweenPillarLayer = 5;

   public static Vec3 approximatePlayerNextPosition(List<Vec3> previousPosition, Vec3 currentPos) {
      return previousPosition.stream()
         .map(vec3 -> VecUtils.planeProject(vec3.subtract(currentPos), VecUtils.yAxis))
         .reduce(Vec3::add)
         .orElse(Vec3.ZERO)
         .multiply(-0.5, -0.5, -0.5)
         .add(currentPos);
   }

   public static void onDeath(LivingEntity actor, int experienceDrop) {
      Level level = actor.level();
      if (!level.isClientSide()) {
         BlockPos blockPos = actor.blockPosition();
         Vec3 vecPos = actor.position();
         EventScheduler eventScheduler = CapabilityUtils.getLevelEventScheduler(level);
         level.explode(actor, actor.getX(), actor.getY(), actor.getZ(), 2.0F, ExplosionInteraction.MOB);

         for (int y = 0; y <= 15; y++) {
            int y1 = y;
            eventScheduler.addEvent(new TimedEvent(() -> {
               actor.playSound(SoundEvents.STONE_PLACE, 1.0F, SoundUtils.randomPitch(actor.getRandom()));

               for (Vec3 pos : circlePos) {
                  level.setBlockAndUpdate(new BlockPos((int)pos.x, y1, (int)pos.z).offset(blockPos), Blocks.OBSIDIAN.defaultBlockState());
               }
            }, y * 5));
         }

         eventScheduler.addEvent(
            new TimedEvent(
               () -> {
                  BlockPos chestPos = blockPos.above(16);
                  level.setBlock(chestPos, Blocks.SHULKER_BOX.defaultBlockState(), 2);
                  RandomizableContainer.setBlockEntityLootTable(
                     level,
                     actor.getRandom(),
                     chestPos,
                     ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "chests/obsidilith"))
                  );
               },
               75
            )
         );
         int expTicks = 20;
         int expPerTick = (int)((float)experienceDrop / expTicks);
         Vec3 pillarTop = vecPos.add(VecUtils.yAxis.scale(15.0));
         eventScheduler.addEvent(
            new TimedEvent(
               () -> VanillaCopiesServer.awardExperience(
                  expPerTick, pillarTop.add(VecUtils.planeProject(RandomUtils.randVec(), VecUtils.yAxis).scale(2.0)), level
               ),
               75,
               expTicks,
               () -> false
            )
         );
      }
   }
}
