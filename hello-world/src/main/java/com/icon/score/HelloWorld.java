package com.icon.score;

import java.math.BigInteger;

import score.Address;
import score.Context;
import score.VarDB;
import score.annotation.EventLog;
import score.annotation.External;
import score.annotation.Payable;

public class HelloWorld {
    private final String name;
    public final VarDB<BigInteger> sn = Context.newVarDB("sn", BigInteger.class);
    public final VarDB<BigInteger> networkId = Context.newVarDB("networkId", BigInteger.class);
    private final Address systemAddress = Address.fromString("cx0000000000000000000000000000000000000000");

    public HelloWorld(String name) {
        this.name = name;
        sn.set(BigInteger.ZERO);
        networkId.set(BigInteger.ONE);
    }

    @External(readonly=true)
    public String name() {
        return name;
    }

    @External(readonly=true)
    public String getGreeting() {
        String msg = "Hello " + name + "!";
        Context.println(msg);
        return msg;
    }

    @External
    public void setNetworkId(BigInteger nid) {
        networkId.set(nid);
    }

    @External
    public void sendPacket() {

        Packet pkt = new Packet();
        pkt.sequence = sn.get();
        sn.set(sn.get().add(BigInteger.ONE));
        pkt.sourcePort = "xcall";
        pkt.sourceChannel = "channel-0";
        pkt.destinationChannel = "channel-1";
        pkt.destinationPort = "xcall";
        pkt.data = new byte[0];
        pkt.timestamp = BigInteger.valueOf(Context.getBlockTimestamp());
        Height h = new Height();
        h.revisionNumber = BigInteger.TWO;
        h.revisionHeight = BigInteger.valueOf(Context.getBlockHeight());
        pkt.height = h;

        // generate sendPacket event
        SendPacket(pkt.toBytes());
        Context.call(systemAddress, "sendBTPMessage", networkId.get(), pkt.toBytes());
        
    }

    @EventLog(indexed = 1)
    public void SendPacket(byte[] packet) {
    }

    @EventLog(indexed = 1)
    public void RecvPacket(byte[] packet) {
    }

    @EventLog(indexed = 1)
    public void AcknowledgePacket(String destPort, String destChannel, BigInteger sequence, byte[] acknowledgement) {
    }

    @External 
    public void recvPacket() {
        Packet pkt = new Packet();
        pkt.sequence = sn.get();
        sn.set(sn.get().add(BigInteger.ONE));
        pkt.sourcePort = "xcall";
        pkt.sourceChannel = "channel-0";
        pkt.destinationChannel = "channel-1";
        pkt.destinationPort = "xcall";
        pkt.data = new byte[0];
        pkt.timestamp = BigInteger.valueOf(Context.getBlockTimestamp());
        Height h = new Height();
        h.revisionNumber = BigInteger.TWO;
        h.revisionHeight = BigInteger.valueOf(Context.getBlockHeight());
        pkt.height = h;

        // generate RecvPacket event
        RecvPacket(pkt.toBytes());
        Context.call(systemAddress, "sendBTPMessage", networkId.get(), pkt.toBytes());

    }

    @External
    public void ackPacket() {
        Packet pkt = new Packet();
        pkt.sequence = sn.get();
        sn.set(sn.get().add(BigInteger.ONE));
        pkt.sourcePort = "xcall";
        pkt.sourceChannel = "channel-0";
        pkt.destinationChannel = "channel-1";
        pkt.destinationPort = "xcall";
        pkt.data = new byte[0];
        pkt.timestamp = BigInteger.valueOf(Context.getBlockTimestamp());
        Height h = new Height();
        h.revisionNumber = BigInteger.TWO;
        h.revisionHeight = BigInteger.valueOf(Context.getBlockHeight());
        pkt.height = h;

        // generate AcknowledgePacket event
        AcknowledgePacket("xcall","channel-1", sn.get(), new byte[0]);
        Context.call(systemAddress, "sendBTPMessage", networkId.get(), pkt.toBytes());

    }

    @External
    public void sendBTPMessageWithBytes(byte[] message) {
        SendPacket(message);
        Context.call(systemAddress, "sendBTPMessage", networkId.get(), message);
    }

    @External
    public void sendBTPMessage() {
        SendPacket(new byte[0] );
        Context.call(systemAddress, "sendBTPMessage", networkId.get(), new byte[0]);
    }


    @Payable
    public void fallback() {
        // just receive incoming funds
    }
}
