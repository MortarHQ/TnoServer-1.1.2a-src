package cc.sighs.oelib.dev.example.net;

import cc.sighs.oelib.OELib;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.time.Instant;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Triple;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class BigPacketSender {
   private static final ScheduledExecutorService STRESS_TESTER = Executors.newSingleThreadScheduledExecutor();
   private static final BigPacketSender.Stats STATS = new BigPacketSender.Stats();

   public static void runStressTest(ServerPlayer player, int count, int intervalMs) {
      STATS.initGcMonitor();
      int preCount = 20;
      int preInterval = Math.max(20, intervalMs / 2);
      long warmupDurationMs = (long)preCount * preInterval;

      for (int i = 0; i < preCount; i++) {
         int delay = i * preInterval;
         STRESS_TESTER.schedule(() -> sendOne(player, false, 2, 0, 0, 0), delay, TimeUnit.MILLISECONDS);
      }

      AtomicInteger sent = new AtomicInteger(0);

      for (int i = 0; i < count; i++) {
         int idx = i + 1;
         long delay = warmupDurationMs + (long)i * intervalMs;
         STRESS_TESTER.schedule(() -> {
            sendOne(player, true, 2, idx, count, intervalMs);
            if (sent.incrementAndGet() == count) {
               STRESS_TESTER.schedule(BigPacketSender::printSummary, 500L, TimeUnit.MILLISECONDS);
            }
         }, delay, TimeUnit.MILLISECONDS);
      }
   }

   private static void sendOne(ServerPlayer player, boolean collect, int targetMb, int idx, int total, int intervalMs) {
      BigPacketSender.BuildResult br = createMegabytePacket(targetMb);
      if (br != null && player != null) {
         Runtime rt = Runtime.getRuntime();
         long usedBefore = rt.totalMemory() - rt.freeMemory();
         long enqueueNs = System.nanoTime();
         player.server
            .execute(
               () -> {
                  try {
                     br.packet.sendTo(player);
                     long usedAfter = rt.totalMemory() - rt.freeMemory();
                     double delayMs = (System.nanoTime() - enqueueNs) / 1000000.0;
                     double totalMs = br.buildMs + delayMs;
                     if (collect) {
                        STATS.recordSend(br.buildMs, totalMs, br.finalScore, usedBefore, usedAfter, br.firstBuild);
                     }

                     OELib.LOGGER
                        .info(
                           "[Send] {}/{} size={}MB build={}ms total={}ms score={}",
                           new Object[]{
                              idx,
                              total,
                              String.format("%.3f", br.mbSize),
                              String.format("%.3f", br.buildMs),
                              String.format("%.3f", totalMs),
                              String.format("%.2f", br.finalScore)
                           }
                        );
                  } catch (Throwable var16) {
                  }
               }
            );
      }
   }

   public static void recordReceive(double ms) {
      STATS.recordReceive(ms);
   }

   private static void printSummary() {
      String stability = STATS.stabilityLabel();
      OELib.LOGGER
         .info(
            "[Summary] avgBuild={}ms avgTotal={}ms maxScore={} minScore={} avgScore={} firstBuildAvg={}ms steadyStateAvg={}ms stability={} avgRecv={}ms heapDelta={}MB stabilityScore={} gcCount={} minGCInterval={}ms maxGCPause={}ms heapVar={}MB^2(每包堆增量方差)",
            new Object[]{
               String.format("%.3f", STATS.avgBuild()),
               String.format("%.3f", STATS.avgTotal()),
               String.format("%.2f", STATS.maxScore),
               String.format("%.2f", STATS.minScore),
               String.format("%.2f", STATS.avgScore()),
               String.format("%.3f", STATS.firstBuildMs),
               String.format("%.3f", STATS.steadyAvgBuild()),
               stability,
               String.format("%.3f", STATS.avgReceive()),
               String.format("%.3f", STATS.heapDeltaMB()),
               String.format("%.2f", STATS.stabilityScore()),
               STATS.gcCount,
               String.format("%.2f", STATS.minGcIntervalMs == Double.POSITIVE_INFINITY ? 0.0 : STATS.minGcIntervalMs),
               String.format("%.2f", STATS.maxGcPauseMs),
               String.format("%.3f", STATS.deltaVarianceMB())
            }
         );
      STATS.reset();
   }

   public static BigPacketSender.BuildResult createMegabytePacket(int targetMb) {
      byte[] megaBytes = new byte[targetMb * 1024 * 1024];
      new Random().nextBytes(megaBytes);
      return createBasePacket("SmallTestString", megaBytes);
   }

   private static BigPacketSender.BuildResult createBasePacket(String customString, byte[] customBytes) {
      long startNs = System.nanoTime();

      try {
         List<String> largeList = new ArrayList<>();

         for (int i = 0; i < 1000; i++) {
            largeList.add("String_" + i + "_" + generateRandomString(50));
         }

         Set<Integer> largeSet = new HashSet<>();

         for (int i = 0; i < 500; i++) {
            largeSet.add(i * 100);
         }

         Map<String, Long> largeMap = new HashMap<>();

         for (int i = 0; i < 300; i++) {
            largeMap.put("Key_" + i + "_" + generateRandomString(20), i * 1000L);
         }

         int[] largeIntArray = new int[500];

         for (int i = 0; i < largeIntArray.length; i++) {
            largeIntArray[i] = i * 100;
         }

         long[] largeLongArray = new long[400];

         for (int i = 0; i < largeLongArray.length; i++) {
            largeLongArray[i] = i * 1000L;
         }

         BitSet largeBitSet = new BitSet(1000);

         for (int i = 0; i < 1000; i++) {
            if (i % 3 == 0) {
               largeBitSet.set(i);
            }
         }

         KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
         keyGen.initialize(512);
         PublicKey publicKey = keyGen.generateKeyPair().getPublic();
         ObjectList<String> fastList = new ObjectArrayList();

         for (int i = 0; i < 800; i++) {
            fastList.add("FastUtil_String_" + i + "_" + generateRandomString(30));
         }

         ObjectSet<UUID> fastObjectSet = new ObjectOpenHashSet();

         for (int i = 0; i < 300; i++) {
            fastObjectSet.add(UUID.randomUUID());
         }

         IntSet fastIntSet = new IntOpenHashSet();

         for (int i = 0; i < 1000; i++) {
            fastIntSet.add(i * 10);
         }

         LongSet fastLongSet = new LongOpenHashSet();

         for (int i = 0; i < 600; i++) {
            fastLongSet.add(i * 100L);
         }

         Object2IntMap<String> fastO2I = new Object2IntOpenHashMap();

         for (int i = 0; i < 200; i++) {
            fastO2I.put("O2I_Key_" + i, i * 100);
         }

         Object2LongMap<String> fastO2L = new Object2LongOpenHashMap();

         for (int i = 0; i < 200; i++) {
            fastO2L.put("O2L_Key_" + i, i * 1000L);
         }

         Object2ObjectMap<String, UUID> fastO2O = new Object2ObjectOpenHashMap();

         for (int i = 0; i < 150; i++) {
            fastO2O.put("O2O_Key_" + i, UUID.randomUUID());
         }

         Int2ObjectMap<String> fastI2O = new Int2ObjectOpenHashMap();

         for (int i = 0; i < 400; i++) {
            fastI2O.put(i * 5, "I2O_Value_" + i);
         }

         Int2IntMap fastI2I = new Int2IntOpenHashMap();

         for (int i = 0; i < 500; i++) {
            fastI2I.put(i, i * 2);
         }

         Long2ObjectMap<String> fastL2O = new Long2ObjectOpenHashMap();

         for (int i = 0; i < 300; i++) {
            fastL2O.put(i * 100L, "L2O_Value_" + i);
         }

         IntList fastIntList = new IntArrayList();

         for (int i = 0; i < 1000; i++) {
            fastIntList.add(i * 3);
         }

         BigComponentPacket packet = new BigComponentPacket(
            42,
            4242,
            99999999999L,
            88888888888L,
            true,
            false,
            3.14159F,
            2.71828F,
            1.41421356,
            1.7320508,
            (byte)127,
            (byte)-128,
            (short)32767,
            (short)-32768,
            customString,
            UUID.randomUUID(),
            new Date(),
            Instant.now(),
            largeBitSet,
            publicKey,
            customBytes,
            largeIntArray,
            largeLongArray,
            new BlockPos(100, 64, -200),
            new ChunkPos(10, -5),
            SectionPos.of(new BlockPos(0, 64, 0)),
            GlobalPos.of(ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("overworld")), new BlockPos(100, 70, 100)),
            new Vector3f(1.0F, 2.0F, 3.0F),
            new Quaternionf(),
            ResourceLocation.fromNamespaceAndPath("oelib", "test"),
            ResourceKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("stone")),
            new BlockHitResult(new Vec3(0.5, 1.0, 0.5), Direction.UP, new BlockPos(0, 64, 0), false),
            createLargeCompoundTag(),
            BigComponentPacket.ExampleEnum.B,
            new BigComponentPacket.NestedRecord("NestedA", 123, ResourceLocation.fromNamespaceAndPath("oelib", "test_nested")),
            Optional.of("OptionalString"),
            Pair.of(42, "PairValue"),
            org.apache.commons.lang3.tuple.Pair.of(123456789L, UUID.randomUUID()),
            Either.left("EitherLeftValue"),
            Triple.of("First", 2, 3L),
            largeList,
            largeSet,
            largeMap,
            EnumSet.allOf(BigComponentPacket.ExampleEnum.class),
            fastList,
            fastObjectSet,
            fastIntSet,
            fastLongSet,
            fastO2I,
            fastO2L,
            fastO2O,
            fastI2O,
            fastI2I,
            fastL2O,
            fastIntList
         );
         double buildMs = (System.nanoTime() - startNs) / 1000000.0;
         int fieldCount = 55;
         int complexStructures = 15;
         double mbSize = customBytes.length / 1048576.0;
         double scoreBase = fieldCount * 1.2 + complexStructures * 2.5 + mbSize * 5.0;
         double efficiencyFactor = 1.0 / (1.0 + Math.pow(buildMs / 5.0, 2.0));
         double finalScore = scoreBase * efficiencyFactor;
         boolean first = STATS.markFirstIfNeeded(buildMs);
         return new BigPacketSender.BuildResult(packet, buildMs, finalScore, mbSize, first);
      } catch (Throwable t) {
         return null;
      }
   }

   private static String generateRandomString(int length) {
      String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
      StringBuilder sb = new StringBuilder(length);
      Random random = new Random();

      for (int i = 0; i < length; i++) {
         sb.append(chars.charAt(random.nextInt(chars.length())));
      }

      return sb.toString();
   }

   private static CompoundTag createLargeCompoundTag() {
      CompoundTag tag = new CompoundTag();

      for (int i = 0; i < 100; i++) {
         tag.putString("StringKey_" + i, "Value_" + i + "_" + generateRandomString(20));
         tag.putInt("IntKey_" + i, i * 100);
         tag.putLong("LongKey_" + i, i * 1000L);
         tag.putFloat("FloatKey_" + i, i * 1.5F);
         tag.putDouble("DoubleKey_" + i, i * 2.5);
         tag.putByteArray("ByteArrayKey_" + i, new byte[]{(byte)i, (byte)(i + 1), (byte)(i + 2)});
         if (i % 10 == 0) {
            CompoundTag nested = new CompoundTag();
            nested.putString("Nested", "Level2_" + i);
            tag.put("CompoundKey_" + i, nested);
         }
      }

      return tag;
   }

   public record BuildResult(BigComponentPacket packet, double buildMs, double finalScore, double mbSize, boolean firstBuild) {
   }

   private static final class Stats {
      private double buildSum;
      private double buildSqSum;
      private int buildCount;
      private double totalSum;
      private int totalCount;
      private double scoreSum;
      private int scoreCount;
      private double minScore = Double.POSITIVE_INFINITY;
      private double maxScore = Double.NEGATIVE_INFINITY;
      private double firstBuildMs = -1.0;
      private double recvSum;
      private int recvCount;
      private long minUsed = Long.MAX_VALUE;
      private long maxUsed = Long.MIN_VALUE;
      private double deltaSumMB;
      private double deltaSqSumMB;
      private int deltaCount;
      private boolean gcInitialized;
      private long lastGcEndMillis = -1L;
      private double minGcIntervalMs = Double.POSITIVE_INFINITY;
      private double maxGcPauseMs = 0.0;
      private int gcCount;

      synchronized boolean markFirstIfNeeded(double buildMs) {
         if (this.firstBuildMs < 0.0) {
            this.firstBuildMs = buildMs;
            return true;
         } else {
            return false;
         }
      }

      synchronized void recordSend(double buildMs, double totalMs, double score, long usedBefore, long usedAfter, boolean first) {
         this.buildSum += buildMs;
         this.buildSqSum += buildMs * buildMs;
         this.buildCount++;
         this.totalSum += totalMs;
         this.totalCount++;
         this.scoreSum += score;
         this.scoreCount++;
         this.minScore = Math.min(this.minScore, score);
         this.maxScore = Math.max(this.maxScore, score);
         this.minUsed = Math.min(this.minUsed, usedAfter);
         this.maxUsed = Math.max(this.maxUsed, usedAfter);
         double deltaMB = (usedAfter - usedBefore) / 1048576.0;
         this.deltaSumMB += deltaMB;
         this.deltaSqSumMB += deltaMB * deltaMB;
         this.deltaCount++;
      }

      synchronized void recordReceive(double ms) {
         this.recvSum += ms;
         this.recvCount++;
      }

      synchronized double avgBuild() {
         return this.buildCount == 0 ? 0.0 : this.buildSum / this.buildCount;
      }

      synchronized double steadyAvgBuild() {
         return this.buildCount > 1 && !(this.firstBuildMs < 0.0) ? (this.buildSum - this.firstBuildMs) / (this.buildCount - 1) : 0.0;
      }

      synchronized double avgTotal() {
         return this.totalCount == 0 ? 0.0 : this.totalSum / this.totalCount;
      }

      synchronized double avgScore() {
         return this.scoreCount == 0 ? 0.0 : this.scoreSum / this.scoreCount;
      }

      synchronized double avgReceive() {
         return this.recvCount == 0 ? 0.0 : this.recvSum / this.recvCount;
      }

      synchronized double heapDeltaMB() {
         return this.minUsed != Long.MAX_VALUE && this.maxUsed != Long.MIN_VALUE ? (this.maxUsed - this.minUsed) / 1048576.0 : 0.0;
      }

      synchronized double projectedHeapDeltaMB(long usedAfter) {
         long min = Math.min(this.minUsed == Long.MAX_VALUE ? usedAfter : this.minUsed, usedAfter);
         long max = Math.max(this.maxUsed == Long.MIN_VALUE ? usedAfter : this.maxUsed, usedAfter);
         return (max - min) / 1048576.0;
      }

      synchronized double deltaAvgMB() {
         return this.deltaCount == 0 ? 0.0 : this.deltaSumMB / this.deltaCount;
      }

      synchronized double deltaVarianceMB() {
         if (this.deltaCount == 0) {
            return 0.0;
         }

         double mean = this.deltaSumMB / this.deltaCount;
         return Math.max(0.0, this.deltaSqSumMB / this.deltaCount - mean * mean);
      }

      synchronized String stabilityLabel() {
         if (this.buildCount <= 1) {
            return "unknown";
         } else {
            double mean = this.buildSum / this.buildCount;
            double var = Math.max(0.0, this.buildSqSum / this.buildCount - mean * mean);
            double sd = Math.sqrt(var);
            double cv = mean == 0.0 ? 0.0 : sd / mean;
            if (cv < 0.1) {
               return "稳定";
            } else {
               return cv < 0.2 ? "较稳定" : "波动较大";
            }
         }
      }

      synchronized double stabilityScore() {
         double score = 100.0;
         double varPenalty = Math.min(40.0, this.deltaVarianceMB() * 4.0);
         score -= varPenalty;
         double intervalPenalty = 0.0;
         if (this.minGcIntervalMs != Double.POSITIVE_INFINITY) {
            intervalPenalty = Math.min(30.0, Math.max(0.0, 50.0 - this.minGcIntervalMs) * 0.6);
         }

         score -= intervalPenalty;
         double pausePenalty = Math.min(30.0, Math.max(0.0, this.maxGcPauseMs - 50.0) * 0.5);
         score -= pausePenalty;
         if (score < 0.0) {
            score = 0.0;
         }

         return score;
      }

      synchronized void initGcMonitor() {
         if (!this.gcInitialized) {
            this.gcInitialized = true;

            for (GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans()) {
               if (gcBean instanceof NotificationEmitter emitter) {
                  NotificationListener listener = (notification, handback) -> {
                     if ("com.sun.management.gc.notification".equals(notification.getType())) {
                        try {
                           CompositeData cd = (CompositeData)notification.getUserData();
                           GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from(cd);
                           GcInfo gcInfo = info.getGcInfo();
                           long startTime = gcInfo.getStartTime();
                           long endTime = gcInfo.getEndTime();
                           double pauseMs = gcInfo.getDuration();
                           this.gcCount++;
                           if (this.lastGcEndMillis >= 0L) {
                              double interval = startTime - this.lastGcEndMillis;
                              if (interval > 0.0 && interval < this.minGcIntervalMs) {
                                 this.minGcIntervalMs = interval;
                              }
                           }

                           if (pauseMs > this.maxGcPauseMs) {
                              this.maxGcPauseMs = pauseMs;
                           }

                           this.lastGcEndMillis = endTime;
                        } catch (Throwable var14) {
                        }
                     }
                  };
                  emitter.addNotificationListener(listener, null, null);
               }
            }
         }
      }

      synchronized void reset() {
         this.buildSum = 0.0;
         this.buildSqSum = 0.0;
         this.buildCount = 0;
         this.totalSum = 0.0;
         this.totalCount = 0;
         this.scoreSum = 0.0;
         this.scoreCount = 0;
         this.minScore = Double.POSITIVE_INFINITY;
         this.maxScore = Double.NEGATIVE_INFINITY;
         this.recvSum = 0.0;
         this.recvCount = 0;
         this.minUsed = Long.MAX_VALUE;
         this.maxUsed = Long.MIN_VALUE;
         this.deltaSumMB = 0.0;
         this.deltaSqSumMB = 0.0;
         this.deltaCount = 0;
         this.lastGcEndMillis = -1L;
         this.minGcIntervalMs = Double.POSITIVE_INFINITY;
         this.maxGcPauseMs = 0.0;
         this.gcCount = 0;
      }
   }
}
