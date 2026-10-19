package com.mongoose.royalvariations.common.attachments;

import com.mongoose.royalvariations.common.entities.IRoyalInterface;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.PacketDistributor;

public class RVCapHelper {
   public static RVAttachment getCapability(LivingEntity livingEntity) {
      return livingEntity.hasData((AttachmentType)RVAttachments.RV_ATTACHMENT.get())
         ? (RVAttachment)livingEntity.getData((AttachmentType)RVAttachments.RV_ATTACHMENT.get())
         : new RVAttachment();
   }

   public static boolean isFlashing(LivingEntity livingEntity) {
      return ((IRoyalInterface)livingEntity).isFlashing();
   }

   public static int getFlashTick(LivingEntity livingEntity) {
      return ((IRoyalInterface)livingEntity).getFlashTick();
   }

   public static void setFlashTick(LivingEntity livingEntity, int tick) {
      ((IRoyalInterface)livingEntity).setFlashTick(tick);
   }

   public static void decreaseFlashTick(LivingEntity livingEntity) {
      setFlashTick(livingEntity, getFlashTick(livingEntity) - 1);
   }

   public static boolean isMarked(LivingEntity livingEntity) {
      return ((IRoyalInterface)livingEntity).isMarked();
   }

   public static int getMarkedTick(LivingEntity livingEntity) {
      return ((IRoyalInterface)livingEntity).getMarkedTick();
   }

   public static void setMarkedTick(LivingEntity livingEntity, int tick) {
      ((IRoyalInterface)livingEntity).setMarkedTick(tick);
   }

   public static boolean isGazed(LivingEntity livingEntity) {
      return ((IRoyalInterface)livingEntity).isGazed();
   }

   public static int getGazeTick(LivingEntity livingEntity) {
      return ((IRoyalInterface)livingEntity).getGazeTick();
   }

   public static void setGazeTick(LivingEntity livingEntity, int tick) {
      ((IRoyalInterface)livingEntity).setGazeTick(tick);
   }

   public static void sendMiscUpdatePacket(LivingEntity livingEntity) {
      if (!livingEntity.level().isClientSide) {
         PacketDistributor.sendToPlayersTrackingEntityAndSelf(livingEntity, new RVCapUpdatePacket(livingEntity), new CustomPacketPayload[0]);
      }
   }
}
