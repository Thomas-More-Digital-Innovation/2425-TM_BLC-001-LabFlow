<script lang="ts">
    import Nav from "../../../components/nav.svelte";
    import { goto } from '$app/navigation';
    import { getCookie, fetchAll } from '$lib/globalFunctions';
    import { getRol } from '$lib/globalFunctions';
    // @ts-ignore
    import FaTrashAlt from 'svelte-icons/fa/FaTrashAlt.svelte'
    // @ts-ignore
    import FaRegEdit from 'svelte-icons/fa/FaRegEdit.svelte'
    // @ts-ignore
    import FaPlus from 'svelte-icons/fa/FaPlus.svelte'
    // @ts-ignore
    import FaArrowLeft from 'svelte-icons/fa/FaArrowLeft.svelte'
    // @ts-ignore
    import FaArrowRight from 'svelte-icons/fa/FaArrowRight.svelte'
    // @ts-ignore
    import GoX from 'svelte-icons/go/GoX.svelte'
    import { getUserId } from "$lib/globalFunctions";

    // voor het inladen van crud voor admins
    const rol = getRol();

    let tests: any[] = [];
    let filteredTests: any[] = [];
    let searchCode = '';
    const token = getCookie('authToken') || '';

    // nieuwe staalcode
    let nieuweStaalCode = '';
    let naam = '';
    let voornaam = '';
    let geslacht = '';
    let geboortedatum = '';

    let errrorVelden = {
        naam: false,
        voornaam: false,
        geslacht: false,
        geboortedatum: false
    }

    let userId = getUserId();

    // geselecteerde tests
    let geselecteerdeTests: any[] = [];

    // fetchen van tests op "tests"
    // verkrijgen nieuwe staalcode op "/api/newStaalCode"
    async function loadData() {
        if (token != null) {
            try {
                tests = await fetchAll(token, 'tests');
                filteredTests = tests; // zonder filter worden alle tests ingeladen
                nieuweStaalCode = await fetchAll(token, 'newStaalCode'); // fetchen van nieuwe staalcode
            } catch (error) {
                console.error("data kon niet gefetched worden:", error);
            }
        } else {
            console.error("jwt error");
            goto('/login');
        }
    }

    loadData();

    // zoeken op basis van code
    function filterTests() {
        filteredTests = tests.filter(test => {
            const codeMatch = test.testCode.toString().toLowerCase().includes(searchCode.toLowerCase());
            console.log(geselecteerdeTests);
            return codeMatch;
        });
    }

    // verwijderen van zoekparameter, terug alle tests tonen
    function verwijderZoek() {
        searchCode = '';
        filteredTests = tests;
    }

    // toevoegen van geselecteerde test, of verwijderen indien al geselecteerd
    function toggleTestSelectie(testCode: number) {
        if (geselecteerdeTests.includes(testCode)) {
            geselecteerdeTests = geselecteerdeTests.filter(code => code !== testCode);
        } else {
            geselecteerdeTests = [...geselecteerdeTests, testCode];
        }
    }

    let errorMessage = '';
    // POST: Aanmaken van een nieuwe staal
    async function nieuweStaal() {
        // Resetten van de errorvelden
        errrorVelden = { naam: false, voornaam: false, geslacht: false, geboortedatum: false };

        // Validatie van de input
        let isValid = true;

        if (!naam) {
            errrorVelden.naam = true;
            isValid = false;
        }
        if (!voornaam) {
            errrorVelden.voornaam = true;
            isValid = false;
        }
        if (!geboortedatum) {
            errrorVelden.geboortedatum = true;
            isValid = false;
        }
        if (!geslacht) {
            errrorVelden.geslacht = true;
            isValid = false;
        }
        // errormessage tonen indien niet alle velden zijn ingevuld
        if (!isValid) {
            errorMessage = 'Vul alle verplichte velden in.';
            return;
        }
        
        const geselecteerdeTestsArray = Array.from(geselecteerdeTests).map(testCode => ({
            test: { testCode: testCode }
        }));

        try {
            await fetch("http://localhost:8080/api/createstaal", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + token
                },
                body: JSON.stringify({
                    staalCode: nieuweStaalCode,
                    patientAchternaam: naam,
                    patientVoornaam: voornaam,
                    patientGeslacht: geslacht,
                    patientGeboorteDatum: geboortedatum,
                    user: {
                        id: userId
                    },
                    registeredTests: geselecteerdeTestsArray
                }),
            });
        } catch (error) {
            console.error("staal kon niet worden aangemaakt: ", error);
        }
        return goto("/stalen");
    }
</script>


