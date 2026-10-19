package xaero.map.file.export;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public enum PNGExportResultType {
   NOT_PREPARED(Component.translatable("gui.xaero_png_result_not_prepared").withStyle(s -> s.withColor(ChatFormatting.RED)), false),
   EMPTY(Component.translatable("gui.xaero_png_result_empty").withStyle(s -> s.withColor(ChatFormatting.RED)), false),
   TOO_BIG(Component.translatable("gui.xaero_png_result_too_big").withStyle(s -> s.withColor(ChatFormatting.RED)), false),
   IMAGE_TOO_BIG(Component.translatable("gui.xaero_png_result_image_too_big").withStyle(s -> s.withColor(ChatFormatting.RED)), false),
   OUT_OF_MEMORY(Component.translatable("gui.xaero_png_result_out_of_memory").withStyle(s -> s.withColor(ChatFormatting.RED)), false),
   BAD_FBO(Component.translatable("gui.xaero_png_result_bad_fbo").withStyle(s -> s.withColor(ChatFormatting.RED)), false),
   IO_EXCEPTION(Component.translatable("gui.xaero_png_result_io_exception").withStyle(s -> s.withColor(ChatFormatting.RED)), false),
   SUCCESS(Component.translatable("gui.xaero_png_result_success").withStyle(s -> s.withColor(ChatFormatting.GREEN)), true);

   private final Component message;
   private final boolean success;

   PNGExportResultType(Component message, boolean success) {
      this.message = message;
      this.success = success;
   }

   public Component getMessage() {
      return this.message;
   }

   public boolean isSuccess() {
      return this.success;
   }
}
