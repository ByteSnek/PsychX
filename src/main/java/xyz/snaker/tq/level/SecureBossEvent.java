package xyz.snaker.tq.level;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 18/09/2023
 **/
public class SecureBossEvent extends BossEvent
{
    private final Set<ServerPlayer> players = Sets.newHashSet();
    private final Set<ServerPlayer> unmodifiablePlayers = Collections.unmodifiableSet(players);

    private boolean visible = true;

    public SecureBossEvent(UUID id, Component name, BossBarColor colour, BossBarOverlay overlay)
    {
        super(id, name, colour, overlay);
    }

    public void setProgress(float value)
    {
        if (value != progress) {
            super.setProgress(value);
            broadcast(ClientboundBossEventPacket::createUpdateProgressPacket);
        }
    }

    public void setColor(BossEvent.@NotNull BossBarColor barColour)
    {
        if (barColour != color) {
            super.setColor(barColour);
            broadcast(ClientboundBossEventPacket::createUpdateStylePacket);
        }
    }

    public void setOverlay(BossEvent.@NotNull BossBarOverlay barOverlay)
    {
        if (barOverlay != overlay) {
            super.setOverlay(barOverlay);
            broadcast(ClientboundBossEventPacket::createUpdateStylePacket);
        }
    }

    public @NotNull BossEvent setDarkenScreen(boolean value)
    {
        if (value != darkenScreen) {
            super.setDarkenScreen(value);
            broadcast(ClientboundBossEventPacket::createUpdatePropertiesPacket);
        }
        return this;
    }

    public @NotNull BossEvent setPlayBossMusic(boolean value)
    {
        if (value != playBossMusic) {
            super.setPlayBossMusic(value);
            broadcast(ClientboundBossEventPacket::createUpdatePropertiesPacket);
        }
        return this;
    }

    public @NotNull BossEvent setCreateWorldFog(boolean value)
    {
        if (value != createWorldFog) {
            super.setCreateWorldFog(value);
            broadcast(ClientboundBossEventPacket::createUpdatePropertiesPacket);
        }
        return this;
    }

    public void setName(@NotNull Component newName)
    {
        if (newName != name) {
            super.setName(newName);
            broadcast(ClientboundBossEventPacket::createUpdateNamePacket);
        }
    }

    private void broadcast(Function<BossEvent, ClientboundBossEventPacket> packet)
    {
        if (visible) {
            ClientboundBossEventPacket pkt = packet.apply(this);
            for (ServerPlayer player : players) {
                player.connection.send(pkt);
            }
        }
    }

    public void addPlayer(ServerPlayer player)
    {
        if (players.add(player) && visible) {
            player.connection.send(ClientboundBossEventPacket.createAddPacket(this));
        }
    }

    public void removePlayer(ServerPlayer player)
    {
        if (players.remove(player) && visible) {
            player.connection.send(ClientboundBossEventPacket.createRemovePacket(getId()));
        }
    }

    public void removeAllPlayers()
    {
        if (!players.isEmpty()) {
            for (ServerPlayer player : Lists.newArrayList(players)) {
                removePlayer(player);
            }
        }

    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean value)
    {
        if (value != visible) {
            visible = value;
            for (ServerPlayer player : players) {
                player.connection.send(value ? ClientboundBossEventPacket.createAddPacket(this) : ClientboundBossEventPacket.createRemovePacket(getId()));
            }
        }
    }

    public Collection<ServerPlayer> getPlayers()
    {
        return this.unmodifiablePlayers;
    }
}
