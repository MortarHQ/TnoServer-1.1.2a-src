package com.tensura_guild;

import com.tensura_guild.data.GuildPartyService;
import java.lang.reflect.Method;
import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public final class GuildPartyFriendlyFireEvents {
   private static final int OWNER_RESOLVE_DEPTH = 4;

   private GuildPartyFriendlyFireEvents() {
   }

   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      if (event.getEntity() instanceof ServerPlayer target) {
         resolveAttackingPlayer(event.getSource())
            .filter(attacker -> GuildPartyService.arePartyMembers(target, attacker))
            .ifPresent(attacker -> event.setCanceled(true));
      }
   }

   private static Optional<ServerPlayer> resolveAttackingPlayer(DamageSource source) {
      if (source == null) {
         return Optional.empty();
      }

      Optional<ServerPlayer> attacker = resolvePlayer(source.getEntity(), 4);
      return attacker.isPresent() ? attacker : resolvePlayer(source.getDirectEntity(), 4);
   }

   private static Optional<ServerPlayer> resolvePlayer(Entity entity, int depth) {
      if (entity instanceof ServerPlayer player) {
         return Optional.of(player);
      } else if (entity != null && depth > 0) {
         Entity owner = resolveOwner(entity);
         return owner != null && owner != entity ? resolvePlayer(owner, depth - 1) : Optional.empty();
      } else {
         return Optional.empty();
      }
   }

   private static Entity resolveOwner(Entity entity) {
      if (entity instanceof TraceableEntity traceable) {
         Entity owner = traceable.getOwner();
         if (owner != null) {
            return owner;
         }
      }

      return entity instanceof OwnableEntity ownable && ownable.getOwner() instanceof Entity owner ? owner : resolveReflectedOwner(entity);
   }

   private static Entity resolveReflectedOwner(Entity entity) {
      try {
         Method getOwner = entity.getClass().getMethod("getOwner");
         if (getOwner.getParameterCount() == 0 && Entity.class.isAssignableFrom(getOwner.getReturnType())) {
            return getOwner.invoke(entity) instanceof Entity ownerEntity ? ownerEntity : null;
         } else {
            return null;
         }
      } catch (ReflectiveOperationException | SecurityException ignored) {
         return null;
      }
   }
}
