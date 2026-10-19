package com.benji.netherman;

import com.benji.netherman.client.ManipulationOverlay;
import com.benji.netherman.client.model.AzazelScytheModel;
import com.benji.netherman.client.model.AzazelShieldModel;
import com.benji.netherman.client.model.AzazelSpearModel;
import com.benji.netherman.client.renderer.AltarRenderer;
import com.benji.netherman.client.renderer.AzazelEarthquakeRenderer;
import com.benji.netherman.client.renderer.AzazelGuideBookRenderer;
import com.benji.netherman.client.renderer.AzazelHumanRenderer;
import com.benji.netherman.client.renderer.AzazelRenderer;
import com.benji.netherman.client.renderer.AzazelSpikeProjectileRenderer;
import com.benji.netherman.client.renderer.AzazelSpikeRenderer;
import com.benji.netherman.client.renderer.AzazelSplashRenderer;
import com.benji.netherman.client.renderer.BelieverRenderer;
import com.benji.netherman.client.renderer.BelieverVillagerRenderer;
import com.benji.netherman.client.renderer.BellGuardianRenderer;
import com.benji.netherman.client.renderer.BlacksmithRenderer;
import com.benji.netherman.client.renderer.DoctorRenderer;
import com.benji.netherman.client.renderer.EyeRenderer;
import com.benji.netherman.client.renderer.FacePuzzleLeftDownRenderer;
import com.benji.netherman.client.renderer.FacePuzzleLeftUpRenderer;
import com.benji.netherman.client.renderer.FacePuzzleRightDownRenderer;
import com.benji.netherman.client.renderer.FacePuzzleRightUpRenderer;
import com.benji.netherman.client.renderer.GrandDoorRenderer;
import com.benji.netherman.client.renderer.LabyrinthTeleportRenderer;
import com.benji.netherman.client.renderer.LaserRenderer;
import com.benji.netherman.client.renderer.ManipulatorRenderer;
import com.benji.netherman.client.renderer.MazeDoorRenderer;
import com.benji.netherman.client.renderer.PiglinPrisonerRenderer;
import com.benji.netherman.client.renderer.PointedBlackstoneRenderer;
import com.benji.netherman.client.renderer.SamsoniteBellRenderer;
import com.benji.netherman.client.renderer.StatueBossunitRenderer;
import com.benji.netherman.client.renderer.StatueRenderer;
import com.benji.netherman.client.renderer.TotemusPuzzleRenderer;
import com.benji.netherman.client.renderer.TraderRenderer;
import com.benji.netherman.client.renderer.TraphiveRenderer;
import com.benji.netherman.client.renderer.VillagerPrisonerRenderer;
import com.benji.netherman.client.renderer.VoidNetherCornerRenderer;
import com.benji.netherman.client.renderer.VoidNetherMidCornerRenderer;
import com.benji.netherman.client.renderer.VoidNetherMidRenderer;
import com.benji.netherman.client.renderer.entity.CrimsonArrowRenderer;
import com.benji.netherman.client.renderer.entity.GhastlyRenderer;
import com.benji.netherman.client.renderer.entity.GildedGolemRenderer;
import com.benji.netherman.client.renderer.entity.GuardianRenderer;
import com.benji.netherman.client.renderer.entity.WelcomerRenderer;
import com.benji.netherman.client.renderer.item.GeoBlockItemRenderer;
import com.benji.netherman.common.item.AzazelScytheItem;
import com.benji.netherman.common.item.AzazelShieldItem;
import com.benji.netherman.common.item.AzazelSpearItem;
import com.benji.netherman.common.item.GeoBlockItem;
import com.benji.netherman.init.ModBlockEntities;
import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModItems;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoItemRenderer;

@Mod(value = "netherman", dist = Dist.CLIENT)
public class NetherExpClient {
   public NetherExpClient(IEventBus modBus, ModContainer container) {
      modBus.addListener(this::clientInit);
      modBus.addListener(this::registerClientExtensions);
      modBus.addListener(this::registerGuiOverlays);
      modBus.addListener(this::registerRenderers);
   }

