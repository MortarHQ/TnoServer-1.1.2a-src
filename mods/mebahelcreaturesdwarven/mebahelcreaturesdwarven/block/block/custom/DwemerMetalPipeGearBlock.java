package mebahelcreaturesdwarven.block.block.custom;

import com.mojang.serialization.MapCodec;
import java.lang.reflect.Method;
import mebahelcreaturesdwarven.block.entity.ModBlockEntities;
import mebahelcreaturesdwarven.block.entity.custom.DwemerMetalPipeGearBlockEntity;
import mebahelcreaturesdwarven.sound.ModSounds;
import mebahelcreaturesdwarven.util.ClientSoundBridge;
import net.minecraft.class_1750;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2318;
import net.minecraft.class_2338;
import net.minecraft.class_2343;
import net.minecraft.class_2350;
import net.minecraft.class_2415;
import net.minecraft.class_2464;
import net.minecraft.class_2470;
import net.minecraft.class_2586;
import net.minecraft.class_259;
import net.minecraft.class_2591;
import net.minecraft.class_265;
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2769;
import net.minecraft.class_2960;
import net.minecraft.class_3726;
import net.minecraft.class_5558;
import net.minecraft.class_2689.class_2690;
import net.minecraft.class_4970.class_2251;

public class DwemerMetalPipeGearBlock extends class_2318 implements class_2343 {
   private static boolean started = false;

   public DwemerMetalPipeGearBlock(class_2251 settings) {
      super(settings);
      this.method_9590((class_2680)((class_2680)this.field_10647.method_11664()).method_11657(field_10927, class_2350.field_11043));
   }

   protected MapCodec<? extends class_2318> method_53969() {
      return MapCodec.unit(this);
   }

   protected void method_9515(class_2690<class_2248, class_2680> builder) {
      builder.method_11667(new class_2769[]{field_10927});
   }

   public class_2586 method_10123(class_2338 pos, class_2680 state) {
      return new DwemerMetalPipeGearBlockEntity(pos, state);
   }

   public class_265 method_9530(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
      class_2350 facing = (class_2350)state.method_11654(class_2741.field_12525);

      return switch (facing) {
         case field_11043, field_11035 -> class_259.method_1081(0.125, 0.125, 0.0, 0.875, 0.875, 1.0);
         case field_11039, field_11034 -> class_259.method_1081(0.0, 0.125, 0.125, 1.0, 0.875, 0.875);
         case field_11036, field_11033 -> class_259.method_1081(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);
         default -> throw new IncompatibleClassChangeError();
      };
   }

   public class_2680 method_9605(class_1750 context) {
      return (class_2680)this.method_9564().method_11657(field_10927, context.method_8042().method_10153());
   }

   public class_2464 method_9604(class_2680 state) {
      return class_2464.field_11455;
   }

   public <T extends class_2586> class_5558<T> method_31645(class_1937 world, class_2680 state, class_2591<T> type) {
      return world.field_9236 && type == ModBlockEntities.DWARVEN_METAL_PIPE_GEAR ? (w, pos, st, be) -> {
         if (be instanceof DwemerMetalPipeGearBlockEntity gearBe) {
            DwemerMetalPipeGearBlockEntity.clientTick(w, pos, st, gearBe);
         }
      } : null;
   }

   public void method_9536(class_2680 state, class_1937 world, class_2338 pos, class_2680 newState, boolean moved) {
      if (world.field_9236 && state.method_26204() != newState.method_26204()) {
         ClientSoundBridge.stopLoopingBlockSound(world.method_27983(), pos);
      }

      super.method_9536(state, world, pos, newState, moved);
   }

   private static void invokeClientStartSound(class_2338 pos) {
      if (!started) {
         started = true;

         try {
            Class<?> clazz = Class.forName("mebahelcreaturesdwarven.client.sound.DwemerLoopingBlockSounds");
            Method m = clazz.getMethod("start", class_2338.class, class_2960.class, float.class, float.class);
            m.invoke(null, pos, ModSounds.DWARVEN_GEAR_MULTIPLE.method_14833(), 0.3F, 1.15F);
         } catch (Throwable var3) {
         }
      }
   }

   public class_2680 method_9598(class_2680 state, class_2470 rotation) {
      return (class_2680)state.method_11657(field_10927, rotation.method_10503((class_2350)state.method_11654(field_10927)));
   }

   public class_2680 method_9569(class_2680 state, class_2415 mirror) {
      return this.method_9598(state, mirror.method_10345((class_2350)state.method_11654(field_10927)));
   }
}
