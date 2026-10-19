# TnoServer 1.1.2a — 反编译源码仓库

本仓库是 Minecraft 整合包 **TnoServer 1.1.2a**（MC 1.21.1 / NeoForge 21.1.244，216 个 jar）的
**全量反编译源码** + 整合包自身的魔改层（config / kubejs）+ 自动生成的结构索引，
目的是让代码可检索、可问答（可直接交给 DeepWiki / 其他代码 wiki 工具生成文档）。

## 这是什么，不是什么

- ✅ 每个 mod 的真实实现：类、方法、事件、Mixin、注册表、配置读取逻辑。
- ✅ 整合包的**魔改层**：`pack/config/`（文本配置）、`pack/kubejs/`（脚本 + 数据包 + 汉化覆盖）。
- ✅ 机器可读索引：`docs/classes.tsv`（3.3 万类）、`docs/configs.tsv`（配置项 → 读取者）、`docs/ids.tsv`（注册 id → 源码文件）。
- ❌ 不是可编译工程：没有 `build.gradle`、没有依赖 jar、没有资源文件（`assets/`、`data/` 已剔除以控制仓库体积）。
- ❌ 不含注释与 Javadoc（反编译无法恢复）；局部变量名是 `var1` 这类；少数类会保留 `// $VF: Couldn't be decompiled`。

## 目录结构

```
mods/<modid>/                 # 每个 mod 一个目录
├─ README.md                  # 该 mod 的结构说明（jar、版本、依赖、包结构、关键类、事件、Mixin、配置类）
└─ <包路径>/<类>.java          # 反编译源码（一个 .java 对应一个类）
pack/
├─ config/                    # 整合包 config/ 的文本配置（剔除皮肤/菜单等大资源）
├─ kubejs/                    # 脚本、数据包（配方/标签/战利品/世界生成）、汉化覆盖
└─ README.md
meta/
├─ mods.tsv                   # 216 jar → modId / 版本 / 分类 / 汉化覆盖
└─ decompile.tsv              # 每个 jar 的反编译日志（状态 / 类数 / 耗时）
docs/
├─ 00-索引.md                 # 全部 mod 的类数/包数/事件/Mixin 一览
├─ 10-阅读指南.md              # 怎么在这套源码里检索（含 grep 配方）
├─ classes.tsv                # mod | 包 | 类 | 继承 | 注解 | 方法数 | 事件数 | mixin | 行数 | 路径
├─ configs.tsv                # mod | 文件 | 键 | 实际值 | 类型 | 注释 | 读取者
├─ ids.tsv                    # 物品/方块/实体 id → 候选源码文件
└─ configs/<modid>.md         # 每个 mod 的配置项逐项文档
```


## 覆盖范围说明

- `mods/` 下 **209 个目录** 覆盖了有 Java 类的全部 mod；**33,884 个 .java**。
- 另外 **7 个 jar 没有 Java 类**：5 个纯数据 mod（`explorify`、`geophilic`、`geophilic_backport`、`mes`、`mr_lukis_woodlandmansions`）
  的 `data/` 已放在 `pack/data-only-mods/`；`fzzy_config`（纯 Kotlin 资源）与 `kotlinforforge`（jar 打包器）未收录。
- 2 个 **Fabric** mod（`mebahels-api`、`mebahelcreaturesdwarven`）的原代码也一并反编译进来，但它们在本包（NeoForge）下不加载。
- 未收录：各 mod 的 `assets/`、`data/` 资源（贴图/模型/音效/配方/战利品表），以及 jar 内嵌套的 JiJ 依赖库；
  这些属于"数据"而非"代码"，需要时请直接查整合包本体。

## 常见查法

| 想问什么 | 怎么做 |
|---|---|
| 某物品/方块/实体在哪实现 | `docs/ids.tsv` 搜 `命名空间:路径` → 得到源文件；或 `grep -rn "registry_name" mods/<modid>/` |
| 某个配置项有什么影响 | `docs/configs.tsv` 搜键名 → 看「读取者」列 → 打开对应类 |
| 某事件/钩子触发了什么 | `awk -F'\t' '$9>0' docs/classes.tsv` 找事件类 → 读类里的 `@SubscribeEvent` 方法 |
| 某 Mixin 改了原版什么行为 | `awk -F'\t' '$10==1' docs/classes.tsv` → 读 `@Inject/@Redirect/@ModifyVariable` 所在方法 |
| 某 mod 整体结构 | 打开 `mods/<modid>/README.md` |
| 技能/遗物的具体数值 | 先看 `docs/ids.tsv` 或 `grep -rn "<skill_id>" mods/<modid>/`，再对照 `pack/config/` |

更完整的检索套路见 [`docs/10-阅读指南.md`](docs/10-阅读指南.md)。

## 反编译信息

- 工具：**Vineflower 1.12.0**（JDK 21.0.5），命令：
  `java -jar vineflower.jar --folder --decompile-generics=true --remove-bridge=true --remove-synthetic=true <jar> <out>`
- 覆盖：`mods/` 下 **216 个 jar 全部反编译成功**（日志见 `meta/decompile.tsv`），共 **33884 个 .java**。
- 未包含：jar 内嵌套的 JiJ 依赖库（26 个 mod 共 59 个内嵌 jar，属第三方库而非 mod 本体）。
- 已知瑕疵：**41 个类** 被 Vineflower 标记无法完整反编译（保留字节码注释）；混淆库的类名仍是 `a/b/c`。

## 来源与版权

所有代码版权归各 mod 原作者所有；本仓库仅为**本地分析与检索**用途的反编译副本，
不包含任何 mod 二进制，也不代表原作者立场。详见 [`NOTICE.md`](NOTICE.md)。
如有侵权请提出，仓库可立即删除。
