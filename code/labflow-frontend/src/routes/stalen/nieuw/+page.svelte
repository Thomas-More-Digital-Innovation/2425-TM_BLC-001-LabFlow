<script lang="ts">
    import Nav from "../../../components/nav.svelte";
    import { goto } from '$app/navigation';
    import { getCookie, fetchAll } from '$lib/globalFunctions';
    import { getRol } from '$lib/globalFunctions';
    import { getUserId } from "$lib/globalFunctions";
    import { onMount } from "svelte";

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
    import { staalId } from '$lib/store';
    // @ts-ignore
    import IoMdCheckmarkCircle from 'svelte-icons/io/IoMdCheckmarkCircle.svelte'
    // popup laborantgegevens, test & categorie aanmaken
    import Modal from "../../../components/Modal/Modal.svelte";
	import AutoTrigger from "../../../components/Modal/AutoTrigger.svelte";
	import ContentWithoutClose from "../../../components/Modal/ContentWithoutClose.svelte";
    import { id } from "../../../components/Modal/store.js";
	import Trigger from "../../../components/Modal/Trigger.svelte";
    import Content from "../../../components/Modal/Content.svelte";
    // https://svelte-awesome-color-picker.vercel.app/
	import ColorPicker, { ChromeVariant } from 'svelte-awesome-color-picker';

    // voor het inladen van crud voor admins
    const rol = getRol();
    let userId = getUserId();

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

    // laborantgegevens
    let laborantNaam = '';
    let laborantRnummer = '';

    let errrorVeldenStaal = {
        naam: false,
        voornaam: false,
        geslacht: false,
        geboortedatum: false,
        laborantNaam: false,
        laborantRnummer: false
    }

    // variabelen voor popup test aanmaken
    let testCode = '';
    let testNaam = '';
    let eenheid = '';
    let testcategorie = '';

    let testcategorieën: any[] = [];
    let eenheden: any[] = [];
    let errorVeldenTest = {
        testCode: false,
        testNaam: false,
        eenheid: false,
        testcategorie: false,
    }

    // variabelen voor popup categorie aanmaken
    let categorienaam = '';
    let hex = "";

    let errorVeldenCategorie = {
        categorienaam: false,
        kleur: false,
    }

    // geselecteerde tests
    let geselecteerdeTests: any[] = [];

    let loading = true;
    // fetchen van tests op "tests"
    // verkrijgen nieuwe staalcode op "/api/newStaalCode"
    async function loadTests() {
        if (token != null) {
            try {
                tests = await fetchAll(token, 'tests');
                filteredTests = tests; // zonder filter worden alle tests ingeladen
                nieuweStaalCode = await fetchAll(token, 'newStaalCode'); // fetchen van nieuwe staalcode
                loading = false; // zorgt ervoor dat de modal pas opent wanneer de data is ingeladen
            } catch (error) {
                console.error("testen kon niet gefetched worden:", error);
            }
        } else {
            console.error("jwt error");
            goto('/login');
        }
    }

    onMount(() => {
        loadTests();
    })

    // laden categorieën & eenheden voor popup test aanmaken
    async function loadTestCategorieënEnEenheden() {
        if (token != null) {
            try {
                testcategorieën = await fetchAll(token, 'testcategorieen');
                eenheden = await fetchAll(token, 'readeenheid');
            } catch (error) {
                console.error("testcategorieën/eenheden kon(den) niet gefetched worden:", error);
            }
        } else {
            console.error("jwt error");
            goto('/login');
        }
    }

    function setLaborant() {
        let isValid = false;
        laborantRnummer = laborantRnummer.toUpperCase();
        // regex voor R-nummer: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions/Character_classes 
        const regex = /^R\d{7}$/;

        if (!laborantNaam) {
            errrorVeldenStaal.laborantNaam = true;
        }
        if (!laborantRnummer || !regex.test(laborantRnummer)) {
            errrorVeldenStaal.laborantRnummer = true;
        }

        if (laborantNaam && laborantRnummer && regex.test(laborantRnummer)) {
            isValid = true;
        }
        if (isValid) {
            return $id = null;
        }
        return;
    }

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

    // verwijderen van geselecteerde testen
    function verwijderSelectie() {
        geselecteerdeTests = [];
    }

    // toevoegen van geselecteerde test, of verwijderen indien al geselecteerd
    function toggleTestSelectie(testCode: number) {
        if (geselecteerdeTests.includes(testCode)) {
            geselecteerdeTests = geselecteerdeTests.filter(code => code !== testCode);
        } else {
            geselecteerdeTests = [...geselecteerdeTests, testCode];
        }
    }

    // POST: Aanmaken van een nieuwe test
    let errorMessageTest = '';
    async function nieuweTest() {
        // resetten errorvelden
        errorVeldenTest = { testCode: false, testNaam: false, eenheid: false, testcategorie: false };
        let isValid = true;

        if (!testCode) {
            errorVeldenTest.testCode = true;
            isValid = false;
        }
        if (!testNaam) {
            errorVeldenTest.testNaam = true;
            isValid = false;
        }
        if (!eenheid) {
            errorVeldenTest.eenheid = true;
            isValid = false;
        }
        if (!testcategorie) {
            errorVeldenTest.testcategorie = true;
            isValid = false;
        }
        if (!isValid) {
            errorMessageTest = 'Vul alle verplichte velden in.';
            return;
        } try {
            await fetch("http://localhost:8080/api/createtest", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + token
                },
                body: JSON.stringify({
                    testCode: testCode,
                    naam: testNaam,
                    eenheid: {
                        id: eenheid
                    },
                    testcategorie: {
                        id: testcategorie
                    }
                }),
            });
        } catch (error) {
            console.error("test kon niet worden aangemaakt: ", error);
        }
        tests = await fetchAll(token, 'tests'); // tests refreshen, triggert een refresh
        filteredTests = tests;
        return $id = null;
    }

    let errorMessageStaal = '';
    // POST: Aanmaken van een nieuwe staal
    async function nieuweStaal() {
        // Resetten van de errorvelden
        errrorVeldenStaal = { naam: false, voornaam: false, geslacht: false, geboortedatum: false, laborantNaam: true, laborantRnummer: true };

        // Validatie van de input
        let isValid = true;

        if (!naam) {
            errrorVeldenStaal.naam = true;
            isValid = false;
        }
        if (!voornaam) {
            errrorVeldenStaal.voornaam = true;
            isValid = false;
        }
        if (!geboortedatum) {
            errrorVeldenStaal.geboortedatum = true;
            isValid = false;
        }
        if (!geslacht) {
            errrorVeldenStaal.geslacht = true;
            isValid = false;
        }
        // errorMessageStaal tonen indien niet alle velden zijn ingevuld
        if (!isValid) {
            errorMessageStaal = 'Vul alle verplichte velden in.';
            return;
        }
        
        const geselecteerdeTestsArray = Array.from(geselecteerdeTests).map(testCode => ({
            test: { testCode: testCode }
        }));

        try {
            const response  = await fetch("http://localhost:8080/api/createstaal", {
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
                    laborantNaam: laborantNaam,
                    laborantRnummer: laborantRnummer,
                    user: {
                        id: userId
                    },
                    registeredTests: geselecteerdeTestsArray
                }),
            });
            // César: doorgeven van aangemaakte staal's id naar volgend scherm
            const data = await response.json();
            staalId.set(data.id);

        } catch (error) {
            console.error("staal kon niet worden aangemaakt: ", error);
        }
        return goto("/stalen/labels");
    }

    let errorMessageCategorie = '';
        // POST: Aanmaken van een nieuwe categorie
        async function nieuweCategorie() {
            errorVeldenCategorie = { categorienaam: false, kleur: false };
            let isValid = true;
            const regex = /^#([0-9A-F]{3}){1,2}$/i;

            if (!categorienaam) {
                errorVeldenCategorie.categorienaam = true;
                isValid = false;
            }
            if (!hex || !regex.test(hex)) {
                errorVeldenCategorie.kleur = true;
                isValid = false;
            }
            // errorMessageStaal tonen indien niet alle velden zijn ingevuld
            if (!isValid) {
                errorMessageCategorie = 'Geef de categorie een naam en een kleur.';
                return;
            }

            try {
            await fetch("http://localhost:8080/api/createtestcategorie", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + token
                },
                body: JSON.stringify({
                    naam: categorienaam,
                    kleur: hex,
                }),
            });
        } catch (error) {
            console.error("categorie kon niet worden aangemaakt: ", error);
        }
        return $id = null;
    }
