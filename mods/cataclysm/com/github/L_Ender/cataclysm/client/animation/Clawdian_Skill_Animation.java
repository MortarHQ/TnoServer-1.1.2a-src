package com.github.L_Ender.cataclysm.client.animation;

import net.minecraft.client.animation.AnimationDefinition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Clawdian_Skill_Animation {
   public static final AnimationDefinition CHARGE_READY;
   public static final AnimationDefinition CHARGE_LOOP;
   public static final AnimationDefinition CHARGE_END;
   public static final AnimationDefinition WAVE_STOMP;

   static {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.OutOfMemoryError: Java heap space
      //   at org.jetbrains.java.decompiler.util.collections.FastSparseSetFactory$FastSparseSet.allocNext(FastSparseSetFactory.java:144)
      //   at org.jetbrains.java.decompiler.util.collections.FastSparseSetFactory$FastSparseSet.changeNext(FastSparseSetFactory.java:200)
      //   at org.jetbrains.java.decompiler.util.collections.FastSparseSetFactory$FastSparseSet.add(FastSparseSetFactory.java:135)
      //   at org.jetbrains.java.decompiler.util.collections.SFormsFastMapDirect.setCurrentVar(SFormsFastMapDirect.java:398)
      //   at org.jetbrains.java.decompiler.util.collections.SFormsFastMapDirect.setCurrentVar(SFormsFastMapDirect.java:403)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SSAUConstructorSparseEx.varReadSingleVersion(SSAUConstructorSparseEx.java:111)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.varRead(SFormsConstructor.java:168)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.VarExprent.processSforms(VarExprent.java:574)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.Exprent.processSforms(Exprent.java:317)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.AssignmentExprent.processSforms(AssignmentExprent.java:343)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.AssignmentExprent.processSforms(AssignmentExprent.java:311)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.ssaStatements(SFormsConstructor.java:127)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SSAUConstructorSparseEx.splitVariables(SSAUConstructorSparseEx.java:45)
      //   at org.jetbrains.java.decompiler.modules.decompiler.StackVarsProcessor.simplifyStackVars(StackVarsProcessor.java:67)
      //   at org.jetbrains.java.decompiler.modules.decompiler.StackVarsProcessor.simplifyStackVars(StackVarsProcessor.java:43)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:238)
      //
      // Bytecode:
      // 0000: ldc 1.5
      // 0002: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0005: ldc "lower_body"
      // 0007: new net/minecraft/client/animation/AnimationChannel
      // 000a: dup
      // 000b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 000e: bipush 2
      // 000f: anewarray 24
      // 0012: dup
      // 0013: bipush 0
      // 0014: new net/minecraft/client/animation/Keyframe
      // 0017: dup
      // 0018: fconst_0
      // 0019: fconst_0
      // 001a: fconst_0
      // 001b: fconst_0
      // 001c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 001f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0022: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0025: aastore
      // 0026: dup
      // 0027: bipush 1
      // 0028: new net/minecraft/client/animation/Keyframe
      // 002b: dup
      // 002c: ldc 1.5
      // 002e: fconst_0
      // 002f: ldc -30.0
      // 0031: fconst_0
      // 0032: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0035: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0038: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 003b: aastore
      // 003c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 003f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0042: ldc "lower_body"
      // 0044: new net/minecraft/client/animation/AnimationChannel
      // 0047: dup
      // 0048: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 004b: bipush 1
      // 004c: anewarray 24
      // 004f: dup
      // 0050: bipush 0
      // 0051: new net/minecraft/client/animation/Keyframe
      // 0054: dup
      // 0055: fconst_0
      // 0056: fconst_0
      // 0057: fconst_0
      // 0058: fconst_0
      // 0059: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 005c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 005f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0062: aastore
      // 0063: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0066: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0069: ldc "tail1"
      // 006b: new net/minecraft/client/animation/AnimationChannel
      // 006e: dup
      // 006f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0072: bipush 2
      // 0073: anewarray 24
      // 0076: dup
      // 0077: bipush 0
      // 0078: new net/minecraft/client/animation/Keyframe
      // 007b: dup
      // 007c: fconst_0
      // 007d: fconst_0
      // 007e: fconst_0
      // 007f: fconst_0
      // 0080: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0083: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0086: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0089: aastore
      // 008a: dup
      // 008b: bipush 1
      // 008c: new net/minecraft/client/animation/Keyframe
      // 008f: dup
      // 0090: ldc 1.5
      // 0092: ldc 2.6061
      // 0094: ldc -14.8782
      // 0096: ldc -0.9806
      // 0098: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 009b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 009e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00a1: aastore
      // 00a2: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 00a5: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 00a8: ldc "pelvis"
      // 00aa: new net/minecraft/client/animation/AnimationChannel
      // 00ad: dup
      // 00ae: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 00b1: bipush 3
      // 00b2: anewarray 24
      // 00b5: dup
      // 00b6: bipush 0
      // 00b7: new net/minecraft/client/animation/Keyframe
      // 00ba: dup
      // 00bb: fconst_0
      // 00bc: fconst_0
      // 00bd: fconst_0
      // 00be: fconst_0
      // 00bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 00c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00c8: aastore
      // 00c9: dup
      // 00ca: bipush 1
      // 00cb: new net/minecraft/client/animation/Keyframe
      // 00ce: dup
      // 00cf: ldc 0.875
      // 00d1: ldc 32.5623
      // 00d3: ldc 4.5839
      // 00d5: ldc -2.6332
      // 00d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 00da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00e0: aastore
      // 00e1: dup
      // 00e2: bipush 2
      // 00e3: new net/minecraft/client/animation/Keyframe
      // 00e6: dup
      // 00e7: ldc 1.5
      // 00e9: ldc 51.9151
      // 00eb: ldc 8.1763
      // 00ed: ldc 12.1632
      // 00ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 00f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00f8: aastore
      // 00f9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 00fc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 00ff: ldc "pelvis"
      // 0101: new net/minecraft/client/animation/AnimationChannel
      // 0104: dup
      // 0105: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0108: bipush 2
      // 0109: anewarray 24
      // 010c: dup
      // 010d: bipush 0
      // 010e: new net/minecraft/client/animation/Keyframe
      // 0111: dup
      // 0112: fconst_0
      // 0113: fconst_0
      // 0114: fconst_0
      // 0115: fconst_0
      // 0116: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0119: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 011c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 011f: aastore
      // 0120: dup
      // 0121: bipush 1
      // 0122: new net/minecraft/client/animation/Keyframe
      // 0125: dup
      // 0126: ldc 1.5
      // 0128: fconst_0
      // 0129: fconst_0
      // 012a: fconst_0
      // 012b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 012e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0131: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0134: aastore
      // 0135: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0138: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 013b: ldc "tail2"
      // 013d: new net/minecraft/client/animation/AnimationChannel
      // 0140: dup
      // 0141: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0144: bipush 2
      // 0145: anewarray 24
      // 0148: dup
      // 0149: bipush 0
      // 014a: new net/minecraft/client/animation/Keyframe
      // 014d: dup
      // 014e: fconst_0
      // 014f: fconst_0
      // 0150: fconst_0
      // 0151: fconst_0
      // 0152: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0155: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0158: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 015b: aastore
      // 015c: dup
      // 015d: bipush 1
      // 015e: new net/minecraft/client/animation/Keyframe
      // 0161: dup
      // 0162: ldc 1.5
      // 0164: ldc 7.4506
      // 0166: ldc -28.2464
      // 0168: ldc -8.4326
      // 016a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 016d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0170: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0173: aastore
      // 0174: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0177: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 017a: ldc "body"
      // 017c: new net/minecraft/client/animation/AnimationChannel
      // 017f: dup
      // 0180: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0183: bipush 2
      // 0184: anewarray 24
      // 0187: dup
      // 0188: bipush 0
      // 0189: new net/minecraft/client/animation/Keyframe
      // 018c: dup
      // 018d: fconst_0
      // 018e: fconst_0
      // 018f: fconst_0
      // 0190: fconst_0
      // 0191: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0194: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0197: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 019a: aastore
      // 019b: dup
      // 019c: bipush 1
      // 019d: new net/minecraft/client/animation/Keyframe
      // 01a0: dup
      // 01a1: ldc 1.5
      // 01a3: ldc -0.1856
      // 01a5: ldc 29.7735
      // 01a7: ldc 3.9792
      // 01a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01b2: aastore
      // 01b3: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 01b6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 01b9: ldc "neck"
      // 01bb: new net/minecraft/client/animation/AnimationChannel
      // 01be: dup
      // 01bf: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 01c2: bipush 2
      // 01c3: anewarray 24
      // 01c6: dup
      // 01c7: bipush 0
      // 01c8: new net/minecraft/client/animation/Keyframe
      // 01cb: dup
      // 01cc: fconst_0
      // 01cd: fconst_0
      // 01ce: fconst_0
      // 01cf: fconst_0
      // 01d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01d9: aastore
      // 01da: dup
      // 01db: bipush 1
      // 01dc: new net/minecraft/client/animation/Keyframe
      // 01df: dup
      // 01e0: ldc 1.5
      // 01e2: ldc 3.4161
      // 01e4: ldc -30.5752
      // 01e6: ldc -6.6815
      // 01e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01f1: aastore
      // 01f2: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 01f5: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 01f8: ldc "left_arm"
      // 01fa: new net/minecraft/client/animation/AnimationChannel
      // 01fd: dup
      // 01fe: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0201: bipush 2
      // 0202: anewarray 24
      // 0205: dup
      // 0206: bipush 0
      // 0207: new net/minecraft/client/animation/Keyframe
      // 020a: dup
      // 020b: fconst_0
      // 020c: fconst_0
      // 020d: fconst_0
      // 020e: fconst_0
      // 020f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0212: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0215: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0218: aastore
      // 0219: dup
      // 021a: bipush 1
      // 021b: new net/minecraft/client/animation/Keyframe
      // 021e: dup
      // 021f: ldc 1.5
      // 0221: ldc -115.5439
      // 0223: ldc 3.0318
      // 0225: ldc 27.3279
      // 0227: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 022a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 022d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0230: aastore
      // 0231: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0234: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0237: ldc "left_front_arm"
      // 0239: new net/minecraft/client/animation/AnimationChannel
      // 023c: dup
      // 023d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0240: bipush 2
      // 0241: anewarray 24
      // 0244: dup
      // 0245: bipush 0
      // 0246: new net/minecraft/client/animation/Keyframe
      // 0249: dup
      // 024a: fconst_0
      // 024b: fconst_0
      // 024c: fconst_0
      // 024d: fconst_0
      // 024e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0251: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0254: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0257: aastore
      // 0258: dup
      // 0259: bipush 1
      // 025a: new net/minecraft/client/animation/Keyframe
      // 025d: dup
      // 025e: ldc 1.5
      // 0260: ldc 17.0
      // 0262: fconst_0
      // 0263: ldc 62.5
      // 0265: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0268: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 026b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 026e: aastore
      // 026f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0272: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0275: ldc "left_claw"
      // 0277: new net/minecraft/client/animation/AnimationChannel
      // 027a: dup
      // 027b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 027e: bipush 3
      // 027f: anewarray 24
      // 0282: dup
      // 0283: bipush 0
      // 0284: new net/minecraft/client/animation/Keyframe
      // 0287: dup
      // 0288: fconst_0
      // 0289: fconst_0
      // 028a: fconst_0
      // 028b: fconst_0
      // 028c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 028f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0292: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0295: aastore
      // 0296: dup
      // 0297: bipush 1
      // 0298: new net/minecraft/client/animation/Keyframe
      // 029b: dup
      // 029c: ldc 0.8333
      // 029e: ldc -30.0
      // 02a0: fconst_0
      // 02a1: fconst_0
      // 02a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02ab: aastore
      // 02ac: dup
      // 02ad: bipush 2
      // 02ae: new net/minecraft/client/animation/Keyframe
      // 02b1: dup
      // 02b2: ldc 1.5
      // 02b4: ldc 25.0
      // 02b6: fconst_0
      // 02b7: fconst_0
      // 02b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02c1: aastore
      // 02c2: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 02c5: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 02c8: ldc "right_arm"
      // 02ca: new net/minecraft/client/animation/AnimationChannel
      // 02cd: dup
      // 02ce: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 02d1: bipush 2
      // 02d2: anewarray 24
      // 02d5: dup
      // 02d6: bipush 0
      // 02d7: new net/minecraft/client/animation/Keyframe
      // 02da: dup
      // 02db: fconst_0
      // 02dc: fconst_0
      // 02dd: fconst_0
      // 02de: fconst_0
      // 02df: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02e8: aastore
      // 02e9: dup
      // 02ea: bipush 1
      // 02eb: new net/minecraft/client/animation/Keyframe
      // 02ee: dup
      // 02ef: ldc 1.5
      // 02f1: fconst_0
      // 02f2: fconst_0
      // 02f3: fconst_0
      // 02f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02fd: aastore
      // 02fe: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0301: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0304: ldc "right_arm"
      // 0306: new net/minecraft/client/animation/AnimationChannel
      // 0309: dup
      // 030a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 030d: bipush 1
      // 030e: anewarray 24
      // 0311: dup
      // 0312: bipush 0
      // 0313: new net/minecraft/client/animation/Keyframe
      // 0316: dup
      // 0317: ldc 1.5
      // 0319: fconst_0
      // 031a: fconst_0
      // 031b: fconst_0
      // 031c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 031f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0322: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0325: aastore
      // 0326: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0329: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 032c: ldc "right_front_arm"
      // 032e: new net/minecraft/client/animation/AnimationChannel
      // 0331: dup
      // 0332: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0335: bipush 2
      // 0336: anewarray 24
      // 0339: dup
      // 033a: bipush 0
      // 033b: new net/minecraft/client/animation/Keyframe
      // 033e: dup
      // 033f: fconst_0
      // 0340: fconst_0
      // 0341: fconst_0
      // 0342: fconst_0
      // 0343: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0346: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0349: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 034c: aastore
      // 034d: dup
      // 034e: bipush 1
      // 034f: new net/minecraft/client/animation/Keyframe
      // 0352: dup
      // 0353: ldc 1.5
      // 0355: fconst_0
      // 0356: fconst_0
      // 0357: fconst_0
      // 0358: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 035b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 035e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0361: aastore
      // 0362: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0365: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0368: ldc "right_claw"
      // 036a: new net/minecraft/client/animation/AnimationChannel
      // 036d: dup
      // 036e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0371: bipush 2
      // 0372: anewarray 24
      // 0375: dup
      // 0376: bipush 0
      // 0377: new net/minecraft/client/animation/Keyframe
      // 037a: dup
      // 037b: fconst_0
      // 037c: fconst_0
      // 037d: fconst_0
      // 037e: fconst_0
      // 037f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0382: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0385: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0388: aastore
      // 0389: dup
      // 038a: bipush 1
      // 038b: new net/minecraft/client/animation/Keyframe
      // 038e: dup
      // 038f: ldc 1.5
      // 0391: fconst_0
      // 0392: fconst_0
      // 0393: fconst_0
      // 0394: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0397: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 039a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 039d: aastore
      // 039e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 03a1: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 03a4: ldc "right_hammer"
      // 03a6: new net/minecraft/client/animation/AnimationChannel
      // 03a9: dup
      // 03aa: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 03ad: bipush 2
      // 03ae: anewarray 24
      // 03b1: dup
      // 03b2: bipush 0
      // 03b3: new net/minecraft/client/animation/Keyframe
      // 03b6: dup
      // 03b7: fconst_0
      // 03b8: fconst_0
      // 03b9: fconst_0
      // 03ba: fconst_0
      // 03bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03c4: aastore
      // 03c5: dup
      // 03c6: bipush 1
      // 03c7: new net/minecraft/client/animation/Keyframe
      // 03ca: dup
      // 03cb: ldc 1.5
      // 03cd: ldc 5.0
      // 03cf: fconst_0
      // 03d0: fconst_0
      // 03d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03da: aastore
      // 03db: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 03de: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 03e1: ldc "right_hammer"
      // 03e3: new net/minecraft/client/animation/AnimationChannel
      // 03e6: dup
      // 03e7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 03ea: bipush 2
      // 03eb: anewarray 24
      // 03ee: dup
      // 03ef: bipush 0
      // 03f0: new net/minecraft/client/animation/Keyframe
      // 03f3: dup
      // 03f4: fconst_0
      // 03f5: fconst_0
      // 03f6: fconst_0
      // 03f7: fconst_0
      // 03f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 03fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0401: aastore
      // 0402: dup
      // 0403: bipush 1
      // 0404: new net/minecraft/client/animation/Keyframe
      // 0407: dup
      // 0408: ldc 1.5
      // 040a: ldc 1.9264
      // 040c: ldc -0.5482
      // 040e: ldc 1.0948
      // 0410: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0413: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0416: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0419: aastore
      // 041a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 041d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0420: ldc "left_front_arm_rotator"
      // 0422: new net/minecraft/client/animation/AnimationChannel
      // 0425: dup
      // 0426: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0429: bipush 2
      // 042a: anewarray 24
      // 042d: dup
      // 042e: bipush 0
      // 042f: new net/minecraft/client/animation/Keyframe
      // 0432: dup
      // 0433: fconst_0
      // 0434: fconst_0
      // 0435: fconst_0
      // 0436: fconst_0
      // 0437: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 043a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 043d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0440: aastore
      // 0441: dup
      // 0442: bipush 1
      // 0443: new net/minecraft/client/animation/Keyframe
      // 0446: dup
      // 0447: ldc 1.5
      // 0449: ldc 15.0
      // 044b: fconst_0
      // 044c: fconst_0
      // 044d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0450: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0453: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0456: aastore
      // 0457: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 045a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 045d: ldc "everything"
      // 045f: new net/minecraft/client/animation/AnimationChannel
      // 0462: dup
      // 0463: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0466: bipush 1
      // 0467: anewarray 24
      // 046a: dup
      // 046b: bipush 0
      // 046c: new net/minecraft/client/animation/Keyframe
      // 046f: dup
      // 0470: fconst_0
      // 0471: fconst_0
      // 0472: fconst_0
      // 0473: fconst_0
      // 0474: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0477: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 047a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 047d: aastore
      // 047e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0481: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0484: ldc "mid_root"
      // 0486: new net/minecraft/client/animation/AnimationChannel
      // 0489: dup
      // 048a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 048d: bipush 2
      // 048e: anewarray 24
      // 0491: dup
      // 0492: bipush 0
      // 0493: new net/minecraft/client/animation/Keyframe
      // 0496: dup
      // 0497: fconst_0
      // 0498: fconst_0
      // 0499: fconst_0
      // 049a: fconst_0
      // 049b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 049e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04a4: aastore
      // 04a5: dup
      // 04a6: bipush 1
      // 04a7: new net/minecraft/client/animation/Keyframe
      // 04aa: dup
      // 04ab: ldc 1.5
      // 04ad: ldc 20.0
      // 04af: ldc 30.0
      // 04b1: fconst_0
      // 04b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04bb: aastore
      // 04bc: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 04bf: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 04c2: ldc "mid_root"
      // 04c4: new net/minecraft/client/animation/AnimationChannel
      // 04c7: dup
      // 04c8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 04cb: bipush 2
      // 04cc: anewarray 24
      // 04cf: dup
      // 04d0: bipush 0
      // 04d1: new net/minecraft/client/animation/Keyframe
      // 04d4: dup
      // 04d5: fconst_0
      // 04d6: fconst_0
      // 04d7: fconst_0
      // 04d8: fconst_0
      // 04d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 04dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04e2: aastore
      // 04e3: dup
      // 04e4: bipush 1
      // 04e5: new net/minecraft/client/animation/Keyframe
      // 04e8: dup
      // 04e9: ldc 1.5
      // 04eb: fconst_0
      // 04ec: ldc 0.5
      // 04ee: fconst_0
      // 04ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 04f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04f8: aastore
      // 04f9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 04fc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 04ff: ldc "head"
      // 0501: new net/minecraft/client/animation/AnimationChannel
      // 0504: dup
      // 0505: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0508: bipush 2
      // 0509: anewarray 24
      // 050c: dup
      // 050d: bipush 0
      // 050e: new net/minecraft/client/animation/Keyframe
      // 0511: dup
      // 0512: fconst_0
      // 0513: fconst_0
      // 0514: fconst_0
      // 0515: fconst_0
      // 0516: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0519: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 051c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 051f: aastore
      // 0520: dup
      // 0521: bipush 1
      // 0522: new net/minecraft/client/animation/Keyframe
      // 0525: dup
      // 0526: ldc 1.5
      // 0528: ldc -69.7576
      // 052a: ldc -10.4897
      // 052c: ldc 1.4281
      // 052e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0531: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0534: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0537: aastore
      // 0538: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 053b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 053e: ldc "left_f_leg"
      // 0540: new net/minecraft/client/animation/AnimationChannel
      // 0543: dup
      // 0544: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0547: bipush 2
      // 0548: anewarray 24
      // 054b: dup
      // 054c: bipush 0
      // 054d: new net/minecraft/client/animation/Keyframe
      // 0550: dup
      // 0551: fconst_0
      // 0552: fconst_0
      // 0553: fconst_0
      // 0554: fconst_0
      // 0555: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0558: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 055b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 055e: aastore
      // 055f: dup
      // 0560: bipush 1
      // 0561: new net/minecraft/client/animation/Keyframe
      // 0564: dup
      // 0565: ldc 1.5
      // 0567: fconst_0
      // 0568: fconst_0
      // 0569: fconst_0
      // 056a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 056d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0570: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0573: aastore
      // 0574: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0577: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 057a: ldc "left_f_leg"
      // 057c: new net/minecraft/client/animation/AnimationChannel
      // 057f: dup
      // 0580: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0583: bipush 2
      // 0584: anewarray 24
      // 0587: dup
      // 0588: bipush 0
      // 0589: new net/minecraft/client/animation/Keyframe
      // 058c: dup
      // 058d: fconst_0
      // 058e: fconst_0
      // 058f: fconst_0
      // 0590: fconst_0
      // 0591: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0594: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0597: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 059a: aastore
      // 059b: dup
      // 059c: bipush 1
      // 059d: new net/minecraft/client/animation/Keyframe
      // 05a0: dup
      // 05a1: ldc 1.5
      // 05a3: fconst_0
      // 05a4: ldc 0.5209
      // 05a6: ldc 2.9544
      // 05a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 05ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05b1: aastore
      // 05b2: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 05b5: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 05b8: ldc "left_f_fore_leg"
      // 05ba: new net/minecraft/client/animation/AnimationChannel
      // 05bd: dup
      // 05be: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 05c1: bipush 2
      // 05c2: anewarray 24
      // 05c5: dup
      // 05c6: bipush 0
      // 05c7: new net/minecraft/client/animation/Keyframe
      // 05ca: dup
      // 05cb: fconst_0
      // 05cc: fconst_0
      // 05cd: fconst_0
      // 05ce: fconst_0
      // 05cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05d8: aastore
      // 05d9: dup
      // 05da: bipush 1
      // 05db: new net/minecraft/client/animation/Keyframe
      // 05de: dup
      // 05df: ldc 1.5
      // 05e1: ldc -11.3173
      // 05e3: ldc -1.3485
      // 05e5: ldc -29.6357
      // 05e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05f0: aastore
      // 05f1: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 05f4: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 05f7: ldc "right_b_leg"
      // 05f9: new net/minecraft/client/animation/AnimationChannel
      // 05fc: dup
      // 05fd: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0600: bipush 2
      // 0601: anewarray 24
      // 0604: dup
      // 0605: bipush 0
      // 0606: new net/minecraft/client/animation/Keyframe
      // 0609: dup
      // 060a: fconst_0
      // 060b: fconst_0
      // 060c: fconst_0
      // 060d: fconst_0
      // 060e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0611: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0614: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0617: aastore
      // 0618: dup
      // 0619: bipush 1
      // 061a: new net/minecraft/client/animation/Keyframe
      // 061d: dup
      // 061e: fconst_1
      // 061f: fconst_0
      // 0620: fconst_0
      // 0621: ldc -2.5
      // 0623: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0626: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0629: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 062c: aastore
      // 062d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0630: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0633: ldc "right_b_leg"
      // 0635: new net/minecraft/client/animation/AnimationChannel
      // 0638: dup
      // 0639: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 063c: bipush 2
      // 063d: anewarray 24
      // 0640: dup
      // 0641: bipush 0
      // 0642: new net/minecraft/client/animation/Keyframe
      // 0645: dup
      // 0646: fconst_0
      // 0647: fconst_0
      // 0648: fconst_0
      // 0649: fconst_0
      // 064a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 064d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0650: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0653: aastore
      // 0654: dup
      // 0655: bipush 1
      // 0656: new net/minecraft/client/animation/Keyframe
      // 0659: dup
      // 065a: ldc 1.5
      // 065c: fconst_0
      // 065d: ldc 0.1746
      // 065f: ldc 0.9892
      // 0661: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0664: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0667: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 066a: aastore
      // 066b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 066e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0671: ldc "right_b_fore_leg"
      // 0673: new net/minecraft/client/animation/AnimationChannel
      // 0676: dup
      // 0677: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 067a: bipush 2
      // 067b: anewarray 24
      // 067e: dup
      // 067f: bipush 0
      // 0680: new net/minecraft/client/animation/Keyframe
      // 0683: dup
      // 0684: fconst_0
      // 0685: fconst_0
      // 0686: fconst_0
      // 0687: fconst_0
      // 0688: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 068b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 068e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0691: aastore
      // 0692: dup
      // 0693: bipush 1
      // 0694: new net/minecraft/client/animation/Keyframe
      // 0697: dup
      // 0698: ldc 1.5
      // 069a: ldc -13.7142
      // 069c: ldc 2.6061
      // 069e: ldc 27.5327
      // 06a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06a9: aastore
      // 06aa: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 06ad: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 06b0: ldc "right_f_leg"
      // 06b2: new net/minecraft/client/animation/AnimationChannel
      // 06b5: dup
      // 06b6: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 06b9: bipush 1
      // 06ba: anewarray 24
      // 06bd: dup
      // 06be: bipush 0
      // 06bf: new net/minecraft/client/animation/Keyframe
      // 06c2: dup
      // 06c3: fconst_0
      // 06c4: fconst_0
      // 06c5: fconst_0
      // 06c6: fconst_0
      // 06c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06d0: aastore
      // 06d1: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 06d4: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 06d7: ldc "right_f_leg"
      // 06d9: new net/minecraft/client/animation/AnimationChannel
      // 06dc: dup
      // 06dd: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 06e0: bipush 3
      // 06e1: anewarray 24
      // 06e4: dup
      // 06e5: bipush 0
      // 06e6: new net/minecraft/client/animation/Keyframe
      // 06e9: dup
      // 06ea: fconst_0
      // 06eb: fconst_0
      // 06ec: fconst_0
      // 06ed: fconst_0
      // 06ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 06f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06f7: aastore
      // 06f8: dup
      // 06f9: bipush 1
      // 06fa: new net/minecraft/client/animation/Keyframe
      // 06fd: dup
      // 06fe: fconst_1
      // 06ff: ldc 0.467
      // 0701: ldc -0.6163
      // 0703: ldc 1.2203
      // 0705: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0708: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 070b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 070e: aastore
      // 070f: dup
      // 0710: bipush 2
      // 0711: new net/minecraft/client/animation/Keyframe
      // 0714: dup
      // 0715: ldc 1.5
      // 0717: fconst_0
      // 0718: ldc 2.9535
      // 071a: ldc -0.5254
      // 071c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 071f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0722: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0725: aastore
      // 0726: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0729: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 072c: ldc "right_f_fore_leg"
      // 072e: new net/minecraft/client/animation/AnimationChannel
      // 0731: dup
      // 0732: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0735: bipush 2
      // 0736: anewarray 24
      // 0739: dup
      // 073a: bipush 0
      // 073b: new net/minecraft/client/animation/Keyframe
      // 073e: dup
      // 073f: fconst_0
      // 0740: fconst_0
      // 0741: fconst_0
      // 0742: fconst_0
      // 0743: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0746: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0749: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 074c: aastore
      // 074d: dup
      // 074e: bipush 1
      // 074f: new net/minecraft/client/animation/Keyframe
      // 0752: dup
      // 0753: ldc 1.5
      // 0755: ldc 22.5
      // 0757: fconst_0
      // 0758: ldc -5.0
      // 075a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 075d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0760: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0763: aastore
      // 0764: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0767: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 076a: ldc "left_f_leg_joint"
      // 076c: new net/minecraft/client/animation/AnimationChannel
      // 076f: dup
      // 0770: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0773: bipush 2
      // 0774: anewarray 24
      // 0777: dup
      // 0778: bipush 0
      // 0779: new net/minecraft/client/animation/Keyframe
      // 077c: dup
      // 077d: fconst_0
      // 077e: fconst_0
      // 077f: fconst_0
      // 0780: fconst_0
      // 0781: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0784: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0787: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 078a: aastore
      // 078b: dup
      // 078c: bipush 1
      // 078d: new net/minecraft/client/animation/Keyframe
      // 0790: dup
      // 0791: ldc 1.5
      // 0793: ldc 10.0
      // 0795: fconst_0
      // 0796: fconst_0
      // 0797: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 079a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 079d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07a0: aastore
      // 07a1: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 07a4: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 07a7: ldc "left_f_leg_joint"
      // 07a9: new net/minecraft/client/animation/AnimationChannel
      // 07ac: dup
      // 07ad: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 07b0: bipush 1
      // 07b1: anewarray 24
      // 07b4: dup
      // 07b5: bipush 0
      // 07b6: new net/minecraft/client/animation/Keyframe
      // 07b9: dup
      // 07ba: fconst_0
      // 07bb: fconst_0
      // 07bc: fconst_0
      // 07bd: fconst_0
      // 07be: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 07c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07c7: aastore
      // 07c8: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 07cb: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 07ce: ldc "right_f_leg_joint"
      // 07d0: new net/minecraft/client/animation/AnimationChannel
      // 07d3: dup
      // 07d4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 07d7: bipush 2
      // 07d8: anewarray 24
      // 07db: dup
      // 07dc: bipush 0
      // 07dd: new net/minecraft/client/animation/Keyframe
      // 07e0: dup
      // 07e1: fconst_0
      // 07e2: fconst_0
      // 07e3: fconst_0
      // 07e4: fconst_0
      // 07e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 07e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07ee: aastore
      // 07ef: dup
      // 07f0: bipush 1
      // 07f1: new net/minecraft/client/animation/Keyframe
      // 07f4: dup
      // 07f5: ldc 1.5
      // 07f7: ldc -29.1134
      // 07f9: ldc -15.5554
      // 07fb: ldc 3.99
      // 07fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0800: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0803: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0806: aastore
      // 0807: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 080a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 080d: ldc "right_b_leg_joint"
      // 080f: new net/minecraft/client/animation/AnimationChannel
      // 0812: dup
      // 0813: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0816: bipush 2
      // 0817: anewarray 24
      // 081a: dup
      // 081b: bipush 0
      // 081c: new net/minecraft/client/animation/Keyframe
      // 081f: dup
      // 0820: fconst_0
      // 0821: fconst_0
      // 0822: fconst_0
      // 0823: fconst_0
      // 0824: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0827: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 082a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 082d: aastore
      // 082e: dup
      // 082f: bipush 1
      // 0830: new net/minecraft/client/animation/Keyframe
      // 0833: dup
      // 0834: ldc 1.5
      // 0836: ldc 21.9529
      // 0838: ldc -4.9358
      // 083a: ldc -12.6843
      // 083c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 083f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0842: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0845: aastore
      // 0846: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0849: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 084c: ldc "left_b_leg_joint"
      // 084e: new net/minecraft/client/animation/AnimationChannel
      // 0851: dup
      // 0852: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0855: bipush 2
      // 0856: anewarray 24
      // 0859: dup
      // 085a: bipush 0
      // 085b: new net/minecraft/client/animation/Keyframe
      // 085e: dup
      // 085f: fconst_0
      // 0860: fconst_0
      // 0861: fconst_0
      // 0862: fconst_0
      // 0863: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0866: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0869: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 086c: aastore
      // 086d: dup
      // 086e: bipush 1
      // 086f: new net/minecraft/client/animation/Keyframe
      // 0872: dup
      // 0873: ldc 1.5
      // 0875: ldc -12.3237
      // 0877: ldc 34.5622
      // 0879: ldc 2.8772
      // 087b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 087e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0881: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0884: aastore
      // 0885: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0888: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 088b: ldc "left_b_leg"
      // 088d: new net/minecraft/client/animation/AnimationChannel
      // 0890: dup
      // 0891: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0894: bipush 1
      // 0895: anewarray 24
      // 0898: dup
      // 0899: bipush 0
      // 089a: new net/minecraft/client/animation/Keyframe
      // 089d: dup
      // 089e: fconst_0
      // 089f: fconst_0
      // 08a0: fconst_0
      // 08a1: fconst_0
      // 08a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 08a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08ab: aastore
      // 08ac: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 08af: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 08b2: ldc "left_b_leg"
      // 08b4: new net/minecraft/client/animation/AnimationChannel
      // 08b7: dup
      // 08b8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 08bb: bipush 2
      // 08bc: anewarray 24
      // 08bf: dup
      // 08c0: bipush 0
      // 08c1: new net/minecraft/client/animation/Keyframe
      // 08c4: dup
      // 08c5: fconst_0
      // 08c6: fconst_0
      // 08c7: fconst_0
      // 08c8: fconst_0
      // 08c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08d2: aastore
      // 08d3: dup
      // 08d4: bipush 1
      // 08d5: new net/minecraft/client/animation/Keyframe
      // 08d8: dup
      // 08d9: ldc 1.5
      // 08db: ldc 0.0578
      // 08dd: ldc 0.4035
      // 08df: ldc -3.3315
      // 08e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 08e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08ea: aastore
      // 08eb: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 08ee: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 08f1: ldc "left_b_fore_leg"
      // 08f3: new net/minecraft/client/animation/AnimationChannel
      // 08f6: dup
      // 08f7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 08fa: bipush 2
      // 08fb: anewarray 24
      // 08fe: dup
      // 08ff: bipush 0
      // 0900: new net/minecraft/client/animation/Keyframe
      // 0903: dup
      // 0904: fconst_0
      // 0905: fconst_0
      // 0906: fconst_0
      // 0907: fconst_0
      // 0908: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 090b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 090e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0911: aastore
      // 0912: dup
      // 0913: bipush 1
      // 0914: new net/minecraft/client/animation/Keyframe
      // 0917: dup
      // 0918: ldc 1.5
      // 091a: ldc 23.8072
      // 091c: ldc 0.6087
      // 091e: ldc -28.9939
      // 0920: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0923: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0926: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0929: aastore
      // 092a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 092d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0930: ldc "right_down_antenna"
      // 0932: new net/minecraft/client/animation/AnimationChannel
      // 0935: dup
      // 0936: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0939: bipush 3
      // 093a: anewarray 24
      // 093d: dup
      // 093e: bipush 0
      // 093f: new net/minecraft/client/animation/Keyframe
      // 0942: dup
      // 0943: fconst_0
      // 0944: fconst_0
      // 0945: fconst_0
      // 0946: fconst_0
      // 0947: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 094a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 094d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0950: aastore
      // 0951: dup
      // 0952: bipush 1
      // 0953: new net/minecraft/client/animation/Keyframe
      // 0956: dup
      // 0957: ldc 0.9583
      // 0959: ldc -7.5
      // 095b: fconst_0
      // 095c: fconst_0
      // 095d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0960: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0963: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0966: aastore
      // 0967: dup
      // 0968: bipush 2
      // 0969: new net/minecraft/client/animation/Keyframe
      // 096c: dup
      // 096d: ldc 1.5
      // 096f: fconst_0
      // 0970: fconst_0
      // 0971: fconst_0
      // 0972: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0975: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0978: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 097b: aastore
      // 097c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 097f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0982: ldc "left_down_antenna"
      // 0984: new net/minecraft/client/animation/AnimationChannel
      // 0987: dup
      // 0988: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 098b: bipush 3
      // 098c: anewarray 24
      // 098f: dup
      // 0990: bipush 0
      // 0991: new net/minecraft/client/animation/Keyframe
      // 0994: dup
      // 0995: fconst_0
      // 0996: fconst_0
      // 0997: fconst_0
      // 0998: fconst_0
      // 0999: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 099c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 099f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09a2: aastore
      // 09a3: dup
      // 09a4: bipush 1
      // 09a5: new net/minecraft/client/animation/Keyframe
      // 09a8: dup
      // 09a9: ldc 0.9583
      // 09ab: ldc -7.5
      // 09ad: fconst_0
      // 09ae: fconst_0
      // 09af: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 09b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09b8: aastore
      // 09b9: dup
      // 09ba: bipush 2
      // 09bb: new net/minecraft/client/animation/Keyframe
      // 09be: dup
      // 09bf: ldc 1.5
      // 09c1: fconst_0
      // 09c2: fconst_0
      // 09c3: fconst_0
      // 09c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 09c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09cd: aastore
      // 09ce: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 09d1: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 09d4: ldc "right_front_arm_rotator"
      // 09d6: new net/minecraft/client/animation/AnimationChannel
      // 09d9: dup
      // 09da: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 09dd: bipush 1
      // 09de: anewarray 24
      // 09e1: dup
      // 09e2: bipush 0
      // 09e3: new net/minecraft/client/animation/Keyframe
      // 09e6: dup
      // 09e7: fconst_0
      // 09e8: fconst_0
      // 09e9: fconst_0
      // 09ea: fconst_0
      // 09eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 09ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09f4: aastore
      // 09f5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 09f8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 09fb: ldc "legs"
      // 09fd: new net/minecraft/client/animation/AnimationChannel
      // 0a00: dup
      // 0a01: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0a04: bipush 2
      // 0a05: anewarray 24
      // 0a08: dup
      // 0a09: bipush 0
      // 0a0a: new net/minecraft/client/animation/Keyframe
      // 0a0d: dup
      // 0a0e: fconst_0
      // 0a0f: fconst_0
      // 0a10: fconst_0
      // 0a11: fconst_0
      // 0a12: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0a15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a1b: aastore
      // 0a1c: dup
      // 0a1d: bipush 1
      // 0a1e: new net/minecraft/client/animation/Keyframe
      // 0a21: dup
      // 0a22: ldc 1.5
      // 0a24: ldc -14.9042
      // 0a26: ldc -4.8876
      // 0a28: ldc -1.4153
      // 0a2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0a2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a33: aastore
      // 0a34: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0a37: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0a3a: ldc "legs"
      // 0a3c: new net/minecraft/client/animation/AnimationChannel
      // 0a3f: dup
      // 0a40: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0a43: bipush 1
      // 0a44: anewarray 24
      // 0a47: dup
      // 0a48: bipush 0
      // 0a49: new net/minecraft/client/animation/Keyframe
      // 0a4c: dup
      // 0a4d: fconst_0
      // 0a4e: fconst_0
      // 0a4f: fconst_0
      // 0a50: fconst_0
      // 0a51: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a5a: aastore
      // 0a5b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0a5e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0a61: ldc "head_tail"
      // 0a63: new net/minecraft/client/animation/AnimationChannel
      // 0a66: dup
      // 0a67: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0a6a: bipush 1
      // 0a6b: anewarray 24
      // 0a6e: dup
      // 0a6f: bipush 0
      // 0a70: new net/minecraft/client/animation/Keyframe
      // 0a73: dup
      // 0a74: fconst_0
      // 0a75: fconst_0
      // 0a76: fconst_0
      // 0a77: fconst_0
      // 0a78: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0a7b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a7e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a81: aastore
      // 0a82: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0a85: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0a88: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 0a8b: putstatic com/github/L_Ender/cataclysm/client/animation/Clawdian_Skill_Animation.CHARGE_READY Lnet/minecraft/client/animation/AnimationDefinition;
      // 0a8e: ldc 1.125
      // 0a90: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0a93: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.looping ()Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0a96: ldc "mid_root"
      // 0a98: new net/minecraft/client/animation/AnimationChannel
      // 0a9b: dup
      // 0a9c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0a9f: bipush 4
      // 0aa0: anewarray 24
      // 0aa3: dup
      // 0aa4: bipush 0
      // 0aa5: new net/minecraft/client/animation/Keyframe
      // 0aa8: dup
      // 0aa9: fconst_0
      // 0aaa: ldc 20.0
      // 0aac: ldc 30.0
      // 0aae: fconst_0
      // 0aaf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ab2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ab5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ab8: aastore
      // 0ab9: dup
      // 0aba: bipush 1
      // 0abb: new net/minecraft/client/animation/Keyframe
      // 0abe: dup
      // 0abf: ldc 0.375
      // 0ac1: ldc 20.0
      // 0ac3: ldc 30.0
      // 0ac5: fconst_0
      // 0ac6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ac9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0acc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0acf: aastore
      // 0ad0: dup
      // 0ad1: bipush 2
      // 0ad2: new net/minecraft/client/animation/Keyframe
      // 0ad5: dup
      // 0ad6: ldc 0.75
      // 0ad8: ldc 20.0
      // 0ada: ldc 30.0
      // 0adc: fconst_0
      // 0add: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ae0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ae3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ae6: aastore
      // 0ae7: dup
      // 0ae8: bipush 3
      // 0ae9: new net/minecraft/client/animation/Keyframe
      // 0aec: dup
      // 0aed: ldc 1.125
      // 0aef: ldc 20.0
      // 0af1: ldc 30.0
      // 0af3: fconst_0
      // 0af4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0af7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0afa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0afd: aastore
      // 0afe: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0b01: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0b04: ldc "mid_root"
      // 0b06: new net/minecraft/client/animation/AnimationChannel
      // 0b09: dup
      // 0b0a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0b0d: bipush 4
      // 0b0e: anewarray 24
      // 0b11: dup
      // 0b12: bipush 0
      // 0b13: new net/minecraft/client/animation/Keyframe
      // 0b16: dup
      // 0b17: fconst_0
      // 0b18: fconst_0
      // 0b19: ldc 0.5
      // 0b1b: fconst_0
      // 0b1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b25: aastore
      // 0b26: dup
      // 0b27: bipush 1
      // 0b28: new net/minecraft/client/animation/Keyframe
      // 0b2b: dup
      // 0b2c: ldc 0.375
      // 0b2e: fconst_0
      // 0b2f: ldc 0.5
      // 0b31: fconst_0
      // 0b32: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b35: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b38: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b3b: aastore
      // 0b3c: dup
      // 0b3d: bipush 2
      // 0b3e: new net/minecraft/client/animation/Keyframe
      // 0b41: dup
      // 0b42: ldc 0.75
      // 0b44: fconst_0
      // 0b45: ldc 0.5
      // 0b47: fconst_0
      // 0b48: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b4b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b4e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b51: aastore
      // 0b52: dup
      // 0b53: bipush 3
      // 0b54: new net/minecraft/client/animation/Keyframe
      // 0b57: dup
      // 0b58: ldc 1.125
      // 0b5a: fconst_0
      // 0b5b: ldc 0.5
      // 0b5d: fconst_0
      // 0b5e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b61: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b64: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b67: aastore
      // 0b68: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0b6b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0b6e: ldc "lower_body"
      // 0b70: new net/minecraft/client/animation/AnimationChannel
      // 0b73: dup
      // 0b74: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0b77: bipush 7
      // 0b79: anewarray 24
      // 0b7c: dup
      // 0b7d: bipush 0
      // 0b7e: new net/minecraft/client/animation/Keyframe
      // 0b81: dup
      // 0b82: fconst_0
      // 0b83: fconst_0
      // 0b84: ldc -30.0
      // 0b86: fconst_0
      // 0b87: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b8a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b8d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b90: aastore
      // 0b91: dup
      // 0b92: bipush 1
      // 0b93: new net/minecraft/client/animation/Keyframe
      // 0b96: dup
      // 0b97: ldc 0.2083
      // 0b99: fconst_0
      // 0b9a: ldc -45.0
      // 0b9c: fconst_0
      // 0b9d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ba0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ba3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ba6: aastore
      // 0ba7: dup
      // 0ba8: bipush 2
      // 0ba9: new net/minecraft/client/animation/Keyframe
      // 0bac: dup
      // 0bad: ldc 0.375
      // 0baf: fconst_0
      // 0bb0: ldc -30.0
      // 0bb2: fconst_0
      // 0bb3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bb6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bb9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bbc: aastore
      // 0bbd: dup
      // 0bbe: bipush 3
      // 0bbf: new net/minecraft/client/animation/Keyframe
      // 0bc2: dup
      // 0bc3: ldc 0.5833
      // 0bc5: fconst_0
      // 0bc6: ldc -45.0
      // 0bc8: fconst_0
      // 0bc9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bcc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bcf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bd2: aastore
      // 0bd3: dup
      // 0bd4: bipush 4
      // 0bd5: new net/minecraft/client/animation/Keyframe
      // 0bd8: dup
      // 0bd9: ldc 0.75
      // 0bdb: fconst_0
      // 0bdc: ldc -30.0
      // 0bde: fconst_0
      // 0bdf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0be2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0be5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0be8: aastore
      // 0be9: dup
      // 0bea: bipush 5
      // 0beb: new net/minecraft/client/animation/Keyframe
      // 0bee: dup
      // 0bef: ldc 0.9583
      // 0bf1: fconst_0
      // 0bf2: ldc -45.0
      // 0bf4: fconst_0
      // 0bf5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0bf8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bfb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bfe: aastore
      // 0bff: dup
      // 0c00: bipush 6
      // 0c02: new net/minecraft/client/animation/Keyframe
      // 0c05: dup
      // 0c06: ldc 1.125
      // 0c08: fconst_0
      // 0c09: ldc -30.0
      // 0c0b: fconst_0
      // 0c0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c15: aastore
      // 0c16: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0c19: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0c1c: ldc "lower_body"
      // 0c1e: new net/minecraft/client/animation/AnimationChannel
      // 0c21: dup
      // 0c22: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0c25: bipush 2
      // 0c26: anewarray 24
      // 0c29: dup
      // 0c2a: bipush 0
      // 0c2b: new net/minecraft/client/animation/Keyframe
      // 0c2e: dup
      // 0c2f: fconst_0
      // 0c30: fconst_0
      // 0c31: fconst_0
      // 0c32: fconst_0
      // 0c33: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c36: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c39: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c3c: aastore
      // 0c3d: dup
      // 0c3e: bipush 1
      // 0c3f: new net/minecraft/client/animation/Keyframe
      // 0c42: dup
      // 0c43: ldc 1.125
      // 0c45: fconst_0
      // 0c46: fconst_0
      // 0c47: fconst_0
      // 0c48: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0c4b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c4e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c51: aastore
      // 0c52: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0c55: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0c58: ldc "pelvis"
      // 0c5a: new net/minecraft/client/animation/AnimationChannel
      // 0c5d: dup
      // 0c5e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0c61: bipush 7
      // 0c63: anewarray 24
      // 0c66: dup
      // 0c67: bipush 0
      // 0c68: new net/minecraft/client/animation/Keyframe
      // 0c6b: dup
      // 0c6c: fconst_0
      // 0c6d: ldc 51.9151
      // 0c6f: ldc 8.1763
      // 0c71: ldc 12.1632
      // 0c73: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c76: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c79: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c7c: aastore
      // 0c7d: dup
      // 0c7e: bipush 1
      // 0c7f: new net/minecraft/client/animation/Keyframe
      // 0c82: dup
      // 0c83: ldc 0.2083
      // 0c85: ldc 52.4397
      // 0c87: ldc 16.8671
      // 0c89: ldc -0.2762
      // 0c8b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0c8e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c91: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c94: aastore
      // 0c95: dup
      // 0c96: bipush 2
      // 0c97: new net/minecraft/client/animation/Keyframe
      // 0c9a: dup
      // 0c9b: ldc 0.375
      // 0c9d: ldc 51.9151
      // 0c9f: ldc 8.1763
      // 0ca1: ldc 12.1632
      // 0ca3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ca6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ca9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cac: aastore
      // 0cad: dup
      // 0cae: bipush 3
      // 0caf: new net/minecraft/client/animation/Keyframe
      // 0cb2: dup
      // 0cb3: ldc 0.5833
      // 0cb5: ldc 52.4397
      // 0cb7: ldc 16.8671
      // 0cb9: ldc -0.2762
      // 0cbb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cbe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cc1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cc4: aastore
      // 0cc5: dup
      // 0cc6: bipush 4
      // 0cc7: new net/minecraft/client/animation/Keyframe
      // 0cca: dup
      // 0ccb: ldc 0.75
      // 0ccd: ldc 51.9151
      // 0ccf: ldc 8.1763
      // 0cd1: ldc 12.1632
      // 0cd3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cd6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cd9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cdc: aastore
      // 0cdd: dup
      // 0cde: bipush 5
      // 0cdf: new net/minecraft/client/animation/Keyframe
      // 0ce2: dup
      // 0ce3: ldc 0.9583
      // 0ce5: ldc 52.4397
      // 0ce7: ldc 16.8671
      // 0ce9: ldc -0.2762
      // 0ceb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cf1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cf4: aastore
      // 0cf5: dup
      // 0cf6: bipush 6
      // 0cf8: new net/minecraft/client/animation/Keyframe
      // 0cfb: dup
      // 0cfc: ldc 1.125
      // 0cfe: ldc 51.9151
      // 0d00: ldc 8.1763
      // 0d02: ldc 12.1632
      // 0d04: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d07: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d0a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d0d: aastore
      // 0d0e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0d11: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0d14: ldc "pelvis"
      // 0d16: new net/minecraft/client/animation/AnimationChannel
      // 0d19: dup
      // 0d1a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0d1d: bipush 10
      // 0d1f: anewarray 24
      // 0d22: dup
      // 0d23: bipush 0
      // 0d24: new net/minecraft/client/animation/Keyframe
      // 0d27: dup
      // 0d28: fconst_0
      // 0d29: fconst_0
      // 0d2a: fconst_0
      // 0d2b: fconst_0
      // 0d2c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0d2f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d32: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d35: aastore
      // 0d36: dup
      // 0d37: bipush 1
      // 0d38: new net/minecraft/client/animation/Keyframe
      // 0d3b: dup
      // 0d3c: ldc 0.1667
      // 0d3e: fconst_0
      // 0d3f: ldc 0.7878
      // 0d41: ldc -0.1389
      // 0d43: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0d46: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d49: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d4c: aastore
      // 0d4d: dup
      // 0d4e: bipush 2
      // 0d4f: new net/minecraft/client/animation/Keyframe
      // 0d52: dup
      // 0d53: ldc 0.2917
      // 0d55: fconst_0
      // 0d56: ldc 0.1006
      // 0d58: ldc -0.0184
      // 0d5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0d5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d63: aastore
      // 0d64: dup
      // 0d65: bipush 3
      // 0d66: new net/minecraft/client/animation/Keyframe
      // 0d69: dup
      // 0d6a: ldc 0.375
      // 0d6c: fconst_0
      // 0d6d: fconst_0
      // 0d6e: fconst_0
      // 0d6f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0d72: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d75: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d78: aastore
      // 0d79: dup
      // 0d7a: bipush 4
      // 0d7b: new net/minecraft/client/animation/Keyframe
      // 0d7e: dup
      // 0d7f: ldc 0.5417
      // 0d81: fconst_0
      // 0d82: ldc 0.7878
      // 0d84: ldc -0.1389
      // 0d86: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0d89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d8f: aastore
      // 0d90: dup
      // 0d91: bipush 5
      // 0d92: new net/minecraft/client/animation/Keyframe
      // 0d95: dup
      // 0d96: ldc 0.6667
      // 0d98: fconst_0
      // 0d99: ldc 0.1006
      // 0d9b: ldc -0.0184
      // 0d9d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0da0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0da3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0da6: aastore
      // 0da7: dup
      // 0da8: bipush 6
      // 0daa: new net/minecraft/client/animation/Keyframe
      // 0dad: dup
      // 0dae: ldc 0.75
      // 0db0: fconst_0
      // 0db1: fconst_0
      // 0db2: fconst_0
      // 0db3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0db6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0db9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dbc: aastore
      // 0dbd: dup
      // 0dbe: bipush 7
      // 0dc0: new net/minecraft/client/animation/Keyframe
      // 0dc3: dup
      // 0dc4: ldc 0.9167
      // 0dc6: fconst_0
      // 0dc7: ldc 0.7878
      // 0dc9: ldc -0.1389
      // 0dcb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0dce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dd1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dd4: aastore
      // 0dd5: dup
      // 0dd6: bipush 8
      // 0dd8: new net/minecraft/client/animation/Keyframe
      // 0ddb: dup
      // 0ddc: ldc 1.0417
      // 0dde: fconst_0
      // 0ddf: ldc 0.1006
      // 0de1: ldc -0.0184
      // 0de3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0de6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0de9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dec: aastore
      // 0ded: dup
      // 0dee: bipush 9
      // 0df0: new net/minecraft/client/animation/Keyframe
      // 0df3: dup
      // 0df4: ldc 1.125
      // 0df6: fconst_0
      // 0df7: fconst_0
      // 0df8: fconst_0
      // 0df9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0dfc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e02: aastore
      // 0e03: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0e06: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0e09: ldc "body"
      // 0e0b: new net/minecraft/client/animation/AnimationChannel
      // 0e0e: dup
      // 0e0f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0e12: bipush 7
      // 0e14: anewarray 24
      // 0e17: dup
      // 0e18: bipush 0
      // 0e19: new net/minecraft/client/animation/Keyframe
      // 0e1c: dup
      // 0e1d: fconst_0
      // 0e1e: ldc -0.1856
      // 0e20: ldc 29.7735
      // 0e22: ldc 3.9792
      // 0e24: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e27: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e2a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e2d: aastore
      // 0e2e: dup
      // 0e2f: bipush 1
      // 0e30: new net/minecraft/client/animation/Keyframe
      // 0e33: dup
      // 0e34: ldc 0.2083
      // 0e36: ldc 2.9829
      // 0e38: ldc 37.2277
      // 0e3a: ldc 11.9231
      // 0e3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e45: aastore
      // 0e46: dup
      // 0e47: bipush 2
      // 0e48: new net/minecraft/client/animation/Keyframe
      // 0e4b: dup
      // 0e4c: ldc 0.375
      // 0e4e: ldc -0.1856
      // 0e50: ldc 29.7735
      // 0e52: ldc 3.9792
      // 0e54: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e57: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e5a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e5d: aastore
      // 0e5e: dup
      // 0e5f: bipush 3
      // 0e60: new net/minecraft/client/animation/Keyframe
      // 0e63: dup
      // 0e64: ldc 0.5833
      // 0e66: ldc 2.9829
      // 0e68: ldc 37.2277
      // 0e6a: ldc 11.9231
      // 0e6c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e6f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e72: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e75: aastore
      // 0e76: dup
      // 0e77: bipush 4
      // 0e78: new net/minecraft/client/animation/Keyframe
      // 0e7b: dup
      // 0e7c: ldc 0.75
      // 0e7e: ldc -0.1856
      // 0e80: ldc 29.7735
      // 0e82: ldc 3.9792
      // 0e84: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e8d: aastore
      // 0e8e: dup
      // 0e8f: bipush 5
      // 0e90: new net/minecraft/client/animation/Keyframe
      // 0e93: dup
      // 0e94: ldc 0.9583
      // 0e96: ldc 2.9829
      // 0e98: ldc 37.2277
      // 0e9a: ldc 11.9231
      // 0e9c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e9f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ea2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ea5: aastore
      // 0ea6: dup
      // 0ea7: bipush 6
      // 0ea9: new net/minecraft/client/animation/Keyframe
      // 0eac: dup
      // 0ead: ldc 1.125
      // 0eaf: ldc -0.1856
      // 0eb1: ldc 29.7735
      // 0eb3: ldc 3.9792
      // 0eb5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0eb8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ebb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ebe: aastore
      // 0ebf: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0ec2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0ec5: ldc "neck"
      // 0ec7: new net/minecraft/client/animation/AnimationChannel
      // 0eca: dup
      // 0ecb: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0ece: bipush 7
      // 0ed0: anewarray 24
      // 0ed3: dup
      // 0ed4: bipush 0
      // 0ed5: new net/minecraft/client/animation/Keyframe
      // 0ed8: dup
      // 0ed9: fconst_0
      // 0eda: ldc 3.4161
      // 0edc: ldc -30.5752
      // 0ede: ldc -6.6815
      // 0ee0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ee3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ee6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ee9: aastore
      // 0eea: dup
      // 0eeb: bipush 1
      // 0eec: new net/minecraft/client/animation/Keyframe
      // 0eef: dup
      // 0ef0: ldc 0.1667
      // 0ef2: ldc 0.0261
      // 0ef4: ldc -26.001
      // 0ef6: ldc -1.8491
      // 0ef8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0efb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0efe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f01: aastore
      // 0f02: dup
      // 0f03: bipush 2
      // 0f04: new net/minecraft/client/animation/Keyframe
      // 0f07: dup
      // 0f08: ldc 0.375
      // 0f0a: ldc 3.4161
      // 0f0c: ldc -30.5752
      // 0f0e: ldc -6.6815
      // 0f10: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f13: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f16: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f19: aastore
      // 0f1a: dup
      // 0f1b: bipush 3
      // 0f1c: new net/minecraft/client/animation/Keyframe
      // 0f1f: dup
      // 0f20: ldc 0.5417
      // 0f22: ldc 0.0261
      // 0f24: ldc -26.001
      // 0f26: ldc -1.8491
      // 0f28: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f2b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f2e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f31: aastore
      // 0f32: dup
      // 0f33: bipush 4
      // 0f34: new net/minecraft/client/animation/Keyframe
      // 0f37: dup
      // 0f38: ldc 0.75
      // 0f3a: ldc 3.4161
      // 0f3c: ldc -30.5752
      // 0f3e: ldc -6.6815
      // 0f40: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f49: aastore
      // 0f4a: dup
      // 0f4b: bipush 5
      // 0f4c: new net/minecraft/client/animation/Keyframe
      // 0f4f: dup
      // 0f50: ldc 0.9167
      // 0f52: ldc 0.0261
      // 0f54: ldc -26.001
      // 0f56: ldc -1.8491
      // 0f58: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f5b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f5e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f61: aastore
      // 0f62: dup
      // 0f63: bipush 6
      // 0f65: new net/minecraft/client/animation/Keyframe
      // 0f68: dup
      // 0f69: ldc 1.125
      // 0f6b: ldc 3.4161
      // 0f6d: ldc -30.5752
      // 0f6f: ldc -6.6815
      // 0f71: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f7a: aastore
      // 0f7b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0f7e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0f81: ldc "head"
      // 0f83: new net/minecraft/client/animation/AnimationChannel
      // 0f86: dup
      // 0f87: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0f8a: bipush 7
      // 0f8c: anewarray 24
      // 0f8f: dup
      // 0f90: bipush 0
      // 0f91: new net/minecraft/client/animation/Keyframe
      // 0f94: dup
      // 0f95: fconst_0
      // 0f96: ldc -69.7576
      // 0f98: ldc -10.4897
      // 0f9a: ldc 1.4281
      // 0f9c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f9f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fa2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fa5: aastore
      // 0fa6: dup
      // 0fa7: bipush 1
      // 0fa8: new net/minecraft/client/animation/Keyframe
      // 0fab: dup
      // 0fac: ldc 0.2083
      // 0fae: ldc -69.5613
      // 0fb0: ldc -6.5079
      // 0fb2: ldc -1.603
      // 0fb4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fb7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fbd: aastore
      // 0fbe: dup
      // 0fbf: bipush 2
      // 0fc0: new net/minecraft/client/animation/Keyframe
      // 0fc3: dup
      // 0fc4: ldc 0.375
      // 0fc6: ldc -69.7576
      // 0fc8: ldc -10.4897
      // 0fca: ldc 1.4281
      // 0fcc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fcf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fd2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fd5: aastore
      // 0fd6: dup
      // 0fd7: bipush 3
      // 0fd8: new net/minecraft/client/animation/Keyframe
      // 0fdb: dup
      // 0fdc: ldc 0.5833
      // 0fde: ldc -69.5613
      // 0fe0: ldc -6.5079
      // 0fe2: ldc -1.603
      // 0fe4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fe7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fed: aastore
      // 0fee: dup
      // 0fef: bipush 4
      // 0ff0: new net/minecraft/client/animation/Keyframe
      // 0ff3: dup
      // 0ff4: ldc 0.75
      // 0ff6: ldc -69.7576
      // 0ff8: ldc -10.4897
      // 0ffa: ldc 1.4281
      // 0ffc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1002: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1005: aastore
      // 1006: dup
      // 1007: bipush 5
      // 1008: new net/minecraft/client/animation/Keyframe
      // 100b: dup
      // 100c: ldc 0.9583
      // 100e: ldc -69.5613
      // 1010: ldc -6.5079
      // 1012: ldc -1.603
      // 1014: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1017: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 101a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 101d: aastore
      // 101e: dup
      // 101f: bipush 6
      // 1021: new net/minecraft/client/animation/Keyframe
      // 1024: dup
      // 1025: ldc 1.125
      // 1027: ldc -69.7576
      // 1029: ldc -10.4897
      // 102b: ldc 1.4281
      // 102d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1030: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1033: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1036: aastore
      // 1037: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 103a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 103d: ldc "left_arm"
      // 103f: new net/minecraft/client/animation/AnimationChannel
      // 1042: dup
      // 1043: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1046: bipush 7
      // 1048: anewarray 24
      // 104b: dup
      // 104c: bipush 0
      // 104d: new net/minecraft/client/animation/Keyframe
      // 1050: dup
      // 1051: fconst_0
      // 1052: ldc -115.5439
      // 1054: ldc 3.0318
      // 1056: ldc 27.3279
      // 1058: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 105b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 105e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1061: aastore
      // 1062: dup
      // 1063: bipush 1
      // 1064: new net/minecraft/client/animation/Keyframe
      // 1067: dup
      // 1068: ldc 0.1667
      // 106a: ldc -119.4763
      // 106c: ldc 7.6439
      // 106e: ldc 25.1212
      // 1070: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1073: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1076: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1079: aastore
      // 107a: dup
      // 107b: bipush 2
      // 107c: new net/minecraft/client/animation/Keyframe
      // 107f: dup
      // 1080: ldc 0.375
      // 1082: ldc -115.5439
      // 1084: ldc 3.0318
      // 1086: ldc 27.3279
      // 1088: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 108b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 108e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1091: aastore
      // 1092: dup
      // 1093: bipush 3
      // 1094: new net/minecraft/client/animation/Keyframe
      // 1097: dup
      // 1098: ldc 0.5417
      // 109a: ldc -119.4763
      // 109c: ldc 7.6439
      // 109e: ldc 25.1212
      // 10a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10a9: aastore
      // 10aa: dup
      // 10ab: bipush 4
      // 10ac: new net/minecraft/client/animation/Keyframe
      // 10af: dup
      // 10b0: ldc 0.75
      // 10b2: ldc -115.5439
      // 10b4: ldc 3.0318
      // 10b6: ldc 27.3279
      // 10b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10c1: aastore
      // 10c2: dup
      // 10c3: bipush 5
      // 10c4: new net/minecraft/client/animation/Keyframe
      // 10c7: dup
      // 10c8: ldc 0.9167
      // 10ca: ldc -119.4763
      // 10cc: ldc 7.6439
      // 10ce: ldc 25.1212
      // 10d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10d9: aastore
      // 10da: dup
      // 10db: bipush 6
      // 10dd: new net/minecraft/client/animation/Keyframe
      // 10e0: dup
      // 10e1: ldc 1.125
      // 10e3: ldc -115.5439
      // 10e5: ldc 3.0318
      // 10e7: ldc 27.3279
      // 10e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10f2: aastore
      // 10f3: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 10f6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 10f9: ldc "left_front_arm"
      // 10fb: new net/minecraft/client/animation/AnimationChannel
      // 10fe: dup
      // 10ff: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1102: bipush 4
      // 1103: anewarray 24
      // 1106: dup
      // 1107: bipush 0
      // 1108: new net/minecraft/client/animation/Keyframe
      // 110b: dup
      // 110c: fconst_0
      // 110d: ldc 17.0
      // 110f: fconst_0
      // 1110: ldc 62.5
      // 1112: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1115: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1118: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 111b: aastore
      // 111c: dup
      // 111d: bipush 1
      // 111e: new net/minecraft/client/animation/Keyframe
      // 1121: dup
      // 1122: ldc 0.375
      // 1124: ldc 17.0
      // 1126: fconst_0
      // 1127: ldc 62.5
      // 1129: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 112c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 112f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1132: aastore
      // 1133: dup
      // 1134: bipush 2
      // 1135: new net/minecraft/client/animation/Keyframe
      // 1138: dup
      // 1139: ldc 0.75
      // 113b: ldc 17.0
      // 113d: fconst_0
      // 113e: ldc 62.5
      // 1140: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1143: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1146: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1149: aastore
      // 114a: dup
      // 114b: bipush 3
      // 114c: new net/minecraft/client/animation/Keyframe
      // 114f: dup
      // 1150: ldc 1.125
      // 1152: ldc 17.0
      // 1154: fconst_0
      // 1155: ldc 62.5
      // 1157: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 115a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 115d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1160: aastore
      // 1161: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1164: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1167: ldc "left_claw"
      // 1169: new net/minecraft/client/animation/AnimationChannel
      // 116c: dup
      // 116d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1170: bipush 4
      // 1171: anewarray 24
      // 1174: dup
      // 1175: bipush 0
      // 1176: new net/minecraft/client/animation/Keyframe
      // 1179: dup
      // 117a: fconst_0
      // 117b: ldc 25.0
      // 117d: fconst_0
      // 117e: fconst_0
      // 117f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1182: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1185: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1188: aastore
      // 1189: dup
      // 118a: bipush 1
      // 118b: new net/minecraft/client/animation/Keyframe
      // 118e: dup
      // 118f: ldc 0.375
      // 1191: ldc 25.0
      // 1193: fconst_0
      // 1194: fconst_0
      // 1195: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1198: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 119b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 119e: aastore
      // 119f: dup
      // 11a0: bipush 2
      // 11a1: new net/minecraft/client/animation/Keyframe
      // 11a4: dup
      // 11a5: ldc 0.75
      // 11a7: ldc 25.0
      // 11a9: fconst_0
      // 11aa: fconst_0
      // 11ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11b4: aastore
      // 11b5: dup
      // 11b6: bipush 3
      // 11b7: new net/minecraft/client/animation/Keyframe
      // 11ba: dup
      // 11bb: ldc 1.125
      // 11bd: ldc 25.0
      // 11bf: fconst_0
      // 11c0: fconst_0
      // 11c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11ca: aastore
      // 11cb: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 11ce: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 11d1: ldc "right_arm"
      // 11d3: new net/minecraft/client/animation/AnimationChannel
      // 11d6: dup
      // 11d7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 11da: bipush 2
      // 11db: anewarray 24
      // 11de: dup
      // 11df: bipush 0
      // 11e0: new net/minecraft/client/animation/Keyframe
      // 11e3: dup
      // 11e4: fconst_0
      // 11e5: fconst_0
      // 11e6: fconst_0
      // 11e7: fconst_0
      // 11e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11f1: aastore
      // 11f2: dup
      // 11f3: bipush 1
      // 11f4: new net/minecraft/client/animation/Keyframe
      // 11f7: dup
      // 11f8: ldc 1.125
      // 11fa: fconst_0
      // 11fb: fconst_0
      // 11fc: fconst_0
      // 11fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1200: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1203: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1206: aastore
      // 1207: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 120a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 120d: ldc "right_arm"
      // 120f: new net/minecraft/client/animation/AnimationChannel
      // 1212: dup
      // 1213: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1216: bipush 10
      // 1218: anewarray 24
      // 121b: dup
      // 121c: bipush 0
      // 121d: new net/minecraft/client/animation/Keyframe
      // 1220: dup
      // 1221: fconst_0
      // 1222: fconst_0
      // 1223: fconst_0
      // 1224: fconst_0
      // 1225: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1228: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 122b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 122e: aastore
      // 122f: dup
      // 1230: bipush 1
      // 1231: new net/minecraft/client/animation/Keyframe
      // 1234: dup
      // 1235: ldc 0.1667
      // 1237: ldc -0.0144
      // 1239: ldc 0.7772
      // 123b: ldc -0.1898
      // 123d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1240: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1243: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1246: aastore
      // 1247: dup
      // 1248: bipush 2
      // 1249: new net/minecraft/client/animation/Keyframe
      // 124c: dup
      // 124d: ldc 0.2917
      // 124f: ldc -0.0052
      // 1251: ldc -0.1573
      // 1253: ldc 0.0635
      // 1255: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1258: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 125b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 125e: aastore
      // 125f: dup
      // 1260: bipush 3
      // 1261: new net/minecraft/client/animation/Keyframe
      // 1264: dup
      // 1265: ldc 0.375
      // 1267: fconst_0
      // 1268: fconst_0
      // 1269: fconst_0
      // 126a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 126d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1270: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1273: aastore
      // 1274: dup
      // 1275: bipush 4
      // 1276: new net/minecraft/client/animation/Keyframe
      // 1279: dup
      // 127a: ldc 0.5417
      // 127c: ldc -0.0144
      // 127e: ldc 0.7772
      // 1280: ldc -0.1898
      // 1282: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1285: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1288: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 128b: aastore
      // 128c: dup
      // 128d: bipush 5
      // 128e: new net/minecraft/client/animation/Keyframe
      // 1291: dup
      // 1292: ldc 0.6667
      // 1294: ldc -0.0052
      // 1296: ldc -0.1573
      // 1298: ldc 0.0635
      // 129a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 129d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12a3: aastore
      // 12a4: dup
      // 12a5: bipush 6
      // 12a7: new net/minecraft/client/animation/Keyframe
      // 12aa: dup
      // 12ab: ldc 0.75
      // 12ad: fconst_0
      // 12ae: fconst_0
      // 12af: fconst_0
      // 12b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 12b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12b9: aastore
      // 12ba: dup
      // 12bb: bipush 7
      // 12bd: new net/minecraft/client/animation/Keyframe
      // 12c0: dup
      // 12c1: ldc 0.9167
      // 12c3: ldc -0.0144
      // 12c5: ldc 0.7772
      // 12c7: ldc -0.1898
      // 12c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 12cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12d2: aastore
      // 12d3: dup
      // 12d4: bipush 8
      // 12d6: new net/minecraft/client/animation/Keyframe
      // 12d9: dup
      // 12da: ldc 1.0417
      // 12dc: ldc -0.0052
      // 12de: ldc -0.1573
      // 12e0: ldc 0.0635
      // 12e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 12e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12eb: aastore
      // 12ec: dup
      // 12ed: bipush 9
      // 12ef: new net/minecraft/client/animation/Keyframe
      // 12f2: dup
      // 12f3: ldc 1.125
      // 12f5: fconst_0
      // 12f6: fconst_0
      // 12f7: fconst_0
      // 12f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 12fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1301: aastore
      // 1302: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1305: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1308: ldc "right_front_arm"
      // 130a: new net/minecraft/client/animation/AnimationChannel
      // 130d: dup
      // 130e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1311: bipush 7
      // 1313: anewarray 24
      // 1316: dup
      // 1317: bipush 0
      // 1318: new net/minecraft/client/animation/Keyframe
      // 131b: dup
      // 131c: fconst_0
      // 131d: fconst_0
      // 131e: fconst_0
      // 131f: fconst_0
      // 1320: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1323: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1326: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1329: aastore
      // 132a: dup
      // 132b: bipush 1
      // 132c: new net/minecraft/client/animation/Keyframe
      // 132f: dup
      // 1330: ldc 0.2083
      // 1332: ldc -2.0
      // 1334: fconst_0
      // 1335: fconst_0
      // 1336: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1339: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 133c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 133f: aastore
      // 1340: dup
      // 1341: bipush 2
      // 1342: new net/minecraft/client/animation/Keyframe
      // 1345: dup
      // 1346: ldc 0.375
      // 1348: fconst_0
      // 1349: fconst_0
      // 134a: fconst_0
      // 134b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 134e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1351: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1354: aastore
      // 1355: dup
      // 1356: bipush 3
      // 1357: new net/minecraft/client/animation/Keyframe
      // 135a: dup
      // 135b: ldc 0.5833
      // 135d: ldc -2.0
      // 135f: fconst_0
      // 1360: fconst_0
      // 1361: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1364: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1367: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 136a: aastore
      // 136b: dup
      // 136c: bipush 4
      // 136d: new net/minecraft/client/animation/Keyframe
      // 1370: dup
      // 1371: ldc 0.75
      // 1373: fconst_0
      // 1374: fconst_0
      // 1375: fconst_0
      // 1376: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1379: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 137c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 137f: aastore
      // 1380: dup
      // 1381: bipush 5
      // 1382: new net/minecraft/client/animation/Keyframe
      // 1385: dup
      // 1386: ldc 0.9583
      // 1388: ldc -2.0
      // 138a: fconst_0
      // 138b: fconst_0
      // 138c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 138f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1392: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1395: aastore
      // 1396: dup
      // 1397: bipush 6
      // 1399: new net/minecraft/client/animation/Keyframe
      // 139c: dup
      // 139d: ldc 1.125
      // 139f: fconst_0
      // 13a0: fconst_0
      // 13a1: fconst_0
      // 13a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13ab: aastore
      // 13ac: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 13af: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 13b2: ldc "right_claw"
      // 13b4: new net/minecraft/client/animation/AnimationChannel
      // 13b7: dup
      // 13b8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 13bb: bipush 4
      // 13bc: anewarray 24
      // 13bf: dup
      // 13c0: bipush 0
      // 13c1: new net/minecraft/client/animation/Keyframe
      // 13c4: dup
      // 13c5: fconst_0
      // 13c6: fconst_0
      // 13c7: fconst_0
      // 13c8: fconst_0
      // 13c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13d2: aastore
      // 13d3: dup
      // 13d4: bipush 1
      // 13d5: new net/minecraft/client/animation/Keyframe
      // 13d8: dup
      // 13d9: ldc 0.375
      // 13db: fconst_0
      // 13dc: fconst_0
      // 13dd: fconst_0
      // 13de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13e7: aastore
      // 13e8: dup
      // 13e9: bipush 2
      // 13ea: new net/minecraft/client/animation/Keyframe
      // 13ed: dup
      // 13ee: ldc 0.75
      // 13f0: fconst_0
      // 13f1: fconst_0
      // 13f2: fconst_0
      // 13f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13fc: aastore
      // 13fd: dup
      // 13fe: bipush 3
      // 13ff: new net/minecraft/client/animation/Keyframe
      // 1402: dup
      // 1403: ldc 1.125
      // 1405: fconst_0
      // 1406: fconst_0
      // 1407: fconst_0
      // 1408: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 140b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 140e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1411: aastore
      // 1412: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1415: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1418: ldc "right_hammer"
      // 141a: new net/minecraft/client/animation/AnimationChannel
      // 141d: dup
      // 141e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1421: bipush 7
      // 1423: anewarray 24
      // 1426: dup
      // 1427: bipush 0
      // 1428: new net/minecraft/client/animation/Keyframe
      // 142b: dup
      // 142c: fconst_0
      // 142d: ldc 5.0
      // 142f: fconst_0
      // 1430: fconst_0
      // 1431: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1434: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1437: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 143a: aastore
      // 143b: dup
      // 143c: bipush 1
      // 143d: new net/minecraft/client/animation/Keyframe
      // 1440: dup
      // 1441: ldc 0.25
      // 1443: ldc 10.0
      // 1445: fconst_0
      // 1446: fconst_0
      // 1447: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 144a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 144d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1450: aastore
      // 1451: dup
      // 1452: bipush 2
      // 1453: new net/minecraft/client/animation/Keyframe
      // 1456: dup
      // 1457: ldc 0.375
      // 1459: ldc 5.0
      // 145b: fconst_0
      // 145c: fconst_0
      // 145d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1460: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1463: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1466: aastore
      // 1467: dup
      // 1468: bipush 3
      // 1469: new net/minecraft/client/animation/Keyframe
      // 146c: dup
      // 146d: ldc 0.625
      // 146f: ldc 10.0
      // 1471: fconst_0
      // 1472: fconst_0
      // 1473: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1476: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1479: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 147c: aastore
      // 147d: dup
      // 147e: bipush 4
      // 147f: new net/minecraft/client/animation/Keyframe
      // 1482: dup
      // 1483: ldc 0.75
      // 1485: ldc 5.0
      // 1487: fconst_0
      // 1488: fconst_0
      // 1489: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 148c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 148f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1492: aastore
      // 1493: dup
      // 1494: bipush 5
      // 1495: new net/minecraft/client/animation/Keyframe
      // 1498: dup
      // 1499: fconst_1
      // 149a: ldc 10.0
      // 149c: fconst_0
      // 149d: fconst_0
      // 149e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14a7: aastore
      // 14a8: dup
      // 14a9: bipush 6
      // 14ab: new net/minecraft/client/animation/Keyframe
      // 14ae: dup
      // 14af: ldc 1.125
      // 14b1: ldc 5.0
      // 14b3: fconst_0
      // 14b4: fconst_0
      // 14b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14be: aastore
      // 14bf: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 14c2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 14c5: ldc "right_hammer"
      // 14c7: new net/minecraft/client/animation/AnimationChannel
      // 14ca: dup
      // 14cb: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 14ce: bipush 4
      // 14cf: anewarray 24
      // 14d2: dup
      // 14d3: bipush 0
      // 14d4: new net/minecraft/client/animation/Keyframe
      // 14d7: dup
      // 14d8: fconst_0
      // 14d9: ldc 1.9264
      // 14db: ldc -0.5482
      // 14dd: ldc 1.0948
      // 14df: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 14e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14e8: aastore
      // 14e9: dup
      // 14ea: bipush 1
      // 14eb: new net/minecraft/client/animation/Keyframe
      // 14ee: dup
      // 14ef: ldc 0.375
      // 14f1: ldc 1.9264
      // 14f3: ldc -0.5482
      // 14f5: ldc 1.0948
      // 14f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 14fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1500: aastore
      // 1501: dup
      // 1502: bipush 2
      // 1503: new net/minecraft/client/animation/Keyframe
      // 1506: dup
      // 1507: ldc 0.75
      // 1509: ldc 1.9264
      // 150b: ldc -0.5482
      // 150d: ldc 1.0948
      // 150f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1512: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1515: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1518: aastore
      // 1519: dup
      // 151a: bipush 3
      // 151b: new net/minecraft/client/animation/Keyframe
      // 151e: dup
      // 151f: ldc 1.125
      // 1521: ldc 1.9264
      // 1523: ldc -0.5482
      // 1525: ldc 1.0948
      // 1527: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 152a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 152d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1530: aastore
      // 1531: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1534: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1537: ldc "tail1"
      // 1539: new net/minecraft/client/animation/AnimationChannel
      // 153c: dup
      // 153d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1540: bipush 13
      // 1542: anewarray 24
      // 1545: dup
      // 1546: bipush 0
      // 1547: new net/minecraft/client/animation/Keyframe
      // 154a: dup
      // 154b: fconst_0
      // 154c: ldc 2.6061
      // 154e: ldc -14.8782
      // 1550: ldc -0.9806
      // 1552: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1555: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1558: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 155b: aastore
      // 155c: dup
      // 155d: bipush 1
      // 155e: new net/minecraft/client/animation/Keyframe
      // 1561: dup
      // 1562: ldc 0.125
      // 1564: ldc 2.715
      // 1566: ldc 7.5713
      // 1568: ldc_w 0.7421
      // 156b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 156e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1571: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1574: aastore
      // 1575: dup
      // 1576: bipush 2
      // 1577: new net/minecraft/client/animation/Keyframe
      // 157a: dup
      // 157b: ldc 0.2083
      // 157d: ldc 2.6061
      // 157f: ldc_w -0.1074
      // 1582: ldc_w 1.6386
      // 1585: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1588: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 158b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 158e: aastore
      // 158f: dup
      // 1590: bipush 3
      // 1591: new net/minecraft/client/animation/Keyframe
      // 1594: dup
      // 1595: ldc_w 0.3333
      // 1598: ldc 2.715
      // 159a: ldc_w -22.5569
      // 159d: ldc_w -0.084
      // 15a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15a9: aastore
      // 15aa: dup
      // 15ab: bipush 4
      // 15ac: new net/minecraft/client/animation/Keyframe
      // 15af: dup
      // 15b0: ldc 0.375
      // 15b2: ldc 2.6061
      // 15b4: ldc -14.8782
      // 15b6: ldc -0.9806
      // 15b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15c1: aastore
      // 15c2: dup
      // 15c3: bipush 5
      // 15c4: new net/minecraft/client/animation/Keyframe
      // 15c7: dup
      // 15c8: ldc 0.5
      // 15ca: ldc 2.715
      // 15cc: ldc 7.5713
      // 15ce: ldc_w 0.7421
      // 15d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15da: aastore
      // 15db: dup
      // 15dc: bipush 6
      // 15de: new net/minecraft/client/animation/Keyframe
      // 15e1: dup
      // 15e2: ldc 0.5833
      // 15e4: ldc 2.6061
      // 15e6: ldc_w -0.1074
      // 15e9: ldc_w 1.6386
      // 15ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15f5: aastore
      // 15f6: dup
      // 15f7: bipush 7
      // 15f9: new net/minecraft/client/animation/Keyframe
      // 15fc: dup
      // 15fd: ldc_w 0.7083
      // 1600: ldc 2.715
      // 1602: ldc_w -22.5569
      // 1605: ldc_w -0.084
      // 1608: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 160b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 160e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1611: aastore
      // 1612: dup
      // 1613: bipush 8
      // 1615: new net/minecraft/client/animation/Keyframe
      // 1618: dup
      // 1619: ldc 0.75
      // 161b: ldc 2.6061
      // 161d: ldc -14.8782
      // 161f: ldc -0.9806
      // 1621: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1624: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1627: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 162a: aastore
      // 162b: dup
      // 162c: bipush 9
      // 162e: new net/minecraft/client/animation/Keyframe
      // 1631: dup
      // 1632: ldc 0.875
      // 1634: ldc 2.715
      // 1636: ldc 7.5713
      // 1638: ldc_w 0.7421
      // 163b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 163e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1641: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1644: aastore
      // 1645: dup
      // 1646: bipush 10
      // 1648: new net/minecraft/client/animation/Keyframe
      // 164b: dup
      // 164c: ldc 0.9583
      // 164e: ldc 2.6061
      // 1650: ldc_w -0.1074
      // 1653: ldc_w 1.6386
      // 1656: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1659: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 165c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 165f: aastore
      // 1660: dup
      // 1661: bipush 11
      // 1663: new net/minecraft/client/animation/Keyframe
      // 1666: dup
      // 1667: ldc_w 1.0833
      // 166a: ldc 2.715
      // 166c: ldc_w -22.5569
      // 166f: ldc_w -0.084
      // 1672: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1675: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1678: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 167b: aastore
      // 167c: dup
      // 167d: bipush 12
      // 167f: new net/minecraft/client/animation/Keyframe
      // 1682: dup
      // 1683: ldc 1.125
      // 1685: ldc 2.6061
      // 1687: ldc -14.8782
      // 1689: ldc -0.9806
      // 168b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 168e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1691: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1694: aastore
      // 1695: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1698: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 169b: ldc "tail2"
      // 169d: new net/minecraft/client/animation/AnimationChannel
      // 16a0: dup
      // 16a1: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 16a4: bipush 13
      // 16a6: anewarray 24
      // 16a9: dup
      // 16aa: bipush 0
      // 16ab: new net/minecraft/client/animation/Keyframe
      // 16ae: dup
      // 16af: fconst_0
      // 16b0: ldc 7.4506
      // 16b2: ldc -28.2464
      // 16b4: ldc -8.4326
      // 16b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16bf: aastore
      // 16c0: dup
      // 16c1: bipush 1
      // 16c2: new net/minecraft/client/animation/Keyframe
      // 16c5: dup
      // 16c6: ldc 0.125
      // 16c8: ldc_w 7.5595
      // 16cb: ldc_w -5.7969
      // 16ce: ldc_w -6.7099
      // 16d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16da: aastore
      // 16db: dup
      // 16dc: bipush 2
      // 16dd: new net/minecraft/client/animation/Keyframe
      // 16e0: dup
      // 16e1: ldc 0.2083
      // 16e3: ldc 7.4506
      // 16e5: ldc_w -13.4755
      // 16e8: ldc_w -5.8133
      // 16eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16f4: aastore
      // 16f5: dup
      // 16f6: bipush 3
      // 16f7: new net/minecraft/client/animation/Keyframe
      // 16fa: dup
      // 16fb: ldc_w 0.3333
      // 16fe: ldc_w 7.5595
      // 1701: ldc_w -35.9251
      // 1704: ldc_w -7.536
      // 1707: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 170a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 170d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1710: aastore
      // 1711: dup
      // 1712: bipush 4
      // 1713: new net/minecraft/client/animation/Keyframe
      // 1716: dup
      // 1717: ldc 0.375
      // 1719: ldc 7.4506
      // 171b: ldc -28.2464
      // 171d: ldc -8.4326
      // 171f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1722: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1725: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1728: aastore
      // 1729: dup
      // 172a: bipush 5
      // 172b: new net/minecraft/client/animation/Keyframe
      // 172e: dup
      // 172f: ldc 0.5
      // 1731: ldc_w 7.5595
      // 1734: ldc_w -5.7969
      // 1737: ldc_w -6.7099
      // 173a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 173d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1740: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1743: aastore
      // 1744: dup
      // 1745: bipush 6
      // 1747: new net/minecraft/client/animation/Keyframe
      // 174a: dup
      // 174b: ldc 0.5833
      // 174d: ldc 7.4506
      // 174f: ldc_w -13.4755
      // 1752: ldc_w -5.8133
      // 1755: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1758: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 175b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 175e: aastore
      // 175f: dup
      // 1760: bipush 7
      // 1762: new net/minecraft/client/animation/Keyframe
      // 1765: dup
      // 1766: ldc_w 0.7083
      // 1769: ldc_w 7.5595
      // 176c: ldc_w -35.9251
      // 176f: ldc_w -7.536
      // 1772: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1775: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1778: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 177b: aastore
      // 177c: dup
      // 177d: bipush 8
      // 177f: new net/minecraft/client/animation/Keyframe
      // 1782: dup
      // 1783: ldc 0.75
      // 1785: ldc 7.4506
      // 1787: ldc -28.2464
      // 1789: ldc -8.4326
      // 178b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 178e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1791: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1794: aastore
      // 1795: dup
      // 1796: bipush 9
      // 1798: new net/minecraft/client/animation/Keyframe
      // 179b: dup
      // 179c: ldc 0.875
      // 179e: ldc_w 7.5595
      // 17a1: ldc_w -5.7969
      // 17a4: ldc_w -6.7099
      // 17a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17b0: aastore
      // 17b1: dup
      // 17b2: bipush 10
      // 17b4: new net/minecraft/client/animation/Keyframe
      // 17b7: dup
      // 17b8: ldc 0.9583
      // 17ba: ldc 7.4506
      // 17bc: ldc_w -13.4755
      // 17bf: ldc_w -5.8133
      // 17c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17cb: aastore
      // 17cc: dup
      // 17cd: bipush 11
      // 17cf: new net/minecraft/client/animation/Keyframe
      // 17d2: dup
      // 17d3: ldc_w 1.0833
      // 17d6: ldc_w 7.5595
      // 17d9: ldc_w -35.9251
      // 17dc: ldc_w -7.536
      // 17df: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17e8: aastore
      // 17e9: dup
      // 17ea: bipush 12
      // 17ec: new net/minecraft/client/animation/Keyframe
      // 17ef: dup
      // 17f0: ldc 1.125
      // 17f2: ldc 7.4506
      // 17f4: ldc -28.2464
      // 17f6: ldc -8.4326
      // 17f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1801: aastore
      // 1802: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1805: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1808: ldc "left_f_leg"
      // 180a: new net/minecraft/client/animation/AnimationChannel
      // 180d: dup
      // 180e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1811: bipush 4
      // 1812: anewarray 24
      // 1815: dup
      // 1816: bipush 0
      // 1817: new net/minecraft/client/animation/Keyframe
      // 181a: dup
      // 181b: fconst_0
      // 181c: fconst_0
      // 181d: fconst_0
      // 181e: fconst_0
      // 181f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1822: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1825: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1828: aastore
      // 1829: dup
      // 182a: bipush 1
      // 182b: new net/minecraft/client/animation/Keyframe
      // 182e: dup
      // 182f: ldc 0.375
      // 1831: fconst_0
      // 1832: fconst_0
      // 1833: fconst_0
      // 1834: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1837: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 183a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 183d: aastore
      // 183e: dup
      // 183f: bipush 2
      // 1840: new net/minecraft/client/animation/Keyframe
      // 1843: dup
      // 1844: ldc 0.75
      // 1846: fconst_0
      // 1847: fconst_0
      // 1848: fconst_0
      // 1849: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 184c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 184f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1852: aastore
      // 1853: dup
      // 1854: bipush 3
      // 1855: new net/minecraft/client/animation/Keyframe
      // 1858: dup
      // 1859: ldc 1.125
      // 185b: fconst_0
      // 185c: fconst_0
      // 185d: fconst_0
      // 185e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1861: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1864: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1867: aastore
      // 1868: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 186b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 186e: ldc "left_f_leg"
      // 1870: new net/minecraft/client/animation/AnimationChannel
      // 1873: dup
      // 1874: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1877: bipush 13
      // 1879: anewarray 24
      // 187c: dup
      // 187d: bipush 0
      // 187e: new net/minecraft/client/animation/Keyframe
      // 1881: dup
      // 1882: fconst_0
      // 1883: fconst_0
      // 1884: ldc 0.5209
      // 1886: ldc 2.9544
      // 1888: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 188b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 188e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1891: aastore
      // 1892: dup
      // 1893: bipush 1
      // 1894: new net/minecraft/client/animation/Keyframe
      // 1897: dup
      // 1898: ldc_w 0.0833
      // 189b: ldc_w 0.0129
      // 189e: ldc_w 1.3535
      // 18a1: ldc_w 2.8038
      // 18a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 18a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18ad: aastore
      // 18ae: dup
      // 18af: bipush 2
      // 18b0: new net/minecraft/client/animation/Keyframe
      // 18b3: dup
      // 18b4: ldc 0.2083
      // 18b6: fconst_0
      // 18b7: ldc 2.9535
      // 18b9: ldc -0.5254
      // 18bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 18be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18c4: aastore
      // 18c5: dup
      // 18c6: bipush 3
      // 18c7: new net/minecraft/client/animation/Keyframe
      // 18ca: dup
      // 18cb: ldc 0.2917
      // 18cd: fconst_0
      // 18ce: ldc_w 0.5752
      // 18d1: ldc_w 0.4036
      // 18d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 18d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18dd: aastore
      // 18de: dup
      // 18df: bipush 4
      // 18e0: new net/minecraft/client/animation/Keyframe
      // 18e3: dup
      // 18e4: ldc 0.375
      // 18e6: fconst_0
      // 18e7: ldc 0.5209
      // 18e9: ldc 2.9544
      // 18eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 18ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18f4: aastore
      // 18f5: dup
      // 18f6: bipush 5
      // 18f7: new net/minecraft/client/animation/Keyframe
      // 18fa: dup
      // 18fb: ldc_w 0.4583
      // 18fe: ldc_w 0.0129
      // 1901: ldc_w 1.3535
      // 1904: ldc_w 2.8038
      // 1907: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 190a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 190d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1910: aastore
      // 1911: dup
      // 1912: bipush 6
      // 1914: new net/minecraft/client/animation/Keyframe
      // 1917: dup
      // 1918: ldc 0.5833
      // 191a: fconst_0
      // 191b: ldc 2.9535
      // 191d: ldc -0.5254
      // 191f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1922: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1925: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1928: aastore
      // 1929: dup
      // 192a: bipush 7
      // 192c: new net/minecraft/client/animation/Keyframe
      // 192f: dup
      // 1930: ldc 0.6667
      // 1932: fconst_0
      // 1933: ldc_w 0.5752
      // 1936: ldc_w 0.4036
      // 1939: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 193c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 193f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1942: aastore
      // 1943: dup
      // 1944: bipush 8
      // 1946: new net/minecraft/client/animation/Keyframe
      // 1949: dup
      // 194a: ldc 0.75
      // 194c: fconst_0
      // 194d: ldc 0.5209
      // 194f: ldc 2.9544
      // 1951: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1954: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1957: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 195a: aastore
      // 195b: dup
      // 195c: bipush 9
      // 195e: new net/minecraft/client/animation/Keyframe
      // 1961: dup
      // 1962: ldc 0.8333
      // 1964: ldc_w 0.0129
      // 1967: ldc_w 1.3535
      // 196a: ldc_w 2.8038
      // 196d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1970: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1973: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1976: aastore
      // 1977: dup
      // 1978: bipush 10
      // 197a: new net/minecraft/client/animation/Keyframe
      // 197d: dup
      // 197e: ldc 0.9583
      // 1980: fconst_0
      // 1981: ldc 2.9535
      // 1983: ldc -0.5254
      // 1985: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1988: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 198b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 198e: aastore
      // 198f: dup
      // 1990: bipush 11
      // 1992: new net/minecraft/client/animation/Keyframe
      // 1995: dup
      // 1996: ldc 1.0417
      // 1998: fconst_0
      // 1999: ldc_w 0.5752
      // 199c: ldc_w 0.4036
      // 199f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 19a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19a8: aastore
      // 19a9: dup
      // 19aa: bipush 12
      // 19ac: new net/minecraft/client/animation/Keyframe
      // 19af: dup
      // 19b0: ldc 1.125
      // 19b2: fconst_0
      // 19b3: ldc 0.5209
      // 19b5: ldc 2.9544
      // 19b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 19ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19c0: aastore
      // 19c1: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 19c4: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 19c7: ldc "left_f_fore_leg"
      // 19c9: new net/minecraft/client/animation/AnimationChannel
      // 19cc: dup
      // 19cd: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 19d0: bipush 7
      // 19d2: anewarray 24
      // 19d5: dup
      // 19d6: bipush 0
      // 19d7: new net/minecraft/client/animation/Keyframe
      // 19da: dup
      // 19db: fconst_0
      // 19dc: ldc -11.3173
      // 19de: ldc -1.3485
      // 19e0: ldc -29.6357
      // 19e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19eb: aastore
      // 19ec: dup
      // 19ed: bipush 1
      // 19ee: new net/minecraft/client/animation/Keyframe
      // 19f1: dup
      // 19f2: ldc 0.2083
      // 19f4: ldc 22.5
      // 19f6: fconst_0
      // 19f7: ldc 5.0
      // 19f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a02: aastore
      // 1a03: dup
      // 1a04: bipush 2
      // 1a05: new net/minecraft/client/animation/Keyframe
      // 1a08: dup
      // 1a09: ldc 0.375
      // 1a0b: ldc -11.3173
      // 1a0d: ldc -1.3485
      // 1a0f: ldc -29.6357
      // 1a11: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a14: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a17: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a1a: aastore
      // 1a1b: dup
      // 1a1c: bipush 3
      // 1a1d: new net/minecraft/client/animation/Keyframe
      // 1a20: dup
      // 1a21: ldc 0.5833
      // 1a23: ldc 22.5
      // 1a25: fconst_0
      // 1a26: ldc 5.0
      // 1a28: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a2b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a2e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a31: aastore
      // 1a32: dup
      // 1a33: bipush 4
      // 1a34: new net/minecraft/client/animation/Keyframe
      // 1a37: dup
      // 1a38: ldc 0.75
      // 1a3a: ldc -11.3173
      // 1a3c: ldc -1.3485
      // 1a3e: ldc -29.6357
      // 1a40: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a49: aastore
      // 1a4a: dup
      // 1a4b: bipush 5
      // 1a4c: new net/minecraft/client/animation/Keyframe
      // 1a4f: dup
      // 1a50: ldc 0.9583
      // 1a52: ldc 22.5
      // 1a54: fconst_0
      // 1a55: ldc 5.0
      // 1a57: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a5a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a5d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a60: aastore
      // 1a61: dup
      // 1a62: bipush 6
      // 1a64: new net/minecraft/client/animation/Keyframe
      // 1a67: dup
      // 1a68: ldc 1.125
      // 1a6a: ldc -11.3173
      // 1a6c: ldc -1.3485
      // 1a6e: ldc -29.6357
      // 1a70: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a73: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a76: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a79: aastore
      // 1a7a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1a7d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1a80: ldc "right_b_leg"
      // 1a82: new net/minecraft/client/animation/AnimationChannel
      // 1a85: dup
      // 1a86: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1a89: bipush 2
      // 1a8a: anewarray 24
      // 1a8d: dup
      // 1a8e: bipush 0
      // 1a8f: new net/minecraft/client/animation/Keyframe
      // 1a92: dup
      // 1a93: fconst_0
      // 1a94: fconst_0
      // 1a95: fconst_0
      // 1a96: fconst_0
      // 1a97: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a9a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a9d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1aa0: aastore
      // 1aa1: dup
      // 1aa2: bipush 1
      // 1aa3: new net/minecraft/client/animation/Keyframe
      // 1aa6: dup
      // 1aa7: ldc 1.125
      // 1aa9: fconst_0
      // 1aaa: fconst_0
      // 1aab: fconst_0
      // 1aac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1aaf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ab2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ab5: aastore
      // 1ab6: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1ab9: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1abc: ldc "right_b_leg"
      // 1abe: new net/minecraft/client/animation/AnimationChannel
      // 1ac1: dup
      // 1ac2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1ac5: bipush 13
      // 1ac7: anewarray 24
      // 1aca: dup
      // 1acb: bipush 0
      // 1acc: new net/minecraft/client/animation/Keyframe
      // 1acf: dup
      // 1ad0: fconst_0
      // 1ad1: fconst_0
      // 1ad2: ldc 0.1746
      // 1ad4: ldc 0.9892
      // 1ad6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ad9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1adc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1adf: aastore
      // 1ae0: dup
      // 1ae1: bipush 1
      // 1ae2: new net/minecraft/client/animation/Keyframe
      // 1ae5: dup
      // 1ae6: ldc_w 0.0833
      // 1ae9: ldc_w -0.2634
      // 1aec: ldc_w 3.7426
      // 1aef: ldc_w -1.6125
      // 1af2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1af5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1af8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1afb: aastore
      // 1afc: dup
      // 1afd: bipush 2
      // 1afe: new net/minecraft/client/animation/Keyframe
      // 1b01: dup
      // 1b02: ldc 0.2083
      // 1b04: fconst_0
      // 1b05: ldc_w -0.5209
      // 1b08: ldc_w -2.9544
      // 1b0b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b0e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b11: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b14: aastore
      // 1b15: dup
      // 1b16: bipush 3
      // 1b17: new net/minecraft/client/animation/Keyframe
      // 1b1a: dup
      // 1b1b: ldc 0.2917
      // 1b1d: ldc_w -0.03
      // 1b20: ldc_w 0.4563
      // 1b23: ldc_w -0.9956
      // 1b26: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b29: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b2c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b2f: aastore
      // 1b30: dup
      // 1b31: bipush 4
      // 1b32: new net/minecraft/client/animation/Keyframe
      // 1b35: dup
      // 1b36: ldc 0.375
      // 1b38: fconst_0
      // 1b39: ldc 0.1746
      // 1b3b: ldc 0.9892
      // 1b3d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b40: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b43: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b46: aastore
      // 1b47: dup
      // 1b48: bipush 5
      // 1b49: new net/minecraft/client/animation/Keyframe
      // 1b4c: dup
      // 1b4d: ldc_w 0.4583
      // 1b50: ldc_w -0.2634
      // 1b53: ldc_w 3.7426
      // 1b56: ldc_w -1.6125
      // 1b59: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b5c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b5f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b62: aastore
      // 1b63: dup
      // 1b64: bipush 6
      // 1b66: new net/minecraft/client/animation/Keyframe
      // 1b69: dup
      // 1b6a: ldc 0.5833
      // 1b6c: fconst_0
      // 1b6d: ldc_w -0.5209
      // 1b70: ldc_w -2.9544
      // 1b73: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b76: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b79: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b7c: aastore
      // 1b7d: dup
      // 1b7e: bipush 7
      // 1b80: new net/minecraft/client/animation/Keyframe
      // 1b83: dup
      // 1b84: ldc 0.6667
      // 1b86: ldc_w -0.03
      // 1b89: ldc_w 0.4563
      // 1b8c: ldc_w -0.9956
      // 1b8f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1b92: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b95: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b98: aastore
      // 1b99: dup
      // 1b9a: bipush 8
      // 1b9c: new net/minecraft/client/animation/Keyframe
      // 1b9f: dup
      // 1ba0: ldc 0.75
      // 1ba2: fconst_0
      // 1ba3: ldc 0.1746
      // 1ba5: ldc 0.9892
      // 1ba7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1baa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bb0: aastore
      // 1bb1: dup
      // 1bb2: bipush 9
      // 1bb4: new net/minecraft/client/animation/Keyframe
      // 1bb7: dup
      // 1bb8: ldc 0.8333
      // 1bba: ldc_w -0.2634
      // 1bbd: ldc_w 3.7426
      // 1bc0: ldc_w -1.6125
      // 1bc3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1bc6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bc9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bcc: aastore
      // 1bcd: dup
      // 1bce: bipush 10
      // 1bd0: new net/minecraft/client/animation/Keyframe
      // 1bd3: dup
      // 1bd4: ldc 0.9583
      // 1bd6: fconst_0
      // 1bd7: ldc_w -0.5209
      // 1bda: ldc_w -2.9544
      // 1bdd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1be0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1be3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1be6: aastore
      // 1be7: dup
      // 1be8: bipush 11
      // 1bea: new net/minecraft/client/animation/Keyframe
      // 1bed: dup
      // 1bee: ldc 1.0417
      // 1bf0: ldc_w -0.03
      // 1bf3: ldc_w 0.4563
      // 1bf6: ldc_w -0.9956
      // 1bf9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1bfc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c02: aastore
      // 1c03: dup
      // 1c04: bipush 12
      // 1c06: new net/minecraft/client/animation/Keyframe
      // 1c09: dup
      // 1c0a: ldc 1.125
      // 1c0c: fconst_0
      // 1c0d: ldc 0.1746
      // 1c0f: ldc 0.9892
      // 1c11: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c14: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c17: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c1a: aastore
      // 1c1b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1c1e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1c21: ldc "right_b_fore_leg"
      // 1c23: new net/minecraft/client/animation/AnimationChannel
      // 1c26: dup
      // 1c27: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1c2a: bipush 10
      // 1c2c: anewarray 24
      // 1c2f: dup
      // 1c30: bipush 0
      // 1c31: new net/minecraft/client/animation/Keyframe
      // 1c34: dup
      // 1c35: fconst_0
      // 1c36: ldc -13.7142
      // 1c38: ldc 2.6061
      // 1c3a: ldc 27.5327
      // 1c3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c45: aastore
      // 1c46: dup
      // 1c47: bipush 1
      // 1c48: new net/minecraft/client/animation/Keyframe
      // 1c4b: dup
      // 1c4c: ldc_w 0.0833
      // 1c4f: ldc_w 19.4552
      // 1c52: ldc_w 4.0727
      // 1c55: ldc_w 8.9398
      // 1c58: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c5b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c5e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c61: aastore
      // 1c62: dup
      // 1c63: bipush 2
      // 1c64: new net/minecraft/client/animation/Keyframe
      // 1c67: dup
      // 1c68: ldc 0.2083
      // 1c6a: ldc 23.8072
      // 1c6c: ldc_w -0.6087
      // 1c6f: ldc_w 28.9939
      // 1c72: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c7b: aastore
      // 1c7c: dup
      // 1c7d: bipush 3
      // 1c7e: new net/minecraft/client/animation/Keyframe
      // 1c81: dup
      // 1c82: ldc 0.375
      // 1c84: ldc -13.7142
      // 1c86: ldc 2.6061
      // 1c88: ldc 27.5327
      // 1c8a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c8d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c90: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c93: aastore
      // 1c94: dup
      // 1c95: bipush 4
      // 1c96: new net/minecraft/client/animation/Keyframe
      // 1c99: dup
      // 1c9a: ldc_w 0.4583
      // 1c9d: ldc_w 19.4552
      // 1ca0: ldc_w 4.0727
      // 1ca3: ldc_w 8.9398
      // 1ca6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ca9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1caf: aastore
      // 1cb0: dup
      // 1cb1: bipush 5
      // 1cb2: new net/minecraft/client/animation/Keyframe
      // 1cb5: dup
      // 1cb6: ldc 0.5833
      // 1cb8: ldc 23.8072
      // 1cba: ldc_w -0.6087
      // 1cbd: ldc_w 28.9939
      // 1cc0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1cc3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cc6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cc9: aastore
      // 1cca: dup
      // 1ccb: bipush 6
      // 1ccd: new net/minecraft/client/animation/Keyframe
      // 1cd0: dup
      // 1cd1: ldc 0.75
      // 1cd3: ldc -13.7142
      // 1cd5: ldc 2.6061
      // 1cd7: ldc 27.5327
      // 1cd9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1cdc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cdf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ce2: aastore
      // 1ce3: dup
      // 1ce4: bipush 7
      // 1ce6: new net/minecraft/client/animation/Keyframe
      // 1ce9: dup
      // 1cea: ldc 0.8333
      // 1cec: ldc_w 19.4552
      // 1cef: ldc_w 4.0727
      // 1cf2: ldc_w 8.9398
      // 1cf5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1cf8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cfb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cfe: aastore
      // 1cff: dup
      // 1d00: bipush 8
      // 1d02: new net/minecraft/client/animation/Keyframe
      // 1d05: dup
      // 1d06: ldc 0.9583
      // 1d08: ldc 23.8072
      // 1d0a: ldc_w -0.6087
      // 1d0d: ldc_w 28.9939
      // 1d10: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d13: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d16: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d19: aastore
      // 1d1a: dup
      // 1d1b: bipush 9
      // 1d1d: new net/minecraft/client/animation/Keyframe
      // 1d20: dup
      // 1d21: ldc 1.125
      // 1d23: ldc -13.7142
      // 1d25: ldc 2.6061
      // 1d27: ldc 27.5327
      // 1d29: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d2c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d2f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d32: aastore
      // 1d33: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1d36: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1d39: ldc "right_f_leg"
      // 1d3b: new net/minecraft/client/animation/AnimationChannel
      // 1d3e: dup
      // 1d3f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1d42: bipush 2
      // 1d43: anewarray 24
      // 1d46: dup
      // 1d47: bipush 0
      // 1d48: new net/minecraft/client/animation/Keyframe
      // 1d4b: dup
      // 1d4c: fconst_0
      // 1d4d: fconst_0
      // 1d4e: fconst_0
      // 1d4f: fconst_0
      // 1d50: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d53: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d56: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d59: aastore
      // 1d5a: dup
      // 1d5b: bipush 1
      // 1d5c: new net/minecraft/client/animation/Keyframe
      // 1d5f: dup
      // 1d60: ldc 1.125
      // 1d62: fconst_0
      // 1d63: fconst_0
      // 1d64: fconst_0
      // 1d65: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d6e: aastore
      // 1d6f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1d72: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1d75: ldc "right_f_leg"
      // 1d77: new net/minecraft/client/animation/AnimationChannel
      // 1d7a: dup
      // 1d7b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1d7e: bipush 13
      // 1d80: anewarray 24
      // 1d83: dup
      // 1d84: bipush 0
      // 1d85: new net/minecraft/client/animation/Keyframe
      // 1d88: dup
      // 1d89: fconst_0
      // 1d8a: fconst_0
      // 1d8b: ldc 2.9535
      // 1d8d: ldc -0.5254
      // 1d8f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d92: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d95: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d98: aastore
      // 1d99: dup
      // 1d9a: bipush 1
      // 1d9b: new net/minecraft/client/animation/Keyframe
      // 1d9e: dup
      // 1d9f: ldc_w 0.0833
      // 1da2: fconst_0
      // 1da3: ldc_w 0.5752
      // 1da6: ldc_w 0.4036
      // 1da9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1dac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1daf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1db2: aastore
      // 1db3: dup
      // 1db4: bipush 2
      // 1db5: new net/minecraft/client/animation/Keyframe
      // 1db8: dup
      // 1db9: ldc 0.2083
      // 1dbb: fconst_0
      // 1dbc: ldc 0.5209
      // 1dbe: ldc 2.9544
      // 1dc0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1dc3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dc6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1dc9: aastore
      // 1dca: dup
      // 1dcb: bipush 3
      // 1dcc: new net/minecraft/client/animation/Keyframe
      // 1dcf: dup
      // 1dd0: ldc 0.2917
      // 1dd2: ldc_w 0.0093
      // 1dd5: ldc_w 1.3735
      // 1dd8: ldc_w 2.9023
      // 1ddb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1dde: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1de1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1de4: aastore
      // 1de5: dup
      // 1de6: bipush 4
      // 1de7: new net/minecraft/client/animation/Keyframe
      // 1dea: dup
      // 1deb: ldc 0.375
      // 1ded: fconst_0
      // 1dee: ldc 2.9535
      // 1df0: ldc -0.5254
      // 1df2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1df5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1df8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1dfb: aastore
      // 1dfc: dup
      // 1dfd: bipush 5
      // 1dfe: new net/minecraft/client/animation/Keyframe
      // 1e01: dup
      // 1e02: ldc_w 0.4583
      // 1e05: fconst_0
      // 1e06: ldc_w 0.5752
      // 1e09: ldc_w 0.4036
      // 1e0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e15: aastore
      // 1e16: dup
      // 1e17: bipush 6
      // 1e19: new net/minecraft/client/animation/Keyframe
      // 1e1c: dup
      // 1e1d: ldc 0.5833
      // 1e1f: fconst_0
      // 1e20: ldc 0.5209
      // 1e22: ldc 2.9544
      // 1e24: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e27: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e2a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e2d: aastore
      // 1e2e: dup
      // 1e2f: bipush 7
      // 1e31: new net/minecraft/client/animation/Keyframe
      // 1e34: dup
      // 1e35: ldc 0.6667
      // 1e37: ldc_w 0.0093
      // 1e3a: ldc_w 1.3735
      // 1e3d: ldc_w 2.9023
      // 1e40: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e49: aastore
      // 1e4a: dup
      // 1e4b: bipush 8
      // 1e4d: new net/minecraft/client/animation/Keyframe
      // 1e50: dup
      // 1e51: ldc 0.75
      // 1e53: fconst_0
      // 1e54: ldc 2.9535
      // 1e56: ldc -0.5254
      // 1e58: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e5b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e5e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e61: aastore
      // 1e62: dup
      // 1e63: bipush 9
      // 1e65: new net/minecraft/client/animation/Keyframe
      // 1e68: dup
      // 1e69: ldc 0.8333
      // 1e6b: fconst_0
      // 1e6c: ldc_w 0.5752
      // 1e6f: ldc_w 0.4036
      // 1e72: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e7b: aastore
      // 1e7c: dup
      // 1e7d: bipush 10
      // 1e7f: new net/minecraft/client/animation/Keyframe
      // 1e82: dup
      // 1e83: ldc 0.9583
      // 1e85: fconst_0
      // 1e86: ldc 0.5209
      // 1e88: ldc 2.9544
      // 1e8a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e8d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e90: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e93: aastore
      // 1e94: dup
      // 1e95: bipush 11
      // 1e97: new net/minecraft/client/animation/Keyframe
      // 1e9a: dup
      // 1e9b: ldc 1.0417
      // 1e9d: ldc_w 0.0093
      // 1ea0: ldc_w 1.3735
      // 1ea3: ldc_w 2.9023
      // 1ea6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ea9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1eac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1eaf: aastore
      // 1eb0: dup
      // 1eb1: bipush 12
      // 1eb3: new net/minecraft/client/animation/Keyframe
      // 1eb6: dup
      // 1eb7: ldc 1.125
      // 1eb9: fconst_0
      // 1eba: ldc 2.9535
      // 1ebc: ldc -0.5254
      // 1ebe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ec1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ec4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ec7: aastore
      // 1ec8: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1ecb: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1ece: ldc "right_f_fore_leg"
      // 1ed0: new net/minecraft/client/animation/AnimationChannel
      // 1ed3: dup
      // 1ed4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1ed7: bipush 7
      // 1ed9: anewarray 24
      // 1edc: dup
      // 1edd: bipush 0
      // 1ede: new net/minecraft/client/animation/Keyframe
      // 1ee1: dup
      // 1ee2: fconst_0
      // 1ee3: ldc 22.5
      // 1ee5: fconst_0
      // 1ee6: ldc -5.0
      // 1ee8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1eeb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1eee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ef1: aastore
      // 1ef2: dup
      // 1ef3: bipush 1
      // 1ef4: new net/minecraft/client/animation/Keyframe
      // 1ef7: dup
      // 1ef8: ldc 0.2083
      // 1efa: ldc -11.3173
      // 1efc: ldc_w 1.3485
      // 1eff: ldc_w 29.6357
      // 1f02: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f05: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f08: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f0b: aastore
      // 1f0c: dup
      // 1f0d: bipush 2
      // 1f0e: new net/minecraft/client/animation/Keyframe
      // 1f11: dup
      // 1f12: ldc 0.375
      // 1f14: ldc 22.5
      // 1f16: fconst_0
      // 1f17: ldc -5.0
      // 1f19: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f22: aastore
      // 1f23: dup
      // 1f24: bipush 3
      // 1f25: new net/minecraft/client/animation/Keyframe
      // 1f28: dup
      // 1f29: ldc 0.5833
      // 1f2b: ldc -11.3173
      // 1f2d: ldc_w 1.3485
      // 1f30: ldc_w 29.6357
      // 1f33: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f36: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f39: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f3c: aastore
      // 1f3d: dup
      // 1f3e: bipush 4
      // 1f3f: new net/minecraft/client/animation/Keyframe
      // 1f42: dup
      // 1f43: ldc 0.75
      // 1f45: ldc 22.5
      // 1f47: fconst_0
      // 1f48: ldc -5.0
      // 1f4a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f4d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f50: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f53: aastore
      // 1f54: dup
      // 1f55: bipush 5
      // 1f56: new net/minecraft/client/animation/Keyframe
      // 1f59: dup
      // 1f5a: ldc 0.9583
      // 1f5c: ldc -11.3173
      // 1f5e: ldc_w 1.3485
      // 1f61: ldc_w 29.6357
      // 1f64: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f67: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f6a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f6d: aastore
      // 1f6e: dup
      // 1f6f: bipush 6
      // 1f71: new net/minecraft/client/animation/Keyframe
      // 1f74: dup
      // 1f75: ldc 1.125
      // 1f77: ldc 22.5
      // 1f79: fconst_0
      // 1f7a: ldc -5.0
      // 1f7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f85: aastore
      // 1f86: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1f89: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1f8c: ldc "legs"
      // 1f8e: new net/minecraft/client/animation/AnimationChannel
      // 1f91: dup
      // 1f92: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1f95: bipush 7
      // 1f97: anewarray 24
      // 1f9a: dup
      // 1f9b: bipush 0
      // 1f9c: new net/minecraft/client/animation/Keyframe
      // 1f9f: dup
      // 1fa0: fconst_0
      // 1fa1: ldc -14.9042
      // 1fa3: ldc -4.8876
      // 1fa5: ldc -1.4153
      // 1fa7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1faa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fb0: aastore
      // 1fb1: dup
      // 1fb2: bipush 1
      // 1fb3: new net/minecraft/client/animation/Keyframe
      // 1fb6: dup
      // 1fb7: ldc 0.2083
      // 1fb9: ldc -14.9042
      // 1fbb: ldc_w 4.8876
      // 1fbe: ldc_w 1.4153
      // 1fc1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fc4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fc7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fca: aastore
      // 1fcb: dup
      // 1fcc: bipush 2
      // 1fcd: new net/minecraft/client/animation/Keyframe
      // 1fd0: dup
      // 1fd1: ldc 0.375
      // 1fd3: ldc -14.9042
      // 1fd5: ldc -4.8876
      // 1fd7: ldc -1.4153
      // 1fd9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fdc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fdf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fe2: aastore
      // 1fe3: dup
      // 1fe4: bipush 3
      // 1fe5: new net/minecraft/client/animation/Keyframe
      // 1fe8: dup
      // 1fe9: ldc 0.5833
      // 1feb: ldc -14.9042
      // 1fed: ldc_w 4.8876
      // 1ff0: ldc_w 1.4153
      // 1ff3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ff6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ff9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ffc: aastore
      // 1ffd: dup
      // 1ffe: bipush 4
      // 1fff: new net/minecraft/client/animation/Keyframe
      // 2002: dup
      // 2003: ldc 0.75
      // 2005: ldc -14.9042
      // 2007: ldc -4.8876
      // 2009: ldc -1.4153
      // 200b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 200e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2011: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2014: aastore
      // 2015: dup
      // 2016: bipush 5
      // 2017: new net/minecraft/client/animation/Keyframe
      // 201a: dup
      // 201b: ldc 0.9583
      // 201d: ldc -14.9042
      // 201f: ldc_w 4.8876
      // 2022: ldc_w 1.4153
      // 2025: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2028: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 202b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 202e: aastore
      // 202f: dup
      // 2030: bipush 6
      // 2032: new net/minecraft/client/animation/Keyframe
      // 2035: dup
      // 2036: ldc 1.125
      // 2038: ldc -14.9042
      // 203a: ldc -4.8876
      // 203c: ldc -1.4153
      // 203e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2041: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2044: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2047: aastore
      // 2048: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 204b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 204e: ldc "legs"
      // 2050: new net/minecraft/client/animation/AnimationChannel
      // 2053: dup
      // 2054: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2057: bipush 2
      // 2058: anewarray 24
      // 205b: dup
      // 205c: bipush 0
      // 205d: new net/minecraft/client/animation/Keyframe
      // 2060: dup
      // 2061: fconst_0
      // 2062: fconst_0
      // 2063: fconst_0
      // 2064: fconst_0
      // 2065: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2068: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 206b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 206e: aastore
      // 206f: dup
      // 2070: bipush 1
      // 2071: new net/minecraft/client/animation/Keyframe
      // 2074: dup
      // 2075: ldc 1.125
      // 2077: fconst_0
      // 2078: fconst_0
      // 2079: fconst_0
      // 207a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 207d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2080: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2083: aastore
      // 2084: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2087: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 208a: ldc "left_f_leg_joint"
      // 208c: new net/minecraft/client/animation/AnimationChannel
      // 208f: dup
      // 2090: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2093: bipush 13
      // 2095: anewarray 24
      // 2098: dup
      // 2099: bipush 0
      // 209a: new net/minecraft/client/animation/Keyframe
      // 209d: dup
      // 209e: fconst_0
      // 209f: ldc 10.0
      // 20a1: fconst_0
      // 20a2: fconst_0
      // 20a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20ac: aastore
      // 20ad: dup
      // 20ae: bipush 1
      // 20af: new net/minecraft/client/animation/Keyframe
      // 20b2: dup
      // 20b3: ldc_w 0.0833
      // 20b6: ldc_w 1.0E-4
      // 20b9: ldc_w 0.0037
      // 20bc: ldc_w -0.0434
      // 20bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20c8: aastore
      // 20c9: dup
      // 20ca: bipush 2
      // 20cb: new net/minecraft/client/animation/Keyframe
      // 20ce: dup
      // 20cf: ldc 0.2083
      // 20d1: ldc -29.1134
      // 20d3: ldc_w 15.5554
      // 20d6: ldc_w -3.99
      // 20d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20e2: aastore
      // 20e3: dup
      // 20e4: bipush 3
      // 20e5: new net/minecraft/client/animation/Keyframe
      // 20e8: dup
      // 20e9: ldc 0.25
      // 20eb: ldc_w -34.0885
      // 20ee: ldc_w 11.461
      // 20f1: ldc_w -5.5599
      // 20f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20fd: aastore
      // 20fe: dup
      // 20ff: bipush 4
      // 2100: new net/minecraft/client/animation/Keyframe
      // 2103: dup
      // 2104: ldc 0.375
      // 2106: ldc 10.0
      // 2108: fconst_0
      // 2109: fconst_0
      // 210a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 210d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2110: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2113: aastore
      // 2114: dup
      // 2115: bipush 5
      // 2116: new net/minecraft/client/animation/Keyframe
      // 2119: dup
      // 211a: ldc_w 0.4583
      // 211d: ldc_w 1.0E-4
      // 2120: ldc_w 0.0037
      // 2123: ldc_w -0.0434
      // 2126: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2129: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 212c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 212f: aastore
      // 2130: dup
      // 2131: bipush 6
      // 2133: new net/minecraft/client/animation/Keyframe
      // 2136: dup
      // 2137: ldc 0.5833
      // 2139: ldc -29.1134
      // 213b: ldc_w 15.5554
      // 213e: ldc_w -3.99
      // 2141: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2144: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2147: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 214a: aastore
      // 214b: dup
      // 214c: bipush 7
      // 214e: new net/minecraft/client/animation/Keyframe
      // 2151: dup
      // 2152: ldc 0.625
      // 2154: ldc_w -34.0885
      // 2157: ldc_w 11.461
      // 215a: ldc_w -5.5599
      // 215d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2160: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2163: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2166: aastore
      // 2167: dup
      // 2168: bipush 8
      // 216a: new net/minecraft/client/animation/Keyframe
      // 216d: dup
      // 216e: ldc 0.75
      // 2170: ldc 10.0
      // 2172: fconst_0
      // 2173: fconst_0
      // 2174: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2177: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 217a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 217d: aastore
      // 217e: dup
      // 217f: bipush 9
      // 2181: new net/minecraft/client/animation/Keyframe
      // 2184: dup
      // 2185: ldc 0.8333
      // 2187: ldc_w 1.0E-4
      // 218a: ldc_w 0.0037
      // 218d: ldc_w -0.0434
      // 2190: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2193: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2196: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2199: aastore
      // 219a: dup
      // 219b: bipush 10
      // 219d: new net/minecraft/client/animation/Keyframe
      // 21a0: dup
      // 21a1: ldc 0.9583
      // 21a3: ldc -29.1134
      // 21a5: ldc_w 15.5554
      // 21a8: ldc_w -3.99
      // 21ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21b4: aastore
      // 21b5: dup
      // 21b6: bipush 11
      // 21b8: new net/minecraft/client/animation/Keyframe
      // 21bb: dup
      // 21bc: fconst_1
      // 21bd: ldc_w -34.0885
      // 21c0: ldc_w 11.461
      // 21c3: ldc_w -5.5599
      // 21c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21cf: aastore
      // 21d0: dup
      // 21d1: bipush 12
      // 21d3: new net/minecraft/client/animation/Keyframe
      // 21d6: dup
      // 21d7: ldc 1.125
      // 21d9: ldc 10.0
      // 21db: fconst_0
      // 21dc: fconst_0
      // 21dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21e6: aastore
      // 21e7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 21ea: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 21ed: ldc "right_f_leg_joint"
      // 21ef: new net/minecraft/client/animation/AnimationChannel
      // 21f2: dup
      // 21f3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 21f6: bipush 13
      // 21f8: anewarray 24
      // 21fb: dup
      // 21fc: bipush 0
      // 21fd: new net/minecraft/client/animation/Keyframe
      // 2200: dup
      // 2201: fconst_0
      // 2202: ldc -29.1134
      // 2204: ldc -15.5554
      // 2206: ldc 3.99
      // 2208: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 220b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 220e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2211: aastore
      // 2212: dup
      // 2213: bipush 1
      // 2214: new net/minecraft/client/animation/Keyframe
      // 2217: dup
      // 2218: ldc_w 0.0833
      // 221b: ldc_w -34.0885
      // 221e: ldc_w -11.461
      // 2221: ldc_w 5.5599
      // 2224: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2227: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 222a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 222d: aastore
      // 222e: dup
      // 222f: bipush 2
      // 2230: new net/minecraft/client/animation/Keyframe
      // 2233: dup
      // 2234: ldc 0.2083
      // 2236: ldc 10.0
      // 2238: fconst_0
      // 2239: fconst_0
      // 223a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 223d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2240: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2243: aastore
      // 2244: dup
      // 2245: bipush 3
      // 2246: new net/minecraft/client/animation/Keyframe
      // 2249: dup
      // 224a: ldc 0.2917
      // 224c: ldc_w 1.0E-4
      // 224f: ldc_w 0.0037
      // 2252: ldc_w -0.0434
      // 2255: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2258: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 225b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 225e: aastore
      // 225f: dup
      // 2260: bipush 4
      // 2261: new net/minecraft/client/animation/Keyframe
      // 2264: dup
      // 2265: ldc 0.375
      // 2267: ldc -29.1134
      // 2269: ldc -15.5554
      // 226b: ldc 3.99
      // 226d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2270: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2273: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2276: aastore
      // 2277: dup
      // 2278: bipush 5
      // 2279: new net/minecraft/client/animation/Keyframe
      // 227c: dup
      // 227d: ldc_w 0.4583
      // 2280: ldc_w -34.0885
      // 2283: ldc_w -11.461
      // 2286: ldc_w 5.5599
      // 2289: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 228c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 228f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2292: aastore
      // 2293: dup
      // 2294: bipush 6
      // 2296: new net/minecraft/client/animation/Keyframe
      // 2299: dup
      // 229a: ldc 0.5833
      // 229c: ldc 10.0
      // 229e: fconst_0
      // 229f: fconst_0
      // 22a0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22a3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22a6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22a9: aastore
      // 22aa: dup
      // 22ab: bipush 7
      // 22ad: new net/minecraft/client/animation/Keyframe
      // 22b0: dup
      // 22b1: ldc 0.6667
      // 22b3: ldc_w 1.0E-4
      // 22b6: ldc_w 0.0037
      // 22b9: ldc_w -0.0434
      // 22bc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22bf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22c2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22c5: aastore
      // 22c6: dup
      // 22c7: bipush 8
      // 22c9: new net/minecraft/client/animation/Keyframe
      // 22cc: dup
      // 22cd: ldc 0.75
      // 22cf: ldc -29.1134
      // 22d1: ldc -15.5554
      // 22d3: ldc 3.99
      // 22d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22de: aastore
      // 22df: dup
      // 22e0: bipush 9
      // 22e2: new net/minecraft/client/animation/Keyframe
      // 22e5: dup
      // 22e6: ldc 0.8333
      // 22e8: ldc_w -34.0885
      // 22eb: ldc_w -11.461
      // 22ee: ldc_w 5.5599
      // 22f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22fa: aastore
      // 22fb: dup
      // 22fc: bipush 10
      // 22fe: new net/minecraft/client/animation/Keyframe
      // 2301: dup
      // 2302: ldc 0.9583
      // 2304: ldc 10.0
      // 2306: fconst_0
      // 2307: fconst_0
      // 2308: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 230b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 230e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2311: aastore
      // 2312: dup
      // 2313: bipush 11
      // 2315: new net/minecraft/client/animation/Keyframe
      // 2318: dup
      // 2319: ldc 1.0417
      // 231b: ldc_w 1.0E-4
      // 231e: ldc_w 0.0037
      // 2321: ldc_w -0.0434
      // 2324: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2327: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 232a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 232d: aastore
      // 232e: dup
      // 232f: bipush 12
      // 2331: new net/minecraft/client/animation/Keyframe
      // 2334: dup
      // 2335: ldc 1.125
      // 2337: ldc -29.1134
      // 2339: ldc -15.5554
      // 233b: ldc 3.99
      // 233d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2340: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2343: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2346: aastore
      // 2347: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 234a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 234d: ldc "right_b_leg_joint"
      // 234f: new net/minecraft/client/animation/AnimationChannel
      // 2352: dup
      // 2353: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2356: bipush 10
      // 2358: anewarray 24
      // 235b: dup
      // 235c: bipush 0
      // 235d: new net/minecraft/client/animation/Keyframe
      // 2360: dup
      // 2361: fconst_0
      // 2362: ldc 21.9529
      // 2364: ldc -4.9358
      // 2366: ldc -12.6843
      // 2368: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 236b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 236e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2371: aastore
      // 2372: dup
      // 2373: bipush 1
      // 2374: new net/minecraft/client/animation/Keyframe
      // 2377: dup
      // 2378: ldc_w 0.0833
      // 237b: ldc_w -0.5541
      // 237e: ldc_w -24.0659
      // 2381: ldc_w 12.2373
      // 2384: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2387: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 238a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 238d: aastore
      // 238e: dup
      // 238f: bipush 2
      // 2390: new net/minecraft/client/animation/Keyframe
      // 2393: dup
      // 2394: ldc 0.2083
      // 2396: ldc -12.3237
      // 2398: ldc_w -34.5622
      // 239b: ldc_w -2.8772
      // 239e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23a7: aastore
      // 23a8: dup
      // 23a9: bipush 3
      // 23aa: new net/minecraft/client/animation/Keyframe
      // 23ad: dup
      // 23ae: ldc 0.375
      // 23b0: ldc 21.9529
      // 23b2: ldc -4.9358
      // 23b4: ldc -12.6843
      // 23b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23bf: aastore
      // 23c0: dup
      // 23c1: bipush 4
      // 23c2: new net/minecraft/client/animation/Keyframe
      // 23c5: dup
      // 23c6: ldc_w 0.4583
      // 23c9: ldc_w -0.5541
      // 23cc: ldc_w -24.0659
      // 23cf: ldc_w 12.2373
      // 23d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23db: aastore
      // 23dc: dup
      // 23dd: bipush 5
      // 23de: new net/minecraft/client/animation/Keyframe
      // 23e1: dup
      // 23e2: ldc 0.5833
      // 23e4: ldc -12.3237
      // 23e6: ldc_w -34.5622
      // 23e9: ldc_w -2.8772
      // 23ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23f5: aastore
      // 23f6: dup
      // 23f7: bipush 6
      // 23f9: new net/minecraft/client/animation/Keyframe
      // 23fc: dup
      // 23fd: ldc 0.75
      // 23ff: ldc 21.9529
      // 2401: ldc -4.9358
      // 2403: ldc -12.6843
      // 2405: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2408: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 240b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 240e: aastore
      // 240f: dup
      // 2410: bipush 7
      // 2412: new net/minecraft/client/animation/Keyframe
      // 2415: dup
      // 2416: ldc 0.8333
      // 2418: ldc_w -0.5541
      // 241b: ldc_w -24.0659
      // 241e: ldc_w 12.2373
      // 2421: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2424: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2427: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 242a: aastore
      // 242b: dup
      // 242c: bipush 8
      // 242e: new net/minecraft/client/animation/Keyframe
      // 2431: dup
      // 2432: ldc 0.9583
      // 2434: ldc -12.3237
      // 2436: ldc_w -34.5622
      // 2439: ldc_w -2.8772
      // 243c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 243f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2442: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2445: aastore
      // 2446: dup
      // 2447: bipush 9
      // 2449: new net/minecraft/client/animation/Keyframe
      // 244c: dup
      // 244d: ldc 1.125
      // 244f: ldc 21.9529
      // 2451: ldc -4.9358
      // 2453: ldc -12.6843
      // 2455: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2458: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 245b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 245e: aastore
      // 245f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2462: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2465: ldc "left_b_leg_joint"
      // 2467: new net/minecraft/client/animation/AnimationChannel
      // 246a: dup
      // 246b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 246e: bipush 10
      // 2470: anewarray 24
      // 2473: dup
      // 2474: bipush 0
      // 2475: new net/minecraft/client/animation/Keyframe
      // 2478: dup
      // 2479: fconst_0
      // 247a: ldc -12.3237
      // 247c: ldc 34.5622
      // 247e: ldc 2.8772
      // 2480: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2483: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2486: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2489: aastore
      // 248a: dup
      // 248b: bipush 1
      // 248c: new net/minecraft/client/animation/Keyframe
      // 248f: dup
      // 2490: ldc 0.2083
      // 2492: ldc 21.9529
      // 2494: ldc_w 4.9358
      // 2497: ldc_w 12.6843
      // 249a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 249d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24a3: aastore
      // 24a4: dup
      // 24a5: bipush 2
      // 24a6: new net/minecraft/client/animation/Keyframe
      // 24a9: dup
      // 24aa: ldc 0.2917
      // 24ac: ldc_w -0.5541
      // 24af: ldc_w 24.0659
      // 24b2: ldc_w -12.2373
      // 24b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24be: aastore
      // 24bf: dup
      // 24c0: bipush 3
      // 24c1: new net/minecraft/client/animation/Keyframe
      // 24c4: dup
      // 24c5: ldc 0.375
      // 24c7: ldc -12.3237
      // 24c9: ldc 34.5622
      // 24cb: ldc 2.8772
      // 24cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24d6: aastore
      // 24d7: dup
      // 24d8: bipush 4
      // 24d9: new net/minecraft/client/animation/Keyframe
      // 24dc: dup
      // 24dd: ldc 0.5833
      // 24df: ldc 21.9529
      // 24e1: ldc_w 4.9358
      // 24e4: ldc_w 12.6843
      // 24e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24f0: aastore
      // 24f1: dup
      // 24f2: bipush 5
      // 24f3: new net/minecraft/client/animation/Keyframe
      // 24f6: dup
      // 24f7: ldc 0.6667
      // 24f9: ldc_w -0.5541
      // 24fc: ldc_w 24.0659
      // 24ff: ldc_w -12.2373
      // 2502: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2505: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2508: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 250b: aastore
      // 250c: dup
      // 250d: bipush 6
      // 250f: new net/minecraft/client/animation/Keyframe
      // 2512: dup
      // 2513: ldc 0.75
      // 2515: ldc -12.3237
      // 2517: ldc 34.5622
      // 2519: ldc 2.8772
      // 251b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 251e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2521: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2524: aastore
      // 2525: dup
      // 2526: bipush 7
      // 2528: new net/minecraft/client/animation/Keyframe
      // 252b: dup
      // 252c: ldc 0.9583
      // 252e: ldc 21.9529
      // 2530: ldc_w 4.9358
      // 2533: ldc_w 12.6843
      // 2536: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2539: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 253c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 253f: aastore
      // 2540: dup
      // 2541: bipush 8
      // 2543: new net/minecraft/client/animation/Keyframe
      // 2546: dup
      // 2547: ldc 1.0417
      // 2549: ldc_w -0.5541
      // 254c: ldc_w 24.0659
      // 254f: ldc_w -12.2373
      // 2552: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2555: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2558: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 255b: aastore
      // 255c: dup
      // 255d: bipush 9
      // 255f: new net/minecraft/client/animation/Keyframe
      // 2562: dup
      // 2563: ldc 1.125
      // 2565: ldc -12.3237
      // 2567: ldc 34.5622
      // 2569: ldc 2.8772
      // 256b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 256e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2571: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2574: aastore
      // 2575: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2578: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 257b: ldc "left_b_leg"
      // 257d: new net/minecraft/client/animation/AnimationChannel
      // 2580: dup
      // 2581: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2584: bipush 2
      // 2585: anewarray 24
      // 2588: dup
      // 2589: bipush 0
      // 258a: new net/minecraft/client/animation/Keyframe
      // 258d: dup
      // 258e: fconst_0
      // 258f: fconst_0
      // 2590: fconst_0
      // 2591: fconst_0
      // 2592: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2595: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2598: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 259b: aastore
      // 259c: dup
      // 259d: bipush 1
      // 259e: new net/minecraft/client/animation/Keyframe
      // 25a1: dup
      // 25a2: ldc 1.125
      // 25a4: fconst_0
      // 25a5: fconst_0
      // 25a6: fconst_0
      // 25a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25b0: aastore
      // 25b1: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 25b4: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 25b7: ldc "left_b_leg"
      // 25b9: new net/minecraft/client/animation/AnimationChannel
      // 25bc: dup
      // 25bd: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 25c0: bipush 13
      // 25c2: anewarray 24
      // 25c5: dup
      // 25c6: bipush 0
      // 25c7: new net/minecraft/client/animation/Keyframe
      // 25ca: dup
      // 25cb: fconst_0
      // 25cc: ldc 0.0578
      // 25ce: ldc 0.4035
      // 25d0: ldc -3.3315
      // 25d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 25d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25db: aastore
      // 25dc: dup
      // 25dd: bipush 1
      // 25de: new net/minecraft/client/animation/Keyframe
      // 25e1: dup
      // 25e2: ldc_w 0.0833
      // 25e5: ldc_w 0.0093
      // 25e8: ldc_w 0.4051
      // 25eb: ldc_w -1.3215
      // 25ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 25f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25f7: aastore
      // 25f8: dup
      // 25f9: bipush 2
      // 25fa: new net/minecraft/client/animation/Keyframe
      // 25fd: dup
      // 25fe: ldc 0.2083
      // 2600: fconst_0
      // 2601: ldc 0.1746
      // 2603: ldc 0.9892
      // 2605: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2608: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 260b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 260e: aastore
      // 260f: dup
      // 2610: bipush 3
      // 2611: new net/minecraft/client/animation/Keyframe
      // 2614: dup
      // 2615: ldc 0.2917
      // 2617: fconst_0
      // 2618: ldc_w 2.7844
      // 261b: ldc_w -1.5009
      // 261e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2621: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2624: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2627: aastore
      // 2628: dup
      // 2629: bipush 4
      // 262a: new net/minecraft/client/animation/Keyframe
      // 262d: dup
      // 262e: ldc 0.375
      // 2630: ldc 0.0578
      // 2632: ldc 0.4035
      // 2634: ldc -3.3315
      // 2636: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2639: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 263c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 263f: aastore
      // 2640: dup
      // 2641: bipush 5
      // 2642: new net/minecraft/client/animation/Keyframe
      // 2645: dup
      // 2646: ldc_w 0.4583
      // 2649: ldc_w 0.0093
      // 264c: ldc_w 0.4051
      // 264f: ldc_w -1.3215
      // 2652: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2655: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2658: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 265b: aastore
      // 265c: dup
      // 265d: bipush 6
      // 265f: new net/minecraft/client/animation/Keyframe
      // 2662: dup
      // 2663: ldc 0.5833
      // 2665: fconst_0
      // 2666: ldc 0.1746
      // 2668: ldc 0.9892
      // 266a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 266d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2670: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2673: aastore
      // 2674: dup
      // 2675: bipush 7
      // 2677: new net/minecraft/client/animation/Keyframe
      // 267a: dup
      // 267b: ldc 0.6667
      // 267d: fconst_0
      // 267e: ldc_w 2.7844
      // 2681: ldc_w -1.5009
      // 2684: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2687: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 268a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 268d: aastore
      // 268e: dup
      // 268f: bipush 8
      // 2691: new net/minecraft/client/animation/Keyframe
      // 2694: dup
      // 2695: ldc 0.75
      // 2697: ldc 0.0578
      // 2699: ldc 0.4035
      // 269b: ldc -3.3315
      // 269d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26a6: aastore
      // 26a7: dup
      // 26a8: bipush 9
      // 26aa: new net/minecraft/client/animation/Keyframe
      // 26ad: dup
      // 26ae: ldc 0.8333
      // 26b0: ldc_w 0.0093
      // 26b3: ldc_w 0.4051
      // 26b6: ldc_w -1.3215
      // 26b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26c2: aastore
      // 26c3: dup
      // 26c4: bipush 10
      // 26c6: new net/minecraft/client/animation/Keyframe
      // 26c9: dup
      // 26ca: ldc 0.9583
      // 26cc: fconst_0
      // 26cd: ldc 0.1746
      // 26cf: ldc 0.9892
      // 26d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26da: aastore
      // 26db: dup
      // 26dc: bipush 11
      // 26de: new net/minecraft/client/animation/Keyframe
      // 26e1: dup
      // 26e2: ldc 1.0417
      // 26e4: fconst_0
      // 26e5: ldc_w 2.7844
      // 26e8: ldc_w -1.5009
      // 26eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26f4: aastore
      // 26f5: dup
      // 26f6: bipush 12
      // 26f8: new net/minecraft/client/animation/Keyframe
      // 26fb: dup
      // 26fc: ldc 1.125
      // 26fe: ldc 0.0578
      // 2700: ldc 0.4035
      // 2702: ldc -3.3315
      // 2704: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2707: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 270a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 270d: aastore
      // 270e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2711: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2714: ldc "left_b_fore_leg"
      // 2716: new net/minecraft/client/animation/AnimationChannel
      // 2719: dup
      // 271a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 271d: bipush 10
      // 271f: anewarray 24
      // 2722: dup
      // 2723: bipush 0
      // 2724: new net/minecraft/client/animation/Keyframe
      // 2727: dup
      // 2728: fconst_0
      // 2729: ldc 23.8072
      // 272b: ldc 0.6087
      // 272d: ldc -28.9939
      // 272f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2732: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2735: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2738: aastore
      // 2739: dup
      // 273a: bipush 1
      // 273b: new net/minecraft/client/animation/Keyframe
      // 273e: dup
      // 273f: ldc 0.2083
      // 2741: ldc -13.7142
      // 2743: ldc_w -2.6061
      // 2746: ldc_w -27.5327
      // 2749: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 274c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 274f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2752: aastore
      // 2753: dup
      // 2754: bipush 2
      // 2755: new net/minecraft/client/animation/Keyframe
      // 2758: dup
      // 2759: ldc 0.2917
      // 275b: ldc_w 15.8036
      // 275e: ldc_w -6.0862
      // 2761: ldc_w -7.195
      // 2764: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2767: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 276a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 276d: aastore
      // 276e: dup
      // 276f: bipush 3
      // 2770: new net/minecraft/client/animation/Keyframe
      // 2773: dup
      // 2774: ldc 0.375
      // 2776: ldc 23.8072
      // 2778: ldc 0.6087
      // 277a: ldc -28.9939
      // 277c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 277f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2782: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2785: aastore
      // 2786: dup
      // 2787: bipush 4
      // 2788: new net/minecraft/client/animation/Keyframe
      // 278b: dup
      // 278c: ldc 0.5833
      // 278e: ldc -13.7142
      // 2790: ldc_w -2.6061
      // 2793: ldc_w -27.5327
      // 2796: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2799: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 279c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 279f: aastore
      // 27a0: dup
      // 27a1: bipush 5
      // 27a2: new net/minecraft/client/animation/Keyframe
      // 27a5: dup
      // 27a6: ldc 0.6667
      // 27a8: ldc_w 15.8036
      // 27ab: ldc_w -6.0862
      // 27ae: ldc_w -7.195
      // 27b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27ba: aastore
      // 27bb: dup
      // 27bc: bipush 6
      // 27be: new net/minecraft/client/animation/Keyframe
      // 27c1: dup
      // 27c2: ldc 0.75
      // 27c4: ldc 23.8072
      // 27c6: ldc 0.6087
      // 27c8: ldc -28.9939
      // 27ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27d3: aastore
      // 27d4: dup
      // 27d5: bipush 7
      // 27d7: new net/minecraft/client/animation/Keyframe
      // 27da: dup
      // 27db: ldc 0.9583
      // 27dd: ldc -13.7142
      // 27df: ldc_w -2.6061
      // 27e2: ldc_w -27.5327
      // 27e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 27e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27ee: aastore
      // 27ef: dup
      // 27f0: bipush 8
      // 27f2: new net/minecraft/client/animation/Keyframe
      // 27f5: dup
      // 27f6: ldc 1.0417
      // 27f8: ldc_w 15.8036
      // 27fb: ldc_w -6.0862
      // 27fe: ldc_w -7.195
      // 2801: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2804: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2807: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 280a: aastore
      // 280b: dup
      // 280c: bipush 9
      // 280e: new net/minecraft/client/animation/Keyframe
      // 2811: dup
      // 2812: ldc 1.125
      // 2814: ldc 23.8072
      // 2816: ldc 0.6087
      // 2818: ldc -28.9939
      // 281a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 281d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2820: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2823: aastore
      // 2824: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2827: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 282a: ldc "right_down_antenna"
      // 282c: new net/minecraft/client/animation/AnimationChannel
      // 282f: dup
      // 2830: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2833: bipush 3
      // 2834: anewarray 24
      // 2837: dup
      // 2838: bipush 0
      // 2839: new net/minecraft/client/animation/Keyframe
      // 283c: dup
      // 283d: fconst_0
      // 283e: fconst_0
      // 283f: fconst_0
      // 2840: fconst_0
      // 2841: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2844: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2847: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 284a: aastore
      // 284b: dup
      // 284c: bipush 1
      // 284d: new net/minecraft/client/animation/Keyframe
      // 2850: dup
      // 2851: ldc 0.5833
      // 2853: ldc_w -3.3781
      // 2856: ldc_w 2.8299
      // 2859: ldc_w -3.4622
      // 285c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 285f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2862: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2865: aastore
      // 2866: dup
      // 2867: bipush 2
      // 2868: new net/minecraft/client/animation/Keyframe
      // 286b: dup
      // 286c: ldc 1.125
      // 286e: fconst_0
      // 286f: fconst_0
      // 2870: fconst_0
      // 2871: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2874: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2877: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 287a: aastore
      // 287b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 287e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2881: ldc "left_down_antenna"
      // 2883: new net/minecraft/client/animation/AnimationChannel
      // 2886: dup
      // 2887: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 288a: bipush 3
      // 288b: anewarray 24
      // 288e: dup
      // 288f: bipush 0
      // 2890: new net/minecraft/client/animation/Keyframe
      // 2893: dup
      // 2894: fconst_0
      // 2895: fconst_0
      // 2896: fconst_0
      // 2897: fconst_0
      // 2898: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 289b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 289e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28a1: aastore
      // 28a2: dup
      // 28a3: bipush 1
      // 28a4: new net/minecraft/client/animation/Keyframe
      // 28a7: dup
      // 28a8: ldc 0.5833
      // 28aa: ldc_w -3.3781
      // 28ad: ldc_w -2.8299
      // 28b0: ldc_w 3.4622
      // 28b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28bc: aastore
      // 28bd: dup
      // 28be: bipush 2
      // 28bf: new net/minecraft/client/animation/Keyframe
      // 28c2: dup
      // 28c3: ldc 1.125
      // 28c5: fconst_0
      // 28c6: fconst_0
      // 28c7: fconst_0
      // 28c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 28cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28d1: aastore
      // 28d2: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 28d5: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 28d8: ldc "everything"
      // 28da: new net/minecraft/client/animation/AnimationChannel
      // 28dd: dup
      // 28de: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 28e1: bipush 2
      // 28e2: anewarray 24
      // 28e5: dup
      // 28e6: bipush 0
      // 28e7: new net/minecraft/client/animation/Keyframe
      // 28ea: dup
      // 28eb: fconst_0
      // 28ec: fconst_0
      // 28ed: fconst_0
      // 28ee: fconst_0
      // 28ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 28f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28f8: aastore
      // 28f9: dup
      // 28fa: bipush 1
      // 28fb: new net/minecraft/client/animation/Keyframe
      // 28fe: dup
      // 28ff: ldc 1.125
      // 2901: fconst_0
      // 2902: fconst_0
      // 2903: fconst_0
      // 2904: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2907: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 290a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 290d: aastore
      // 290e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2911: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2914: ldc "head_tail"
      // 2916: new net/minecraft/client/animation/AnimationChannel
      // 2919: dup
      // 291a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 291d: bipush 2
      // 291e: anewarray 24
      // 2921: dup
      // 2922: bipush 0
      // 2923: new net/minecraft/client/animation/Keyframe
      // 2926: dup
      // 2927: fconst_0
      // 2928: fconst_0
      // 2929: fconst_0
      // 292a: fconst_0
      // 292b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 292e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2931: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2934: aastore
      // 2935: dup
      // 2936: bipush 1
      // 2937: new net/minecraft/client/animation/Keyframe
      // 293a: dup
      // 293b: ldc 1.125
      // 293d: fconst_0
      // 293e: fconst_0
      // 293f: fconst_0
      // 2940: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2943: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2946: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2949: aastore
      // 294a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 294d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2950: ldc "left_front_arm_rotator"
      // 2952: new net/minecraft/client/animation/AnimationChannel
      // 2955: dup
      // 2956: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2959: bipush 2
      // 295a: anewarray 24
      // 295d: dup
      // 295e: bipush 0
      // 295f: new net/minecraft/client/animation/Keyframe
      // 2962: dup
      // 2963: fconst_0
      // 2964: ldc 15.0
      // 2966: fconst_0
      // 2967: fconst_0
      // 2968: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 296b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 296e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2971: aastore
      // 2972: dup
      // 2973: bipush 1
      // 2974: new net/minecraft/client/animation/Keyframe
      // 2977: dup
      // 2978: ldc 1.125
      // 297a: ldc 15.0
      // 297c: fconst_0
      // 297d: fconst_0
      // 297e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2981: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2984: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2987: aastore
      // 2988: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 298b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 298e: ldc "right_front_arm_rotator"
      // 2990: new net/minecraft/client/animation/AnimationChannel
      // 2993: dup
      // 2994: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2997: bipush 2
      // 2998: anewarray 24
      // 299b: dup
      // 299c: bipush 0
      // 299d: new net/minecraft/client/animation/Keyframe
      // 29a0: dup
      // 29a1: fconst_0
      // 29a2: fconst_0
      // 29a3: fconst_0
      // 29a4: fconst_0
      // 29a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29ae: aastore
      // 29af: dup
      // 29b0: bipush 1
      // 29b1: new net/minecraft/client/animation/Keyframe
      // 29b4: dup
      // 29b5: ldc 1.125
      // 29b7: fconst_0
      // 29b8: fconst_0
      // 29b9: fconst_0
      // 29ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29c3: aastore
      // 29c4: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 29c7: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 29ca: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 29cd: putstatic com/github/L_Ender/cataclysm/client/animation/Clawdian_Skill_Animation.CHARGE_LOOP Lnet/minecraft/client/animation/AnimationDefinition;
      // 29d0: ldc 0.5
      // 29d2: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 29d5: ldc "lower_body"
      // 29d7: new net/minecraft/client/animation/AnimationChannel
      // 29da: dup
      // 29db: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 29de: bipush 2
      // 29df: anewarray 24
      // 29e2: dup
      // 29e3: bipush 0
      // 29e4: new net/minecraft/client/animation/Keyframe
      // 29e7: dup
      // 29e8: fconst_0
      // 29e9: fconst_0
      // 29ea: ldc -30.0
      // 29ec: fconst_0
      // 29ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 29f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29f6: aastore
      // 29f7: dup
      // 29f8: bipush 1
      // 29f9: new net/minecraft/client/animation/Keyframe
      // 29fc: dup
      // 29fd: ldc 0.5
      // 29ff: fconst_0
      // 2a00: fconst_0
      // 2a01: fconst_0
      // 2a02: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a05: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a08: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a0b: aastore
      // 2a0c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2a0f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2a12: ldc "lower_body"
      // 2a14: new net/minecraft/client/animation/AnimationChannel
      // 2a17: dup
      // 2a18: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2a1b: bipush 2
      // 2a1c: anewarray 24
      // 2a1f: dup
      // 2a20: bipush 0
      // 2a21: new net/minecraft/client/animation/Keyframe
      // 2a24: dup
      // 2a25: fconst_0
      // 2a26: fconst_0
      // 2a27: fconst_0
      // 2a28: fconst_0
      // 2a29: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2a2c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a2f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a32: aastore
      // 2a33: dup
      // 2a34: bipush 1
      // 2a35: new net/minecraft/client/animation/Keyframe
      // 2a38: dup
      // 2a39: ldc 0.5
      // 2a3b: fconst_0
      // 2a3c: fconst_0
      // 2a3d: fconst_0
      // 2a3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2a41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a47: aastore
      // 2a48: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2a4b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2a4e: ldc "tail1"
      // 2a50: new net/minecraft/client/animation/AnimationChannel
      // 2a53: dup
      // 2a54: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2a57: bipush 2
      // 2a58: anewarray 24
      // 2a5b: dup
      // 2a5c: bipush 0
      // 2a5d: new net/minecraft/client/animation/Keyframe
      // 2a60: dup
      // 2a61: fconst_0
      // 2a62: ldc 2.6061
      // 2a64: ldc -14.8782
      // 2a66: ldc -0.9806
      // 2a68: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a6b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a6e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a71: aastore
      // 2a72: dup
      // 2a73: bipush 1
      // 2a74: new net/minecraft/client/animation/Keyframe
      // 2a77: dup
      // 2a78: ldc 0.5
      // 2a7a: fconst_0
      // 2a7b: fconst_0
      // 2a7c: fconst_0
      // 2a7d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2a80: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a83: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a86: aastore
      // 2a87: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2a8a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2a8d: ldc "pelvis"
      // 2a8f: new net/minecraft/client/animation/AnimationChannel
      // 2a92: dup
      // 2a93: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2a96: bipush 3
      // 2a97: anewarray 24
      // 2a9a: dup
      // 2a9b: bipush 0
      // 2a9c: new net/minecraft/client/animation/Keyframe
      // 2a9f: dup
      // 2aa0: fconst_0
      // 2aa1: ldc 51.9151
      // 2aa3: ldc 8.1763
      // 2aa5: ldc 12.1632
      // 2aa7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2aaa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2aad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ab0: aastore
      // 2ab1: dup
      // 2ab2: bipush 1
      // 2ab3: new net/minecraft/client/animation/Keyframe
      // 2ab6: dup
      // 2ab7: ldc 0.2083
      // 2ab9: ldc 32.5623
      // 2abb: ldc 4.5839
      // 2abd: ldc -2.6332
      // 2abf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ac2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ac5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ac8: aastore
      // 2ac9: dup
      // 2aca: bipush 2
      // 2acb: new net/minecraft/client/animation/Keyframe
      // 2ace: dup
      // 2acf: ldc 0.5
      // 2ad1: fconst_0
      // 2ad2: fconst_0
      // 2ad3: fconst_0
      // 2ad4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ad7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ada: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2add: aastore
      // 2ade: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2ae1: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2ae4: ldc "pelvis"
      // 2ae6: new net/minecraft/client/animation/AnimationChannel
      // 2ae9: dup
      // 2aea: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2aed: bipush 2
      // 2aee: anewarray 24
      // 2af1: dup
      // 2af2: bipush 0
      // 2af3: new net/minecraft/client/animation/Keyframe
      // 2af6: dup
      // 2af7: fconst_0
      // 2af8: fconst_0
      // 2af9: fconst_0
      // 2afa: fconst_0
      // 2afb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2afe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b01: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b04: aastore
      // 2b05: dup
      // 2b06: bipush 1
      // 2b07: new net/minecraft/client/animation/Keyframe
      // 2b0a: dup
      // 2b0b: ldc 0.5
      // 2b0d: fconst_0
      // 2b0e: fconst_0
      // 2b0f: fconst_0
      // 2b10: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2b13: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b16: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b19: aastore
      // 2b1a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2b1d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2b20: ldc "tail2"
      // 2b22: new net/minecraft/client/animation/AnimationChannel
      // 2b25: dup
      // 2b26: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2b29: bipush 2
      // 2b2a: anewarray 24
      // 2b2d: dup
      // 2b2e: bipush 0
      // 2b2f: new net/minecraft/client/animation/Keyframe
      // 2b32: dup
      // 2b33: fconst_0
      // 2b34: ldc 7.4506
      // 2b36: ldc -28.2464
      // 2b38: ldc -8.4326
      // 2b3a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b3d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b40: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b43: aastore
      // 2b44: dup
      // 2b45: bipush 1
      // 2b46: new net/minecraft/client/animation/Keyframe
      // 2b49: dup
      // 2b4a: ldc 0.5
      // 2b4c: fconst_0
      // 2b4d: fconst_0
      // 2b4e: fconst_0
      // 2b4f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b52: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b55: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b58: aastore
      // 2b59: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2b5c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2b5f: ldc "body"
      // 2b61: new net/minecraft/client/animation/AnimationChannel
      // 2b64: dup
      // 2b65: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2b68: bipush 2
      // 2b69: anewarray 24
      // 2b6c: dup
      // 2b6d: bipush 0
      // 2b6e: new net/minecraft/client/animation/Keyframe
      // 2b71: dup
      // 2b72: fconst_0
      // 2b73: ldc -0.1856
      // 2b75: ldc 29.7735
      // 2b77: ldc 3.9792
      // 2b79: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b7c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b7f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b82: aastore
      // 2b83: dup
      // 2b84: bipush 1
      // 2b85: new net/minecraft/client/animation/Keyframe
      // 2b88: dup
      // 2b89: ldc 0.5
      // 2b8b: fconst_0
      // 2b8c: fconst_0
      // 2b8d: fconst_0
      // 2b8e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2b91: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b94: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b97: aastore
      // 2b98: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2b9b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2b9e: ldc "neck"
      // 2ba0: new net/minecraft/client/animation/AnimationChannel
      // 2ba3: dup
      // 2ba4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2ba7: bipush 2
      // 2ba8: anewarray 24
      // 2bab: dup
      // 2bac: bipush 0
      // 2bad: new net/minecraft/client/animation/Keyframe
      // 2bb0: dup
      // 2bb1: fconst_0
      // 2bb2: ldc 3.4161
      // 2bb4: ldc -30.5752
      // 2bb6: ldc -6.6815
      // 2bb8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bbb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bbe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bc1: aastore
      // 2bc2: dup
      // 2bc3: bipush 1
      // 2bc4: new net/minecraft/client/animation/Keyframe
      // 2bc7: dup
      // 2bc8: ldc 0.5
      // 2bca: fconst_0
      // 2bcb: fconst_0
      // 2bcc: fconst_0
      // 2bcd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bd0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bd3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bd6: aastore
      // 2bd7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2bda: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2bdd: ldc "left_arm"
      // 2bdf: new net/minecraft/client/animation/AnimationChannel
      // 2be2: dup
      // 2be3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2be6: bipush 2
      // 2be7: anewarray 24
      // 2bea: dup
      // 2beb: bipush 0
      // 2bec: new net/minecraft/client/animation/Keyframe
      // 2bef: dup
      // 2bf0: fconst_0
      // 2bf1: ldc -115.5439
      // 2bf3: ldc 3.0318
      // 2bf5: ldc 27.3279
      // 2bf7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2bfa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bfd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c00: aastore
      // 2c01: dup
      // 2c02: bipush 1
      // 2c03: new net/minecraft/client/animation/Keyframe
      // 2c06: dup
      // 2c07: ldc 0.5
      // 2c09: fconst_0
      // 2c0a: fconst_0
      // 2c0b: fconst_0
      // 2c0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c15: aastore
      // 2c16: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2c19: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2c1c: ldc "left_front_arm"
      // 2c1e: new net/minecraft/client/animation/AnimationChannel
      // 2c21: dup
      // 2c22: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2c25: bipush 2
      // 2c26: anewarray 24
      // 2c29: dup
      // 2c2a: bipush 0
      // 2c2b: new net/minecraft/client/animation/Keyframe
      // 2c2e: dup
      // 2c2f: fconst_0
      // 2c30: ldc 17.0
      // 2c32: fconst_0
      // 2c33: ldc 62.5
      // 2c35: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c38: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c3b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c3e: aastore
      // 2c3f: dup
      // 2c40: bipush 1
      // 2c41: new net/minecraft/client/animation/Keyframe
      // 2c44: dup
      // 2c45: ldc 0.5
      // 2c47: fconst_0
      // 2c48: fconst_0
      // 2c49: fconst_0
      // 2c4a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c4d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c50: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c53: aastore
      // 2c54: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2c57: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2c5a: ldc "left_claw"
      // 2c5c: new net/minecraft/client/animation/AnimationChannel
      // 2c5f: dup
      // 2c60: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2c63: bipush 3
      // 2c64: anewarray 24
      // 2c67: dup
      // 2c68: bipush 0
      // 2c69: new net/minecraft/client/animation/Keyframe
      // 2c6c: dup
      // 2c6d: fconst_0
      // 2c6e: ldc 25.0
      // 2c70: fconst_0
      // 2c71: fconst_0
      // 2c72: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c7b: aastore
      // 2c7c: dup
      // 2c7d: bipush 1
      // 2c7e: new net/minecraft/client/animation/Keyframe
      // 2c81: dup
      // 2c82: ldc 0.2083
      // 2c84: ldc -30.0
      // 2c86: fconst_0
      // 2c87: fconst_0
      // 2c88: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2c8b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c8e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c91: aastore
      // 2c92: dup
      // 2c93: bipush 2
      // 2c94: new net/minecraft/client/animation/Keyframe
      // 2c97: dup
      // 2c98: ldc 0.5
      // 2c9a: fconst_0
      // 2c9b: fconst_0
      // 2c9c: fconst_0
      // 2c9d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ca0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ca3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ca6: aastore
      // 2ca7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2caa: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2cad: ldc "right_arm"
      // 2caf: new net/minecraft/client/animation/AnimationChannel
      // 2cb2: dup
      // 2cb3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2cb6: bipush 2
      // 2cb7: anewarray 24
      // 2cba: dup
      // 2cbb: bipush 0
      // 2cbc: new net/minecraft/client/animation/Keyframe
      // 2cbf: dup
      // 2cc0: fconst_0
      // 2cc1: fconst_0
      // 2cc2: fconst_0
      // 2cc3: fconst_0
      // 2cc4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cc7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ccd: aastore
      // 2cce: dup
      // 2ccf: bipush 1
      // 2cd0: new net/minecraft/client/animation/Keyframe
      // 2cd3: dup
      // 2cd4: ldc 0.5
      // 2cd6: fconst_0
      // 2cd7: fconst_0
      // 2cd8: fconst_0
      // 2cd9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cdc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cdf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ce2: aastore
      // 2ce3: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2ce6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2ce9: ldc "right_arm"
      // 2ceb: new net/minecraft/client/animation/AnimationChannel
      // 2cee: dup
      // 2cef: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2cf2: bipush 1
      // 2cf3: anewarray 24
      // 2cf6: dup
      // 2cf7: bipush 0
      // 2cf8: new net/minecraft/client/animation/Keyframe
      // 2cfb: dup
      // 2cfc: fconst_0
      // 2cfd: fconst_0
      // 2cfe: fconst_0
      // 2cff: fconst_0
      // 2d00: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2d03: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d06: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d09: aastore
      // 2d0a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2d0d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2d10: ldc "right_front_arm"
      // 2d12: new net/minecraft/client/animation/AnimationChannel
      // 2d15: dup
      // 2d16: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2d19: bipush 2
      // 2d1a: anewarray 24
      // 2d1d: dup
      // 2d1e: bipush 0
      // 2d1f: new net/minecraft/client/animation/Keyframe
      // 2d22: dup
      // 2d23: fconst_0
      // 2d24: fconst_0
      // 2d25: fconst_0
      // 2d26: fconst_0
      // 2d27: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d30: aastore
      // 2d31: dup
      // 2d32: bipush 1
      // 2d33: new net/minecraft/client/animation/Keyframe
      // 2d36: dup
      // 2d37: ldc 0.5
      // 2d39: fconst_0
      // 2d3a: fconst_0
      // 2d3b: fconst_0
      // 2d3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d45: aastore
      // 2d46: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2d49: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2d4c: ldc "right_claw"
      // 2d4e: new net/minecraft/client/animation/AnimationChannel
      // 2d51: dup
      // 2d52: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2d55: bipush 2
      // 2d56: anewarray 24
      // 2d59: dup
      // 2d5a: bipush 0
      // 2d5b: new net/minecraft/client/animation/Keyframe
      // 2d5e: dup
      // 2d5f: fconst_0
      // 2d60: fconst_0
      // 2d61: fconst_0
      // 2d62: fconst_0
      // 2d63: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d66: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d69: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d6c: aastore
      // 2d6d: dup
      // 2d6e: bipush 1
      // 2d6f: new net/minecraft/client/animation/Keyframe
      // 2d72: dup
      // 2d73: ldc 0.5
      // 2d75: fconst_0
      // 2d76: fconst_0
      // 2d77: fconst_0
      // 2d78: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d7b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d7e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d81: aastore
      // 2d82: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2d85: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2d88: ldc "right_hammer"
      // 2d8a: new net/minecraft/client/animation/AnimationChannel
      // 2d8d: dup
      // 2d8e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2d91: bipush 2
      // 2d92: anewarray 24
      // 2d95: dup
      // 2d96: bipush 0
      // 2d97: new net/minecraft/client/animation/Keyframe
      // 2d9a: dup
      // 2d9b: fconst_0
      // 2d9c: ldc 5.0
      // 2d9e: fconst_0
      // 2d9f: fconst_0
      // 2da0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2da3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2da6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2da9: aastore
      // 2daa: dup
      // 2dab: bipush 1
      // 2dac: new net/minecraft/client/animation/Keyframe
      // 2daf: dup
      // 2db0: ldc 0.5
      // 2db2: fconst_0
      // 2db3: fconst_0
      // 2db4: fconst_0
      // 2db5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2db8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2dbb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2dbe: aastore
      // 2dbf: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2dc2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2dc5: ldc "right_hammer"
      // 2dc7: new net/minecraft/client/animation/AnimationChannel
      // 2dca: dup
      // 2dcb: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2dce: bipush 2
      // 2dcf: anewarray 24
      // 2dd2: dup
      // 2dd3: bipush 0
      // 2dd4: new net/minecraft/client/animation/Keyframe
      // 2dd7: dup
      // 2dd8: fconst_0
      // 2dd9: ldc 1.9264
      // 2ddb: ldc -0.5482
      // 2ddd: ldc 1.0948
      // 2ddf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2de2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2de5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2de8: aastore
      // 2de9: dup
      // 2dea: bipush 1
      // 2deb: new net/minecraft/client/animation/Keyframe
      // 2dee: dup
      // 2def: ldc 0.5
      // 2df1: fconst_0
      // 2df2: fconst_0
      // 2df3: fconst_0
      // 2df4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2df7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2dfa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2dfd: aastore
      // 2dfe: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2e01: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2e04: ldc "left_front_arm_rotator"
      // 2e06: new net/minecraft/client/animation/AnimationChannel
      // 2e09: dup
      // 2e0a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2e0d: bipush 2
      // 2e0e: anewarray 24
      // 2e11: dup
      // 2e12: bipush 0
      // 2e13: new net/minecraft/client/animation/Keyframe
      // 2e16: dup
      // 2e17: fconst_0
      // 2e18: ldc 15.0
      // 2e1a: fconst_0
      // 2e1b: fconst_0
      // 2e1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e25: aastore
      // 2e26: dup
      // 2e27: bipush 1
      // 2e28: new net/minecraft/client/animation/Keyframe
      // 2e2b: dup
      // 2e2c: ldc 0.5
      // 2e2e: fconst_0
      // 2e2f: fconst_0
      // 2e30: fconst_0
      // 2e31: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e3a: aastore
      // 2e3b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2e3e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2e41: ldc "everything"
      // 2e43: new net/minecraft/client/animation/AnimationChannel
      // 2e46: dup
      // 2e47: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2e4a: bipush 2
      // 2e4b: anewarray 24
      // 2e4e: dup
      // 2e4f: bipush 0
      // 2e50: new net/minecraft/client/animation/Keyframe
      // 2e53: dup
      // 2e54: fconst_0
      // 2e55: fconst_0
      // 2e56: fconst_0
      // 2e57: fconst_0
      // 2e58: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2e5b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e5e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e61: aastore
      // 2e62: dup
      // 2e63: bipush 1
      // 2e64: new net/minecraft/client/animation/Keyframe
      // 2e67: dup
      // 2e68: ldc 0.5
      // 2e6a: fconst_0
      // 2e6b: fconst_0
      // 2e6c: fconst_0
      // 2e6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2e70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e76: aastore
      // 2e77: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2e7a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2e7d: ldc "mid_root"
      // 2e7f: new net/minecraft/client/animation/AnimationChannel
      // 2e82: dup
      // 2e83: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2e86: bipush 2
      // 2e87: anewarray 24
      // 2e8a: dup
      // 2e8b: bipush 0
      // 2e8c: new net/minecraft/client/animation/Keyframe
      // 2e8f: dup
      // 2e90: fconst_0
      // 2e91: ldc 20.0
      // 2e93: ldc 30.0
      // 2e95: fconst_0
      // 2e96: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e99: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e9c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e9f: aastore
      // 2ea0: dup
      // 2ea1: bipush 1
      // 2ea2: new net/minecraft/client/animation/Keyframe
      // 2ea5: dup
      // 2ea6: ldc 0.5
      // 2ea8: fconst_0
      // 2ea9: fconst_0
      // 2eaa: fconst_0
      // 2eab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2eae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2eb1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2eb4: aastore
      // 2eb5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2eb8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2ebb: ldc "mid_root"
      // 2ebd: new net/minecraft/client/animation/AnimationChannel
      // 2ec0: dup
      // 2ec1: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2ec4: bipush 2
      // 2ec5: anewarray 24
      // 2ec8: dup
      // 2ec9: bipush 0
      // 2eca: new net/minecraft/client/animation/Keyframe
      // 2ecd: dup
      // 2ece: fconst_0
      // 2ecf: fconst_0
      // 2ed0: ldc 0.5
      // 2ed2: fconst_0
      // 2ed3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2ed6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ed9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2edc: aastore
      // 2edd: dup
      // 2ede: bipush 1
      // 2edf: new net/minecraft/client/animation/Keyframe
      // 2ee2: dup
      // 2ee3: ldc 0.5
      // 2ee5: fconst_0
      // 2ee6: fconst_0
      // 2ee7: fconst_0
      // 2ee8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2eeb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2eee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ef1: aastore
      // 2ef2: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2ef5: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2ef8: ldc "head"
      // 2efa: new net/minecraft/client/animation/AnimationChannel
      // 2efd: dup
      // 2efe: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2f01: bipush 2
      // 2f02: anewarray 24
      // 2f05: dup
      // 2f06: bipush 0
      // 2f07: new net/minecraft/client/animation/Keyframe
      // 2f0a: dup
      // 2f0b: fconst_0
      // 2f0c: ldc -69.7576
      // 2f0e: ldc -10.4897
      // 2f10: ldc 1.4281
      // 2f12: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f1b: aastore
      // 2f1c: dup
      // 2f1d: bipush 1
      // 2f1e: new net/minecraft/client/animation/Keyframe
      // 2f21: dup
      // 2f22: ldc 0.5
      // 2f24: fconst_0
      // 2f25: fconst_0
      // 2f26: fconst_0
      // 2f27: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f30: aastore
      // 2f31: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2f34: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2f37: ldc "left_f_leg"
      // 2f39: new net/minecraft/client/animation/AnimationChannel
      // 2f3c: dup
      // 2f3d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2f40: bipush 2
      // 2f41: anewarray 24
      // 2f44: dup
      // 2f45: bipush 0
      // 2f46: new net/minecraft/client/animation/Keyframe
      // 2f49: dup
      // 2f4a: fconst_0
      // 2f4b: fconst_0
      // 2f4c: fconst_0
      // 2f4d: fconst_0
      // 2f4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f57: aastore
      // 2f58: dup
      // 2f59: bipush 1
      // 2f5a: new net/minecraft/client/animation/Keyframe
      // 2f5d: dup
      // 2f5e: ldc 0.5
      // 2f60: fconst_0
      // 2f61: fconst_0
      // 2f62: fconst_0
      // 2f63: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f66: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f69: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f6c: aastore
      // 2f6d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2f70: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2f73: ldc "left_f_leg"
      // 2f75: new net/minecraft/client/animation/AnimationChannel
      // 2f78: dup
      // 2f79: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2f7c: bipush 2
      // 2f7d: anewarray 24
      // 2f80: dup
      // 2f81: bipush 0
      // 2f82: new net/minecraft/client/animation/Keyframe
      // 2f85: dup
      // 2f86: fconst_0
      // 2f87: fconst_0
      // 2f88: ldc 0.5209
      // 2f8a: ldc 2.9544
      // 2f8c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2f8f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f92: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f95: aastore
      // 2f96: dup
      // 2f97: bipush 1
      // 2f98: new net/minecraft/client/animation/Keyframe
      // 2f9b: dup
      // 2f9c: ldc 0.5
      // 2f9e: fconst_0
      // 2f9f: fconst_0
      // 2fa0: fconst_0
      // 2fa1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2fa4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fa7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2faa: aastore
      // 2fab: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2fae: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2fb1: ldc "left_f_fore_leg"
      // 2fb3: new net/minecraft/client/animation/AnimationChannel
      // 2fb6: dup
      // 2fb7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2fba: bipush 2
      // 2fbb: anewarray 24
      // 2fbe: dup
      // 2fbf: bipush 0
      // 2fc0: new net/minecraft/client/animation/Keyframe
      // 2fc3: dup
      // 2fc4: fconst_0
      // 2fc5: ldc -11.3173
      // 2fc7: ldc -1.3485
      // 2fc9: ldc -29.6357
      // 2fcb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fd1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fd4: aastore
      // 2fd5: dup
      // 2fd6: bipush 1
      // 2fd7: new net/minecraft/client/animation/Keyframe
      // 2fda: dup
      // 2fdb: ldc 0.5
      // 2fdd: fconst_0
      // 2fde: fconst_0
      // 2fdf: fconst_0
      // 2fe0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fe3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fe6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fe9: aastore
      // 2fea: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2fed: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2ff0: ldc "right_b_leg"
      // 2ff2: new net/minecraft/client/animation/AnimationChannel
      // 2ff5: dup
      // 2ff6: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2ff9: bipush 3
      // 2ffa: anewarray 24
      // 2ffd: dup
      // 2ffe: bipush 0
      // 2fff: new net/minecraft/client/animation/Keyframe
      // 3002: dup
      // 3003: fconst_0
      // 3004: fconst_0
      // 3005: fconst_0
      // 3006: fconst_0
      // 3007: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 300a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 300d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3010: aastore
      // 3011: dup
      // 3012: bipush 1
      // 3013: new net/minecraft/client/animation/Keyframe
      // 3016: dup
      // 3017: ldc 0.1667
      // 3019: fconst_0
      // 301a: fconst_0
      // 301b: ldc -2.5
      // 301d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3020: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3023: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3026: aastore
      // 3027: dup
      // 3028: bipush 2
      // 3029: new net/minecraft/client/animation/Keyframe
      // 302c: dup
      // 302d: ldc 0.5
      // 302f: fconst_0
      // 3030: fconst_0
      // 3031: fconst_0
      // 3032: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3035: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3038: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 303b: aastore
      // 303c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 303f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3042: ldc "right_b_leg"
      // 3044: new net/minecraft/client/animation/AnimationChannel
      // 3047: dup
      // 3048: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 304b: bipush 2
      // 304c: anewarray 24
      // 304f: dup
      // 3050: bipush 0
      // 3051: new net/minecraft/client/animation/Keyframe
      // 3054: dup
      // 3055: fconst_0
      // 3056: fconst_0
      // 3057: ldc 0.1746
      // 3059: ldc 0.9892
      // 305b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 305e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3061: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3064: aastore
      // 3065: dup
      // 3066: bipush 1
      // 3067: new net/minecraft/client/animation/Keyframe
      // 306a: dup
      // 306b: ldc 0.5
      // 306d: fconst_0
      // 306e: fconst_0
      // 306f: fconst_0
      // 3070: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3073: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3076: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3079: aastore
      // 307a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 307d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3080: ldc "right_b_fore_leg"
      // 3082: new net/minecraft/client/animation/AnimationChannel
      // 3085: dup
      // 3086: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3089: bipush 2
      // 308a: anewarray 24
      // 308d: dup
      // 308e: bipush 0
      // 308f: new net/minecraft/client/animation/Keyframe
      // 3092: dup
      // 3093: fconst_0
      // 3094: ldc -13.7142
      // 3096: ldc 2.6061
      // 3098: ldc 27.5327
      // 309a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 309d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30a3: aastore
      // 30a4: dup
      // 30a5: bipush 1
      // 30a6: new net/minecraft/client/animation/Keyframe
      // 30a9: dup
      // 30aa: ldc 0.5
      // 30ac: fconst_0
      // 30ad: fconst_0
      // 30ae: fconst_0
      // 30af: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30b8: aastore
      // 30b9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 30bc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 30bf: ldc "right_f_leg"
      // 30c1: new net/minecraft/client/animation/AnimationChannel
      // 30c4: dup
      // 30c5: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 30c8: bipush 2
      // 30c9: anewarray 24
      // 30cc: dup
      // 30cd: bipush 0
      // 30ce: new net/minecraft/client/animation/Keyframe
      // 30d1: dup
      // 30d2: fconst_0
      // 30d3: fconst_0
      // 30d4: fconst_0
      // 30d5: fconst_0
      // 30d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30df: aastore
      // 30e0: dup
      // 30e1: bipush 1
      // 30e2: new net/minecraft/client/animation/Keyframe
      // 30e5: dup
      // 30e6: ldc 0.5
      // 30e8: fconst_0
      // 30e9: fconst_0
      // 30ea: fconst_0
      // 30eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30f4: aastore
      // 30f5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 30f8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 30fb: ldc "right_f_leg"
      // 30fd: new net/minecraft/client/animation/AnimationChannel
      // 3100: dup
      // 3101: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3104: bipush 3
      // 3105: anewarray 24
      // 3108: dup
      // 3109: bipush 0
      // 310a: new net/minecraft/client/animation/Keyframe
      // 310d: dup
      // 310e: fconst_0
      // 310f: fconst_0
      // 3110: ldc 2.9535
      // 3112: ldc -0.5254
      // 3114: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3117: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 311a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 311d: aastore
      // 311e: dup
      // 311f: bipush 1
      // 3120: new net/minecraft/client/animation/Keyframe
      // 3123: dup
      // 3124: ldc 0.1667
      // 3126: ldc 0.467
      // 3128: ldc -0.6163
      // 312a: ldc 1.2203
      // 312c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 312f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3132: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3135: aastore
      // 3136: dup
      // 3137: bipush 2
      // 3138: new net/minecraft/client/animation/Keyframe
      // 313b: dup
      // 313c: ldc 0.5
      // 313e: fconst_0
      // 313f: fconst_0
      // 3140: fconst_0
      // 3141: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3144: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3147: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 314a: aastore
      // 314b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 314e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3151: ldc "right_f_fore_leg"
      // 3153: new net/minecraft/client/animation/AnimationChannel
      // 3156: dup
      // 3157: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 315a: bipush 2
      // 315b: anewarray 24
      // 315e: dup
      // 315f: bipush 0
      // 3160: new net/minecraft/client/animation/Keyframe
      // 3163: dup
      // 3164: fconst_0
      // 3165: ldc 22.5
      // 3167: fconst_0
      // 3168: ldc -5.0
      // 316a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 316d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3170: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3173: aastore
      // 3174: dup
      // 3175: bipush 1
      // 3176: new net/minecraft/client/animation/Keyframe
      // 3179: dup
      // 317a: ldc 0.5
      // 317c: fconst_0
      // 317d: fconst_0
      // 317e: fconst_0
      // 317f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3182: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3185: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3188: aastore
      // 3189: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 318c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 318f: ldc "left_f_leg_joint"
      // 3191: new net/minecraft/client/animation/AnimationChannel
      // 3194: dup
      // 3195: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3198: bipush 2
      // 3199: anewarray 24
      // 319c: dup
      // 319d: bipush 0
      // 319e: new net/minecraft/client/animation/Keyframe
      // 31a1: dup
      // 31a2: fconst_0
      // 31a3: ldc 10.0
      // 31a5: fconst_0
      // 31a6: fconst_0
      // 31a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31b0: aastore
      // 31b1: dup
      // 31b2: bipush 1
      // 31b3: new net/minecraft/client/animation/Keyframe
      // 31b6: dup
      // 31b7: ldc 0.5
      // 31b9: fconst_0
      // 31ba: fconst_0
      // 31bb: fconst_0
      // 31bc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31bf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31c2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31c5: aastore
      // 31c6: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 31c9: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 31cc: ldc "left_f_leg_joint"
      // 31ce: new net/minecraft/client/animation/AnimationChannel
      // 31d1: dup
      // 31d2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 31d5: bipush 1
      // 31d6: anewarray 24
      // 31d9: dup
      // 31da: bipush 0
      // 31db: new net/minecraft/client/animation/Keyframe
      // 31de: dup
      // 31df: ldc 0.5
      // 31e1: fconst_0
      // 31e2: fconst_0
      // 31e3: fconst_0
      // 31e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 31e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31ed: aastore
      // 31ee: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 31f1: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 31f4: ldc "right_f_leg_joint"
      // 31f6: new net/minecraft/client/animation/AnimationChannel
      // 31f9: dup
      // 31fa: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 31fd: bipush 2
      // 31fe: anewarray 24
      // 3201: dup
      // 3202: bipush 0
      // 3203: new net/minecraft/client/animation/Keyframe
      // 3206: dup
      // 3207: fconst_0
      // 3208: ldc -29.1134
      // 320a: ldc -15.5554
      // 320c: ldc 3.99
      // 320e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3211: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3214: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3217: aastore
      // 3218: dup
      // 3219: bipush 1
      // 321a: new net/minecraft/client/animation/Keyframe
      // 321d: dup
      // 321e: ldc 0.5
      // 3220: fconst_0
      // 3221: fconst_0
      // 3222: fconst_0
      // 3223: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3226: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3229: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 322c: aastore
      // 322d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3230: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3233: ldc "right_b_leg_joint"
      // 3235: new net/minecraft/client/animation/AnimationChannel
      // 3238: dup
      // 3239: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 323c: bipush 2
      // 323d: anewarray 24
      // 3240: dup
      // 3241: bipush 0
      // 3242: new net/minecraft/client/animation/Keyframe
      // 3245: dup
      // 3246: fconst_0
      // 3247: ldc 21.9529
      // 3249: ldc -4.9358
      // 324b: ldc -12.6843
      // 324d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3250: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3253: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3256: aastore
      // 3257: dup
      // 3258: bipush 1
      // 3259: new net/minecraft/client/animation/Keyframe
      // 325c: dup
      // 325d: ldc 0.5
      // 325f: fconst_0
      // 3260: fconst_0
      // 3261: fconst_0
      // 3262: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3265: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3268: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 326b: aastore
      // 326c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 326f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3272: ldc "left_b_leg_joint"
      // 3274: new net/minecraft/client/animation/AnimationChannel
      // 3277: dup
      // 3278: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 327b: bipush 2
      // 327c: anewarray 24
      // 327f: dup
      // 3280: bipush 0
      // 3281: new net/minecraft/client/animation/Keyframe
      // 3284: dup
      // 3285: fconst_0
      // 3286: ldc -12.3237
      // 3288: ldc 34.5622
      // 328a: ldc 2.8772
      // 328c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 328f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3292: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3295: aastore
      // 3296: dup
      // 3297: bipush 1
      // 3298: new net/minecraft/client/animation/Keyframe
      // 329b: dup
      // 329c: ldc 0.5
      // 329e: fconst_0
      // 329f: fconst_0
      // 32a0: fconst_0
      // 32a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32aa: aastore
      // 32ab: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 32ae: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 32b1: ldc "left_b_leg"
      // 32b3: new net/minecraft/client/animation/AnimationChannel
      // 32b6: dup
      // 32b7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 32ba: bipush 2
      // 32bb: anewarray 24
      // 32be: dup
      // 32bf: bipush 0
      // 32c0: new net/minecraft/client/animation/Keyframe
      // 32c3: dup
      // 32c4: fconst_0
      // 32c5: fconst_0
      // 32c6: fconst_0
      // 32c7: fconst_0
      // 32c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32d1: aastore
      // 32d2: dup
      // 32d3: bipush 1
      // 32d4: new net/minecraft/client/animation/Keyframe
      // 32d7: dup
      // 32d8: ldc 0.5
      // 32da: fconst_0
      // 32db: fconst_0
      // 32dc: fconst_0
      // 32dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32e6: aastore
      // 32e7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 32ea: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 32ed: ldc "left_b_leg"
      // 32ef: new net/minecraft/client/animation/AnimationChannel
      // 32f2: dup
      // 32f3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 32f6: bipush 2
      // 32f7: anewarray 24
      // 32fa: dup
      // 32fb: bipush 0
      // 32fc: new net/minecraft/client/animation/Keyframe
      // 32ff: dup
      // 3300: fconst_0
      // 3301: ldc 0.0578
      // 3303: ldc 0.4035
      // 3305: ldc -3.3315
      // 3307: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 330a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 330d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3310: aastore
      // 3311: dup
      // 3312: bipush 1
      // 3313: new net/minecraft/client/animation/Keyframe
      // 3316: dup
      // 3317: ldc 0.5
      // 3319: fconst_0
      // 331a: fconst_0
      // 331b: fconst_0
      // 331c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 331f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3322: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3325: aastore
      // 3326: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3329: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 332c: ldc "left_b_fore_leg"
      // 332e: new net/minecraft/client/animation/AnimationChannel
      // 3331: dup
      // 3332: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3335: bipush 2
      // 3336: anewarray 24
      // 3339: dup
      // 333a: bipush 0
      // 333b: new net/minecraft/client/animation/Keyframe
      // 333e: dup
      // 333f: fconst_0
      // 3340: ldc 23.8072
      // 3342: ldc 0.6087
      // 3344: ldc -28.9939
      // 3346: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3349: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 334c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 334f: aastore
      // 3350: dup
      // 3351: bipush 1
      // 3352: new net/minecraft/client/animation/Keyframe
      // 3355: dup
      // 3356: ldc 0.5
      // 3358: fconst_0
      // 3359: fconst_0
      // 335a: fconst_0
      // 335b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 335e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3361: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3364: aastore
      // 3365: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3368: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 336b: ldc "right_down_antenna"
      // 336d: new net/minecraft/client/animation/AnimationChannel
      // 3370: dup
      // 3371: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3374: bipush 3
      // 3375: anewarray 24
      // 3378: dup
      // 3379: bipush 0
      // 337a: new net/minecraft/client/animation/Keyframe
      // 337d: dup
      // 337e: fconst_0
      // 337f: fconst_0
      // 3380: fconst_0
      // 3381: fconst_0
      // 3382: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3385: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3388: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 338b: aastore
      // 338c: dup
      // 338d: bipush 1
      // 338e: new net/minecraft/client/animation/Keyframe
      // 3391: dup
      // 3392: ldc 0.2917
      // 3394: ldc_w 8.2708
      // 3397: ldc_w 9.7081
      // 339a: ldc_w 1.6114
      // 339d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33a6: aastore
      // 33a7: dup
      // 33a8: bipush 2
      // 33a9: new net/minecraft/client/animation/Keyframe
      // 33ac: dup
      // 33ad: ldc 0.5
      // 33af: fconst_0
      // 33b0: fconst_0
      // 33b1: fconst_0
      // 33b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33bb: aastore
      // 33bc: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 33bf: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 33c2: ldc "left_down_antenna"
      // 33c4: new net/minecraft/client/animation/AnimationChannel
      // 33c7: dup
      // 33c8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 33cb: bipush 3
      // 33cc: anewarray 24
      // 33cf: dup
      // 33d0: bipush 0
      // 33d1: new net/minecraft/client/animation/Keyframe
      // 33d4: dup
      // 33d5: fconst_0
      // 33d6: fconst_0
      // 33d7: fconst_0
      // 33d8: fconst_0
      // 33d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33e2: aastore
      // 33e3: dup
      // 33e4: bipush 1
      // 33e5: new net/minecraft/client/animation/Keyframe
      // 33e8: dup
      // 33e9: ldc 0.2917
      // 33eb: ldc_w 8.2708
      // 33ee: ldc_w -9.7081
      // 33f1: ldc_w -1.6114
      // 33f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33fd: aastore
      // 33fe: dup
      // 33ff: bipush 2
      // 3400: new net/minecraft/client/animation/Keyframe
      // 3403: dup
      // 3404: ldc 0.5
      // 3406: fconst_0
      // 3407: fconst_0
      // 3408: fconst_0
      // 3409: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 340c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 340f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3412: aastore
      // 3413: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3416: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3419: ldc "right_front_arm_rotator"
      // 341b: new net/minecraft/client/animation/AnimationChannel
      // 341e: dup
      // 341f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3422: bipush 2
      // 3423: anewarray 24
      // 3426: dup
      // 3427: bipush 0
      // 3428: new net/minecraft/client/animation/Keyframe
      // 342b: dup
      // 342c: fconst_0
      // 342d: fconst_0
      // 342e: fconst_0
      // 342f: fconst_0
      // 3430: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3433: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3436: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3439: aastore
      // 343a: dup
      // 343b: bipush 1
      // 343c: new net/minecraft/client/animation/Keyframe
      // 343f: dup
      // 3440: ldc 0.5
      // 3442: fconst_0
      // 3443: fconst_0
      // 3444: fconst_0
      // 3445: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3448: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 344b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 344e: aastore
      // 344f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3452: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3455: ldc "legs"
      // 3457: new net/minecraft/client/animation/AnimationChannel
      // 345a: dup
      // 345b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 345e: bipush 2
      // 345f: anewarray 24
      // 3462: dup
      // 3463: bipush 0
      // 3464: new net/minecraft/client/animation/Keyframe
      // 3467: dup
      // 3468: fconst_0
      // 3469: ldc -14.9042
      // 346b: ldc -4.8876
      // 346d: ldc -1.4153
      // 346f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3472: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3475: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3478: aastore
      // 3479: dup
      // 347a: bipush 1
      // 347b: new net/minecraft/client/animation/Keyframe
      // 347e: dup
      // 347f: ldc 0.5
      // 3481: fconst_0
      // 3482: fconst_0
      // 3483: fconst_0
      // 3484: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3487: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 348a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 348d: aastore
      // 348e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3491: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3494: ldc "legs"
      // 3496: new net/minecraft/client/animation/AnimationChannel
      // 3499: dup
      // 349a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 349d: bipush 2
      // 349e: anewarray 24
      // 34a1: dup
      // 34a2: bipush 0
      // 34a3: new net/minecraft/client/animation/Keyframe
      // 34a6: dup
      // 34a7: fconst_0
      // 34a8: fconst_0
      // 34a9: fconst_0
      // 34aa: fconst_0
      // 34ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 34ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34b4: aastore
      // 34b5: dup
      // 34b6: bipush 1
      // 34b7: new net/minecraft/client/animation/Keyframe
      // 34ba: dup
      // 34bb: ldc 0.5
      // 34bd: fconst_0
      // 34be: fconst_0
      // 34bf: fconst_0
      // 34c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 34c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34c9: aastore
      // 34ca: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 34cd: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 34d0: ldc "head_tail"
      // 34d2: new net/minecraft/client/animation/AnimationChannel
      // 34d5: dup
      // 34d6: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 34d9: bipush 2
      // 34da: anewarray 24
      // 34dd: dup
      // 34de: bipush 0
      // 34df: new net/minecraft/client/animation/Keyframe
      // 34e2: dup
      // 34e3: fconst_0
      // 34e4: fconst_0
      // 34e5: fconst_0
      // 34e6: fconst_0
      // 34e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34f0: aastore
      // 34f1: dup
      // 34f2: bipush 1
      // 34f3: new net/minecraft/client/animation/Keyframe
      // 34f6: dup
      // 34f7: ldc 0.5
      // 34f9: fconst_0
      // 34fa: fconst_0
      // 34fb: fconst_0
      // 34fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3502: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3505: aastore
      // 3506: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3509: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 350c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 350f: putstatic com/github/L_Ender/cataclysm/client/animation/Clawdian_Skill_Animation.CHARGE_END Lnet/minecraft/client/animation/AnimationDefinition;
      // 3512: ldc_w 2.625
      // 3515: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3518: ldc "mid_root"
      // 351a: new net/minecraft/client/animation/AnimationChannel
      // 351d: dup
      // 351e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3521: bipush 7
      // 3523: anewarray 24
      // 3526: dup
      // 3527: bipush 0
      // 3528: new net/minecraft/client/animation/Keyframe
      // 352b: dup
      // 352c: fconst_0
      // 352d: fconst_0
      // 352e: fconst_0
      // 352f: fconst_0
      // 3530: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3533: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3536: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3539: aastore
      // 353a: dup
      // 353b: bipush 1
      // 353c: new net/minecraft/client/animation/Keyframe
      // 353f: dup
      // 3540: ldc 0.5417
      // 3542: fconst_0
      // 3543: fconst_0
      // 3544: fconst_0
      // 3545: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3548: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 354b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 354e: aastore
      // 354f: dup
      // 3550: bipush 2
      // 3551: new net/minecraft/client/animation/Keyframe
      // 3554: dup
      // 3555: ldc 0.8333
      // 3557: ldc_w -31.7903
      // 355a: ldc_w 11.1657
      // 355d: ldc_w 20.3131
      // 3560: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3563: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3566: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3569: aastore
      // 356a: dup
      // 356b: bipush 3
      // 356c: new net/minecraft/client/animation/Keyframe
      // 356f: dup
      // 3570: ldc_w 1.25
      // 3573: ldc_w -34.2547
      // 3576: ldc_w 12.0496
      // 3579: ldc_w 21.0909
      // 357c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 357f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3582: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3585: aastore
      // 3586: dup
      // 3587: bipush 4
      // 3588: new net/minecraft/client/animation/Keyframe
      // 358b: dup
      // 358c: ldc_w 1.375
      // 358f: fconst_0
      // 3590: fconst_0
      // 3591: fconst_0
      // 3592: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3595: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3598: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 359b: aastore
      // 359c: dup
      // 359d: bipush 5
      // 359e: new net/minecraft/client/animation/Keyframe
      // 35a1: dup
      // 35a2: ldc_w 1.5417
      // 35a5: fconst_0
      // 35a6: fconst_0
      // 35a7: fconst_0
      // 35a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 35ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35b1: aastore
      // 35b2: dup
      // 35b3: bipush 6
      // 35b5: new net/minecraft/client/animation/Keyframe
      // 35b8: dup
      // 35b9: ldc_w 2.625
      // 35bc: fconst_0
      // 35bd: fconst_0
      // 35be: fconst_0
      // 35bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 35c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35c8: aastore
      // 35c9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 35cc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 35cf: ldc "mid_root"
      // 35d1: new net/minecraft/client/animation/AnimationChannel
      // 35d4: dup
      // 35d5: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 35d8: bipush 7
      // 35da: anewarray 24
      // 35dd: dup
      // 35de: bipush 0
      // 35df: new net/minecraft/client/animation/Keyframe
      // 35e2: dup
      // 35e3: fconst_0
      // 35e4: fconst_0
      // 35e5: fconst_0
      // 35e6: fconst_0
      // 35e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 35ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35f0: aastore
      // 35f1: dup
      // 35f2: bipush 1
      // 35f3: new net/minecraft/client/animation/Keyframe
      // 35f6: dup
      // 35f7: ldc 0.5417
      // 35f9: fconst_0
      // 35fa: ldc_w -4.0
      // 35fd: fconst_0
      // 35fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3601: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3604: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3607: aastore
      // 3608: dup
      // 3609: bipush 2
      // 360a: new net/minecraft/client/animation/Keyframe
      // 360d: dup
      // 360e: ldc 0.8333
      // 3610: fconst_0
      // 3611: ldc_w 8.0
      // 3614: ldc_w 8.0
      // 3617: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 361a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 361d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3620: aastore
      // 3621: dup
      // 3622: bipush 3
      // 3623: new net/minecraft/client/animation/Keyframe
      // 3626: dup
      // 3627: ldc_w 1.25
      // 362a: fconst_0
      // 362b: ldc_w 8.0
      // 362e: ldc_w 9.0
      // 3631: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3634: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3637: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 363a: aastore
      // 363b: dup
      // 363c: bipush 4
      // 363d: new net/minecraft/client/animation/Keyframe
      // 3640: dup
      // 3641: ldc_w 1.375
      // 3644: fconst_0
      // 3645: ldc_w -4.0
      // 3648: fconst_0
      // 3649: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 364c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 364f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3652: aastore
      // 3653: dup
      // 3654: bipush 5
      // 3655: new net/minecraft/client/animation/Keyframe
      // 3658: dup
      // 3659: ldc_w 1.5417
      // 365c: fconst_0
      // 365d: ldc_w -4.0
      // 3660: fconst_0
      // 3661: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3664: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3667: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 366a: aastore
      // 366b: dup
      // 366c: bipush 6
      // 366e: new net/minecraft/client/animation/Keyframe
      // 3671: dup
      // 3672: ldc_w 2.625
      // 3675: fconst_0
      // 3676: fconst_0
      // 3677: fconst_0
      // 3678: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 367b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 367e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3681: aastore
      // 3682: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3685: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3688: ldc "lower_body"
      // 368a: new net/minecraft/client/animation/AnimationChannel
      // 368d: dup
      // 368e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3691: bipush 3
      // 3692: anewarray 24
      // 3695: dup
      // 3696: bipush 0
      // 3697: new net/minecraft/client/animation/Keyframe
      // 369a: dup
      // 369b: fconst_0
      // 369c: fconst_0
      // 369d: fconst_0
      // 369e: fconst_0
      // 369f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 36a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36a8: aastore
      // 36a9: dup
      // 36aa: bipush 1
      // 36ab: new net/minecraft/client/animation/Keyframe
      // 36ae: dup
      // 36af: ldc_w 1.25
      // 36b2: ldc -5.0
      // 36b4: fconst_0
      // 36b5: fconst_0
      // 36b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 36b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36bf: aastore
      // 36c0: dup
      // 36c1: bipush 2
      // 36c2: new net/minecraft/client/animation/Keyframe
      // 36c5: dup
      // 36c6: ldc_w 2.625
      // 36c9: fconst_0
      // 36ca: fconst_0
      // 36cb: fconst_0
      // 36cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 36cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36d5: aastore
      // 36d6: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 36d9: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 36dc: ldc "lower_body"
      // 36de: new net/minecraft/client/animation/AnimationChannel
      // 36e1: dup
      // 36e2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 36e5: bipush 2
      // 36e6: anewarray 24
      // 36e9: dup
      // 36ea: bipush 0
      // 36eb: new net/minecraft/client/animation/Keyframe
      // 36ee: dup
      // 36ef: fconst_0
      // 36f0: fconst_0
      // 36f1: fconst_0
      // 36f2: fconst_0
      // 36f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 36f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36fc: aastore
      // 36fd: dup
      // 36fe: bipush 1
      // 36ff: new net/minecraft/client/animation/Keyframe
      // 3702: dup
      // 3703: ldc_w 2.625
      // 3706: fconst_0
      // 3707: fconst_0
      // 3708: fconst_0
      // 3709: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 370c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 370f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3712: aastore
      // 3713: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3716: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3719: ldc "pelvis"
      // 371b: new net/minecraft/client/animation/AnimationChannel
      // 371e: dup
      // 371f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3722: bipush 8
      // 3724: anewarray 24
      // 3727: dup
      // 3728: bipush 0
      // 3729: new net/minecraft/client/animation/Keyframe
      // 372c: dup
      // 372d: fconst_0
      // 372e: fconst_0
      // 372f: fconst_0
      // 3730: fconst_0
      // 3731: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3734: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3737: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 373a: aastore
      // 373b: dup
      // 373c: bipush 1
      // 373d: new net/minecraft/client/animation/Keyframe
      // 3740: dup
      // 3741: ldc 0.5417
      // 3743: ldc_w 12.5778
      // 3746: ldc_w -4.98
      // 3749: ldc_w -0.4531
      // 374c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 374f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3752: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3755: aastore
      // 3756: dup
      // 3757: bipush 2
      // 3758: new net/minecraft/client/animation/Keyframe
      // 375b: dup
      // 375c: ldc 0.8333
      // 375e: ldc_w 35.2528
      // 3761: ldc_w -7.7648
      // 3764: ldc_w -2.1632
      // 3767: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 376a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 376d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3770: aastore
      // 3771: dup
      // 3772: bipush 3
      // 3773: new net/minecraft/client/animation/Keyframe
      // 3776: dup
      // 3777: ldc_w 1.25
      // 377a: ldc_w 22.68
      // 377d: ldc_w -7.1348
      // 3780: ldc_w -1.6115
      // 3783: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3786: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3789: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 378c: aastore
      // 378d: dup
      // 378e: bipush 4
      // 378f: new net/minecraft/client/animation/Keyframe
      // 3792: dup
      // 3793: ldc_w 1.375
      // 3796: ldc_w 35.574
      // 3799: ldc_w -4.1492
      // 379c: ldc_w -3.4836
      // 379f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 37a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37a8: aastore
      // 37a9: dup
      // 37aa: bipush 5
      // 37ab: new net/minecraft/client/animation/Keyframe
      // 37ae: dup
      // 37af: ldc_w 1.5417
      // 37b2: ldc_w 56.6952
      // 37b5: ldc_w -3.9926
      // 37b8: ldc_w -3.5818
      // 37bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 37be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37c4: aastore
      // 37c5: dup
      // 37c6: bipush 6
      // 37c8: new net/minecraft/client/animation/Keyframe
      // 37cb: dup
      // 37cc: ldc_w 1.9583
      // 37cf: ldc_w 40.574
      // 37d2: ldc_w -4.1492
      // 37d5: ldc_w -3.4836
      // 37d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 37db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37e1: aastore
      // 37e2: dup
      // 37e3: bipush 7
      // 37e5: new net/minecraft/client/animation/Keyframe
      // 37e8: dup
      // 37e9: ldc_w 2.625
      // 37ec: fconst_0
      // 37ed: fconst_0
      // 37ee: fconst_0
      // 37ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 37f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37f8: aastore
      // 37f9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 37fc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 37ff: ldc "pelvis"
      // 3801: new net/minecraft/client/animation/AnimationChannel
      // 3804: dup
      // 3805: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3808: bipush 2
      // 3809: anewarray 24
      // 380c: dup
      // 380d: bipush 0
      // 380e: new net/minecraft/client/animation/Keyframe
      // 3811: dup
      // 3812: fconst_0
      // 3813: fconst_0
      // 3814: fconst_0
      // 3815: fconst_0
      // 3816: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3819: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 381c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 381f: aastore
      // 3820: dup
      // 3821: bipush 1
      // 3822: new net/minecraft/client/animation/Keyframe
      // 3825: dup
      // 3826: ldc_w 2.625
      // 3829: fconst_0
      // 382a: fconst_0
      // 382b: fconst_0
      // 382c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 382f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3832: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3835: aastore
      // 3836: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3839: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 383c: ldc "body"
      // 383e: new net/minecraft/client/animation/AnimationChannel
      // 3841: dup
      // 3842: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3845: bipush 7
      // 3847: anewarray 24
      // 384a: dup
      // 384b: bipush 0
      // 384c: new net/minecraft/client/animation/Keyframe
      // 384f: dup
      // 3850: fconst_0
      // 3851: fconst_0
      // 3852: fconst_0
      // 3853: fconst_0
      // 3854: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3857: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 385a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 385d: aastore
      // 385e: dup
      // 385f: bipush 1
      // 3860: new net/minecraft/client/animation/Keyframe
      // 3863: dup
      // 3864: ldc 0.5417
      // 3866: ldc 10.0
      // 3868: fconst_0
      // 3869: fconst_0
      // 386a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 386d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3870: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3873: aastore
      // 3874: dup
      // 3875: bipush 2
      // 3876: new net/minecraft/client/animation/Keyframe
      // 3879: dup
      // 387a: ldc 0.8333
      // 387c: ldc_w -1.7659
      // 387f: ldc_w 6.8454
      // 3882: ldc_w 0.4483
      // 3885: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3888: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 388b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 388e: aastore
      // 388f: dup
      // 3890: bipush 3
      // 3891: new net/minecraft/client/animation/Keyframe
      // 3894: dup
      // 3895: ldc_w 1.25
      // 3898: ldc_w -23.0705
      // 389b: ldc_w 13.5499
      // 389e: ldc_w 3.6565
      // 38a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38aa: aastore
      // 38ab: dup
      // 38ac: bipush 4
      // 38ad: new net/minecraft/client/animation/Keyframe
      // 38b0: dup
      // 38b1: ldc_w 1.375
      // 38b4: ldc_w -2.4771
      // 38b7: ldc_w 0.4647
      // 38ba: ldc_w -2.0989
      // 38bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38c6: aastore
      // 38c7: dup
      // 38c8: bipush 5
      // 38c9: new net/minecraft/client/animation/Keyframe
      // 38cc: dup
      // 38cd: ldc_w 1.7083
      // 38d0: ldc_w 5.0229
      // 38d3: ldc_w 0.4647
      // 38d6: ldc_w -2.0989
      // 38d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38e2: aastore
      // 38e3: dup
      // 38e4: bipush 6
      // 38e6: new net/minecraft/client/animation/Keyframe
      // 38e9: dup
      // 38ea: ldc_w 2.625
      // 38ed: fconst_0
      // 38ee: fconst_0
      // 38ef: fconst_0
      // 38f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38f9: aastore
      // 38fa: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 38fd: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3900: ldc "neck"
      // 3902: new net/minecraft/client/animation/AnimationChannel
      // 3905: dup
      // 3906: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3909: bipush 8
      // 390b: anewarray 24
      // 390e: dup
      // 390f: bipush 0
      // 3910: new net/minecraft/client/animation/Keyframe
      // 3913: dup
      // 3914: fconst_0
      // 3915: fconst_0
      // 3916: fconst_0
      // 3917: fconst_0
      // 3918: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 391b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 391e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3921: aastore
      // 3922: dup
      // 3923: bipush 1
      // 3924: new net/minecraft/client/animation/Keyframe
      // 3927: dup
      // 3928: ldc 0.5417
      // 392a: ldc_w 9.8264
      // 392d: ldc_w -0.0591
      // 3930: ldc_w -1.6334
      // 3933: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3936: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3939: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 393c: aastore
      // 393d: dup
      // 393e: bipush 2
      // 393f: new net/minecraft/client/animation/Keyframe
      // 3942: dup
      // 3943: ldc 0.8333
      // 3945: ldc_w 4.989
      // 3948: ldc_w -0.2383
      // 394b: ldc_w -0.1016
      // 394e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3951: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3954: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3957: aastore
      // 3958: dup
      // 3959: bipush 3
      // 395a: new net/minecraft/client/animation/Keyframe
      // 395d: dup
      // 395e: ldc_w 1.25
      // 3961: ldc_w 17.1873
      // 3964: ldc_w -7.7688
      // 3967: ldc_w -14.886
      // 396a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 396d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3970: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3973: aastore
      // 3974: dup
      // 3975: bipush 4
      // 3976: new net/minecraft/client/animation/Keyframe
      // 3979: dup
      // 397a: ldc_w 1.375
      // 397d: ldc_w 2.5
      // 3980: fconst_0
      // 3981: fconst_0
      // 3982: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3985: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3988: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 398b: aastore
      // 398c: dup
      // 398d: bipush 5
      // 398e: new net/minecraft/client/animation/Keyframe
      // 3991: dup
      // 3992: ldc_w 1.5417
      // 3995: ldc_w 2.5
      // 3998: fconst_0
      // 3999: fconst_0
      // 399a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 399d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39a3: aastore
      // 39a4: dup
      // 39a5: bipush 6
      // 39a7: new net/minecraft/client/animation/Keyframe
      // 39aa: dup
      // 39ab: ldc_w 1.7083
      // 39ae: ldc 10.0
      // 39b0: fconst_0
      // 39b1: fconst_0
      // 39b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39bb: aastore
      // 39bc: dup
      // 39bd: bipush 7
      // 39bf: new net/minecraft/client/animation/Keyframe
      // 39c2: dup
      // 39c3: ldc_w 2.625
      // 39c6: fconst_0
      // 39c7: fconst_0
      // 39c8: fconst_0
      // 39c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39d2: aastore
      // 39d3: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 39d6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 39d9: ldc "head"
      // 39db: new net/minecraft/client/animation/AnimationChannel
      // 39de: dup
      // 39df: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 39e2: bipush 8
      // 39e4: anewarray 24
      // 39e7: dup
      // 39e8: bipush 0
      // 39e9: new net/minecraft/client/animation/Keyframe
      // 39ec: dup
      // 39ed: fconst_0
      // 39ee: fconst_0
      // 39ef: fconst_0
      // 39f0: fconst_0
      // 39f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39fa: aastore
      // 39fb: dup
      // 39fc: bipush 1
      // 39fd: new net/minecraft/client/animation/Keyframe
      // 3a00: dup
      // 3a01: ldc 0.5417
      // 3a03: ldc_w -4.8808
      // 3a06: ldc_w 2.2179
      // 3a09: ldc_w 1.1599
      // 3a0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a15: aastore
      // 3a16: dup
      // 3a17: bipush 2
      // 3a18: new net/minecraft/client/animation/Keyframe
      // 3a1b: dup
      // 3a1c: ldc 0.9167
      // 3a1e: ldc_w -12.635
      // 3a21: ldc_w -5.2164
      // 3a24: ldc_w -2.4982
      // 3a27: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a30: aastore
      // 3a31: dup
      // 3a32: bipush 3
      // 3a33: new net/minecraft/client/animation/Keyframe
      // 3a36: dup
      // 3a37: ldc_w 1.25
      // 3a3a: ldc_w 2.5683
      // 3a3d: ldc_w 0.7455
      // 3a40: ldc_w 2.0588
      // 3a43: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a46: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a49: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a4c: aastore
      // 3a4d: dup
      // 3a4e: bipush 4
      // 3a4f: new net/minecraft/client/animation/Keyframe
      // 3a52: dup
      // 3a53: ldc_w 1.375
      // 3a56: ldc_w -4.8808
      // 3a59: ldc_w 2.2179
      // 3a5c: ldc_w 1.1599
      // 3a5f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a62: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a65: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a68: aastore
      // 3a69: dup
      // 3a6a: bipush 5
      // 3a6b: new net/minecraft/client/animation/Keyframe
      // 3a6e: dup
      // 3a6f: ldc_w 1.5417
      // 3a72: ldc_w -4.8808
      // 3a75: ldc_w 2.2179
      // 3a78: ldc_w 1.1599
      // 3a7b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a7e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a81: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a84: aastore
      // 3a85: dup
      // 3a86: bipush 6
      // 3a88: new net/minecraft/client/animation/Keyframe
      // 3a8b: dup
      // 3a8c: ldc_w 1.7083
      // 3a8f: ldc_w -14.8808
      // 3a92: ldc_w 2.2179
      // 3a95: ldc_w 1.1599
      // 3a98: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3aa1: aastore
      // 3aa2: dup
      // 3aa3: bipush 7
      // 3aa5: new net/minecraft/client/animation/Keyframe
      // 3aa8: dup
      // 3aa9: ldc_w 2.625
      // 3aac: fconst_0
      // 3aad: fconst_0
      // 3aae: fconst_0
      // 3aaf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ab2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ab5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ab8: aastore
      // 3ab9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3abc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3abf: ldc "left_arm"
      // 3ac1: new net/minecraft/client/animation/AnimationChannel
      // 3ac4: dup
      // 3ac5: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3ac8: bipush 7
      // 3aca: anewarray 24
      // 3acd: dup
      // 3ace: bipush 0
      // 3acf: new net/minecraft/client/animation/Keyframe
      // 3ad2: dup
      // 3ad3: fconst_0
      // 3ad4: fconst_0
      // 3ad5: fconst_0
      // 3ad6: fconst_0
      // 3ad7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ada: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3add: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ae0: aastore
      // 3ae1: dup
      // 3ae2: bipush 1
      // 3ae3: new net/minecraft/client/animation/Keyframe
      // 3ae6: dup
      // 3ae7: ldc 0.8333
      // 3ae9: ldc_w -2.3453
      // 3aec: ldc_w 0.6217
      // 3aef: ldc_w -18.0871
      // 3af2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3af5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3af8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3afb: aastore
      // 3afc: dup
      // 3afd: bipush 2
      // 3afe: new net/minecraft/client/animation/Keyframe
      // 3b01: dup
      // 3b02: ldc_w 1.25
      // 3b05: ldc_w -16.8295
      // 3b08: ldc_w 10.5383
      // 3b0b: ldc_w -29.8329
      // 3b0e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b11: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b14: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b17: aastore
      // 3b18: dup
      // 3b19: bipush 3
      // 3b1a: new net/minecraft/client/animation/Keyframe
      // 3b1d: dup
      // 3b1e: ldc_w 1.4583
      // 3b21: ldc_w 3.5432
      // 3b24: ldc_w -4.1242
      // 3b27: ldc_w -24.8817
      // 3b2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b33: aastore
      // 3b34: dup
      // 3b35: bipush 4
      // 3b36: new net/minecraft/client/animation/Keyframe
      // 3b39: dup
      // 3b3a: ldc_w 1.8333
      // 3b3d: ldc_w -18.8669
      // 3b40: ldc_w -2.6328
      // 3b43: ldc_w 10.1294
      // 3b46: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b49: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b4c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b4f: aastore
      // 3b50: dup
      // 3b51: bipush 5
      // 3b52: new net/minecraft/client/animation/Keyframe
      // 3b55: dup
      // 3b56: ldc_w 2.1667
      // 3b59: ldc_w -47.0359
      // 3b5c: ldc_w -8.2496
      // 3b5f: ldc_w 35.8375
      // 3b62: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b6b: aastore
      // 3b6c: dup
      // 3b6d: bipush 6
      // 3b6f: new net/minecraft/client/animation/Keyframe
      // 3b72: dup
      // 3b73: ldc_w 2.625
      // 3b76: fconst_0
      // 3b77: fconst_0
      // 3b78: fconst_0
      // 3b79: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3b7c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b7f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b82: aastore
      // 3b83: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3b86: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3b89: ldc "left_front_arm"
      // 3b8b: new net/minecraft/client/animation/AnimationChannel
      // 3b8e: dup
      // 3b8f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3b92: bipush 8
      // 3b94: anewarray 24
      // 3b97: dup
      // 3b98: bipush 0
      // 3b99: new net/minecraft/client/animation/Keyframe
      // 3b9c: dup
      // 3b9d: fconst_0
      // 3b9e: fconst_0
      // 3b9f: fconst_0
      // 3ba0: fconst_0
      // 3ba1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ba4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ba7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3baa: aastore
      // 3bab: dup
      // 3bac: bipush 1
      // 3bad: new net/minecraft/client/animation/Keyframe
      // 3bb0: dup
      // 3bb1: ldc 0.5417
      // 3bb3: ldc 10.0
      // 3bb5: fconst_0
      // 3bb6: fconst_0
      // 3bb7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3bba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bbd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bc0: aastore
      // 3bc1: dup
      // 3bc2: bipush 2
      // 3bc3: new net/minecraft/client/animation/Keyframe
      // 3bc6: dup
      // 3bc7: ldc 0.8333
      // 3bc9: fconst_0
      // 3bca: fconst_0
      // 3bcb: fconst_0
      // 3bcc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3bcf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bd2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bd5: aastore
      // 3bd6: dup
      // 3bd7: bipush 3
      // 3bd8: new net/minecraft/client/animation/Keyframe
      // 3bdb: dup
      // 3bdc: ldc_w 1.25
      // 3bdf: ldc -2.5
      // 3be1: fconst_0
      // 3be2: fconst_0
      // 3be3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3be6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3be9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bec: aastore
      // 3bed: dup
      // 3bee: bipush 4
      // 3bef: new net/minecraft/client/animation/Keyframe
      // 3bf2: dup
      // 3bf3: ldc_w 1.375
      // 3bf6: ldc 10.0
      // 3bf8: fconst_0
      // 3bf9: fconst_0
      // 3bfa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3bfd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c00: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c03: aastore
      // 3c04: dup
      // 3c05: bipush 5
      // 3c06: new net/minecraft/client/animation/Keyframe
      // 3c09: dup
      // 3c0a: ldc_w 1.5417
      // 3c0d: ldc 10.0
      // 3c0f: fconst_0
      // 3c10: fconst_0
      // 3c11: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c14: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c17: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c1a: aastore
      // 3c1b: dup
      // 3c1c: bipush 6
      // 3c1e: new net/minecraft/client/animation/Keyframe
      // 3c21: dup
      // 3c22: ldc_w 1.8333
      // 3c25: ldc -2.5
      // 3c27: fconst_0
      // 3c28: fconst_0
      // 3c29: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c2c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c2f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c32: aastore
      // 3c33: dup
      // 3c34: bipush 7
      // 3c36: new net/minecraft/client/animation/Keyframe
      // 3c39: dup
      // 3c3a: ldc_w 2.625
      // 3c3d: fconst_0
      // 3c3e: fconst_0
      // 3c3f: fconst_0
      // 3c40: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c49: aastore
      // 3c4a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3c4d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3c50: ldc "left_claw"
      // 3c52: new net/minecraft/client/animation/AnimationChannel
      // 3c55: dup
      // 3c56: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3c59: bipush 7
      // 3c5b: anewarray 24
      // 3c5e: dup
      // 3c5f: bipush 0
      // 3c60: new net/minecraft/client/animation/Keyframe
      // 3c63: dup
      // 3c64: fconst_0
      // 3c65: fconst_0
      // 3c66: fconst_0
      // 3c67: fconst_0
      // 3c68: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c6b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c6e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c71: aastore
      // 3c72: dup
      // 3c73: bipush 1
      // 3c74: new net/minecraft/client/animation/Keyframe
      // 3c77: dup
      // 3c78: ldc 0.5417
      // 3c7a: ldc_w -10.0
      // 3c7d: fconst_0
      // 3c7e: fconst_0
      // 3c7f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c82: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c85: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c88: aastore
      // 3c89: dup
      // 3c8a: bipush 2
      // 3c8b: new net/minecraft/client/animation/Keyframe
      // 3c8e: dup
      // 3c8f: ldc 0.8333
      // 3c91: ldc -5.0
      // 3c93: fconst_0
      // 3c94: fconst_0
      // 3c95: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3c98: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c9b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c9e: aastore
      // 3c9f: dup
      // 3ca0: bipush 3
      // 3ca1: new net/minecraft/client/animation/Keyframe
      // 3ca4: dup
      // 3ca5: ldc_w 1.25
      // 3ca8: ldc -5.0
      // 3caa: fconst_0
      // 3cab: fconst_0
      // 3cac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3caf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cb2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cb5: aastore
      // 3cb6: dup
      // 3cb7: bipush 4
      // 3cb8: new net/minecraft/client/animation/Keyframe
      // 3cbb: dup
      // 3cbc: ldc_w 1.375
      // 3cbf: ldc_w -10.0
      // 3cc2: fconst_0
      // 3cc3: fconst_0
      // 3cc4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cc7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ccd: aastore
      // 3cce: dup
      // 3ccf: bipush 5
      // 3cd0: new net/minecraft/client/animation/Keyframe
      // 3cd3: dup
      // 3cd4: ldc_w 1.5417
      // 3cd7: ldc_w -10.0
      // 3cda: fconst_0
      // 3cdb: fconst_0
      // 3cdc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cdf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ce2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ce5: aastore
      // 3ce6: dup
      // 3ce7: bipush 6
      // 3ce9: new net/minecraft/client/animation/Keyframe
      // 3cec: dup
      // 3ced: ldc_w 2.625
      // 3cf0: fconst_0
      // 3cf1: fconst_0
      // 3cf2: fconst_0
      // 3cf3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3cf6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cf9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cfc: aastore
      // 3cfd: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3d00: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3d03: ldc "right_arm"
      // 3d05: new net/minecraft/client/animation/AnimationChannel
      // 3d08: dup
      // 3d09: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3d0c: bipush 8
      // 3d0e: anewarray 24
      // 3d11: dup
      // 3d12: bipush 0
      // 3d13: new net/minecraft/client/animation/Keyframe
      // 3d16: dup
      // 3d17: fconst_0
      // 3d18: fconst_0
      // 3d19: fconst_0
      // 3d1a: fconst_0
      // 3d1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d24: aastore
      // 3d25: dup
      // 3d26: bipush 1
      // 3d27: new net/minecraft/client/animation/Keyframe
      // 3d2a: dup
      // 3d2b: ldc 0.5417
      // 3d2d: ldc -5.0
      // 3d2f: fconst_0
      // 3d30: fconst_0
      // 3d31: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d3a: aastore
      // 3d3b: dup
      // 3d3c: bipush 2
      // 3d3d: new net/minecraft/client/animation/Keyframe
      // 3d40: dup
      // 3d41: ldc 0.8333
      // 3d43: ldc_w -9.0704
      // 3d46: ldc_w 4.6461
      // 3d49: ldc_w 10.28
      // 3d4c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d4f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d52: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d55: aastore
      // 3d56: dup
      // 3d57: bipush 3
      // 3d58: new net/minecraft/client/animation/Keyframe
      // 3d5b: dup
      // 3d5c: ldc_w 1.25
      // 3d5f: ldc_w -13.1861
      // 3d62: ldc_w 2.1948
      // 3d65: ldc_w 13.5714
      // 3d68: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d6b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d6e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d71: aastore
      // 3d72: dup
      // 3d73: bipush 4
      // 3d74: new net/minecraft/client/animation/Keyframe
      // 3d77: dup
      // 3d78: ldc_w 1.375
      // 3d7b: ldc -5.0
      // 3d7d: fconst_0
      // 3d7e: fconst_0
      // 3d7f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d82: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d85: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d88: aastore
      // 3d89: dup
      // 3d8a: bipush 5
      // 3d8b: new net/minecraft/client/animation/Keyframe
      // 3d8e: dup
      // 3d8f: ldc_w 1.5417
      // 3d92: ldc -5.0
      // 3d94: fconst_0
      // 3d95: fconst_0
      // 3d96: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3d99: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d9c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d9f: aastore
      // 3da0: dup
      // 3da1: bipush 6
      // 3da3: new net/minecraft/client/animation/Keyframe
      // 3da6: dup
      // 3da7: ldc_w 1.7083
      // 3daa: ldc_w 2.5
      // 3dad: fconst_0
      // 3dae: fconst_0
      // 3daf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3db2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3db5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3db8: aastore
      // 3db9: dup
      // 3dba: bipush 7
      // 3dbc: new net/minecraft/client/animation/Keyframe
      // 3dbf: dup
      // 3dc0: ldc_w 2.625
      // 3dc3: fconst_0
      // 3dc4: fconst_0
      // 3dc5: fconst_0
      // 3dc6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3dc9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dcc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3dcf: aastore
      // 3dd0: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3dd3: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3dd6: ldc "right_front_arm"
      // 3dd8: new net/minecraft/client/animation/AnimationChannel
      // 3ddb: dup
      // 3ddc: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3ddf: bipush 2
      // 3de0: anewarray 24
      // 3de3: dup
      // 3de4: bipush 0
      // 3de5: new net/minecraft/client/animation/Keyframe
      // 3de8: dup
      // 3de9: fconst_0
      // 3dea: fconst_0
      // 3deb: fconst_0
      // 3dec: fconst_0
      // 3ded: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3df0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3df3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3df6: aastore
      // 3df7: dup
      // 3df8: bipush 1
      // 3df9: new net/minecraft/client/animation/Keyframe
      // 3dfc: dup
      // 3dfd: ldc_w 2.625
      // 3e00: fconst_0
      // 3e01: fconst_0
      // 3e02: fconst_0
      // 3e03: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e06: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e09: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e0c: aastore
      // 3e0d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3e10: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3e13: ldc "right_claw"
      // 3e15: new net/minecraft/client/animation/AnimationChannel
      // 3e18: dup
      // 3e19: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3e1c: bipush 2
      // 3e1d: anewarray 24
      // 3e20: dup
      // 3e21: bipush 0
      // 3e22: new net/minecraft/client/animation/Keyframe
      // 3e25: dup
      // 3e26: fconst_0
      // 3e27: fconst_0
      // 3e28: fconst_0
      // 3e29: fconst_0
      // 3e2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e33: aastore
      // 3e34: dup
      // 3e35: bipush 1
      // 3e36: new net/minecraft/client/animation/Keyframe
      // 3e39: dup
      // 3e3a: ldc_w 2.625
      // 3e3d: fconst_0
      // 3e3e: fconst_0
      // 3e3f: fconst_0
      // 3e40: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e49: aastore
      // 3e4a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3e4d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3e50: ldc "right_hammer"
      // 3e52: new net/minecraft/client/animation/AnimationChannel
      // 3e55: dup
      // 3e56: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3e59: bipush 2
      // 3e5a: anewarray 24
      // 3e5d: dup
      // 3e5e: bipush 0
      // 3e5f: new net/minecraft/client/animation/Keyframe
      // 3e62: dup
      // 3e63: fconst_0
      // 3e64: fconst_0
      // 3e65: fconst_0
      // 3e66: fconst_0
      // 3e67: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e6a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e6d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e70: aastore
      // 3e71: dup
      // 3e72: bipush 1
      // 3e73: new net/minecraft/client/animation/Keyframe
      // 3e76: dup
      // 3e77: ldc_w 2.625
      // 3e7a: fconst_0
      // 3e7b: fconst_0
      // 3e7c: fconst_0
      // 3e7d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e80: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e83: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e86: aastore
      // 3e87: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3e8a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3e8d: ldc "right_hammer"
      // 3e8f: new net/minecraft/client/animation/AnimationChannel
      // 3e92: dup
      // 3e93: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3e96: bipush 2
      // 3e97: anewarray 24
      // 3e9a: dup
      // 3e9b: bipush 0
      // 3e9c: new net/minecraft/client/animation/Keyframe
      // 3e9f: dup
      // 3ea0: fconst_0
      // 3ea1: fconst_0
      // 3ea2: fconst_0
      // 3ea3: fconst_0
      // 3ea4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ea7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3eaa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ead: aastore
      // 3eae: dup
      // 3eaf: bipush 1
      // 3eb0: new net/minecraft/client/animation/Keyframe
      // 3eb3: dup
      // 3eb4: ldc_w 2.625
      // 3eb7: fconst_0
      // 3eb8: fconst_0
      // 3eb9: fconst_0
      // 3eba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ebd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ec0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ec3: aastore
      // 3ec4: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3ec7: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3eca: ldc "tail1"
      // 3ecc: new net/minecraft/client/animation/AnimationChannel
      // 3ecf: dup
      // 3ed0: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3ed3: bipush 7
      // 3ed5: anewarray 24
      // 3ed8: dup
      // 3ed9: bipush 0
      // 3eda: new net/minecraft/client/animation/Keyframe
      // 3edd: dup
      // 3ede: fconst_0
      // 3edf: fconst_0
      // 3ee0: fconst_0
      // 3ee1: fconst_0
      // 3ee2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ee5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ee8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3eeb: aastore
      // 3eec: dup
      // 3eed: bipush 1
      // 3eee: new net/minecraft/client/animation/Keyframe
      // 3ef1: dup
      // 3ef2: ldc 0.5417
      // 3ef4: ldc_w 0.043
      // 3ef7: ldc_w 7.3858
      // 3efa: ldc_w 1.3125
      // 3efd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f06: aastore
      // 3f07: dup
      // 3f08: bipush 2
      // 3f09: new net/minecraft/client/animation/Keyframe
      // 3f0c: dup
      // 3f0d: ldc 0.8333
      // 3f0f: ldc_w 23.4051
      // 3f12: ldc_w 23.8133
      // 3f15: ldc_w 7.6552
      // 3f18: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f1b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f1e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f21: aastore
      // 3f22: dup
      // 3f23: bipush 3
      // 3f24: new net/minecraft/client/animation/Keyframe
      // 3f27: dup
      // 3f28: ldc_w 1.25
      // 3f2b: ldc_w 23.7792
      // 3f2e: ldc_w 26.2435
      // 3f31: ldc_w 8.3509
      // 3f34: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f37: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f3a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f3d: aastore
      // 3f3e: dup
      // 3f3f: bipush 4
      // 3f40: new net/minecraft/client/animation/Keyframe
      // 3f43: dup
      // 3f44: ldc_w 1.375
      // 3f47: ldc_w 0.043
      // 3f4a: ldc_w 7.3858
      // 3f4d: ldc_w 1.3125
      // 3f50: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f53: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f56: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f59: aastore
      // 3f5a: dup
      // 3f5b: bipush 5
      // 3f5c: new net/minecraft/client/animation/Keyframe
      // 3f5f: dup
      // 3f60: ldc_w 1.5417
      // 3f63: ldc_w 0.043
      // 3f66: ldc_w 7.3858
      // 3f69: ldc_w 1.3125
      // 3f6c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f6f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f72: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f75: aastore
      // 3f76: dup
      // 3f77: bipush 6
      // 3f79: new net/minecraft/client/animation/Keyframe
      // 3f7c: dup
      // 3f7d: ldc_w 2.625
      // 3f80: fconst_0
      // 3f81: fconst_0
      // 3f82: fconst_0
      // 3f83: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3f86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f8c: aastore
      // 3f8d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3f90: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3f93: ldc "tail2"
      // 3f95: new net/minecraft/client/animation/AnimationChannel
      // 3f98: dup
      // 3f99: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3f9c: bipush 7
      // 3f9e: anewarray 24
      // 3fa1: dup
      // 3fa2: bipush 0
      // 3fa3: new net/minecraft/client/animation/Keyframe
      // 3fa6: dup
      // 3fa7: fconst_0
      // 3fa8: fconst_0
      // 3fa9: fconst_0
      // 3faa: fconst_0
      // 3fab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fb1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fb4: aastore
      // 3fb5: dup
      // 3fb6: bipush 1
      // 3fb7: new net/minecraft/client/animation/Keyframe
      // 3fba: dup
      // 3fbb: ldc 0.5417
      // 3fbd: ldc_w 0.2655
      // 3fc0: ldc_w 2.4293
      // 3fc3: ldc_w 0.6364
      // 3fc6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fc9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fcc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fcf: aastore
      // 3fd0: dup
      // 3fd1: bipush 2
      // 3fd2: new net/minecraft/client/animation/Keyframe
      // 3fd5: dup
      // 3fd6: ldc 0.8333
      // 3fd8: ldc_w 0.5308
      // 3fdb: ldc_w 7.0725
      // 3fde: ldc_w 2.5912
      // 3fe1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3fe4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fe7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fea: aastore
      // 3feb: dup
      // 3fec: bipush 3
      // 3fed: new net/minecraft/client/animation/Keyframe
      // 3ff0: dup
      // 3ff1: ldc_w 1.25
      // 3ff4: ldc_w -0.0433
      // 3ff7: ldc_w 14.5149
      // 3ffa: ldc_w 1.4996
      // 3ffd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4000: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4003: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4006: aastore
      // 4007: dup
      // 4008: bipush 4
      // 4009: new net/minecraft/client/animation/Keyframe
      // 400c: dup
      // 400d: ldc_w 1.375
      // 4010: ldc_w 0.2655
      // 4013: ldc_w 2.4293
      // 4016: ldc_w 0.6364
      // 4019: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 401c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 401f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4022: aastore
      // 4023: dup
      // 4024: bipush 5
      // 4025: new net/minecraft/client/animation/Keyframe
      // 4028: dup
      // 4029: ldc_w 1.5417
      // 402c: ldc_w 0.2655
      // 402f: ldc_w 2.4293
      // 4032: ldc_w 0.6364
      // 4035: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4038: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 403b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 403e: aastore
      // 403f: dup
      // 4040: bipush 6
      // 4042: new net/minecraft/client/animation/Keyframe
      // 4045: dup
      // 4046: ldc_w 2.625
      // 4049: fconst_0
      // 404a: fconst_0
      // 404b: fconst_0
      // 404c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 404f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4052: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4055: aastore
      // 4056: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4059: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 405c: ldc "left_f_leg"
      // 405e: new net/minecraft/client/animation/AnimationChannel
      // 4061: dup
      // 4062: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4065: bipush 9
      // 4067: anewarray 24
      // 406a: dup
      // 406b: bipush 0
      // 406c: new net/minecraft/client/animation/Keyframe
      // 406f: dup
      // 4070: fconst_0
      // 4071: fconst_0
      // 4072: fconst_0
      // 4073: fconst_0
      // 4074: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4077: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 407a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 407d: aastore
      // 407e: dup
      // 407f: bipush 1
      // 4080: new net/minecraft/client/animation/Keyframe
      // 4083: dup
      // 4084: ldc 0.5417
      // 4086: ldc_w -35.9268
      // 4089: ldc_w 13.1508
      // 408c: ldc_w -39.9338
      // 408f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4092: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4095: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4098: aastore
      // 4099: dup
      // 409a: bipush 2
      // 409b: new net/minecraft/client/animation/Keyframe
      // 409e: dup
      // 409f: ldc_w 0.7083
      // 40a2: ldc_w -47.8467
      // 40a5: ldc_w -39.8189
      // 40a8: ldc_w -23.1607
      // 40ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40b4: aastore
      // 40b5: dup
      // 40b6: bipush 3
      // 40b7: new net/minecraft/client/animation/Keyframe
      // 40ba: dup
      // 40bb: ldc 0.8333
      // 40bd: ldc_w -85.9431
      // 40c0: ldc_w -74.7318
      // 40c3: ldc_w 12.2345
      // 40c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40cf: aastore
      // 40d0: dup
      // 40d1: bipush 4
      // 40d2: new net/minecraft/client/animation/Keyframe
      // 40d5: dup
      // 40d6: ldc_w 1.25
      // 40d9: ldc_w -99.1574
      // 40dc: ldc_w -81.186
      // 40df: ldc_w 22.0847
      // 40e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 40e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40eb: aastore
      // 40ec: dup
      // 40ed: bipush 5
      // 40ee: new net/minecraft/client/animation/Keyframe
      // 40f1: dup
      // 40f2: ldc_w 1.3333
      // 40f5: ldc_w -34.015
      // 40f8: ldc_w -26.3205
      // 40fb: ldc_w -24.1642
      // 40fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4101: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4104: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4107: aastore
      // 4108: dup
      // 4109: bipush 6
      // 410b: new net/minecraft/client/animation/Keyframe
      // 410e: dup
      // 410f: ldc_w 1.375
      // 4112: ldc_w -35.9268
      // 4115: ldc_w 13.1508
      // 4118: ldc_w -39.9338
      // 411b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 411e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4121: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4124: aastore
      // 4125: dup
      // 4126: bipush 7
      // 4128: new net/minecraft/client/animation/Keyframe
      // 412b: dup
      // 412c: ldc_w 1.5417
      // 412f: ldc_w -35.9268
      // 4132: ldc_w 13.1508
      // 4135: ldc_w -39.9338
      // 4138: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 413b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 413e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4141: aastore
      // 4142: dup
      // 4143: bipush 8
      // 4145: new net/minecraft/client/animation/Keyframe
      // 4148: dup
      // 4149: ldc_w 2.625
      // 414c: fconst_0
      // 414d: fconst_0
      // 414e: fconst_0
      // 414f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4152: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4155: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4158: aastore
      // 4159: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 415c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 415f: ldc "left_f_leg"
      // 4161: new net/minecraft/client/animation/AnimationChannel
      // 4164: dup
      // 4165: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4168: bipush 2
      // 4169: anewarray 24
      // 416c: dup
      // 416d: bipush 0
      // 416e: new net/minecraft/client/animation/Keyframe
      // 4171: dup
      // 4172: fconst_0
      // 4173: fconst_0
      // 4174: fconst_0
      // 4175: fconst_0
      // 4176: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4179: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 417c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 417f: aastore
      // 4180: dup
      // 4181: bipush 1
      // 4182: new net/minecraft/client/animation/Keyframe
      // 4185: dup
      // 4186: ldc_w 2.625
      // 4189: fconst_0
      // 418a: fconst_0
      // 418b: fconst_0
      // 418c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 418f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4192: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4195: aastore
      // 4196: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4199: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 419c: ldc "left_f_fore_leg"
      // 419e: new net/minecraft/client/animation/AnimationChannel
      // 41a1: dup
      // 41a2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 41a5: bipush 7
      // 41a7: anewarray 24
      // 41aa: dup
      // 41ab: bipush 0
      // 41ac: new net/minecraft/client/animation/Keyframe
      // 41af: dup
      // 41b0: fconst_0
      // 41b1: fconst_0
      // 41b2: fconst_0
      // 41b3: fconst_0
      // 41b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41bd: aastore
      // 41be: dup
      // 41bf: bipush 1
      // 41c0: new net/minecraft/client/animation/Keyframe
      // 41c3: dup
      // 41c4: ldc 0.5417
      // 41c6: ldc_w -11.3645
      // 41c9: ldc_w 8.9927
      // 41cc: ldc_w 12.9622
      // 41cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41d8: aastore
      // 41d9: dup
      // 41da: bipush 2
      // 41db: new net/minecraft/client/animation/Keyframe
      // 41de: dup
      // 41df: ldc 0.8333
      // 41e1: ldc_w 40.9346
      // 41e4: ldc_w -24.9797
      // 41e7: ldc_w 20.3232
      // 41ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 41ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41f3: aastore
      // 41f4: dup
      // 41f5: bipush 3
      // 41f6: new net/minecraft/client/animation/Keyframe
      // 41f9: dup
      // 41fa: ldc_w 1.25
      // 41fd: ldc_w 40.9346
      // 4200: ldc_w -24.9797
      // 4203: ldc_w 20.3232
      // 4206: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4209: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 420c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 420f: aastore
      // 4210: dup
      // 4211: bipush 4
      // 4212: new net/minecraft/client/animation/Keyframe
      // 4215: dup
      // 4216: ldc_w 1.375
      // 4219: ldc_w -11.3645
      // 421c: ldc_w 8.9927
      // 421f: ldc_w 12.9622
      // 4222: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4225: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4228: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 422b: aastore
      // 422c: dup
      // 422d: bipush 5
      // 422e: new net/minecraft/client/animation/Keyframe
      // 4231: dup
      // 4232: ldc_w 1.5417
      // 4235: ldc_w -11.3645
      // 4238: ldc_w 8.9927
      // 423b: ldc_w 12.9622
      // 423e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4241: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4244: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4247: aastore
      // 4248: dup
      // 4249: bipush 6
      // 424b: new net/minecraft/client/animation/Keyframe
      // 424e: dup
      // 424f: ldc_w 2.625
      // 4252: fconst_0
      // 4253: fconst_0
      // 4254: fconst_0
      // 4255: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4258: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 425b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 425e: aastore
      // 425f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4262: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4265: ldc "right_b_leg"
      // 4267: new net/minecraft/client/animation/AnimationChannel
      // 426a: dup
      // 426b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 426e: bipush 7
      // 4270: anewarray 24
      // 4273: dup
      // 4274: bipush 0
      // 4275: new net/minecraft/client/animation/Keyframe
      // 4278: dup
      // 4279: fconst_0
      // 427a: fconst_0
      // 427b: fconst_0
      // 427c: fconst_0
      // 427d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4280: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4283: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4286: aastore
      // 4287: dup
      // 4288: bipush 1
      // 4289: new net/minecraft/client/animation/Keyframe
      // 428c: dup
      // 428d: ldc 0.5417
      // 428f: ldc_w -2.1638
      // 4292: ldc_w -3.8416
      // 4295: ldc_w 18.5632
      // 4298: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 429b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 429e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42a1: aastore
      // 42a2: dup
      // 42a3: bipush 2
      // 42a4: new net/minecraft/client/animation/Keyframe
      // 42a7: dup
      // 42a8: ldc 0.8333
      // 42aa: ldc_w -2.86
      // 42ad: ldc_w -6.5904
      // 42b0: ldc_w -18.4907
      // 42b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42bc: aastore
      // 42bd: dup
      // 42be: bipush 3
      // 42bf: new net/minecraft/client/animation/Keyframe
      // 42c2: dup
      // 42c3: ldc_w 1.25
      // 42c6: ldc_w 4.2344
      // 42c9: ldc_w -9.8774
      // 42cc: ldc_w -17.1357
      // 42cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42d8: aastore
      // 42d9: dup
      // 42da: bipush 4
      // 42db: new net/minecraft/client/animation/Keyframe
      // 42de: dup
      // 42df: ldc_w 1.375
      // 42e2: ldc_w -2.1638
      // 42e5: ldc_w -3.8416
      // 42e8: ldc_w 18.5632
      // 42eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 42ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42f4: aastore
      // 42f5: dup
      // 42f6: bipush 5
      // 42f7: new net/minecraft/client/animation/Keyframe
      // 42fa: dup
      // 42fb: ldc_w 1.5417
      // 42fe: ldc_w -2.1638
      // 4301: ldc_w -3.8416
      // 4304: ldc_w 18.5632
      // 4307: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 430a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 430d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4310: aastore
      // 4311: dup
      // 4312: bipush 6
      // 4314: new net/minecraft/client/animation/Keyframe
      // 4317: dup
      // 4318: ldc_w 2.625
      // 431b: fconst_0
      // 431c: fconst_0
      // 431d: fconst_0
      // 431e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4321: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4324: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4327: aastore
      // 4328: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 432b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 432e: ldc "right_b_leg"
      // 4330: new net/minecraft/client/animation/AnimationChannel
      // 4333: dup
      // 4334: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4337: bipush 2
      // 4338: anewarray 24
      // 433b: dup
      // 433c: bipush 0
      // 433d: new net/minecraft/client/animation/Keyframe
      // 4340: dup
      // 4341: fconst_0
      // 4342: fconst_0
      // 4343: fconst_0
      // 4344: fconst_0
      // 4345: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4348: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 434b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 434e: aastore
      // 434f: dup
      // 4350: bipush 1
      // 4351: new net/minecraft/client/animation/Keyframe
      // 4354: dup
      // 4355: ldc_w 2.625
      // 4358: fconst_0
      // 4359: fconst_0
      // 435a: fconst_0
      // 435b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 435e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4361: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4364: aastore
      // 4365: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4368: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 436b: ldc "right_b_fore_leg"
      // 436d: new net/minecraft/client/animation/AnimationChannel
      // 4370: dup
      // 4371: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4374: bipush 7
      // 4376: anewarray 24
      // 4379: dup
      // 437a: bipush 0
      // 437b: new net/minecraft/client/animation/Keyframe
      // 437e: dup
      // 437f: fconst_0
      // 4380: fconst_0
      // 4381: fconst_0
      // 4382: fconst_0
      // 4383: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4386: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4389: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 438c: aastore
      // 438d: dup
      // 438e: bipush 1
      // 438f: new net/minecraft/client/animation/Keyframe
      // 4392: dup
      // 4393: ldc 0.5417
      // 4395: ldc_w 7.9808
      // 4398: ldc_w 8.7279
      // 439b: ldc_w -15.4235
      // 439e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 43a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43a7: aastore
      // 43a8: dup
      // 43a9: bipush 2
      // 43aa: new net/minecraft/client/animation/Keyframe
      // 43ad: dup
      // 43ae: ldc 0.8333
      // 43b0: ldc_w 8.2634
      // 43b3: ldc_w -10.325
      // 43b6: ldc_w 44.384
      // 43b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 43bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43c2: aastore
      // 43c3: dup
      // 43c4: bipush 3
      // 43c5: new net/minecraft/client/animation/Keyframe
      // 43c8: dup
      // 43c9: ldc_w 1.25
      // 43cc: ldc_w 8.2634
      // 43cf: ldc_w -10.325
      // 43d2: ldc_w 44.384
      // 43d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 43d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43de: aastore
      // 43df: dup
      // 43e0: bipush 4
      // 43e1: new net/minecraft/client/animation/Keyframe
      // 43e4: dup
      // 43e5: ldc_w 1.375
      // 43e8: ldc_w 7.9808
      // 43eb: ldc_w 8.7279
      // 43ee: ldc_w -15.4235
      // 43f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 43f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43fa: aastore
      // 43fb: dup
      // 43fc: bipush 5
      // 43fd: new net/minecraft/client/animation/Keyframe
      // 4400: dup
      // 4401: ldc_w 1.5417
      // 4404: ldc_w 7.9808
      // 4407: ldc_w 8.7279
      // 440a: ldc_w -15.4235
      // 440d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4410: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4413: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4416: aastore
      // 4417: dup
      // 4418: bipush 6
      // 441a: new net/minecraft/client/animation/Keyframe
      // 441d: dup
      // 441e: ldc_w 2.625
      // 4421: fconst_0
      // 4422: fconst_0
      // 4423: fconst_0
      // 4424: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4427: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 442a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 442d: aastore
      // 442e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4431: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4434: ldc "right_f_leg"
      // 4436: new net/minecraft/client/animation/AnimationChannel
      // 4439: dup
      // 443a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 443d: bipush 9
      // 443f: anewarray 24
      // 4442: dup
      // 4443: bipush 0
      // 4444: new net/minecraft/client/animation/Keyframe
      // 4447: dup
      // 4448: fconst_0
      // 4449: fconst_0
      // 444a: fconst_0
      // 444b: fconst_0
      // 444c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 444f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4452: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4455: aastore
      // 4456: dup
      // 4457: bipush 1
      // 4458: new net/minecraft/client/animation/Keyframe
      // 445b: dup
      // 445c: ldc 0.5417
      // 445e: ldc_w -81.7678
      // 4461: ldc_w -12.9242
      // 4464: ldc_w 69.9638
      // 4467: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 446a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 446d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4470: aastore
      // 4471: dup
      // 4472: bipush 2
      // 4473: new net/minecraft/client/animation/Keyframe
      // 4476: dup
      // 4477: ldc_w 0.7083
      // 447a: ldc_w -69.0743
      // 447d: ldc_w 35.4674
      // 4480: ldc_w 39.9023
      // 4483: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4486: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4489: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 448c: aastore
      // 448d: dup
      // 448e: bipush 3
      // 448f: new net/minecraft/client/animation/Keyframe
      // 4492: dup
      // 4493: ldc 0.8333
      // 4495: ldc_w -85.9431
      // 4498: ldc_w 74.7318
      // 449b: ldc_w -12.2345
      // 449e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44a7: aastore
      // 44a8: dup
      // 44a9: bipush 4
      // 44aa: new net/minecraft/client/animation/Keyframe
      // 44ad: dup
      // 44ae: ldc_w 1.25
      // 44b1: ldc_w -99.1574
      // 44b4: ldc_w 81.186
      // 44b7: ldc_w -22.0847
      // 44ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44c3: aastore
      // 44c4: dup
      // 44c5: bipush 5
      // 44c6: new net/minecraft/client/animation/Keyframe
      // 44c9: dup
      // 44ca: ldc_w 1.3333
      // 44cd: ldc_w -62.8465
      // 44d0: ldc_w 19.0596
      // 44d3: ldc_w 42.6699
      // 44d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44df: aastore
      // 44e0: dup
      // 44e1: bipush 6
      // 44e3: new net/minecraft/client/animation/Keyframe
      // 44e6: dup
      // 44e7: ldc_w 1.375
      // 44ea: ldc_w -81.7678
      // 44ed: ldc_w -12.9242
      // 44f0: ldc_w 69.9638
      // 44f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 44f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44fc: aastore
      // 44fd: dup
      // 44fe: bipush 7
      // 4500: new net/minecraft/client/animation/Keyframe
      // 4503: dup
      // 4504: ldc_w 1.5417
      // 4507: ldc_w -81.7678
      // 450a: ldc_w -12.9242
      // 450d: ldc_w 69.9638
      // 4510: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4513: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4516: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4519: aastore
      // 451a: dup
      // 451b: bipush 8
      // 451d: new net/minecraft/client/animation/Keyframe
      // 4520: dup
      // 4521: ldc_w 2.625
      // 4524: fconst_0
      // 4525: fconst_0
      // 4526: fconst_0
      // 4527: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 452a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 452d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4530: aastore
      // 4531: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4534: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4537: ldc "right_f_leg"
      // 4539: new net/minecraft/client/animation/AnimationChannel
      // 453c: dup
      // 453d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4540: bipush 2
      // 4541: anewarray 24
      // 4544: dup
      // 4545: bipush 0
      // 4546: new net/minecraft/client/animation/Keyframe
      // 4549: dup
      // 454a: fconst_0
      // 454b: fconst_0
      // 454c: fconst_0
      // 454d: fconst_0
      // 454e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4551: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4554: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4557: aastore
      // 4558: dup
      // 4559: bipush 1
      // 455a: new net/minecraft/client/animation/Keyframe
      // 455d: dup
      // 455e: ldc_w 2.625
      // 4561: fconst_0
      // 4562: fconst_0
      // 4563: fconst_0
      // 4564: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4567: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 456a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 456d: aastore
      // 456e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4571: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4574: ldc "right_f_fore_leg"
      // 4576: new net/minecraft/client/animation/AnimationChannel
      // 4579: dup
      // 457a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 457d: bipush 7
      // 457f: anewarray 24
      // 4582: dup
      // 4583: bipush 0
      // 4584: new net/minecraft/client/animation/Keyframe
      // 4587: dup
      // 4588: fconst_0
      // 4589: fconst_0
      // 458a: fconst_0
      // 458b: fconst_0
      // 458c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 458f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4592: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4595: aastore
      // 4596: dup
      // 4597: bipush 1
      // 4598: new net/minecraft/client/animation/Keyframe
      // 459b: dup
      // 459c: ldc 0.5417
      // 459e: ldc_w -11.3645
      // 45a1: ldc_w -8.9927
      // 45a4: ldc_w -12.9622
      // 45a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45b0: aastore
      // 45b1: dup
      // 45b2: bipush 2
      // 45b3: new net/minecraft/client/animation/Keyframe
      // 45b6: dup
      // 45b7: ldc 0.8333
      // 45b9: ldc_w 40.9346
      // 45bc: ldc_w 24.9797
      // 45bf: ldc_w -20.3232
      // 45c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45cb: aastore
      // 45cc: dup
      // 45cd: bipush 3
      // 45ce: new net/minecraft/client/animation/Keyframe
      // 45d1: dup
      // 45d2: ldc_w 1.25
      // 45d5: ldc_w 40.9346
      // 45d8: ldc_w 24.9797
      // 45db: ldc_w -20.3232
      // 45de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45e7: aastore
      // 45e8: dup
      // 45e9: bipush 4
      // 45ea: new net/minecraft/client/animation/Keyframe
      // 45ed: dup
      // 45ee: ldc_w 1.375
      // 45f1: ldc_w -11.3645
      // 45f4: ldc_w -8.9927
      // 45f7: ldc_w -12.9622
      // 45fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4600: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4603: aastore
      // 4604: dup
      // 4605: bipush 5
      // 4606: new net/minecraft/client/animation/Keyframe
      // 4609: dup
      // 460a: ldc_w 1.5417
      // 460d: ldc_w -11.3645
      // 4610: ldc_w -8.9927
      // 4613: ldc_w -12.9622
      // 4616: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4619: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 461c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 461f: aastore
      // 4620: dup
      // 4621: bipush 6
      // 4623: new net/minecraft/client/animation/Keyframe
      // 4626: dup
      // 4627: ldc_w 2.625
      // 462a: fconst_0
      // 462b: fconst_0
      // 462c: fconst_0
      // 462d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4630: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4633: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4636: aastore
      // 4637: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 463a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 463d: ldc "legs"
      // 463f: new net/minecraft/client/animation/AnimationChannel
      // 4642: dup
      // 4643: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4646: bipush 2
      // 4647: anewarray 24
      // 464a: dup
      // 464b: bipush 0
      // 464c: new net/minecraft/client/animation/Keyframe
      // 464f: dup
      // 4650: fconst_0
      // 4651: fconst_0
      // 4652: fconst_0
      // 4653: fconst_0
      // 4654: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4657: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 465a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 465d: aastore
      // 465e: dup
      // 465f: bipush 1
      // 4660: new net/minecraft/client/animation/Keyframe
      // 4663: dup
      // 4664: ldc_w 2.625
      // 4667: fconst_0
      // 4668: fconst_0
      // 4669: fconst_0
      // 466a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 466d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4670: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4673: aastore
      // 4674: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4677: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 467a: ldc "legs"
      // 467c: new net/minecraft/client/animation/AnimationChannel
      // 467f: dup
      // 4680: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4683: bipush 2
      // 4684: anewarray 24
      // 4687: dup
      // 4688: bipush 0
      // 4689: new net/minecraft/client/animation/Keyframe
      // 468c: dup
      // 468d: fconst_0
      // 468e: fconst_0
      // 468f: fconst_0
      // 4690: fconst_0
      // 4691: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4694: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4697: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 469a: aastore
      // 469b: dup
      // 469c: bipush 1
      // 469d: new net/minecraft/client/animation/Keyframe
      // 46a0: dup
      // 46a1: ldc_w 2.625
      // 46a4: fconst_0
      // 46a5: fconst_0
      // 46a6: fconst_0
      // 46a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 46aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46b0: aastore
      // 46b1: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 46b4: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 46b7: ldc "left_f_leg_joint"
      // 46b9: new net/minecraft/client/animation/AnimationChannel
      // 46bc: dup
      // 46bd: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 46c0: bipush 2
      // 46c1: anewarray 24
      // 46c4: dup
      // 46c5: bipush 0
      // 46c6: new net/minecraft/client/animation/Keyframe
      // 46c9: dup
      // 46ca: fconst_0
      // 46cb: fconst_0
      // 46cc: fconst_0
      // 46cd: fconst_0
      // 46ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46d7: aastore
      // 46d8: dup
      // 46d9: bipush 1
      // 46da: new net/minecraft/client/animation/Keyframe
      // 46dd: dup
      // 46de: ldc_w 2.625
      // 46e1: fconst_0
      // 46e2: fconst_0
      // 46e3: fconst_0
      // 46e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46ed: aastore
      // 46ee: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 46f1: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 46f4: ldc "right_f_leg_joint"
      // 46f6: new net/minecraft/client/animation/AnimationChannel
      // 46f9: dup
      // 46fa: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 46fd: bipush 2
      // 46fe: anewarray 24
      // 4701: dup
      // 4702: bipush 0
      // 4703: new net/minecraft/client/animation/Keyframe
      // 4706: dup
      // 4707: fconst_0
      // 4708: fconst_0
      // 4709: fconst_0
      // 470a: fconst_0
      // 470b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 470e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4711: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4714: aastore
      // 4715: dup
      // 4716: bipush 1
      // 4717: new net/minecraft/client/animation/Keyframe
      // 471a: dup
      // 471b: ldc_w 2.625
      // 471e: fconst_0
      // 471f: fconst_0
      // 4720: fconst_0
      // 4721: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4724: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4727: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 472a: aastore
      // 472b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 472e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4731: ldc "right_b_leg_joint"
      // 4733: new net/minecraft/client/animation/AnimationChannel
      // 4736: dup
      // 4737: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 473a: bipush 2
      // 473b: anewarray 24
      // 473e: dup
      // 473f: bipush 0
      // 4740: new net/minecraft/client/animation/Keyframe
      // 4743: dup
      // 4744: fconst_0
      // 4745: fconst_0
      // 4746: fconst_0
      // 4747: fconst_0
      // 4748: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 474b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 474e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4751: aastore
      // 4752: dup
      // 4753: bipush 1
      // 4754: new net/minecraft/client/animation/Keyframe
      // 4757: dup
      // 4758: ldc_w 2.625
      // 475b: fconst_0
      // 475c: fconst_0
      // 475d: fconst_0
      // 475e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4761: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4764: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4767: aastore
      // 4768: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 476b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 476e: ldc "left_b_leg_joint"
      // 4770: new net/minecraft/client/animation/AnimationChannel
      // 4773: dup
      // 4774: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4777: bipush 2
      // 4778: anewarray 24
      // 477b: dup
      // 477c: bipush 0
      // 477d: new net/minecraft/client/animation/Keyframe
      // 4780: dup
      // 4781: fconst_0
      // 4782: fconst_0
      // 4783: fconst_0
      // 4784: fconst_0
      // 4785: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4788: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 478b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 478e: aastore
      // 478f: dup
      // 4790: bipush 1
      // 4791: new net/minecraft/client/animation/Keyframe
      // 4794: dup
      // 4795: ldc_w 2.625
      // 4798: fconst_0
      // 4799: fconst_0
      // 479a: fconst_0
      // 479b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 479e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47a4: aastore
      // 47a5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 47a8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 47ab: ldc "left_b_leg"
      // 47ad: new net/minecraft/client/animation/AnimationChannel
      // 47b0: dup
      // 47b1: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 47b4: bipush 7
      // 47b6: anewarray 24
      // 47b9: dup
      // 47ba: bipush 0
      // 47bb: new net/minecraft/client/animation/Keyframe
      // 47be: dup
      // 47bf: fconst_0
      // 47c0: fconst_0
      // 47c1: fconst_0
      // 47c2: fconst_0
      // 47c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47cc: aastore
      // 47cd: dup
      // 47ce: bipush 1
      // 47cf: new net/minecraft/client/animation/Keyframe
      // 47d2: dup
      // 47d3: ldc 0.5417
      // 47d5: ldc_w -2.1638
      // 47d8: ldc_w 3.8416
      // 47db: ldc_w -18.5632
      // 47de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47e7: aastore
      // 47e8: dup
      // 47e9: bipush 2
      // 47ea: new net/minecraft/client/animation/Keyframe
      // 47ed: dup
      // 47ee: ldc 0.8333
      // 47f0: ldc_w -2.86
      // 47f3: ldc_w 6.5904
      // 47f6: ldc_w 18.4907
      // 47f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4802: aastore
      // 4803: dup
      // 4804: bipush 3
      // 4805: new net/minecraft/client/animation/Keyframe
      // 4808: dup
      // 4809: ldc_w 1.25
      // 480c: ldc_w 4.2344
      // 480f: ldc_w 9.8774
      // 4812: ldc_w 17.1357
      // 4815: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4818: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 481b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 481e: aastore
      // 481f: dup
      // 4820: bipush 4
      // 4821: new net/minecraft/client/animation/Keyframe
      // 4824: dup
      // 4825: ldc_w 1.375
      // 4828: ldc_w -2.1638
      // 482b: ldc_w 3.8416
      // 482e: ldc_w -18.5632
      // 4831: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4834: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4837: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 483a: aastore
      // 483b: dup
      // 483c: bipush 5
      // 483d: new net/minecraft/client/animation/Keyframe
      // 4840: dup
      // 4841: ldc_w 1.5417
      // 4844: ldc_w -2.1638
      // 4847: ldc_w 3.8416
      // 484a: ldc_w -18.5632
      // 484d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4850: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4853: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4856: aastore
      // 4857: dup
      // 4858: bipush 6
      // 485a: new net/minecraft/client/animation/Keyframe
      // 485d: dup
      // 485e: ldc_w 2.625
      // 4861: fconst_0
      // 4862: fconst_0
      // 4863: fconst_0
      // 4864: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4867: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 486a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 486d: aastore
      // 486e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4871: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4874: ldc "left_b_leg"
      // 4876: new net/minecraft/client/animation/AnimationChannel
      // 4879: dup
      // 487a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 487d: bipush 2
      // 487e: anewarray 24
      // 4881: dup
      // 4882: bipush 0
      // 4883: new net/minecraft/client/animation/Keyframe
      // 4886: dup
      // 4887: fconst_0
      // 4888: fconst_0
      // 4889: fconst_0
      // 488a: fconst_0
      // 488b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 488e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4891: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4894: aastore
      // 4895: dup
      // 4896: bipush 1
      // 4897: new net/minecraft/client/animation/Keyframe
      // 489a: dup
      // 489b: ldc_w 2.625
      // 489e: fconst_0
      // 489f: fconst_0
      // 48a0: fconst_0
      // 48a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 48a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48aa: aastore
      // 48ab: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 48ae: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 48b1: ldc "left_b_fore_leg"
      // 48b3: new net/minecraft/client/animation/AnimationChannel
      // 48b6: dup
      // 48b7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 48ba: bipush 7
      // 48bc: anewarray 24
      // 48bf: dup
      // 48c0: bipush 0
      // 48c1: new net/minecraft/client/animation/Keyframe
      // 48c4: dup
      // 48c5: fconst_0
      // 48c6: fconst_0
      // 48c7: fconst_0
      // 48c8: fconst_0
      // 48c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48d2: aastore
      // 48d3: dup
      // 48d4: bipush 1
      // 48d5: new net/minecraft/client/animation/Keyframe
      // 48d8: dup
      // 48d9: ldc 0.5417
      // 48db: ldc_w 7.9808
      // 48de: ldc_w -8.7279
      // 48e1: ldc_w 15.4235
      // 48e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48ed: aastore
      // 48ee: dup
      // 48ef: bipush 2
      // 48f0: new net/minecraft/client/animation/Keyframe
      // 48f3: dup
      // 48f4: ldc 0.8333
      // 48f6: ldc_w 8.2634
      // 48f9: ldc_w 10.325
      // 48fc: ldc_w -44.384
      // 48ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4902: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4905: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4908: aastore
      // 4909: dup
      // 490a: bipush 3
      // 490b: new net/minecraft/client/animation/Keyframe
      // 490e: dup
      // 490f: ldc_w 1.25
      // 4912: ldc_w 8.2634
      // 4915: ldc_w 10.325
      // 4918: ldc_w -44.384
      // 491b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 491e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4921: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4924: aastore
      // 4925: dup
      // 4926: bipush 4
      // 4927: new net/minecraft/client/animation/Keyframe
      // 492a: dup
      // 492b: ldc_w 1.375
      // 492e: ldc_w 7.9808
      // 4931: ldc_w -8.7279
      // 4934: ldc_w 15.4235
      // 4937: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 493a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 493d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4940: aastore
      // 4941: dup
      // 4942: bipush 5
      // 4943: new net/minecraft/client/animation/Keyframe
      // 4946: dup
      // 4947: ldc_w 1.5417
      // 494a: ldc_w 7.9808
      // 494d: ldc_w -8.7279
      // 4950: ldc_w 15.4235
      // 4953: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4956: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4959: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 495c: aastore
      // 495d: dup
      // 495e: bipush 6
      // 4960: new net/minecraft/client/animation/Keyframe
      // 4963: dup
      // 4964: ldc_w 2.625
      // 4967: fconst_0
      // 4968: fconst_0
      // 4969: fconst_0
      // 496a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 496d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4970: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4973: aastore
      // 4974: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4977: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 497a: ldc "everything"
      // 497c: new net/minecraft/client/animation/AnimationChannel
      // 497f: dup
      // 4980: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4983: bipush 2
      // 4984: anewarray 24
      // 4987: dup
      // 4988: bipush 0
      // 4989: new net/minecraft/client/animation/Keyframe
      // 498c: dup
      // 498d: fconst_0
      // 498e: fconst_0
      // 498f: fconst_0
      // 4990: fconst_0
      // 4991: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4994: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4997: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 499a: aastore
      // 499b: dup
      // 499c: bipush 1
      // 499d: new net/minecraft/client/animation/Keyframe
      // 49a0: dup
      // 49a1: ldc_w 2.625
      // 49a4: fconst_0
      // 49a5: fconst_0
      // 49a6: fconst_0
      // 49a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 49aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49b0: aastore
      // 49b1: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 49b4: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 49b7: ldc "left_front_arm_rotator"
      // 49b9: new net/minecraft/client/animation/AnimationChannel
      // 49bc: dup
      // 49bd: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 49c0: bipush 2
      // 49c1: anewarray 24
      // 49c4: dup
      // 49c5: bipush 0
      // 49c6: new net/minecraft/client/animation/Keyframe
      // 49c9: dup
      // 49ca: fconst_0
      // 49cb: fconst_0
      // 49cc: fconst_0
      // 49cd: fconst_0
      // 49ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49d7: aastore
      // 49d8: dup
      // 49d9: bipush 1
      // 49da: new net/minecraft/client/animation/Keyframe
      // 49dd: dup
      // 49de: ldc_w 2.625
      // 49e1: fconst_0
      // 49e2: fconst_0
      // 49e3: fconst_0
      // 49e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49ed: aastore
      // 49ee: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 49f1: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 49f4: ldc "right_down_antenna"
      // 49f6: new net/minecraft/client/animation/AnimationChannel
      // 49f9: dup
      // 49fa: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 49fd: bipush 6
      // 49ff: anewarray 24
      // 4a02: dup
      // 4a03: bipush 0
      // 4a04: new net/minecraft/client/animation/Keyframe
      // 4a07: dup
      // 4a08: fconst_0
      // 4a09: fconst_0
      // 4a0a: fconst_0
      // 4a0b: fconst_0
      // 4a0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a15: aastore
      // 4a16: dup
      // 4a17: bipush 1
      // 4a18: new net/minecraft/client/animation/Keyframe
      // 4a1b: dup
      // 4a1c: ldc 0.9583
      // 4a1e: ldc_w 36.9521
      // 4a21: ldc_w 28.0388
      // 4a24: ldc_w 18.4043
      // 4a27: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a30: aastore
      // 4a31: dup
      // 4a32: bipush 2
      // 4a33: new net/minecraft/client/animation/Keyframe
      // 4a36: dup
      // 4a37: ldc_w 1.4167
      // 4a3a: ldc_w -7.6494
      // 4a3d: ldc_w -10.1516
      // 4a40: ldc_w -3.9987
      // 4a43: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a46: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a49: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a4c: aastore
      // 4a4d: dup
      // 4a4e: bipush 3
      // 4a4f: new net/minecraft/client/animation/Keyframe
      // 4a52: dup
      // 4a53: ldc_w 1.8333
      // 4a56: ldc_w 3.3995
      // 4a59: ldc_w 5.664
      // 4a5c: ldc_w 0.3187
      // 4a5f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a62: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a65: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a68: aastore
      // 4a69: dup
      // 4a6a: bipush 4
      // 4a6b: new net/minecraft/client/animation/Keyframe
      // 4a6e: dup
      // 4a6f: ldc_w 2.375
      // 4a72: ldc_w 10.0887
      // 4a75: ldc_w 11.224
      // 4a78: ldc_w 4.2634
      // 4a7b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a7e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a81: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a84: aastore
      // 4a85: dup
      // 4a86: bipush 5
      // 4a87: new net/minecraft/client/animation/Keyframe
      // 4a8a: dup
      // 4a8b: ldc_w 2.625
      // 4a8e: fconst_0
      // 4a8f: fconst_0
      // 4a90: fconst_0
      // 4a91: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a94: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a97: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a9a: aastore
      // 4a9b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4a9e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4aa1: ldc "left_down_antenna"
      // 4aa3: new net/minecraft/client/animation/AnimationChannel
      // 4aa6: dup
      // 4aa7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4aaa: bipush 6
      // 4aac: anewarray 24
      // 4aaf: dup
      // 4ab0: bipush 0
      // 4ab1: new net/minecraft/client/animation/Keyframe
      // 4ab4: dup
      // 4ab5: fconst_0
      // 4ab6: fconst_0
      // 4ab7: fconst_0
      // 4ab8: fconst_0
      // 4ab9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4abc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4abf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ac2: aastore
      // 4ac3: dup
      // 4ac4: bipush 1
      // 4ac5: new net/minecraft/client/animation/Keyframe
      // 4ac8: dup
      // 4ac9: ldc 0.9583
      // 4acb: ldc_w 31.3869
      // 4ace: ldc_w -29.8173
      // 4ad1: ldc_w -16.6273
      // 4ad4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ad7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ada: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4add: aastore
      // 4ade: dup
      // 4adf: bipush 2
      // 4ae0: new net/minecraft/client/animation/Keyframe
      // 4ae3: dup
      // 4ae4: ldc_w 1.4167
      // 4ae7: ldc_w -7.6494
      // 4aea: ldc_w 10.1516
      // 4aed: ldc_w 3.9987
      // 4af0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4af3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4af6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4af9: aastore
      // 4afa: dup
      // 4afb: bipush 3
      // 4afc: new net/minecraft/client/animation/Keyframe
      // 4aff: dup
      // 4b00: ldc_w 1.8333
      // 4b03: ldc_w 3.3995
      // 4b06: ldc_w -5.664
      // 4b09: ldc_w -0.3187
      // 4b0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b15: aastore
      // 4b16: dup
      // 4b17: bipush 4
      // 4b18: new net/minecraft/client/animation/Keyframe
      // 4b1b: dup
      // 4b1c: ldc_w 2.375
      // 4b1f: ldc_w 10.0887
      // 4b22: ldc_w -11.224
      // 4b25: ldc_w -4.2634
      // 4b28: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b2b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b2e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b31: aastore
      // 4b32: dup
      // 4b33: bipush 5
      // 4b34: new net/minecraft/client/animation/Keyframe
      // 4b37: dup
      // 4b38: ldc_w 2.625
      // 4b3b: fconst_0
      // 4b3c: fconst_0
      // 4b3d: fconst_0
      // 4b3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b47: aastore
      // 4b48: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4b4b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4b4e: ldc "right_front_arm_rotator"
      // 4b50: new net/minecraft/client/animation/AnimationChannel
      // 4b53: dup
      // 4b54: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4b57: bipush 2
      // 4b58: anewarray 24
      // 4b5b: dup
      // 4b5c: bipush 0
      // 4b5d: new net/minecraft/client/animation/Keyframe
      // 4b60: dup
      // 4b61: fconst_0
      // 4b62: fconst_0
      // 4b63: fconst_0
      // 4b64: fconst_0
      // 4b65: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b6e: aastore
      // 4b6f: dup
      // 4b70: bipush 1
      // 4b71: new net/minecraft/client/animation/Keyframe
      // 4b74: dup
      // 4b75: ldc_w 2.625
      // 4b78: fconst_0
      // 4b79: fconst_0
      // 4b7a: fconst_0
      // 4b7b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b7e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b81: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b84: aastore
      // 4b85: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4b88: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4b8b: ldc "head_tail"
      // 4b8d: new net/minecraft/client/animation/AnimationChannel
      // 4b90: dup
      // 4b91: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4b94: bipush 2
      // 4b95: anewarray 24
      // 4b98: dup
      // 4b99: bipush 0
      // 4b9a: new net/minecraft/client/animation/Keyframe
      // 4b9d: dup
      // 4b9e: fconst_0
      // 4b9f: fconst_0
      // 4ba0: fconst_0
      // 4ba1: fconst_0
      // 4ba2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ba5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ba8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bab: aastore
      // 4bac: dup
      // 4bad: bipush 1
      // 4bae: new net/minecraft/client/animation/Keyframe
      // 4bb1: dup
      // 4bb2: ldc_w 2.625
      // 4bb5: fconst_0
      // 4bb6: fconst_0
      // 4bb7: fconst_0
      // 4bb8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bbb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bbe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bc1: aastore
      // 4bc2: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4bc5: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4bc8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 4bcb: putstatic com/github/L_Ender/cataclysm/client/animation/Clawdian_Skill_Animation.WAVE_STOMP Lnet/minecraft/client/animation/AnimationDefinition;
      // 4bce: return
   }
}
