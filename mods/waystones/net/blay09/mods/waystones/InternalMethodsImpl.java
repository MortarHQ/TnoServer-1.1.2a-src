package net.blay09.mods.waystones;

import com.mojang.datafixers.util.Either;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.api.InternalMethods;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneOrigin;
import net.blay09.mods.waystones.api.WaystoneStyle;
import net.blay09.mods.waystones.api.WaystoneTeleportContext;
import net.blay09.mods.waystones.api.WaystonesAPI;
import net.blay09.mods.waystones.api.error.WaystoneTeleportError;
import net.blay09.mods.waystones.api.requirement.ConditionResolver;
import net.blay09.mods.waystones.api.requirement.ParameterSerializer;
import net.blay09.mods.waystones.api.requirement.RequirementFunction;
import net.blay09.mods.waystones.api.requirement.RequirementType;
import net.blay09.mods.waystones.api.requirement.VariableResolver;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.blay09.mods.waystones.api.trait.IAttunementItem;
import net.blay09.mods.waystones.block.ModBlocks;
import net.blay09.mods.waystones.block.SharestoneBlock;
import net.blay09.mods.waystones.block.WaystoneBlock;
import net.blay09.mods.waystones.block.WaystoneBlockBase;
import net.blay09.mods.waystones.block.entity.WaystoneBlockEntityBase;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.config.WaystonesConfigData;
import net.blay09.mods.waystones.core.InvalidWaystone;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WaystoneManagerImpl;
import net.blay09.mods.waystones.core.WaystoneSyncManager;
import net.blay09.mods.waystones.core.WaystoneTeleportContextImpl;
import net.blay09.mods.waystones.core.WaystoneTeleportManager;
import net.blay09.mods.waystones.item.ModItems;
import net.blay09.mods.waystones.requirement.RequirementModifierParser;
import net.blay09.mods.waystones.requirement.RequirementRegistry;
import net.blay09.mods.waystones.requirement.WarpRequirementsContextImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.Nullable;

public class InternalMethodsImpl implements InternalMethods {
   @Override
   public Either<WaystoneTeleportContext, WaystoneTeleportError> createDefaultTeleportContext(
      Entity entity, Waystone waystone, Consumer<WaystoneTeleportContext> init
   ) {
      return WaystonesAPI.createCustomTeleportContext(entity, waystone)
         .ifLeft(
            context -> {
               WaystonesConfigData config = WaystonesConfig.getActive();
               WaystonesConfigData.TransportMobs shouldTransportPets = config.teleports.transportPets;
               if (shouldTransportPets == WaystonesConfigData.TransportMobs.ENABLED
                  || shouldTransportPets == WaystonesConfigData.TransportMobs.SAME_DIMENSION && !context.isDimensionalTeleport()) {
                  context.getAdditionalEntities().addAll(WaystoneTeleportManager.findPets(entity));
               }

               context.getLeashedEntities().addAll(WaystoneTeleportManager.findLeashedAnimals(entity));
               context.setAppliesModifiers(config.teleports.enableModifiers);
               init.accept(context);
               context.setRequirements(this.resolveRequirements(context));
            }
         );
   }

   @Override
   public Either<WaystoneTeleportContext, WaystoneTeleportError> createCustomTeleportContext(Entity entity, Waystone waystone) {
      if (!waystone.isValid()) {
         return Either.right(new WaystoneTeleportError.InvalidWaystone(waystone));
      } else {
         MinecraftServer server = entity.getServer();
         if (server == null) {
            return Either.right(new WaystoneTeleportError.NotOnServer());
         } else {
            ServerLevel targetLevel = server.getLevel(waystone.getDimension());
            if (targetLevel == null) {
               return Either.right(new WaystoneTeleportError.InvalidDimension(waystone.getDimension()));
            } else {
               return !waystone.isValidInLevel(targetLevel)
                  ? Either.right(new WaystoneTeleportError.MissingWaystone(waystone))
                  : Either.left(new WaystoneTeleportContextImpl(entity, waystone));
            }
         }
      }
   }

   @Override
   public WaystoneTeleportContext createUnboundTeleportContext(Entity entity, Waystone waystone) {
      return new WaystoneTeleportContextImpl(entity, waystone);
   }

   @Override
   public WaystoneTeleportContext createUnboundTeleportContext(Entity entity) {
      return new WaystoneTeleportContextImpl(entity, InvalidWaystone.INSTANCE);
   }

