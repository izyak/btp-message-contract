package com.icon.score;

import java.math.BigInteger;
import score.ByteArrayObjectWriter;
import score.Context;
import score.ObjectReader;
import score.ObjectWriter;



public class Height {
    public BigInteger revisionNumber;
    public BigInteger revisionHeight;


    public static void writeObject(ObjectWriter writer, Height obj) {
        obj.writeObject(writer);
    }

    public void writeObject(ObjectWriter writer) {
        writer.beginList(2);
        writer.write(this.revisionNumber);
        writer.write(this.revisionHeight);
        writer.end();
    }

    public static Height readObject(ObjectReader reader) {
        Height obj = new Height();
        reader.beginList();
        obj.revisionNumber = reader.readBigInteger();
        obj.revisionHeight = reader.readBigInteger();
        reader.end();
        return obj; 
    }

    public static Height fromBytes(byte[] bytes) {
        ObjectReader reader = Context.newByteArrayObjectReader("RLPn", bytes);
        return Height.readObject(reader);
    }

    public byte[] toBytes() {
        ByteArrayObjectWriter writer = Context.newByteArrayObjectWriter("RLPn");
        Height.writeObject(writer, this);
        return writer.toByteArray();
    }
}
