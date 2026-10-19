package mcjty.incontrol.compat;

import java.util.function.Function;
import mcjty.incontrol.InControl;
import mcjty.incontrol.tools.rules.IEventQuery;
import mcjty.lostcities.api.ILostChunkInfo;
import mcjty.lostcities.api.ILostCities;
import mcjty.lostcities.api.ILostCityInformation;
import mcjty.lostcities.api.ILostSphere;
import mcjty.lostcities.api.ILostChunkInfo.MultiBuildingInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.ModList;

public class LostCitySupport {
   private static boolean registered = false;
   private static ILostCities lostCities;

   public static void register() {
      if (ModList.get().isLoaded("lostcities")) {
         registerInternal();
      }
   }

   private static void registerInternal() {
      if (!registered) {
         registered = true;
         InterModComms.sendTo("lostcities", "getLostCities", LostCitySupport.GetLostCities::new);
         InControl.setup.getLogger().info("Enabling support for Lost Cities");
      }
   }

   private static <T> Level getWorld(IEventQuery<T> query, T event) {
      LevelAccessor world = query.getWorld(event);
      if (world.isClientSide()) {
         return null;
      }

      if (!(world instanceof Level w)) {
         if (!(world instanceof ServerLevelAccessor)) {
            throw new IllegalStateException("Bad world!");
         }

         w = ((ServerLevelAccessor)world).getLevel();
      }

      return w;
   }

   public static <T> boolean isCity(IEventQuery<T> query, T event) {
      Level w = getWorld(query, event);
      if (w == null) {
         return false;
      } else {
         ILostCityInformation info = lostCities.getLostInfo(w);
         if (info != null) {
            BlockPos pos = query.getPos(event);
            ILostChunkInfo chunkInfo = info.getChunkInfo(pos.getX() >> 4, pos.getZ() >> 4);
            return chunkInfo.isCity();
         } else {
            return false;
         }
      }
   }

   public static boolean isCity(Level world, BlockPos pos) {
      ILostCityInformation info = lostCities.getLostInfo(world);
      if (info != null) {
         ILostChunkInfo chunkInfo = info.getChunkInfo(pos.getX() >> 4, pos.getZ() >> 4);
         return chunkInfo.isCity();
      } else {
         return false;
      }
   }

   public static <T> boolean isStreet(IEventQuery<T> query, T event) {
      Level w = getWorld(query, event);
      if (w == null) {
         return false;
      }

      ILostCityInformation info = lostCities.getLostInfo(w);
      if (info == null) {
         return false;
      }

      BlockPos pos = query.getPos(event);
      ILostChunkInfo chunkInfo = info.getChunkInfo(pos.getX() >> 4, pos.getZ() >> 4);
      return chunkInfo.isCity() && chunkInfo.getBuildingType() == null;
   }

   public static boolean isStreet(Level world, BlockPos pos) {
      ILostCityInformation info = lostCities.getLostInfo(world);
      if (info == null) {
         return false;
      }

      ILostChunkInfo chunkInfo = info.getChunkInfo(pos.getX() >> 4, pos.getZ() >> 4);
      return chunkInfo.isCity() && chunkInfo.getBuildingType() == null;
   }

   public static <T> boolean inSphere(IEventQuery<T> query, T event) {
      Level w = getWorld(query, event);
      if (w == null) {
         return false;
      } else {
         ILostCityInformation info = lostCities.getLostInfo(w);
         if (info != null) {
            BlockPos pos = query.getPos(event);
            ILostSphere sphere = info.getSphere(pos.getX(), pos.getZ());
            return sphere != null;
         } else {
            return false;
         }
      }
   }

   public static boolean inSphere(Level world, BlockPos pos) {
      ILostCityInformation info = lostCities.getLostInfo(world);
      if (info != null) {
         ILostSphere sphere = info.getSphere(pos.getX(), pos.getZ());
         return sphere != null;
      } else {
         return false;
      }
   }