</script>


<!-- popup modal voor het ingeven van de laborantgegevens die automatisch opent onmount van de pagina -->
<!-- loading variabele zorgt ervoor dat de modal pas opent wanneer de data is ingeladen -->
{#if !loading}
<Modal>
	<ContentWithoutClose>
		<h1 class="font-bold text-xl mb-2">laborantgegevens</h1>
		<div class="flex space-x-4 mb-4 ">
			<div class="flex flex-col w-1/2">
				<label for="naam">Volledige Naam</label>
				<input type="text" id="naam" name="naam" bind:value={laborantNaam} class="rounded-lg text-black bg-gray-200 h-12 pl-3 {errrorVeldenStaal.laborantNaam ? 'border-2 border-red-500' : ''}">
			</div>
			<div class="flex flex-col w-1/2">
                <label for="r-nummer">R-Nummer <span class="{errrorVeldenStaal.laborantRnummer ? 'text-red-500 inline-block' : 'hidden'}">moet in format rXXXXXXX met 7 cijfers</span></label>
				<input type="text" id="r-nummer" name="r-nummer" bind:value={laborantRnummer} class="rounded-lg text-black bg-gray-200 h-12 pl-3 {errrorVeldenStaal.laborantRnummer ? 'border-2 border-red-500' : ''}">
			</div>
		</div>
        <button type="button" on:click={setLaborant}>
            <button class="bg-blue-500 text-xl rounded-lg p-3 text-white h-12 w-32 justify-center items-center flex">Start</button>
        </button>
	</ContentWithoutClose>
	<!-- <AutoTrigger>
	</AutoTrigger> -->
</Modal>
{/if}

<!-- navbar -->
<Nav/>

<!-- pagina content -->
<div class="px-8">
    <div class="bg-slate-200 w-full h-full rounded-2xl p-5">

        <h1 class="font-bold text-xl mb-2">Patiëntgegevens</h1>
        {#if errorMessageStaal}
            <div class="text-red-500 mb-2">{errorMessageStaal}</div>
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
                    class="rounded-lg text-black bg-gray-200 h-10 pl-3 {errrorVeldenStaal.naam ? 'border-2 border-red-500' : ''}">
                </div>
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Voornaam</p>
                    <input 
                    type="text"
                    id="voornaam"
                    name="voornaam"
                    bind:value={voornaam}
                    class="rounded-lg text-black bg-gray-200 h-10 pl-3 {errrorVeldenStaal.voornaam ? 'border-2 border-red-500' : ''}">
                </div>
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Geboortedatum</p>
                    <input 
                    type="date"
                    id="geboortedatum"
                    name="geboortedatum"
                    bind:value={geboortedatum}
                    class="rounded-lg text-black bg-gray-200 h-10 pl-3 px-3 {errrorVeldenStaal.geboortedatum ? 'border-2 border-red-500' : ''}">
                </div>
                <div class="flex flex-col justify-center pl-5">
                    <!-- https://svelte.dev/repl/2b143322f242467fbf2b230baccc0484?version=3.23.2 -->
                    <p class="text-gray-400">Geslacht</p>
                    <div>
                        <label class="container mr-5 {errrorVeldenStaal.geslacht ? 'text-red-500 font-bold' : ''}">
                            <input type="radio" name="radio" bind:group={geslacht} value="M">
                            Man
                        </label>
                        <label class="container {errrorVeldenStaal.geslacht ? 'text-red-500 font-bold' : ''}">
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

                <button type="button" on:click={verwijderSelectie} class="bg-red-500 h-12 w-36 rounded-lg text-white mx-6">Verwijder selectie</button>

            
                <div class="flex items-center w-1/3">
                    <!-- dynamisch tonen hoeveel geselecteerde tests -->
                    <p class=" text-blue-600">
                        <span>{geselecteerdeTests.length}</span> geselecteerd
                    </p>
                </div>
            
                <!-- knoppen en modals voor aanmaken cat & test -->
                <div class="flex flex-row justify-end space-x-2 w-1/3">
                    {#if rol === 'admin'}
                    <Modal>
                        <Content>
                            <h1 class="font-bold text-xl mb-4">Categorie Aanmaken</h1>
                            {#if errorMessageCategorie}
                            <div class="text-red-500 mb-2">{errorMessageCategorie}</div>
                            {/if}
                            <div class="w-200 flex flex-row place-content-between">
                                <div class="flex flex-col w-3/5 place-content-between">
                                    <div class="flex flex-col">
                                        <label for="categorienaam">Naam</label>
                                        <input type="text" id="categorienaam" name="categorienaam" bind:value={categorienaam} class="rounded-lg text-black bg-gray-200 h-12 pl-3
                                        {errorVeldenCategorie.categorienaam ? 'border-2 border-red-500' : ''}">
                                    </div>
                                    <div>
                                        <button on:click={nieuweCategorie} type="button" class="bg-green-500 rounded-lg p-3 mb-3 text-black h-12 flex flex-row items-center justify-center flex-grow w-56 font-bold text-lg">Opslaan
                                            <div class="w-5 h-5 ml-5"><IoMdCheckmarkCircle/></div>
                                        </button>
                                    </div>
                                </div>
                                <div class="flex flex-col">
                                    <label for="kleur" class="pl-2 {errorVeldenCategorie.kleur ? 'text-red-500 font-bold' : ''}">Kleur</label>
                                    <div>
                                        <ColorPicker
                                            bind:hex
                                            components={ChromeVariant}
                                            sliderDirection="horizontal"
                                            isDialog={false}
                                        />
                                    </div>
                                </div>
                            </div>
                        </Content>

                        <Trigger>
                            <button class="bg-gray-200 rounded-lg p-3 text-black h-12 flex flex-row items-center justify-center flex-grow">
                                <div class="w-3 h-3 mr-2"><FaPlus/></div>
                                Categorie aanmaken
                            </button>
                        </Trigger>

                    </Modal>

                    <Modal>
                        <Content>
                            <h1 class="font-bold text-xl mb-4">Test Aanmaken</h1>
                            {#if errorMessageTest}
                            <div class="text-red-500 mb-2">{errorMessageTest}</div>
                            {/if}
                            <div class="flex flex-row space-x-4">
                                <div class="flex flex-col w-1/2">
                                    <label for="testCode">Testcode</label>
                                    <input type="text" id="testCode" name="testCode" bind:value={testCode} class="rounded-lg text-black bg-gray-200 h-12 pl-3
                                    {errorVeldenTest.testCode ? 'border-2 border-red-500' : ''}">
                                </div>
                                <div class="flex flex-col w-1/2">
                                    <label for="testNaam">Eenheid</label>
                                    <select id="eenheid" name="eenheid" bind:value={eenheid} class="rounded-lg text-black bg-gray-200 h-12 pl-3
                                    {errorVeldenTest.eenheid ? 'border-2 border-red-500' : ''}">
                                        <option value="" disabled>Selecteer een eenheid</option>
                                        {#each eenheden as eenheid}
                                            <option value={eenheid.id}>{eenheid.naam} ({eenheid.afkorting})</option>
                                        {/each}
                                    </select>
                                </div>
                            </div>
                            <div class="flex flex-row space-x-4 my-4">
                                <div class="flex flex-col w-1/2">
                                    <label for="naam">Naam</label>
                                    <input type="text" id="naam" name="naam" bind:value={testNaam} class="rounded-lg text-black bg-gray-200 h-12 pl-3
                                    {errorVeldenTest.testNaam ? 'border-2 border-red-500' : ''}">
                                </div>
                                <!-- https://svelte.dev/repl/16778e290bf548f790dc45d249bed94d?version=3.46.4  -->
                                <div class="flex flex-col w-1/2">
                                    <label for="testcategorie">Categorie</label>
                                    <select id="testcategorie" name="testcategorie" bind:value={testcategorie} class="rounded-lg text-black bg-gray-200 h-12 pl-3
                                    {errorVeldenTest.testcategorie ? 'border-2 border-red-500' : ''}">
                                        <option value="" disabled>Selecteer een categorie</option>
                                        {#each testcategorieën as categorie}
                                            <option value={categorie.id}>{categorie.naam}</option>
                                        {/each}
                                    </select>
                                </div>
                            </div>

                            <button on:click={nieuweTest} type="button" class="bg-green-500 rounded-lg p-3 text-black h-12 flex flex-row items-center justify-center flex-grow w-56 font-bold text-lg">Opslaan
                            <div class="w-5 h-5 ml-5"><IoMdCheckmarkCircle/></div>
                            </button>
                        </Content>

                        <Trigger>
                            <button on:click={loadTestCategorieënEnEenheden} class="bg-gray-200 rounded-lg p-3 text-black h-12 flex flex-row items-center justify-center flex-grow">
                                <div class="w-3 h-3 mr-2"><FaPlus/></div>
                                Test aanmaken
                            </button>
                        </Trigger>
                    </Modal>
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
                    checked={geselecteerdeTests.includes(test.testCode)}
                    class="w-5 h-5 mt-2 appearance-none border-2 border-gray-300 rounded-md checked:bg-blue-600 checked:border-transparent focus:outline-none">                                                 
                </div>
                <div class="col-span-2">
                    <p class="text-gray-400">Testcode</p>
                    <p>{test?.testCode || 'Loading...'}</p>
                </div>
                <div class="col-span-4">
                    <p class="text-gray-400">Naam</p>
                    <p class="truncate">{test?.naam || 'Loading...'}</p>
                </div>
                <div class="col-span-2">
                    <p class="text-gray-400">Categorie</p>
                    <p>{test?.testcategorie.naam || 'Loading...'}</p>
                </div>
                <div class="col-span-2">
                    <p class="text-gray-400">Eenheid</p>
                    <p>{test?.eenheid.naam || 'Loading...'}</p>
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