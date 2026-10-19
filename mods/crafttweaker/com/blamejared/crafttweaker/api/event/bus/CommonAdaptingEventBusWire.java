package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.Phase;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.reflect.TypeToken;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public final class CommonAdaptingEventBusWire<P, C> implements IEventBusWire {
   private static final CommonAdaptingEventBusWire.CommonToPlatformConverter<?, ?> NOTHING = (a, b) -> {};
   private final IEventBusWire delegate;
   private final IEventBus<C> commonBus;
   private final CommonAdaptingEventBusWire.PlatformToCommonConverter<P, C> platformToCommon;
   private final CommonAdaptingEventBusWire.CommonToPlatformConverter<C, P> commonToPlatform;

   private CommonAdaptingEventBusWire(
      IEventBusWire delegate,
      IEventBus<C> commonBus,
      CommonAdaptingEventBusWire.PlatformToCommonConverter<P, C> platformToCommon,
      CommonAdaptingEventBusWire.CommonToPlatformConverter<C, P> commonToPlatform
   ) {
      this.delegate = delegate;
      this.commonBus = commonBus;
      this.platformToCommon = platformToCommon;
      this.commonToPlatform = commonToPlatform;
   }

   public static <C, P> IEventBusWire of(IEventBusWire delegate, IEventBus<C> commonBus, Function<P, C> platformToCommon) {
      return of(delegate, commonBus, platformToCommon, GenericUtil.uncheck(NOTHING));
   }

   public static <C, P> IEventBusWire of(IEventBusWire delegate, IEventBus<C> commonBus, Function<P, C> platformToCommon, BiConsumer<C, P> commonToPlatform) {
      Objects.requireNonNull(delegate, "delegate");
      Objects.requireNonNull(commonBus, "commonBus");
      Objects.requireNonNull(platformToCommon, "platformToCommon");
      Objects.requireNonNull(commonToPlatform, "commonToPlatform");
      return new CommonAdaptingEventBusWire<>(delegate, commonBus, platformToCommon::apply, commonToPlatform::accept);
   }

   @Override
   public <T> void registerBusForDispatch(TypeToken<T> eventType, IEventBus<T> bus) {
      this.delegate.registerBusForDispatch(eventType, bus);
      this.setUpListenerRedirector(bus);
   }

   private <T> void setUpListenerRedirector(IEventBus<T> bus) {
      for (Phase phase : Phase.values()) {
         Consumer<?> consumer = new CommonAdaptingEventBusWire.ListenerRedirector<>(phase, this.commonBus, this.platformToCommon, this.commonToPlatform);
         bus.registerHandler(phase, true, GenericUtil.uncheck(consumer));
      }
   }

   @FunctionalInterface
   private interface CommonToPlatformConverter<D, Q> extends BiConsumer<D, Q> {
   }

   private record ListenerRedirector<Q, D>(
      Phase phase,
      IEventBus<D> commonBus,
      CommonAdaptingEventBusWire.PlatformToCommonConverter<Q, D> platformToCommon,
      CommonAdaptingEventBusWire.CommonToPlatformConverter<D, Q> commonToPlatform
   ) implements Consumer<Q> {
      @Override
      public void accept(Q platform) {
         D common = this.platformToCommon().apply(platform);
         this.commonBus().postCatching(this.phase(), common, this::rethrow);
         this.commonToPlatform().accept(common, platform);
      }

      private void rethrow(BusHandlingException e) {
         throw e;
      }
   }

   @FunctionalInterface
   private interface PlatformToCommonConverter<Q, D> extends Function<Q, D> {
   }
}
