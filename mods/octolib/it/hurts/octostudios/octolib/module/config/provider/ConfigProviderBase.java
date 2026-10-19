package it.hurts.octostudios.octolib.module.config.provider;

import it.hurts.octostudios.octolib.module.config.cfgbuilder.CompoundEntry;
import it.hurts.octostudios.octolib.module.config.cfgbuilder.ConfigEntry;
import it.hurts.octostudios.octolib.module.config.util.ConfigEntryConverter;
import it.hurts.octostudios.octolib.module.config.util.ConstructorExt;
import it.hurts.octostudios.octolib.module.config.util.EntryInjector;
import it.hurts.octostudios.octolib.module.config.util.PropertyUtilsExt;
import it.hurts.octostudios.octolib.module.config.util.RepresenterExt;
import it.hurts.octostudios.octolib.module.config.util.SchemeInjector;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

public class ConfigProviderBase implements ConfigProvider {
   private final Yaml yamlConverted;
   private final ConstructorExt constructor;
   private final RepresenterExt patternRepresenter;
   private final RepresenterExt configRepresenter;
   private final DumperOptions options;
   private final ConfigEntryConverter configEntryConverter;
   private final EntryInjector<ConfigEntry> injector;

   public ConfigProviderBase(ConstructorExt constructor, RepresenterExt patternRepresenter, RepresenterExt configRepresenter, DumperOptions options) {
      this(constructor, patternRepresenter, configRepresenter, options, new SchemeInjector());
   }

   public ConfigProviderBase(
      ConstructorExt constructor,
      RepresenterExt patternRepresenter,
      RepresenterExt configRepresenter,
      DumperOptions options,
      EntryInjector<ConfigEntry> injector
   ) {
      this.constructor = constructor;
      this.patternRepresenter = patternRepresenter;
      this.configRepresenter = configRepresenter;
      this.options = options;
      this.injector = injector;
      this.yamlConverted = new Yaml(constructor, configRepresenter, options);
      this.configEntryConverter = new ConfigEntryConverter(constructor, patternRepresenter);
   }

   public static ConfigProviderBase getDefault(int indent) {
      PropertyUtils propertyUtils = new PropertyUtilsExt();
      LoaderOptions loaderOptions = new LoaderOptions();
      loaderOptions.setProcessComments(true);
      loaderOptions.setTagInspector(tag -> true);
      ConstructorExt constructor = new ConstructorExt(loaderOptions);
      constructor.setPropertyUtils(propertyUtils);
      DumperOptions options = new DumperOptions();
      options.setDefaultFlowStyle(FlowStyle.BLOCK);
      options.setPrettyFlow(true);
      options.setProcessComments(true);
      options.setIndent(indent);
      var configRepresenter = new RepresenterExt(options) {
         @Override
         protected boolean removeTypes() {
            return true;
         }

         @Override
         protected boolean convertEnumToStr() {
            return true;
         }
      };
      configRepresenter.setPropertyUtils(propertyUtils);
      RepresenterExt patternRepresenter = new RepresenterExt(options);
      patternRepresenter.setPropertyUtils(new PropertyUtilsExt());
      return new ConfigProviderBase(constructor, patternRepresenter, configRepresenter, options);
   }

   @Override
   public ConfigEntry createPattern(Object object) {
      return this.configEntryConverter.representDeconstructed(object);
   }

   @Override
   public void save(Writer writer, Object config) {
      this.yamlConverted.dump(config, writer);
   }

   @Override
   public void saveAll(Writer writer, Iterator<?> config) {
      this.yamlConverted.dumpAll(config, writer);
   }

   @Override
   public Object load(Reader reader, CompoundEntry pattern) {
      CompoundEntry compound = (CompoundEntry)this.yamlConverted.loadAs(reader, CompoundEntry.class);
      if (compound == null) {
         return this.configEntryConverter.construct(pattern);
      }

      ConfigEntry resultFirst = this.injector.apply(pattern, compound);
      return this.configEntryConverter.construct(resultFirst);
   }

   @Override
   public <T> List<T> loadAll(Reader reader, Iterator<CompoundEntry> patternIterator) {
      Iterable<Object> objects = this.yamlConverted.loadAll(reader);
      ArrayList<T> list = new ArrayList<>();

      for (Object object : objects) {
         CompoundEntry pattern = patternIterator.next();
         ConfigEntry compound = this.configEntryConverter.represent(object);
         ConfigEntry resultFirst = this.injector.apply(pattern, compound);
         list.add(this.configEntryConverter.construct(resultFirst));
      }

      return list;
   }

   @Override
   public <T> T insert2ndStep(T target, T data) {
      Set<Property> props = this.patternRepresenter.getPropertyUtils().getProperties(target.getClass());
      if (Collection.class.isAssignableFrom(target.getClass())) {
         this.rewriteCollection((Collection<?>)target, (Collection<?>)data);
         return target;
      }

      if (Map.class.isAssignableFrom(target.getClass())) {
         this.rewriteMap((Map<?, ?>)target, (Map<?, ?>)data);
         return target;
      }

      for (Property prop : props) {
         try {
            if (Set.class.isAssignableFrom(prop.getClass()) || List.class.isAssignableFrom(prop.getClass())) {
               this.rewriteCollection((Set)prop.get(target), (Collection<?>)prop.get(data));
            } else if (Map.class.isAssignableFrom(prop.getClass())) {
               this.rewriteMap((Map<?, ?>)prop.get(target), (Map<?, ?>)prop.get(data));
            } else {
               prop.set(target, prop.get(data));
            }
         } catch (Exception var7) {
         }
      }

      return target;
   }

   protected void rewriteCollection(Collection<?> target, Collection<?> data) {
      target.clear();
      target.addAll(data);
   }

   protected void rewriteMap(Map<?, ?> target, Map<?, ?> data) {
      target.clear();
      target.putAll(data);
   }

   public Yaml getYamlConverted() {
      return this.yamlConverted;
   }

   public ConstructorExt getConstructor() {
      return this.constructor;
   }

   public RepresenterExt getPatternRepresenter() {
      return this.patternRepresenter;
   }

   public RepresenterExt getConfigRepresenter() {
      return this.configRepresenter;
   }

   public DumperOptions getOptions() {
      return this.options;
   }

   public ConfigEntryConverter getConfigEntryConverter() {
      return this.configEntryConverter;
   }

   public EntryInjector<ConfigEntry> getInjector() {
      return this.injector;
   }
}
