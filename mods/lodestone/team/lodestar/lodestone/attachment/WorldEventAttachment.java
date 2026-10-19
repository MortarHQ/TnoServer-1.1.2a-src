package team.lodestar.lodestone.attachment;

import java.util.ArrayList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;
import team.lodestar.lodestone.registry.common.LodestoneWorldEventTypes;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;

public class WorldEventAttachment implements INBTSerializable<CompoundTag> {
   public final ArrayList<WorldEventInstance> activeWorldEvents = new ArrayList<>();
   public final ArrayList<WorldEventInstance> inboundWorldEvents = new ArrayList<>();

   public @UnknownNullability CompoundTag serializeNBT(Provider provider) {
      CompoundTag tag = new CompoundTag();
      CompoundTag worldTag = new CompoundTag();
      worldTag.putInt("worldEventCount", this.activeWorldEvents.size());

      for (int i = 0; i < this.activeWorldEvents.size(); i++) {
         WorldEventInstance instance = this.activeWorldEvents.get(i);
         CompoundTag instanceTag = instance.serializeNBT();
         worldTag.put("worldEvent_" + i, instanceTag);
      }

      tag.put("worldEventData", worldTag);
      return tag;
   }

   public void deserializeNBT(Provider provider, CompoundTag tag) {
      this.activeWorldEvents.clear();
      CompoundTag worldTag = tag.getCompound("worldEventData");
      int worldEventCount = worldTag.getInt("worldEventCount");

      for (int i = 0; i < worldEventCount; i++) {
         CompoundTag instanceTag = worldTag.getCompound("worldEvent_" + i);
         WorldEventType type = (WorldEventType)LodestoneWorldEventTypes.WORLD_EVENT_TYPE_REGISTRY.get(ResourceLocation.parse(instanceTag.getString("type")));
         WorldEventInstance eventInstance = type.createInstance(instanceTag);
         this.activeWorldEvents.add(eventInstance);
      }
   }
}
