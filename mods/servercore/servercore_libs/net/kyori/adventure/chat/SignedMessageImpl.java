package servercore_libs.net.kyori.adventure.chat;

import java.security.SecureRandom;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.identity.Identity;
import servercore_libs.net.kyori.adventure.text.Component;

final class SignedMessageImpl implements SignedMessage {
   static final SecureRandom RANDOM = new SecureRandom();
   private final Instant instant = Instant.now();
   private final long salt = RANDOM.nextLong();
   private final String message;
   private final Component unsignedContent;

   SignedMessageImpl(final String message, final Component unsignedContent) {
      this.message = message;
      this.unsignedContent = unsignedContent;
   }

   @NotNull
   @Override
   public Instant timestamp() {
      return this.instant;
   }

   @Override
   public long salt() {
      return this.salt;
   }

   @Override
   public SignedMessage.Signature signature() {
      return null;
   }

   @Nullable
   @Override
   public Component unsignedContent() {
      return this.unsignedContent;
   }

   @NotNull
   @Override
   public String message() {
      return this.message;
   }

   @NotNull
   @Override
   public Identity identity() {
      return Identity.nil();
   }

   static final class SignatureImpl implements SignedMessage.Signature {
      final byte[] signature;

      SignatureImpl(final byte[] signature) {
         this.signature = signature;
      }

      @Override
      public byte[] bytes() {
         return this.signature;
      }
   }
}
