package dev.shadowako.trlagbegon;

import io.github.manasmods.tensura.entity.TensuraProjectile;
import io.github.manasmods.tensura.entity.magic.barrier.BarrierEntity;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.effect.IEffect;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

public final class StressTestRunner {
   private static final List<ResourceLocation> STRESS_ENTITY_TYPES = List.of(
      ResourceLocation.fromNamespaceAndPath("tensura", "black_lightning_blast"),
      ResourceLocation.fromNamespaceAndPath("tensura", "electro_blast"),
      ResourceLocation.fromNamespaceAndPath("tensura", "solar_beam"),
      ResourceLocation.fromNamespaceAndPath("tensura", "blood_ray"),
      ResourceLocation.fromNamespaceAndPath("tensura", "spatial_ray"),
      ResourceLocation.fromNamespaceAndPath("tensura", "black_flame_breath"),
      ResourceLocation.fromNamespaceAndPath("tensura", "flame_breath"),
      ResourceLocation.fromNamespaceAndPath("tensura", "water_breath"),
      ResourceLocation.fromNamespaceAndPath("tensura", "thunder_breath"),
      ResourceLocation.fromNamespaceAndPath("tensura", "ice_breath"),
      ResourceLocation.fromNamespaceAndPath("tensura", "wind_breath")
   );
   private static final List<ResourceLocation> UNSAFE_VISUAL_ENTITY_TYPES = List.of(
      ResourceLocation.fromNamespaceAndPath("tensura", "flare_circle"),
      ResourceLocation.fromNamespaceAndPath("tensura", "earth_storm"),
      ResourceLocation.fromNamespaceAndPath("tensura", "thunder_rain"),
      ResourceLocation.fromNamespaceAndPath("tensura", "healing_rain"),
      ResourceLocation.fromNamespaceAndPath("tensura", "blizzard"),
      ResourceLocation.fromNamespaceAndPath("tensura", "dark_cube"),
      ResourceLocation.fromNamespaceAndPath("tensura", "disintegration"),
      ResourceLocation.fromNamespaceAndPath("tensura", "megiddo_bubble"),
      ResourceLocation.fromNamespaceAndPath("tensura", "water_jail"),
      ResourceLocation.fromNamespaceAndPath("tensura", "fire_jail")
   );
   private static final Map<UUID, StressTestRunner.StressSession> SESSIONS = new ConcurrentHashMap<>();
   private static final int REFLECTIVE_EVENT_BURST = 24;

   private StressTestRunner() {
   }

   public static void start(ServerPlayer player, int seconds, int entitiesPerTick) {
      SESSIONS.put(player.getUUID(), new StressTestRunner.StressSession(player.getUUID(), seconds * 20, entitiesPerTick));
   }

   public static boolean stop(UUID playerId) {
      return SESSIONS.remove(playerId) != null;
   }

   public static void onServerTick(Post event) {
      if (!SESSIONS.isEmpty()) {
         List<UUID> finished = new ArrayList<>();

         for (StressTestRunner.StressSession session : SESSIONS.values()) {
            if (!tickSession(event, session)) {
               finished.add(session.playerId());
            }
         }

         for (UUID id : finished) {
            SESSIONS.remove(id);
         }
      }
   }

