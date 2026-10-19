package mebahelcreaturesdwarven.block.block.custom;

import net.minecraft.class_1309;
import net.minecraft.class_1750;
import net.minecraft.class_1799;
import net.minecraft.class_1922;
import net.minecraft.class_1936;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2415;
import net.minecraft.class_2470;
import net.minecraft.class_259;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2746;
import net.minecraft.class_2753;
import net.minecraft.class_2769;
import net.minecraft.class_3726;
import net.minecraft.class_2689.class_2690;
import net.minecraft.class_4970.class_2251;
import org.jetbrains.annotations.Nullable;

public class DwarvenMetalPipeBlock extends class_2248 {
   public static final class_2746 CONNECTED_SIDE = class_2746.method_11825("connected_side");
   public static final class_2753 FACING = class_2741.field_12525;
   public static final class_2746 FRONT_CONNECTED = class_2746.method_11825("front_connected");
   public static final class_2746 BACK_CONNECTED = class_2746.method_11825("back_connected");
   public static final class_2746 SMOOTH = class_2746.method_11825("smooth");
   private static final class_265 NORTH_SHAPE = class_259.method_1081(0.125, 0.125, 0.1875, 0.875, 0.875, 1.0);
   private static final class_265 NORTH_SMOOTH = class_259.method_1081(0.125, 0.125, 0.0, 0.875, 0.875, 1.0);
   private static final class_265 NORTH_BACK_SMOOTH_SIDE = class_259.method_1081(0.125, 0.125, 0.125, 0.875, 0.875, 1.0);
   private static final class_265 NORTH_BACK = class_259.method_1081(0.125, 0.125, 0.0, 0.875, 0.875, 1.125);
   private static final class_265 NORTH_BACK_SMOOTH_TOP = class_259.method_1081(0.125, 0.125, 0.125, 0.875, 0.875, 1.125);
   private static final class_265 NORTH_BACK_SMOOTH = class_259.method_1081(0.125, 0.125, 0.0, 0.875, 0.875, 1.125);
   private static final class_265 NORTH_FRONT = class_259.method_1081(0.125, 0.125, -0.125, 0.875, 0.875, 1.0);
   private static final class_265 UP_BACK_SMOOTH_TOP = class_259.method_1081(0.125, -0.125, 0.125, 0.875, 0.875, 0.875);
   private static final class_265 UP_BACK_SMOOTH_SIDE = class_259.method_1081(0.125, 0.0, 0.125, 0.875, 0.875, 0.875);
   private static final class_265 UP_BACK = class_259.method_1081(0.125, -0.125, 0.125, 0.875, 0.875, 0.875);
   private static final class_265 UP_BACK_SMOOTH = class_259.method_1081(0.125, -0.125, 0.125, 0.875, 1.0, 0.875);
   private static final class_265 UP_SHAPE = class_259.method_1081(0.125, 0.0, 0.125, 0.875, 0.875, 0.875);
   private static final class_265 DOWN_DOUBLE = class_259.method_1081(0.125, -0.125, 0.125, 0.875, 1.125, 0.875);
   private static final class_265 SOUTH_SHAPE = class_259.method_1081(0.125, 0.125, 0.0, 0.875, 0.875, 0.875);
   private static final class_265 SOUTH_BACK = class_259.method_1081(0.125, 0.125, -0.125, 0.875, 0.875, 0.875);
   private static final class_265 SOUTH_BACK_SMOOTH = class_259.method_1081(0.125, 0.125, -0.125, 0.875, 0.875, 1.0);
   private static final class_265 SOUTH_BACK_SMOOTH_SIDE = class_259.method_1081(0.125, 0.125, 0.0, 0.875, 0.875, 0.875);
   private static final class_265 SOUTH_BACK_SMOOTH_TOP = class_259.method_1081(0.125, 0.125, -0.125, 0.875, 0.875, 0.875);
   private static final class_265 EAST_SHAPE = class_259.method_1081(0.0, 0.125, 0.125, 0.875, 0.875, 0.875);
   private static final class_265 EAST_BACK = class_259.method_1081(-0.125, 0.125, 0.125, 0.875, 0.875, 0.875);
   private static final class_265 EAST_BACK_SMOOTH = class_259.method_1081(-0.125, 0.125, 0.125, 1.0, 0.875, 0.875);
   private static final class_265 EAST_SMOOTH = class_259.method_1081(0.0, 0.125, 0.125, 1.0, 0.875, 0.875);
   private static final class_265 EAST_BACK_SMOOTH_SIDE = class_259.method_1081(0.0, 0.125, 0.125, 0.875, 0.875, 0.875);
   private static final class_265 EAST_DOUBLE = class_259.method_1081(-0.125, 0.125, 0.125, 1.125, 0.875, 0.875);
   private static final class_265 EAST_FRONT = class_259.method_1081(0.0, 0.125, 0.125, 1.125, 0.875, 0.875);
   private static final class_265 WEST_BACK_SMOOTH_SIDE = class_259.method_1081(0.125, 0.125, 0.125, 1.0, 0.875, 0.875);
   private static final class_265 WEST_BACK_SMOOTH = class_259.method_1081(0.0, 0.125, 0.125, 1.125, 0.875, 0.875);
   private static final class_265 DOWN_SHAPE = class_259.method_1081(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);
   private static final class_265 DOWN_BACK = class_259.method_1081(0.125, 0.0, 0.125, 0.875, 1.125, 0.875);
   private static final class_265 DOWN_BACK_SMOOTH = class_259.method_1081(0.125, 0.0, 0.125, 0.875, 1.125, 0.875);
   private static final class_265 DOWN_BACK_SMOOTH_TOP = class_259.method_1081(0.125, 0.0, 0.125, 0.875, 0.875, 0.875);
   private static final class_265 DOWN_BACK_SMOOTH_SIDE = class_259.method_1081(0.125, 0.125, 0.125, 0.875, 1.0, 0.875);
   private static final class_265 WEST_SHAPE = class_259.method_1081(0.125, 0.125, 0.125, 1.0, 0.875, 0.875);
   private static final class_265 WEST_BACK = class_259.method_1081(0.125, 0.125, 0.125, 1.125, 0.875, 0.875);
   private static final class_265 UP_FRONT = class_259.method_1081(0.125, 0.125, 0.125, 0.875, 1.0, 0.875);
   private static final class_265 DOWN_FRONT = class_259.method_1081(0.125, -0.125, 0.125, 0.875, 0.875, 0.875);
   private static final class_265 SOUTH_FRONT = class_259.method_1081(0.125, 0.125, 0.125, 0.875, 0.875, 1.125);
   private static final class_265 WEST_FRONT = class_259.method_1081(-0.125, 0.125, 0.125, 0.875, 0.875, 0.875);
   private static final class_265 NORTH_DOUBLE = class_259.method_1081(0.125, 0.125, -0.125, 0.875, 0.875, 1.125);
   private static final class_265 WEST_BACK_SMOOTH_TOP = class_259.method_1081(0.125, 0.125, 0.125, 1.125, 0.875, 0.875);
   private static final class_265 DOWN_SMOOTH = class_259.method_1081(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);
   private static final class_265 EAST_BACK_SMOOTH_TOP = class_259.method_1081(-0.125, 0.125, 0.125, 0.875, 0.875, 0.875);

