package net.blay09.mods.waystones.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import net.blay09.mods.waystones.api.MutableWaystone;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneOrigin;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.api.WaystoneVisibility;
import net.blay09.mods.waystones.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class WaystoneImpl implements Waystone, MutableWaystone {
   public static final StreamCodec<RegistryFriendlyByteBuf, Waystone> STREAM_CODEC = StreamCodec.of(WaystoneImpl::write, WaystoneImpl::read);
   public static final StreamCodec<RegistryFriendlyByteBuf, Collection<Waystone>> LIST_STREAM_CODEC = STREAM_CODEC.apply(
      ByteBufCodecs.collection(ArrayList::new)
   );
   private final ResourceLocation waystoneType;
   private final UUID waystoneUid;
   private final WaystoneOrigin origin;
   private boolean isTransient;
   private ResourceKey<Level> dimension;
   private BlockPos pos;
   private Component name = Component.empty();
   private WaystoneVisibility visibility;
   private UUID ownerUid;

   public WaystoneImpl(
      ResourceLocation waystoneType, UUID waystoneUid, ResourceKey<Level> dimension, BlockPos pos, WaystoneOrigin origin, @Nullable UUID ownerUid
   ) {
      this.waystoneType = waystoneType;
      this.waystoneUid = waystoneUid;
      this.dimension = dimension;
      this.pos = pos;
      this.origin = origin;
      this.ownerUid = ownerUid;
      this.visibility = WaystoneVisibility.fromWaystoneType(waystoneType);
   }

   @Override
   public UUID getWaystoneUid() {
      return this.waystoneUid;
   }

   @Override
   public Component getName() {
      return this.name;
   }

   @Override
   public void setName(Component name) {
      this.name = name;
   }

   @Override
   public ResourceKey<Level> getDimension() {
      return this.dimension;
   }

   @Override
   public WaystoneOrigin getOrigin() {
      return this.origin;
   }

   @Override
   public WaystoneVisibility getVisibility() {
      return this.visibility;
   }

   @Override
   public void setVisibility(WaystoneVisibility visibility) {
      this.visibility = visibility;
   }

   @Override
   public boolean isOwner(Player player) {
      return this.ownerUid == null || player.getGameProfile().getId().equals(this.ownerUid) || player.getAbilities().instabuild;
   }

   @Override
   public void setOwnerUid(@Nullable UUID ownerUid) {
      this.ownerUid = ownerUid;
   }

   @Override
   public BlockPos getPos() {
      return this.pos;
   }

   @Override
   public boolean isValid() {
      return true;
   }

   @Override
   public UUID getOwnerUid() {
      return this.ownerUid;
   }

   @Override
   public void setDimension(ResourceKey<Level> dimension) {
      this.dimension = dimension;
   }

   @Override
   public void setPos(BlockPos pos) {
      this.pos = pos;
   }

   @Override
   public ResourceLocation getWaystoneType() {
      return this.waystoneType;
   }

   @Override
   public boolean isValidInLevel(ServerLevel level) {
      BlockState state = level.getBlockState(this.pos);
      return state.is(ModBlockTags.IS_TELEPORT_TARGET);
   }

   @Override
   public boolean isTransient() {
      return this.isTransient;
   }

   @Override
   public void setTransient(boolean isTransient) {
      this.isTransient = isTransient;
   }

   public static List<Waystone> readList(RegistryFriendlyByteBuf buf) {
      int size = buf.readShort();
      List<Waystone> waystones = new ArrayList<>(size);

      for (int i = 0; i < size; i++) {
         waystones.add(read(buf));
      }

      return waystones;
   }

   public static Waystone read(RegistryFriendlyByteBuf buf) {
      UUID waystoneUid = buf.readUUID();
      ResourceLocation waystoneType = buf.readResourceLocation();
      Component name = (Component)ComponentSerialization.STREAM_CODEC.decode(buf);
      WaystoneVisibility visibility = (WaystoneVisibility)buf.readEnum(WaystoneVisibility.class);
      ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(buf.readUtf(250)));
      BlockPos pos = buf.readBlockPos();
      WaystoneOrigin origin = (WaystoneOrigin)buf.readEnum(WaystoneOrigin.class);
      WaystoneImpl waystone = new WaystoneImpl(waystoneType, waystoneUid, dimension, pos, origin, null);
      waystone.setName(name);
      waystone.setVisibility(visibility);
      return waystone;
   }

   private static BlockPos readLegacyBlockPos(CompoundTag compound) {
      return new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z"));
   }

   public static Waystone read(CompoundTag compound, Provider provider) {
      UUID waystoneUid = NbtUtils.loadUUID(Objects.requireNonNull(compound.get("WaystoneUid")));
      String legacyName = compound.getString("Name");
      MutableComponent name = compound.contains("NameV2") ? Serializer.fromJson(compound.getString("NameV2"), provider) : Component.literal(legacyName);
      ResourceKey<Level> dimensionType = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(compound.getString("World")));
      BlockPos pos = NbtUtils.readBlockPos(compound, "BlockPos").orElseGet(() -> readLegacyBlockPos(compound.getCompound("BlockPos")));
      boolean legacyWasGenerated = compound.getBoolean("WasGenerated");
      WaystoneOrigin origin = legacyWasGenerated ? WaystoneOrigin.WILDERNESS : WaystoneOrigin.UNKNOWN;
      if (compound.contains("Origin")) {
         try {
            origin = WaystoneOrigin.valueOf(compound.getString("Origin"));
         } catch (IllegalArgumentException var13) {
         }
      }

      UUID ownerUid = compound.contains("OwnerUid") ? NbtUtils.loadUUID(Objects.requireNonNull(compound.get("OwnerUid"))) : null;
      ResourceLocation waystoneType = compound.contains("Type") ? ResourceLocation.parse(compound.getString("Type")) : WaystoneTypes.WAYSTONE;
      boolean isTransient = compound.contains("Transient") && compound.getBoolean("Transient");
      WaystoneImpl waystone = new WaystoneImpl(waystoneType, waystoneUid, dimensionType, pos, origin, ownerUid);
      waystone.setName(name);
      waystone.setTransient(isTransient);
      if (compound.contains("Visibility")) {
         waystone.setVisibility(WaystoneVisibility.valueOf(compound.getString("Visibility")));
      } else {
         waystone.setVisibility(compound.getBoolean("IsGlobal") ? WaystoneVisibility.GLOBAL : WaystoneVisibility.ACTIVATION);
      }

      return waystone;
   }

   public static void writeList(RegistryFriendlyByteBuf buf, Collection<Waystone> waystones) {
      buf.writeShort(waystones.size());

      for (Waystone waystone : waystones) {
         write(buf, waystone);
      }
   }

   public static void write(RegistryFriendlyByteBuf buf, Waystone waystone) {
      buf.writeUUID(waystone.getWaystoneUid());
      buf.writeResourceLocation(waystone.getWaystoneType());
      ComponentSerialization.STREAM_CODEC.encode(buf, waystone.getName());
      buf.writeEnum(waystone.getVisibility());
      buf.writeResourceLocation(waystone.getDimension().location());
      buf.writeBlockPos(waystone.getPos());
      buf.writeEnum(waystone.getOrigin());
   }

   public static CompoundTag write(Waystone waystone, CompoundTag compound, Provider provider) {
      compound.put("WaystoneUid", NbtUtils.createUUID(waystone.getWaystoneUid()));
      compound.putString("Type", waystone.getWaystoneType().toString());
      compound.putString("NameV2", Serializer.toJson(waystone.getName(), provider));
      compound.putString("World", waystone.getDimension().location().toString());
      compound.putBoolean("Transient", waystone.isTransient());
      compound.put("BlockPos", NbtUtils.writeBlockPos(waystone.getPos()));
      compound.putString("Origin", waystone.getOrigin().name());
      if (waystone.getOwnerUid() != null) {
         compound.put("OwnerUid", NbtUtils.createUUID(waystone.getOwnerUid()));
      }

      compound.putString("Visibility", waystone.getVisibility().name());
      return compound;
   }
}
