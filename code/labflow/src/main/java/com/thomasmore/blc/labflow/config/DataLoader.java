package com.thomasmore.blc.labflow.config;

import com.thomasmore.blc.labflow.entity.*;
import com.thomasmore.blc.labflow.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final EenheidRepository eenheidRepository;
    private final TestCategorieRepository testCategorieRepository;
    private final TestRepository testRepository;
    private final StaalRepository staalRepository;
    private final ReferentiewaardeRepository referentiewaardeRepository;


    public DataLoader(UserRepository userRepository,
                      RolRepository rolRepository,
                      EenheidRepository eenheidRepository,
                      TestCategorieRepository testCategorieRepository,
                      TestRepository testRepository,
                      StaalRepository staalRepository,
                      ReferentiewaardeRepository referentiewaardeRepository) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.eenheidRepository = eenheidRepository;
        this.testCategorieRepository = testCategorieRepository;
        this.testRepository = testRepository;
        this.staalRepository = staalRepository;
        this.referentiewaardeRepository = referentiewaardeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // aanmaken rollen
        Rol rol_admin = new Rol("admin");
        Rol rol_student = new Rol("student");
        rolRepository.save(rol_admin);
        rolRepository.save(rol_student);

        // aanmaken users
        User user1 = new User("123", "nathanneve@test.be", "Nathan", "Neve", rol_student);
        User user2 = new User("456", "césarvanleuffelen@test.be", "César", "van Leuffelen", rol_admin);
        userRepository.save(user1);
        userRepository.save(user2);

        // aanmaken testcategorie
        Testcategorie edtaCat = new Testcategorie("EDTA", "paars");
        Testcategorie citraatCat = new Testcategorie("citraat", "blauw");
        Testcategorie serumCat = new Testcategorie("serum", "rood");
        Testcategorie heparineCat = new Testcategorie("heparine", "groen");
        Testcategorie fluorideCat = new Testcategorie("Fluoride", "grijs");
        Testcategorie urineCat = new Testcategorie("Urine", "geel");
        testCategorieRepository.save(edtaCat);
        testCategorieRepository.save(citraatCat);
        testCategorieRepository.save(serumCat);
        testCategorieRepository.save(heparineCat);
        testCategorieRepository.save(fluorideCat);
        testCategorieRepository.save(urineCat);

        // Stalen
        Staal staal1 = new Staal(2024000001, "Emma", "Janssen", java.sql.Date.valueOf("1990-05-14"), 'V', "Pieter", "RN12345", user1);
        Staal staal2 = new Staal(2024000002, "Lucas", "Peeters", java.sql.Date.valueOf("1985-07-21"), 'M', "Sofie", "RN67890", user2);
        Staal staal3 = new Staal(2024000003, "Mila", "Vermeulen", java.sql.Date.valueOf("1993-02-11"), 'V', "Bart", "RN13579", user1);
        Staal staal4 = new Staal(2024000004, "Liam", "Claes", java.sql.Date.valueOf("1992-11-30"), 'M', "Lies", "RN24680", user2);
        Staal staal5 = new Staal(2024000005, "Olivia", "Dubois", java.sql.Date.valueOf("2000-01-08"), 'V', "An", "RN11223", user1);
        Staal staal6 = new Staal(2024000006, "Noah", "De Smet", java.sql.Date.valueOf("1987-03-16"), 'M', "Koen", "RN44556", user2);
        Staal staal7 = new Staal(2024000007, "Marie", "De Vries", java.sql.Date.valueOf("1991-09-25"), 'V', "Katrien", "RN77889", user1);
        Staal staal8 = new Staal(2024000008, "Arthur", "Van Damme", java.sql.Date.valueOf("1988-12-19"), 'M', "Jan", "RN99001", user2);
        Staal staal9 = new Staal(2024000009, "Charlotte", "Jacobs", java.sql.Date.valueOf("1995-04-03"), 'V', "Eva", "RN22334", user1);
        Staal staal10 = new Staal(2024000010, "Victor", "Maes", java.sql.Date.valueOf("1990-06-10"), 'M', "Lotte", "RN55667", user2);

        staalRepository.save(staal1);
        staalRepository.save(staal2);
        staalRepository.save(staal3);
        staalRepository.save(staal4);
        staalRepository.save(staal5);
        staalRepository.save(staal6);
        staalRepository.save(staal7);
        staalRepository.save(staal8);
        staalRepository.save(staal9);
        staalRepository.save(staal10);

        // aanmaken eenheden
        Eenheid gramPerDeciliter = new Eenheid("gram per deciliter", "g/dl");
        Eenheid percentage = new Eenheid("percentage", "%");
        Eenheid cellsPerMicroliter = new Eenheid("cells per microliter", "cellen/µl");
        Eenheid femtoliter = new Eenheid("femtoliter", "fl");
        Eenheid picogram = new Eenheid("picogram", "pg");
        Eenheid plateletsPerMicroliter = new Eenheid("platelets per microliter", "PLT/µl");
        Eenheid per1000RedBloodCells = new Eenheid("per 1000 red blood cells", "per 1000 RBC");
        Eenheid notAvailable = new Eenheid("not available", "/");
        Eenheid seconds = new Eenheid("seconds", "sec");
        Eenheid millimetersPerHour = new Eenheid("millimeters per hour", "mm/u");
        Eenheid internationalUnitsPerMilliliter = new Eenheid("international units per milliliter", "IU/ml");
        Eenheid milligramsPerDeciliter = new Eenheid("milligrams per deciliter", "mg/dL");
        Eenheid micromolesPerLiter = new Eenheid("micromoles per liter", "µmol/L");
        Eenheid millimolesPerLiter = new Eenheid("millimoles per liter", "mmol/L");
        Eenheid milliosmolesPerKilogram = new Eenheid("milliosmoles per kilogram", "mOsmol/kg");
        Eenheid unitsPerLiter = new Eenheid("units per liter", "U/L");
        Eenheid gramsPerLiter = new Eenheid("grams per liter", "g/L");
        Eenheid milligramsPerLiter = new Eenheid("milligrams per liter", "mg/L");
        Eenheid nanogramsPerMilliliter = new Eenheid("nanograms per milliliter", "ng/mL");
        Eenheid nanogramsPerLiter = new Eenheid("nanograms per liter", "ng/L");
        Eenheid microgramsPerDeciliter = new Eenheid("micrograms per deciliter", "µg/dL");
        Eenheid microgramsPerLiter = new Eenheid("micrograms per liter", "µg/L");
        Eenheid milliunitsPerLiter = new Eenheid("milliunits per liter", "mU/L");
        Eenheid picomolesPerLiter = new Eenheid("picomoles per liter", "pmol/L");
        Eenheid millilitersPerMinute = new Eenheid("milliliters per minute", "mL/min");
        Eenheid milliequivalentsPerLiter = new Eenheid("millimol per liter", "mmol/L");

        eenheidRepository.save(gramPerDeciliter);
        eenheidRepository.save(percentage);
        eenheidRepository.save(cellsPerMicroliter);
        eenheidRepository.save(femtoliter);
        eenheidRepository.save(picogram);
        eenheidRepository.save(plateletsPerMicroliter);
        eenheidRepository.save(per1000RedBloodCells);
        eenheidRepository.save(notAvailable);
        eenheidRepository.save(seconds);
        eenheidRepository.save(millimetersPerHour);
        eenheidRepository.save(internationalUnitsPerMilliliter);
        eenheidRepository.save(milligramsPerDeciliter);
        eenheidRepository.save(micromolesPerLiter);
        eenheidRepository.save(millimolesPerLiter);
        eenheidRepository.save(milliosmolesPerKilogram);
        eenheidRepository.save(unitsPerLiter);
        eenheidRepository.save(gramsPerLiter);
        eenheidRepository.save(milligramsPerLiter);
        eenheidRepository.save(nanogramsPerMilliliter);
        eenheidRepository.save(nanogramsPerLiter);
        eenheidRepository.save(microgramsPerDeciliter);
        eenheidRepository.save(microgramsPerLiter);
        eenheidRepository.save(milliunitsPerLiter);
        eenheidRepository.save(picomolesPerLiter);
        eenheidRepository.save(millilitersPerMinute);
        eenheidRepository.save(milliequivalentsPerLiter);

        // Aanmaken van tests met hun codes en opslaan
        Test test1 = new Test("601", "Hemoglobine", gramPerDeciliter, edtaCat);
        testRepository.save(test1);
        Test test2 = new Test("602", "Hematocriet", percentage, edtaCat);
        testRepository.save(test2);
        Test test3 = new Test("603", "RBC", cellsPerMicroliter, edtaCat);
        testRepository.save(test3);
        Test test4 = new Test("604", "MCV", femtoliter, edtaCat);
        testRepository.save(test4);
        Test test5 = new Test("605", "MCH", picogram, edtaCat);
        testRepository.save(test5);
        Test test6 = new Test("606", "MCHC", gramPerDeciliter, edtaCat);
        testRepository.save(test6);
        Test test7 = new Test("607", "WBC", cellsPerMicroliter, edtaCat);
        testRepository.save(test7);
        Test test8 = new Test("608", "Formule: staafkerninge neutrofielen, segmentkerninge neutrofielen, lymfocyten, monocyten, eosinofielen, basofielen", notAvailable, edtaCat);
        testRepository.save(test8);
        Test test9 = new Test("609", "Thrombocyten", plateletsPerMicroliter, edtaCat);
        testRepository.save(test9);
        Test test10 = new Test("610", "Reticulocyten", cellsPerMicroliter, edtaCat);
        testRepository.save(test10);
        Test test11 = new Test("611", "Foetale RBC", cellsPerMicroliter, edtaCat);
        testRepository.save(test11);
        Test test12 = new Test("64", "Bloedgroep ABO-D", notAvailable, edtaCat);
        testRepository.save(test12);
        Test test13 = new Test("65", "Cc Ee Kell", notAvailable, edtaCat);
        testRepository.save(test13);
        Test test14 = new Test("66", "Screening op irregulier AL", notAvailable, edtaCat);
        testRepository.save(test14);
        Test test15 = new Test("67", "Identificatie van irreguliere AL", notAvailable, edtaCat);
        testRepository.save(test15);
        Test test16 = new Test("68", "Kruisproef", notAvailable, edtaCat);
        testRepository.save(test16);
        Test test17 = new Test("630", "PT", seconds, citraatCat);
        testRepository.save(test17);
        Test test18 = new Test("631", "APTT", seconds, citraatCat);
        testRepository.save(test18);
        Test test19 = new Test("221", "ESR", millimetersPerHour, serumCat);
        testRepository.save(test19);
        Test test20 = new Test("222", "Reumafactor", notAvailable, serumCat);
        testRepository.save(test20);
        Test test21 = new Test("223", "ANA", notAvailable, serumCat);
        testRepository.save(test21);
        Test test22 = new Test("224", "HLA B27", notAvailable, serumCat);
        testRepository.save(test22);
        Test test23 = new Test("280", "Epstein Barr IgG", notAvailable, serumCat);
        testRepository.save(test23);
        Test test24 = new Test("281", "Epstein Barr Paul & Bunnell", notAvailable, serumCat);
        testRepository.save(test24);
        Test test25 = new Test("270", "ASLO", notAvailable, serumCat);
        testRepository.save(test25);
        Test test26 = new Test("727", "Flow cytometrie volbloed", notAvailable, edtaCat);
        testRepository.save(test26);
        Test test27 = new Test("728", "Immunofentoypering", notAvailable, edtaCat);
        testRepository.save(test27);
        Test test28 = new Test("729", "Karyotypering", notAvailable, serumCat);
        testRepository.save(test28);
        Test test29 = new Test("730", "Glucose 6 fosfaat dehydrogenase", notAvailable, serumCat);
        testRepository.save(test29);
        Test test30 = new Test("100", "Glucose", milligramsPerDeciliter, serumCat);
        testRepository.save(test30);
        Test test31 = new Test("103", "Hemoglobine A1c", percentage, serumCat);
        testRepository.save(test31);
        Test test32 = new Test("120", "Cholesterol", milligramsPerDeciliter, serumCat);
        testRepository.save(test32);
        Test test33 = new Test("121", "HDL-cholesterol", milligramsPerDeciliter, serumCat);
        testRepository.save(test33);
        Test test34 = new Test("122", "LDL-Cholesterol", milligramsPerDeciliter, serumCat);
        testRepository.save(test34);
        Test test35 = new Test("123", "Triglyceriden", milligramsPerDeciliter, serumCat);
        testRepository.save(test35);
        Test test36 = new Test("130", "Ureum", milligramsPerDeciliter, serumCat);
        testRepository.save(test36);
        Test test37 = new Test("131", "Creatinine", milligramsPerDeciliter, serumCat);
        testRepository.save(test37);
        Test test38 = new Test("132", "Urinezuur", micromolesPerLiter, serumCat);
        testRepository.save(test38);
        Test test39 = new Test("141", "Natrium", milliequivalentsPerLiter, serumCat);
        testRepository.save(test39);
        Test test40 = new Test("142", "Kalium", milliequivalentsPerLiter, serumCat);
        testRepository.save(test40);
        Test test41 = new Test("143", "Chloride", milliequivalentsPerLiter, serumCat);
        testRepository.save(test41);
        Test test42 = new Test("144", "Bicarbonaat", milliequivalentsPerLiter, serumCat);
        testRepository.save(test42);
        Test test43 = new Test("145", "Calcium", milligramsPerDeciliter, serumCat);
        testRepository.save(test43);
        Test test44 = new Test("156", "Fosfaat", milligramsPerDeciliter, serumCat);
        testRepository.save(test44);
        Test test45 = new Test("157", "Magnesium", milligramsPerDeciliter, serumCat);
        testRepository.save(test45);
        Test test46 = new Test("203", "Osmolaliteit", milliosmolesPerKilogram, serumCat);
        testRepository.save(test46);
        Test test47 = new Test("180", "AST", unitsPerLiter, serumCat);
        testRepository.save(test47);
        Test test48 = new Test("181", "ALT", unitsPerLiter, serumCat);
        testRepository.save(test48);
        Test test49 = new Test("182", "AF", unitsPerLiter, serumCat);
        testRepository.save(test49);
        Test test50 = new Test("184", "GGT", unitsPerLiter, serumCat);
        testRepository.save(test50);
        Test test51 = new Test("194", "Bilirubine totaal", milligramsPerDeciliter, serumCat);
        testRepository.save(test51);
        Test test52 = new Test("195", "Bilirubine direct", milligramsPerDeciliter, serumCat);
        testRepository.save(test52);
        Test test53 = new Test("185", "LDH", unitsPerLiter, serumCat);
        testRepository.save(test53);
        Test test54 = new Test("186", "CK", unitsPerLiter, serumCat);
        testRepository.save(test54);
        Test test55 = new Test("187", "Troponine", unitsPerLiter, serumCat);
        testRepository.save(test55);
        Test test56 = new Test("188", "CK-MB", unitsPerLiter, serumCat);
        testRepository.save(test56);
        Test test57 = new Test("160", "Totaal eiwit", gramsPerLiter, serumCat);
        testRepository.save(test57);
        Test test58 = new Test("161", "Albumine", gramsPerLiter, serumCat);
        testRepository.save(test58);
        Test test59 = new Test("162", "Eiwit elektroforese", notAvailable, serumCat);
        testRepository.save(test59);
        Test test60 = new Test("163", "Immuunfixatie", notAvailable, serumCat);
        testRepository.save(test60);
        Test test61 = new Test("164", "CRP", notAvailable, serumCat);
        testRepository.save(test61);
        Test test62 = new Test("210", "Vit. D3 (25-OH)", notAvailable, serumCat);
        testRepository.save(test62);
        Test test63 = new Test("211", "Vit. B12", notAvailable, serumCat);
        testRepository.save(test63);
        Test test64 = new Test("620", "Ijzer", milligramsPerDeciliter, serumCat);
        testRepository.save(test64);
        Test test65 = new Test("621", "TIBC", notAvailable, serumCat);
        testRepository.save(test65);
        Test test66 = new Test("622", "Transferrine", notAvailable, serumCat);
        testRepository.save(test66);
        Test test67 = new Test("623", "Foliumzuur", notAvailable, serumCat);
        testRepository.save(test67);
        Test test68 = new Test("624", "Ferritine", notAvailable, serumCat);
        testRepository.save(test68);
        Test test69 = new Test("390", "TSH", notAvailable, serumCat);
        testRepository.save(test69);
        Test test70 = new Test("391", "Vrije T3", notAvailable, serumCat);
        testRepository.save(test70);
        Test test71 = new Test("392", "Vrije T4", notAvailable, serumCat);
        testRepository.save(test71);
        Test test72 = new Test("430", "HCG", unitsPerLiter, serumCat);
        testRepository.save(test72);
        Test test73 = new Test("431", "LH", unitsPerLiter, serumCat);
        testRepository.save(test73);
        Test test74 = new Test("550", "Totaal Eiwit", gramsPerLiter, serumCat);
        testRepository.save(test74);
        Test test75 = new Test("551", "Glucose", gramsPerLiter, serumCat);
        testRepository.save(test75);
        Test test76 = new Test("552", "Osmolaliteit", milliosmolesPerKilogram, serumCat);
        testRepository.save(test76);
        Test test77 = new Test("553", "Creatinine clearance", millilitersPerMinute, serumCat);
        testRepository.save(test77);


        // referentiewaarden aanmaken
