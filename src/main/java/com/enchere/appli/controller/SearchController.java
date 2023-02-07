package com.enchere.appli.controller;

import com.enchere.appli.model.ObjectReturn;
import com.enchere.appli.model.ResultatRecherche;
import com.enchere.appli.utils.Search;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin("*")
@RestController
@RequestMapping("searchs")
public class SearchController
{
    @PostMapping
    public ObjectReturn find(@RequestBody Search search) throws Exception {
        ObjectReturn objet = null;

        try
        {
            ArrayList<ResultatRecherche> p = search.getResult();
            for(ResultatRecherche pp :p)
            {
                System.out.println(pp.toString());
            }
            objet = new ObjectReturn();
            objet.setData(p);
            objet.setMessage("success");
        }
        catch (Exception e)
        {
            throw e;
        }
        return objet;
    }
}
