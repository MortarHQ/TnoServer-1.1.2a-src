# SmartBrainLib（`smartbrainlib`）

> jar：`[前置]SmartBrainLib.jar` ｜ 版本：`1.16.11` ｜ 作者：Tslat
> 规模：**119 个类 / 21 个包 / 9380 行** ｜ 事件处理类 1 ｜ Mixin 0 ｜ 注册/管理类 0

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.tslat.smartbrainlib.api.core.sensor.vanilla` | 18 | `NearestItemSensor`、`NearbyLivingEntitySensor`、`ItemTemptingSensor` |
| `net.tslat.smartbrainlib.api.core.behaviour.custom.move` | 13 | `FollowEntity`、`AvoidEntity`、`InteractWithDoor` |
| `net.tslat.smartbrainlib.api.core.behaviour.custom.misc` | 12 | `HoldItem`、`BreakBlock`、`BlockWithShield` |
| `net.tslat.smartbrainlib.object` | 10 | `SBLShufflingList`、`ExtendedTargetingConditions`、`MemoryTest` |
| `net.tslat.smartbrainlib.api.core.behaviour` | 9 | `ExtendedBehaviour`、`RepeatingBehaviour`、`GroupBehaviour` |
| `net.tslat.smartbrainlib.api.core.navigation` | 8 | `SmoothGroundNavigation`、`ExtendedNavigator`、`SmoothAmphibiousPathNavigation` |
| `net.tslat.smartbrainlib.api.core.behaviour.custom.path` | 7 | `SetWalkTargetToBlock`、`SeekRandomNearbyPosition`、`SetRandomWalkTarget` |
| `net.tslat.smartbrainlib.api.core.behaviour.custom.target` | 7 | `TargetOrRetaliate`、`InvalidateAttackTarget`、`SetAttackTarget` |
| `net.tslat.smartbrainlib.api.core.behaviour.custom.attack` | 6 | `ConditionlessAttack`、`AnimatableMeleeAttack`、`AnimatableRangedAttack` |
| `net.tslat.smartbrainlib` | 5 | `SmartBrainLib`、`SBLNeoForge`、`APIOnly` |
| `net.tslat.smartbrainlib.api.core.sensor.custom` | 5 | `NearbyItemsSensor`、`NearbyBlocksSensor`、`GenericAttackTargetSensor` |
| `net.tslat.smartbrainlib.util` | 4 | `EntityRetrievalUtil`、`RandomUtil`、`BrainUtils` |
| `net.tslat.smartbrainlib.api.core` | 3 | `SmartBrain`、`BrainActivityGroup`、`SmartBrainProvider` |
| `net.tslat.smartbrainlib.api.core.sensor` | 3 | `EntityFilteringSensor`、`ExtendedSensor`、`PredicateSensor` |
| `net.tslat.smartbrainlib.api.core.behaviour.custom.look` | 2 | `LookAtTarget`、`LookAtAttackTarget` |
| `net.tslat.smartbrainlib.registry` | 2 | `SBLMemoryTypes`、`SBLSensors` |
| `net.tslat.smartbrainlib.example.boilerplate` | 1 | `SBLClient` |
| `net.tslat.smartbrainlib.api.core.schedule` | 1 | `SmartBrainSchedule` |
| `net.tslat.smartbrainlib.example` | 1 | `SBLSkeleton` |
| `net.tslat.smartbrainlib.api` | 1 | `SmartBrainOwner` |
| `net.tslat.smartbrainlib.api.core.navigation.nodeevaluator` | 1 | `MultiFluidWalkNodeEvaluator` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SBLClient` | class | — | EventBusSubscriber | 1 | 22 | [SBLClient.java](net/tslat/smartbrainlib/example/boilerplate/SBLClient.java) |
| `EntityRetrievalUtil` | class | — | — | 110 | 691 | [EntityRetrievalUtil.java](net/tslat/smartbrainlib/util/EntityRetrievalUtil.java) |
| `SmartBrainLib` | class | — | Mod | 0 | 12 | [SmartBrainLib.java](net/tslat/smartbrainlib/SmartBrainLib.java) |
| `RandomUtil` | class | RandomSource | — | 44 | 270 | [RandomUtil.java](net/tslat/smartbrainlib/util/RandomUtil.java) |
| `BrainUtils` | class | — | — | 37 | 355 | [BrainUtils.java](net/tslat/smartbrainlib/util/BrainUtils.java) |
| `SmartBrain` | class | — | — | 29 | 430 | [SmartBrain.java](net/tslat/smartbrainlib/api/core/SmartBrain.java) |
| `FollowEntity` | class | ExtendedBehaviour | — | 18 | 182 | [FollowEntity.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/FollowEntity.java) |
| `ExtendedBehaviour` | class | Behavior | — | 23 | 150 | [ExtendedBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/ExtendedBehaviour.java) |
| `BrainActivityGroup` | class | — | — | 15 | 112 | [BrainActivityGroup.java](net/tslat/smartbrainlib/api/core/BrainActivityGroup.java) |
| `EntityFilteringSensor` | class | PredicateSensor | — | 6 | 36 | [EntityFilteringSensor.java](net/tslat/smartbrainlib/api/core/sensor/EntityFilteringSensor.java) |
| `NearbyItemsSensor` | class | PredicateSensor | — | 5 | 55 | [NearbyItemsSensor.java](net/tslat/smartbrainlib/api/core/sensor/custom/NearbyItemsSensor.java) |
| `NearbyBlocksSensor` | class | PredicateSensor | — | 5 | 64 | [NearbyBlocksSensor.java](net/tslat/smartbrainlib/api/core/sensor/custom/NearbyBlocksSensor.java) |
| `NearestItemSensor` | class | PredicateSensor | — | 5 | 55 | [NearestItemSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/NearestItemSensor.java) |
| `NearbyLivingEntitySensor` | class | PredicateSensor | — | 5 | 66 | [NearbyLivingEntitySensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/NearbyLivingEntitySensor.java) |
| `ItemTemptingSensor` | class | PredicateSensor | — | 7 | 82 | [ItemTemptingSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/ItemTemptingSensor.java) |
| `AvoidEntity` | class | ExtendedBehaviour | — | 9 | 92 | [AvoidEntity.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/AvoidEntity.java) |
| `SetWalkTargetToBlock` | class | ExtendedBehaviour | — | 7 | 71 | [SetWalkTargetToBlock.java](net/tslat/smartbrainlib/api/core/behaviour/custom/path/SetWalkTargetToBlock.java) |
| `HoldItem` | class | ExtendedBehaviour | — | 6 | 54 | [HoldItem.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/HoldItem.java) |
| `BreakBlock` | class | ExtendedBehaviour | — | 9 | 103 | [BreakBlock.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/BreakBlock.java) |
| `BlockWithShield` | class | ExtendedBehaviour | — | 6 | 63 | [BlockWithShield.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/BlockWithShield.java) |
| `RepeatingBehaviour` | class | ExtendedBehaviour | — | 11 | 92 | [RepeatingBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/RepeatingBehaviour.java) |
| `GroupBehaviour` | class | ExtendedBehaviour | — | 11 | 102 | [GroupBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/GroupBehaviour.java) |
| `InteractWithDoor` | class | ExtendedBehaviour | — | 10 | 149 | [InteractWithDoor.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/InteractWithDoor.java) |
| `FollowTemptation` | class | ExtendedBehaviour | — | 10 | 99 | [FollowTemptation.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/FollowTemptation.java) |
| `SeekRandomNearbyPosition` | class | ExtendedBehaviour | — | 11 | 98 | [SeekRandomNearbyPosition.java](net/tslat/smartbrainlib/api/core/behaviour/custom/path/SeekRandomNearbyPosition.java) |
| `SetRandomWalkTarget` | class | ExtendedBehaviour | — | 10 | 85 | [SetRandomWalkTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/path/SetRandomWalkTarget.java) |
| `Panic` | class | ExtendedBehaviour | — | 13 | 138 | [Panic.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/Panic.java) |
| `TargetOrRetaliate` | class | ExtendedBehaviour | — | 11 | 134 | [TargetOrRetaliate.java](net/tslat/smartbrainlib/api/core/behaviour/custom/target/TargetOrRetaliate.java) |
| `SBLShufflingList` | class | Iterable<T> | — | 11 | 123 | [SBLShufflingList.java](net/tslat/smartbrainlib/object/SBLShufflingList.java) |
| `ExtendedTargetingConditions` | class | — | — | 12 | 95 | [ExtendedTargetingConditions.java](net/tslat/smartbrainlib/object/ExtendedTargetingConditions.java) |
| `SBLNeoForge` | class | SBLLoader | — | 7 | 81 | [SBLNeoForge.java](net/tslat/smartbrainlib/SBLNeoForge.java) |
| `SmartBrainProvider` | class | — | — | 6 | 117 | [SmartBrainProvider.java](net/tslat/smartbrainlib/api/core/SmartBrainProvider.java) |
| `SmoothGroundNavigation` | class | GroundPathNavigation | — | 6 | 63 | [SmoothGroundNavigation.java](net/tslat/smartbrainlib/api/core/navigation/SmoothGroundNavigation.java) |
| `ExtendedNavigator` | interface | — | — | 2 | 252 | [ExtendedNavigator.java](net/tslat/smartbrainlib/api/core/navigation/ExtendedNavigator.java) |
| `SmartBrainSchedule` | class | Schedule | — | 9 | 147 | [SmartBrainSchedule.java](net/tslat/smartbrainlib/api/core/schedule/SmartBrainSchedule.java) |
| `ExtendedSensor` | class | Sensor | — | 7 | 48 | [ExtendedSensor.java](net/tslat/smartbrainlib/api/core/sensor/ExtendedSensor.java) |
| `SecondaryPoiSensor` | class | ExtendedSensor | — | 5 | 74 | [SecondaryPoiSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/SecondaryPoiSensor.java) |
| `NearbyHostileSensor` | class | EntityFilteringSensor | — | 6 | 74 | [NearbyHostileSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/NearbyHostileSensor.java) |
| `NearbyPlayersSensor` | class | PredicateSensor | — | 5 | 72 | [NearbyPlayersSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/NearbyPlayersSensor.java) |
| `AxolotlSpecificSensor` | class | EntityFilteringSensor | — | 5 | 54 | [AxolotlSpecificSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/AxolotlSpecificSensor.java) |
| `SequentialBehaviour` | class | GroupBehaviour | — | 6 | 88 | [SequentialBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/SequentialBehaviour.java) |
| `HeldBehaviour` | class | ExtendedBehaviour | — | 5 | 45 | [HeldBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/HeldBehaviour.java) |
| `AllApplicableBehaviours` | class | GroupBehaviour | — | 7 | 120 | [AllApplicableBehaviours.java](net/tslat/smartbrainlib/api/core/behaviour/AllApplicableBehaviours.java) |
| `DelayedBehaviour` | class | ExtendedBehaviour | — | 6 | 56 | [DelayedBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/DelayedBehaviour.java) |
| `ConditionlessAttack` | class | DelayedBehaviour | — | 7 | 76 | [ConditionlessAttack.java](net/tslat/smartbrainlib/api/core/behaviour/custom/attack/ConditionlessAttack.java) |
| `AnimatableMeleeAttack` | class | DelayedBehaviour | — | 6 | 63 | [AnimatableMeleeAttack.java](net/tslat/smartbrainlib/api/core/behaviour/custom/attack/AnimatableMeleeAttack.java) |
| `AnimatableRangedAttack` | class | DelayedBehaviour | — | 7 | 75 | [AnimatableRangedAttack.java](net/tslat/smartbrainlib/api/core/behaviour/custom/attack/AnimatableRangedAttack.java) |
| `LeapAtTarget` | class | AnimatableMeleeAttack | — | 7 | 70 | [LeapAtTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/attack/LeapAtTarget.java) |
| `ConditionlessHeldAttack` | class | HeldBehaviour | — | 5 | 56 | [ConditionlessHeldAttack.java](net/tslat/smartbrainlib/api/core/behaviour/custom/attack/ConditionlessHeldAttack.java) |
| `StrafeTarget` | class | ExtendedBehaviour | — | 6 | 90 | [StrafeTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/StrafeTarget.java) |
| `FleeTarget` | class | ExtendedBehaviour | — | 7 | 71 | [FleeTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/FleeTarget.java) |
| `MoveToWalkTarget` | class | ExtendedBehaviour | — | 9 | 140 | [MoveToWalkTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/MoveToWalkTarget.java) |
| `EscapeSun` | class | ExtendedBehaviour | — | 7 | 87 | [EscapeSun.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/EscapeSun.java) |
| `StayWithinDistanceOfAttackTarget` | class | ExtendedBehaviour | — | 9 | 102 | [StayWithinDistanceOfAttackTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/StayWithinDistanceOfAttackTarget.java) |
| `FloatToSurfaceOfFluid` | class | ExtendedBehaviour | — | 7 | 51 | [FloatToSurfaceOfFluid.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/FloatToSurfaceOfFluid.java) |
| `SetWalkTargetToAttackTarget` | class | ExtendedBehaviour | — | 7 | 78 | [SetWalkTargetToAttackTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/path/SetWalkTargetToAttackTarget.java) |
| `SetRandomSwimTarget` | class | ExtendedBehaviour | — | 8 | 71 | [SetRandomSwimTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/path/SetRandomSwimTarget.java) |
| `SetRandomHoverTarget` | class | ExtendedBehaviour | — | 8 | 80 | [SetRandomHoverTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/path/SetRandomHoverTarget.java) |
| `BreedWithPartner` | class | ExtendedBehaviour | — | 9 | 105 | [BreedWithPartner.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/BreedWithPartner.java) |
| `ReactToUnreachableTarget` | class | ExtendedBehaviour | — | 8 | 64 | [ReactToUnreachableTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/ReactToUnreachableTarget.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `SBLClient` | 1 | `net.tslat.smartbrainlib.example.boilerplate` | [SBLClient.java](net/tslat/smartbrainlib/example/boilerplate/SBLClient.java) |

## 全部类（119）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SBLClient` | class | — | EventBusSubscriber | 1 | 22 | [SBLClient.java](net/tslat/smartbrainlib/example/boilerplate/SBLClient.java) |
| `EntityRetrievalUtil` | class | — | — | 110 | 691 | [EntityRetrievalUtil.java](net/tslat/smartbrainlib/util/EntityRetrievalUtil.java) |
| `SmartBrainLib` | class | — | Mod | 0 | 12 | [SmartBrainLib.java](net/tslat/smartbrainlib/SmartBrainLib.java) |
| `RandomUtil` | class | RandomSource | — | 44 | 270 | [RandomUtil.java](net/tslat/smartbrainlib/util/RandomUtil.java) |
| `BrainUtils` | class | — | — | 37 | 355 | [BrainUtils.java](net/tslat/smartbrainlib/util/BrainUtils.java) |
| `SmartBrain` | class | — | — | 29 | 430 | [SmartBrain.java](net/tslat/smartbrainlib/api/core/SmartBrain.java) |
| `FollowEntity` | class | ExtendedBehaviour | — | 18 | 182 | [FollowEntity.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/FollowEntity.java) |
| `ExtendedBehaviour` | class | Behavior | — | 23 | 150 | [ExtendedBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/ExtendedBehaviour.java) |
| `BrainActivityGroup` | class | — | — | 15 | 112 | [BrainActivityGroup.java](net/tslat/smartbrainlib/api/core/BrainActivityGroup.java) |
| `EntityFilteringSensor` | class | PredicateSensor | — | 6 | 36 | [EntityFilteringSensor.java](net/tslat/smartbrainlib/api/core/sensor/EntityFilteringSensor.java) |
| `NearbyItemsSensor` | class | PredicateSensor | — | 5 | 55 | [NearbyItemsSensor.java](net/tslat/smartbrainlib/api/core/sensor/custom/NearbyItemsSensor.java) |
| `NearbyBlocksSensor` | class | PredicateSensor | — | 5 | 64 | [NearbyBlocksSensor.java](net/tslat/smartbrainlib/api/core/sensor/custom/NearbyBlocksSensor.java) |
| `NearestItemSensor` | class | PredicateSensor | — | 5 | 55 | [NearestItemSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/NearestItemSensor.java) |
| `NearbyLivingEntitySensor` | class | PredicateSensor | — | 5 | 66 | [NearbyLivingEntitySensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/NearbyLivingEntitySensor.java) |
| `ItemTemptingSensor` | class | PredicateSensor | — | 7 | 82 | [ItemTemptingSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/ItemTemptingSensor.java) |
| `AvoidEntity` | class | ExtendedBehaviour | — | 9 | 92 | [AvoidEntity.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/AvoidEntity.java) |
| `SetWalkTargetToBlock` | class | ExtendedBehaviour | — | 7 | 71 | [SetWalkTargetToBlock.java](net/tslat/smartbrainlib/api/core/behaviour/custom/path/SetWalkTargetToBlock.java) |
| `HoldItem` | class | ExtendedBehaviour | — | 6 | 54 | [HoldItem.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/HoldItem.java) |
| `BreakBlock` | class | ExtendedBehaviour | — | 9 | 103 | [BreakBlock.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/BreakBlock.java) |
| `BlockWithShield` | class | ExtendedBehaviour | — | 6 | 63 | [BlockWithShield.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/BlockWithShield.java) |
| `RepeatingBehaviour` | class | ExtendedBehaviour | — | 11 | 92 | [RepeatingBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/RepeatingBehaviour.java) |
| `GroupBehaviour` | class | ExtendedBehaviour | — | 11 | 102 | [GroupBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/GroupBehaviour.java) |
| `InteractWithDoor` | class | ExtendedBehaviour | — | 10 | 149 | [InteractWithDoor.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/InteractWithDoor.java) |
| `FollowTemptation` | class | ExtendedBehaviour | — | 10 | 99 | [FollowTemptation.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/FollowTemptation.java) |
| `SeekRandomNearbyPosition` | class | ExtendedBehaviour | — | 11 | 98 | [SeekRandomNearbyPosition.java](net/tslat/smartbrainlib/api/core/behaviour/custom/path/SeekRandomNearbyPosition.java) |
| `SetRandomWalkTarget` | class | ExtendedBehaviour | — | 10 | 85 | [SetRandomWalkTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/path/SetRandomWalkTarget.java) |
| `Panic` | class | ExtendedBehaviour | — | 13 | 138 | [Panic.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/Panic.java) |
| `TargetOrRetaliate` | class | ExtendedBehaviour | — | 11 | 134 | [TargetOrRetaliate.java](net/tslat/smartbrainlib/api/core/behaviour/custom/target/TargetOrRetaliate.java) |
| `SBLShufflingList` | class | Iterable<T> | — | 11 | 123 | [SBLShufflingList.java](net/tslat/smartbrainlib/object/SBLShufflingList.java) |
| `ExtendedTargetingConditions` | class | — | — | 12 | 95 | [ExtendedTargetingConditions.java](net/tslat/smartbrainlib/object/ExtendedTargetingConditions.java) |
| `SBLNeoForge` | class | SBLLoader | — | 7 | 81 | [SBLNeoForge.java](net/tslat/smartbrainlib/SBLNeoForge.java) |
| `SmartBrainProvider` | class | — | — | 6 | 117 | [SmartBrainProvider.java](net/tslat/smartbrainlib/api/core/SmartBrainProvider.java) |
| `SmoothGroundNavigation` | class | GroundPathNavigation | — | 6 | 63 | [SmoothGroundNavigation.java](net/tslat/smartbrainlib/api/core/navigation/SmoothGroundNavigation.java) |
| `ExtendedNavigator` | interface | — | — | 2 | 252 | [ExtendedNavigator.java](net/tslat/smartbrainlib/api/core/navigation/ExtendedNavigator.java) |
| `SmartBrainSchedule` | class | Schedule | — | 9 | 147 | [SmartBrainSchedule.java](net/tslat/smartbrainlib/api/core/schedule/SmartBrainSchedule.java) |
| `ExtendedSensor` | class | Sensor | — | 7 | 48 | [ExtendedSensor.java](net/tslat/smartbrainlib/api/core/sensor/ExtendedSensor.java) |
| `SecondaryPoiSensor` | class | ExtendedSensor | — | 5 | 74 | [SecondaryPoiSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/SecondaryPoiSensor.java) |
| `NearbyHostileSensor` | class | EntityFilteringSensor | — | 6 | 74 | [NearbyHostileSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/NearbyHostileSensor.java) |
| `NearbyPlayersSensor` | class | PredicateSensor | — | 5 | 72 | [NearbyPlayersSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/NearbyPlayersSensor.java) |
| `AxolotlSpecificSensor` | class | EntityFilteringSensor | — | 5 | 54 | [AxolotlSpecificSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/AxolotlSpecificSensor.java) |
| `SequentialBehaviour` | class | GroupBehaviour | — | 6 | 88 | [SequentialBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/SequentialBehaviour.java) |
| `HeldBehaviour` | class | ExtendedBehaviour | — | 5 | 45 | [HeldBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/HeldBehaviour.java) |
| `AllApplicableBehaviours` | class | GroupBehaviour | — | 7 | 120 | [AllApplicableBehaviours.java](net/tslat/smartbrainlib/api/core/behaviour/AllApplicableBehaviours.java) |
| `DelayedBehaviour` | class | ExtendedBehaviour | — | 6 | 56 | [DelayedBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/DelayedBehaviour.java) |
| `ConditionlessAttack` | class | DelayedBehaviour | — | 7 | 76 | [ConditionlessAttack.java](net/tslat/smartbrainlib/api/core/behaviour/custom/attack/ConditionlessAttack.java) |
| `AnimatableMeleeAttack` | class | DelayedBehaviour | — | 6 | 63 | [AnimatableMeleeAttack.java](net/tslat/smartbrainlib/api/core/behaviour/custom/attack/AnimatableMeleeAttack.java) |
| `AnimatableRangedAttack` | class | DelayedBehaviour | — | 7 | 75 | [AnimatableRangedAttack.java](net/tslat/smartbrainlib/api/core/behaviour/custom/attack/AnimatableRangedAttack.java) |
| `LeapAtTarget` | class | AnimatableMeleeAttack | — | 7 | 70 | [LeapAtTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/attack/LeapAtTarget.java) |
| `ConditionlessHeldAttack` | class | HeldBehaviour | — | 5 | 56 | [ConditionlessHeldAttack.java](net/tslat/smartbrainlib/api/core/behaviour/custom/attack/ConditionlessHeldAttack.java) |
| `StrafeTarget` | class | ExtendedBehaviour | — | 6 | 90 | [StrafeTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/StrafeTarget.java) |
| `FleeTarget` | class | ExtendedBehaviour | — | 7 | 71 | [FleeTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/FleeTarget.java) |
| `MoveToWalkTarget` | class | ExtendedBehaviour | — | 9 | 140 | [MoveToWalkTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/MoveToWalkTarget.java) |
| `EscapeSun` | class | ExtendedBehaviour | — | 7 | 87 | [EscapeSun.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/EscapeSun.java) |
| `StayWithinDistanceOfAttackTarget` | class | ExtendedBehaviour | — | 9 | 102 | [StayWithinDistanceOfAttackTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/StayWithinDistanceOfAttackTarget.java) |
| `FloatToSurfaceOfFluid` | class | ExtendedBehaviour | — | 7 | 51 | [FloatToSurfaceOfFluid.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/FloatToSurfaceOfFluid.java) |
| `SetWalkTargetToAttackTarget` | class | ExtendedBehaviour | — | 7 | 78 | [SetWalkTargetToAttackTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/path/SetWalkTargetToAttackTarget.java) |
| `SetRandomSwimTarget` | class | ExtendedBehaviour | — | 8 | 71 | [SetRandomSwimTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/path/SetRandomSwimTarget.java) |
| `SetRandomHoverTarget` | class | ExtendedBehaviour | — | 8 | 80 | [SetRandomHoverTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/path/SetRandomHoverTarget.java) |
| `BreedWithPartner` | class | ExtendedBehaviour | — | 9 | 105 | [BreedWithPartner.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/BreedWithPartner.java) |
| `ReactToUnreachableTarget` | class | ExtendedBehaviour | — | 8 | 64 | [ReactToUnreachableTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/ReactToUnreachableTarget.java) |
| `InvalidateAttackTarget` | class | ExtendedBehaviour | — | 8 | 79 | [InvalidateAttackTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/target/InvalidateAttackTarget.java) |
| `SetAttackTarget` | class | ExtendedBehaviour | — | 5 | 63 | [SetAttackTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/target/SetAttackTarget.java) |
| `SetRandomLookTarget` | class | ExtendedBehaviour | — | 5 | 51 | [SetRandomLookTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/target/SetRandomLookTarget.java) |
| `SetRetaliateTarget` | class | ExtendedBehaviour | — | 7 | 92 | [SetRetaliateTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/target/SetRetaliateTarget.java) |
| `SetPlayerLookTarget` | class | ExtendedBehaviour | — | 7 | 80 | [SetPlayerLookTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/target/SetPlayerLookTarget.java) |
| `SetAdditionalAttackTargets` | class | ExtendedBehaviour | — | 7 | 95 | [SetAdditionalAttackTargets.java](net/tslat/smartbrainlib/api/core/behaviour/custom/target/SetAdditionalAttackTargets.java) |
| `LookAtTarget` | class | ExtendedBehaviour | — | 5 | 51 | [LookAtTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/look/LookAtTarget.java) |
| `SensoryUtils` | class | — | — | 4 | 34 | [SensoryUtils.java](net/tslat/smartbrainlib/util/SensoryUtils.java) |
| `SBLSkeleton` | class | Skeleton | — | 9 | 116 | [SBLSkeleton.java](net/tslat/smartbrainlib/example/SBLSkeleton.java) |
| `MemoryTest` | class | ObjectArrayList | — | 9 | 62 | [MemoryTest.java](net/tslat/smartbrainlib/object/MemoryTest.java) |
| `APIOnly` | @interface | — | Retention、Target | 0 | 12 | [APIOnly.java](net/tslat/smartbrainlib/APIOnly.java) |
| `SBLLoader` | interface | — | — | 0 | 32 | [SBLLoader.java](net/tslat/smartbrainlib/SBLLoader.java) |
| `SBLConstants` | class | — | — | 0 | 14 | [SBLConstants.java](net/tslat/smartbrainlib/SBLConstants.java) |
| `SmartBrainOwner` | interface | — | — | 0 | 66 | [SmartBrainOwner.java](net/tslat/smartbrainlib/api/SmartBrainOwner.java) |
| `SmoothAmphibiousPathNavigation` | class | AmphibiousPathNavigation | — | 4 | 37 | [SmoothAmphibiousPathNavigation.java](net/tslat/smartbrainlib/api/core/navigation/SmoothAmphibiousPathNavigation.java) |
| `MultiFluidNavigationElement` | interface | — | — | 0 | 15 | [MultiFluidNavigationElement.java](net/tslat/smartbrainlib/api/core/navigation/MultiFluidNavigationElement.java) |
| `SmoothWaterBoundPathNavigation` | class | WaterBoundPathNavigation | — | 4 | 38 | [SmoothWaterBoundPathNavigation.java](net/tslat/smartbrainlib/api/core/navigation/SmoothWaterBoundPathNavigation.java) |
| `MultiFluidSmoothGroundNavigation` | class | SmoothGroundNavigation | — | 3 | 54 | [MultiFluidSmoothGroundNavigation.java](net/tslat/smartbrainlib/api/core/navigation/MultiFluidSmoothGroundNavigation.java) |
| `SmoothFlyingPathNavigation` | class | FlyingPathNavigation | — | 3 | 33 | [SmoothFlyingPathNavigation.java](net/tslat/smartbrainlib/api/core/navigation/SmoothFlyingPathNavigation.java) |
| `SmoothWallClimberNavigation` | class | WallClimberNavigation | — | 4 | 37 | [SmoothWallClimberNavigation.java](net/tslat/smartbrainlib/api/core/navigation/SmoothWallClimberNavigation.java) |
| `MultiFluidWalkNodeEvaluator` | class | WalkNodeEvaluator | — | 2 | 70 | [MultiFluidWalkNodeEvaluator.java](net/tslat/smartbrainlib/api/core/navigation/nodeevaluator/MultiFluidWalkNodeEvaluator.java) |
| `PredicateSensor` | class | ExtendedSensor | — | 2 | 26 | [PredicateSensor.java](net/tslat/smartbrainlib/api/core/sensor/PredicateSensor.java) |
| `GenericAttackTargetSensor` | class | EntityFilteringSensor | — | 4 | 35 | [GenericAttackTargetSensor.java](net/tslat/smartbrainlib/api/core/sensor/custom/GenericAttackTargetSensor.java) |
| `UnreachableTargetSensor` | class | ExtendedSensor | — | 4 | 62 | [UnreachableTargetSensor.java](net/tslat/smartbrainlib/api/core/sensor/custom/UnreachableTargetSensor.java) |
| `IncomingProjectilesSensor` | class | PredicateSensor | — | 3 | 51 | [IncomingProjectilesSensor.java](net/tslat/smartbrainlib/api/core/sensor/custom/IncomingProjectilesSensor.java) |
| `NearestHomeSensor` | class | PredicateSensor | — | 4 | 83 | [NearestHomeSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/NearestHomeSensor.java) |
| `NearbyBabySensor` | class | EntityFilteringSensor | — | 4 | 36 | [NearbyBabySensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/NearbyBabySensor.java) |
| `NearbyGolemSensor` | class | PredicateSensor | — | 4 | 53 | [NearbyGolemSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/NearbyGolemSensor.java) |
| `HoglinSpecificSensor` | class | ExtendedSensor | — | 3 | 74 | [HoglinSpecificSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/HoglinSpecificSensor.java) |
| `NearbyAdultSensor` | class | EntityFilteringSensor | — | 4 | 35 | [NearbyAdultSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/NearbyAdultSensor.java) |
| `HurtBySensor` | class | PredicateSensor | — | 3 | 56 | [HurtBySensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/HurtBySensor.java) |
| `FrogSpecificSensor` | class | EntityFilteringSensor | — | 4 | 58 | [FrogSpecificSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/FrogSpecificSensor.java) |
| `WardenSpecificSensor` | class | NearbyLivingEntitySensor | — | 3 | 57 | [WardenSpecificSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/WardenSpecificSensor.java) |
| `InWaterSensor` | class | PredicateSensor | — | 3 | 41 | [InWaterSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/InWaterSensor.java) |
| `PiglinBruteSpecificSensor` | class | ExtendedSensor | — | 3 | 54 | [PiglinBruteSpecificSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/PiglinBruteSpecificSensor.java) |
| `PiglinSpecificSensor` | class | ExtendedSensor | — | 3 | 128 | [PiglinSpecificSensor.java](net/tslat/smartbrainlib/api/core/sensor/vanilla/PiglinSpecificSensor.java) |
| `FirstApplicableBehaviour` | class | GroupBehaviour | — | 1 | 39 | [FirstApplicableBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/FirstApplicableBehaviour.java) |
| `OneRandomBehaviour` | class | GroupBehaviour | — | 1 | 41 | [OneRandomBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/OneRandomBehaviour.java) |
| `BowAttack` | class | AnimatableRangedAttack | — | 3 | 40 | [BowAttack.java](net/tslat/smartbrainlib/api/core/behaviour/custom/attack/BowAttack.java) |
| `WalkOrRunToWalkTarget` | class | MoveToWalkTarget | — | 2 | 22 | [WalkOrRunToWalkTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/WalkOrRunToWalkTarget.java) |
| `FollowParent` | class | FollowEntity | — | 4 | 46 | [FollowParent.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/FollowParent.java) |
| `FollowOwner` | class | FollowEntity | — | 1 | 26 | [FollowOwner.java](net/tslat/smartbrainlib/api/core/behaviour/custom/move/FollowOwner.java) |
| `SetRandomFlyingTarget` | class | SetRandomHoverTarget | — | 2 | 42 | [SetRandomFlyingTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/path/SetRandomFlyingTarget.java) |
| `CustomHeldBehaviour` | class | HeldBehaviour | — | 3 | 33 | [CustomHeldBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/CustomHeldBehaviour.java) |
| `InvalidateMemory` | class | ExtendedBehaviour | — | 4 | 48 | [InvalidateMemory.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/InvalidateMemory.java) |
| `AvoidSun` | class | ExtendedBehaviour | — | 4 | 33 | [AvoidSun.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/AvoidSun.java) |
| `CustomDelayedBehaviour` | class | DelayedBehaviour | — | 1 | 20 | [CustomDelayedBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/CustomDelayedBehaviour.java) |
| `CustomBehaviour` | class | ExtendedBehaviour | — | 3 | 33 | [CustomBehaviour.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/CustomBehaviour.java) |
| `Idle` | class | ExtendedBehaviour | — | 2 | 21 | [Idle.java](net/tslat/smartbrainlib/api/core/behaviour/custom/misc/Idle.java) |
| `LookAtAttackTarget` | class | ExtendedBehaviour | — | 4 | 44 | [LookAtAttackTarget.java](net/tslat/smartbrainlib/api/core/behaviour/custom/look/LookAtAttackTarget.java) |
| `SBLMemoryTypes` | class | — | — | 3 | 33 | [SBLMemoryTypes.java](net/tslat/smartbrainlib/registry/SBLMemoryTypes.java) |
| `SBLSensors` | class | — | — | 2 | 69 | [SBLSensors.java](net/tslat/smartbrainlib/registry/SBLSensors.java) |
| `FreePositionTracker` | class | PositionTracker | — | 4 | 32 | [FreePositionTracker.java](net/tslat/smartbrainlib/object/FreePositionTracker.java) |
| `BrainBehaviourConsumer` | interface | — | — | 0 | 11 | [BrainBehaviourConsumer.java](net/tslat/smartbrainlib/object/BrainBehaviourConsumer.java) |
| `TriPredicate` | interface | — | — | 0 | 23 | [TriPredicate.java](net/tslat/smartbrainlib/object/TriPredicate.java) |
| `SquareRadius` | record | — | — | 4 | 25 | [SquareRadius.java](net/tslat/smartbrainlib/object/SquareRadius.java) |
| `BrainBehaviourPredicate` | interface | — | — | 0 | 11 | [BrainBehaviourPredicate.java](net/tslat/smartbrainlib/object/BrainBehaviourPredicate.java) |
| `FixedNearestVisibleLivingEntities` | class | NearestVisibleLivingEntities | — | 2 | 29 | [FixedNearestVisibleLivingEntities.java](net/tslat/smartbrainlib/object/FixedNearestVisibleLivingEntities.java) |
| `DynamicPositionTracker` | class | PositionTracker | — | 3 | 29 | [DynamicPositionTracker.java](net/tslat/smartbrainlib/object/DynamicPositionTracker.java) |
