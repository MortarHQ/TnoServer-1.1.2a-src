package com.faboslav.variantsandventures.common.init;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import com.faboslav.variantsandventures.common.client.model.GelidEntityModel;
import com.faboslav.variantsandventures.common.client.model.MurkEntityModel;
import com.faboslav.variantsandventures.common.client.model.ThicketEntityModel;
import com.faboslav.variantsandventures.common.events.client.RegisterEntityLayersEvent;
import com.faboslav.variantsandventures.common.util.LayerDefinitionFactory;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;

public final class VariantsAndVenturesModelLayers {
   private static final String GELID_ID = "gelid";
   private static final String THICKET_ID = "thicket";
   private static final String MURK_ID = "murk";
   private static final String VERDANT_ID = "verdant";
   public static final ModelLayerLocation GELID = new ModelLayerLocation(VariantsAndVentures.makeID("gelid"), "main");
   public static final ModelLayerLocation GELID_OUTER = new ModelLayerLocation(VariantsAndVentures.makeID("gelid"), "outer");
   public static final ModelLayerLocation MURK = new ModelLayerLocation(VariantsAndVentures.makeID("murk"), "main");
   public static final ModelLayerLocation THICKET = new ModelLayerLocation(VariantsAndVentures.makeID("thicket"), "main");
   public static final ModelLayerLocation THICKET_OUTER = new ModelLayerLocation(VariantsAndVentures.makeID("thicket"), "outer");
   public static final ModelLayerLocation VERDANT = new ModelLayerLocation(VariantsAndVentures.makeID("verdant"), "main");
   public static final ModelLayerLocation GELID_INNER_ARMOR = new ModelLayerLocation(VariantsAndVentures.makeID("gelid"), "inner_armor");
   public static final ModelLayerLocation GELID_OUTER_ARMOR = new ModelLayerLocation(VariantsAndVentures.makeID("gelid"), "outer_armor");
   public static final ModelLayerLocation MURK_INNER_ARMOR = new ModelLayerLocation(VariantsAndVentures.makeID("murk"), "inner_armor");
   public static final ModelLayerLocation MURK_OUTER_ARMOR = new ModelLayerLocation(VariantsAndVentures.makeID("murk"), "outer_armor");
   public static final ModelLayerLocation THICKET_INNER_ARMOR = new ModelLayerLocation(VariantsAndVentures.makeID("thicket"), "inner_armor");
   public static final ModelLayerLocation THICKET_OUTER_ARMOR = new ModelLayerLocation(VariantsAndVentures.makeID("thicket"), "outer_armor");
   public static final ModelLayerLocation VERDANT_INNER_ARMOR = new ModelLayerLocation(VariantsAndVentures.makeID("verdant"), "inner_armor");
   public static final ModelLayerLocation VERDANT_OUTER_ARMOR = new ModelLayerLocation(VariantsAndVentures.makeID("verdant"), "outer_armor");

   public static void registerEntityLayers(RegisterEntityLayersEvent event) {
      event.register(GELID, GelidEntityModel::createBodyLayer);
      event.register(GELID_OUTER, GelidEntityModel::createOuterLayer);
      event.register(MURK, MurkEntityModel::createBodyLayer);
      event.register(THICKET, ThicketEntityModel::createBodyLayer);
      event.register(THICKET_OUTER, ThicketEntityModel::createOuterLayer);
      event.register(VERDANT, SkeletonModel::createBodyLayer);
      event.register(GELID_INNER_ARMOR, GelidEntityModel::createInnerArmorLayer);
      event.register(GELID_OUTER_ARMOR, GelidEntityModel::createOuterArmorLayer);
      event.register(MURK_INNER_ARMOR, MurkEntityModel::createInnerArmorLayer);
      event.register(MURK_OUTER_ARMOR, MurkEntityModel::createOuterArmorLayer);
      event.register(THICKET_INNER_ARMOR, ThicketEntityModel::createInnerArmorLayer);
      event.register(THICKET_OUTER_ARMOR, ThicketEntityModel::createOuterArmorLayer);
      event.register(VERDANT_INNER_ARMOR, LayerDefinitionFactory::createInnerArmorLayer);
      event.register(VERDANT_OUTER_ARMOR, LayerDefinitionFactory::createOuterArmorLayer);
   }

   private VariantsAndVenturesModelLayers() {
   }
}
