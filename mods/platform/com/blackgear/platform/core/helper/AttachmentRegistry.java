package com.blackgear.platform.core.helper;

import com.blackgear.platform.common.data.Attachment;
import com.blackgear.platform.core.helper.neoforge.AttachmentRegistryImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import net.minecraft.world.entity.LivingEntity;

public class AttachmentRegistry {
   @ExpectPlatform
   @Transformed
   public static <T> void register(Attachment<T> attachment) {
      AttachmentRegistryImpl.register(attachment);
   }

   @ExpectPlatform
   @Transformed
   public static <T> boolean hasAttachment(LivingEntity entity, Attachment<T> attachment) {
      return AttachmentRegistryImpl.hasAttachment(entity, attachment);
   }

   @ExpectPlatform
   @Transformed
   public static <T> T getAttachmentValue(LivingEntity entity, Attachment<T> attachment) {
      return AttachmentRegistryImpl.getAttachmentValue(entity, attachment);
   }

   @ExpectPlatform
   @Transformed
   public static <T> void setAttachment(LivingEntity entity, Attachment<T> attachment, T value) {
      AttachmentRegistryImpl.setAttachment(entity, attachment, value);
   }

   @ExpectPlatform
   @Transformed
   public static <T> void removeAttachment(LivingEntity entity, Attachment<T> attachment) {
      AttachmentRegistryImpl.removeAttachment(entity, attachment);
   }

   @ExpectPlatform
   @Transformed
   public static void bootstrap() {
      AttachmentRegistryImpl.bootstrap();
   }
}
