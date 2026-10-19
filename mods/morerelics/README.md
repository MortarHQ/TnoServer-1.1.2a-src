# More Relics（`morerelics`）

> jar：`[更多遗物]morerelics.jar` ｜ 版本：`1.7.2` ｜ 作者：Blorbilon
> 规模：**71 个类 / 10 个包 / 6830 行** ｜ 事件处理类 31 ｜ Mixin 5 ｜ 注册/管理类 5

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.blorb.morerelics.relics` | 28 | `MadeInHeaven`、`TyrantMask`、`WonderOfU` |
| `com.blorb.morerelics.effects` | 9 | `Dazed`、`Charred`、`Vulnerability` |
| `com.blorb.morerelics.dataComponents` | 9 | `DataComponentHandler`、`AttachmentHandler`、`VectorDequeHolder` |
| `com.blorb.morerelics.render` | 5 | `PlayerAfterImageRenderer`、`MonochromePlayerLayer`、`PlayerAfterImageState` |
| `com.blorb.morerelics.mixin` | 5 | `PlayerRendererMixin`、`AbstractClientPlayerMixin`、`HumanoidArmorLayerMixin` |
| `com.blorb.morerelics.helper` | 4 | `DelayedTaskManager`、`CyberpsychosisSubscriber`、`CyberwareHelper` |
| `com.blorb.morerelics.network` | 4 | `SoarVelocityPayloadHandler`、`SilkCounterPayloadHandler`、`SilkCounterPayload` |
| `com.blorb.morerelics` | 3 | `Config`、`MoreRelics`、`MoreRelicsUtil` |
| `com.blorb.morerelics.particles` | 3 | `VaryingAnimationParticle`、`VaryingSizedAnimationParticle`、`OrbitingParticle` |
| `com.blorb.morerelics.Loot` | 1 | `ModLootAdder` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `DelayedTaskManager` | class | — | EventBusSubscriber | 2 | 35 | [DelayedTaskManager.java](com/blorb/morerelics/helper/DelayedTaskManager.java) |
| `MadeInHeaven` | class | MoreRelicBase | EventBusSubscriber | 15 | 518 | [MadeInHeaven.java](com/blorb/morerelics/relics/MadeInHeaven.java) |
| `Config` | class | — | EventBusSubscriber | 1 | 87 | [Config.java](com/blorb/morerelics/Config.java) |
| `TyrantMask` | class | MoreRelicBase | EventBusSubscriber | 10 | 219 | [TyrantMask.java](com/blorb/morerelics/relics/TyrantMask.java) |
| `WonderOfU` | class | MoreRelicBase | EventBusSubscriber | 10 | 330 | [WonderOfU.java](com/blorb/morerelics/relics/WonderOfU.java) |
| `WeaversSpool` | class | MoreRelicBase | EventBusSubscriber | 14 | 360 | [WeaversSpool.java](com/blorb/morerelics/relics/WeaversSpool.java) |
| `ModLootAdder` | class | — | EventBusSubscriber | 1 | 56 | [ModLootAdder.java](com/blorb/morerelics/Loot/ModLootAdder.java) |
| `MoreRelics` | class | — | Mod | 9 | 268 | [MoreRelics.java](com/blorb/morerelics/MoreRelics.java) |
| `KingCrimson` | class | MoreRelicBase | EventBusSubscriber | 7 | 251 | [KingCrimson.java](com/blorb/morerelics/relics/KingCrimson.java) |
| `VertebraX` | class | CyberwareBase | EventBusSubscriber | 6 | 222 | [VertebraX.java](com/blorb/morerelics/relics/VertebraX.java) |
| `GravitumStrider` | class | MoreRelicBase | EventBusSubscriber | 8 | 230 | [GravitumStrider.java](com/blorb/morerelics/relics/GravitumStrider.java) |
| `PlayerAfterImageRenderer` | class | — | EventBusSubscriber、OnlyIn | 2 | 38 | [PlayerAfterImageRenderer.java](com/blorb/morerelics/render/PlayerAfterImageRenderer.java) |
| `ThermoseismicHeart` | class | CyberwareBase | EventBusSubscriber | 4 | 214 | [ThermoseismicHeart.java](com/blorb/morerelics/relics/ThermoseismicHeart.java) |
| `GravitumGlove` | class | MoreRelicBase | EventBusSubscriber | 6 | 174 | [GravitumGlove.java](com/blorb/morerelics/relics/GravitumGlove.java) |
| `MoodWorm` | class | MoreRelicBase | EventBusSubscriber | 7 | 193 | [MoodWorm.java](com/blorb/morerelics/relics/MoodWorm.java) |
| `ConvergingOrb` | class | MoreRelicBase | EventBusSubscriber | 8 | 139 | [ConvergingOrb.java](com/blorb/morerelics/relics/ConvergingOrb.java) |
| `CyberpsychosisSubscriber` | class | — | EventBusSubscriber | 1 | 28 | [CyberpsychosisSubscriber.java](com/blorb/morerelics/helper/CyberpsychosisSubscriber.java) |
| `WhimsOfFate` | class | MoreRelicBase | EventBusSubscriber | 3 | 150 | [WhimsOfFate.java](com/blorb/morerelics/relics/WhimsOfFate.java) |
| `OpalNecklace` | class | MoreRelicBase | EventBusSubscriber | 3 | 110 | [OpalNecklace.java](com/blorb/morerelics/relics/OpalNecklace.java) |
| `BioJoint` | class | CyberwareBase | EventBusSubscriber | 2 | 117 | [BioJoint.java](com/blorb/morerelics/relics/BioJoint.java) |
| `MassGauntlet` | class | MoreRelicBase | EventBusSubscriber | 2 | 76 | [MassGauntlet.java](com/blorb/morerelics/relics/MassGauntlet.java) |
| `ShieldWeaveCape` | class | MoreRelicBase | EventBusSubscriber | 4 | 144 | [ShieldWeaveCape.java](com/blorb/morerelics/relics/ShieldWeaveCape.java) |
| `CrownOfTheLegend` | class | MoreRelicBase | EventBusSubscriber | 4 | 117 | [CrownOfTheLegend.java](com/blorb/morerelics/relics/CrownOfTheLegend.java) |
| `SentientRust` | class | MoreRelicBase | EventBusSubscriber | 2 | 98 | [SentientRust.java](com/blorb/morerelics/relics/SentientRust.java) |
| `AxolotlCream` | class | MoreRelicBase | EventBusSubscriber | 3 | 92 | [AxolotlCream.java](com/blorb/morerelics/relics/AxolotlCream.java) |
| `Dazed` | class | MobEffect | EventBusSubscriber | 1 | 34 | [Dazed.java](com/blorb/morerelics/effects/Dazed.java) |
| `Charred` | class | MobEffect | EventBusSubscriber | 1 | 29 | [Charred.java](com/blorb/morerelics/effects/Charred.java) |
| `Vulnerability` | class | MobEffect | EventBusSubscriber | 1 | 29 | [Vulnerability.java](com/blorb/morerelics/effects/Vulnerability.java) |
| `DataComponentHandler` | class | — | — | 11 | 215 | [DataComponentHandler.java](com/blorb/morerelics/dataComponents/DataComponentHandler.java) |
| `PlayerRendererMixin` | class | — | Mixin | 1 | 31 | [PlayerRendererMixin.java](com/blorb/morerelics/mixin/PlayerRendererMixin.java) |
| `AbstractClientPlayerMixin` | class | — | Mixin | 1 | 33 | [AbstractClientPlayerMixin.java](com/blorb/morerelics/mixin/AbstractClientPlayerMixin.java) |
| `HumanoidArmorLayerMixin` | class | — | Mixin | 0 | 37 | [HumanoidArmorLayerMixin.java](com/blorb/morerelics/mixin/HumanoidArmorLayerMixin.java) |
| `DamageContainerMixin` | class | — | Mixin | 0 | 9 | [DamageContainerMixin.java](com/blorb/morerelics/mixin/DamageContainerMixin.java) |
| `WalkAnimationStateAccessor` | interface | — | Mixin | 0 | 21 | [WalkAnimationStateAccessor.java](com/blorb/morerelics/mixin/WalkAnimationStateAccessor.java) |
| `SoarVelocityPayloadHandler` | class | — | — | 2 | 17 | [SoarVelocityPayloadHandler.java](com/blorb/morerelics/network/SoarVelocityPayloadHandler.java) |
| `SilkCounterPayloadHandler` | class | — | — | 2 | 25 | [SilkCounterPayloadHandler.java](com/blorb/morerelics/network/SilkCounterPayloadHandler.java) |
| `Nullification` | class | MobEffect | — | 6 | 107 | [Nullification.java](com/blorb/morerelics/effects/Nullification.java) |
| `Cyberpsychosis` | class | MobEffect | — | 5 | 151 | [Cyberpsychosis.java](com/blorb/morerelics/effects/Cyberpsychosis.java) |
| `AttachmentHandler` | class | — | — | 0 | 15 | [AttachmentHandler.java](com/blorb/morerelics/dataComponents/AttachmentHandler.java) |
| `Static` | class | MobEffect | — | 2 | 55 | [Static.java](com/blorb/morerelics/effects/Static.java) |
| `EpochApple` | class | MoreRelicBase | — | 11 | 195 | [EpochApple.java](com/blorb/morerelics/relics/EpochApple.java) |
| `VectorDequeHolder` | class | — | — | 10 | 71 | [VectorDequeHolder.java](com/blorb/morerelics/dataComponents/VectorDequeHolder.java) |
| `MoreRelicsUtil` | class | — | — | 3 | 50 | [MoreRelicsUtil.java](com/blorb/morerelics/MoreRelicsUtil.java) |
| `CyberwareHelper` | class | — | — | 1 | 34 | [CyberwareHelper.java](com/blorb/morerelics/helper/CyberwareHelper.java) |
| `DepletedSpool` | class | MoreRelicBase | — | 5 | 104 | [DepletedSpool.java](com/blorb/morerelics/relics/DepletedSpool.java) |
| `WhisperingAmulet` | class | MoreRelicBase | — | 8 | 156 | [WhisperingAmulet.java](com/blorb/morerelics/relics/WhisperingAmulet.java) |
| `MoreRelicBase` | class | RelicItem | — | 5 | 63 | [MoreRelicBase.java](com/blorb/morerelics/relics/MoreRelicBase.java) |
| `LocationHolder` | class | — | — | 8 | 50 | [LocationHolder.java](com/blorb/morerelics/dataComponents/LocationHolder.java) |
| `MonochromePlayerLayer` | class | RenderLayer | OnlyIn | 5 | 131 | [MonochromePlayerLayer.java](com/blorb/morerelics/render/MonochromePlayerLayer.java) |
| `SilkCounterPayload` | record | CustomPacketPayload | — | 3 | 29 | [SilkCounterPayload.java](com/blorb/morerelics/network/SilkCounterPayload.java) |
| `SoarVelocityPayload` | record | CustomPacketPayload | — | 3 | 33 | [SoarVelocityPayload.java](com/blorb/morerelics/network/SoarVelocityPayload.java) |
| `DelayedTask` | class | — | — | 0 | 12 | [DelayedTask.java](com/blorb/morerelics/helper/DelayedTask.java) |
| `GutsOrb` | class | MoreRelicBase | — | 3 | 109 | [GutsOrb.java](com/blorb/morerelics/relics/GutsOrb.java) |
| `SlumberingAmulet` | class | MoreRelicBase | — | 4 | 116 | [SlumberingAmulet.java](com/blorb/morerelics/relics/SlumberingAmulet.java) |
| `CyberwareBase` | class | MoreRelicBase | — | 2 | 26 | [CyberwareBase.java](com/blorb/morerelics/relics/CyberwareBase.java) |
| `EjectButton` | class | MoreRelicBase | — | 3 | 68 | [EjectButton.java](com/blorb/morerelics/relics/EjectButton.java) |
| `BionicEye` | class | CyberwareBase | — | 3 | 146 | [BionicEye.java](com/blorb/morerelics/relics/BionicEye.java) |
| `Corrosion` | class | MobEffect | — | 0 | 11 | [Corrosion.java](com/blorb/morerelics/effects/Corrosion.java) |
| `Accelerating` | class | MobEffect | — | 0 | 11 | [Accelerating.java](com/blorb/morerelics/effects/Accelerating.java) |
| `Overload` | class | MobEffect | — | 0 | 11 | [Overload.java](com/blorb/morerelics/effects/Overload.java) |

## 事件处理（`@SubscribeEvent`，31 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `MoreRelics` | 4 | `com.blorb.morerelics` | [MoreRelics.java](com/blorb/morerelics/MoreRelics.java) |
| `MadeInHeaven` | 3 | `com.blorb.morerelics.relics` | [MadeInHeaven.java](com/blorb/morerelics/relics/MadeInHeaven.java) |
| `TyrantMask` | 2 | `com.blorb.morerelics.relics` | [TyrantMask.java](com/blorb/morerelics/relics/TyrantMask.java) |
| `WeaversSpool` | 2 | `com.blorb.morerelics.relics` | [WeaversSpool.java](com/blorb/morerelics/relics/WeaversSpool.java) |
| `KingCrimson` | 2 | `com.blorb.morerelics.relics` | [KingCrimson.java](com/blorb/morerelics/relics/KingCrimson.java) |
| `GravitumStrider` | 2 | `com.blorb.morerelics.relics` | [GravitumStrider.java](com/blorb/morerelics/relics/GravitumStrider.java) |
| `PlayerAfterImageRenderer` | 2 | `com.blorb.morerelics.render` | [PlayerAfterImageRenderer.java](com/blorb/morerelics/render/PlayerAfterImageRenderer.java) |
| `MoodWorm` | 2 | `com.blorb.morerelics.relics` | [MoodWorm.java](com/blorb/morerelics/relics/MoodWorm.java) |
| `CrownOfTheLegend` | 2 | `com.blorb.morerelics.relics` | [CrownOfTheLegend.java](com/blorb/morerelics/relics/CrownOfTheLegend.java) |
| `Nullification` | 2 | `com.blorb.morerelics.effects` | [Nullification.java](com/blorb/morerelics/effects/Nullification.java) |
| `Cyberpsychosis` | 2 | `com.blorb.morerelics.effects` | [Cyberpsychosis.java](com/blorb/morerelics/effects/Cyberpsychosis.java) |
| `Static` | 2 | `com.blorb.morerelics.effects` | [Static.java](com/blorb/morerelics/effects/Static.java) |
| `DelayedTaskManager` | 1 | `com.blorb.morerelics.helper` | [DelayedTaskManager.java](com/blorb/morerelics/helper/DelayedTaskManager.java) |
| `Config` | 1 | `com.blorb.morerelics` | [Config.java](com/blorb/morerelics/Config.java) |
| `WonderOfU` | 1 | `com.blorb.morerelics.relics` | [WonderOfU.java](com/blorb/morerelics/relics/WonderOfU.java) |
| `ModLootAdder` | 1 | `com.blorb.morerelics.Loot` | [ModLootAdder.java](com/blorb/morerelics/Loot/ModLootAdder.java) |
| `VertebraX` | 1 | `com.blorb.morerelics.relics` | [VertebraX.java](com/blorb/morerelics/relics/VertebraX.java) |
| `ThermoseismicHeart` | 1 | `com.blorb.morerelics.relics` | [ThermoseismicHeart.java](com/blorb/morerelics/relics/ThermoseismicHeart.java) |
| `GravitumGlove` | 1 | `com.blorb.morerelics.relics` | [GravitumGlove.java](com/blorb/morerelics/relics/GravitumGlove.java) |
| `ConvergingOrb` | 1 | `com.blorb.morerelics.relics` | [ConvergingOrb.java](com/blorb/morerelics/relics/ConvergingOrb.java) |
| `CyberpsychosisSubscriber` | 1 | `com.blorb.morerelics.helper` | [CyberpsychosisSubscriber.java](com/blorb/morerelics/helper/CyberpsychosisSubscriber.java) |
| `WhimsOfFate` | 1 | `com.blorb.morerelics.relics` | [WhimsOfFate.java](com/blorb/morerelics/relics/WhimsOfFate.java) |
| `OpalNecklace` | 1 | `com.blorb.morerelics.relics` | [OpalNecklace.java](com/blorb/morerelics/relics/OpalNecklace.java) |
| `BioJoint` | 1 | `com.blorb.morerelics.relics` | [BioJoint.java](com/blorb/morerelics/relics/BioJoint.java) |
| `MassGauntlet` | 1 | `com.blorb.morerelics.relics` | [MassGauntlet.java](com/blorb/morerelics/relics/MassGauntlet.java) |
| `ShieldWeaveCape` | 1 | `com.blorb.morerelics.relics` | [ShieldWeaveCape.java](com/blorb/morerelics/relics/ShieldWeaveCape.java) |
| `SentientRust` | 1 | `com.blorb.morerelics.relics` | [SentientRust.java](com/blorb/morerelics/relics/SentientRust.java) |
| `AxolotlCream` | 1 | `com.blorb.morerelics.relics` | [AxolotlCream.java](com/blorb/morerelics/relics/AxolotlCream.java) |
| `Dazed` | 1 | `com.blorb.morerelics.effects` | [Dazed.java](com/blorb/morerelics/effects/Dazed.java) |
| `Charred` | 1 | `com.blorb.morerelics.effects` | [Charred.java](com/blorb/morerelics/effects/Charred.java) |
| `Vulnerability` | 1 | `com.blorb.morerelics.effects` | [Vulnerability.java](com/blorb/morerelics/effects/Vulnerability.java) |

## Mixin（5 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `PlayerRendererMixin` | `PlayerRenderer` | [PlayerRendererMixin.java](com/blorb/morerelics/mixin/PlayerRendererMixin.java) |
| `AbstractClientPlayerMixin` | `AbstractClientPlayer` | [AbstractClientPlayerMixin.java](com/blorb/morerelics/mixin/AbstractClientPlayerMixin.java) |
| `HumanoidArmorLayerMixin` | `HumanoidArmorLayer` | [HumanoidArmorLayerMixin.java](com/blorb/morerelics/mixin/HumanoidArmorLayerMixin.java) |
| `DamageContainerMixin` | `DamageContainer` | [DamageContainerMixin.java](com/blorb/morerelics/mixin/DamageContainerMixin.java) |
| `WalkAnimationStateAccessor` | `WalkAnimationStateAccessor` | [WalkAnimationStateAccessor.java](com/blorb/morerelics/mixin/WalkAnimationStateAccessor.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `Config` | EventBusSubscriber | [Config.java](com/blorb/morerelics/Config.java) |

## 全部类（71）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `DelayedTaskManager` | class | — | EventBusSubscriber | 2 | 35 | [DelayedTaskManager.java](com/blorb/morerelics/helper/DelayedTaskManager.java) |
| `MadeInHeaven` | class | MoreRelicBase | EventBusSubscriber | 15 | 518 | [MadeInHeaven.java](com/blorb/morerelics/relics/MadeInHeaven.java) |
| `Config` | class | — | EventBusSubscriber | 1 | 87 | [Config.java](com/blorb/morerelics/Config.java) |
| `TyrantMask` | class | MoreRelicBase | EventBusSubscriber | 10 | 219 | [TyrantMask.java](com/blorb/morerelics/relics/TyrantMask.java) |
| `WonderOfU` | class | MoreRelicBase | EventBusSubscriber | 10 | 330 | [WonderOfU.java](com/blorb/morerelics/relics/WonderOfU.java) |
| `WeaversSpool` | class | MoreRelicBase | EventBusSubscriber | 14 | 360 | [WeaversSpool.java](com/blorb/morerelics/relics/WeaversSpool.java) |
| `ModLootAdder` | class | — | EventBusSubscriber | 1 | 56 | [ModLootAdder.java](com/blorb/morerelics/Loot/ModLootAdder.java) |
| `MoreRelics` | class | — | Mod | 9 | 268 | [MoreRelics.java](com/blorb/morerelics/MoreRelics.java) |
| `KingCrimson` | class | MoreRelicBase | EventBusSubscriber | 7 | 251 | [KingCrimson.java](com/blorb/morerelics/relics/KingCrimson.java) |
| `VertebraX` | class | CyberwareBase | EventBusSubscriber | 6 | 222 | [VertebraX.java](com/blorb/morerelics/relics/VertebraX.java) |
| `GravitumStrider` | class | MoreRelicBase | EventBusSubscriber | 8 | 230 | [GravitumStrider.java](com/blorb/morerelics/relics/GravitumStrider.java) |
| `PlayerAfterImageRenderer` | class | — | EventBusSubscriber、OnlyIn | 2 | 38 | [PlayerAfterImageRenderer.java](com/blorb/morerelics/render/PlayerAfterImageRenderer.java) |
| `ThermoseismicHeart` | class | CyberwareBase | EventBusSubscriber | 4 | 214 | [ThermoseismicHeart.java](com/blorb/morerelics/relics/ThermoseismicHeart.java) |
| `GravitumGlove` | class | MoreRelicBase | EventBusSubscriber | 6 | 174 | [GravitumGlove.java](com/blorb/morerelics/relics/GravitumGlove.java) |
| `MoodWorm` | class | MoreRelicBase | EventBusSubscriber | 7 | 193 | [MoodWorm.java](com/blorb/morerelics/relics/MoodWorm.java) |
| `ConvergingOrb` | class | MoreRelicBase | EventBusSubscriber | 8 | 139 | [ConvergingOrb.java](com/blorb/morerelics/relics/ConvergingOrb.java) |
| `CyberpsychosisSubscriber` | class | — | EventBusSubscriber | 1 | 28 | [CyberpsychosisSubscriber.java](com/blorb/morerelics/helper/CyberpsychosisSubscriber.java) |
| `WhimsOfFate` | class | MoreRelicBase | EventBusSubscriber | 3 | 150 | [WhimsOfFate.java](com/blorb/morerelics/relics/WhimsOfFate.java) |
| `OpalNecklace` | class | MoreRelicBase | EventBusSubscriber | 3 | 110 | [OpalNecklace.java](com/blorb/morerelics/relics/OpalNecklace.java) |
| `BioJoint` | class | CyberwareBase | EventBusSubscriber | 2 | 117 | [BioJoint.java](com/blorb/morerelics/relics/BioJoint.java) |
| `MassGauntlet` | class | MoreRelicBase | EventBusSubscriber | 2 | 76 | [MassGauntlet.java](com/blorb/morerelics/relics/MassGauntlet.java) |
| `ShieldWeaveCape` | class | MoreRelicBase | EventBusSubscriber | 4 | 144 | [ShieldWeaveCape.java](com/blorb/morerelics/relics/ShieldWeaveCape.java) |
| `CrownOfTheLegend` | class | MoreRelicBase | EventBusSubscriber | 4 | 117 | [CrownOfTheLegend.java](com/blorb/morerelics/relics/CrownOfTheLegend.java) |
| `SentientRust` | class | MoreRelicBase | EventBusSubscriber | 2 | 98 | [SentientRust.java](com/blorb/morerelics/relics/SentientRust.java) |
| `AxolotlCream` | class | MoreRelicBase | EventBusSubscriber | 3 | 92 | [AxolotlCream.java](com/blorb/morerelics/relics/AxolotlCream.java) |
| `Dazed` | class | MobEffect | EventBusSubscriber | 1 | 34 | [Dazed.java](com/blorb/morerelics/effects/Dazed.java) |
| `Charred` | class | MobEffect | EventBusSubscriber | 1 | 29 | [Charred.java](com/blorb/morerelics/effects/Charred.java) |
| `Vulnerability` | class | MobEffect | EventBusSubscriber | 1 | 29 | [Vulnerability.java](com/blorb/morerelics/effects/Vulnerability.java) |
| `DataComponentHandler` | class | — | — | 11 | 215 | [DataComponentHandler.java](com/blorb/morerelics/dataComponents/DataComponentHandler.java) |
| `PlayerRendererMixin` | class | — | Mixin | 1 | 31 | [PlayerRendererMixin.java](com/blorb/morerelics/mixin/PlayerRendererMixin.java) |
| `AbstractClientPlayerMixin` | class | — | Mixin | 1 | 33 | [AbstractClientPlayerMixin.java](com/blorb/morerelics/mixin/AbstractClientPlayerMixin.java) |
| `HumanoidArmorLayerMixin` | class | — | Mixin | 0 | 37 | [HumanoidArmorLayerMixin.java](com/blorb/morerelics/mixin/HumanoidArmorLayerMixin.java) |
| `DamageContainerMixin` | class | — | Mixin | 0 | 9 | [DamageContainerMixin.java](com/blorb/morerelics/mixin/DamageContainerMixin.java) |
| `WalkAnimationStateAccessor` | interface | — | Mixin | 0 | 21 | [WalkAnimationStateAccessor.java](com/blorb/morerelics/mixin/WalkAnimationStateAccessor.java) |
| `SoarVelocityPayloadHandler` | class | — | — | 2 | 17 | [SoarVelocityPayloadHandler.java](com/blorb/morerelics/network/SoarVelocityPayloadHandler.java) |
| `SilkCounterPayloadHandler` | class | — | — | 2 | 25 | [SilkCounterPayloadHandler.java](com/blorb/morerelics/network/SilkCounterPayloadHandler.java) |
| `Nullification` | class | MobEffect | — | 6 | 107 | [Nullification.java](com/blorb/morerelics/effects/Nullification.java) |
| `Cyberpsychosis` | class | MobEffect | — | 5 | 151 | [Cyberpsychosis.java](com/blorb/morerelics/effects/Cyberpsychosis.java) |
| `AttachmentHandler` | class | — | — | 0 | 15 | [AttachmentHandler.java](com/blorb/morerelics/dataComponents/AttachmentHandler.java) |
| `Static` | class | MobEffect | — | 2 | 55 | [Static.java](com/blorb/morerelics/effects/Static.java) |
| `EpochApple` | class | MoreRelicBase | — | 11 | 195 | [EpochApple.java](com/blorb/morerelics/relics/EpochApple.java) |
| `VectorDequeHolder` | class | — | — | 10 | 71 | [VectorDequeHolder.java](com/blorb/morerelics/dataComponents/VectorDequeHolder.java) |
| `MoreRelicsUtil` | class | — | — | 3 | 50 | [MoreRelicsUtil.java](com/blorb/morerelics/MoreRelicsUtil.java) |
| `CyberwareHelper` | class | — | — | 1 | 34 | [CyberwareHelper.java](com/blorb/morerelics/helper/CyberwareHelper.java) |
| `DepletedSpool` | class | MoreRelicBase | — | 5 | 104 | [DepletedSpool.java](com/blorb/morerelics/relics/DepletedSpool.java) |
| `WhisperingAmulet` | class | MoreRelicBase | — | 8 | 156 | [WhisperingAmulet.java](com/blorb/morerelics/relics/WhisperingAmulet.java) |
| `MoreRelicBase` | class | RelicItem | — | 5 | 63 | [MoreRelicBase.java](com/blorb/morerelics/relics/MoreRelicBase.java) |
| `LocationHolder` | class | — | — | 8 | 50 | [LocationHolder.java](com/blorb/morerelics/dataComponents/LocationHolder.java) |
| `MonochromePlayerLayer` | class | RenderLayer | OnlyIn | 5 | 131 | [MonochromePlayerLayer.java](com/blorb/morerelics/render/MonochromePlayerLayer.java) |
| `SilkCounterPayload` | record | CustomPacketPayload | — | 3 | 29 | [SilkCounterPayload.java](com/blorb/morerelics/network/SilkCounterPayload.java) |
| `SoarVelocityPayload` | record | CustomPacketPayload | — | 3 | 33 | [SoarVelocityPayload.java](com/blorb/morerelics/network/SoarVelocityPayload.java) |
| `DelayedTask` | class | — | — | 0 | 12 | [DelayedTask.java](com/blorb/morerelics/helper/DelayedTask.java) |
| `GutsOrb` | class | MoreRelicBase | — | 3 | 109 | [GutsOrb.java](com/blorb/morerelics/relics/GutsOrb.java) |
| `SlumberingAmulet` | class | MoreRelicBase | — | 4 | 116 | [SlumberingAmulet.java](com/blorb/morerelics/relics/SlumberingAmulet.java) |
| `CyberwareBase` | class | MoreRelicBase | — | 2 | 26 | [CyberwareBase.java](com/blorb/morerelics/relics/CyberwareBase.java) |
| `EjectButton` | class | MoreRelicBase | — | 3 | 68 | [EjectButton.java](com/blorb/morerelics/relics/EjectButton.java) |
| `BionicEye` | class | CyberwareBase | — | 3 | 146 | [BionicEye.java](com/blorb/morerelics/relics/BionicEye.java) |
| `Corrosion` | class | MobEffect | — | 0 | 11 | [Corrosion.java](com/blorb/morerelics/effects/Corrosion.java) |
| `Accelerating` | class | MobEffect | — | 0 | 11 | [Accelerating.java](com/blorb/morerelics/effects/Accelerating.java) |
| `Overload` | class | MobEffect | — | 0 | 11 | [Overload.java](com/blorb/morerelics/effects/Overload.java) |
| `BoolHolder` | class | — | — | 4 | 30 | [BoolHolder.java](com/blorb/morerelics/dataComponents/BoolHolder.java) |
| `StarCounter` | record | — | — | 1 | 8 | [StarCounter.java](com/blorb/morerelics/dataComponents/StarCounter.java) |
| `DoubleCounter` | class | — | — | 4 | 30 | [DoubleCounter.java](com/blorb/morerelics/dataComponents/DoubleCounter.java) |
| `IntDequeHolder` | class | — | — | 0 | 5 | [IntDequeHolder.java](com/blorb/morerelics/dataComponents/IntDequeHolder.java) |
| `IntegerCounter` | record | — | — | 1 | 8 | [IntegerCounter.java](com/blorb/morerelics/dataComponents/IntegerCounter.java) |
| `PlayerAfterImageState` | record | — | — | 0 | 7 | [PlayerAfterImageState.java](com/blorb/morerelics/render/PlayerAfterImageState.java) |
| `AfterImageRenderContext` | class | — | — | 0 | 7 | [AfterImageRenderContext.java](com/blorb/morerelics/render/AfterImageRenderContext.java) |
| `AfterImageTracker` | class | — | — | 3 | 47 | [AfterImageTracker.java](com/blorb/morerelics/render/AfterImageTracker.java) |
| `VaryingAnimationParticle` | class | TextureSheetParticle | — | 3 | 61 | [VaryingAnimationParticle.java](com/blorb/morerelics/particles/VaryingAnimationParticle.java) |
| `VaryingSizedAnimationParticle` | class | TextureSheetParticle | — | 3 | 58 | [VaryingSizedAnimationParticle.java](com/blorb/morerelics/particles/VaryingSizedAnimationParticle.java) |
| `OrbitingParticle` | class | TextureSheetParticle | — | 3 | 69 | [OrbitingParticle.java](com/blorb/morerelics/particles/OrbitingParticle.java) |
