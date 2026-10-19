package net.mebahelsapi.network;

import net.minecraft.class_2338;
import net.minecraft.class_2960;
import net.minecraft.class_8710;
import net.minecraft.class_9129;
import net.minecraft.class_9135;
import net.minecraft.class_9139;
import net.minecraft.class_8710.class_9154;

public record UpdateChestPayload(class_2338 pos, boolean shouldDoSpawnAnimation) implements class_8710 {
   public static final class_9154<UpdateChestPayload> ID = new class_9154(class_2960.method_60655("mebahelsapi", "update_chest"));
   public static final class_9139<class_9129, UpdateChestPayload> CODEC = class_9139.method_56435(
      class_2338.field_48404, UpdateChestPayload::pos, class_9135.field_48547, UpdateChestPayload::shouldDoSpawnAnimation, UpdateChestPayload::new
   );

   public class_9154<? extends class_8710> method_56479() {
      return ID;
   }
}
