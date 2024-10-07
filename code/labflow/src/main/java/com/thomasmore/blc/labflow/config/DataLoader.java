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
        testRepository.save(new Test(601, "Hemoglobine", gramPerDeciliter, edtaCat));
        testRepository.save(new Test(602, "Hematocriet", percentage, edtaCat));
        testRepository.save(new Test(603, "RBC", cellsPerMicroliter, edtaCat));
        testRepository.save(new Test(604, "MCV", femtoliter, edtaCat));
        testRepository.save(new Test(605, "MCH", picogram, edtaCat));
        testRepository.save(new Test(606, "MCHC", gramPerDeciliter, edtaCat));
        testRepository.save(new Test(607, "WBC", cellsPerMicroliter, edtaCat));
        testRepository.save(new Test(608, "Formule: staafkerninge neutrofielen, segmentkerninge neutrofielen, lymfocyten, monocyten, eosinofielen, basofielen", notAvailable, edtaCat));
        testRepository.save(new Test(609, "Thrombocyten", plateletsPerMicroliter, edtaCat));
        testRepository.save(new Test(610, "Reticulocyten", cellsPerMicroliter, edtaCat));
        testRepository.save(new Test(611, "Foetale RBC", cellsPerMicroliter, edtaCat));

        testRepository.save(new Test(64, "Bloedgroep ABO-D", notAvailable, edtaCat));
        testRepository.save(new Test(65, "Cc Ee Kell", notAvailable, edtaCat));
        testRepository.save(new Test(66, "Screening op irregulier AL", notAvailable, edtaCat));
        testRepository.save(new Test(67, "Identificatie van irreguliere AL", notAvailable, edtaCat));
        testRepository.save(new Test(68, "Kruisproef", notAvailable, edtaCat));

        testRepository.save(new Test(630, "PT", seconds, citraatCat));
        testRepository.save(new Test(631, "APTT", seconds, citraatCat));

        testRepository.save(new Test(221, "ESR", millimetersPerHour, serumCat));
        testRepository.save(new Test(222, "Reumafactor", notAvailable, serumCat));
        testRepository.save(new Test(223, "ANA", notAvailable, serumCat));
        testRepository.save(new Test(224, "HLA B27", notAvailable, serumCat));

        testRepository.save(new Test(280, "Epstein Barr IgG", notAvailable, serumCat));
        testRepository.save(new Test(281, "Epstein Barr Paul & Bunnell", notAvailable, serumCat));

        testRepository.save(new Test(270, "ASLO", notAvailable, serumCat));

        testRepository.save(new Test(727, "Flow cytometrie volbloed", notAvailable, edtaCat));
        testRepository.save(new Test(728, "Immunofentoypering", notAvailable, edtaCat));

        testRepository.save(new Test(729, "Karyotypering", notAvailable, serumCat));

        testRepository.save(new Test(730, "Glucose 6 fosfaat dehydrogenase", notAvailable, serumCat));

        testRepository.save(new Test(100, "Glucose", milligramsPerDeciliter, serumCat));
        testRepository.save(new Test(103, "Hemoglobine A1c", percentage, serumCat));

        testRepository.save(new Test(120, "Cholesterol", milligramsPerDeciliter, serumCat));
        testRepository.save(new Test(121, "HDL-cholesterol", milligramsPerDeciliter, serumCat));
        testRepository.save(new Test(122, "LDL-Cholesterol", milligramsPerDeciliter, serumCat));
        testRepository.save(new Test(123, "Triglyceriden", milligramsPerDeciliter, serumCat));

        testRepository.save(new Test(130, "Ureum", milligramsPerDeciliter, serumCat));
        testRepository.save(new Test(131, "Creatinine", milligramsPerDeciliter, serumCat));
        testRepository.save(new Test(132, "Urinezuur", micromolesPerLiter, serumCat));

        testRepository.save(new Test(141, "Natrium", milliequivalentsPerLiter, serumCat));
        testRepository.save(new Test(142, "Kalium", milliequivalentsPerLiter, serumCat));
        testRepository.save(new Test(143, "Chloride", milliequivalentsPerLiter, serumCat));
        testRepository.save(new Test(144, "Bicarbonaat", milliequivalentsPerLiter, serumCat));
        testRepository.save(new Test(145, "Calcium", milligramsPerDeciliter, serumCat));
        testRepository.save(new Test(156, "Fosfaat", milligramsPerDeciliter, serumCat));
        testRepository.save(new Test(157, "Magnesium", milligramsPerDeciliter, serumCat));
        testRepository.save(new Test(203, "Osmolaliteit", milliosmolesPerKilogram, serumCat));

        testRepository.save(new Test(180, "AST", unitsPerLiter, serumCat));
        testRepository.save(new Test(181, "ALT", unitsPerLiter, serumCat));
        testRepository.save(new Test(182, "AF", unitsPerLiter, serumCat));
        testRepository.save(new Test(184, "GGT", unitsPerLiter, serumCat));
        testRepository.save(new Test(194, "Bilirubine totaal", milligramsPerDeciliter, serumCat));
        testRepository.save(new Test(195, "Bilirubine direct", milligramsPerDeciliter, serumCat));
        testRepository.save(new Test(185, "LDH", unitsPerLiter, serumCat));
        testRepository.save(new Test(186, "CK", unitsPerLiter, serumCat));
        testRepository.save(new Test(187, "Troponine", unitsPerLiter, serumCat));
        testRepository.save(new Test(188, "CK-MB", unitsPerLiter, serumCat));

        testRepository.save(new Test(160, "Totaal eiwit", gramsPerLiter, serumCat));
        testRepository.save(new Test(161, "Albumine", gramsPerLiter, serumCat));
        testRepository.save(new Test(162, "Eiwit elektroforese", notAvailable, serumCat));
        testRepository.save(new Test(163, "Immuunfixatie", notAvailable, serumCat));
        testRepository.save(new Test(164, "CRP", notAvailable, serumCat));

        testRepository.save(new Test(210, "Vit. D3 (25-OH)", notAvailable, serumCat));
        testRepository.save(new Test(211, "Vit. B12", notAvailable, serumCat));

        testRepository.save(new Test(620, "Ijzer", milligramsPerDeciliter, serumCat));
        testRepository.save(new Test(621, "TIBC", notAvailable, serumCat));
        testRepository.save(new Test(622, "Transferrine", notAvailable, serumCat));
        testRepository.save(new Test(623, "Foliumzuur", notAvailable, serumCat));
        testRepository.save(new Test(624, "Ferritine", notAvailable, serumCat));

        testRepository.save(new Test(390, "TSH", notAvailable, serumCat));
        testRepository.save(new Test(391, "Vrije T3", notAvailable, serumCat));
        testRepository.save(new Test(392, "Vrije T4", notAvailable, serumCat));

        testRepository.save(new Test(600, "Hematologie, overige", notAvailable, edtaCat));
        testRepository.save(new Test(601, "Immuunhematologie, overige", notAvailable, edtaCat));
        testRepository.save(new Test(602, "Hematologie, DNA", notAvailable, edtaCat));
        testRepository.save(new Test(603, "Hematologie, bloedvlek", notAvailable, edtaCat));
        testRepository.save(new Test(604, "Hematologie, overig", notAvailable, edtaCat));

        testRepository.save(new Test(710, "Pseudomonas aeruginosa", notAvailable, serumCat));
        testRepository.save(new Test(711, "Staphylococcus aureus", notAvailable, serumCat));
        testRepository.save(new Test(712, "Escherichia coli", notAvailable, serumCat));
        testRepository.save(new Test(713, "Klebsiella pneumoniae", notAvailable, serumCat));
        testRepository.save(new Test(714, "Proteus mirabilis", notAvailable, serumCat));
        testRepository.save(new Test(715, "Serratia marcescens", notAvailable, serumCat));
        testRepository.save(new Test(716, "Candida albicans", notAvailable, serumCat));
        testRepository.save(new Test(717, "Candida tropicalis", notAvailable, serumCat));
        testRepository.save(new Test(718, "Enterococcus faecalis", notAvailable, serumCat));
        testRepository.save(new Test(719, "Streptococcus pneumoniae", notAvailable, serumCat));
        testRepository.save(new Test(720, "Neisseria meningitidis", notAvailable, serumCat));
        testRepository.save(new Test(721, "Chlamydia trachomatis", notAvailable, serumCat));
        testRepository.save(new Test(722, "Treponema pallidum", notAvailable, serumCat));
    }
}