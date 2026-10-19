package twilightforest.beanification;

import java.lang.annotation.ElementType;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforgespi.language.ModFileScanData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import twilightforest.beanification.internal.BeanContextConfig;
import twilightforest.beanification.internal.DistAnnotationRetriever;
import twilightforest.beanification.processors.BeanProcessor;
import twilightforest.beanification.processors.IBeanProcessor;

public final class BeanContext extends AbstractBeanContext {
   private static final Logger LOGGER = LogManager.getLogger(BeanContext.class);
   static BeanContext INSTANCE = new BeanContext();
   @Nullable
   private static WeakReference<Object> LAST_INJECTED_INTO = null;
   @InternalAutowired
   private BeanContextConfig config;
   @InternalAutowired
   private DistAnnotationRetriever distAnnotationRetriever;
   private BeanLifeCycle lifeCycle = BeanLifeCycle.Start;
   private final Map<BeanLifeCycle, List<IBeanProcessor>> beanProcessors = new HashMap<>();
   @Nullable
   private BeanContext.ContainerContext currentContainerContext = null;

   private BeanContext() {
      InternalBeanContext.injectInto(this);
   }

   public static BeanContextConfig configure() {
      return INSTANCE.config;
   }

   public BeanLifeCycle getLifeCycle() {
      return this.lifeCycle;
   }

   public static void init(String modid) {
      INSTANCE.initInternal(modid);
   }

