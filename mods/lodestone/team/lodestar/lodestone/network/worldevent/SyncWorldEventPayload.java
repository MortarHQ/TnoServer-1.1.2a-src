package team.lodestar.lodestone.network.worldevent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.lodestar.lodestone.handlers.WorldEventHandler;
import team.lodestar.lodestone.registry.common.LodestoneWorldEventTypes;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;

public class SyncWorldEventPayload extends OneSidedPayloadData {
   private final ResourceLocation type;
   private final boolean start;
   private final CompoundTag eventData;

   public SyncWorldEventPayload(FriendlyByteBuf byteBuf) {
      this(byteBuf.readResourceLocation(), byteBuf.readBoolean(), byteBuf.readNbt());
   }

   public SyncWorldEventPayload(WorldEventInstance instance, boolean start) {
      this(instance.type.id, start, instance.serializeNBT());
   }

   public SyncWorldEventPayload(ResourceLocation type, boolean start, CompoundTag eventData) {
      this.type = type;
      this.start = start;
      this.eventData = eventData;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void handle(IPayloadContext context) {
      WorldEventType eventType = (WorldEventType)LodestoneWorldEventTypes.WORLD_EVENT_TYPE_REGISTRY.get(this.type);
      ClientLevel level = Minecraft.getInstance().level;
      WorldEventHandler.addWorldEvent(level, this.start, eventType.createInstance(this.eventData));
   }

   @Override
   public void serialize(FriendlyByteBuf byteBuf) {
      byteBuf.writeResourceLocation(this.type);
      byteBuf.writeBoolean(this.start);
      byteBuf.writeNbt(this.eventData);
   }
}