// Hematologie (EDTA)
        referentiewaardeRepository.save(new Referentiewaarde("man 14-18", test1));
        referentiewaardeRepository.save(new Referentiewaarde("vrouw 12-16", test1));

        referentiewaardeRepository.save(new Referentiewaarde("man 40-52", test2));
        referentiewaardeRepository.save(new Referentiewaarde("vrouw 35-47", test2));

        referentiewaardeRepository.save(new Referentiewaarde("man 4,5-5,9 x 106", test3));
        referentiewaardeRepository.save(new Referentiewaarde("vrouw 3,8-5,2 x 106", test3));

        referentiewaardeRepository.save(new Referentiewaarde("89+5", test4));
        referentiewaardeRepository.save(new Referentiewaarde("29+2", test5));
        referentiewaardeRepository.save(new Referentiewaarde("33+2", test6));
        referentiewaardeRepository.save(new Referentiewaarde("4000-10000", test7));

// Formule: staafkerninge neutrofielen, segmentkerninge neutrofielen, lymfocyten, monocyten, eosinofielen, basofielen
        referentiewaardeRepository.save(new Referentiewaarde("staafkerninge neutrofielen 5-16", test8));
        referentiewaardeRepository.save(new Referentiewaarde("segmentkerninge neutrofielen 50-70", test8));
        referentiewaardeRepository.save(new Referentiewaarde("lymfocyten 20-35", test8));
        referentiewaardeRepository.save(new Referentiewaarde(" monocyten 2-6", test8));
        referentiewaardeRepository.save(new Referentiewaarde("eosinofielen 0-1", test8));
        referentiewaardeRepository.save(new Referentiewaarde("basofielen 0-1", test8));

        referentiewaardeRepository.save(new Referentiewaarde("150000-400000", test9));
        referentiewaardeRepository.save(new Referentiewaarde("2-20", test10));
        referentiewaardeRepository.save(new Referentiewaarde("ja/nee", test11));