   public static <T> boolean isBuilding(IEventQuery<T> query, T event) {
      Level w = getWorld(query, event);
      if (w == null) {
         return false;
      }

      ILostCityInformation info = lostCities.getLostInfo(w);
      if (info == null) {
         return false;
      }

      BlockPos pos = query.getPos(event);
      ILostChunkInfo chunkInfo = info.getChunkInfo(pos.getX() >> 4, pos.getZ() >> 4);
      return chunkInfo.isCity() && chunkInfo.getBuildingType() != null;
   }

   public static boolean isBuilding(Level world, BlockPos pos) {
      ILostCityInformation info = lostCities.getLostInfo(world);
      if (info == null) {
         return false;
      }

      ILostChunkInfo chunkInfo = info.getChunkInfo(pos.getX() >> 4, pos.getZ() >> 4);
      return chunkInfo.isCity() && chunkInfo.getBuildingType() != null;
   }

   public static <T> boolean isMultiBuilding(IEventQuery<T> query, T event) {
      Level w = getWorld(query, event);
      if (w == null) {
         return false;
      }

      ILostCityInformation info = lostCities.getLostInfo(w);
      if (info != null) {
         BlockPos pos = query.getPos(event);
         ILostChunkInfo chunkInfo = info.getChunkInfo(pos.getX() >> 4, pos.getZ() >> 4);
         if (!chunkInfo.isCity()) {
            return false;
         }

         MultiBuildingInfo mi = chunkInfo.getMultiBuildingInfo();
         return mi != null;
      } else {
         return false;
      }
   }

   public static boolean isMultiBuilding(Level world, BlockPos pos) {
      ILostCityInformation info = lostCities.getLostInfo(world);
      if (info != null) {
         ILostChunkInfo chunkInfo = info.getChunkInfo(pos.getX() >> 4, pos.getZ() >> 4);
         if (!chunkInfo.isCity()) {
            return false;
         }

         MultiBuildingInfo mi = chunkInfo.getMultiBuildingInfo();
         return mi != null;
      } else {
         return false;
      }
   }

   public static <T> String getBuildingName(IEventQuery<T> query, T event) {
      Level w = getWorld(query, event);
      if (w == null) {
         return null;
      } else {
         ILostCityInformation info = lostCities.getLostInfo(w);
         if (info != null) {
            BlockPos pos = query.getPos(event);
            ILostChunkInfo chunkInfo = info.getChunkInfo(pos.getX() >> 4, pos.getZ() >> 4);
            return chunkInfo.getBuildingType();
         } else {
            return null;
         }
      }
   }

   public static String getBuildingName(Level world, BlockPos pos) {
      ILostCityInformation info = lostCities.getLostInfo(world);
      if (info != null) {
         ILostChunkInfo chunkInfo = info.getChunkInfo(pos.getX() >> 4, pos.getZ() >> 4);
         return chunkInfo.getBuildingType();
      } else {
         return null;
      }
   }

   public static <T> String getMultiBuildingName(IEventQuery<T> query, T event) {
      Level w = getWorld(query, event);
      if (w == null) {
         return null;
      } else {
         ILostCityInformation info = lostCities.getLostInfo(w);
         if (info != null) {
            BlockPos pos = query.getPos(event);
            ILostChunkInfo chunkInfo = info.getChunkInfo(pos.getX() >> 4, pos.getZ() >> 4);
            MultiBuildingInfo mi = chunkInfo.getMultiBuildingInfo();
            return mi != null ? mi.buildingType().toString() : null;
         } else {
            return null;
         }
      }
   }

   public static String getMultiBuildingName(Level world, BlockPos pos) {
      ILostCityInformation info = lostCities.getLostInfo(world);
      if (info != null) {
         ILostChunkInfo chunkInfo = info.getChunkInfo(pos.getX() >> 4, pos.getZ() >> 4);
         MultiBuildingInfo mi = chunkInfo.getMultiBuildingInfo();
         return mi != null ? mi.buildingType().toString() : null;
      } else {
         return null;
      }
   }

   public static class GetLostCities implements Function<ILostCities, Void> {
      public Void apply(ILostCities lc) {
         LostCitySupport.lostCities = lc;
         return null;
      }
   }
}
