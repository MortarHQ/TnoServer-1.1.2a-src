package twilightforest.item;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData.HoldingPlayer;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFDataMaps;
import twilightforest.init.TFItems;
import twilightforest.item.mapdata.TFMazeMapData;
import twilightforest.util.datamaps.OreMapOreColor;

public class MazeMapItem extends MapItem {
   public static final String STR_ID = "mazemap";
   private static final int YSEARCH = 3;
   protected final boolean mapOres;

   public MazeMapItem(boolean mapOres, Properties properties) {
      super(properties);
      this.mapOres = mapOres;
   }

   public static ItemStack setupNewMap(
      Level level, int worldX, int worldZ, byte scale, boolean trackingPosition, boolean unlimitedTracking, int worldY, boolean mapOres
   ) {
      ItemStack itemstack = new ItemStack(mapOres ? (ItemLike)TFItems.FILLED_ORE_MAP.get() : (ItemLike)TFItems.FILLED_MAZE_MAP.get());
      createMapData(itemstack, level, worldX, worldZ, scale, trackingPosition, unlimitedTracking, level.dimension(), worldY, mapOres);
      return itemstack;
   }

   @Nullable
   public static TFMazeMapData getData(ItemStack stack, Level level) {
      MapId id = (MapId)stack.get(DataComponents.MAP_ID);
      return id == null ? null : TFMazeMapData.getMazeMapData(level, getMapName(id.id()));
   }

   @Nullable
   protected TFMazeMapData getCustomMapData(ItemStack stack, Level level) {
      TFMazeMapData mapdata = getData(stack, level);
      if (mapdata == null && !level.isClientSide()) {
         BlockPos pos = level.getSharedSpawnPos();
         mapdata = createMapData(stack, level, pos.getX(), pos.getZ(), 0, false, false, level.dimension(), pos.getY(), this.mapOres);
      }

      return mapdata;
   }

   private static TFMazeMapData createMapData(
      ItemStack stack,
      Level level,
      int x,
      int z,
      int scale,
      boolean trackingPosition,
      boolean unlimitedTracking,
      ResourceKey<Level> dimension,
      int y,
      boolean ore
   ) {
      MapId i = level.getFreeMapId();
      int mapSize = 128 * (1 << scale);
      int roundX = Mth.floor((x + 64.0) / mapSize);
      int roundZ = Mth.floor((z + 64.0) / mapSize);
      int scaledX = roundX * mapSize + mapSize / 2 - 64;
      int scaledZ = roundZ * mapSize + mapSize / 2 - 64;
      TFMazeMapData mapdata = new TFMazeMapData(scaledX, scaledZ, (byte)scale, trackingPosition, unlimitedTracking, false, dimension);
      mapdata.calculateMapCenter(level, x, y, z);
      mapdata.ore = ore;
      TFMazeMapData.registerMazeMapData(level, mapdata, getMapName(i.id()));
      stack.set(DataComponents.MAP_ID, i);
      return mapdata;
   }

   public static String getMapName(int id) {
      return "mazemap_" + id;
   }