   public DwarvenMetalPipeBlock(class_2251 settings) {
      super(settings);
      this.method_9590(
         (class_2680)((class_2680)((class_2680)((class_2680)((class_2680)this.field_10647.method_11664()).method_11657(FACING, class_2350.field_11043))
                  .method_11657(FRONT_CONNECTED, false))
               .method_11657(BACK_CONNECTED, false))
            .method_11657(SMOOTH, false)
      );
   }

   protected void method_9515(class_2690<class_2248, class_2680> builder) {
      builder.method_11667(new class_2769[]{FACING, FRONT_CONNECTED, BACK_CONNECTED, SMOOTH, CONNECTED_SIDE});
   }

   public class_2680 method_9605(class_1750 ctx) {
      class_2350 dir = ctx.method_8038();
      class_2338 pos = ctx.method_8037();
      class_1936 world = ctx.method_8045();
      return (class_2680)((class_2680)((class_2680)((class_2680)((class_2680)this.method_9564().method_11657(FACING, dir))
                  .method_11657(FRONT_CONNECTED, this.canConnectFront(world, pos, dir)))
               .method_11657(BACK_CONNECTED, this.canConnectBack(world, pos, dir)))
            .method_11657(SMOOTH, this.isSmooth(world, pos, dir)))
         .method_11657(CONNECTED_SIDE, this.hasSideConnection(world, pos, dir));
   }

