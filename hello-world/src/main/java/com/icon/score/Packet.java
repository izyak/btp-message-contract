package com.icon.score;

import java.math.BigInteger;

import score.ByteArrayObjectWriter;
import score.Context;
import score.ObjectReader;
import score.ObjectWriter;

public class Packet {
    public BigInteger sequence;
    public String sourcePort;
    public String sourceChannel;
    public String destinationPort;
    public String destinationChannel;
    public byte[] data;
    public Height height;
    public BigInteger timestamp; 

    public static void writeObject(ObjectWriter writer, Packet obj) {
        obj.writeObject(writer);
    }

    public void writeObject(ObjectWriter writer) {
        writer.beginList(8);
        writer.write(this.sequence);
        writer.write(this.sourcePort);
        writer.write(this.sourceChannel);
        writer.write(this.destinationPort);
        writer.write(this.destinationChannel);
        writer.write(this.height);
        writer.write(this.data);
        writer.write(this.timestamp);
        writer.end();
    }

    public static Packet readObject(ObjectReader reader) {
        Packet obj = new Packet();
        reader.beginList();
        obj.sequence = reader.readBigInteger();
        obj.sourcePort = reader.readString();
        obj.sourceChannel = reader.readString();
        obj.destinationPort = reader.readString();
        obj.destinationChannel = reader.readString();
        obj.height = reader.read(Height.class);
        obj.data = reader.readByteArray();
        obj.timestamp = reader.readBigInteger();
        reader.end();
        return obj; 
    }

    public static Packet fromBytes(byte[] bytes) {
        ObjectReader reader = Context.newByteArrayObjectReader("RLPn", bytes);
        return Packet.readObject(reader);
    }

    public byte[] toBytes() {
        ByteArrayObjectWriter writer = Context.newByteArrayObjectWriter("RLPn");
        Packet.writeObject(writer, this);
        return writer.toByteArray();
    }
}
