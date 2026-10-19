package com.shin.tensura_kumodesu.neoforge;

import com.shin.tensura_kumodesu.TensuraKumodesu;
import com.shin.tensura_kumodesu.bridge.neoforge.KumodesuAttributeRegisterImpl;
import com.shin.tensura_kumodesu.client.KumodesuClient;
import com.shin.tensura_kumodesu.entity.taratect.ArachneTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.ArchTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.EdeSaineTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.GreatPoisonTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.GreatTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.GreaterTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.LesserPoisonTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.LesserTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.OrthocadinahtTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.PoisonTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.QueenTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.SmallLesserTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.SmallPoisonTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.SmallTaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.TaratectEntity;
import com.shin.tensura_kumodesu.entity.taratect.ZanaHorowaTaratectEntity;
import com.shin.tensura_kumodesu.neoforge.data.KumodesuRegistryProvider;
import com.shin.tensura_kumodesu.neoforge.data.TensuraKumodesuLanguageProvider;
import com.shin.tensura_kumodesu.registry.entities.KumodesuEntities;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@Mod("tensura_kumodesu")
public final class TensuraKumodesuNeoForge {
   public TensuraKumodesuNeoForge(IEventBus modBus) {
      if (!DatagenModLoader.isRunningDataGen()) {
         TensuraKumodesu.init();
         modBus.addListener(this::registerAttributes);
         KumodesuAttributeRegisterImpl.ATTRIBUTES.register(modBus);
         if (FMLEnvironment.dist == Dist.CLIENT) {
            KumodesuClient.init();
         }
      } else {
         TensuraKumodesu.init();
         KumodesuAttributeRegisterImpl.ATTRIBUTES.register(modBus);
         modBus.addListener(this::gatherData);
      }
   }

   private void gatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      PackOutput output = generator.getPackOutput();
      CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
      ExistingFileHelper helper = event.getExistingFileHelper();
      generator.addProvider(event.includeClient(), new TensuraKumodesuLanguageProvider(event.getGenerator().getPackOutput()));
      KumodesuRegistryProvider kumodesuRegistryProvider = new KumodesuRegistryProvider(output, lookupProvider);
      generator.addProvider(event.includeServer(), kumodesuRegistryProvider);
   }

   private void registerAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)KumodesuEntities.SMALL_LESSER_TARATECT.get(), SmallLesserTaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.ORTHOCADINAHT.get(), OrthocadinahtTaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.SMALL_TARATECT.get(), SmallTaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.TARATECT.get(), TaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.GREAT_TARATECT.get(), GreatTaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.GREATER_TARATECT.get(), GreaterTaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.ARCH_TARATECT.get(), ArchTaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.QUEEN_TARATECT.get(), QueenTaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.SMALL_POISON_TARATECT.get(), SmallPoisonTaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.ZOA_ELE.get(), LesserTaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.EDE_SAINE.get(), EdeSaineTaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.ZANA_HOROWA.get(), ZanaHorowaTaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.ARACHNE.get(), ArachneTaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.LESSER_TARATECT.get(), LesserTaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.LESSER_POISON_TARATECT.get(), LesserPoisonTaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.POISON_TARATECT.get(), PoisonTaratectEntity.createAttributes().build());
      event.put((EntityType)KumodesuEntities.GREAT_POISON_TARATECT.get(), GreatPoisonTaratectEntity.createAttributes().build());
   }

   @EventBusSubscriber(modid = "tensura_kumodesu", value = Dist.CLIENT)
   public static class ClientModEvents {
      @SubscribeEvent
      public static void registerLayerDefinitions(RegisterLayerDefinitions event) {
      }
   }

   @EventBusSubscriber(modid = "tensura_kumodesu")
   public static class Events {
      @SubscribeEvent
      public static void onCommonSetup(FMLCommonSetupEvent event) {
         event.enqueueWork(TensuraKumodesu::postInit);
      }
   }
}
