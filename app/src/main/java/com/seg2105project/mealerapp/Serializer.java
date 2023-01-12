package com.seg2105project.mealerapp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {

    public static <Item> byte[] serialize(Item item) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(item);
        out.close();
        return baos.toByteArray();
    }

    @SuppressWarnings("unchecked")
    public static <Item> Item deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Item item = (Item) in.readObject();
        in.close();
        return item;
    }
}
