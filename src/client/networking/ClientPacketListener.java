package client.networking;

import client.game.Entity;
import client.networking.packets.C2S.play.ChatMessageC2SPacket;
import client.networking.packets.S2C.configuration.*;
import client.networking.packets.S2C.play.*;

public interface ClientPacketListener extends PacketListener {
    //login
    void onStatus(StatusResponseS2CPacket packet);
    void onCookieRequest(CookieRequestS2CPacket packet);
    void onEncryption(EncryptionRequestS2CPacket packet);
    void onCompression(CompressionRequestS2CPacket packet);
    void onLoginSuccess(LoginSuccessS2CPacket packet);

    //configuration
    void onFinishConfiguration(FinishConfigurationS2CPacket packet);
    void onClientPluginMessage(ClientBoundPluginMessageS2CPacket packet);
    void onKnowPacks(ClientBoundKnownPacksS2CPacket packet);

    //play
    void onKeepAlive(KeepAliveS2CPacket packet);
    void onLoginPlay(LoginPlayS2CPacket packet);
    void onSetHealth(SetHealthS2CPacket packet);
    void onTeleportEntityS2CPacket(TeleportEntityS2CPacket packet);
    void onUpdateEntityPos(UpdateEntityPositionS2CPacket packet);
    void onUpdateEntityPosAndRotation(UpdateEntityPositionAndRotation packet);
    void onUpdateEntityRotation(UpdateEntityRotationS2CPacket packet);
    <T extends Entity> void onSpawnEntity(SpawnEntityS2CPacket packet);
    void onRemoveEntities(RemoveEntitiesS2CPacket packet);
    void onSetHeldItem(SetHeldItemS2CPacket packet);
    void onSynchronizePlayerPosition(SynchronizePlayerPositionS2CPacket packet);
    void onSetCenterChunk(SetCenterChunkS2CPacket packet);
    void onChunkData(ChunkDataS2CPacket packet);
    void onBlockUpdate(BlockUpdateS2CPacket packet);
    void onChatMessageS2C(PlayerChatMessageS2C packet);
    void onSetContainerContent(SetContainerContentS2CPacket packet);
    void onSetContainerSlot(SetContainerSlotS2CPacket packet);
    void onBlockSectionUpdate(UpdateBlockSectionS2CPacket packet);
    void onSetEntityVelocity(SetEntityVelocityS2CPacket packet);
    void onUnloadChunk(UnloadChunkS2CPacket packet);


}
