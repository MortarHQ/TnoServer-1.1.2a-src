package cc.sighs.oelib.data.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;

public interface DataValidator<T> {
   DataValidator.ValidationResult validate(T var1, ResourceLocation var2);

   class NoValidator implements DataValidator<Object> {
      @Override
      public DataValidator.ValidationResult validate(Object data, ResourceLocation source) {
         return DataValidator.ValidationResult.success();
      }
   }

   interface ServerContextAware<T> extends DataValidator<T> {
      DataValidator.ValidationResult validateWithContext(T var1, ResourceLocation var2, MinecraftServer var3);

      @Override
      default DataValidator.ValidationResult validate(T data, ResourceLocation source) {
         return this.validateWithContext(data, source, null);
      }
   }

   record ValidationResult(boolean valid, String message, boolean deferrable) {
      public static DataValidator.ValidationResult success() {
         return new DataValidator.ValidationResult(true, null, false);
      }

      public static DataValidator.ValidationResult failure(String message) {
         return new DataValidator.ValidationResult(false, message, false);
      }

      public static DataValidator.ValidationResult deferred(String message) {
         return new DataValidator.ValidationResult(true, message, true);
      }
   }
}
