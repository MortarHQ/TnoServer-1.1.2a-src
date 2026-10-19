package com.finderfeed.raids_enhanced;

import com.finderfeed.raids_enhanced.content.entities.engineer.ZapperIllager;
import com.finderfeed.raids_enhanced.content.entities.golem_of_last_resort.GolemOfLastResort;
import com.finderfeed.raids_enhanced.content.entities.raid_blimp.RaidBlimp;
import com.finderfeed.raids_enhanced.content.entities.raid_drill.RaidDrill;
import com.finderfeed.raids_enhanced.init.REEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;

public class REMixinHandler {
   public static void raidMixin(Raid raid, BlockPos raidPos, int currentGroup, int numGroups) {
      if (numGroups == currentGroup) {
         Level level = raid.getLevel();
         int raidLevel = raid.getRaidOmenLevel();
         if (raidLevel == 2) {
            RaidDrill drill = (RaidDrill)REEntities.RAID_DRILL.get().create(level);
            if (drill != null) {
               raid.joinRaid(currentGroup, drill, raidPos, false);
            }
         } else if (raidLevel == 3) {
            GolemOfLastResort golem = (GolemOfLastResort)REEntities.GOLEM_OF_LAST_RESORT.get().create(level);
            if (golem != null) {
               raid.joinRaid(currentGroup, golem, raidPos, false);
            }
         } else if (raidLevel == 4) {
            ZapperIllager engineer = (ZapperIllager)REEntities.ZAPPER_ILLAGER.get().create(level);
            if (engineer != null) {
               raid.joinRaid(currentGroup, engineer, raidPos, false);
            }
         } else if (raidLevel >= 5) {
            RaidBlimp blimp = (RaidBlimp)REEntities.RAID_BLIMP.get().create(level);
            if (blimp != null) {
               int height = level.getHeight(Types.MOTION_BLOCKING, raidPos.getX(), raidPos.getZ());
               Vec3 spawnPos = new Vec3(raidPos.getX(), height + 25, raidPos.getZ());
               blimp.setPos(spawnPos);
               blimp.setCurrentRaid(raid);
               blimp.setWave(currentGroup);
               blimp.setCanJoinRaid(true);
               blimp.setTicksOutsideRaid(0);
               level.addFreshEntity(blimp);
               raid.addWaveMob(currentGroup, blimp, true);
            }
         }
      }
   }
}
