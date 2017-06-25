package com.blackbelt.androidboundrv.misc;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import android.text.TextUtils;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateTimeDeserializer implements JsonDeserializer<Date>, JsonSerializer<Date> {

    private static final String SCREEN_NAME = "DateTimeDeserializer";

    private List<SimpleDateFormat> mDateTimeFormatters;

    public DateTimeDeserializer() {
        mDateTimeFormatters = new ArrayList<>();
        mDateTimeFormatters.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault()));
        mDateTimeFormatters.add(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()));
    }


    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        final String date = json.getAsString();

        if (TextUtils.isEmpty(date)) {
            return null;
        }

        for (SimpleDateFormat dateTimeFormatter : mDateTimeFormatters) {
            try {
                return dateTimeFormatter.parse(date);
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}