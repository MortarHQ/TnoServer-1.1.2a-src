package dev.ftb.mods.ftbquests.block.entity;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.integration.stages.StageHelper;
import dev.ftb.mods.ftbquests.registry.ModBlockEntityTypes;
import dev.ftb.mods.ftbteams.api.TeamStagesHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class StageBarrierBlockEntity extends BaseBarrierBlockEntity {
   public StageBarrierBlockEntity(BlockPos blockPos, BlockState blockState) {
      super((BlockEntityType<?>)ModBlockEntityTypes.STAGE_BARRIER.get(), blockPos, blockState);
   }

   @Override
   protected boolean checkIfOpen(Player player) {
      return !this.objStr.isEmpty() && (StageHelper.INSTANCE.getProvider().has(player, this.objStr) || TeamStagesHelper.hasTeamStage(player, this.objStr));
   }

   @Override
   protected void addConfigEntries(ConfigGroup cg) {
      cg.addString("stage", this.objStr, s -> this.objStr = s, "").setNameKey("ftbquests.task.ftbquests.gamestage");
   }
}
