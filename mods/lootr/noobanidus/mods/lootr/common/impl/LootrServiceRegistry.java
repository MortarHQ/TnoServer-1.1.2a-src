package noobanidus.mods.lootr.common.impl;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.StringJoiner;
import java.util.function.Function;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.mods.lootr.common.api.ILootrAPI;
import noobanidus.mods.lootr.common.api.ILootrBlockEntityConverter;
import noobanidus.mods.lootr.common.api.ILootrEntityConverter;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.adapter.AdapterMap;
import noobanidus.mods.lootr.common.api.adapter.ILootrDataAdapter;
import noobanidus.mods.lootr.common.api.adapter.ILootrItemFrameAdapter;
import noobanidus.mods.lootr.common.api.client.ILootrFabricModelProvider;
import noobanidus.mods.lootr.common.api.command.ILootrCommandExtension;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.data.entity.ILootrEntity;
import noobanidus.mods.lootr.common.api.filter.ILootrFilter;
import noobanidus.mods.lootr.common.api.filter.ILootrFilterProvider;
import noobanidus.mods.lootr.common.api.processor.ILootrBlockEntityProcessor;
import noobanidus.mods.lootr.common.api.processor.ILootrEntityProcessor;
import noobanidus.mods.lootr.common.api.replacement.BlockReplacementMap;
import noobanidus.mods.lootr.common.api.replacement.ILootrBlockReplacementProvider;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;

public class LootrServiceRegistry {
   private static LootrServiceRegistry INSTANCE;
   private final Map<BlockEntityType<?>, Function<?, ?>> blockEntityConverterMap = new Object2ObjectOpenHashMap();
   private final Map<EntityType<?>, Function<?, ?>> entityConverterMap = new Object2ObjectOpenHashMap();
   private final List<ILootrFilter> filters = new ObjectArrayList();
   private final List<ILootrBlockEntityProcessor.Post> blockEntityPostProcessors = new ObjectArrayList();
   private final List<ILootrBlockEntityProcessor.Pre> blockEntityPreProcessors = new ObjectArrayList();
   private final List<ILootrEntityProcessor.Pre> entityPreProcessors = new ObjectArrayList();
   private final List<ILootrEntityProcessor.Post> entityPostProcessors = new ObjectArrayList();
   private final AdapterMap<ILootrDataAdapter<?>> dataAdapterMap = new AdapterMap<>(AdapterMap.NONE_DATA_ADAPTER);
   private final AdapterMap<ILootrItemFrameAdapter<?>> itemFrameAdapterMap = new AdapterMap<>(AdapterMap.NONE_ITEM_FRAME_ADAPTER);
   private final BlockReplacementMap replacementMap = new BlockReplacementMap();
   private final Map<String, ILootrType> typeMap = new Object2ObjectOpenHashMap();
   private final List<ILootrFabricModelProvider> fabricModelProviders = new ObjectArrayList();
   private final List<ILootrCommandExtension> commandExtensions = new ObjectArrayList();
   private final String commands;

