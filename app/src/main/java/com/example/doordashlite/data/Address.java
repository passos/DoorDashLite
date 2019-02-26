package com.example.doordashlite.data;

import androidx.annotation.Nullable;

public class Address {
    public double lat;
    public double lng;
    public String name;

    public Address(String name, double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Address)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        Address other = (Address) obj;
        return Math.abs(this.lat - other.lat) < 0.0000001 && Math.abs(this.lng - other.lng) < 0.0000001;
    }
}
