package twilightforest.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import twilightforest.TwilightForestMod;
import twilightforest.components.entity.TravellersWingsAttachment;
import twilightforest.init.TFDataAttachments;

public class TravellersWingsStatePacket implements CustomPacketPayload {
   public static final Type<TravellersWingsStatePacket> TYPE = new Type(TwilightForestMod.prefix("travellers_wings_state"));
   public static final StreamCodec<RegistryFriendlyByteBuf, TravellersWingsStatePacket> STREAM_CODEC = CustomPacketPayload.codec(
      TravellersWingsStatePacket::write, TravellersWingsStatePacket::new
   );
   private final int entityId;
   private final TravellersWingsAttachment.WingState state;
   private final boolean sidestepLeft;
   private final int doubleJumpTimer;
   private final int sidestepTimer;

   public TravellersWingsStatePacket(int entityId, TravellersWingsAttachment.WingState state, boolean sidestepLeft, int doubleJumpTimer, int sidestepTimer) {
      this.entityId = entityId;
      this.state = state;
      this.sidestepLeft = sidestepLeft;
      this.doubleJumpTimer = doubleJumpTimer;
      this.sidestepTimer = sidestepTimer;
   }

   public TravellersWingsStatePacket(int entityId, TravellersWingsAttachment.WingState state) {
      this(entityId, state, false, 0, 0);
   }

   public TravellersWingsStatePacket(RegistryFriendlyByteBuf buf) {
      this.entityId = buf.readInt();
      this.state = (TravellersWingsAttachment.WingState)buf.readEnum(TravellersWingsAttachment.WingState.class);
      this.sidestepLeft = buf.readBoolean();
      this.doubleJumpTimer = buf.readInt();
      this.sidestepTimer = buf.readInt();
   }

   public static void handle(TravellersWingsStatePacket message, IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         Player player = ctx.player();
         if (player != null && player.level() != null && player.level().getEntity(message.entityId) instanceof LivingEntity livingEntity) {
            TravellersWingsAttachment attachment = (TravellersWingsAttachment)livingEntity.getData(TFDataAttachments.TRAVELLERS_WINGS);
            attachment.state = message.state;
            attachment.sidestepLeft = message.sidestepLeft;
            attachment.doubleJumpTimer = message.doubleJumpTimer;
            attachment.sidestepTimer = message.sidestepTimer;
         }
      });
   }

   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.writeEnum(this.state);
      buf.writeBoolean(this.sidestepLeft);
      buf.writeInt(this.doubleJumpTimer);
      buf.writeInt(this.sidestepTimer);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
