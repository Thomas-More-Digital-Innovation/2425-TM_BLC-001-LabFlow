import type { Staal } from "$lib/types/dbTypes";

export function createDefaultStaal(): Staal {
    return {
        id: 0,
        staalCode: '',
        patientAchternaam: '',
        patientVoornaam: '',
        patientGeboorteDatum: '',
        patientGeslacht: '',
        laborantNaam: '',
        laborantRnummer: '',
        aanmaakDatum: '',
        status: '',
        confirmDelete: false,
        user: {
            id: 0,
            email: '',
            voorNaam: '',
            achterNaam: '',
            wachtwoord: '',
            fullName: '',
            rol: {
                id: 0,
                naam: ''
            },
            newWachtwoord: null,
            confirmDelete: false
        },
        registeredTests: []
    };
}
