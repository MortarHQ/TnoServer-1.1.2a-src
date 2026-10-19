package twilightforest.client.event;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.SelectMusicEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;
import net.neoforged.neoforge.client.event.RenderHighlightEvent.Block;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.client.event.RenderLivingEvent.Pre;
import net.neoforged.neoforge.client.event.ScreenEvent.Closing;
import net.neoforged.neoforge.client.event.ScreenEvent.Init.Post;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import twilightforest.TwilightForestMod;
import twilightforest.beanification.Autowired;
import twilightforest.beanification.PostConstruct;
import twilightforest.block.GiantBlock;
import twilightforest.block.MiniatureStructureBlock;
import twilightforest.block.entity.GrowingBeanstalkBlockEntity;
import twilightforest.client.BugModelAnimationHelper;
import twilightforest.client.ISTER;
import twilightforest.client.OptifineWarningScreen;
import twilightforest.client.TFShaders;
import twilightforest.client.renderer.entity.MagicPaintingRenderer;
import twilightforest.compat.curios.CuriosCompat;
import twilightforest.config.TFConfig;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.entity.boss.bar.ClientTFBossBar;
import twilightforest.events.HostileMountEvents;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFDimension;
import twilightforest.item.EnderBowItem;
import twilightforest.item.GiantPickItem;
import twilightforest.item.IceBowItem;
import twilightforest.item.SeekerBowItem;
import twilightforest.item.TripleBowItem;
import twilightforest.item.TrophyItem;
import twilightforest.util.HolderMatcher;
import twilightforest.util.entities.EntityRenderingUtil;

@twilightforest.beanification.Component(dist = Dist.CLIENT)
public class ClientGameEvents {
   private final VoxelShape GIANT_BLOCK = Shapes.box(0.0, 0.0, 0.0, 4.0, 4.0, 4.0);
   private final MutableComponent WIP_TEXT = Component.translatable("misc.twilightforest.wip").withStyle(ChatFormatting.RED);
   private final MutableComponent EMPERORS_CLOTH_TOOLTIP = Component.translatable("item.twilightforest.emperors_cloth.desc").withStyle(ChatFormatting.GRAY);
   private boolean firstTitleScreenShown = false;
   public static int time = 0;
   private float shakeIntensity = 0.0F;
   private int aurora = 0;
   private int lastAurora = 0;
   @Autowired(dist = Dist.CLIENT)
   private HolderMatcher holderMatcher;

   @PostConstruct
   private void setup() {
      NeoForge.EVENT_BUS.addListener(this::addCustomTooltips);
      NeoForge.EVENT_BUS.addListener(this::clientTick);
      NeoForge.EVENT_BUS.addListener(this::customizeSplashes);
      NeoForge.EVENT_BUS.addListener(this::clearEntityRenderUtilMap);
      NeoForge.EVENT_BUS.addListener(this::handleGameBootup);
      NeoForge.EVENT_BUS.addListener(this::killVignette);
      NeoForge.EVENT_BUS.addListener(this::removeHostileMountHealth);
      NeoForge.EVENT_BUS.addListener(this::renderAurora);
      NeoForge.EVENT_BUS.addListener(this::renderCustomBossbars);
      NeoForge.EVENT_BUS.addListener(this::renderGiantBlockOutlines);
      NeoForge.EVENT_BUS.addListener(this::setMusicInDimension);
      NeoForge.EVENT_BUS.addListener(this::shakeCamera);
      NeoForge.EVENT_BUS.addListener(this::translateBookAuthor);
      NeoForge.EVENT_BUS.addListener(this::unrenderHeadWithTrophies);
      NeoForge.EVENT_BUS.addListener(this::updateBowFOV);
      NeoForge.EVENT_BUS.addListener(CloudEvents::renderPrecipitation);
      NeoForge.EVENT_BUS.addListener(CloudEvents::tickWeatherEffects);
      NeoForge.EVENT_BUS.addListener(FogHandler::renderFog);
      NeoForge.EVENT_BUS.addListener(FogHandler::unloadFog);
      NeoForge.EVENT_BUS.addListener(LockedBiomeToastHandler::tickLockedToastLogic);
   }

