package com.blackbelt.androidboundrv.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Accessors(prefix = "m")
@Data
public class FragmentModel {

    private String mTitle;

    private Class<? extends Fragment> mFragment;

    private Bundle mBundle;
}