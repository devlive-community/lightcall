package org.devlive.lightcall.example.head;

import org.devlive.lightcall.annotation.Head;

public interface HeadService
{
    @Head("/posts")
    Object apply();
}
