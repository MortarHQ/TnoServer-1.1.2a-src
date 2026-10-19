package org.crypticdev.ancient_artifacts.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.Map;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.ancient_artifacts.artifact.ArtifactDefinition;
import org.crypticdev.ancient_artifacts.artifact.ArtifactSlot;
import org.crypticdev.ancient_artifacts.platform.neoforge.PlatformEquipmentImpl;

public class PlatformEquipment {
   @ExpectPlatform
   @Transformed
   public static boolean hasArtifact(LivingEntity entity, ArtifactDefinition definition) {
      return PlatformEquipmentImpl.hasArtifact(entity, definition);
   }

   @ExpectPlatform
   @Transformed
   public static int amountOfGradeEquipped(LivingEntity entity, int grade) {
      return PlatformEquipmentImpl.amountOfGradeEquipped(entity, grade);
   }

   @ExpectPlatform
   @Transformed
   public static ItemStack getArtifactStack(LivingEntity entity, ArtifactDefinition definition) {
      return PlatformEquipmentImpl.getArtifactStack(entity, definition);
   }

   @ExpectPlatform
   @Transformed
   public static Map<Integer, ItemStack> getArtifactStacksinSlot(LivingEntity entity, ArtifactSlot artifactSlot) {
      return PlatformEquipmentImpl.getArtifactStacksinSlot(entity, artifactSlot);
   }

   @ExpectPlatform
   @Transformed
   public static ItemStack getArtifactStackinSlot(LivingEntity entity, int index, ArtifactSlot artifactSlot) {
      return PlatformEquipmentImpl.getArtifactStackinSlot(entity, index, artifactSlot);
   }

   @ExpectPlatform
   @Transformed
   public static void setArtifactInSlot(LivingEntity entity, int index, ArtifactSlot artifactSlot, ItemStack itemStack) {
      PlatformEquipmentImpl.setArtifactInSlot(entity, index, artifactSlot, itemStack);
   }
}