   public void method_9567(class_1937 world, class_2338 pos, class_2680 state, @Nullable class_1309 placer, class_1799 itemStack) {
      super.method_9567(world, pos, state, placer, itemStack);

      for (class_2350 dir : class_2350.values()) {
         class_2338 neighborPos = pos.method_10093(dir);
         class_2680 neighborState = world.method_8320(neighborPos);
         if (neighborState.method_26204() instanceof DwarvenMetalPipeBlock) {
            class_2680 updated = (class_2680)((class_2680)((class_2680)((class_2680)neighborState.method_11657(
                        FRONT_CONNECTED, this.canConnectFront(world, neighborPos, (class_2350)neighborState.method_11654(FACING))
                     ))
                     .method_11657(BACK_CONNECTED, this.canConnectBack(world, neighborPos, (class_2350)neighborState.method_11654(FACING))))
                  .method_11657(SMOOTH, this.isSmooth(world, neighborPos, (class_2350)neighborState.method_11654(FACING))))
               .method_11657(CONNECTED_SIDE, this.hasSideConnection(world, neighborPos, (class_2350)neighborState.method_11654(FACING)));
            world.method_8501(neighborPos, updated);
         }
      }
   }

   public class_2680 method_9559(class_2680 state, class_2350 dir, class_2680 neighborState, class_1936 world, class_2338 pos, class_2338 neighborPos) {
      class_2350 facing = (class_2350)state.method_11654(FACING);
      class_2680 newState = (class_2680)((class_2680)((class_2680)((class_2680)state.method_11657(FRONT_CONNECTED, this.canConnectFront(world, pos, facing)))
               .method_11657(BACK_CONNECTED, this.canConnectBack(world, pos, facing)))
            .method_11657(SMOOTH, this.isSmooth(world, pos, facing)))
         .method_11657(CONNECTED_SIDE, this.hasSideConnection(world, pos, facing));
      if (!world.method_8608()) {
         world.method_39279(pos, this, 1);
      }

      return newState;
   }

   private boolean canConnectFront(class_1922 world, class_2338 pos, class_2350 dir) {
      class_2680 target = world.method_8320(pos.method_10093(dir));
      return (target.method_26204() instanceof DwarvenMetalPipeBlock || target.method_26204() instanceof DwemerMetalPipeGearBlock)
         && target.method_11654(FACING) != dir
         && target.method_11654(FACING) != dir.method_10153();
   }

   private boolean canConnectBack(class_1922 world, class_2338 pos, class_2350 dir) {
      class_2680 target = world.method_8320(pos.method_10093(dir.method_10153()));
      return (target.method_26204() instanceof DwarvenMetalPipeBlock || target.method_26204() instanceof DwemerMetalPipeGearBlock)
         && target.method_11654(FACING) != dir
         && target.method_11654(FACING) != dir.method_10153();
   }

   private boolean isSmooth(class_1922 world, class_2338 pos, class_2350 dir) {
      class_2680 target = world.method_8320(pos.method_10093(dir));
      return (target.method_26204() instanceof DwarvenMetalPipeBlock || target.method_26204() instanceof DwemerMetalPipeGearBlock)
         && target.method_11654(FACING) == dir
         && !this.canConnectFront(world, pos, dir);
   }

