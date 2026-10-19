package net.unusual.block_factorys_bosses.init;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.unusual.block_factorys_bosses.client.model.BigCageModel;
import net.unusual.block_factorys_bosses.client.model.BigSkellyCageModel;
import net.unusual.block_factorys_bosses.client.model.CageModel;
import net.unusual.block_factorys_bosses.client.model.ModelBigRiftProjectile;
import net.unusual.block_factorys_bosses.client.model.ModelCrate1;
import net.unusual.block_factorys_bosses.client.model.ModelCrate2;
import net.unusual.block_factorys_bosses.client.model.ModelCrate3;
import net.unusual.block_factorys_bosses.client.model.ModelCrate4;
import net.unusual.block_factorys_bosses.client.model.ModelCrate5;
import net.unusual.block_factorys_bosses.client.model.ModelCrate6;
import net.unusual.block_factorys_bosses.client.model.ModelCrate7;
import net.unusual.block_factorys_bosses.client.model.ModelCrate8;
import net.unusual.block_factorys_bosses.client.model.ModelCustomModel;
import net.unusual.block_factorys_bosses.client.model.ModelDragonBanner;
import net.unusual.block_factorys_bosses.client.model.ModelRiftProjectile;
import net.unusual.block_factorys_bosses.client.model.Modelboots;
import net.unusual.block_factorys_bosses.client.model.Modelchestplate;
import net.unusual.block_factorys_bosses.client.model.Modeldagger;
import net.unusual.block_factorys_bosses.client.model.Modelhelmet;
import net.unusual.block_factorys_bosses.client.model.Modelice_gauntlet;
import net.unusual.block_factorys_bosses.client.model.Modelicespike_pr;
import net.unusual.block_factorys_bosses.client.model.Modelknight_arm;
import net.unusual.block_factorys_bosses.client.model.Modelknight_armor;
import net.unusual.block_factorys_bosses.client.model.Modelknight_sword;
import net.unusual.block_factorys_bosses.client.model.Modellarge_sword;
import net.unusual.block_factorys_bosses.client.model.Modellarge_vfx;
import net.unusual.block_factorys_bosses.client.model.Modelleggings;
import net.unusual.block_factorys_bosses.client.model.Modelsoul_knight_wither_skeleton;
import net.unusual.block_factorys_bosses.client.model.Modelsoul_skeleton;
import net.unusual.block_factorys_bosses.client.model.Modelwarrior_sword;
import net.unusual.block_factorys_bosses.client.model.SkellyCageModel;

@EventBusSubscriber(Dist.CLIENT)
public class BossesRiseModels {
   @SubscribeEvent
   public static void registerLayerDefinitions(RegisterLayerDefinitions event) {
      event.registerLayerDefinition(CageModel.LAYER_LOCATION, CageModel::createBodyLayer);
      event.registerLayerDefinition(Modelleggings.LAYER_LOCATION, Modelleggings::createBodyLayer);
      event.registerLayerDefinition(Modellarge_sword.LAYER_LOCATION, Modellarge_sword::createBodyLayer);
      event.registerLayerDefinition(Modelsoul_knight_wither_skeleton.LAYER_LOCATION, Modelsoul_knight_wither_skeleton::createBodyLayer);
      event.registerLayerDefinition(ModelDragonBanner.LAYER_LOCATION, ModelDragonBanner::createBodyLayer);
      event.registerLayerDefinition(Modelicespike_pr.LAYER_LOCATION, Modelicespike_pr::createBodyLayer);
      event.registerLayerDefinition(Modelknight_sword.LAYER_LOCATION, Modelknight_sword::createBodyLayer);
      event.registerLayerDefinition(Modelknight_arm.LAYER_LOCATION, Modelknight_arm::createBodyLayer);
      event.registerLayerDefinition(Modelchestplate.LAYER_LOCATION, Modelchestplate::createBodyLayer);
      event.registerLayerDefinition(Modelice_gauntlet.LAYER_LOCATION, Modelice_gauntlet::createBodyLayer);
      event.registerLayerDefinition(Modelknight_armor.LAYER_LOCATION, Modelknight_armor::createBodyLayer);
      event.registerLayerDefinition(Modelboots.LAYER_LOCATION, Modelboots::createBodyLayer);
      event.registerLayerDefinition(ModelCustomModel.LAYER_LOCATION, ModelCustomModel::createBodyLayer);
      event.registerLayerDefinition(Modelsoul_skeleton.LAYER_LOCATION, Modelsoul_skeleton::createBodyLayer);
      event.registerLayerDefinition(Modellarge_vfx.LAYER_LOCATION, Modellarge_vfx::createBodyLayer);
      event.registerLayerDefinition(Modeldagger.LAYER_LOCATION, Modeldagger::createBodyLayer);
      event.registerLayerDefinition(BigSkellyCageModel.LAYER_LOCATION, BigSkellyCageModel::createBodyLayer);
      event.registerLayerDefinition(SkellyCageModel.LAYER_LOCATION, SkellyCageModel::createBodyLayer);
      event.registerLayerDefinition(Modelwarrior_sword.LAYER_LOCATION, Modelwarrior_sword::createBodyLayer);
      event.registerLayerDefinition(Modelhelmet.LAYER_LOCATION, Modelhelmet::createBodyLayer);
      event.registerLayerDefinition(BigCageModel.LAYER_LOCATION, BigCageModel::createBodyLayer);
      event.registerLayerDefinition(ModelRiftProjectile.LAYER_LOCATION, ModelRiftProjectile::createBodyLayer);
      event.registerLayerDefinition(ModelBigRiftProjectile.LAYER_LOCATION, ModelBigRiftProjectile::createBodyLayer);
      event.registerLayerDefinition(ModelCrate1.LAYER_LOCATION, ModelCrate1::createBodyLayer);
      event.registerLayerDefinition(ModelCrate2.LAYER_LOCATION, ModelCrate2::createBodyLayer);
      event.registerLayerDefinition(ModelCrate3.LAYER_LOCATION, ModelCrate3::createBodyLayer);
      event.registerLayerDefinition(ModelCrate4.LAYER_LOCATION, ModelCrate4::createBodyLayer);
      event.registerLayerDefinition(ModelCrate5.LAYER_LOCATION, ModelCrate5::createBodyLayer);
      event.registerLayerDefinition(ModelCrate6.LAYER_LOCATION, ModelCrate6::createBodyLayer);
      event.registerLayerDefinition(ModelCrate7.LAYER_LOCATION, ModelCrate7::createBodyLayer);
      event.registerLayerDefinition(ModelCrate8.LAYER_LOCATION, ModelCrate8::createBodyLayer);
   }
}
