package net.blay09.mods.balm.common.proxy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.blay09.mods.balm.api.proxy.ModProxy;

public class ModProxyImpl<T> implements ModProxy<T> {
   private final Predicate<String> modLoadedPredicate;
   private final List<ModProxyImpl<T>.ModEntry> proxies = new ArrayList<>();
   private Function<List<T>, T> multiplexer;
   private T fallback;

   public ModProxyImpl(Predicate<String> modLoadedPredicate) {
      this.modLoadedPredicate = modLoadedPredicate;
   }

   @Override
   public ModProxy<T> with(String modId, String clazzName) {
      this.proxies.add(new ModProxyImpl.ModEntry(modId, clazzName, () -> {
         try {
            return (T)Class.forName(clazzName).getConstructor().newInstance();
         } catch (InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to instantiate mod proxy " + clazzName, e);
         } catch (NoSuchMethodException e) {
            throw new RuntimeException("Failed to instantiate mod proxy, missing no-arg constructor in " + clazzName, e);
         }
      }));
      return this;
   }

   @Override
   public ModProxy<T> withMultiplexer(Function<List<T>, T> multiplexer) {
      this.multiplexer = multiplexer;
      return this;
   }

   @Override
   public ModProxy<T> withFallback(T fallback) {
      this.fallback = fallback;
      return this;
   }

   @Override
   public T build() {
      List<ModProxyImpl<T>.ModEntry> applicableProxies = this.proxies.stream().filter(proxy -> this.modLoadedPredicate.test(proxy.modId)).toList();
      if (this.multiplexer != null && applicableProxies.size() > 1) {
         return this.multiplexer.apply(applicableProxies.stream().map(ModProxyImpl.ModEntry::proxy).map(Supplier::get).collect(Collectors.toList()));
      } else {
         return (T)(applicableProxies.isEmpty() ? this.fallback : applicableProxies.getFirst().proxy().get());
      }
   }

   @Override
   public Supplier<T> buildLazily() {
      return new Supplier<T>() {
         private Object instance;

         @Override
         public T get() {
            if (this.instance == null) {
               this.instance = ModProxyImpl.this.build();
            }

            return (T)this.instance;
         }
      };
   }

   private final class ModEntry {
      private final String modId;
      private final String clazzName;
      private final Supplier<T> proxy;

      private ModEntry(String modId, String clazzName, Supplier<T> proxy) {
         this.modId = modId;
         this.clazzName = clazzName;
         this.proxy = proxy;
      }

      public String modId() {
         return this.modId;
      }

      public String clazzName() {
         return this.clazzName;
      }

      public Supplier<T> proxy() {
         return this.proxy;
      }
   }
}
