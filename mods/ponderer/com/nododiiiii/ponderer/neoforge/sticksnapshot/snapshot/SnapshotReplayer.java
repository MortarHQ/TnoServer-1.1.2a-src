package com.nododiiiii.ponderer.neoforge.sticksnapshot.snapshot;

import com.mojang.authlib.GameProfile;
import com.nododiiiii.ponderer.neoforge.sticksnapshot.StickSnapshotFeature;
import com.nododiiiii.ponderer.neoforge.sticksnapshot.network.MirrorNeoForgeOpenPacket;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.payload.AdvancedOpenScreenPayload;
import org.jetbrains.annotations.Nullable;

public class SnapshotReplayer {
   private static final AtomicInteger MIRROR_CONTAINER_COUNTER = new AtomicInteger(100);

   private SnapshotReplayer() {
   }

   public static void replay(ServerPlayer player, BlockSnapshot snapshot) {
      ServerLevel level = player.serverLevel();
      BlockPos pos = ReplaySessionManager.getSandboxPos(player);
      level.getChunkAt(pos);
      ReplaySessionManager.restoreSession(player);
      BlockState snapshotState = Block.stateById(snapshot.getStateId());
      BlockState originalState = level.getBlockState(pos);
      BlockEntity originalBe = level.getBlockEntity(pos);
      CompoundTag originalBeTag = null;
      if (originalBe != null) {
         originalBeTag = originalBe.saveWithFullMetadata(level.registryAccess());
      }

      List<SnapshotReplayer.SandboxInjectedBlock> sandboxInjectedBlocks = injectReferencedContextBlocks(level, snapshot, pos);
      List<SnapshotReplayer.SandboxInjectedBlock> sandboxClearedAirBlocks = clearSandboxNeighborsToAir(level, pos);
      boolean keepSandbox = false;

      try {
         level.setBlock(pos, snapshotState, 0);
         if (snapshot.getBlockEntityTag() != null) {
            CompoundTag normalizedBeTag = normalizeBlockEntityTagForPos(snapshot.getBlockEntityTag(), snapshot.getPos(), pos);
            BlockEntity replayBe = BlockEntity.loadStatic(pos, snapshotState, normalizedBeTag, level.registryAccess());
            if (replayBe != null) {
               level.setBlockEntity(replayBe);
            }
         }

         Vec3 hitOffset = snapshot.getHitLocation().subtract(Vec3.atLowerCornerOf(snapshot.getPos()));
         Vec3 sandboxHit = Vec3.atLowerCornerOf(pos).add(hitOffset);
         BlockHitResult hitResult = new BlockHitResult(sandboxHit, snapshot.getFace(), pos, snapshot.isInside());
         MenuProvider provider = level.getBlockState(pos).getMenuProvider(level, pos);
         Component menuTitle = (Component)(provider != null ? provider.getDisplayName() : Component.literal(snapshot.getBlockId().toString()));
         SnapshotReplayer.CapturedPackets capturedPackets = runVirtualUse(level, player, snapshotState, hitResult, sandboxHit);
         if (capturedPackets != null && !capturedPackets.packets.isEmpty()) {
            mirrorCapturedPacketsToRealPlayer(player, level, capturedPackets, snapshot);
            StickSnapshotFeature.LOGGER
               .info(
                  "Opened mirrored interface for player={} block={} title={} packetCount={}",
                  new Object[]{player.getScoreboardName(), snapshot.getBlockId(), menuTitle.getString(), capturedPackets.packets.size()}
               );
         }
      } finally {
         if (!sandboxClearedAirBlocks.isEmpty()) {
            restoreInjectedContextBlocks(level, sandboxClearedAirBlocks);
         }

         if (!sandboxInjectedBlocks.isEmpty()) {
            restoreInjectedContextBlocks(level, sandboxInjectedBlocks);
         }

         if (!keepSandbox) {
            ReplaySessionManager.restoreBlock(level, pos, originalState, originalBeTag);
         }
      }
   }

