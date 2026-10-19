package com.mafuyu404.oneenoughitem.data;

import cc.sighs.oelib.data.api.DataValidator.ValidationResult;
import java.util.Objects;
import java.util.function.Function;

public final class ValidationStreams {
   private ValidationStreams() {
   }

   public static final class Accumulator {
      private final boolean hasValid;
      private final boolean hasDeferred;
      private final int validCount;
      private final String failureMessage;

      private Accumulator(boolean hasValid, boolean hasDeferred, int validCount, String failureMessage) {
         this.hasValid = hasValid;
         this.hasDeferred = hasDeferred;
         this.validCount = validCount;
         this.failureMessage = failureMessage;
      }

      public static ValidationStreams.Accumulator identity() {
         return new ValidationStreams.Accumulator(false, false, 0, null);
      }

      public static ValidationStreams.Accumulator valid(int c) {
         return new ValidationStreams.Accumulator(true, false, Math.max(0, c), null);
      }

      public static ValidationStreams.Accumulator deferred() {
         return new ValidationStreams.Accumulator(false, true, 0, null);
      }

      public static ValidationStreams.Accumulator invalid() {
         return identity();
      }

      public static ValidationStreams.Accumulator failure(String msg) {
         return new ValidationStreams.Accumulator(false, false, 0, Objects.requireNonNull(msg));
      }

      public ValidationStreams.Accumulator combine(ValidationStreams.Accumulator other) {
         if (this.failureMessage != null) {
            return this;
         } else {
            return other.failureMessage != null
               ? other
               : new ValidationStreams.Accumulator(
                  this.hasValid || other.hasValid, this.hasDeferred || other.hasDeferred, this.validCount + other.validCount, null
               );
         }
      }

      public ValidationStreams.Accumulator map(Function<ValidationStreams.Accumulator, ValidationStreams.Accumulator> f) {
         return f.apply(this);
      }

      public ValidationStreams.Accumulator flatCombine(Function<ValidationStreams.Accumulator, ValidationStreams.Accumulator> f) {
         return this.failureMessage != null ? this : f.apply(this);
      }

      public ValidationResult toResult(String deferredMessage, String noValidMessage) {
         if (this.failureMessage != null) {
            return ValidationResult.failure(this.failureMessage);
         } else if (this.hasValid) {
            return ValidationResult.success();
         } else {
            return this.hasDeferred ? ValidationResult.deferred(deferredMessage) : ValidationResult.failure(noValidMessage);
         }
      }
   }
}
