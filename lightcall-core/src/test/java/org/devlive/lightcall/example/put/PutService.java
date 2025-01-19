package org.devlive.lightcall.example.put;

import org.devlive.lightcall.annotation.Body;
import org.devlive.lightcall.annotation.PathVariable;
import org.devlive.lightcall.annotation.Put;
import org.devlive.lightcall.example.PostModel;

public interface PutService
{
    @Put("/posts/{id}")
    PostModel putPost(@PathVariable("id") Long id, @Body PostModel post);
}