   private static List<SnapshotReplayer.SandboxInjectedBlock> injectReferencedContextBlocks(ServerLevel level, BlockSnapshot snapshot, BlockPos sandboxPos) {
      CompoundTag snapshotBeTag = snapshot.getBlockEntityTag();
      if (snapshotBeTag != null && level.dimension().location().equals(snapshot.getDimensionId())) {
         int dx = sandboxPos.getX() - snapshot.getPos().getX();
         int dy = sandboxPos.getY() - snapshot.getPos().getY();
         int dz = sandboxPos.getZ() - snapshot.getPos().getZ();
         Set<BlockPos> referencedSourcePositions = new LinkedHashSet<>();
         collectEmbeddedPositions(snapshotBeTag, referencedSourcePositions);
         referencedSourcePositions.remove(snapshot.getPos());
         if (referencedSourcePositions.isEmpty()) {
            return List.of();
         }

         List<SnapshotReplayer.SandboxInjectedBlock> injected = new ArrayList<>();
         int injectedCount = 0;

         for (BlockPos sourcePos : referencedSourcePositions) {
            if (injectedCount >= 8) {
               break;
            }

            if (level.hasChunkAt(sourcePos)) {
               BlockPos targetPos = sourcePos.offset(dx, dy, dz);
               if (!targetPos.equals(sandboxPos)) {
                  BlockState oldState = level.getBlockState(targetPos);
                  BlockEntity oldBe = level.getBlockEntity(targetPos);
                  CompoundTag oldBeTag = oldBe != null ? oldBe.saveWithFullMetadata(level.registryAccess()) : null;
                  injected.add(new SnapshotReplayer.SandboxInjectedBlock(targetPos.immutable(), oldState, oldBeTag));
                  BlockState sourceState = level.getBlockState(sourcePos);
                  BlockEntity sourceBe = level.getBlockEntity(sourcePos);
                  CompoundTag sourceBeTag = sourceBe != null ? sourceBe.saveWithFullMetadata(level.registryAccess()) : null;
                  level.setBlock(targetPos, sourceState, 0);
                  if (sourceBeTag != null) {
                     CompoundTag normalizedBeTag = normalizeBlockEntityTagForPos(sourceBeTag, sourcePos, targetPos);
                     BlockEntity copiedBe = BlockEntity.loadStatic(targetPos, sourceState, normalizedBeTag, level.registryAccess());
                     if (copiedBe != null) {
                        level.setBlockEntity(copiedBe);
                     }
                  } else {
                     level.removeBlockEntity(targetPos);
                  }

                  injectedCount++;
               }
            }
         }

         return injected;
      } else {
         return List.of();
      }
   }

   private static void restoreInjectedContextBlocks(ServerLevel level, List<SnapshotReplayer.SandboxInjectedBlock> injectedBlocks) {
      for (SnapshotReplayer.SandboxInjectedBlock injected : injectedBlocks) {
         ReplaySessionManager.restoreBlock(level, injected.pos(), injected.originalState(), injected.originalBeTag());
      }
   }

   private static List<SnapshotReplayer.SandboxInjectedBlock> clearSandboxNeighborsToAir(ServerLevel level, BlockPos sandboxPos) {
      List<SnapshotReplayer.SandboxInjectedBlock> cleared = new ArrayList<>();

      for (Direction direction : Direction.values()) {
         BlockPos targetPos = sandboxPos.relative(direction);
         BlockState oldState = level.getBlockState(targetPos);
         BlockEntity oldBe = level.getBlockEntity(targetPos);
         CompoundTag oldBeTag = oldBe != null ? oldBe.saveWithFullMetadata(level.registryAccess()) : null;
         cleared.add(new SnapshotReplayer.SandboxInjectedBlock(targetPos.immutable(), oldState, oldBeTag));
         level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 0);
         level.removeBlockEntity(targetPos);
      }