   private boolean hasSideConnection(class_1936 world, class_2338 pos, class_2350 facing) {
      for (class_2350 dir : class_2350.values()) {
         if (dir.method_10166() != facing.method_10166()) {
            class_2338 neighborPos = pos.method_10093(dir);
            class_2680 neighborState = world.method_8320(neighborPos);
            if (neighborState.method_26204() instanceof DwarvenMetalPipeBlock || neighborState.method_26204() instanceof DwemerMetalPipeGearBlock) {
               class_2350 neighborFacing = (class_2350)neighborState.method_11654(FACING);
               if (neighborFacing.method_10166() != facing.method_10166()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public class_2680 method_9598(class_2680 state, class_2470 rotation) {
      return (class_2680)state.method_11657(FACING, rotation.method_10503((class_2350)state.method_11654(FACING)));
   }

   public class_2680 method_9569(class_2680 state, class_2415 mirror) {
      return this.method_9598(state, mirror.method_10345((class_2350)state.method_11654(FACING)));
   }

   public class_265 method_9530(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
      class_2350 facing = (class_2350)state.method_11654(FACING);
      boolean front = (Boolean)state.method_11654(FRONT_CONNECTED);
      boolean back = (Boolean)state.method_11654(BACK_CONNECTED);
      boolean smooth = (Boolean)state.method_11654(SMOOTH);
      boolean connected_side = (Boolean)state.method_11654(CONNECTED_SIDE);
      if (connected_side && !front && !back && !smooth) {
         return switch (facing) {
            case field_11033 -> DOWN_BACK_SMOOTH_SIDE;
            case field_11036 -> UP_BACK_SMOOTH_SIDE;
            case field_11043 -> NORTH_BACK_SMOOTH_SIDE;
            case field_11035 -> SOUTH_BACK_SMOOTH_SIDE;
            case field_11034 -> EAST_BACK_SMOOTH_SIDE;
            case field_11039 -> WEST_BACK_SMOOTH_SIDE;
            default -> throw new IncompatibleClassChangeError();
         };
      } else if (connected_side && back && !front && !smooth) {
         return switch (facing) {
            case field_11033 -> DOWN_BACK_SMOOTH_TOP;
            case field_11036 -> UP_BACK_SMOOTH_TOP;
            case field_11043 -> NORTH_BACK_SMOOTH_TOP;
            case field_11035 -> SOUTH_BACK_SMOOTH_TOP;
            case field_11034 -> EAST_BACK_SMOOTH_TOP;
            case field_11039 -> WEST_BACK_SMOOTH_TOP;
            default -> throw new IncompatibleClassChangeError();
         };
      } else if (smooth && back) {
         return switch (facing) {
            case field_11033 -> DOWN_BACK_SMOOTH;
            case field_11036 -> UP_BACK_SMOOTH;
            case field_11043 -> NORTH_BACK_SMOOTH;
            case field_11035 -> SOUTH_BACK_SMOOTH;
            case field_11034 -> EAST_BACK_SMOOTH;
            case field_11039 -> WEST_BACK_SMOOTH;
            default -> throw new IncompatibleClassChangeError();
         };
      } else if (smooth) {
         return switch (facing) {
            case field_11033, field_11036 -> DOWN_SMOOTH;
            case field_11043, field_11035 -> NORTH_SMOOTH;
            case field_11034, field_11039 -> EAST_SMOOTH;
            default -> throw new IncompatibleClassChangeError();
         };
      } else if (front && back) {
         return switch (facing) {
            case field_11033, field_11036 -> DOWN_DOUBLE;
            case field_11043, field_11035 -> NORTH_DOUBLE;
            case field_11034, field_11039 -> EAST_DOUBLE;
            default -> throw new IncompatibleClassChangeError();
         };
      } else if (front) {
         return switch (facing) {
            case field_11033 -> DOWN_FRONT;
            case field_11036 -> UP_FRONT;
            case field_11043 -> NORTH_FRONT;
            case field_11035 -> SOUTH_FRONT;
            case field_11034 -> EAST_FRONT;
            case field_11039 -> WEST_FRONT;
            default -> throw new IncompatibleClassChangeError();
         };
      } else if (back) {
         return switch (facing) {
            case field_11033 -> DOWN_BACK;
            case field_11036 -> UP_BACK;
            case field_11043 -> NORTH_BACK;
            case field_11035 -> SOUTH_BACK;
            case field_11034 -> EAST_BACK;
            case field_11039 -> WEST_BACK;
            default -> throw new IncompatibleClassChangeError();
         };
      } else {
         return switch (facing) {
            case field_11033 -> DOWN_SHAPE;
            case field_11036 -> UP_SHAPE;
            case field_11043 -> NORTH_SHAPE;
            case field_11035 -> SOUTH_SHAPE;
            case field_11034 -> EAST_SHAPE;
            case field_11039 -> WEST_SHAPE;
            default -> throw new IncompatibleClassChangeError();
         };
      }
   }
}
