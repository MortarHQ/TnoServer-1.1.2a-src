package net.unusual.block_factorys_bosses.client.animation.yeti;

import net.minecraft.client.animation.AnimationDefinition;

public class yetiDeath {
   public static final AnimationDefinition DEATH;

   static {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 0000: ldc 2.62
      // 0002: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0005: ldc "CTR_leg_left.002"
      // 0007: new net/minecraft/client/animation/AnimationChannel
      // 000a: dup
      // 000b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 000e: bipush 25
      // 0010: anewarray 24
      // 0013: dup
      // 0014: bipush 0
      // 0015: new net/minecraft/client/animation/Keyframe
      // 0018: dup
      // 0019: fconst_0
      // 001a: ldc 2.113
      // 001c: fconst_0
      // 001d: ldc -13.058
      // 001f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0022: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0025: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0028: aastore
      // 0029: dup
      // 002a: bipush 1
      // 002b: new net/minecraft/client/animation/Keyframe
      // 002e: dup
      // 002f: ldc 0.21
      // 0031: ldc 2.113
      // 0033: fconst_0
      // 0034: ldc -13.058
      // 0036: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0039: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 003c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 003f: aastore
      // 0040: dup
      // 0041: bipush 2
      // 0042: new net/minecraft/client/animation/Keyframe
      // 0045: dup
      // 0046: ldc 0.25
      // 0048: ldc 5.764
      // 004a: fconst_0
      // 004b: ldc -0.273
      // 004d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0050: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0053: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0056: aastore
      // 0057: dup
      // 0058: bipush 3
      // 0059: new net/minecraft/client/animation/Keyframe
      // 005c: dup
      // 005d: ldc 0.29
      // 005f: ldc 9.414
      // 0061: fconst_0
      // 0062: ldc 12.511
      // 0064: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0067: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 006a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 006d: aastore
      // 006e: dup
      // 006f: bipush 4
      // 0070: new net/minecraft/client/animation/Keyframe
      // 0073: dup
      // 0074: ldc 0.58
      // 0076: ldc 9.414
      // 0078: fconst_0
      // 0079: ldc 12.511
      // 007b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 007e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0081: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0084: aastore
      // 0085: dup
      // 0086: bipush 5
      // 0087: new net/minecraft/client/animation/Keyframe
      // 008a: dup
      // 008b: ldc 0.62
      // 008d: ldc 9.414
      // 008f: fconst_0
      // 0090: ldc 15.375
      // 0092: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0095: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0098: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 009b: aastore
      // 009c: dup
      // 009d: bipush 6
      // 009f: new net/minecraft/client/animation/Keyframe
      // 00a2: dup
      // 00a3: ldc 0.67
      // 00a5: ldc 9.414
      // 00a7: fconst_0
      // 00a8: ldc 22.205
      // 00aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00b3: aastore
      // 00b4: dup
      // 00b5: bipush 7
      // 00b7: new net/minecraft/client/animation/Keyframe
      // 00ba: dup
      // 00bb: ldc 0.71
      // 00bd: ldc 9.414
      // 00bf: fconst_0
      // 00c0: ldc 30.357
      // 00c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00cb: aastore
      // 00cc: dup
      // 00cd: bipush 8
      // 00cf: new net/minecraft/client/animation/Keyframe
      // 00d2: dup
      // 00d3: ldc 0.75
      // 00d5: ldc 9.414
      // 00d7: fconst_0
      // 00d8: ldc 37.187
      // 00da: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00e3: aastore
      // 00e4: dup
      // 00e5: bipush 9
      // 00e7: new net/minecraft/client/animation/Keyframe
      // 00ea: dup
      // 00eb: ldc 0.79
      // 00ed: ldc 9.414
      // 00ef: fconst_0
      // 00f0: ldc 40.051
      // 00f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 00f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00fb: aastore
      // 00fc: dup
      // 00fd: bipush 10
      // 00ff: new net/minecraft/client/animation/Keyframe
      // 0102: dup
      // 0103: ldc 2.04
      // 0105: ldc 9.414
      // 0107: fconst_0
      // 0108: ldc 40.051
      // 010a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 010d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0110: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0113: aastore
      // 0114: dup
      // 0115: bipush 11
      // 0117: new net/minecraft/client/animation/Keyframe
      // 011a: dup
      // 011b: ldc 2.08
      // 011d: ldc 9.414
      // 011f: fconst_0
      // 0120: ldc 41.145
      // 0122: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0125: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0128: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 012b: aastore
      // 012c: dup
      // 012d: bipush 12
      // 012f: new net/minecraft/client/animation/Keyframe
      // 0132: dup
      // 0133: ldc 2.12
      // 0135: ldc 9.414
      // 0137: fconst_0
      // 0138: ldc 43.879
      // 013a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 013d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0140: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0143: aastore
      // 0144: dup
      // 0145: bipush 13
      // 0147: new net/minecraft/client/animation/Keyframe
      // 014a: dup
      // 014b: ldc 2.17
      // 014d: ldc 9.414
      // 014f: fconst_0
      // 0150: ldc 47.434
      // 0152: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0155: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0158: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 015b: aastore
      // 015c: dup
      // 015d: bipush 14
      // 015f: new net/minecraft/client/animation/Keyframe
      // 0162: dup
      // 0163: ldc 2.21
      // 0165: ldc 9.414
      // 0167: fconst_0
      // 0168: ldc 50.99
      // 016a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 016d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0170: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0173: aastore
      // 0174: dup
      // 0175: bipush 15
      // 0177: new net/minecraft/client/animation/Keyframe
      // 017a: dup
      // 017b: ldc 2.25
      // 017d: ldc 9.414
      // 017f: fconst_0
      // 0180: ldc 53.724
      // 0182: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0185: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0188: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 018b: aastore
      // 018c: dup
      // 018d: bipush 16
      // 018f: new net/minecraft/client/animation/Keyframe
      // 0192: dup
      // 0193: ldc 2.29
      // 0195: ldc 9.414
      // 0197: fconst_0
      // 0198: ldc 54.818
      // 019a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 019d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01a3: aastore
      // 01a4: dup
      // 01a5: bipush 17
      // 01a7: new net/minecraft/client/animation/Keyframe
      // 01aa: dup
      // 01ab: ldc 2.33
      // 01ad: ldc 9.414
      // 01af: fconst_0
      // 01b0: ldc 54.658
      // 01b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 01b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01bb: aastore
      // 01bc: dup
      // 01bd: bipush 18
      // 01bf: new net/minecraft/client/animation/Keyframe
      // 01c2: dup
      // 01c3: ldc 2.38
      // 01c5: ldc 9.414
      // 01c7: fconst_0
      // 01c8: ldc 54.238
      // 01ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 01cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01d3: aastore
      // 01d4: dup
      // 01d5: bipush 19
      // 01d7: new net/minecraft/client/animation/Keyframe
      // 01da: dup
      // 01db: ldc 2.42
      // 01dd: ldc 9.414
      // 01df: fconst_0
      // 01e0: ldc 53.642
      // 01e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 01e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01eb: aastore
      // 01ec: dup
      // 01ed: bipush 20
      // 01ef: new net/minecraft/client/animation/Keyframe
      // 01f2: dup
      // 01f3: ldc 2.46
      // 01f5: ldc 9.414
      // 01f7: fconst_0
      // 01f8: ldc 52.96
      // 01fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 01fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0200: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0203: aastore
      // 0204: dup
      // 0205: bipush 21
      // 0207: new net/minecraft/client/animation/Keyframe
      // 020a: dup
      // 020b: ldc 2.5
      // 020d: ldc 9.414
      // 020f: fconst_0
      // 0210: ldc 52.278
      // 0212: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0215: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0218: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 021b: aastore
      // 021c: dup
      // 021d: bipush 22
      // 021f: new net/minecraft/client/animation/Keyframe
      // 0222: dup
      // 0223: ldc 2.54
      // 0225: ldc 9.414
      // 0227: fconst_0
      // 0228: ldc 51.683
      // 022a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 022d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0230: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0233: aastore
      // 0234: dup
      // 0235: bipush 23
      // 0237: new net/minecraft/client/animation/Keyframe
      // 023a: dup
      // 023b: ldc 2.58
      // 023d: ldc 9.414
      // 023f: fconst_0
      // 0240: ldc 51.262
      // 0242: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0245: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0248: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 024b: aastore
      // 024c: dup
      // 024d: bipush 24
      // 024f: new net/minecraft/client/animation/Keyframe
      // 0252: dup
      // 0253: ldc 2.62
      // 0255: ldc 9.414
      // 0257: fconst_0
      // 0258: ldc 51.103
      // 025a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 025d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0260: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0263: aastore
      // 0264: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0267: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 026a: ldc "CTR_upperarm_left.001"
      // 026c: new net/minecraft/client/animation/AnimationChannel
      // 026f: dup
      // 0270: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0273: bipush 64
      // 0275: anewarray 24
      // 0278: dup
      // 0279: bipush 0
      // 027a: new net/minecraft/client/animation/Keyframe
      // 027d: dup
      // 027e: fconst_0
      // 027f: ldc 2.163
      // 0281: ldc -22.491
      // 0283: ldc -1.837
      // 0285: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0288: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 028b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 028e: aastore
      // 028f: dup
      // 0290: bipush 1
      // 0291: new net/minecraft/client/animation/Keyframe
      // 0294: dup
      // 0295: ldc 0.04
      // 0297: ldc 1.54
      // 0299: ldc -15.249
      // 029b: ldc -1.992
      // 029d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02a6: aastore
      // 02a7: dup
      // 02a8: bipush 2
      // 02a9: new net/minecraft/client/animation/Keyframe
      // 02ac: dup
      // 02ad: ldc 0.08
      // 02af: ldc 4.196
      // 02b1: ldc 10.092
      // 02b3: ldc 3.546
      // 02b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02be: aastore
      // 02bf: dup
      // 02c0: bipush 3
      // 02c1: new net/minecraft/client/animation/Keyframe
      // 02c4: dup
      // 02c5: ldc 0.12
      // 02c7: ldc 1.468
      // 02c9: ldc 35.06
      // 02cb: ldc 1.978
      // 02cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02d6: aastore
      // 02d7: dup
      // 02d8: bipush 4
      // 02d9: new net/minecraft/client/animation/Keyframe
      // 02dc: dup
      // 02dd: ldc 0.17
      // 02df: ldc -1.166
      // 02e1: ldc 49.162
      // 02e3: ldc 4.362
      // 02e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02ee: aastore
      // 02ef: dup
      // 02f0: bipush 5
      // 02f1: new net/minecraft/client/animation/Keyframe
      // 02f4: dup
      // 02f5: ldc 0.21
      // 02f7: ldc 0.471
      // 02f9: ldc 60.852
      // 02fb: ldc 2.323
      // 02fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0300: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0303: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0306: aastore
      // 0307: dup
      // 0308: bipush 6
      // 030a: new net/minecraft/client/animation/Keyframe
      // 030d: dup
      // 030e: ldc 0.25
      // 0310: ldc 10.945
      // 0312: ldc 65.45
      // 0314: ldc -8.911
      // 0316: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0319: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 031c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 031f: aastore
      // 0320: dup
      // 0321: bipush 7
      // 0323: new net/minecraft/client/animation/Keyframe
      // 0326: dup
      // 0327: ldc 0.29
      // 0329: ldc 16.776
      // 032b: ldc 66.221
      // 032d: ldc -15.121
      // 032f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0332: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0335: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0338: aastore
      // 0339: dup
      // 033a: bipush 8
      // 033c: new net/minecraft/client/animation/Keyframe
      // 033f: dup
      // 0340: ldc 0.33
      // 0342: ldc 14.701
      // 0344: ldc 67.373
      // 0346: ldc -12.908
      // 0348: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 034b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 034e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0351: aastore
      // 0352: dup
      // 0353: bipush 9
      // 0355: new net/minecraft/client/animation/Keyframe
      // 0358: dup
      // 0359: ldc 0.38
      // 035b: ldc 12.702
      // 035d: ldc 66.49
      // 035f: ldc -10.783
      // 0361: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0364: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0367: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 036a: aastore
      // 036b: dup
      // 036c: bipush 10
      // 036e: new net/minecraft/client/animation/Keyframe
      // 0371: dup
      // 0372: ldc 0.42
      // 0374: ldc 11.209
      // 0376: ldc 64.313
      // 0378: ldc -9.191
      // 037a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 037d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0380: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0383: aastore
      // 0384: dup
      // 0385: bipush 11
      // 0387: new net/minecraft/client/animation/Keyframe
      // 038a: dup
      // 038b: ldc 0.46
      // 038d: ldc 10.258
      // 038f: ldc 61.586
      // 0391: ldc -8.17
      // 0393: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0396: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0399: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 039c: aastore
      // 039d: dup
      // 039e: bipush 12
      // 03a0: new net/minecraft/client/animation/Keyframe
      // 03a3: dup
      // 03a4: ldc 0.5
      // 03a6: ldc 10.046
      // 03a8: ldc 58.395
      // 03aa: ldc -7.933
      // 03ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03b5: aastore
      // 03b6: dup
      // 03b7: bipush 13
      // 03b9: new net/minecraft/client/animation/Keyframe
      // 03bc: dup
      // 03bd: ldc 0.54
      // 03bf: ldc 10.652
      // 03c1: ldc 54.678
      // 03c3: ldc -8.577
      // 03c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03ce: aastore
      // 03cf: dup
      // 03d0: bipush 14
      // 03d2: new net/minecraft/client/animation/Keyframe
      // 03d5: dup
      // 03d6: ldc 0.58
      // 03d8: ldc 11.729
      // 03da: ldc 50.761
      // 03dc: ldc -9.742
      // 03de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03e7: aastore
      // 03e8: dup
      // 03e9: bipush 15
      // 03eb: new net/minecraft/client/animation/Keyframe
      // 03ee: dup
      // 03ef: ldc 0.62
      // 03f1: ldc 12.779
      // 03f3: ldc 47.053
      // 03f5: ldc -10.895
      // 03f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0400: aastore
      // 0401: dup
      // 0402: bipush 16
      // 0404: new net/minecraft/client/animation/Keyframe
      // 0407: dup
      // 0408: ldc 0.67
      // 040a: ldc 13.333
      // 040c: ldc 43.175
      // 040e: ldc -11.52
      // 0410: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0413: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0416: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0419: aastore
      // 041a: dup
      // 041b: bipush 17
      // 041d: new net/minecraft/client/animation/Keyframe
      // 0420: dup
      // 0421: ldc 0.71
      // 0423: ldc 13.263
      // 0425: ldc 38.869
      // 0427: ldc -11.462
      // 0429: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 042c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 042f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0432: aastore
      // 0433: dup
      // 0434: bipush 18
      // 0436: new net/minecraft/client/animation/Keyframe
      // 0439: dup
      // 043a: ldc 0.75
      // 043c: ldc 12.876
      // 043e: ldc 34.648
      // 0440: ldc -11.044
      // 0442: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0445: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0448: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 044b: aastore
      // 044c: dup
      // 044d: bipush 19
      // 044f: new net/minecraft/client/animation/Keyframe
      // 0452: dup
      // 0453: ldc 0.79
      // 0455: ldc 12.653
      // 0457: ldc 30.697
      // 0459: ldc -10.805
      // 045b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 045e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0461: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0464: aastore
      // 0465: dup
      // 0466: bipush 20
      // 0468: new net/minecraft/client/animation/Keyframe
      // 046b: dup
      // 046c: ldc 0.83
      // 046e: ldc 13.204
      // 0470: ldc 27.039
      // 0472: ldc -11.483
      // 0474: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0477: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 047a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 047d: aastore
      // 047e: dup
      // 047f: bipush 21
      // 0481: new net/minecraft/client/animation/Keyframe
      // 0484: dup
      // 0485: ldc 0.88
      // 0487: ldc 14.779
      // 0489: ldc 22.34
      // 048b: ldc -13.51
      // 048d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0490: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0493: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0496: aastore
      // 0497: dup
      // 0498: bipush 22
      // 049a: new net/minecraft/client/animation/Keyframe
      // 049d: dup
      // 049e: ldc 0.92
      // 04a0: ldc 16.517
      // 04a2: ldc 17.207
      // 04a4: ldc -16.122
      // 04a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04af: aastore
      // 04b0: dup
      // 04b1: bipush 23
      // 04b3: new net/minecraft/client/animation/Keyframe
      // 04b6: dup
      // 04b7: ldc 0.96
      // 04b9: ldc 17.619
      // 04bb: ldc 12.461
      // 04bd: ldc -18.71
      // 04bf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04c2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04c5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04c8: aastore
      // 04c9: dup
      // 04ca: bipush 24
      // 04cc: new net/minecraft/client/animation/Keyframe
      // 04cf: dup
      // 04d0: fconst_1
      // 04d1: ldc 17.655
      // 04d3: ldc 9.346
      // 04d5: ldc -20.582
      // 04d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04e0: aastore
      // 04e1: dup
      // 04e2: bipush 25
      // 04e4: new net/minecraft/client/animation/Keyframe
      // 04e7: dup
      // 04e8: ldc 1.04
      // 04ea: ldc 17.268
      // 04ec: ldc 7.834
      // 04ee: ldc -21.695
      // 04f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04f9: aastore
      // 04fa: dup
      // 04fb: bipush 26
      // 04fd: new net/minecraft/client/animation/Keyframe
      // 0500: dup
      // 0501: ldc 1.08
      // 0503: ldc 16.667
      // 0505: ldc 6.536
      // 0507: ldc -23.348
      // 0509: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 050c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 050f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0512: aastore
      // 0513: dup
      // 0514: bipush 27
      // 0516: new net/minecraft/client/animation/Keyframe
      // 0519: dup
      // 051a: ldc 1.12
      // 051c: ldc 9.854
      // 051e: ldc 3.682
      // 0520: ldc -32.364
      // 0522: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0525: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0528: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 052b: aastore
      // 052c: dup
      // 052d: bipush 28
      // 052f: new net/minecraft/client/animation/Keyframe
      // 0532: dup
      // 0533: ldc 1.17
      // 0535: ldc 40.682
      // 0537: ldc 0.323
      // 0539: ldc -3.638
      // 053b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 053e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0541: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0544: aastore
      // 0545: dup
      // 0546: bipush 29
      // 0548: new net/minecraft/client/animation/Keyframe
      // 054b: dup
      // 054c: ldc 1.21
      // 054e: ldc 29.87
      // 0550: ldc -2.999
      // 0552: ldc -16.402
      // 0554: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0557: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 055a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 055d: aastore
      // 055e: dup
      // 055f: bipush 30
      // 0561: new net/minecraft/client/animation/Keyframe
      // 0564: dup
      // 0565: ldc 1.25
      // 0567: ldc 28.646
      // 0569: ldc -6.352
      // 056b: ldc -19.436
      // 056d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0570: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0573: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0576: aastore
      // 0577: dup
      // 0578: bipush 31
      // 057a: new net/minecraft/client/animation/Keyframe
      // 057d: dup
      // 057e: ldc 1.29
      // 0580: ldc 28.62
      // 0582: ldc -9.537
      // 0584: ldc -21.13
      // 0586: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0589: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 058c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 058f: aastore
      // 0590: dup
      // 0591: bipush 32
      // 0593: new net/minecraft/client/animation/Keyframe
      // 0596: dup
      // 0597: ldc 1.33
      // 0599: ldc 28.983
      // 059b: ldc -12.193
      // 059d: ldc -22.308
      // 059f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05a8: aastore
      // 05a9: dup
      // 05aa: bipush 33
      // 05ac: new net/minecraft/client/animation/Keyframe
      // 05af: dup
      // 05b0: ldc 1.38
      // 05b2: ldc 29.282
      // 05b4: ldc -14.391
      // 05b6: ldc -23.106
      // 05b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05c1: aastore
      // 05c2: dup
      // 05c3: bipush 34
      // 05c5: new net/minecraft/client/animation/Keyframe
      // 05c8: dup
      // 05c9: ldc 1.42
      // 05cb: ldc 31.636
      // 05cd: ldc -9.779
      // 05cf: ldc -23.285
      // 05d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05da: aastore
      // 05db: dup
      // 05dc: bipush 35
      // 05de: new net/minecraft/client/animation/Keyframe
      // 05e1: dup
      // 05e2: ldc 1.46
      // 05e4: ldc 38.017
      // 05e6: ldc -2.943
      // 05e8: ldc -19.268
      // 05ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 05ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05f3: aastore
      // 05f4: dup
      // 05f5: bipush 36
      // 05f7: new net/minecraft/client/animation/Keyframe
      // 05fa: dup
      // 05fb: ldc 1.5
      // 05fd: ldc 12.037
      // 05ff: ldc 4.076
      // 0601: ldc -46.525
      // 0603: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0606: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0609: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 060c: aastore
      // 060d: dup
      // 060e: bipush 37
      // 0610: new net/minecraft/client/animation/Keyframe
      // 0613: dup
      // 0614: ldc 1.54
      // 0616: ldc 25.575
      // 0618: ldc 10.273
      // 061a: ldc -33.354
      // 061c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 061f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0622: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0625: aastore
      // 0626: dup
      // 0627: bipush 38
      // 0629: new net/minecraft/client/animation/Keyframe
      // 062c: dup
      // 062d: ldc 1.58
      // 062f: ldc 27.401
      // 0631: ldc 15.832
      // 0633: ldc -31.256
      // 0635: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0638: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 063b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 063e: aastore
      // 063f: dup
      // 0640: bipush 39
      // 0642: new net/minecraft/client/animation/Keyframe
      // 0645: dup
      // 0646: ldc 1.62
      // 0648: ldc 27.826
      // 064a: ldc 20.873
      // 064c: ldc -30.034
      // 064e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0651: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0654: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0657: aastore
      // 0658: dup
      // 0659: bipush 40
      // 065b: new net/minecraft/client/animation/Keyframe
      // 065e: dup
      // 065f: ldc 1.67
      // 0661: ldc 27.65
      // 0663: ldc 25.54
      // 0665: ldc -28.902
      // 0667: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 066a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 066d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0670: aastore
      // 0671: dup
      // 0672: bipush 41
      // 0674: new net/minecraft/client/animation/Keyframe
      // 0677: dup
      // 0678: ldc_w 1.71
      // 067b: ldc_w 27.072
      // 067e: ldc_w 29.888
      // 0681: ldc_w -27.672
      // 0684: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0687: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 068a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 068d: aastore
      // 068e: dup
      // 068f: bipush 42
      // 0691: new net/minecraft/client/animation/Keyframe
      // 0694: dup
      // 0695: ldc_w 1.75
      // 0698: ldc_w 26.327
      // 069b: ldc_w 33.609
      // 069e: ldc_w -26.485
      // 06a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06aa: aastore
      // 06ab: dup
      // 06ac: bipush 43
      // 06ae: new net/minecraft/client/animation/Keyframe
      // 06b1: dup
      // 06b2: ldc_w 1.79
      // 06b5: ldc_w 25.814
      // 06b8: ldc_w 36.093
      // 06bb: ldc_w -25.73
      // 06be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06c7: aastore
      // 06c8: dup
      // 06c9: bipush 44
      // 06cb: new net/minecraft/client/animation/Keyframe
      // 06ce: dup
      // 06cf: ldc_w 1.83
      // 06d2: ldc_w 25.835
      // 06d5: ldc_w 36.94
      // 06d8: ldc_w -25.703
      // 06db: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06de: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06e1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06e4: aastore
      // 06e5: dup
      // 06e6: bipush 45
      // 06e8: new net/minecraft/client/animation/Keyframe
      // 06eb: dup
      // 06ec: ldc_w 1.88
      // 06ef: ldc_w 26.23
      // 06f2: ldc_w 36.735
      // 06f5: ldc_w -26.161
      // 06f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0701: aastore
      // 0702: dup
      // 0703: bipush 46
      // 0705: new net/minecraft/client/animation/Keyframe
      // 0708: dup
      // 0709: ldc_w 1.92
      // 070c: ldc_w 26.645
      // 070f: ldc_w 36.439
      // 0712: ldc_w -26.649
      // 0715: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0718: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 071b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 071e: aastore
      // 071f: dup
      // 0720: bipush 47
      // 0722: new net/minecraft/client/animation/Keyframe
      // 0725: dup
      // 0726: ldc_w 1.96
      // 0729: ldc_w 27.044
      // 072c: ldc_w 36.112
      // 072f: ldc_w -27.122
      // 0732: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0735: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0738: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 073b: aastore
      // 073c: dup
      // 073d: bipush 48
      // 073f: new net/minecraft/client/animation/Keyframe
      // 0742: dup
      // 0743: fconst_2
      // 0744: ldc_w 27.412
      // 0747: ldc_w 35.772
      // 074a: ldc_w -27.561
      // 074d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0750: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0753: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0756: aastore
      // 0757: dup
      // 0758: bipush 49
      // 075a: new net/minecraft/client/animation/Keyframe
      // 075d: dup
      // 075e: ldc 2.04
      // 0760: ldc_w 27.801
      // 0763: ldc_w 35.332
      // 0766: ldc_w -28.035
      // 0769: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 076c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 076f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0772: aastore
      // 0773: dup
      // 0774: bipush 50
      // 0776: new net/minecraft/client/animation/Keyframe
      // 0779: dup
      // 077a: ldc 2.08
      // 077c: ldc_w 27.712
      // 077f: ldc_w 34.654
      // 0782: ldc_w -27.984
      // 0785: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0788: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 078b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 078e: aastore
      // 078f: dup
      // 0790: bipush 51
      // 0792: new net/minecraft/client/animation/Keyframe
      // 0795: dup
      // 0796: ldc 2.12
      // 0798: ldc_w 27.547
      // 079b: ldc_w 33.333
      // 079e: ldc_w -27.902
      // 07a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 07a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07aa: aastore
      // 07ab: dup
      // 07ac: bipush 52
      // 07ae: new net/minecraft/client/animation/Keyframe
      // 07b1: dup
      // 07b2: ldc 2.17
      // 07b4: ldc_w 24.411
      // 07b7: ldc_w 38.019
      // 07ba: ldc_w -24.039
      // 07bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 07c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07c6: aastore
      // 07c7: dup
      // 07c8: bipush 53
      // 07ca: new net/minecraft/client/animation/Keyframe
      // 07cd: dup
      // 07ce: ldc 2.21
      // 07d0: ldc_w 19.876
      // 07d3: ldc_w 30.036
      // 07d6: ldc_w -19.263
      // 07d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 07dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07e2: aastore
      // 07e3: dup
      // 07e4: bipush 54
      // 07e6: new net/minecraft/client/animation/Keyframe
      // 07e9: dup
      // 07ea: ldc 2.25
      // 07ec: ldc_w 18.567
      // 07ef: ldc_w 30.985
      // 07f2: ldc_w -17.69
      // 07f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 07f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07fe: aastore
      // 07ff: dup
      // 0800: bipush 55
      // 0802: new net/minecraft/client/animation/Keyframe
      // 0805: dup
      // 0806: ldc 2.29
      // 0808: ldc_w 7.638
      // 080b: ldc_w 28.389
      // 080e: ldc_w -4.884
      // 0811: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0814: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0817: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 081a: aastore
      // 081b: dup
      // 081c: bipush 56
      // 081e: new net/minecraft/client/animation/Keyframe
      // 0821: dup
      // 0822: ldc 2.33
      // 0824: ldc_w 1.973
      // 0827: ldc_w 23.989
      // 082a: ldc_w 2.221
      // 082d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0830: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0833: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0836: aastore
      // 0837: dup
      // 0838: bipush 57
      // 083a: new net/minecraft/client/animation/Keyframe
      // 083d: dup
      // 083e: ldc 2.38
      // 0840: ldc_w 1.966
      // 0843: ldc_w 24.091
      // 0846: ldc_w 2.217
      // 0849: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 084c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 084f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0852: aastore
      // 0853: dup
      // 0854: bipush 58
      // 0856: new net/minecraft/client/animation/Keyframe
      // 0859: dup
      // 085a: ldc 2.42
      // 085c: ldc_w 2.135
      // 085f: ldc_w 21.524
      // 0862: ldc_w 2.338
      // 0865: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0868: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 086b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 086e: aastore
      // 086f: dup
      // 0870: bipush 59
      // 0872: new net/minecraft/client/animation/Keyframe
      // 0875: dup
      // 0876: ldc 2.46
      // 0878: ldc_w 2.054
      // 087b: ldc_w 22.699
      // 087e: ldc_w 2.278
      // 0881: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0884: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0887: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 088a: aastore
      // 088b: dup
      // 088c: bipush 60
      // 088e: new net/minecraft/client/animation/Keyframe
      // 0891: dup
      // 0892: ldc 2.5
      // 0894: ldc_w 2.044
      // 0897: ldc_w 22.84
      // 089a: ldc_w 2.271
      // 089d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 08a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08a6: aastore
      // 08a7: dup
      // 08a8: bipush 61
      // 08aa: new net/minecraft/client/animation/Keyframe
      // 08ad: dup
      // 08ae: ldc 2.54
      // 08b0: ldc_w 2.007
      // 08b3: ldc_w 23.423
      // 08b6: ldc_w 2.245
      // 08b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 08bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08c2: aastore
      // 08c3: dup
      // 08c4: bipush 62
      // 08c6: new net/minecraft/client/animation/Keyframe
      // 08c9: dup
      // 08ca: ldc 2.58
      // 08cc: ldc_w 1.939
      // 08cf: ldc_w 24.557
      // 08d2: ldc_w 2.198
      // 08d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 08d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08de: aastore
      // 08df: dup
      // 08e0: bipush 63
      // 08e2: new net/minecraft/client/animation/Keyframe
      // 08e5: dup
      // 08e6: ldc 2.62
      // 08e8: ldc_w 1.939
      // 08eb: ldc_w 24.557
      // 08ee: ldc_w 2.198
      // 08f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 08f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08fa: aastore
      // 08fb: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 08fe: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0901: ldc "CTR_upperarm_left.001"
      // 0903: new net/minecraft/client/animation/AnimationChannel
      // 0906: dup
      // 0907: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 090a: bipush 64
      // 090c: anewarray 24
      // 090f: dup
      // 0910: bipush 0
      // 0911: new net/minecraft/client/animation/Keyframe
      // 0914: dup
      // 0915: fconst_0
      // 0916: dconst_1
      // 0917: dconst_1
      // 0918: ldc2_w 1.7860000133514404
      // 091b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 091e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0921: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0924: aastore
      // 0925: dup
      // 0926: bipush 1
      // 0927: new net/minecraft/client/animation/Keyframe
      // 092a: dup
      // 092b: ldc 0.04
      // 092d: dconst_1
      // 092e: dconst_1
      // 092f: ldc2_w 1.593999981880188
      // 0932: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0935: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0938: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 093b: aastore
      // 093c: dup
      // 093d: bipush 2
      // 093e: new net/minecraft/client/animation/Keyframe
      // 0941: dup
      // 0942: ldc 0.08
      // 0944: dconst_1
      // 0945: dconst_1
      // 0946: ldc2_w 1.3519999980926514
      // 0949: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 094c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 094f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0952: aastore
      // 0953: dup
      // 0954: bipush 3
      // 0955: new net/minecraft/client/animation/Keyframe
      // 0958: dup
      // 0959: ldc 0.12
      // 095b: dconst_1
      // 095c: dconst_1
      // 095d: ldc2_w 1.2920000553131104
      // 0960: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0963: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0966: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0969: aastore
      // 096a: dup
      // 096b: bipush 4
      // 096c: new net/minecraft/client/animation/Keyframe
      // 096f: dup
      // 0970: ldc 0.17
      // 0972: dconst_1
      // 0973: dconst_1
      // 0974: ldc2_w 1.36899995803833
      // 0977: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 097a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 097d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0980: aastore
      // 0981: dup
      // 0982: bipush 5
      // 0983: new net/minecraft/client/animation/Keyframe
      // 0986: dup
      // 0987: ldc 0.21
      // 0989: dconst_1
      // 098a: dconst_1
      // 098b: ldc2_w 1.503000020980835
      // 098e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0991: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0994: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0997: aastore
      // 0998: dup
      // 0999: bipush 6
      // 099b: new net/minecraft/client/animation/Keyframe
      // 099e: dup
      // 099f: ldc 0.25
      // 09a1: dconst_1
      // 09a2: dconst_1
      // 09a3: ldc2_w 1.5399999618530273
      // 09a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 09a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09af: aastore
      // 09b0: dup
      // 09b1: bipush 7
      // 09b3: new net/minecraft/client/animation/Keyframe
      // 09b6: dup
      // 09b7: ldc 0.29
      // 09b9: dconst_1
      // 09ba: dconst_1
      // 09bb: ldc2_w 1.5199999809265137
      // 09be: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 09c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09c7: aastore
      // 09c8: dup
      // 09c9: bipush 8
      // 09cb: new net/minecraft/client/animation/Keyframe
      // 09ce: dup
      // 09cf: ldc 0.33
      // 09d1: dconst_1
      // 09d2: dconst_1
      // 09d3: ldc2_w 1.5369999408721924
      // 09d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 09d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09df: aastore
      // 09e0: dup
      // 09e1: bipush 9
      // 09e3: new net/minecraft/client/animation/Keyframe
      // 09e6: dup
      // 09e7: ldc 0.38
      // 09e9: dconst_1
      // 09ea: dconst_1
      // 09eb: ldc2_w 1.5269999504089355
      // 09ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 09f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09f7: aastore
      // 09f8: dup
      // 09f9: bipush 10
      // 09fb: new net/minecraft/client/animation/Keyframe
      // 09fe: dup
      // 09ff: ldc 0.42
      // 0a01: dconst_1
      // 0a02: dconst_1
      // 0a03: ldc2_w 1.4910000562667847
      // 0a06: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0a09: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a0c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a0f: aastore
      // 0a10: dup
      // 0a11: bipush 11
      // 0a13: new net/minecraft/client/animation/Keyframe
      // 0a16: dup
      // 0a17: ldc 0.46
      // 0a19: dconst_1
      // 0a1a: dconst_1
      // 0a1b: ldc2_w 1.4320000410079956
      // 0a1e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0a21: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a24: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a27: aastore
      // 0a28: dup
      // 0a29: bipush 12
      // 0a2b: new net/minecraft/client/animation/Keyframe
      // 0a2e: dup
      // 0a2f: ldc 0.5
      // 0a31: dconst_1
      // 0a32: dconst_1
      // 0a33: ldc2_w 1.3589999675750732
      // 0a36: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0a39: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a3c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a3f: aastore
      // 0a40: dup
      // 0a41: bipush 13
      // 0a43: new net/minecraft/client/animation/Keyframe
      // 0a46: dup
      // 0a47: ldc 0.54
      // 0a49: dconst_1
      // 0a4a: dconst_1
      // 0a4b: ldc2_w 1.2860000133514404
      // 0a4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0a51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a57: aastore
      // 0a58: dup
      // 0a59: bipush 14
      // 0a5b: new net/minecraft/client/animation/Keyframe
      // 0a5e: dup
      // 0a5f: ldc 0.58
      // 0a61: dconst_1
      // 0a62: dconst_1
      // 0a63: ldc2_w 1.215999960899353
      // 0a66: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0a69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a6f: aastore
      // 0a70: dup
      // 0a71: bipush 15
      // 0a73: new net/minecraft/client/animation/Keyframe
      // 0a76: dup
      // 0a77: ldc 0.62
      // 0a79: dconst_1
      // 0a7a: dconst_1
      // 0a7b: ldc2_w 1.152999997138977
      // 0a7e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0a81: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a84: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a87: aastore
      // 0a88: dup
      // 0a89: bipush 16
      // 0a8b: new net/minecraft/client/animation/Keyframe
      // 0a8e: dup
      // 0a8f: ldc 0.67
      // 0a91: dconst_1
      // 0a92: dconst_1
      // 0a93: ldc2_w 1.090999960899353
      // 0a96: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0a99: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a9c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a9f: aastore
      // 0aa0: dup
      // 0aa1: bipush 17
      // 0aa3: new net/minecraft/client/animation/Keyframe
      // 0aa6: dup
      // 0aa7: ldc 0.71
      // 0aa9: dconst_1
      // 0aaa: dconst_1
      // 0aab: ldc2_w 1.031000018119812
      // 0aae: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0ab1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ab4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ab7: aastore
      // 0ab8: dup
      // 0ab9: bipush 18
      // 0abb: new net/minecraft/client/animation/Keyframe
      // 0abe: dup
      // 0abf: ldc 0.75
      // 0ac1: dconst_1
      // 0ac2: dconst_1
      // 0ac3: ldc2_w 0.9850000143051147
      // 0ac6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0ac9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0acc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0acf: aastore
      // 0ad0: dup
      // 0ad1: bipush 19
      // 0ad3: new net/minecraft/client/animation/Keyframe
      // 0ad6: dup
      // 0ad7: ldc 0.79
      // 0ad9: dconst_1
      // 0ada: dconst_1
      // 0adb: ldc2_w 0.9539999961853027
      // 0ade: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0ae1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ae4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ae7: aastore
      // 0ae8: dup
      // 0ae9: bipush 20
      // 0aeb: new net/minecraft/client/animation/Keyframe
      // 0aee: dup
      // 0aef: ldc 0.83
      // 0af1: dconst_1
      // 0af2: dconst_1
      // 0af3: ldc2_w 0.9350000023841858
      // 0af6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0af9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0afc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0aff: aastore
      // 0b00: dup
      // 0b01: bipush 21
      // 0b03: new net/minecraft/client/animation/Keyframe
      // 0b06: dup
      // 0b07: ldc 0.88
      // 0b09: dconst_1
      // 0b0a: dconst_1
      // 0b0b: ldc2_w 0.8999999761581421
      // 0b0e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0b11: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b14: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b17: aastore
      // 0b18: dup
      // 0b19: bipush 22
      // 0b1b: new net/minecraft/client/animation/Keyframe
      // 0b1e: dup
      // 0b1f: ldc 0.92
      // 0b21: dconst_1
      // 0b22: dconst_1
      // 0b23: ldc2_w 0.871999979019165
      // 0b26: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0b29: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b2c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b2f: aastore
      // 0b30: dup
      // 0b31: bipush 23
      // 0b33: new net/minecraft/client/animation/Keyframe
      // 0b36: dup
      // 0b37: ldc 0.96
      // 0b39: dconst_1
      // 0b3a: dconst_1
      // 0b3b: ldc2_w 0.8669999837875366
      // 0b3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0b41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b47: aastore
      // 0b48: dup
      // 0b49: bipush 24
      // 0b4b: new net/minecraft/client/animation/Keyframe
      // 0b4e: dup
      // 0b4f: fconst_1
      // 0b50: dconst_1
      // 0b51: dconst_1
      // 0b52: ldc2_w 0.8939999938011169
      // 0b55: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0b58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b5e: aastore
      // 0b5f: dup
      // 0b60: bipush 25
      // 0b62: new net/minecraft/client/animation/Keyframe
      // 0b65: dup
      // 0b66: ldc 1.04
      // 0b68: dconst_1
      // 0b69: dconst_1
      // 0b6a: ldc2_w 0.9419999718666077
      // 0b6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0b70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b76: aastore
      // 0b77: dup
      // 0b78: bipush 26
      // 0b7a: new net/minecraft/client/animation/Keyframe
      // 0b7d: dup
      // 0b7e: ldc 1.08
      // 0b80: dconst_1
      // 0b81: dconst_1
      // 0b82: ldc2_w 0.9869999885559082
      // 0b85: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0b88: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b8b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b8e: aastore
      // 0b8f: dup
      // 0b90: bipush 27
      // 0b92: new net/minecraft/client/animation/Keyframe
      // 0b95: dup
      // 0b96: ldc 1.12
      // 0b98: dconst_1
      // 0b99: dconst_1
      // 0b9a: ldc2_w 1.0049999952316284
      // 0b9d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0ba0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ba3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ba6: aastore
      // 0ba7: dup
      // 0ba8: bipush 28
      // 0baa: new net/minecraft/client/animation/Keyframe
      // 0bad: dup
      // 0bae: ldc 1.17
      // 0bb0: dconst_1
      // 0bb1: dconst_1
      // 0bb2: ldc2_w 1.0149999856948853
      // 0bb5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0bb8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bbb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bbe: aastore
      // 0bbf: dup
      // 0bc0: bipush 29
      // 0bc2: new net/minecraft/client/animation/Keyframe
      // 0bc5: dup
      // 0bc6: ldc 1.21
      // 0bc8: dconst_1
      // 0bc9: dconst_1
      // 0bca: ldc2_w 1.0199999809265137
      // 0bcd: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0bd0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bd3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bd6: aastore
      // 0bd7: dup
      // 0bd8: bipush 30
      // 0bda: new net/minecraft/client/animation/Keyframe
      // 0bdd: dup
      // 0bde: ldc 1.25
      // 0be0: dconst_1
      // 0be1: dconst_1
      // 0be2: ldc2_w 1.0219999551773071
      // 0be5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0be8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0beb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bee: aastore
      // 0bef: dup
      // 0bf0: bipush 31
      // 0bf2: new net/minecraft/client/animation/Keyframe
      // 0bf5: dup
      // 0bf6: ldc 1.29
      // 0bf8: dconst_1
      // 0bf9: dconst_1
      // 0bfa: ldc2_w 1.0219999551773071
      // 0bfd: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0c00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c06: aastore
      // 0c07: dup
      // 0c08: bipush 32
      // 0c0a: new net/minecraft/client/animation/Keyframe
      // 0c0d: dup
      // 0c0e: ldc 1.33
      // 0c10: dconst_1
      // 0c11: dconst_1
      // 0c12: ldc2_w 1.0210000276565552
      // 0c15: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0c18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c1e: aastore
      // 0c1f: dup
      // 0c20: bipush 33
      // 0c22: new net/minecraft/client/animation/Keyframe
      // 0c25: dup
      // 0c26: ldc 1.38
      // 0c28: dconst_1
      // 0c29: dconst_1
      // 0c2a: ldc2_w 1.0230000019073486
      // 0c2d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0c30: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c33: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c36: aastore
      // 0c37: dup
      // 0c38: bipush 34
      // 0c3a: new net/minecraft/client/animation/Keyframe
      // 0c3d: dup
      // 0c3e: ldc 1.42
      // 0c40: dconst_1
      // 0c41: dconst_1
      // 0c42: ldc2_w 1.0950000286102295
      // 0c45: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0c48: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c4b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c4e: aastore
      // 0c4f: dup
      // 0c50: bipush 35
      // 0c52: new net/minecraft/client/animation/Keyframe
      // 0c55: dup
      // 0c56: ldc 1.46
      // 0c58: dconst_1
      // 0c59: dconst_1
      // 0c5a: ldc2_w 1.1929999589920044
      // 0c5d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0c60: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c63: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c66: aastore
      // 0c67: dup
      // 0c68: bipush 36
      // 0c6a: new net/minecraft/client/animation/Keyframe
      // 0c6d: dup
      // 0c6e: ldc 1.5
      // 0c70: dconst_1
      // 0c71: dconst_1
      // 0c72: ldc2_w 1.2929999828338623
      // 0c75: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0c78: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c7b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c7e: aastore
      // 0c7f: dup
      // 0c80: bipush 37
      // 0c82: new net/minecraft/client/animation/Keyframe
      // 0c85: dup
      // 0c86: ldc 1.54
      // 0c88: dconst_1
      // 0c89: dconst_1
      // 0c8a: ldc2_w 1.3869999647140503
      // 0c8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0c90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c96: aastore
      // 0c97: dup
      // 0c98: bipush 38
      // 0c9a: new net/minecraft/client/animation/Keyframe
      // 0c9d: dup
      // 0c9e: ldc 1.58
      // 0ca0: dconst_1
      // 0ca1: dconst_1
      // 0ca2: ldc2_w 1.4709999561309814
      // 0ca5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0ca8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cae: aastore
      // 0caf: dup
      // 0cb0: bipush 39
      // 0cb2: new net/minecraft/client/animation/Keyframe
      // 0cb5: dup
      // 0cb6: ldc 1.62
      // 0cb8: dconst_1
      // 0cb9: dconst_1
      // 0cba: ldc2_w 1.5460000038146973
      // 0cbd: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0cc0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cc3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cc6: aastore
      // 0cc7: dup
      // 0cc8: bipush 40
      // 0cca: new net/minecraft/client/animation/Keyframe
      // 0ccd: dup
      // 0cce: ldc 1.67
      // 0cd0: dconst_1
      // 0cd1: dconst_1
      // 0cd2: ldc2_w 1.6150000095367432
      // 0cd5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0cd8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cdb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cde: aastore
      // 0cdf: dup
      // 0ce0: bipush 41
      // 0ce2: new net/minecraft/client/animation/Keyframe
      // 0ce5: dup
      // 0ce6: ldc_w 1.71
      // 0ce9: dconst_1
      // 0cea: dconst_1
      // 0ceb: ldc2_w 1.6799999475479126
      // 0cee: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0cf1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cf4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cf7: aastore
      // 0cf8: dup
      // 0cf9: bipush 42
      // 0cfb: new net/minecraft/client/animation/Keyframe
      // 0cfe: dup
      // 0cff: ldc_w 1.75
      // 0d02: dconst_1
      // 0d03: dconst_1
      // 0d04: ldc2_w 1.7389999628067017
      // 0d07: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0d0a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d0d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d10: aastore
      // 0d11: dup
      // 0d12: bipush 43
      // 0d14: new net/minecraft/client/animation/Keyframe
      // 0d17: dup
      // 0d18: ldc_w 1.79
      // 0d1b: dconst_1
      // 0d1c: dconst_1
      // 0d1d: ldc2_w 1.781000018119812
      // 0d20: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0d23: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d26: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d29: aastore
      // 0d2a: dup
      // 0d2b: bipush 44
      // 0d2d: new net/minecraft/client/animation/Keyframe
      // 0d30: dup
      // 0d31: ldc_w 1.83
      // 0d34: dconst_1
      // 0d35: dconst_1
      // 0d36: ldc2_w 1.7929999828338623
      // 0d39: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0d3c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d3f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d42: aastore
      // 0d43: dup
      // 0d44: bipush 45
      // 0d46: new net/minecraft/client/animation/Keyframe
      // 0d49: dup
      // 0d4a: ldc_w 1.88
      // 0d4d: dconst_1
      // 0d4e: dconst_1
      // 0d4f: ldc2_w 1.7829999923706055
      // 0d52: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0d55: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d58: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d5b: aastore
      // 0d5c: dup
      // 0d5d: bipush 46
      // 0d5f: new net/minecraft/client/animation/Keyframe
      // 0d62: dup
      // 0d63: ldc_w 1.92
      // 0d66: dconst_1
      // 0d67: dconst_1
      // 0d68: ldc2_w 1.7649999856948853
      // 0d6b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0d6e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d71: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d74: aastore
      // 0d75: dup
      // 0d76: bipush 47
      // 0d78: new net/minecraft/client/animation/Keyframe
      // 0d7b: dup
      // 0d7c: ldc_w 1.96
      // 0d7f: dconst_1
      // 0d80: dconst_1
      // 0d81: ldc2_w 1.7410000562667847
      // 0d84: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0d87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d8d: aastore
      // 0d8e: dup
      // 0d8f: bipush 48
      // 0d91: new net/minecraft/client/animation/Keyframe
      // 0d94: dup
      // 0d95: fconst_2
      // 0d96: dconst_1
      // 0d97: dconst_1
      // 0d98: ldc2_w 1.7170000076293945
      // 0d9b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0d9e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0da1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0da4: aastore
      // 0da5: dup
      // 0da6: bipush 49
      // 0da8: new net/minecraft/client/animation/Keyframe
      // 0dab: dup
      // 0dac: ldc 2.04
      // 0dae: dconst_1
      // 0daf: dconst_1
      // 0db0: ldc2_w 1.7020000219345093
      // 0db3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0db6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0db9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dbc: aastore
      // 0dbd: dup
      // 0dbe: bipush 50
      // 0dc0: new net/minecraft/client/animation/Keyframe
      // 0dc3: dup
      // 0dc4: ldc 2.08
      // 0dc6: dconst_1
      // 0dc7: dconst_1
      // 0dc8: ldc2_w 1.7209999561309814
      // 0dcb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0dce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dd1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dd4: aastore
      // 0dd5: dup
      // 0dd6: bipush 51
      // 0dd8: new net/minecraft/client/animation/Keyframe
      // 0ddb: dup
      // 0ddc: ldc 2.12
      // 0dde: dconst_1
      // 0ddf: dconst_1
      // 0de0: ldc2_w 1.8029999732971191
      // 0de3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0de6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0de9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dec: aastore
      // 0ded: dup
      // 0dee: bipush 52
      // 0df0: new net/minecraft/client/animation/Keyframe
      // 0df3: dup
      // 0df4: ldc 2.17
      // 0df6: dconst_1
      // 0df7: dconst_1
      // 0df8: ldc2_w 1.9700000286102295
      // 0dfb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0dfe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e01: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e04: aastore
      // 0e05: dup
      // 0e06: bipush 53
      // 0e08: new net/minecraft/client/animation/Keyframe
      // 0e0b: dup
      // 0e0c: ldc 2.21
      // 0e0e: dconst_1
      // 0e0f: dconst_1
      // 0e10: ldc2_w 1.805999994277954
      // 0e13: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0e16: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e19: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e1c: aastore
      // 0e1d: dup
      // 0e1e: bipush 54
      // 0e20: new net/minecraft/client/animation/Keyframe
      // 0e23: dup
      // 0e24: ldc 2.25
      // 0e26: dconst_1
      // 0e27: dconst_1
      // 0e28: ldc2_w 1.7680000066757202
      // 0e2b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0e2e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e31: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e34: aastore
      // 0e35: dup
      // 0e36: bipush 55
      // 0e38: new net/minecraft/client/animation/Keyframe
      // 0e3b: dup
      // 0e3c: ldc 2.29
      // 0e3e: dconst_1
      // 0e3f: dconst_1
      // 0e40: ldc2_w 1.7829999923706055
      // 0e43: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0e46: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e49: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e4c: aastore
      // 0e4d: dup
      // 0e4e: bipush 56
      // 0e50: new net/minecraft/client/animation/Keyframe
      // 0e53: dup
      // 0e54: ldc 2.33
      // 0e56: dconst_1
      // 0e57: dconst_1
      // 0e58: ldc2_w 1.7259999513626099
      // 0e5b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0e5e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e61: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e64: aastore
      // 0e65: dup
      // 0e66: bipush 57
      // 0e68: new net/minecraft/client/animation/Keyframe
      // 0e6b: dup
      // 0e6c: ldc 2.38
      // 0e6e: dconst_1
      // 0e6f: dconst_1
      // 0e70: ldc2_w 1.7369999885559082
      // 0e73: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0e76: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e79: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e7c: aastore
      // 0e7d: dup
      // 0e7e: bipush 58
      // 0e80: new net/minecraft/client/animation/Keyframe
      // 0e83: dup
      // 0e84: ldc 2.42
      // 0e86: dconst_1
      // 0e87: dconst_1
      // 0e88: ldc2_w 1.7350000143051147
      // 0e8b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0e8e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e91: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e94: aastore
      // 0e95: dup
      // 0e96: bipush 59
      // 0e98: new net/minecraft/client/animation/Keyframe
      // 0e9b: dup
      // 0e9c: ldc 2.46
      // 0e9e: dconst_1
      // 0e9f: dconst_1
      // 0ea0: ldc2_w 1.75
      // 0ea3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0ea6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ea9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0eac: aastore
      // 0ead: dup
      // 0eae: bipush 60
      // 0eb0: new net/minecraft/client/animation/Keyframe
      // 0eb3: dup
      // 0eb4: ldc 2.5
      // 0eb6: dconst_1
      // 0eb7: dconst_1
      // 0eb8: ldc2_w 1.7510000467300415
      // 0ebb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0ebe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ec1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ec4: aastore
      // 0ec5: dup
      // 0ec6: bipush 61
      // 0ec8: new net/minecraft/client/animation/Keyframe
      // 0ecb: dup
      // 0ecc: ldc 2.54
      // 0ece: dconst_1
      // 0ecf: dconst_1
      // 0ed0: ldc2_w 1.7599999904632568
      // 0ed3: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0ed6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ed9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0edc: aastore
      // 0edd: dup
      // 0ede: bipush 62
      // 0ee0: new net/minecraft/client/animation/Keyframe
      // 0ee3: dup
      // 0ee4: ldc 2.58
      // 0ee6: dconst_1
      // 0ee7: dconst_1
      // 0ee8: ldc2_w 1.777999997138977
      // 0eeb: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0eee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ef1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ef4: aastore
      // 0ef5: dup
      // 0ef6: bipush 63
      // 0ef8: new net/minecraft/client/animation/Keyframe
      // 0efb: dup
      // 0efc: ldc 2.62
      // 0efe: dconst_1
      // 0eff: dconst_1
      // 0f00: ldc2_w 1.777999997138977
      // 0f03: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0f06: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f09: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f0c: aastore
      // 0f0d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0f10: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0f13: ldc_w "CTR_upperarm_right.001"
      // 0f16: new net/minecraft/client/animation/AnimationChannel
      // 0f19: dup
      // 0f1a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0f1d: bipush 63
      // 0f1f: anewarray 24
      // 0f22: dup
      // 0f23: bipush 0
      // 0f24: new net/minecraft/client/animation/Keyframe
      // 0f27: dup
      // 0f28: fconst_0
      // 0f29: ldc_w 0.168
      // 0f2c: ldc_w 17.864
      // 0f2f: ldc_w 2.048
      // 0f32: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f35: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f38: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f3b: aastore
      // 0f3c: dup
      // 0f3d: bipush 1
      // 0f3e: new net/minecraft/client/animation/Keyframe
      // 0f41: dup
      // 0f42: ldc 0.04
      // 0f44: ldc_w -0.082
      // 0f47: ldc_w 11.051
      // 0f4a: ldc_w 1.666
      // 0f4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f56: aastore
      // 0f57: dup
      // 0f58: bipush 2
      // 0f59: new net/minecraft/client/animation/Keyframe
      // 0f5c: dup
      // 0f5d: ldc 0.08
      // 0f5f: ldc_w -1.329
      // 0f62: ldc_w -8.97
      // 0f65: ldc_w 1.592
      // 0f68: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f6b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f6e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f71: aastore
      // 0f72: dup
      // 0f73: bipush 3
      // 0f74: new net/minecraft/client/animation/Keyframe
      // 0f77: dup
      // 0f78: ldc 0.12
      // 0f7a: ldc_w -1.2
      // 0f7d: ldc_w -33.888
      // 0f80: ldc_w 1.211
      // 0f83: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f86: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f89: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f8c: aastore
      // 0f8d: dup
      // 0f8e: bipush 4
      // 0f8f: new net/minecraft/client/animation/Keyframe
      // 0f92: dup
      // 0f93: ldc 0.17
      // 0f95: ldc_w -1.184
      // 0f98: ldc_w -42.544
      // 0f9b: ldc_w 1.198
      // 0f9e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fa1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fa4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fa7: aastore
      // 0fa8: dup
      // 0fa9: bipush 5
      // 0faa: new net/minecraft/client/animation/Keyframe
      // 0fad: dup
      // 0fae: ldc 0.21
      // 0fb0: ldc_w -1.248
      // 0fb3: ldc_w -20.514
      // 0fb6: ldc_w 1.255
      // 0fb9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fbc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fbf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fc2: aastore
      // 0fc3: dup
      // 0fc4: bipush 6
      // 0fc6: new net/minecraft/client/animation/Keyframe
      // 0fc9: dup
      // 0fca: ldc 0.25
      // 0fcc: ldc_w -0.953
      // 0fcf: ldc_w 8.855
      // 0fd2: ldc_w 0.95
      // 0fd5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fd8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fdb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fde: aastore
      // 0fdf: dup
      // 0fe0: bipush 7
      // 0fe2: new net/minecraft/client/animation/Keyframe
      // 0fe5: dup
      // 0fe6: ldc 0.29
      // 0fe8: ldc_w -1.059
      // 0feb: ldc_w 21.261
      // 0fee: ldc_w 1.052
      // 0ff1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ff4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ff7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ffa: aastore
      // 0ffb: dup
      // 0ffc: bipush 8
      // 0ffe: new net/minecraft/client/animation/Keyframe
      // 1001: dup
      // 1002: ldc 0.33
      // 1004: ldc_w -1.038
      // 1007: ldc_w 16.982
      // 100a: ldc_w 1.032
      // 100d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1010: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1013: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1016: aastore
      // 1017: dup
      // 1018: bipush 9
      // 101a: new net/minecraft/client/animation/Keyframe
      // 101d: dup
      // 101e: ldc 0.38
      // 1020: ldc_w -0.98
      // 1023: ldc_w 10.62
      // 1026: ldc_w 0.977
      // 1029: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 102c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 102f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1032: aastore
      // 1033: dup
      // 1034: bipush 10
      // 1036: new net/minecraft/client/animation/Keyframe
      // 1039: dup
      // 103a: ldc 0.42
      // 103c: ldc_w 2.519
      // 103f: ldc_w 2.204
      // 1042: ldc_w 1.832
      // 1045: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1048: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 104b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 104e: aastore
      // 104f: dup
      // 1050: bipush 11
      // 1052: new net/minecraft/client/animation/Keyframe
      // 1055: dup
      // 1056: ldc 0.46
      // 1058: ldc_w 3.517
      // 105b: ldc_w -8.927
      // 105e: ldc_w 9.669
      // 1061: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1064: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1067: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 106a: aastore
      // 106b: dup
      // 106c: bipush 12
      // 106e: new net/minecraft/client/animation/Keyframe
      // 1071: dup
      // 1072: ldc 0.5
      // 1074: ldc_w 8.222
      // 1077: ldc_w -24.233
      // 107a: ldc_w 12.292
      // 107d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1080: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1083: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1086: aastore
      // 1087: dup
      // 1088: bipush 13
      // 108a: new net/minecraft/client/animation/Keyframe
      // 108d: dup
      // 108e: ldc 0.54
      // 1090: ldc_w 9.871
      // 1093: ldc_w -42.235
      // 1096: ldc_w 13.224
      // 1099: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 109c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 109f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10a2: aastore
      // 10a3: dup
      // 10a4: bipush 14
      // 10a6: new net/minecraft/client/animation/Keyframe
      // 10a9: dup
      // 10aa: ldc 0.58
      // 10ac: ldc_w 9.488
      // 10af: ldc_w -59.3
      // 10b2: ldc_w 12.458
      // 10b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10be: aastore
      // 10bf: dup
      // 10c0: bipush 15
      // 10c2: new net/minecraft/client/animation/Keyframe
      // 10c5: dup
      // 10c6: ldc 0.62
      // 10c8: ldc_w 8.438
      // 10cb: ldc_w -72.875
      // 10ce: ldc_w 11.201
      // 10d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10da: aastore
      // 10db: dup
      // 10dc: bipush 16
      // 10de: new net/minecraft/client/animation/Keyframe
      // 10e1: dup
      // 10e2: ldc 0.67
      // 10e4: ldc_w 7.493
      // 10e7: ldc_w -81.985
      // 10ea: ldc_w 10.146
      // 10ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10f6: aastore
      // 10f7: dup
      // 10f8: bipush 17
      // 10fa: new net/minecraft/client/animation/Keyframe
      // 10fd: dup
      // 10fe: ldc 0.71
      // 1100: ldc_w 7.652
      // 1103: ldc_w -84.551
      // 1106: ldc_w 10.295
      // 1109: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 110c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 110f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1112: aastore
      // 1113: dup
      // 1114: bipush 18
      // 1116: new net/minecraft/client/animation/Keyframe
      // 1119: dup
      // 111a: ldc 0.75
      // 111c: ldc_w 8.662
      // 111f: ldc_w -82.683
      // 1122: ldc_w 11.354
      // 1125: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1128: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 112b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 112e: aastore
      // 112f: dup
      // 1130: bipush 19
      // 1132: new net/minecraft/client/animation/Keyframe
      // 1135: dup
      // 1136: ldc 0.79
      // 1138: ldc_w 9.488
      // 113b: ldc_w -80.426
      // 113e: ldc_w 12.228
      // 1141: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1144: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1147: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 114a: aastore
      // 114b: dup
      // 114c: bipush 20
      // 114e: new net/minecraft/client/animation/Keyframe
      // 1151: dup
      // 1152: ldc 0.83
      // 1154: ldc_w 10.015
      // 1157: ldc_w -78.085
      // 115a: ldc_w 12.796
      // 115d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1160: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1163: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1166: aastore
      // 1167: dup
      // 1168: bipush 21
      // 116a: new net/minecraft/client/animation/Keyframe
      // 116d: dup
      // 116e: ldc 0.88
      // 1170: ldc_w 10.155
      // 1173: ldc_w -75.944
      // 1176: ldc_w 12.961
      // 1179: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 117c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 117f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1182: aastore
      // 1183: dup
      // 1184: bipush 22
      // 1186: new net/minecraft/client/animation/Keyframe
      // 1189: dup
      // 118a: ldc 0.92
      // 118c: ldc_w 9.98
      // 118f: ldc_w -74.508
      // 1192: ldc_w 12.794
      // 1195: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1198: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 119b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 119e: aastore
      // 119f: dup
      // 11a0: bipush 23
      // 11a2: new net/minecraft/client/animation/Keyframe
      // 11a5: dup
      // 11a6: ldc 0.96
      // 11a8: ldc_w 9.275
      // 11ab: ldc_w -73.963
      // 11ae: ldc_w 12.064
      // 11b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11ba: aastore
      // 11bb: dup
      // 11bc: bipush 24
      // 11be: new net/minecraft/client/animation/Keyframe
      // 11c1: dup
      // 11c2: fconst_1
      // 11c3: ldc_w 8.145
      // 11c6: ldc_w -73.886
      // 11c9: ldc_w 10.886
      // 11cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11d5: aastore
      // 11d6: dup
      // 11d7: bipush 25
      // 11d9: new net/minecraft/client/animation/Keyframe
      // 11dc: dup
      // 11dd: ldc 1.04
      // 11df: ldc_w 6.992
      // 11e2: ldc_w -74.11
      // 11e5: ldc_w 9.681
      // 11e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11f1: aastore
      // 11f2: dup
      // 11f3: bipush 26
      // 11f5: new net/minecraft/client/animation/Keyframe
      // 11f8: dup
      // 11f9: ldc 1.08
      // 11fb: ldc_w 6.19
      // 11fe: ldc_w -74.591
      // 1201: ldc_w 8.841
      // 1204: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1207: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 120a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 120d: aastore
      // 120e: dup
      // 120f: bipush 27
      // 1211: new net/minecraft/client/animation/Keyframe
      // 1214: dup
      // 1215: ldc 1.12
      // 1217: ldc_w 5.982
      // 121a: ldc_w -75.322
      // 121d: ldc_w 8.62
      // 1220: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1223: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1226: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1229: aastore
      // 122a: dup
      // 122b: bipush 28
      // 122d: new net/minecraft/client/animation/Keyframe
      // 1230: dup
      // 1231: ldc 1.17
      // 1233: ldc_w 6.436
      // 1236: ldc_w -76.085
      // 1239: ldc_w 9.087
      // 123c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 123f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1242: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1245: aastore
      // 1246: dup
      // 1247: bipush 29
      // 1249: new net/minecraft/client/animation/Keyframe
      // 124c: dup
      // 124d: ldc 1.21
      // 124f: ldc_w 7.442
      // 1252: ldc_w -76.655
      // 1255: ldc_w 10.132
      // 1258: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 125b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 125e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1261: aastore
      // 1262: dup
      // 1263: bipush 30
      // 1265: new net/minecraft/client/animation/Keyframe
      // 1268: dup
      // 1269: ldc 1.25
      // 126b: ldc_w 8.797
      // 126e: ldc_w -76.883
      // 1271: ldc_w 11.54
      // 1274: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1277: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 127a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 127d: aastore
      // 127e: dup
      // 127f: bipush 31
      // 1281: new net/minecraft/client/animation/Keyframe
      // 1284: dup
      // 1285: ldc 1.29
      // 1287: ldc_w 10.415
      // 128a: ldc_w -76.63
      // 128d: ldc_w 13.226
      // 1290: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1293: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1296: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1299: aastore
      // 129a: dup
      // 129b: bipush 32
      // 129d: new net/minecraft/client/animation/Keyframe
      // 12a0: dup
      // 12a1: ldc 1.33
      // 12a3: ldc_w 12.139
      // 12a6: ldc_w -75.8
      // 12a9: ldc_w 15.028
      // 12ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12b5: aastore
      // 12b6: dup
      // 12b7: bipush 33
      // 12b9: new net/minecraft/client/animation/Keyframe
      // 12bc: dup
      // 12bd: ldc 1.38
      // 12bf: ldc_w 13.788
      // 12c2: ldc_w -74.678
      // 12c5: ldc_w 16.759
      // 12c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12d1: aastore
      // 12d2: dup
      // 12d3: bipush 34
      // 12d5: new net/minecraft/client/animation/Keyframe
      // 12d8: dup
      // 12d9: ldc 1.42
      // 12db: ldc_w 15.331
      // 12de: ldc_w -73.489
      // 12e1: ldc_w 18.383
      // 12e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12ed: aastore
      // 12ee: dup
      // 12ef: bipush 35
      // 12f1: new net/minecraft/client/animation/Keyframe
      // 12f4: dup
      // 12f5: ldc 1.46
      // 12f7: ldc_w 16.726
      // 12fa: ldc_w -72.205
      // 12fd: ldc_w 19.856
      // 1300: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1303: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1306: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1309: aastore
      // 130a: dup
      // 130b: bipush 36
      // 130d: new net/minecraft/client/animation/Keyframe
      // 1310: dup
      // 1311: ldc 1.5
      // 1313: ldc_w 17.92
      // 1316: ldc_w -70.851
      // 1319: ldc_w 21.124
      // 131c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 131f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1322: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1325: aastore
      // 1326: dup
      // 1327: bipush 37
      // 1329: new net/minecraft/client/animation/Keyframe
      // 132c: dup
      // 132d: ldc 1.54
      // 132f: ldc_w 18.925
      // 1332: ldc_w -69.333
      // 1335: ldc_w 22.201
      // 1338: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 133b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 133e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1341: aastore
      // 1342: dup
      // 1343: bipush 38
      // 1345: new net/minecraft/client/animation/Keyframe
      // 1348: dup
      // 1349: ldc 1.58
      // 134b: ldc_w 19.688
      // 134e: ldc_w -67.541
      // 1351: ldc_w 23.034
      // 1354: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1357: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 135a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 135d: aastore
      // 135e: dup
      // 135f: bipush 39
      // 1361: new net/minecraft/client/animation/Keyframe
      // 1364: dup
      // 1365: ldc 1.62
      // 1367: ldc_w 19.34
      // 136a: ldc_w -65.887
      // 136d: ldc_w 22.705
      // 1370: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1373: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1376: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1379: aastore
      // 137a: dup
      // 137b: bipush 40
      // 137d: new net/minecraft/client/animation/Keyframe
      // 1380: dup
      // 1381: ldc 1.67
      // 1383: ldc_w 17.097
      // 1386: ldc_w -64.598
      // 1389: ldc_w 20.38
      // 138c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 138f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1392: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1395: aastore
      // 1396: dup
      // 1397: bipush 41
      // 1399: new net/minecraft/client/animation/Keyframe
      // 139c: dup
      // 139d: ldc_w 1.71
      // 13a0: ldc_w 12.953
      // 13a3: ldc_w -63.107
      // 13a6: ldc_w 16.053
      // 13a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13b2: aastore
      // 13b3: dup
      // 13b4: bipush 42
      // 13b6: new net/minecraft/client/animation/Keyframe
      // 13b9: dup
      // 13ba: ldc_w 1.75
      // 13bd: ldc_w 7.363
      // 13c0: ldc_w -60.681
      // 13c3: ldc_w 10.195
      // 13c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13cf: aastore
      // 13d0: dup
      // 13d1: bipush 43
      // 13d3: new net/minecraft/client/animation/Keyframe
      // 13d6: dup
      // 13d7: ldc_w 1.79
      // 13da: ldc_w 1.365
      // 13dd: ldc_w -56.798
      // 13e0: ldc_w 3.877
      // 13e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13ec: aastore
      // 13ed: dup
      // 13ee: bipush 44
      // 13f0: new net/minecraft/client/animation/Keyframe
      // 13f3: dup
      // 13f4: ldc_w 1.83
      // 13f7: ldc_w -4.095
      // 13fa: ldc_w -51.139
      // 13fd: ldc_w -1.936
      // 1400: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1403: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1406: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1409: aastore
      // 140a: dup
      // 140b: bipush 45
      // 140d: new net/minecraft/client/animation/Keyframe
      // 1410: dup
      // 1411: ldc_w 1.88
      // 1414: ldc_w -8.323
      // 1417: ldc_w -43.763
      // 141a: ldc_w -6.549
      // 141d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1420: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1423: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1426: aastore
      // 1427: dup
      // 1428: bipush 46
      // 142a: new net/minecraft/client/animation/Keyframe
      // 142d: dup
      // 142e: ldc_w 1.92
      // 1431: ldc_w -11.053
      // 1434: ldc_w -35.113
      // 1437: ldc_w -9.728
      // 143a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 143d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1440: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1443: aastore
      // 1444: dup
      // 1445: bipush 47
      // 1447: new net/minecraft/client/animation/Keyframe
      // 144a: dup
      // 144b: ldc_w 1.96
      // 144e: ldc_w -12.254
      // 1451: ldc_w -25.403
      // 1454: ldc_w -11.569
      // 1457: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 145a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 145d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1460: aastore
      // 1461: dup
      // 1462: bipush 48
      // 1464: new net/minecraft/client/animation/Keyframe
      // 1467: dup
      // 1468: fconst_2
      // 1469: ldc_w -11.525
      // 146c: ldc_w -14.119
      // 146f: ldc_w -12.343
      // 1472: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1475: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1478: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 147b: aastore
      // 147c: dup
      // 147d: bipush 49
      // 147f: new net/minecraft/client/animation/Keyframe
      // 1482: dup
      // 1483: ldc 2.04
      // 1485: ldc_w -14.642
      // 1488: ldc_w 2.019
      // 148b: ldc_w -4.472
      // 148e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1491: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1494: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1497: aastore
      // 1498: dup
      // 1499: bipush 50
      // 149b: new net/minecraft/client/animation/Keyframe
      // 149e: dup
      // 149f: ldc 2.08
      // 14a1: ldc_w 1.699
      // 14a4: ldc_w 35.744
      // 14a7: ldc_w 3.634
      // 14aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14b3: aastore
      // 14b4: dup
      // 14b5: bipush 51
      // 14b7: new net/minecraft/client/animation/Keyframe
      // 14ba: dup
      // 14bb: ldc 2.12
      // 14bd: ldc_w 55.377
      // 14c0: ldc_w 1.289
      // 14c3: ldc_w 16.588
      // 14c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14cf: aastore
      // 14d0: dup
      // 14d1: bipush 52
      // 14d3: new net/minecraft/client/animation/Keyframe
      // 14d6: dup
      // 14d7: ldc 2.17
      // 14d9: ldc_w 24.16
      // 14dc: ldc_w -17.749
      // 14df: ldc_w 32.759
      // 14e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14eb: aastore
      // 14ec: dup
      // 14ed: bipush 53
      // 14ef: new net/minecraft/client/animation/Keyframe
      // 14f2: dup
      // 14f3: ldc 2.21
      // 14f5: ldc_w 9.721
      // 14f8: ldc_w -5.244
      // 14fb: ldc_w 36.679
      // 14fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1501: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1504: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1507: aastore
      // 1508: dup
      // 1509: bipush 54
      // 150b: new net/minecraft/client/animation/Keyframe
      // 150e: dup
      // 150f: ldc 2.25
      // 1511: ldc_w -1.605
      // 1514: ldc_w -6.208
      // 1517: ldc_w 1.607
      // 151a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 151d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1520: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1523: aastore
      // 1524: dup
      // 1525: bipush 55
      // 1527: new net/minecraft/client/animation/Keyframe
      // 152a: dup
      // 152b: ldc 2.29
      // 152d: ldc_w -2.539
      // 1530: ldc_w -3.257
      // 1533: ldc 2.54
      // 1535: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1538: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 153b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 153e: aastore
      // 153f: dup
      // 1540: bipush 56
      // 1542: new net/minecraft/client/animation/Keyframe
      // 1545: dup
      // 1546: ldc 2.33
      // 1548: ldc_w -1.672
      // 154b: ldc_w -5.657
      // 154e: ldc_w 1.673
      // 1551: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1554: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1557: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 155a: aastore
      // 155b: dup
      // 155c: bipush 57
      // 155e: new net/minecraft/client/animation/Keyframe
      // 1561: dup
      // 1562: ldc 2.38
      // 1564: ldc_w -1.739
      // 1567: ldc_w -5.221
      // 156a: ldc_w 1.741
      // 156d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1570: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1573: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1576: aastore
      // 1577: dup
      // 1578: bipush 58
      // 157a: new net/minecraft/client/animation/Keyframe
      // 157d: dup
      // 157e: ldc 2.42
      // 1580: ldc_w -2.234
      // 1583: ldc_w -3.667
      // 1586: ldc_w 2.235
      // 1589: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 158c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 158f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1592: aastore
      // 1593: dup
      // 1594: bipush 59
      // 1596: new net/minecraft/client/animation/Keyframe
      // 1599: dup
      // 159a: ldc 2.46
      // 159c: ldc_w -1.803
      // 159f: ldc_w -4.889
      // 15a2: ldc_w 1.805
      // 15a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15ae: aastore
      // 15af: dup
      // 15b0: bipush 60
      // 15b2: new net/minecraft/client/animation/Keyframe
      // 15b5: dup
      // 15b6: ldc 2.5
      // 15b8: ldc_w -1.576
      // 15bb: ldc_w -6.512
      // 15be: ldc_w 1.578
      // 15c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15ca: aastore
      // 15cb: dup
      // 15cc: bipush 61
      // 15ce: new net/minecraft/client/animation/Keyframe
      // 15d1: dup
      // 15d2: ldc 2.54
      // 15d4: ldc_w -1.45
      // 15d7: ldc_w -8.427
      // 15da: ldc_w 1.453
      // 15dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15e6: aastore
      // 15e7: dup
      // 15e8: bipush 62
      // 15ea: new net/minecraft/client/animation/Keyframe
      // 15ed: dup
      // 15ee: ldc 2.62
      // 15f0: ldc_w -1.45
      // 15f3: ldc_w -8.427
      // 15f6: ldc_w 1.453
      // 15f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1602: aastore
      // 1603: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1606: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1609: ldc_w "CTR_upperarm_right.001"
      // 160c: new net/minecraft/client/animation/AnimationChannel
      // 160f: dup
      // 1610: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1613: bipush 63
      // 1615: anewarray 24
      // 1618: dup
      // 1619: bipush 0
      // 161a: new net/minecraft/client/animation/Keyframe
      // 161d: dup
      // 161e: fconst_0
      // 161f: dconst_1
      // 1620: dconst_1
      // 1621: ldc2_w 1.6740000247955322
      // 1624: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1627: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 162a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 162d: aastore
      // 162e: dup
      // 162f: bipush 1
      // 1630: new net/minecraft/client/animation/Keyframe
      // 1633: dup
      // 1634: ldc 0.04
      // 1636: dconst_1
      // 1637: dconst_1
      // 1638: ldc2_w 1.6160000562667847
      // 163b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 163e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1641: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1644: aastore
      // 1645: dup
      // 1646: bipush 2
      // 1647: new net/minecraft/client/animation/Keyframe
      // 164a: dup
      // 164b: ldc 0.08
      // 164d: dconst_1
      // 164e: dconst_1
      // 164f: ldc2_w 1.5820000171661377
      // 1652: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1655: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1658: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 165b: aastore
      // 165c: dup
      // 165d: bipush 3
      // 165e: new net/minecraft/client/animation/Keyframe
      // 1661: dup
      // 1662: ldc 0.12
      // 1664: dconst_1
      // 1665: dconst_1
      // 1666: ldc2_w 1.715000033378601
      // 1669: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 166c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 166f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1672: aastore
      // 1673: dup
      // 1674: bipush 4
      // 1675: new net/minecraft/client/animation/Keyframe
      // 1678: dup
      // 1679: ldc 0.17
      // 167b: dconst_1
      // 167c: dconst_1
      // 167d: ldc2_w 1.7039999961853027
      // 1680: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1683: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1686: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1689: aastore
      // 168a: dup
      // 168b: bipush 5
      // 168c: new net/minecraft/client/animation/Keyframe
      // 168f: dup
      // 1690: ldc 0.21
      // 1692: dconst_1
      // 1693: dconst_1
      // 1694: ldc2_w 1.3509999513626099
      // 1697: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 169a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 169d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16a0: aastore
      // 16a1: dup
      // 16a2: bipush 6
      // 16a4: new net/minecraft/client/animation/Keyframe
      // 16a7: dup
      // 16a8: ldc 0.25
      // 16aa: dconst_1
      // 16ab: dconst_1
      // 16ac: ldc2_w 1.0980000495910645
      // 16af: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 16b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16b8: aastore
      // 16b9: dup
      // 16ba: bipush 7
      // 16bc: new net/minecraft/client/animation/Keyframe
      // 16bf: dup
      // 16c0: ldc 0.29
      // 16c2: dconst_1
      // 16c3: dconst_1
      // 16c4: ldc2_w 1.0709999799728394
      // 16c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 16ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16d0: aastore
      // 16d1: dup
      // 16d2: bipush 8
      // 16d4: new net/minecraft/client/animation/Keyframe
      // 16d7: dup
      // 16d8: ldc 0.33
      // 16da: dconst_1
      // 16db: dconst_1
      // 16dc: ldc2_w 1.121999979019165
      // 16df: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 16e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16e8: aastore
      // 16e9: dup
      // 16ea: bipush 9
      // 16ec: new net/minecraft/client/animation/Keyframe
      // 16ef: dup
      // 16f0: ldc 0.38
      // 16f2: dconst_1
      // 16f3: dconst_1
      // 16f4: ldc2_w 1.1629999876022339
      // 16f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 16fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1700: aastore
      // 1701: dup
      // 1702: bipush 10
      // 1704: new net/minecraft/client/animation/Keyframe
      // 1707: dup
      // 1708: ldc 0.42
      // 170a: dconst_1
      // 170b: dconst_1
      // 170c: ldc2_w 1.2239999771118164
      // 170f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1712: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1715: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1718: aastore
      // 1719: dup
      // 171a: bipush 11
      // 171c: new net/minecraft/client/animation/Keyframe
      // 171f: dup
      // 1720: ldc 0.46
      // 1722: dconst_1
      // 1723: dconst_1
      // 1724: ldc2_w 1.347000002861023
      // 1727: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 172a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 172d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1730: aastore
      // 1731: dup
      // 1732: bipush 12
      // 1734: new net/minecraft/client/animation/Keyframe
      // 1737: dup
      // 1738: ldc 0.5
      // 173a: dconst_1
      // 173b: dconst_1
      // 173c: ldc2_w 1.5679999589920044
      // 173f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1742: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1745: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1748: aastore
      // 1749: dup
      // 174a: bipush 13
      // 174c: new net/minecraft/client/animation/Keyframe
      // 174f: dup
      // 1750: ldc 0.54
      // 1752: dconst_1
      // 1753: dconst_1
      // 1754: ldc2_w 1.843999981880188
      // 1757: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 175a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 175d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1760: aastore
      // 1761: dup
      // 1762: bipush 14
      // 1764: new net/minecraft/client/animation/Keyframe
      // 1767: dup
      // 1768: ldc 0.58
      // 176a: dconst_1
      // 176b: dconst_1
      // 176c: ldc2_w 2.0739998817443848
      // 176f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1772: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1775: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1778: aastore
      // 1779: dup
      // 177a: bipush 15
      // 177c: new net/minecraft/client/animation/Keyframe
      // 177f: dup
      // 1780: ldc 0.62
      // 1782: dconst_1
      // 1783: dconst_1
      // 1784: ldc2_w 2.2019999027252197
      // 1787: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 178a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 178d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1790: aastore
      // 1791: dup
      // 1792: bipush 16
      // 1794: new net/minecraft/client/animation/Keyframe
      // 1797: dup
      // 1798: ldc 0.67
      // 179a: dconst_1
      // 179b: dconst_1
      // 179c: ldc2_w 2.247999906539917
      // 179f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 17a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17a8: aastore
      // 17a9: dup
      // 17aa: bipush 17
      // 17ac: new net/minecraft/client/animation/Keyframe
      // 17af: dup
      // 17b0: ldc 0.71
      // 17b2: dconst_1
      // 17b3: dconst_1
      // 17b4: ldc2_w 2.242000102996826
      // 17b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 17ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17c0: aastore
      // 17c1: dup
      // 17c2: bipush 18
      // 17c4: new net/minecraft/client/animation/Keyframe
      // 17c7: dup
      // 17c8: ldc 0.75
      // 17ca: dconst_1
      // 17cb: dconst_1
      // 17cc: ldc2_w 2.2119998931884766
      // 17cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 17d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17d8: aastore
      // 17d9: dup
      // 17da: bipush 19
      // 17dc: new net/minecraft/client/animation/Keyframe
      // 17df: dup
      // 17e0: ldc 0.79
      // 17e2: dconst_1
      // 17e3: dconst_1
      // 17e4: ldc2_w 2.1730000972747803
      // 17e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 17ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17f0: aastore
      // 17f1: dup
      // 17f2: bipush 20
      // 17f4: new net/minecraft/client/animation/Keyframe
      // 17f7: dup
      // 17f8: ldc 0.83
      // 17fa: dconst_1
      // 17fb: dconst_1
      // 17fc: ldc2_w 2.132999897003174
      // 17ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1802: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1805: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1808: aastore
      // 1809: dup
      // 180a: bipush 21
      // 180c: new net/minecraft/client/animation/Keyframe
      // 180f: dup
      // 1810: ldc 0.88
      // 1812: dconst_1
      // 1813: dconst_1
      // 1814: ldc2_w 2.0969998836517334
      // 1817: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 181a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 181d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1820: aastore
      // 1821: dup
      // 1822: bipush 22
      // 1824: new net/minecraft/client/animation/Keyframe
      // 1827: dup
      // 1828: ldc 0.92
      // 182a: dconst_1
      // 182b: dconst_1
      // 182c: ldc2_w 2.075000047683716
      // 182f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1832: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1835: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1838: aastore
      // 1839: dup
      // 183a: bipush 23
      // 183c: new net/minecraft/client/animation/Keyframe
      // 183f: dup
      // 1840: ldc 0.96
      // 1842: dconst_1
      // 1843: dconst_1
      // 1844: ldc2_w 2.063999891281128
      // 1847: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 184a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 184d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1850: aastore
      // 1851: dup
      // 1852: bipush 24
      // 1854: new net/minecraft/client/animation/Keyframe
      // 1857: dup
      // 1858: fconst_1
      // 1859: dconst_1
      // 185a: dconst_1
      // 185b: ldc2_w 2.055000066757202
      // 185e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1861: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1864: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1867: aastore
      // 1868: dup
      // 1869: bipush 25
      // 186b: new net/minecraft/client/animation/Keyframe
      // 186e: dup
      // 186f: ldc 1.04
      // 1871: dconst_1
      // 1872: dconst_1
      // 1873: ldc2_w 2.0480000972747803
      // 1876: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1879: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 187c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 187f: aastore
      // 1880: dup
      // 1881: bipush 26
      // 1883: new net/minecraft/client/animation/Keyframe
      // 1886: dup
      // 1887: ldc 1.08
      // 1889: dconst_1
      // 188a: dconst_1
      // 188b: ldc2_w 2.0429999828338623
      // 188e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1891: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1894: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1897: aastore
      // 1898: dup
      // 1899: bipush 27
      // 189b: new net/minecraft/client/animation/Keyframe
      // 189e: dup
      // 189f: ldc 1.12
      // 18a1: dconst_1
      // 18a2: dconst_1
      // 18a3: ldc2_w 2.0439999103546143
      // 18a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 18a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18af: aastore
      // 18b0: dup
      // 18b1: bipush 28
      // 18b3: new net/minecraft/client/animation/Keyframe
      // 18b6: dup
      // 18b7: ldc 1.17
      // 18b9: dconst_1
      // 18ba: dconst_1
      // 18bb: ldc2_w 2.056999921798706
      // 18be: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 18c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18c7: aastore
      // 18c8: dup
      // 18c9: bipush 29
      // 18cb: new net/minecraft/client/animation/Keyframe
      // 18ce: dup
      // 18cf: ldc 1.21
      // 18d1: dconst_1
      // 18d2: dconst_1
      // 18d3: ldc2_w 2.0769999027252197
      // 18d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 18d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18df: aastore
      // 18e0: dup
      // 18e1: bipush 30
      // 18e3: new net/minecraft/client/animation/Keyframe
      // 18e6: dup
      // 18e7: ldc 1.25
      // 18e9: dconst_1
      // 18ea: dconst_1
      // 18eb: ldc2_w 2.0980000495910645
      // 18ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 18f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18f7: aastore
      // 18f8: dup
      // 18f9: bipush 31
      // 18fb: new net/minecraft/client/animation/Keyframe
      // 18fe: dup
      // 18ff: ldc 1.29
      // 1901: dconst_1
      // 1902: dconst_1
      // 1903: ldc2_w 2.119999885559082
      // 1906: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1909: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 190c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 190f: aastore
      // 1910: dup
      // 1911: bipush 32
      // 1913: new net/minecraft/client/animation/Keyframe
      // 1916: dup
      // 1917: ldc 1.33
      // 1919: dconst_1
      // 191a: dconst_1
      // 191b: ldc2_w 2.1410000324249268
      // 191e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1921: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1924: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1927: aastore
      // 1928: dup
      // 1929: bipush 33
      // 192b: new net/minecraft/client/animation/Keyframe
      // 192e: dup
      // 192f: ldc 1.38
      // 1931: dconst_1
      // 1932: dconst_1
      // 1933: ldc2_w 2.1579999923706055
      // 1936: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1939: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 193c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 193f: aastore
      // 1940: dup
      // 1941: bipush 34
      // 1943: new net/minecraft/client/animation/Keyframe
      // 1946: dup
      // 1947: ldc 1.42
      // 1949: dconst_1
      // 194a: dconst_1
      // 194b: ldc2_w 2.1710000038146973
      // 194e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1951: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1954: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1957: aastore
      // 1958: dup
      // 1959: bipush 35
      // 195b: new net/minecraft/client/animation/Keyframe
      // 195e: dup
      // 195f: ldc 1.46
      // 1961: dconst_1
      // 1962: dconst_1
      // 1963: ldc2_w 2.178999900817871
      // 1966: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1969: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 196c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 196f: aastore
      // 1970: dup
      // 1971: bipush 36
      // 1973: new net/minecraft/client/animation/Keyframe
      // 1976: dup
      // 1977: ldc 1.5
      // 1979: dconst_1
      // 197a: dconst_1
      // 197b: ldc2_w 2.181999921798706
      // 197e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1981: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1984: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1987: aastore
      // 1988: dup
      // 1989: bipush 37
      // 198b: new net/minecraft/client/animation/Keyframe
      // 198e: dup
      // 198f: ldc 1.54
      // 1991: dconst_1
      // 1992: dconst_1
      // 1993: ldc2_w 2.180000066757202
      // 1996: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1999: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 199c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 199f: aastore
      // 19a0: dup
      // 19a1: bipush 38
      // 19a3: new net/minecraft/client/animation/Keyframe
      // 19a6: dup
      // 19a7: ldc 1.58
      // 19a9: dconst_1
      // 19aa: dconst_1
      // 19ab: ldc2_w 2.1730000972747803
      // 19ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 19b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19b7: aastore
      // 19b8: dup
      // 19b9: bipush 39
      // 19bb: new net/minecraft/client/animation/Keyframe
      // 19be: dup
      // 19bf: ldc 1.62
      // 19c1: dconst_1
      // 19c2: dconst_1
      // 19c3: ldc2_w 2.1640000343322754
      // 19c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 19c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19cf: aastore
      // 19d0: dup
      // 19d1: bipush 40
      // 19d3: new net/minecraft/client/animation/Keyframe
      // 19d6: dup
      // 19d7: ldc 1.67
      // 19d9: dconst_1
      // 19da: dconst_1
      // 19db: ldc2_w 2.1540000438690186
      // 19de: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 19e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19e7: aastore
      // 19e8: dup
      // 19e9: bipush 41
      // 19eb: new net/minecraft/client/animation/Keyframe
      // 19ee: dup
      // 19ef: ldc_w 1.71
      // 19f2: dconst_1
      // 19f3: dconst_1
      // 19f4: ldc2_w 2.127000093460083
      // 19f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 19fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a00: aastore
      // 1a01: dup
      // 1a02: bipush 42
      // 1a04: new net/minecraft/client/animation/Keyframe
      // 1a07: dup
      // 1a08: ldc_w 1.75
      // 1a0b: dconst_1
      // 1a0c: dconst_1
      // 1a0d: ldc2_w 2.065999984741211
      // 1a10: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1a13: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a16: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a19: aastore
      // 1a1a: dup
      // 1a1b: bipush 43
      // 1a1d: new net/minecraft/client/animation/Keyframe
      // 1a20: dup
      // 1a21: ldc_w 1.79
      // 1a24: dconst_1
      // 1a25: dconst_1
      // 1a26: ldc2_w 1.9620000123977661
      // 1a29: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1a2c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a2f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a32: aastore
      // 1a33: dup
      // 1a34: bipush 44
      // 1a36: new net/minecraft/client/animation/Keyframe
      // 1a39: dup
      // 1a3a: ldc_w 1.83
      // 1a3d: dconst_1
      // 1a3e: dconst_1
      // 1a3f: ldc2_w 1.809000015258789
      // 1a42: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1a45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a4b: aastore
      // 1a4c: dup
      // 1a4d: bipush 45
      // 1a4f: new net/minecraft/client/animation/Keyframe
      // 1a52: dup
      // 1a53: ldc_w 1.88
      // 1a56: dconst_1
      // 1a57: dconst_1
      // 1a58: ldc2_w 1.6009999513626099
      // 1a5b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1a5e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a61: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a64: aastore
      // 1a65: dup
      // 1a66: bipush 46
      // 1a68: new net/minecraft/client/animation/Keyframe
      // 1a6b: dup
      // 1a6c: ldc_w 1.92
      // 1a6f: dconst_1
      // 1a70: dconst_1
      // 1a71: ldc2_w 1.3569999933242798
      // 1a74: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1a77: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a7a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a7d: aastore
      // 1a7e: dup
      // 1a7f: bipush 47
      // 1a81: new net/minecraft/client/animation/Keyframe
      // 1a84: dup
      // 1a85: ldc_w 1.96
      // 1a88: dconst_1
      // 1a89: dconst_1
      // 1a8a: ldc2_w 1.0950000286102295
      // 1a8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1a90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a96: aastore
      // 1a97: dup
      // 1a98: bipush 48
      // 1a9a: new net/minecraft/client/animation/Keyframe
      // 1a9d: dup
      // 1a9e: fconst_2
      // 1a9f: dconst_1
      // 1aa0: dconst_1
      // 1aa1: ldc2_w 0.8240000009536743
      // 1aa4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1aa7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1aaa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1aad: aastore
      // 1aae: dup
      // 1aaf: bipush 49
      // 1ab1: new net/minecraft/client/animation/Keyframe
      // 1ab4: dup
      // 1ab5: ldc 2.04
      // 1ab7: dconst_1
      // 1ab8: dconst_1
      // 1ab9: ldc2_w 0.5490000247955322
      // 1abc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1abf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ac2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ac5: aastore
      // 1ac6: dup
      // 1ac7: bipush 50
      // 1ac9: new net/minecraft/client/animation/Keyframe
      // 1acc: dup
      // 1acd: ldc 2.08
      // 1acf: dconst_1
      // 1ad0: dconst_1
      // 1ad1: ldc2_w 0.32600000500679016
      // 1ad4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1ad7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ada: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1add: aastore
      // 1ade: dup
      // 1adf: bipush 51
      // 1ae1: new net/minecraft/client/animation/Keyframe
      // 1ae4: dup
      // 1ae5: ldc 2.12
      // 1ae7: dconst_1
      // 1ae8: dconst_1
      // 1ae9: ldc2_w 0.8709999918937683
      // 1aec: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1aef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1af2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1af5: aastore
      // 1af6: dup
      // 1af7: bipush 52
      // 1af9: new net/minecraft/client/animation/Keyframe
      // 1afc: dup
      // 1afd: ldc 2.17
      // 1aff: dconst_1
      // 1b00: dconst_1
      // 1b01: ldc2_w 1.61899995803833
      // 1b04: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1b07: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b0a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b0d: aastore
      // 1b0e: dup
      // 1b0f: bipush 53
      // 1b11: new net/minecraft/client/animation/Keyframe
      // 1b14: dup
      // 1b15: ldc 2.21
      // 1b17: dconst_1
      // 1b18: dconst_1
      // 1b19: ldc2_w 1.437999963760376
      // 1b1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1b1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b25: aastore
      // 1b26: dup
      // 1b27: bipush 54
      // 1b29: new net/minecraft/client/animation/Keyframe
      // 1b2c: dup
      // 1b2d: ldc 2.25
      // 1b2f: dconst_1
      // 1b30: dconst_1
      // 1b31: ldc2_w 1.4739999771118164
      // 1b34: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1b37: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b3a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b3d: aastore
      // 1b3e: dup
      // 1b3f: bipush 55
      // 1b41: new net/minecraft/client/animation/Keyframe
      // 1b44: dup
      // 1b45: ldc 2.29
      // 1b47: dconst_1
      // 1b48: dconst_1
      // 1b49: ldc2_w 1.4589999914169312
      // 1b4c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1b4f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b52: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b55: aastore
      // 1b56: dup
      // 1b57: bipush 56
      // 1b59: new net/minecraft/client/animation/Keyframe
      // 1b5c: dup
      // 1b5d: ldc 2.33
      // 1b5f: dconst_1
      // 1b60: dconst_1
      // 1b61: ldc2_w 1.4789999723434448
      // 1b64: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1b67: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b6a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b6d: aastore
      // 1b6e: dup
      // 1b6f: bipush 57
      // 1b71: new net/minecraft/client/animation/Keyframe
      // 1b74: dup
      // 1b75: ldc 2.38
      // 1b77: dconst_1
      // 1b78: dconst_1
      // 1b79: ldc2_w 1.4800000190734863
      // 1b7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1b7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b85: aastore
      // 1b86: dup
      // 1b87: bipush 58
      // 1b89: new net/minecraft/client/animation/Keyframe
      // 1b8c: dup
      // 1b8d: ldc 2.42
      // 1b8f: dconst_1
      // 1b90: dconst_1
      // 1b91: ldc2_w 1.496999979019165
      // 1b94: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1b97: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b9a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b9d: aastore
      // 1b9e: dup
      // 1b9f: bipush 59
      // 1ba1: new net/minecraft/client/animation/Keyframe
      // 1ba4: dup
      // 1ba5: ldc 2.46
      // 1ba7: dconst_1
      // 1ba8: dconst_1
      // 1ba9: ldc2_w 1.5140000581741333
      // 1bac: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1baf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bb2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bb5: aastore
      // 1bb6: dup
      // 1bb7: bipush 60
      // 1bb9: new net/minecraft/client/animation/Keyframe
      // 1bbc: dup
      // 1bbd: ldc 2.5
      // 1bbf: dconst_1
      // 1bc0: dconst_1
      // 1bc1: ldc2_w 1.5369999408721924
      // 1bc4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1bc7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bcd: aastore
      // 1bce: dup
      // 1bcf: bipush 61
      // 1bd1: new net/minecraft/client/animation/Keyframe
      // 1bd4: dup
      // 1bd5: ldc 2.54
      // 1bd7: dconst_1
      // 1bd8: dconst_1
      // 1bd9: ldc2_w 1.565000057220459
      // 1bdc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1bdf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1be2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1be5: aastore
      // 1be6: dup
      // 1be7: bipush 62
      // 1be9: new net/minecraft/client/animation/Keyframe
      // 1bec: dup
      // 1bed: ldc 2.62
      // 1bef: dconst_1
      // 1bf0: dconst_1
      // 1bf1: ldc2_w 1.565000057220459
      // 1bf4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1bf7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bfa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bfd: aastore
      // 1bfe: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1c01: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1c04: ldc_w "CTR_leg_right.002"
      // 1c07: new net/minecraft/client/animation/AnimationChannel
      // 1c0a: dup
      // 1c0b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1c0e: bipush 32
      // 1c10: anewarray 24
      // 1c13: dup
      // 1c14: bipush 0
      // 1c15: new net/minecraft/client/animation/Keyframe
      // 1c18: dup
      // 1c19: fconst_0
      // 1c1a: ldc_w -2.707
      // 1c1d: fconst_0
      // 1c1e: ldc_w -2.155
      // 1c21: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c24: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c27: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c2a: aastore
      // 1c2b: dup
      // 1c2c: bipush 1
      // 1c2d: new net/minecraft/client/animation/Keyframe
      // 1c30: dup
      // 1c31: ldc 0.08
      // 1c33: ldc_w -2.707
      // 1c36: fconst_0
      // 1c37: ldc_w -2.155
      // 1c3a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c3d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c40: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c43: aastore
      // 1c44: dup
      // 1c45: bipush 2
      // 1c46: new net/minecraft/client/animation/Keyframe
      // 1c49: dup
      // 1c4a: ldc 0.12
      // 1c4c: ldc_w -2.707
      // 1c4f: fconst_0
      // 1c50: ldc_w 4.728
      // 1c53: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c5c: aastore
      // 1c5d: dup
      // 1c5e: bipush 3
      // 1c5f: new net/minecraft/client/animation/Keyframe
      // 1c62: dup
      // 1c63: ldc 0.17
      // 1c65: ldc_w -2.707
      // 1c68: fconst_0
      // 1c69: ldc_w 17.511
      // 1c6c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c6f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c72: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c75: aastore
      // 1c76: dup
      // 1c77: bipush 4
      // 1c78: new net/minecraft/client/animation/Keyframe
      // 1c7b: dup
      // 1c7c: ldc 0.21
      // 1c7e: ldc_w -2.707
      // 1c81: fconst_0
      // 1c82: ldc_w 24.393
      // 1c85: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1c88: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c8b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c8e: aastore
      // 1c8f: dup
      // 1c90: bipush 5
      // 1c91: new net/minecraft/client/animation/Keyframe
      // 1c94: dup
      // 1c95: ldc 0.29
      // 1c97: ldc_w -2.707
      // 1c9a: fconst_0
      // 1c9b: ldc_w 24.393
      // 1c9e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ca1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ca4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ca7: aastore
      // 1ca8: dup
      // 1ca9: bipush 6
      // 1cab: new net/minecraft/client/animation/Keyframe
      // 1cae: dup
      // 1caf: ldc 0.33
      // 1cb1: ldc_w -2.707
      // 1cb4: fconst_0
      // 1cb5: ldc 28.983
      // 1cb7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1cba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cbd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cc0: aastore
      // 1cc1: dup
      // 1cc2: bipush 7
      // 1cc4: new net/minecraft/client/animation/Keyframe
      // 1cc7: dup
      // 1cc8: ldc 0.38
      // 1cca: ldc_w -2.707
      // 1ccd: fconst_0
      // 1cce: ldc_w 37.505
      // 1cd1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1cd4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cd7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cda: aastore
      // 1cdb: dup
      // 1cdc: bipush 8
      // 1cde: new net/minecraft/client/animation/Keyframe
      // 1ce1: dup
      // 1ce2: ldc 0.42
      // 1ce4: ldc_w -2.707
      // 1ce7: fconst_0
      // 1ce8: ldc_w 42.095
      // 1ceb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1cee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cf1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cf4: aastore
      // 1cf5: dup
      // 1cf6: bipush 9
      // 1cf8: new net/minecraft/client/animation/Keyframe
      // 1cfb: dup
      // 1cfc: ldc 0.79
      // 1cfe: ldc_w -2.707
      // 1d01: fconst_0
      // 1d02: ldc_w 42.095
      // 1d05: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d08: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d0b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d0e: aastore
      // 1d0f: dup
      // 1d10: bipush 10
      // 1d12: new net/minecraft/client/animation/Keyframe
      // 1d15: dup
      // 1d16: ldc 0.83
      // 1d18: ldc_w -2.707
      // 1d1b: fconst_0
      // 1d1c: ldc_w 42.338
      // 1d1f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d22: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d25: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d28: aastore
      // 1d29: dup
      // 1d2a: bipush 11
      // 1d2c: new net/minecraft/client/animation/Keyframe
      // 1d2f: dup
      // 1d30: ldc 0.88
      // 1d32: ldc_w -2.707
      // 1d35: fconst_0
      // 1d36: ldc_w 43.016
      // 1d39: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d3c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d3f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d42: aastore
      // 1d43: dup
      // 1d44: bipush 12
      // 1d46: new net/minecraft/client/animation/Keyframe
      // 1d49: dup
      // 1d4a: ldc 0.92
      // 1d4c: ldc_w -2.707
      // 1d4f: fconst_0
      // 1d50: ldc_w 44.048
      // 1d53: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d56: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d59: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d5c: aastore
      // 1d5d: dup
      // 1d5e: bipush 13
      // 1d60: new net/minecraft/client/animation/Keyframe
      // 1d63: dup
      // 1d64: ldc 0.96
      // 1d66: ldc_w -2.707
      // 1d69: fconst_0
      // 1d6a: ldc_w 45.357
      // 1d6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d76: aastore
      // 1d77: dup
      // 1d78: bipush 14
      // 1d7a: new net/minecraft/client/animation/Keyframe
      // 1d7d: dup
      // 1d7e: fconst_1
      // 1d7f: ldc_w -2.707
      // 1d82: fconst_0
      // 1d83: ldc_w 46.864
      // 1d86: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1d89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d8f: aastore
      // 1d90: dup
      // 1d91: bipush 15
      // 1d93: new net/minecraft/client/animation/Keyframe
      // 1d96: dup
      // 1d97: ldc 1.04
      // 1d99: ldc_w -2.707
      // 1d9c: fconst_0
      // 1d9d: ldc_w 48.488
      // 1da0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1da3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1da6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1da9: aastore
      // 1daa: dup
      // 1dab: bipush 16
      // 1dad: new net/minecraft/client/animation/Keyframe
      // 1db0: dup
      // 1db1: ldc 1.08
      // 1db3: ldc_w -2.707
      // 1db6: fconst_0
      // 1db7: ldc_w 50.152
      // 1dba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1dbd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dc0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1dc3: aastore
      // 1dc4: dup
      // 1dc5: bipush 17
      // 1dc7: new net/minecraft/client/animation/Keyframe
      // 1dca: dup
      // 1dcb: ldc 1.12
      // 1dcd: ldc_w -2.707
      // 1dd0: fconst_0
      // 1dd1: ldc_w 51.777
      // 1dd4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1dd7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dda: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ddd: aastore
      // 1dde: dup
      // 1ddf: bipush 18
      // 1de1: new net/minecraft/client/animation/Keyframe
      // 1de4: dup
      // 1de5: ldc 1.17
      // 1de7: ldc_w -2.707
      // 1dea: fconst_0
      // 1deb: ldc_w 53.283
      // 1dee: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1df1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1df4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1df7: aastore
      // 1df8: dup
      // 1df9: bipush 19
      // 1dfb: new net/minecraft/client/animation/Keyframe
      // 1dfe: dup
      // 1dff: ldc 1.21
      // 1e01: ldc_w -2.707
      // 1e04: fconst_0
      // 1e05: ldc_w 54.592
      // 1e08: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e0b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e0e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e11: aastore
      // 1e12: dup
      // 1e13: bipush 20
      // 1e15: new net/minecraft/client/animation/Keyframe
      // 1e18: dup
      // 1e19: ldc 1.25
      // 1e1b: ldc_w -2.707
      // 1e1e: fconst_0
      // 1e1f: ldc_w 55.625
      // 1e22: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e25: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e28: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e2b: aastore
      // 1e2c: dup
      // 1e2d: bipush 21
      // 1e2f: new net/minecraft/client/animation/Keyframe
      // 1e32: dup
      // 1e33: ldc 1.29
      // 1e35: ldc_w -2.707
      // 1e38: fconst_0
      // 1e39: ldc_w 56.302
      // 1e3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e45: aastore
      // 1e46: dup
      // 1e47: bipush 22
      // 1e49: new net/minecraft/client/animation/Keyframe
      // 1e4c: dup
      // 1e4d: ldc 1.33
      // 1e4f: ldc_w -2.707
      // 1e52: fconst_0
      // 1e53: ldc_w 56.546
      // 1e56: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e59: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e5c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e5f: aastore
      // 1e60: dup
      // 1e61: bipush 23
      // 1e63: new net/minecraft/client/animation/Keyframe
      // 1e66: dup
      // 1e67: ldc 2.29
      // 1e69: ldc_w -2.707
      // 1e6c: fconst_0
      // 1e6d: ldc_w 56.546
      // 1e70: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e73: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e76: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e79: aastore
      // 1e7a: dup
      // 1e7b: bipush 24
      // 1e7d: new net/minecraft/client/animation/Keyframe
      // 1e80: dup
      // 1e81: ldc 2.33
      // 1e83: ldc_w -2.707
      // 1e86: fconst_0
      // 1e87: ldc_w 56.043
      // 1e8a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1e8d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e90: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e93: aastore
      // 1e94: dup
      // 1e95: bipush 25
      // 1e97: new net/minecraft/client/animation/Keyframe
      // 1e9a: dup
      // 1e9b: ldc 2.38
      // 1e9d: ldc_w -2.707
      // 1ea0: fconst_0
      // 1ea1: ldc_w 54.716
      // 1ea4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ea7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1eaa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ead: aastore
      // 1eae: dup
      // 1eaf: bipush 26
      // 1eb1: new net/minecraft/client/animation/Keyframe
      // 1eb4: dup
      // 1eb5: ldc 2.42
      // 1eb7: ldc_w -2.707
      // 1eba: fconst_0
      // 1ebb: ldc_w 52.84
      // 1ebe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ec1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ec4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ec7: aastore
      // 1ec8: dup
      // 1ec9: bipush 27
      // 1ecb: new net/minecraft/client/animation/Keyframe
      // 1ece: dup
      // 1ecf: ldc 2.46
      // 1ed1: ldc_w -2.707
      // 1ed4: fconst_0
      // 1ed5: ldc_w 50.69
      // 1ed8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1edb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ede: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ee1: aastore
      // 1ee2: dup
      // 1ee3: bipush 28
      // 1ee5: new net/minecraft/client/animation/Keyframe
      // 1ee8: dup
      // 1ee9: ldc 2.5
      // 1eeb: ldc_w -2.707
      // 1eee: fconst_0
      // 1eef: ldc_w 48.54
      // 1ef2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1ef5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ef8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1efb: aastore
      // 1efc: dup
      // 1efd: bipush 29
      // 1eff: new net/minecraft/client/animation/Keyframe
      // 1f02: dup
      // 1f03: ldc 2.54
      // 1f05: ldc_w -2.707
      // 1f08: fconst_0
      // 1f09: ldc_w 46.664
      // 1f0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f15: aastore
      // 1f16: dup
      // 1f17: bipush 30
      // 1f19: new net/minecraft/client/animation/Keyframe
      // 1f1c: dup
      // 1f1d: ldc 2.58
      // 1f1f: ldc_w -2.707
      // 1f22: fconst_0
      // 1f23: ldc_w 45.338
      // 1f26: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f29: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f2c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f2f: aastore
      // 1f30: dup
      // 1f31: bipush 31
      // 1f33: new net/minecraft/client/animation/Keyframe
      // 1f36: dup
      // 1f37: ldc 2.62
      // 1f39: ldc_w -2.707
      // 1f3c: fconst_0
      // 1f3d: ldc_w 44.834
      // 1f40: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1f43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f49: aastore
      // 1f4a: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1f4d: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1f50: ldc_w "CTR_hips"
      // 1f53: new net/minecraft/client/animation/AnimationChannel
      // 1f56: dup
      // 1f57: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1f5a: bipush 61
      // 1f5c: anewarray 24
      // 1f5f: dup
      // 1f60: bipush 0
      // 1f61: new net/minecraft/client/animation/Keyframe
      // 1f64: dup
      // 1f65: fconst_0
      // 1f66: ldc_w 31.916
      // 1f69: ldc_w 8.465
      // 1f6c: ldc_w -0.253
      // 1f6f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f72: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f75: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f78: aastore
      // 1f79: dup
      // 1f7a: bipush 1
      // 1f7b: new net/minecraft/client/animation/Keyframe
      // 1f7e: dup
      // 1f7f: ldc 0.04
      // 1f81: ldc_w 21.775
      // 1f84: ldc_w 8.538
      // 1f87: ldc_w -1.551
      // 1f8a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f8d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f90: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f93: aastore
      // 1f94: dup
      // 1f95: bipush 2
      // 1f96: new net/minecraft/client/animation/Keyframe
      // 1f99: dup
      // 1f9a: ldc 0.08
      // 1f9c: ldc_w 2.906
      // 1f9f: ldc_w 9.049
      // 1fa2: ldc_w -3.968
      // 1fa5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fa8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fae: aastore
      // 1faf: dup
      // 1fb0: bipush 3
      // 1fb1: new net/minecraft/client/animation/Keyframe
      // 1fb4: dup
      // 1fb5: ldc 0.12
      // 1fb7: ldc_w -7.38
      // 1fba: ldc_w 10.463
      // 1fbd: ldc_w -5.29
      // 1fc0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fc3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fc6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fc9: aastore
      // 1fca: dup
      // 1fcb: bipush 4
      // 1fcc: new net/minecraft/client/animation/Keyframe
      // 1fcf: dup
      // 1fd0: ldc 0.17
      // 1fd2: ldc_w -6.605
      // 1fd5: ldc_w 13.149
      // 1fd8: ldc_w -4.19
      // 1fdb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1fde: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1fe1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fe4: aastore
      // 1fe5: dup
      // 1fe6: bipush 5
      // 1fe7: new net/minecraft/client/animation/Keyframe
      // 1fea: dup
      // 1feb: ldc 0.21
      // 1fed: ldc_w -4.232
      // 1ff0: ldc_w 16.413
      // 1ff3: ldc_w -1.68
      // 1ff6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ff9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ffc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1fff: aastore
      // 2000: dup
      // 2001: bipush 6
      // 2003: new net/minecraft/client/animation/Keyframe
      // 2006: dup
      // 2007: ldc 0.25
      // 2009: ldc_w -1.051
      // 200c: ldc_w 19.143
      // 200f: ldc_w 0.907
      // 2012: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2015: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2018: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 201b: aastore
      // 201c: dup
      // 201d: bipush 7
      // 201f: new net/minecraft/client/animation/Keyframe
      // 2022: dup
      // 2023: ldc 0.29
      // 2025: ldc_w 1.696
      // 2028: ldc_w 20.278
      // 202b: ldc_w 2.109
      // 202e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2031: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2034: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2037: aastore
      // 2038: dup
      // 2039: bipush 8
      // 203b: new net/minecraft/client/animation/Keyframe
      // 203e: dup
      // 203f: ldc 0.33
      // 2041: ldc_w 3.476
      // 2044: ldc_w 20.143
      // 2047: ldc_w 1.981
      // 204a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 204d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2050: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2053: aastore
      // 2054: dup
      // 2055: bipush 9
      // 2057: new net/minecraft/client/animation/Keyframe
      // 205a: dup
      // 205b: ldc 0.38
      // 205d: ldc_w 4.752
      // 2060: ldc_w 19.754
      // 2063: ldc_w 1.628
      // 2066: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2069: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 206c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 206f: aastore
      // 2070: dup
      // 2071: bipush 10
      // 2073: new net/minecraft/client/animation/Keyframe
      // 2076: dup
      // 2077: ldc 0.42
      // 2079: ldc_w 5.604
      // 207c: ldc_w 19.146
      // 207f: ldc_w 1.099
      // 2082: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2085: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2088: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 208b: aastore
      // 208c: dup
      // 208d: bipush 11
      // 208f: new net/minecraft/client/animation/Keyframe
      // 2092: dup
      // 2093: ldc 0.46
      // 2095: ldc_w 6.102
      // 2098: ldc_w 18.359
      // 209b: ldc_w 0.445
      // 209e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20a7: aastore
      // 20a8: dup
      // 20a9: bipush 12
      // 20ab: new net/minecraft/client/animation/Keyframe
      // 20ae: dup
      // 20af: ldc 0.5
      // 20b1: ldc_w 6.308
      // 20b4: ldc_w 17.441
      // 20b7: ldc_w -0.286
      // 20ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20c3: aastore
      // 20c4: dup
      // 20c5: bipush 13
      // 20c7: new net/minecraft/client/animation/Keyframe
      // 20ca: dup
      // 20cb: ldc 0.54
      // 20cd: ldc_w 6.271
      // 20d0: ldc_w 16.443
      // 20d3: ldc_w -1.046
      // 20d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20df: aastore
      // 20e0: dup
      // 20e1: bipush 14
      // 20e3: new net/minecraft/client/animation/Keyframe
      // 20e6: dup
      // 20e7: ldc 0.58
      // 20e9: ldc_w 6.031
      // 20ec: ldc_w 15.416
      // 20ef: ldc_w -1.791
      // 20f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 20f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 20f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 20fb: aastore
      // 20fc: dup
      // 20fd: bipush 15
      // 20ff: new net/minecraft/client/animation/Keyframe
      // 2102: dup
      // 2103: ldc 0.62
      // 2105: ldc_w 5.616
      // 2108: ldc_w 14.413
      // 210b: ldc_w -2.478
      // 210e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2111: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2114: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2117: aastore
      // 2118: dup
      // 2119: bipush 16
      // 211b: new net/minecraft/client/animation/Keyframe
      // 211e: dup
      // 211f: ldc 0.67
      // 2121: ldc_w 5.05
      // 2124: ldc_w 13.482
      // 2127: ldc_w -3.064
      // 212a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 212d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2130: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2133: aastore
      // 2134: dup
      // 2135: bipush 17
      // 2137: new net/minecraft/client/animation/Keyframe
      // 213a: dup
      // 213b: ldc 0.71
      // 213d: ldc_w 4.346
      // 2140: ldc_w 12.663
      // 2143: ldc_w -3.507
      // 2146: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2149: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 214c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 214f: aastore
      // 2150: dup
      // 2151: bipush 18
      // 2153: new net/minecraft/client/animation/Keyframe
      // 2156: dup
      // 2157: ldc 0.75
      // 2159: ldc_w 3.516
      // 215c: ldc_w 11.993
      // 215f: ldc_w -3.766
      // 2162: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2165: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2168: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 216b: aastore
      // 216c: dup
      // 216d: bipush 19
      // 216f: new net/minecraft/client/animation/Keyframe
      // 2172: dup
      // 2173: ldc 0.79
      // 2175: ldc_w 2.57
      // 2178: ldc_w 11.5
      // 217b: ldc_w -3.799
      // 217e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2181: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2184: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2187: aastore
      // 2188: dup
      // 2189: bipush 20
      // 218b: new net/minecraft/client/animation/Keyframe
      // 218e: dup
      // 218f: ldc 0.83
      // 2191: ldc_w 1.521
      // 2194: ldc_w 11.204
      // 2197: ldc_w -3.562
      // 219a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 219d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21a3: aastore
      // 21a4: dup
      // 21a5: bipush 21
      // 21a7: new net/minecraft/client/animation/Keyframe
      // 21aa: dup
      // 21ab: ldc 0.88
      // 21ad: ldc_w 0.383
      // 21b0: ldc_w 11.121
      // 21b3: ldc_w -3.011
      // 21b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21bf: aastore
      // 21c0: dup
      // 21c1: bipush 22
      // 21c3: new net/minecraft/client/animation/Keyframe
      // 21c6: dup
      // 21c7: ldc 0.92
      // 21c9: ldc_w -0.345
      // 21cc: ldc_w 11.059
      // 21cf: ldc_w -2.361
      // 21d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21db: aastore
      // 21dc: dup
      // 21dd: bipush 23
      // 21df: new net/minecraft/client/animation/Keyframe
      // 21e2: dup
      // 21e3: ldc 0.96
      // 21e5: ldc_w -0.359
      // 21e8: ldc_w 10.843
      // 21eb: ldc_w -1.859
      // 21ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 21f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 21f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 21f7: aastore
      // 21f8: dup
      // 21f9: bipush 24
      // 21fb: new net/minecraft/client/animation/Keyframe
      // 21fe: dup
      // 21ff: fconst_1
      // 2200: ldc_w 0.154
      // 2203: ldc_w 10.498
      // 2206: ldc_w -1.506
      // 2209: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 220c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 220f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2212: aastore
      // 2213: dup
      // 2214: bipush 25
      // 2216: new net/minecraft/client/animation/Keyframe
      // 2219: dup
      // 221a: ldc 1.04
      // 221c: ldc_w 1.071
      // 221f: ldc_w 10.048
      // 2222: ldc_w -1.293
      // 2225: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2228: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 222b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 222e: aastore
      // 222f: dup
      // 2230: bipush 26
      // 2232: new net/minecraft/client/animation/Keyframe
      // 2235: dup
      // 2236: ldc 1.08
      // 2238: ldc_w 2.308
      // 223b: ldc_w 9.518
      // 223e: ldc_w -1.196
      // 2241: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2244: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2247: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 224a: aastore
      // 224b: dup
      // 224c: bipush 27
      // 224e: new net/minecraft/client/animation/Keyframe
      // 2251: dup
      // 2252: ldc 1.12
      // 2254: ldc_w 3.805
      // 2257: ldc_w 8.931
      // 225a: ldc_w -1.175
      // 225d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2260: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2263: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2266: aastore
      // 2267: dup
      // 2268: bipush 28
      // 226a: new net/minecraft/client/animation/Keyframe
      // 226d: dup
      // 226e: ldc 1.17
      // 2270: ldc_w 5.512
      // 2273: ldc_w 8.313
      // 2276: ldc_w -1.171
      // 2279: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 227c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 227f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2282: aastore
      // 2283: dup
      // 2284: bipush 29
      // 2286: new net/minecraft/client/animation/Keyframe
      // 2289: dup
      // 228a: ldc 1.21
      // 228c: ldc_w 7.38
      // 228f: ldc_w 7.697
      // 2292: ldc_w -1.115
      // 2295: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2298: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 229b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 229e: aastore
      // 229f: dup
      // 22a0: bipush 30
      // 22a2: new net/minecraft/client/animation/Keyframe
      // 22a5: dup
      // 22a6: ldc 1.25
      // 22a8: ldc_w 9.347
      // 22ab: ldc_w 7.126
      // 22ae: ldc_w -0.938
      // 22b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22ba: aastore
      // 22bb: dup
      // 22bc: bipush 31
      // 22be: new net/minecraft/client/animation/Keyframe
      // 22c1: dup
      // 22c2: ldc 1.29
      // 22c4: ldc_w 11.315
      // 22c7: ldc_w 6.67
      // 22ca: ldc_w -0.585
      // 22cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22d6: aastore
      // 22d7: dup
      // 22d8: bipush 32
      // 22da: new net/minecraft/client/animation/Keyframe
      // 22dd: dup
      // 22de: ldc 1.33
      // 22e0: ldc_w 13.056
      // 22e3: ldc_w 6.453
      // 22e6: ldc_w -0.024
      // 22e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 22ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 22ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 22f2: aastore
      // 22f3: dup
      // 22f4: bipush 33
      // 22f6: new net/minecraft/client/animation/Keyframe
      // 22f9: dup
      // 22fa: ldc 1.38
      // 22fc: ldc_w 14.279
      // 22ff: ldc_w 6.336
      // 2302: ldc_w 0.517
      // 2305: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2308: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 230b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 230e: aastore
      // 230f: dup
      // 2310: bipush 34
      // 2312: new net/minecraft/client/animation/Keyframe
      // 2315: dup
      // 2316: ldc 1.42
      // 2318: ldc_w 15.038
      // 231b: ldc_w 6.083
      // 231e: ldc_w 0.876
      // 2321: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2324: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2327: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 232a: aastore
      // 232b: dup
      // 232c: bipush 35
      // 232e: new net/minecraft/client/animation/Keyframe
      // 2331: dup
      // 2332: ldc 1.46
      // 2334: ldc_w 15.322
      // 2337: ldc_w 5.741
      // 233a: ldc_w 1.119
      // 233d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2340: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2343: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2346: aastore
      // 2347: dup
      // 2348: bipush 36
      // 234a: new net/minecraft/client/animation/Keyframe
      // 234d: dup
      // 234e: ldc 1.5
      // 2350: ldc_w 15.117
      // 2353: ldc_w 5.334
      // 2356: ldc_w 1.278
      // 2359: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 235c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 235f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2362: aastore
      // 2363: dup
      // 2364: bipush 37
      // 2366: new net/minecraft/client/animation/Keyframe
      // 2369: dup
      // 236a: ldc 1.54
      // 236c: ldc_w 14.403
      // 236f: ldc_w 4.874
      // 2372: ldc_w 1.372
      // 2375: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2378: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 237b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 237e: aastore
      // 237f: dup
      // 2380: bipush 38
      // 2382: new net/minecraft/client/animation/Keyframe
      // 2385: dup
      // 2386: ldc 1.58
      // 2388: ldc_w 13.162
      // 238b: ldc_w 4.372
      // 238e: ldc_w 1.414
      // 2391: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2394: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2397: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 239a: aastore
      // 239b: dup
      // 239c: bipush 39
      // 239e: new net/minecraft/client/animation/Keyframe
      // 23a1: dup
      // 23a2: ldc 1.62
      // 23a4: ldc_w 11.367
      // 23a7: ldc_w 3.833
      // 23aa: ldc_w 1.412
      // 23ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23b6: aastore
      // 23b7: dup
      // 23b8: bipush 40
      // 23ba: new net/minecraft/client/animation/Keyframe
      // 23bd: dup
      // 23be: ldc 1.67
      // 23c0: ldc_w 8.992
      // 23c3: ldc_w 3.263
      // 23c6: ldc_w 1.371
      // 23c9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23cc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23cf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23d2: aastore
      // 23d3: dup
      // 23d4: bipush 41
      // 23d6: new net/minecraft/client/animation/Keyframe
      // 23d9: dup
      // 23da: ldc_w 1.71
      // 23dd: ldc_w 6.005
      // 23e0: ldc_w 2.663
      // 23e3: ldc_w 1.296
      // 23e6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 23e9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 23ec: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 23ef: aastore
      // 23f0: dup
      // 23f1: bipush 42
      // 23f3: new net/minecraft/client/animation/Keyframe
      // 23f6: dup
      // 23f7: ldc_w 1.75
      // 23fa: ldc_w 2.37
      // 23fd: ldc_w 2.037
      // 2400: ldc_w 1.189
      // 2403: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2406: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2409: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 240c: aastore
      // 240d: dup
      // 240e: bipush 43
      // 2410: new net/minecraft/client/animation/Keyframe
      // 2413: dup
      // 2414: ldc_w 1.79
      // 2417: ldc_w -1.957
      // 241a: ldc_w 1.385
      // 241d: ldc_w 1.053
      // 2420: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2423: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2426: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2429: aastore
      // 242a: dup
      // 242b: bipush 44
      // 242d: new net/minecraft/client/animation/Keyframe
      // 2430: dup
      // 2431: ldc_w 1.83
      // 2434: ldc_w -7.026
      // 2437: ldc_w 0.708
      // 243a: ldc_w 0.887
      // 243d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2440: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2443: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2446: aastore
      // 2447: dup
      // 2448: bipush 45
      // 244a: new net/minecraft/client/animation/Keyframe
      // 244d: dup
      // 244e: ldc_w 1.88
      // 2451: ldc_w -12.897
      // 2454: ldc_w 0.005
      // 2457: ldc_w 0.692
      // 245a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 245d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2460: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2463: aastore
      // 2464: dup
      // 2465: bipush 46
      // 2467: new net/minecraft/client/animation/Keyframe
      // 246a: dup
      // 246b: ldc_w 1.92
      // 246e: ldc_w -19.645
      // 2471: ldc_w -0.725
      // 2474: ldc_w 0.467
      // 2477: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 247a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 247d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2480: aastore
      // 2481: dup
      // 2482: bipush 47
      // 2484: new net/minecraft/client/animation/Keyframe
      // 2487: dup
      // 2488: ldc_w 1.96
      // 248b: ldc_w -27.364
      // 248e: ldc_w -1.484
      // 2491: ldc_w 0.209
      // 2494: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2497: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 249a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 249d: aastore
      // 249e: dup
      // 249f: bipush 48
      // 24a1: new net/minecraft/client/animation/Keyframe
      // 24a4: dup
      // 24a5: fconst_2
      // 24a6: ldc_w -36.178
      // 24a9: ldc_w -2.28
      // 24ac: ldc_w -0.088
      // 24af: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24b8: aastore
      // 24b9: dup
      // 24ba: bipush 49
      // 24bc: new net/minecraft/client/animation/Keyframe
      // 24bf: dup
      // 24c0: ldc 2.04
      // 24c2: ldc_w -46.261
      // 24c5: ldc_w -3.123
      // 24c8: ldc_w -0.433
      // 24cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24d4: aastore
      // 24d5: dup
      // 24d6: bipush 50
      // 24d8: new net/minecraft/client/animation/Keyframe
      // 24db: dup
      // 24dc: ldc 2.08
      // 24de: ldc_w -57.877
      // 24e1: ldc_w -4.046
      // 24e4: ldc_w -0.849
      // 24e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 24ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 24ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 24f0: aastore
      // 24f1: dup
      // 24f2: bipush 51
      // 24f4: new net/minecraft/client/animation/Keyframe
      // 24f7: dup
      // 24f8: ldc 2.12
      // 24fa: ldc_w -71.506
      // 24fd: ldc_w -5.273
      // 2500: ldc_w -1.406
      // 2503: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2506: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2509: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 250c: aastore
      // 250d: dup
      // 250e: bipush 52
      // 2510: new net/minecraft/client/animation/Keyframe
      // 2513: dup
      // 2514: ldc 2.17
      // 2516: ldc_w -89.031
      // 2519: ldc_w -5.273
      // 251c: ldc_w -1.406
      // 251f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2522: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2525: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2528: aastore
      // 2529: dup
      // 252a: bipush 53
      // 252c: new net/minecraft/client/animation/Keyframe
      // 252f: dup
      // 2530: ldc 2.21
      // 2532: ldc_w -82.862
      // 2535: ldc_w -5.273
      // 2538: ldc_w -1.406
      // 253b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 253e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2541: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2544: aastore
      // 2545: dup
      // 2546: bipush 54
      // 2548: new net/minecraft/client/animation/Keyframe
      // 254b: dup
      // 254c: ldc 2.25
      // 254e: ldc_w -81.714
      // 2551: ldc_w -5.273
      // 2554: ldc_w -1.406
      // 2557: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 255a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 255d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2560: aastore
      // 2561: dup
      // 2562: bipush 55
      // 2564: new net/minecraft/client/animation/Keyframe
      // 2567: dup
      // 2568: ldc 2.29
      // 256a: ldc_w -84.171
      // 256d: ldc_w -5.273
      // 2570: ldc_w -1.406
      // 2573: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2576: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2579: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 257c: aastore
      // 257d: dup
      // 257e: bipush 56
      // 2580: new net/minecraft/client/animation/Keyframe
      // 2583: dup
      // 2584: ldc 2.33
      // 2586: ldc_w -89.031
      // 2589: ldc_w -5.273
      // 258c: ldc_w -1.406
      // 258f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2592: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2595: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2598: aastore
      // 2599: dup
      // 259a: bipush 57
      // 259c: new net/minecraft/client/animation/Keyframe
      // 259f: dup
      // 25a0: ldc 2.38
      // 25a2: ldc_w -87.676
      // 25a5: ldc_w -5.273
      // 25a8: ldc_w -1.406
      // 25ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25b4: aastore
      // 25b5: dup
      // 25b6: bipush 58
      // 25b8: new net/minecraft/client/animation/Keyframe
      // 25bb: dup
      // 25bc: ldc 2.42
      // 25be: ldc_w -87.72
      // 25c1: ldc_w -5.273
      // 25c4: ldc_w -1.406
      // 25c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25d0: aastore
      // 25d1: dup
      // 25d2: bipush 59
      // 25d4: new net/minecraft/client/animation/Keyframe
      // 25d7: dup
      // 25d8: ldc 2.46
      // 25da: ldc_w -89.031
      // 25dd: ldc_w -5.273
      // 25e0: ldc_w -1.406
      // 25e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 25e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 25e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 25ec: aastore
      // 25ed: dup
      // 25ee: bipush 60
      // 25f0: new net/minecraft/client/animation/Keyframe
      // 25f3: dup
      // 25f4: ldc 2.62
      // 25f6: ldc_w -89.031
      // 25f9: ldc_w -5.273
      // 25fc: ldc_w -1.406
      // 25ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2602: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2605: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2608: aastore
      // 2609: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 260c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 260f: ldc_w "CTR_hips"
      // 2612: new net/minecraft/client/animation/AnimationChannel
      // 2615: dup
      // 2616: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2619: bipush 61
      // 261b: anewarray 24
      // 261e: dup
      // 261f: bipush 0
      // 2620: new net/minecraft/client/animation/Keyframe
      // 2623: dup
      // 2624: fconst_0
      // 2625: ldc_w 0.237
      // 2628: ldc_w -6.539
      // 262b: ldc_w -6.991
      // 262e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2631: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2634: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2637: aastore
      // 2638: dup
      // 2639: bipush 1
      // 263a: new net/minecraft/client/animation/Keyframe
      // 263d: dup
      // 263e: ldc 0.04
      // 2640: ldc_w 0.472
      // 2643: ldc_w -6.655
      // 2646: ldc_w -4.5442
      // 2649: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 264c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 264f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2652: aastore
      // 2653: dup
      // 2654: bipush 2
      // 2655: new net/minecraft/client/animation/Keyframe
      // 2658: dup
      // 2659: ldc 0.08
      // 265b: ldc_w 0.915
      // 265e: ldc_w -6.882
      // 2661: ldc_w 2.5947
      // 2664: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2667: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 266a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 266d: aastore
      // 266e: dup
      // 266f: bipush 3
      // 2670: new net/minecraft/client/animation/Keyframe
      // 2673: dup
      // 2674: ldc 0.12
      // 2676: ldc_w 1.171
      // 2679: ldc_w -7.046
      // 267c: ldc_w 10.6335
      // 267f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2682: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2685: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2688: aastore
      // 2689: dup
      // 268a: bipush 4
      // 268b: new net/minecraft/client/animation/Keyframe
      // 268e: dup
      // 268f: ldc 0.17
      // 2691: ldc_w 1.199
      // 2694: ldc_w -7.029
      // 2697: ldc_w 16.1888
      // 269a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 269d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26a3: aastore
      // 26a4: dup
      // 26a5: bipush 5
      // 26a6: new net/minecraft/client/animation/Keyframe
      // 26a9: dup
      // 26aa: ldc 0.21
      // 26ac: ldc_w 1.219
      // 26af: ldc_w -6.935
      // 26b2: ldc_w 19.5746
      // 26b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26be: aastore
      // 26bf: dup
      // 26c0: bipush 6
      // 26c2: new net/minecraft/client/animation/Keyframe
      // 26c5: dup
      // 26c6: ldc 0.25
      // 26c8: ldc_w 1.225
      // 26cb: ldc_w -6.921
      // 26ce: ldc_w 21.0015
      // 26d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26da: aastore
      // 26db: dup
      // 26dc: bipush 7
      // 26de: new net/minecraft/client/animation/Keyframe
      // 26e1: dup
      // 26e2: ldc 0.29
      // 26e4: ldc_w 1.213
      // 26e7: ldc_w -7.143
      // 26ea: ldc_w 21.0073
      // 26ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 26f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 26f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 26f6: aastore
      // 26f7: dup
      // 26f8: bipush 8
      // 26fa: new net/minecraft/client/animation/Keyframe
      // 26fd: dup
      // 26fe: ldc 0.33
      // 2700: ldc_w 1.191
      // 2703: ldc_w -7.502
      // 2706: ldc_w 20.5732
      // 2709: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 270c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 270f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2712: aastore
      // 2713: dup
      // 2714: bipush 9
      // 2716: new net/minecraft/client/animation/Keyframe
      // 2719: dup
      // 271a: ldc 0.38
      // 271c: ldc_w 1.174
      // 271f: ldc_w -7.796
      // 2722: ldc_w 20.0055
      // 2725: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2728: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 272b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 272e: aastore
      // 272f: dup
      // 2730: bipush 10
      // 2732: new net/minecraft/client/animation/Keyframe
      // 2735: dup
      // 2736: ldc 0.42
      // 2738: ldc_w 1.159
      // 273b: ldc_w -8.028
      // 273e: ldc_w 19.8613
      // 2741: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2744: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2747: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 274a: aastore
      // 274b: dup
      // 274c: bipush 11
      // 274e: new net/minecraft/client/animation/Keyframe
      // 2751: dup
      // 2752: ldc 0.46
      // 2754: ldc_w 1.147
      // 2757: ldc_w -8.198
      // 275a: ldc_w 19.8261
      // 275d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2760: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2763: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2766: aastore
      // 2767: dup
      // 2768: bipush 12
      // 276a: new net/minecraft/client/animation/Keyframe
      // 276d: dup
      // 276e: ldc 0.5
      // 2770: ldc_w 1.138
      // 2773: ldc_w -8.309
      // 2776: ldc_w 19.874
      // 2779: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 277c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 277f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2782: aastore
      // 2783: dup
      // 2784: bipush 13
      // 2786: new net/minecraft/client/animation/Keyframe
      // 2789: dup
      // 278a: ldc 0.54
      // 278c: ldc_w 1.132
      // 278f: ldc_w -8.364
      // 2792: ldc_w 19.9828
      // 2795: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2798: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 279b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 279e: aastore
      // 279f: dup
      // 27a0: bipush 14
      // 27a2: new net/minecraft/client/animation/Keyframe
      // 27a5: dup
      // 27a6: ldc 0.58
      // 27a8: ldc_w 1.128
      // 27ab: ldc_w -8.365
      // 27ae: ldc_w 20.1266
      // 27b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 27b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27ba: aastore
      // 27bb: dup
      // 27bc: bipush 15
      // 27be: new net/minecraft/client/animation/Keyframe
      // 27c1: dup
      // 27c2: ldc 0.62
      // 27c4: ldc_w 1.129
      // 27c7: ldc_w -8.315
      // 27ca: ldc_w 20.2795
      // 27cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 27d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27d6: aastore
      // 27d7: dup
      // 27d8: bipush 16
      // 27da: new net/minecraft/client/animation/Keyframe
      // 27dd: dup
      // 27de: ldc 0.67
      // 27e0: ldc_w 1.132
      // 27e3: ldc_w -8.218
      // 27e6: ldc_w 20.1258
      // 27e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 27ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 27ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 27f2: aastore
      // 27f3: dup
      // 27f4: bipush 17
      // 27f6: new net/minecraft/client/animation/Keyframe
      // 27f9: dup
      // 27fa: ldc 0.71
      // 27fc: ldc_w 1.139
      // 27ff: ldc_w -8.076
      // 2802: ldc_w 20.2166
      // 2805: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2808: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 280b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 280e: aastore
      // 280f: dup
      // 2810: bipush 18
      // 2812: new net/minecraft/client/animation/Keyframe
      // 2815: dup
      // 2816: ldc 0.75
      // 2818: ldc_w 1.15
      // 281b: ldc_w -7.893
      // 281e: ldc_w 20.2305
      // 2821: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2824: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2827: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 282a: aastore
      // 282b: dup
      // 282c: bipush 19
      // 282e: new net/minecraft/client/animation/Keyframe
      // 2831: dup
      // 2832: ldc 0.79
      // 2834: ldc_w 1.166
      // 2837: ldc_w -7.674
      // 283a: ldc_w 20.1353
      // 283d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2840: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2843: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2846: aastore
      // 2847: dup
      // 2848: bipush 20
      // 284a: new net/minecraft/client/animation/Keyframe
      // 284d: dup
      // 284e: ldc 0.83
      // 2850: ldc_w 1.186
      // 2853: ldc_w -7.423
      // 2856: ldc_w 19.8911
      // 2859: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 285c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 285f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2862: aastore
      // 2863: dup
      // 2864: bipush 21
      // 2866: new net/minecraft/client/animation/Keyframe
      // 2869: dup
      // 286a: ldc 0.88
      // 286c: ldc_w 1.213
      // 286f: ldc_w -7.143
      // 2872: ldc_w 19.1664
      // 2875: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2878: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 287b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 287e: aastore
      // 287f: dup
      // 2880: bipush 22
      // 2882: new net/minecraft/client/animation/Keyframe
      // 2885: dup
      // 2886: ldc 0.92
      // 2888: ldc_w 1.238
      // 288b: ldc_w -6.696
      // 288e: ldc_w 18.4873
      // 2891: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2894: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2897: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 289a: aastore
      // 289b: dup
      // 289c: bipush 23
      // 289e: new net/minecraft/client/animation/Keyframe
      // 28a1: dup
      // 28a2: ldc 0.96
      // 28a4: ldc_w 1.26
      // 28a7: ldc_w -6.139
      // 28aa: ldc_w 17.6971
      // 28ad: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 28b0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28b3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28b6: aastore
      // 28b7: dup
      // 28b8: bipush 24
      // 28ba: new net/minecraft/client/animation/Keyframe
      // 28bd: dup
      // 28be: fconst_1
      // 28bf: ldc_w 1.281
      // 28c2: ldc_w -5.58
      // 28c5: ldc_w 16.8569
      // 28c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 28cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28d1: aastore
      // 28d2: dup
      // 28d3: bipush 25
      // 28d5: new net/minecraft/client/animation/Keyframe
      // 28d8: dup
      // 28d9: ldc 1.04
      // 28db: ldc_w 1.3
      // 28de: ldc_w -5.053
      // 28e1: ldc_w 15.9828
      // 28e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 28e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 28ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 28ed: aastore
      // 28ee: dup
      // 28ef: bipush 26
      // 28f1: new net/minecraft/client/animation/Keyframe
      // 28f4: dup
      // 28f5: ldc 1.08
      // 28f7: ldc_w 1.319
      // 28fa: ldc_w -4.577
      // 28fd: ldc_w 15.0826
      // 2900: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2903: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2906: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2909: aastore
      // 290a: dup
      // 290b: bipush 27
      // 290d: new net/minecraft/client/animation/Keyframe
      // 2910: dup
      // 2911: ldc 1.12
      // 2913: ldc_w 1.337
      // 2916: ldc_w -4.161
      // 2919: ldc_w 14.1575
      // 291c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 291f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2922: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2925: aastore
      // 2926: dup
      // 2927: bipush 28
      // 2929: new net/minecraft/client/animation/Keyframe
      // 292c: dup
      // 292d: ldc 1.17
      // 292f: ldc_w 1.353
      // 2932: ldc_w -3.812
      // 2935: ldc_w 12.9168
      // 2938: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 293b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 293e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2941: aastore
      // 2942: dup
      // 2943: bipush 29
      // 2945: new net/minecraft/client/animation/Keyframe
      // 2948: dup
      // 2949: ldc 1.21
      // 294b: ldc_w 1.369
      // 294e: ldc_w -3.538
      // 2951: ldc_w 11.9376
      // 2954: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2957: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 295a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 295d: aastore
      // 295e: dup
      // 295f: bipush 30
      // 2961: new net/minecraft/client/animation/Keyframe
      // 2964: dup
      // 2965: ldc 1.25
      // 2967: ldc_w 1.383
      // 296a: ldc_w -3.342
      // 296d: ldc_w 10.9214
      // 2970: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2973: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2976: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2979: aastore
      // 297a: dup
      // 297b: bipush 31
      // 297d: new net/minecraft/client/animation/Keyframe
      // 2980: dup
      // 2981: ldc 1.29
      // 2983: ldc_w 1.395
      // 2986: ldc_w -3.229
      // 2989: ldc_w 9.8493
      // 298c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 298f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2992: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2995: aastore
      // 2996: dup
      // 2997: bipush 32
      // 2999: new net/minecraft/client/animation/Keyframe
      // 299c: dup
      // 299d: ldc 1.33
      // 299f: ldc_w 1.401
      // 29a2: ldc_w -3.203
      // 29a5: ldc_w 8.6581
      // 29a8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 29ab: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29ae: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29b1: aastore
      // 29b2: dup
      // 29b3: bipush 33
      // 29b5: new net/minecraft/client/animation/Keyframe
      // 29b8: dup
      // 29b9: ldc 1.38
      // 29bb: ldc_w 1.391
      // 29be: ldc_w -3.252
      // 29c1: ldc_w 7.1034
      // 29c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 29c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29cd: aastore
      // 29ce: dup
      // 29cf: bipush 34
      // 29d1: new net/minecraft/client/animation/Keyframe
      // 29d4: dup
      // 29d5: ldc 1.42
      // 29d7: ldc_w 1.363
      // 29da: ldc_w -3.362
      // 29dd: ldc_w 5.9492
      // 29e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 29e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 29e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 29e9: aastore
      // 29ea: dup
      // 29eb: bipush 35
      // 29ed: new net/minecraft/client/animation/Keyframe
      // 29f0: dup
      // 29f1: ldc 1.46
      // 29f3: ldc_w 1.319
      // 29f6: ldc_w -3.538
      // 29f9: ldc_w 4.9091
      // 29fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 29ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a05: aastore
      // 2a06: dup
      // 2a07: bipush 36
      // 2a09: new net/minecraft/client/animation/Keyframe
      // 2a0c: dup
      // 2a0d: ldc_w 1.48
      // 2a10: ldc_w 1.2905
      // 2a13: ldc_w -3.6605
      // 2a16: ldc_w 4.4475
      // 2a19: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2a1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a22: aastore
      // 2a23: dup
      // 2a24: bipush 37
      // 2a26: new net/minecraft/client/animation/Keyframe
      // 2a29: dup
      // 2a2a: ldc 1.5
      // 2a2c: ldc_w 1.262
      // 2a2f: ldc_w -3.783
      // 2a32: ldc_w 3.267
      // 2a35: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2a38: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a3b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a3e: aastore
      // 2a3f: dup
      // 2a40: bipush 38
      // 2a42: new net/minecraft/client/animation/Keyframe
      // 2a45: dup
      // 2a46: ldc 1.54
      // 2a48: ldc_w 1.194
      // 2a4b: ldc_w -4.104
      // 2a4e: ldc_w 1.026
      // 2a51: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2a54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a5a: aastore
      // 2a5b: dup
      // 2a5c: bipush 39
      // 2a5e: new net/minecraft/client/animation/Keyframe
      // 2a61: dup
      // 2a62: ldc 1.58
      // 2a64: ldc_w 1.116
      // 2a67: ldc_w -4.506
      // 2a6a: ldc_w -1.089
      // 2a6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2a70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a76: aastore
      // 2a77: dup
      // 2a78: bipush 40
      // 2a7a: new net/minecraft/client/animation/Keyframe
      // 2a7d: dup
      // 2a7e: ldc 1.62
      // 2a80: ldc_w 1.029
      // 2a83: ldc_w -4.997
      // 2a86: ldc_w -3.075
      // 2a89: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2a8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2a8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2a92: aastore
      // 2a93: dup
      // 2a94: bipush 41
      // 2a96: new net/minecraft/client/animation/Keyframe
      // 2a99: dup
      // 2a9a: ldc 1.67
      // 2a9c: ldc_w 0.932
      // 2a9f: ldc_w -5.584
      // 2aa2: ldc_w -5.575
      // 2aa5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2aa8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2aab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2aae: aastore
      // 2aaf: dup
      // 2ab0: bipush 42
      // 2ab2: new net/minecraft/client/animation/Keyframe
      // 2ab5: dup
      // 2ab6: ldc_w 1.71
      // 2ab9: ldc_w 0.828
      // 2abc: ldc_w -6.277
      // 2abf: ldc_w -7.283
      // 2ac2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2ac5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ac8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2acb: aastore
      // 2acc: dup
      // 2acd: bipush 43
      // 2acf: new net/minecraft/client/animation/Keyframe
      // 2ad2: dup
      // 2ad3: ldc_w 1.75
      // 2ad6: ldc_w 0.714
      // 2ad9: ldc_w -7.089
      // 2adc: ldc_w -8.841
      // 2adf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2ae2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ae5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ae8: aastore
      // 2ae9: dup
      // 2aea: bipush 44
      // 2aec: new net/minecraft/client/animation/Keyframe
      // 2aef: dup
      // 2af0: ldc_w 1.79
      // 2af3: ldc_w 0.592
      // 2af6: ldc_w -8.033
      // 2af9: ldc_w -10.239
      // 2afc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2aff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b05: aastore
      // 2b06: dup
      // 2b07: bipush 45
      // 2b09: new net/minecraft/client/animation/Keyframe
      // 2b0c: dup
      // 2b0d: ldc_w 1.83
      // 2b10: ldc 0.46
      // 2b12: ldc_w -9.128
      // 2b15: ldc_w -11.467
      // 2b18: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2b1b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b1e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b21: aastore
      // 2b22: dup
      // 2b23: bipush 46
      // 2b25: new net/minecraft/client/animation/Keyframe
      // 2b28: dup
      // 2b29: ldc_w 1.88
      // 2b2c: ldc_w 0.319
      // 2b2f: ldc_w -10.396
      // 2b32: ldc_w -13.158
      // 2b35: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2b38: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b3b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b3e: aastore
      // 2b3f: dup
      // 2b40: bipush 47
      // 2b42: new net/minecraft/client/animation/Keyframe
      // 2b45: dup
      // 2b46: ldc_w 1.92
      // 2b49: ldc_w 0.166
      // 2b4c: ldc_w -11.869
      // 2b4f: ldc_w -13.993
      // 2b52: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2b55: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b58: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b5b: aastore
      // 2b5c: dup
      // 2b5d: bipush 48
      // 2b5f: new net/minecraft/client/animation/Keyframe
      // 2b62: dup
      // 2b63: ldc_w 1.96
      // 2b66: fconst_0
      // 2b67: ldc_w -13.588
      // 2b6a: ldc_w -14.593
      // 2b6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2b70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b76: aastore
      // 2b77: dup
      // 2b78: bipush 49
      // 2b7a: new net/minecraft/client/animation/Keyframe
      // 2b7d: dup
      // 2b7e: fconst_2
      // 2b7f: ldc_w -0.183
      // 2b82: ldc_w -15.617
      // 2b85: ldc_w -14.917
      // 2b88: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2b8b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2b8e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2b91: aastore
      // 2b92: dup
      // 2b93: bipush 50
      // 2b95: new net/minecraft/client/animation/Keyframe
      // 2b98: dup
      // 2b99: ldc 2.04
      // 2b9b: ldc_w -0.391
      // 2b9e: ldc_w -18.058
      // 2ba1: ldc_w -14.89
      // 2ba4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2ba7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2baa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bad: aastore
      // 2bae: dup
      // 2baf: bipush 51
      // 2bb1: new net/minecraft/client/animation/Keyframe
      // 2bb4: dup
      // 2bb5: ldc 2.08
      // 2bb7: ldc_w -0.64
      // 2bba: ldc_w -21.105
      // 2bbd: ldc_w -14.349
      // 2bc0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2bc3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bc6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2bc9: aastore
      // 2bca: dup
      // 2bcb: bipush 52
      // 2bcd: new net/minecraft/client/animation/Keyframe
      // 2bd0: dup
      // 2bd1: ldc 2.12
      // 2bd3: ldc_w -1.011
      // 2bd6: ldc_w -25.232
      // 2bd9: ldc_w -9.843
      // 2bdc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2bdf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2be2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2be5: aastore
      // 2be6: dup
      // 2be7: bipush 53
      // 2be9: new net/minecraft/client/animation/Keyframe
      // 2bec: dup
      // 2bed: ldc 2.17
      // 2bef: ldc_w -1.011
      // 2bf2: ldc_w -22.194
      // 2bf5: ldc_w -9.624
      // 2bf8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2bfb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2bfe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c01: aastore
      // 2c02: dup
      // 2c03: bipush 54
      // 2c05: new net/minecraft/client/animation/Keyframe
      // 2c08: dup
      // 2c09: ldc 2.21
      // 2c0b: ldc_w -1.011
      // 2c0e: ldc_w -21.504
      // 2c11: ldc_w -9.237
      // 2c14: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2c17: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c1a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c1d: aastore
      // 2c1e: dup
      // 2c1f: bipush 55
      // 2c21: new net/minecraft/client/animation/Keyframe
      // 2c24: dup
      // 2c25: ldc 2.25
      // 2c27: ldc_w -1.011
      // 2c2a: ldc_w -22.401
      // 2c2d: ldc_w -8.451
      // 2c30: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2c33: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c36: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c39: aastore
      // 2c3a: dup
      // 2c3b: bipush 56
      // 2c3d: new net/minecraft/client/animation/Keyframe
      // 2c40: dup
      // 2c41: ldc 2.29
      // 2c43: ldc_w -1.011
      // 2c46: ldc_w -25.232
      // 2c49: ldc_w -6.691
      // 2c4c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2c4f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c52: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c55: aastore
      // 2c56: dup
      // 2c57: bipush 57
      // 2c59: new net/minecraft/client/animation/Keyframe
      // 2c5c: dup
      // 2c5d: ldc 2.33
      // 2c5f: ldc_w -1.011
      // 2c62: ldc_w -24.009
      // 2c65: ldc_w -6.794
      // 2c68: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2c6b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c6e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c71: aastore
      // 2c72: dup
      // 2c73: bipush 58
      // 2c75: new net/minecraft/client/animation/Keyframe
      // 2c78: dup
      // 2c79: ldc 2.38
      // 2c7b: ldc_w -1.011
      // 2c7e: ldc_w -24.103
      // 2c81: ldc_w -6.602
      // 2c84: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2c87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2c8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2c8d: aastore
      // 2c8e: dup
      // 2c8f: bipush 59
      // 2c91: new net/minecraft/client/animation/Keyframe
      // 2c94: dup
      // 2c95: ldc 2.42
      // 2c97: ldc_w -1.011
      // 2c9a: ldc_w -25.232
      // 2c9d: ldc_w -5.024
      // 2ca0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2ca3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ca6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ca9: aastore
      // 2caa: dup
      // 2cab: bipush 60
      // 2cad: new net/minecraft/client/animation/Keyframe
      // 2cb0: dup
      // 2cb1: ldc 2.62
      // 2cb3: ldc_w -1.011
      // 2cb6: ldc_w -25.232
      // 2cb9: ldc_w -5.024
      // 2cbc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 2cbf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cc2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cc5: aastore
      // 2cc6: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 2cc9: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 2ccc: ldc_w "CTR_chest"
      // 2ccf: new net/minecraft/client/animation/AnimationChannel
      // 2cd2: dup
      // 2cd3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 2cd6: bipush 62
      // 2cd8: anewarray 24
      // 2cdb: dup
      // 2cdc: bipush 0
      // 2cdd: new net/minecraft/client/animation/Keyframe
      // 2ce0: dup
      // 2ce1: fconst_0
      // 2ce2: ldc_w 11.174
      // 2ce5: ldc_w 11.305
      // 2ce8: ldc_w 8.012
      // 2ceb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2cee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2cf1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2cf4: aastore
      // 2cf5: dup
      // 2cf6: bipush 1
      // 2cf7: new net/minecraft/client/animation/Keyframe
      // 2cfa: dup
      // 2cfb: ldc 0.04
      // 2cfd: ldc_w 18.934
      // 2d00: ldc_w 11.204
      // 2d03: ldc_w 9.637
      // 2d06: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d09: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d0c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d0f: aastore
      // 2d10: dup
      // 2d11: bipush 2
      // 2d12: new net/minecraft/client/animation/Keyframe
      // 2d15: dup
      // 2d16: ldc 0.08
      // 2d18: ldc_w 33.271
      // 2d1b: ldc_w 10.725
      // 2d1e: ldc_w 12.64
      // 2d21: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d24: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d27: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d2a: aastore
      // 2d2b: dup
      // 2d2c: bipush 3
      // 2d2d: new net/minecraft/client/animation/Keyframe
      // 2d30: dup
      // 2d31: ldc 0.12
      // 2d33: ldc_w 40.769
      // 2d36: ldc_w 9.669
      // 2d39: ldc_w 14.218
      // 2d3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d45: aastore
      // 2d46: dup
      // 2d47: bipush 4
      // 2d48: new net/minecraft/client/animation/Keyframe
      // 2d4b: dup
      // 2d4c: ldc 0.17
      // 2d4e: ldc_w 36.394
      // 2d51: ldc_w 7.217
      // 2d54: ldc_w 12.926
      // 2d57: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d5a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d5d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d60: aastore
      // 2d61: dup
      // 2d62: bipush 5
      // 2d63: new net/minecraft/client/animation/Keyframe
      // 2d66: dup
      // 2d67: ldc 0.21
      // 2d69: ldc_w 26.302
      // 2d6c: ldc_w 4.024
      // 2d6f: ldc_w 10.229
      // 2d72: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d7b: aastore
      // 2d7c: dup
      // 2d7d: bipush 6
      // 2d7f: new net/minecraft/client/animation/Keyframe
      // 2d82: dup
      // 2d83: ldc 0.25
      // 2d85: ldc_w 13.542
      // 2d88: ldc_w 2.299
      // 2d8b: ldc_w 7.591
      // 2d8e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2d91: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2d94: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2d97: aastore
      // 2d98: dup
      // 2d99: bipush 7
      // 2d9b: new net/minecraft/client/animation/Keyframe
      // 2d9e: dup
      // 2d9f: ldc 0.29
      // 2da1: ldc_w 0.309
      // 2da4: ldc_w 1.661
      // 2da7: ldc_w 6.398
      // 2daa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2dad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2db0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2db3: aastore
      // 2db4: dup
      // 2db5: bipush 8
      // 2db7: new net/minecraft/client/animation/Keyframe
      // 2dba: dup
      // 2dbb: ldc 0.33
      // 2dbd: ldc_w -11.651
      // 2dc0: ldc_w 1.474
      // 2dc3: ldc_w 6.549
      // 2dc6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2dc9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2dcc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2dcf: aastore
      // 2dd0: dup
      // 2dd1: bipush 9
      // 2dd3: new net/minecraft/client/animation/Keyframe
      // 2dd6: dup
      // 2dd7: ldc 0.38
      // 2dd9: ldc_w -21.564
      // 2ddc: ldc 1.54
      // 2dde: ldc_w 6.966
      // 2de1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2de4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2de7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2dea: aastore
      // 2deb: dup
      // 2dec: bipush 10
      // 2dee: new net/minecraft/client/animation/Keyframe
      // 2df1: dup
      // 2df2: ldc 0.42
      // 2df4: ldc_w -28.75
      // 2df7: ldc_w 2.043
      // 2dfa: ldc_w 7.579
      // 2dfd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e06: aastore
      // 2e07: dup
      // 2e08: bipush 11
      // 2e0a: new net/minecraft/client/animation/Keyframe
      // 2e0d: dup
      // 2e0e: ldc 0.46
      // 2e10: ldc_w -32.823
      // 2e13: ldc_w 3.107
      // 2e16: ldc_w 8.305
      // 2e19: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e22: aastore
      // 2e23: dup
      // 2e24: bipush 12
      // 2e26: new net/minecraft/client/animation/Keyframe
      // 2e29: dup
      // 2e2a: ldc 0.5
      // 2e2c: ldc_w -33.88
      // 2e2f: ldc_w 4.177
      // 2e32: ldc_w 9.052
      // 2e35: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e38: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e3b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e3e: aastore
      // 2e3f: dup
      // 2e40: bipush 13
      // 2e42: new net/minecraft/client/animation/Keyframe
      // 2e45: dup
      // 2e46: ldc 0.54
      // 2e48: ldc_w -32.316
      // 2e4b: ldc_w 4.822
      // 2e4e: ldc_w 9.729
      // 2e51: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e5a: aastore
      // 2e5b: dup
      // 2e5c: bipush 14
      // 2e5e: new net/minecraft/client/animation/Keyframe
      // 2e61: dup
      // 2e62: ldc 0.58
      // 2e64: ldc_w -28.578
      // 2e67: ldc_w 5.137
      // 2e6a: ldc_w 10.264
      // 2e6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e76: aastore
      // 2e77: dup
      // 2e78: bipush 15
      // 2e7a: new net/minecraft/client/animation/Keyframe
      // 2e7d: dup
      // 2e7e: ldc 0.62
      // 2e80: ldc_w -24.401
      // 2e83: ldc_w 5.243
      // 2e86: ldc_w 10.605
      // 2e89: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2e8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2e8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2e92: aastore
      // 2e93: dup
      // 2e94: bipush 16
      // 2e96: new net/minecraft/client/animation/Keyframe
      // 2e99: dup
      // 2e9a: ldc 0.67
      // 2e9c: ldc_w -21.063
      // 2e9f: ldc_w 5.218
      // 2ea2: ldc_w 10.721
      // 2ea5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ea8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2eab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2eae: aastore
      // 2eaf: dup
      // 2eb0: bipush 17
      // 2eb2: new net/minecraft/client/animation/Keyframe
      // 2eb5: dup
      // 2eb6: ldc 0.71
      // 2eb8: ldc_w -18.418
      // 2ebb: ldc_w 5.073
      // 2ebe: ldc_w 10.405
      // 2ec1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ec4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ec7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2eca: aastore
      // 2ecb: dup
      // 2ecc: bipush 18
      // 2ece: new net/minecraft/client/animation/Keyframe
      // 2ed1: dup
      // 2ed2: ldc 0.75
      // 2ed4: ldc_w -16.369
      // 2ed7: ldc_w 4.785
      // 2eda: ldc_w 9.587
      // 2edd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ee0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ee3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ee6: aastore
      // 2ee7: dup
      // 2ee8: bipush 19
      // 2eea: new net/minecraft/client/animation/Keyframe
      // 2eed: dup
      // 2eee: ldc 0.79
      // 2ef0: ldc_w -14.824
      // 2ef3: ldc_w 4.327
      // 2ef6: ldc_w 8.493
      // 2ef9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2efc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2eff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f02: aastore
      // 2f03: dup
      // 2f04: bipush 20
      // 2f06: new net/minecraft/client/animation/Keyframe
      // 2f09: dup
      // 2f0a: ldc 0.83
      // 2f0c: ldc_w -13.715
      // 2f0f: ldc_w 3.698
      // 2f12: ldc_w 7.332
      // 2f15: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f1e: aastore
      // 2f1f: dup
      // 2f20: bipush 21
      // 2f22: new net/minecraft/client/animation/Keyframe
      // 2f25: dup
      // 2f26: ldc 0.88
      // 2f28: ldc_w -12.998
      // 2f2b: ldc_w 2.952
      // 2f2e: ldc_w 6.251
      // 2f31: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f34: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f37: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f3a: aastore
      // 2f3b: dup
      // 2f3c: bipush 22
      // 2f3e: new net/minecraft/client/animation/Keyframe
      // 2f41: dup
      // 2f42: ldc 0.92
      // 2f44: ldc_w -12.658
      // 2f47: ldc_w 2.197
      // 2f4a: ldc_w 5.328
      // 2f4d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f50: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f53: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f56: aastore
      // 2f57: dup
      // 2f58: bipush 23
      // 2f5a: new net/minecraft/client/animation/Keyframe
      // 2f5d: dup
      // 2f5e: ldc 0.96
      // 2f60: ldc_w -12.714
      // 2f63: ldc_w 1.549
      // 2f66: ldc_w 4.593
      // 2f69: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f6c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f6f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f72: aastore
      // 2f73: dup
      // 2f74: bipush 24
      // 2f76: new net/minecraft/client/animation/Keyframe
      // 2f79: dup
      // 2f7a: fconst_1
      // 2f7b: ldc_w -13.229
      // 2f7e: ldc_w 1.071
      // 2f81: ldc_w 4.054
      // 2f84: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2f87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2f8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2f8d: aastore
      // 2f8e: dup
      // 2f8f: bipush 25
      // 2f91: new net/minecraft/client/animation/Keyframe
      // 2f94: dup
      // 2f95: ldc 1.04
      // 2f97: ldc_w -14.332
      // 2f9a: ldc_w 0.771
      // 2f9d: ldc_w 3.703
      // 2fa0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fa3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fa6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fa9: aastore
      // 2faa: dup
      // 2fab: bipush 26
      // 2fad: new net/minecraft/client/animation/Keyframe
      // 2fb0: dup
      // 2fb1: ldc 1.08
      // 2fb3: ldc_w -16.305
      // 2fb6: ldc_w 0.632
      // 2fb9: ldc_w 3.528
      // 2fbc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fbf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fc2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fc5: aastore
      // 2fc6: dup
      // 2fc7: bipush 27
      // 2fc9: new net/minecraft/client/animation/Keyframe
      // 2fcc: dup
      // 2fcd: ldc 1.12
      // 2fcf: ldc_w -18.724
      // 2fd2: ldc_w 0.625
      // 2fd5: ldc_w 3.709
      // 2fd8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2fdb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2fde: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2fe1: aastore
      // 2fe2: dup
      // 2fe3: bipush 28
      // 2fe5: new net/minecraft/client/animation/Keyframe
      // 2fe8: dup
      // 2fe9: ldc 1.17
      // 2feb: ldc_w -20.736
      // 2fee: ldc_w 0.726
      // 2ff1: ldc_w 4.251
      // 2ff4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 2ff7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 2ffa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 2ffd: aastore
      // 2ffe: dup
      // 2fff: bipush 29
      // 3001: new net/minecraft/client/animation/Keyframe
      // 3004: dup
      // 3005: ldc 1.21
      // 3007: ldc_w -22.275
      // 300a: ldc_w 0.918
      // 300d: ldc_w 4.987
      // 3010: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3013: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3016: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3019: aastore
      // 301a: dup
      // 301b: bipush 30
      // 301d: new net/minecraft/client/animation/Keyframe
      // 3020: dup
      // 3021: ldc 1.25
      // 3023: ldc_w -23.314
      // 3026: ldc_w 1.183
      // 3029: ldc_w 5.815
      // 302c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 302f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3032: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3035: aastore
      // 3036: dup
      // 3037: bipush 31
      // 3039: new net/minecraft/client/animation/Keyframe
      // 303c: dup
      // 303d: ldc 1.29
      // 303f: ldc_w -23.857
      // 3042: ldc_w 1.506
      // 3045: ldc_w 6.668
      // 3048: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 304b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 304e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3051: aastore
      // 3052: dup
      // 3053: bipush 32
      // 3055: new net/minecraft/client/animation/Keyframe
      // 3058: dup
      // 3059: ldc 1.33
      // 305b: ldc_w -23.931
      // 305e: ldc_w 1.872
      // 3061: ldc_w 7.496
      // 3064: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3067: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 306a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 306d: aastore
      // 306e: dup
      // 306f: bipush 33
      // 3071: new net/minecraft/client/animation/Keyframe
      // 3074: dup
      // 3075: ldc 1.38
      // 3077: ldc_w -23.578
      // 307a: ldc_w 2.268
      // 307d: ldc_w 8.256
      // 3080: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3083: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3086: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3089: aastore
      // 308a: dup
      // 308b: bipush 34
      // 308d: new net/minecraft/client/animation/Keyframe
      // 3090: dup
      // 3091: ldc 1.42
      // 3093: ldc_w -22.844
      // 3096: ldc_w 2.683
      // 3099: ldc_w 8.911
      // 309c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 309f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30a5: aastore
      // 30a6: dup
      // 30a7: bipush 35
      // 30a9: new net/minecraft/client/animation/Keyframe
      // 30ac: dup
      // 30ad: ldc 1.46
      // 30af: ldc_w -21.774
      // 30b2: ldc_w 3.103
      // 30b5: ldc_w 9.424
      // 30b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30c1: aastore
      // 30c2: dup
      // 30c3: bipush 36
      // 30c5: new net/minecraft/client/animation/Keyframe
      // 30c8: dup
      // 30c9: ldc 1.5
      // 30cb: ldc_w -20.416
      // 30ce: ldc_w 3.519
      // 30d1: ldc_w 9.759
      // 30d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30dd: aastore
      // 30de: dup
      // 30df: bipush 37
      // 30e1: new net/minecraft/client/animation/Keyframe
      // 30e4: dup
      // 30e5: ldc 1.54
      // 30e7: ldc_w -18.499
      // 30ea: ldc_w 3.92
      // 30ed: ldc_w 9.875
      // 30f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 30f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 30f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 30f9: aastore
      // 30fa: dup
      // 30fb: bipush 38
      // 30fd: new net/minecraft/client/animation/Keyframe
      // 3100: dup
      // 3101: ldc 1.58
      // 3103: ldc_w -15.796
      // 3106: ldc_w 4.296
      // 3109: ldc_w 9.652
      // 310c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 310f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3112: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3115: aastore
      // 3116: dup
      // 3117: bipush 39
      // 3119: new net/minecraft/client/animation/Keyframe
      // 311c: dup
      // 311d: ldc 1.62
      // 311f: ldc_w -12.444
      // 3122: ldc_w 4.64
      // 3125: ldc_w 8.875
      // 3128: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 312b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 312e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3131: aastore
      // 3132: dup
      // 3133: bipush 40
      // 3135: new net/minecraft/client/animation/Keyframe
      // 3138: dup
      // 3139: ldc 1.67
      // 313b: ldc_w -8.61
      // 313e: ldc_w 4.942
      // 3141: ldc_w 7.299
      // 3144: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3147: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 314a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 314d: aastore
      // 314e: dup
      // 314f: bipush 41
      // 3151: new net/minecraft/client/animation/Keyframe
      // 3154: dup
      // 3155: ldc_w 1.71
      // 3158: ldc_w -4.489
      // 315b: ldc_w 5.186
      // 315e: ldc_w 4.717
      // 3161: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3164: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3167: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 316a: aastore
      // 316b: dup
      // 316c: bipush 42
      // 316e: new net/minecraft/client/animation/Keyframe
      // 3171: dup
      // 3172: ldc_w 1.75
      // 3175: ldc_w -0.236
      // 3178: ldc_w 5.344
      // 317b: ldc_w 1.528
      // 317e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3181: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3184: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3187: aastore
      // 3188: dup
      // 3189: bipush 43
      // 318b: new net/minecraft/client/animation/Keyframe
      // 318e: dup
      // 318f: ldc_w 1.79
      // 3192: ldc_w 4.05
      // 3195: ldc_w 5.396
      // 3198: ldc_w -1.179
      // 319b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 319e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31a4: aastore
      // 31a5: dup
      // 31a6: bipush 44
      // 31a8: new net/minecraft/client/animation/Keyframe
      // 31ab: dup
      // 31ac: ldc_w 1.83
      // 31af: ldc_w 8.209
      // 31b2: ldc_w 5.277
      // 31b5: ldc_w -2.946
      // 31b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31c1: aastore
      // 31c2: dup
      // 31c3: bipush 45
      // 31c5: new net/minecraft/client/animation/Keyframe
      // 31c8: dup
      // 31c9: ldc_w 1.88
      // 31cc: ldc_w 12.005
      // 31cf: ldc_w 4.995
      // 31d2: ldc_w -3.937
      // 31d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31de: aastore
      // 31df: dup
      // 31e0: bipush 46
      // 31e2: new net/minecraft/client/animation/Keyframe
      // 31e5: dup
      // 31e6: ldc_w 1.92
      // 31e9: ldc_w 15.189
      // 31ec: ldc_w 4.608
      // 31ef: ldc_w -4.378
      // 31f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 31f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 31f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 31fb: aastore
      // 31fc: dup
      // 31fd: bipush 47
      // 31ff: new net/minecraft/client/animation/Keyframe
      // 3202: dup
      // 3203: ldc_w 1.96
      // 3206: ldc_w 17.522
      // 3209: ldc_w 4.141
      // 320c: ldc_w -4.486
      // 320f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3212: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3215: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3218: aastore
      // 3219: dup
      // 321a: bipush 48
      // 321c: new net/minecraft/client/animation/Keyframe
      // 321f: dup
      // 3220: fconst_2
      // 3221: ldc_w 18.534
      // 3224: ldc_w 3.61
      // 3227: ldc_w -4.364
      // 322a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 322d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3230: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3233: aastore
      // 3234: dup
      // 3235: bipush 49
      // 3237: new net/minecraft/client/animation/Keyframe
      // 323a: dup
      // 323b: ldc 2.04
      // 323d: ldc_w 17.902
      // 3240: ldc_w 3.023
      // 3243: ldc_w -4.015
      // 3246: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3249: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 324c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 324f: aastore
      // 3250: dup
      // 3251: bipush 50
      // 3253: new net/minecraft/client/animation/Keyframe
      // 3256: dup
      // 3257: ldc 2.08
      // 3259: ldc_w 15.654
      // 325c: ldc_w 2.384
      // 325f: ldc_w -3.435
      // 3262: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3265: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3268: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 326b: aastore
      // 326c: dup
      // 326d: bipush 51
      // 326f: new net/minecraft/client/animation/Keyframe
      // 3272: dup
      // 3273: ldc 2.12
      // 3275: ldc_w 11.857
      // 3278: ldc_w 1.691
      // 327b: ldc_w -2.61
      // 327e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3281: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3284: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3287: aastore
      // 3288: dup
      // 3289: bipush 52
      // 328b: new net/minecraft/client/animation/Keyframe
      // 328e: dup
      // 328f: ldc 2.17
      // 3291: ldc_w 6.602
      // 3294: ldc_w 0.932
      // 3297: ldc_w -1.507
      // 329a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 329d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32a3: aastore
      // 32a4: dup
      // 32a5: bipush 53
      // 32a7: new net/minecraft/client/animation/Keyframe
      // 32aa: dup
      // 32ab: ldc 2.21
      // 32ad: fconst_0
      // 32ae: fconst_0
      // 32af: fconst_0
      // 32b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32b9: aastore
      // 32ba: dup
      // 32bb: bipush 54
      // 32bd: new net/minecraft/client/animation/Keyframe
      // 32c0: dup
      // 32c1: ldc 2.25
      // 32c3: ldc_w 4.154
      // 32c6: ldc_w -0.217
      // 32c9: ldc_w 0.414
      // 32cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32d5: aastore
      // 32d6: dup
      // 32d7: bipush 55
      // 32d9: new net/minecraft/client/animation/Keyframe
      // 32dc: dup
      // 32dd: ldc 2.29
      // 32df: ldc_w 3.742
      // 32e2: ldc_w -0.582
      // 32e5: ldc_w 0.916
      // 32e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 32eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 32ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 32f1: aastore
      // 32f2: dup
      // 32f3: bipush 56
      // 32f5: new net/minecraft/client/animation/Keyframe
      // 32f8: dup
      // 32f9: ldc 2.33
      // 32fb: fconst_0
      // 32fc: ldc_w -1.187
      // 32ff: fconst_0
      // 3300: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3303: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3306: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3309: aastore
      // 330a: dup
      // 330b: bipush 57
      // 330d: new net/minecraft/client/animation/Keyframe
      // 3310: dup
      // 3311: ldc 2.38
      // 3313: ldc 1.42
      // 3315: ldc_w -1.187
      // 3318: ldc_w -0.199
      // 331b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 331e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3321: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3324: aastore
      // 3325: dup
      // 3326: bipush 58
      // 3328: new net/minecraft/client/animation/Keyframe
      // 332b: dup
      // 332c: ldc 2.42
      // 332e: ldc_w 1.781
      // 3331: ldc_w -1.187
      // 3334: ldc_w -0.39
      // 3337: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 333a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 333d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3340: aastore
      // 3341: dup
      // 3342: bipush 59
      // 3344: new net/minecraft/client/animation/Keyframe
      // 3347: dup
      // 3348: ldc 2.46
      // 334a: ldc_w 1.228
      // 334d: ldc_w -1.187
      // 3350: fconst_0
      // 3351: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3354: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3357: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 335a: aastore
      // 335b: dup
      // 335c: bipush 60
      // 335e: new net/minecraft/client/animation/Keyframe
      // 3361: dup
      // 3362: ldc 2.5
      // 3364: fconst_0
      // 3365: ldc_w -1.187
      // 3368: fconst_0
      // 3369: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 336c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 336f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3372: aastore
      // 3373: dup
      // 3374: bipush 61
      // 3376: new net/minecraft/client/animation/Keyframe
      // 3379: dup
      // 337a: ldc 2.62
      // 337c: fconst_0
      // 337d: ldc_w -1.187
      // 3380: fconst_0
      // 3381: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3384: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3387: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 338a: aastore
      // 338b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 338e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3391: ldc_w "CTR_chest"
      // 3394: new net/minecraft/client/animation/AnimationChannel
      // 3397: dup
      // 3398: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 339b: bipush 2
      // 339c: anewarray 24
      // 339f: dup
      // 33a0: bipush 0
      // 33a1: new net/minecraft/client/animation/Keyframe
      // 33a4: dup
      // 33a5: fconst_0
      // 33a6: ldc_w -0.365
      // 33a9: ldc_w -0.408
      // 33ac: ldc_w -1.364
      // 33af: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 33b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33b8: aastore
      // 33b9: dup
      // 33ba: bipush 1
      // 33bb: new net/minecraft/client/animation/Keyframe
      // 33be: dup
      // 33bf: ldc 2.62
      // 33c1: ldc_w -0.365
      // 33c4: ldc_w -0.408
      // 33c7: ldc_w -1.364
      // 33ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 33cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 33d3: aastore
      // 33d4: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 33d7: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 33da: ldc_w "CTR_head"
      // 33dd: new net/minecraft/client/animation/AnimationChannel
      // 33e0: dup
      // 33e1: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 33e4: bipush 62
      // 33e6: anewarray 24
      // 33e9: dup
      // 33ea: bipush 0
      // 33eb: new net/minecraft/client/animation/Keyframe
      // 33ee: dup
      // 33ef: fconst_0
      // 33f0: ldc_w -42.709
      // 33f3: ldc_w -6.683
      // 33f6: ldc_w 0.359
      // 33f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 33fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 33ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3402: aastore
      // 3403: dup
      // 3404: bipush 1
      // 3405: new net/minecraft/client/animation/Keyframe
      // 3408: dup
      // 3409: ldc 0.04
      // 340b: ldc_w -19.363
      // 340e: ldc_w -6.579
      // 3411: ldc_w -0.814
      // 3414: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3417: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 341a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 341d: aastore
      // 341e: dup
      // 341f: bipush 2
      // 3420: new net/minecraft/client/animation/Keyframe
      // 3423: dup
      // 3424: ldc 0.08
      // 3426: ldc_w 23.883
      // 3429: ldc_w -6.022
      // 342c: ldc_w -1.931
      // 342f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3432: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3435: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3438: aastore
      // 3439: dup
      // 343a: bipush 3
      // 343b: new net/minecraft/client/animation/Keyframe
      // 343e: dup
      // 343f: ldc 0.12
      // 3441: ldc_w 47.114
      // 3444: ldc_w -4.514
      // 3447: ldc_w -1.534
      // 344a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 344d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3450: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3453: aastore
      // 3454: dup
      // 3455: bipush 4
      // 3456: new net/minecraft/client/animation/Keyframe
      // 3459: dup
      // 345a: ldc 0.17
      // 345c: ldc_w 47.608
      // 345f: ldc_w -1.303
      // 3462: ldc_w 0.505
      // 3465: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3468: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 346b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 346e: aastore
      // 346f: dup
      // 3470: bipush 5
      // 3471: new net/minecraft/client/animation/Keyframe
      // 3474: dup
      // 3475: ldc 0.21
      // 3477: ldc_w 48.456
      // 347a: ldc_w 1.766
      // 347d: ldc_w 4.988
      // 3480: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3483: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3486: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3489: aastore
      // 348a: dup
      // 348b: bipush 6
      // 348d: new net/minecraft/client/animation/Keyframe
      // 3490: dup
      // 3491: ldc 0.25
      // 3493: ldc_w 48.521
      // 3496: ldc_w 1.451
      // 3499: ldc_w 10.515
      // 349c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 349f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34a5: aastore
      // 34a6: dup
      // 34a7: bipush 7
      // 34a9: new net/minecraft/client/animation/Keyframe
      // 34ac: dup
      // 34ad: ldc 0.29
      // 34af: ldc_w 47.179
      // 34b2: ldc_w -1.083
      // 34b5: ldc_w 13.1
      // 34b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34c1: aastore
      // 34c2: dup
      // 34c3: bipush 8
      // 34c5: new net/minecraft/client/animation/Keyframe
      // 34c8: dup
      // 34c9: ldc 0.33
      // 34cb: ldc_w 44.342
      // 34ce: ldc_w -3.329
      // 34d1: ldc_w 12.646
      // 34d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34dd: aastore
      // 34de: dup
      // 34df: bipush 9
      // 34e1: new net/minecraft/client/animation/Keyframe
      // 34e4: dup
      // 34e5: ldc 0.38
      // 34e7: ldc_w 40.012
      // 34ea: ldc_w -5.036
      // 34ed: ldc_w 11.446
      // 34f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 34f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 34f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 34f9: aastore
      // 34fa: dup
      // 34fb: bipush 10
      // 34fd: new net/minecraft/client/animation/Keyframe
      // 3500: dup
      // 3501: ldc 0.42
      // 3503: ldc_w 34.473
      // 3506: ldc_w -6.089
      // 3509: ldc_w 9.756
      // 350c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 350f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3512: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3515: aastore
      // 3516: dup
      // 3517: bipush 11
      // 3519: new net/minecraft/client/animation/Keyframe
      // 351c: dup
      // 351d: ldc 0.46
      // 351f: ldc_w 28.293
      // 3522: ldc_w -6.6
      // 3525: ldc_w 7.784
      // 3528: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 352b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 352e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3531: aastore
      // 3532: dup
      // 3533: bipush 12
      // 3535: new net/minecraft/client/animation/Keyframe
      // 3538: dup
      // 3539: ldc 0.5
      // 353b: ldc_w 22.587
      // 353e: ldc_w -6.372
      // 3541: ldc_w 5.988
      // 3544: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3547: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 354a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 354d: aastore
      // 354e: dup
      // 354f: bipush 13
      // 3551: new net/minecraft/client/animation/Keyframe
      // 3554: dup
      // 3555: ldc 0.54
      // 3557: ldc_w 18.729
      // 355a: ldc_w -5.391
      // 355d: ldc_w 4.663
      // 3560: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3563: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3566: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3569: aastore
      // 356a: dup
      // 356b: bipush 14
      // 356d: new net/minecraft/client/animation/Keyframe
      // 3570: dup
      // 3571: ldc 0.58
      // 3573: ldc_w 15.346
      // 3576: ldc_w -4.089
      // 3579: ldc_w 3.689
      // 357c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 357f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3582: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3585: aastore
      // 3586: dup
      // 3587: bipush 15
      // 3589: new net/minecraft/client/animation/Keyframe
      // 358c: dup
      // 358d: ldc 0.62
      // 358f: ldc_w 11.685
      // 3592: ldc_w -2.908
      // 3595: ldc_w 2.864
      // 3598: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 359b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 359e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35a1: aastore
      // 35a2: dup
      // 35a3: bipush 16
      // 35a5: new net/minecraft/client/animation/Keyframe
      // 35a8: dup
      // 35a9: ldc 0.67
      // 35ab: ldc_w 8.987
      // 35ae: ldc_w -1.999
      // 35b1: ldc_w 2.176
      // 35b4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 35b7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35ba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35bd: aastore
      // 35be: dup
      // 35bf: bipush 17
      // 35c1: new net/minecraft/client/animation/Keyframe
      // 35c4: dup
      // 35c5: ldc 0.71
      // 35c7: ldc_w 7.067
      // 35ca: ldc_w -1.402
      // 35cd: ldc_w 1.92
      // 35d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 35d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35d9: aastore
      // 35da: dup
      // 35db: bipush 18
      // 35dd: new net/minecraft/client/animation/Keyframe
      // 35e0: dup
      // 35e1: ldc 0.75
      // 35e3: ldc_w 5.813
      // 35e6: ldc_w -1.075
      // 35e9: ldc_w 2.245
      // 35ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 35ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 35f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 35f5: aastore
      // 35f6: dup
      // 35f7: bipush 19
      // 35f9: new net/minecraft/client/animation/Keyframe
      // 35fc: dup
      // 35fd: ldc 0.79
      // 35ff: ldc_w 5.152
      // 3602: ldc_w -0.921
      // 3605: ldc_w 2.988
      // 3608: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 360b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 360e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3611: aastore
      // 3612: dup
      // 3613: bipush 20
      // 3615: new net/minecraft/client/animation/Keyframe
      // 3618: dup
      // 3619: ldc 0.83
      // 361b: ldc_w 5.545
      // 361e: ldc_w -1.473
      // 3621: ldc_w 3.931
      // 3624: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3627: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 362a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 362d: aastore
      // 362e: dup
      // 362f: bipush 21
      // 3631: new net/minecraft/client/animation/Keyframe
      // 3634: dup
      // 3635: ldc 0.88
      // 3637: ldc_w 7.331
      // 363a: ldc_w -3.157
      // 363d: ldc_w 4.882
      // 3640: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3643: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3646: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3649: aastore
      // 364a: dup
      // 364b: bipush 22
      // 364d: new net/minecraft/client/animation/Keyframe
      // 3650: dup
      // 3651: ldc 0.92
      // 3653: ldc_w 9.818
      // 3656: ldc_w -5.431
      // 3659: ldc_w 5.592
      // 365c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 365f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3662: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3665: aastore
      // 3666: dup
      // 3667: bipush 23
      // 3669: new net/minecraft/client/animation/Keyframe
      // 366c: dup
      // 366d: ldc 0.96
      // 366f: ldc_w 12.512
      // 3672: ldc_w -7.784
      // 3675: ldc_w 5.942
      // 3678: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 367b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 367e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3681: aastore
      // 3682: dup
      // 3683: bipush 24
      // 3685: new net/minecraft/client/animation/Keyframe
      // 3688: dup
      // 3689: fconst_1
      // 368a: ldc_w 15.437
      // 368d: ldc_w -10.041
      // 3690: ldc_w 5.972
      // 3693: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3696: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3699: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 369c: aastore
      // 369d: dup
      // 369e: bipush 25
      // 36a0: new net/minecraft/client/animation/Keyframe
      // 36a3: dup
      // 36a4: ldc 1.04
      // 36a6: ldc_w 18.636
      // 36a9: ldc_w -11.966
      // 36ac: ldc_w 5.695
      // 36af: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 36b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36b8: aastore
      // 36b9: dup
      // 36ba: bipush 26
      // 36bc: new net/minecraft/client/animation/Keyframe
      // 36bf: dup
      // 36c0: ldc 1.08
      // 36c2: ldc_w 22.287
      // 36c5: ldc_w -13.301
      // 36c8: ldc_w 5.071
      // 36cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 36ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36d4: aastore
      // 36d5: dup
      // 36d6: bipush 27
      // 36d8: new net/minecraft/client/animation/Keyframe
      // 36db: dup
      // 36dc: ldc 1.12
      // 36de: ldc_w 25.839
      // 36e1: ldc_w -13.648
      // 36e4: ldc_w 4.055
      // 36e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 36ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 36ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 36f0: aastore
      // 36f1: dup
      // 36f2: bipush 28
      // 36f4: new net/minecraft/client/animation/Keyframe
      // 36f7: dup
      // 36f8: ldc 1.17
      // 36fa: ldc_w 28.312
      // 36fd: ldc_w -12.663
      // 3700: ldc_w 2.908
      // 3703: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3706: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3709: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 370c: aastore
      // 370d: dup
      // 370e: bipush 29
      // 3710: new net/minecraft/client/animation/Keyframe
      // 3713: dup
      // 3714: ldc 1.21
      // 3716: ldc_w 29.698
      // 3719: ldc_w -10.583
      // 371c: ldc_w 1.781
      // 371f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3722: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3725: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3728: aastore
      // 3729: dup
      // 372a: bipush 30
      // 372c: new net/minecraft/client/animation/Keyframe
      // 372f: dup
      // 3730: ldc 1.25
      // 3732: ldc_w 30.088
      // 3735: ldc_w -7.837
      // 3738: ldc_w 0.685
      // 373b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 373e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3741: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3744: aastore
      // 3745: dup
      // 3746: bipush 31
      // 3748: new net/minecraft/client/animation/Keyframe
      // 374b: dup
      // 374c: ldc 1.29
      // 374e: ldc_w 29.64
      // 3751: ldc_w -4.849
      // 3754: ldc_w -0.397
      // 3757: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 375a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 375d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3760: aastore
      // 3761: dup
      // 3762: bipush 32
      // 3764: new net/minecraft/client/animation/Keyframe
      // 3767: dup
      // 3768: ldc 1.33
      // 376a: ldc_w 28.651
      // 376d: ldc_w -2.042
      // 3770: ldc_w -1.411
      // 3773: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3776: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3779: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 377c: aastore
      // 377d: dup
      // 377e: bipush 33
      // 3780: new net/minecraft/client/animation/Keyframe
      // 3783: dup
      // 3784: ldc 1.38
      // 3786: ldc_w 27.497
      // 3789: ldc_w 0.478
      // 378c: ldc_w -2.442
      // 378f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3792: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3795: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3798: aastore
      // 3799: dup
      // 379a: bipush 34
      // 379c: new net/minecraft/client/animation/Keyframe
      // 379f: dup
      // 37a0: ldc 1.42
      // 37a2: ldc_w 26.226
      // 37a5: ldc_w 2.583
      // 37a8: ldc_w -3.621
      // 37ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 37ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37b4: aastore
      // 37b5: dup
      // 37b6: bipush 35
      // 37b8: new net/minecraft/client/animation/Keyframe
      // 37bb: dup
      // 37bc: ldc 1.46
      // 37be: ldc_w 24.966
      // 37c1: ldc_w 3.871
      // 37c4: ldc_w -4.812
      // 37c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 37ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37d0: aastore
      // 37d1: dup
      // 37d2: bipush 36
      // 37d4: new net/minecraft/client/animation/Keyframe
      // 37d7: dup
      // 37d8: ldc 1.5
      // 37da: ldc_w 23.861
      // 37dd: ldc_w 3.996
      // 37e0: ldc_w -5.86
      // 37e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 37e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 37e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 37ec: aastore
      // 37ed: dup
      // 37ee: bipush 37
      // 37f0: new net/minecraft/client/animation/Keyframe
      // 37f3: dup
      // 37f4: ldc 1.54
      // 37f6: ldc_w 22.711
      // 37f9: ldc_w 3.346
      // 37fc: ldc_w -6.684
      // 37ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3802: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3805: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3808: aastore
      // 3809: dup
      // 380a: bipush 38
      // 380c: new net/minecraft/client/animation/Keyframe
      // 380f: dup
      // 3810: ldc 1.58
      // 3812: ldc_w 21.305
      // 3815: ldc_w 2.502
      // 3818: ldc_w -7.174
      // 381b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 381e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3821: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3824: aastore
      // 3825: dup
      // 3826: bipush 39
      // 3828: new net/minecraft/client/animation/Keyframe
      // 382b: dup
      // 382c: ldc 1.62
      // 382e: ldc_w 19.777
      // 3831: ldc_w 1.47
      // 3834: ldc_w -7.066
      // 3837: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 383a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 383d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3840: aastore
      // 3841: dup
      // 3842: bipush 40
      // 3844: new net/minecraft/client/animation/Keyframe
      // 3847: dup
      // 3848: ldc 1.67
      // 384a: ldc_w 18.277
      // 384d: ldc_w 0.347
      // 3850: ldc_w -6.066
      // 3853: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3856: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3859: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 385c: aastore
      // 385d: dup
      // 385e: bipush 41
      // 3860: new net/minecraft/client/animation/Keyframe
      // 3863: dup
      // 3864: ldc_w 1.71
      // 3867: ldc_w 16.965
      // 386a: ldc_w -0.65
      // 386d: ldc_w -3.931
      // 3870: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3873: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3876: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3879: aastore
      // 387a: dup
      // 387b: bipush 42
      // 387d: new net/minecraft/client/animation/Keyframe
      // 3880: dup
      // 3881: ldc_w 1.75
      // 3884: ldc_w 16.04
      // 3887: ldc_w -1.212
      // 388a: ldc_w -1.07
      // 388d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3890: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3893: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3896: aastore
      // 3897: dup
      // 3898: bipush 43
      // 389a: new net/minecraft/client/animation/Keyframe
      // 389d: dup
      // 389e: ldc_w 1.79
      // 38a1: ldc_w 15.767
      // 38a4: ldc_w -1.213
      // 38a7: ldc_w 1.369
      // 38aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38b3: aastore
      // 38b4: dup
      // 38b5: bipush 44
      // 38b7: new net/minecraft/client/animation/Keyframe
      // 38ba: dup
      // 38bb: ldc_w 1.83
      // 38be: ldc_w 16.432
      // 38c1: ldc_w -0.761
      // 38c4: ldc_w 2.855
      // 38c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38d0: aastore
      // 38d1: dup
      // 38d2: bipush 45
      // 38d4: new net/minecraft/client/animation/Keyframe
      // 38d7: dup
      // 38d8: ldc_w 1.88
      // 38db: ldc_w 18.311
      // 38de: ldc_w -0.053
      // 38e1: ldc_w 3.507
      // 38e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 38e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 38ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 38ed: aastore
      // 38ee: dup
      // 38ef: bipush 46
      // 38f1: new net/minecraft/client/animation/Keyframe
      // 38f4: dup
      // 38f5: ldc_w 1.92
      // 38f8: ldc_w 21.686
      // 38fb: ldc_w 0.749
      // 38fe: ldc_w 3.558
      // 3901: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3904: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3907: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 390a: aastore
      // 390b: dup
      // 390c: bipush 47
      // 390e: new net/minecraft/client/animation/Keyframe
      // 3911: dup
      // 3912: ldc_w 1.96
      // 3915: ldc_w 26.854
      // 3918: ldc_w 1.599
      // 391b: ldc_w 3.262
      // 391e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3921: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3924: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3927: aastore
      // 3928: dup
      // 3929: bipush 48
      // 392b: new net/minecraft/client/animation/Keyframe
      // 392e: dup
      // 392f: fconst_2
      // 3930: ldc_w 34.385
      // 3933: ldc_w 2.497
      // 3936: ldc_w 2.799
      // 3939: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 393c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 393f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3942: aastore
      // 3943: dup
      // 3944: bipush 49
      // 3946: new net/minecraft/client/animation/Keyframe
      // 3949: dup
      // 394a: ldc 2.04
      // 394c: ldc_w 44.772
      // 394f: ldc_w 3.479
      // 3952: ldc_w 2.304
      // 3955: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3958: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 395b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 395e: aastore
      // 395f: dup
      // 3960: bipush 50
      // 3962: new net/minecraft/client/animation/Keyframe
      // 3965: dup
      // 3966: ldc 2.08
      // 3968: ldc_w 58.252
      // 396b: ldc_w 4.628
      // 396e: ldc_w 1.959
      // 3971: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3974: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3977: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 397a: aastore
      // 397b: dup
      // 397c: bipush 51
      // 397e: new net/minecraft/client/animation/Keyframe
      // 3981: dup
      // 3982: ldc 2.12
      // 3984: ldc_w 75.264
      // 3987: ldc_w 6.155
      // 398a: ldc_w 2.009
      // 398d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3990: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3993: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3996: aastore
      // 3997: dup
      // 3998: bipush 52
      // 399a: new net/minecraft/client/animation/Keyframe
      // 399d: dup
      // 399e: ldc 2.17
      // 39a0: ldc_w 58.879
      // 39a3: ldc_w 5.327
      // 39a6: ldc_w -0.561
      // 39a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39b2: aastore
      // 39b3: dup
      // 39b4: bipush 53
      // 39b6: new net/minecraft/client/animation/Keyframe
      // 39b9: dup
      // 39ba: ldc 2.21
      // 39bc: ldc_w -13.129
      // 39bf: ldc_w 4.407
      // 39c2: ldc_w -9.015
      // 39c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39ce: aastore
      // 39cf: dup
      // 39d0: bipush 54
      // 39d2: new net/minecraft/client/animation/Keyframe
      // 39d5: dup
      // 39d6: ldc 2.25
      // 39d8: ldc_w -57.245
      // 39db: ldc_w 2.494
      // 39de: ldc_w -13.326
      // 39e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 39e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 39e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 39ea: aastore
      // 39eb: dup
      // 39ec: bipush 55
      // 39ee: new net/minecraft/client/animation/Keyframe
      // 39f1: dup
      // 39f2: ldc 2.29
      // 39f4: ldc_w -48.348
      // 39f7: ldc_w 6.066
      // 39fa: ldc_w -12.077
      // 39fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a06: aastore
      // 3a07: dup
      // 3a08: bipush 56
      // 3a0a: new net/minecraft/client/animation/Keyframe
      // 3a0d: dup
      // 3a0e: ldc 2.33
      // 3a10: ldc_w -33.649
      // 3a13: ldc_w 10.653
      // 3a16: ldc_w -8.16
      // 3a19: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a22: aastore
      // 3a23: dup
      // 3a24: bipush 57
      // 3a26: new net/minecraft/client/animation/Keyframe
      // 3a29: dup
      // 3a2a: ldc 2.38
      // 3a2c: ldc_w -41.403
      // 3a2f: ldc_w 9.201
      // 3a32: ldc_w -11.244
      // 3a35: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a38: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a3b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a3e: aastore
      // 3a3f: dup
      // 3a40: bipush 58
      // 3a42: new net/minecraft/client/animation/Keyframe
      // 3a45: dup
      // 3a46: ldc 2.42
      // 3a48: ldc_w -40.571
      // 3a4b: ldc_w 7.875
      // 3a4e: ldc_w -10.698
      // 3a51: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a54: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a57: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a5a: aastore
      // 3a5b: dup
      // 3a5c: bipush 59
      // 3a5e: new net/minecraft/client/animation/Keyframe
      // 3a61: dup
      // 3a62: ldc 2.46
      // 3a64: ldc_w -37.646
      // 3a67: ldc_w 7.165
      // 3a6a: ldc_w -10.262
      // 3a6d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a70: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a73: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a76: aastore
      // 3a77: dup
      // 3a78: bipush 60
      // 3a7a: new net/minecraft/client/animation/Keyframe
      // 3a7d: dup
      // 3a7e: ldc 2.5
      // 3a80: ldc_w -35.947
      // 3a83: ldc_w 6.94
      // 3a86: ldc_w -9.805
      // 3a89: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3a8c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3a8f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3a92: aastore
      // 3a93: dup
      // 3a94: bipush 61
      // 3a96: new net/minecraft/client/animation/Keyframe
      // 3a99: dup
      // 3a9a: ldc 2.62
      // 3a9c: ldc_w -35.947
      // 3a9f: ldc_w 6.94
      // 3aa2: ldc_w -9.805
      // 3aa5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3aa8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3aab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3aae: aastore
      // 3aaf: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3ab2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3ab5: ldc_w "CTR_head"
      // 3ab8: new net/minecraft/client/animation/AnimationChannel
      // 3abb: dup
      // 3abc: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3abf: bipush 31
      // 3ac1: anewarray 24
      // 3ac4: dup
      // 3ac5: bipush 0
      // 3ac6: new net/minecraft/client/animation/Keyframe
      // 3ac9: dup
      // 3aca: fconst_0
      // 3acb: ldc_w 0.245
      // 3ace: ldc_w 1.257
      // 3ad1: ldc_w 2.263
      // 3ad4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ad7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ada: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3add: aastore
      // 3ade: dup
      // 3adf: bipush 1
      // 3ae0: new net/minecraft/client/animation/Keyframe
      // 3ae3: dup
      // 3ae4: ldc 0.33
      // 3ae6: ldc_w 0.245
      // 3ae9: ldc_w 1.257
      // 3aec: ldc_w 2.263
      // 3aef: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3af2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3af5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3af8: aastore
      // 3af9: dup
      // 3afa: bipush 2
      // 3afb: new net/minecraft/client/animation/Keyframe
      // 3afe: dup
      // 3aff: ldc 0.38
      // 3b01: ldc_w 0.245
      // 3b04: ldc_w 1.257
      // 3b07: ldc_w 2.264
      // 3b0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b13: aastore
      // 3b14: dup
      // 3b15: bipush 3
      // 3b16: new net/minecraft/client/animation/Keyframe
      // 3b19: dup
      // 3b1a: ldc 0.42
      // 3b1c: ldc_w 0.245
      // 3b1f: ldc_w 1.257
      // 3b22: ldc_w 2.264
      // 3b25: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b28: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b2b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b2e: aastore
      // 3b2f: dup
      // 3b30: bipush 4
      // 3b31: new net/minecraft/client/animation/Keyframe
      // 3b34: dup
      // 3b35: ldc 0.46
      // 3b37: ldc_w 0.245
      // 3b3a: ldc_w 1.257
      // 3b3d: ldc_w 2.263
      // 3b40: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b43: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b46: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b49: aastore
      // 3b4a: dup
      // 3b4b: bipush 5
      // 3b4c: new net/minecraft/client/animation/Keyframe
      // 3b4f: dup
      // 3b50: ldc 0.83
      // 3b52: ldc_w 0.245
      // 3b55: ldc_w 1.257
      // 3b58: ldc_w 2.263
      // 3b5b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b5e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b61: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b64: aastore
      // 3b65: dup
      // 3b66: bipush 6
      // 3b68: new net/minecraft/client/animation/Keyframe
      // 3b6b: dup
      // 3b6c: ldc 0.88
      // 3b6e: ldc_w 0.245
      // 3b71: ldc_w 1.257
      // 3b74: ldc_w 2.264
      // 3b77: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b7a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b7d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b80: aastore
      // 3b81: dup
      // 3b82: bipush 7
      // 3b84: new net/minecraft/client/animation/Keyframe
      // 3b87: dup
      // 3b88: ldc 0.92
      // 3b8a: ldc_w 0.245
      // 3b8d: ldc_w 1.257
      // 3b90: ldc_w 2.263
      // 3b93: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3b96: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3b99: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3b9c: aastore
      // 3b9d: dup
      // 3b9e: bipush 8
      // 3ba0: new net/minecraft/client/animation/Keyframe
      // 3ba3: dup
      // 3ba4: ldc 1.25
      // 3ba6: ldc_w 0.245
      // 3ba9: ldc_w 1.257
      // 3bac: ldc_w 2.263
      // 3baf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3bb2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bb5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bb8: aastore
      // 3bb9: dup
      // 3bba: bipush 9
      // 3bbc: new net/minecraft/client/animation/Keyframe
      // 3bbf: dup
      // 3bc0: ldc 1.29
      // 3bc2: ldc_w 0.245
      // 3bc5: ldc_w 1.257
      // 3bc8: ldc_w 2.264
      // 3bcb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3bce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bd1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bd4: aastore
      // 3bd5: dup
      // 3bd6: bipush 10
      // 3bd8: new net/minecraft/client/animation/Keyframe
      // 3bdb: dup
      // 3bdc: ldc 1.33
      // 3bde: ldc_w 0.245
      // 3be1: ldc_w 1.257
      // 3be4: ldc_w 2.263
      // 3be7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3bea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3bed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3bf0: aastore
      // 3bf1: dup
      // 3bf2: bipush 11
      // 3bf4: new net/minecraft/client/animation/Keyframe
      // 3bf7: dup
      // 3bf8: ldc 1.38
      // 3bfa: ldc_w 0.245
      // 3bfd: ldc_w 1.257
      // 3c00: ldc_w 2.263
      // 3c03: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3c06: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c09: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c0c: aastore
      // 3c0d: dup
      // 3c0e: bipush 12
      // 3c10: new net/minecraft/client/animation/Keyframe
      // 3c13: dup
      // 3c14: ldc 1.42
      // 3c16: ldc_w 0.245
      // 3c19: ldc_w 1.257
      // 3c1c: ldc_w 2.264
      // 3c1f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3c22: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c25: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c28: aastore
      // 3c29: dup
      // 3c2a: bipush 13
      // 3c2c: new net/minecraft/client/animation/Keyframe
      // 3c2f: dup
      // 3c30: ldc 1.46
      // 3c32: ldc_w 0.245
      // 3c35: ldc_w 1.257
      // 3c38: ldc_w 2.263
      // 3c3b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3c3e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c41: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c44: aastore
      // 3c45: dup
      // 3c46: bipush 14
      // 3c48: new net/minecraft/client/animation/Keyframe
      // 3c4b: dup
      // 3c4c: ldc 1.5
      // 3c4e: ldc_w 0.245
      // 3c51: ldc_w 1.257
      // 3c54: ldc_w 2.263
      // 3c57: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3c5a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c5d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c60: aastore
      // 3c61: dup
      // 3c62: bipush 15
      // 3c64: new net/minecraft/client/animation/Keyframe
      // 3c67: dup
      // 3c68: ldc 1.54
      // 3c6a: ldc_w 0.288
      // 3c6d: ldc_w 1.2
      // 3c70: ldc_w 2.28
      // 3c73: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3c76: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c79: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c7c: aastore
      // 3c7d: dup
      // 3c7e: bipush 16
      // 3c80: new net/minecraft/client/animation/Keyframe
      // 3c83: dup
      // 3c84: ldc 1.58
      // 3c86: ldc_w 0.411
      // 3c89: ldc 1.04
      // 3c8b: ldc_w 2.327
      // 3c8e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3c91: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3c94: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3c97: aastore
      // 3c98: dup
      // 3c99: bipush 17
      // 3c9b: new net/minecraft/client/animation/Keyframe
      // 3c9e: dup
      // 3c9f: ldc 1.62
      // 3ca1: ldc_w 0.6
      // 3ca4: ldc_w 0.794
      // 3ca7: ldc_w 2.4
      // 3caa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3cad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3cb0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cb3: aastore
      // 3cb4: dup
      // 3cb5: bipush 18
      // 3cb7: new net/minecraft/client/animation/Keyframe
      // 3cba: dup
      // 3cbb: ldc 1.67
      // 3cbd: ldc_w 0.844
      // 3cc0: ldc_w 0.476
      // 3cc3: ldc_w 2.493
      // 3cc6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3cc9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ccc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ccf: aastore
      // 3cd0: dup
      // 3cd1: bipush 19
      // 3cd3: new net/minecraft/client/animation/Keyframe
      // 3cd6: dup
      // 3cd7: ldc_w 1.71
      // 3cda: ldc_w 1.13
      // 3cdd: ldc_w 0.102
      // 3ce0: ldc_w 2.603
      // 3ce3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ce6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ce9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3cec: aastore
      // 3ced: dup
      // 3cee: bipush 20
      // 3cf0: new net/minecraft/client/animation/Keyframe
      // 3cf3: dup
      // 3cf4: ldc_w 1.75
      // 3cf7: ldc_w 1.447
      // 3cfa: ldc_w -0.311
      // 3cfd: ldc_w 2.724
      // 3d00: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3d03: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d06: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d09: aastore
      // 3d0a: dup
      // 3d0b: bipush 21
      // 3d0d: new net/minecraft/client/animation/Keyframe
      // 3d10: dup
      // 3d11: ldc_w 1.79
      // 3d14: ldc_w 1.782
      // 3d17: ldc_w -0.748
      // 3d1a: ldc_w 2.853
      // 3d1d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3d20: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d23: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d26: aastore
      // 3d27: dup
      // 3d28: bipush 22
      // 3d2a: new net/minecraft/client/animation/Keyframe
      // 3d2d: dup
      // 3d2e: ldc_w 1.83
      // 3d31: ldc_w 2.123
      // 3d34: ldc_w -1.193
      // 3d37: ldc_w 2.984
      // 3d3a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3d3d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d40: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d43: aastore
      // 3d44: dup
      // 3d45: bipush 23
      // 3d47: new net/minecraft/client/animation/Keyframe
      // 3d4a: dup
      // 3d4b: ldc_w 1.88
      // 3d4e: ldc_w 2.458
      // 3d51: ldc_w -1.629
      // 3d54: ldc_w 3.112
      // 3d57: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3d5a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d5d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d60: aastore
      // 3d61: dup
      // 3d62: bipush 24
      // 3d64: new net/minecraft/client/animation/Keyframe
      // 3d67: dup
      // 3d68: ldc_w 1.92
      // 3d6b: ldc_w 2.775
      // 3d6e: ldc_w -2.043
      // 3d71: ldc_w 3.234
      // 3d74: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3d77: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d7a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d7d: aastore
      // 3d7e: dup
      // 3d7f: bipush 25
      // 3d81: new net/minecraft/client/animation/Keyframe
      // 3d84: dup
      // 3d85: ldc_w 1.96
      // 3d88: ldc_w 3.061
      // 3d8b: ldc_w -2.416
      // 3d8e: ldc_w 3.343
      // 3d91: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3d94: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3d97: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3d9a: aastore
      // 3d9b: dup
      // 3d9c: bipush 26
      // 3d9e: new net/minecraft/client/animation/Keyframe
      // 3da1: dup
      // 3da2: fconst_2
      // 3da3: ldc_w 3.305
      // 3da6: ldc_w -2.734
      // 3da9: ldc_w 3.437
      // 3dac: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3daf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3db2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3db5: aastore
      // 3db6: dup
      // 3db7: bipush 27
      // 3db9: new net/minecraft/client/animation/Keyframe
      // 3dbc: dup
      // 3dbd: ldc 2.04
      // 3dbf: ldc_w 3.494
      // 3dc2: ldc_w -2.981
      // 3dc5: ldc_w 3.51
      // 3dc8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3dcb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3dd1: aastore
      // 3dd2: dup
      // 3dd3: bipush 28
      // 3dd5: new net/minecraft/client/animation/Keyframe
      // 3dd8: dup
      // 3dd9: ldc 2.08
      // 3ddb: ldc_w 3.617
      // 3dde: ldc_w -3.141
      // 3de1: ldc_w 3.556
      // 3de4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3de7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3dea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ded: aastore
      // 3dee: dup
      // 3def: bipush 29
      // 3df1: new net/minecraft/client/animation/Keyframe
      // 3df4: dup
      // 3df5: ldc 2.12
      // 3df7: ldc_w 3.66
      // 3dfa: ldc_w -3.197
      // 3dfd: ldc_w 3.573
      // 3e00: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e03: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e06: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e09: aastore
      // 3e0a: dup
      // 3e0b: bipush 30
      // 3e0d: new net/minecraft/client/animation/Keyframe
      // 3e10: dup
      // 3e11: ldc 2.62
      // 3e13: ldc_w 3.66
      // 3e16: ldc_w -3.197
      // 3e19: ldc_w 3.573
      // 3e1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3e1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e25: aastore
      // 3e26: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3e29: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3e2c: ldc_w "CTR_shoulde1r_left"
      // 3e2f: new net/minecraft/client/animation/AnimationChannel
      // 3e32: dup
      // 3e33: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3e36: bipush 5
      // 3e37: anewarray 24
      // 3e3a: dup
      // 3e3b: bipush 0
      // 3e3c: new net/minecraft/client/animation/Keyframe
      // 3e3f: dup
      // 3e40: fconst_0
      // 3e41: ldc_w 0.892
      // 3e44: ldc_w -3.013
      // 3e47: ldc_w 0.342
      // 3e4a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e4d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e50: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e53: aastore
      // 3e54: dup
      // 3e55: bipush 1
      // 3e56: new net/minecraft/client/animation/Keyframe
      // 3e59: dup
      // 3e5a: ldc 0.04
      // 3e5c: ldc_w 0.661
      // 3e5f: ldc_w -2.232
      // 3e62: ldc_w 0.253
      // 3e65: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e68: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e6b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e6e: aastore
      // 3e6f: dup
      // 3e70: bipush 2
      // 3e71: new net/minecraft/client/animation/Keyframe
      // 3e74: dup
      // 3e75: ldc 0.08
      // 3e77: ldc_w 0.231
      // 3e7a: ldc_w -0.781
      // 3e7d: ldc_w 0.089
      // 3e80: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e83: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e86: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e89: aastore
      // 3e8a: dup
      // 3e8b: bipush 3
      // 3e8c: new net/minecraft/client/animation/Keyframe
      // 3e8f: dup
      // 3e90: ldc 0.12
      // 3e92: fconst_0
      // 3e93: fconst_0
      // 3e94: fconst_0
      // 3e95: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3e98: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3e9b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3e9e: aastore
      // 3e9f: dup
      // 3ea0: bipush 4
      // 3ea1: new net/minecraft/client/animation/Keyframe
      // 3ea4: dup
      // 3ea5: ldc 2.62
      // 3ea7: fconst_0
      // 3ea8: fconst_0
      // 3ea9: fconst_0
      // 3eaa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 3ead: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3eb0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3eb3: aastore
      // 3eb4: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 3eb7: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 3eba: ldc_w "CTR_shoulde1r_left"
      // 3ebd: new net/minecraft/client/animation/AnimationChannel
      // 3ec0: dup
      // 3ec1: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 3ec4: bipush 58
      // 3ec6: anewarray 24
      // 3ec9: dup
      // 3eca: bipush 0
      // 3ecb: new net/minecraft/client/animation/Keyframe
      // 3ece: dup
      // 3ecf: fconst_0
      // 3ed0: ldc_w -7.769
      // 3ed3: ldc_w 8.448
      // 3ed6: ldc_w 14.525
      // 3ed9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3edc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3edf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ee2: aastore
      // 3ee3: dup
      // 3ee4: bipush 1
      // 3ee5: new net/minecraft/client/animation/Keyframe
      // 3ee8: dup
      // 3ee9: ldc 0.04
      // 3eeb: ldc_w -5.704
      // 3eee: ldc_w 6.97
      // 3ef1: ldc_w 11.214
      // 3ef4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3ef7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3efa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3efd: aastore
      // 3efe: dup
      // 3eff: bipush 2
      // 3f00: new net/minecraft/client/animation/Keyframe
      // 3f03: dup
      // 3f04: ldc 0.08
      // 3f06: ldc_w -1.867
      // 3f09: ldc_w 4.225
      // 3f0c: ldc_w 4.247
      // 3f0f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3f12: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f15: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f18: aastore
      // 3f19: dup
      // 3f1a: bipush 3
      // 3f1b: new net/minecraft/client/animation/Keyframe
      // 3f1e: dup
      // 3f1f: ldc 0.12
      // 3f21: ldc_w 0.199
      // 3f24: ldc_w 2.746
      // 3f27: ldc_w -1.926
      // 3f2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3f2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f33: aastore
      // 3f34: dup
      // 3f35: bipush 4
      // 3f36: new net/minecraft/client/animation/Keyframe
      // 3f39: dup
      // 3f3a: ldc 0.17
      // 3f3c: ldc_w -0.064
      // 3f3f: ldc_w 3.099
      // 3f42: ldc_w -5.704
      // 3f45: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3f48: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f4b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f4e: aastore
      // 3f4f: dup
      // 3f50: bipush 5
      // 3f51: new net/minecraft/client/animation/Keyframe
      // 3f54: dup
      // 3f55: ldc 0.21
      // 3f57: ldc_w -0.756
      // 3f5a: ldc_w 4.031
      // 3f5d: ldc_w -8.544
      // 3f60: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3f63: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f66: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f69: aastore
      // 3f6a: dup
      // 3f6b: bipush 6
      // 3f6d: new net/minecraft/client/animation/Keyframe
      // 3f70: dup
      // 3f71: ldc 0.25
      // 3f73: ldc_w -1.734
      // 3f76: ldc_w 5.347
      // 3f79: ldc_w -10.578
      // 3f7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3f7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3f85: aastore
      // 3f86: dup
      // 3f87: bipush 7
      // 3f89: new net/minecraft/client/animation/Keyframe
      // 3f8c: dup
      // 3f8d: ldc 0.29
      // 3f8f: ldc_w -2.855
      // 3f92: ldc_w 6.856
      // 3f95: ldc_w -11.942
      // 3f98: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3f9b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3f9e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fa1: aastore
      // 3fa2: dup
      // 3fa3: bipush 8
      // 3fa5: new net/minecraft/client/animation/Keyframe
      // 3fa8: dup
      // 3fa9: ldc 0.33
      // 3fab: ldc_w -3.976
      // 3fae: ldc_w 8.365
      // 3fb1: ldc_w -12.769
      // 3fb4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3fb7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fbd: aastore
      // 3fbe: dup
      // 3fbf: bipush 9
      // 3fc1: new net/minecraft/client/animation/Keyframe
      // 3fc4: dup
      // 3fc5: ldc 0.38
      // 3fc7: ldc_w -4.954
      // 3fca: ldc_w 9.681
      // 3fcd: ldc_w -13.194
      // 3fd0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3fd3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3fd6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3fd9: aastore
      // 3fda: dup
      // 3fdb: bipush 10
      // 3fdd: new net/minecraft/client/animation/Keyframe
      // 3fe0: dup
      // 3fe1: ldc 0.42
      // 3fe3: ldc_w -5.646
      // 3fe6: ldc_w 10.612
      // 3fe9: ldc_w -13.351
      // 3fec: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 3fef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 3ff2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 3ff5: aastore
      // 3ff6: dup
      // 3ff7: bipush 11
      // 3ff9: new net/minecraft/client/animation/Keyframe
      // 3ffc: dup
      // 3ffd: ldc 0.46
      // 3fff: ldc_w -5.908
      // 4002: ldc_w 10.965
      // 4005: ldc_w -13.373
      // 4008: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 400b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 400e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4011: aastore
      // 4012: dup
      // 4013: bipush 12
      // 4015: new net/minecraft/client/animation/Keyframe
      // 4018: dup
      // 4019: ldc 0.5
      // 401b: ldc_w -5.895
      // 401e: ldc_w 10.886
      // 4021: ldc_w -13.268
      // 4024: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4027: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 402a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 402d: aastore
      // 402e: dup
      // 402f: bipush 13
      // 4031: new net/minecraft/client/animation/Keyframe
      // 4034: dup
      // 4035: ldc 0.54
      // 4037: ldc_w -5.858
      // 403a: ldc_w 10.664
      // 403d: ldc_w -12.969
      // 4040: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4043: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4046: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4049: aastore
      // 404a: dup
      // 404b: bipush 14
      // 404d: new net/minecraft/client/animation/Keyframe
      // 4050: dup
      // 4051: ldc 0.58
      // 4053: ldc_w -5.798
      // 4056: ldc_w 10.32
      // 4059: ldc_w -12.502
      // 405c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 405f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4062: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4065: aastore
      // 4066: dup
      // 4067: bipush 15
      // 4069: new net/minecraft/client/animation/Keyframe
      // 406c: dup
      // 406d: ldc 0.62
      // 406f: ldc_w -5.719
      // 4072: ldc_w 9.876
      // 4075: ldc_w -11.895
      // 4078: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 407b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 407e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4081: aastore
      // 4082: dup
      // 4083: bipush 16
      // 4085: new net/minecraft/client/animation/Keyframe
      // 4088: dup
      // 4089: ldc 0.67
      // 408b: ldc_w -5.623
      // 408e: ldc_w 9.353
      // 4091: ldc_w -11.172
      // 4094: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4097: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 409a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 409d: aastore
      // 409e: dup
      // 409f: bipush 17
      // 40a1: new net/minecraft/client/animation/Keyframe
      // 40a4: dup
      // 40a5: ldc 0.71
      // 40a7: ldc_w -5.513
      // 40aa: ldc_w 8.772
      // 40ad: ldc_w -10.36
      // 40b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 40b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40b9: aastore
      // 40ba: dup
      // 40bb: bipush 18
      // 40bd: new net/minecraft/client/animation/Keyframe
      // 40c0: dup
      // 40c1: ldc 0.75
      // 40c3: ldc_w -5.391
      // 40c6: ldc_w 8.156
      // 40c9: ldc_w -9.486
      // 40cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 40cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40d5: aastore
      // 40d6: dup
      // 40d7: bipush 19
      // 40d9: new net/minecraft/client/animation/Keyframe
      // 40dc: dup
      // 40dd: ldc 0.79
      // 40df: ldc_w -5.259
      // 40e2: ldc_w 7.525
      // 40e5: ldc_w -8.574
      // 40e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 40eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 40ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 40f1: aastore
      // 40f2: dup
      // 40f3: bipush 20
      // 40f5: new net/minecraft/client/animation/Keyframe
      // 40f8: dup
      // 40f9: ldc 0.83
      // 40fb: ldc_w -5.12
      // 40fe: ldc_w 6.902
      // 4101: ldc_w -7.651
      // 4104: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4107: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 410a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 410d: aastore
      // 410e: dup
      // 410f: bipush 21
      // 4111: new net/minecraft/client/animation/Keyframe
      // 4114: dup
      // 4115: ldc 0.88
      // 4117: ldc_w -4.976
      // 411a: ldc_w 6.307
      // 411d: ldc_w -6.744
      // 4120: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4123: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4126: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4129: aastore
      // 412a: dup
      // 412b: bipush 22
      // 412d: new net/minecraft/client/animation/Keyframe
      // 4130: dup
      // 4131: ldc 0.92
      // 4133: ldc_w -4.831
      // 4136: ldc_w 5.762
      // 4139: ldc_w -5.878
      // 413c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 413f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4142: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4145: aastore
      // 4146: dup
      // 4147: bipush 23
      // 4149: new net/minecraft/client/animation/Keyframe
      // 414c: dup
      // 414d: ldc 0.96
      // 414f: ldc_w -4.686
      // 4152: ldc_w 5.289
      // 4155: ldc_w -5.08
      // 4158: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 415b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 415e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4161: aastore
      // 4162: dup
      // 4163: bipush 24
      // 4165: new net/minecraft/client/animation/Keyframe
      // 4168: dup
      // 4169: fconst_1
      // 416a: ldc_w -4.544
      // 416d: ldc_w 4.909
      // 4170: ldc_w -4.375
      // 4173: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4176: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4179: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 417c: aastore
      // 417d: dup
      // 417e: bipush 25
      // 4180: new net/minecraft/client/animation/Keyframe
      // 4183: dup
      // 4184: ldc 1.04
      // 4186: ldc_w -4.408
      // 4189: ldc_w 4.644
      // 418c: ldc_w -3.789
      // 418f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4192: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4195: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4198: aastore
      // 4199: dup
      // 419a: bipush 26
      // 419c: new net/minecraft/client/animation/Keyframe
      // 419f: dup
      // 41a0: ldc 1.08
      // 41a2: ldc_w -4.28
      // 41a5: ldc_w 4.515
      // 41a8: ldc_w -3.35
      // 41ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 41ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41b4: aastore
      // 41b5: dup
      // 41b6: bipush 27
      // 41b8: new net/minecraft/client/animation/Keyframe
      // 41bb: dup
      // 41bc: ldc 1.12
      // 41be: ldc_w -4.162
      // 41c1: ldc_w 4.467
      // 41c4: ldc_w -3.05
      // 41c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 41ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41d0: aastore
      // 41d1: dup
      // 41d2: bipush 28
      // 41d4: new net/minecraft/client/animation/Keyframe
      // 41d7: dup
      // 41d8: ldc 1.17
      // 41da: ldc_w -4.054
      // 41dd: ldc_w 4.428
      // 41e0: ldc_w -2.851
      // 41e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 41e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 41e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 41ec: aastore
      // 41ed: dup
      // 41ee: bipush 29
      // 41f0: new net/minecraft/client/animation/Keyframe
      // 41f3: dup
      // 41f4: ldc 1.21
      // 41f6: ldc_w -3.958
      // 41f9: ldc_w 4.397
      // 41fc: ldc_w -2.731
      // 41ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4202: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4205: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4208: aastore
      // 4209: dup
      // 420a: bipush 30
      // 420c: new net/minecraft/client/animation/Keyframe
      // 420f: dup
      // 4210: ldc 1.25
      // 4212: ldc_w -3.874
      // 4215: ldc_w 4.375
      // 4218: ldc_w -2.666
      // 421b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 421e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4221: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4224: aastore
      // 4225: dup
      // 4226: bipush 31
      // 4228: new net/minecraft/client/animation/Keyframe
      // 422b: dup
      // 422c: ldc 1.29
      // 422e: ldc_w -3.801
      // 4231: ldc_w 4.358
      // 4234: ldc_w -2.633
      // 4237: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 423a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 423d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4240: aastore
      // 4241: dup
      // 4242: bipush 32
      // 4244: new net/minecraft/client/animation/Keyframe
      // 4247: dup
      // 4248: ldc 1.33
      // 424a: ldc_w -3.741
      // 424d: ldc_w 4.347
      // 4250: ldc_w -2.61
      // 4253: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4256: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4259: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 425c: aastore
      // 425d: dup
      // 425e: bipush 33
      // 4260: new net/minecraft/client/animation/Keyframe
      // 4263: dup
      // 4264: ldc 1.38
      // 4266: ldc_w -3.694
      // 4269: ldc_w 4.341
      // 426c: ldc_w -2.574
      // 426f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4272: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4275: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4278: aastore
      // 4279: dup
      // 427a: bipush 34
      // 427c: new net/minecraft/client/animation/Keyframe
      // 427f: dup
      // 4280: ldc 1.42
      // 4282: ldc_w -3.659
      // 4285: ldc_w 4.337
      // 4288: ldc_w -2.501
      // 428b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 428e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4291: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4294: aastore
      // 4295: dup
      // 4296: bipush 35
      // 4298: new net/minecraft/client/animation/Keyframe
      // 429b: dup
      // 429c: ldc 1.46
      // 429e: ldc_w -3.639
      // 42a1: ldc_w 4.336
      // 42a4: ldc_w -2.37
      // 42a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42b0: aastore
      // 42b1: dup
      // 42b2: bipush 36
      // 42b4: new net/minecraft/client/animation/Keyframe
      // 42b7: dup
      // 42b8: ldc 1.5
      // 42ba: ldc_w -3.631
      // 42bd: ldc_w 4.336
      // 42c0: ldc_w -2.156
      // 42c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42cc: aastore
      // 42cd: dup
      // 42ce: bipush 37
      // 42d0: new net/minecraft/client/animation/Keyframe
      // 42d3: dup
      // 42d4: ldc 1.54
      // 42d6: ldc_w -3.632
      // 42d9: ldc_w 4.336
      // 42dc: ldc_w -1.844
      // 42df: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42e2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 42e5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 42e8: aastore
      // 42e9: dup
      // 42ea: bipush 38
      // 42ec: new net/minecraft/client/animation/Keyframe
      // 42ef: dup
      // 42f0: ldc 1.58
      // 42f2: ldc_w -3.632
      // 42f5: ldc_w 4.34
      // 42f8: ldc_w -1.443
      // 42fb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 42fe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4301: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4304: aastore
      // 4305: dup
      // 4306: bipush 39
      // 4308: new net/minecraft/client/animation/Keyframe
      // 430b: dup
      // 430c: ldc 1.62
      // 430e: ldc_w -3.633
      // 4311: ldc_w 4.35
      // 4314: ldc_w -0.969
      // 4317: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 431a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 431d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4320: aastore
      // 4321: dup
      // 4322: bipush 40
      // 4324: new net/minecraft/client/animation/Keyframe
      // 4327: dup
      // 4328: ldc 1.67
      // 432a: ldc_w -3.634
      // 432d: ldc_w 4.37
      // 4330: ldc_w -0.44
      // 4333: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4336: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4339: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 433c: aastore
      // 433d: dup
      // 433e: bipush 41
      // 4340: new net/minecraft/client/animation/Keyframe
      // 4343: dup
      // 4344: ldc_w 1.71
      // 4347: ldc_w -3.637
      // 434a: ldc_w 4.403
      // 434d: ldc_w 0.129
      // 4350: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4353: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4356: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4359: aastore
      // 435a: dup
      // 435b: bipush 42
      // 435d: new net/minecraft/client/animation/Keyframe
      // 4360: dup
      // 4361: ldc_w 1.75
      // 4364: ldc_w -3.642
      // 4367: ldc_w 4.452
      // 436a: ldc_w 0.721
      // 436d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4370: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4373: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4376: aastore
      // 4377: dup
      // 4378: bipush 43
      // 437a: new net/minecraft/client/animation/Keyframe
      // 437d: dup
      // 437e: ldc_w 1.79
      // 4381: ldc_w -3.647
      // 4384: ldc_w 4.52
      // 4387: ldc_w 1.321
      // 438a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 438d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4390: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4393: aastore
      // 4394: dup
      // 4395: bipush 44
      // 4397: new net/minecraft/client/animation/Keyframe
      // 439a: dup
      // 439b: ldc_w 1.83
      // 439e: ldc_w -3.655
      // 43a1: ldc_w 4.611
      // 43a4: ldc_w 1.911
      // 43a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43b0: aastore
      // 43b1: dup
      // 43b2: bipush 45
      // 43b4: new net/minecraft/client/animation/Keyframe
      // 43b7: dup
      // 43b8: ldc_w 1.88
      // 43bb: ldc_w -3.666
      // 43be: ldc_w 4.728
      // 43c1: ldc_w 2.475
      // 43c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43cd: aastore
      // 43ce: dup
      // 43cf: bipush 46
      // 43d1: new net/minecraft/client/animation/Keyframe
      // 43d4: dup
      // 43d5: ldc_w 1.92
      // 43d8: ldc_w -3.678
      // 43db: ldc_w 4.874
      // 43de: ldc_w 2.998
      // 43e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 43e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 43e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 43ea: aastore
      // 43eb: dup
      // 43ec: bipush 47
      // 43ee: new net/minecraft/client/animation/Keyframe
      // 43f1: dup
      // 43f2: ldc_w 1.96
      // 43f5: ldc_w -3.694
      // 43f8: ldc_w 5.051
      // 43fb: ldc_w 3.462
      // 43fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4401: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4404: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4407: aastore
      // 4408: dup
      // 4409: bipush 48
      // 440b: new net/minecraft/client/animation/Keyframe
      // 440e: dup
      // 440f: fconst_2
      // 4410: ldc_w -3.712
      // 4413: ldc_w 5.265
      // 4416: ldc_w 3.852
      // 4419: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 441c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 441f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4422: aastore
      // 4423: dup
      // 4424: bipush 49
      // 4426: new net/minecraft/client/animation/Keyframe
      // 4429: dup
      // 442a: ldc 2.04
      // 442c: ldc_w -3.734
      // 442f: ldc_w 5.517
      // 4432: ldc_w 4.151
      // 4435: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4438: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 443b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 443e: aastore
      // 443f: dup
      // 4440: bipush 50
      // 4442: new net/minecraft/client/animation/Keyframe
      // 4445: dup
      // 4446: ldc 2.08
      // 4448: ldc_w -4.611
      // 444b: ldc_w 7.057
      // 444e: ldc_w 4.348
      // 4451: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4454: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4457: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 445a: aastore
      // 445b: dup
      // 445c: bipush 51
      // 445e: new net/minecraft/client/animation/Keyframe
      // 4461: dup
      // 4462: ldc 2.12
      // 4464: ldc_w -6.214
      // 4467: ldc_w 9.632
      // 446a: ldc_w 4.454
      // 446d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4470: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4473: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4476: aastore
      // 4477: dup
      // 4478: bipush 52
      // 447a: new net/minecraft/client/animation/Keyframe
      // 447d: dup
      // 447e: ldc 2.17
      // 4480: ldc_w -7.074
      // 4483: ldc_w 10.991
      // 4486: ldc_w 4.485
      // 4489: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 448c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 448f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4492: aastore
      // 4493: dup
      // 4494: bipush 53
      // 4496: new net/minecraft/client/animation/Keyframe
      // 4499: dup
      // 449a: ldc 2.21
      // 449c: ldc_w -5.473
      // 449f: ldc_w 2.867
      // 44a2: ldc_w 2.899
      // 44a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 44a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44ae: aastore
      // 44af: dup
      // 44b0: bipush 54
      // 44b2: new net/minecraft/client/animation/Keyframe
      // 44b5: dup
      // 44b6: ldc 2.25
      // 44b8: ldc_w -3.871
      // 44bb: ldc_w -5.256
      // 44be: ldc_w 1.16
      // 44c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 44c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44ca: aastore
      // 44cb: dup
      // 44cc: bipush 55
      // 44ce: new net/minecraft/client/animation/Keyframe
      // 44d1: dup
      // 44d2: ldc 2.29
      // 44d4: ldc_w -4.456
      // 44d7: ldc_w -3.246
      // 44da: ldc_w 0.984
      // 44dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 44e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 44e6: aastore
      // 44e7: dup
      // 44e8: bipush 56
      // 44ea: new net/minecraft/client/animation/Keyframe
      // 44ed: dup
      // 44ee: ldc 2.33
      // 44f0: ldc_w -5.041
      // 44f3: ldc_w -1.236
      // 44f6: ldc_w 0.958
      // 44f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 44fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 44ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4502: aastore
      // 4503: dup
      // 4504: bipush 57
      // 4506: new net/minecraft/client/animation/Keyframe
      // 4509: dup
      // 450a: ldc 2.62
      // 450c: ldc_w -5.041
      // 450f: ldc_w -1.236
      // 4512: ldc_w 0.958
      // 4515: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4518: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 451b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 451e: aastore
      // 451f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4522: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4525: ldc_w "CTR_upperarm_left"
      // 4528: new net/minecraft/client/animation/AnimationChannel
      // 452b: dup
      // 452c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 452f: bipush 57
      // 4531: anewarray 24
      // 4534: dup
      // 4535: bipush 0
      // 4536: new net/minecraft/client/animation/Keyframe
      // 4539: dup
      // 453a: fconst_0
      // 453b: ldc_w -30.679
      // 453e: ldc_w -7.739
      // 4541: ldc_w -10.095
      // 4544: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4547: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 454a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 454d: aastore
      // 454e: dup
      // 454f: bipush 1
      // 4550: new net/minecraft/client/animation/Keyframe
      // 4553: dup
      // 4554: ldc 0.04
      // 4556: ldc_w -31.054
      // 4559: ldc_w -9.51
      // 455c: ldc_w -11.506
      // 455f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4562: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4565: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4568: aastore
      // 4569: dup
      // 456a: bipush 2
      // 456b: new net/minecraft/client/animation/Keyframe
      // 456e: dup
      // 456f: ldc 0.08
      // 4571: ldc_w -28.7
      // 4574: ldc_w -16.962
      // 4577: ldc_w -20.927
      // 457a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 457d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4580: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4583: aastore
      // 4584: dup
      // 4585: bipush 3
      // 4586: new net/minecraft/client/animation/Keyframe
      // 4589: dup
      // 458a: ldc 0.12
      // 458c: ldc_w -26.485
      // 458f: ldc_w -28.592
      // 4592: ldc_w -34.73
      // 4595: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4598: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 459b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 459e: aastore
      // 459f: dup
      // 45a0: bipush 4
      // 45a1: new net/minecraft/client/animation/Keyframe
      // 45a4: dup
      // 45a5: ldc 0.17
      // 45a7: ldc_w -17.973
      // 45aa: ldc_w -33.933
      // 45ad: ldc_w -53.638
      // 45b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45b9: aastore
      // 45ba: dup
      // 45bb: bipush 5
      // 45bc: new net/minecraft/client/animation/Keyframe
      // 45bf: dup
      // 45c0: ldc 0.21
      // 45c2: ldc_w 2.797
      // 45c5: ldc_w -39.12
      // 45c8: ldc_w -71.52
      // 45cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45d4: aastore
      // 45d5: dup
      // 45d6: bipush 6
      // 45d8: new net/minecraft/client/animation/Keyframe
      // 45db: dup
      // 45dc: ldc 0.25
      // 45de: ldc_w 31.041
      // 45e1: ldc_w -57.461
      // 45e4: ldc_w -82.126
      // 45e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 45ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 45ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 45f0: aastore
      // 45f1: dup
      // 45f2: bipush 7
      // 45f4: new net/minecraft/client/animation/Keyframe
      // 45f7: dup
      // 45f8: ldc 0.29
      // 45fa: ldc_w 55.258
      // 45fd: ldc_w -70.201
      // 4600: ldc_w -94.993
      // 4603: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4606: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4609: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 460c: aastore
      // 460d: dup
      // 460e: bipush 8
      // 4610: new net/minecraft/client/animation/Keyframe
      // 4613: dup
      // 4614: ldc 0.33
      // 4616: ldc_w 52.274
      // 4619: ldc_w -72.835
      // 461c: ldc_w -91.835
      // 461f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4622: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4625: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4628: aastore
      // 4629: dup
      // 462a: bipush 9
      // 462c: new net/minecraft/client/animation/Keyframe
      // 462f: dup
      // 4630: ldc 0.38
      // 4632: ldc_w 44.407
      // 4635: ldc_w -74.815
      // 4638: ldc_w -83.511
      // 463b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 463e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4641: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4644: aastore
      // 4645: dup
      // 4646: bipush 10
      // 4648: new net/minecraft/client/animation/Keyframe
      // 464b: dup
      // 464c: ldc 0.42
      // 464e: ldc_w 33.285
      // 4651: ldc_w -76.233
      // 4654: ldc_w -71.742
      // 4657: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 465a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 465d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4660: aastore
      // 4661: dup
      // 4662: bipush 11
      // 4664: new net/minecraft/client/animation/Keyframe
      // 4667: dup
      // 4668: ldc 0.46
      // 466a: ldc_w 20.535
      // 466d: ldc_w -77.184
      // 4670: ldc_w -58.251
      // 4673: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4676: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4679: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 467c: aastore
      // 467d: dup
      // 467e: bipush 12
      // 4680: new net/minecraft/client/animation/Keyframe
      // 4683: dup
      // 4684: ldc 0.5
      // 4686: ldc_w 7.785
      // 4689: ldc_w -77.761
      // 468c: ldc_w -44.759
      // 468f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4692: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4695: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4698: aastore
      // 4699: dup
      // 469a: bipush 13
      // 469c: new net/minecraft/client/animation/Keyframe
      // 469f: dup
      // 46a0: ldc 0.54
      // 46a2: ldc_w -3.337
      // 46a5: ldc_w -78.057
      // 46a8: ldc_w -32.99
      // 46ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46b4: aastore
      // 46b5: dup
      // 46b6: bipush 14
      // 46b8: new net/minecraft/client/animation/Keyframe
      // 46bb: dup
      // 46bc: ldc 0.58
      // 46be: ldc_w -11.204
      // 46c1: ldc_w -78.166
      // 46c4: ldc_w -24.666
      // 46c7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46ca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46cd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46d0: aastore
      // 46d1: dup
      // 46d2: bipush 15
      // 46d4: new net/minecraft/client/animation/Keyframe
      // 46d7: dup
      // 46d8: ldc 0.62
      // 46da: ldc_w -14.188
      // 46dd: ldc_w -78.182
      // 46e0: ldc_w -21.508
      // 46e3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 46e6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 46e9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 46ec: aastore
      // 46ed: dup
      // 46ee: bipush 16
      // 46f0: new net/minecraft/client/animation/Keyframe
      // 46f3: dup
      // 46f4: ldc 0.67
      // 46f6: ldc_w -11.646
      // 46f9: ldc_w -77.417
      // 46fc: ldc_w -22.847
      // 46ff: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4702: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4705: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4708: aastore
      // 4709: dup
      // 470a: bipush 17
      // 470c: new net/minecraft/client/animation/Keyframe
      // 470f: dup
      // 4710: ldc 0.71
      // 4712: ldc_w -5.584
      // 4715: ldc_w -75.592
      // 4718: ldc_w -26.038
      // 471b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 471e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4721: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4724: aastore
      // 4725: dup
      // 4726: bipush 18
      // 4728: new net/minecraft/client/animation/Keyframe
      // 472b: dup
      // 472c: ldc 0.75
      // 472e: ldc_w 1.651
      // 4731: ldc_w -73.415
      // 4734: ldc_w -29.847
      // 4737: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 473a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 473d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4740: aastore
      // 4741: dup
      // 4742: bipush 19
      // 4744: new net/minecraft/client/animation/Keyframe
      // 4747: dup
      // 4748: ldc 0.79
      // 474a: ldc_w 7.712
      // 474d: ldc_w -71.591
      // 4750: ldc_w -33.039
      // 4753: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4756: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4759: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 475c: aastore
      // 475d: dup
      // 475e: bipush 20
      // 4760: new net/minecraft/client/animation/Keyframe
      // 4763: dup
      // 4764: ldc 0.83
      // 4766: ldc_w 10.254
      // 4769: ldc_w -70.825
      // 476c: ldc_w -34.377
      // 476f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4772: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4775: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4778: aastore
      // 4779: dup
      // 477a: bipush 21
      // 477c: new net/minecraft/client/animation/Keyframe
      // 477f: dup
      // 4780: ldc 0.88
      // 4782: ldc_w 4.722
      // 4785: ldc_w -71.5
      // 4788: ldc_w -28.111
      // 478b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 478e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4791: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4794: aastore
      // 4795: dup
      // 4796: bipush 22
      // 4798: new net/minecraft/client/animation/Keyframe
      // 479b: dup
      // 479c: ldc 0.92
      // 479e: ldc_w -1.393
      // 47a1: ldc_w -72.246
      // 47a4: ldc_w -21.184
      // 47a7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47aa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47ad: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47b0: aastore
      // 47b1: dup
      // 47b2: bipush 23
      // 47b4: new net/minecraft/client/animation/Keyframe
      // 47b7: dup
      // 47b8: ldc 0.96
      // 47ba: ldc_w -3.96
      // 47bd: ldc_w -72.559
      // 47c0: ldc_w -18.276
      // 47c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47cc: aastore
      // 47cd: dup
      // 47ce: bipush 24
      // 47d0: new net/minecraft/client/animation/Keyframe
      // 47d3: dup
      // 47d4: fconst_1
      // 47d5: ldc_w -0.798
      // 47d8: ldc_w -72.173
      // 47db: ldc_w -21.858
      // 47de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 47e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 47e7: aastore
      // 47e8: dup
      // 47e9: bipush 25
      // 47eb: new net/minecraft/client/animation/Keyframe
      // 47ee: dup
      // 47ef: ldc 1.04
      // 47f1: ldc_w 5.766
      // 47f4: ldc_w -71.373
      // 47f7: ldc_w -29.293
      // 47fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 47fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4800: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4803: aastore
      // 4804: dup
      // 4805: bipush 26
      // 4807: new net/minecraft/client/animation/Keyframe
      // 480a: dup
      // 480b: ldc 1.08
      // 480d: ldc_w 10.254
      // 4810: ldc_w -70.825
      // 4813: ldc_w -34.377
      // 4816: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4819: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 481c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 481f: aastore
      // 4820: dup
      // 4821: bipush 27
      // 4823: new net/minecraft/client/animation/Keyframe
      // 4826: dup
      // 4827: ldc 1.12
      // 4829: ldc_w 7.687
      // 482c: ldc_w -70.985
      // 482f: ldc_w -31.895
      // 4832: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4835: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4838: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 483b: aastore
      // 483c: dup
      // 483d: bipush 28
      // 483f: new net/minecraft/client/animation/Keyframe
      // 4842: dup
      // 4843: ldc 1.17
      // 4845: ldc_w 3.874
      // 4848: ldc_w -71.221
      // 484b: ldc_w -28.21
      // 484e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4851: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4854: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4857: aastore
      // 4858: dup
      // 4859: bipush 29
      // 485b: new net/minecraft/client/animation/Keyframe
      // 485e: dup
      // 485f: ldc 1.21
      // 4861: ldc_w -0.255
      // 4864: ldc_w -71.478
      // 4867: ldc_w -24.218
      // 486a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 486d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4870: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4873: aastore
      // 4874: dup
      // 4875: bipush 30
      // 4877: new net/minecraft/client/animation/Keyframe
      // 487a: dup
      // 487b: ldc 1.25
      // 487d: ldc_w -4.495
      // 4880: ldc_w -71.741
      // 4883: ldc_w -20.12
      // 4886: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4889: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 488c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 488f: aastore
      // 4890: dup
      // 4891: bipush 31
      // 4893: new net/minecraft/client/animation/Keyframe
      // 4896: dup
      // 4897: ldc 1.29
      // 4899: ldc_w -8.717
      // 489c: ldc_w -72.003
      // 489f: ldc_w -16.039
      // 48a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48ab: aastore
      // 48ac: dup
      // 48ad: bipush 32
      // 48af: new net/minecraft/client/animation/Keyframe
      // 48b2: dup
      // 48b3: ldc 1.33
      // 48b5: ldc_w -12.744
      // 48b8: ldc_w -72.253
      // 48bb: ldc_w -12.146
      // 48be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48c7: aastore
      // 48c8: dup
      // 48c9: bipush 33
      // 48cb: new net/minecraft/client/animation/Keyframe
      // 48ce: dup
      // 48cf: ldc 1.38
      // 48d1: ldc_w -15.713
      // 48d4: ldc_w -72.437
      // 48d7: ldc_w -9.277
      // 48da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48e3: aastore
      // 48e4: dup
      // 48e5: bipush 34
      // 48e7: new net/minecraft/client/animation/Keyframe
      // 48ea: dup
      // 48eb: ldc 1.42
      // 48ed: ldc_w -20.929
      // 48f0: ldc_w -70.622
      // 48f3: ldc_w -10.524
      // 48f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 48f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 48fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 48ff: aastore
      // 4900: dup
      // 4901: bipush 35
      // 4903: new net/minecraft/client/animation/Keyframe
      // 4906: dup
      // 4907: ldc 1.46
      // 4909: ldc_w -28.163
      // 490c: ldc_w -68.103
      // 490f: ldc_w -12.254
      // 4912: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4915: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4918: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 491b: aastore
      // 491c: dup
      // 491d: bipush 36
      // 491f: new net/minecraft/client/animation/Keyframe
      // 4922: dup
      // 4923: ldc 1.5
      // 4925: ldc_w -35.657
      // 4928: ldc_w -65.495
      // 492b: ldc_w -14.046
      // 492e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4931: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4934: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4937: aastore
      // 4938: dup
      // 4939: bipush 37
      // 493b: new net/minecraft/client/animation/Keyframe
      // 493e: dup
      // 493f: ldc 1.54
      // 4941: ldc_w -43.063
      // 4944: ldc_w -62.917
      // 4947: ldc_w -15.817
      // 494a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 494d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4950: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4953: aastore
      // 4954: dup
      // 4955: bipush 38
      // 4957: new net/minecraft/client/animation/Keyframe
      // 495a: dup
      // 495b: ldc 1.58
      // 495d: ldc_w -50.202
      // 4960: ldc_w -60.432
      // 4963: ldc_w -17.524
      // 4966: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4969: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 496c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 496f: aastore
      // 4970: dup
      // 4971: bipush 39
      // 4973: new net/minecraft/client/animation/Keyframe
      // 4976: dup
      // 4977: ldc 1.62
      // 4979: ldc_w -56.937
      // 497c: ldc_w -58.088
      // 497f: ldc_w -19.134
      // 4982: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4985: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4988: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 498b: aastore
      // 498c: dup
      // 498d: bipush 40
      // 498f: new net/minecraft/client/animation/Keyframe
      // 4992: dup
      // 4993: ldc 1.67
      // 4995: ldc_w -63.126
      // 4998: ldc_w -55.933
      // 499b: ldc_w -20.614
      // 499e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49a7: aastore
      // 49a8: dup
      // 49a9: bipush 41
      // 49ab: new net/minecraft/client/animation/Keyframe
      // 49ae: dup
      // 49af: ldc_w 1.71
      // 49b2: ldc_w -68.599
      // 49b5: ldc_w -54.028
      // 49b8: ldc_w -21.923
      // 49bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49c4: aastore
      // 49c5: dup
      // 49c6: bipush 42
      // 49c8: new net/minecraft/client/animation/Keyframe
      // 49cb: dup
      // 49cc: ldc_w 1.75
      // 49cf: ldc_w -73.123
      // 49d2: ldc_w -52.453
      // 49d5: ldc_w -23.004
      // 49d8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49db: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49de: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49e1: aastore
      // 49e2: dup
      // 49e3: bipush 43
      // 49e5: new net/minecraft/client/animation/Keyframe
      // 49e8: dup
      // 49e9: ldc_w 1.79
      // 49ec: ldc_w -76.342
      // 49ef: ldc_w -51.333
      // 49f2: ldc_w -23.774
      // 49f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 49f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 49fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 49fe: aastore
      // 49ff: dup
      // 4a00: bipush 44
      // 4a02: new net/minecraft/client/animation/Keyframe
      // 4a05: dup
      // 4a06: ldc_w 1.83
      // 4a09: ldc_w -77.637
      // 4a0c: ldc_w -50.882
      // 4a0f: ldc_w -24.084
      // 4a12: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a15: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a18: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a1b: aastore
      // 4a1c: dup
      // 4a1d: bipush 45
      // 4a1f: new net/minecraft/client/animation/Keyframe
      // 4a22: dup
      // 4a23: ldc 2.17
      // 4a25: ldc_w -77.637
      // 4a28: ldc_w -50.882
      // 4a2b: ldc_w -24.084
      // 4a2e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a31: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a34: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a37: aastore
      // 4a38: dup
      // 4a39: bipush 46
      // 4a3b: new net/minecraft/client/animation/Keyframe
      // 4a3e: dup
      // 4a3f: ldc 2.21
      // 4a41: ldc_w -87.989
      // 4a44: ldc_w -20.256
      // 4a47: ldc_w -37.454
      // 4a4a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a4d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a50: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a53: aastore
      // 4a54: dup
      // 4a55: bipush 47
      // 4a57: new net/minecraft/client/animation/Keyframe
      // 4a5a: dup
      // 4a5b: ldc 2.25
      // 4a5d: ldc_w -90.991
      // 4a60: ldc_w -16.005
      // 4a63: ldc_w -51.744
      // 4a66: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a69: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a6c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a6f: aastore
      // 4a70: dup
      // 4a71: bipush 48
      // 4a73: new net/minecraft/client/animation/Keyframe
      // 4a76: dup
      // 4a77: ldc 2.29
      // 4a79: ldc_w -95.676
      // 4a7c: ldc_w -11.211
      // 4a7f: ldc_w -78.366
      // 4a82: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4a85: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4a88: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4a8b: aastore
      // 4a8c: dup
      // 4a8d: bipush 49
      // 4a8f: new net/minecraft/client/animation/Keyframe
      // 4a92: dup
      // 4a93: ldc 2.33
      // 4a95: ldc_w -97.39
      // 4a98: ldc_w -7.448
      // 4a9b: ldc_w -85.67
      // 4a9e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4aa1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4aa4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4aa7: aastore
      // 4aa8: dup
      // 4aa9: bipush 50
      // 4aab: new net/minecraft/client/animation/Keyframe
      // 4aae: dup
      // 4aaf: ldc 2.38
      // 4ab1: ldc_w -99.474
      // 4ab4: ldc_w -7.644
      // 4ab7: ldc_w -87.002
      // 4aba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4abd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ac0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ac3: aastore
      // 4ac4: dup
      // 4ac5: bipush 51
      // 4ac7: new net/minecraft/client/animation/Keyframe
      // 4aca: dup
      // 4acb: ldc 2.42
      // 4acd: ldc_w -98.502
      // 4ad0: ldc_w -4.785
      // 4ad3: ldc_w -88.558
      // 4ad6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ad9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4adc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4adf: aastore
      // 4ae0: dup
      // 4ae1: bipush 52
      // 4ae3: new net/minecraft/client/animation/Keyframe
      // 4ae6: dup
      // 4ae7: ldc 2.46
      // 4ae9: ldc_w -97.252
      // 4aec: ldc_w -6.153
      // 4aef: ldc_w -87.733
      // 4af2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4af5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4af8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4afb: aastore
      // 4afc: dup
      // 4afd: bipush 53
      // 4aff: new net/minecraft/client/animation/Keyframe
      // 4b02: dup
      // 4b03: ldc 2.5
      // 4b05: ldc_w -96.015
      // 4b08: ldc_w -6.295
      // 4b0b: ldc_w -86.906
      // 4b0e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b11: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b14: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b17: aastore
      // 4b18: dup
      // 4b19: bipush 54
      // 4b1b: new net/minecraft/client/animation/Keyframe
      // 4b1e: dup
      // 4b1f: ldc 2.54
      // 4b21: ldc_w -96.053
      // 4b24: ldc_w -6.991
      // 4b27: ldc_w -86.835
      // 4b2a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b2d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b30: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b33: aastore
      // 4b34: dup
      // 4b35: bipush 55
      // 4b37: new net/minecraft/client/animation/Keyframe
      // 4b3a: dup
      // 4b3b: ldc 2.58
      // 4b3d: ldc_w -96.569
      // 4b40: ldc_w -8.354
      // 4b43: ldc_w -86.724
      // 4b46: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b49: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b4c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b4f: aastore
      // 4b50: dup
      // 4b51: bipush 56
      // 4b53: new net/minecraft/client/animation/Keyframe
      // 4b56: dup
      // 4b57: ldc 2.62
      // 4b59: ldc_w -96.569
      // 4b5c: ldc_w -8.354
      // 4b5f: ldc_w -86.724
      // 4b62: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b6b: aastore
      // 4b6c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4b6f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4b72: ldc_w "CTR_lowerarm_left"
      // 4b75: new net/minecraft/client/animation/AnimationChannel
      // 4b78: dup
      // 4b79: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4b7c: bipush 32
      // 4b7e: anewarray 24
      // 4b81: dup
      // 4b82: bipush 0
      // 4b83: new net/minecraft/client/animation/Keyframe
      // 4b86: dup
      // 4b87: fconst_0
      // 4b88: ldc_w 11.775
      // 4b8b: ldc_w -32.546
      // 4b8e: ldc_w -2.854
      // 4b91: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4b94: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4b97: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4b9a: aastore
      // 4b9b: dup
      // 4b9c: bipush 1
      // 4b9d: new net/minecraft/client/animation/Keyframe
      // 4ba0: dup
      // 4ba1: ldc 0.04
      // 4ba3: ldc_w 13.885
      // 4ba6: ldc_w -41.266
      // 4ba9: ldc_w -5.021
      // 4bac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4baf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bb2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bb5: aastore
      // 4bb6: dup
      // 4bb7: bipush 2
      // 4bb8: new net/minecraft/client/animation/Keyframe
      // 4bbb: dup
      // 4bbc: ldc 0.08
      // 4bbe: ldc_w 8.244
      // 4bc1: ldc_w -46.409
      // 4bc4: ldc_w -3.597
      // 4bc7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bca: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bcd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bd0: aastore
      // 4bd1: dup
      // 4bd2: bipush 3
      // 4bd3: new net/minecraft/client/animation/Keyframe
      // 4bd6: dup
      // 4bd7: ldc 0.12
      // 4bd9: fconst_0
      // 4bda: ldc_w -36.052
      // 4bdd: fconst_0
      // 4bde: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4be1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4be4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4be7: aastore
      // 4be8: dup
      // 4be9: bipush 4
      // 4bea: new net/minecraft/client/animation/Keyframe
      // 4bed: dup
      // 4bee: ldc 0.17
      // 4bf0: fconst_0
      // 4bf1: ldc_w -12.882
      // 4bf4: fconst_0
      // 4bf5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4bf8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4bfb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4bfe: aastore
      // 4bff: dup
      // 4c00: bipush 5
      // 4c01: new net/minecraft/client/animation/Keyframe
      // 4c04: dup
      // 4c05: ldc 0.21
      // 4c07: fconst_0
      // 4c08: ldc_w -6.649
      // 4c0b: fconst_0
      // 4c0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c15: aastore
      // 4c16: dup
      // 4c17: bipush 6
      // 4c19: new net/minecraft/client/animation/Keyframe
      // 4c1c: dup
      // 4c1d: ldc 0.25
      // 4c1f: fconst_0
      // 4c20: ldc_w -24.417
      // 4c23: fconst_0
      // 4c24: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c27: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c2a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c2d: aastore
      // 4c2e: dup
      // 4c2f: bipush 7
      // 4c31: new net/minecraft/client/animation/Keyframe
      // 4c34: dup
      // 4c35: ldc 0.29
      // 4c37: fconst_0
      // 4c38: ldc_w -39.0
      // 4c3b: fconst_0
      // 4c3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c45: aastore
      // 4c46: dup
      // 4c47: bipush 8
      // 4c49: new net/minecraft/client/animation/Keyframe
      // 4c4c: dup
      // 4c4d: ldc 0.33
      // 4c4f: fconst_0
      // 4c50: ldc_w -44.55
      // 4c53: fconst_0
      // 4c54: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c57: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c5a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c5d: aastore
      // 4c5e: dup
      // 4c5f: bipush 9
      // 4c61: new net/minecraft/client/animation/Keyframe
      // 4c64: dup
      // 4c65: ldc 0.38
      // 4c67: fconst_0
      // 4c68: ldc_w -49.249
      // 4c6b: fconst_0
      // 4c6c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c6f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c72: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c75: aastore
      // 4c76: dup
      // 4c77: bipush 10
      // 4c79: new net/minecraft/client/animation/Keyframe
      // 4c7c: dup
      // 4c7d: ldc 0.42
      // 4c7f: fconst_0
      // 4c80: ldc_w -53.166
      // 4c83: fconst_0
      // 4c84: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4c8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4c8d: aastore
      // 4c8e: dup
      // 4c8f: bipush 11
      // 4c91: new net/minecraft/client/animation/Keyframe
      // 4c94: dup
      // 4c95: ldc 0.46
      // 4c97: fconst_0
      // 4c98: ldc_w -56.373
      // 4c9b: fconst_0
      // 4c9c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4c9f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ca2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ca5: aastore
      // 4ca6: dup
      // 4ca7: bipush 12
      // 4ca9: new net/minecraft/client/animation/Keyframe
      // 4cac: dup
      // 4cad: ldc 0.5
      // 4caf: fconst_0
      // 4cb0: ldc_w -58.941
      // 4cb3: fconst_0
      // 4cb4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4cb7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cbd: aastore
      // 4cbe: dup
      // 4cbf: bipush 13
      // 4cc1: new net/minecraft/client/animation/Keyframe
      // 4cc4: dup
      // 4cc5: ldc 0.54
      // 4cc7: fconst_0
      // 4cc8: ldc_w -60.941
      // 4ccb: fconst_0
      // 4ccc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ccf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cd2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4cd5: aastore
      // 4cd6: dup
      // 4cd7: bipush 14
      // 4cd9: new net/minecraft/client/animation/Keyframe
      // 4cdc: dup
      // 4cdd: ldc 0.58
      // 4cdf: fconst_0
      // 4ce0: ldc_w -62.444
      // 4ce3: fconst_0
      // 4ce4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4ce7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4cea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ced: aastore
      // 4cee: dup
      // 4cef: bipush 15
      // 4cf1: new net/minecraft/client/animation/Keyframe
      // 4cf4: dup
      // 4cf5: ldc 0.62
      // 4cf7: fconst_0
      // 4cf8: ldc_w -63.521
      // 4cfb: fconst_0
      // 4cfc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4cff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d05: aastore
      // 4d06: dup
      // 4d07: bipush 16
      // 4d09: new net/minecraft/client/animation/Keyframe
      // 4d0c: dup
      // 4d0d: ldc 0.67
      // 4d0f: fconst_0
      // 4d10: ldc_w -64.243
      // 4d13: fconst_0
      // 4d14: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d17: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d1a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d1d: aastore
      // 4d1e: dup
      // 4d1f: bipush 17
      // 4d21: new net/minecraft/client/animation/Keyframe
      // 4d24: dup
      // 4d25: ldc 0.71
      // 4d27: fconst_0
      // 4d28: ldc_w -64.68
      // 4d2b: fconst_0
      // 4d2c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d2f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d32: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d35: aastore
      // 4d36: dup
      // 4d37: bipush 18
      // 4d39: new net/minecraft/client/animation/Keyframe
      // 4d3c: dup
      // 4d3d: ldc 0.75
      // 4d3f: fconst_0
      // 4d40: ldc_w -64.905
      // 4d43: fconst_0
      // 4d44: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d47: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d4a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d4d: aastore
      // 4d4e: dup
      // 4d4f: bipush 19
      // 4d51: new net/minecraft/client/animation/Keyframe
      // 4d54: dup
      // 4d55: ldc 0.79
      // 4d57: fconst_0
      // 4d58: ldc_w -64.988
      // 4d5b: fconst_0
      // 4d5c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d5f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d62: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d65: aastore
      // 4d66: dup
      // 4d67: bipush 20
      // 4d69: new net/minecraft/client/animation/Keyframe
      // 4d6c: dup
      // 4d6d: ldc 0.83
      // 4d6f: fconst_0
      // 4d70: ldc_w -65.0
      // 4d73: fconst_0
      // 4d74: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d77: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d7a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d7d: aastore
      // 4d7e: dup
      // 4d7f: bipush 21
      // 4d81: new net/minecraft/client/animation/Keyframe
      // 4d84: dup
      // 4d85: ldc 2.21
      // 4d87: fconst_0
      // 4d88: ldc_w -65.0
      // 4d8b: fconst_0
      // 4d8c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4d8f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4d92: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4d95: aastore
      // 4d96: dup
      // 4d97: bipush 22
      // 4d99: new net/minecraft/client/animation/Keyframe
      // 4d9c: dup
      // 4d9d: ldc 2.25
      // 4d9f: fconst_0
      // 4da0: ldc_w -53.724
      // 4da3: fconst_0
      // 4da4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4da7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4daa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4dad: aastore
      // 4dae: dup
      // 4daf: bipush 23
      // 4db1: new net/minecraft/client/animation/Keyframe
      // 4db4: dup
      // 4db5: ldc 2.29
      // 4db7: fconst_0
      // 4db8: ldc_w -34.736
      // 4dbb: fconst_0
      // 4dbc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4dbf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4dc2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4dc5: aastore
      // 4dc6: dup
      // 4dc7: bipush 24
      // 4dc9: new net/minecraft/client/animation/Keyframe
      // 4dcc: dup
      // 4dcd: ldc 2.33
      // 4dcf: fconst_0
      // 4dd0: ldc_w -28.323
      // 4dd3: fconst_0
      // 4dd4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4dd7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4dda: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ddd: aastore
      // 4dde: dup
      // 4ddf: bipush 25
      // 4de1: new net/minecraft/client/animation/Keyframe
      // 4de4: dup
      // 4de5: ldc 2.38
      // 4de7: fconst_0
      // 4de8: ldc_w -27.243
      // 4deb: fconst_0
      // 4dec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4def: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4df2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4df5: aastore
      // 4df6: dup
      // 4df7: bipush 26
      // 4df9: new net/minecraft/client/animation/Keyframe
      // 4dfc: dup
      // 4dfd: ldc 2.42
      // 4dff: fconst_0
      // 4e00: ldc_w -31.375
      // 4e03: fconst_0
      // 4e04: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e07: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e0a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e0d: aastore
      // 4e0e: dup
      // 4e0f: bipush 27
      // 4e11: new net/minecraft/client/animation/Keyframe
      // 4e14: dup
      // 4e15: ldc 2.46
      // 4e17: fconst_0
      // 4e18: ldc_w -30.063
      // 4e1b: fconst_0
      // 4e1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e25: aastore
      // 4e26: dup
      // 4e27: bipush 28
      // 4e29: new net/minecraft/client/animation/Keyframe
      // 4e2c: dup
      // 4e2d: ldc 2.5
      // 4e2f: fconst_0
      // 4e30: ldc_w -30.229
      // 4e33: fconst_0
      // 4e34: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e37: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e3a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e3d: aastore
      // 4e3e: dup
      // 4e3f: bipush 29
      // 4e41: new net/minecraft/client/animation/Keyframe
      // 4e44: dup
      // 4e45: ldc 2.54
      // 4e47: fconst_0
      // 4e48: ldc_w -29.315
      // 4e4b: fconst_0
      // 4e4c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e4f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e52: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e55: aastore
      // 4e56: dup
      // 4e57: bipush 30
      // 4e59: new net/minecraft/client/animation/Keyframe
      // 4e5c: dup
      // 4e5d: ldc 2.58
      // 4e5f: fconst_0
      // 4e60: ldc_w -27.387
      // 4e63: fconst_0
      // 4e64: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e67: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e6a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e6d: aastore
      // 4e6e: dup
      // 4e6f: bipush 31
      // 4e71: new net/minecraft/client/animation/Keyframe
      // 4e74: dup
      // 4e75: ldc 2.62
      // 4e77: fconst_0
      // 4e78: ldc_w -27.387
      // 4e7b: fconst_0
      // 4e7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 4e7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4e82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4e85: aastore
      // 4e86: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 4e89: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 4e8c: ldc_w "CTR_shoulder_right"
      // 4e8f: new net/minecraft/client/animation/AnimationChannel
      // 4e92: dup
      // 4e93: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 4e96: bipush 57
      // 4e98: anewarray 24
      // 4e9b: dup
      // 4e9c: bipush 0
      // 4e9d: new net/minecraft/client/animation/Keyframe
      // 4ea0: dup
      // 4ea1: fconst_0
      // 4ea2: ldc_w 2.345
      // 4ea5: ldc_w 2.784
      // 4ea8: ldc_w 6.121
      // 4eab: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4eae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4eb1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4eb4: aastore
      // 4eb5: dup
      // 4eb6: bipush 1
      // 4eb7: new net/minecraft/client/animation/Keyframe
      // 4eba: dup
      // 4ebb: ldc 0.04
      // 4ebd: ldc_w 1.638
      // 4ec0: ldc_w 2.718
      // 4ec3: ldc_w 4.036
      // 4ec6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4ec9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ecc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4ecf: aastore
      // 4ed0: dup
      // 4ed1: bipush 2
      // 4ed2: new net/minecraft/client/animation/Keyframe
      // 4ed5: dup
      // 4ed6: ldc 0.08
      // 4ed8: ldc_w 0.324
      // 4edb: ldc_w 2.254
      // 4ede: ldc_w -1.123
      // 4ee1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4ee4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ee7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4eea: aastore
      // 4eeb: dup
      // 4eec: bipush 3
      // 4eed: new net/minecraft/client/animation/Keyframe
      // 4ef0: dup
      // 4ef1: ldc 0.12
      // 4ef3: ldc_w -0.383
      // 4ef6: ldc_w 0.997
      // 4ef9: ldc_w -7.714
      // 4efc: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4eff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f05: aastore
      // 4f06: dup
      // 4f07: bipush 4
      // 4f08: new net/minecraft/client/animation/Keyframe
      // 4f0b: dup
      // 4f0c: ldc 0.17
      // 4f0e: ldc_w 0.272
      // 4f11: ldc_w -3.506
      // 4f14: ldc_w -13.84
      // 4f17: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4f1a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f1d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f20: aastore
      // 4f21: dup
      // 4f22: bipush 5
      // 4f23: new net/minecraft/client/animation/Keyframe
      // 4f26: dup
      // 4f27: ldc 0.21
      // 4f29: ldc_w 1.63
      // 4f2c: ldc_w -7.116
      // 4f2f: ldc_w -16.586
      // 4f32: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4f35: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f38: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f3b: aastore
      // 4f3c: dup
      // 4f3d: bipush 6
      // 4f3f: new net/minecraft/client/animation/Keyframe
      // 4f42: dup
      // 4f43: ldc 0.25
      // 4f45: ldc_w 2.849
      // 4f48: ldc_w -4.526
      // 4f4b: ldc_w -15.538
      // 4f4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4f51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f57: aastore
      // 4f58: dup
      // 4f59: bipush 7
      // 4f5b: new net/minecraft/client/animation/Keyframe
      // 4f5e: dup
      // 4f5f: ldc 0.29
      // 4f61: ldc_w 3.365
      // 4f64: ldc_w 0.206
      // 4f67: ldc_w -12.996
      // 4f6a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4f6d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f70: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f73: aastore
      // 4f74: dup
      // 4f75: bipush 8
      // 4f77: new net/minecraft/client/animation/Keyframe
      // 4f7a: dup
      // 4f7b: ldc 0.33
      // 4f7d: ldc_w 3.274
      // 4f80: ldc_w 3.42
      // 4f83: ldc_w -9.888
      // 4f86: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4f89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4f8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4f8f: aastore
      // 4f90: dup
      // 4f91: bipush 9
      // 4f93: new net/minecraft/client/animation/Keyframe
      // 4f96: dup
      // 4f97: ldc 0.38
      // 4f99: ldc_w 2.982
      // 4f9c: ldc_w 5.056
      // 4f9f: ldc_w -7.248
      // 4fa2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4fa5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fa8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fab: aastore
      // 4fac: dup
      // 4fad: bipush 10
      // 4faf: new net/minecraft/client/animation/Keyframe
      // 4fb2: dup
      // 4fb3: ldc 0.42
      // 4fb5: ldc_w 2.458
      // 4fb8: ldc_w 5.95
      // 4fbb: ldc_w -6.133
      // 4fbe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4fc1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fc4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fc7: aastore
      // 4fc8: dup
      // 4fc9: bipush 11
      // 4fcb: new net/minecraft/client/animation/Keyframe
      // 4fce: dup
      // 4fcf: ldc 0.46
      // 4fd1: ldc_w 1.711
      // 4fd4: ldc_w 6.779
      // 4fd7: ldc_w -7.577
      // 4fda: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4fdd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4fe0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fe3: aastore
      // 4fe4: dup
      // 4fe5: bipush 12
      // 4fe7: new net/minecraft/client/animation/Keyframe
      // 4fea: dup
      // 4feb: ldc 0.5
      // 4fed: ldc_w 0.913
      // 4ff0: ldc_w 7.583
      // 4ff3: ldc_w -10.753
      // 4ff6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 4ff9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 4ffc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 4fff: aastore
      // 5000: dup
      // 5001: bipush 13
      // 5003: new net/minecraft/client/animation/Keyframe
      // 5006: dup
      // 5007: ldc 0.54
      // 5009: ldc_w 0.279
      // 500c: ldc_w 8.238
      // 500f: ldc_w -13.93
      // 5012: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5015: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5018: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 501b: aastore
      // 501c: dup
      // 501d: bipush 14
      // 501f: new net/minecraft/client/animation/Keyframe
      // 5022: dup
      // 5023: ldc 0.58
      // 5025: ldc_w 0.02
      // 5028: ldc_w 8.623
      // 502b: ldc_w -15.374
      // 502e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5031: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5034: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5037: aastore
      // 5038: dup
      // 5039: bipush 15
      // 503b: new net/minecraft/client/animation/Keyframe
      // 503e: dup
      // 503f: ldc 0.62
      // 5041: ldc_w 0.032
      // 5044: ldc_w 8.808
      // 5047: ldc_w -15.096
      // 504a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 504d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5050: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5053: aastore
      // 5054: dup
      // 5055: bipush 16
      // 5057: new net/minecraft/client/animation/Keyframe
      // 505a: dup
      // 505b: ldc 0.67
      // 505d: ldc_w 0.067
      // 5060: ldc_w 8.946
      // 5063: ldc_w -14.363
      // 5066: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5069: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 506c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 506f: aastore
      // 5070: dup
      // 5071: bipush 17
      // 5073: new net/minecraft/client/animation/Keyframe
      // 5076: dup
      // 5077: ldc 0.71
      // 5079: ldc_w 0.123
      // 507c: ldc_w 9.046
      // 507f: ldc_w -13.326
      // 5082: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5085: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5088: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 508b: aastore
      // 508c: dup
      // 508d: bipush 18
      // 508f: new net/minecraft/client/animation/Keyframe
      // 5092: dup
      // 5093: ldc 0.75
      // 5095: ldc_w 0.197
      // 5098: ldc_w 9.113
      // 509b: ldc_w -12.138
      // 509e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 50a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50a7: aastore
      // 50a8: dup
      // 50a9: bipush 19
      // 50ab: new net/minecraft/client/animation/Keyframe
      // 50ae: dup
      // 50af: ldc 0.79
      // 50b1: ldc_w 0.288
      // 50b4: ldc_w 9.153
      // 50b7: ldc_w -10.95
      // 50ba: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 50bd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50c0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50c3: aastore
      // 50c4: dup
      // 50c5: bipush 20
      // 50c7: new net/minecraft/client/animation/Keyframe
      // 50ca: dup
      // 50cb: ldc 0.83
      // 50cd: ldc_w 0.393
      // 50d0: ldc_w 9.174
      // 50d3: ldc_w -9.914
      // 50d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 50d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50df: aastore
      // 50e0: dup
      // 50e1: bipush 21
      // 50e3: new net/minecraft/client/animation/Keyframe
      // 50e6: dup
      // 50e7: ldc 0.88
      // 50e9: ldc_w 0.511
      // 50ec: ldc_w 9.181
      // 50ef: ldc_w -9.181
      // 50f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 50f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 50f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 50fb: aastore
      // 50fc: dup
      // 50fd: bipush 22
      // 50ff: new net/minecraft/client/animation/Keyframe
      // 5102: dup
      // 5103: ldc 0.92
      // 5105: ldc_w 0.64
      // 5108: ldc_w 9.182
      // 510b: ldc_w -8.903
      // 510e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5111: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5114: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5117: aastore
      // 5118: dup
      // 5119: bipush 23
      // 511b: new net/minecraft/client/animation/Keyframe
      // 511e: dup
      // 511f: ldc 0.96
      // 5121: ldc_w 0.775
      // 5124: ldc_w 8.911
      // 5127: ldc_w -9.195
      // 512a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 512d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5130: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5133: aastore
      // 5134: dup
      // 5135: bipush 24
      // 5137: new net/minecraft/client/animation/Keyframe
      // 513a: dup
      // 513b: fconst_1
      // 513c: ldc_w 0.909
      // 513f: ldc_w 8.234
      // 5142: ldc_w -9.925
      // 5145: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5148: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 514b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 514e: aastore
      // 514f: dup
      // 5150: bipush 25
      // 5152: new net/minecraft/client/animation/Keyframe
      // 5155: dup
      // 5156: ldc 1.04
      // 5158: ldc_w 1.031
      // 515b: ldc_w 7.353
      // 515e: ldc_w -10.875
      // 5161: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5164: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5167: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 516a: aastore
      // 516b: dup
      // 516c: bipush 26
      // 516e: new net/minecraft/client/animation/Keyframe
      // 5171: dup
      // 5172: ldc 1.08
      // 5174: ldc_w 1.13
      // 5177: ldc_w 6.473
      // 517a: ldc_w -11.825
      // 517d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5180: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5183: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5186: aastore
      // 5187: dup
      // 5188: bipush 27
      // 518a: new net/minecraft/client/animation/Keyframe
      // 518d: dup
      // 518e: ldc 1.12
      // 5190: ldc_w 1.198
      // 5193: ldc_w 5.795
      // 5196: ldc_w -12.555
      // 5199: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 519c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 519f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51a2: aastore
      // 51a3: dup
      // 51a4: bipush 28
      // 51a6: new net/minecraft/client/animation/Keyframe
      // 51a9: dup
      // 51aa: ldc 1.17
      // 51ac: ldc_w 1.222
      // 51af: ldc_w 5.524
      // 51b2: ldc_w -12.848
      // 51b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 51b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51be: aastore
      // 51bf: dup
      // 51c0: bipush 29
      // 51c2: new net/minecraft/client/animation/Keyframe
      // 51c5: dup
      // 51c6: ldc 1.21
      // 51c8: ldc_w 1.199
      // 51cb: ldc_w 5.524
      // 51ce: ldc_w -12.727
      // 51d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 51d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51da: aastore
      // 51db: dup
      // 51dc: bipush 30
      // 51de: new net/minecraft/client/animation/Keyframe
      // 51e1: dup
      // 51e2: ldc 1.25
      // 51e4: ldc_w 1.133
      // 51e7: ldc_w 5.525
      // 51ea: ldc_w -12.385
      // 51ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 51f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 51f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 51f6: aastore
      // 51f7: dup
      // 51f8: bipush 31
      // 51fa: new net/minecraft/client/animation/Keyframe
      // 51fd: dup
      // 51fe: ldc 1.29
      // 5200: ldc_w 1.03
      // 5203: ldc_w 5.528
      // 5206: ldc_w -11.852
      // 5209: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 520c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 520f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5212: aastore
      // 5213: dup
      // 5214: bipush 32
      // 5216: new net/minecraft/client/animation/Keyframe
      // 5219: dup
      // 521a: ldc 1.33
      // 521c: ldc_w 0.896
      // 521f: ldc_w 5.532
      // 5222: ldc_w -11.156
      // 5225: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5228: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 522b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 522e: aastore
      // 522f: dup
      // 5230: bipush 33
      // 5232: new net/minecraft/client/animation/Keyframe
      // 5235: dup
      // 5236: ldc 1.38
      // 5238: ldc_w 0.736
      // 523b: ldc_w 5.54
      // 523e: ldc_w -10.328
      // 5241: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5244: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5247: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 524a: aastore
      // 524b: dup
      // 524c: bipush 34
      // 524e: new net/minecraft/client/animation/Keyframe
      // 5251: dup
      // 5252: ldc 1.42
      // 5254: ldc_w 0.556
      // 5257: ldc_w 5.552
      // 525a: ldc_w -9.396
      // 525d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5260: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5263: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5266: aastore
      // 5267: dup
      // 5268: bipush 35
      // 526a: new net/minecraft/client/animation/Keyframe
      // 526d: dup
      // 526e: ldc 1.46
      // 5270: ldc_w 0.362
      // 5273: ldc_w 5.568
      // 5276: ldc_w -8.391
      // 5279: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 527c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 527f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5282: aastore
      // 5283: dup
      // 5284: bipush 36
      // 5286: new net/minecraft/client/animation/Keyframe
      // 5289: dup
      // 528a: ldc 1.5
      // 528c: ldc_w 0.16
      // 528f: ldc_w 5.589
      // 5292: ldc_w -7.341
      // 5295: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5298: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 529b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 529e: aastore
      // 529f: dup
      // 52a0: bipush 37
      // 52a2: new net/minecraft/client/animation/Keyframe
      // 52a5: dup
      // 52a6: ldc 1.54
      // 52a8: ldc_w -0.046
      // 52ab: ldc_w 5.617
      // 52ae: ldc_w -6.277
      // 52b1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 52b4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52b7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52ba: aastore
      // 52bb: dup
      // 52bc: bipush 38
      // 52be: new net/minecraft/client/animation/Keyframe
      // 52c1: dup
      // 52c2: ldc 1.58
      // 52c4: ldc_w -0.248
      // 52c7: ldc_w 5.652
      // 52ca: ldc_w -5.227
      // 52cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 52d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52d6: aastore
      // 52d7: dup
      // 52d8: bipush 39
      // 52da: new net/minecraft/client/animation/Keyframe
      // 52dd: dup
      // 52de: ldc 1.62
      // 52e0: ldc_w -0.442
      // 52e3: ldc_w 5.694
      // 52e6: ldc_w -4.221
      // 52e9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 52ec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 52ef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 52f2: aastore
      // 52f3: dup
      // 52f4: bipush 40
      // 52f6: new net/minecraft/client/animation/Keyframe
      // 52f9: dup
      // 52fa: ldc 1.67
      // 52fc: ldc_w -0.622
      // 52ff: ldc_w 5.745
      // 5302: ldc_w -3.29
      // 5305: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5308: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 530b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 530e: aastore
      // 530f: dup
      // 5310: bipush 41
      // 5312: new net/minecraft/client/animation/Keyframe
      // 5315: dup
      // 5316: ldc_w 1.71
      // 5319: ldc_w -0.782
      // 531c: ldc_w 5.804
      // 531f: ldc_w -2.461
      // 5322: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5325: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5328: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 532b: aastore
      // 532c: dup
      // 532d: bipush 42
      // 532f: new net/minecraft/client/animation/Keyframe
      // 5332: dup
      // 5333: ldc_w 1.75
      // 5336: ldc_w -0.916
      // 5339: ldc_w 5.874
      // 533c: ldc_w -1.766
      // 533f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5342: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5345: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5348: aastore
      // 5349: dup
      // 534a: bipush 43
      // 534c: new net/minecraft/client/animation/Keyframe
      // 534f: dup
      // 5350: ldc_w 1.79
      // 5353: ldc_w -1.019
      // 5356: ldc_w 5.955
      // 5359: ldc_w -1.232
      // 535c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 535f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5362: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5365: aastore
      // 5366: dup
      // 5367: bipush 44
      // 5369: new net/minecraft/client/animation/Keyframe
      // 536c: dup
      // 536d: ldc_w 1.83
      // 5370: ldc_w -1.085
      // 5373: ldc_w 6.046
      // 5376: ldc_w -0.891
      // 5379: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 537c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 537f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5382: aastore
      // 5383: dup
      // 5384: bipush 45
      // 5386: new net/minecraft/client/animation/Keyframe
      // 5389: dup
      // 538a: ldc_w 1.88
      // 538d: ldc_w -1.108
      // 5390: ldc_w 6.151
      // 5393: ldc_w -0.77
      // 5396: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5399: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 539c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 539f: aastore
      // 53a0: dup
      // 53a1: bipush 46
      // 53a3: new net/minecraft/client/animation/Keyframe
      // 53a6: dup
      // 53a7: ldc_w 1.92
      // 53aa: ldc_w -0.645
      // 53ad: ldc_w 6.445
      // 53b0: ldc_w -1.015
      // 53b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 53b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53bc: aastore
      // 53bd: dup
      // 53be: bipush 47
      // 53c0: new net/minecraft/client/animation/Keyframe
      // 53c3: dup
      // 53c4: ldc_w 1.96
      // 53c7: ldc_w 0.549
      // 53ca: ldc_w 7.027
      // 53cd: ldc_w -1.647
      // 53d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 53d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53d9: aastore
      // 53da: dup
      // 53db: bipush 48
      // 53dd: new net/minecraft/client/animation/Keyframe
      // 53e0: dup
      // 53e1: fconst_2
      // 53e2: ldc_w 2.181
      // 53e5: ldc_w 7.774
      // 53e8: ldc_w -2.512
      // 53eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 53ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 53f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 53f4: aastore
      // 53f5: dup
      // 53f6: bipush 49
      // 53f8: new net/minecraft/client/animation/Keyframe
      // 53fb: dup
      // 53fc: ldc 2.04
      // 53fe: ldc_w 3.96
      // 5401: ldc_w 8.566
      // 5404: ldc_w -3.454
      // 5407: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 540a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 540d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5410: aastore
      // 5411: dup
      // 5412: bipush 50
      // 5414: new net/minecraft/client/animation/Keyframe
      // 5417: dup
      // 5418: ldc 2.08
      // 541a: ldc_w 5.593
      // 541d: ldc_w 9.282
      // 5420: ldc_w -4.318
      // 5423: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5426: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5429: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 542c: aastore
      // 542d: dup
      // 542e: bipush 51
      // 5430: new net/minecraft/client/animation/Keyframe
      // 5433: dup
      // 5434: ldc 2.12
      // 5436: ldc_w 6.787
      // 5439: ldc_w 9.801
      // 543c: ldc_w -4.95
      // 543f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5442: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5445: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5448: aastore
      // 5449: dup
      // 544a: bipush 52
      // 544c: new net/minecraft/client/animation/Keyframe
      // 544f: dup
      // 5450: ldc 2.17
      // 5452: ldc_w 7.25
      // 5455: ldc_w 10.0
      // 5458: ldc_w -5.195
      // 545b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 545e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5461: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5464: aastore
      // 5465: dup
      // 5466: bipush 53
      // 5468: new net/minecraft/client/animation/Keyframe
      // 546b: dup
      // 546c: ldc 2.21
      // 546e: ldc_w 5.778
      // 5471: ldc_w -6.821
      // 5474: ldc_w -4.131
      // 5477: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 547a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 547d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5480: aastore
      // 5481: dup
      // 5482: bipush 54
      // 5484: new net/minecraft/client/animation/Keyframe
      // 5487: dup
      // 5488: ldc 2.25
      // 548a: ldc_w 5.821
      // 548d: ldc_w -5.032
      // 5490: ldc_w -3.943
      // 5493: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 5496: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5499: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 549c: aastore
      // 549d: dup
      // 549e: bipush 55
      // 54a0: new net/minecraft/client/animation/Keyframe
      // 54a3: dup
      // 54a4: ldc 2.29
      // 54a6: ldc_w 5.864
      // 54a9: ldc_w -3.243
      // 54ac: ldc_w -3.916
      // 54af: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 54b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54b8: aastore
      // 54b9: dup
      // 54ba: bipush 56
      // 54bc: new net/minecraft/client/animation/Keyframe
      // 54bf: dup
      // 54c0: ldc 2.62
      // 54c2: ldc_w 5.864
      // 54c5: ldc_w -3.243
      // 54c8: ldc_w -3.916
      // 54cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 54ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 54d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 54d4: aastore
      // 54d5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 54d8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 54db: ldc_w "CTR_upperarm_right"
      // 54de: new net/minecraft/client/animation/AnimationChannel
      // 54e1: dup
      // 54e2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 54e5: bipush 63
      // 54e7: anewarray 24
      // 54ea: dup
      // 54eb: bipush 0
      // 54ec: new net/minecraft/client/animation/Keyframe
      // 54ef: dup
      // 54f0: fconst_0
      // 54f1: ldc_w -5.313
      // 54f4: ldc_w 1.904
      // 54f7: ldc_w 14.61
      // 54fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 54fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5500: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5503: aastore
      // 5504: dup
      // 5505: bipush 1
      // 5506: new net/minecraft/client/animation/Keyframe
      // 5509: dup
      // 550a: ldc 0.04
      // 550c: ldc_w -5.283
      // 550f: ldc_w 3.052
      // 5512: ldc_w 17.885
      // 5515: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5518: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 551b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 551e: aastore
      // 551f: dup
      // 5520: bipush 2
      // 5521: new net/minecraft/client/animation/Keyframe
      // 5524: dup
      // 5525: ldc 0.08
      // 5527: ldc_w -4.51
      // 552a: ldc_w 7.988
      // 552d: ldc_w 30.35
      // 5530: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5533: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5536: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5539: aastore
      // 553a: dup
      // 553b: bipush 3
      // 553c: new net/minecraft/client/animation/Keyframe
      // 553f: dup
      // 5540: ldc 0.12
      // 5542: ldc_w -3.194
      // 5545: ldc_w 17.769
      // 5548: ldc_w 52.647
      // 554b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 554e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5551: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5554: aastore
      // 5555: dup
      // 5556: bipush 4
      // 5557: new net/minecraft/client/animation/Keyframe
      // 555a: dup
      // 555b: ldc 0.17
      // 555d: ldc_w -4.027
      // 5560: ldc_w 32.962
      // 5563: ldc_w 59.682
      // 5566: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5569: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 556c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 556f: aastore
      // 5570: dup
      // 5571: bipush 5
      // 5572: new net/minecraft/client/animation/Keyframe
      // 5575: dup
      // 5576: ldc 0.21
      // 5578: ldc_w -27.99
      // 557b: ldc_w 41.238
      // 557e: ldc_w 16.376
      // 5581: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5584: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5587: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 558a: aastore
      // 558b: dup
      // 558c: bipush 6
      // 558e: new net/minecraft/client/animation/Keyframe
      // 5591: dup
      // 5592: ldc 0.25
      // 5594: ldc_w -45.103
      // 5597: ldc_w 36.48
      // 559a: ldc_w -27.532
      // 559d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55a6: aastore
      // 55a7: dup
      // 55a8: bipush 7
      // 55aa: new net/minecraft/client/animation/Keyframe
      // 55ad: dup
      // 55ae: ldc 0.29
      // 55b0: ldc_w -47.422
      // 55b3: ldc_w 36.567
      // 55b6: ldc_w -49.43
      // 55b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55c2: aastore
      // 55c3: dup
      // 55c4: bipush 8
      // 55c6: new net/minecraft/client/animation/Keyframe
      // 55c9: dup
      // 55ca: ldc 0.33
      // 55cc: ldc_w -43.317
      // 55cf: ldc_w 40.459
      // 55d2: ldc_w -53.969
      // 55d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55de: aastore
      // 55df: dup
      // 55e0: bipush 9
      // 55e2: new net/minecraft/client/animation/Keyframe
      // 55e5: dup
      // 55e6: ldc 0.38
      // 55e8: ldc_w -38.893
      // 55eb: ldc_w 44.734
      // 55ee: ldc_w -54.609
      // 55f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 55f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 55f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 55fa: aastore
      // 55fb: dup
      // 55fc: bipush 10
      // 55fe: new net/minecraft/client/animation/Keyframe
      // 5601: dup
      // 5602: ldc 0.42
      // 5604: ldc_w -37.171
      // 5607: ldc_w 49.921
      // 560a: ldc_w -52.204
      // 560d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5610: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5613: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5616: aastore
      // 5617: dup
      // 5618: bipush 11
      // 561a: new net/minecraft/client/animation/Keyframe
      // 561d: dup
      // 561e: ldc 0.46
      // 5620: ldc_w -39.311
      // 5623: ldc_w 56.762
      // 5626: ldc_w -47.399
      // 5629: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 562c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 562f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5632: aastore
      // 5633: dup
      // 5634: bipush 12
      // 5636: new net/minecraft/client/animation/Keyframe
      // 5639: dup
      // 563a: ldc 0.5
      // 563c: ldc_w -37.911
      // 563f: ldc_w 66.327
      // 5642: ldc_w -34.905
      // 5645: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5648: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 564b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 564e: aastore
      // 564f: dup
      // 5650: bipush 13
      // 5652: new net/minecraft/client/animation/Keyframe
      // 5655: dup
      // 5656: ldc 0.54
      // 5658: ldc_w -8.891
      // 565b: ldc_w 76.202
      // 565e: ldc_w 7.744
      // 5661: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5664: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5667: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 566a: aastore
      // 566b: dup
      // 566c: bipush 14
      // 566e: new net/minecraft/client/animation/Keyframe
      // 5671: dup
      // 5672: ldc 0.58
      // 5674: ldc_w 47.487
      // 5677: ldc_w 70.949
      // 567a: ldc_w 78.472
      // 567d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5680: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5683: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5686: aastore
      // 5687: dup
      // 5688: bipush 15
      // 568a: new net/minecraft/client/animation/Keyframe
      // 568d: dup
      // 568e: ldc 0.62
      // 5690: ldc_w 62.17
      // 5693: ldc_w 57.178
      // 5696: ldc_w 105.849
      // 5699: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 569c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 569f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56a2: aastore
      // 56a3: dup
      // 56a4: bipush 16
      // 56a6: new net/minecraft/client/animation/Keyframe
      // 56a9: dup
      // 56aa: ldc 0.67
      // 56ac: ldc_w 65.31
      // 56af: ldc_w 46.785
      // 56b2: ldc_w 118.462
      // 56b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56be: aastore
      // 56bf: dup
      // 56c0: bipush 17
      // 56c2: new net/minecraft/client/animation/Keyframe
      // 56c5: dup
      // 56c6: ldc 0.71
      // 56c8: ldc_w 66.822
      // 56cb: ldc_w 43.357
      // 56ce: ldc_w 123.62
      // 56d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56da: aastore
      // 56db: dup
      // 56dc: bipush 18
      // 56de: new net/minecraft/client/animation/Keyframe
      // 56e1: dup
      // 56e2: ldc 0.75
      // 56e4: ldc_w 68.869
      // 56e7: ldc_w 44.473
      // 56ea: ldc_w 124.935
      // 56ed: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 56f0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 56f3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 56f6: aastore
      // 56f7: dup
      // 56f8: bipush 19
      // 56fa: new net/minecraft/client/animation/Keyframe
      // 56fd: dup
      // 56fe: ldc 0.79
      // 5700: ldc_w 71.53
      // 5703: ldc_w 45.923
      // 5706: ldc_w 126.644
      // 5709: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 570c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 570f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5712: aastore
      // 5713: dup
      // 5714: bipush 20
      // 5716: new net/minecraft/client/animation/Keyframe
      // 5719: dup
      // 571a: ldc 0.83
      // 571c: ldc_w 74.191
      // 571f: ldc_w 47.373
      // 5722: ldc_w 128.354
      // 5725: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5728: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 572b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 572e: aastore
      // 572f: dup
      // 5730: bipush 21
      // 5732: new net/minecraft/client/animation/Keyframe
      // 5735: dup
      // 5736: ldc 0.88
      // 5738: ldc_w 76.238
      // 573b: ldc_w 48.488
      // 573e: ldc_w 129.668
      // 5741: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5744: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5747: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 574a: aastore
      // 574b: dup
      // 574c: bipush 22
      // 574e: new net/minecraft/client/animation/Keyframe
      // 5751: dup
      // 5752: ldc 0.92
      // 5754: ldc_w 77.057
      // 5757: ldc_w 48.935
      // 575a: ldc_w 130.194
      // 575d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5760: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5763: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5766: aastore
      // 5767: dup
      // 5768: bipush 23
      // 576a: new net/minecraft/client/animation/Keyframe
      // 576d: dup
      // 576e: ldc 0.96
      // 5770: ldc_w 76.215
      // 5773: ldc_w 48.919
      // 5776: ldc_w 129.739
      // 5779: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 577c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 577f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5782: aastore
      // 5783: dup
      // 5784: bipush 24
      // 5786: new net/minecraft/client/animation/Keyframe
      // 5789: dup
      // 578a: fconst_1
      // 578b: ldc_w 74.361
      // 578e: ldc_w 48.884
      // 5791: ldc_w 128.737
      // 5794: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5797: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 579a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 579d: aastore
      // 579e: dup
      // 579f: bipush 25
      // 57a1: new net/minecraft/client/animation/Keyframe
      // 57a4: dup
      // 57a5: ldc 1.04
      // 57a7: ldc_w 72.507
      // 57aa: ldc_w 48.849
      // 57ad: ldc_w 127.734
      // 57b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57b9: aastore
      // 57ba: dup
      // 57bb: bipush 26
      // 57bd: new net/minecraft/client/animation/Keyframe
      // 57c0: dup
      // 57c1: ldc 1.08
      // 57c3: ldc_w 71.665
      // 57c6: ldc_w 48.834
      // 57c9: ldc_w 127.279
      // 57cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57d5: aastore
      // 57d6: dup
      // 57d7: bipush 27
      // 57d9: new net/minecraft/client/animation/Keyframe
      // 57dc: dup
      // 57dd: ldc 1.12
      // 57df: ldc_w 71.786
      // 57e2: ldc_w 48.939
      // 57e5: ldc_w 127.347
      // 57e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 57eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 57ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 57f1: aastore
      // 57f2: dup
      // 57f3: bipush 28
      // 57f5: new net/minecraft/client/animation/Keyframe
      // 57f8: dup
      // 57f9: ldc 1.17
      // 57fb: ldc_w 72.123
      // 57fe: ldc_w 49.231
      // 5801: ldc_w 127.535
      // 5804: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5807: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 580a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 580d: aastore
      // 580e: dup
      // 580f: bipush 29
      // 5811: new net/minecraft/client/animation/Keyframe
      // 5814: dup
      // 5815: ldc 1.21
      // 5817: ldc_w 72.631
      // 581a: ldc_w 49.671
      // 581d: ldc_w 127.82
      // 5820: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5823: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5826: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5829: aastore
      // 582a: dup
      // 582b: bipush 30
      // 582d: new net/minecraft/client/animation/Keyframe
      // 5830: dup
      // 5831: ldc 1.25
      // 5833: ldc_w 73.267
      // 5836: ldc_w 50.224
      // 5839: ldc_w 128.177
      // 583c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 583f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5842: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5845: aastore
      // 5846: dup
      // 5847: bipush 31
      // 5849: new net/minecraft/client/animation/Keyframe
      // 584c: dup
      // 584d: ldc 1.29
      // 584f: ldc_w 73.99
      // 5852: ldc_w 50.851
      // 5855: ldc_w 128.581
      // 5858: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 585b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 585e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5861: aastore
      // 5862: dup
      // 5863: bipush 32
      // 5865: new net/minecraft/client/animation/Keyframe
      // 5868: dup
      // 5869: ldc 1.33
      // 586b: ldc_w 74.755
      // 586e: ldc_w 51.515
      // 5871: ldc_w 129.01
      // 5874: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5877: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 587a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 587d: aastore
      // 587e: dup
      // 587f: bipush 33
      // 5881: new net/minecraft/client/animation/Keyframe
      // 5884: dup
      // 5885: ldc 1.38
      // 5887: ldc_w 75.521
      // 588a: ldc_w 52.179
      // 588d: ldc_w 129.439
      // 5890: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5893: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5896: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5899: aastore
      // 589a: dup
      // 589b: bipush 34
      // 589d: new net/minecraft/client/animation/Keyframe
      // 58a0: dup
      // 58a1: ldc 1.42
      // 58a3: ldc_w 76.243
      // 58a6: ldc_w 52.805
      // 58a9: ldc_w 129.844
      // 58ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58b5: aastore
      // 58b6: dup
      // 58b7: bipush 35
      // 58b9: new net/minecraft/client/animation/Keyframe
      // 58bc: dup
      // 58bd: ldc 1.46
      // 58bf: ldc_w 76.88
      // 58c2: ldc_w 53.358
      // 58c5: ldc_w 130.2
      // 58c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58d1: aastore
      // 58d2: dup
      // 58d3: bipush 36
      // 58d5: new net/minecraft/client/animation/Keyframe
      // 58d8: dup
      // 58d9: ldc 1.5
      // 58db: ldc_w 77.388
      // 58de: ldc_w 53.798
      // 58e1: ldc_w 130.485
      // 58e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 58e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 58ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 58ed: aastore
      // 58ee: dup
      // 58ef: bipush 37
      // 58f1: new net/minecraft/client/animation/Keyframe
      // 58f4: dup
      // 58f5: ldc 1.54
      // 58f7: ldc_w 77.724
      // 58fa: ldc_w 54.09
      // 58fd: ldc_w 130.673
      // 5900: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5903: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5906: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5909: aastore
      // 590a: dup
      // 590b: bipush 38
      // 590d: new net/minecraft/client/animation/Keyframe
      // 5910: dup
      // 5911: ldc 1.58
      // 5913: ldc_w 77.846
      // 5916: ldc_w 54.196
      // 5919: ldc_w 130.741
      // 591c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 591f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5922: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5925: aastore
      // 5926: dup
      // 5927: bipush 39
      // 5929: new net/minecraft/client/animation/Keyframe
      // 592c: dup
      // 592d: ldc 1.62
      // 592f: ldc_w 75.299
      // 5932: ldc_w 53.781
      // 5935: ldc_w 129.024
      // 5938: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 593b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 593e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5941: aastore
      // 5942: dup
      // 5943: bipush 40
      // 5945: new net/minecraft/client/animation/Keyframe
      // 5948: dup
      // 5949: ldc 1.67
      // 594b: ldc_w 68.169
      // 594e: ldc_w 52.621
      // 5951: ldc_w 124.216
      // 5954: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5957: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 595a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 595d: aastore
      // 595e: dup
      // 595f: bipush 41
      // 5961: new net/minecraft/client/animation/Keyframe
      // 5964: dup
      // 5965: ldc_w 1.71
      // 5968: ldc_w 57.219
      // 596b: ldc_w 50.838
      // 596e: ldc_w 116.833
      // 5971: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5974: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5977: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 597a: aastore
      // 597b: dup
      // 597c: bipush 42
      // 597e: new net/minecraft/client/animation/Keyframe
      // 5981: dup
      // 5982: ldc_w 1.75
      // 5985: ldc_w 43.213
      // 5988: ldc_w 48.559
      // 598b: ldc_w 107.389
      // 598e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5991: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5994: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5997: aastore
      // 5998: dup
      // 5999: bipush 43
      // 599b: new net/minecraft/client/animation/Keyframe
      // 599e: dup
      // 599f: ldc_w 1.79
      // 59a2: ldc_w 26.916
      // 59a5: ldc_w 45.906
      // 59a8: ldc_w 96.4
      // 59ab: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59ae: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59b1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59b4: aastore
      // 59b5: dup
      // 59b6: bipush 44
      // 59b8: new net/minecraft/client/animation/Keyframe
      // 59bb: dup
      // 59bc: ldc_w 1.83
      // 59bf: ldc_w 9.09
      // 59c2: ldc_w 43.005
      // 59c5: ldc_w 84.38
      // 59c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59d1: aastore
      // 59d2: dup
      // 59d3: bipush 45
      // 59d5: new net/minecraft/client/animation/Keyframe
      // 59d8: dup
      // 59d9: ldc_w 1.88
      // 59dc: ldc_w -9.499
      // 59df: ldc_w 39.979
      // 59e2: ldc_w 71.846
      // 59e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 59e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 59eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 59ee: aastore
      // 59ef: dup
      // 59f0: bipush 46
      // 59f2: new net/minecraft/client/animation/Keyframe
      // 59f5: dup
      // 59f6: ldc_w 1.92
      // 59f9: ldc_w -28.088
      // 59fc: ldc_w 36.954
      // 59ff: ldc_w 59.311
      // 5a02: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a05: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a08: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a0b: aastore
      // 5a0c: dup
      // 5a0d: bipush 47
      // 5a0f: new net/minecraft/client/animation/Keyframe
      // 5a12: dup
      // 5a13: ldc_w 1.96
      // 5a16: ldc_w -45.914
      // 5a19: ldc_w 34.052
      // 5a1c: ldc_w 47.292
      // 5a1f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a22: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a25: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a28: aastore
      // 5a29: dup
      // 5a2a: bipush 48
      // 5a2c: new net/minecraft/client/animation/Keyframe
      // 5a2f: dup
      // 5a30: fconst_2
      // 5a31: ldc_w -62.212
      // 5a34: ldc_w 31.4
      // 5a37: ldc_w 36.302
      // 5a3a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a3d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a40: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a43: aastore
      // 5a44: dup
      // 5a45: bipush 49
      // 5a47: new net/minecraft/client/animation/Keyframe
      // 5a4a: dup
      // 5a4b: ldc 2.04
      // 5a4d: ldc_w -76.217
      // 5a50: ldc_w 29.12
      // 5a53: ldc_w 26.858
      // 5a56: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a59: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a5c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a5f: aastore
      // 5a60: dup
      // 5a61: bipush 50
      // 5a63: new net/minecraft/client/animation/Keyframe
      // 5a66: dup
      // 5a67: ldc 2.08
      // 5a69: ldc_w -87.167
      // 5a6c: ldc_w 27.338
      // 5a6f: ldc_w 19.475
      // 5a72: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a7b: aastore
      // 5a7c: dup
      // 5a7d: bipush 51
      // 5a7f: new net/minecraft/client/animation/Keyframe
      // 5a82: dup
      // 5a83: ldc 2.12
      // 5a85: ldc_w -94.297
      // 5a88: ldc_w 26.177
      // 5a8b: ldc_w 14.667
      // 5a8e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5a91: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5a94: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5a97: aastore
      // 5a98: dup
      // 5a99: bipush 52
      // 5a9b: new net/minecraft/client/animation/Keyframe
      // 5a9e: dup
      // 5a9f: ldc 2.17
      // 5aa1: ldc_w -96.844
      // 5aa4: ldc_w 25.763
      // 5aa7: ldc_w 12.95
      // 5aaa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5aad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ab0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ab3: aastore
      // 5ab4: dup
      // 5ab5: bipush 53
      // 5ab7: new net/minecraft/client/animation/Keyframe
      // 5aba: dup
      // 5abb: ldc 2.21
      // 5abd: ldc_w -86.924
      // 5ac0: ldc_w 11.1
      // 5ac3: ldc_w 42.603
      // 5ac6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ac9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5acc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5acf: aastore
      // 5ad0: dup
      // 5ad1: bipush 54
      // 5ad3: new net/minecraft/client/animation/Keyframe
      // 5ad6: dup
      // 5ad7: ldc 2.25
      // 5ad9: ldc_w -93.973
      // 5adc: ldc_w 6.277
      // 5adf: ldc_w 97.875
      // 5ae2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ae5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ae8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5aeb: aastore
      // 5aec: dup
      // 5aed: bipush 55
      // 5aef: new net/minecraft/client/animation/Keyframe
      // 5af2: dup
      // 5af3: ldc 2.29
      // 5af5: ldc_w -89.777
      // 5af8: ldc_w 3.514
      // 5afb: ldc_w 95.404
      // 5afe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b01: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b04: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b07: aastore
      // 5b08: dup
      // 5b09: bipush 56
      // 5b0b: new net/minecraft/client/animation/Keyframe
      // 5b0e: dup
      // 5b0f: ldc 2.33
      // 5b11: ldc_w -84.491
      // 5b14: ldc_w 6.482
      // 5b17: ldc_w 88.626
      // 5b1a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b1d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b20: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b23: aastore
      // 5b24: dup
      // 5b25: bipush 57
      // 5b27: new net/minecraft/client/animation/Keyframe
      // 5b2a: dup
      // 5b2b: ldc 2.38
      // 5b2d: ldc_w -86.202
      // 5b30: ldc_w 5.973
      // 5b33: ldc_w 91.09
      // 5b36: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b39: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b3c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b3f: aastore
      // 5b40: dup
      // 5b41: bipush 58
      // 5b43: new net/minecraft/client/animation/Keyframe
      // 5b46: dup
      // 5b47: ldc 2.42
      // 5b49: ldc_w -86.935
      // 5b4c: ldc_w 4.434
      // 5b4f: ldc_w 93.017
      // 5b52: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b55: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b58: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b5b: aastore
      // 5b5c: dup
      // 5b5d: bipush 59
      // 5b5f: new net/minecraft/client/animation/Keyframe
      // 5b62: dup
      // 5b63: ldc 2.46
      // 5b65: ldc_w -85.682
      // 5b68: ldc_w 5.727
      // 5b6b: ldc_w 91.213
      // 5b6e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b71: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b74: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b77: aastore
      // 5b78: dup
      // 5b79: bipush 60
      // 5b7b: new net/minecraft/client/animation/Keyframe
      // 5b7e: dup
      // 5b7f: ldc 2.5
      // 5b81: ldc_w -85.211
      // 5b84: ldc_w 7.423
      // 5b87: ldc_w 90.415
      // 5b8a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5b8d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5b90: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5b93: aastore
      // 5b94: dup
      // 5b95: bipush 61
      // 5b97: new net/minecraft/client/animation/Keyframe
      // 5b9a: dup
      // 5b9b: ldc 2.54
      // 5b9d: ldc_w -86.015
      // 5ba0: ldc_w 9.407
      // 5ba3: ldc_w 90.943
      // 5ba6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ba9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5baf: aastore
      // 5bb0: dup
      // 5bb1: bipush 62
      // 5bb3: new net/minecraft/client/animation/Keyframe
      // 5bb6: dup
      // 5bb7: ldc 2.62
      // 5bb9: ldc_w -86.015
      // 5bbc: ldc_w 9.407
      // 5bbf: ldc_w 90.943
      // 5bc2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5bc5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bc8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bcb: aastore
      // 5bcc: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5bcf: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5bd2: ldc_w "CTR_lowerarm_right"
      // 5bd5: new net/minecraft/client/animation/AnimationChannel
      // 5bd8: dup
      // 5bd9: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5bdc: bipush 29
      // 5bde: anewarray 24
      // 5be1: dup
      // 5be2: bipush 0
      // 5be3: new net/minecraft/client/animation/Keyframe
      // 5be6: dup
      // 5be7: fconst_0
      // 5be8: ldc_w 0.342
      // 5beb: ldc_w 33.697
      // 5bee: ldc_w -9.405
      // 5bf1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5bf4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5bf7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5bfa: aastore
      // 5bfb: dup
      // 5bfc: bipush 1
      // 5bfd: new net/minecraft/client/animation/Keyframe
      // 5c00: dup
      // 5c01: ldc 0.04
      // 5c03: ldc_w 0.975
      // 5c06: ldc_w 36.226
      // 5c09: ldc_w -6.696
      // 5c0c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c0f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c12: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c15: aastore
      // 5c16: dup
      // 5c17: bipush 2
      // 5c18: new net/minecraft/client/animation/Keyframe
      // 5c1b: dup
      // 5c1c: ldc 0.08
      // 5c1e: ldc_w 0.924
      // 5c21: ldc_w 34.348
      // 5c24: ldc_w -2.148
      // 5c27: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c30: aastore
      // 5c31: dup
      // 5c32: bipush 3
      // 5c33: new net/minecraft/client/animation/Keyframe
      // 5c36: dup
      // 5c37: ldc 0.12
      // 5c39: fconst_0
      // 5c3a: ldc_w 17.916
      // 5c3d: fconst_0
      // 5c3e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c41: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c44: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c47: aastore
      // 5c48: dup
      // 5c49: bipush 4
      // 5c4a: new net/minecraft/client/animation/Keyframe
      // 5c4d: dup
      // 5c4e: ldc 0.17
      // 5c50: fconst_0
      // 5c51: ldc_w 9.33
      // 5c54: fconst_0
      // 5c55: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c5e: aastore
      // 5c5f: dup
      // 5c60: bipush 5
      // 5c61: new net/minecraft/client/animation/Keyframe
      // 5c64: dup
      // 5c65: ldc 0.21
      // 5c67: fconst_0
      // 5c68: ldc_w 46.135
      // 5c6b: fconst_0
      // 5c6c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c6f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c72: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c75: aastore
      // 5c76: dup
      // 5c77: bipush 6
      // 5c79: new net/minecraft/client/animation/Keyframe
      // 5c7c: dup
      // 5c7d: ldc 0.25
      // 5c7f: fconst_0
      // 5c80: ldc_w 65.103
      // 5c83: fconst_0
      // 5c84: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5c8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5c8d: aastore
      // 5c8e: dup
      // 5c8f: bipush 7
      // 5c91: new net/minecraft/client/animation/Keyframe
      // 5c94: dup
      // 5c95: ldc 0.29
      // 5c97: fconst_0
      // 5c98: ldc_w 60.703
      // 5c9b: fconst_0
      // 5c9c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5c9f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ca2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ca5: aastore
      // 5ca6: dup
      // 5ca7: bipush 8
      // 5ca9: new net/minecraft/client/animation/Keyframe
      // 5cac: dup
      // 5cad: ldc 0.33
      // 5caf: fconst_0
      // 5cb0: ldc_w 50.071
      // 5cb3: fconst_0
      // 5cb4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5cb7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cba: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5cbd: aastore
      // 5cbe: dup
      // 5cbf: bipush 9
      // 5cc1: new net/minecraft/client/animation/Keyframe
      // 5cc4: dup
      // 5cc5: ldc 0.38
      // 5cc7: fconst_0
      // 5cc8: ldc_w 39.038
      // 5ccb: fconst_0
      // 5ccc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ccf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cd2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5cd5: aastore
      // 5cd6: dup
      // 5cd7: bipush 10
      // 5cd9: new net/minecraft/client/animation/Keyframe
      // 5cdc: dup
      // 5cdd: ldc 0.42
      // 5cdf: fconst_0
      // 5ce0: ldc_w 35.487
      // 5ce3: fconst_0
      // 5ce4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5ce7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5cea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ced: aastore
      // 5cee: dup
      // 5cef: bipush 11
      // 5cf1: new net/minecraft/client/animation/Keyframe
      // 5cf4: dup
      // 5cf5: ldc 0.46
      // 5cf7: fconst_0
      // 5cf8: ldc_w 44.283
      // 5cfb: fconst_0
      // 5cfc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5cff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d02: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d05: aastore
      // 5d06: dup
      // 5d07: bipush 12
      // 5d09: new net/minecraft/client/animation/Keyframe
      // 5d0c: dup
      // 5d0d: ldc 0.5
      // 5d0f: fconst_0
      // 5d10: ldc_w 56.319
      // 5d13: fconst_0
      // 5d14: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d17: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d1a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d1d: aastore
      // 5d1e: dup
      // 5d1f: bipush 13
      // 5d21: new net/minecraft/client/animation/Keyframe
      // 5d24: dup
      // 5d25: ldc 0.54
      // 5d27: fconst_0
      // 5d28: ldc_w 64.315
      // 5d2b: fconst_0
      // 5d2c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d2f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d32: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d35: aastore
      // 5d36: dup
      // 5d37: bipush 14
      // 5d39: new net/minecraft/client/animation/Keyframe
      // 5d3c: dup
      // 5d3d: ldc 0.58
      // 5d3f: fconst_0
      // 5d40: ldc_w 67.015
      // 5d43: fconst_0
      // 5d44: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d47: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d4a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d4d: aastore
      // 5d4e: dup
      // 5d4f: bipush 15
      // 5d51: new net/minecraft/client/animation/Keyframe
      // 5d54: dup
      // 5d55: ldc 0.62
      // 5d57: fconst_0
      // 5d58: ldc_w 67.162
      // 5d5b: fconst_0
      // 5d5c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d5f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d62: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d65: aastore
      // 5d66: dup
      // 5d67: bipush 16
      // 5d69: new net/minecraft/client/animation/Keyframe
      // 5d6c: dup
      // 5d6d: ldc 0.67
      // 5d6f: fconst_0
      // 5d70: ldc_w 67.02
      // 5d73: fconst_0
      // 5d74: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d77: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d7a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d7d: aastore
      // 5d7e: dup
      // 5d7f: bipush 17
      // 5d81: new net/minecraft/client/animation/Keyframe
      // 5d84: dup
      // 5d85: ldc 0.71
      // 5d87: fconst_0
      // 5d88: ldc_w 67.0
      // 5d8b: fconst_0
      // 5d8c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5d8f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5d92: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5d95: aastore
      // 5d96: dup
      // 5d97: bipush 18
      // 5d99: new net/minecraft/client/animation/Keyframe
      // 5d9c: dup
      // 5d9d: ldc 2.17
      // 5d9f: fconst_0
      // 5da0: ldc_w 67.0
      // 5da3: fconst_0
      // 5da4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5da7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5daa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5dad: aastore
      // 5dae: dup
      // 5daf: bipush 19
      // 5db1: new net/minecraft/client/animation/Keyframe
      // 5db4: dup
      // 5db5: ldc 2.21
      // 5db7: fconst_0
      // 5db8: ldc_w 60.538
      // 5dbb: fconst_0
      // 5dbc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5dbf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5dc2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5dc5: aastore
      // 5dc6: dup
      // 5dc7: bipush 20
      // 5dc9: new net/minecraft/client/animation/Keyframe
      // 5dcc: dup
      // 5dcd: ldc 2.25
      // 5dcf: fconst_0
      // 5dd0: ldc_w 6.457
      // 5dd3: fconst_0
      // 5dd4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5dd7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5dda: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ddd: aastore
      // 5dde: dup
      // 5ddf: bipush 21
      // 5de1: new net/minecraft/client/animation/Keyframe
      // 5de4: dup
      // 5de5: ldc 2.29
      // 5de7: fconst_0
      // 5de8: ldc_w 11.5
      // 5deb: fconst_0
      // 5dec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5def: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5df2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5df5: aastore
      // 5df6: dup
      // 5df7: bipush 22
      // 5df9: new net/minecraft/client/animation/Keyframe
      // 5dfc: dup
      // 5dfd: ldc 2.33
      // 5dff: fconst_0
      // 5e00: ldc_w 8.696
      // 5e03: fconst_0
      // 5e04: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e07: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e0a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e0d: aastore
      // 5e0e: dup
      // 5e0f: bipush 23
      // 5e11: new net/minecraft/client/animation/Keyframe
      // 5e14: dup
      // 5e15: ldc 2.38
      // 5e17: fconst_0
      // 5e18: ldc_w 10.494
      // 5e1b: fconst_0
      // 5e1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e25: aastore
      // 5e26: dup
      // 5e27: bipush 24
      // 5e29: new net/minecraft/client/animation/Keyframe
      // 5e2c: dup
      // 5e2d: ldc 2.42
      // 5e2f: fconst_0
      // 5e30: ldc_w 12.187
      // 5e33: fconst_0
      // 5e34: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e37: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e3a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e3d: aastore
      // 5e3e: dup
      // 5e3f: bipush 25
      // 5e41: new net/minecraft/client/animation/Keyframe
      // 5e44: dup
      // 5e45: ldc 2.46
      // 5e47: fconst_0
      // 5e48: ldc_w 10.316
      // 5e4b: fconst_0
      // 5e4c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e4f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e52: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e55: aastore
      // 5e56: dup
      // 5e57: bipush 26
      // 5e59: new net/minecraft/client/animation/Keyframe
      // 5e5c: dup
      // 5e5d: ldc 2.5
      // 5e5f: fconst_0
      // 5e60: ldc_w 8.275
      // 5e63: fconst_0
      // 5e64: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e67: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e6a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e6d: aastore
      // 5e6e: dup
      // 5e6f: bipush 27
      // 5e71: new net/minecraft/client/animation/Keyframe
      // 5e74: dup
      // 5e75: ldc 2.54
      // 5e77: fconst_0
      // 5e78: ldc_w 6.416
      // 5e7b: fconst_0
      // 5e7c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e7f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e82: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e85: aastore
      // 5e86: dup
      // 5e87: bipush 28
      // 5e89: new net/minecraft/client/animation/Keyframe
      // 5e8c: dup
      // 5e8d: ldc 2.62
      // 5e8f: fconst_0
      // 5e90: ldc_w 6.416
      // 5e93: fconst_0
      // 5e94: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5e97: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5e9a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5e9d: aastore
      // 5e9e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5ea1: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5ea4: ldc_w "icespike"
      // 5ea7: new net/minecraft/client/animation/AnimationChannel
      // 5eaa: dup
      // 5eab: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5eae: bipush 1
      // 5eaf: anewarray 24
      // 5eb2: dup
      // 5eb3: bipush 0
      // 5eb4: new net/minecraft/client/animation/Keyframe
      // 5eb7: dup
      // 5eb8: fconst_0
      // 5eb9: dconst_0
      // 5eba: dconst_0
      // 5ebb: dconst_0
      // 5ebc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 5ebf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ec2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ec5: aastore
      // 5ec6: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 5ec9: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 5ecc: ldc_w "CTR_leg_left"
      // 5ecf: new net/minecraft/client/animation/AnimationChannel
      // 5ed2: dup
      // 5ed3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 5ed6: bipush 64
      // 5ed8: anewarray 24
      // 5edb: dup
      // 5edc: bipush 0
      // 5edd: new net/minecraft/client/animation/Keyframe
      // 5ee0: dup
      // 5ee1: fconst_0
      // 5ee2: ldc_w 87.679
      // 5ee5: ldc_w -26.436
      // 5ee8: ldc_w -92.321
      // 5eeb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5eee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5ef1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5ef4: aastore
      // 5ef5: dup
      // 5ef6: bipush 1
      // 5ef7: new net/minecraft/client/animation/Keyframe
      // 5efa: dup
      // 5efb: ldc 0.04
      // 5efd: ldc_w 88.122
      // 5f00: ldc_w -24.483
      // 5f03: ldc_w -91.878
      // 5f06: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5f09: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f0c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f0f: aastore
      // 5f10: dup
      // 5f11: bipush 2
      // 5f12: new net/minecraft/client/animation/Keyframe
      // 5f15: dup
      // 5f16: ldc 0.08
      // 5f18: ldc_w 88.529
      // 5f1b: ldc_w -22.179
      // 5f1e: ldc_w -91.471
      // 5f21: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5f24: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f27: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f2a: aastore
      // 5f2b: dup
      // 5f2c: bipush 3
      // 5f2d: new net/minecraft/client/animation/Keyframe
      // 5f30: dup
      // 5f31: ldc 0.12
      // 5f33: ldc_w 87.664
      // 5f36: ldc_w -25.768
      // 5f39: ldc_w -92.336
      // 5f3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5f3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f45: aastore
      // 5f46: dup
      // 5f47: bipush 4
      // 5f48: new net/minecraft/client/animation/Keyframe
      // 5f4b: dup
      // 5f4c: ldc 0.17
      // 5f4e: ldc_w 85.168
      // 5f51: ldc_w -34.005
      // 5f54: ldc_w -94.832
      // 5f57: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5f5a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f5d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f60: aastore
      // 5f61: dup
      // 5f62: bipush 5
      // 5f63: new net/minecraft/client/animation/Keyframe
      // 5f66: dup
      // 5f67: ldc 0.21
      // 5f69: ldc_w 81.944
      // 5f6c: ldc_w -39.443
      // 5f6f: ldc_w -98.056
      // 5f72: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5f75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f7b: aastore
      // 5f7c: dup
      // 5f7d: bipush 6
      // 5f7f: new net/minecraft/client/animation/Keyframe
      // 5f82: dup
      // 5f83: ldc 0.25
      // 5f85: ldc_w 78.125
      // 5f88: ldc_w -26.43
      // 5f8b: ldc_w -101.875
      // 5f8e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5f91: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5f94: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5f97: aastore
      // 5f98: dup
      // 5f99: bipush 7
      // 5f9b: new net/minecraft/client/animation/Keyframe
      // 5f9e: dup
      // 5f9f: ldc 0.29
      // 5fa1: ldc_w 76.637
      // 5fa4: ldc_w -5.473
      // 5fa7: ldc_w -103.364
      // 5faa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5fad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fb0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fb3: aastore
      // 5fb4: dup
      // 5fb5: bipush 8
      // 5fb7: new net/minecraft/client/animation/Keyframe
      // 5fba: dup
      // 5fbb: ldc 0.33
      // 5fbd: ldc_w 76.221
      // 5fc0: ldc_w -8.63
      // 5fc3: ldc_w -103.779
      // 5fc6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5fc9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fcc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5fcf: aastore
      // 5fd0: dup
      // 5fd1: bipush 9
      // 5fd3: new net/minecraft/client/animation/Keyframe
      // 5fd6: dup
      // 5fd7: ldc 0.38
      // 5fd9: ldc_w 75.968
      // 5fdc: ldc_w -11.976
      // 5fdf: ldc_w -104.032
      // 5fe2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 5fe5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 5fe8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 5feb: aastore
      // 5fec: dup
      // 5fed: bipush 10
      // 5fef: new net/minecraft/client/animation/Keyframe
      // 5ff2: dup
      // 5ff3: ldc 0.42
      // 5ff5: ldc_w 75.881
      // 5ff8: ldc_w -15.46
      // 5ffb: ldc_w -104.119
      // 5ffe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6001: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6004: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6007: aastore
      // 6008: dup
      // 6009: bipush 11
      // 600b: new net/minecraft/client/animation/Keyframe
      // 600e: dup
      // 600f: ldc 0.46
      // 6011: ldc_w 75.958
      // 6014: ldc_w -18.989
      // 6017: ldc_w -104.042
      // 601a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 601d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6020: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6023: aastore
      // 6024: dup
      // 6025: bipush 12
      // 6027: new net/minecraft/client/animation/Keyframe
      // 602a: dup
      // 602b: ldc 0.5
      // 602d: ldc_w 76.192
      // 6030: ldc_w -22.454
      // 6033: ldc_w -103.808
      // 6036: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6039: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 603c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 603f: aastore
      // 6040: dup
      // 6041: bipush 13
      // 6043: new net/minecraft/client/animation/Keyframe
      // 6046: dup
      // 6047: ldc 0.54
      // 6049: ldc_w 76.561
      // 604c: ldc_w -25.736
      // 604f: ldc_w -103.439
      // 6052: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6055: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6058: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 605b: aastore
      // 605c: dup
      // 605d: bipush 14
      // 605f: new net/minecraft/client/animation/Keyframe
      // 6062: dup
      // 6063: ldc 0.58
      // 6065: ldc_w 77.034
      // 6068: ldc_w -28.724
      // 606b: ldc_w -102.966
      // 606e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6071: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6074: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6077: aastore
      // 6078: dup
      // 6079: bipush 15
      // 607b: new net/minecraft/client/animation/Keyframe
      // 607e: dup
      // 607f: ldc 0.62
      // 6081: ldc_w 78.039
      // 6084: ldc_w -26.708
      // 6087: ldc_w -101.961
      // 608a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 608d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6090: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6093: aastore
      // 6094: dup
      // 6095: bipush 16
      // 6097: new net/minecraft/client/animation/Keyframe
      // 609a: dup
      // 609b: ldc 0.67
      // 609d: ldc_w 79.723
      // 60a0: ldc_w -17.07
      // 60a3: ldc_w -100.277
      // 60a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 60a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60af: aastore
      // 60b0: dup
      // 60b1: bipush 17
      // 60b3: new net/minecraft/client/animation/Keyframe
      // 60b6: dup
      // 60b7: ldc 0.71
      // 60b9: ldc_w 81.684
      // 60bc: ldc_w -3.489
      // 60bf: ldc_w -98.316
      // 60c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 60c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60cb: aastore
      // 60cc: dup
      // 60cd: bipush 18
      // 60cf: new net/minecraft/client/animation/Keyframe
      // 60d2: dup
      // 60d3: ldc 0.75
      // 60d5: ldc_w 83.266
      // 60d8: ldc_w 7.991
      // 60db: ldc_w -96.734
      // 60de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 60e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 60e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 60e7: aastore
      // 60e8: dup
      // 60e9: bipush 19
      // 60eb: new net/minecraft/client/animation/Keyframe
      // 60ee: dup
      // 60ef: ldc 0.79
      // 60f1: ldc_w 83.78
      // 60f4: ldc_w 11.843
      // 60f7: ldc_w -96.22
      // 60fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 60fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6100: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6103: aastore
      // 6104: dup
      // 6105: bipush 20
      // 6107: new net/minecraft/client/animation/Keyframe
      // 610a: dup
      // 610b: ldc 0.83
      // 610d: ldc_w 83.551
      // 6110: ldc_w 10.743
      // 6113: ldc_w -96.45
      // 6116: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6119: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 611c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 611f: aastore
      // 6120: dup
      // 6121: bipush 21
      // 6123: new net/minecraft/client/animation/Keyframe
      // 6126: dup
      // 6127: ldc 0.88
      // 6129: ldc_w 83.23
      // 612c: ldc_w 10.318
      // 612f: ldc_w -96.769
      // 6132: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6135: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6138: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 613b: aastore
      // 613c: dup
      // 613d: bipush 22
      // 613f: new net/minecraft/client/animation/Keyframe
      // 6142: dup
      // 6143: ldc 0.92
      // 6145: ldc_w 82.955
      // 6148: ldc_w 9.994
      // 614b: ldc_w -97.045
      // 614e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6151: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6154: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6157: aastore
      // 6158: dup
      // 6159: bipush 23
      // 615b: new net/minecraft/client/animation/Keyframe
      // 615e: dup
      // 615f: ldc 0.96
      // 6161: ldc_w 82.797
      // 6164: ldc_w 9.082
      // 6167: ldc_w -97.203
      // 616a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 616d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6170: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6173: aastore
      // 6174: dup
      // 6175: bipush 24
      // 6177: new net/minecraft/client/animation/Keyframe
      // 617a: dup
      // 617b: fconst_1
      // 617c: ldc_w 82.721
      // 617f: ldc_w 7.686
      // 6182: ldc_w -97.279
      // 6185: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6188: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 618b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 618e: aastore
      // 618f: dup
      // 6190: bipush 25
      // 6192: new net/minecraft/client/animation/Keyframe
      // 6195: dup
      // 6196: ldc 1.04
      // 6198: ldc_w 82.71
      // 619b: ldc_w 5.906
      // 619e: ldc_w -97.29
      // 61a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 61a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61aa: aastore
      // 61ab: dup
      // 61ac: bipush 26
      // 61ae: new net/minecraft/client/animation/Keyframe
      // 61b1: dup
      // 61b2: ldc 1.08
      // 61b4: ldc_w 82.747
      // 61b7: ldc_w 3.826
      // 61ba: ldc_w -97.253
      // 61bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 61c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61c6: aastore
      // 61c7: dup
      // 61c8: bipush 27
      // 61ca: new net/minecraft/client/animation/Keyframe
      // 61cd: dup
      // 61ce: ldc 1.12
      // 61d0: ldc_w 82.81
      // 61d3: ldc_w 1.517
      // 61d6: ldc_w -97.19
      // 61d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 61dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61e2: aastore
      // 61e3: dup
      // 61e4: bipush 28
      // 61e6: new net/minecraft/client/animation/Keyframe
      // 61e9: dup
      // 61ea: ldc 1.17
      // 61ec: ldc_w 82.865
      // 61ef: ldc_w -0.95
      // 61f2: ldc_w -97.135
      // 61f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 61f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 61fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 61fe: aastore
      // 61ff: dup
      // 6200: bipush 29
      // 6202: new net/minecraft/client/animation/Keyframe
      // 6205: dup
      // 6206: ldc 1.21
      // 6208: ldc_w 82.873
      // 620b: ldc_w -3.497
      // 620e: ldc_w -97.127
      // 6211: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6214: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6217: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 621a: aastore
      // 621b: dup
      // 621c: bipush 30
      // 621e: new net/minecraft/client/animation/Keyframe
      // 6221: dup
      // 6222: ldc 1.25
      // 6224: ldc_w 82.794
      // 6227: ldc_w -6.017
      // 622a: ldc_w -97.206
      // 622d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6230: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6233: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6236: aastore
      // 6237: dup
      // 6238: bipush 31
      // 623a: new net/minecraft/client/animation/Keyframe
      // 623d: dup
      // 623e: ldc 1.29
      // 6240: ldc_w 82.593
      // 6243: ldc_w -8.327
      // 6246: ldc_w -97.407
      // 6249: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 624c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 624f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6252: aastore
      // 6253: dup
      // 6254: bipush 32
      // 6256: new net/minecraft/client/animation/Keyframe
      // 6259: dup
      // 625a: ldc 1.33
      // 625c: ldc_w 82.251
      // 625f: ldc_w -9.986
      // 6262: ldc_w -97.749
      // 6265: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6268: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 626b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 626e: aastore
      // 626f: dup
      // 6270: bipush 33
      // 6272: new net/minecraft/client/animation/Keyframe
      // 6275: dup
      // 6276: ldc 1.38
      // 6278: ldc_w 81.901
      // 627b: ldc_w -10.933
      // 627e: ldc_w -98.099
      // 6281: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6284: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6287: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 628a: aastore
      // 628b: dup
      // 628c: bipush 34
      // 628e: new net/minecraft/client/animation/Keyframe
      // 6291: dup
      // 6292: ldc 1.42
      // 6294: ldc_w 81.626
      // 6297: ldc_w -11.777
      // 629a: ldc_w -98.374
      // 629d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 62a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62a6: aastore
      // 62a7: dup
      // 62a8: bipush 35
      // 62aa: new net/minecraft/client/animation/Keyframe
      // 62ad: dup
      // 62ae: ldc 1.46
      // 62b0: ldc_w 81.392
      // 62b3: ldc_w -12.476
      // 62b6: ldc_w -98.608
      // 62b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 62bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62c2: aastore
      // 62c3: dup
      // 62c4: bipush 36
      // 62c6: new net/minecraft/client/animation/Keyframe
      // 62c9: dup
      // 62ca: ldc 1.5
      // 62cc: ldc_w 81.186
      // 62cf: ldc_w -13.009
      // 62d2: ldc_w -98.814
      // 62d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 62d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62de: aastore
      // 62df: dup
      // 62e0: bipush 37
      // 62e2: new net/minecraft/client/animation/Keyframe
      // 62e5: dup
      // 62e6: ldc 1.54
      // 62e8: ldc_w 81.003
      // 62eb: ldc_w -13.364
      // 62ee: ldc_w -98.997
      // 62f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 62f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 62f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 62fa: aastore
      // 62fb: dup
      // 62fc: bipush 38
      // 62fe: new net/minecraft/client/animation/Keyframe
      // 6301: dup
      // 6302: ldc 1.58
      // 6304: ldc_w 80.842
      // 6307: ldc_w -13.535
      // 630a: ldc_w -99.158
      // 630d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6310: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6313: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6316: aastore
      // 6317: dup
      // 6318: bipush 39
      // 631a: new net/minecraft/client/animation/Keyframe
      // 631d: dup
      // 631e: ldc 1.62
      // 6320: ldc_w 80.706
      // 6323: ldc_w -13.522
      // 6326: ldc_w -99.294
      // 6329: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 632c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 632f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6332: aastore
      // 6333: dup
      // 6334: bipush 40
      // 6336: new net/minecraft/client/animation/Keyframe
      // 6339: dup
      // 633a: ldc 1.67
      // 633c: ldc_w 80.598
      // 633f: ldc_w -13.328
      // 6342: ldc_w -99.402
      // 6345: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6348: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 634b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 634e: aastore
      // 634f: dup
      // 6350: bipush 41
      // 6352: new net/minecraft/client/animation/Keyframe
      // 6355: dup
      // 6356: ldc_w 1.71
      // 6359: ldc_w 80.526
      // 635c: ldc_w -12.961
      // 635f: ldc_w -99.475
      // 6362: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6365: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6368: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 636b: aastore
      // 636c: dup
      // 636d: bipush 42
      // 636f: new net/minecraft/client/animation/Keyframe
      // 6372: dup
      // 6373: ldc_w 1.75
      // 6376: ldc_w 80.499
      // 6379: ldc_w -12.428
      // 637c: ldc_w -99.501
      // 637f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6382: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6385: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6388: aastore
      // 6389: dup
      // 638a: bipush 43
      // 638c: new net/minecraft/client/animation/Keyframe
      // 638f: dup
      // 6390: ldc_w 1.79
      // 6393: ldc_w 80.529
      // 6396: ldc_w -11.738
      // 6399: ldc_w -99.471
      // 639c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 639f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63a5: aastore
      // 63a6: dup
      // 63a7: bipush 44
      // 63a9: new net/minecraft/client/animation/Keyframe
      // 63ac: dup
      // 63ad: ldc_w 1.83
      // 63b0: ldc_w 80.632
      // 63b3: ldc_w -10.891
      // 63b6: ldc_w -99.368
      // 63b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 63bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63c2: aastore
      // 63c3: dup
      // 63c4: bipush 45
      // 63c6: new net/minecraft/client/animation/Keyframe
      // 63c9: dup
      // 63ca: ldc_w 1.88
      // 63cd: ldc_w 80.824
      // 63d0: ldc_w -9.878
      // 63d3: ldc_w -99.176
      // 63d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 63d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63df: aastore
      // 63e0: dup
      // 63e1: bipush 46
      // 63e3: new net/minecraft/client/animation/Keyframe
      // 63e6: dup
      // 63e7: ldc_w 1.92
      // 63ea: ldc_w 81.125
      // 63ed: ldc_w -8.671
      // 63f0: ldc_w -98.875
      // 63f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 63f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 63f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 63fc: aastore
      // 63fd: dup
      // 63fe: bipush 47
      // 6400: new net/minecraft/client/animation/Keyframe
      // 6403: dup
      // 6404: ldc_w 1.96
      // 6407: ldc_w 81.552
      // 640a: ldc_w -7.209
      // 640d: ldc_w -98.448
      // 6410: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6413: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6416: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6419: aastore
      // 641a: dup
      // 641b: bipush 48
      // 641d: new net/minecraft/client/animation/Keyframe
      // 6420: dup
      // 6421: fconst_2
      // 6422: ldc_w 82.12
      // 6425: ldc_w -5.396
      // 6428: ldc_w -97.88
      // 642b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 642e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6431: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6434: aastore
      // 6435: dup
      // 6436: bipush 49
      // 6438: new net/minecraft/client/animation/Keyframe
      // 643b: dup
      // 643c: ldc 2.04
      // 643e: ldc_w 82.838
      // 6441: ldc_w -3.097
      // 6444: ldc_w -97.162
      // 6447: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 644a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 644d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6450: aastore
      // 6451: dup
      // 6452: bipush 50
      // 6454: new net/minecraft/client/animation/Keyframe
      // 6457: dup
      // 6458: ldc 2.08
      // 645a: ldc_w 83.472
      // 645d: ldc_w 0.853
      // 6460: ldc_w -96.53
      // 6463: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6466: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6469: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 646c: aastore
      // 646d: dup
      // 646e: bipush 51
      // 6470: new net/minecraft/client/animation/Keyframe
      // 6473: dup
      // 6474: ldc 2.12
      // 6476: ldc_w 84.085
      // 6479: ldc_w 5.552
      // 647c: ldc_w -95.915
      // 647f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6482: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6485: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6488: aastore
      // 6489: dup
      // 648a: bipush 52
      // 648c: new net/minecraft/client/animation/Keyframe
      // 648f: dup
      // 6490: ldc 2.17
      // 6492: ldc_w 83.51
      // 6495: ldc_w 28.153
      // 6498: ldc_w -96.49
      // 649b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 649e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64a1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64a4: aastore
      // 64a5: dup
      // 64a6: bipush 53
      // 64a8: new net/minecraft/client/animation/Keyframe
      // 64ab: dup
      // 64ac: ldc 2.21
      // 64ae: ldc_w 82.714
      // 64b1: ldc_w 26.814
      // 64b4: ldc_w -97.286
      // 64b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64c0: aastore
      // 64c1: dup
      // 64c2: bipush 54
      // 64c4: new net/minecraft/client/animation/Keyframe
      // 64c7: dup
      // 64c8: ldc 2.25
      // 64ca: ldc_w 81.977
      // 64cd: ldc_w 26.45
      // 64d0: ldc_w -98.023
      // 64d3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64d6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64d9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64dc: aastore
      // 64dd: dup
      // 64de: bipush 55
      // 64e0: new net/minecraft/client/animation/Keyframe
      // 64e3: dup
      // 64e4: ldc 2.29
      // 64e6: ldc_w 81.869
      // 64e9: ldc_w 22.923
      // 64ec: ldc_w -98.131
      // 64ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 64f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 64f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 64f8: aastore
      // 64f9: dup
      // 64fa: bipush 56
      // 64fc: new net/minecraft/client/animation/Keyframe
      // 64ff: dup
      // 6500: ldc 2.33
      // 6502: ldc_w 81.931
      // 6505: ldc_w 29.141
      // 6508: ldc_w -98.069
      // 650b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 650e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6511: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6514: aastore
      // 6515: dup
      // 6516: bipush 57
      // 6518: new net/minecraft/client/animation/Keyframe
      // 651b: dup
      // 651c: ldc 2.38
      // 651e: ldc_w 82.003
      // 6521: ldc_w 27.531
      // 6524: ldc_w -97.997
      // 6527: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 652a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 652d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6530: aastore
      // 6531: dup
      // 6532: bipush 58
      // 6534: new net/minecraft/client/animation/Keyframe
      // 6537: dup
      // 6538: ldc 2.42
      // 653a: ldc_w 82.441
      // 653d: ldc_w 24.029
      // 6540: ldc_w -97.559
      // 6543: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6546: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6549: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 654c: aastore
      // 654d: dup
      // 654e: bipush 59
      // 6550: new net/minecraft/client/animation/Keyframe
      // 6553: dup
      // 6554: ldc 2.46
      // 6556: ldc_w 82.435
      // 6559: ldc_w 24.976
      // 655c: ldc_w -97.565
      // 655f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6562: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6565: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6568: aastore
      // 6569: dup
      // 656a: bipush 60
      // 656c: new net/minecraft/client/animation/Keyframe
      // 656f: dup
      // 6570: ldc 2.5
      // 6572: ldc_w 82.435
      // 6575: ldc_w 24.776
      // 6578: ldc_w -97.565
      // 657b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 657e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6581: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6584: aastore
      // 6585: dup
      // 6586: bipush 61
      // 6588: new net/minecraft/client/animation/Keyframe
      // 658b: dup
      // 658c: ldc 2.54
      // 658e: ldc_w 82.434
      // 6591: ldc_w 24.602
      // 6594: ldc_w -97.566
      // 6597: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 659a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 659d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65a0: aastore
      // 65a1: dup
      // 65a2: bipush 62
      // 65a4: new net/minecraft/client/animation/Keyframe
      // 65a7: dup
      // 65a8: ldc 2.58
      // 65aa: ldc_w 82.434
      // 65ad: ldc_w 24.48
      // 65b0: ldc_w -97.566
      // 65b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 65b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65bc: aastore
      // 65bd: dup
      // 65be: bipush 63
      // 65c0: new net/minecraft/client/animation/Keyframe
      // 65c3: dup
      // 65c4: ldc 2.62
      // 65c6: ldc_w 82.434
      // 65c9: ldc_w 24.433
      // 65cc: ldc_w -97.566
      // 65cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 65d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 65d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 65d8: aastore
      // 65d9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 65dc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 65df: ldc_w "CTR_leg_left"
      // 65e2: new net/minecraft/client/animation/AnimationChannel
      // 65e5: dup
      // 65e6: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 65e9: bipush 59
      // 65eb: anewarray 24
      // 65ee: dup
      // 65ef: bipush 0
      // 65f0: new net/minecraft/client/animation/Keyframe
      // 65f3: dup
      // 65f4: fconst_0
      // 65f5: ldc_w 0.107
      // 65f8: ldc_w 8.937
      // 65fb: ldc_w -2.831
      // 65fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6601: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6604: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6607: aastore
      // 6608: dup
      // 6609: bipush 1
      // 660a: new net/minecraft/client/animation/Keyframe
      // 660d: dup
      // 660e: ldc 0.04
      // 6610: ldc_w 0.28
      // 6613: ldc_w 7.954
      // 6616: ldc_w -3.541
      // 6619: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 661c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 661f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6622: aastore
      // 6623: dup
      // 6624: bipush 2
      // 6625: new net/minecraft/client/animation/Keyframe
      // 6628: dup
      // 6629: ldc 0.08
      // 662b: ldc_w 0.551
      // 662e: ldc_w 4.384
      // 6631: ldc_w -5.247
      // 6634: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6637: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 663a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 663d: aastore
      // 663e: dup
      // 663f: bipush 3
      // 6640: new net/minecraft/client/animation/Keyframe
      // 6643: dup
      // 6644: ldc 0.12
      // 6646: ldc_w 0.638
      // 6649: ldc_w 2.767
      // 664c: ldc_w -5.907
      // 664f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6652: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6655: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6658: aastore
      // 6659: dup
      // 665a: bipush 4
      // 665b: new net/minecraft/client/animation/Keyframe
      // 665e: dup
      // 665f: ldc 0.25
      // 6661: ldc_w 0.638
      // 6664: ldc_w 2.767
      // 6667: ldc_w -5.907
      // 666a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 666d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6670: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6673: aastore
      // 6674: dup
      // 6675: bipush 5
      // 6676: new net/minecraft/client/animation/Keyframe
      // 6679: dup
      // 667a: ldc 0.29
      // 667c: ldc_w -0.376
      // 667f: ldc_w 4.775
      // 6682: ldc_w -5.697
      // 6685: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6688: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 668b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 668e: aastore
      // 668f: dup
      // 6690: bipush 6
      // 6692: new net/minecraft/client/animation/Keyframe
      // 6695: dup
      // 6696: ldc 0.33
      // 6698: ldc_w -0.537
      // 669b: ldc_w 5.004
      // 669e: ldc_w -5.535
      // 66a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66aa: aastore
      // 66ab: dup
      // 66ac: bipush 7
      // 66ae: new net/minecraft/client/animation/Keyframe
      // 66b1: dup
      // 66b2: ldc 0.38
      // 66b4: ldc_w -0.59
      // 66b7: ldc_w 5.042
      // 66ba: ldc_w -5.376
      // 66bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66c6: aastore
      // 66c7: dup
      // 66c8: bipush 8
      // 66ca: new net/minecraft/client/animation/Keyframe
      // 66cd: dup
      // 66ce: ldc 0.42
      // 66d0: ldc_w -0.534
      // 66d3: ldc_w 4.903
      // 66d6: ldc_w -5.255
      // 66d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66e2: aastore
      // 66e3: dup
      // 66e4: bipush 9
      // 66e6: new net/minecraft/client/animation/Keyframe
      // 66e9: dup
      // 66ea: ldc 0.46
      // 66ec: ldc_w -0.374
      // 66ef: ldc_w 4.603
      // 66f2: ldc_w -5.21
      // 66f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 66f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 66fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 66fe: aastore
      // 66ff: dup
      // 6700: bipush 10
      // 6702: new net/minecraft/client/animation/Keyframe
      // 6705: dup
      // 6706: ldc 0.5
      // 6708: ldc_w -0.124
      // 670b: ldc_w 4.159
      // 670e: ldc_w -5.273
      // 6711: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6714: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6717: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 671a: aastore
      // 671b: dup
      // 671c: bipush 11
      // 671e: new net/minecraft/client/animation/Keyframe
      // 6721: dup
      // 6722: ldc 0.54
      // 6724: ldc_w 0.19
      // 6727: ldc_w 3.598
      // 672a: ldc_w -5.467
      // 672d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6730: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6733: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6736: aastore
      // 6737: dup
      // 6738: bipush 12
      // 673a: new net/minecraft/client/animation/Keyframe
      // 673d: dup
      // 673e: ldc 0.58
      // 6740: ldc_w 0.543
      // 6743: ldc_w 2.947
      // 6746: ldc_w -5.799
      // 6749: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 674c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 674f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6752: aastore
      // 6753: dup
      // 6754: bipush 13
      // 6756: new net/minecraft/client/animation/Keyframe
      // 6759: dup
      // 675a: ldc 0.62
      // 675c: ldc_w 0.377
      // 675f: ldc_w 3.318
      // 6762: ldc_w -5.608
      // 6765: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6768: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 676b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 676e: aastore
      // 676f: dup
      // 6770: bipush 14
      // 6772: new net/minecraft/client/animation/Keyframe
      // 6775: dup
      // 6776: ldc 0.67
      // 6778: ldc_w -0.189
      // 677b: ldc_w 4.919
      // 677e: ldc_w -5.211
      // 6781: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6784: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6787: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 678a: aastore
      // 678b: dup
      // 678c: bipush 15
      // 678e: new net/minecraft/client/animation/Keyframe
      // 6791: dup
      // 6792: ldc 0.71
      // 6794: ldc_w -0.301
      // 6797: ldc_w 5.907
      // 679a: ldc_w -5.709
      // 679d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 67a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67a6: aastore
      // 67a7: dup
      // 67a8: bipush 16
      // 67aa: new net/minecraft/client/animation/Keyframe
      // 67ad: dup
      // 67ae: ldc 0.75
      // 67b0: ldc_w 0.002
      // 67b3: ldc_w 5.409
      // 67b6: ldc_w -6.286
      // 67b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 67bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67c2: aastore
      // 67c3: dup
      // 67c4: bipush 17
      // 67c6: new net/minecraft/client/animation/Keyframe
      // 67c9: dup
      // 67ca: ldc 0.79
      // 67cc: ldc_w 0.173
      // 67cf: ldc_w 4.849
      // 67d2: ldc_w -6.351
      // 67d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 67d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67de: aastore
      // 67df: dup
      // 67e0: bipush 18
      // 67e2: new net/minecraft/client/animation/Keyframe
      // 67e5: dup
      // 67e6: ldc 0.83
      // 67e8: ldc_w 0.173
      // 67eb: ldc_w 4.776
      // 67ee: ldc_w -6.295
      // 67f1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 67f4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 67f7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 67fa: aastore
      // 67fb: dup
      // 67fc: bipush 19
      // 67fe: new net/minecraft/client/animation/Keyframe
      // 6801: dup
      // 6802: ldc 0.88
      // 6804: ldc_w 0.178
      // 6807: ldc_w 4.66
      // 680a: ldc_w -6.259
      // 680d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6810: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6813: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6816: aastore
      // 6817: dup
      // 6818: bipush 20
      // 681a: new net/minecraft/client/animation/Keyframe
      // 681d: dup
      // 681e: ldc 0.92
      // 6820: ldc_w 0.224
      // 6823: ldc_w 4.402
      // 6826: ldc_w -6.202
      // 6829: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 682c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 682f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6832: aastore
      // 6833: dup
      // 6834: bipush 21
      // 6836: new net/minecraft/client/animation/Keyframe
      // 6839: dup
      // 683a: ldc 0.96
      // 683c: ldc_w 0.301
      // 683f: ldc_w 4.069
      // 6842: ldc_w -6.12
      // 6845: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6848: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 684b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 684e: aastore
      // 684f: dup
      // 6850: bipush 22
      // 6852: new net/minecraft/client/animation/Keyframe
      // 6855: dup
      // 6856: fconst_1
      // 6857: ldc_w 0.383
      // 685a: ldc_w 3.743
      // 685d: ldc_w -6.042
      // 6860: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6863: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6866: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6869: aastore
      // 686a: dup
      // 686b: bipush 23
      // 686d: new net/minecraft/client/animation/Keyframe
      // 6870: dup
      // 6871: ldc 1.04
      // 6873: ldc 0.46
      // 6875: ldc_w 3.449
      // 6878: ldc_w -5.979
      // 687b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 687e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6881: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6884: aastore
      // 6885: dup
      // 6886: bipush 24
      // 6888: new net/minecraft/client/animation/Keyframe
      // 688b: dup
      // 688c: ldc 1.08
      // 688e: ldc_w 0.526
      // 6891: ldc_w 3.199
      // 6894: ldc_w -5.936
      // 6897: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 689a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 689d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68a0: aastore
      // 68a1: dup
      // 68a2: bipush 25
      // 68a4: new net/minecraft/client/animation/Keyframe
      // 68a7: dup
      // 68a8: ldc 1.12
      // 68aa: ldc_w 0.576
      // 68ad: ldc_w 3.008
      // 68b0: ldc_w -5.913
      // 68b3: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 68b6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68b9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68bc: aastore
      // 68bd: dup
      // 68be: bipush 26
      // 68c0: new net/minecraft/client/animation/Keyframe
      // 68c3: dup
      // 68c4: ldc 1.17
      // 68c6: ldc_w 0.606
      // 68c9: ldc_w 2.893
      // 68cc: ldc_w -5.905
      // 68cf: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 68d2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68d5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68d8: aastore
      // 68d9: dup
      // 68da: bipush 27
      // 68dc: new net/minecraft/client/animation/Keyframe
      // 68df: dup
      // 68e0: ldc 1.21
      // 68e2: ldc_w 0.612
      // 68e5: ldc_w 2.869
      // 68e8: ldc_w -5.9
      // 68eb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 68ee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 68f1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 68f4: aastore
      // 68f5: dup
      // 68f6: bipush 28
      // 68f8: new net/minecraft/client/animation/Keyframe
      // 68fb: dup
      // 68fc: ldc 1.25
      // 68fe: ldc_w 0.59
      // 6901: ldc_w 2.951
      // 6904: ldc_w -5.887
      // 6907: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 690a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 690d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6910: aastore
      // 6911: dup
      // 6912: bipush 29
      // 6914: new net/minecraft/client/animation/Keyframe
      // 6917: dup
      // 6918: ldc 1.29
      // 691a: ldc_w 0.539
      // 691d: ldc_w 3.14
      // 6920: ldc_w -5.851
      // 6923: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6926: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6929: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 692c: aastore
      // 692d: dup
      // 692e: bipush 30
      // 6930: new net/minecraft/client/animation/Keyframe
      // 6933: dup
      // 6934: ldc 1.33
      // 6936: ldc_w 0.456
      // 6939: ldc_w 3.419
      // 693c: ldc_w -5.789
      // 693f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6942: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6945: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6948: aastore
      // 6949: dup
      // 694a: bipush 31
      // 694c: new net/minecraft/client/animation/Keyframe
      // 694f: dup
      // 6950: ldc 1.38
      // 6952: ldc_w 0.365
      // 6955: ldc_w 3.698
      // 6958: ldc_w -5.722
      // 695b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 695e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6961: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6964: aastore
      // 6965: dup
      // 6966: bipush 32
      // 6968: new net/minecraft/client/animation/Keyframe
      // 696b: dup
      // 696c: ldc 1.42
      // 696e: ldc_w 0.279
      // 6971: ldc_w 3.946
      // 6974: ldc_w -5.653
      // 6977: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 697a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 697d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6980: aastore
      // 6981: dup
      // 6982: bipush 33
      // 6984: new net/minecraft/client/animation/Keyframe
      // 6987: dup
      // 6988: ldc 1.46
      // 698a: ldc_w 0.194
      // 698d: ldc_w 4.181
      // 6990: ldc_w -5.583
      // 6993: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6996: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6999: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 699c: aastore
      // 699d: dup
      // 699e: bipush 34
      // 69a0: new net/minecraft/client/animation/Keyframe
      // 69a3: dup
      // 69a4: ldc 1.5
      // 69a6: ldc_w 0.108
      // 69a9: ldc_w 4.41
      // 69ac: ldc_w -5.513
      // 69af: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 69b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69b8: aastore
      // 69b9: dup
      // 69ba: bipush 35
      // 69bc: new net/minecraft/client/animation/Keyframe
      // 69bf: dup
      // 69c0: ldc 1.54
      // 69c2: ldc_w 0.021
      // 69c5: ldc_w 4.637
      // 69c8: ldc_w -5.445
      // 69cb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 69ce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69d1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69d4: aastore
      // 69d5: dup
      // 69d6: bipush 36
      // 69d8: new net/minecraft/client/animation/Keyframe
      // 69db: dup
      // 69dc: ldc 1.58
      // 69de: ldc_w -0.066
      // 69e1: ldc_w 4.862
      // 69e4: ldc_w -5.382
      // 69e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 69ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 69ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 69f0: aastore
      // 69f1: dup
      // 69f2: bipush 37
      // 69f4: new net/minecraft/client/animation/Keyframe
      // 69f7: dup
      // 69f8: ldc 1.62
      // 69fa: ldc_w -0.153
      // 69fd: ldc_w 5.082
      // 6a00: ldc_w -5.327
      // 6a03: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a06: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a09: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a0c: aastore
      // 6a0d: dup
      // 6a0e: bipush 38
      // 6a10: new net/minecraft/client/animation/Keyframe
      // 6a13: dup
      // 6a14: ldc 1.67
      // 6a16: ldc_w -0.234
      // 6a19: ldc_w 5.292
      // 6a1c: ldc_w -5.283
      // 6a1f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a22: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a25: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a28: aastore
      // 6a29: dup
      // 6a2a: bipush 39
      // 6a2c: new net/minecraft/client/animation/Keyframe
      // 6a2f: dup
      // 6a30: ldc_w 1.71
      // 6a33: ldc_w -0.307
      // 6a36: ldc_w 5.482
      // 6a39: ldc_w -5.255
      // 6a3c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a3f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a42: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a45: aastore
      // 6a46: dup
      // 6a47: bipush 40
      // 6a49: new net/minecraft/client/animation/Keyframe
      // 6a4c: dup
      // 6a4d: ldc_w 1.75
      // 6a50: ldc_w -0.363
      // 6a53: ldc_w 5.638
      // 6a56: ldc_w -5.246
      // 6a59: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a5c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a5f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a62: aastore
      // 6a63: dup
      // 6a64: bipush 41
      // 6a66: new net/minecraft/client/animation/Keyframe
      // 6a69: dup
      // 6a6a: ldc_w 1.79
      // 6a6d: ldc_w -0.394
      // 6a70: ldc_w 5.741
      // 6a73: ldc_w -5.262
      // 6a76: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a79: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a7c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a7f: aastore
      // 6a80: dup
      // 6a81: bipush 42
      // 6a83: new net/minecraft/client/animation/Keyframe
      // 6a86: dup
      // 6a87: ldc_w 1.83
      // 6a8a: ldc_w -0.388
      // 6a8d: ldc_w 5.763
      // 6a90: ldc_w -5.306
      // 6a93: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6a96: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6a99: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6a9c: aastore
      // 6a9d: dup
      // 6a9e: bipush 43
      // 6aa0: new net/minecraft/client/animation/Keyframe
      // 6aa3: dup
      // 6aa4: ldc_w 1.88
      // 6aa7: ldc_w -0.332
      // 6aaa: ldc_w 5.666
      // 6aad: ldc_w -5.382
      // 6ab0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ab3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ab6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ab9: aastore
      // 6aba: dup
      // 6abb: bipush 44
      // 6abd: new net/minecraft/client/animation/Keyframe
      // 6ac0: dup
      // 6ac1: ldc_w 1.92
      // 6ac4: ldc_w -0.209
      // 6ac7: ldc_w 5.397
      // 6aca: ldc_w -5.491
      // 6acd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6ad0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ad3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ad6: aastore
      // 6ad7: dup
      // 6ad8: bipush 45
      // 6ada: new net/minecraft/client/animation/Keyframe
      // 6add: dup
      // 6ade: ldc_w 1.96
      // 6ae1: ldc_w -0.007
      // 6ae4: ldc_w 4.881
      // 6ae7: ldc_w -5.63
      // 6aea: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6aed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6af0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6af3: aastore
      // 6af4: dup
      // 6af5: bipush 46
      // 6af7: new net/minecraft/client/animation/Keyframe
      // 6afa: dup
      // 6afb: fconst_2
      // 6afc: ldc_w 0.285
      // 6aff: ldc_w 4.016
      // 6b02: ldc_w -5.785
      // 6b05: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b08: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b0b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b0e: aastore
      // 6b0f: dup
      // 6b10: bipush 47
      // 6b12: new net/minecraft/client/animation/Keyframe
      // 6b15: dup
      // 6b16: ldc 2.04
      // 6b18: ldc_w 0.638
      // 6b1b: ldc_w 2.767
      // 6b1e: ldc_w -5.907
      // 6b21: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b24: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b27: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b2a: aastore
      // 6b2b: dup
      // 6b2c: bipush 48
      // 6b2e: new net/minecraft/client/animation/Keyframe
      // 6b31: dup
      // 6b32: ldc 2.21
      // 6b34: ldc_w 0.638
      // 6b37: ldc_w 2.767
      // 6b3a: ldc_w -5.907
      // 6b3d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b40: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b43: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b46: aastore
      // 6b47: dup
      // 6b48: bipush 49
      // 6b4a: new net/minecraft/client/animation/Keyframe
      // 6b4d: dup
      // 6b4e: ldc 2.25
      // 6b50: ldc_w 0.137
      // 6b53: ldc_w 4.407
      // 6b56: ldc_w -6.749
      // 6b59: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b5c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b5f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b62: aastore
      // 6b63: dup
      // 6b64: bipush 50
      // 6b66: new net/minecraft/client/animation/Keyframe
      // 6b69: dup
      // 6b6a: ldc 2.29
      // 6b6c: ldc_w -0.072
      // 6b6f: ldc_w 5.097
      // 6b72: ldc_w -6.925
      // 6b75: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b78: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b7b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b7e: aastore
      // 6b7f: dup
      // 6b80: bipush 51
      // 6b82: new net/minecraft/client/animation/Keyframe
      // 6b85: dup
      // 6b86: ldc 2.33
      // 6b88: ldc_w 0.16
      // 6b8b: ldc_w 4.103
      // 6b8e: ldc_w -6.743
      // 6b91: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6b94: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6b97: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6b9a: aastore
      // 6b9b: dup
      // 6b9c: bipush 52
      // 6b9e: new net/minecraft/client/animation/Keyframe
      // 6ba1: dup
      // 6ba2: ldc 2.38
      // 6ba4: ldc_w 0.118
      // 6ba7: ldc_w 3.734
      // 6baa: ldc_w -6.648
      // 6bad: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6bb0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6bb3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6bb6: aastore
      // 6bb7: dup
      // 6bb8: bipush 53
      // 6bba: new net/minecraft/client/animation/Keyframe
      // 6bbd: dup
      // 6bbe: ldc 2.42
      // 6bc0: ldc_w 0.27
      // 6bc3: ldc_w 2.55
      // 6bc6: ldc_w -6.167
      // 6bc9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6bcc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6bcf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6bd2: aastore
      // 6bd3: dup
      // 6bd4: bipush 54
      // 6bd6: new net/minecraft/client/animation/Keyframe
      // 6bd9: dup
      // 6bda: ldc 2.46
      // 6bdc: ldc_w 0.229
      // 6bdf: ldc_w 1.813
      // 6be2: ldc_w -6.053
      // 6be5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6be8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6beb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6bee: aastore
      // 6bef: dup
      // 6bf0: bipush 55
      // 6bf2: new net/minecraft/client/animation/Keyframe
      // 6bf5: dup
      // 6bf6: ldc 2.5
      // 6bf8: ldc_w 0.16
      // 6bfb: ldc_w 1.178
      // 6bfe: ldc_w -5.968
      // 6c01: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c04: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c07: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c0a: aastore
      // 6c0b: dup
      // 6c0c: bipush 56
      // 6c0e: new net/minecraft/client/animation/Keyframe
      // 6c11: dup
      // 6c12: ldc 2.54
      // 6c14: ldc_w 0.1
      // 6c17: ldc_w 0.624
      // 6c1a: ldc_w -5.893
      // 6c1d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c20: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c23: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c26: aastore
      // 6c27: dup
      // 6c28: bipush 57
      // 6c2a: new net/minecraft/client/animation/Keyframe
      // 6c2d: dup
      // 6c2e: ldc 2.58
      // 6c30: ldc_w 0.057
      // 6c33: ldc_w 0.232
      // 6c36: ldc_w -5.84
      // 6c39: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c3c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c3f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c42: aastore
      // 6c43: dup
      // 6c44: bipush 58
      // 6c46: new net/minecraft/client/animation/Keyframe
      // 6c49: dup
      // 6c4a: ldc 2.62
      // 6c4c: ldc_w 0.041
      // 6c4f: ldc_w 0.083
      // 6c52: ldc_w -5.82
      // 6c55: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 6c58: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c5b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c5e: aastore
      // 6c5f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 6c62: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 6c65: ldc_w "CTR_leg_right"
      // 6c68: new net/minecraft/client/animation/AnimationChannel
      // 6c6b: dup
      // 6c6c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 6c6f: bipush 64
      // 6c71: anewarray 24
      // 6c74: dup
      // 6c75: bipush 0
      // 6c76: new net/minecraft/client/animation/Keyframe
      // 6c79: dup
      // 6c7a: fconst_0
      // 6c7b: ldc_w -94.923
      // 6c7e: ldc_w -18.188
      // 6c81: ldc_w -85.077
      // 6c84: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6c87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6c8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6c8d: aastore
      // 6c8e: dup
      // 6c8f: bipush 1
      // 6c90: new net/minecraft/client/animation/Keyframe
      // 6c93: dup
      // 6c94: ldc 0.04
      // 6c96: ldc_w -95.303
      // 6c99: ldc_w -16.184
      // 6c9c: ldc_w -84.697
      // 6c9f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6ca2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ca5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ca8: aastore
      // 6ca9: dup
      // 6caa: bipush 2
      // 6cab: new net/minecraft/client/animation/Keyframe
      // 6cae: dup
      // 6caf: ldc 0.08
      // 6cb1: ldc_w -95.267
      // 6cb4: ldc_w -17.164
      // 6cb7: ldc_w -84.733
      // 6cba: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6cbd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6cc0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6cc3: aastore
      // 6cc4: dup
      // 6cc5: bipush 3
      // 6cc6: new net/minecraft/client/animation/Keyframe
      // 6cc9: dup
      // 6cca: ldc 0.12
      // 6ccc: ldc_w -95.139
      // 6ccf: ldc_w -13.251
      // 6cd2: ldc_w -84.861
      // 6cd5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6cd8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6cdb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6cde: aastore
      // 6cdf: dup
      // 6ce0: bipush 4
      // 6ce1: new net/minecraft/client/animation/Keyframe
      // 6ce4: dup
      // 6ce5: ldc 0.17
      // 6ce7: ldc_w -95.585
      // 6cea: ldc_w -3.092
      // 6ced: ldc_w -84.415
      // 6cf0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6cf3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6cf6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6cf9: aastore
      // 6cfa: dup
      // 6cfb: bipush 5
      // 6cfc: new net/minecraft/client/animation/Keyframe
      // 6cff: dup
      // 6d00: ldc 0.21
      // 6d02: ldc_w -94.56
      // 6d05: ldc_w -1.77
      // 6d08: ldc_w -85.44
      // 6d0b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6d0e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d11: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d14: aastore
      // 6d15: dup
      // 6d16: bipush 6
      // 6d18: new net/minecraft/client/animation/Keyframe
      // 6d1b: dup
      // 6d1c: ldc 0.25
      // 6d1e: ldc_w -91.974
      // 6d21: ldc_w -11.118
      // 6d24: ldc_w -88.026
      // 6d27: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6d2a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d2d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d30: aastore
      // 6d31: dup
      // 6d32: bipush 7
      // 6d34: new net/minecraft/client/animation/Keyframe
      // 6d37: dup
      // 6d38: ldc 0.29
      // 6d3a: ldc_w -90.76
      // 6d3d: ldc_w -16.462
      // 6d40: ldc_w -89.24
      // 6d43: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6d46: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d49: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d4c: aastore
      // 6d4d: dup
      // 6d4e: bipush 8
      // 6d50: new net/minecraft/client/animation/Keyframe
      // 6d53: dup
      // 6d54: ldc 0.33
      // 6d56: ldc_w -92.389
      // 6d59: ldc_w -10.483
      // 6d5c: ldc_w -87.611
      // 6d5f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6d62: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d65: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d68: aastore
      // 6d69: dup
      // 6d6a: bipush 9
      // 6d6c: new net/minecraft/client/animation/Keyframe
      // 6d6f: dup
      // 6d70: ldc 0.38
      // 6d72: ldc_w -95.533
      // 6d75: ldc_w 4.816
      // 6d78: ldc_w -84.467
      // 6d7b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6d7e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d81: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6d84: aastore
      // 6d85: dup
      // 6d86: bipush 10
      // 6d88: new net/minecraft/client/animation/Keyframe
      // 6d8b: dup
      // 6d8c: ldc 0.42
      // 6d8e: ldc_w -97.001
      // 6d91: ldc_w 11.783
      // 6d94: ldc_w -82.999
      // 6d97: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6d9a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6d9d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6da0: aastore
      // 6da1: dup
      // 6da2: bipush 11
      // 6da4: new net/minecraft/client/animation/Keyframe
      // 6da7: dup
      // 6da8: ldc 0.46
      // 6daa: ldc_w -96.979
      // 6dad: ldc_w 9.654
      // 6db0: ldc_w -83.021
      // 6db3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6db6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6db9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6dbc: aastore
      // 6dbd: dup
      // 6dbe: bipush 12
      // 6dc0: new net/minecraft/client/animation/Keyframe
      // 6dc3: dup
      // 6dc4: ldc 0.5
      // 6dc6: ldc_w -96.974
      // 6dc9: ldc_w 7.589
      // 6dcc: ldc_w -83.026
      // 6dcf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6dd2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6dd5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6dd8: aastore
      // 6dd9: dup
      // 6dda: bipush 13
      // 6ddc: new net/minecraft/client/animation/Keyframe
      // 6ddf: dup
      // 6de0: ldc 0.54
      // 6de2: ldc_w -96.982
      // 6de5: ldc_w 5.56
      // 6de8: ldc_w -83.018
      // 6deb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6dee: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6df1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6df4: aastore
      // 6df5: dup
      // 6df6: bipush 14
      // 6df8: new net/minecraft/client/animation/Keyframe
      // 6dfb: dup
      // 6dfc: ldc 0.58
      // 6dfe: ldc_w -96.997
      // 6e01: ldc_w 3.556
      // 6e04: ldc_w -83.004
      // 6e07: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6e0a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e0d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e10: aastore
      // 6e11: dup
      // 6e12: bipush 15
      // 6e14: new net/minecraft/client/animation/Keyframe
      // 6e17: dup
      // 6e18: ldc 0.62
      // 6e1a: ldc_w -97.004
      // 6e1d: ldc_w 1.59
      // 6e20: ldc_w -82.996
      // 6e23: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6e26: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e29: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e2c: aastore
      // 6e2d: dup
      // 6e2e: bipush 16
      // 6e30: new net/minecraft/client/animation/Keyframe
      // 6e33: dup
      // 6e34: ldc 0.67
      // 6e36: ldc_w -96.987
      // 6e39: ldc_w -0.303
      // 6e3c: ldc_w -83.013
      // 6e3f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6e42: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e45: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e48: aastore
      // 6e49: dup
      // 6e4a: bipush 17
      // 6e4c: new net/minecraft/client/animation/Keyframe
      // 6e4f: dup
      // 6e50: ldc 0.71
      // 6e52: ldc_w -96.922
      // 6e55: ldc_w -2.063
      // 6e58: ldc_w -83.077
      // 6e5b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6e5e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e61: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e64: aastore
      // 6e65: dup
      // 6e66: bipush 18
      // 6e68: new net/minecraft/client/animation/Keyframe
      // 6e6b: dup
      // 6e6c: ldc 0.75
      // 6e6e: ldc_w -96.784
      // 6e71: ldc_w -3.61
      // 6e74: ldc_w -83.216
      // 6e77: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6e7a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e7d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e80: aastore
      // 6e81: dup
      // 6e82: bipush 19
      // 6e84: new net/minecraft/client/animation/Keyframe
      // 6e87: dup
      // 6e88: ldc 0.79
      // 6e8a: ldc_w -96.545
      // 6e8d: ldc_w -4.852
      // 6e90: ldc_w -83.454
      // 6e93: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6e96: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6e99: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6e9c: aastore
      // 6e9d: dup
      // 6e9e: bipush 20
      // 6ea0: new net/minecraft/client/animation/Keyframe
      // 6ea3: dup
      // 6ea4: ldc 0.83
      // 6ea6: ldc_w -96.239
      // 6ea9: ldc_w -5.115
      // 6eac: ldc_w -83.761
      // 6eaf: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6eb2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6eb5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6eb8: aastore
      // 6eb9: dup
      // 6eba: bipush 21
      // 6ebc: new net/minecraft/client/animation/Keyframe
      // 6ebf: dup
      // 6ec0: ldc 0.88
      // 6ec2: ldc_w -95.888
      // 6ec5: ldc_w -3.896
      // 6ec8: ldc_w -84.111
      // 6ecb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6ece: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6ed1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ed4: aastore
      // 6ed5: dup
      // 6ed6: bipush 22
      // 6ed8: new net/minecraft/client/animation/Keyframe
      // 6edb: dup
      // 6edc: ldc 0.92
      // 6ede: ldc_w -95.599
      // 6ee1: ldc_w -1.709
      // 6ee4: ldc_w -84.401
      // 6ee7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6eea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6eed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6ef0: aastore
      // 6ef1: dup
      // 6ef2: bipush 23
      // 6ef4: new net/minecraft/client/animation/Keyframe
      // 6ef7: dup
      // 6ef8: ldc 0.96
      // 6efa: ldc_w -95.471
      // 6efd: ldc_w 0.637
      // 6f00: ldc_w -84.531
      // 6f03: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6f06: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f09: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f0c: aastore
      // 6f0d: dup
      // 6f0e: bipush 24
      // 6f10: new net/minecraft/client/animation/Keyframe
      // 6f13: dup
      // 6f14: fconst_1
      // 6f15: ldc_w -95.461
      // 6f18: ldc_w 2.94
      // 6f1b: ldc_w -84.539
      // 6f1e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6f21: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f24: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f27: aastore
      // 6f28: dup
      // 6f29: bipush 25
      // 6f2b: new net/minecraft/client/animation/Keyframe
      // 6f2e: dup
      // 6f2f: ldc 1.04
      // 6f31: ldc_w -95.533
      // 6f34: ldc_w 5.084
      // 6f37: ldc_w -84.467
      // 6f3a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6f3d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f40: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f43: aastore
      // 6f44: dup
      // 6f45: bipush 26
      // 6f47: new net/minecraft/client/animation/Keyframe
      // 6f4a: dup
      // 6f4b: ldc 1.08
      // 6f4d: ldc_w -95.645
      // 6f50: ldc_w 6.966
      // 6f53: ldc_w -84.356
      // 6f56: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6f59: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f5c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f5f: aastore
      // 6f60: dup
      // 6f61: bipush 27
      // 6f63: new net/minecraft/client/animation/Keyframe
      // 6f66: dup
      // 6f67: ldc 1.12
      // 6f69: ldc_w -95.757
      // 6f6c: ldc_w 8.489
      // 6f6f: ldc_w -84.243
      // 6f72: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6f75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f7b: aastore
      // 6f7c: dup
      // 6f7d: bipush 28
      // 6f7f: new net/minecraft/client/animation/Keyframe
      // 6f82: dup
      // 6f83: ldc 1.17
      // 6f85: ldc_w -95.831
      // 6f88: ldc_w 9.56
      // 6f8b: ldc_w -84.169
      // 6f8e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6f91: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6f94: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6f97: aastore
      // 6f98: dup
      // 6f99: bipush 29
      // 6f9b: new net/minecraft/client/animation/Keyframe
      // 6f9e: dup
      // 6f9f: ldc 1.21
      // 6fa1: ldc_w -95.832
      // 6fa4: ldc_w 10.097
      // 6fa7: ldc_w -84.169
      // 6faa: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6fad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6fb0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6fb3: aastore
      // 6fb4: dup
      // 6fb5: bipush 30
      // 6fb7: new net/minecraft/client/animation/Keyframe
      // 6fba: dup
      // 6fbb: ldc 1.25
      // 6fbd: ldc_w -95.735
      // 6fc0: ldc_w 10.036
      // 6fc3: ldc_w -84.265
      // 6fc6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6fc9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6fcc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6fcf: aastore
      // 6fd0: dup
      // 6fd1: bipush 31
      // 6fd3: new net/minecraft/client/animation/Keyframe
      // 6fd6: dup
      // 6fd7: ldc 1.29
      // 6fd9: ldc_w -95.534
      // 6fdc: ldc_w 9.36
      // 6fdf: ldc_w -84.466
      // 6fe2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 6fe5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 6fe8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 6feb: aastore
      // 6fec: dup
      // 6fed: bipush 32
      // 6fef: new net/minecraft/client/animation/Keyframe
      // 6ff2: dup
      // 6ff3: ldc 1.33
      // 6ff5: ldc_w -95.241
      // 6ff8: ldc_w 8.215
      // 6ffb: ldc_w -84.759
      // 6ffe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7001: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7004: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7007: aastore
      // 7008: dup
      // 7009: bipush 33
      // 700b: new net/minecraft/client/animation/Keyframe
      // 700e: dup
      // 700f: ldc 1.38
      // 7011: ldc_w -94.957
      // 7014: ldc_w 7.259
      // 7017: ldc_w -85.043
      // 701a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 701d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7020: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7023: aastore
      // 7024: dup
      // 7025: bipush 34
      // 7027: new net/minecraft/client/animation/Keyframe
      // 702a: dup
      // 702b: ldc 1.42
      // 702d: ldc_w -94.733
      // 7030: ldc_w 6.697
      // 7033: ldc_w -85.267
      // 7036: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7039: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 703c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 703f: aastore
      // 7040: dup
      // 7041: bipush 35
      // 7043: new net/minecraft/client/animation/Keyframe
      // 7046: dup
      // 7047: ldc 1.46
      // 7049: ldc_w -94.546
      // 704c: ldc_w 6.507
      // 704f: ldc_w -85.454
      // 7052: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7055: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7058: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 705b: aastore
      // 705c: dup
      // 705d: bipush 36
      // 705f: new net/minecraft/client/animation/Keyframe
      // 7062: dup
      // 7063: ldc 1.5
      // 7065: ldc_w -94.384
      // 7068: ldc_w 6.684
      // 706b: ldc_w -85.616
      // 706e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7071: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7074: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7077: aastore
      // 7078: dup
      // 7079: bipush 37
      // 707b: new net/minecraft/client/animation/Keyframe
      // 707e: dup
      // 707f: ldc 1.54
      // 7081: ldc_w -94.243
      // 7084: ldc_w 7.227
      // 7087: ldc_w -85.757
      // 708a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 708d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7090: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7093: aastore
      // 7094: dup
      // 7095: bipush 38
      // 7097: new net/minecraft/client/animation/Keyframe
      // 709a: dup
      // 709b: ldc 1.58
      // 709d: ldc_w -94.122
      // 70a0: ldc_w 8.138
      // 70a3: ldc_w -85.878
      // 70a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 70a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 70ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 70af: aastore
      // 70b0: dup
      // 70b1: bipush 39
      // 70b3: new net/minecraft/client/animation/Keyframe
      // 70b6: dup
      // 70b7: ldc 1.62
      // 70b9: ldc_w -94.023
      // 70bc: ldc_w 9.417
      // 70bf: ldc_w -85.977
      // 70c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 70c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 70c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 70cb: aastore
      // 70cc: dup
      // 70cd: bipush 40
      // 70cf: new net/minecraft/client/animation/Keyframe
      // 70d2: dup
      // 70d3: ldc 1.67
      // 70d5: ldc_w -93.947
      // 70d8: ldc_w 11.06
      // 70db: ldc_w -86.053
      // 70de: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 70e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 70e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 70e7: aastore
      // 70e8: dup
      // 70e9: bipush 41
      // 70eb: new net/minecraft/client/animation/Keyframe
      // 70ee: dup
      // 70ef: ldc_w 1.71
      // 70f2: ldc_w -93.901
      // 70f5: ldc_w 13.056
      // 70f8: ldc_w -86.099
      // 70fb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 70fe: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7101: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7104: aastore
      // 7105: dup
      // 7106: bipush 42
      // 7108: new net/minecraft/client/animation/Keyframe
      // 710b: dup
      // 710c: ldc_w 1.75
      // 710f: ldc_w -93.89
      // 7112: ldc_w 15.383
      // 7115: ldc_w -86.11
      // 7118: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 711b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 711e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7121: aastore
      // 7122: dup
      // 7123: bipush 43
      // 7125: new net/minecraft/client/animation/Keyframe
      // 7128: dup
      // 7129: ldc_w 1.79
      // 712c: ldc_w -93.923
      // 712f: ldc_w 18.003
      // 7132: ldc_w -86.077
      // 7135: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7138: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 713b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 713e: aastore
      // 713f: dup
      // 7140: bipush 44
      // 7142: new net/minecraft/client/animation/Keyframe
      // 7145: dup
      // 7146: ldc_w 1.83
      // 7149: ldc_w -94.011
      // 714c: ldc_w 20.849
      // 714f: ldc_w -85.989
      // 7152: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7155: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7158: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 715b: aastore
      // 715c: dup
      // 715d: bipush 45
      // 715f: new net/minecraft/client/animation/Keyframe
      // 7162: dup
      // 7163: ldc_w 1.88
      // 7166: ldc_w -94.17
      // 7169: ldc_w 23.813
      // 716c: ldc_w -85.83
      // 716f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7172: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7175: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7178: aastore
      // 7179: dup
      // 717a: bipush 46
      // 717c: new net/minecraft/client/animation/Keyframe
      // 717f: dup
      // 7180: ldc_w 1.92
      // 7183: ldc_w -94.418
      // 7186: ldc_w 26.715
      // 7189: ldc_w -85.582
      // 718c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 718f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7192: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7195: aastore
      // 7196: dup
      // 7197: bipush 47
      // 7199: new net/minecraft/client/animation/Keyframe
      // 719c: dup
      // 719d: ldc_w 1.96
      // 71a0: ldc_w -94.778
      // 71a3: ldc_w 29.257
      // 71a6: ldc_w -85.222
      // 71a9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 71ac: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 71af: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 71b2: aastore
      // 71b3: dup
      // 71b4: bipush 48
      // 71b6: new net/minecraft/client/animation/Keyframe
      // 71b9: dup
      // 71ba: fconst_2
      // 71bb: ldc_w -95.265
      // 71be: ldc_w 30.935
      // 71c1: ldc_w -84.735
      // 71c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 71c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 71ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 71cd: aastore
      // 71ce: dup
      // 71cf: bipush 49
      // 71d1: new net/minecraft/client/animation/Keyframe
      // 71d4: dup
      // 71d5: ldc 2.04
      // 71d7: ldc_w -95.861
      // 71da: ldc_w 30.899
      // 71dd: ldc_w -84.139
      // 71e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 71e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 71e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 71e9: aastore
      // 71ea: dup
      // 71eb: bipush 50
      // 71ed: new net/minecraft/client/animation/Keyframe
      // 71f0: dup
      // 71f1: ldc 2.08
      // 71f3: ldc_w -96.44
      // 71f6: ldc_w 27.777
      // 71f9: ldc_w -83.56
      // 71fc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 71ff: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7202: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7205: aastore
      // 7206: dup
      // 7207: bipush 51
      // 7209: new net/minecraft/client/animation/Keyframe
      // 720c: dup
      // 720d: ldc 2.12
      // 720f: ldc_w -96.556
      // 7212: ldc_w 19.081
      // 7215: ldc_w -83.444
      // 7218: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 721b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 721e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7221: aastore
      // 7222: dup
      // 7223: bipush 52
      // 7225: new net/minecraft/client/animation/Keyframe
      // 7228: dup
      // 7229: ldc 2.17
      // 722b: ldc_w -96.304
      // 722e: ldc_w 41.243
      // 7231: ldc_w -83.696
      // 7234: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7237: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 723a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 723d: aastore
      // 723e: dup
      // 723f: bipush 53
      // 7241: new net/minecraft/client/animation/Keyframe
      // 7244: dup
      // 7245: ldc 2.21
      // 7247: ldc_w -96.074
      // 724a: ldc_w 36.645
      // 724d: ldc_w -83.926
      // 7250: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7253: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7256: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7259: aastore
      // 725a: dup
      // 725b: bipush 54
      // 725d: new net/minecraft/client/animation/Keyframe
      // 7260: dup
      // 7261: ldc 2.25
      // 7263: ldc_w -96.033
      // 7266: ldc_w 32.268
      // 7269: ldc_w -83.967
      // 726c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 726f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7272: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7275: aastore
      // 7276: dup
      // 7277: bipush 55
      // 7279: new net/minecraft/client/animation/Keyframe
      // 727c: dup
      // 727d: ldc 2.29
      // 727f: ldc_w -96.003
      // 7282: ldc_w 24.98
      // 7285: ldc_w -83.997
      // 7288: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 728b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 728e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7291: aastore
      // 7292: dup
      // 7293: bipush 56
      // 7295: new net/minecraft/client/animation/Keyframe
      // 7298: dup
      // 7299: ldc 2.33
      // 729b: ldc_w -95.914
      // 729e: ldc_w 31.613
      // 72a1: ldc_w -84.086
      // 72a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 72a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 72aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 72ad: aastore
      // 72ae: dup
      // 72af: bipush 57
      // 72b1: new net/minecraft/client/animation/Keyframe
      // 72b4: dup
      // 72b5: ldc 2.38
      // 72b7: ldc_w -95.737
      // 72ba: ldc_w 28.471
      // 72bd: ldc_w -84.263
      // 72c0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 72c3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 72c6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 72c9: aastore
      // 72ca: dup
      // 72cb: bipush 58
      // 72cd: new net/minecraft/client/animation/Keyframe
      // 72d0: dup
      // 72d1: ldc 2.42
      // 72d3: ldc_w -95.435
      // 72d6: ldc_w 22.671
      // 72d9: ldc_w -84.565
      // 72dc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 72df: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 72e2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 72e5: aastore
      // 72e6: dup
      // 72e7: bipush 59
      // 72e9: new net/minecraft/client/animation/Keyframe
      // 72ec: dup
      // 72ed: ldc 2.46
      // 72ef: ldc_w -95.285
      // 72f2: ldc_w 22.526
      // 72f5: ldc_w -84.715
      // 72f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 72fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 72fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7301: aastore
      // 7302: dup
      // 7303: bipush 60
      // 7305: new net/minecraft/client/animation/Keyframe
      // 7308: dup
      // 7309: ldc 2.5
      // 730b: ldc_w -95.129
      // 730e: ldc_w 21.106
      // 7311: ldc_w -84.871
      // 7314: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7317: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 731a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 731d: aastore
      // 731e: dup
      // 731f: bipush 61
      // 7321: new net/minecraft/client/animation/Keyframe
      // 7324: dup
      // 7325: ldc 2.54
      // 7327: ldc_w -94.983
      // 732a: ldc_w 19.784
      // 732d: ldc_w -85.017
      // 7330: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 7333: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7336: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7339: aastore
      // 733a: dup
      // 733b: bipush 62
      // 733d: new net/minecraft/client/animation/Keyframe
      // 7340: dup
      // 7341: ldc 2.58
      // 7343: ldc_w -94.877
      // 7346: ldc_w 18.83
      // 7349: ldc_w -85.123
      // 734c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 734f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7352: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7355: aastore
      // 7356: dup
      // 7357: bipush 63
      // 7359: new net/minecraft/client/animation/Keyframe
      // 735c: dup
      // 735d: ldc 2.62
      // 735f: ldc_w -94.839
      // 7362: ldc_w 18.489
      // 7365: ldc_w -85.161
      // 7368: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 736b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 736e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7371: aastore
      // 7372: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 7375: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 7378: ldc_w "CTR_leg_right"
      // 737b: new net/minecraft/client/animation/AnimationChannel
      // 737e: dup
      // 737f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 7382: bipush 64
      // 7384: anewarray 24
      // 7387: dup
      // 7388: bipush 0
      // 7389: new net/minecraft/client/animation/Keyframe
      // 738c: dup
      // 738d: fconst_0
      // 738e: ldc_w 1.587
      // 7391: ldc_w 7.256
      // 7394: ldc_w -1.851
      // 7397: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 739a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 739d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 73a0: aastore
      // 73a1: dup
      // 73a2: bipush 1
      // 73a3: new net/minecraft/client/animation/Keyframe
      // 73a6: dup
      // 73a7: ldc 0.04
      // 73a9: ldc_w 1.749
      // 73ac: ldc_w 7.689
      // 73af: ldc_w -1.959
      // 73b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 73b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 73b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 73bb: aastore
      // 73bc: dup
      // 73bd: bipush 2
      // 73be: new net/minecraft/client/animation/Keyframe
      // 73c1: dup
      // 73c2: ldc 0.08
      // 73c4: ldc_w 1.577
      // 73c7: ldc_w 6.809
      // 73ca: ldc_w -2.11
      // 73cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 73d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 73d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 73d6: aastore
      // 73d7: dup
      // 73d8: bipush 3
      // 73d9: new net/minecraft/client/animation/Keyframe
      // 73dc: dup
      // 73dd: ldc 0.12
      // 73df: ldc_w 1.395
      // 73e2: ldc_w 6.021
      // 73e5: ldc_w -2.722
      // 73e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 73eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 73ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 73f1: aastore
      // 73f2: dup
      // 73f3: bipush 4
      // 73f4: new net/minecraft/client/animation/Keyframe
      // 73f7: dup
      // 73f8: ldc 0.17
      // 73fa: ldc_w 1.657
      // 73fd: ldc_w 7.008
      // 7400: ldc_w -3.569
      // 7403: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7406: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7409: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 740c: aastore
      // 740d: dup
      // 740e: bipush 5
      // 740f: new net/minecraft/client/animation/Keyframe
      // 7412: dup
      // 7413: ldc 0.21
      // 7415: ldc_w 1.382
      // 7418: ldc_w 6.652
      // 741b: ldc_w -3.719
      // 741e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7421: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7424: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7427: aastore
      // 7428: dup
      // 7429: bipush 6
      // 742b: new net/minecraft/client/animation/Keyframe
      // 742e: dup
      // 742f: ldc 0.25
      // 7431: ldc_w 0.82
      // 7434: ldc_w 5.818
      // 7437: ldc_w -2.966
      // 743a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 743d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7440: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7443: aastore
      // 7444: dup
      // 7445: bipush 7
      // 7447: new net/minecraft/client/animation/Keyframe
      // 744a: dup
      // 744b: ldc 0.29
      // 744d: ldc_w 0.613
      // 7450: ldc_w 5.544
      // 7453: ldc_w -2.583
      // 7456: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7459: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 745c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 745f: aastore
      // 7460: dup
      // 7461: bipush 8
      // 7463: new net/minecraft/client/animation/Keyframe
      // 7466: dup
      // 7467: ldc 0.33
      // 7469: ldc_w 0.967
      // 746c: ldc_w 6.743
      // 746f: ldc_w -2.848
      // 7472: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7475: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7478: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 747b: aastore
      // 747c: dup
      // 747d: bipush 9
      // 747f: new net/minecraft/client/animation/Keyframe
      // 7482: dup
      // 7483: ldc 0.38
      // 7485: ldc_w 1.582
      // 7488: ldc_w 6.676
      // 748b: ldc_w -4.367
      // 748e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7491: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7494: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7497: aastore
      // 7498: dup
      // 7499: bipush 10
      // 749b: new net/minecraft/client/animation/Keyframe
      // 749e: dup
      // 749f: ldc 0.42
      // 74a1: ldc_w 1.692
      // 74a4: ldc_w 5.873
      // 74a7: ldc_w -4.907
      // 74aa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 74ad: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 74b0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 74b3: aastore
      // 74b4: dup
      // 74b5: bipush 11
      // 74b7: new net/minecraft/client/animation/Keyframe
      // 74ba: dup
      // 74bb: ldc 0.46
      // 74bd: ldc_w 1.851
      // 74c0: ldc_w 6.541
      // 74c3: ldc_w -4.835
      // 74c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 74c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 74cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 74cf: aastore
      // 74d0: dup
      // 74d1: bipush 12
      // 74d3: new net/minecraft/client/animation/Keyframe
      // 74d6: dup
      // 74d7: ldc 0.5
      // 74d9: ldc_w 1.997
      // 74dc: ldc_w 7.145
      // 74df: ldc_w -4.713
      // 74e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 74e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 74e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 74eb: aastore
      // 74ec: dup
      // 74ed: bipush 13
      // 74ef: new net/minecraft/client/animation/Keyframe
      // 74f2: dup
      // 74f3: ldc 0.54
      // 74f5: ldc_w 2.125
      // 74f8: ldc_w 7.665
      // 74fb: ldc_w -4.544
      // 74fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7501: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7504: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7507: aastore
      // 7508: dup
      // 7509: bipush 14
      // 750b: new net/minecraft/client/animation/Keyframe
      // 750e: dup
      // 750f: ldc 0.58
      // 7511: ldc_w 2.231
      // 7514: ldc_w 8.083
      // 7517: ldc_w -4.334
      // 751a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 751d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7520: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7523: aastore
      // 7524: dup
      // 7525: bipush 15
      // 7527: new net/minecraft/client/animation/Keyframe
      // 752a: dup
      // 752b: ldc 0.62
      // 752d: ldc_w 2.306
      // 7530: ldc_w 8.383
      // 7533: ldc_w -4.098
      // 7536: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7539: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 753c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 753f: aastore
      // 7540: dup
      // 7541: bipush 16
      // 7543: new net/minecraft/client/animation/Keyframe
      // 7546: dup
      // 7547: ldc 0.67
      // 7549: ldc_w 2.343
      // 754c: ldc_w 8.553
      // 754f: ldc_w -3.851
      // 7552: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7555: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7558: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 755b: aastore
      // 755c: dup
      // 755d: bipush 17
      // 755f: new net/minecraft/client/animation/Keyframe
      // 7562: dup
      // 7563: ldc 0.71
      // 7565: ldc_w 2.334
      // 7568: ldc_w 8.585
      // 756b: ldc_w -3.617
      // 756e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7571: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7574: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7577: aastore
      // 7578: dup
      // 7579: bipush 18
      // 757b: new net/minecraft/client/animation/Keyframe
      // 757e: dup
      // 757f: ldc 0.75
      // 7581: ldc_w 2.271
      // 7584: ldc_w 8.478
      // 7587: ldc_w -3.417
      // 758a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 758d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7590: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7593: aastore
      // 7594: dup
      // 7595: bipush 19
      // 7597: new net/minecraft/client/animation/Keyframe
      // 759a: dup
      // 759b: ldc 0.79
      // 759d: ldc_w 2.152
      // 75a0: ldc_w 8.237
      // 75a3: ldc_w -3.275
      // 75a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 75a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 75ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 75af: aastore
      // 75b0: dup
      // 75b1: bipush 20
      // 75b3: new net/minecraft/client/animation/Keyframe
      // 75b6: dup
      // 75b7: ldc 0.83
      // 75b9: ldc_w 1.992
      // 75bc: ldc_w 7.879
      // 75bf: ldc_w -3.275
      // 75c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 75c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 75c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 75cb: aastore
      // 75cc: dup
      // 75cd: bipush 21
      // 75cf: new net/minecraft/client/animation/Keyframe
      // 75d2: dup
      // 75d3: ldc 0.88
      // 75d5: ldc_w 1.811
      // 75d8: ldc_w 7.432
      // 75db: ldc_w -3.455
      // 75de: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 75e1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 75e4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 75e7: aastore
      // 75e8: dup
      // 75e9: bipush 22
      // 75eb: new net/minecraft/client/animation/Keyframe
      // 75ee: dup
      // 75ef: ldc 0.92
      // 75f1: ldc_w 1.623
      // 75f4: ldc_w 6.825
      // 75f7: ldc_w -3.719
      // 75fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 75fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7600: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7603: aastore
      // 7604: dup
      // 7605: bipush 23
      // 7607: new net/minecraft/client/animation/Keyframe
      // 760a: dup
      // 760b: ldc 0.96
      // 760d: ldc_w 1.465
      // 7610: ldc_w 6.148
      // 7613: ldc_w -3.948
      // 7616: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7619: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 761c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 761f: aastore
      // 7620: dup
      // 7621: bipush 24
      // 7623: new net/minecraft/client/animation/Keyframe
      // 7626: dup
      // 7627: fconst_1
      // 7628: ldc_w 1.333
      // 762b: ldc_w 5.467
      // 762e: ldc_w -4.118
      // 7631: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7634: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7637: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 763a: aastore
      // 763b: dup
      // 763c: bipush 25
      // 763e: new net/minecraft/client/animation/Keyframe
      // 7641: dup
      // 7642: ldc 1.04
      // 7644: ldc_w 1.213
      // 7647: ldc_w 4.792
      // 764a: ldc_w -4.223
      // 764d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7650: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7653: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7656: aastore
      // 7657: dup
      // 7658: bipush 26
      // 765a: new net/minecraft/client/animation/Keyframe
      // 765d: dup
      // 765e: ldc 1.08
      // 7660: ldc_w 1.097
      // 7663: ldc_w 4.132
      // 7666: ldc_w -4.265
      // 7669: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 766c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 766f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7672: aastore
      // 7673: dup
      // 7674: bipush 27
      // 7676: new net/minecraft/client/animation/Keyframe
      // 7679: dup
      // 767a: ldc 1.12
      // 767c: ldc_w 0.98
      // 767f: ldc_w 3.496
      // 7682: ldc_w -4.252
      // 7685: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7688: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 768b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 768e: aastore
      // 768f: dup
      // 7690: bipush 28
      // 7692: new net/minecraft/client/animation/Keyframe
      // 7695: dup
      // 7696: ldc 1.17
      // 7698: ldc_w 0.864
      // 769b: ldc_w 2.903
      // 769e: ldc_w -4.197
      // 76a1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 76a4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 76a7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 76aa: aastore
      // 76ab: dup
      // 76ac: bipush 29
      // 76ae: new net/minecraft/client/animation/Keyframe
      // 76b1: dup
      // 76b2: ldc 1.21
      // 76b4: ldc_w 0.755
      // 76b7: ldc_w 2.378
      // 76ba: ldc_w -4.12
      // 76bd: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 76c0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 76c3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 76c6: aastore
      // 76c7: dup
      // 76c8: bipush 30
      // 76ca: new net/minecraft/client/animation/Keyframe
      // 76cd: dup
      // 76ce: ldc 1.25
      // 76d0: ldc_w 0.664
      // 76d3: ldc_w 1.952
      // 76d6: ldc_w -4.042
      // 76d9: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 76dc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 76df: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 76e2: aastore
      // 76e3: dup
      // 76e4: bipush 31
      // 76e6: new net/minecraft/client/animation/Keyframe
      // 76e9: dup
      // 76ea: ldc 1.29
      // 76ec: ldc_w 0.599
      // 76ef: ldc_w 1.656
      // 76f2: ldc_w -3.982
      // 76f5: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 76f8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 76fb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 76fe: aastore
      // 76ff: dup
      // 7700: bipush 32
      // 7702: new net/minecraft/client/animation/Keyframe
      // 7705: dup
      // 7706: ldc 1.33
      // 7708: ldc_w 0.566
      // 770b: ldc_w 1.507
      // 770e: ldc_w -3.949
      // 7711: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7714: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7717: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 771a: aastore
      // 771b: dup
      // 771c: bipush 33
      // 771e: new net/minecraft/client/animation/Keyframe
      // 7721: dup
      // 7722: ldc 1.38
      // 7724: ldc_w 0.555
      // 7727: ldc 1.468
      // 7729: ldc_w -3.937
      // 772c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 772f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7732: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7735: aastore
      // 7736: dup
      // 7737: bipush 34
      // 7739: new net/minecraft/client/animation/Keyframe
      // 773c: dup
      // 773d: ldc 1.42
      // 773f: ldc_w 0.561
      // 7742: ldc_w 1.523
      // 7745: ldc_w -3.94
      // 7748: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 774b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 774e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7751: aastore
      // 7752: dup
      // 7753: bipush 35
      // 7755: new net/minecraft/client/animation/Keyframe
      // 7758: dup
      // 7759: ldc 1.46
      // 775b: ldc_w 0.581
      // 775e: ldc_w 1.666
      // 7761: ldc_w -3.955
      // 7764: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7767: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 776a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 776d: aastore
      // 776e: dup
      // 776f: bipush 36
      // 7771: new net/minecraft/client/animation/Keyframe
      // 7774: dup
      // 7775: ldc 1.5
      // 7777: ldc_w 0.614
      // 777a: ldc_w 1.897
      // 777d: ldc_w -3.984
      // 7780: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7783: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7786: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7789: aastore
      // 778a: dup
      // 778b: bipush 37
      // 778d: new net/minecraft/client/animation/Keyframe
      // 7790: dup
      // 7791: ldc 1.54
      // 7793: ldc_w 0.658
      // 7796: ldc_w 2.217
      // 7799: ldc_w -4.032
      // 779c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 779f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 77a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 77a5: aastore
      // 77a6: dup
      // 77a7: bipush 38
      // 77a9: new net/minecraft/client/animation/Keyframe
      // 77ac: dup
      // 77ad: ldc 1.58
      // 77af: ldc_w 0.713
      // 77b2: ldc_w 2.628
      // 77b5: ldc_w -4.109
      // 77b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 77bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 77be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 77c1: aastore
      // 77c2: dup
      // 77c3: bipush 39
      // 77c5: new net/minecraft/client/animation/Keyframe
      // 77c8: dup
      // 77c9: ldc 1.62
      // 77cb: ldc_w 0.78
      // 77ce: ldc_w 3.131
      // 77d1: ldc_w -4.228
      // 77d4: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 77d7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 77da: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 77dd: aastore
      // 77de: dup
      // 77df: bipush 40
      // 77e1: new net/minecraft/client/animation/Keyframe
      // 77e4: dup
      // 77e5: ldc 1.67
      // 77e7: ldc_w 0.858
      // 77ea: ldc_w 3.727
      // 77ed: ldc_w -4.406
      // 77f0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 77f3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 77f6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 77f9: aastore
      // 77fa: dup
      // 77fb: bipush 41
      // 77fd: new net/minecraft/client/animation/Keyframe
      // 7800: dup
      // 7801: ldc_w 1.71
      // 7804: ldc_w 0.951
      // 7807: ldc_w 4.414
      // 780a: ldc_w -4.662
      // 780d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7810: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7813: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7816: aastore
      // 7817: dup
      // 7818: bipush 42
      // 781a: new net/minecraft/client/animation/Keyframe
      // 781d: dup
      // 781e: ldc_w 1.75
      // 7821: ldc_w 1.06
      // 7824: ldc_w 5.188
      // 7827: ldc_w -5.02
      // 782a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 782d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7830: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7833: aastore
      // 7834: dup
      // 7835: bipush 43
      // 7837: new net/minecraft/client/animation/Keyframe
      // 783a: dup
      // 783b: ldc_w 1.79
      // 783e: ldc_w 1.189
      // 7841: ldc_w 6.043
      // 7844: ldc_w -5.504
      // 7847: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 784a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 784d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7850: aastore
      // 7851: dup
      // 7852: bipush 44
      // 7854: new net/minecraft/client/animation/Keyframe
      // 7857: dup
      // 7858: ldc_w 1.83
      // 785b: ldc_w 1.347
      // 785e: ldc_w 6.967
      // 7861: ldc_w -6.137
      // 7864: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7867: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 786a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 786d: aastore
      // 786e: dup
      // 786f: bipush 45
      // 7871: new net/minecraft/client/animation/Keyframe
      // 7874: dup
      // 7875: ldc_w 1.88
      // 7878: ldc_w 1.541
      // 787b: ldc_w 7.946
      // 787e: ldc_w -6.931
      // 7881: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7884: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7887: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 788a: aastore
      // 788b: dup
      // 788c: bipush 46
      // 788e: new net/minecraft/client/animation/Keyframe
      // 7891: dup
      // 7892: ldc_w 1.92
      // 7895: ldc_w 1.787
      // 7898: ldc_w 8.968
      // 789b: ldc_w -7.882
      // 789e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 78a1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 78a4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 78a7: aastore
      // 78a8: dup
      // 78a9: bipush 47
      // 78ab: new net/minecraft/client/animation/Keyframe
      // 78ae: dup
      // 78af: ldc_w 1.96
      // 78b2: ldc_w 2.105
      // 78b5: ldc_w 10.029
      // 78b8: ldc_w -8.942
      // 78bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 78be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 78c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 78c4: aastore
      // 78c5: dup
      // 78c6: bipush 48
      // 78c8: new net/minecraft/client/animation/Keyframe
      // 78cb: dup
      // 78cc: fconst_2
      // 78cd: ldc_w 2.514
      // 78d0: ldc_w 11.143
      // 78d3: ldc_w -9.986
      // 78d6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 78d9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 78dc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 78df: aastore
      // 78e0: dup
      // 78e1: bipush 49
      // 78e3: new net/minecraft/client/animation/Keyframe
      // 78e6: dup
      // 78e7: ldc 2.04
      // 78e9: ldc_w 3.02
      // 78ec: ldc_w 12.347
      // 78ef: ldc_w -10.732
      // 78f2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 78f5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 78f8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 78fb: aastore
      // 78fc: dup
      // 78fd: bipush 50
      // 78ff: new net/minecraft/client/animation/Keyframe
      // 7902: dup
      // 7903: ldc 2.08
      // 7905: ldc_w 3.542
      // 7908: ldc_w 13.614
      // 790b: ldc_w -10.613
      // 790e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7911: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7914: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7917: aastore
      // 7918: dup
      // 7919: bipush 51
      // 791b: new net/minecraft/client/animation/Keyframe
      // 791e: dup
      // 791f: ldc 2.12
      // 7921: ldc_w 3.614
      // 7924: ldc_w 14.127
      // 7927: ldc_w -8.486
      // 792a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 792d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7930: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7933: aastore
      // 7934: dup
      // 7935: bipush 52
      // 7937: new net/minecraft/client/animation/Keyframe
      // 793a: dup
      // 793b: ldc 2.17
      // 793d: ldc_w 2.608
      // 7940: ldc_w 9.199
      // 7943: ldc_w -11.139
      // 7946: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7949: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 794c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 794f: aastore
      // 7950: dup
      // 7951: bipush 53
      // 7953: new net/minecraft/client/animation/Keyframe
      // 7956: dup
      // 7957: ldc 2.21
      // 7959: ldc_w 2.507
      // 795c: ldc_w 9.422
      // 795f: ldc_w -10.194
      // 7962: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7965: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7968: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 796b: aastore
      // 796c: dup
      // 796d: bipush 54
      // 796f: new net/minecraft/client/animation/Keyframe
      // 7972: dup
      // 7973: ldc 2.25
      // 7975: ldc_w 2.557
      // 7978: ldc_w 9.947
      // 797b: ldc_w -9.573
      // 797e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7981: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7984: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7987: aastore
      // 7988: dup
      // 7989: bipush 55
      // 798b: new net/minecraft/client/animation/Keyframe
      // 798e: dup
      // 798f: ldc 2.29
      // 7991: ldc_w 2.632
      // 7994: ldc_w 10.669
      // 7997: ldc_w -8.422
      // 799a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 799d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 79a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 79a3: aastore
      // 79a4: dup
      // 79a5: bipush 56
      // 79a7: new net/minecraft/client/animation/Keyframe
      // 79aa: dup
      // 79ab: ldc 2.33
      // 79ad: ldc_w 2.301
      // 79b0: ldc_w 9.043
      // 79b3: ldc_w -8.859
      // 79b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 79b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 79bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 79bf: aastore
      // 79c0: dup
      // 79c1: bipush 57
      // 79c3: new net/minecraft/client/animation/Keyframe
      // 79c6: dup
      // 79c7: ldc 2.38
      // 79c9: ldc_w 2.075
      // 79cc: ldc_w 8.245
      // 79cf: ldc_w -7.977
      // 79d2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 79d5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 79d8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 79db: aastore
      // 79dc: dup
      // 79dd: bipush 58
      // 79df: new net/minecraft/client/animation/Keyframe
      // 79e2: dup
      // 79e3: ldc 2.42
      // 79e5: ldc_w 1.612
      // 79e8: ldc_w 6.258
      // 79eb: ldc_w -6.418
      // 79ee: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 79f1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 79f4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 79f7: aastore
      // 79f8: dup
      // 79f9: bipush 59
      // 79fb: new net/minecraft/client/animation/Keyframe
      // 79fe: dup
      // 79ff: ldc 2.46
      // 7a01: ldc_w 1.263
      // 7a04: ldc_w 4.307
      // 7a07: ldc_w -5.864
      // 7a0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7a0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7a10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7a13: aastore
      // 7a14: dup
      // 7a15: bipush 60
      // 7a17: new net/minecraft/client/animation/Keyframe
      // 7a1a: dup
      // 7a1b: ldc 2.5
      // 7a1d: ldc_w 0.931
      // 7a20: ldc_w 2.479
      // 7a23: ldc_w -5.193
      // 7a26: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7a29: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7a2c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7a2f: aastore
      // 7a30: dup
      // 7a31: bipush 61
      // 7a33: new net/minecraft/client/animation/Keyframe
      // 7a36: dup
      // 7a37: ldc 2.54
      // 7a39: ldc_w 0.63
      // 7a3c: ldc_w 0.886
      // 7a3f: ldc_w -4.559
      // 7a42: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7a45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7a48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7a4b: aastore
      // 7a4c: dup
      // 7a4d: bipush 62
      // 7a4f: new net/minecraft/client/animation/Keyframe
      // 7a52: dup
      // 7a53: ldc 2.58
      // 7a55: ldc_w 0.413
      // 7a58: ldc_w -0.246
      // 7a5b: ldc_w -4.098
      // 7a5e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7a61: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7a64: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7a67: aastore
      // 7a68: dup
      // 7a69: bipush 63
      // 7a6b: new net/minecraft/client/animation/Keyframe
      // 7a6e: dup
      // 7a6f: ldc 2.62
      // 7a71: ldc_w 0.333
      // 7a74: ldc_w -0.682
      // 7a77: ldc_w -3.933
      // 7a7a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 7a7d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.LINEAR Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 7a80: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 7a83: aastore
      // 7a84: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 7a87: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 7a8a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 7a8d: putstatic net/unusual/block_factorys_bosses/client/animation/yeti/yetiDeath.DEATH Lnet/minecraft/client/animation/AnimationDefinition;
      // 7a90: return
   }
}
