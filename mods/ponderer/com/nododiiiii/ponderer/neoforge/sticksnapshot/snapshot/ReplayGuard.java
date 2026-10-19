package com.nododiiiii.ponderer.neoforge.sticksnapshot.snapshot;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public final class ReplayGuard {
   private static final ThreadLocal<ReplayGuard.ReplayContext> THREAD_CONTEXT = new ThreadLocal<>();
   private static final Map<UUID, UUID> PLAYER_TO_SESSION = new ConcurrentHashMap<>();
   private static final Map<UUID, ReplayGuard.ReplaySessionState> SESSION_STATES = new ConcurrentHashMap<>();

   private ReplayGuard() {
   }

   public static ReplayGuard.Scope begin(ServerPlayer realPlayer, @Nullable UUID fakePlayerId, String reason) {
      UUID sessionId = UUID.randomUUID();
      ReplayGuard.ReplayContext context = new ReplayGuard.ReplayContext(sessionId, realPlayer.getUUID(), fakePlayerId, reason);
      ReplayGuard.ReplaySessionState state = new ReplayGuard.ReplaySessionState(context);
      THREAD_CONTEXT.set(context);
      SESSION_STATES.put(sessionId, state);
      PLAYER_TO_SESSION.put(realPlayer.getUUID(), sessionId);
      if (fakePlayerId != null) {
         PLAYER_TO_SESSION.put(fakePlayerId, sessionId);
      }

      return new ReplayGuard.Scope(sessionId, true);
   }

   public static ReplayGuard.Scope openFromCapture(@Nullable ReplayGuard.CapturedContext captured, String source) {
      if (captured != null && isSessionActive(captured.sessionId())) {
         ReplayGuard.ReplayContext context = new ReplayGuard.ReplayContext(
            captured.sessionId(), captured.realPlayerId(), captured.fakePlayerId(), captured.reason() + " -> " + source
         );
         THREAD_CONTEXT.set(context);
         return new ReplayGuard.Scope(captured.sessionId(), false);
      } else {
         return ReplayGuard.Scope.noop();
      }
   }

   @Nullable
   public static ReplayGuard.CapturedContext captureCurrentContext() {
      ReplayGuard.ReplayContext context = THREAD_CONTEXT.get();
      return context == null ? null : new ReplayGuard.CapturedContext(context.sessionId, context.realPlayerId, context.fakePlayerId, context.reason);
   }

   public static boolean isActive() {
      return THREAD_CONTEXT.get() != null;
   }

   public static boolean isReplayPlayer(UUID playerId) {
      ReplayGuard.ReplayContext context = THREAD_CONTEXT.get();
      if (context != null && context.matches(playerId)) {
         return true;
      }

      UUID sessionId = PLAYER_TO_SESSION.get(playerId);
      return sessionId != null && isSessionActive(sessionId);
   }

   public static boolean shouldBlockPlayer(ServerPlayer player) {
      return isReplayPlayer(player.getUUID());
   }

   public static boolean isSessionActive(UUID sessionId) {
      ReplayGuard.ReplaySessionState state = SESSION_STATES.get(sessionId);
      return state != null && state.active.get();
   }

   public static void auditBlocked(String layer, String action, @Nullable String actor) {
   }

   public static void auditDroppedAsync(String source, UUID sessionId) {
   }

   private static void endSession(UUID sessionId) {
      ReplayGuard.ReplaySessionState state = SESSION_STATES.remove(sessionId);
      if (state != null) {
         state.active.set(false);
         PLAYER_TO_SESSION.remove(state.context.realPlayerId, sessionId);
         if (state.context.fakePlayerId != null) {
            PLAYER_TO_SESSION.remove(state.context.fakePlayerId, sessionId);
         }
      }
   }

   public record CapturedContext(UUID sessionId, UUID realPlayerId, @Nullable UUID fakePlayerId, String reason) {
   }

   private static final class ReplayContext {
      private final UUID sessionId;
      private final UUID realPlayerId;
      @Nullable
      private final UUID fakePlayerId;
      private final String reason;

      private ReplayContext(UUID sessionId, UUID realPlayerId, @Nullable UUID fakePlayerId, String reason) {
         this.sessionId = sessionId;
         this.realPlayerId = realPlayerId;
         this.fakePlayerId = fakePlayerId;
         this.reason = reason;
      }

      private boolean matches(UUID playerId) {
         return this.realPlayerId.equals(playerId) || this.fakePlayerId != null && this.fakePlayerId.equals(playerId);
      }
   }

   private static final class ReplaySessionState {
      private final ReplayGuard.ReplayContext context;
      private final AtomicBoolean active = new AtomicBoolean(true);

      private ReplaySessionState(ReplayGuard.ReplayContext context) {
         this.context = context;
      }
   }

   public static final class Scope implements AutoCloseable {
      @Nullable
      private final UUID sessionId;
      private final boolean owner;
      private boolean closed;

      private Scope(@Nullable UUID sessionId, boolean owner) {
         this.sessionId = sessionId;
         this.owner = owner;
      }

      private static ReplayGuard.Scope noop() {
         ReplayGuard.Scope scope = new ReplayGuard.Scope(null, false);
         scope.closed = true;
         return scope;
      }

      @Override
      public void close() {
         if (!this.closed) {
            this.closed = true;
            ReplayGuard.THREAD_CONTEXT.remove();
            if (this.owner && this.sessionId != null) {
               ReplayGuard.endSession(this.sessionId);
            }
         }
      }
   }
}
