package net.mebahelsapi.network;

import net.minecraft.class_2338;
import net.minecraft.class_2960;
import net.minecraft.class_8710;
import net.minecraft.class_9129;
import net.minecraft.class_9135;
import net.minecraft.class_9139;
import net.minecraft.class_8710.class_9154;

public record ChestOpenPayload(class_2338 pos, boolean open) implements class_8710 {
   public static final class_9154<ChestOpenPayload> ID = new class_9154(class_2960.method_60655("mebahelsapi", "sync_chest_open"));
   public static final class_9139<class_9129, ChestOpenPayload> CODEC = class_9139.method_56435(
      class_2338.field_48404, ChestOpenPayload::pos, class_9135.field_48547, ChestOpenPayload::open, ChestOpenPayload::new
   );

   public class_9154<? extends class_8710> method_56479() {
      return ID;
   }
}
