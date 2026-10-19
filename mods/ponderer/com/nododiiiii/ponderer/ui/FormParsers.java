package com.nododiiiii.ponderer.ui;

import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;

public final class FormParsers {
   private FormParsers() {
   }

   public static FormParsers.ParseResult<String> requiredText(String raw, String errorMessage) {
      String value = raw == null ? "" : raw.trim();
      return value.isEmpty() ? error(errorMessage) : success(value);
   }

   public static FormParsers.ParseResult<Integer> requiredInt(String raw, String fieldName) {
      String value = raw == null ? "" : raw.trim();
      if (value.isEmpty()) {
         return error(UIText.of("ponderer.ui.error.required_field", fieldName));
      }

      try {
         return success(Integer.parseInt(value));
      } catch (NumberFormatException e) {
         return error(UIText.of("ponderer.ui.error.invalid_integer", fieldName));
      }
   }

   public static FormParsers.ParseResult<Double> requiredDouble(String raw, String fieldName) {
      String value = raw == null ? "" : raw.trim();
      if (value.isEmpty()) {
         return error(UIText.of("ponderer.ui.error.required_field", fieldName));
      }

      try {
         return success(Double.parseDouble(value));
      } catch (NumberFormatException e) {
         return error(UIText.of("ponderer.ui.error.invalid_number", fieldName));
      }
   }

   public static FormParsers.ParseResult<FormParsers.Int3> requiredInt3(String x, String y, String z) {
      FormParsers.ParseResult<Integer> px = requiredInt(x, "X");
      if (px.failed()) {
         return error(px.errorMessage());
      }

      FormParsers.ParseResult<Integer> py = requiredInt(y, "Y");
      if (py.failed()) {
         return error(py.errorMessage());
      }

      FormParsers.ParseResult<Integer> pz = requiredInt(z, "Z");
      return pz.failed() ? error(pz.errorMessage()) : success(new FormParsers.Int3(px.value(), py.value(), pz.value()));
   }

   public static FormParsers.ParseResult<FormParsers.Double3> requiredDouble3(String x, String y, String z) {
      FormParsers.ParseResult<Double> px = requiredDouble(x, "X");
      if (px.failed()) {
         return error(px.errorMessage());
      }

      FormParsers.ParseResult<Double> py = requiredDouble(y, "Y");
      if (py.failed()) {
         return error(py.errorMessage());
      }

      FormParsers.ParseResult<Double> pz = requiredDouble(z, "Z");
      return pz.failed() ? error(pz.errorMessage()) : success(new FormParsers.Double3(px.value(), py.value(), pz.value()));
   }

   public static FormParsers.ParseResult<FormParsers.IntRange> intRange(StepXyzFieldHandle from, StepXyzFieldHandle to, String partialErrorMessage) {
      FormParsers.ParseResult<FormParsers.Int3> fromResult = requiredInt3(from.x(), from.y(), from.z());
      if (fromResult.failed()) {
         return error(fromResult.errorMessage());
      }

      FormParsers.ParseResult<FormParsers.Int3> toResult = optionalInt3(to.x(), to.y(), to.z(), partialErrorMessage);
      return toResult.failed() ? error(toResult.errorMessage()) : success(new FormParsers.IntRange(fromResult.value(), toResult.value()));
   }

   public static FormParsers.ParseResult<FormParsers.DoubleRange> doubleRange(
      String fromX, String fromY, String fromZ, String toX, String toY, String toZ, String partialErrorMessage
   ) {
      FormParsers.ParseResult<FormParsers.Double3> fromResult = requiredDouble3(fromX, fromY, fromZ);
      if (fromResult.failed()) {
         return error(fromResult.errorMessage());
      }

      FormParsers.ParseResult<FormParsers.Double3> toResult = optionalDouble3(toX, toY, toZ, partialErrorMessage);
      return toResult.failed() ? error(toResult.errorMessage()) : success(new FormParsers.DoubleRange(fromResult.value(), toResult.value()));
   }

