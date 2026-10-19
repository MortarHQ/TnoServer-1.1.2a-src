package dev.ftb.mods.ftbquests.block.entity;

import dev.architectury.hooks.level.entity.PlayerHooks;
import dev.ftb.mods.ftbquests.quest.QuestObjectBase;
import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbquests.registry.ModBlockEntityTypes;
import dev.ftb.mods.ftbquests.util.ProgressChange;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class DetectorBlockEntity extends BlockEntity {
   private long objectId = 0L;
   private int radius = 8;

   public DetectorBlockEntity(BlockPos blockPos, BlockState blockState) {
      super((BlockEntityType)ModBlockEntityTypes.DETECTOR.get(), blockPos, blockState);
   }

   public void loadAdditional(CompoundTag tag, Provider provider) {
      super.loadAdditional(tag, provider);
      this.objectId = QuestObjectBase.parseCodeString(tag.getString("Object"));
      this.radius = tag.getInt("Radius");
   }

   public void saveAdditional(CompoundTag tag, Provider provider) {
      tag.putString("Object", QuestObjectBase.getCodeString(this.objectId));
      tag.putInt("Radius", this.radius);
   }

   public void update(String idStr) {
      this.objectId = ServerQuestFile.INSTANCE.getID(idStr);
   }

   private static boolean isRealPlayer(ServerPlayer player) {
      return !PlayerHooks.isFake(player);
   }

   public void onPowered(Level level, BlockPos pos) {
      QuestObjectBase qo = ServerQuestFile.INSTANCE.getBase(this.objectId);
      if (qo != null) {
         AABB box = new AABB(pos).inflate(this.radius);

         for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, box, DetectorBlockEntity::isRealPlayer)) {
            ServerQuestFile.INSTANCE
               .getTeamData(player)
               .ifPresent(data -> qo.forceProgressRaw(data, new ProgressChange(qo, player.getUUID()).setReset(false).withNotifications()));
         }
      }
   }
}
