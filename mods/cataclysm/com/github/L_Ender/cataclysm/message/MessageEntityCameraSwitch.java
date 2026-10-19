package com.github.L_Ender.cataclysm.message;

import com.github.L_Ender.cataclysm.config.CMClientConfig;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public abstract class MessageEntityCameraSwitch implements CustomPacketPayload {
   protected final int entityId;

   public MessageEntityCameraSwitch(int entityId) {
      this.entityId = entityId;
   }

   public int getEntityId() {
      return this.entityId;
   }

   public static class FirstPerson extends MessageEntityCameraSwitch {
      public static final Type<MessageEntityCameraSwitch.FirstPerson> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("cataclysm", "first_person_camera"));
      public static final StreamCodec<RegistryFriendlyByteBuf, MessageEntityCameraSwitch.FirstPerson> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.INT, MessageEntityCameraSwitch::getEntityId, MessageEntityCameraSwitch.FirstPerson::new
      );

      public FirstPerson(int entityId) {
         super(entityId);
      }

      public Type<MessageEntityCameraSwitch.FirstPerson> type() {
         return TYPE;
      }

      public static void execute(MessageEntityCameraSwitch.FirstPerson payload, IPayloadContext context) {
         context.enqueueWork(
            () -> {
               Entity entity = context.player().level().getEntity(payload.entityId);
               if (entity instanceof Player player
                  && CMClientConfig.firstPerson
                  && player == Minecraft.getInstance().player
                  && Minecraft.getInstance().getCameraEntity() == Minecraft.getInstance().player) {
                  Minecraft.getInstance().setCameraEntity(entity);
                  Minecraft.getInstance().options.setCameraType(CameraType.FIRST_PERSON);
               }
            }
         );
      }
   }

   public static class ThridPerson extends MessageEntityCameraSwitch {
      public static final Type<MessageEntityCameraSwitch.ThridPerson> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("cataclysm", "third_person_camera"));
      public static final StreamCodec<RegistryFriendlyByteBuf, MessageEntityCameraSwitch.ThridPerson> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.INT, MessageEntityCameraSwitch::getEntityId, MessageEntityCameraSwitch.ThridPerson::new
      );

      public ThridPerson(int entityId) {
         super(entityId);
      }

      public Type<MessageEntityCameraSwitch.ThridPerson> type() {
         return TYPE;
      }

      public static void execute(MessageEntityCameraSwitch.ThridPerson payload, IPayloadContext context) {
         context.enqueueWork(
            () -> {
               Entity entity = context.player().level().getEntity(payload.entityId);
               if (entity instanceof Player player
                  && CMClientConfig.thirdPerson
                  && player == Minecraft.getInstance().player
                  && Minecraft.getInstance().getCameraEntity() == Minecraft.getInstance().player) {
                  Minecraft.getInstance().setCameraEntity(entity);
                  Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
               }
            }
         );
      }
   }
}
