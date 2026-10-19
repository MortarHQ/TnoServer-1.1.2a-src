package cn.star.structblend;

import cn.star.structblend.terrain.ChunkHeightBatch;
import cn.star.structblend.terrain.HeightQueryDiagnostics;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

@Mod("structblend")
public final class Structblend {
   public static final String MODID = "structblend";
   private long nextDiagnosticsReport;

   public Structblend(ModContainer container) {
      container.registerConfig(Type.COMMON, Config.SPEC);
      NeoForge.EVENT_BUS.addListener(event -> ChunkHeightBatch.logStatus());
      if (HeightQueryDiagnostics.ENABLED) {
         this.nextDiagnosticsReport = System.nanoTime() + 60000000000L;
         NeoForge.EVENT_BUS.addListener(this::reportDiagnostics);
         NeoForge.EVENT_BUS.addListener(this::reportFinalDiagnostics);
         LogUtils.getLogger().info("[StructBlend Heights] diagnostics enabled; cumulative report every 60s and at server stop");
      }
   }

   private void reportDiagnostics(Post event) {
      long now = System.nanoTime();
      if (now - this.nextDiagnosticsReport >= 0L) {
         this.nextDiagnosticsReport = now + 60000000000L;
         LogUtils.getLogger().info("[StructBlend Heights] {}", HeightQueryDiagnostics.summary());
      }
   }

   private void reportFinalDiagnostics(ServerStoppedEvent event) {
      LogUtils.getLogger().info("[StructBlend Heights] final {}", HeightQueryDiagnostics.summary());
   }
}