   void initInternal(String modid) {
      long ms = System.currentTimeMillis();
      LOGGER.info("Starting Bean Context");
      if (this.isFrozen()) {
         throw new IllegalStateException("Bean Context already frozen");
      }

      this.getBeans().clear();
      this.lifeCycle = BeanLifeCycle.Start;
      this.registerInternal(BeanContext.class, null, this);
      ModContainer modContainer = (ModContainer)ModList.get().getModContainerById(modid).orElseThrow(() -> new RuntimeException("Where is " + modid + "???!"));
      if (modContainer.getEventBus() == null) {
         throw new RuntimeException("Mod EventBus is null");
      }

      ModFileScanData scanData = modContainer.getModInfo().getOwningFile().getFile().getScanResult();
      AtomicReference<Object> currentInjection = new AtomicReference<>();

      try {
         LOGGER.debug("Registering Bean annotation processors");
         this.beanProcessors.clear();
         Iterator<? extends Class<?>> it = this.distAnnotationRetriever.retrieve(scanData, ElementType.TYPE, BeanProcessor.class).map(a -> {
            try {
               return Class.forName(a.clazz().getClassName());
            } catch (ClassNotFoundException e) {
               throw new RuntimeException(ex);
            }
         }).sorted(Comparator.comparingInt(cx -> cx.getAnnotation(BeanProcessor.class).priority())).iterator();

         while (it.hasNext()) {
            Class<?> c = (Class<?>)it.next();
            if (!IBeanProcessor.class.isAssignableFrom(c)) {
               throw new RuntimeException("Bean processor must implement IBeanProcessor: " + c);
            }

            this.beanProcessors
               .computeIfAbsent(c.getAnnotation(BeanProcessor.class).value(), k -> new ArrayList<>())
               .add((IBeanProcessor)c.getConstructor().newInstance());
            LOGGER.debug("Registered Bean processor: {}", c);
         }

         this.beanProcessors.values().stream().flatMap(Collection::stream).forEach(InternalBeanContext::injectInto);
         this.lifeCycle = BeanLifeCycle.Gather;
         BeanContext.BeanLifeCycleContext lifeCycleContext = new BeanContext.BeanLifeCycleContext(
            Optional.of(new HashMap<>()),
            Optional.empty(),
            Optional.empty(),
            Optional.of(currentInjection),
            Optional.of(definition -> this.injectInternal(definition.type(), definition.name())),
            Optional.empty()
         );
         this.runAnnotationProcessor(this.beanProcessors, this.lifeCycle, lifeCycleContext, modContainer, scanData);
         this.lifeCycle = BeanLifeCycle.Inspect;
         BeanContext.BeanLifeCycleContext var11 = new BeanContext.BeanLifeCycleContext(
            Optional.of(Collections.unmodifiableMap(lifeCycleContext.gather.orElseThrow())),
            Optional.of(new HashMap<>()),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
         );
         this.runAnnotationProcessor(this.beanProcessors, this.lifeCycle, var11, modContainer, scanData);
         this.lifeCycle = BeanLifeCycle.Validate;
         var11.dependencies().orElseThrow().replaceAll((k, v) -> Collections.unmodifiableList((List<? extends BeanDefinition<?>>)v));
         BeanContext.BeanLifeCycleContext var12 = new BeanContext.BeanLifeCycleContext(
            var11.gather,
            Optional.of(Collections.unmodifiableMap(var11.dependencies.orElseThrow())),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
         );
         this.runAnnotationProcessor(this.beanProcessors, this.lifeCycle, var12, modContainer, scanData);
         this.lifeCycle = BeanLifeCycle.Construct;
         BeanContext.BeanLifeCycleContext var13 = new BeanContext.BeanLifeCycleContext(
            var12.gather,
            var12.dependencies,
            Optional.of((definition, bean) -> this.registerInternal(definition.type(), definition.name(), bean)),
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
         );
         this.runAnnotationProcessor(this.beanProcessors, this.lifeCycle, var13, modContainer, scanData);
         this.freeze();
         this.lifeCycle = BeanLifeCycle.StaticInject;
         BeanContext.BeanLifeCycleContext var14 = new BeanContext.BeanLifeCycleContext(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(currentInjection),
            Optional.of(definition -> this.injectChecked(definition.type(), definition.name()).orElse(null)),
            Optional.of(this.getBeans())
         );
         this.runAnnotationProcessor(this.beanProcessors, this.lifeCycle, var14, modContainer, scanData);
         currentInjection.set(null);
         this.lifeCycle = BeanLifeCycle.Inject;
         BeanContext.BeanLifeCycleContext var15 = new BeanContext.BeanLifeCycleContext(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.of(currentInjection),
            Optional.of(definition -> this.injectChecked(definition.type(), definition.name()).orElse(null)),
            Optional.of(this.getBeans())
         );
         this.runAnnotationProcessor(this.beanProcessors, this.lifeCycle, var15, modContainer, scanData);
         currentInjection.set(null);
         this.lifeCycle = BeanLifeCycle.Finalize;
         BeanContext.BeanLifeCycleContext var16 = new BeanContext.BeanLifeCycleContext(
            Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(currentInjection), Optional.empty(), Optional.of(this.getBeans())
         );
         this.runAnnotationProcessor(this.beanProcessors, this.lifeCycle, var16, modContainer, scanData);
         this.lifeCycle = BeanLifeCycle.Complete;
         this.currentContainerContext = new BeanContext.ContainerContext(modContainer, scanData);
         LOGGER.info("Bean Context loaded in {} ms", System.currentTimeMillis() - ms);
      } catch (Throwable e) {
         this.throwInjectionFailedException(currentInjection, e);
      }
   }

   private void runAnnotationProcessor(
      Map<BeanLifeCycle, List<IBeanProcessor>> beanProcessors,
      BeanLifeCycle lifeCycle,
      BeanContext.BeanLifeCycleContext lifeCycleContext,
      ModContainer modContainer,
      ModFileScanData scanData
   ) throws Throwable {
      for (IBeanProcessor beanProcessor : beanProcessors.get(lifeCycle)) {
         if (INSTANCE.lifeCycle != BeanLifeCycle.Complete || this.config.loggingSettings().isInjectIntoEnabled()) {
            LOGGER.debug("Running processor {}", beanProcessor.getClass());
         }

         beanProcessor.process(lifeCycleContext, modContainer, scanData);
      }
   }