   public void clientInit(FMLClientSetupEvent event) {
      registerModelPredicates();
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntities.LABYRINTH_TELEPORT.get(), LabyrinthTeleportRenderer::new);
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntities.VOIDMIDCORNERNETHER.get(), VoidNetherMidCornerRenderer::new);
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntities.VOIDCORNERNETHER.get(), VoidNetherCornerRenderer::new);
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntities.VOIDMIDNETHER.get(), VoidNetherMidRenderer::new);
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntities.SAMSONITE_BELL.get(), SamsoniteBellRenderer::new);
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntities.TRAPHIVE.get(), TraphiveRenderer::new);
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntities.EYE.get(), EyeRenderer::new);
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntities.ALTAR.get(), AltarRenderer::new);
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntities.GRAND_DOOR.get(), GrandDoorRenderer::new);
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntities.POINTED_BLACKSTONE.get(), PointedBlackstoneRenderer::new);
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntities.MAZE_DOOR.get(), MazeDoorRenderer::new);
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntities.FACE_PUZZLE_LEFT_DOWN.get(), FacePuzzleLeftDownRenderer::new);
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntities.FACE_PUZZLE_RIGHT_DOWN.get(), FacePuzzleRightDownRenderer::new);
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntities.FACE_PUZZLE_LEFT_UP.get(), FacePuzzleLeftUpRenderer::new);
      BlockEntityRenderers.register((BlockEntityType)ModBlockEntities.FACE_PUZZLE_RIGHT_UP.get(), FacePuzzleRightUpRenderer::new);
   }

   public void registerClientExtensions(RegisterClientExtensionsEvent event) {
      for (DeferredHolder<Item, ? extends Item> block : ModItems.getItems()) {
         if (block.get() instanceof GeoBlockItem geoBlockItem) {
            event.registerItem(new IClientItemExtensions() {
               @NotNull
               public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                  return new GeoBlockItemRenderer(geoBlockItem);
               }
            }, new Item[]{geoBlockItem});
         }
      }

      event.registerItem(new IClientItemExtensions() {
         private GeoItemRenderer<AzazelSpearItem> renderer;

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            if (this.renderer == null) {
               this.renderer = new GeoItemRenderer(new AzazelSpearModel());
            }

            return this.renderer;
         }
      }, new Item[]{(Item)ModItems.AZAZEL_SPEAR.get()});
      event.registerItem(new IClientItemExtensions() {
         private GeoItemRenderer<AzazelScytheItem> renderer;

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            if (this.renderer == null) {
               this.renderer = new GeoItemRenderer(new AzazelScytheModel());
            }

            return this.renderer;
         }
      }, new Item[]{(Item)ModItems.AZAZEL_SCYTHE.get()});
      event.registerItem(new IClientItemExtensions() {
         private GeoItemRenderer<AzazelShieldItem> renderer;

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            if (this.renderer == null) {
               this.renderer = new GeoItemRenderer(new AzazelShieldModel());
            }

            return this.renderer;
         }
      }, new Item[]{(Item)ModItems.AZAZEL_SHIELD.get()});
   }

   public void registerRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)ModEntities.AZAZEL_GUIDE_BOOK.get(), AzazelGuideBookRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.TOTEMUS_PUZZLE.get(), TotemusPuzzleRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.GILDED_GOLEM.get(), GildedGolemRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CRIMSON_ARROW.get(), CrimsonArrowRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.AZAZEL.get(), AzazelRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.LASER.get(), LaserRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.STATUE_BOSSUNIT.get(), StatueBossunitRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.BLACKSMITH.get(), BlacksmithRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.DOCTOR.get(), DoctorRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.AZAZEL_HUMAN.get(), AzazelHumanRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.EARTHQUAKE_ENTITY.get(), AzazelEarthquakeRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.SPLASH_ENTITY.get(), AzazelSplashRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.SPIKE_PROJECTILE_ENTITY.get(), AzazelSpikeProjectileRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.SPIKE_ENTITY.get(), AzazelSpikeRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.TRADER.get(), TraderRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.STATUE.get(), StatueRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.BELIEVER.get(), BelieverRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.BELIEVER_VILLAGER.get(), BelieverVillagerRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.PIGLIN_PRISONER.get(), PiglinPrisonerRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.VILLAGER_PRISONER.get(), VillagerPrisonerRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.MANIPULATOR.get(), ManipulatorRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.WELCOMER.get(), WelcomerRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.GUARDIAN.get(), GuardianRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.GHASTLY.get(), GhastlyRenderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.BELL_GUARDIAN.get(), BellGuardianRenderer::new);
   }

   public void registerGuiOverlays(RegisterGuiLayersEvent event) {
      event.registerAboveAll(NetherExp.location("manipulation_overlay"), ManipulationOverlay.HUD_OVERLAY);
   }

   public static void registerModelPredicates() {
      ItemProperties.register((Item)ModItems.ALTAR_COMPASS_KEY.get(), ResourceLocation.withDefaultNamespace("angle"), (stack, level, entity, seed) -> {
         if (entity == null && !stack.isFramed()) {
            return 0.0F;
         }

         CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
         CompoundTag tag = customData.copyTag();
         if (!tag.contains("TargetX")) {
            return (float)(System.currentTimeMillis() % 4000L / 4000.0);
         }

         double targetX = tag.getInt("TargetX");
         double targetZ = tag.getInt("TargetZ");
         Entity player = (Entity)(entity != null ? entity : stack.getFrame());
         if (player == null) {
            return 0.0F;
         }

         double targetYaw = Math.toDegrees(Math.atan2(targetZ - player.getZ(), targetX - player.getX())) - 90.0;
         double playerYaw = player.getYRot();
         double relativeYaw = targetYaw - playerYaw;
         double angle = 0.5 + relativeYaw / 360.0;
         return (float)Mth.positiveModulo(angle, 1.0);
      });
   }
}
