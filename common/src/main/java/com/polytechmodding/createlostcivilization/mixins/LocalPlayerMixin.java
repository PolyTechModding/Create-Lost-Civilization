package com.polytechmodding.createlostcivilization.mixins;

import com.mojang.authlib.GameProfile;
import com.polytechmodding.createlostcivilization.entities.AirEntity;
import com.polytechmodding.createlostcivilization.world.level.dimension.CivilizationDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends Player {


    @Shadow protected int sprintTriggerTime;

    @Shadow @Final protected Minecraft minecraft;

    @Shadow protected abstract boolean canStartSprinting();

    @Shadow public Input input;

    @Shadow protected abstract boolean hasEnoughImpulseToStartSprinting();

    @Shadow protected abstract boolean hasEnoughFoodToStartSprinting();

    @Shadow @Final public ClientPacketListener connection;

    @Shadow private boolean wasFallFlying;

    @Shadow private int waterVisionTime;

    @Shadow protected abstract boolean isControlledCamera();

    @Shadow public abstract @Nullable PlayerRideableJumping jumpableVehicle();

    @Shadow private int jumpRidingTicks;

    @Shadow private float jumpRidingScale;

    @Shadow protected abstract void sendRidingJump();

    @Shadow public abstract float getJumpRidingScale();

    @Shadow protected abstract void handleNetherPortalClient();

    @Shadow private boolean crouching;

    @Shadow public abstract boolean isMovingSlowly();

    @Shadow private int autoJumpTime;

    @Shadow protected abstract void moveTowardsClosestSpace(double d, double e);

    @Shadow public abstract void tick();

    public LocalPlayerMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    @Inject(method = "aiStep()V",
    at = @At(value = "HEAD"), cancellable = true)
    public void injectAiStep(CallbackInfo ci) {
        PlayerRideableJumping playerRideableJumping;
        int i;
        ItemStack itemStack;
        boolean bl8;
        boolean bl7;
        if (this.sprintTriggerTime > 0) {
            --this.sprintTriggerTime;
        }
        if (!(this.minecraft.screen instanceof ReceivingLevelScreen)) {
            this.handleNetherPortalClient();
        }
        boolean bl = this.input.jumping;
        boolean bl2 = this.input.shiftKeyDown;
        boolean bl3 = this.hasEnoughImpulseToStartSprinting();
        this.crouching = !this.getAbilities().flying && !this.isSwimming() && !this.isPassenger() && this.canPlayerFitWithinBlocksAndEntitiesWhen(Pose.CROUCHING) && (this.isShiftKeyDown() || !this.isSleeping() && !this.canPlayerFitWithinBlocksAndEntitiesWhen(Pose.STANDING));
        float f = Mth.clamp(0.3f + EnchantmentHelper.getSneakingSpeedBonus(this), 0.0f, 1.0f);
        this.input.tick(this.isMovingSlowly(), f);
        this.minecraft.getTutorial().onInput(this.input);
        if (this.isUsingItem() && !this.isPassenger()) {
            this.input.leftImpulse *= 0.2f;
            this.input.forwardImpulse *= 0.2f;
            this.sprintTriggerTime = 0;
        }
        boolean bl4 = false;
        if (this.autoJumpTime > 0) {
            --this.autoJumpTime;
            bl4 = true;
            this.input.jumping = true;
        }
        if (!this.noPhysics) {
            this.moveTowardsClosestSpace(this.getX() - (double)this.getBbWidth() * 0.35, this.getZ() + (double)this.getBbWidth() * 0.35);
            this.moveTowardsClosestSpace(this.getX() - (double)this.getBbWidth() * 0.35, this.getZ() - (double)this.getBbWidth() * 0.35);
            this.moveTowardsClosestSpace(this.getX() + (double)this.getBbWidth() * 0.35, this.getZ() - (double)this.getBbWidth() * 0.35);
            this.moveTowardsClosestSpace(this.getX() + (double)this.getBbWidth() * 0.35, this.getZ() + (double)this.getBbWidth() * 0.35);
        }
        if (bl2) {
            this.sprintTriggerTime = 0;
        }
        boolean bl5 = this.canStartSprinting();
        boolean bl6 = this.isPassenger() ? this.getVehicle().onGround() : this.onGround();
        boolean bl9 = bl7 = !bl2 && !bl3;
        // System.out.println("Start Sprint " + (bl6 || ((AirEntity)this).createLostCivilization$isUnderAir()) + " " + (bl7 && bl5));
        if ((bl6 || this.isUnderWater()) && bl7 && bl5) {
            if (this.sprintTriggerTime > 0 || this.minecraft.options.keySprint.isDown()) {
                this.setSprinting(true);
            } else {
                this.sprintTriggerTime = 7;
            }
        } else if ((bl6 || ((AirEntity)this).createLostCivilization$isUnderAir()) && bl7 && bl5) {
            if (this.sprintTriggerTime > 0 || this.minecraft.options.keySprint.isDown()) {
                this.setSprinting(true);
            } else {
                this.sprintTriggerTime = 7;
            }
        }
        // System.out.println("Start Sprinting " + canStartSprinting() + " " + (!((AirEntity)this).createLostCivilization$isInAir() || ((AirEntity)this).createLostCivilization$isUnderAir()));
        if ((!this.isInWater() || this.isUnderWater()) && bl5 && this.minecraft.options.keySprint.isDown()) {
            this.setSprinting(true);
        } else if ((!((AirEntity)this).createLostCivilization$isInAir() || ((AirEntity)this).createLostCivilization$isUnderAir())
                && bl5 && this.minecraft.options.keySprint.isDown()) {
            this.setSprinting(true);
        }
        if (this.isSprinting()) {
            boolean bl92;
            bl8 = !this.input.hasForwardImpulse() || !this.hasEnoughFoodToStartSprinting();
            boolean bl10 = bl92 = bl8
                    || this.horizontalCollision && !this.minorHorizontalCollision || this.isInWater() && !this.isUnderWater() || ((AirEntity)this).createLostCivilization$isInAir() && !((AirEntity)this).createLostCivilization$isUnderAir();
            if (this.isSwimming()) {
                /* System.out.println("Sprinting deactivated while swimming " +
                        (!this.onGround() && !this.input.shiftKeyDown && bl8) + " " +
                        (!this.isInWater() && !level().dimensionTypeId().equals(CivilizationDimensions.MYSTERY_PLANET)) + " " + !((AirEntity)this).createLostCivilization$isInAir() + " total: " +
                        ((!this.onGround() && !this.input.shiftKeyDown && bl8) || !this.isInWater() ||
                        !((AirEntity)this).createLostCivilization$isInAir())); */
                if ((!this.onGround() && !this.input.shiftKeyDown && bl8) || !this.isInWater() && !level().dimensionTypeId().equals(CivilizationDimensions.MYSTERY_PLANET)
                        // || !((AirEntity)this).createLostCivilization$isInAir()
                ) {
                    this.setSprinting(false);
                }
            } else if (bl92) {
                System.out.println("Spring deactivated because I left the air");
                this.setSprinting(false);
            }
        }
        bl8 = false;
        if (this.getAbilities().mayfly) {
            if (this.minecraft.gameMode.isAlwaysFlying()) {
                if (!this.getAbilities().flying) {
                    this.getAbilities().flying = true;
                    bl8 = true;
                    this.onUpdateAbilities();
                }
            } else if (!bl && this.input.jumping && !bl4) {
                if (this.jumpTriggerTime == 0) {
                    this.jumpTriggerTime = 7;
                } else if (!this.isSwimming()) {
                    this.getAbilities().flying = !this.getAbilities().flying;
                    bl8 = true;
                    this.onUpdateAbilities();
                    this.jumpTriggerTime = 0;
                }
            }
        }
        if (this.input.jumping && !bl8 && !bl && !this.getAbilities().flying && !this.isPassenger() && !this.onClimbable() && (itemStack = this.getItemBySlot(EquipmentSlot.CHEST)).is(Items.ELYTRA) && ElytraItem.isFlyEnabled(itemStack) && this.tryToStartFallFlying()) {
            this.connection.send(new ServerboundPlayerCommandPacket(this, ServerboundPlayerCommandPacket.Action.START_FALL_FLYING));
        }
        this.wasFallFlying = this.isFallFlying();
        if ((this.isInWater() || ((AirEntity)this).createLostCivilization$isInAir())
                && this.input.shiftKeyDown && this.isAffectedByFluids()) {
            this.goDownInWater();
        }
        if (this.isEyeInFluid(FluidTags.WATER)) {
            i = this.isSpectator() ? 10 : 1;
            this.waterVisionTime = Mth.clamp(this.waterVisionTime + i, 0, 600);
        } else if (this.waterVisionTime > 0) {
            this.isEyeInFluid(FluidTags.WATER);
            this.waterVisionTime = Mth.clamp(this.waterVisionTime - 10, 0, 600);
        }
        if (this.getAbilities().flying && this.isControlledCamera()) {
            i = 0;
            if (this.input.shiftKeyDown) {
                --i;
            }
            if (this.input.jumping) {
                ++i;
            }
            if (i != 0) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, (float)i * this.getAbilities().getFlyingSpeed() * 3.0f, 0.0));
            }
        }
        if ((playerRideableJumping = this.jumpableVehicle()) != null && playerRideableJumping.getJumpCooldown() == 0) {
            if (this.jumpRidingTicks < 0) {
                ++this.jumpRidingTicks;
                if (this.jumpRidingTicks == 0) {
                    this.jumpRidingScale = 0.0f;
                }
            }
            if (bl && !this.input.jumping) {
                this.jumpRidingTicks = -10;
                playerRideableJumping.onPlayerJump(Mth.floor(this.getJumpRidingScale() * 100.0f));
                this.sendRidingJump();
            } else if (!bl && this.input.jumping) {
                this.jumpRidingTicks = 0;
                this.jumpRidingScale = 0.0f;
            } else if (bl) {
                ++this.jumpRidingTicks;
                this.jumpRidingScale = this.jumpRidingTicks < 10 ? (float)this.jumpRidingTicks * 0.1f : 0.8f + 2.0f / (float)(this.jumpRidingTicks - 9) * 0.1f;
            }
        } else {
            this.jumpRidingScale = 0.0f;
        }
        super.aiStep();
        if (this.onGround() && this.getAbilities().flying && !this.minecraft.gameMode.isAlwaysFlying()) {
            this.getAbilities().flying = false;
            this.onUpdateAbilities();
        }
        ci.cancel();
    }

    @Inject(method = "hasEnoughImpulseToStartSprinting()Z",
    at = @At("HEAD"), cancellable = true)
    private void hasEnoughImpulseToStartSprintingInjection(CallbackInfoReturnable<Boolean> cir) {
        double d = 0.8;
        cir.setReturnValue((this.isUnderWater() || ((AirEntity)this).createLostCivilization$isUnderAir()) ?
                this.input.hasForwardImpulse() : (double)this.input.forwardImpulse >= 0.8);
    }
}
