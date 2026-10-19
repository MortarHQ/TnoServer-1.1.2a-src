package com.bobmowzie.mowziesmobs;

import com.bobmowzie.mowziesmobs.client.ClientLayerRegistry;
import com.bobmowzie.mowziesmobs.client.MMModels;
import com.bobmowzie.mowziesmobs.client.render.entity.FrozenRenderHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilityClientEventHandler;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemBlowgun;
import com.bobmowzie.mowziesmobs.server.item.ItemEarthrendGauntlet;
import com.bobmowzie.mowziesmobs.server.item.ItemGeomancerArmor;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemSculptorStaff;
import com.bobmowzie.mowziesmobs.server.item.ItemSolVisage;
import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import com.bobmowzie.mowziesmobs.server.item.ItemWroughtHelm;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = "mowziesmobs", dist = Dist.CLIENT)
public class MMClient {
   public MMClient(IEventBus modBus, ModContainer container) {
      modBus.register(MMModels.class);
      modBus.addListener(ClientLayerRegistry::onAddLayers);
      modBus.addListener(this::init);
      modBus.addListener(this::registerClientExtensions);
      NeoForge.EVENT_BUS.addListener(FrozenRenderHandler::onRenderHand);
      NeoForge.EVENT_BUS.addListener(AbilityClientEventHandler::onRenderTick);
      container.registerConfig(Type.CLIENT, ConfigHandler.CLIENT_CONFIG);
   }

   private void init(FMLLoadCompleteEvent event) {
      ItemPropertyFunction pulling = ItemProperties.getProperty(Items.BOW.getDefaultInstance(), ResourceLocation.withDefaultNamespace("pulling"));
      ItemProperties.register(((ItemBlowgun)ItemHandler.BLOWGUN.get()).asItem(), ResourceLocation.withDefaultNamespace("pulling"), pulling);
   }

   private void registerClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerItem(ItemWroughtHelm.ArmorRender.INSTANCE, new Holder[]{ItemHandler.WROUGHT_HELMET});
      event.registerItem(new ItemSolVisage.ClientExtensions(), new Holder[]{ItemHandler.SOL_VISAGE});
      event.registerItem(new ItemEarthrendGauntlet.ClientExtensions(), new Holder[]{ItemHandler.EARTHREND_GAUNTLET});
      event.registerItem(
         new ItemGeomancerArmor.ClientExtensions(),
         new Holder[]{ItemHandler.GEOMANCER_BEADS, ItemHandler.GEOMANCER_BELT, ItemHandler.GEOMANCER_ROBE, ItemHandler.GEOMANCER_SANDALS}
      );
      event.registerItem(
         new ItemUmvuthanaMask.ClientExtensions(),
         new Holder[]{
            ItemHandler.UMVUTHANA_MASK_FURY,
            ItemHandler.UMVUTHANA_MASK_FEAR,
            ItemHandler.UMVUTHANA_MASK_RAGE,
            ItemHandler.UMVUTHANA_MASK_BLISS,
            ItemHandler.UMVUTHANA_MASK_MISERY,
            ItemHandler.UMVUTHANA_MASK_FAITH
         }
      );
      event.registerItem(new ItemSculptorStaff.ClientExtensions(), new Holder[]{ItemHandler.SCULPTOR_STAFF});
   }
}