// Immuunhematologie (EDTA)
        referentiewaardeRepository.save(new Referentiewaarde("A/B/AB/O", test12));
        referentiewaardeRepository.save(new Referentiewaarde("D+/D-", test12));

        referentiewaardeRepository.save(new Referentiewaarde("C/c/E/e +/-", test13));
        referentiewaardeRepository.save(new Referentiewaarde("Kell +/-", test13));

        referentiewaardeRepository.save(new Referentiewaarde(" +/-", test14));
        referentiewaardeRepository.save(new Referentiewaarde("hier graag een tekstvlak om zelf uitslag in te typen", test15));
        referentiewaardeRepository.save(new Referentiewaarde(" +/-", test16));

// Hemostase (citraat)
        referentiewaardeRepository.save(new Referentiewaarde("10-15", test17));
        referentiewaardeRepository.save(new Referentiewaarde("32-42", test18));

// Serologie Inflammatoir Rheuma & Auto-immuniteit (serum)
        referentiewaardeRepository.save(new Referentiewaarde("man 0-5", test19));
        referentiewaardeRepository.save(new Referentiewaarde("vrouw 0-7", test19));
        referentiewaardeRepository.save(new Referentiewaarde(" +/-", test20));
        referentiewaardeRepository.save(new Referentiewaarde("hier graag een tekstvlak om zelf uitslag in te typen", test21));
        referentiewaardeRepository.save(new Referentiewaarde(" +/-", test22));

