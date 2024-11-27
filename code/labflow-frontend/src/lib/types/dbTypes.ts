export interface Staal {
    id: number;
    staalCode: string;
    patientAchternaam: string;
    patientVoornaam: string;
    patientGeboorteDatum: Date;
    patientGeslacht: string;
    laborantNaam: string;
    laborantRnummer: string;
    aanmaakDatum: Date;
    status: string;
    confirmDelete: boolean; // voor het verwijderen van een staal
    user: User; // gebruiker die dit staal heeft aangemaakt/aangepast
    registeredTests: Test[]; // testen die aan staal gekoppeld zijn/worden

}

export interface User {
    id: number;
    email: string;
    voorNaam: string;
    achterNaam: string;
    wachtwoord: string;
}

export interface Test {}

export interface Status {
    status: string;
}