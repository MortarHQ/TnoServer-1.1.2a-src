package com.natamus.collective_common_fabric.functions;

import com.natamus.collective_common_fabric.data.GlobalVariables;
import net.minecraft.class_1917;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2680;

public class TileEntityFunctions {
   public static void updateTileEntity(class_1937 world, class_2338 pos) {
      class_2586 tileentity = world.method_8321(pos);
      updateTileEntity(world, pos, tileentity);
   }

   public static void updateTileEntity(class_1937 world, class_2338 pos, class_2586 tileentity) {
      class_2680 state = world.method_8320(pos);
      updateTileEntity(world, pos, state, tileentity);
   }

   public static void updateTileEntity(class_1937 world, class_2338 pos, class_2680 state, class_2586 tileentity) {
      world.method_16109(pos, state, state);
      world.method_8413(pos, state, state, 3);
      tileentity.method_5431();
   }

   public static void setMobSpawnerDelay(class_1917 spawner, int delay) {
      spawner.field_9154 = delay;
   }

   public static void resetMobSpawnerDelay(class_1917 spawner, int min, int max) {
      setMobSpawnerDelay(spawner, min + GlobalVariables.random.nextInt(max - min));
   }

   public static void resetMobSpawnerDelay(class_1917 spawner) {
      resetMobSpawnerDelay(spawner, 200, 800);
   }
}