// Serologie Viraal (serum)
        referentiewaardeRepository.save(new Referentiewaarde("negatief ≤0,90 /hertesten 0,91-1,09 / positief ≥1,10", test23));
        referentiewaardeRepository.save(new Referentiewaarde(" +/-", test24));

// Serologie Bacterieel (serum)
        referentiewaardeRepository.save(new Referentiewaarde("positief >200", test25));

// Flow cytometrie (EDTA)
        referentiewaardeRepository.save(new Referentiewaarde("hier graag een tekstvlak om zelf uitslag in te typen", test26));
        referentiewaardeRepository.save(new Referentiewaarde("hier graag een tekstvlak om zelf uitslag in te typen", test27));

// Genetica
        referentiewaardeRepository.save(new Referentiewaarde("hier graag een tekstvlak om zelf uitslag in te typen", test28));

// Metabole aandoeningen
        referentiewaardeRepository.save(new Referentiewaarde("hier graag een tekstvlak om zelf uitslag in te typen", test29));

// Biochemie - Suikers
        referentiewaardeRepository.save(new Referentiewaarde("70-105", test30));
        referentiewaardeRepository.save(new Referentiewaarde("4-6%", test31));

// Biochemie - Vetten
        referentiewaardeRepository.save(new Referentiewaarde("<200", test32));
        referentiewaardeRepository.save(new Referentiewaarde(">60", test33));
        referentiewaardeRepository.save(new Referentiewaarde("<100", test34));
        referentiewaardeRepository.save(new Referentiewaarde("<150", test35));

