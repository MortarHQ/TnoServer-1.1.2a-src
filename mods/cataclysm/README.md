# L_Ender's Cataclysm 1.21.1（`cataclysm`）

> jar：`[灾变]Cataclysm.jar` ｜ 版本：`3.27` ｜ 作者：L_Ender
> 规模：**839 个类 / 81 个包 / 442800 行** ｜ 事件处理类 3 ｜ Mixin 23 ｜ 注册/管理类 6

**依赖**：`curios`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.github.L_Ender.cataclysm.client.render.entity` | 92 | `Death_Laser_beam_Renderer`、`Cm_Falling_Block_Renderer`、`The_Harbinger_Renderer` |
| `com.github.L_Ender.cataclysm.client.model.entity` | 70 | `The_Leviathan_Model`、`Amethyst_Crab_Model`、`Ignis_Model` |
| `com.github.L_Ender.cataclysm.items` | 58 | `Cursed_bow`、`BlockItemInventoryOnly`、`Wrath_of_the_desert` |
| `com.github.L_Ender.cataclysm.entity.projectile` | 45 | `Death_Laser_Beam_Entity`、`Brontes_Entity`、`Player_Ceraunus_Entity` |
| `com.github.L_Ender.cataclysm.client.render.layer` | 44 | `Item_Layer`、`The_Harbinger_Item_Layer`、`Clawdian_Hold_Block_Layer` |
| `com.github.L_Ender.cataclysm.client.particle` | 42 | `ParryParticle`、`AbstractTrailParticle`、`Circle_Particle` |
| `com.github.L_Ender.cataclysm.client.animation` | 34 | `Symbiocto_Animation`、`Ancient_Remnant_Animation`、`Maledictus_Animation` |
| `com.github.L_Ender.cataclysm.blocks` | 27 | `PointedIcicleBlock`、`Door_of_Seal_Block`、`Statue_Block` |
| `com.github.L_Ender.cataclysm.init` | 23 | `ModEntities`、`ModAttribute`、`ModParticle` |
| `com.github.L_Ender.cataclysm.client.model.item` | 23 | `Ceraunus_Item_Model`、`Wrath_of_Desert_Model`、`Meat_Shredder_Model` |
| `com.github.L_Ender.cataclysm.client.particle.Options` | 23 | `GatheringWaterParticleOptions`、`ParryParticleOptions`、`RingParticleOptions` |
| `com.github.L_Ender.cataclysm.entity.etc` | 15 | `Animation_Monsters`、`CMEntityMoveHelper`、`IShieldEntity` |
| `com.github.L_Ender.cataclysm.message` | 15 | `MessageEntityCameraSwitch`、`MessageParticle`、`MessageBossBar` |
| `com.github.L_Ender.cataclysm.blockentities` | 14 | `AltarOfAbyss_Block_Entity`、`AltarOfFire_Block_Entity`、`AltarOfAmethyst_Block_Entity` |
| `com.github.L_Ender.cataclysm.client.model.block` | 14 | `Abyssal_Egg_Model`、`Boss_Respawn_Spawner_Model`、`Mechanical_Anvil_Model` |
| `com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan` | 13 | `The_Leviathan_Entity`、`Abyss_Blast_Entity`、`Portal_Abyss_Blast_Entity` |
| `com.github.L_Ender.cataclysm.entity.effect` | 13 | `Wither_Smoke_Effect_Entity`、`Flame_Strike_Entity`、`Lightning_Storm_Entity` |
| `com.github.L_Ender.cataclysm.mixin` | 12 | `NoteBlockMixin`、`NoteBlockInstrumentMixin`、`LivingEntityMixin` |
| `com.github.L_Ender.cataclysm.client.render.blockentity` | 12 | `RendererAltar_of_Amethyst`、`Cataclysm_Skull_Block_Renderer`、`Door_Of_Seal_Renderer` |
| `com.github.L_Ender.cataclysm.effects` | 12 | `EffectAbyssal_Burn`、`EffectStun`、`EffectGhost_Sickness` |
| `com.github.L_Ender.cataclysm.util` | 10 | `EntityUtil`、`CMDamageTypes`、`CMMathUtil` |
| `com.github.L_Ender.cataclysm.client.model.item.CuriosModel` | 10 | `Belt_Of_Monstrosity_Model`、`Chitin_Claw_Model`、`Sturdy_Boots_Model` |
| `com.github.L_Ender.cataclysm.mixin.accessor` | 9 | `StructureManagerAccessor`、`LivingEntityRendererAccessor`、`StructureProcessorAccessor` |
| `com.github.L_Ender.cataclysm.client.render.item.CuriosRenderer` | 9 | `Sticky_Gloves_Renderer`、`Chitin_Claw_Renderer`、`Blazing_Grips_Renderer` |
| `com.github.L_Ender.cataclysm.entity.AI` | 9 | `EntityAINearestTarget3D`、`SemiAquaticAIRandomSwimming`、`AnimalAIRandomSwimming` |
| `com.github.L_Ender.cataclysm.entity.Deepling` | 8 | `Deepling_Entity`、`Deepling_Brute_Entity`、`Coral_Golem_Entity` |
| `com.github.L_Ender.cataclysm.items.CuriosItem` | 8 | `CuriosItem`、`Blazing_Grips`、`AttributeContainer` |
| `com.github.L_Ender.cataclysm.entity.AnimationMonster.AI` | 8 | `AttackMoveGoal`、`AnimationGoal`、`AdvancedHurtByTargetGoal` |
| `com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AcropolisMonsters` | 7 | `Clawdian_Entity`、`Hippocamtus_Entity`、`Drowned_Host_Entity` |
| `com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters` | 7 | `Ender_Guardian_Entity`、`The_Harbinger_Entity`、`Ignis_Entity` |
| `com.github.L_Ender.cataclysm.entity.Pet.AI` | 7 | `TameableAIFollowOwnerWater`、`InternalPetAttackGoal`、`InternalPetMoveGoal` |
| `com.github.L_Ender.cataclysm.world.structures.terrainadaptation` | 7 | `EnhancedTerrainAdaptation`、`NoneAdaptation`、`CustomAdaptation` |
| `com.github.L_Ender.cataclysm.entity.InternalAnimationMonster` | 6 | `Coralssus_Entity`、`Wadjet_Entity`、`Kobolediator_Entity` |
| `com.github.L_Ender.cataclysm.entity.Pet` | 6 | `The_Baby_Leviathan_Entity`、`Netherite_Ministrosity_Entity`、`Modern_Remnant_Entity` |
| `com.github.L_Ender.cataclysm.structures.jisaw` | 6 | `CataclysmJigsawManager`、`JigsawManager`、`PieceEntry` |
| `com.github.L_Ender.cataclysm.client.model.armor` | 6 | `Ignitium_Armor_Model`、`MonstrousHelm_Model`、`Bone_Reptile_Armor_Model` |
| `com.github.L_Ender.cataclysm.entity.AnimationMonster` | 5 | `Endermaptera_Entity`、`Koboleton_Entity`、`The_Watcher_Entity` |
| `com.github.L_Ender.cataclysm.config` | 5 | `CommonConfig`、`CMCommonConfig`、`ClientConfig` |
| `com.github.L_Ender.cataclysm.entity.Pet.Summoned_Entity` | 5 | `Abstract_Summoned_Entity`、`Ignited_Sword_Entity`、`FlyingFollowSummonerGoal` |
| `com.github.L_Ender.cataclysm.jei` | 5 | `WeaponfusionRecipeCategory`、`AltarOfAmethystRecipeCategory`、`CMRecipes` |
| … | | 其余 41 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ServerEventHandler` | class | — | EventBusSubscriber | 30 | 473 | [ServerEventHandler.java](com/github/L_Ender/cataclysm/event/ServerEventHandler.java) |
| `ModEntities` | class | — | EventBusSubscriber | 4 | 911 | [ModEntities.java](com/github/L_Ender/cataclysm/init/ModEntities.java) |
| `ModAttribute` | class | — | EventBusSubscriber | 1 | 34 | [ModAttribute.java](com/github/L_Ender/cataclysm/init/ModAttribute.java) |
| `Clawdian_Entity` | class | Internal_Animation_Monster | — | 62 | 1330 | [Clawdian_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/AcropolisMonsters/Clawdian_Entity.java) |
| `Netherite_Monstrosity_Entity` | class | IABoss_monster | — | 106 | 1654 | [Netherite_Monstrosity_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/IABossMonsters/NewNetherite_Monstrosity/Netherite_Monstrosity_Entity.java) |
| `Scylla_Entity` | class | IABoss_monster | — | 142 | 3689 | [Scylla_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/IABossMonsters/Scylla/Scylla_Entity.java) |
| `Ancient_Remnant_Entity` | class | IABoss_monster | — | 103 | 2202 | [Ancient_Remnant_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/IABossMonsters/Ancient_Remnant/Ancient_Remnant_Entity.java) |
| `Maledictus_Entity` | class | IABoss_monster | — | 140 | 3095 | [Maledictus_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/IABossMonsters/Maledictus/Maledictus_Entity.java) |
| `Ender_Guardian_Entity` | class | LLibrary_Boss_Monster | — | 73 | 1607 | [Ender_Guardian_Entity.java](com/github/L_Ender/cataclysm/entity/AnimationMonster/BossMonsters/Ender_Guardian_Entity.java) |
| `The_Harbinger_Entity` | class | LLibrary_Boss_Monster | — | 81 | 1276 | [The_Harbinger_Entity.java](com/github/L_Ender/cataclysm/entity/AnimationMonster/BossMonsters/The_Harbinger_Entity.java) |
| `Ignis_Entity` | class | LLibrary_Boss_Monster | — | 103 | 3633 | [Ignis_Entity.java](com/github/L_Ender/cataclysm/entity/AnimationMonster/BossMonsters/Ignis_Entity.java) |
| `The_Leviathan_Entity` | class | LLibrary_Boss_Monster | — | 143 | 2685 | [The_Leviathan_Entity.java](com/github/L_Ender/cataclysm/entity/AnimationMonster/BossMonsters/The_Leviathan/The_Leviathan_Entity.java) |
| `Coralssus_Entity` | class | Internal_Animation_Monster | — | 73 | 866 | [Coralssus_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/Coralssus_Entity.java) |
| `Wadjet_Entity` | class | Internal_Animation_Monster | — | 53 | 727 | [Wadjet_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/Wadjet_Entity.java) |
| `Kobolediator_Entity` | class | Internal_Animation_Monster | — | 48 | 778 | [Kobolediator_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/Kobolediator_Entity.java) |
| `Hippocamtus_Entity` | class | Internal_Animation_Monster | — | 43 | 616 | [Hippocamtus_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/AcropolisMonsters/Hippocamtus_Entity.java) |
| `Aptrgangr_Entity` | class | Internal_Animation_Monster | — | 50 | 791 | [Aptrgangr_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/Draugar/Aptrgangr_Entity.java) |
| `Amethyst_Crab_Entity` | class | LLibrary_Boss_Monster | — | 45 | 647 | [Amethyst_Crab_Entity.java](com/github/L_Ender/cataclysm/entity/AnimationMonster/BossMonsters/Amethyst_Crab_Entity.java) |
| `The_Baby_Leviathan_Entity` | class | LLibraryAnimationPet | — | 49 | 738 | [The_Baby_Leviathan_Entity.java](com/github/L_Ender/cataclysm/entity/Pet/The_Baby_Leviathan_Entity.java) |
| `StructureManagerAccessor` | interface | — | Mixin | 0 | 13 | [StructureManagerAccessor.java](com/github/L_Ender/cataclysm/mixin/accessor/StructureManagerAccessor.java) |
| `ClientEvent` | class | — | — | 20 | 560 | [ClientEvent.java](com/github/L_Ender/cataclysm/client/event/ClientEvent.java) |
| `Cataclysm` | class | — | Mod | 3 | 137 | [Cataclysm.java](com/github/L_Ender/cataclysm/Cataclysm.java) |
| `Ignited_Berserker_Entity` | class | Internal_Animation_Monster | — | 31 | 480 | [Ignited_Berserker_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/Ignited_Berserker_Entity.java) |
| `The_Prowler_Entity` | class | Internal_Animation_Monster | — | 35 | 596 | [The_Prowler_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/The_Prowler_Entity.java) |
| `Drowned_Host_Entity` | class | Zombie | — | 46 | 512 | [Drowned_Host_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/AcropolisMonsters/Drowned_Host_Entity.java) |
| `Urchinkin_Entity` | class | Monster | — | 31 | 410 | [Urchinkin_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/AcropolisMonsters/Urchinkin_Entity.java) |
| `Cindaria_Entity` | class | Internal_Animation_Monster | — | 34 | 472 | [Cindaria_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/AcropolisMonsters/Cindaria_Entity.java) |
| `Elite_Draugr_Entity` | class | Internal_Animation_Monster | — | 47 | 502 | [Elite_Draugr_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/Draugar/Elite_Draugr_Entity.java) |
| `Ender_Golem_Entity` | class | LLibrary_Boss_Monster | — | 36 | 502 | [Ender_Golem_Entity.java](com/github/L_Ender/cataclysm/entity/AnimationMonster/BossMonsters/Ender_Golem_Entity.java) |
| `Ignited_Revenant_Entity` | class | LLibrary_Boss_Monster | — | 34 | 522 | [Ignited_Revenant_Entity.java](com/github/L_Ender/cataclysm/entity/AnimationMonster/BossMonsters/Ignited_Revenant_Entity.java) |
| `Abyss_Blast_Entity` | class | Entity | — | 31 | 404 | [Abyss_Blast_Entity.java](com/github/L_Ender/cataclysm/entity/AnimationMonster/BossMonsters/The_Leviathan/Abyss_Blast_Entity.java) |
| `Deepling_Entity` | class | AbstractDeepling | — | 39 | 468 | [Deepling_Entity.java](com/github/L_Ender/cataclysm/entity/Deepling/Deepling_Entity.java) |
| `Deepling_Brute_Entity` | class | AbstractDeepling | — | 44 | 545 | [Deepling_Brute_Entity.java](com/github/L_Ender/cataclysm/entity/Deepling/Deepling_Brute_Entity.java) |
| `Coral_Golem_Entity` | class | LLibrary_Monster | — | 48 | 577 | [Coral_Golem_Entity.java](com/github/L_Ender/cataclysm/entity/Deepling/Coral_Golem_Entity.java) |
| `Death_Laser_Beam_Entity` | class | Entity | — | 33 | 420 | [Death_Laser_Beam_Entity.java](com/github/L_Ender/cataclysm/entity/projectile/Death_Laser_Beam_Entity.java) |
| `Netherite_Ministrosity_Entity` | class | InternalAnimationPet | — | 42 | 545 | [Netherite_Ministrosity_Entity.java](com/github/L_Ender/cataclysm/entity/Pet/Netherite_Ministrosity_Entity.java) |
| `Modern_Remnant_Entity` | class | LLibraryAnimationPet | — | 33 | 429 | [Modern_Remnant_Entity.java](com/github/L_Ender/cataclysm/entity/Pet/Modern_Remnant_Entity.java) |
| `LivingEntityRendererAccessor` | interface | — | Mixin | 0 | 14 | [LivingEntityRendererAccessor.java](com/github/L_Ender/cataclysm/mixin/accessor/LivingEntityRendererAccessor.java) |
| `PointedIcicleBlock` | class | Block | — | 38 | 488 | [PointedIcicleBlock.java](com/github/L_Ender/cataclysm/blocks/PointedIcicleBlock.java) |
| `Symbiocto_Entity` | class | Monster | — | 31 | 352 | [Symbiocto_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/AcropolisMonsters/Symbiocto_Entity.java) |
| `Scylla_Ceraunus_Entity` | class | AbstractArrow | — | 31 | 324 | [Scylla_Ceraunus_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/IABossMonsters/Scylla/Scylla_Ceraunus_Entity.java) |
| `Royal_Draugr_Entity` | class | Monster | — | 32 | 312 | [Royal_Draugr_Entity.java](com/github/L_Ender/cataclysm/entity/InternalAnimationMonster/Draugar/Royal_Draugr_Entity.java) |
| `Endermaptera_Entity` | class | Monster | — | 31 | 355 | [Endermaptera_Entity.java](com/github/L_Ender/cataclysm/entity/AnimationMonster/Endermaptera_Entity.java) |
| `Portal_Abyss_Blast_Entity` | class | Entity | — | 29 | 406 | [Portal_Abyss_Blast_Entity.java](com/github/L_Ender/cataclysm/entity/AnimationMonster/BossMonsters/The_Leviathan/Portal_Abyss_Blast_Entity.java) |
| `Deepling_Angler_Entity` | class | AbstractDeepling | — | 36 | 386 | [Deepling_Angler_Entity.java](com/github/L_Ender/cataclysm/entity/Deepling/Deepling_Angler_Entity.java) |
| `Brontes_Entity` | class | AbstractArrow | — | 30 | 272 | [Brontes_Entity.java](com/github/L_Ender/cataclysm/entity/projectile/Brontes_Entity.java) |
| `AltarOfAbyss_Block_Entity` | class | BlockEntity | — | 21 | 230 | [AltarOfAbyss_Block_Entity.java](com/github/L_Ender/cataclysm/blockentities/AltarOfAbyss_Block_Entity.java) |
| `AltarOfFire_Block_Entity` | class | BlockEntity | — | 20 | 232 | [AltarOfFire_Block_Entity.java](com/github/L_Ender/cataclysm/blockentities/AltarOfFire_Block_Entity.java) |
| `CommonConfig` | class | — | — | 0 | 1373 | [CommonConfig.java](com/github/L_Ender/cataclysm/config/CommonConfig.java) |
| `Lionfish_Entity` | class | Monster | — | 28 | 266 | [Lionfish_Entity.java](com/github/L_Ender/cataclysm/entity/Deepling/Lionfish_Entity.java) |
| `Player_Ceraunus_Entity` | class | AbstractArrow | — | 29 | 250 | [Player_Ceraunus_Entity.java](com/github/L_Ender/cataclysm/entity/projectile/Player_Ceraunus_Entity.java) |
| `Tidal_Tentacle_Entity` | class | Entity | — | 26 | 273 | [Tidal_Tentacle_Entity.java](com/github/L_Ender/cataclysm/entity/projectile/Tidal_Tentacle_Entity.java) |
| `Axe_Blade_Entity` | class | Entity | — | 25 | 329 | [Axe_Blade_Entity.java](com/github/L_Ender/cataclysm/entity/projectile/Axe_Blade_Entity.java) |
| `Cursed_Sandstorm_Entity` | class | Projectile | — | 25 | 398 | [Cursed_Sandstorm_Entity.java](com/github/L_Ender/cataclysm/entity/projectile/Cursed_Sandstorm_Entity.java) |
| `Mini_Abyss_Blast_Entity` | class | Entity | — | 27 | 339 | [Mini_Abyss_Blast_Entity.java](com/github/L_Ender/cataclysm/entity/projectile/Mini_Abyss_Blast_Entity.java) |
| `Elemental_Spear_Entity` | class | Projectile | — | 26 | 241 | [Elemental_Spear_Entity.java](com/github/L_Ender/cataclysm/entity/projectile/Elemental_Spear_Entity.java) |
| `Abstract_Summoned_Entity` | class | PathfinderMob | — | 26 | 253 | [Abstract_Summoned_Entity.java](com/github/L_Ender/cataclysm/entity/Pet/Summoned_Entity/Abstract_Summoned_Entity.java) |
| `Wither_Smoke_Effect_Entity` | class | Entity | — | 25 | 254 | [Wither_Smoke_Effect_Entity.java](com/github/L_Ender/cataclysm/entity/effect/Wither_Smoke_Effect_Entity.java) |
| `Flame_Strike_Entity` | class | Entity | — | 29 | 374 | [Flame_Strike_Entity.java](com/github/L_Ender/cataclysm/entity/effect/Flame_Strike_Entity.java) |
| `Lightning_Storm_Entity` | class | Entity | — | 25 | 270 | [Lightning_Storm_Entity.java](com/github/L_Ender/cataclysm/entity/effect/Lightning_Storm_Entity.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ServerEventHandler` | 29 | `com.github.L_Ender.cataclysm.event` | [ServerEventHandler.java](com/github/L_Ender/cataclysm/event/ServerEventHandler.java) |
| `ModEntities` | 2 | `com.github.L_Ender.cataclysm.init` | [ModEntities.java](com/github/L_Ender/cataclysm/init/ModEntities.java) |
| `ModAttribute` | 1 | `com.github.L_Ender.cataclysm.init` | [ModAttribute.java](com/github/L_Ender/cataclysm/init/ModAttribute.java) |

## Mixin（23 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `StructureManagerAccessor` | `StructureManagerAccessor` | [StructureManagerAccessor.java](com/github/L_Ender/cataclysm/mixin/accessor/StructureManagerAccessor.java) |
| `LivingEntityRendererAccessor` | `LivingEntityRendererAccessor` | [LivingEntityRendererAccessor.java](com/github/L_Ender/cataclysm/mixin/accessor/LivingEntityRendererAccessor.java) |
| `NoteBlockMixin` | `NoteBlock` | [NoteBlockMixin.java](com/github/L_Ender/cataclysm/mixin/NoteBlockMixin.java) |
| `NoteBlockInstrumentMixin` | `NoteBlockInstrument` | [NoteBlockInstrumentMixin.java](com/github/L_Ender/cataclysm/mixin/NoteBlockInstrumentMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](com/github/L_Ender/cataclysm/mixin/LivingEntityMixin.java) |
| `ItemMixin` | `Item` | [ItemMixin.java](com/github/L_Ender/cataclysm/mixin/ItemMixin.java) |
| `NoMagmaBlockInStructuresMixin` | `NoMagmaBlockInStructures` | [NoMagmaBlockInStructuresMixin.java](com/github/L_Ender/cataclysm/mixin/NoMagmaBlockInStructuresMixin.java) |
| `FoodDataMixin` | `FoodData` | [FoodDataMixin.java](com/github/L_Ender/cataclysm/mixin/FoodDataMixin.java) |
| `NoLakesInStructuresMixin` | `NoLakesInStructures` | [NoLakesInStructuresMixin.java](com/github/L_Ender/cataclysm/mixin/NoLakesInStructuresMixin.java) |
| `NoGeodeInStructuresMixin` | `NoGeodeInStructures` | [NoGeodeInStructuresMixin.java](com/github/L_Ender/cataclysm/mixin/NoGeodeInStructuresMixin.java) |
| `NoOreInStructuresMixin` | `NoOreInStructures` | [NoOreInStructuresMixin.java](com/github/L_Ender/cataclysm/mixin/NoOreInStructuresMixin.java) |
| `NoDeltasInStructuresMixin` | `NoDeltasInStructures` | [NoDeltasInStructuresMixin.java](com/github/L_Ender/cataclysm/mixin/NoDeltasInStructuresMixin.java) |
| `NoMultifaceGrowthFeatureInStructuresMixin` | `NoMultifaceGrowthFeatureInStructures` | [NoMultifaceGrowthFeatureInStructuresMixin.java](com/github/L_Ender/cataclysm/mixin/NoMultifaceGrowthFeatureInStructuresMixin.java) |
| `NoBasaltColumnsInStructuresMixin` | `NoBasaltColumnsInStructures` | [NoBasaltColumnsInStructuresMixin.java](com/github/L_Ender/cataclysm/mixin/NoBasaltColumnsInStructuresMixin.java) |
| `StructureProcessorAccessor` | `StructureProcessorAccessor` | [StructureProcessorAccessor.java](com/github/L_Ender/cataclysm/mixin/accessor/StructureProcessorAccessor.java) |
| `BeardifierAccessor` | `BeardifierAccessor` | [BeardifierAccessor.java](com/github/L_Ender/cataclysm/mixin/accessor/BeardifierAccessor.java) |
| `StructureTemplatePoolAccessor` | `StructureTemplatePoolAccessor` | [StructureTemplatePoolAccessor.java](com/github/L_Ender/cataclysm/mixin/accessor/StructureTemplatePoolAccessor.java) |
| `FeaturePoolElementAccessor` | `FeaturePoolElementAccessor` | [FeaturePoolElementAccessor.java](com/github/L_Ender/cataclysm/mixin/accessor/FeaturePoolElementAccessor.java) |
| `ListPoolElementAccessor` | `ListPoolElementAccessor` | [ListPoolElementAccessor.java](com/github/L_Ender/cataclysm/mixin/accessor/ListPoolElementAccessor.java) |
| `SinglePoolElementAccessor` | `SinglePoolElementAccessor` | [SinglePoolElementAccessor.java](com/github/L_Ender/cataclysm/mixin/accessor/SinglePoolElementAccessor.java) |
| `BoundingBoxAccessor` | `BoundingBoxAccessor` | [BoundingBoxAccessor.java](com/github/L_Ender/cataclysm/mixin/accessor/BoundingBoxAccessor.java) |
| `HumanoidArmorLayerMixin` | `HumanoidArmorLayer` | [HumanoidArmorLayerMixin.java](com/github/L_Ender/cataclysm/mixin/Client/HumanoidArmorLayerMixin.java) |
| `HumanoidModelMixin` | `HumanoidModel` | [HumanoidModelMixin.java](com/github/L_Ender/cataclysm/mixin/Client/HumanoidModelMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `CommonConfig` | — | [CommonConfig.java](com/github/L_Ender/cataclysm/config/CommonConfig.java) |
| `CMCommonConfig` | — | [CMCommonConfig.java](com/github/L_Ender/cataclysm/config/CMCommonConfig.java) |
| `ClientConfig` | — | [ClientConfig.java](com/github/L_Ender/cataclysm/config/ClientConfig.java) |
| `CMClientConfig` | — | [CMClientConfig.java](com/github/L_Ender/cataclysm/config/CMClientConfig.java) |
| `ConfigHolder` | — | [ConfigHolder.java](com/github/L_Ender/cataclysm/config/ConfigHolder.java) |

## 按包浏览

- `com.github.L_Ender.cataclysm`（3 类）
- `com.github.L_Ender.cataclysm.Attachment`（4 类）
- `com.github.L_Ender.cataclysm.blockentities`（14 类）
- `com.github.L_Ender.cataclysm.blocks`（27 类）
- `com.github.L_Ender.cataclysm.blocks.Property`（1 类）
- `com.github.L_Ender.cataclysm.client.CustomRarity`（1 类）
- `com.github.L_Ender.cataclysm.client.animation`（34 类）
- `com.github.L_Ender.cataclysm.client.event`（4 类）
- `com.github.L_Ender.cataclysm.client.gui`（3 类）
- `com.github.L_Ender.cataclysm.client.model`（1 类）
- `com.github.L_Ender.cataclysm.client.model.armor`（6 类）
- `com.github.L_Ender.cataclysm.client.model.block`（14 类）
- `com.github.L_Ender.cataclysm.client.model.entity`（70 类）
- `com.github.L_Ender.cataclysm.client.model.item`（23 类）
- `com.github.L_Ender.cataclysm.client.model.item.CuriosModel`（10 类）
- `com.github.L_Ender.cataclysm.client.particle`（42 类）
- `com.github.L_Ender.cataclysm.client.particle.Options`（23 类）
- `com.github.L_Ender.cataclysm.client.render`（3 类）
- `com.github.L_Ender.cataclysm.client.render.blockentity`（12 类）
- `com.github.L_Ender.cataclysm.client.render.entity`（92 类）
- `com.github.L_Ender.cataclysm.client.render.etc`（4 类）
- `com.github.L_Ender.cataclysm.client.render.item`（2 类）
- `com.github.L_Ender.cataclysm.client.render.item.CuriosRenderer`（9 类）
- `com.github.L_Ender.cataclysm.client.render.layer`（44 类）
- `com.github.L_Ender.cataclysm.client.sound`（5 类）
- `com.github.L_Ender.cataclysm.client.tool`（1 类）
- `com.github.L_Ender.cataclysm.config`（5 类）
- `com.github.L_Ender.cataclysm.crafting`（4 类）
- `com.github.L_Ender.cataclysm.effects`（12 类）
- `com.github.L_Ender.cataclysm.entity.AI`（9 类）
- `com.github.L_Ender.cataclysm.entity.AnimationMonster`（5 类）
- `com.github.L_Ender.cataclysm.entity.AnimationMonster.AI`（8 类）
- `com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters`（7 类）
- `com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan`（13 类）
- `com.github.L_Ender.cataclysm.entity.Deepling`（8 类）
- `com.github.L_Ender.cataclysm.entity.InternalAnimationMonster`（6 类）
- `com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI`（3 类）
- `com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AcropolisMonsters`（7 类）
- `com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar`（4 类）
- `com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Goals`（2 类）
- `com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters`（1 类）
- `com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant`（1 类）
- `com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Maledictus`（1 类）
- `com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.NewNetherite_Monstrosity`（2 类）
- `com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Scylla`（2 类）
- `com.github.L_Ender.cataclysm.entity.Pet`（6 类）
- `com.github.L_Ender.cataclysm.entity.Pet.AI`（7 类）
- `com.github.L_Ender.cataclysm.entity.Pet.Summoned_Entity`（5 类）
- `com.github.L_Ender.cataclysm.entity.effect`（13 类）
- `com.github.L_Ender.cataclysm.entity.etc`（15 类）
- `com.github.L_Ender.cataclysm.entity.etc.path`（5 类）
- `com.github.L_Ender.cataclysm.entity.partentity`（1 类）
- `com.github.L_Ender.cataclysm.entity.projectile`（45 类）
- `com.github.L_Ender.cataclysm.entity.util`（1 类）
- `com.github.L_Ender.cataclysm.event`（1 类）
- `com.github.L_Ender.cataclysm.init`（23 类）
- `com.github.L_Ender.cataclysm.inventory`（3 类）
- `com.github.L_Ender.cataclysm.items`（58 类）
- `com.github.L_Ender.cataclysm.items.Components`（1 类）
- `com.github.L_Ender.cataclysm.items.CuriosItem`（8 类）
- `com.github.L_Ender.cataclysm.jei`（5 类）
- `com.github.L_Ender.cataclysm.message`（15 类）
- `com.github.L_Ender.cataclysm.mixin`（12 类）
- `com.github.L_Ender.cataclysm.mixin.Client`（2 类）
- `com.github.L_Ender.cataclysm.mixin.accessor`（9 类）
- `com.github.L_Ender.cataclysm.structures`（5 类）
- `com.github.L_Ender.cataclysm.structures.jisaw`（6 类）
- `com.github.L_Ender.cataclysm.structures.jisaw.assembler`（2 类）
- `com.github.L_Ender.cataclysm.structures.jisaw.condition`（3 类）
- `com.github.L_Ender.cataclysm.structures.jisaw.context`（1 类）
- `com.github.L_Ender.cataclysm.structures.jisaw.element`（3 类）
- `com.github.L_Ender.cataclysm.util`（10 类）
- `com.github.L_Ender.cataclysm.util.CustomExplosion`（1 类）
- `com.github.L_Ender.cataclysm.world`（3 类）
- `com.github.L_Ender.cataclysm.world.data`（1 类）
- `com.github.L_Ender.cataclysm.world.structures.Processor`（1 类）
- `com.github.L_Ender.cataclysm.world.structures.action`（4 类）
- `com.github.L_Ender.cataclysm.world.structures.modifier`（1 类）
- `com.github.L_Ender.cataclysm.world.structures.placements`（1 类）
- `com.github.L_Ender.cataclysm.world.structures.targetselector`（3 类）
- `com.github.L_Ender.cataclysm.world.structures.terrainadaptation`（7 类）