   public static FormParsers.ParseResult<String> registryId(
      String raw, String requiredMessage, String invalidMessage, Function<String, String> unknownMessage, Registry<?> registry
   ) {
      FormParsers.ParseResult<String> text = requiredText(raw, requiredMessage);
      if (text.failed()) {
         return text;
      } else {
         ResourceLocation location = ResourceLocation.tryParse(text.value());
         if (location == null) {
            return error(invalidMessage);
         } else {
            return !registry.containsKey(location) ? error(unknownMessage.apply(text.value())) : success(text.value());
         }
      }
   }

   public static FormParsers.ParseResult<String> optionalNbt(String raw, String invalidMessage) {
      String value = raw == null ? "" : raw.trim();
      if (value.isEmpty()) {
         return success(null);
      }

      try {
         TagParser.parseTag(value);
         return success(value);
      } catch (Exception e) {
         return error(invalidMessage);
      }
   }

   @Nullable
   public static FormParsers.Int3 parseInt3(String raw) {
      try {
         String[] parts = raw.split(",");
         return parts.length < 3
            ? null
            : new FormParsers.Int3(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()), Integer.parseInt(parts[2].trim()));
      } catch (Exception ignored) {
         return null;
      }
   }

   @Nullable
   public static FormParsers.Double3 parseDouble3(String raw) {
      try {
         String[] parts = raw.split(",");
         return parts.length < 3
            ? null
            : new FormParsers.Double3(Double.parseDouble(parts[0].trim()), Double.parseDouble(parts[1].trim()), Double.parseDouble(parts[2].trim()));
      } catch (Exception ignored) {
         return null;
      }
   }

   @Nullable
   public static Boolean parseBoolean(String raw) {
      if (raw == null) {
         return null;
      }

      String value = raw.trim();
      return value.isEmpty() ? null : Boolean.parseBoolean(value);
   }

   private static FormParsers.ParseResult<FormParsers.Int3> optionalInt3(String x, String y, String z, String partialErrorMessage) {
      boolean any = !x.trim().isEmpty() || !y.trim().isEmpty() || !z.trim().isEmpty();
      if (!any) {
         return success(null);
      } else {
         return !x.trim().isEmpty() && !y.trim().isEmpty() && !z.trim().isEmpty() ? requiredInt3(x, y, z) : error(partialErrorMessage);
      }
   }

   private static FormParsers.ParseResult<FormParsers.Double3> optionalDouble3(String x, String y, String z, String partialErrorMessage) {
      boolean any = !x.trim().isEmpty() || !y.trim().isEmpty() || !z.trim().isEmpty();
      if (!any) {
         return success(null);
      } else {
         return !x.trim().isEmpty() && !y.trim().isEmpty() && !z.trim().isEmpty() ? requiredDouble3(x, y, z) : error(partialErrorMessage);
      }
   }

   private static <T> FormParsers.ParseResult<T> success(@Nullable T value) {
      return new FormParsers.ParseResult<>(value, null);
   }

   private static <T> FormParsers.ParseResult<T> error(String message) {
      return new FormParsers.ParseResult<>(null, message);
   }

   public record Double3(double x, double y, double z) {
      public List<Double> toList() {
         return List.of(this.x, this.y, this.z);
      }
   }

   public record DoubleRange(FormParsers.Double3 from, @Nullable FormParsers.Double3 to) {
   }

   public record Int3(int x, int y, int z) {
      public List<Integer> toList() {
         return List.of(this.x, this.y, this.z);
      }
   }

   public record IntRange(FormParsers.Int3 from, @Nullable FormParsers.Int3 to) {
   }

   public record ParseResult<T>(@Nullable T value, @Nullable String errorMessage) {
      public boolean failed() {
         return this.errorMessage != null;
      }
   }
}
