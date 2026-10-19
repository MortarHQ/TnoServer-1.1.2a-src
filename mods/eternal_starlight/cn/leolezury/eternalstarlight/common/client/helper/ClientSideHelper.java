package cn.leolezury.eternalstarlight.common.client.helper;

import cn.leolezury.eternalstarlight.common.client.book.BookDefinition;
import cn.leolezury.eternalstarlight.common.client.book.component.BookComponentRegistry;
import cn.leolezury.eternalstarlight.common.client.book.component.ConfiguredBookComponent;
import cn.leolezury.eternalstarlight.common.client.book.component.IndexBookComponent;
import cn.leolezury.eternalstarlight.common.client.gui.screen.BookScreen;
import cn.leolezury.eternalstarlight.common.client.gui.screen.CrestSelectionScreen;
import cn.leolezury.eternalstarlight.common.client.gui.screen.GatekeeperDialogueScreen;
import cn.leolezury.eternalstarlight.common.client.gui.toast.SimpleTextToast;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.client.particle.advanced.AdvancedParticleOptions;
import cn.leolezury.eternalstarlight.common.client.weather.ClientWeatherState;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.entity.projectile.SoulitSpectator;
import cn.leolezury.eternalstarlight.common.item.component.GuideBook;
import cn.leolezury.eternalstarlight.common.network.ClientDismountPacket;
import cn.leolezury.eternalstarlight.common.network.ClientMountPacket;
import cn.leolezury.eternalstarlight.common.network.OpenBookPacket;
import cn.leolezury.eternalstarlight.common.network.OpenCrestGuiPacket;
import cn.leolezury.eternalstarlight.common.network.OpenGatekeeperGuiPacket;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.network.SimpleActionPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateBookPacket;
import cn.leolezury.eternalstarlight.common.network.UpdateCameraPacket;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import cn.leolezury.eternalstarlight.common.util.Color;
import cn.leolezury.eternalstarlight.common.util.Easing;
import cn.leolezury.eternalstarlight.common.util.SmoothSegmentedValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;

public class ClientSideHelper implements ClientHelper {
   @Override
   public void handleServerToClientSimpleAction(SimpleActionPacket packet) {
      switch (packet.id()) {
         case "clear_weather":
            ClientWeatherState.weather = null;
      }
   }

   @Override
   public void handleParticlePacket(ParticlePacket packet) {
      ClientLevel clientLevel = Minecraft.getInstance().level;
      if (clientLevel != null) {
         clientLevel.addParticle(packet.particle(), true, packet.x(), packet.y(), packet.z(), packet.dx(), packet.dy(), packet.dz());
      }
   }

