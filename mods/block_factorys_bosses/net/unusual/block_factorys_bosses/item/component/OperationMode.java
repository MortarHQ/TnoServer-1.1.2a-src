package net.unusual.block_factorys_bosses.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.unusual.block_factorys_bosses.init.BossesRiseDataComponents;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record OperationMode(String mode) {
   public static final Codec<OperationMode> CODEC = RecordCodecBuilder.create(
      operationMode -> operationMode.group(Codec.STRING.fieldOf("mode").forGetter(OperationMode::mode)).apply(operationMode, OperationMode::new)
   );
   public static final StreamCodec<ByteBuf, OperationMode> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.STRING_UTF8, OperationMode::mode, OperationMode::new
   );

   public static boolean isMode(ItemStack stack, String mode) {
      return mode.equals(get(stack));
   }

   @Nullable
   public static String get(ItemStack stack) {
      OperationMode operationMode = (OperationMode)stack.get(BossesRiseDataComponents.OPERATION_MODE);
      return operationMode == null ? null : operationMode.mode();
   }

   public static void set(ItemStack stack, @Nullable String mode) {
      if (mode == null) {
         stack.remove(BossesRiseDataComponents.OPERATION_MODE);
      } else {
         stack.set(BossesRiseDataComponents.OPERATION_MODE, new OperationMode(mode));
      }
   }
}
