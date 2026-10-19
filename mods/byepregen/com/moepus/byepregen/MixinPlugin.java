package com.moepus.byepregen;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.service.MixinService;

public final class MixinPlugin implements IMixinConfigPlugin {
   private static final String MIXIN_PACKAGE = "com.moepus.byepregen.mixin.";
   private static final String YA_LIGHT_MIXIN_PREFIX = "com.moepus.byepregen.mixin.yalight.";
   private static final String GC_FREE_MIXIN_PREFIX = "com.moepus.byepregen.mixin.gcfree.";
   private static final String C2ME_HOOK_COMPATIBILITY_MIXIN = "com.moepus.byepregen.mixin.compat.C2MEHookCompatibilityMixin";
   private static final String ARCHITECTURY_EVENT_ACCESSOR = "com.moepus.byepregen.mixin.accessor.ArchitecturyEventImplAccessor";
   private static final String CHUNK_STORAGE_ACCESSOR = "com.moepus.byepregen.mixin.accessor.ChunkStorageAccessor";
   private static final String IO_WORKER_PENDING_STORE_ACCESSOR = "com.moepus.byepregen.mixin.accessor.IOWorkerPendingStoreAccessor";
   private static final String REGION_FILE_STORAGE_ACCESSOR = "com.moepus.byepregen.mixin.accessor.RegionFileStorageAccessor";
   private static final String C2ME_SERIALIZER_ACCESS = "com.ishland.c2me.base.common.registry.SerializerAccess";
   private static final String CHUNK_ACCESS_ARENA_MIXIN = "com.moepus.byepregen.mixin.ChunkAccessArenaMixin";
   private static final String CHUNK_SERIALIZER_ARENA_READ_MIXIN = "com.moepus.byepregen.mixin.ChunkSerializerArenaReadMixin";
   private static final String LEVEL_CHUNK_ARENA_MIXIN = "com.moepus.byepregen.mixin.LevelChunkArenaMixin";
   private static final String FASTNOISE_ARENA_MIXIN = "com.moepus.byepregen.mixin.compat.FastNoiseFastChunkSectionMixin";
   private static final String VOXY_ARENA_MIXIN = "com.moepus.byepregen.mixin.compat.VoxyWorldConversionFactoryMixin";
   private static final Set<String> GC_FREE_SATELLITE_MIXINS = Set.of(
      "com.moepus.byepregen.mixin.compat.C2MEHookCompatibilityMixin", "com.moepus.byepregen.mixin.accessor.ArchitecturyEventImplAccessor"
   );
   private static final Set<String> RAW_GC_FREE_MIXINS = Set.of(
      "com.moepus.byepregen.mixin.gcfree.ChunkMapGcFreeSaveMixin",
      "com.moepus.byepregen.mixin.gcfree.ChunkStorageRawMixin",
      "com.moepus.byepregen.mixin.gcfree.IOWorkerRawMixin",
      "com.moepus.byepregen.mixin.accessor.ChunkStorageAccessor",
      "com.moepus.byepregen.mixin.accessor.IOWorkerPendingStoreAccessor",
      "com.moepus.byepregen.mixin.accessor.RegionFileStorageAccessor"
   );
   private static final Set<String> ARENA_MIXINS = Set.of(
      "com.moepus.byepregen.mixin.ChunkAccessArenaMixin",
      "com.moepus.byepregen.mixin.ChunkSerializerArenaReadMixin",
      "com.moepus.byepregen.mixin.LevelChunkArenaMixin",
      "com.moepus.byepregen.mixin.compat.FastNoiseFastChunkSectionMixin",
      "com.moepus.byepregen.mixin.compat.VoxyWorldConversionFactoryMixin"
   );
   private static final MixinGateEvaluator MIXIN_GATE_EVALUATOR = MixinGateEvaluator.createDefault();

   public void onLoad(String mixinPackage) {
   }

   public String getRefMapperConfig() {
      return null;
   }

   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
      Config config = ConfigParser.getConfig();
      boolean featureEnabled = passesFeatureGate(mixinClassName, config, MixinPlugin::hasClass);
      boolean annotationEnabled = MIXIN_GATE_EVALUATOR.shouldApply(targetClassName, mixinClassName, config);
      return featureEnabled && annotationEnabled;
   }

   static boolean passesFeatureGate(String mixinClassName, Config config, Predicate<String> classExists) {
      if (mixinClassName.startsWith("com.moepus.byepregen.mixin.yalight.")) {
         return config.enableYALightEngine;
      } else if (isGcFreeMixin(mixinClassName)) {
         return passesGcFreeGate(mixinClassName, config, classExists);
      } else {
         return ARENA_MIXINS.contains(mixinClassName) ? passesArenaGate(mixinClassName, config) : true;
      }
   }

   private static boolean isGcFreeMixin(String mixinClassName) {
      return mixinClassName.startsWith("com.moepus.byepregen.mixin.gcfree.")
         || GC_FREE_SATELLITE_MIXINS.contains(mixinClassName)
         || RAW_GC_FREE_MIXINS.contains(mixinClassName);
   }

   private static boolean passesGcFreeGate(String mixinClassName, Config config, Predicate<String> classExists) {
      return !config.enableGcFreeWorldgenSave
         ? false
         : !RAW_GC_FREE_MIXINS.contains(mixinClassName) || !classExists.test("com.ishland.c2me.base.common.registry.SerializerAccess");
   }

   private static boolean passesArenaGate(String mixinClassName, Config config) {
      if (!config.enableArenaPalette) {
         return false;
      } else if (isModExist("confluence")) {
         return false;
      } else if ("com.moepus.byepregen.mixin.LevelChunkArenaMixin".equals(mixinClassName)) {
         return !config.enableServerRuntimeArenaPalette;
      } else {
         return "com.moepus.byepregen.mixin.compat.VoxyWorldConversionFactoryMixin".equals(mixinClassName) ? config.enableClientArenaPalette : true;
      }
   }

   private static ModFileInfo getModFile(String modId) {
      LoadingModList modList = LoadingModList.get();
      ModFileInfo modFile = modList.getModFileById(modId);
      return modFile != null
         ? modFile
         : modList.getPlugins()
            .stream()
            .filter(ModFileInfo.class::isInstance)
            .map(ModFileInfo.class::cast)
            .filter(file -> file.getMods().stream().anyMatch(mod -> mod.getModId().equals(modId)))
            .findFirst()
            .orElse(null);
   }

   public static boolean isModExist(String modId) {
      return getModFile(modId) != null;
   }

   public static boolean hasClass(String className) {
      try {
         MixinService.getService().getBytecodeProvider().getClassNode(className);
         return true;
      } catch (Throwable ignored) {
         return false;
      }
   }

   public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
   }

   public List<String> getMixins() {
      return null;
   }

   public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
   }

   public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
   }
}