   @Override
   public Either<List<Entity>, WaystoneTeleportError> tryTeleport(WaystoneTeleportContext context) {
      return WaystoneTeleportManager.tryTeleport(context);
   }

   @Override
   public Either<List<Entity>, WaystoneTeleportError> forceTeleport(WaystoneTeleportContext context) {
      return WaystoneTeleportManager.doTeleport(context);
   }

   @Override
   public Optional<Waystone> getWaystoneAt(Level level, BlockPos pos) {
      return WaystoneManagerImpl.get(level.getServer()).getWaystoneAt(level, pos);
   }

   @Override
   public Optional<Waystone> getWaystoneAt(MinecraftServer server, BlockGetter level, BlockPos pos) {
      return WaystoneManagerImpl.get(server).getWaystoneAt(level, pos);
   }

   @Override
   public Optional<Waystone> getWaystone(Level level, UUID uuid) {
      return WaystoneManagerImpl.get(level.getServer()).getWaystoneById(uuid);
   }

   @Override
   public Optional<Waystone> getWaystone(MinecraftServer server, UUID uuid) {
      return WaystoneManagerImpl.get(server).getWaystoneById(uuid);
   }

   @Override
   public ItemStack createAttunedShard(Waystone warpPlate) {
      ItemStack itemStack = new ItemStack(ModItems.attunedShard);
      this.setBoundWaystone(itemStack, warpPlate);
      return itemStack;
   }

   @Override
   public ItemStack createBoundScroll(Waystone waystone) {
      ItemStack itemStack = new ItemStack(ModItems.warpScroll);
      this.setBoundWaystone(itemStack, waystone);
      return itemStack;
   }

   @Override
   public Optional<Waystone> placeWaystone(Level level, BlockPos pos, WaystoneStyle style) {
      Block block = (Block)BuiltInRegistries.BLOCK.get(style.getBlockRegistryName());
      level.setBlock(
         pos,
         (BlockState)((BlockState)block.defaultBlockState().setValue(WaystoneBlock.HALF, DoubleBlockHalf.LOWER))
            .setValue(WaystoneBlockBase.ORIGIN, WaystoneOrigin.PLAYER),
         3
      );
      level.setBlock(
         pos.above(),
         (BlockState)((BlockState)block.defaultBlockState().setValue(WaystoneBlock.HALF, DoubleBlockHalf.UPPER))
            .setValue(WaystoneBlockBase.ORIGIN, WaystoneOrigin.PLAYER),
         3
      );
      if (level.getBlockEntity(pos) instanceof WaystoneBlockEntityBase waystoneBlockEntity && level instanceof ServerLevel serverLevel) {
         waystoneBlockEntity.initializeWaystone(serverLevel, null, WaystoneOrigin.PLAYER);
         if (level.getBlockEntity(pos.above()) instanceof WaystoneBlockEntityBase waystoneBlockEntityAbove) {
            waystoneBlockEntityAbove.initializeFromBase(waystoneBlockEntity);
         }

         return Optional.of(waystoneBlockEntity.getWaystone());
      } else {
         return this.getWaystoneAt(level, pos);
      }
   }

   @Override
   public Optional<Waystone> placeSharestone(Level level, BlockPos pos, DyeColor color) {
      SharestoneBlock sharestone = ModBlocks.getSharestone(color);
      if (sharestone == null) {
         return Optional.empty();
      }

      level.setBlock(
         pos,
         (BlockState)((BlockState)sharestone.defaultBlockState().setValue(WaystoneBlock.HALF, DoubleBlockHalf.LOWER))
            .setValue(WaystoneBlockBase.ORIGIN, WaystoneOrigin.PLAYER),
         3
      );
      level.setBlock(
         pos.above(),
         (BlockState)((BlockState)sharestone.defaultBlockState().setValue(WaystoneBlock.HALF, DoubleBlockHalf.UPPER))
            .setValue(WaystoneBlockBase.ORIGIN, WaystoneOrigin.PLAYER),
         3
      );
      if (level.getBlockEntity(pos) instanceof WaystoneBlockEntityBase waystoneBlockEntity && level instanceof ServerLevel serverLevel) {
         waystoneBlockEntity.initializeWaystone(serverLevel, null, WaystoneOrigin.PLAYER);
         if (level.getBlockEntity(pos.above()) instanceof WaystoneBlockEntityBase waystoneBlockEntityAbove) {
            waystoneBlockEntityAbove.initializeFromBase(waystoneBlockEntity);
         }

         return Optional.of(waystoneBlockEntity.getWaystone());
      } else {
         return this.getWaystoneAt(level, pos);
      }
   }