   @Override
   public void handleOpenCrestGui(OpenCrestGuiPacket packet) {
      if (packet.ownedCrests().isEmpty()) {
         if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.displayClientMessage(Component.translatable("message.eternal_starlight.no_crest"), true);
         }
      } else {
         Minecraft.getInstance().setScreen(new CrestSelectionScreen(packet.crests(), packet.ownedCrests()));
      }
   }

   @Override
   public void handleUpdateCamera(UpdateCameraPacket packet) {
      if (packet.cameraId() == -1) {
         ESClientHandler.resetCameraIn = 0;
      } else if (Minecraft.getInstance().level != null && !(Minecraft.getInstance().getCameraEntity() instanceof SoulitSpectator)) {
         Entity camera = Minecraft.getInstance().level.getEntity(packet.cameraId());
         if (camera != null) {
            ESClientHandler.resetCameraIn = 260;
            Minecraft.getInstance().options.hideGui = true;
            Minecraft.getInstance().setCameraEntity(camera);
         }
      }
   }

   @Override
   public void handleClientMount(ClientMountPacket packet) {
      if (Minecraft.getInstance().level != null) {
         Entity rider = Minecraft.getInstance().level.getEntity(packet.riderId());
         Entity vehicle = Minecraft.getInstance().level.getEntity(packet.vehicleId());
         if (rider != null && vehicle != null) {
            rider.startRiding(vehicle, true);
         }
      }
   }

   @Override
   public void handleClientDismount(ClientDismountPacket packet) {
      if (Minecraft.getInstance().level != null) {
         Entity rider = Minecraft.getInstance().level.getEntity(packet.riderId());
         if (rider != null) {
            rider.stopRiding();
         }
      }
   }

   @Override
   public void handleOpenGatekeeperGui(OpenGatekeeperGuiPacket packet) {
      ClientLevel clientLevel = Minecraft.getInstance().level;
      if (clientLevel != null && clientLevel.getEntity(packet.id()) instanceof TheGatekeeper gatekeeper) {
         Minecraft.getInstance().setScreen(new GatekeeperDialogueScreen(gatekeeper, packet.challenged()));
      }
   }

   @Override
   public void handleUpdateBook(UpdateBookPacket packet) {
      Set<ResourceLocation> bookIds = new HashSet<>();
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null) {
         Inventory inventory = player.getInventory();

         for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            GuideBook guideBook = (GuideBook)stack.get(ESDataComponents.BOOK.get());
            if (guideBook != null) {
               bookIds.add(guideBook.id());
            }
         }
      }

      Set<BookDefinition> definitions = bookIds.stream().map(ESClientHandler.books::getBook).collect(Collectors.toSet());
      List<IndexBookComponent.Entry> newEntries = new ArrayList<>();
      List<IndexBookComponent.Entry> changedEntries = new ArrayList<>();

      for (BookDefinition definition : definitions) {
         List<IndexBookComponent.Entry> entries = new ArrayList<>();
         definition.components().stream().flatMap(Collection::stream).filter(c -> c.component() == BookComponentRegistry.INDEX).forEach(c -> {
            if (c.config() instanceof IndexBookComponent.Config config) {
               entries.addAll(config.entries());
            }
         });

         for (IndexBookComponent.Entry entry : entries) {
            Optional<ConfiguredBookComponent<?, ?>> jumpTo = definition.getComponent(entry.getJumpToId());
            boolean oldEnabled = jumpTo.isPresent() && jumpTo.get().isEnabled(packet.oldUnlocked());
            boolean enabled = jumpTo.isPresent() && jumpTo.get().isEnabled(packet.unlocked());
            if (!oldEnabled && enabled) {
               newEntries.add(entry);
            }

            for (ResourceLocation listeningId : entry.getListeningIds()) {
               Optional<ConfiguredBookComponent<?, ?>> listening = definition.getComponent(listeningId);
               boolean oldListeningEnabled = listening.isPresent() && listening.get().isEnabled(packet.oldUnlocked());
               boolean listeningEnabled = listening.isPresent() && listening.get().isEnabled(packet.unlocked());
               if (!oldListeningEnabled && listeningEnabled) {
                  changedEntries.add(entry);
                  break;
               }
            }
         }
      }

      changedEntries.removeAll(newEntries);
      if (newEntries.size() > 3) {
         Minecraft.getInstance()
            .getToasts()
            .addToast(
               new SimpleTextToast(
                  Component.translatable("book.eternal_starlight.unlock"),
                  Component.translatable("book.eternal_starlight.unlock.multiple"),
                  ESItems.BOOK.get().getDefaultInstance()
               )
            );
      } else {
         for (IndexBookComponent.Entry entry : newEntries) {
            Minecraft.getInstance()
               .getToasts()
               .addToast(new SimpleTextToast(Component.translatable("book.eternal_starlight.unlock"), entry.getText(), entry.getIcon()));
         }
      }

      if (changedEntries.size() > 3) {
         Minecraft.getInstance()
            .getToasts()
            .addToast(
               new SimpleTextToast(
                  Component.translatable("book.eternal_starlight.update"),
                  Component.translatable("book.eternal_starlight.unlock.multiple"),
                  ESItems.BOOK.get().getDefaultInstance()
               )
            );
      } else {
         for (IndexBookComponent.Entry entry : changedEntries) {
            Minecraft.getInstance()
               .getToasts()
               .addToast(new SimpleTextToast(Component.translatable("book.eternal_starlight.update"), entry.getText(), entry.getIcon()));
         }
      }
   }

   @Override
   public void handleOpenBook(OpenBookPacket packet) {
      BookDefinition definition = ESClientHandler.books.getBook(packet.bookId());
      if (definition != null) {
         Minecraft.getInstance().setScreen(new BookScreen(definition, packet.unlocked()));
      }
   }

   @Override
   public void spawnStellarRackParticles(Vec3 center) {
      if (AdvancedParticleOptions.RANDOM.nextFloat() < 0.3) {
         if (AdvancedParticleOptions.RANDOM.nextBoolean()) {
            float size = (float)(1.0 + (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5) * 0.4);
            new AdvancedParticleOptions()
               .speed(
                  SmoothSegmentedValue.of(
                     Easing.IN_OUT_SINE,
                     (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.1F,
                     (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.1F,
                     1.0F
                  ),
                  SmoothSegmentedValue.of(Easing.IN_OUT_BACK, 0.1F, 0.3F, 1.0F),
                  SmoothSegmentedValue.of(
                     Easing.IN_OUT_SINE,
                     (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.1F,
                     (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.1F,
                     1.0F
                  )
               )
               .spinSpeed(
                  SmoothSegmentedValue.of(
                     Easing.IN_OUT_QUAD,
                     (float)((12.0 + (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5) * 10.0) * (float) (Math.PI / 180.0)),
                     (float)((18.0 + (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5) * 10.0) * (float) (Math.PI / 180.0)),
                     1.0F
                  )
               )
               .quadSize(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0.0F, size, 0.4F).add(Easing.IN_OUT_BOUNCE, size, 0.0F, 0.6F))
               .lifetime((int)(40.0 + (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5) * 20.0))
               .color(
                  SmoothSegmentedValue.of(Easing.IN_OUT_QUART, 0.11764707F, 0.17764707F, 1.0F),
                  SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0.2682353F, 0.3F, 1.0F),
                  SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0.29882354F, 0.29411766F, 1.0F),
                  SmoothSegmentedValue.of(Easing.OUT_QUINT, 0.0F, 1.0F, 0.7F).add(Easing.IN_OUT_QUAD, 1.0F, 0.0F, 0.3F)
               )
               .defaultOperators()
               .spawn(BuiltInRegistries.PARTICLE_TYPE.getKey((ParticleType)ESParticles.SHINE.get()), (float)center.x, (float)center.y, (float)center.z);
         }

         for (int i = 0; i < 4; i++) {
            new AdvancedParticleOptions()
               .speed(
                  SmoothSegmentedValue.of(
                     Easing.IN_OUT_SINE,
                     (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.1F,
                     (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.1F,
                     1.0F
                  ),
                  SmoothSegmentedValue.of(Easing.IN_OUT_BACK, 0.1F, 0.3F, 1.0F),
                  SmoothSegmentedValue.of(
                     Easing.IN_OUT_SINE,
                     (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.1F,
                     (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.1F,
                     1.0F
                  )
               )
               .spinSpeed(SmoothSegmentedValue.of(Easing.IN_OUT_QUAD, (float) (Math.PI / 10), (float) (Math.PI / 5), 1.0F))
               .quadSize(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0.0F, 0.2F, 0.6F).add(Easing.IN_OUT_BOUNCE, 0.2F, 0.0F, 0.4F))
               .lifetime((int)(40.0 + (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5) * 20.0))
               .color(
                  SmoothSegmentedValue.of(Easing.IN_OUT_QUART, 0.29529414F, 0.23294118F, 1.0F),
                  SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0.14705883F, 0.14588235F, 1.0F),
                  SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0.0023529413F, 0.21176472F, 1.0F),
                  SmoothSegmentedValue.of(Easing.OUT_QUINT, 0.0F, 1.0F, 0.7F).add(Easing.IN_OUT_QUAD, 1.0F, 0.0F, 0.3F)
               )
               .defaultOperators()
               .spawn(BuiltInRegistries.PARTICLE_TYPE.getKey((ParticleType)ESParticles.ADVANCED_GLOW.get()), (float)center.x, (float)center.y, (float)center.z);
         }
      }

      for (int i = 0; i < 5; i++) {
         new AdvancedParticleOptions()
            .speed(
               SmoothSegmentedValue.of(
                  Easing.IN_OUT_SINE,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  1.0F
               ),
               SmoothSegmentedValue.of(
                  Easing.IN_OUT_SINE,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  1.0F
               ),
               SmoothSegmentedValue.of(
                  Easing.IN_OUT_SINE,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  1.0F
               )
            )
            .spinSpeed(SmoothSegmentedValue.of(Easing.IN_OUT_QUAD, (float) (Math.PI / 10), (float) (Math.PI / 5), 1.0F))
            .quadSize(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0.0F, 0.15F, 0.5F).add(Easing.IN_OUT_BOUNCE, 0.15F, 0.0F, 0.5F))
            .lifetime(12)
            .color(
               SmoothSegmentedValue.of(Easing.IN_OUT_QUART, 0.29529414F, 0.23294118F, 1.0F),
               SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0.14705883F, 0.14588235F, 1.0F),
               SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0.0023529413F, 0.21176472F, 1.0F),
               SmoothSegmentedValue.of(Easing.OUT_QUINT, 0.0F, 1.0F, 0.7F).add(Easing.IN_OUT_QUAD, 1.0F, 0.0F, 0.3F)
            )
            .defaultOperators()
            .spawn(
               BuiltInRegistries.PARTICLE_TYPE.getKey((ParticleType)ESParticles.ADVANCED_GLOW.get()), (float)center.x, (float)center.y + 0.45F, (float)center.z
            );
      }
   }

   @Override
   public void spawnStellarRackItemParticles(Vec3 center) {
      for (int i = 0; i < 5; i++) {
         new AdvancedParticleOptions()
            .speed(
               SmoothSegmentedValue.of(
                  Easing.IN_OUT_SINE,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  1.0F
               ),
               SmoothSegmentedValue.of(
                  Easing.IN_OUT_SINE,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  1.0F
               ),
               SmoothSegmentedValue.of(
                  Easing.IN_OUT_SINE,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  1.0F
               )
            )
            .spinSpeed(SmoothSegmentedValue.of(Easing.IN_OUT_QUAD, (float) (Math.PI / 10), (float) (Math.PI / 5), 1.0F))
            .quadSize(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0.0F, 0.15F, 0.5F).add(Easing.IN_OUT_BOUNCE, 0.15F, 0.0F, 0.5F))
            .lifetime(12)
            .color(
               SmoothSegmentedValue.of(Easing.IN_OUT_QUART, 0.29529414F, 0.23294118F, 1.0F),
               SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0.14705883F, 0.14588235F, 1.0F),
               SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0.0023529413F, 0.21176472F, 1.0F),
               SmoothSegmentedValue.of(Easing.OUT_QUINT, 0.0F, 1.0F, 0.7F).add(Easing.IN_OUT_QUAD, 1.0F, 0.0F, 0.3F)
            )
            .defaultOperators()
            .spawn(BuiltInRegistries.PARTICLE_TYPE.getKey((ParticleType)ESParticles.ADVANCED_GLOW.get()), (float)center.x, (float)center.y, (float)center.z);
      }
   }

   @Override
   public void spawnManaCrystalItemParticles(ManaType type, Vec3 center) {
      for (int i = 0; i < 5; i++) {
         Vec3 pos = center.offsetRandom(AdvancedParticleOptions.RANDOM, 0.5F);
         Color color = Color.rgb(type.getColor());
         new AdvancedParticleOptions()
            .speed(
               SmoothSegmentedValue.of(
                  Easing.IN_OUT_SINE,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  1.0F
               ),
               SmoothSegmentedValue.of(
                  Easing.IN_OUT_SINE, (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F, AdvancedParticleOptions.RANDOM.nextFloat() * 0.015F, 1.0F
               ),
               SmoothSegmentedValue.of(
                  Easing.IN_OUT_SINE,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  (AdvancedParticleOptions.RANDOM.nextFloat() - 0.5F) * 0.03F,
                  1.0F
               )
            )
            .spinSpeed(SmoothSegmentedValue.of(Easing.IN_OUT_QUAD, (float) (Math.PI / 10), (float) (Math.PI / 5), 1.0F))
            .quadSize(SmoothSegmentedValue.of(Easing.IN_OUT_SINE, 0.0F, 0.25F, 0.6F).add(Easing.IN_OUT_BOUNCE, 0.25F, 0.0F, 0.4F))
            .lifetime(12)
            .color(
               SmoothSegmentedValue.of(Easing.IN_OUT_QUART, color.r() * 0.4F / 255.0F, 0.3F, 1.0F),
               SmoothSegmentedValue.of(Easing.IN_OUT_SINE, color.g() * 0.4F / 255.0F, 0.3F, 1.0F),
               SmoothSegmentedValue.of(Easing.IN_OUT_SINE, color.b() * 0.4F / 255.0F, 0.3F, 1.0F),
               SmoothSegmentedValue.of(Easing.OUT_QUINT, 0.0F, 1.0F, 0.7F).add(Easing.IN_OUT_QUAD, 1.0F, 0.0F, 0.3F)
            )
            .defaultOperators()
            .spawn(BuiltInRegistries.PARTICLE_TYPE.getKey((ParticleType)ESParticles.ADVANCED_GLOW.get()), (float)pos.x, (float)pos.y, (float)pos.z);
      }
   }

   @Override
   public void handleMeteorShowerClientTick() {
      ClientLevel level = Minecraft.getInstance().level;
      Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
      if (level != null && level.getGameTime() % 20L == 0L) {
         Vec3 randomPos = camera.getPosition().offsetRandom(level.getRandom(), 75.0F);
         int height = level.getHeight(Types.MOTION_BLOCKING, (int)randomPos.x, (int)randomPos.z);
         level.addParticle(
            (ParticleOptions)ESParticles.METEOR.get(), true, randomPos.x, Math.max(height + 75, camera.getPosition().y + 75.0), randomPos.z, 0.0, 0.0, 0.0
         );
      }
   }

   @Override
   public float handleMeteorShowerRainLevel() {
      float partialTick = Minecraft.getInstance()
         .getTimer()
         .getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally());
      ClientWeatherState.levelTarget = 1.0F;
      return ClientWeatherState.getRainLevel(partialTick);
   }
}
