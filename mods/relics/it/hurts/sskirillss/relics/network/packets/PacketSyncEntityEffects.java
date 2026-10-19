package it.hurts.sskirillss.relics.network.packets;

import io.netty.buffer.ByteBuf;
import java.util.function.IntFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PacketSyncEntityEffects implements CustomPacketPayload {
   private final CompoundTag data;
   private final PacketSyncEntityEffects.Action action;
   private final int entity;
   public static final Type<PacketSyncEntityEffects> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("relics", "sync_entity_effect"));
   public static final StreamCodec<ByteBuf, PacketSyncEntityEffects> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.COMPOUND_TAG,
      PacketSyncEntityEffects::getData,
      ByteBufCodecs.idMapper(PacketSyncEntityEffects.Action.BY_ID, PacketSyncEntityEffects.Action::getId),
      PacketSyncEntityEffects::getAction,
      ByteBufCodecs.INT,
      PacketSyncEntityEffects::getEntity,
      PacketSyncEntityEffects::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         Minecraft MC = Minecraft.getInstance();
         ClientLevel level = MC.level;
         if (level != null && level.getEntity(this.entity) instanceof LivingEntity entity) {
            MobEffectInstance effect = MobEffectInstance.load(this.data);
            if (effect != null) {
               switch (this.action) {
                  case ADD:
                  case UPDATE:
                     entity.addEffect(effect);
                     break;
                  case REMOVE:
                     entity.removeEffect(effect.getEffect());
               }
            }
         }
      });
   }

   public CompoundTag getData() {
      return this.data;
   }

   public PacketSyncEntityEffects.Action getAction() {
      return this.action;
   }

   public int getEntity() {
      return this.entity;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PacketSyncEntityEffects other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getEntity() != other.getEntity()) {
         return false;
      } else {
         Object this$data = this.getData();
         Object other$data = other.getData();
         if (this$data == null ? other$data == null : this$data.equals(other$data)) {
            Object this$action = this.getAction();
            Object other$action = other.getAction();
            return this$action == null ? other$action == null : this$action.equals(other$action);
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof PacketSyncEntityEffects;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getEntity();
      Object $data = this.getData();
      result = result * 59 + ($data == null ? 43 : $data.hashCode());
      Object $action = this.getAction();
      return result * 59 + ($action == null ? 43 : $action.hashCode());
   }

   @Override
   public String toString() {
      return "PacketSyncEntityEffects(data=" + this.getData() + ", action=" + this.getAction() + ", entity=" + this.getEntity() + ")";
   }

   public PacketSyncEntityEffects(CompoundTag data, PacketSyncEntityEffects.Action action, int entity) {
      this.data = data;
      this.action = action;
      this.entity = entity;
   }

   public enum Action {
      ADD(0),
      REMOVE(1),
      UPDATE(2);

      public static final IntFunction<PacketSyncEntityEffects.Action> BY_ID = ByIdMap.continuous(
         PacketSyncEntityEffects.Action::getId, values(), OutOfBoundsStrategy.ZERO
      );
      private final int id;

      public int getId() {
         return this.id;
      }

      Action(final int id) {
         this.id = id;
      }
   }
}
