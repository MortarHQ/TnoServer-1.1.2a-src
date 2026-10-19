# Friends&Foes - Beekeeper Hut（`beekeeperhut`）

> jar：`[养蜂人]beekeeperhut.jar` ｜ 版本：`3.0.1` ｜ 作者：Faboslav
> 规模：**10 个类 / 7 个包 / 255 行** ｜ 事件处理类 0 ｜ Mixin 0 ｜ 注册/管理类 0

**官方描述**：An addon for the Friends&Foes mod, adding a beekeeper hut structure.

**依赖**：`neoforge`、`minecraft`、`friendsandfoes`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.faboslav.friendsandfoes.beekeeperhut.util.world.processor` | 2 | `BeekeeperHutItemFrameProcessorHelper`、`BeekeeperHutArmorStandProcessorHelper` |
| `com.faboslav.friendsandfoes.beekeeperhut.world.processor.neoforge` | 2 | `BeekeeperHutItemFrameProcessor`、`BeekeeperHutArmorStandProcessor` |
| `com.faboslav.friendsandfoes.beekeeperhut.platform` | 2 | `ProcessorTypes`、`PlatformHooks` |
| `com.faboslav.friendsandfoes.beekeeperhut.neoforge` | 1 | `BeekeeperHutNeoForge` |
| `com.faboslav.friendsandfoes.beekeeperhut` | 1 | `BeekeeperHut` |
| `com.faboslav.friendsandfoes.beekeeperhut.platform.neoforge` | 1 | `ProcessorTypes` |
| `com.faboslav.friendsandfoes.beekeeperhut.init` | 1 | `BeekeeperHutStructureProcessorTypes` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 10）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BeekeeperHutNeoForge` | class | — | Mod | 0 | 14 | [BeekeeperHutNeoForge.java](com/faboslav/friendsandfoes/beekeeperhut/neoforge/BeekeeperHutNeoForge.java) |
| `BeekeeperHutItemFrameProcessorHelper` | class | — | — | 1 | 21 | [BeekeeperHutItemFrameProcessorHelper.java](com/faboslav/friendsandfoes/beekeeperhut/util/world/processor/BeekeeperHutItemFrameProcessorHelper.java) |
| `BeekeeperHutItemFrameProcessor` | class | StructureProcessor | — | 3 | 44 | [BeekeeperHutItemFrameProcessor.java](com/faboslav/friendsandfoes/beekeeperhut/world/processor/neoforge/BeekeeperHutItemFrameProcessor.java) |
| `BeekeeperHutArmorStandProcessorHelper` | class | — | — | 1 | 56 | [BeekeeperHutArmorStandProcessorHelper.java](com/faboslav/friendsandfoes/beekeeperhut/util/world/processor/BeekeeperHutArmorStandProcessorHelper.java) |
| `BeekeeperHut` | class | — | — | 2 | 18 | [BeekeeperHut.java](com/faboslav/friendsandfoes/beekeeperhut/BeekeeperHut.java) |
| `ProcessorTypes` | interface | — | — | 0 | 6 | [ProcessorTypes.java](com/faboslav/friendsandfoes/beekeeperhut/platform/ProcessorTypes.java) |
| `PlatformHooks` | class | — | — | 1 | 12 | [PlatformHooks.java](com/faboslav/friendsandfoes/beekeeperhut/platform/PlatformHooks.java) |
| `ProcessorTypes` | class | com.faboslav.friendsandfoes.beekeepe | — | 1 | 19 | [ProcessorTypes.java](com/faboslav/friendsandfoes/beekeeperhut/platform/neoforge/ProcessorTypes.java) |
| `BeekeeperHutArmorStandProcessor` | class | StructureProcessor | — | 3 | 44 | [BeekeeperHutArmorStandProcessor.java](com/faboslav/friendsandfoes/beekeeperhut/world/processor/neoforge/BeekeeperHutArmorStandProcessor.java) |
| `BeekeeperHutStructureProcessorTypes` | class | — | — | 1 | 21 | [BeekeeperHutStructureProcessorTypes.java](com/faboslav/friendsandfoes/beekeeperhut/init/BeekeeperHutStructureProcessorTypes.java) |

## 全部类（10）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `BeekeeperHutNeoForge` | class | — | Mod | 0 | 14 | [BeekeeperHutNeoForge.java](com/faboslav/friendsandfoes/beekeeperhut/neoforge/BeekeeperHutNeoForge.java) |
| `BeekeeperHutItemFrameProcessorHelper` | class | — | — | 1 | 21 | [BeekeeperHutItemFrameProcessorHelper.java](com/faboslav/friendsandfoes/beekeeperhut/util/world/processor/BeekeeperHutItemFrameProcessorHelper.java) |
| `BeekeeperHutItemFrameProcessor` | class | StructureProcessor | — | 3 | 44 | [BeekeeperHutItemFrameProcessor.java](com/faboslav/friendsandfoes/beekeeperhut/world/processor/neoforge/BeekeeperHutItemFrameProcessor.java) |
| `BeekeeperHutArmorStandProcessorHelper` | class | — | — | 1 | 56 | [BeekeeperHutArmorStandProcessorHelper.java](com/faboslav/friendsandfoes/beekeeperhut/util/world/processor/BeekeeperHutArmorStandProcessorHelper.java) |
| `BeekeeperHut` | class | — | — | 2 | 18 | [BeekeeperHut.java](com/faboslav/friendsandfoes/beekeeperhut/BeekeeperHut.java) |
| `ProcessorTypes` | interface | — | — | 0 | 6 | [ProcessorTypes.java](com/faboslav/friendsandfoes/beekeeperhut/platform/ProcessorTypes.java) |
| `PlatformHooks` | class | — | — | 1 | 12 | [PlatformHooks.java](com/faboslav/friendsandfoes/beekeeperhut/platform/PlatformHooks.java) |
| `ProcessorTypes` | class | com.faboslav.friendsandfoes.beekeepe | — | 1 | 19 | [ProcessorTypes.java](com/faboslav/friendsandfoes/beekeeperhut/platform/neoforge/ProcessorTypes.java) |
| `BeekeeperHutArmorStandProcessor` | class | StructureProcessor | — | 3 | 44 | [BeekeeperHutArmorStandProcessor.java](com/faboslav/friendsandfoes/beekeeperhut/world/processor/neoforge/BeekeeperHutArmorStandProcessor.java) |
| `BeekeeperHutStructureProcessorTypes` | class | — | — | 1 | 21 | [BeekeeperHutStructureProcessorTypes.java](com/faboslav/friendsandfoes/beekeeperhut/init/BeekeeperHutStructureProcessorTypes.java) |
