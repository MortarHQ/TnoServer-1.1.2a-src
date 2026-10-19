package com.github.wal_bos.moreskills.client.fx.avalon;

import com.github.wal_bos.moreskills.client.fx.ArthurAvalonVfxChannel;
import com.github.wal_bos.moreskills.client.fx.ClientVfxGate;
import com.github.wal_bos.moreskills.handler.avalon.AvalonKingdomHandler;
import com.github.wal_bos.moreskills.net.msg.ArthurAvalonVfxData;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.common.NeoForge;

public final class AvalonKingdomVfxClient {
   private static final ArthurAvalonVfxChannel CHANNEL = new ArthurAvalonVfxChannel("avalon_kingdom", ArthurAvalonVfxChannel.Style.DOMAIN, 255, 214, 62);
   private static boolean initialized;

   private AvalonKingdomVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         CHANNEL.init();
         NeoForge.EVENT_BUS.addListener(AvalonKingdomVfxClient::tick);
      }
   }

   public static void spawn(ArthurAvalonVfxData payload) {
      CHANNEL.spawn(payload);
   }

   private static void tick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (ClientVfxGate.allowed("avalon_kingdom")
         && minecraft.level != null
         && minecraft.player != null
         && minecraft.level.dimension().equals(AvalonKingdomHandler.AVALON)) {
         Player player = minecraft.player;
         RandomSource random = minecraft.level.random;

         for (int i = 0; i < 7; i++) {
            double angle = random.nextDouble() * Math.PI * 2.0;
            double distance = 4.0 + random.nextDouble() * 34.0;
            double x = player.getX() + Math.cos(angle) * distance;
            double y = player.getY() + random.nextDouble() * 18.0 - 2.0;
            double z = player.getZ() + Math.sin(angle) * distance;
            minecraft.level.addParticle(i % 3 == 0 ? ParticleTypes.END_ROD : ParticleTypes.WAX_ON, x, y, z, 0.0, 0.01 + random.nextDouble() * 0.025, 0.0);
         }

         if (player.tickCount % 4 == 0) {
            double angle = random.nextDouble() * Math.PI * 2.0;
            double distance = 10.0 + random.nextDouble() * 44.0;
            minecraft.level
               .addParticle(
                  ParticleTypes.FIREWORK,
                  player.getX() + Math.cos(angle) * distance,
                  player.getY() + 8.0 + random.nextDouble() * 24.0,
                  player.getZ() + Math.sin(angle) * distance,
                  0.0,
                  -0.035,
                  0.0
               );
         }
      }
   }
}
