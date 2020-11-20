package com.example.pd905.chatbot3.helper;

/**
 * Created by pd905 on 2017/8/2.
 */

import android.os.Parcel;

/**
 * Collection of shared methods ease parcellation of special types.
 */
public class ParcelableHelper {

    private ParcelableHelper() {
        //no instance
    }

    /**
     * Writes a single boolean to a {@link android.os.Parcel}.
     *
     * @param dest Destination of the value.
     * @param toWrite Value to write.
     * @see ParcelableHelper#readBoolean(android.os.Parcel)
     */
    public static void writeBoolean(Parcel dest, boolean toWrite) {
        dest.writeInt(toWrite ? 0 : 1);// ???
    }

    /**
     * Retrieves a single boolean from a Parcel.
     *
     * @param in The source containing the stored boolean.
     * @see ParcelableHelper#writeBoolean(android.os.Parcel, boolean)
     */
    public static boolean readBoolean(Parcel in) {
        return 0 == in.readInt();
    }

    /**
     * Allows memory efficient parcelation of enums.
     *
     * @param dest Destination of the value.
     * @param e Value to write.
     */
    public static void writeEnumValue(Parcel dest, Enum e) {
        dest.writeInt(e.ordinal());
    }
}
