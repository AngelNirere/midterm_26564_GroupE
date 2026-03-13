package com.angel.assetmanagementsystem.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.angel.assetmanagementsystem.domain.ELocationType;
import com.angel.assetmanagementsystem.domain.Location;
import com.angel.assetmanagementsystem.repository.LocationRepository;


@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public void run(String... args) throws Exception {
        seedLocations();
    }

    private Location saveIfNotExists(String code, String name, ELocationType type, Location parent) {
        Boolean exists = locationRepository.existsByCode(code);
        if (exists) {
            return locationRepository.findByCodeAndType(code, type).orElse(null);
        }
        Location location = new Location();
        location.setCode(code);
        location.setName(name);
        location.setType(type);
        location.setParent(parent);
        return locationRepository.save(location);
    }

    private void seedLocations() {

        
        // PROVINCES
    
        Location kigali      = saveIfNotExists("KG",  "Kigali",    ELocationType.PROVINCE, null);
        Location northern    = saveIfNotExists("RW-03", "Northern Province", ELocationType.PROVINCE, null);
        Location southern    = saveIfNotExists("RW-05", "Southern Province", ELocationType.PROVINCE, null);
        Location eastern     = saveIfNotExists("RW-02", "Eastern Province",  ELocationType.PROVINCE, null);
        Location western     = saveIfNotExists("RW-04", "Western Province",  ELocationType.PROVINCE, null);

    
        // KIGALI DISTRICTS
        
        Location gasabo     = saveIfNotExists("KG-GS", "Gasabo",     ELocationType.DISTRICT, kigali);
        Location kicukiro   = saveIfNotExists("KG-KC", "Kicukiro",   ELocationType.DISTRICT, kigali);
        Location nyarugenge = saveIfNotExists("KG-NY", "Nyarugenge", ELocationType.DISTRICT, kigali);

        
        // GASABO SECTORS
    
        Location bumbogo    = saveIfNotExists("GS-BU", "Bumbogo",    ELocationType.SECTOR, gasabo);
        Location gatsata    = saveIfNotExists("GS-GA", "Gatsata",    ELocationType.SECTOR, gasabo);
        Location gikomero   = saveIfNotExists("GS-GK", "Gikomero",   ELocationType.SECTOR, gasabo);
        Location gisozi     = saveIfNotExists("GS-GZ", "Gisozi",     ELocationType.SECTOR, gasabo);
        Location jabana     = saveIfNotExists("GS-JB", "Jabana",     ELocationType.SECTOR, gasabo);
        Location jali       = saveIfNotExists("GS-JL", "Jali",       ELocationType.SECTOR, gasabo);
        Location kacyiru    = saveIfNotExists("GS-KA", "Kacyiru",    ELocationType.SECTOR, gasabo);
        Location kimihurura = saveIfNotExists("GS-KM", "Kimihurura", ELocationType.SECTOR, gasabo);
        Location kinyinya   = saveIfNotExists("GS-KY", "Kinyinya",   ELocationType.SECTOR, gasabo);
        Location ndera      = saveIfNotExists("GS-ND", "Ndera",      ELocationType.SECTOR, gasabo);
        Location nduba      = saveIfNotExists("GS-NB", "Nduba",      ELocationType.SECTOR, gasabo);
        Location remera     = saveIfNotExists("GS-RE", "Remera",     ELocationType.SECTOR, gasabo);
        Location rubirizi   = saveIfNotExists("GS-RB", "Rubirizi",   ELocationType.SECTOR, gasabo);
        Location rusororo   = saveIfNotExists("GS-RS", "Rusororo",   ELocationType.SECTOR, gasabo);
        Location rutongo    = saveIfNotExists("GS-RT", "Rutongo",    ELocationType.SECTOR, gasabo);

        
        // KICUKIRO SECTORS
        
        Location gahanga    = saveIfNotExists("KC-GH", "Gahanga",    ELocationType.SECTOR, kicukiro);
        Location gatenga    = saveIfNotExists("KC-GT", "Gatenga",    ELocationType.SECTOR, kicukiro);
        Location gikondo    = saveIfNotExists("KC-GD", "Gikondo",    ELocationType.SECTOR, kicukiro);
        Location kagarama   = saveIfNotExists("KC-KG", "Kagarama",   ELocationType.SECTOR, kicukiro);
        Location kanombe    = saveIfNotExists("KC-KN", "Kanombe",    ELocationType.SECTOR, kicukiro);
        Location kicukiroSec= saveIfNotExists("KC-KC", "Kicukiro",   ELocationType.SECTOR, kicukiro);
        Location kigarama   = saveIfNotExists("KC-KR", "Kigarama",   ELocationType.SECTOR, kicukiro);
        Location masaka     = saveIfNotExists("KC-MK", "Masaka",     ELocationType.SECTOR, kicukiro);
        Location niboye     = saveIfNotExists("KC-NB", "Niboye",     ELocationType.SECTOR, kicukiro);
        Location nyarugunga = saveIfNotExists("KC-NR", "Nyarugunga", ELocationType.SECTOR, kicukiro);

        
        // NYARUGENGE SECTORS
        
        Location gitega     = saveIfNotExists("NY-GT", "Gitega",     ELocationType.SECTOR, nyarugenge);
        Location kanyinya   = saveIfNotExists("NY-KY", "Kanyinya",   ELocationType.SECTOR, nyarugenge);
        Location kigalicity = saveIfNotExists("NY-KG", "Kigali",     ELocationType.SECTOR, nyarugenge);
        Location kimisagara = saveIfNotExists("NY-KM", "Kimisagara", ELocationType.SECTOR, nyarugenge);
        Location mageragere = saveIfNotExists("NY-MG", "Mageragere", ELocationType.SECTOR, nyarugenge);
        Location muhima     = saveIfNotExists("NY-MH", "Muhima",     ELocationType.SECTOR, nyarugenge);
        Location nyakabanda = saveIfNotExists("NY-NK", "Nyakabanda", ELocationType.SECTOR, nyarugenge);
        Location nyamirambo = saveIfNotExists("NY-NM", "Nyamirambo", ELocationType.SECTOR, nyarugenge);
        Location nyarugenge2= saveIfNotExists("NY-NY", "Nyarugenge", ELocationType.SECTOR, nyarugenge);
        Location rwezamenyo = saveIfNotExists("NY-RW", "Rwezamenyo", ELocationType.SECTOR, nyarugenge);

        
        // CELLS – REMERA (Gasabo)
        
        Location bibare    = saveIfNotExists("RE-BB", "Bibare",    ELocationType.CELL, remera);
        Location gahogo    = saveIfNotExists("RE-GH", "Gahogo",    ELocationType.CELL, remera);
        Location kibari    = saveIfNotExists("RE-KB", "Kibari",    ELocationType.CELL, remera);
        Location rukiri    = saveIfNotExists("RE-RK", "Rukiri",    ELocationType.CELL, remera);
        Location tatizo    = saveIfNotExists("RE-TZ", "Tatizo",    ELocationType.CELL, remera);

        // CELLS – KACYIRU (Gasabo)
        Location kamatamu   = saveIfNotExists("KA-KM", "Kamatamu",   ELocationType.CELL, kacyiru);
        Location kabyutso   = saveIfNotExists("KA-KB", "Kabyutso",   ELocationType.CELL, kacyiru);
        Location kagugu     = saveIfNotExists("KA-KG", "Kagugu",     ELocationType.CELL, kacyiru);
        Location kacyiruCell= saveIfNotExists("KA-KC", "Kacyiru",    ELocationType.CELL, kacyiru);

        // CELLS – KIMIHURURA (Gasabo)
        Location biryogo      = saveIfNotExists("KM-BR", "Biryogo",      ELocationType.CELL, kimihurura);
        Location kiyovu       = saveIfNotExists("KM-KV", "Kiyovu",       ELocationType.CELL, kimihurura);
        Location kimihururaCell=saveIfNotExists("KM-KH", "Kimihurura",   ELocationType.CELL, kimihurura);
        Location rugando      = saveIfNotExists("KM-RG", "Rugando",      ELocationType.CELL, kimihurura);

        // CELLS – GISOZI (Gasabo)
        Location gisoziCell  = saveIfNotExists("GZ-GZ", "Gisozi",   ELocationType.CELL, gisozi);
        Location nkamira     = saveIfNotExists("GZ-NK", "Nkamira",  ELocationType.CELL, gisozi);
        Location nkurunziza  = saveIfNotExists("GZ-NZ", "Nkurunziza",ELocationType.CELL, gisozi);

        // CELLS – KINYINYA (Gasabo)
        Location kinyinyaCell = saveIfNotExists("KY-KY", "Kinyinya",   ELocationType.CELL, kinyinya);
        Location rusengero    = saveIfNotExists("KY-RS", "Rusengero",  ELocationType.CELL, kinyinya);
        Location ruturuMaz    = saveIfNotExists("KY-RZ", "Ruturo",     ELocationType.CELL, kinyinya);

        // CELLS – GITEGA (Nyarugenge)
        Location akabahizi   = saveIfNotExists("GT-AK", "Akabahizi",  ELocationType.CELL, gitega);
        Location akabukire   = saveIfNotExists("GT-AB", "Akabukire",  ELocationType.CELL, gitega);
        Location akantera    = saveIfNotExists("GT-AT", "Akantera",   ELocationType.CELL, gitega);
        Location impinga     = saveIfNotExists("GT-IM", "Impinga",    ELocationType.CELL, gitega);

        // CELLS – KIMISAGARA (Nyarugenge)
        Location cyahafi     = saveIfNotExists("KS-CY", "Cyahafi",   ELocationType.CELL, kimisagara);
        Location kimisagaraCell=saveIfNotExists("KS-KM","Kimisagara", ELocationType.CELL, kimisagara);
        Location kwa         = saveIfNotExists("KS-KW", "Kwa",        ELocationType.CELL, kimisagara);
        Location nyamiramboCell=saveIfNotExists("KS-NM","Nyamirambo", ELocationType.CELL, kimisagara);

        // CELLS – NYAMIRAMBO (Nyarugenge)
        Location biryogoNY   = saveIfNotExists("NM-BY", "Biryogo",   ELocationType.CELL, nyamirambo);
        Location bukinanyana = saveIfNotExists("NM-BK", "Bukinanyana",ELocationType.CELL, nyamirambo);
        Location kivugiza    = saveIfNotExists("NM-KV", "Kivugiza",   ELocationType.CELL, nyamirambo);
        Location rugarama    = saveIfNotExists("NM-RG", "Rugarama",   ELocationType.CELL, nyamirambo);

        // CELLS – GAHANGA (Kicukiro)
        Location gahangaCell = saveIfNotExists("GH-GH", "Gahanga",   ELocationType.CELL, gahanga);
        Location gasharu     = saveIfNotExists("GH-GS", "Gasharu",   ELocationType.CELL, gahanga);
        Location ruhuha      = saveIfNotExists("GH-RH", "Ruhuha",    ELocationType.CELL, gahanga);

        // CELLS – GIKONDO (Kicukiro)
        Location gikondoCell = saveIfNotExists("GD-GD", "Gikondo",  ELocationType.CELL, gikondo);
        Location nyagisenyi  = saveIfNotExists("GD-NY", "Nyagisenyi",ELocationType.CELL, gikondo);
        Location rugenge     = saveIfNotExists("GD-RG", "Rugenge",   ELocationType.CELL, gikondo);

        // CELLS – NIBOYE (Kicukiro)
        Location gatenga2    = saveIfNotExists("NB-GT", "Gatenga",   ELocationType.CELL, niboye);
        Location niboyeCell  = saveIfNotExists("NB-NB", "Niboye",    ELocationType.CELL, niboye);
        Location nyabisindu  = saveIfNotExists("NB-NS", "Nyabisindu",ELocationType.CELL, niboye);

        
        // VILLAGES – REMERA SECTOR
        
        saveIfNotExists("BB-V1", "Akabeza",     ELocationType.VILLAGE, bibare);
        saveIfNotExists("BB-V2", "Akagaju",     ELocationType.VILLAGE, bibare);
        saveIfNotExists("BB-V3", "Akarama",     ELocationType.VILLAGE, bibare);
        saveIfNotExists("GH-V1", "Rwampara",    ELocationType.VILLAGE, gahogo);
        saveIfNotExists("GH-V2", "Gahogo",      ELocationType.VILLAGE, gahogo);
        saveIfNotExists("GH-V3", "Inganzo",     ELocationType.VILLAGE, gahogo);
        saveIfNotExists("KB-V1", "Kibari",      ELocationType.VILLAGE, kibari);
        saveIfNotExists("KB-V2", "Nyarurama",   ELocationType.VILLAGE, kibari);
        saveIfNotExists("KB-V3", "Kabuye",      ELocationType.VILLAGE, kibari);
        saveIfNotExists("RK-V1", "Gasabo",      ELocationType.VILLAGE, rukiri);
        saveIfNotExists("RK-V2", "Rukiri",      ELocationType.VILLAGE, rukiri);
        saveIfNotExists("TZ-V1", "Tatizo",      ELocationType.VILLAGE, tatizo);
        saveIfNotExists("TZ-V2", "Rugando",     ELocationType.VILLAGE, tatizo);

        
        // VILLAGES – KACYIRU SECTOR
        
        saveIfNotExists("KM-V1", "Kamatamu",    ELocationType.VILLAGE, kamatamu);
        saveIfNotExists("KM-V2", "Rusine",      ELocationType.VILLAGE, kamatamu);
        saveIfNotExists("KB2-V1","Kabyutso",    ELocationType.VILLAGE, kabyutso);
        saveIfNotExists("KB2-V2","Nyarutarama", ELocationType.VILLAGE, kabyutso);
        saveIfNotExists("KG-V1", "Kagugu",      ELocationType.VILLAGE, kagugu);
        saveIfNotExists("KG-V2", "Ubumwe",      ELocationType.VILLAGE, kagugu);
        saveIfNotExists("KC2-V1","Agakura",     ELocationType.VILLAGE, kacyiruCell);
        saveIfNotExists("KC2-V2","Agatare",     ELocationType.VILLAGE, kacyiruCell);
        saveIfNotExists("KC2-V3","Akabahizi",   ELocationType.VILLAGE, kacyiruCell);

       
        // VILLAGES – KIMIHURURA SECTOR
        
        saveIfNotExists("BR-V1", "Biryogo",     ELocationType.VILLAGE, biryogo);
        saveIfNotExists("BR-V2", "Munanira 1",  ELocationType.VILLAGE, biryogo);
        saveIfNotExists("KV-V1", "Kiyovu",      ELocationType.VILLAGE, kiyovu);
        saveIfNotExists("KV-V2", "Rugando",     ELocationType.VILLAGE, kiyovu);
        saveIfNotExists("KH-V1", "Kimihurura",  ELocationType.VILLAGE, kimihururaCell);
        saveIfNotExists("KH-V2", "Akabacuzi",   ELocationType.VILLAGE, kimihururaCell);
        saveIfNotExists("RGD-V1","Rugando",     ELocationType.VILLAGE, rugando);
        saveIfNotExists("RGD-V2","Gacuriro",    ELocationType.VILLAGE, rugando);

        
        // VILLAGES – GISOZI SECTOR
       
        saveIfNotExists("GSZ-V1","Gisozi",      ELocationType.VILLAGE, gisoziCell);
        saveIfNotExists("GSZ-V2","Akera",       ELocationType.VILLAGE, gisoziCell);
        saveIfNotExists("NKM-V1","Nkamira",     ELocationType.VILLAGE, nkamira);
        saveIfNotExists("NKM-V2","Rusave",      ELocationType.VILLAGE, nkamira);

        
        // VILLAGES – KINYINYA SECTOR
        
        saveIfNotExists("KNY-V1","Kinyinya",    ELocationType.VILLAGE, kinyinyaCell);
        saveIfNotExists("KNY-V2","Agakiriro",   ELocationType.VILLAGE, kinyinyaCell);
        saveIfNotExists("RSG-V1","Rusengero",   ELocationType.VILLAGE, rusengero);
        saveIfNotExists("RSG-V2","Murehe",      ELocationType.VILLAGE, rusengero);

       
        // VILLAGES – GITEGA SECTOR (Nyarugenge)
        
        saveIfNotExists("AKB-V1","Akabahizi",   ELocationType.VILLAGE, akabahizi);
        saveIfNotExists("AKB-V2","Agakiriro",   ELocationType.VILLAGE, akabahizi);
        saveIfNotExists("ABK-V1","Akabukire",   ELocationType.VILLAGE, akabukire);
        saveIfNotExists("ABK-V2","Agasharu",    ELocationType.VILLAGE, akabukire);
        saveIfNotExists("IMP-V1","Impinga",      ELocationType.VILLAGE, impinga);
        saveIfNotExists("IMP-V2","Agakiriro",   ELocationType.VILLAGE, impinga);

        
        // VILLAGES – KIMISAGARA SECTOR (Nyarugenge)
       
        saveIfNotExists("CYH-V1","Cyahafi",     ELocationType.VILLAGE, cyahafi);
        saveIfNotExists("CYH-V2","Gasabo",      ELocationType.VILLAGE, cyahafi);
        saveIfNotExists("KMS-V1","Kimisagara",  ELocationType.VILLAGE, kimisagaraCell);
        saveIfNotExists("KMS-V2","Agasharu",    ELocationType.VILLAGE, kimisagaraCell);
        saveIfNotExists("KWA-V1","Kwa Rubangura",ELocationType.VILLAGE,kwa);
        saveIfNotExists("KWA-V2","Inkalindi",   ELocationType.VILLAGE, kwa);

        // VILLAGES – NYAMIRAMBO SECTOR (Nyarugenge)
        
        saveIfNotExists("BYN-V1","Biryogo I",   ELocationType.VILLAGE, biryogoNY);
        saveIfNotExists("BYN-V2","Biryogo II",  ELocationType.VILLAGE, biryogoNY);
        saveIfNotExists("BKN-V1","Bukinanyana", ELocationType.VILLAGE, bukinanyana);
        saveIfNotExists("BKN-V2","Uwimpundu",   ELocationType.VILLAGE, bukinanyana);
        saveIfNotExists("KVZ-V1","Kivugiza",    ELocationType.VILLAGE, kivugiza);
        saveIfNotExists("KVZ-V2","Kagoma",      ELocationType.VILLAGE, kivugiza);
        saveIfNotExists("RGM-V1","Rugarama",    ELocationType.VILLAGE, rugarama);
        saveIfNotExists("RGM-V2","Agashogore",  ELocationType.VILLAGE, rugarama);

        
        // VILLAGES – GAHANGA SECTOR (Kicukiro)
       
        saveIfNotExists("GHG-V1","Gahanga I",   ELocationType.VILLAGE, gahangaCell);
        saveIfNotExists("GHG-V2","Gahanga II",  ELocationType.VILLAGE, gahangaCell);
        saveIfNotExists("GSR-V1","Gasharu I",   ELocationType.VILLAGE, gasharu);
        saveIfNotExists("GSR-V2","Gasharu II",  ELocationType.VILLAGE, gasharu);
        saveIfNotExists("RHH-V1","Ruhuha I",    ELocationType.VILLAGE, ruhuha);
        saveIfNotExists("RHH-V2","Ruhuha II",   ELocationType.VILLAGE, ruhuha);

        
        // VILLAGES – GIKONDO SECTOR (Kicukiro)
        
        saveIfNotExists("GKD-V1","Gikondo I",   ELocationType.VILLAGE, gikondoCell);
        saveIfNotExists("GKD-V2","Gikondo II",  ELocationType.VILLAGE, gikondoCell);
        saveIfNotExists("NYG-V1","Nyagisenyi I",ELocationType.VILLAGE, nyagisenyi);
        saveIfNotExists("NYG-V2","Nyagisenyi II",ELocationType.VILLAGE,nyagisenyi);
        saveIfNotExists("RGG-V1","Rugenge I",   ELocationType.VILLAGE, rugenge);
        saveIfNotExists("RGG-V2","Rugenge II",  ELocationType.VILLAGE, rugenge);

        
        // VILLAGES – NIBOYE SECTOR (Kicukiro)

        saveIfNotExists("NBY-V1","Niboye I",    ELocationType.VILLAGE, niboyeCell);
        saveIfNotExists("NBY-V2","Niboye II",   ELocationType.VILLAGE, niboyeCell);
        saveIfNotExists("NSB-V1","Nyabisindu I",ELocationType.VILLAGE, nyabisindu);
        saveIfNotExists("NSB-V2","Nyabisindu II",ELocationType.VILLAGE,nyabisindu);

        System.out.println(">>> DataSeeder: Rwanda provinces and full Kigali hierarchy seeded successfully.");
    }
}
