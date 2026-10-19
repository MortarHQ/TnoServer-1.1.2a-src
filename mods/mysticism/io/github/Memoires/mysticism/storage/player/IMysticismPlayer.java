package io.github.Memoires.mysticism.storage.player;

import java.util.List;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

public interface IMysticismPlayer {
   void setLastHitTarget(UUID var1);

   UUID getLastHitTarget();

   int getMarks(UUID var1);

   void incrementMarks(UUID var1);

   void clearMarks(UUID var1);

   CompoundTag getMarksTag();

   List<UUID> getTargetsOver(int var1);

   Vec3 getPosition();

   void setPosition(Vec3 var1);
}
