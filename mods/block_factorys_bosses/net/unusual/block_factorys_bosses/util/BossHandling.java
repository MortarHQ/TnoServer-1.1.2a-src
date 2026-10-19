package net.unusual.block_factorys_bosses.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.SelectMusicEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.StartTracking;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.StopTracking;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.configuration.ClientConfiguration;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.network.UpdateBossBarTypeMessage;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossHandling {
   public static final BossHandling.BossBar[] INFO = new BossHandling.BossBar[]{
      new BossHandling.BossBar("warden", 288, 76, 53, 182),
      new BossHandling.BossBar("ender_dragon", 288, 76, 53, 182),
      new BossHandling.BossBar("wither", 288, 76, 53, 182),
      new BossHandling.BossBar("yeti", 288, 76, 53, 182),
      new BossHandling.BossBar("dragon", 288, 76, 53, 182),
      new BossHandling.BossBar("worm", 288, 76, 53, 182),
      new BossHandling.BossBar("knight", 288, 76, 53, 182),
      new BossHandling.BossBar(
         bossBarTexture("knight", "complex_resurrected"), bossBarTexture("knight", "simple"), bossBarTexture("knight", "bar_resurrected"), 288, 76, 53, 182
      ),
      new BossHandling.BossBar(
         bossBarTexture("kraken", "complex_phase_1"), bossBarTexture("kraken", "simple_phase_1"), bossBarTexture("kraken", "bar"), 288, 76, 53, 182
      ),
      new BossHandling.BossBar(
         bossBarTexture("kraken", "complex_phase_2"), bossBarTexture("kraken", "simple_phase_2"), bossBarTexture("kraken", "bar"), 288, 76, 53, 182
      )
   };
   public static final Map<UUID, BossHandling.TrackedBoss> TRACKED_BOSSES = new HashMap<>();
   public static final int VANILLA_BOSS = 2;
   public static final int WARDEN = 0;
   public static final int ENDER_DRAGON = 1;
   public static final int WITHER = 2;
   public static final int YETI = 3;
   public static final int DRAGON = 4;
   public static final int SANDWORM = 5;
   public static final int KNIGHT = 6;
   public static final int KNIGHT_TRANSFORMED = 7;
   public static final int KRAKEN_PHASE_1 = 8;
   public static final int KRAKEN_PHASE_2 = 9;

   public static ResourceLocation bossBarTexture(String boss, String textureVariant) {
      return BossesRise.prefix("textures/gui/bossbar/" + boss + "/" + textureVariant + ".png");
   }

   public static void updateBossType(ServerBossEvent bossEvent, int newBossType) {
      BossHandling.TrackedBoss trackedBoss = TRACKED_BOSSES.get(bossEvent.getId());
      if (trackedBoss != null) {
         BossHandling.TrackedBoss augmented = new BossHandling.TrackedBoss(newBossType, trackedBoss.playingMusic());
         TRACKED_BOSSES.put(bossEvent.getId(), augmented);
         bossEvent.getPlayers().forEach(player -> player.connection.send(new UpdateBossBarTypeMessage(bossEvent.getId(), augmented)));
      }
   }

   public record BossBar(
      ResourceLocation complexBase, ResourceLocation simpleBase, ResourceLocation healthBar, int texWidth, int texHeight, int barStart, int barLength
   ) {
      public BossBar(String boss, int texWidth, int texHeight, int barStart, int barLength) {
         this(
            BossHandling.bossBarTexture(boss, "complex"),
            BossHandling.bossBarTexture(boss, "simple"),
            BossHandling.bossBarTexture(boss, "bar"),
            texWidth,
            texHeight,
            barStart,
            barLength
         );
      }
   }

   @EventBusSubscriber(Dist.CLIENT)
   public static class ClientHandler {
      public static final BossHandling.ClientHandler.BossMusic HELVAR_MUSIC = new BossHandling.ClientHandler.BossMusic(BossesRiseSounds.MUSIC_HELVAR);
      public static final BossHandling.ClientHandler.BossMusic ASHLORD_MUSIC = new BossHandling.ClientHandler.BossMusic(BossesRiseSounds.MUSIC_ASHLORD);
      public static final BossHandling.ClientHandler.BossMusic SKOR_MUSIC = new BossHandling.ClientHandler.BossMusic(BossesRiseSounds.MUSIC_SKOR);
      public static final BossHandling.ClientHandler.BossMusic SIROK_MUSIC = new BossHandling.ClientHandler.BossMusic(BossesRiseSounds.MUSIC_SIROK);
      public static final BossHandling.ClientHandler.BossMusic NERAKYSS_MUSIC = new BossHandling.ClientHandler.BossMusic(BossesRiseSounds.MUSIC_NERAKYSS);

      @SubscribeEvent(priority = EventPriority.LOWEST)
      public static void renderLayer(BossEventProgress event) {
         LerpingBossEvent bossEvent = event.getBossEvent();
         BossHandling.TrackedBoss boss = BossHandling.TRACKED_BOSSES.getOrDefault(event.getBossEvent().getId(), null);
         if (boss != null) {
            int type = boss.type();

            BossHandling.HealthBarStyle style = switch (type) {
               case 0 -> ((BossHandling.HealthBarStyle)ClientConfiguration.WARDEN_BOSSBAR.get()).resolve();
               case 1 -> ((BossHandling.HealthBarStyle)ClientConfiguration.ENDER_DRAGON_BOSSBAR.get()).resolve();
               case 2 -> ((BossHandling.HealthBarStyle)ClientConfiguration.WITHER_BOSSBAR.get()).resolve();
               case 3 -> ((BossHandling.HealthBarStyle)ClientConfiguration.YETI_BOSSBAR.get()).resolve();
               case 4 -> ((BossHandling.HealthBarStyle)ClientConfiguration.DRAGON_BOSSBAR.get()).resolve();
               case 5 -> ((BossHandling.HealthBarStyle)ClientConfiguration.SANDWORM_BOSSBAR.get()).resolve();
               case 6, 7 -> ((BossHandling.HealthBarStyle)ClientConfiguration.KNIGHT_BOSSBAR.get()).resolve();
               case 8, 9 -> ((BossHandling.HealthBarStyle)ClientConfiguration.KRAKEN_BOSSBAR.get()).resolve();
               default -> BossHandling.HealthBarStyle.VANILLA;
            };
            if (style == BossHandling.HealthBarStyle.VANILLA) {
               return;
            }

            if (style == BossHandling.HealthBarStyle.OFF) {
               event.setCanceled(true);
               event.setIncrement(0);
               return;
            }

            event.setCanceled(true);
            event.setIncrement(46);
            BossHandling.BossBar barInfo = BossHandling.INFO[type];
            GuiGraphics guiGraphics = event.getGuiGraphics();
            RenderSystem.enableBlend();
            guiGraphics.pose().pushPose();
            int progressScaled = (int)(barInfo.barStart() + bossEvent.getProgress() * barInfo.barLength());
            if (style == BossHandling.HealthBarStyle.FANCY) {
               guiGraphics.blit(
                  barInfo.complexBase(),
                  (Minecraft.getInstance().getWindow().getGuiScaledWidth() - 280) / 2,
                  event.getY() - 14,
                  0.0F,
                  0.0F,
                  barInfo.texWidth(),
                  barInfo.texHeight(),
                  barInfo.texWidth(),
                  barInfo.texHeight()
               );
            } else {
               guiGraphics.blit(
                  barInfo.simpleBase(),
                  (Minecraft.getInstance().getWindow().getGuiScaledWidth() - 280) / 2,
                  event.getY() - 14,
                  0.0F,
                  0.0F,
                  barInfo.texWidth(),
                  barInfo.texHeight(),
                  barInfo.texWidth(),
                  barInfo.texHeight()
               );
            }

            guiGraphics.blit(
               barInfo.healthBar(),
               (Minecraft.getInstance().getWindow().getGuiScaledWidth() - 280) / 2,
               event.getY() - 14,
               0.0F,
               0.0F,
               progressScaled,
               barInfo.texHeight(),
               barInfo.texWidth(),
               barInfo.texHeight()
            );
            guiGraphics.pose().popPose();
            RenderSystem.disableBlend();
         }
      }

      @SubscribeEvent
      public static void onMusicCheck(SelectMusicEvent event) {
         for (Entry<UUID, LerpingBossEvent> entry : Minecraft.getInstance().gui.getBossOverlay().events.entrySet()) {
            LerpingBossEvent lerpingBossEvent = entry.getValue();
            BossHandling.TrackedBoss boss = BossHandling.TRACKED_BOSSES.getOrDefault(lerpingBossEvent.getId(), null);
            if (boss != null && boss.playingMusic) {
               switch (boss.type) {
                  case 3:
                     event.overrideMusic(SKOR_MUSIC);
                     return;
                  case 4:
                     event.overrideMusic(ASHLORD_MUSIC);
                     return;
                  case 5:
                     event.overrideMusic(SIROK_MUSIC);
                     return;
                  case 6:
                     event.overrideMusic(HELVAR_MUSIC);
                     return;
                  case 7:
                  default:
                     break;
                  case 8:
                  case 9:
                     event.overrideMusic(NERAKYSS_MUSIC);
                     return;
               }
            }
         }

         if (isBossMusic(event.getPlayingMusic())) {
            event.setMusic(null);
         }
      }

      public static boolean isBossMusic(@Nullable SoundInstance instance) {
         if (instance == null) {
            return false;
         }

         ResourceLocation location = instance.getLocation();
         return BossHandling.ClientHandler.BossMusic.contains(location);
      }

      public static class BossMusic extends Music {
         public static final List<Holder<SoundEvent>> BOSS_TRACKS = new ArrayList<>();
         public static final List<ResourceLocation> SUPPLIED_BOSS_TRACKS = new ArrayList<>();

         public BossMusic(Holder<SoundEvent> event) {
            super(event, 0, 0, true);
            BOSS_TRACKS.add(event);
         }

         public static boolean contains(ResourceLocation location) {
            if (SUPPLIED_BOSS_TRACKS.isEmpty()) {
               for (Holder<SoundEvent> holder : BOSS_TRACKS) {
                  if (!holder.isBound()) {
                     SUPPLIED_BOSS_TRACKS.clear();
                     return false;
                  }

                  SUPPLIED_BOSS_TRACKS.add(((SoundEvent)holder.value()).getLocation());
               }
            }

            return SUPPLIED_BOSS_TRACKS.contains(location);
         }
      }
   }

   @EventBusSubscriber
   public static class CommonHandler {
      public static Map<UUID, ServerBossEvent> WARDENS = new HashMap<>();

      public static ServerBossEvent getOrCreate(Warden warden) {
         ServerBossEvent bossEvent = WARDENS.get(warden.getUUID());
         if (bossEvent == null) {
            bossEvent = new ServerBossEvent(warden.getDisplayName(), BossBarColor.BLUE, BossBarOverlay.PROGRESS);
            BossHandling.TRACKED_BOSSES.put(bossEvent.getId(), new BossHandling.TrackedBoss(0, false));
            WARDENS.put(warden.getUUID(), bossEvent);
         }

         return bossEvent;
      }

      @SubscribeEvent
      public static void serverTick(Post event) {
         if (!WARDENS.isEmpty()) {
            List<UUID> toRemove = new ArrayList<>();

            for (UUID id : WARDENS.keySet()) {
               boolean present = false;

               for (ServerLevel level : event.getServer().getAllLevels()) {
                  Entity warden = level.getEntity(id);
                  if (warden != null && !warden.isRemoved()) {
                     present = true;
                     break;
                  }
               }

               if (!present) {
                  toRemove.add(id);
               }
            }

            toRemove.forEach(WARDENS::remove);
         }
      }

      @SubscribeEvent
      public static void onStart(StartTracking event) {
         if (event.getEntity() instanceof ServerPlayer player && event.getTarget() instanceof Warden warden) {
            getOrCreate(warden).addPlayer(player);
         }
      }

      @SubscribeEvent
      public static void onStop(StopTracking event) {
         if (event.getEntity() instanceof ServerPlayer player && event.getTarget() instanceof Warden warden) {
            ServerBossEvent bossEvent = WARDENS.get(warden.getUUID());
            if (bossEvent != null) {
               bossEvent.removePlayer(player);
            }
         }
      }

      @SubscribeEvent
      public static void onDeath(LivingDeathEvent event) {
         if (event.getEntity() instanceof Warden warden) {
            ServerBossEvent bossEvent = WARDENS.get(warden.getUUID());
            if (bossEvent != null) {
               bossEvent.setProgress(0.0F);
            }
         }
      }
   }

   public enum HealthBarDefaultStyle implements StringRepresentable {
      OFF(BossHandling.HealthBarStyle.OFF),
      VANILLA(BossHandling.HealthBarStyle.VANILLA),
      SIMPLE(BossHandling.HealthBarStyle.SIMPLE),
      FANCY(BossHandling.HealthBarStyle.FANCY);

      private final BossHandling.HealthBarStyle style;

      HealthBarDefaultStyle(BossHandling.HealthBarStyle style) {
         this.style = style;
      }

      public BossHandling.HealthBarStyle getStyle() {
         return this.style;
      }

      public String getSerializedName() {
         return this.toString().toLowerCase(Locale.ROOT);
      }
   }

   public enum HealthBarStyle implements StringRepresentable {
      DEFAULT,
      OFF,
      VANILLA,
      SIMPLE,
      FANCY;

      public BossHandling.HealthBarStyle resolve() {
         return this == DEFAULT ? ((BossHandling.HealthBarDefaultStyle)ClientConfiguration.DEFAULT_BOSSBAR.get()).getStyle() : this;
      }

      public String getSerializedName() {
         return this.toString().toLowerCase(Locale.ROOT);
      }
   }

   public record TrackedBoss(int type, boolean playingMusic) {
      public static final Codec<BossHandling.TrackedBoss> CODEC = RecordCodecBuilder.create(
         recordCodecBuilder -> recordCodecBuilder.group(
               Codec.INT.fieldOf("type").forGetter(BossHandling.TrackedBoss::type),
               Codec.BOOL.fieldOf("playing_music").forGetter(BossHandling.TrackedBoss::playingMusic)
            )
            .apply(recordCodecBuilder, BossHandling.TrackedBoss::new)
      );

      public BossHandling.TrackedBoss setMusic(boolean music) {
         return new BossHandling.TrackedBoss(this.type, music);
      }
   }
}
