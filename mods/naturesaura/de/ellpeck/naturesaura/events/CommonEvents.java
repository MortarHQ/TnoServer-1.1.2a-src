package de.ellpeck.naturesaura.events;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.NaturesAura;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.misc.ILevelData;
import de.ellpeck.naturesaura.chunk.AuraChunk;
import de.ellpeck.naturesaura.commands.CommandAura;
import de.ellpeck.naturesaura.misc.LevelData;
import de.ellpeck.naturesaura.packet.PacketHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.level.ChunkEvent.Unload;
import net.neoforged.neoforge.event.level.ChunkWatchEvent.Watch;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;

public class CommonEvents {
   private static final Method GET_LOADED_CHUNKS_METHOD = ObfuscationReflectionHelper.findMethod(ChunkMap.class, "getChunks", new Class[0]);
   private static final SetMultimap<UUID, ChunkPos> PENDING_AURA_CHUNKS = Multimaps.synchronizedSetMultimap(HashMultimap.create());

   @SubscribeEvent
   public void onChunkUnload(Unload event) {
      ChunkAccess iChunk = event.getChunk();
      if (iChunk instanceof LevelChunk chunk) {
         IAuraChunk auraChunk = (IAuraChunk)chunk.getData(NaturesAuraAPI.AURA_CHUNK_ATTACHMENT);
         if (auraChunk instanceof AuraChunk) {
            LevelData data = (LevelData)ILevelData.getLevelData(chunk.getLevel());
            data.auraChunksWithSpots.remove(chunk.getPos().toLong());
         }
      }

      synchronized (PENDING_AURA_CHUNKS) {
         PENDING_AURA_CHUNKS.values().remove(iChunk.getPos());
      }
   }

   @SubscribeEvent
   public void onItemUse(RightClickBlock event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide) {
         ItemStack held = event.getItemStack();
         if (!held.isEmpty() && BuiltInRegistries.ITEM.getKey(held.getItem()).getPath().contains("chisel")) {
            BlockState state = player.level().getBlockState(event.getPos());
            if (NaturesAuraAPI.BOTANIST_PICKAXE_CONVERSIONS.containsKey(state)) {
               LevelData data = (LevelData)ILevelData.getLevelData(player.level());
               data.addMossStone(event.getPos());
            }
         }
      }
   }

   @SubscribeEvent
   public void onLevelTick(Post event) {
      if (!event.getLevel().isClientSide && event.getLevel().getGameTime() % 20L == 0L) {
         event.getLevel().getProfiler().push("naturesaura:onLevelTick");

         try {
            ChunkMap manager = ((ServerChunkCache)event.getLevel().getChunkSource()).chunkMap;

            for (ChunkHolder holder : (Iterable)GET_LOADED_CHUNKS_METHOD.invoke(manager)) {
               LevelChunk chunk = holder.getTickingChunk();
               if (chunk != null) {
                  AuraChunk auraChunk = (AuraChunk)chunk.getData(NaturesAuraAPI.AURA_CHUNK_ATTACHMENT);
                  if (auraChunk != null) {
                     auraChunk.update();
                  }
               }
            }
         } catch (IllegalAccessException | InvocationTargetException e) {
            NaturesAura.LOGGER.fatal(e);
         }

         event.getLevel().getProfiler().pop();
      }
   }

   @SubscribeEvent
   public void onPlayerTick(net.neoforged.neoforge.event.tick.PlayerTickEvent.Post event) {
      if (!event.getEntity().level().isClientSide) {
         if (event.getEntity().level().getGameTime() % 10L == 0L) {
            Set<ChunkPos> pending = PENDING_AURA_CHUNKS.get(event.getEntity().getUUID());
            pending.removeIf(p -> this.handleChunkWatchDeferred(event.getEntity(), p));
         }

         if (event.getEntity().level().getGameTime() % 200L != 0L) {
            return;
         }

         int aura = IAuraChunk.triangulateAuraInArea(event.getEntity().level(), event.getEntity().blockPosition(), 25);
         if (aura <= 0) {
            Helper.addAdvancement(event.getEntity(), ResourceLocation.fromNamespaceAndPath("naturesaura", "negative_imbalance"), "triggered_in_code");
         } else if (aura >= 1500000) {
            Helper.addAdvancement(event.getEntity(), ResourceLocation.fromNamespaceAndPath("naturesaura", "positive_imbalance"), "triggered_in_code");
         }
      }
   }

   @SubscribeEvent
   public void onPlayerDisconnect(PlayerLoggedOutEvent event) {
      PENDING_AURA_CHUNKS.removeAll(event.getEntity().getUUID());
   }

   @SubscribeEvent
   public void onChunkWatch(Watch event) {
      PENDING_AURA_CHUNKS.put(event.getPlayer().getUUID(), event.getPos());
   }

   private boolean handleChunkWatchDeferred(Player player, ChunkPos pos) {
      if (Helper.getLoadedChunk(player.level(), pos.x, pos.z) instanceof LevelChunk levelChunk) {
         AuraChunk auraChunk = (AuraChunk)levelChunk.getData(NaturesAuraAPI.AURA_CHUNK_ATTACHMENT);
         if (auraChunk == null) {
            return false;
         }

         PacketHandler.sendTo(player, auraChunk.makePacket());
         return true;
      } else {
         return false;
      }
   }

   @SubscribeEvent
   public void onCommands(RegisterCommandsEvent event) {
      CommandAura.register(event.getDispatcher());
   }
}
