package com.github.wal_bos.moreskills.registry.entity;

import com.github.wal_bos.moreskills.world.entity.ArsWeissCloneEntity;
import com.github.wal_bos.moreskills.world.entity.AvalonKnightEntity;
import com.github.wal_bos.moreskills.world.entity.AvalonNovariEntity;
import com.github.wal_bos.moreskills.world.entity.AvalonNovariTransitionEntity;
import com.github.wal_bos.moreskills.world.entity.BerettaEntity;
import com.github.wal_bos.moreskills.world.entity.FeldwayAngelEntity;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakenedEntity;
import com.github.wal_bos.moreskills.world.entity.FeldwayAwakeningEntity;
import com.github.wal_bos.moreskills.world.entity.FeldwayEntity;
import com.github.wal_bos.moreskills.world.entity.MichaelManasEntity;
import com.github.wal_bos.moreskills.world.entity.TheCoordinateEntity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

public class ModEntityAttributes {
   public static void onAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)ModEntityTypes.FELDWAY.get(), FeldwayEntity.createAttributes().build());
      event.put((EntityType)ModEntityTypes.FELDWAY_ANGEL.get(), FeldwayAngelEntity.createAttributes().build());
      event.put((EntityType)ModEntityTypes.MICHAEL_MANAS.get(), MichaelManasEntity.createAttributes().build());
      event.put((EntityType)ModEntityTypes.FELDWAY_AWAKENING.get(), FeldwayAwakeningEntity.createAttributes().build());
      event.put((EntityType)ModEntityTypes.FELDWAY_AWAKENED.get(), FeldwayAwakenedEntity.createAttributes().build());
      event.put((EntityType)ModEntityTypes.THE_COORDINATE.get(), TheCoordinateEntity.createAttributes().build());
      event.put((EntityType)ModEntityTypes.ARS_WEISS_CLONE.get(), ArsWeissCloneEntity.createAttributes().build());
      event.put((EntityType)ModEntityTypes.AVALON_NOVARI.get(), AvalonNovariEntity.createAttributes().build());
      event.put((EntityType)ModEntityTypes.AVALON_NOVARI_TRANSITION.get(), AvalonNovariTransitionEntity.createAttributes().build());
      event.put((EntityType)ModEntityTypes.BERETTA.get(), BerettaEntity.createAttributes().build());
      event.put((EntityType)ModEntityTypes.AVALON_KNIGHT.get(), AvalonKnightEntity.createAttributes().build());
   }
}
