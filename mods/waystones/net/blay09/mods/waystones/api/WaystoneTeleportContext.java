package net.blay09.mods.waystones.api;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface WaystoneTeleportContext {
   Entity getEntity();

   Waystone getTargetWaystone();

   List<Mob> getLeashedEntities();

   List<Entity> getAdditionalEntities();

   WaystoneTeleportContext addAdditionalEntity(Entity var1);

   Optional<Waystone> getFromWaystone();

   WaystoneTeleportContext setFromWaystone(@Nullable Waystone var1);

   ItemStack getWarpItem();

   WaystoneTeleportContext setWarpItem(ItemStack var1);

   boolean isDimensionalTeleport();

   WarpRequirement getRequirements();

   WaystoneTeleportContext setRequirements(WarpRequirement var1);

   boolean playsSound();

   WaystoneTeleportContext setPlaysSound(boolean var1);

   boolean playsEffect();

   WaystoneTeleportContext setPlaysEffect(boolean var1);

   boolean appliesModifiers();

   WaystoneTeleportContext setAppliesModifiers(boolean var1);

   Set<ResourceLocation> getFlags();

   WaystoneTeleportContext addFlag(ResourceLocation var1);

   WaystoneTeleportContext removeFlag(ResourceLocation var1);

   default WaystoneTeleportContext addFlags(Set<ResourceLocation> flags) {
      for (ResourceLocation flag : flags) {
         this.addFlag(flag);
      }

      return this;
   }

   default boolean hasFlag(ResourceLocation flag) {
      return this.getFlags().contains(flag);
   }
}