// Biochemie - Nier
        referentiewaardeRepository.save(new Referentiewaarde("15-39", test36));
        referentiewaardeRepository.save(new Referentiewaarde("man 0,9-1,3", test37));
        referentiewaardeRepository.save(new Referentiewaarde("vrouw 0,6-1,1", test37));
        referentiewaardeRepository.save(new Referentiewaarde("man 3,5-7,2", test38));
        referentiewaardeRepository.save(new Referentiewaarde("vrouw 2,6-6,0", test38));

// Electrolytes
        referentiewaardeRepository.save(new Referentiewaarde("136-146", test39));
        referentiewaardeRepository.save(new Referentiewaarde("3,5-5,1", test40));
        referentiewaardeRepository.save(new Referentiewaarde("101-109", test41));
        referentiewaardeRepository.save(new Referentiewaarde("21-31", test42));
        referentiewaardeRepository.save(new Referentiewaarde("2,15-2,55", test43));
        referentiewaardeRepository.save(new Referentiewaarde("0,8-1,4", test44));
        referentiewaardeRepository.save(new Referentiewaarde("0,75-0,95", test45));
        referentiewaardeRepository.save(new Referentiewaarde("<61 jaar: 275-295, >61 jaar: 280-301", test46));

// Enzymes
        referentiewaardeRepository.save(new Referentiewaarde("<50", test47));
        referentiewaardeRepository.save(new Referentiewaarde("<41", test48));
        referentiewaardeRepository.save(new Referentiewaarde("17-45", test49));
        referentiewaardeRepository.save(new Referentiewaarde("man: <22, vrouw: <15", test50));
        referentiewaardeRepository.save(new Referentiewaarde("<2", test51));
        referentiewaardeRepository.save(new Referentiewaarde("<0,3", test52));
        referentiewaardeRepository.save(new Referentiewaarde("<248", test53));
        referentiewaardeRepository.save(new Referentiewaarde("man: 10-65, vrouw: 7-55", test54));
        referentiewaardeRepository.save(new Referentiewaarde("man: <19,8, vrouw: <11,6", test55));
        referentiewaardeRepository.save(new Referentiewaarde("<25", test56));