   public void update(Level level, Entity viewer, MapItemSavedData data) {
      if (level.dimension() == data.dimension && viewer instanceof Player) {
         int blocksPerPixel = 1 << data.scale;
         int centerX = data.centerX;
         int centerZ = data.centerZ;
         int viewerX = Mth.floor(viewer.getX() - centerX) / blocksPerPixel + 64;
         int viewerZ = Mth.floor(viewer.getZ() - centerZ) / blocksPerPixel + 64;
         int viewRadiusPixels = 16;
         if (level.dimensionType().hasCeiling()) {
            viewRadiusPixels /= 2;
         }

         HoldingPlayer mapdata$mapinfo = data.getHoldingPlayer((Player)viewer);
         mapdata$mapinfo.step++;
         boolean flag = false;

         for (int xPixel = viewerX - viewRadiusPixels + 1; xPixel < viewerX + viewRadiusPixels; xPixel++) {
            if ((xPixel & 15) == (mapdata$mapinfo.step & 15) || flag) {
               flag = false;

               for (int zPixel = viewerZ - viewRadiusPixels - 1; zPixel < viewerZ + viewRadiusPixels; zPixel++) {
                  if (xPixel >= 0 && zPixel >= -1 && xPixel < 128 && zPixel < 128) {
                     int xPixelDist = xPixel - viewerX;
                     int zPixelDist = zPixel - viewerZ;
                     boolean shouldFuzz = xPixelDist * xPixelDist + zPixelDist * zPixelDist > (viewRadiusPixels - 2) * (viewRadiusPixels - 2);
                     int worldX = (centerX / blocksPerPixel + xPixel - 64) * blocksPerPixel;
                     int worldZ = (centerZ / blocksPerPixel + zPixel - 64) * blocksPerPixel;
                     Multiset<MapColor> multiset = HashMultiset.create();
                     LevelChunk chunk = level.getChunkAt(new BlockPos(worldX, 0, worldZ));
                     int brightness = 1;
                     if (!chunk.isEmpty()) {
                        int worldXRounded = worldX & 15;
                        int worldZRounded = worldZ & 15;
                        if (level.dimensionType().hasCeiling()) {
                           int l3 = worldX + worldZ * 231871;
                           l3 = l3 * l3 * 31287121 + l3 * 11;
                           if ((l3 >> 20 & 1) == 0) {
                              multiset.add(MapColor.DIRT, 10);
                           } else {
                              multiset.add(MapColor.STONE, 100);
                           }
                        } else {
                           int yCenter = ((TFMazeMapData)data).yCenter;
                           MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos(worldXRounded, yCenter, worldZRounded);
                           BlockState state = chunk.getBlockState(blockpos$mutableblockpos);
                           multiset.add(state.getMapColor(level, blockpos$mutableblockpos));
                           if (state.is(Blocks.STONE) || state.isAir()) {
                              for (int i = -3; i <= 3; i++) {
                                 blockpos$mutableblockpos.setY(yCenter + i);
                                 BlockState searchID = chunk.getBlockState(blockpos$mutableblockpos);
                                 if (searchID.is(Blocks.STONE) && !searchID.isAir()) {
                                    state = searchID;
                                    if (i > 0) {
                                       brightness = 2;
                                    }

                                    if (i < 0) {
                                       brightness = 0;
                                    }
                                    break;
                                 }
                              }
                           }

                           if (this.mapOres) {
                              OreMapOreColor color = (OreMapOreColor)state.getBlock().builtInRegistryHolder().getData(TFDataMaps.ORE_MAP_ORE_COLOR);
                              if (color != null) {
                                 multiset.add(color.color(), 1000);
                              } else if (!state.isAir() && state.is(net.neoforged.neoforge.common.Tags.Blocks.ORES)) {
                                 multiset.add(MapColor.COLOR_PINK, 1000);
                              }
                           }
                        }

                        MapColor mapcolor = (MapColor)Iterables.getFirst(Multisets.copyHighestCountFirst(multiset), MapColor.NONE);
                        if (zPixel >= 0
                           && xPixelDist * xPixelDist + zPixelDist * zPixelDist < viewRadiusPixels * viewRadiusPixels
                           && (!shouldFuzz || (xPixel + zPixel & 1) != 0)) {
                           byte b0 = data.colors[xPixel + zPixel * 128];
                           byte b1 = (byte)(mapcolor.id * 4 + brightness);
                           if (b0 != b1) {
                              data.setColor(xPixel, zPixel, b1);
                              data.setDirty();
                              flag = true;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
      if (!level.isClientSide()) {
         TFMazeMapData mapdata = this.getCustomMapData(stack, level);
         if (mapdata != null) {
            if (entity instanceof Player entityplayer) {
               mapdata.tickCarriedBy(entityplayer, stack);
               int yProximity = Mth.floor(entityplayer.getY() - mapdata.yCenter);
               if (yProximity < -3 || yProximity > 3) {
                  MapDecoration decoration = (MapDecoration)mapdata.decorations.get(entityplayer.getName().getString());
                  if (decoration != null) {
                     mapdata.decorations
                        .put(
                           entityplayer.getName().getString(),
                           new MapDecoration(MapDecorationTypes.PLAYER_OFF_MAP, decoration.x(), decoration.y(), decoration.rot(), Optional.empty())
                        );
                  }
               }
            }

            if (!mapdata.locked && (isSelected || entity instanceof Player player && player.getOffhandItem() == stack)) {
               this.update(level, entity, mapdata);
            }
         }
      }
   }

   public void onCraftedBy(ItemStack stack, Level level, Player player) {
   }

   @Nullable
   public Packet<?> getUpdatePacket(ItemStack stack, Level level, Player player) {
      MapId mapId = (MapId)stack.get(DataComponents.MAP_ID);
      TFMazeMapData mapdata = this.getCustomMapData(stack, level);
      return mapId != null && mapdata != null ? mapdata.getUpdatePacket(mapId, player) : null;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      MapId mapId = (MapId)stack.get(DataComponents.MAP_ID);
      if (mapId != null) {
         TFMazeMapData data = TFMazeMapData.getClientMagicMapData(getMapName(mapId.id()));
         if (flag.isAdvanced()) {
            if (data != null) {
               tooltip.add(Component.translatable("item.twilightforest.maze_map.y_level", new Object[]{data.yCenter}).withStyle(ChatFormatting.GRAY));
               tooltip.add(Component.translatable("filled_map.id", new Object[]{mapId.id()}).withStyle(ChatFormatting.GRAY));
               tooltip.add(Component.translatable("filled_map.scale", new Object[]{1 << data.scale}).withStyle(ChatFormatting.GRAY));
               tooltip.add(Component.translatable("filled_map.level", new Object[]{data.scale, 4}).withStyle(ChatFormatting.GRAY));
            } else {
               tooltip.add(Component.translatable("filled_map.unknown").withStyle(ChatFormatting.GRAY));
            }
         } else {
            tooltip.add(MapItem.getTooltipForId(mapId));
         }
      }
   }
}
