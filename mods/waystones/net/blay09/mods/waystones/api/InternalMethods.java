package net.blay09.mods.waystones.api;

import com.mojang.datafixers.util.Either;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.blay09.mods.waystones.api.error.WaystoneTeleportError;
import net.blay09.mods.waystones.api.requirement.ConditionResolver;
import net.blay09.mods.waystones.api.requirement.ParameterSerializer;
import net.blay09.mods.waystones.api.requirement.RequirementFunction;
import net.blay09.mods.waystones.api.requirement.RequirementType;
import net.blay09.mods.waystones.api.requirement.VariableResolver;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public interface InternalMethods {
   Either<WaystoneTeleportContext, WaystoneTeleportError> createDefaultTeleportContext(Entity var1, Waystone var2, Consumer<WaystoneTeleportContext> var3);

   Either<WaystoneTeleportContext, WaystoneTeleportError> createCustomTeleportContext(Entity var1, Waystone var2);

   WaystoneTeleportContext createUnboundTeleportContext(Entity var1, Waystone var2);

   WaystoneTeleportContext createUnboundTeleportContext(Entity var1);

   Either<List<Entity>, WaystoneTeleportError> tryTeleport(WaystoneTeleportContext var1);

   Either<List<Entity>, WaystoneTeleportError> forceTeleport(WaystoneTeleportContext var1);

   Optional<Waystone> getWaystoneAt(Level var1, BlockPos var2);

   Optional<Waystone> getWaystone(Level var1, UUID var2);

   ItemStack createAttunedShard(Waystone var1);

   ItemStack createBoundScroll(Waystone var1);

   Optional<Waystone> placeWaystone(Level var1, BlockPos var2, WaystoneStyle var3);

   Optional<Waystone> placeSharestone(Level var1, BlockPos var2, DyeColor var3);

   Optional<Waystone> placeWarpPlate(Level var1, BlockPos var2);

   Optional<Waystone> getBoundWaystone(Player var1, ItemStack var2);

   void setBoundWaystone(ItemStack var1, @Nullable Waystone var2);

   Optional<Waystone> getWaystoneAt(MinecraftServer var1, BlockGetter var2, BlockPos var3);

   WarpRequirement resolveRequirements(WaystoneTeleportContext var1);

   Optional<Waystone> getWaystone(MinecraftServer var1, UUID var2);

   void registerRequirementType(RequirementType<?> var1);

   void registerRequirementModifier(RequirementFunction<?, ?> var1);

   void registerVariableResolver(VariableResolver var1);

   void registerConditionResolver(ConditionResolver<?> var1);

   void registerParameterSerializer(ParameterSerializer<?> var1);

   boolean isWaystoneActivated(Player var1, Waystone var2);

   Collection<Waystone> getActivatedWaystones(Player var1);

   void activateWaystone(ServerPlayer var1, Waystone var2);

   void deactivateWaystone(ServerPlayer var1, Waystone var2);

   Optional<Waystone> getNearestWaystone(Player var1);

   Stream<Waystone> getAllWaystones(MinecraftServer var1);

   Stream<Waystone> getWaystonesByType(MinecraftServer var1, ResourceLocation var2);

   void removeWaystoneFromDatabase(MinecraftServer var1, Waystone var2);
}
