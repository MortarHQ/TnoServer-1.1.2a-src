package mebahelcreaturesdwarven.block.block.custom;

import com.mojang.serialization.MapCodec;
import mebahelcreaturesdwarven.block.entity.custom.DwemerSpiderBlockEntity;
import mebahelcreaturesdwarven.entity.ModEntities;
import mebahelcreaturesdwarven.entity.custom.DwemerSpiderEntity;
import mebahelcreaturesdwarven.item.ModItems;
import net.minecraft.class_1269;
import net.minecraft.class_1657;
import net.minecraft.class_1750;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
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
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2753;
import net.minecraft.class_2758;
import net.minecraft.class_2769;
import net.minecraft.class_3417;
import net.minecraft.class_3419;
import net.minecraft.class_3965;
import net.minecraft.class_5819;
import net.minecraft.class_2689.class_2690;
import net.minecraft.class_4970.class_2251;
import org.jetbrains.annotations.Nullable;

public class DwemerSpiderBlock extends class_2318 implements class_2343 {
   public static final class_2758 STATE = class_2758.method_11867("state", 0, 2);
   public static final class_2753 FACING = class_2741.field_12525;

   public DwemerSpiderBlock(class_2251 settings) {
      super(settings);
      this.method_9590((class_2680)((class_2680)this.field_10647.method_11664()).method_11657(FACING, class_2350.field_11043));
      this.method_9590((class_2680)((class_2680)this.field_10647.method_11664()).method_11657(STATE, 0));
   }

   protected void method_9515(class_2690<class_2248, class_2680> builder) {
      builder.method_11667(new class_2769[]{FACING, STATE});
   }

   protected MapCodec<? extends class_2318> method_53969() {
      return MapCodec.unit(this);
   }

   protected class_1269 method_55766(class_2680 state, class_1937 world, class_2338 pos, class_1657 player, class_3965 hit) {
      if (world.field_9236) {
         world.method_43128(player, pos.method_10263(), pos.method_10264(), pos.method_10260(), class_3417.field_26960, class_3419.field_15245, 0.5F, 1.0F);
         return class_1269.field_5812;
      }

      int currentState = (Integer)state.method_11654(STATE);
      class_5819 random = world.method_8409();
      if (currentState == 0 && random.method_43048(3) == 0) {
         world.method_22352(pos, false);
         DwemerSpiderEntity spider = (DwemerSpiderEntity)ModEntities.DWARVEN_SPIDER.method_5883(world);
         if (spider != null) {
            class_2350 facing = (class_2350)state.method_11654(FACING);

            float yaw = switch (facing) {
               case field_11043 -> 180.0F;
               case field_11035 -> 0.0F;
               case field_11039 -> 90.0F;
               case field_11034 -> -90.0F;
               default -> 0.0F;
            };
            double x = pos.method_10263() + 0.5;
            double y = pos.method_10264();
            double z = pos.method_10260() + 0.5;
            spider.method_5808(x, y, z, yaw, 0.0F);
            spider.method_36456(yaw);
            spider.method_5847(yaw);
            spider.method_5636(yaw);
            spider.field_5982 = yaw;
            spider.field_6220 = yaw;
            spider.field_6259 = yaw;
            world.method_8649(spider);
         }

         return class_1269.field_21466;
      } else {
         int roll = random.method_43048(9);

         class_1799 drop = switch (roll) {
            case 0 -> new class_1799(class_1802.field_33400);
            case 1 -> new class_1799(class_1802.field_33402);
            case 2 -> new class_1799(class_1802.field_8477);
            case 3 -> new class_1799(class_1802.field_8687);
            default -> new class_1799(ModItems.DWEMER_METAL_SCRAP);
         };
         method_9577(world, pos, drop);
         if (currentState < 2) {
            world.method_8652(pos, (class_2680)state.method_11657(STATE, currentState + 1), 3);
         } else {
            world.method_22352(pos, false);
         }

         return class_1269.field_21466;
      }
   }

   @Nullable
   public class_2586 method_10123(class_2338 pos, class_2680 state) {
      return new DwemerSpiderBlockEntity(pos, state);
   }

   public class_2464 method_9604(class_2680 state) {
      return class_2464.field_11455;
   }

   public class_2680 method_9605(class_1750 ctx) {
      class_2350 direction = ctx.method_8042().method_10153();
      return (class_2680)this.method_9564().method_11657(FACING, direction);
   }

   public class_2680 method_9598(class_2680 state, class_2470 rotation) {
      return (class_2680)state.method_11657(FACING, rotation.method_10503((class_2350)state.method_11654(FACING)));
   }

   public class_2680 method_9569(class_2680 state, class_2415 mirror) {
      return this.method_9598(state, mirror.method_10345((class_2350)state.method_11654(FACING)));
   }
}
