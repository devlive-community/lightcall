package org.devlive.lightcall.example.options;

import org.devlive.lightcall.annotation.Options;

public interface OptionsService
{
    @Options("/posts")
    Object apply();
}