   private static boolean tickSession(Post event, StressTestRunner.StressSession session) {
      ServerPlayer player = event.getServer().getPlayerList().getPlayer(session.playerId());
      if (player != null && player.isAlive()) {
         ServerLevel level = player.serverLevel();
         RandomSource random = level.random;

         for (int i = 0; i < session.entitiesPerTick(); i++) {
            Entity spawned = spawnRandomTensuraEntity(level, player, random);
            if (spawned != null) {
               level.addFreshEntity(spawned);
            }
         }

         player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, true, false, false));
         player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 0, true, false, false));
         player.hurt(level.damageSources().magic(), 0.25F);
         runManascoreSkillApiReflectiveStress(player, random);
         runTensuraStorageStress(player, random);
         runTensuraEventReflectiveStress(level, player, random);
         runManascoreReflectiveEventStress(level, player, random);
         session.ticksLeft--;
         if (session.ticksLeft <= 0) {
            player.sendSystemMessage(Component.literal("TR stress test complete."));
            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   private static Entity spawnRandomTensuraEntity(ServerLevel level, ServerPlayer owner, RandomSource random) {
      ResourceLocation id = STRESS_ENTITY_TYPES.get(random.nextInt(STRESS_ENTITY_TYPES.size()));
      if (UNSAFE_VISUAL_ENTITY_TYPES.contains(id)) {
         return null;
      }

      EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(id);
      if (type != null && !Objects.equals(type, EntityType.PIG)) {
         Entity entity = type.create(level);
         if (entity == null) {
            return null;
         }

         Vec3 center = owner.position();
         double x = center.x + random.nextDouble() * 10.0 - 5.0;
         double y = center.y + random.nextDouble() * 3.0 + 0.5;
         double z = center.z + random.nextDouble() * 10.0 - 5.0;
         entity.setPos(x, y, z);
         if (entity instanceof Projectile projectile) {
            projectile.setOwner(owner);
            projectile.setDeltaMovement(random.nextDouble() * 0.4 - 0.2, random.nextDouble() * 0.2, random.nextDouble() * 0.4 - 0.2);
         }

         if (entity instanceof TensuraProjectile projectile) {
            projectile.setLife(100);
            projectile.setDamage(2.0F);
            projectile.setSize(Math.max(0.8F, random.nextFloat() * 2.5F));
            projectile.setOwner(owner);
         }

         if (entity instanceof BarrierEntity barrier) {
            barrier.setHealth(35.0F);
            barrier.setLife(120);
            barrier.setTickEachHit(2);
            barrier.setFollowOwner(false);
            barrier.setOwner(owner);
         }

         return entity;
      } else {
         return null;
      }
   }

   private static void runTensuraStorageStress(ServerPlayer player, RandomSource random) {
      IExistence existence = TensuraStorages.getExistenceFrom(player);
      existence.setSleepModeTime(Math.max(0, existence.getSleepModeTime() + (random.nextBoolean() ? 1 : -1)));
      existence.setSpiritualForm(random.nextBoolean());
      existence.markDirty();
      IEffect effect = TensuraStorages.getEffectFrom(player);
      effect.setIgnorePainNull(random.nextBoolean());
      effect.setSeveranceAmount(Math.max(0.0F, effect.getSeveranceAmount() + (random.nextFloat() - 0.5F)));
      effect.markDirty();
      ITensuraPlayer playerData = TensuraStorages.getPlayerDataFrom(player);
      safeInvoke(playerData, "setDodgeCooldown", int.class, random.nextInt(40));
      safeInvoke(playerData, "setPresenceSenseMode", int.class, random.nextInt(3));
      safeInvoke(playerData, "setAnalysisMode", int.class, random.nextInt(3));
      safeInvoke(playerData, "markDirty");
   }

   private static void runManascoreSkillApiReflectiveStress(ServerPlayer player, RandomSource random) {
      try {
         Class<?> skillApiClass = Class.forName("io.github.manasmods.manascore.skill.api.SkillAPI");
         Method getSkillsFrom = skillApiClass.getMethod("getSkillsFrom", LivingEntity.class);
         Object skills = getSkillsFrom.invoke(null, player);
         if (skills == null) {
            return;
         }

         Method getLearnedSkills = skills.getClass().getMethod("getLearnedSkills");
         Object learned = getLearnedSkills.invoke(skills);
         if (!(learned instanceof Iterable)) {
            return;
         }

         for (Object instance : (Iterable)learned) {
            safeInvoke(instance, "getModes");
            int modes = Math.max(1, asInt(safeInvoke(instance, "getModes"), 1));
            int mode = random.nextInt(modes);
            safeInvoke(instance, "setCoolDown", int.class, int.class, random.nextInt(80), mode);
            safeInvoke(instance, "decreaseCoolDown", int.class, int.class, 1, mode);
            safeInvoke(instance, "setToggled", boolean.class, safeInvoke(instance, "isToggled") instanceof Boolean b && !b);
            safeInvoke(instance, "addMasteryPoint", LivingEntity.class, double.class, player, random.nextDouble() * 0.25);
            Object tag = safeInvoke(instance, "getOrCreateTag");
            if (tag != null) {
               safeInvoke(tag, "putInt", String.class, int.class, "trlagbegon_tick", player.tickCount);
            }

            safeInvoke(instance, "onTick", LivingEntity.class, player);
            safeInvoke(instance, "onScroll", LivingEntity.class, double.class, int.class, player, random.nextDouble() * 2.0 - 1.0, mode);
            safeInvoke(instance, "onHeld", LivingEntity.class, int.class, int.class, player, random.nextInt(20), mode);
            safeInvoke(instance, "onRelease", LivingEntity.class, int.class, int.class, int.class, player, random.nextInt(20), 0, mode);
            safeInvoke(
               instance,
               "onTakenDamage",
               LivingEntity.class,
               DamageSource.class,
               changeableClass(),
               player,
               player.level().damageSources().magic(),
               changeableOf(0.5F)
            );
            safeInvoke(instance, "onBeingDamaged", LivingEntity.class, DamageSource.class, float.class, player, player.level().damageSources().magic(), 0.5F);
         }
      } catch (Throwable var14) {
      }
   }

   private static void runManascoreReflectiveEventStress(ServerLevel level, ServerPlayer player, RandomSource random) {
      invokeReflectiveEventBurst(
         "io.github.manasmods.manascore.skill.api.EntityEvents",
         new String[]{"LIVING_TICK", "LIVING_POST_TICK", "LIVING_HURT", "LIVING_EFFECT_ADDED", "PROJECTILE_HIT"},
         level,
         player,
         random,
         24
      );
      invokeReflectiveEventBurst(
         "io.github.manasmods.manascore.skill.api.SkillEvents",
         new String[]{
            "SKILL_PRE_TICK",
            "SKILL_POST_TICK",
            "SKILL_UPDATE_COOLDOWN",
            "SKILL_DAMAGE_PRE_CALCULATION",
            "SKILL_DAMAGE_CALCULATION",
            "SKILL_DAMAGE_POST_CALCULATION"
         },
         level,
         player,
         random,
         24
      );
   }

   private static void runTensuraEventReflectiveStress(ServerLevel level, ServerPlayer player, RandomSource random) {
      invokeReflectiveEventBurst(
         "io.github.manasmods.tensura.event.TensuraEntityEvents",
         new String[]{
            "LIVING_POST_DAMAGE",
            "FORCE_MOVEMENT_EVENT",
            "SPIRITUAL_HURT_EVENT",
            "ENERGY_DRAIN_EVENT",
            "INSTANT_TRANSMISSION_EVENT",
            "DIMENSION_TRAVEL_EVENT",
            "FORCE_TAME_EVENT",
            "POSSESSION_EVENT"
         },
         level,
         player,
         random,
         12
      );
      invokeReflectiveEventBurst(
         "io.github.manasmods.tensura.event.TensuraSkillEvents",
         new String[]{"SKILL_LEARNING", "ABILITY_EQUIP", "SKILL_GRIEF_PRE", "SKILL_GRIEF_POS", "SKILL_PLUNDER"},
         level,
         player,
         random,
         12
      );
   }

   private static void invokeReflectiveEventBurst(
      String className, String[] fieldNames, ServerLevel level, ServerPlayer player, RandomSource random, int budget
   ) {
      try {
         Class<?> cls = Class.forName(className);

         for (String name : fieldNames) {
            Field f = cls.getField(name);
            Object event = f.get(null);
            if (event != null) {
               Method invoker = event.getClass().getMethod("invoker");
               Object listener = invoker.invoke(event);
               if (listener != null) {
                  budget = invokeListenerBestEffort(listener, level, player, random, budget);
                  if (budget <= 0) {
                     return;
                  }
               }
            }
         }
      } catch (Throwable var15) {
      }
   }

   private static int invokeListenerBestEffort(Object listener, ServerLevel level, ServerPlayer player, RandomSource random, int budget) {
      for (Method method : listener.getClass().getMethods()) {
         if (method.getDeclaringClass() != Object.class) {
            try {
               Object[] args = new Object[method.getParameterCount()];
               Class<?>[] types = method.getParameterTypes();

               for (int i = 0; i < types.length; i++) {
                  args[i] = defaultArg(types[i], level, player, random);
               }

               method.invoke(listener, args);
               if (--budget <= 0) {
                  return 0;
               }
            } catch (Throwable var12) {
            }
         }
      }

      return budget;
   }

   private static Object defaultArg(Class<?> type, ServerLevel level, ServerPlayer player, RandomSource random) {
      if (type.isAssignableFrom(player.getClass()) || type == LivingEntity.class || type == Entity.class) {
         return player;
      } else if (type.isAssignableFrom(level.getClass())) {
         return level;
      } else if (type == float.class || type == Float.class) {
         return 0.5F;
      } else if (type == double.class || type == Double.class) {
         return 1.0;
      } else if (type == int.class || type == Integer.class) {
         return 1;
      } else if (type == boolean.class || type == Boolean.class) {
         return Boolean.TRUE;
      } else if (type == Vec3.class) {
         return new Vec3(random.nextDouble(), random.nextDouble(), random.nextDouble());
      } else {
         return type.getName().equals("io.github.manasmods.manascore.network.api.util.Changeable") ? changeableOf(null) : null;
      }
   }

   private static Object safeInvoke(Object target, String method, Class<?>[] signature, Object[] args) {
      try {
         Method m = target.getClass().getMethod(method, signature);
         return m.invoke(target, args);
      } catch (Throwable ignored) {
         return null;
      }
   }

   private static Object safeInvoke(Object target, String method, Object... args) {
      Class<?>[] sig = new Class[args.length];

      for (int i = 0; i < args.length; i++) {
         sig[i] = args[i].getClass();
      }

      return safeInvoke(target, method, sig, args);
   }

   private static Object safeInvoke(Object target, String method) {
      return safeInvoke(target, method, new Class[0], new Object[0]);
   }

   private static Object safeInvoke(Object target, String method, Class<?> p1, Object a1) {
      return safeInvoke(target, method, new Class[]{p1}, new Object[]{a1});
   }

   private static Object safeInvoke(Object target, String method, Class<?> p1, Class<?> p2, Object a1, Object a2) {
      return safeInvoke(target, method, new Class[]{p1, p2}, new Object[]{a1, a2});
   }

   private static Object safeInvoke(Object target, String method, Class<?> p1, Class<?> p2, Class<?> p3, Object a1, Object a2, Object a3) {
      return safeInvoke(target, method, new Class[]{p1, p2, p3}, new Object[]{a1, a2, a3});
   }

   private static Object safeInvoke(
      Object target, String method, Class<?> p1, Class<?> p2, Class<?> p3, Class<?> p4, Object a1, Object a2, Object a3, Object a4
   ) {
      return safeInvoke(target, method, new Class[]{p1, p2, p3, p4}, new Object[]{a1, a2, a3, a4});
   }

   private static Object safeInvoke(
      Object target, String method, Class<?> p1, Class<?> p2, Class<?> p3, Class<?> p4, Class<?> p5, Object a1, Object a2, Object a3, Object a4, Object a5
   ) {
      return safeInvoke(target, method, new Class[]{p1, p2, p3, p4, p5}, new Object[]{a1, a2, a3, a4, a5});
   }

   private static Object safeInvoke(
      Object target,
      String method,
      Class<?> p1,
      Class<?> p2,
      Class<?> p3,
      Class<?> p4,
      Class<?> p5,
      Class<?> p6,
      Object a1,
      Object a2,
      Object a3,
      Object a4,
      Object a5,
      Object a6
   ) {
      return safeInvoke(target, method, new Class[]{p1, p2, p3, p4, p5, p6}, new Object[]{a1, a2, a3, a4, a5, a6});
   }

   private static Class<?> changeableClass() {
      try {
         return Class.forName("io.github.manasmods.manascore.network.api.util.Changeable");
      } catch (ClassNotFoundException ignored) {
         return Object.class;
      }
   }

   private static Object changeableOf(Object value) {
      try {
         Class<?> clazz = changeableClass();
         Method of = clazz.getMethod("of", Object.class);
         return of.invoke(null, value);
      } catch (Throwable ignored) {
         return null;
      }
   }

   private static int asInt(Object value, int fallback) {
      return value instanceof Integer i ? i : fallback;
   }

   private static final class StressSession {
      private final UUID playerId;
      private int ticksLeft;
      private final int entitiesPerTick;

      private StressSession(UUID playerId, int ticksLeft, int entitiesPerTick) {
         this.playerId = playerId;
         this.ticksLeft = ticksLeft;
         this.entitiesPerTick = entitiesPerTick;
      }

      private UUID playerId() {
         return this.playerId;
      }

      private int entitiesPerTick() {
         return this.entitiesPerTick;
      }
   }
}
