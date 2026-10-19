package com.github.wal_bos.moreskills.client.fx;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public final class ClientVfxClassPurger {
   private static final List<String> PACKAGES = List.of(
      "com.github.wal_bos.moreskills.client.fx.", "com.github.wal_bos.moreskills.client.vfx.", "com.github.wal_bos.moreskills.client.renderer."
   );
   private static final Map<String, List<String>> CLASSES = createClasses();

   private ClientVfxClassPurger() {
   }

   public static void purge(String id) {
      String normalized = ClientVfxIds.normalize(id);
      if (normalized != null) {
         List<String> classNames = CLASSES.get(normalized);
         if (classNames != null) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();

            for (String className : classNames) {
               purgeClass(loader, className);
            }
         }
      }
   }

   private static void purgeClass(ClassLoader loader, String simpleName) {
      for (String packageName : PACKAGES) {
         try {
            Class<?> type = Class.forName(packageName + simpleName, false, loader);
            clearState(type);
            return;
         } catch (ClassNotFoundException var5) {
         } catch (Throwable ignored) {
            return;
         }
      }
   }

   private static void clearState(Class<?> type) {
      for (Field field : type.getDeclaredFields()) {
         if (Modifier.isStatic(field.getModifiers())) {
            try {
               field.setAccessible(true);
               Object value = field.get(null);
               if (value instanceof Collection<?> collection) {
                  collection.clear();
               } else if (value instanceof Map<?, ?> map) {
                  map.clear();
               } else if (value instanceof AtomicReference<?> reference) {
                  reference.set(null);
               } else if (value instanceof AtomicInteger integer) {
                  integer.set(0);
               } else if (value instanceof AtomicLong atomicLong) {
                  atomicLong.set(0L);
               } else if (value instanceof AtomicBoolean atomicBoolean && isRuntimeBoolean(field.getName())) {
                  atomicBoolean.set(false);
               } else if (!Modifier.isFinal(field.getModifiers())) {
                  Class<?> fieldType = field.getType();
                  String fieldName = normalizeFieldName(field.getName());
                  if ((fieldType == boolean.class || fieldType == Boolean.class) && isRuntimeBoolean(fieldName)) {
                     field.set(null, false);
                  } else if ((fieldType == int.class || fieldType == Integer.class) && isRuntimeNumber(fieldName)) {
                     field.set(null, 0);
                  } else if ((fieldType == long.class || fieldType == Long.class) && isRuntimeNumber(fieldName)) {
                     field.set(null, 0L);
                  } else if ((fieldType == float.class || fieldType == Float.class) && isRuntimeNumber(fieldName)) {
                     field.set(null, 0.0F);
                  } else if ((fieldType == double.class || fieldType == Double.class) && isRuntimeNumber(fieldName)) {
                     field.set(null, 0.0);
                  } else if (!fieldType.isPrimitive() && isRuntimeObject(fieldName)) {
                     field.set(null, null);
                  }
               }
            } catch (Throwable var8) {
            }
         }
      }
   }

   private static boolean isRuntimeBoolean(String name) {
      String normalized = normalizeFieldName(name);
      return normalized.equals("active")
         || normalized.equals("playing")
         || normalized.equals("running")
         || normalized.equals("visible")
         || normalized.equals("shaking");
   }

   private static boolean isRuntimeNumber(String name) {
      String normalized = normalizeFieldName(name);
      return normalized.contains("shake")
         || normalized.contains("remaining")
         || normalized.contains("timer")
         || normalized.endsWith("ticks")
         || normalized.endsWith("tick")
         || normalized.equals("age")
         || normalized.equals("duration")
         || normalized.contains("intensity")
         || normalized.contains("strength")
         || normalized.contains("opacity")
         || normalized.contains("alpha")
         || normalized.contains("flash");
   }

   private static boolean isRuntimeObject(String name) {
      String normalized = normalizeFieldName(name);
      return normalized.equals("active")
         || normalized.equals("current")
         || normalized.equals("instance")
         || normalized.equals("state")
         || normalized.equals("target");
   }

   private static String normalizeFieldName(String value) {
      return value.toLowerCase(Locale.ROOT).replace("_", "");
   }

   private static Map<String, List<String>> createClasses() {
      Map<String, List<String>> map = new LinkedHashMap<>();
      map.put("albis_vina", List.of("AlbisVinaVfxClient"));
      map.put("asmoday_black_hole", List.of("AsmodayBlackHoleClient"));
      map.put("asmoday_falling_cube", List.of("AsmodayFallingCubeClient"));
      map.put("asmoday_prison_realm", List.of("AsmodayPrisonRealmClient"));
      map.put("asmoday_space", List.of("AsmodaySpaceClient"));
      map.put("asmoday_spatial_absorption", List.of("AsmodaySpatialAbsorptionClient"));
      map.put("asmoday_spatial_cube", List.of("AsmodaySpatialCubeClient"));
      map.put("avalon_novari_phase", List.of("AvalonNovariPhaseVfxClient"));
      map.put("axis_judgment", List.of("AxisJudgmentClient"));
      map.put("black_blood_domain_fog", List.of("BlackBloodDomainFogHandler"));
      map.put("black_communion", List.of("BlackCommunionVfxClient"));
      map.put("chrono_judgement_clock", List.of("ChronoJudgementClockClient"));
      map.put("elfaria_albis_ice_sheet", List.of("ElfariaAlbisIceSheetRenderer"));
      map.put("elfaria_ares_weiss_bloom", List.of("ElfariaAresWeissBloomVfxClient"));
      map.put("elfaria_fruzel_cardeneia", List.of("ElfariaFruzelCardeneiaVfxClient"));
      map.put("elfaria_glacial_last_albis", List.of("ElfariaGlacialLastAlbisVfxClient"));
      map.put("elfaria_icemaiden", List.of("ElfariaIcemaidenVfxClient"));
      map.put("elfaria_myrdas_fridolet", List.of("ElfariaMyrdasFridoletVfxClient"));
      map.put("elfaria_oplce_shine", List.of("ElfariaOplceShineVfxClient"));
      map.put("elfaria_passive_ice_steps", List.of("ElfariaPassiveIceStepsVfxClient"));
      map.put("elfaria_stellas_natea", List.of("ElfariaStellasNateaVfxClient"));
      map.put("elfaria_vfx_helper", List.of("ElfariaVfxHelper"));
      map.put("elfaria_vfx_vertex_helper", List.of("ElfariaVfxVertexHelper"));
      map.put("exploiter_xray_hook", List.of("ExploiterClientXrayHook"));
      map.put("feldway_avatar_beam", List.of("FeldwayAvatarBeamClient"));
      map.put("feldway_awakening_block_storm", List.of("FeldwayAwakeningBlockStorm"));
      map.put("feldway_awakening_camera_shake", List.of("FeldwayAwakeningCameraShake"));
      map.put("feldway_awakening_coronation_beam", List.of("FeldwayAwakeningCoronationBeam"));
      map.put("feldway_awakening_shader", List.of("FeldwayAwakeningShader"));
      map.put("feldway_reward_client_fx", List.of("FeldwayRewardClientFx"));
      map.put("full_cowl_lightning", List.of("FullCowlLightningClient"));
      map.put("gearshift_blue_trail", List.of("GearshiftBlueTrailClient"));
      map.put("imperial_halo_shot", List.of("ImperialHaloShotClient"));
      map.put("istaroth_camera_shake", List.of("IstarothClientCameraShake"));
      map.put("istaroth_doom_tornado", List.of("IstarothClientDoomTornado"));
      map.put("istaroth_gray_shader", List.of("IstarothGrayShader"));
      map.put("istaroth_screen_fx", List.of("IstarothScreenFx"));
      map.put("i_want_limbo", List.of("IWantLimboClient"));
      map.put("michael_aura", List.of("MichaelAuraVfxClient"));
      map.put("michael_halberd_shot", List.of("MichaelHalberdShotVfxClient"));
      map.put("michael_jerez", List.of("MichaelJerezVfxClient"));
      map.put("michael_keter", List.of("MichaelKeterVfxClient"));
      map.put("michael_lataib_barrage", List.of("MichaelLataibBarrageVfxClient"));
      map.put("michael_segva", List.of("MichaelSegvaVfxClient"));
      map.put("michael_shifuru_meteor", List.of("MichaelShifuruMeteorVfxClient"));
      map.put("michael_vfx", List.of("MichaelVfxClients"));
      map.put("mitama_atomic_edict", List.of("MitamaAtomicEdictVfxClient"));
      map.put("mitama_divine_beams", List.of("MitamaDivineBeamsClient"));
      map.put("novari_black_white", List.of("NovariBlackWhiteVfxClient"));
      map.put("pain_planetary_devastation", List.of("PainPlanetaryDevastationClient"));
      map.put("pain_six_paths", List.of("PainSixPathsVfxClient"));
      map.put("phainon_acquirement", List.of("PhainonAcquirementVfxClient"));
      map.put("ronova_finality", List.of("RonovaFinalityVfxClient"));
      map.put("satella", List.of("SatellaVfxClient"));
      map.put("seraphic_guillotine", List.of("SeraphicGuillotineClient"));
      map.put("witch_of_envy_satella", List.of("WitchOfEnvySatellaVfxClient"));
      return Map.copyOf(map);
   }
}