   private void throwInjectionFailedException(AtomicReference<Object> o, Throwable e) {
      throw new RuntimeException("Bean injection failed." + (o.get() == null ? "" : " At: " + o), e);
   }

   public static synchronized void injectInto(Object object) {
      if (LAST_INJECTED_INTO == null || LAST_INJECTED_INTO.get() == null || LAST_INJECTED_INTO.get() != object) {
         LAST_INJECTED_INTO = new WeakReference<>(object);
         long ms = System.currentTimeMillis();
         boolean loggingEnabled = INSTANCE.config.loggingSettings().isInjectIntoEnabled();
         if (loggingEnabled) {
            LOGGER.debug("Processing {}", object);
         }

         AtomicReference<Object> curInj = new AtomicReference<>();

         try {
            BeanContext.ContainerContext context = INSTANCE.currentContainerContext;
            if (context == null) {
               throw new IllegalStateException("BeanContext.init() must be ran first before calling BeanContext.injectInto(obj)");
            }

            INSTANCE.runAnnotationProcessor(
               INSTANCE.beanProcessors,
               BeanLifeCycle.Inject,
               new BeanContext.BeanLifeCycleContext(
                  Optional.empty(),
                  Optional.empty(),
                  Optional.empty(),
                  Optional.of(curInj),
                  Optional.of(definition -> INSTANCE.injectChecked(definition.type(), definition.name()).orElse(null)),
                  Optional.of(Map.of(new BeanDefinition<>(object.getClass(), null), object))
               ),
               context.container(),
               context.scanData()
            );
         } catch (Throwable e) {
            INSTANCE.throwInjectionFailedException(curInj, e);
         }

         if (loggingEnabled) {
            LOGGER.debug("Finished processing {} in {} ms", object, System.currentTimeMillis() - ms);
         }
      }
   }

   @Override
   protected void registerInternal(Class<?> type, @org.jetbrains.annotations.Nullable String name, Object instance) {
      LOGGER.debug("Registering Bean {} {}", type, name == null ? "" : "with name: " + name);
      super.registerInternal(type, name, instance);
   }

   @Override
   protected boolean canAccessUnfrozen() {
      return this.lifeCycle == BeanLifeCycle.Construct;
   }

   private <T> Optional<T> injectChecked(Class<T> type) {
      return this.injectChecked(type, null);
   }

   private <T> Optional<T> injectChecked(Class<T> type, @Nullable String name) {
      return INSTANCE.getBeans().containsKey(new BeanDefinition<>(type, name)) ? Optional.of(INSTANCE.injectInternal(type, name)) : Optional.empty();
   }

   public static <T> T inject(Class<T> type) {
      return inject(type, null);
   }

   public static <T> T inject(Class<T> type, @Nullable String name) {
      return INSTANCE.injectInternal(type, name);
   }

   public static <T> Lazy<T> injectLazy(Class<T> type) {
      return injectLazy(type, null);
   }

   public static <T> Lazy<T> injectLazy(Class<T> type, @Nullable String name) {
      return Lazy.of(() -> INSTANCE.injectInternal(type, name));
   }

   public record BeanLifeCycleContext(
      Optional<Map<BeanDefinition<?>, BeanContext.ThrowingSupplier<Object>>> gather,
      Optional<Map<BeanDefinition<?>, List<BeanDefinition<?>>>> dependencies,
      Optional<BiConsumer<BeanDefinition<?>, Object>> register,
      Optional<AtomicReference<Object>> currentInjection,
      Optional<Function<BeanDefinition<?>, Object>> injector,
      Optional<Map<BeanDefinition<?>, Object>> beans
   ) {
   }

   private record ContainerContext(ModContainer container, ModFileScanData scanData) {
   }

   @FunctionalInterface
   public interface ThrowingSupplier<R> {
      R get() throws Throwable;
   }
}