// Proteins
        referentiewaardeRepository.save(new Referentiewaarde("63-83", test57));
        referentiewaardeRepository.save(new Referentiewaarde("35-52", test58));
        referentiewaardeRepository.save(new Referentiewaarde("Albumine: 59,8-72,4, alfa-1 globulinen: 1-3,2%, alfa-2 globulinen: 7,4-12,6%, Bèta globulinen: 7,5-12,9%, Gamma globulinen: 8-15,8", test59));
        referentiewaardeRepository.save(new Referentiewaarde("hier graag een tekstvlak om zelf uitslag in te typen", test60));

// Referentiewaarden for the given tests

        referentiewaardeRepository.save(new Referentiewaarde("<5", test61)); // CRP
        referentiewaardeRepository.save(new Referentiewaarde("30-100", test62)); // Vit. D3 (25-OH)
        referentiewaardeRepository.save(new Referentiewaarde("180-914", test63)); // Vit. B12
        referentiewaardeRepository.save(new Referentiewaarde("man: 65-175, vrouw: 50-170", test64)); // Ijzer
        referentiewaardeRepository.save(new Referentiewaarde("250-425", test65)); // TIBC
        referentiewaardeRepository.save(new Referentiewaarde("2-3,60", test66)); // Transferrine
        referentiewaardeRepository.save(new Referentiewaarde(">3,5", test67)); // Foliumzuur
        referentiewaardeRepository.save(new Referentiewaarde("man: 25-250, vrouw: 20-250", test68)); // Ferritine
        referentiewaardeRepository.save(new Referentiewaarde("0,38-5,33", test69)); // TSH
        referentiewaardeRepository.save(new Referentiewaarde("3,85-6,01", test70)); // Vrije T3
        referentiewaardeRepository.save(new Referentiewaarde("7,85-14,41", test71)); // Vrije T4

