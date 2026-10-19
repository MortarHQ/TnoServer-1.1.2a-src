package com.bobmowzie.mowziesmobs.client;

import com.bobmowzie.mowziesmobs.client.gui.GuiSculptorTrade;
import com.bobmowzie.mowziesmobs.client.gui.GuiUmvuthanaTrade;
import com.bobmowzie.mowziesmobs.client.gui.GuiUmvuthiTrade;
import com.bobmowzie.mowziesmobs.client.render.block.GongRenderer;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderAxeAttack;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderBluff;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderBoulder;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderDart;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderEarthSpike;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderElokosa;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderFallingBlock;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderFissurePiece;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderFoliaath;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderFoliaathBaby;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderFrostmaw;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderGrottol;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderIceBall;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderLantern;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderNaga;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderNothing;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderPillar;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderPoisonBall;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderRockSling;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderSculptor;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderSolarBeam;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderSunstrike;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderSuperNova;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderUmvuthana;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderUmvuthi;
import com.bobmowzie.mowziesmobs.client.render.entity.RenderWroughtnaut;
import com.bobmowzie.mowziesmobs.server.block.entity.BlockEntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.MaskType;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerHandler;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.ModelEvent.RegisterAdditional;

@EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
   @SubscribeEvent
   public static void onClientSetup(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
         EntityRenderers.register((EntityType)EntityHandler.BABY_FOLIAATH.get(), RenderFoliaathBaby::new);
         EntityRenderers.register((EntityType)EntityHandler.FOLIAATH.get(), RenderFoliaath::new);
         EntityRenderers.register((EntityType)EntityHandler.WROUGHTNAUT.get(), RenderWroughtnaut::new);
         EntityRenderers.register((EntityType)EntityHandler.UMVUTHI.get(), RenderUmvuthi::new);
         EntityRenderers.register((EntityType)EntityHandler.UMVUTHANA_RAPTOR.get(), RenderUmvuthana::new);
         EntityRenderers.register((EntityType)EntityHandler.UMVUTHANA_FOLLOWER_TO_RAPTOR.get(), RenderUmvuthana::new);
         EntityRenderers.register((EntityType)EntityHandler.UMVUTHANA_MINION.get(), RenderUmvuthana::new);
         EntityRenderers.register((EntityType)EntityHandler.UMVUTHANA_FOLLOWER_TO_PLAYER.get(), RenderUmvuthana::new);
         EntityRenderers.register((EntityType)EntityHandler.UMVUTHANA_CRANE_TO_PLAYER.get(), RenderUmvuthana::new);
         EntityRenderers.register((EntityType)EntityHandler.UMVUTHANA_CRANE.get(), RenderUmvuthana::new);
         EntityRenderers.register((EntityType)EntityHandler.FROSTMAW.get(), RenderFrostmaw::new);
         EntityRenderers.register((EntityType)EntityHandler.GROTTOL.get(), RenderGrottol::new);
         EntityRenderers.register((EntityType)EntityHandler.LANTERN.get(), RenderLantern::new);
         EntityRenderers.register((EntityType)EntityHandler.NAGA.get(), RenderNaga::new);
         EntityRenderers.register((EntityType)EntityHandler.SCULPTOR.get(), RenderSculptor::new);
         EntityRenderers.register((EntityType)EntityHandler.BLUFF.get(), RenderBluff::new);
         EntityRenderers.register((EntityType)EntityHandler.ELOKOSA_FOLLOWER_TO_HOWLER.get(), RenderElokosa::new);
         EntityRenderers.register((EntityType)EntityHandler.ELOKOSA_HOWLER.get(), RenderElokosa::new);
         EntityRenderers.register((EntityType)EntityHandler.DART.get(), RenderDart::new);
         EntityRenderers.register((EntityType)EntityHandler.SUNSTRIKE.get(), RenderSunstrike::new);
         EntityRenderers.register((EntityType)EntityHandler.SOLAR_BEAM.get(), RenderSolarBeam::new);
         EntityRenderers.register((EntityType)EntityHandler.BOULDER_PROJECTILE.get(), RenderBoulder::new);
         EntityRenderers.register((EntityType)EntityHandler.BOULDER_SCULPTOR.get(), RenderBoulder::new);
         EntityRenderers.register((EntityType)EntityHandler.BOULDER_SCULPTOR_CRUMBLING.get(), RenderBoulder::new);
         EntityRenderers.register((EntityType)EntityHandler.PILLAR.get(), RenderPillar::new);
         EntityRenderers.register((EntityType)EntityHandler.PILLAR_SCULPTOR.get(), RenderPillar::new);
         EntityRenderers.register((EntityType)EntityHandler.PILLAR_PIECE.get(), RenderNothing::new);
         EntityRenderers.register((EntityType)EntityHandler.AXE_ATTACK.get(), RenderAxeAttack::new);
         EntityRenderers.register((EntityType)EntityHandler.POISON_BALL.get(), RenderPoisonBall::new);
         EntityRenderers.register((EntityType)EntityHandler.ICE_BALL.get(), RenderIceBall::new);
         EntityRenderers.register((EntityType)EntityHandler.ICE_BREATH.get(), RenderNothing::new);
         EntityRenderers.register((EntityType)EntityHandler.FROZEN_CONTROLLER.get(), RenderNothing::new);
         EntityRenderers.register((EntityType)EntityHandler.SUPER_NOVA.get(), RenderSuperNova::new);
         EntityRenderers.register((EntityType)EntityHandler.FALLING_BLOCK.get(), RenderFallingBlock::new);
         EntityRenderers.register((EntityType)EntityHandler.BLOCK_SWAPPER.get(), RenderNothing::new);
         EntityRenderers.register((EntityType)EntityHandler.BLOCK_SWAPPER_TUNNELING.get(), RenderNothing::new);
         EntityRenderers.register((EntityType)EntityHandler.CAMERA_SHAKE.get(), RenderNothing::new);
         EntityRenderers.register((EntityType)EntityHandler.ROCK_SLING.get(), RenderRockSling::new);
         EntityRenderers.register((EntityType)EntityHandler.FISSURE.get(), RenderNothing::new);
         EntityRenderers.register((EntityType)EntityHandler.FISSURE_PIECE.get(), RenderFissurePiece::new);
         EntityRenderers.register((EntityType)EntityHandler.EARTH_SPIKE.get(), RenderEarthSpike::new);
         BlockEntityRenderers.register((BlockEntityType)BlockEntityHandler.GONG_BLOCK_ENTITY.get(), GongRenderer::new);
      });
   }

   @SubscribeEvent
   public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
      event.register((MenuType)ContainerHandler.CONTAINER_UMVUTHANA_TRADE.get(), GuiUmvuthanaTrade::new);
      event.register((MenuType)ContainerHandler.CONTAINER_UMVUTHI_TRADE.get(), GuiUmvuthiTrade::new);
      event.register((MenuType)ContainerHandler.CONTAINER_SCULPTOR_TRADE.get(), GuiSculptorTrade::new);
   }

   @SubscribeEvent
   public static void onRegisterModels(RegisterAdditional modelRegistryEvent) {
      for (String item : MMModels.HAND_MODEL_ITEMS) {
         modelRegistryEvent.register(MMModels.prefixed(item + "_in_hand"));
      }

      for (MaskType type : MaskType.values()) {
         modelRegistryEvent.register(MMModels.prefixed("umvuthana_mask_" + type.name + "_frame"));
      }

      modelRegistryEvent.register(MMModels.prefixed("sol_visage_frame"));
   }
}