   public LootrServiceRegistry() {
      ClassLoader classLoader = ILootrAPI.class.getClassLoader();

      for (ILootrBlockEntityConverter<?> converter : ServiceLoader.load(ILootrBlockEntityConverter.class, classLoader)) {
         this.blockEntityConverterMap.put(converter.getBlockEntityType(), converter);
      }

      for (ILootrEntityConverter<?> converter2 : ServiceLoader.load(ILootrEntityConverter.class, classLoader)) {
         this.entityConverterMap.put(converter2.getEntityType(), converter2);
      }

      for (ILootrFilterProvider provider : ServiceLoader.load(ILootrFilterProvider.class, classLoader)) {
         this.filters.addAll(provider.getFilters());
      }

      this.filters.sort(Comparator.comparingInt(ILootrFilter::getPriority).reversed());

      for (ILootrBlockEntityProcessor.Post processor : ServiceLoader.load(ILootrBlockEntityProcessor.Post.class, classLoader)) {
         this.blockEntityPostProcessors.add(processor);
      }

      for (ILootrBlockEntityProcessor.Pre processor : ServiceLoader.load(ILootrBlockEntityProcessor.Pre.class, classLoader)) {
         this.blockEntityPreProcessors.add(processor);
      }

      for (ILootrEntityProcessor.Pre processor : ServiceLoader.load(ILootrEntityProcessor.Pre.class, classLoader)) {
         this.entityPreProcessors.add(processor);
      }

      for (ILootrEntityProcessor.Post processor : ServiceLoader.load(ILootrEntityProcessor.Post.class, classLoader)) {
         this.entityPostProcessors.add(processor);
      }

      for (ILootrDataAdapter<?> adapter : ServiceLoader.load(ILootrDataAdapter.class, classLoader)) {
         this.dataAdapterMap.register(adapter);
      }

      for (ILootrBlockReplacementProvider provider : ServiceLoader.load(ILootrBlockReplacementProvider.class, classLoader)) {
         this.replacementMap.register(provider);
      }

      this.replacementMap.sort();

      for (ILootrType type : ServiceLoader.load(ILootrType.class, classLoader)) {
         this.typeMap.put(type.getName(), type);
         type.callback();
      }

      for (ILootrFabricModelProvider appender : ServiceLoader.load(ILootrFabricModelProvider.class, classLoader)) {
         this.fabricModelProviders.add(appender);
      }

      StringJoiner commandsTemp = new StringJoiner(" | ");

      for (ILootrCommandExtension extension : ServiceLoader.load(ILootrCommandExtension.class, classLoader)) {
         this.commandExtensions.add(extension);
         commandsTemp.add(extension.getId());
         commandsTemp.add(extension.getId() + " <loot-table>");
      }

      this.commands = commandsTemp.toString();

      for (ILootrItemFrameAdapter<?> adapter : ServiceLoader.load(ILootrItemFrameAdapter.class, classLoader)) {
         this.itemFrameAdapterMap.register(adapter);
      }
   }

   public static LootrServiceRegistry getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new LootrServiceRegistry();
      }

      return INSTANCE;
   }

   @Nullable
   private static <T> Function<T, ILootrBlockEntity> getBlockEntity(BlockEntityType<?> clazz) {
      return (Function<T, ILootrBlockEntity>)getInstance().blockEntityConverterMap.get(clazz);
   }

   @Nullable
   private static <T> Function<T, ILootrEntity> getEntity(EntityType<?> clazz) {
      return (Function<T, ILootrEntity>)getInstance().entityConverterMap.get(clazz);
   }

   @Nullable
   static <T extends BlockEntity> ILootrBlockEntity convertBlockEntity(T blockEntity) {
      if (blockEntity == null) {
         return null;
      }

      Function<T, ILootrBlockEntity> converter = getBlockEntity(blockEntity.getType());
      return converter == null ? null : converter.apply(blockEntity);
   }

   @Nullable
   static <T extends Entity> ILootrEntity convertEntity(T entity) {
      if (entity == null) {
         return null;
      }

      Function<T, ILootrEntity> converter = getEntity(entity.getType());
      return converter == null ? null : converter.apply(entity);
   }

   static List<ILootrFilter> getFilters() {
      return getInstance().filters;
   }

   static List<ILootrEntityProcessor.Pre> getEntityPreProcessors() {
      return getInstance().entityPreProcessors;
   }

   static List<ILootrBlockEntityProcessor.Pre> getBlockEntityPreProcessors() {
      return getInstance().blockEntityPreProcessors;
   }

   static List<ILootrEntityProcessor.Post> getEntityPostProcessors() {
      return getInstance().entityPostProcessors;
   }

   static List<ILootrBlockEntityProcessor.Post> getBlockEntityPostProcessors() {
      return getInstance().blockEntityPostProcessors;
   }

   static BlockState getReplacementBlockState(BlockState block) {
      return getInstance().replacementMap.getReplacement(block);
   }

   public static void clearReplacements() {
      getInstance().replacementMap.clear();
   }

   @Nullable
   static <T> ILootrDataAdapter<T> getAdapter(T type) {
      return (ILootrDataAdapter<T>)getInstance().dataAdapterMap.getAdapter(type);
   }

   @Nullable
   static <T> ILootrItemFrameAdapter<T> getItemFrameAdapter(T type) {
      return (ILootrItemFrameAdapter<T>)getInstance().itemFrameAdapterMap.getAdapter(type);
   }

   @Nullable
   static ILootrType getType(String type) {
      return getInstance().typeMap.get(type);
   }

   @Internal
   public static List<ILootrFabricModelProvider> getModelAppenders() {
      return getInstance().fabricModelProviders;
   }

   @Internal
   public static List<ILootrCommandExtension> getCommandExtensions() {
      return getInstance().commandExtensions;
   }

   @Internal
   public static String getCommandExtensionsString() {
      return getInstance().commands;
   }
}
