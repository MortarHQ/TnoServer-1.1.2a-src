package de.ellpeck.naturesaura.renderers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import de.ellpeck.naturesaura.NaturesAura;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@OnlyIn(Dist.CLIENT)
public class SupporterFancyHandler {
   public static final Map<String, SupporterFancyHandler.FancyInfo> FANCY_INFOS = new HashMap<>();

   public SupporterFancyHandler() {
      new SupporterFancyHandler.FetchThread();
      NeoForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (player.level().isClientSide) {
         if (!player.isInvisible() && player.isModelPartShown(PlayerModelPart.CAPE)) {
            Minecraft mc = Minecraft.getInstance();
            if (player != mc.player || mc.options.getCameraType() != CameraType.FIRST_PERSON) {
               SupporterFancyHandler.FancyInfo info = FANCY_INFOS.get(player.getGameProfile().getName());
               if (info != null) {
                  RandomSource rand = player.level().random;
                  if (rand.nextFloat() >= 0.75F) {
                     int color;
                     if (info.tier == 1) {
                        BlockPos pos = player.blockPosition();
                        color = BiomeColors.getAverageGrassColor(player.level(), pos);
                     } else {
                        color = info.color;
                     }

                     NaturesAuraAPI.instance()
                        .spawnMagicParticle(
                           player.getX() + rand.nextGaussian() * 0.15F,
                           player.getY() + rand.nextFloat() * 1.8F,
                           player.getZ() + rand.nextGaussian() * 0.15F,
                           rand.nextGaussian() * 0.01F,
                           rand.nextFloat() * 0.01F,
                           rand.nextGaussian() * 0.01F,
                           color,
                           rand.nextFloat() + 1.0F,
                           rand.nextInt(50) + 50,
                           0.0F,
                           false,
                           true
                        );
                  }
               }
            }
         }
      }
   }

   public record FancyInfo(int tier, int color) {
   }

   private static class FetchThread extends Thread {
      public FetchThread() {
         this.setName("naturesaura_support_fetcher");
         this.setDaemon(true);
         this.start();
      }

      @Override
      public void run() {
         try {
            URI url = new URI("https://raw.githubusercontent.com/Ellpeck/NaturesAura/main/supporters.json");
            JsonReader reader = new JsonReader(new InputStreamReader(url.toURL().openStream()));
            JsonObject main = JsonParser.parseReader(reader).getAsJsonObject();

            for (Entry<String, JsonElement> entry : main.entrySet()) {
               JsonObject object = entry.getValue().getAsJsonObject();
               int tier = object.get("tier").getAsInt();
               int color = object.has("color") ? Integer.parseInt(object.get("color").getAsString(), 16) : 0;
               SupporterFancyHandler.FANCY_INFOS.put(entry.getKey(), new SupporterFancyHandler.FancyInfo(tier, color));
            }

            reader.close();
         } catch (Exception e) {
            NaturesAura.LOGGER.warn("Fetching supporter information failed", e);
         }
      }
   }
}
