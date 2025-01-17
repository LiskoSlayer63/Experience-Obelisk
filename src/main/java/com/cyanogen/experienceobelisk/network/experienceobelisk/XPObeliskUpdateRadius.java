package com.cyanogen.experienceobelisk.network.experienceobelisk;

import com.cyanogen.experienceobelisk.block_entities.XPObeliskEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class XPObeliskUpdateRadius {

    public BlockPos pos;
    public double changeInRadius;

    public XPObeliskUpdateRadius(BlockPos pos, double changeInRadius) {
        this.pos = pos;
        this.changeInRadius = changeInRadius;
    }

    public XPObeliskUpdateRadius(FriendlyByteBuf buffer) {

        pos = buffer.readBlockPos();
        changeInRadius = buffer.readDouble();

    }

    public void encode(FriendlyByteBuf buffer){

        buffer.writeBlockPos(pos);
        buffer.writeDouble(changeInRadius);

    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();
            assert sender != null;

            BlockEntity serverEntity = sender.level.getBlockEntity(pos);

            if(serverEntity instanceof XPObeliskEntity xpobelisk){

                double finalRadius = xpobelisk.getRadius() + changeInRadius;

                if(changeInRadius == 0){
                    xpobelisk.setRadius(2.5); //set to default
                }
                else if(finalRadius >= 1 && finalRadius <= 5){
                    xpobelisk.setRadius(finalRadius);
                }

            }

            success.set(true);

        });
        ctx.get().setPacketHandled(true);
        return success.get();
    }
}