   private void handleGameBootup(Post event) {
      if (!this.firstTitleScreenShown && event.getScreen() instanceof TitleScreen) {
         if (Minecraft.getInstance().getResourceManager() instanceof ReloadableResourceManager resourceManager) {
            resourceManager.registerReloadListener((PreparableReloadListener)ISTER.INSTANCE.get());
            TwilightForestMod.LOGGER.debug("Registered ISTER listener");
         }

         if (ClientRegistrationEvents.isOptifinePresent() && !TFConfig.disableOptifineNagScreen) {
            Minecraft.getInstance().setScreen(new OptifineWarningScreen(event.getScreen()));
         }

         this.firstTitleScreenShown = true;
      }
   }

   private void customizeSplashes(Post event) {
      if (event.getScreen() instanceof TitleScreen title) {
         SplashRenderer renderer = title.splash;
         if (renderer != null) {
            LocalDate date = LocalDate.now();
            if (date.getMonth() == Month.AUGUST && date.getDayOfMonth() == 19) {
               RuleBasedNumberFormat formatter = new RuleBasedNumberFormat(Locale.US, 2);
               renderer.splash = String.format("Happy %s birthday to the Twilight Forest!", formatter.format(date.getYear() - 2011));
            }
         }
      }
   }

   private void clearEntityRenderUtilMap(Closing event) {
      if (!EntityRenderingUtil.ENTITY_MAP.isEmpty()) {
         EntityRenderingUtil.ENTITY_MAP.clear();
      }
   }

   private void setMusicInDimension(SelectMusicEvent event) {
      Music music = event.getOriginalMusic();
      if (Minecraft.getInstance().level != null
         && Minecraft.getInstance().player != null
         && (music == Musics.CREATIVE || music == Musics.UNDER_WATER)
         && TFDimension.isTwilightWorldOnClient(Minecraft.getInstance().level)) {
         event.setMusic(
            ((Biome)Minecraft.getInstance().level.getBiomeManager().getNoiseBiomeAtPosition(Minecraft.getInstance().player.blockPosition()).value())
               .getBackgroundMusic()
               .orElse(Musics.GAME)
         );
      }
   }

   private void removeHostileMountHealth(net.neoforged.neoforge.client.event.RenderGuiLayerEvent.Pre event) {
      if (VanillaGuiLayers.VEHICLE_HEALTH == event.getName() && HostileMountEvents.isRidingUnfriendly(Minecraft.getInstance().player)) {
         event.setCanceled(true);
      }
   }