      return cleared;
   }

   private static SnapshotReplayer.CapturedPackets runVirtualUse(
      ServerLevel level, ServerPlayer realPlayer, BlockState snapshotState, BlockHitResult hitResult, Vec3 sandboxHit
   ) {
      try {
         UUID fakeId = UUID.nameUUIDFromBytes(("sticksnapshot:" + realPlayer.getUUID()).getBytes(StandardCharsets.UTF_8));
         GameProfile profile = new GameProfile(fakeId, "stick_snapshot_virtual");

         try (ReplayGuard.Scope ignored = ReplayGuard.begin(realPlayer, fakeId, "virtual-use")) {
            FakePlayer fakePlayer = new ReplayGuardedFakePlayer(level, profile);
            if (fakePlayer.containerMenu != fakePlayer.inventoryMenu) {
               fakePlayer.closeContainer();
            }

            ServerGamePacketListenerImpl oldConnection = fakePlayer.connection;
            SnapshotReplayer.CapturingPacketListener captureConnection = new SnapshotReplayer.CapturingPacketListener(level, fakePlayer, profile);
            fakePlayer.connection = captureConnection;
            fakePlayer.setPos(sandboxHit.x, sandboxHit.y, sandboxHit.z);
            fakePlayer.setYRot(realPlayer.getYRot());
            fakePlayer.setXRot(realPlayer.getXRot());
            fakePlayer.setShiftKeyDown(realPlayer.isShiftKeyDown());
            ItemStack oldMainHand = fakePlayer.getItemInHand(InteractionHand.MAIN_HAND);
            fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);

            try {
               snapshotState.useWithoutItem(level, fakePlayer, hitResult);
               AbstractContainerMenu fakeMenu = fakePlayer.containerMenu != fakePlayer.inventoryMenu ? fakePlayer.containerMenu : null;
               List<Packet<?>> packets = captureConnection.snapshot();
               if (fakeMenu == null) {
                  StickSnapshotFeature.LOGGER
                     .warn("Virtual UI replay opened no menu for player={} block={}", realPlayer.getScoreboardName(), snapshotState.getBlock());
                  return null;
               } else if (packets.isEmpty()) {
                  StickSnapshotFeature.LOGGER
                     .warn(
                        "Virtual UI replay opened menu but captured no packets for player={} block={} menu={}",
                        new Object[]{realPlayer.getScoreboardName(), snapshotState.getBlock(), fakeMenu.getClass().getName()}
                     );
                  return null;
               } else {
                  int sourceContainerId = detectContainerId(packets);
                  return new SnapshotReplayer.CapturedPackets(packets, sourceContainerId);
               }
            } finally {
               fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, oldMainHand);
               if (fakePlayer.containerMenu != fakePlayer.inventoryMenu) {
                  fakePlayer.closeContainer();
               }

               fakePlayer.connection = oldConnection;
            }
         }
      } catch (Exception ex) {
         StickSnapshotFeature.LOGGER
            .warn("Virtual UI replay failed for player={} block={}", new Object[]{realPlayer.getScoreboardName(), snapshotState.getBlock(), ex});
         return null;
      }
   }

   private static void mirrorCapturedPacketsToRealPlayer(
      ServerPlayer realPlayer, ServerLevel level, SnapshotReplayer.CapturedPackets captured, BlockSnapshot snapshot
   ) {
      List<Packet<?>> packets = captured.packets;
      int sourceContainerId = captured.sourceContainerId;
      boolean hasAdvancedOpenPayload = hasAdvancedOpenPayload(packets);
      BlockPos clientVirtualPos = getClientVirtualPos(realPlayer);
      int targetContainerId = sourceContainerId;
      if (!hasAdvancedOpenPayload && sourceContainerId >= 0) {
         targetContainerId = nextMirrorContainerId();
      }

      int mirrored = 0;

      for (Packet<?> packet : packets) {
         if (packet instanceof ClientboundCustomPayloadPacket customPayload) {
            MirrorNeoForgeOpenPacket neoForgeOpenPacket = decodeNeoForgeOpenPacket(level, customPayload, snapshot, clientVirtualPos);
            if (neoForgeOpenPacket != null) {
               PacketDistributor.sendToPlayer(realPlayer, neoForgeOpenPacket, new CustomPacketPayload[0]);
               mirrored++;
               continue;
            }
         }

         if (!hasAdvancedOpenPayload && packet instanceof ClientboundOpenScreenPacket openPacket) {
            int mappedWindowId = remapContainerId(openPacket.getContainerId(), sourceContainerId, targetContainerId);
            MirrorNeoForgeOpenPacket vanillaMirrorOpen = createVanillaMirrorOpenPacket(openPacket, snapshot, clientVirtualPos, mappedWindowId);
            PacketDistributor.sendToPlayer(realPlayer, vanillaMirrorOpen, new CustomPacketPayload[0]);
            mirrored++;
         } else {
            Packet<?> packetToSend = remapMenuPacketContainerId(packet, sourceContainerId, targetContainerId);
            if (packetToSend != null) {
               realPlayer.connection.send(packetToSend);
               mirrored++;
            }
         }
      }

      if (mirrored != 0) {
         ;
      }
   }

   private static boolean hasAdvancedOpenPayload(List<Packet<?>> packets) {
      for (Packet<?> packet : packets) {
         if (packet instanceof ClientboundCustomPayloadPacket customPayload && customPayload.payload() instanceof AdvancedOpenScreenPayload) {
            return true;
         }
      }

      return false;
   }

   private static Packet<?> remapMenuPacketContainerId(Packet<?> packet, int sourceContainerId, int targetContainerId) {
      if (packet instanceof ClientboundOpenScreenPacket openPacket) {
         int newContainerId = remapContainerId(openPacket.getContainerId(), sourceContainerId, targetContainerId);
         return new ClientboundOpenScreenPacket(newContainerId, openPacket.getType(), openPacket.getTitle());
      } else if (packet instanceof ClientboundContainerSetSlotPacket setSlotPacket) {
         int original = setSlotPacket.getContainerId();
         int newContainerId = original < 0 ? original : remapContainerId(original, sourceContainerId, targetContainerId);
         return new ClientboundContainerSetSlotPacket(newContainerId, setSlotPacket.getStateId(), setSlotPacket.getSlot(), setSlotPacket.getItem());
      } else if (!(packet instanceof ClientboundContainerSetContentPacket setContentPacket)) {
         if (packet instanceof ClientboundContainerSetDataPacket setDataPacket) {
            int newContainerId = remapContainerId(setDataPacket.getContainerId(), sourceContainerId, targetContainerId);
            return new ClientboundContainerSetDataPacket(newContainerId, setDataPacket.getId(), setDataPacket.getValue());
         } else if (packet instanceof ClientboundContainerClosePacket closePacket) {
            int newContainerId = remapContainerId(closePacket.getContainerId(), sourceContainerId, targetContainerId);
            return new ClientboundContainerClosePacket(newContainerId);
         } else if (packet instanceof ClientboundHorseScreenOpenPacket horsePacket) {
            int newContainerId = remapContainerId(horsePacket.getContainerId(), sourceContainerId, targetContainerId);
            return new ClientboundHorseScreenOpenPacket(newContainerId, horsePacket.getInventoryColumns(), horsePacket.getEntityId());
         } else if (packet instanceof ClientboundMerchantOffersPacket offersPacket) {
            int newContainerId = remapContainerId(offersPacket.getContainerId(), sourceContainerId, targetContainerId);
            return new ClientboundMerchantOffersPacket(
               newContainerId,
               offersPacket.getOffers(),
               offersPacket.getVillagerLevel(),
               offersPacket.getVillagerXp(),
               offersPacket.showProgress(),
               offersPacket.canRestock()
            );
         } else {
            return packet instanceof ClientboundCustomPayloadPacket ? packet : packet;
         }
      } else {
         int newContainerId = remapContainerId(setContentPacket.getContainerId(), sourceContainerId, targetContainerId);
         NonNullList<ItemStack> copied = NonNullList.withSize(setContentPacket.getItems().size(), ItemStack.EMPTY);

         for (int i = 0; i < setContentPacket.getItems().size(); i++) {
            copied.set(i, ((ItemStack)setContentPacket.getItems().get(i)).copy());
         }

         return new ClientboundContainerSetContentPacket(newContainerId, setContentPacket.getStateId(), copied, setContentPacket.getCarriedItem());
      }
   }

   @Nullable
   private static MirrorNeoForgeOpenPacket decodeNeoForgeOpenPacket(
      ServerLevel level, ClientboundCustomPayloadPacket customPayload, BlockSnapshot snapshot, BlockPos clientVirtualPos
   ) {
      if (customPayload.payload() instanceof AdvancedOpenScreenPayload payload) {
         int var16 = BuiltInRegistries.MENU.getId(payload.menuType());
         int windowId = payload.windowId();
         Component title = payload.name();
         byte[] extraData = payload.additionalData();
         BlockPos menuSourcePos = readFirstBlockPos(extraData);
         byte[] rewrittenExtraData = rewriteFirstBlockPos(extraData, clientVirtualPos);
         BlockState contextState = Block.stateById(snapshot.getStateId());
         CompoundTag contextBeTag = snapshot.getBlockEntityTag();
         BlockPos contextPos = snapshot.getPos();
         boolean usingSnapshotContext = contextBeTag != null;
         if (!usingSnapshotContext && menuSourcePos != null && level.hasChunkAt(menuSourcePos)) {
            contextPos = menuSourcePos;
            contextState = level.getBlockState(menuSourcePos);
            BlockEntity contextBe = level.getBlockEntity(menuSourcePos);
            contextBeTag = contextBe != null ? contextBe.saveWithFullMetadata(level.registryAccess()) : null;
         }

         CompoundTag normalizedSnapshotBeTag = normalizeBlockEntityTagForPos(contextBeTag, contextPos, clientVirtualPos);
         return new MirrorNeoForgeOpenPacket(var16, windowId, title, rewrittenExtraData, Block.getId(contextState), normalizedSnapshotBeTag);
      } else {
         return null;
      }
   }

   private static BlockPos getClientVirtualPos(ServerPlayer player) {
      int y = player.serverLevel().getMinBuildHeight() + 1;
      return new BlockPos(player.getBlockX(), y, player.getBlockZ());
   }

   private static byte[] rewriteFirstBlockPos(byte[] extraData, BlockPos sourcePos) {
      if (extraData.length < 8) {
         return extraData;
      }

      FriendlyByteBuf input = new FriendlyByteBuf(Unpooled.wrappedBuffer(extraData));
      input.readLong();
      byte[] remaining = new byte[input.readableBytes()];
      input.readBytes(remaining);
      FriendlyByteBuf output = new FriendlyByteBuf(Unpooled.buffer(extraData.length));
      output.writeLong(sourcePos.asLong());
      output.writeBytes(remaining);
      byte[] rewritten = new byte[output.readableBytes()];
      output.readBytes(rewritten);
      return rewritten;
   }

   @Nullable
   private static BlockPos readFirstBlockPos(byte[] extraData) {
      if (extraData.length < 8) {
         return null;
      }

      FriendlyByteBuf input = new FriendlyByteBuf(Unpooled.wrappedBuffer(extraData));
      return BlockPos.of(input.readLong());
   }

   private static MirrorNeoForgeOpenPacket createVanillaMirrorOpenPacket(
      ClientboundOpenScreenPacket openPacket, BlockSnapshot snapshot, BlockPos clientVirtualPos, int mappedWindowId
   ) {
      int menuTypeId = BuiltInRegistries.MENU.getId(openPacket.getType());
      byte[] extraData = encodeVirtualPos(clientVirtualPos);
      BlockState contextState = Block.stateById(snapshot.getStateId());
      CompoundTag normalizedSnapshotBeTag = normalizeBlockEntityTagForPos(snapshot.getBlockEntityTag(), snapshot.getPos(), clientVirtualPos);
      return new MirrorNeoForgeOpenPacket(menuTypeId, mappedWindowId, openPacket.getTitle(), extraData, Block.getId(contextState), normalizedSnapshotBeTag);
   }

   private static byte[] encodeVirtualPos(BlockPos pos) {
      FriendlyByteBuf data = new FriendlyByteBuf(Unpooled.buffer(8));
      data.writeLong(pos.asLong());
      byte[] out = new byte[data.readableBytes()];
      data.readBytes(out);
      return out;
   }

   private static int remapContainerId(int id, int sourceContainerId, int targetContainerId) {
      if (sourceContainerId < 0) {
         return id;
      } else {
         return id == sourceContainerId ? targetContainerId : id;
      }
   }

   @Nullable
   private static CompoundTag normalizeBlockEntityTagForPos(@Nullable CompoundTag rawTag, BlockPos sourcePos, BlockPos targetPos) {
      if (rawTag == null) {
         return null;
      }

      CompoundTag normalized = rawTag.copy();
      int dx = targetPos.getX() - sourcePos.getX();
      int dy = targetPos.getY() - sourcePos.getY();
      int dz = targetPos.getZ() - sourcePos.getZ();
      remapEmbeddedPositions(normalized, dx, dy, dz);
      normalized.putInt("x", targetPos.getX());
      normalized.putInt("y", targetPos.getY());
      normalized.putInt("z", targetPos.getZ());
      return normalized;
   }

   private static int remapEmbeddedPositions(CompoundTag tag, int dx, int dy, int dz) {
      int remapped = 0;
      if (tag.contains("x") && tag.contains("y") && tag.contains("z")) {
         tag.putInt("x", tag.getInt("x") + dx);
         tag.putInt("y", tag.getInt("y") + dy);
         tag.putInt("z", tag.getInt("z") + dz);
         remapped++;
      }

      if (tag.contains("X") && tag.contains("Y") && tag.contains("Z")) {
         tag.putInt("X", tag.getInt("X") + dx);
         tag.putInt("Y", tag.getInt("Y") + dy);
         tag.putInt("Z", tag.getInt("Z") + dz);
         remapped++;
      }

      for (String key : tag.getAllKeys()) {
         if (tag.get(key) instanceof CompoundTag nested) {
            remapped += remapEmbeddedPositions(nested, dx, dy, dz);
         } else if (tag.get(key) instanceof ListTag listTag) {
            remapped += remapEmbeddedPositionsInList(listTag, dx, dy, dz);
         }
      }

      return remapped;
   }

   private static int remapEmbeddedPositionsInList(ListTag listTag, int dx, int dy, int dz) {
      int remapped = 0;

      for (int i = 0; i < listTag.size(); i++) {
         if (listTag.get(i) instanceof CompoundTag nested) {
            remapped += remapEmbeddedPositions(nested, dx, dy, dz);
         } else if (listTag.get(i) instanceof ListTag nestedList) {
            remapped += remapEmbeddedPositionsInList(nestedList, dx, dy, dz);
         }
      }

      return remapped;
   }

   private static void collectEmbeddedPositions(CompoundTag tag, Set<BlockPos> positions) {
      if (tag.contains("x") && tag.contains("y") && tag.contains("z")) {
         positions.add(new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z")));
      }

      if (tag.contains("X") && tag.contains("Y") && tag.contains("Z")) {
         positions.add(new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z")));
      }

      for (String key : tag.getAllKeys()) {
         if (tag.get(key) instanceof CompoundTag nested) {
            collectEmbeddedPositions(nested, positions);
         } else if (tag.get(key) instanceof ListTag listTag) {
            collectEmbeddedPositionsInList(listTag, positions);
         }
      }
   }

   private static void collectEmbeddedPositionsInList(ListTag listTag, Set<BlockPos> positions) {
      for (int i = 0; i < listTag.size(); i++) {
         if (listTag.get(i) instanceof CompoundTag nested) {
            collectEmbeddedPositions(nested, positions);
         } else if (listTag.get(i) instanceof ListTag nestedList) {
            collectEmbeddedPositionsInList(nestedList, positions);
         }
      }
   }

   private static int detectContainerId(List<Packet<?>> packets) {
      for (Packet<?> packet : packets) {
         if (packet instanceof ClientboundOpenScreenPacket openPacket) {
            return openPacket.getContainerId();
         }

         if (packet instanceof ClientboundContainerSetContentPacket contentPacket) {
            return contentPacket.getContainerId();
         }

         if (packet instanceof ClientboundContainerSetSlotPacket slotPacket && slotPacket.getContainerId() >= 0) {
            return slotPacket.getContainerId();
         }

         if (packet instanceof ClientboundContainerSetDataPacket dataPacket) {
            return dataPacket.getContainerId();
         }

         if (packet instanceof ClientboundContainerClosePacket closePacket) {
            return closePacket.getContainerId();
         }

         if (packet instanceof ClientboundHorseScreenOpenPacket horsePacket) {
            return horsePacket.getContainerId();
         }

         if (packet instanceof ClientboundMerchantOffersPacket offersPacket) {
            return offersPacket.getContainerId();
         }
      }

      return -1;
   }

   private static int nextMirrorContainerId() {
      int id = MIRROR_CONTAINER_COUNTER.incrementAndGet();
      if (id > 1000) {
         MIRROR_CONTAINER_COUNTER.set(100);
         return 101;
      } else {
         return id;
      }
   }

   private static class CapturedPackets {
      private final List<Packet<?>> packets;
      private final int sourceContainerId;

      private CapturedPackets(List<Packet<?>> packets, int sourceContainerId) {
         this.packets = packets;
         this.sourceContainerId = sourceContainerId;
      }
   }

   private static class CapturingConnection extends Connection {
      private CapturingConnection() {
         super(PacketFlow.SERVERBOUND);
      }
   }

   private static class CapturingPacketListener extends ServerGamePacketListenerImpl {
      private final List<Packet<?>> capturedPackets = new ArrayList<>();

      private CapturingPacketListener(ServerLevel level, ServerPlayer fakePlayer, GameProfile profile) {
         super(level.getServer(), new SnapshotReplayer.CapturingConnection(), fakePlayer, CommonListenerCookie.createInitial(profile, false));
      }

      public void send(Packet<?> packet) {
         this.capture(packet, null);
      }

      public void send(Packet<?> packet, @Nullable PacketSendListener sendListener) {
         this.capture(packet, sendListener);
      }

      private void capture(Packet<?> packet, @Nullable PacketSendListener sendListener) {
         this.capturedPackets.add(packet);
         if (sendListener != null) {
            sendListener.onSuccess();
         }
      }

      private List<Packet<?>> snapshot() {
         return new ArrayList<>(this.capturedPackets);
      }
   }

   private record SandboxInjectedBlock(BlockPos pos, BlockState originalState, @Nullable CompoundTag originalBeTag) {
   }
}
