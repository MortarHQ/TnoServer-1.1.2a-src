package com.mortal_cultivation.flight;

import com.mortal_cultivation.entity.FlyingSwordEntity;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class FlyingSwordDeploymentData {
   private static final String ACTIVE_DEPLOYMENT_TAG = "FlyingSwordDeployment";
   private static final String DEPLOYMENT_ID_TAG = "DeploymentId";
   private static final String ENTITY_ID_TAG = "EntityId";
   private static final String SWORD_TAG = "Sword";

   private FlyingSwordDeploymentData() {
   }

   public static void track(ServerPlayer player, FlyingSwordEntity sword) {
      ItemStack swordStack = sword.getSwordStack();
      if (!swordStack.isEmpty()) {
         CompoundTag deployment = new CompoundTag();
         deployment.putUUID("DeploymentId", sword.getOrCreateDeploymentId());
         deployment.putUUID("EntityId", sword.getUUID());
         deployment.put("Sword", swordStack.save(player.registryAccess()));
         putDeploymentTag(player, deployment);
      }
   }

   @Nullable
   public static FlyingSwordDeploymentData.ActiveDeployment get(ServerPlayer player) {
      CompoundTag deployment = getDeploymentTag(player);
      if (deployment != null && deployment.hasUUID("DeploymentId") && deployment.hasUUID("EntityId") && deployment.contains("Sword", 10)) {
         ItemStack swordStack = ItemStack.parse(player.registryAccess(), deployment.getCompound("Sword")).orElse(ItemStack.EMPTY);
         if (swordStack.isEmpty()) {
            clear(player);
            return null;
         } else {
            return new FlyingSwordDeploymentData.ActiveDeployment(deployment.getUUID("DeploymentId"), deployment.getUUID("EntityId"), swordStack);
         }
      } else {
         return null;
      }
   }

   public static void clearIfEntityMatches(ServerPlayer player, FlyingSwordEntity sword) {
      FlyingSwordDeploymentData.ActiveDeployment active = get(player);
      if (active != null && active.matchesEntity(sword)) {
         clear(player);
      }
   }

   public static void clear(ServerPlayer player) {
      CompoundTag root = player.getPersistentData();
      if (root.contains("PlayerPersisted", 10)) {
         CompoundTag persisted = root.getCompound("PlayerPersisted");
         if (persisted.contains("mortal_cultivation", 10)) {
            CompoundTag modData = persisted.getCompound("mortal_cultivation");
            modData.remove("FlyingSwordDeployment");
            persisted.put("mortal_cultivation", modData);
            root.put("PlayerPersisted", persisted);
         }
      }
   }

   @Nullable
   private static CompoundTag getDeploymentTag(ServerPlayer player) {
      CompoundTag root = player.getPersistentData();
      if (!root.contains("PlayerPersisted", 10)) {
         return null;
      }

      CompoundTag persisted = root.getCompound("PlayerPersisted");
      if (!persisted.contains("mortal_cultivation", 10)) {
         return null;
      }

      CompoundTag modData = persisted.getCompound("mortal_cultivation");
      return !modData.contains("FlyingSwordDeployment", 10) ? null : modData.getCompound("FlyingSwordDeployment");
   }

   private static void putDeploymentTag(ServerPlayer player, CompoundTag deployment) {
      CompoundTag root = player.getPersistentData();
      CompoundTag persisted = root.contains("PlayerPersisted", 10) ? root.getCompound("PlayerPersisted") : new CompoundTag();
      CompoundTag modData = persisted.contains("mortal_cultivation", 10) ? persisted.getCompound("mortal_cultivation") : new CompoundTag();
      modData.put("FlyingSwordDeployment", deployment);
      persisted.put("mortal_cultivation", modData);
      root.put("PlayerPersisted", persisted);
   }

   public record ActiveDeployment(UUID deploymentId, UUID entityId, ItemStack swordStack) {
      public ActiveDeployment {
         swordStack = swordStack.copy();
      }

      public ItemStack copySwordStack() {
         return this.swordStack.copy();
      }

      public boolean matchesEntity(FlyingSwordEntity sword) {
         return this.entityId.equals(sword.getUUID()) && this.matchesDeployment(sword);
      }

      public boolean matchesDeployment(FlyingSwordEntity sword) {
         return sword.getDeploymentId().filter(this.deploymentId::equals).isPresent();
      }
   }
}
