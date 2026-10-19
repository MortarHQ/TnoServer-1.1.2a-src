package com.shin.tensura_kumodesu.data.existence;

import com.shin.tensura_kumodesu.registry.entities.KumodesuEntities;
import com.shin.tensura_kumodesu.registry.skill.KumoExtraSkills;
import com.shin.tensura_kumodesu.registry.skill.KumoIntrinsicSkills;
import io.github.manasmods.tensura.data.existence.EntityExistenceData;
import io.github.manasmods.tensura.registry.data.TensuraCustomData;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class KumodesuEntityExistenceData {
   public static void bootstrap(BootstrapContext<EntityExistenceData> context) {
      applyTaratect(context);
   }

   public static void register(BootstrapContext<EntityExistenceData> context, EntityExistenceData data) {
      ResourceKey<EntityExistenceData> key = ResourceKey.create(TensuraCustomData.ENTITY_EXISTENCE, data.entity());
      context.register(key, data);
   }

   private static void applyTaratect(BootstrapContext<EntityExistenceData> context) {
      List<ResourceLocation> smalllessertaratectSkills = new ArrayList<>();
      smalllessertaratectSkills.add(CommonSkills.POISON.getId());
      smalllessertaratectSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      register(context, EntityExistenceData.getDefault(KumodesuEntities.SMALL_LESSER_TARATECT.getId(), 12, 100, 250, 500, 1000, smalllessertaratectSkills));
      List<ResourceLocation> orthocadinahtSkills = new ArrayList<>();
      orthocadinahtSkills.add(KumoIntrinsicSkills.DIVINEMAGICRELEASE.getId());
      orthocadinahtSkills.add(ResistanceSkills.MAGIC_RESISTANCE.getId());
      orthocadinahtSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      orthocadinahtSkills.add(ExtraSkills.STRENGTHEN_BODY.getId());
      orthocadinahtSkills.add(ExtraSkills.STEEL_STRENGTH.getId());
      orthocadinahtSkills.add(ExtraSkills.SAGE.getId());
      register(context, EntityExistenceData.getDefault(KumodesuEntities.ORTHOCADINAHT.getId(), 6200, 2800000, 2800000, 1000000, 1000000, orthocadinahtSkills));
      List<ResourceLocation> arachneSkills = new ArrayList<>();
      arachneSkills.add(ExtraSkills.CHANT_ANNULMENT.getId());
      arachneSkills.add(ExtraSkills.ANALYTICAL_APPRAISAL.getId());
      arachneSkills.add(ExtraSkills.SAGE.getId());
      arachneSkills.add(UniqueSkills.PRIDE.getId());
      arachneSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      arachneSkills.add(ExtraSkills.INFINITE_REGENERATION.getId());
      arachneSkills.add(IntrinsicSkills.DIVINE_KI_RELEASE.getId());
      arachneSkills.add(ResistanceSkills.MAGIC_RESISTANCE.getId());
      arachneSkills.add(ResistanceSkills.POISON_NULLIFICATION.getId());
      arachneSkills.add(ResistanceSkills.PHYSICAL_ATTACK_RESISTANCE.getId());
      arachneSkills.add(ResistanceSkills.SPIRITUAL_ATTACK_RESISTANCE.getId());
      arachneSkills.add(ExtraSkills.STRENGTHEN_BODY.getId());
      arachneSkills.add(KumoIntrinsicSkills.DEADLYSPIDERPOISON.getId());
      arachneSkills.add(ExtraSkills.STEEL_STRENGTH.getId());
      arachneSkills.add(CommonSkills.CORROSION.getId());
      register(context, EntityExistenceData.getDefault(KumodesuEntities.ARACHNE.getId(), 10360, 3000000, 3000000, 500000, 500000, arachneSkills));
      List<ResourceLocation> zanahorowaSkills = new ArrayList<>();
      zanahorowaSkills.add(ResistanceSkills.SPIRITUAL_ATTACK_RESISTANCE.getId());
      zanahorowaSkills.add(ResistanceSkills.POISON_NULLIFICATION.getId());
      zanahorowaSkills.add(UniqueSkills.PRIDE.getId());
      zanahorowaSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      zanahorowaSkills.add(ExtraSkills.INFINITE_REGENERATION.getId());
      zanahorowaSkills.add(ExtraSkills.STRENGTHEN_BODY.getId());
      zanahorowaSkills.add(KumoIntrinsicSkills.DEADLYSPIDERPOISON.getId());
      zanahorowaSkills.add(ExtraSkills.STEEL_STRENGTH.getId());
      zanahorowaSkills.add(CommonSkills.CORROSION.getId());
      register(context, EntityExistenceData.getDefault(KumodesuEntities.ZANA_HOROWA.getId(), 5360, 700000, 700000, 300000, 300000, zanahorowaSkills));
      List<ResourceLocation> edesaineSkills = new ArrayList<>();
      edesaineSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      edesaineSkills.add(ExtraSkills.ULTRASPEED_REGENERATION.getId());
      edesaineSkills.add(ExtraSkills.STRENGTHEN_BODY.getId());
      edesaineSkills.add(KumoIntrinsicSkills.DEADLYSPIDERPOISON.getId());
      edesaineSkills.add(ExtraSkills.STEEL_STRENGTH.getId());
      edesaineSkills.add(CommonSkills.CORROSION.getId());
      register(context, EntityExistenceData.getDefault(KumodesuEntities.EDE_SAINE.getId(), 840, 350000, 350000, 250000, 250000, edesaineSkills));
      List<ResourceLocation> zoeeleSkills = new ArrayList<>();
      zoeeleSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      zoeeleSkills.add(KumoIntrinsicSkills.DEADLYSPIDERPOISON.getId());
      zoeeleSkills.add(ExtraSkills.STEEL_STRENGTH.getId());
      zoeeleSkills.add(CommonSkills.CORROSION.getId());
      zoeeleSkills.add(CommonSkills.SELF_REGENERATION.getId());
      register(context, EntityExistenceData.getDefault(KumodesuEntities.ZOA_ELE.getId(), 660, 250000, 250000, 150000, 150000, zoeeleSkills));
      List<ResourceLocation> smallpoisontaratectSkills = new ArrayList<>();
      smallpoisontaratectSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      zoeeleSkills.add(KumoExtraSkills.DEADLYPOISON.getId());
      register(
         context, EntityExistenceData.getDefault(KumodesuEntities.SMALL_POISON_TARATECT.getId(), 215, 67000, 67000, 33000, 33000, smallpoisontaratectSkills)
      );
      List<ResourceLocation> queentaratectSkills = new ArrayList<>();
      queentaratectSkills.add(ResistanceSkills.MAGIC_RESISTANCE.getId());
      queentaratectSkills.add(ResistanceSkills.PIERCE_RESISTANCE.getId());
      queentaratectSkills.add(ResistanceSkills.POISON_NULLIFICATION.getId());
      queentaratectSkills.add(ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.getId());
      queentaratectSkills.add(ResistanceSkills.ABNORMAL_CONDITION_NULLIFICATION.getId());
      queentaratectSkills.add(ResistanceSkills.THERMAL_FLUCTUATION_NULLIFICATION.getId());
      queentaratectSkills.add(ResistanceSkills.SPIRITUAL_ATTACK_RESISTANCE.getId());
      queentaratectSkills.add(ExtraSkills.INFINITE_REGENERATION.getId());
      queentaratectSkills.add(ExtraSkills.STEEL_STRENGTH.getId());
      queentaratectSkills.add(ExtraSkills.STRENGTHEN_BODY.getId());
      queentaratectSkills.add(ExtraSkills.MANA_MANIPULATION.getId());
      queentaratectSkills.add(KumoExtraSkills.DEADLYPOISON.getId());
      queentaratectSkills.add(IntrinsicSkills.DIVINE_KI_RELEASE.getId());
      queentaratectSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      register(context, EntityExistenceData.getDefault(KumodesuEntities.QUEEN_TARATECT.getId(), 9100, 1000000, 1000000, 2500000, 2500000, queentaratectSkills));
      List<ResourceLocation> archtaratectSkills = new ArrayList<>();
      archtaratectSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      archtaratectSkills.add(ExtraSkills.STEEL_STRENGTH.getId());
      archtaratectSkills.add(ExtraSkills.STRENGTHEN_BODY.getId());
      archtaratectSkills.add(ResistanceSkills.ABNORMAL_CONDITION_NULLIFICATION.getId());
      archtaratectSkills.add(CommonSkills.POISON.getId());
      register(context, EntityExistenceData.getDefault(KumodesuEntities.ARCH_TARATECT.getId(), 4700, 1000000, 1000000, 1500000, 1500000, archtaratectSkills));
      List<ResourceLocation> greatertaratectSkills = new ArrayList<>();
      greatertaratectSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      greatertaratectSkills.add(ExtraSkills.STEEL_STRENGTH.getId());
      greatertaratectSkills.add(CommonSkills.POISON.getId());
      register(context, EntityExistenceData.getDefault(KumodesuEntities.GREATER_TARATECT.getId(), 900, 350000, 350000, 450000, 450000, greatertaratectSkills));
      List<ResourceLocation> greattaratectSkills = new ArrayList<>();
      greattaratectSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      greattaratectSkills.add(CommonSkills.STRENGTH.getId());
      greattaratectSkills.add(CommonSkills.POISON.getId());
      register(context, EntityExistenceData.getDefault(KumodesuEntities.GREAT_TARATECT.getId(), 700, 150000, 150000, 250000, 250000, greattaratectSkills));
      List<ResourceLocation> taratectSkills = new ArrayList<>();
      taratectSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      taratectSkills.add(CommonSkills.POISON.getId());
      register(context, EntityExistenceData.getDefault(KumodesuEntities.TARATECT.getId(), 200, 33000, 33000, 67000, 67000, taratectSkills));
      List<ResourceLocation> smalltaratectSkills = new ArrayList<>();
      smalltaratectSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      smalltaratectSkills.add(CommonSkills.POISON.getId());
      register(context, EntityExistenceData.getDefault(KumodesuEntities.SMALL_TARATECT.getId(), 18, 6600, 6600, 13400, 13400, smalltaratectSkills));
      List<ResourceLocation> greatpoisonTaratect = new ArrayList<>();
      greatpoisonTaratect.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      greatpoisonTaratect.add(KumoIntrinsicSkills.FATALSPIDERPOISON.getId());
      greatpoisonTaratect.add(ExtraSkills.ULTRASPEED_REGENERATION.getId());
      greatpoisonTaratect.add(ResistanceSkills.POISON_NULLIFICATION.getId());
      greatpoisonTaratect.add(IntrinsicSkills.DIVINE_KI_RELEASE.getId());
      register(
         context, EntityExistenceData.getDefault(KumodesuEntities.GREAT_POISON_TARATECT.getId(), 6000, 1000000, 1000000, 2800000, 2800000, greatpoisonTaratect)
      );
      List<ResourceLocation> poisontaratectSkills = new ArrayList<>();
      poisontaratectSkills.add(KumoIntrinsicSkills.DEADLYSPIDERPOISON.getId());
      poisontaratectSkills.add(ResistanceSkills.POISON_RESISTANCE.getId());
      register(context, EntityExistenceData.getDefault(KumodesuEntities.POISON_TARATECT.getId(), 2800, 150000, 150000, 600000, 600000, poisontaratectSkills));
      List<ResourceLocation> lesserpoisontaratectSkills = new ArrayList<>();
      lesserpoisontaratectSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      lesserpoisontaratectSkills.add(KumoExtraSkills.DEADLYPOISON.getId());
      register(
         context,
         EntityExistenceData.getDefault(KumodesuEntities.LESSER_POISON_TARATECT.getId(), 150, 50000, 50000, 100000, 100000, lesserpoisontaratectSkills)
      );
      List<ResourceLocation> lessertaratectSkills = new ArrayList<>();
      lessertaratectSkills.add(ExtraSkills.STICKY_STEEL_THREAD.getId());
      lessertaratectSkills.add(CommonSkills.POISON.getId());
      register(context, EntityExistenceData.getDefault(KumodesuEntities.LESSER_TARATECT.getId(), 20, 6600, 6600, 13400, 13400, lessertaratectSkills));
   }
}
