package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.entity_model_system.packets;

import com.finderfeed.fdlib.FDClientPacketExecutables;
import com.finderfeed.fdlib.network.FDPacket;
import com.finderfeed.fdlib.network.RegisterFDPacket;
import com.finderfeed.fdlib.systems.FDRegistries;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachment;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachmentData;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.attachments.ModelAttachmentType;
import java.util.UUID;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@RegisterFDPacket("fdlib:add_entity_attachment")
public class AddEntityAttachmentPacket extends FDPacket {
   private int entityId;
   private int layer;
   private String bone;
   private UUID uuid;
   private ModelAttachmentData<?> modelAttachmentData;

   public AddEntityAttachmentPacket(Entity entity, int layer, String bone, UUID uuid, ModelAttachmentData<?> modelAttachmentData) {
      this.entityId = entity.getId();
      this.layer = layer;
      this.bone = bone;
      this.uuid = uuid;
      this.modelAttachmentData = modelAttachmentData;
   }

   public AddEntityAttachmentPacket(RegistryFriendlyByteBuf buf) {
      this.entityId = buf.readInt();
      this.layer = buf.readInt();
      this.bone = buf.readUtf();
      this.uuid = buf.readUUID();
      Registry<ModelAttachmentType<?, ?>> registry = buf.registryAccess().registryOrThrow(FDRegistries.MODEL_ATTACHMENT_TYPE_KEY);
      ModelAttachmentType<? extends ModelAttachment<?, ? extends ModelAttachmentData<?>>, ? extends ModelAttachmentData<? extends ModelAttachment<?, ?>>> type = (ModelAttachmentType<? extends ModelAttachment<?, ? extends ModelAttachmentData<?>>, ? extends ModelAttachmentData<? extends ModelAttachment<?, ?>>>)registry.get(
         buf.readResourceLocation()
      );
      this.modelAttachmentData = (ModelAttachmentData<?>)type.dataStreamCodec().decode(buf);
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.writeInt(this.layer);
      buf.writeUtf(this.bone);
      buf.writeUUID(this.uuid);
      ModelAttachmentType<?, ? extends ModelAttachmentData<?>> type = (ModelAttachmentType<?, ? extends ModelAttachmentData<?>>)this.modelAttachmentData.type();
      Registry<ModelAttachmentType<?, ?>> registry = buf.registryAccess().registryOrThrow(FDRegistries.MODEL_ATTACHMENT_TYPE_KEY);
      buf.writeResourceLocation(registry.getKey(type));
      this.hackyEncode(buf, type);
   }

   @Override
   public void clientAction(IPayloadContext context) {
      FDClientPacketExecutables.addEntityAttachmentPacket(this.entityId, this.layer, this.bone, this.uuid, this.modelAttachmentData);
   }

   @Override
   public void serverAction(IPayloadContext context) {
   }

   private <T extends ModelAttachmentData<?>> void hackyEncode(RegistryFriendlyByteBuf buf, ModelAttachmentType<?, T> type) {
      type.dataStreamCodec().encode(buf, this.modelAttachmentData);
   }
}