// Referentiewaarden for Biochemie - Gonaden tests
        referentiewaardeRepository.save(new Referentiewaarde("<5", test72)); // HCG
        referentiewaardeRepository.save(new Referentiewaarde("LH Mid-folliculair 2.1-10.9", test73)); // LH Mid-folliculair
        referentiewaardeRepository.save(new Referentiewaarde("LH Mid-cyclus 19.2-103", test73)); // LH Mid-cyclus
        referentiewaardeRepository.save(new Referentiewaarde("LH Mid-luteaal 1.2-12.9", test73)); // LH Mid-luteaal
        referentiewaardeRepository.save(new Referentiewaarde("LH Postmenopause 10.9-58.6", test73)); // LH Postmenopause
        referentiewaardeRepository.save(new Referentiewaarde("man 1.2-8.6", test73)); // LH man

// Referentiewaarden for Biochemie - Urine tests
        referentiewaardeRepository.save(new Referentiewaarde("<0.16", test74)); // Totaal Eiwit
        referentiewaardeRepository.save(new Referentiewaarde("<0.15", test75)); // Glucose
        referentiewaardeRepository.save(new Referentiewaarde("50-1200", test76)); // Osmolaliteit
        referentiewaardeRepository.save(new Referentiewaarde("71-151", test77)); // Creatinine clearance
    }
}