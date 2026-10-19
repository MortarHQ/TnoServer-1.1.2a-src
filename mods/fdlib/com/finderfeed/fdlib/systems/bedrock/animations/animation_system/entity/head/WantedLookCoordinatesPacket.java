package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.entity.head;

import com.finderfeed.fdlib.FDClientHelpers;
import com.finderfeed.fdlib.network.FDPacket;
import com.finderfeed.fdlib.network.RegisterFDPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@RegisterFDPacket("fdlib:wanted_look_coords_packet")
public class WantedLookCoordinatesPacket extends FDPacket {
   private int entityId;
   private double x;
   private double y;
   private double z;

   public WantedLookCoordinatesPacket(RegistryFriendlyByteBuf buf) {
      this.entityId = buf.readInt();
      this.x = buf.readDouble();
      this.y = buf.readDouble();
      this.z = buf.readDouble();
   }

   public WantedLookCoordinatesPacket(Mob entity, double x, double y, double z) {
      this.entityId = entity.getId();
      this.x = x;
      this.y = y;
      this.z = z;
   }

   @Override
   public void write(RegistryFriendlyByteBuf registryFriendlyByteBuf) {
      registryFriendlyByteBuf.writeInt(this.entityId);
      registryFriendlyByteBuf.writeDouble(this.x);
      registryFriendlyByteBuf.writeDouble(this.y);
      registryFriendlyByteBuf.writeDouble(this.z);
   }

   @Override
   public void clientAction(IPayloadContext iPayloadContext) {
      if (FDClientHelpers.getClientLevel().getEntity(this.entityId) instanceof Mob mob) {
         LookControl lookControl = mob.getLookControl();
         lookControl.setLookAt(this.x, this.y, this.z);
      }
   }

   @Override
   public void serverAction(IPayloadContext iPayloadContext) {
   }
}