   @Override
   public Optional<Waystone> placeWarpPlate(Level level, BlockPos pos) {
      level.setBlock(pos, (BlockState)ModBlocks.warpPlate.defaultBlockState().setValue(WaystoneBlockBase.ORIGIN, WaystoneOrigin.PLAYER), 3);
      if (level.getBlockEntity(pos) instanceof WaystoneBlockEntityBase waystoneBlockEntity && level instanceof ServerLevel serverLevel) {
         waystoneBlockEntity.initializeWaystone(serverLevel, null, WaystoneOrigin.PLAYER);
         return Optional.of(waystoneBlockEntity.getWaystone());
      } else {
         return this.getWaystoneAt(level, pos);
      }
   }

   @Override
   public Optional<Waystone> getBoundWaystone(@Nullable Player player, ItemStack itemStack) {
      return itemStack.getItem() instanceof IAttunementItem attunementItem
         ? attunementItem.getWaystoneAttunedTo(Balm.getHooks().getServer(), player, itemStack)
         : Optional.empty();
   }

   @Override
   public void setBoundWaystone(ItemStack itemStack, @Nullable Waystone waystone) {
      if (itemStack.getItem() instanceof IAttunementItem attunementItem) {
         attunementItem.setWaystoneAttunedTo(itemStack, waystone);
      }
   }

   @Override
   public WarpRequirement resolveRequirements(WaystoneTeleportContext context) {
      WarpRequirementsContextImpl requirementsContext = new WarpRequirementsContextImpl(context);

      for (String modifier : WaystonesConfig.getActive().teleports.warpRequirements) {
         if (!modifier.isBlank()) {
            RequirementModifierParser.parse(modifier)
               .stream()
               .filter(configuredModifier -> configuredModifier.requirement().modifier().isEnabled())
               .forEach(requirementsContext::apply);
         }
      }

      return requirementsContext.resolve();
   }

   @Override
   public void registerRequirementType(RequirementType<?> requirementType) {
      RequirementRegistry.register(requirementType);
   }

   @Override
   public void registerRequirementModifier(RequirementFunction<?, ?> requirementModifier) {
      RequirementRegistry.register(requirementModifier);
   }

   @Override
   public void registerVariableResolver(VariableResolver variableResolver) {
      RequirementRegistry.register(variableResolver);
   }

   @Override
   public void registerConditionResolver(ConditionResolver<?> conditionResolver) {
      RequirementRegistry.register(conditionResolver);
   }

   @Override
   public void registerParameterSerializer(ParameterSerializer<?> parameterSerializer) {
      RequirementRegistry.register(parameterSerializer);
   }

   @Override
   public boolean isWaystoneActivated(Player player, Waystone waystone) {
      return PlayerWaystoneManager.isWaystoneActivated(player, waystone);
   }

   @Override
   public Collection<Waystone> getActivatedWaystones(Player player) {
      return PlayerWaystoneManager.getActivatedWaystones(player);
   }

   @Override
   public Optional<Waystone> getNearestWaystone(Player player) {
      return PlayerWaystoneManager.getNearestWaystone(player);
   }

   @Override
   public void activateWaystone(ServerPlayer player, Waystone waystone) {
      PlayerWaystoneManager.activateWaystone(player, waystone);
      WaystoneSyncManager.sendActivatedWaystones(player);
   }

   @Override
   public void deactivateWaystone(ServerPlayer player, Waystone waystone) {
      PlayerWaystoneManager.deactivateWaystone(player, waystone);
      WaystoneSyncManager.sendActivatedWaystones(player);
   }

   @Override
   public Stream<Waystone> getAllWaystones(MinecraftServer server) {
      return WaystoneManagerImpl.get(server).getWaystones();
   }

   @Override
   public Stream<Waystone> getWaystonesByType(MinecraftServer server, ResourceLocation type) {
      return WaystoneManagerImpl.get(server).getWaystonesByType(type);
   }

   @Override
   public void removeWaystoneFromDatabase(MinecraftServer server, Waystone waystone) {
      WaystoneManagerImpl.get(server).removeWaystone(waystone);
      PlayerWaystoneManager.removeKnownWaystone(server, waystone);
   }
}