<Nav/>
<div class="px-8">
    <div class="bg-slate-200 w-full h-full rounded-2xl p-5">

        <h1 class="font-bold text-xl mb-2">Patiëntgegevens</h1>
        {#if errorMessage}
            <div class="text-red-500 mb-2">{errorMessage}</div>
        {/if}
        <div class="flex flex-row space-x-4">
            <!-- Invullen patientgegevens -->
            <div class="grid grid-cols-5 bg-white rounded-lg h-20 w-5/6 space-x-2  px-2">
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Code</p>
                    <p class="font-bold">{nieuweStaalCode || "loading..."}</p>
                </div>
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Achternaam</p>
                    <input 
                    type="text"
                    id="naam"
                    name="naam"
                    bind:value={naam}
                    class="rounded-lg text-black bg-gray-200 h-10 pl-3 {errrorVelden.naam ? 'border-2 border-red-500' : ''}">
                </div>
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Voornaam</p>
                    <input 
                    type="text"
                    id="voornaam"
                    name="voornaam"
                    bind:value={voornaam}
                    class="rounded-lg text-black bg-gray-200 h-10 pl-3 {errrorVelden.voornaam ? 'border-2 border-red-500' : ''}">
                </div>
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Geboortedatum</p>
                    <input 
                    type="date"
                    id="geboortedatum"
                    name="geboortedatum"
                    bind:value={geboortedatum}
                    class="rounded-lg text-black bg-gray-200 h-10 pl-3 px-3 {errrorVelden.geboortedatum ? 'border-2 border-red-500' : ''}">
                </div>
                <div class="flex flex-col justify-center pl-5">
                    <!-- https://svelte.dev/repl/2b143322f242467fbf2b230baccc0484?version=3.23.2 -->
                    <p class="text-gray-400">Geslacht</p>
                    <div>
                        <label class="container mr-5 {errrorVelden.geslacht ? 'text-red-500 font-bold' : ''}">
                            <input type="radio" name="radio" bind:group={geslacht} value="M">
                            Man
                        </label>
                        <label class="container {errrorVelden.geslacht ? 'text-red-500 font-bold' : ''}">
                            <input type="radio" name="radio" bind:group={geslacht} value="V">
                            Vrouw
                        </label>
                    </div>
                </div>
            </div>


            <!-- navigatie volgende en terug -->
            <div class="pb-5 flex flex-row space-x-2 justify-end w-3/12">
                <button on:click={() => { goto("/stalen") }} class="bg-gray-400 text-xl rounded-lg p-3 text-white h-20 w-1/2 flex flex-row items-center justify-center">
                    <div class="w-5 h-5 mr-2"><FaArrowLeft/></div>
                    Terug
                </button>
                <!-- staat tijdelijk naar volgende pagina omdat ik nog niet weet hoe César zijn pagina heet -->
                <button on:click={nieuweStaal} class="bg-blue-600 text-xl rounded-lg p-3 text-white h-20 w-1/2 flex flex-row items-center justify-center">
                    Volgende
                    <div class="w-5 h-5 ml-2"><FaArrowRight/></div>
                </button>
            </div>
        </div>
        

        <div class="rounded-xl bg-white">
            <div class="bg-white rounded-xl p-3 flex flex-row items-center place-content-between">
    
                <div class="w-1/4 flex items-center">
                    <!-- zoeken op code -->
                    <input
                        type="text" 
                        id="searchCode" 
                        name="searchCode" 
                        placeholder="zoeken op code" 
                        bind:value={searchCode} 
                        on:input={filterTests}
                        class="h-12 rounded-l-lg text-black pl-3 bg-gray-200 w-full">
                    <button on:click={verwijderZoek} class="w-12 h-12 p-3 flex items-center justify-center bg-red-200 rounded-r-lg">
                        <GoX />
                    </button>
                </div>
            
                <div class="flex items-center w-1/3">
                    <!-- dynamisch tonen hoeveel geselecteerde tests -->
                    <p class=" text-blue-600">
                        <span>{geselecteerdeTests.length}</span> geselecteerd
                    </p>
                </div>
            
                <!-- knoppen voor aanmaken cat & test -->
                <div class="flex flex-row justify-end space-x-2 w-1/3">
                    {#if rol === 'admin'}
                    <button class="bg-gray-200 rounded-lg p-3 text-black h-12 flex flex-row items-center justify-center flex-grow">
                        <div class="w-3 h-3 mr-2"><FaPlus/></div>
                        Categorie aanmaken
                    </button>
                    <button class="bg-gray-200 rounded-lg p-3 text-black h-12 flex flex-row items-center justify-center flex-grow">
                        <div class="w-3 h-3 mr-2"><FaPlus/></div>
                        Test aanmaken
                    </button>
                    {/if}
                </div>
            </div>
            
            <!-- tabel met alle tests -->
            {#each filteredTests as test}
            <div class="grid grid-cols-12 gap-4 h-16 items-center px-3 border-b border-gray-300">
                <div class="col-span-1">
                    <!-- https://svelte.dev/repl/986adbafc5b042cbbf979c1381c7cacc?version=3.50.1 -->
                    <!-- checkbox voor het selecteren van tests -->
                    <input 
                    type="checkbox"
                    on:change={() => toggleTestSelectie(test.testCode)}
                    class="w-5 h-5 mt-2 appearance-none border-2 border-gray-300 rounded-md checked:bg-blue-600 checked:border-transparent focus:outline-none">                                                 
                </div>
                <div class="col-span-2">
                    <p class="text-gray-400">Code</p>
                    <p>{test?.testCode || 'Loading...'}</p>
                </div>
                <div class="col-span-5">
                    <p class="text-gray-400">Naam</p>
                    <p class="truncate">{test?.naam || 'Loading...'}</p>
                </div>
                <div class="col-span-3">
                    <p class="text-gray-400">Categorie</p>
                    <p>{test?.testcategorie.naam || 'Loading...'}</p>
                </div>
                <!-- admin-only crud knoppen -->
                {#if rol === 'admin'}
                <div class="col-span-1 flex justify-end space-x-2">
                    <div class="h-10 w-10 bg-blue-400 p-2 rounded-lg text-white">
                        <FaRegEdit />
                    </div>
                    <div class="h-10 w-10 bg-red-500 p-2 rounded-lg text-white">
                        <FaTrashAlt />
                    </div>
                </div>
                {/if}
            </div>
            {/each}
        </div>
    </div>
</div>