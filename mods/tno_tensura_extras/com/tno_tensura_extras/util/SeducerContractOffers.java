package com.tno_tensura_extras.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class SeducerContractOffers {
   private static final long OFFER_TICKS = 600L;
   private final Map<UUID, SeducerContractOffers.Offer> offers = new HashMap<>();

   public SeducerContractOffers.Offer create(UUID subject, UUID owner, long now) {
      if (subject != null && owner != null && !subject.equals(owner) && now <= 9223372036854775207L) {
         SeducerContractOffers.Offer current = this.offers.get(subject);
         if (current != null && now < current.expiresAt()) {
            return null;
         }

         SeducerContractOffers.Offer offer = new SeducerContractOffers.Offer(UUID.randomUUID(), owner, now + 600L);
         this.offers.put(subject, offer);
         return offer;
      } else {
         return null;
      }
   }

   public SeducerContractOffers.Offer consume(UUID subject, UUID token, long now) {
      if (subject != null && token != null) {
         SeducerContractOffers.Offer offer = this.offers.get(subject);
         if (offer == null) {
            return null;
         }

         if (now >= offer.expiresAt()) {
            this.offers.remove(subject);
            return null;
         }

         if (!offer.token().equals(token)) {
            return null;
         }

         this.offers.remove(subject);
         return offer;
      } else {
         return null;
      }
   }

   public void remove(UUID subject) {
      this.offers.remove(subject);
   }

   public void cancel(UUID participant) {
      if (participant != null) {
         this.offers.entrySet().removeIf(entry -> entry.getKey().equals(participant) || entry.getValue().owner().equals(participant));
      }
   }

   public void expire(long now) {
      this.offers.values().removeIf(offer -> now >= offer.expiresAt());
   }

   public void clear() {
      this.offers.clear();
   }

   public record Offer(UUID token, UUID owner, long expiresAt) {
      public Offer {
         Objects.requireNonNull(token, "token");
         Objects.requireNonNull(owner, "owner");
      }
   }
}