   private void renderAurora(RenderLevelStageEvent event) {
      if (Minecraft.getInstance().level != null) {
         if (event.getStage() == Stage.AFTER_WEATHER && (this.aurora > 0 || this.lastAurora > 0) && TFShaders.AURORA != null) {
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            float scale = 2048.0F * (Minecraft.getInstance().gameRenderer.getRenderDistance() / 32.0F);
            Vec3 pos = event.getCamera().getPosition();
            float y = (float)(256.0 - pos.y());
            buffer.addVertex(-scale, y, scale).setColor(1.0F, 1.0F, 1.0F, 1.0F);
            buffer.addVertex(-scale, y, -scale).setColor(1.0F, 1.0F, 1.0F, 1.0F);
            buffer.addVertex(scale, y, -scale).setColor(1.0F, 1.0F, 1.0F, 1.0F);
            buffer.addVertex(scale, y, scale).setColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, Mth.lerp(event.getPartialTick().getGameTimeDeltaTicks(), this.lastAurora, this.aurora) / 60.0F * 0.5F);
            TFShaders.AURORA
               .invokeThenEndTesselator(
                  Minecraft.getInstance().level == null ? 0 : Mth.abs((int)Minecraft.getInstance().level.getBiomeManager().biomeZoomSeed),
                  (float)pos.x(),
                  (float)pos.y(),
                  (float)pos.z(),
                  buffer
               );
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableDepthTest();
            RenderSystem.disableBlend();
         }
      }
   }

   private void killVignette(net.neoforged.neoforge.client.event.RenderFrameEvent.Pre event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null && TFDimension.DIMENSION_KEY.equals(minecraft.level.dimension())) {
         minecraft.gui.vignetteBrightness = 0.0F;
      }

      if (minecraft.player != null && HostileMountEvents.isRidingUnfriendly(minecraft.player)) {
         minecraft.gui.setOverlayMessage(Component.empty(), false);
      }
   }

   private void clientTick(net.neoforged.neoforge.client.event.ClientTickEvent.Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (!mc.isPaused()) {
         time++;
         this.lastAurora = this.aurora;
         if (mc.level != null && mc.cameraEntity != null && !TFConfig.getValidAuroraBiomes(mc.level.registryAccess()).isEmpty()) {
            RegistryAccess access = mc.level.registryAccess();
            Holder<Biome> biome = mc.level.getBiome(mc.cameraEntity.blockPosition());
            if (TFConfig.getValidAuroraBiomes(access).stream().anyMatch(c -> this.holderMatcher.match(c, biome))) {
               this.aurora++;
            } else {
               this.aurora--;
            }

            this.aurora = Mth.clamp(this.aurora, 0, 60);
         } else {
            this.aurora = 0;
         }

         BugModelAnimationHelper.animate();
         if (mc.level != null) {
            if (mc.level.getSkyFlashTime() > 0) {
               MagicPaintingRenderer.lastLightning = mc.level.getGameTime();
            }

            if (TFConfig.firstPersonEffects && mc.player != null) {
               HashSet<ChunkPos> chunksInRange = new HashSet<>();

               for (int x = -16; x <= 16; x += 16) {
                  for (int z = -16; z <= 16; z += 16) {
                     chunksInRange.add(new ChunkPos((int)(mc.player.getX() + x) >> 4, (int)(mc.player.getZ() + z) >> 4));
                  }
               }

               for (ChunkPos pos : chunksInRange) {
                  if (mc.level.getChunk(pos.x, pos.z, ChunkStatus.FULL, false) != null) {
                     List<BlockEntity> beanstalksInChunk = mc.level
                        .getChunk(pos.x, pos.z)
                        .getBlockEntities()
                        .values()
                        .stream()
                        .filter(blockEntity -> blockEntity instanceof GrowingBeanstalkBlockEntity beanstalkBlock && beanstalkBlock.isBeanstalkRumbling())
                        .toList();
                     if (!beanstalksInChunk.isEmpty()) {
                        BlockEntity beanstalk = beanstalksInChunk.getFirst();
                        Player player = mc.player;
                        this.shakeIntensity = (float)(1.0 - mc.player.distanceToSqr(Vec3.atCenterOf(beanstalk.getBlockPos())) / Math.pow(16.0, 2.0));
                        if (this.shakeIntensity > 0.0F) {
                           player.moveTo(
                              player.getX(),
                              player.getY(),
                              player.getZ(),
                              player.getYRot() + (player.getRandom().nextFloat() - 0.5F) * this.shakeIntensity,
                              player.getXRot() + (player.getRandom().nextFloat() * 2.5F - 1.25F) * this.shakeIntensity
                           );
                           this.shakeIntensity = 0.0F;
                           break;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void shakeCamera(ComputeCameraAngles event) {
      if (TFConfig.firstPersonEffects && !Minecraft.getInstance().isPaused() && this.shakeIntensity > 0.0F && Minecraft.getInstance().player != null) {
         event.setYaw(
            (float)Mth.lerp(
               event.getPartialTick(),
               event.getYaw(),
               event.getYaw() + (Minecraft.getInstance().player.getRandom().nextFloat() * 2.0F - 1.0F) * this.shakeIntensity
            )
         );
         event.setPitch(
            (float)Mth.lerp(
               event.getPartialTick(),
               event.getPitch(),
               event.getPitch() + (Minecraft.getInstance().player.getRandom().nextFloat() * 2.0F - 1.0F) * this.shakeIntensity
            )
         );
         event.setRoll(
            (float)Mth.lerp(
               event.getPartialTick(),
               event.getRoll(),
               event.getRoll() + (Minecraft.getInstance().player.getRandom().nextFloat() * 2.0F - 1.0F) * this.shakeIntensity
            )
         );
         this.shakeIntensity = 0.0F;
      }
   }

   private void addCustomTooltips(ItemTooltipEvent event) {
      ItemStack item = event.getItemStack();
      if (item.has(TFDataComponents.EMPERORS_CLOTH)) {
         event.getToolTip().add(1, this.EMPERORS_CLOTH_TOOLTIP);
      }

      if (item.is(ItemTagGenerator.WIP)) {
         event.getToolTip().add(this.WIP_TEXT);
      }
   }

   private void updateBowFOV(ComputeFovModifierEvent event) {
      Player player = event.getPlayer();
      if (player.isUsingItem()) {
         Item useItem = player.getUseItem().getItem();
         if (useItem instanceof TripleBowItem || useItem instanceof EnderBowItem || useItem instanceof IceBowItem || useItem instanceof SeekerBowItem) {
            float f = player.getTicksUsingItem() / 20.0F;
            f = f > 1.0F ? 1.0F : f * f;
            event.setNewFovModifier(
               (float)Mth.lerp((Double)Minecraft.getInstance().options.fovEffectScale().get(), 1.0, event.getFovModifier() * (1.0F - f * 0.15F))
            );
         }
      }
   }

   private void unrenderHeadWithTrophies(Pre<?, ?> event) {
      ItemStack stack = event.getEntity().getItemBySlot(EquipmentSlot.HEAD);
      boolean visible = !(stack.getItem() instanceof TrophyItem) && !this.areCuriosEquipped(event.getEntity());
      boolean isPlayer = event.getEntity() instanceof Player;
      if (event.getRenderer().getModel() instanceof HeadedModel headedModel) {
         headedModel.getHead().visible = visible && (!isPlayer || headedModel.getHead().visible);
         if (event.getRenderer().getModel() instanceof HumanoidModel<?> humanoidModel) {
            humanoidModel.hat.visible = visible && (!isPlayer || humanoidModel.hat.visible);
         }
      }
   }

   private boolean areCuriosEquipped(LivingEntity entity) {
      return ModList.get().isLoaded("curios") ? CuriosCompat.isCurioEquippedAndVisible(entity, stack -> stack.getItem() instanceof TrophyItem) : false;
   }

   private void translateBookAuthor(ItemTooltipEvent event) {
      ItemStack stack = event.getItemStack();
      if (stack.getItem() instanceof WrittenBookItem && stack.has(DataComponents.WRITTEN_BOOK_CONTENT) && stack.has(TFDataComponents.TRANSLATABLE_BOOK)) {
         List<Component> components = event.getToolTip();

         for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            if (component.toString().contains("book.byAuthor")) {
               components.set(
                  i,
                  Component.translatable("book.byAuthor", new Object[]{Component.translatable("twilightforest.book.author")}).withStyle(component.getStyle())
               );
            }
         }
      }
   }

   private void renderGiantBlockOutlines(Block event) {
      BlockPos pos = event.getTarget().getBlockPos();
      BlockState state = event.getCamera().getEntity().level().getBlockState(pos);
      if (state.getBlock() instanceof MiniatureStructureBlock) {
         event.setCanceled(true);
      } else {
         LocalPlayer player = Minecraft.getInstance().player;
         if (player != null
            && (
               player.getMainHandItem().getItem() instanceof GiantPickItem
                  || player.getMainHandItem().getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof GiantBlock
            )) {
            event.setCanceled(true);
            if (!state.isAir() && player.level().getWorldBorder().isWithinBounds(pos)) {
               BlockPos offsetPos = new BlockPos(pos.getX() & -4, pos.getY() & -4, pos.getZ() & -4);
               VertexConsumer consumer = event.getMultiBufferSource().getBuffer(RenderType.lines());
               Vec3 xyz = Vec3.atLowerCornerOf(offsetPos).subtract(event.getCamera().getPosition());
               LevelRenderer.renderShape(event.getPoseStack(), consumer, this.GIANT_BLOCK, xyz.x(), xyz.y(), xyz.z(), 0.0F, 0.0F, 0.0F, 0.45F);
            }
         }
      }
   }

   private void renderCustomBossbars(BossEventProgress event) {
      if (event.getBossEvent() instanceof ClientTFBossBar bossEvent) {
         event.setCanceled(true);
         bossEvent.renderBossBar(event.getGuiGraphics(), event.getX(), event.getY());
      }
   }
}
