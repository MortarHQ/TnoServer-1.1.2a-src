package team.lodestar.lodestone.network.worldevent;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.lodestar.lodestone.attachment.WorldEventAttachment;
import team.lodestar.lodestone.registry.common.LodestoneAttachmentTypes;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;

public class UpdateWorldEventPayload extends OneSidedPayloadData {
   private final UUID uuid;
   private final CompoundTag eventData;

   public UpdateWorldEventPayload(FriendlyByteBuf byteBuf) {
      this(byteBuf.readUUID(), byteBuf.readNbt());
   }

   public UpdateWorldEventPayload(WorldEventInstance instance) {
      this(instance.uuid, instance.serializeNBT());
   }

   public UpdateWorldEventPayload(UUID uuid, CompoundTag eventData) {
      this.uuid = uuid;
      this.eventData = eventData;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void handle(IPayloadContext context) {
      ClientLevel level = Minecraft.getInstance().level;
      if (level != null) {
         WorldEventAttachment worldData = (WorldEventAttachment)level.getData(LodestoneAttachmentTypes.WORLD_EVENT_DATA);

         for (WorldEventInstance instance : worldData.activeWorldEvents) {
            if (instance.uuid.equals(this.uuid)) {
               instance.deserializeNBT(this.eventData);
               break;
            }
         }
      }
   }

   @Override
   public void serialize(FriendlyByteBuf byteBuf) {
      byteBuf.writeUUID(this.uuid);
      byteBuf.writeNbt(this.eventData);
   }
}
